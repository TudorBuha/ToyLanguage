package Model.Statement;

import Model.ADT.IDictionary;
import Model.ADT.IStack;
import Model.Exceptions.DictionaryException;
import Model.Exceptions.ExpressionException;
import Model.Exceptions.StatementException;
import Model.ProgramState.ProgramState;
import Model.Type.IType;

public class CompoundStatement implements IStatement{
    private IStatement statement1;
    private IStatement statement2;

    public CompoundStatement(IStatement s1, IStatement s2) {
        this.statement1 = s1;
        this.statement2 = s2;
    }

    @Override
    public ProgramState execute(ProgramState currentState) {
        IStack<IStatement> executionStack = currentState.getExecutionStack();
        executionStack.push(this.statement2);
        executionStack.push(this.statement1);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new CompoundStatement(this.statement1.deepCopy(), this.statement2.deepCopy());
    }

    @Override
    public String toString() {
        return "(" + this.statement1.toString() + "; " + this.statement2.toString() + ")";
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnv) throws StatementException, ExpressionException, DictionaryException {
        return this.statement2.typeCheck(this.statement1.typeCheck(typeEnv));
    }
}
