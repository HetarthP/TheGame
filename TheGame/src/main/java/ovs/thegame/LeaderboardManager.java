//Name: Hetarth Parikh
//Unit,Lesson,Name of Assignment: Unit 4 Lesson 3, Implementation- The Game
//Date of Completion: November 23rd 2024
//Description:This is a game called Animal-Match where the user plays and has fun across 
//various levels matching and getting the fastest time across different levels in a unique
//animal themed memory game! 
//This class is responsible for managing the leaderboard of fastest times across the levels.
//It has methods to update the fastest time, retrieve and display the current fastest times, 
//and as well as reset the leaderboard. The class interacts basically with files to store
//and also read the fastest times for each level and keeps it in the leaderboard to show
//the player or user.



package ovs.thegame; // declares the package name as 'ovs.thegame'

import javax.swing.*; // imports the javax.swing package for GUI components
import java.io.*; // imports the java.io package for file handling

public class LeaderboardManager { // defines the LeaderboardManager class
    private static final String LEVEL1_FILE = "fastestTimeLevel1.txt"; // file path for Level 1 fastest time
    private static final String LEVEL2_FILE = "fastestTimeLevel2.txt"; // file path for Level 2 fastest time
    private static final String LEVEL3_FILE = "fastestTimeLevel3.txt"; // file path for Level 3 fastest time

    // method to update the fastest time for a level
    public void updateFastestTime(long elapsedTime, String filename) { // takes elapsed time and filename as parameters
        long currentFastestTime = getFastestTime(filename); // gets the current fastest time from the file
        if (currentFastestTime == -1 || elapsedTime < currentFastestTime) { // checks if there is no record or if the new time is faster
            try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) { // creates a PrintWriter to write to the file
                writer.println(elapsedTime); // writes the new fastest time to the file
            } catch (IOException e) { // catches any IOExceptions
                e.printStackTrace(); // prints the stack trace for debugging purposes
            }
        }
    }

    // method to get the fastest time from a level file
    private long getFastestTime(String filename) { // takes the filename as a parameter
        File file = new File(filename); // creates a File object for the specified file
        if (!file.exists()) { // checks if the file does not exist
            try (PrintWriter writer = new PrintWriter(new FileWriter(file))) { // creates a PrintWriter to create the file
                writer.println("-1"); // writes "-1" to indicate no record exists
            } catch (IOException e) { // catches any IOExceptions
                e.printStackTrace(); // prints the stack trace for debugging purposes
            }
            return -1; // returns -1 if no record exists
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) { // creates a BufferedReader to read the file
            String line = reader.readLine(); // reads the first line of the file
            return (line != null && !line.equals("-1")) ? Long.parseLong(line) : -1; // returns the parsed time if valid, otherwise -1
        } catch (IOException | NumberFormatException e) { // catches IOExceptions or NumberFormatExceptions
            return -1; // returns -1 in case of an error
        }
    }

    // method to show the leaderboard with fastest times for each level
    public void showLeaderboard() { // no parameters required
        String level1Time = readFastestTime(LEVEL1_FILE); // reads the fastest time for Level 1
        String level2Time = readFastestTime(LEVEL2_FILE); // reads the fastest time for Level 2
        String level3Time = readFastestTime(LEVEL3_FILE); // reads the fastest time for Level 3

        // builds the leaderboard display content
        String leaderboard = "<html>Leaderboard:<br>" // starts the leaderboard HTML content
                             + "Level 1: " + (level1Time.equals("-1") ? "No record" : level1Time) + "<br>" // adds Level 1 time or "No record"
                             + "Level 2: " + (level2Time.equals("-1") ? "No record" : level2Time) + "<br>" // adds Level 2 time or "No record"
                             + "Level 3: " + (level3Time.equals("-1") ? "No record" : level3Time) + "<br></html>"; // adds Level 3 time or "No record"
        
        JOptionPane.showMessageDialog(null, leaderboard, "Leaderboard", JOptionPane.INFORMATION_MESSAGE); // shows the leaderboard in a dialog box
    }

    // method to read the fastest time from a file
    String readFastestTime(String filename) { // takes the filename as a parameter
        File file = new File(filename); // creates a File object for the specified file
        if (!file.exists()) { // checks if the file does not exist
            return "No record"; // returns "No record" if the file doesn't exist
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) { // creates a BufferedReader to read the file
            String time = reader.readLine(); // reads the first line of the file
            return (time != null && !time.equals("-1")) ? time : "No record"; // returns the time or "No record" if invalid
        } catch (IOException e) { // catches any IOExceptions
            return "Error reading file"; // returns an error message if an exception occurs
        }
    }

    // method to reset the leaderboard
    public void resetLeaderboard() { // no parameters required
        resetFile(LEVEL1_FILE); // resets the Level 1 file
        resetFile(LEVEL2_FILE); // resets the Level 2 file
        resetFile(LEVEL3_FILE); // resets the Level 3 file
    }

    // helper method to reset a file
    private void resetFile(String filename) { // takes the filename as a parameter
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) { // creates a PrintWriter to write to the file
            writer.println("-1"); // writes "-1" to indicate no record exists
        } catch (IOException e) { // catches any IOExceptions
            e.printStackTrace(); // prints the stack trace for debugging purposes
        }
    }

    // main method for testing the LeaderboardManager class
    public static void main(String[] args) { // main entry point of the program
        LeaderboardManager leaderboardManager = new LeaderboardManager(); // creates an instance of LeaderboardManager

        // example usage of updating the fastest times for each level
        leaderboardManager.updateFastestTime(5000, LEVEL1_FILE); // updates level 1 with a time of 5000ms
        leaderboardManager.updateFastestTime(6000, LEVEL2_FILE); // updates level 2 with a time of 6000ms
        leaderboardManager.updateFastestTime(7000, LEVEL3_FILE); // updates level 3 with a time of 7000ms

        leaderboardManager.showLeaderboard(); // displays the leaderboard
    }

    void saveFastestTime(long elapsedTime) { // placeholder method to save the fastest time
        throw new UnsupportedOperationException("Not supported yet."); // throws an exception indicating method not implemented
    }
}
