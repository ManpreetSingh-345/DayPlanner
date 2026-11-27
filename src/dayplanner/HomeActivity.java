package dayplanner;

/**
 * Represents an activity that belongs to the 'home' category.
 * Inherits from the Activity class with shared properties
 * 
 * @author Manpreet Singh
 */
public class HomeActivity extends Activity {
    /**
     * 
     * Creates a home activity with the supplied field values.
     *
     * @param title        The title of the activity
     * @param startingTime The starting time of the activity
     * @param endingTime   The ending time of the activity
     * @param comment      A short description of the activity
     * 
     * @throws InvalidActivityException if an activity field is invalid
     * @throws InvalidTimeException if an activity time is invalid
     */
    public HomeActivity(String title, Time startingTime, Time endingTime, String comment)
            throws InvalidActivityException, InvalidTimeException {
        super(title, startingTime, endingTime, comment);
    }

    /**
     * Creates a copy of a home activity.
     *
     * @param otherHomeActivity the home activity to copy
     * 
     * @throws InvalidActivityException if copied fields are invalid
     * @throws InvalidTimeException if a copied time is invalid
     */
    public HomeActivity(HomeActivity otherHomeActivity) throws InvalidActivityException, InvalidTimeException {
        super(otherHomeActivity);
    }

    /**
     * Creates a home activity with default field values.
     */
    public HomeActivity() {
        super();
    }

    /**
     * @return a string representing this home activity
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        try {
            if (this.getComment().isBlank()) {
                return "home: %s, %s, %s".formatted(this.getTitle(), this.getStartingTime().toString(),
                        this.getEndingTime().toString());
            }
            return "home: %s, %s, %s, %s".formatted(this.getTitle(), this.getStartingTime().toString(),
                    this.getEndingTime().toString(), this.getComment());
        } catch (InvalidTimeException e) {
            return null;
        }
    }
}
