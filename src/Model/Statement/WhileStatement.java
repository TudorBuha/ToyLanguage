package Model.Statement;

import Model.ADT.IDictionary;
import Model.Exceptions.*;
import Model.Expression.IExpression;
import Model.ProgramState.ProgramState;
import Model.Type.BoolType;
import Model.Type.IType;
import Model.Type.IntType;
import Model.Value.BoolValue;
import Model.Value.IValue;

public class WhileStatement implements IStatement{
    private IExpression expression;
    private IStatement statement;

    public WhileStatement(IExpression expression, IStatement statement) {
        this.expression = expression;
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState currentState) throws StatementException, ExpressionException, DictionaryException, HeapException {
        IValue conditional = this.expression.eval(currentState.getSymbolTable(), currentState.getHeapTable());
        if (!conditional.getType().equals(new BoolType())) {
            throw new StatementException("ERROR: The given while conditional expression(" + this.expression.toString() + ") is not a boolean.");
        }
        BoolValue boolConditional = (BoolValue) conditional;
        if (boolConditional.getValue()) {
            currentState.getExecutionStack().push(this);
            currentState.getExecutionStack().push(this.statement);
        }
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new WhileStatement(this.expression.deepCopy(), this.statement.deepCopy());
    }

    @Override
    public String toString() {
        return "While (" + this.expression.toString() + ") { " + this.statement.toString() + " }";
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnv) throws StatementException, ExpressionException, DictionaryException {
        IType typeExpression = this.expression.typeCheck(typeEnv);
        if (!typeExpression.equals(new BoolType())) {
            throw new StatementException("While statement: Conditional expression is not a boolean.");
        }
        this.statement.typeCheck(typeEnv.shallowCopy());
        return typeEnv;
    }
}
