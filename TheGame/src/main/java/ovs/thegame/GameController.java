
//Description:This is a game called Animal-Match where the user plays and has fun across 
//various levels matching and getting the fastest time across different levels in a unique
//animal themed memory game! 
//this is the main game controller class where all the functionality, features 
//and everything will be. Its like the powerhouse of the game here. All the levels, and
//main methods will be here which essentially controls the game, hence the name 
//GameController of the class. Here I've added onto the game 3 distinct levels. 
//The first level will be kind of like a freebie where the user has unlimited
//moves and time to complete. The second level is only unlocked if the first is completed.
//The second level has a move constraint of 20 moves only. If it gets to 21, you start again. 
//The third level has 15 moves to complete and only 30 seconds of time. There is also a 
//leaderboard tracking the fastest times on each level as you can play again the levels
//that you have unlocked as many times as you want. Ive added various labels, and a timer as well.




package ovs.thegame;// package declaration for organizing classes

import javax.swing.*;  // import the Swing components for UI
import java.awt.*;  // import AWT components for layout and event handling
import java.awt.event.ActionEvent;  // import ActionEvent for handling button clicks
import java.awt.event.ActionListener;  // import ActionListener interface for event handling
import java.io.BufferedReader;  // import BufferedReader for reading files
import java.io.FileReader;  // import FileReader for reading from files
import java.io.FileWriter;  // import FileWriter for writing to files
import java.io.IOException;  // import IOException for handling file-related errors
import java.io.PrintWriter;  // import PrintWriter for writing to files
import java.io.File;  // import File class for file operations
import java.io.BufferedWriter;  // import BufferedWriter for efficient file writing
import java.awt.event.WindowAdapter;  // import WindowAdapter for window event handling
import java.awt.event.WindowEvent;  // import WindowEvent for window-related events
import java.util.ArrayList;//allows to use array lists
import java.util.List;//allows to use lists




// main controller class for the game logic
public class GameController {
    private GameBoard board; // game board object
    private Card firstSelectedCard; // tracks the first selected card
    private int score = 0; // player's score
    private JLabel scoreLabel; // label to display the score
    private long startTime; // tracks the start time of the game
    private static final String LEADERBOARD_FILE = "fastestTime.txt"; // leaderboard file name
    private int moveCounter = 0; // counter for moves made by the player
    private JLabel moveLabel; // label to display the move count
    private int moveLimit = -1; // default move limit, -1 indicates unlimited moves
    private int timeRemaining; // tracks remaining time
    private int timeLimit; // maximum allowed time for the level
    LeaderboardManager leaderboardManager = new LeaderboardManager(); // leaderboard manager object
    private int unlockedLevel = 1; // tracks the unlocked level, starts at level 1
    private long bestTimeLevel1 = Long.MAX_VALUE; // best time for level 1
    private long bestTimeLevel2 = Long.MAX_VALUE; // best time for level 2
    private long bestTimeLevel3 = Long.MAX_VALUE; // best time for level 3
    private Timer timer; // declare a Timer variable for a regular timer
private Timer gtimer; // declare a Timer variable for a game-related timer
private static List<JFrame> openWindows = new ArrayList<>(); // create a list to hold all open windows
private static List<JFrame> GameoverWindows = new ArrayList<>(); // create a list to hold game-over windows
private static List<Timer> activeTimers = new ArrayList<>(); // create a list to keep track of active timers

    


    
    //this is the constructor for initializing the game controller
    public GameController(int rows, int cols) {
        
        JFrame frame = new JFrame("Card Matching Game"); // creates the game window
      
        board = new GameBoard(rows, cols); // initializes the game board
        GameController.registerWindow(frame); 
        loadBestTimes(); // load the best times from the file basically
        
        // initialize and configure the score label
        scoreLabel = new JLabel("Score: " + score, SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 24));//sets the font and size
        scoreLabel.setForeground(Color.BLACK);//sets color to white
        frame.add(board, BorderLayout.CENTER); // add the board to the frame center
        frame.add(scoreLabel, BorderLayout.NORTH); // add score label at the top

