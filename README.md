# DayPlanner Project (Assignment 3 for CIS\*2430)

## Name: Manpreet Singh

## Student Number: 1360670

## Date: November 21, 2026

### General Problem

There is a demand for efficiently organizing tasks for the sake of managing time. This program allows users to add tasks that fall into one of three categories: home, school, or other. This is the final version of this project, which rounds everything up by providing the user with a Graphical User Interface to interact with the program easier. In addition, several key enhancements have been made to ensure proper exception handling is taken care of.

### Assumptions and Limitations

The program assumes that the user has a Java Development Kit installed in their machine to be able to run the program (This project was made using Java 21, but versions no earlier than Java 17 are also compatible). In addition, the program assumes that the user has familiarity with interacting with the command line to issue commands and inputs to the console.

The program only allows users to add and search for tasks. The program does not allow users to delete or modify certain attributes of the tasks they add in the program. In addition, the searching mechanism of the program does not allow users to sort through the list in any particular order. The program does, however, allow users to save their DayPlanner content into text files, either by specifying the file name itself after the command to run the program or (if not provided) through the default file name (output.txt).

### Building and Testing

To build and run the program (assuming the user starts at /msingh87_a2, the root Java project directory), do:

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

This program allows users to add tasks that fall into one of three categories: home, school, or other. Upon running the program, the user will be prompted to either add a task, search for task, or quit the program.

When adding a new task, the program will prompt the user to add a title for the task, the starting time of the task, the ending time of the task, and a descriptive comment for the task.

This program also allows users to search through the added tasks by activity, by title, and/or by timings (starting time and ending time). If a task falls after the specified starting time (based on the tasks' own starting time), the task is shown to the user on the bottom screen of the GUI interface, otherwise it is omitted in the output. On the other hand, if a task falls before the specified ending time (based on the tasks' own ending time), the task is shown to the user. A user may choose one or all of the search conditions, and in doing so, any task regardless of the attributes of it will be shown (i.e. if you don't provide a title in the search prompt, all titles will be shown).

Finally, when the user quits the program, a message will display letting the user know that they are exiting the program and their tasks are saved either in the specified file when they first booted up the GUI interface or (if omitted) to output.txt.

### Testing Plan

This plan lists the main conditions for testing activities, searches, and file handling.

#### Menu and command input

- Accept reasonable menu and quit commands.
- Reject unknown commands and let the user try again.
- Locate a supplied input file or warn if it is missing.
- Quitting should close the program and save activities with an accommodating message to the console.

#### Adding an activity

- Add valid home, school, and other activities.
- Reject a blank title.
- Reject missing starting or ending times.
- Reject incomplete or invalid times.
- Reject a starting time equal to or later than the ending time.
- Reject an other activity with no location.
- Display appropriate warning messages for each error encountered.
- Allow an activity to have no comment.
- Add each lowercase title keyword to the HashMap.
- Append the new position to an existing keyword.
- Create a new entry for a new keyword.

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
