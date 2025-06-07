
//Description:This is a game called Animal-Match where the user plays and has fun across 
//various levels matching and getting the fastest time across different levels in a unique
//animal themed memory game! This class here initializes the card being used and the
//other methods and mechanisms being used such as the flipping etc.






package ovs.thegame; // package declaration for organizing classes

import javax.swing.*; // imports all javax.swing classes for GUI components

public class Card { // class definition for Card
    private String id; //just an identifier for matching cards
    private ImageIcon faceImage; // image icon for the front face of the card
    private JButton button; // button to represent the card in the GUI
    private boolean isMatched = false; // flag to indicate if the card is matched
    private boolean flipped = false; // flag to indicate if the card is flipped
    public static final ImageIcon cardBackImageIcon = new ImageIcon("images/Cardback.jpg"); // static icon for card back image

    public Card(String id, ImageIcon faceImage) { // constructor with id and face image
        this.id = id; // assigns id to the card
        this.faceImage = faceImage; // assigns face image to the card
        this.button = new JButton(); // creates a button for the card
        this.button.setIcon(cardBackImageIcon); // sets button icon to the card back image
    }

    public String getId() { // getter for id
        return id; // returns the id of the card
    }

    public ImageIcon getFaceImage() { // getter for face image
        return faceImage; // returns the face image of the card
    }

    public JButton getButton() { // getter for button
        return button; // returns the button representing the card basically
    }

    public boolean isFlipped() { // checks if the card is flipped
        return flipped; // returns flipped status
    }

    public void setFlipped(boolean flipped) { // sets flipped status
        this.flipped = flipped; // assigns flipped to the flipped variable
    }

    public void flip() { // flips the card
        if (isMatched) return; // exits method if the card is already matched
        if (isFlipped()) { // checks if the card is currently flipped
            button.setIcon(cardBackImageIcon); // shows the card back image
            setFlipped(false); // sets flipped status to false
        } else { // if the card is not currently flipped
            button.setIcon(faceImage); // shows the face image
            setFlipped(true); // sets flipped status to true basically
        }
    }

    public void unflip() { // unflips the card if not matched
        if (!isMatched) { // checks if the card is not matched
            button.setIcon(cardBackImageIcon); // shows the card back image again
            flipped = false; // sets flipped status to false
        }
    }

    public void setMatched(boolean matched) { // sets matched status of the card
        isMatched = matched; // assigns matched to isMatched
        if (matched) { // if the card is matched and checks if so 
            button.setEnabled(false); // disables the button if matched
        }
    }

    public boolean isMatched() { // checks if the card is matched
        return isMatched; // then returns matched status
    }
} 
