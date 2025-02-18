package production.exceptions.uncheckedExceptions;

public class ValidNumberException extends RuntimeException{
    public ValidNumberException() {
    }

    public ValidNumberException(String message) {
        super(message);
    }

    public ValidNumberException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidNumberException(Throwable cause) {
        super(cause);
    }

    public ValidNumberException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