        // initialize and configure the move label
        moveLabel = new JLabel("Moves: 0", SwingConstants.CENTER);//position of label and what it says
        moveLabel.setFont(new Font("Arial", Font.BOLD, 24));//font and size
        moveLabel.setForeground(Color.BLACK);//color of it
        frame.add(moveLabel, BorderLayout.SOUTH); // add move label at the bottom

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); //set the frame to fullscreen
        frame.setUndecorated(true); // remove window decorations
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//set default close operation
        frame.setVisible(true); // make the frame visible

        // record the start time of the game
        startTime = System.currentTimeMillis();
        addCardListeners(); // add event listeners for the cards
    }

    // setter to update the move limit
    public void setMoveLimit(int moveLimit) {
        this.moveLimit = moveLimit;//setting it equal to the move limit
    }
    
    // method to add action listeners to each card
    private void addCardListeners() {
        for (Card card : board.getCards()) { // iterate through all cards on the board
            card.getButton().addActionListener(new CardFlipListener(card)); // add flip listener
        }
    }
    
    // method to start a countdown timer
    private void startTimer(int seconds) {
    this.timeLimit = seconds; // set the total time limit
    this.timeRemaining = seconds; // initialize remaining time
    activeTimers.remove(gtimer); 
    stopTime();
    // create a timer to decrease remaining time every second
    gtimer = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) { 
            timeRemaining--; // decrease remaining time
            if (timeRemaining <= 0) { // check if time is up
                GameController.closeAllWindows();
                stopTime();
                moveLimit = 15;
                //timer.stop(); // stop the timer
                showGameOverScreen(-1); // show game over screen with time-out indicator
            }
        }
    });
    gtimer.start(); // start the timer
    activeTimers.add(gtimer);
}
    
void stopTime() {
    if (gtimer != null) { // check if the timer exists
        gtimer.stop(); // stop the timer
        gtimer = null; // nullify the reference to avoid re-use
    }
    activeTimers.remove(gtimer); // remove the timer from the list of active timers
   
}

public static void registerWindow(JFrame frame) {
    openWindows.add(frame); // add the given frame to the list of open windows
}

public static void gregisterWindow(JFrame frame) {
    GameoverWindows.add(frame); // add the given frame to the list of game over windows
}

// Method to close all open windows
public static void closeAllWindows() {
    for (JFrame frame : openWindows) { // loop through each frame in the open windows list
        if (frame != null) { // check if the frame is not null
            frame.dispose(); // close the JFrame
        }
    }
    openWindows.clear(); // clear the list of open windows
}

public static void gcloseAllWindows() {
    for (JFrame frame : GameoverWindows) { // loop through each frame in the game over windows list
        if (frame != null) { // check if the frame is not null
            frame.dispose(); // close the JFrame
        }
    }
    GameoverWindows.clear(); // clear the list of game over windows
}  
    

    
//method to see fastest time
private String readFastestTime(String fileName) {
    //reading through the file
    try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
        String line = br.readLine();//reads the first line from file basically
        if (line != null) {//checks if line is not null
            return line;  // Return the fastest time recorded
        }
    } catch (IOException e) {// handle exceptions during file reading
        e.printStackTrace();// print the stack trace for debugging
    }
    return "No record";  // default message if no time is recorded at all is what its doing
}
//method to fave the fastest time
private void saveFastestTime(long time) {
    //the file name as a string
    String fileName = "fastestTimeLevel" + unlockedLevel + ".txt";
    String currentBestTime = readFastestTime(fileName);//reads current best time

    //parse the current best time if it's not "No record"
    long currentBestTimeInSeconds = currentBestTime.equals("No record") ? Long.MAX_VALUE : parseTime(currentBestTime);

    // If the new time is better then show the new fastest time
    if (time < currentBestTimeInSeconds) {
        //reading through file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("Fastest Time: " + time + " seconds");// print in this format if true
        } catch (IOException e) {// handle exceptions during file reading
            e.printStackTrace();// print stack trace in case of an error
        }
    }
}


