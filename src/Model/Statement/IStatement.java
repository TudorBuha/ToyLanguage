package Model.Statement;

import Model.Exceptions.DictionaryException;
import Model.Exceptions.ExpressionException;
import Model.Exceptions.StatementException;
import Model.ProgramState.ProgramState;

public interface IStatement {
    ProgramState execute(ProgramState currentState) throws StatementException, ExpressionException, DictionaryException;
    IStatement deepCopy();
}
