package dayplanner;

/**
 * Represents an activity with a title, startingTime, endingTime, and a comment
 * 
 * @author Manpreet Singh
 */
public abstract class Activity {
    private String title;
    private Time startingTime;
    private Time endingTime;
    private String comment;

    /**
     * 
     * Creates an activity with the supplied field values.
     *
     * @param title        The title of the activity
     * @param startingTime The starting time of the activity
     * @param endingTime   The ending time of the activity
     * @param comment      A short description of the activity
     * 
     * @throws InvalidActivityException if a required field is invalid
     */
    public Activity(String title, Time startingTime, Time endingTime, String comment) throws InvalidActivityException {
        setTitle(title);
        setStartingTime(startingTime);
        setEndingTime(endingTime);
        setComment(comment);
    }

    /**
     * Creates a copy of an activity.
     *
     * @param otherActivity the activity to copy
     * 
     * @throws InvalidActivityException if the source has invalid fields
     */
    public Activity(Activity otherActivity) throws InvalidActivityException {
        if (otherActivity == null) {
            throw new InvalidActivityException("Activity object cannot be null");
        }
        setTitle(otherActivity.title);
        setStartingTime(new Time(otherActivity.startingTime));
        setEndingTime(new Time(otherActivity.endingTime));
        setComment(otherActivity.comment);
    }

    /**
     * Creates an activity with default, empty field values.
     */
    public Activity() {
        this.title = "";
        this.startingTime = null;
        this.endingTime = null;
        this.comment = "";
    }

    /**
     * Returns a copy of the activity title.
     *
     * @return a copy of the activity title
     */
    public String getTitle() {
        return this.title == null ? "" : new String(this.title);
    }

    /**
     * Returns a copy of the starting time.
     *
     * @return a copy of the starting time
     */
    public Time getStartingTime() {
        return new Time(this.startingTime);
    }

    /**
     * Returns a copy of the ending time.
     *
     * @return a copy of the ending time
     */
    public Time getEndingTime() {
        return new Time(this.endingTime);
    }

    /**
     * Returns a copy of the activity comment.
     *
     * @return a copy of the activity comment
     */
    public String getComment() {
        return this.comment == null ? "" : new String(this.comment);
    }

    /**
     * Stores a validated activity title.
     *
     * @param title the title to store
     * 
     * @throws InvalidActivityException if the title is blank or null
     */
    public void setTitle(String title) throws InvalidActivityException {
        if (title == null || title.isBlank()) {
            throw new InvalidActivityException("Title cannot be blank.");
        }
        this.title = title;
    }

    /**
     * Stores a validated starting time.
     *
     * @param time the starting time to store
     * 
     * @throws InvalidActivityException if the starting time is null
     */
    public void setStartingTime(Time time) throws InvalidActivityException {
        if (time == null) {
            throw new InvalidActivityException("Starting time cannot be empty.");
        }
        this.startingTime = time;
    }

    /**
     * Stores a validated ending time.
     *
     * @param time the ending time to store
     * 
     * @throws InvalidActivityException if the ending time is null
     */
    public void setEndingTime(Time time) throws InvalidActivityException {
        if (time == null) {
            throw new InvalidActivityException("Ending time cannot be empty.");
        }
        this.endingTime = time;
    }

    /**
     * Stores an activity comment.
     *
     * @param comment the comment to store; null is stored as an empty string
     */
    public void setComment(String comment) {
        this.comment = comment == null ? "" : comment;
    }

    /**
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        if (this.comment.isBlank()) {
            return "%s, %s, %s".formatted(this.title, this.startingTime, this.endingTime);
        }
        return "%s, %s, %s, %s".formatted(this.title, this.startingTime, this.endingTime, this.comment);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Activity)) {
            return false;
        }

        Activity otherActivity = (Activity) other;

        return getTitle().equals(otherActivity.getTitle())
                && getStartingTime().equals(otherActivity.getStartingTime())
                && getEndingTime().equals(otherActivity.getEndingTime())
                && getComment().equals(otherActivity.getComment());
    }

    /**
     * 
     * Searches the activity title for every supplied keyword.
     *
     * @param keywords keywords to search for in the activity title
     * 
     * @return true if the title contains all the keywords, false otherwise
     */
    public boolean titleContainsKeywords(String keywords) {
        // Normalize both the activity title words and the search title words by lower
        // casing for case insentivie comparison
        String[] activityTitleWords = this.title.toLowerCase().split(" ");
        String[] searchTitleWords = keywords.toLowerCase().split(" ");
        boolean titleFlag = true;

        // The title flag is set to false if at least one of the keywords to search for
        // is not present within the title
        for (int j = 0; j < searchTitleWords.length; j++) {
            boolean flag = false;
            for (int k = 0; k < activityTitleWords.length; k++) {
                if (searchTitleWords[j].equals(activityTitleWords[k])) {
                    flag = true;
                    break;
                }
            }

            if (!flag) {
                titleFlag = false;
            }
        }
        return titleFlag;
    }

    /**
     * 
     * Determines whether the activity falls within the supplied time range.
     *
     * @param otherStartingTime The other starting time to compare 'this' starting
     *                          time with
     * @param otherEndingTime   The other ending time to compare 'this' ending time
     *                          with
     * @return true if the activity falls within both supplied time bounds
     */
    public boolean fitsTimeCondition(Time otherStartingTime, Time otherEndingTime) {
        // If the other starting time is null (i.e. the user did not input anything for
        // the starting time), or the input is not null and the starting time in this
        // object comes after the other starting time, set to true, otherwise false
        boolean includeStartingTime = (otherStartingTime == null)
                || (otherStartingTime != null && this.startingTime.compareTo(otherStartingTime) >= 0);
        // If the other ending time is null (i.e. the user did not input anything for
        // the ending time), or the input is not null and the ending time in this
        // object comes after the other ending time, set to true, otherwise false
        boolean includeEndingTime = (otherEndingTime == null)
                || (otherEndingTime != null && this.endingTime.compareTo(otherEndingTime) <= 0);

        return includeStartingTime && includeEndingTime;
    }
}