//method to parse time from the format "Fastest Time: X seconds"
private long parseTime(String record) {
    String[] parts = record.split(": ");//split the string by the colon
    if (parts.length == 2) {//if the length of the array is 2, 
        //then return by replacing it in the following format after taking out seconds
        return Long.parseLong(parts[1].replace(" seconds", ""));
    }
    return Long.MAX_VALUE;  // return a large value if parsing fails
}
    //class to listen for card flip events basically
    private class CardFlipListener implements ActionListener {
    private Card card;
    //this is just the constructor to initialize the card
    public CardFlipListener(Card card) {
        this.card = card;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // prevent flipping of already matched cards or the same card twice
        if (card.isMatched() || card == firstSelectedCard) return;

        card.flip();  // flip the card
        if (firstSelectedCard == null) {
            //first card selected
            firstSelectedCard = card;
            moveCounter++;//add on to the move counter
            moveLabel.setText("Moves: " + moveCounter);//show moves

            // this just checks if move limit is exceeded
            if (moveLimit > 0 && moveCounter > moveLimit) {
                stopTime();//stop timer
                GameController.closeAllWindows();//close all windows
                showGameOverScreen(-1);  // exceeded move limit
                //return;//then exits the method
            }
        } else {
            // second card selected
            if (firstSelectedCard.getId().equals(card.getId())) {
                // If cards match
                firstSelectedCard.setMatched(true);
                card.setMatched(true);
                score++;//add to score
                scoreLabel.setText("Score: " + score);
                firstSelectedCard = null;  // reset after match

                // check if the game is won
                if (checkIfGameWon()) {
                    long elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
                    saveFastestTime(elapsedTime);//save fastest times
                    showGameOverScreen(elapsedTime);//show game over screen in relation to elapsed time
                }
            } else {
                // If cards don't match, start timers to flip them back
                startFlipBackTimer(card);
                startFlipBackTimer(firstSelectedCard);
                firstSelectedCard = null;  // reset after non-matching pair
            }
        }
    }

    private void startFlipBackTimer(Card card) {
        // Create a timer to flip the card back after a delay
        Timer timer = new Timer(1000, evt -> {
            card.flip();  // Flip card back
            card.getButton().setIcon(new ImageIcon("images/Cardback.jpg"));
            card.getButton().revalidate();  // Revalidate the button (to refresh UI)
            card.getButton().repaint();     // Repaint the button (to refresh UI)
        });
        timer.setRepeats(false);  // Ensure it only runs once
        timer.start();//start timer
    }
}



  

private void loadBestTimes() {
    try (BufferedReader reader = new BufferedReader(new FileReader("bestTimes.txt"))) {
        // Read the best times for each level, but only update if they are not Long.MAX_VALUE
        bestTimeLevel1 = Long.parseLong(reader.readLine());//read for first
        bestTimeLevel2 = Long.parseLong(reader.readLine());//read for second
        bestTimeLevel3 = Long.parseLong(reader.readLine());//read for third
    } catch (IOException | NumberFormatException e) {//handle error exceptions
        // If the file doesn't exist or there was an issue reading it, leave the times as Long.MAX_VALUE
        // these times will remain untouched until the player completes levels as well
    }
}

// save best times to the file
private void saveBestTimes() {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("bestTimes.txt"))) {
        writer.write(Long.toString(bestTimeLevel1)); // write the best time for level 1
        writer.newLine(); // add a new line after the first time
        writer.write(Long.toString(bestTimeLevel2)); // write the best time for level 2
        writer.newLine(); // add a new line after the second time
        writer.write(Long.toString(bestTimeLevel3)); // write the best time for level 3
    } catch (IOException e) {
        e.printStackTrace(); // print stack trace in case of an error
    }
}

