
//Description:This is a game called Animal-Match where the user plays and has fun across 
//various levels matching and getting the fastest time across different levels in a unique
//animal themed memory game! 
//This is the GameBoard class where this initializes the whole game board that the
//user is actually playing on. This class has the methods for the flipping mechanism 
//of the actual cards, the cards flipping back within a few seconds etc. The grids and panel
//sizes are all handled here. 




package ovs.thegame; // package declaration for organizing classes

import javax.swing.*; // imports all javax.swing classes for GUI components
import java.awt.*; // imports java.awt classes for layout management
import java.util.ArrayList; // imports ArrayList class
import java.util.Collections; // imports Collections class for shuffling

public class GameBoard extends JPanel { // class definition for GameBoard
    private ArrayList<Card> cards; // list of cards in the game
    private int rows; // number of rows in the game grid
    private int cols; // number of columns in the game grid
    private int score = 0; // variable to keep track of the score
    private Card firstCard = null; // variable to track the first flipped card
    private JLabel timeLabel; // label to display time elapsed
    private Timer btimer; // timer to update elapsed time
    private int belapsedTime = 0; // counter for elapsed time in seconds

    public GameBoard(int rows, int cols) { // constructor with row and column parameters
        this.rows = rows; // assigns rows to the instance variable
        this.cols = cols; // assigns cols to the instance variable
        this.cards = new ArrayList<>(); // initializes the cards list

        setLayout(new BorderLayout()); // sets BorderLayout for top label and grid

        initializeTimeLabel(); // adds time label at the top
        initializeCards(); // initializes and shuffles cards
        addCardsToBoard(); // adds cards to the board

        startBannerTimer(); // starts the elapsed time timer
    }

    private void initializeTimeLabel() { // method to initialize time label
        timeLabel = new JLabel("Time Elapsed: 0 seconds", SwingConstants.CENTER); // creates a label with centered text
        timeLabel.setFont(new Font("Arial", Font.BOLD, 16)); // sets font style and size
        timeLabel.setOpaque(true); // makes the label background visible
        timeLabel.setBackground(Color.BLACK); // sets background color to black
        timeLabel.setForeground(Color.WHITE); // sets text color to white

        add(timeLabel, BorderLayout.NORTH); // adds timeLabel to the top
    }

    private void startBannerTimer() { // method to start the timer
        btimer = new Timer(1000, e -> { // creates a timer with a 1-second delay
            belapsedTime++; // increments the elapsed time
            timeLabel.setText("Time Elapsed: " + belapsedTime + " seconds"); // updates the label with the elapsed time
        });
        btimer.start(); // starts the timer
    }

    private void initializeCards() { // method to initialize cards
        String[] imageFiles = {"dog.jpg", "cat.jpg", "wolf.jpg", "tiger.jpg",
                "shark.jpg", "trex.jpg", "lion.jpg", "alligator.jpg"}; // array of image filenames

        for (String imageFile : imageFiles) { // iterates over image files
            ImageIcon faceImage = new ImageIcon("images/" + imageFile); // creates an icon from the image file
            String id = imageFile; // uses the image filename as the card id
            cards.add(new Card(id, faceImage)); // adds the first instance of the card
            cards.add(new Card(id, faceImage)); // adds a duplicate card for matching
        }

        Collections.shuffle(cards); // shuffles the cards list
    }

    private void addCardsToBoard() { // method to add cards to the board
        JPanel gridPanel = new JPanel(new GridLayout(rows, cols)); // creates a grid panel
        for (Card card : cards) { // iterates over each card in the cards list
            card.getButton().addActionListener(e -> cardClicked(card)); // adds a click listener to each card
            gridPanel.add(card.getButton()); // adds the card button to the grid
        }
        add(gridPanel, BorderLayout.CENTER); // adds the grid panel to the center
    }

    private void cardClicked(Card card) { // method to handle card click event
        if (!card.isFlipped() && firstCard == null) { // checks if the first card is not flipped and no card is set as first
            card.flip(); // flips the first card
            firstCard = card; // sets the first flipped card
        } else if (!card.isFlipped() && firstCard != null) { // checks if the second card is not flipped and the first card is set
            card.flip(); // flips the second card
            if (firstCard.getId().equals(card.getId())) { // checks if the two cards match
                firstCard.setMatched(true); // marks the first card as matched
                card.setMatched(true); // marks the second card as matched
                score++; // increments the score
                updateScoreDisplay(); // updates the score display
                firstCard = null; // resets the first card for the next turn
            } else { // handles the case where cards do not match
                Timer timer = new Timer(1000, evt -> { // creates a timer with a delay
                    firstCard.unflip(); // unflips the first card
                    card.unflip(); // unflips the second card
                    firstCard = null; // resets the first card for the next turn
                });
                timer.setRepeats(false); // sets the timer to not repeat
                timer.start(); // starts the timer
            }
        }
    }

    private void updateScoreDisplay() { // method to update score display
       
    }

    public ArrayList<Card> getCards() { // getter for cards list
        return cards; // returns the list of cards
    }

    public void stopTimer() { // method to stop the timer
        if (btimer != null) { // checks if the timer is not null
            btimer.stop(); // stops the timer
            btimer=null;//set to null
        }
    }
}
