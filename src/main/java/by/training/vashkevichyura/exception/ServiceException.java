package by.training.vashkevichyura.exception;


public class ServiceException extends Throwable {

    private static final long serialVersionUID = -3572846262945218653L;

    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Exception cause) {
        super(cause);
    }

    public ServiceException(String message, Exception cause) {
        super(message, cause);
    }


    public ServiceException(String message, DAOException cause) {
        super(message,cause);
    }

    public ServiceException(String s, String message) {

    }
}

