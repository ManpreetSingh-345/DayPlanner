package dayplanner;

/**
 * Represents a specific time including year, month, day, hour, and minute
 * Used to define starting and ending times for activities.
 */
public class Time {
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;

    /**
     * Creates a new instance of the Time object
     * 
     * @param year   The year of the time to store
     * @param month  The month of the time to store
     * @param day    The day of the time to store
     * @param hour   The hour of the time to store
     * @param minute The minute of the time to store
     */
    public Time(int year, int month, int day, int hour, int minute) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
    }

    /**
     * Creates a new instance of the Time object based on another initialization of
     * the same type
     * 
     * @param otherTime The Time object to 'inherit' from
     */
    public Time(Time otherTime) {
        this.year = otherTime.year;
        this.month = otherTime.month;
        this.day = otherTime.day;
        this.hour = otherTime.hour;
        this.minute = otherTime.minute;
    }

    public Time() {
        this.year = -1;
        this.month = -1;
        this.day = -1;
        this.hour = -1;
        this.minute = -1;
    }

    public int getYear() {
        return this.year;
    }

    public int getMonth() {
        return this.month;
    }

    public int getDay() {
        return this.day;
    }

    public int getHour() {
        return this.hour;
    }

    public int getMinute() {
        return this.minute;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    /**
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return String.format("%04d/%02d/%02d,%d:%02d", this.year, this.month, this.day, this.hour, this.minute);
    }

    /**
     * 
     * @param otherTime The Time object to compare against
     * @return true if the calling object is equivalent to the specified object,
     *         false otherwise
     */
    public boolean equals(Time otherTime) {
        return this.year == otherTime.year && this.month == otherTime.month && this.day == otherTime.day
                && this.hour == otherTime.hour && this.minute == otherTime.minute;
    }

    /**
     * 
     * @param otherTime The Time object to compare against
     * @return A negative integer if this time is earlier than the specified time,
     *         zero if they're equal, and postiive if this time is later than the
     *         specified time
     */
    public int compareTo(Time otherTime) {
        if (this.year != otherTime.year)
            return this.year - otherTime.year;
        if (this.month != otherTime.month)
            return this.month - otherTime.month;
        if (this.day != otherTime.day)
            return this.day - otherTime.day;
        if (this.hour != otherTime.hour)
            return this.hour - otherTime.hour;
        return this.minute - otherTime.minute;
    }

    /**
     * 
     * @param timeString A string to convert to the Time object
     * @return A Time object containing the attributes specified within the Time
     *         string. If the inputs aren't valid however, the method returns null.
     */
    public static Time getTimeFromString(String timeString) {
        String[] timeArray = timeString.split(" ");
        if (timeArray.length != 5)
            return null;
        try {
            int year = Integer.parseInt(timeArray[0]);
            int month = Integer.parseInt(timeArray[1]);
            int day = Integer.parseInt(timeArray[2]);
            int hour = Integer.parseInt(timeArray[3]);
            int minute = Integer.parseInt(timeArray[4]);
            return new Time(year, month, day, hour, minute);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * 
     * @return true if this Time object is valid, which means it falls under the
     *         Gregorian Calendar, false otherwise
     */
    public boolean isValid() {
        // The months April, June, September, and November have 30 days in them
        if ((this.month == 4 || this.month == 6 || this.month == 9 || this.month == 11)
                && this.day > 30)
            return false;
        // February has 29 days on leap years, and 28 days otherwise
        else if (this.month == 2 && ((this.day == 29 && !validateLeapYear()) || this.day >= 30))
            return false;
        // General case
        return this.year > 0 && this.month < 13 && this.month > 0 && this.day < 32 && this.day > 0 && this.hour < 24
                && this.hour > -1 && this.minute < 60 && this.minute > -1;
    }

    /**
     * 
     * @return true if this Time object is a valid leap year (if this object has
     *         month = 2 and day = 29), false otherwise
     */
    private boolean validateLeapYear() {
        if (this.month != 2 || (this.month == 2 && this.day != 29))
            return true;
        return (this.year % 4 == 0 && (this.year % 100 != 0 || this.year % 400 == 0));
    }
}