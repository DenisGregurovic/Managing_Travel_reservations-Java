package production.exceptions.checkedExceptions;

public class DateBeforeToday extends Exception{
    public DateBeforeToday() {
    }

    public DateBeforeToday(String message) {
        super(message);
    }

    public DateBeforeToday(String message, Throwable cause) {
        super(message, cause);
    }

    public DateBeforeToday(Throwable cause) {
        super(cause);
    }

    public DateBeforeToday(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
