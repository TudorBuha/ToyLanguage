package Model.Statement;

import Model.Exceptions.DictionaryException;
import Model.Exceptions.ExpressionException;
import Model.Expression.IExpression;
import Model.ProgramState.ProgramState;
import Model.Value.IValue;

public class PrintStatement implements IStatement{
    private IExpression expression;

    public PrintStatement(IExpression e) {
        this.expression = e;
    }

    @Override
    public ProgramState execute(ProgramState currentState) throws ExpressionException, DictionaryException {
        IValue expressionValue = this.expression.eval(currentState.getSymbolTable());
        currentState.getOutput().add(expressionValue);
        return currentState;
    }

    @Override
    public IStatement deepCopy() {
        return new PrintStatement(this.expression.deepCopy());
    }

    @Override
    public String toString() {
        return "print(" + this.expression.toString() + ")";
    }
}
