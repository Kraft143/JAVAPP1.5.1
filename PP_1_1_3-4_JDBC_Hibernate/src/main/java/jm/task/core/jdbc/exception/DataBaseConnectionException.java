package jm.task.core.jdbc.exception;

public class DataBaseConnectionException extends RuntimeException {
    public DataBaseConnectionException() {
    }

    public DataBaseConnectionException(String message) {
        super(message);
    }

    public DataBaseConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataBaseConnectionException(Throwable cause) {
        super(cause);
    }

    public DataBaseConnectionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
