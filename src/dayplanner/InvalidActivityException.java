package dayplanner;

public class InvalidActivityException extends Exception {
    public InvalidActivityException(String message) {
        super(message);
    }

    public InvalidActivityException() {
        super("Invalid activity inputs.");
    }
}
