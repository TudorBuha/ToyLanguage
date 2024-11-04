package Model.ProgramState;
import Model.ADT.MyDictionary;
import Model.ADT.MyList;
import Model.ADT.MyStack;
import Model.Statement.IStatement;
import Model.Value.IValue;

public class ProgramState {
    private MyStack<IStatement> executionStack;
    private MyDictionary<String, IValue> symbolTable;
    private MyList<IValue> output;
    private IStatement originalProgram;

    public ProgramState(MyStack<IStatement> exeStack, MyDictionary<String, IValue> symTable, MyList<IValue> out, IStatement origPrg) {
        this.executionStack = exeStack;
        this.symbolTable = symTable;
        this.output = out;
        this.originalProgram = origPrg.deepCopy();
        this.executionStack.push(this.originalProgram);
    }

    public MyStack<IStatement> getExecutionStack() {
        return this.executionStack;
    }

    public MyDictionary<String, IValue> getSymbolTable() {
        return this.symbolTable;
    }

    public MyList<IValue> getOutput() {
        return this.output;
    }

    public IStatement getOriginalProgram() {
        return this.originalProgram;
    }

    @Override
    public String toString() {
        return "Execution Stack: " +
                this.executionStack.toString() +
                "\n" +
                "Symbol Table: " +
                this.symbolTable.toString() +
                "\n" +
                "Output: " +
                this.output.toString() +
                "\n";
    }
}
