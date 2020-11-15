package hr.services;

public class HRException extends Exception {
    public HRException() {
    }

    public HRException(String message) {
        super(message);
    }

    public HRException(String message, Throwable cause) {
        super(message, cause);
    }
}
