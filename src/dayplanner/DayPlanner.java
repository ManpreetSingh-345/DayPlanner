package dayplanner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Orchestrates the functionality of the DayPlanner project.
 * Lets users add and search through activities that are split into three
 * categories: home, school, and other.
 * 
 * @author Manpreet Singh
 */
public class DayPlanner {
	// ArrayList holding all activities
	private ArrayList<Activity> activities;

	// HashMap containing tokenized title keywords for fast searching
	private HashMap<String, ArrayList<Integer>> titleHashMap;

	/**
	 * Creates an empty day planner.
	 */
	public DayPlanner() {
		this.activities = new ArrayList<>();
		this.titleHashMap = new HashMap<>();
	}

	/**
	 * Returns a copy of all currently stored activities.
	 *
	 * @return a copy of the stored activity list
	 */
	public ArrayList<Activity> getActivities() {
		return new ArrayList<Activity>(activities);
	}

	/**
	 * Adds an activity of the requested type.
	 *
	 * Adds a new activity to the appropriate array
	 * 
	 * @param type         The type of the acitivity
	 * @param title        The title of the activity
	 * @param startingTime The starting time of the activity
	 * @param endingTime   The ending time of the activity
	 * @param comment      A short description of the activity
	 * @param location     The location of the activity (if the activity is 'other')
	 * @throws InvalidActivityException if the activity fields are invalid
	 * @throws InvalidTimeException     if an activity time is invalid
	 */
	public void addNewActivity(String type, String title, Time startingTime, Time endingTime, String comment,
			String location) throws InvalidActivityException, InvalidTimeException {
		switch (type) {
			case "home":
				activities.add(new HomeActivity(title, startingTime,
						endingTime, comment));
				break;
			case "school":
				activities.add(new SchoolActivity(title, startingTime,
						endingTime, comment));
				break;
			case "other":
				activities.add(new OtherActivity(title, startingTime,
						endingTime, comment, location));
				break;
			default:
				System.out.println(
						"Type of activity does not fall in one of the three given categories (home, school, or other)");
		}

		for (String keyword : title.split("\\s+")) {
			keyword = keyword.toLowerCase();
			if (!(titleHashMap.containsKey(keyword))) {
				titleHashMap.put(keyword, new ArrayList<Integer>());
			}

			titleHashMap.get(keyword).add(activities.size() - 1);
		}
	}

	/**
	 * Prints the activities that fits the conditions inputted by the user
	 * 
	 * @param type          If given, The type of the activity to search for
	 * @param titleKeywords If given, the title keywords to search for in each
	 *                      activity
	 * @param startingTime  If given, the starting time of the range
	 * @param endingTime    If given, the ending time of the range
	 * @return activities matching all supplied restrictions
	 */
	public ArrayList<Activity> getActivitiesBasedOnRestrictions(String type, String titleKeywords, Time startingTime,
			Time endingTime) {

		ArrayList<Activity> restrictedActivities = new ArrayList<>();
		// If the title keywords are included in the search, use the title keywords
		// hashmap
		if (!titleKeywords.isBlank()) {
			// Initialized to keep track of each list
			ArrayList<ArrayList<Integer>> titleListIndices = new ArrayList<>();

			// If a title keyword is in the hashmap, add it to the titleListIndices array
			for (String titleKeyword : titleKeywords.trim().split("\\s+")) {
				String titleKeywordLowercase = titleKeyword.toLowerCase();
				if (titleHashMap.containsKey(titleKeywordLowercase)) {
					titleListIndices.add(titleHashMap.get(titleKeywordLowercase));
				}
			}

			// If all the keywords aren't in any activity title, return
			if (titleListIndices.size() != titleKeywords.trim().split("\\s+").length) {
				return null;
			}

			if (titleListIndices.size() > 0) {
				// Stores a copy of the first index list in the titleListIndices
				ArrayList<Integer> baseList = new ArrayList<>(titleListIndices.get(0));

				// Take the intersection of each index list after the first one
				for (int j = 1; j < titleListIndices.size(); j++) {
					baseList.retainAll(titleListIndices.get(j));
				}

				// Match each activity in the final baseList by the remaining conditions (if
				// provided)
				for (int index : baseList) {
					try {
						if (((type.equals("home") && activities.get(index) instanceof HomeActivity)
								|| (type.equals("school") && activities.get(index) instanceof SchoolActivity)
								|| (type.equals("other") && activities.get(index) instanceof OtherActivity)
								|| type.equals("none"))
								&& activities.get(index).fitsTimeCondition(startingTime, endingTime)) {
							restrictedActivities.add(activities.get(index));
						}
					} catch (InvalidTimeException exception) {

					}
				}
			}
		}
		// If title condition not provided, match the activity by the other three
		// conditions (four if the activity type is other)
		else {
			for (int i = 0; i < activities.size(); i++) {
				try {
					if (((type.equals("home") && activities.get(i) instanceof HomeActivity)
							|| (type.equals("school") && activities.get(i) instanceof SchoolActivity)
							|| (type.equals("other") && activities.get(i) instanceof OtherActivity)
							|| type.equals("none"))
							&& activities.get(i).fitsTimeCondition(startingTime, endingTime)) {
						restrictedActivities.add(activities.get(i));
					}
				} catch (InvalidTimeException exception) {

				}
			}
		}

		return restrictedActivities;
	}

