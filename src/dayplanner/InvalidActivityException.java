package dayplanner;

/**
 * Indicates that an activity contains invalid field values.
 *
 * @author Manpreet Singh
 */
public class InvalidActivityException extends Exception {
    /**
     * Creates an InvalidActivityException with a given message.
     *
     * @param message the exception message
     */
    public InvalidActivityException(String message) {
        super(message);
    }

    /**
     * Creates an InvalidActivityException with a default message.
     */
    public InvalidActivityException() {
        super("Invalid activity inputs.");
    }
}
