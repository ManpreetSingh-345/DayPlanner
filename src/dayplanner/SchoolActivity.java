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
     * Creates a school activity with the supplied field values.
     *
     * @param title        The title of the activity
     * @param startingTime The starting time of the activity
     * @param endingTime   The ending time of the activity
     * @param comment      A short description of the activity
     * 
     * @throws InvalidActivityException if an activity field is invalid
     */
    public SchoolActivity(String title, Time startingTime, Time endingTime, String comment)
            throws InvalidActivityException {
        super(title, startingTime, endingTime, comment);
    }

    /**
     * Creates a copy of a school activity.
     *
     * @param otherSchoolActivity the school activity to copy
     * 
     * @throws InvalidActivityException if copied fields are invalid
     */
    public SchoolActivity(SchoolActivity otherSchoolActivity) throws InvalidActivityException {
        super(otherSchoolActivity);
    }

    /**
     * Creates a school activity with default field values.
     */
    public SchoolActivity() {
        super();
    }

    /**
     * @return a string representing this school activity
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        if (this.getComment().isBlank()) {
            return "school: %s, %s, %s".formatted(this.getTitle(), this.getStartingTime().toString(),
                    this.getEndingTime().toString());
        }
        return "school: %s, %s, %s, %s".formatted(this.getTitle(), this.getStartingTime().toString(),
                this.getEndingTime().toString(), this.getComment());
    }

    /**
     * @param other the object to compare
     * 
     * @return true if the objects represent the same school activity
     */
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