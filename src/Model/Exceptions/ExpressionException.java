package Model.Exceptions;

public class ExpressionException extends Exception{
    private String msg;

    public ExpressionException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public ExpressionException() {
        super("Expression operation failed.");
    }

    @Override
    public String getMessage() {
        return this.msg;
    }
}
