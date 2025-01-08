package Model.Statement;

import Model.ADT.IDictionary;
import Model.ADT.MyDictionary;
import Model.ADT.MyStack;
import Model.Exceptions.*;
import Model.ProgramState.ProgramState;
import Model.Type.IType;
import Model.Value.IValue;

import java.util.Map;
import java.util.stream.Collectors;

public class forkStatement implements IStatement{
    private IStatement statement;

    public forkStatement(IStatement stmt) {
        this.statement = stmt;
    }

    @Override
    public ProgramState execute(ProgramState currentState) throws StatementException {
        MyStack<IStatement> forkedExecutionStack = new MyStack<>();

        Map<String, IValue> symbolTableContent = currentState.getSymbolTable().getContent();
        MyDictionary<String, IValue> forkedSymbolTable = new MyDictionary<>();
        forkedSymbolTable.setContent(symbolTableContent.entrySet().stream()
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue().deepCopy())));
        return new ProgramState(forkedExecutionStack, forkedSymbolTable, currentState.getOutput(), currentState.getFileTable(), currentState.getHeapTable(), this.statement);
    }

    @Override
    public IStatement deepCopy() {
        return new forkStatement(this.statement.deepCopy());
    }

    @Override
    public String toString() {
        return "fork(" + this.statement.toString() + ")";
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnv) throws StatementException, ExpressionException, DictionaryException {
        return this.statement.typeCheck(typeEnv.shallowCopy());
    }
}
