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
     * @param title        The title of the activity
     * @param startingTime The starting time of the activity
     * @param endingTime   The ending time of the activity
     * @param comment      A short description of the activity
     */
    public HomeActivity(String title, Time startingTime, Time endingTime, String comment) {
        super(title, startingTime, endingTime, comment);
    }

    public HomeActivity() {
        super();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof HomeActivity)) {
            return false;
        }

        HomeActivity otherActivity = (HomeActivity) other;
        return getTitle().equals(otherActivity.getTitle())
                && getStartingTime().equals(otherActivity.getStartingTime())
                && getEndingTime().equals(otherActivity.getEndingTime())
                && getComment().equals(otherActivity.getComment());
    }
}
