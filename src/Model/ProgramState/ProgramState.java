package Model.ProgramState;
import Model.ADT.MyDictionary;
import Model.ADT.MyList;
import Model.ADT.MyStack;
import Model.Statement.IStatement;
import Model.Value.IValue;
import Model.Value.StringValue;

import java.io.BufferedReader;
import java.nio.Buffer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ProgramState {
    private MyStack<IStatement> executionStack;
    private MyDictionary<String, IValue> symbolTable;
    private MyList<IValue> output;
    private MyDictionary<StringValue, BufferedReader> fileTable;
    private IStatement originalProgram;

    public ProgramState(MyStack<IStatement> exeStack, MyDictionary<String, IValue> symTable, MyList<IValue> out, MyDictionary<StringValue, BufferedReader> fileT, IStatement origPrg) {
        this.executionStack = exeStack;
        this.symbolTable = symTable;
        this.output = out;
        this.fileTable = fileT;
        this.originalProgram = origPrg.deepCopy();
        this.executionStack.push(this.originalProgram);
    }

    // new copy constructor for the original program state
    public ProgramState(IStatement statement) {
        this.executionStack = new MyStack<>();
        this.symbolTable = new MyDictionary<>();
        this.output = new MyList<>();
        this.fileTable = new MyDictionary<>();
        this.originalProgram = statement;
        this.executionStack.push(statement);
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

    public MyDictionary<StringValue, BufferedReader> getFileTable() { return this.fileTable; }

    public IStatement getOriginalProgram() {
        return this.originalProgram;
    }

    @Override
    public String toString() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now) +
                "\nExecution Stack:\n" +
                this.executionStack.toString() +
                "\n" +
                "Symbol Table:\n" +
                this.symbolTable.toString() +
                "\n" +
                "Output:\n" +
                this.output.toString() +
                "\n" +
                //this.fileTable.toString() +
                //"\n" +
                "-".repeat(50);
    }
}
