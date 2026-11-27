package dayplanner;

/**
 * Represents a specific time including year, month, day, hour, and minute
 * Used to define starting and ending times for activities.
 */
public class Time implements Comparable<Time> {
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
     * @throws InvalidTimeException if any time field is invalid
     */
    public Time(int year, int month, int day, int hour, int minute) throws InvalidTimeException {
        setYear(year);
        setMonth(month);
        setDay(day);
        setHour(hour);
        setMinute(minute);
    }

    /**
     * Creates a new instance of the Time object based on another initialization of
     * the same type
     * 
     * @param otherTime The Time object to 'inherit' from
     * @throws InvalidTimeException if otherTime is null or contains an invalid
     *                              field
     */
    public Time(Time otherTime) throws InvalidTimeException {
        if (otherTime == null) {
            throw new InvalidTimeException("The caller must pass in a non-null Time object.");
        }
        setYear(otherTime.year);
        setMonth(otherTime.month);
        setDay(otherTime.day);
        setHour(otherTime.hour);
        setMinute(otherTime.minute);
    }

    /**
     * Creates a Time with sentinel values that represent an unset time.
     */
    public Time() {
        this.year = -1;
        this.month = -1;
        this.day = -1;
        this.hour = -1;
        this.minute = -1;
    }

    /**
     * Returns the stored year.
     *
     * @return the stored year
     */
    public int getYear() {
        return this.year;
    }

    /**
     * Returns the stored month.
     *
     * @return the stored month
     */
    public int getMonth() {
        return this.month;
    }

    /**
     * Returns the stored day.
     *
     * @return the stored day
     */
    public int getDay() {
        return this.day;
    }

    /**
     * Returns the stored hour.
     *
     * @return the stored hour
     */
    public int getHour() {
        return this.hour;
    }

    /**
     * Returns the stored minute.
     *
     * @return the stored minute
     */
    public int getMinute() {
        return this.minute;
    }

    /**
     * Stores the year.
     *
     * @param year the year to store
     * @throws InvalidTimeException if the year is not positive
     */
    public void setYear(int year) throws InvalidTimeException {
        if (year <= 0) {
            throw new InvalidTimeException("Invalid year input.");
        }
        this.year = year;
    }

    /**
     * Stores the month.
     *
     * @param month the month to store
     * @throws InvalidTimeException if the month is outside the range 1 through 12
     */
    public void setMonth(int month) throws InvalidTimeException {
        if (month <= 0 || month > 12) {
            throw new InvalidTimeException("Invalid month input. Month must be between 1 and 12");
        }
        this.month = month;
    }

    /**
     * Stores the day.
     *
     * @param day the day to store
     * @throws InvalidTimeException if the day is outside the range 1 through 31
     */
    public void setDay(int day) throws InvalidTimeException {
        if (day <= 0 || day > 31) {
            throw new InvalidTimeException("Invalid day input. Day must be between 1 and 31");
        }
        this.day = day;
    }

    /**
     * Stores the hour.
     *
     * @param hour the hour to store
     * @throws InvalidTimeException if the hour is outside the range 0 through 23
     */
    public void setHour(int hour) throws InvalidTimeException {
        if (hour < 0 || hour > 23) {
            throw new InvalidTimeException("Invalid hour input. Hour must be between 0 and 23");
        }
        this.hour = hour;
    }

    /**
     * Stores the minute.
     *
     * @param minute the minute to store
     * @throws InvalidTimeException if the minute is outside the range 0 through 59
     */
    public void setMinute(int minute) throws InvalidTimeException {
        if (minute < 0 || minute > 59) {
            throw new InvalidTimeException("Invalid minute input. Minute must be between 1 and 59");
        }
        this.minute = minute;
    }

    /**
     * @return a string representing the time in the format "YYYY/MM/DD,HH:MM"
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return String.format("%04d/%02d/%02d,%d:%02d", this.year, this.month, this.day, this.hour, this.minute);
    }

    /**
     * 
     * Tests whether two Time objects contain the same values.
     *
     * @param other The object to compare against
     * @return true if the calling object is equivalent to the specified object,
     *         false otherwise
     */
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        else if (other == null || getClass() != other.getClass()) {
            return false;
        }

        Time otherTime = (Time) other;
        return this.year == otherTime.year && this.month == otherTime.month && this.day == otherTime.day
                && this.hour == otherTime.hour && this.minute == otherTime.minute;
    }

    /**
     * 
     * @param otherTime The Time object to compare against
     * 
     * @return A negative integer if this time is earlier than the specified time,
     *         zero if they're equal, and postiive if this time is later than the
     *         specified time
     * @throws NullPointerException if otherTime is null
     * @throws ClassCastException   if otherTime is not a Time object
     */
    @Override
    public int compareTo(Time otherTime) throws NullPointerException, ClassCastException {
        if (otherTime == null) {
            throw new NullPointerException("Other time cannot be null.");
        }
        if (this.getClass() != otherTime.getClass()) {
            throw new ClassCastException("Time objects must be the same class");
        }

        if (this.year != otherTime.year) {
            return this.year - otherTime.year;
        }
        if (this.month != otherTime.month) {
            return this.month - otherTime.month;
        }
        if (this.day != otherTime.day) {
            return this.day - otherTime.day;
        }
        if (this.hour != otherTime.hour) {
            return this.hour - otherTime.hour;
        }
        return this.minute - otherTime.minute;
    }

    /**
     * 
     * Parses a comma-separated five-part time string.
     *
     * @param timeString A string to convert to the Time object
     * 
     * @return A Time object containing the attributes specified within the Time
     *         string. If the inputs aren't valid, the method returns null.
     * @throws InvalidTimeException if a parsed time field is invalid
     */
    public static Time getTimeFromString(String timeString) throws InvalidTimeException {
        if (timeString == null)
            return null;
        String[] timeArray = timeString.split(", ");
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
     * Determines whether this time follows the Gregorian calendar rules.
     *
     * @return true if this Time object is valid, which means it falls under the
     *         Gregorian Calendar, false otherwise
     * @throws InvalidTimeException if a given parameter violates the Gregorian
     *                              Calendar standard
     */
    public boolean isValid() throws InvalidTimeException {
        // The months April, June, September, and November have 30 days in them
        if ((this.month == 4 || this.month == 6 || this.month == 9 || this.month == 11)
                && this.day > 30)
            throw new InvalidTimeException(
                    "Invalid month + day input. The months April, June, September, and November have 30 days in them");
        // February has 29 days on leap years, and 28 days otherwise
        else if (this.month == 2 && ((this.day == 29 && !validateLeapYear()) || this.day >= 30))
            throw new InvalidTimeException(
                    "Invalid input for February. Make sure the date is valid under the Gregorian Calendar.");
        // General case
        else if (!(this.year > 0 && this.month < 13 && this.month > 0 && this.day < 32 && this.day > 0 && this.hour < 24
                && this.hour > -1 && this.minute < 60 && this.minute > -1)) {
            throw new InvalidTimeException(
                    "Invalid input for times. Make sure the date is valid under the Gregorian Calendar.");
        }
        return true;
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