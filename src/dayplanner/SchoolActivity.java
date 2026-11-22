package dayplanner;

/**
 * Represents an activity that belongs to the 'school' category.
 * Inherits from the Activity class with shared properties
 * 
 * @author Manpreet Singh
 */
public class SchoolActivity extends Activity {
    /**
     * 
     * @param title        The title of the activity
     * @param startingTime The starting time of the activity
     * @param endingTime   The ending time of the activity
     * @param comment      A short description of the activity
     */
    public SchoolActivity(String title, Time startingTime, Time endingTime, String comment)
            throws InvalidActivityException {
        super(title, startingTime, endingTime, comment);
    }

    public SchoolActivity() {
        super();
    }

    public String toString() {
        if (this.getComment().isBlank()) {
            return "school: %s, %s, %s".formatted(this.getTitle(), this.getStartingTime().toString(),
                    this.getEndingTime().toString());
        }
        return "school: %s, %s, %s, %s".formatted(this.getTitle(), this.getStartingTime().toString(),
                this.getEndingTime().toString(), this.getComment());
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SchoolActivity)) {
            return false;
        }

        SchoolActivity otherActivity = (SchoolActivity) other;
        return getTitle().equals(otherActivity.getTitle())
                && getStartingTime().equals(otherActivity.getStartingTime())
                && getEndingTime().equals(otherActivity.getEndingTime())
                && getComment().equals(otherActivity.getComment());
    }
}