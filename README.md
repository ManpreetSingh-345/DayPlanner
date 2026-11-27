# DayPlanner Project (Assignment 3 for CIS\*2430)

## Name: Manpreet Singh

## Student Number: 1360670

## Date: November 27, 2026

### General Problem

There is a demand for efficiently organizing tasks for the sake of managing time. This program allows users to add tasks that fall into one of three categories: home, school, or other. This is the final version of this project, which rounds everything up by providing the user with a Graphical User Interface to interact with the program easier. In addition, several key enhancements have been made to ensure proper exception handling is taken care of.

### Assumptions and Limitations

The program assumes that the user has a Java Development Kit installed in their machine to be able to run the program (This project was made using Java 21, but versions no earlier than Java 17 are also compatible). The program also assumes that times are entered in the format the GUI asks for, which is the year, month, day, hour, and minute separated by a comma and a space (e.g. 2026, 12, 4, 9, 30), with the hour in 24-hour format. Any time that doesn't follow this format, or isn't a valid date under the Gregorian calendar (e.g. February 29 on a non leap year), is rejected and the user is asked to try again.

In addition, the program assumes that a file passed in when starting the program follows the same format that the program saves activities in. Any activity in the file that has invalid fields is skipped, and a warning is printed to the console.

The program only allows users to add and search for tasks. The program does not allow users to delete or modify certain attributes of the tasks they add in the program. In addition, the searching mechanism of the program does not allow users to sort through the list in any particular order. The program does, however, allow users to save their DayPlanner content into text files, either by specifying the file name itself after the command to run the program or (if not provided) through the default file name (output.txt).

### Building and Testing

To build and run the program (assuming the user starts at /msingh87_a3, the root Java project directory), do:

```bash

cd src
javac dayplanner/*.java // Compiles the program
java dayplanner.MainWindow // Runs the GUI interface

```

If you have a file you'd like the program to read from, use the following command:

```bash

cd src
javac dayplanner/*.java // Compiles the program
java dayplanner.MainWindow [filename.txt] // Runs the program using the text file provided as a basis

```

This program allows users to add tasks that fall into one of three categories: home, school, or other. Upon running the program, a welcome screen is shown, and the user can use the "Commands" menu at the top to either add a task, search for a task, or quit the program.

When adding a new task, the user picks the type of task from the drop-down menu (home by default), and fills in the title, starting time, ending time, and an optional comment for the task. The location field only shows up when the type is set to "other", since only those tasks need a location. Clicking "Add" will try to add the task, and if anything is invalid, an error message is shown in the messages area at the bottom so the user can fix it and try again. Clicking "Reset" clears all of the text fields.

This program also allows users to search through the added tasks by activity type, by title, and/or by timings (starting time and ending time). The results (or any errors) show up in the "Search Results" area at the bottom. If a task falls after the specified starting time (based on the tasks' own starting time), the task is shown to the user on the bottom screen of the GUI interface, otherwise it is omitted in the output. On the other hand, if a task falls before the specified ending time (based on the tasks' own ending time), the task is shown to the user. A user may choose one or all of the search conditions, and in doing so, any task regardless of the attributes of it will be shown (i.e. if you don't provide a title in the search prompt, all titles will be shown).

Finally, when the user quits the program (either through the "Commands" menu or the X button on the window), the tasks are saved to output.txt, and also to the file specified when they first booted up the GUI interface (if one was given). A message is printed to the console letting the user know their tasks are being saved.

### Testing Plan

This plan lists the main conditions for testing activities, searches, and file handling.

#### GUI and commands

- The welcome screen and "Commands" menu (add, search, quit) show up on startup.
- Choosing add or search switches to the right screen.
- Both "Quit" and the X button close the program and save activities with an accommodating message to the console.
- Locate a supplied input file or warn if it is missing.
- The location field only shows up when the type is "other".
- Reset clears all of the text fields but leaves the type drop-down alone.
- Long messages and results can be scrolled through in the text area.

#### Adding an activity

- Add valid home, school, and other activities.
- Reject a blank title.
- Reject missing starting or ending times.
- Reject incomplete or invalid times.
- Reject a starting time equal to or later than the ending time.
- Reject an other activity with no location.
- Reject non-number values in a time (e.g. "2026, Dec, 4, 9, 30") without crashing.
- Reject impossible dates, such as February 29 on a non leap year or April 31.
- Display appropriate warning messages for each error encountered.
- Allow an activity to have no comment.
- Add each lowercase title keyword to the HashMap.
- Append the new position to an existing keyword.
- Create a new entry for a new keyword.

#### Exceptions and object behaviour

- Time's constructor and mutators throw an InvalidTimeException for out of range values.
- Activity's constructor and mutators throw an InvalidActivityException for a blank title, a missing time, or a starting time that isn't before the ending time.
- compareTo in Time throws a NullPointerException when given null.
- Changing a Time object after passing it into an activity doesn't change the activity's time (copies are stored).
- equals returns true for two activities of the same type with the same fields, and false for different types.

#### Searching by title

- Search when no title matches exist.
- Search for matches at the start, middle, and end of the ArrayList.
- Search with one keyword.
- Search with multiple keywords in any order.
- Use case-insensitive keyword matching.
- Match whole words only, such as not matching "program" with "programming".
- A blank title should not restrict a search.

#### Searching by activity type

- Search for home, school, or other activities.
- Search with no type restriction.
- Search for a type with no matches.

#### Searching by time

- Search with no times, only a starting time, only an ending time, or both.
- Search before, between, and after activities.
- Reject malformed or invalid times.
- Reject a starting time equal to or later than the ending time.
- Include only activities within the given time range.

#### Combined searches

- Search using title, type, and time together.
- Search using two conditions at a time.
- Intersect HashMap keyword lists before applying other conditions.
- Combined conditions must all match.

#### File input

- Start with no input file.
- Load home, school, and other activities.
- Load blank lines, comments, and locations.
- Skip malformed records and show a warning.
- Require locations only for other activities.
- Normalize loaded title keywords and rebuild the HashMap.
- Check that keywords map to the correct ArrayList positions.

#### File output

- Save an empty list.
- Save home, school, and other activities in the assignment format.
- Check that saved data can be loaded later.
- Write comments and locations where required.
- Create or overwrite `output.txt` on quit.
- Use `output.txt` when no filename is given.
- Report an error if the output file cannot be written.

### Submission Note

I submitted an earlier version of this assignment on November 23, 2026. Please mark this latest submission instead.
