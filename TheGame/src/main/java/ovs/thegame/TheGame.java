////Name: Hetarth Parikh
//Unit,Lesson,Name of Assignment: Unit 4 Lesson 3, Implementation- The Game
//Date of Completion: November 23rd 2024
//Description:This is a game called Animal-Match where the user plays and has fun across 
//various levels matching and getting the fastest time across different levels in a unique
//animal themed memory game! This class is the main class which allows the game to start





package ovs.thegame;
import javax.swing.SwingUtilities;//allows to use java's swing utilities basically

//main class and method here
public class TheGame {
    public static void main(String[] args) {
        
        //new GameController(4, 4); // Start game with 4x4 grid
        SwingUtilities.invokeLater(SplashScreen::showSplash);//start by showing splash screen
    }
}