// Reset best times to Long.MAX_VALUE
private void resetLeaderboard() {
    bestTimeLevel1 = Long.MAX_VALUE;//reset level 1 time as max val
    bestTimeLevel2 = Long.MAX_VALUE;//reset level 2 time as max val
    bestTimeLevel3 = Long.MAX_VALUE;//reset level 3 time as max val
    saveBestTimes();  // save the reset times to the file
}

// display the game over screen with the elapsed time
private void showGameOverScreen(long elapsedTime) {
    stopTime();//stop the timer
    board.stopTimer();//stop the gameboard timer from the other class
      
    // create a new frame for the game over screen
    JFrame gameOverFrame = new JFrame("Game Over");
    GameController.gregisterWindow(gameOverFrame); 
    // set the frame to maximize to full screen
    gameOverFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    // remove window borders for a cleaner look
    gameOverFrame.setUndecorated(true);
    // prevent the frame from being closed
    gameOverFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // use a border layout for the frame
    gameOverFrame.setLayout(new BorderLayout());

    // create a background label with the game over image
    JLabel background = new JLabel(new ImageIcon("images/gameover.jpg"));
    // add the background to the frame
    gameOverFrame.add(background, BorderLayout.CENTER);
    // set the layout of the background to border layout
    background.setLayout(new BorderLayout());

    // create a panel to display messages
    JPanel messagePanel = new JPanel();
    // make the panel transparent
    messagePanel.setOpaque(false);
    // set the layout of the panel to a vertical box layout
    messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));

    // initialize message strings
    String message = "";
    String timeMessage = "";

    // check if the player is on level 1 with unlimited moves
    if (moveLimit == -1) {
        // set a congratulatory message for completing the level
        message = "Congratulations! You've matched all cards!";
        // display the time taken to complete the level
        timeMessage = "Time Taken: " + elapsedTime + " seconds";
        // check if the elapsed time is better than the best time for level 1
        if (elapsedTime < bestTimeLevel1) {
            // update the best time for level 1
            bestTimeLevel1 = elapsedTime;
            // save the updated best times
            saveBestTimes();
        }
        // unlock the next level
        unlockNextLevel();
        // reset the move limit
        this.moveLimit = 0;
        // reset the move counter
        this.moveCounter = 0;
    } 
    // check if the player is on level 2 with a 20-move limit
    else if (moveLimit == 20) {
        // check if the player completed the level within the move limit
        if (moveCounter <= 20) {
            // set a congratulatory message for completing the level
            message = "Congratulations! You've matched all cards!";
            // display the moves and time taken to complete the level
            timeMessage = "Moves Taken: " + moveCounter + " Time Taken: " + elapsedTime + " seconds";
            // check if the elapsed time is better than the best time for level 2
            if (elapsedTime < bestTimeLevel2) {
                // update the best time for level 2
                bestTimeLevel2 = elapsedTime;
                // save the updated best times
                saveBestTimes();
            }
            // unlock the next level
            unlockNextLevel();
            // reset the move limit
            this.moveLimit = 0;
            // reset the move counter
            this.moveCounter = 0;
        } else {
            // set a game over message for exceeding the move limit
            message = "Game Over! You exceeded the move limit.";
            // display the moves taken
            timeMessage = "Moves Taken: " + moveCounter;
            // set the unlocked level to 2
            unlockedLevel = 2;
            // reset the move limit
            this.moveLimit = 0;
            // reset the move counter
            this.moveCounter = 0;
        }
    } 
    // check if the player is on level 3 with a 15-move and 30-second limit
    else if (moveLimit == 15) {
        stopTime();
        // check if the time remaining has expired
        if (moveCounter > 15) {
            // set a message indicating moves exceeded the limit
            message = "Better luck next time! Moves exceeded the limit!";
            
            // set the unlocked level to 3
            unlockedLevel = 3;
            
            
        } else if (elapsedTime > 0 && elapsedTime <30) {
            // check if the player completed the level within the limits
            if (moveCounter <= 15) {
                // set a congratulatory message for completing the level
                message = "Congratulations! You've matched all cards!";
                // display the time taken to complete the level
                timeMessage = "Time Taken: " + elapsedTime + " seconds";
                // check if the elapsed time is better than the best time for level 3
                if (elapsedTime < bestTimeLevel3) {
                    // update the best time for level 3
                    bestTimeLevel3 = elapsedTime;
                    // save the updated best times
                    saveBestTimes();
                }
                // set the unlocked level to 3
                unlockedLevel = 3;
            }
        }
        else {
            message = "Better luck next time! Time exceeded the limit!";
            // set the unlocked level to 3
            unlockedLevel = 3;
        }
    }

    // create a jlabel to display the message, centered horizontally
