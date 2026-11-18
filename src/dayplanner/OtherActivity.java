package dayplanner;

/**
 * Represents an activity that belongs to the 'other' category.
 * Inherits from the Activity class, but contains an additional parameter
 * 'location.'
 * 
 * @author Manpreet Singh
 */
public class OtherActivity extends Activity {
    private String location;

    /**
     * Creates a new instance of the OtherActivity object
     * 
     * @param title        The title of the activity
     * @param startingTime The starting time of the activity
     * @param endingTime   The ending time of the activity
     * @param comment      A short description of the activity
     * @param location     The location of the activity
     */
    public OtherActivity(String title, Time startingTime, Time endingTime, String comment,
            String location) {
        super(title, startingTime, endingTime, comment);
        this.location = location;
    }

    public OtherActivity() {
        super();
        this.location = null;
    }

    public String getLocation() {
        return new String(this.location);
    }

    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * (non-Javadoc)
     * 
     * @see src.dayplanner.Activity#toString()
     */
    public String toString() {
        return super.toString() + ",%s".formatted(this.location);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof OtherActivity)) {
            return false;
        }

        OtherActivity otherActivity = (OtherActivity) other;
        return getTitle().equals(otherActivity.getTitle())
                && getStartingTime().equals(otherActivity.getStartingTime())
                && getEndingTime().equals(otherActivity.getEndingTime())
                && getComment().equals(otherActivity.getComment())
                && location.equals(otherActivity.location);
    }
}