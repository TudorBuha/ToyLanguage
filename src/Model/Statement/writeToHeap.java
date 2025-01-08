package Model.Statement;
import Model.ADT.IDictionary;
import Model.Exceptions.*;
import Model.Expression.IExpression;
import Model.ProgramState.ProgramState;
import Model.Type.IType;
import Model.Type.RefType;
import Model.Value.IValue;
import Model.Value.RefValue;


public class writeToHeap implements IStatement{
    private String variableName;
    private IExpression expression;

    public writeToHeap(String varName, IExpression expr) {
        this.variableName = varName;
        this.expression = expr;
    }

    @Override
    public ProgramState execute(ProgramState currentState) throws StatementException, ExpressionException, DictionaryException, HeapException {
        if (!currentState.getSymbolTable().isDefined(this.variableName)) {
            throw new StatementException("ERROR: The given variable(" + this.variableName + ") is not defined in the symbol table.");
        }
        IValue variableValue = currentState.getSymbolTable().lookUp(this.variableName);
        if (!(variableValue.getType().equals(new RefType(null)))) {
            throw new StatementException("ERROR: The given variable(" + this.variableName + ") is not of type RefType.");
        }
        RefValue refValue = (RefValue) variableValue;
        if (!currentState.getHeapTable().isDefined(refValue.getAddress())) {
            throw new StatementException("ERROR: The address associated with the given variable(" + this.variableName + ") is no longer available in the heap.");
        }
        IValue expressionValue = this.expression.eval(currentState.getSymbolTable(), currentState.getHeapTable());
        if (!expressionValue.getType().equals(refValue.getLocationType())) {
            throw new StatementException("ERROR: The type of the given expression(" + this.expression.toString() + ") does not match with the location type.");
        }
        currentState.getHeapTable().updateHeapEntry(refValue.getAddress(), expressionValue);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new writeToHeap(this.variableName, this.expression.deepCopy());
    }

    @Override
    public String toString() {
        return "WriteToHeap(" + this.variableName + ", " + this.expression.toString() + ")";
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnv) throws StatementException, ExpressionException, DictionaryException {
        IType typeVar = typeEnv.lookUp(this.variableName);
        IType typeExp = this.expression.typeCheck(typeEnv);
        if (!typeVar.equals(new RefType(typeExp))) {
            throw new StatementException("WriteToHeap statement: right hand side and left hand side have different types. Declared type of variable " + this.variableName + " and type of the assigned expression do not match.");
        }
        return typeEnv;
    }
}
