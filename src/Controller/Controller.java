package Controller;

import Model.ADT.IStack;
import Model.ADT.MyList;
import Model.Exceptions.*;
import Model.ProgramState.ProgramState;
import Model.Statement.*;
import Model.Value.IValue;
import Repository.IRepository;

public class Controller {
    private IRepository repo;
    private boolean displayFlag;

    public Controller(IRepository repo) {
        this.repo = repo;
        this.displayFlag = true;
    }

    public void addProgramState(ProgramState prg) {
        this.repo.addProgramState(prg);
    }

    public ProgramState oneStep(ProgramState currentState) throws ControllerException, StackException, StatementException, ExpressionException, DictionaryException {
        IStack<IStatement> executionStack = currentState.getExecutionStack();
        if (executionStack.isEmpty()) {
            throw new ControllerException("Program state's execution stack is empty.");
        }
        IStatement topStatement = executionStack.pop();
        return topStatement.execute(currentState);
    }

    public void allSteps() throws ControllerException, StatementException, StackException, ExpressionException, DictionaryException, ListException {
        ProgramState programState = this.repo.getCurrentProgramState();
        if (this.displayFlag) {
            System.out.println("Program execution started:");
            System.out.print(programState.toString() + "\n");
        }
        int outputListSize = 0;
        MyList<IValue > output;
        while (!programState.getExecutionStack().isEmpty()) {
            this.oneStep(programState);
            if (this.displayFlag) {
                System.out.println(programState.toString());
            } else {
                output = programState.getOutput();
                if (outputListSize != output.size()) {
                    outputListSize = output.size();
                    System.out.println(output.getElemAtIndex(output.size() - 1).toString());
                }
            }
        }
    }

    public void turnDisplayFlagOn() {
        this.displayFlag = true;
    }

    public void turnDisplayFlagOff() {
        this.displayFlag = false;
    }
}
