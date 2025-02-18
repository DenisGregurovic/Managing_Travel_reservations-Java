package production.exceptions.checkedExceptions;

public class DateAfterToday extends Exception{
    public DateAfterToday() {
    }

    public DateAfterToday(String message) {
        super(message);
    }

    public DateAfterToday(String message, Throwable cause) {
        super(message, cause);
    }

    public DateAfterToday(Throwable cause) {
        super(cause);
    }

    public DateAfterToday(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
