package by.training.vashkevichyura.exception;

public class DAOException extends Throwable {
    private static final long serialVersionUID = 6622597961241028408L;

    public DAOException(String message) {
        super(message);
    }

    public DAOException(Exception cause) {
        super(cause);
    }

    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }


}
