package Repository;

import Model.ProgramState.ProgramState;

import java.util.ArrayList;

public class Repository implements IRepository {
    private ArrayList<ProgramState> elems;

    public Repository(ProgramState prgState) {
        this.elems = new ArrayList<ProgramState>();
        this.elems.add(prgState);
    }

    @Override
    public ProgramState getCurrentProgramState() {
        return this.elems.get(0);
    }

    @Override
    public void addProgramState(ProgramState newProgramState) {
        this.elems.set(0, newProgramState);
    }
}
