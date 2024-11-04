package Model.Exceptions;

public class ListException extends Exception{
    private String msg;

    public ListException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public ListException() {
        super("List operation failed");
    }

    @Override
    public String getMessage() {
        return this.msg;
    }
}
