package dayplanner;

/**
 * Represents an activity with a title, startingTime, endingTime, and a comment
 * 
 * @author Manpreet Singh
 */
public class Activity {
    private String title;
    private Time startingTime;
    private Time endingTime;
    private String comment;

    /**
     * 
     * @param title        The title of the activity
     * @param startingTime The starting time of the activity
     * @param endingTime   The ending time of the activity
     * @param comment      A short description of the activity
     */
    public Activity(String title, Time startingTime, Time endingTime, String comment) {
        this.title = title;
        this.startingTime = startingTime;
        this.endingTime = endingTime;
        this.comment = comment;
    }

    public Activity() {
        this.title = null;
        this.startingTime = null;
        this.endingTime = null;
        this.comment = null;
    }

    public String getTitle() {
        return new String(this.title);
    }

    public Time getStartingTime() {
        return new Time(this.startingTime);
    }

    public Time getEndingTime() {
        return new Time(this.endingTime);
    }

    public String getComment() {
        return new String(this.comment);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStartingTime(Time time) {
        this.startingTime = time;
    }

    public void setEndingTime(Time time) {
        this.endingTime = time;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    /**
     * 
     * @param keywords keywords to search for in the activity title
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
     * @param otherStartingTime The other starting time to compare 'this' starting
     *                          time with
     * @param otherEndingTime   The other ending time to compare 'this' ending time
     *                          with
     * @return
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