package Model.Expression;

import Model.ADT.IDictionary;
import Model.ADT.IHeapTable;
import Model.Exceptions.DictionaryException;
import Model.Exceptions.ExpressionException;
import Model.Exceptions.HeapException;
import Model.Type.IType;
import Model.Value.IValue;

public interface IExpression {
    IValue eval(IDictionary<String, IValue> symbolTable, IHeapTable<IValue> heapTable) throws ExpressionException, DictionaryException, HeapException;
    IExpression deepCopy();
    IType typeCheck(IDictionary<String, IType> typeEnv) throws ExpressionException, DictionaryException;
}
