
//Description:This is a game called Animal-Match where the user plays and has fun across 
//various levels matching and getting the fastest time across different levels in a unique
//animal themed memory game! 
//This class is responsible for managing high scores in the game. Although scores aren't
//as valuable in this game, it's used to show the user how many matches have been made
//as they continue on to play the actual level. It has methods to append new scores to a text file
//and show all the high scores. The scores are read from a file, sorted in descending order,
//and then printed to the console while handling any IO errors as well. 
//Overall, not much happening here, but shows how seperate sorting algorithms work together
//when making a game. 




package ovs.thegame; // declares the package name as 'ovs.thegame'

import java.io.FileWriter; // imports FileWriter for writing to files
import java.io.IOException; // imports IOException for handling IO exceptions
import java.io.File; // imports File class for file handling
import java.util.ArrayList; // imports ArrayList for storing a list of scores
import java.util.List; // imports List interface for list operations
import java.util.Scanner; // imports Scanner for reading from files

public class ScoreManager { // defines the ScoreManager class
    private static final String FILE_NAME = "highscores.txt"; // file path for storing high scores

    // method to append a new score to the high scores file
    public static void appendScore(int score) { // takes score as a parameter
        try (FileWriter writer = new FileWriter(FILE_NAME, true)) { // creates a FileWriter in append mode
            writer.write("Score: " + score + "\n"); // writes the score to the file
        } catch (IOException e) { // catches any IOExceptions
            System.out.println("An error occurred while saving the score: " + e.getMessage()); // prints an error message if an exception occurs
        }
    }

    // method to display all high scores from the file
    public static void displayHighScores() { // no parameters required
        File file = new File(FILE_NAME); // creates a File object for the high scores file
        List<String> scores = new ArrayList<>(); // creates a list to store scores

        if (file.exists()) { // checks if the file exists
            try (Scanner scanner = new Scanner(file)) { // creates a Scanner to read the file
                while (scanner.hasNextLine()) { // loops while there are lines to read
                    scores.add(scanner.nextLine()); // adds each line to the scores list
                }
                // optional: sort scores or limit display
                scores.sort((s1, s2) -> Integer.compare( // sorts scores in descending order
                        Integer.parseInt(s2.split(": ")[1]), // gets the score value from the string in descending order
                        Integer.parseInt(s1.split(": ")[1]) // gets the score value from the string in ascending order
                ));
                scores.forEach(System.out::println); // prints each score to the console
            } catch (IOException e) { // catches any IOExceptions
                System.out.println("An error occurred while reading high scores: " + e.getMessage()); // prints an error message if an exception occurs
            }
        } else { // if the file does not exist
            System.out.println("No high scores recorded yet."); // prints a message indicating no high scores exist
        }
    }
}
