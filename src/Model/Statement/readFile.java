package Model.Statement;

import Model.ADT.IDictionary;
import Model.Exceptions.*;
import Model.Expression.IExpression;
import Model.ProgramState.ProgramState;
import Model.Type.IType;
import Model.Type.IntType;
import Model.Type.StringType;
import Model.Value.IValue;
import Model.Value.IntValue;
import Model.Value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class readFile implements IStatement{
    private IExpression expression;
    private String variableName;

    public readFile(IExpression exp, String varName) {
        this.expression = exp;
        this.variableName = varName;
    }

    @Override
    public ProgramState execute(ProgramState currentState) throws StatementException, ExpressionException, DictionaryException, HeapException {
        if (currentState.getSymbolTable().isDefined(this.variableName)) {
            IValue variableValue =  currentState.getSymbolTable().lookUp(this.variableName);
            if (variableValue.getType().equals(new IntType())) {
                IValue fileName = this.expression.eval(currentState.getSymbolTable(), currentState.getHeapTable());
                if (fileName.getType().equals(new StringType())) {
                    StringValue stringFileName = (StringValue) fileName;
                    BufferedReader fileDescriptor = currentState.getFileTable().lookUp(stringFileName);
                    try {
                        String line = fileDescriptor.readLine();
                        if (line != null) {
                            currentState.getSymbolTable().addKeyValuePair(this.variableName, new IntValue(Integer.parseInt(line)));
                        }
                        else {
                            currentState.getSymbolTable().addKeyValuePair(this.variableName, new IntType().getDefaultValue());
                        }
                    } catch (IOException e) {
                        throw new StatementException("Failed to read line from the given file.");
                    }
                }
                else {
                    throw new StatementException("The type of the given value (" + fileName + ") is not a String.");
                }
            }
            else {
                throw new StatementException("The type of the given variable (" + this.variableName + ") is not Int.");
            }
        }
        else {
            throw new DictionaryException("The given variable (" + this.variableName + ") is not defined in the symbol table.");
        }

        return null;
    }

    @Override
    public String toString() {
        return "readFile " + this.expression.toString() + " into variable " + this.variableName;
    }

    @Override
    public IStatement deepCopy() {
        return new readFile(this.expression, this.variableName);
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnv) throws StatementException, ExpressionException, DictionaryException {
        IType typeExp = this.expression.typeCheck(typeEnv);
        if (!typeExp.equals(new StringType())) {
            throw new StatementException("readFile statement: expression is not a string.");
        }
        if (typeEnv.isDefined(this.variableName)) {
            if (!typeEnv.lookUp(this.variableName).equals(new IntType())) {
                throw new StatementException("readFile statement: variable is not an integer.");
            }
        }
        else {
            throw new StatementException("readFile statement: variable is not defined.");
        }
        return typeEnv;
    }
}