	/**
	 * Writes all activities to the output file using the planner's activity
	 * format.
	 *
	 * @param fileName the output file name
	 * @throws InvalidTimeException if an activity time is invalid
	 */
	public void writeToFile(String fileName) throws InvalidTimeException {
		PrintWriter writer = null;

		// Instantiate the writer object using a try/catch block
		try {
			writer = new PrintWriter(new FileOutputStream(fileName));
		} catch (FileNotFoundException e) {
			System.out.println("Error opening the file " + fileName);
			return;
		}

		// Write each activity to the file
		for (int i = 0; i < activities.size(); i++) {
			if (activities.get(i) instanceof HomeActivity) {
				writer.println("type = 'home'");
			} else if (activities.get(i) instanceof SchoolActivity) {
				writer.println("type = 'school'");
			} else if (activities.get(i) instanceof OtherActivity) {
				writer.println("type = 'other'");
			}
			writer.println("title = '" + activities.get(i).getTitle() + "'");
			writer.println("start = '" + activities.get(i).getStartingTime().toString() + "'");
			writer.println("end = '" + activities.get(i).getEndingTime().toString() + "'");
			writer.println("comment = '" + activities.get(i).getComment() + "'");
			if (activities.get(i) instanceof OtherActivity) {
				writer.println("location = '" + ((OtherActivity) activities.get(i)).getLocation() + "'");
			}
			writer.println();
		}

		writer.close();
	}

	/**
	 * Reads activities from a file and adds them to the planner.
	 *
	 * @param fileName the name of the file containing the saved activities
	 * @throws InvalidTimeException if a stored time is invalid
	 */
	public void readFromFile(String fileName) throws InvalidTimeException {
		Scanner reader = null;

		try {
			reader = new Scanner(new FileInputStream(fileName));
		} catch (FileNotFoundException e) {
			System.out.println("Error opening the file " + fileName);
			return;
		}

		while (reader.hasNextLine()) {
			String line = reader.nextLine();
			if (line.isBlank()) {
				continue;
			}

			// The format of each line in the file is '[attribute] = [content]'
			// Hence I am accessing the item after '='
			String type = getStoredValue(line);
			String title = getNextStoredValue(reader);
			Time start = getStoredTime(getNextStoredValue(reader));
			Time end = getStoredTime(getNextStoredValue(reader));
			String comment = getNextStoredValue(reader);

			String location = "";
			if (type.equals("other")) {
				location = getNextStoredValue(reader);
			}

			try {
				addNewActivity(type, title, start, end, comment, location);
			} catch (InvalidActivityException exception) {
				System.out.println("Error loading activity: " + exception.getMessage());
			} catch (InvalidTimeException exception) {
				System.out.println("Error processing time: " + exception.getMessage());
			}

			if (reader.hasNextLine()) {
				reader.nextLine();
			}
		}

		reader.close();
	}

	/**
	 * Extracts the value after the equals sign in a stored activity field.
	 *
	 * @param line a stored activity field in key-value format
	 * @return the field value without surrounding apostrophes
	 */
	private static String getStoredValue(String line) {
		if (line == null || !line.contains("=")) {
			return "";
		}
		return line.substring(line.indexOf('=') + 1).replace("'", "").trim();
	}

	/**
	 * Reads and extracts the next key-value field from a file.
	 *
	 * @param reader the scanner containing the stored activity file
	 * @return the extracted value, or an empty string if no line remains
	 */
	private static String getNextStoredValue(Scanner reader) {
		if (!reader.hasNextLine()) {
			return "";
		}
		return getStoredValue(reader.nextLine());
	}

	/**
	 * Converts a stored date and time string into a Time object.
	 *
	 * @param timeString a time in the format YYYY/MM/DD
	 * @return the parsed time
	 * @throws InvalidTimeException if the stored time is invalid
	 */
	private static Time getStoredTime(String timeString) throws InvalidTimeException {
		if (timeString == null)
			return null;
		try {
			String[] dateAndTime = timeString.split("[/,]");
			if (dateAndTime.length != 4)
				return null;
			String[] clock = dateAndTime[3].split(":");
			if (clock.length != 2)
				return null;
			return new Time(Integer.parseInt(dateAndTime[0]), Integer.parseInt(dateAndTime[1]),
					Integer.parseInt(dateAndTime[2]), Integer.parseInt(clock[0]),
					Integer.parseInt(clock[1]));
		} catch (NumberFormatException exception) {
			return null;
		}
	}
}