package dayplanner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.JFrame;

/**
 * Orchestrates the functionality of the DayPlanner project.
 * Lets users add and search through activities that are split into three
 * categories: home, school, and other.
 * 
 * @author Manpreet Singh
 */
public class DayPlanner {

	JFrame frame = new JFrame("Dayplanner");

	// ArrayList holding all activities
	private static ArrayList<Activity> activities = new ArrayList<>();

	// HashMap containing tokenized title keywords for fast searching
	private static HashMap<String, ArrayList<Integer>> titleHashMap = new HashMap<>();

	/**
	 * The main method of the DayPlanner class. Orchestrates the logic of the
	 * program.
	 * 
	 * @param args Arguments provided in command line (not used)
	 */
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Welcome to the Day Planner Program");
		boolean flag = true;

		if (args.length > 0) {
			readFromFile(args[0]);
		}

		while (flag) {
			prompt();
			// Normalize choice input by lowercasing for more robust input handling
			String choice = scanner.nextLine().toLowerCase().trim();
			switch (choice) {
				// Adding a new activity
				case "add", "a", "1":
					System.out.println("----------------------------------");
					// Get user input
					System.out.println("Adding a new activity");

					System.out.print("Enter type of activity (home, school, or other): ");
					String activityType = scanner.nextLine().toLowerCase();

					// Prompts user for activity type if left blank or user inputted an invalid type
					while ((!activityType.equals("home") && !activityType.equals("school")
							&& !activityType.equals("other"))) {
						System.out.println(
								"Invalid/empty activity type. Please select either home, school, or other.");
						System.out.print("Enter type of activity (home, school, or other): ");
						activityType = scanner.nextLine().toLowerCase();
					}

					System.out.print("Enter title: ");
					String activityTitle = scanner.nextLine();

					// Prompts user for the title again if left blank
					while (activityTitle.isBlank()) {
						System.out.println("Blank title. Please enter a title.");
						System.out.print("Enter title: ");
						activityTitle = scanner.nextLine();
					}

					System.out.print(
							"Enter starting time (Format your response as: '[year] [month] [day] [hour] [minute]'): ");
					Time activityStartingTime = Time.getTimeFromString(scanner.nextLine());

					// Prompts user for the starting time if left blank or isn't valid
					while (activityStartingTime == null || !activityStartingTime.isValid()) {
						System.out.println(
								"Invalid time parameters. Make sure the parameters are integer values, year is positive, the month is between 1-12, the day is between 1-31, the hour is between 0-23, and the minute is between 0-59.");
						System.out
								.println(
										"In addition, as per the Gregorian Calendar, make sure it is a valid leap year if trying to set February 29.");
						System.out.print(
								"Enter starting time (Format your response as: '[year] [month] [day] [hour] [minute]'): ");
						activityStartingTime = Time.getTimeFromString(scanner.nextLine());
					}

					System.out.print(
							"Enter ending time (Format your response as: '[year] [month] [day] [hour] [minute]'): ");
					Time activityEndingTime = Time.getTimeFromString(scanner.nextLine());

					// Prompts user for the starting time if left blank or isn't valid
					while (activityEndingTime == null || !activityEndingTime.isValid()) {
						System.out.println(
								"Invalid time parameters. Make sure the parameters are integer values, year is positive, the month is between 1-12, the day is between 1-31, the hour is between 0-23, and the minute is between 0-59.");
						System.out
								.println(
										"In addition, make sure it is a valid leap year if trying to set February 29.");
						System.out.print(
								"Enter starting time (Format your response as: '[year] [month] [day] [hour] [minute]'): ");
						activityEndingTime = Time.getTimeFromString(scanner.nextLine());
					}

					// If the ending time is earlier than or equal to the starting time, print error
					if (activityEndingTime.compareTo(activityStartingTime) <= 0) {
						System.out.println(
								"Invalid timings. The ending time should be after the starting time.");
						break;
					}

					System.out.print("Enter comment (optional): ");
					String activityComment = scanner.nextLine();

					String activityLocation = "";

					// Only ask for the location if the activity type the user inputted is 'other'
					if (activityType.equals("other")) {
						System.out.print("Enter the location of the activity: ");
						activityLocation = scanner.nextLine();

						while (activityLocation.isBlank()) {
							System.out.println("Blank location. Please enter a location");
							System.out.print("Enter the location of the activity: ");
							activityLocation = scanner.nextLine();
						}
					}

					addNewActivity(activityType, activityTitle, activityStartingTime,
							activityEndingTime,
							activityComment, activityLocation);

					// Uppercase first letter of activity type string before printing to the console
					System.out.println("%s Activity Added Successfully."
							.formatted(activityType.substring(0, 1).toUpperCase()
									+ activityType.substring(1)));
					break;

				// Searching for activities
				case "search", "s", "2":
					System.out.println("----------------------------------");
					System.out.println("Searching for an activity");

					System.out.print("Enter type of activity (home, school, or other): ");
					String searchActivityType = scanner.nextLine().toLowerCase();

					// Prompts user for activity type if invalid (i.e. not left blank and doesn't
					// belong to one of the three activity types)
					while (!searchActivityType.equals("home")
							&& !searchActivityType.equals("school")
							&& !searchActivityType.equals("other")
							&& !searchActivityType.isBlank()) {
						System.out.println(
								"Invalid activity type. Please select either home, school, other, or a blank input.");
						System.out.print("Enter type of activity (home, school, or other): ");
						searchActivityType = scanner.nextLine().toLowerCase();
					}

					System.out.print("Enter keywords from title to search for: ");
					String searchKeywords = scanner.nextLine();
					System.out.print(
							"Enter starting time (Format your response as: '[year] [month] [day] [hour] [minute]'): ");
					String startingTimeString = scanner.nextLine();
					Time startingTime = !startingTimeString.isBlank()
							? Time.getTimeFromString(startingTimeString)
							: null;

					// Prompts user for starting time again if the input isn't blank and is invalid
					if (!startingTimeString.isBlank()) {
						while (startingTime == null || !startingTime.isValid()) {
							System.out.println(
									"Invalid time parameters. Make sure the parameters are integer values, year is positive, the month is between 1-12, the day is between 1-31, the hour is between 0-23, and the minute is between 0-59.");
							System.out
									.println(
											"In addition, as per the Gregorian Calendar, make sure it is a valid leap year if trying to set February 29.");
							System.out.print(
									"Enter starting time (Format your response as: '[year] [month] [day] [hour] [minute]'): ");
							startingTimeString = scanner.nextLine();
							startingTime = !startingTimeString.isBlank()
									? Time.getTimeFromString(startingTimeString)
									: null;
						}
					}

					System.out.print(
							"Enter ending time (Format your response as: '[year] [month] [day] [hour] [minute]'): ");
					String endingTimeString = scanner.nextLine();
					Time endingTime = !endingTimeString.isBlank()
							? Time.getTimeFromString(endingTimeString)
							: null;

					// Prompts user for starting time again if the input isn't blank and is invalid
					if (!endingTimeString.isBlank()) {
						while (endingTime == null || !endingTime.isValid()) {
							System.out.println(
									"Invalid time parameters. Make sure the parameters are integer values, year is positive, the month is between 1-12, the day is between 1-31, the hour is between 0-23, and the minute is between 0-59.");
							System.out.println(
									"In addition, as per the Gregorian Calendar, make sure it is a valid leap year if trying to set February 29.");
							System.out.print(
									"Enter ending time (Format your response as: '[year] [month] [day] [hour] [minute]'): ");
							endingTimeString = scanner.nextLine();
							endingTime = !endingTimeString.isBlank()
									? Time.getTimeFromString(endingTimeString)
									: null;
						}
					}

					// If the ending time is earlier than or equal to the starting time, print error
					if ((startingTime != null && endingTime != null)
							&& startingTime.compareTo(endingTime) >= 0) {
						System.out.println(
								"Invalid timings. The ending time should be after the starting time.");
						break;
					}

					System.out.println("----------------------------------");
					System.out.println("Results based on search restrictions:");
					printActivitiesBasedOnRestrictions(searchActivityType, searchKeywords,
							startingTime,
							endingTime);
					break;

				// Quit program
				case "quit", "q", "3":
					System.out.println("----------------------------------");
					flag = false;
					System.out.println("Quitting program.");
					break;

				// If no match occurs above, ask user to input again
				default:
					System.out.println("----------------------------------");
					System.out.println(
							"Invalid input. Please select one of the options below by typing the option out (i.e. for adding a new activity, type 'add'), or by typing the number corresponding to the activity.");
			}
		}

