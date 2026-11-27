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
     * Creates an other-type activity with the supplied field values.
     *
     * @param title        The title of the activity
     * @param startingTime The starting time of the activity
     * @param endingTime   The ending time of the activity
     * @param comment      A short description of the activity
     * @param location     The location of the activity
     * 
     * @throws InvalidActivityException if an activity field is invalid
     * @throws InvalidTimeException if an activity time is invalid
     */
    public OtherActivity(String title, Time startingTime, Time endingTime, String comment,
            String location) throws InvalidActivityException, InvalidTimeException {
        super(title, startingTime, endingTime, comment);
        setLocation(location);
    }

    /**
     * Creates a copy of an other-type activity.
     *
     * @param otherOtherActivity the activity to copy
     * 
     * @throws InvalidActivityException if copied fields are invalid
     * @throws InvalidTimeException if a copied time is invalid
     */
    public OtherActivity(OtherActivity otherOtherActivity) throws InvalidActivityException, InvalidTimeException {
        super(otherOtherActivity);
        setLocation(otherOtherActivity.location);
    }

    /**
     * Creates an other-type activity with default field values.
     */
    public OtherActivity() {
        super();
        this.location = null;
    }

    /**
     * Returns a copy of the activity location.
     *
     * @return a copy of the activity location
     */
    public String getLocation() {
        return this.location == null ? "" : new String(this.location);
    }

    /**
     * Stores a validated activity location.
     *
     * @param location the location to store
     * 
     * @throws InvalidActivityException if the location is blank or null
     */
    public void setLocation(String location) throws InvalidActivityException {
        if (location == null || location.isBlank()) {
            throw new InvalidActivityException("Location cannot be empty.");
        }
        this.location = location;
    }

    /**
     * @return a string representing this other-type activity
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        try {
            if (this.getComment().isBlank()) {
                return "other: %s, %s, %s, %s".formatted(this.getTitle(), this.getStartingTime().toString(),
                        this.getEndingTime().toString(), this.getLocation());
            }
            return "other: %s, %s, %s, %s, %s".formatted(this.getTitle(), this.getStartingTime().toString(),
                    this.getEndingTime().toString(), this.getComment(), this.getLocation());
        } catch (InvalidTimeException e) {
            return null;
        }
    }

    /**
     * @param other the object to compare
     * 
     * @return true if the objects represent the same other-type activity
     */
    @Override
    public boolean equals(Object other) {
        return super.equals(other)
                && getLocation().equals(((OtherActivity) other).getLocation());
    }
}