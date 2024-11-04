package Model.Expression;

import Model.ADT.IDictionary;
import Model.Exceptions.DictionaryException;
import Model.Exceptions.ExpressionException;
import Model.Value.IValue;

public interface IExpression {
    IValue eval(IDictionary<String, IValue> symbolTable) throws ExpressionException, DictionaryException;
    IExpression deepCopy();
}
