package by.epam.carrentalapp.ioc;

public class InversionOfControlException extends RuntimeException {
    public InversionOfControlException() {
    }

    public InversionOfControlException(String message) {
        super(message);
    }

    public InversionOfControlException(String message, Throwable cause) {
        super(message, cause);
    }

    public InversionOfControlException(Throwable cause) {
        super(cause);
    }
}
