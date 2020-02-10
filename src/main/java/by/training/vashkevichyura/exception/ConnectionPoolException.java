package by.training.vashkevichyura.exception;

public class ConnectionPoolException extends Throwable {

    private static final long serialVersionUID = -8515958467767457163L;

    public ConnectionPoolException(String message) {
        super(message);
    }

    public ConnectionPoolException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectionPoolException(Throwable cause) {
        super(cause);
    }
}
