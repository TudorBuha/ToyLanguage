package Model.Statement;

import Model.ADT.IDictionary;
import Model.ADT.MyDictionary;
import Model.Exceptions.*;
import Model.ProgramState.ProgramState;
import Model.Type.IType;

import java.lang.reflect.Type;

public interface IStatement {
    ProgramState execute(ProgramState currentState) throws StatementException, HeapException, ExpressionException, DictionaryException;
    IStatement deepCopy();
    IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnv) throws StatementException, ExpressionException, DictionaryException;
}