		if (args.length > 0) {
			writeToFile(args[0]);
		} else {
			writeToFile("output.txt");
		}
		scanner.close();
		return;

	}

	/**
	 * Displays the available actions in the main menu.
	 */
	static void prompt() {
		System.out.println("----------------------------------");
		System.out.println("(1) Add a new activity");
		System.out.println("(2) Search for an activity");
		System.out.println("(3) Terminate the program");
		System.out.print(
				"Please select an option to execute from the ones above ('add', 'search', or 'quit'): ");
	}

	/**
	 * Adds a new activity to the appropriate array
	 * 
	 * @param type         The type of the acitivity
	 * @param title        The title of the activity
	 * @param startingTime The starting time of the activity
	 * @param endingTime   The ending time of the activity
	 * @param comment      A short description of the activity
	 * @param location     The location of the activity (if the activity is 'other')
	 */
	static void addNewActivity(String type, String title, Time startingTime, Time endingTime, String comment,
			String location) {
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
	 */
	static void printActivitiesBasedOnRestrictions(String type, String titleKeywords, Time startingTime,
			Time endingTime) {
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
				return;
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
					if (((type.equals("home") && activities.get(index) instanceof HomeActivity)
							|| (type.equals("school") && activities.get(index) instanceof SchoolActivity)
							|| (type.equals("other") && activities.get(index) instanceof OtherActivity)
							|| type.isBlank())
							&& activities.get(index).fitsTimeCondition(startingTime, endingTime)) {
						System.out.println(activities.get(index));
					}
				}
			}
		}
		// If title condition not provided, match the activity by the other three
		// conditions (four if the activity type is other)
		else {
			for (int i = 0; i < activities.size(); i++) {
				if (((type.equals("home") && activities.get(i) instanceof HomeActivity)
						|| (type.equals("school") && activities.get(i) instanceof SchoolActivity)
						|| (type.equals("other") && activities.get(i) instanceof OtherActivity)
						|| type.isBlank())
						&& activities.get(i).fitsTimeCondition(startingTime, endingTime)) {
					System.out.println(activities.get(i));
				}
			}
		}
	}

	/**
	 * Writes all activities to the output file using the planner's activity
	 * format.
	 */
	private static void writeToFile(String fileName) {
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
	 */
	private static void readFromFile(String fileName) {
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
			String title = getStoredValue(reader.nextLine());
			Time start = getStoredTime(getStoredValue(reader.nextLine()));
			Time end = getStoredTime(getStoredValue(reader.nextLine()));
			String comment = getStoredValue(reader.nextLine());

			String location = "";
			if (type.equals("other")) {
				location = getStoredValue(reader.nextLine());
			}

			addNewActivity(type, title, start, end, comment, location);

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
		return line.substring(line.indexOf('=') + 1).replace("'", "").trim();
	}

	/**
	 * Converts a stored date and time string into a Time object.
	 *
	 * @param timeString a time in the format YYYY/MM/DD
	 * @return the parsed time
	 */
	private static Time getStoredTime(String timeString) {
		String[] dateAndTime = timeString.split("[/,]");
		String[] clock = dateAndTime[3].split(":");
		return new Time(Integer.parseInt(dateAndTime[0]), Integer.parseInt(dateAndTime[1]),
				Integer.parseInt(dateAndTime[2]), Integer.parseInt(clock[0]),
				Integer.parseInt(clock[1]));
	}
}