JLabel messageLabel = new JLabel(message, SwingConstants.CENTER);
// set the font for the message label (impact, bold, size 30)
messageLabel.setFont(new Font("Impact", Font.BOLD, 30));
// set the text color for the message label to white
messageLabel.setForeground(Color.WHITE);

// create a jlabel to display the time-related message, centered horizontally
JLabel timeLabel = new JLabel(timeMessage, SwingConstants.CENTER);
// set the font for the time label (arial, plain, size 24)
timeLabel.setFont(new Font("Arial", Font.PLAIN, 24));
// set the text color for the time label to white
timeLabel.setForeground(Color.WHITE);

// add the message label to the message panel
messagePanel.add(messageLabel);
// add the time label to the message panel
messagePanel.add(timeLabel);

// add the message panel to the center of the background
background.add(messagePanel, BorderLayout.CENTER);

// create a new panel for buttons
JPanel buttonPanel = new JPanel();
// this just makes the button panel transparent
buttonPanel.setOpaque(false);
// set the layout of the button panel to flow layout, centered alignment
buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

// create a "play again" button now so user can retry for faster time basically
JButton playAgainButton = new JButton("Play Again");
// add an action listener to handle clicks on the "play again" button
playAgainButton.addActionListener(e -> {
    stopTime();//stop the timer
    this.moveCounter=0;//set the move counter to 0
    GameController.closeAllWindows();//close all the windows 
    

    
    // create an array of level options, size depends on unlocked levels
    Object[] levelOptions = new Object[unlockedLevel];
    // populate the level options array based on unlocked levels
    for (int i = 0; i < unlockedLevel; i++) {
        if (i == 0) {
            levelOptions[i] = "Level 1: Unlimited Moves"; // option for level 1
        } else if (i == 1) {
            levelOptions[i] = "Level 2: 20 Moves"; // option for level 2
        } else if (i == 2) {
            levelOptions[i] = "Level 3: 15 Moves & 30 Seconds"; // option for level 3
            
        }
    }

    // show a dialog to let the user select a level
    int choice = JOptionPane.showOptionDialog(null, "Select Level:",
            "Choose Level", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
            null, levelOptions, levelOptions[0]);

   
   
    // launch the selected level based on the user's choice
    if (choice == 0) {
        new GameController(4, 4); // start level 1
    } else if (choice == 1) {//if level 2 is chosen then start level 2
        
        GameController gameController = new GameController(4, 4);//start it
        gameController.setMoveLimit(20); // set move limit for level 2 of 20 moves 
    } else if (choice == 2) {//if level 3 is chosen then start it
        stopTime();//stop the timer
        GameController gameController = new GameController(4, 4);//start it
        gameController.setMoveLimit(15); // set move limit for level 3
        startTimer(30);// start a timer with 30 seconds for level 3
        
    }
    //this is so the game doesnt close when user presses the x on the dialog box
    if (choice!= -1){
    GameController.gcloseAllWindows();//close all the windows
   }
});

    // create a button for viewing the leaderboard
JButton leaderboardButton = new JButton("Leaderboard");
// add an action listener to show the leaderboard when clicked
leaderboardButton.addActionListener(e -> showLeaderboard());

// create a button for resetting the leaderboard
JButton resetLeaderboardButton = new JButton("Reset Leaderboard");
// add an action listener to reset the leaderboard and show a confirmation message
resetLeaderboardButton.addActionListener(e -> {
    resetLeaderboard();  // reset the leaderboard
    JOptionPane.showMessageDialog(gameOverFrame, "Leaderboard reset successful!");
});

