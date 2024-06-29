/**
 * Manages and stores high score 
 *
 * Original concept by Robert Donner and Curt Johnson, 1989.
 * @author Julius Gauldie
 * @version 29/06/24
 */
import java.io.*;
import java.nio.file.*;

public class HighScoreManager {
    // Names of text files
    private static final String EASY_HIGH_SCORE_FILE = "easy_highscore.txt";
    private static final String MEDIUM_HIGH_SCORE_FILE = "medium_highscore.txt";
    private static final String HARD_HIGH_SCORE_FILE = "hard_highscore.txt";

    /**
     * Retrieves the high score for the specified difficulty.
     * 
     * @param difficulty The difficulty level ("easy", "medium", "hard").
     * @return The high score for the specified difficulty, or Integer.MAX_VALUE if no previous score is found.
     */
    public static int getHighScore(String difficulty) {
        int highScore = Integer.MAX_VALUE; // Initialize with a high value
        String highScoreFile = getHighScoreFile(difficulty);

        try {
            if (Files.exists(Paths.get(highScoreFile))) {
                BufferedReader reader = new BufferedReader(new FileReader(highScoreFile));
                String line = reader.readLine();
                if (line != null) {
                    highScore = Integer.parseInt(line);
                }
                reader.close();
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return highScore;
    }

    /**
     * Saves the new high score for the specified difficulty.
     * 
     * @param difficulty The difficulty level ("easy", "medium", "hard").
     * @param newHighScore The new high score to be saved.
     */
    public static void saveHighScore(String difficulty, int newHighScore) {
        String highScoreFile = getHighScoreFile(difficulty);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(highScoreFile))) {
            writer.write(String.valueOf(newHighScore));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Returns the file for the high score file for a certain difficulty.
     * 
     * @param difficulty The difficulty level ("easy", "medium", "hard").
     * @return The file path for the high score file.
     * @throws IllegalArgumentException if the difficulty is unsupported.
     */
    private static String getHighScoreFile(String difficulty) {
        switch (difficulty.toLowerCase()) {
            case "easy":
                return EASY_HIGH_SCORE_FILE;
            case "medium":
                return MEDIUM_HIGH_SCORE_FILE;
            case "hard":
                return HARD_HIGH_SCORE_FILE;
            default:
                throw new IllegalArgumentException("Unsupported difficulty: " + difficulty);
        }
    }
}


