package dayplanner;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

public class MainWindow extends JFrame {
    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;

    private JPanel container; // Contains the panels managed by CardLayout
    private JPanel initialPanel; // Initial panel displaying welcome message
    private JPanel addPanel; // Add panel letting user add a new activity
    private JPanel searchPanel; // Search panel letting user search for an activity

    public MainWindow() {
        super();
        // Add base properties to window
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("DayPlanner");
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        createInitialPanel();
        createAddPanel();
        createSearchPanel();
        createMenu();

        // Set up container panel
        container = new JPanel(new CardLayout());

        container.add(initialPanel, "initial");
        container.add(addPanel, "add");
        container.add(searchPanel, "search");

        add(container, BorderLayout.CENTER);
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
        addPanel = new JPanel(new GridLayout(2, 2, 50, 50));

        // Add activity section formatting
        JPanel addActivitySection = new JPanel();
        addActivitySection.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        addActivitySection.add(new JLabel("Adding new activity"));
        addPanel.add(addActivitySection);

        // Add/reset button formatting
        addPanel.add(new JLabel("Buttons"));

        /// Messages formatting
        addPanel.add(new JLabel("Messages"));
    }

    private void createSearchPanel() {
        searchPanel = new JPanel();
        searchPanel.add(new JLabel("Searching for new activity"));
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

    public static void main(String[] args) {
        MainWindow window = new MainWindow();
        window.setVisible(true);
    }
}