// create a button for exiting the game
JButton exitButton = new JButton("Exit");
// add an action listener to display a goodbye message and exit the game
exitButton.addActionListener(e -> {
    JOptionPane.showMessageDialog(gameOverFrame, "Hope you had fun playing Animal-Match! See you next time! :)");
    System.exit(0);  // exit the game
});

// add the play again button to the button panel
buttonPanel.add(playAgainButton);
// add the leaderboard button to the button panel
buttonPanel.add(leaderboardButton);
// add the reset leaderboard button to the button panel
buttonPanel.add(resetLeaderboardButton);
// add the exit button to the button panel
buttonPanel.add(exitButton);

// add the button panel to the bottom of the background
background.add(buttonPanel, BorderLayout.SOUTH);

// make the game over frame visible
gameOverFrame.setVisible(true);
}

// display the leaderboard
private void showLeaderboard() {
    // create an html-formatted string for the leaderboard message
    String leaderboardMessage = "<html><h1>Leaderboard</h1>";

    // add the best time for level 1 to the leaderboard message
    leaderboardMessage += "<p>Level 1: Unlimited Moves - Best Time: " + getBestTimeForLevel(1) + "</p>";
    // add the best time for level 2 to the leaderboard message
    leaderboardMessage += "<p>Level 2: 20 Moves - Best Time: " + getBestTimeForLevel(2) + "</p>";
    // add the best time for level 3 to the leaderboard message
    leaderboardMessage += "<p>Level 3: 15 Moves & 30 Seconds - Best Time: " + getBestTimeForLevel(3) + "</p>";

    // close the html tag
    leaderboardMessage += "</html>";

    // create a jlabel with the leaderboard message
    JLabel leaderboardLabel = new JLabel(leaderboardMessage);
    // display the leaderboard message in a dialog
    JOptionPane.showMessageDialog(null, leaderboardLabel);
}

// retrieve the best time for each level
private String getBestTimeForLevel(int level) {
    // check the level and return the corresponding best time or a default message
    switch (level) {
        case 1:
            //check level 1
            return bestTimeLevel1 == Long.MAX_VALUE ? "Not Completed" : bestTimeLevel1 + " seconds";
        case 2:
            //check level 2
            return bestTimeLevel2 == Long.MAX_VALUE ? "Not Completed" : bestTimeLevel2 + " seconds";
        case 3:
            //check level 3 now 
            return bestTimeLevel3 == Long.MAX_VALUE ? "Not Completed" : bestTimeLevel3 + " seconds";
        default:
            return "Invalid level";  // handle invalid level inputs
    }
}

// method to unlock the next level
private void unlockNextLevel() {
    // unlock level 2 if the move limit is unlimited and level 2 is not yet unlocked
    if (moveLimit == -1 && unlockedLevel < 2) {
        unlockedLevel = 2;  // unlock level 2
    // unlock level 3 if the move limit is 20 and level 3 is not yet unlocked
    } else if (moveLimit == 20 && unlockedLevel < 3) {
        unlockedLevel = 3;  // unlock level 3
    }
}

// method to get the available level options based on unlocked levels
private String[] getLevelOptions() {
    // return options for all three levels if all levels are unlocked
    if (unlockedLevel == 3) {
        return new String[]{"Level 1: Unlimited Moves", "Level 2: 20 Moves", "Level 3: 15 Moves and 30 Seconds"};
    // return options for the first two levels if only two levels are unlocked
    } else if (unlockedLevel == 2) {
        return new String[]{"Level 1: Unlimited Moves", "Level 2: 20 Moves"};
    // return the option for level 1 if no other levels are unlocked
    } else {
        return new String[]{"Level 1: Unlimited Moves"};
    }
}

// method to check if the game is won
private boolean checkIfGameWon() {
    // return true if all cards on the board are matched
    return board.getCards().stream().allMatch(Card::isMatched);
}
}
