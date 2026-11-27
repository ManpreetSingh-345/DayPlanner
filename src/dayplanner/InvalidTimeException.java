package dayplanner;

/**
 * Indicates that a Time object contains invalid field values
 * 
 * @author Manpreet Singh
 */
public class InvalidTimeException extends Exception {
    /**
     * Creates an InvalidTimeException with a given message.
     *
     * @param message the exception message
     */
    public InvalidTimeException(String message) {
        super(message);
    }

    /**
     * Creates an InvalidTimeException with a default message.
     */
    public InvalidTimeException() {
        super("Invalid time inputs.");
    }
}
