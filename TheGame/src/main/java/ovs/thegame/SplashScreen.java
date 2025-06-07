//Name: Hetarth Parikh
//Unit,Lesson,Name of Assignment: Unit 4 Lesson 3, Implementation- The Game
//Date of Completion: November 23rd 2024
//Description:This is a game called Animal-Match where the user plays and has fun across 
//various levels matching and getting the fastest time across different levels in a unique
//animal themed memory game! 
//Now this is the splash screen. This is where the user starts their game off by seeing and 
//beginning their playing journey.//They can see instructions on how to play and
//also is a nice visual as well. 




package ovs.thegame; // package declaration for organizing classes

import javax.swing.*; // imports all javax.swing classes for GUI components
import java.awt.*; // imports java.awt classes for layout management
import java.awt.event.ActionEvent; // imports ActionEvent class for handling actions
import java.awt.event.ActionListener; // imports ActionListener interface for action handling

public class SplashScreen { // class definition for SplashScreen
    public static void showSplash() { // method to show the splash screen
        JFrame splashFrame = new JFrame("Welcome to the Card Matching Game"); // creates a new JFrame for the splash screen

        splashFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); // sets the splash screen to full screen
        splashFrame.setUndecorated(true); // removes the title bar for a full-screen effect

        splashFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // sets the default close operation to exit the application
        splashFrame.setLayout(new BorderLayout()); // sets the layout manager to BorderLayout

        ImageIcon background = new ImageIcon("C:\\Users\\user\\OneDrive\\Documents\\NetBeansProjects\\TheGame\\images\\screen.jpg"); // loads the background image
        JLabel backgroundLabel = new JLabel(background); // creates a JLabel to display the background image
        backgroundLabel.setLayout(new BorderLayout()); // sets the layout manager for the background label
        splashFrame.setContentPane(backgroundLabel); // sets the background label as the content pane

        JLabel splashLabel = new JLabel("Welcome to Animal-Match!", SwingConstants.CENTER); // creates a title label with centered text
        splashLabel.setFont(new Font("Impact", Font.BOLD, 30)); // sets the font style and size for the title label
        splashLabel.setForeground(Color.WHITE); // sets the text color to white
        splashFrame.add(splashLabel, BorderLayout.CENTER); // adds the title label to the center of the splash screen

        JPanel buttonPanel = new JPanel(); // creates a panel for buttons
        buttonPanel.setOpaque(false); // makes the button panel transparent
        buttonPanel.setLayout(new FlowLayout()); // sets the layout manager to FlowLayout

        JButton chooseLevelButton = new JButton("Start Playing"); // creates a button to start the game
        chooseLevelButton.setFont(new Font("Arial", Font.PLAIN, 20)); // sets the font style and size for the button
        buttonPanel.add(chooseLevelButton); // adds the button to the button panel

        JButton howToPlayButton = new JButton("How to Play"); // creates a button to display game instructions
        howToPlayButton.setFont(new Font("Arial", Font.PLAIN, 20)); // sets the font style and size for the button
        buttonPanel.add(howToPlayButton); // adds the button to the button panel

        backgroundLabel.add(buttonPanel, BorderLayout.SOUTH); // adds the button panel to the bottom of the background label

        chooseLevelButton.addActionListener(e -> showLevelSelection(splashFrame)); // adds an action listener to handle level selection

        howToPlayButton.addActionListener(e -> showHowToPlay()); // adds an action listener to display game instructions

        splashFrame.setVisible(true); // makes the splash screen visible
    }

    private static void showLevelSelection(JFrame splashFrame) { // method to show the level selection dialog
        String[] options = {"Level 1: Unlimited Moves"}; // array of level options
        int choice = JOptionPane.showOptionDialog(splashFrame, "Select Level:", // displays a dialog to choose a level
                "Choose Level", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, options, options[0]); // sets options and default choice
        if (choice!= -1){
        splashFrame.dispose(); // closes the splash screen after choosing a level
        }

        if (choice == 0) { // checks if the first option is selected
            new GameController(4, 4); // starts Level 1 with a 4x4 grid
        }
    }

    private static void showHowToPlay() { // method to show the instructions
        JOptionPane.showMessageDialog(null, // displays a dialog box with game instructions
            "Game Instructions:\n" +
            "1. Flip two cards at a time.\n" +
            "2. Try to match the cards with the same image.\n" +
            "3. Moves increase when a match is made (2 flips= 1 move).\n" +
            "4. Continue until all cards are matched.\n" +
            "5. Levels go upto 3 with time and move constraints.\n" +        
            "Good luck!"); // provides instructions for the game
    }
}
