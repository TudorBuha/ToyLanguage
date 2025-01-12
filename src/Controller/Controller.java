package Controller;

import Model.Exceptions.*;
import Model.ProgramState.ProgramState;
import Model.Value.IValue;
import Model.Value.RefValue;
import Repository.IRepository;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class Controller {
    private IRepository repo;
    private ExecutorService executor;

    public Controller(IRepository repo) {
        this.repo = repo;
    }

    public void addProgramState(ProgramState prg) {
        this.repo.addProgramState(prg);
    }

    List<ProgramState> removeCompletedPrg(List<ProgramState> inProgramList) {
        return inProgramList.stream()
                .filter(ProgramState::isNotCompleted)
                .collect(Collectors.toList());
    }

    public void oneStepForAllPrg(List<ProgramState> programStatesList) throws InterruptedException {
        List<Callable<ProgramState>> callList = programStatesList.stream()
                .map((ProgramState p) -> (Callable<ProgramState>)(p::oneStep))
                .collect(Collectors.toList());
        List<ProgramState> newProgramStatesList = this.executor.invokeAll(callList).stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        programStatesList.addAll(newProgramStatesList);
        programStatesList.forEach(prg -> {
            try {
                System.out.println(prg);
                repo.logPrgStateExec(prg);
            } catch (FileException e) {
                e.printStackTrace();
            }
        });
        this.repo.setPrgList(programStatesList);
    }

    public void allSteps() throws InterruptedException {
        this.executor = Executors.newFixedThreadPool(2);
        List<ProgramState> prgList = this.removeCompletedPrg(this.repo.getPrgList());
        while (prgList.size() > 0) {
            this.conservativeGarbageCollector(prgList);
            this.oneStepForAllPrg(prgList);
            prgList = this.removeCompletedPrg(this.repo.getPrgList());
        }
        this.executor.shutdownNow();
        this.repo.setPrgList(prgList);
    }

    private List<Integer> getAddressesFromSymTable(Collection<IValue> symTableValues) {
        return symTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> {RefValue v1 = (RefValue) v; return v1.getAddress();})
                .collect(Collectors.toList());
    }

    private List<Integer> addIndirectAddresses(List<Integer> addressesFromSymbolTable, Map<Integer, IValue> heap) {
        boolean change = true;
        List<Integer> newAddressList = new ArrayList<>(addressesFromSymbolTable);   //copy of list in order to add indirections
        // we go through heapSet again and again and each time we add to the address list new indirection level
        // and new addresses which must NOT be deleted
        while (change) {
            List<Integer> appendingList;
            change = false;

            appendingList = heap.entrySet().stream()
                    .filter(e -> e.getValue() instanceof RefValue)      // check if val in heap is RefValue, so it can have indirections
                    .filter(e -> newAddressList.contains(e.getKey()))   // check if address list contains ref to this
                    .map(e -> ((RefValue) e.getValue()).getAddress())   // map the reference to its address, so we can add it
                    .filter(e -> !newAddressList.contains(e))           // check if the address list already has that reference from prev elems
                    .collect(Collectors.toList());                      // collect to list
            if (!appendingList.isEmpty()) {
                // if we get here => we still have indirect references, so we have to keep checking
                change = true;
                newAddressList.addAll(appendingList);
            }
        }
        return newAddressList;
    }

    private Map<Integer, IValue> garbageCollector(List<Integer> referencedAddresses, Map<Integer, IValue> heap) {
        return heap.entrySet().stream()
                .filter(e -> referencedAddresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private void conservativeGarbageCollector(List<ProgramState> prgList) {
        Map<Integer, IValue> heapContent = prgList.get(0).getHeapTable().getContent();
        List<IValue> symbolTableValues = prgList.stream().flatMap(prg -> prg.getSymbolTable().getContent().values().stream()).collect(Collectors.toList());
        List<Integer> symbolTableAddresses = this.getAddressesFromSymTable(symbolTableValues);
        List<Integer> allReferencedAddresses = this.addIndirectAddresses(symbolTableAddresses, heapContent);
        prgList.get(0).getHeapTable().setContent(this.garbageCollector(allReferencedAddresses, heapContent));   // garbage collector call
    }
}
