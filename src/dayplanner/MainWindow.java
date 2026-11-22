package dayplanner;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class MainWindow extends JFrame {
    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;

    private JPanel container; // Contains the panels managed by CardLayout
    private JPanel initialPanel; // Initial panel displaying welcome message
    private JPanel addPanel; // Add panel letting user add a new activity
    private JPanel searchPanel; // Search panel letting user search for an activity

    DayPlanner dayplanner = new DayPlanner();

    public MainWindow() {
        super();
        dayplanner.readFromFile("output.txt");
        // Add base properties to window
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("DayPlanner");
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        // Save contents of dayplanner upon exit
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("Saving to output.txt...");
                dayplanner.writeToFile("output.txt");
            }
        });

        createInitialPanel();
        createAddPanel();
        createSearchPanel();
        createMenu();

        // Set up container panel
        container = new JPanel(new CardLayout());

        container.add(initialPanel, "initial");
        container.add(addPanel, "add");
        container.add(searchPanel, "search");

        add(container);
    }

    private void createInitialPanel() {
        initialPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 24, 80));

        JTextArea instructions = new JTextArea(
                "Welcome to DayPlanner.\n\nChoose a command from the “Commands” menu above for adding an activity, searching activities, or quitting the program.");

        instructions.setPreferredSize(new Dimension(400, 300));
        instructions.setLineWrap(true);
        instructions.setWrapStyleWord(true);

        instructions.setEditable(false);
        instructions.setFocusable(false);
        instructions.setOpaque(false);

        initialPanel.add(instructions);

    }

    private void createAddPanel() {
        addPanel = new JPanel(new BorderLayout());

        // Instantiating grid layout
        JPanel addPanelGrid = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        //// Add activity section
        JPanel addActivitySection = new JPanel();
        addActivitySection.setLayout(new BoxLayout(addActivitySection, BoxLayout.Y_AXIS));
        addActivitySection.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        /// Add activity title
        JLabel addActivityHeader = new JLabel("Adding an activity: ");
        addActivityHeader.setBorder(BorderFactory.createEmptyBorder(5, 10, 15, 0));
        addActivitySection.add(addActivityHeader);

        // Inputs container
        JPanel inputContainer = new JPanel();
        inputContainer.setAlignmentX(Component.LEFT_ALIGNMENT);
        inputContainer.setLayout(new BoxLayout(inputContainer, BoxLayout.Y_AXIS));
        inputContainer.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));
        inputContainer.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        /// Type input
        JPanel addActivityType = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        addActivityType.setAlignmentX(Component.LEFT_ALIGNMENT);
        // ↓↓ Sets the maximum height of the addActivityType panel to its preferred
        // height, and let's it stretch as much as it wants to horizontally
        addActivityType.add(new JLabel("Type: "));
        String[] typeOptions = { "home", "school", "other" };
        JComboBox<String> typeDropdown = new JComboBox<>(typeOptions);
        addActivityType.add(typeDropdown);
        addActivityType.setMaximumSize(new Dimension(Integer.MAX_VALUE, addActivityType.getPreferredSize().height));
        inputContainer.add(addActivityType);

        // Create space between input boxes
        inputContainer.add(Box.createRigidArea(new Dimension(0, 10)));

        /// Title input
        JPanel addActivityTitle = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        addActivityTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        addActivityTitle.add(new JLabel("Title: "));
        JTextField titleInput = new JTextField(20);
        addActivityTitle.add(titleInput);
        addActivityTitle.setMaximumSize(new Dimension(Integer.MAX_VALUE, addActivityType.getPreferredSize().height));
        inputContainer.add(addActivityTitle);

        // Create space between input boxes
        inputContainer.add(Box.createRigidArea(new Dimension(0, 10)));

        /// Starting time input
        JPanel addActivityStartingTime = new JPanel();
        addActivityStartingTime.setLayout(new BoxLayout(addActivityStartingTime, BoxLayout.Y_AXIS));
        addActivityStartingTime.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel startingTimeLabel = new JLabel("Starting time (year, month, day, hour, minute): ");
        startingTimeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField startingTimeInput = new JTextField(25);
        startingTimeInput.setAlignmentX(Component.LEFT_ALIGNMENT);
        startingTimeInput.setMaximumSize(
                new Dimension(startingTimeInput.getPreferredSize().width, startingTimeInput.getPreferredSize().height));

        addActivityStartingTime.add(startingTimeLabel);
        addActivityStartingTime.add(startingTimeInput);

        inputContainer.add(addActivityStartingTime);

        // Create space between input boxes
        inputContainer.add(Box.createRigidArea(new Dimension(0, 10)));

        /// Ending time input
        JPanel addActivityEndingTime = new JPanel();
        addActivityEndingTime.setLayout(new BoxLayout(addActivityEndingTime, BoxLayout.Y_AXIS));
        addActivityEndingTime.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel endingTimeLabel = new JLabel("Ending time (year, month, day, hour, minute): ");
        endingTimeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField endingTimeInput = new JTextField(25);
        endingTimeInput.setAlignmentX(Component.LEFT_ALIGNMENT);
        endingTimeInput.setMaximumSize(
                new Dimension(endingTimeInput.getPreferredSize().width, endingTimeInput.getPreferredSize().height));

        addActivityEndingTime.add(endingTimeLabel);
        addActivityEndingTime.add(endingTimeInput);

        inputContainer.add(addActivityEndingTime);

        // Create space between input boxes
        inputContainer.add(Box.createRigidArea(new Dimension(0, 10)));

        /// Comment input
        JPanel addActivityComment = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        addActivityComment.setAlignmentX(Component.LEFT_ALIGNMENT);
        addActivityComment.add(new JLabel("Comment: "));
        JTextField commentInput = new JTextField(20);
        addActivityComment.add(commentInput);
        addActivityComment
                .setMaximumSize(new Dimension(Integer.MAX_VALUE, addActivityComment.getPreferredSize().height));
        inputContainer.add(addActivityComment);

        /// Location input
        JPanel addActivityLocation = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        addActivityLocation.setAlignmentX(Component.LEFT_ALIGNMENT);
        addActivityLocation.add(new JLabel("Location: "));
        JTextField locationInput = new JTextField(20);
        addActivityLocation.add(locationInput);
        addActivityLocation
                .setMaximumSize(
                        new Dimension(Integer.MAX_VALUE, addActivityLocation.getPreferredSize().height));
        addActivityLocation.setVisible(false);
        inputContainer.add(addActivityLocation);

        typeDropdown.addActionListener(e -> {
            String selected = (String) typeDropdown.getSelectedItem();

            if (selected.equals("other")) {
                addActivityLocation.setVisible(true);
            } else {
                addActivityLocation.setVisible(false);
            }
        });

        addActivitySection.add(inputContainer);

        // Grid bag constraints for 'add activity' section
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.75;
        gbc.weighty = 0.40;
        gbc.fill = GridBagConstraints.BOTH;
        addPanelGrid.add(addActivitySection, gbc);

        //// Add button section
        JPanel addButtonSection = createButtonSection("Add");

        // Grid bag constraints for button section
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.25;
        gbc.weighty = 0.40;
        gbc.fill = GridBagConstraints.BOTH;
        addPanelGrid.add(addButtonSection, gbc);

        //// Messages section
        JPanel messagesSection = new JPanel(new BorderLayout(0, 5)); // 5px vertical gap between label and text area
        messagesSection.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel messagesLabel = new JLabel("Messages: ");
        messagesSection.add(messagesLabel, BorderLayout.NORTH);

        JTextArea messages = new JTextArea();
        messages.setMargin(new Insets(10, 15, 10, 15));

        JScrollPane messagesPane = new JScrollPane(messages);
        messagesPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        messagesPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        messagesSection.add(messagesPane, BorderLayout.CENTER);

        // Grid bag constraints for messages section
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridheight = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.weighty = 0.60;
        gbc.fill = GridBagConstraints.BOTH;
        addPanelGrid.add(messagesSection, gbc);

        //// Action listeners for button inputs
        for (Component comp : addButtonSection.getComponents()) {
            if (comp instanceof JButton && comp.getName().equals("Reset")) {
                JButton resetButton = (JButton) comp;
                resetButton.addActionListener(e -> {
                    typeDropdown.setSelectedIndex(0);
                    titleInput.setText("");
                    startingTimeInput.setText("");
                    endingTimeInput.setText("");
                    commentInput.setText("");
                    locationInput.setText("");
                });
            } else if (comp instanceof JButton && comp.getName().equals("Add")) {
                JButton addButton = (JButton) comp;
                addButton.addActionListener(e -> {
                    Time startingTime = Time.getTimeFromString(startingTimeInput.getText());
                    Time endingTime = Time.getTimeFromString(endingTimeInput.getText());
                    // Time conditions
                    boolean emptyStartingTime = startingTime == null;
                    boolean emptyEndingTime = endingTime == null;
                    boolean invalidStartingTime = !startingTime.isValid();
                    boolean invalidEndingTime = !endingTime.isValid();
                    boolean startingTimeAfterEndingTime = endingTime.compareTo(startingTime) <= 0;
                    if (titleInput.getText().isBlank()) {
                        messages.append("Error: title is missing. Try again.\n");
                    } else if (emptyStartingTime) {
                        messages.append("Error: starting time is missing. Try again.\n");
                    } else if (emptyEndingTime) {
                        messages.append("Error: ending time is missing. Try again.\n");
                    } else if (invalidStartingTime) {
                        messages.append(
                                "Error: starting time is invalid. Make sure to adhere to the Gregorian standard, which includes valid dates in case of leap years. Try again.\n");
                    } else if (invalidEndingTime) {
                        messages.append(
                                "Error: ending time is invalid. Make sure to adhere to the Gregorian standard, which includes valid dates in case of leap years. Try again.\n");
                    } else if (startingTimeAfterEndingTime) {
                        messages.append("Error: starting time must be strictly before ending time. Try again.\n");
                    } else if (typeDropdown.getSelectedItem().equals("other") && locationInput.getText().isBlank()) {
                        messages.append("Error: location is missing. Try again.\n");
                    } else {
                        messages.append("Adding new activity\n");
                        String type = String.valueOf(typeDropdown.getSelectedItem());
                        String title = titleInput.getText();
                        String comment = commentInput.getText();
                        String location = locationInput.getText();
                        dayplanner.addNewActivity(type, title, startingTime, endingTime, comment, location);
                    }
                });
            }
        }

        // Add the grid layout to the wrapper panel
        addPanel.add(addPanelGrid, BorderLayout.CENTER);
    }

    private void createSearchPanel() {
        searchPanel = new JPanel(new BorderLayout());

        // Instantiating grid layout
        JPanel searchPanelGrid = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        //// Search activity section
        JPanel searchActivitySection = new JPanel();
        searchActivitySection.setLayout(new BoxLayout(searchActivitySection, BoxLayout.Y_AXIS));
        searchActivitySection.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        /// Search activity title
        JLabel searchActivityHeader = new JLabel("Searching activities: ");
        searchActivityHeader.setBorder(BorderFactory.createEmptyBorder(5, 10, 15, 0));
        searchActivitySection.add(searchActivityHeader);

        // Inputs container
        JPanel inputContainer = new JPanel();
        inputContainer.setAlignmentX(Component.LEFT_ALIGNMENT);
        inputContainer.setLayout(new BoxLayout(inputContainer, BoxLayout.Y_AXIS));
        inputContainer.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));
        inputContainer.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        /// Type input
        JPanel searchActivityType = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        searchActivityType.setAlignmentX(Component.LEFT_ALIGNMENT);
        // ↓↓ Sets the maximum height of the searchActivityType panel to its preferred
        // height, and let's it stretch as much as it wants to horizontally
        searchActivityType.add(new JLabel("Type: "));
        String[] typeOptions = { "none", "home", "school", "other" };
        JComboBox<String> typeDropdown = new JComboBox<>(typeOptions);
        searchActivityType.add(typeDropdown);
        searchActivityType
                .setMaximumSize(new Dimension(Integer.MAX_VALUE, searchActivityType.getPreferredSize().height));
        inputContainer.add(searchActivityType);

        // Create space between input boxes
        inputContainer.add(Box.createRigidArea(new Dimension(0, 10)));

        /// Title input
        JPanel searchActivityTitle = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        searchActivityTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        searchActivityTitle.add(new JLabel("Title: "));
        JTextField titleInput = new JTextField(20);
        searchActivityTitle.add(titleInput);
        searchActivityTitle
                .setMaximumSize(new Dimension(Integer.MAX_VALUE, searchActivityTitle.getPreferredSize().height));
        inputContainer.add(searchActivityTitle);

        // Create space between input boxes
        inputContainer.add(Box.createRigidArea(new Dimension(0, 10)));

        /// Starting time input
        JPanel searchActivityStartingTime = new JPanel();
        searchActivityStartingTime.setLayout(new BoxLayout(searchActivityStartingTime, BoxLayout.Y_AXIS));
        searchActivityStartingTime.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel startingTimeLabel = new JLabel("Starting time (year, month, day, hour, minute): ");
        startingTimeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField startingTimeInput = new JTextField(25);
        startingTimeInput.setAlignmentX(Component.LEFT_ALIGNMENT);
        startingTimeInput.setMaximumSize(
                new Dimension(startingTimeInput.getPreferredSize().width, startingTimeInput.getPreferredSize().height));

        searchActivityStartingTime.add(startingTimeLabel);
        searchActivityStartingTime.add(startingTimeInput);

        inputContainer.add(searchActivityStartingTime);

        // Create space between input boxes
        inputContainer.add(Box.createRigidArea(new Dimension(0, 10)));

        /// Ending time input
        JPanel searchActivityEndingTime = new JPanel();
        searchActivityEndingTime.setLayout(new BoxLayout(searchActivityEndingTime, BoxLayout.Y_AXIS));
        searchActivityEndingTime.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel endingTimeLabel = new JLabel("Ending time (year, month, day, hour, minute): ");
        endingTimeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField endingTimeInput = new JTextField(25);
        endingTimeInput.setAlignmentX(Component.LEFT_ALIGNMENT);
        endingTimeInput.setMaximumSize(
                new Dimension(endingTimeInput.getPreferredSize().width, endingTimeInput.getPreferredSize().height));

        searchActivityEndingTime.add(endingTimeLabel);
        searchActivityEndingTime.add(endingTimeInput);

        inputContainer.add(searchActivityEndingTime);

        // Create space between input boxes
        inputContainer.add(Box.createRigidArea(new Dimension(0, 10)));

        // Add inputs to search section
        searchActivitySection.add(inputContainer);

        // Grid bag constraints for 'search activity' section
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.75;
        gbc.weighty = 0.40;
        gbc.fill = GridBagConstraints.BOTH;
        searchPanelGrid.add(searchActivitySection, gbc);

        //// Search button section
        JPanel searchButtonSection = createButtonSection("Search");

        // Grid bag constraints for button section
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.25;
        gbc.weighty = 0.40;
        gbc.fill = GridBagConstraints.BOTH;
        searchPanelGrid.add(searchButtonSection, gbc);

        //// Messages section
        JPanel messagesSection = new JPanel(new BorderLayout(0, 5)); // 5px vertical gap between label and text area
        messagesSection.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel messagesLabel = new JLabel("Search results: ");
        messagesSection.add(messagesLabel, BorderLayout.NORTH);

        JTextArea messages = new JTextArea();
        messages.setMargin(new Insets(10, 15, 10, 15));

        JScrollPane messagesPane = new JScrollPane(messages);
        messagesPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        messagesPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        messagesSection.add(messagesPane, BorderLayout.CENTER);

        // Grid bag constraints for messages section
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridheight = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.weighty = 0.60;
        gbc.fill = GridBagConstraints.BOTH;
        searchPanelGrid.add(messagesSection, gbc);

        //// Action listeners for button inputs
        for (Component comp : searchButtonSection.getComponents()) {
            if (comp instanceof JButton && comp.getName().equals("Reset")) {
                JButton resetButton = (JButton) comp;
                resetButton.addActionListener(e -> {
                    typeDropdown.setSelectedIndex(0);
                    titleInput.setText("");
                    startingTimeInput.setText("");
                    endingTimeInput.setText("");
                });
            } else if (comp instanceof JButton && comp.getName().equals("Search")) {
                JButton searchButton = (JButton) comp;
                searchButton.addActionListener(e -> {
                    Time startingTime = Time.getTimeFromString(startingTimeInput.getText());
                    Time endingTime = Time.getTimeFromString(endingTimeInput.getText());
                    // Time conditions
                    boolean invalidStartingTime = startingTime != null && !startingTime.isValid();
                    boolean invalidEndingTime = endingTime != null && !endingTime.isValid();
                    boolean startingTimeAfterEndingTime = startingTime != null && endingTime != null
                            && endingTime.compareTo(startingTime) <= 0;
                    if (invalidStartingTime) {
                        messages.append(
                                "Error: starting time is invalid. Make sure to adhere to the Gregorian standard, which includes valid dates in case of leap years. Try again.\n");
                    } else if (invalidEndingTime) {
                        messages.append(
                                "Error: ending time is invalid. Make sure to adhere to the Gregorian standard, which includes valid dates in case of leap years. Try again.\n");
                    } else if (startingTimeAfterEndingTime) {
                        messages.append("Error: starting time must be strictly before ending time. Try again.\n");
                    } else {
                        messages.append("Searching activities\n");
                        String type = String.valueOf(typeDropdown.getSelectedItem());
                        String title = titleInput.getText();
                        ArrayList<Activity> restrictedActivities = dayplanner.getActivitiesBasedOnRestrictions(type,
                                title, startingTime, endingTime);

                        if (restrictedActivities != null && restrictedActivities.size() != 0) {
                            for (Activity activity : restrictedActivities) {
                                if (activity instanceof HomeActivity) {
                                    messages.append(((HomeActivity) activity).toString() + "\n");
                                } else if (activity instanceof SchoolActivity) {
                                    messages.append(((SchoolActivity) activity).toString() + "\n");
                                } else if (activity instanceof OtherActivity) {
                                    messages.append(((OtherActivity) activity).toString() + "\n");
                                }
                            }
                        } else {
                            messages.append("No activities found.\n");
                        }
                    }
                });
            }
        }

        // Add the grid layout to the wrapper panel
        searchPanel.add(searchPanelGrid, BorderLayout.CENTER);
    }

    private void createMenu() {
        // Add Menu bar to window containing all the commands
        // This will persist across all panels
        JMenu menu = new JMenu("Commands");

        // Add
        JMenuItem addChoice = new JMenuItem("Add new activity");
        addChoice.addActionListener(e -> {
            showPanel("add");
        });
        menu.add(addChoice);

        // Search
        JMenuItem searchChoice = new JMenuItem("Search for activity");
        searchChoice.addActionListener(e -> {
            showPanel("search");
        });
        menu.add(searchChoice);

        // Quit
        JMenuItem quitChoice = new JMenuItem("Quit program");
        quitChoice.addActionListener(e -> {
            System.exit(0);
        });
        menu.add(quitChoice);

        // Add menu bar to frame
        JMenuBar bar = new JMenuBar();
        bar.add(menu);
        setJMenuBar(bar);
    }

    private void showPanel(String panelName) {
        CardLayout layout = (CardLayout) container.getLayout();
        layout.show(container, panelName);
    }

    private JPanel createButtonSection(String action) {
        //// Search/reset button section
        JPanel buttonSection = new JPanel();
        buttonSection.setLayout(new BoxLayout(buttonSection, BoxLayout.Y_AXIS));
        buttonSection.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Buttons + Layouts
        buttonSection.add(Box.createVerticalGlue()); // Creates space away from top of the screen

        // Reset Button
        JButton resetButton = new JButton("Reset");
        resetButton.setName("Reset");
        resetButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonSection.add(resetButton);

        // Create space between buttons
        buttonSection.add(Box.createRigidArea(new Dimension(0, 50)));

        // Search button
        JButton actionButton = new JButton(action);
        actionButton.setName(action);
        actionButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonSection.add(actionButton);

        buttonSection.add(Box.createVerticalGlue()); // Creates space away from bottom of the screen

        return buttonSection;
    }

    public static void main(String[] args) {
        MainWindow window = new MainWindow();
        window.setVisible(true);
    }
}
