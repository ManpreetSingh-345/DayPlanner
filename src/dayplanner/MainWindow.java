package dayplanner;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.Box;

public class MainWindow extends JFrame {
    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;

    public MainWindow() {
        super();
        // Add base properties to window
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("DayPlanner");
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Add Menu bar to window containing all the commands
        JMenu menu = new JMenu("Commands");

        JMenuItem addChoice = new JMenuItem("Add new activity");
        menu.add(addChoice);

        JMenuItem searchChoice = new JMenuItem("Search for activity");
        menu.add(searchChoice);

        JMenuItem quitChoice = new JMenuItem("Quit program");
        quitChoice.addActionListener(e -> {
            System.exit(0);
        });
        menu.add(quitChoice);

        JMenuBar bar = new JMenuBar();
        bar.add(menu);
        setJMenuBar(bar);

        JPanel content = new JPanel(new FlowLayout(FlowLayout.LEFT, 24, 80));
        // Add the welcome text
        JTextArea instructions = new JTextArea(
                "Welcome to DayPlanner.\n\nChoose a command from the “Commands” menu above for adding an activity, searching activities, or quitting the program.");

        instructions.setPreferredSize(new Dimension(400, 300));
        instructions.setLineWrap(true);
        instructions.setWrapStyleWord(true);

        instructions.setEditable(false);
        instructions.setFocusable(false);
        instructions.setOpaque(false);

        content.add(instructions);
        add(content, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        MainWindow window = new MainWindow();
        window.setVisible(true);
    }
}
