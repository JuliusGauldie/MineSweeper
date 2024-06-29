
/**
 * Logic for Info Board - Difficulty, Timer, Flags Left, New Game
 *
 * Original concept by Robert Donner and Curt Johnson, 1989.
 * @author Julius Gauldie
 * @version 29/06/24
 */
import java.awt.event.*;
import java.awt.*; 
import javax.swing.*;
public class InfoBoardPanel extends JPanel {
    private MainBoardPanel mainPanel;
    private MineMap mineMap;
    private JButton newGameButton = new JButton();
    
    // Flag Count
    public int amountOfFlags = 0;
    private JLabel flagCount = new JLabel();
    
    // Timer
    private JLabel timerLabel = new JLabel("Time: 0");
    private Timer timer;
    public int secondsElapsed;  

    // High Score
    HighScoreManager score = new HighScoreManager();
    
    // Difficulty dropdown menu
    private JComboBox<String> difficultyComboBox;
    
    // Difficulty Settings
    public static final int EASY_ROWS = 9;
    public static final int EASY_COLS = 9;
    public static final int EASY_MINES = 10;

    public static final int MEDIUM_ROWS = 16;
    public static final int MEDIUM_COLS = 16;
    public static final int MEDIUM_MINES = 40;

    public static final int HARD_ROWS = 30;
    public static final int HARD_COLS = 16;
    public static final int HARD_MINES = 99;    

    // Current Difficulty
    public String currentDifficulty = "Medium"; // Set default to medium

    // Accesibility Options
    public boolean bigMode = false;

    /**
     * Constructor for InfoBoardPanel class.
     */
    public InfoBoardPanel() 
    {
        super.setLayout(new GridLayout(1, 5)); 

        // JComboBox for difficulty selection
        String[] difficultyLevels = {"Easy", "Medium", "Hard"};
        difficultyComboBox = new JComboBox<>(difficultyLevels);
        difficultyComboBox.setSelectedItem("Medium"); // Set default to Medium
        difficultyComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedDifficulty = (String) difficultyComboBox.getSelectedItem();
                updateGameSettings(selectedDifficulty); // If new difficulty selected, update difficulty 
                stopTimer();
                secondsElapsed = 0;
                timerLabel.setText("Time: 0"); // Reset timer
            }
        });
        super.add(difficultyComboBox);

        // Set timer label
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timerLabel.setFont(new Font("Dialog", Font.PLAIN, 14));
        super.add(timerLabel);

        // Set flag counter label
        flagCount.setText("FLAGS LEFT: " + amountOfFlags);
        flagCount.setFont(new Font("Dialog", Font.PLAIN, 14));
        super.add(flagCount);

        // New Game button
        newGameButton.setText("New Game");
        newGameButton.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e)
            {  
                newGame(); // Start new game
            }  
        });  
        super.add(newGameButton);

        // Timer setup
        timer = new Timer(1000, new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                secondsElapsed++;
                timerLabel.setText("Time: " + secondsElapsed);
            }
        });
    }

    /**
     * Updates the font size of flag and timer labels based on bigMode setting.
     * 
     * @param isSelected Boolean indicating if bigMode is selected.
     */
    public void updateFontSize(Boolean isSelected) {
        bigMode = isSelected;

        // Adjust font size based on bigMode
        int fontSize = bigMode ? 20 : 15; // Change font size to 20 if bigMode is true
        Font font = new Font("Dialog", Font.PLAIN, fontSize);

        // Update labels with new font
        timerLabel.setFont(font);
        flagCount.setFont(font);
    }

    /**
     * Updates game settings based on selected difficulty.
     * 
     * @param difficulty String representing the selected difficulty.
     */
    private void updateGameSettings(String difficulty) 
    {
        switch (difficulty) {
            case "Easy":
                currentDifficulty = "Easy";
                mineMap.changeDifficulty(EASY_COLS, EASY_ROWS);
                mainPanel.updateDifficultySettings(EASY_COLS, EASY_ROWS, EASY_MINES);
                break;
            case "Medium":
                currentDifficulty = "Medium";
                mineMap.changeDifficulty(MEDIUM_COLS, MEDIUM_COLS);
                mainPanel.updateDifficultySettings(MEDIUM_COLS, MEDIUM_ROWS, MEDIUM_MINES);
                break;
            case "Hard":
                currentDifficulty = "Hard";
                mineMap.changeDifficulty(HARD_COLS, HARD_ROWS);
                mainPanel.updateDifficultySettings(HARD_COLS, HARD_ROWS, HARD_MINES);
                break;
            default:
                break;
        }
    }

    /**
     * Starts a new game.
     */
    public void newGame()
    {
        mainPanel.newGame();
        timerLabel.setText("Time: 0");
        stopTimer();
    }

    /**
     * Resets the flag count for a new game.
     * 
     * @param mines Number of mines in the game.
     */
    public void resetFlags(int mines) {
        amountOfFlags = mines;
        flagCount.setText("Flags Left: " + amountOfFlags); // Reset flags for new game
    }

    /**
     * Increases the flag count.
     */
    public void increaseFlag() {
        amountOfFlags++;
        flagCount.setText("Flags Left: " + amountOfFlags); // Increase flags with one
    }

    /**
     * Decreases the flag count.
     */
    public void decreaseFlag() {
        amountOfFlags--;
        flagCount.setText("Flags Left: " + amountOfFlags); // Decrease flags with one
    }
    
    /**
     * Passes the main panel and mine map to this class for interaction.
     * 
     * @param panel MainBoardPanel instance.
     * @param minePanel MineMap instance.
     */
    public void passPanel(MainBoardPanel panel, MineMap minePanel)
    {
        this.mainPanel = panel;
        this.mineMap = minePanel;
    }
    
    /**
     * Starts the game timer.
     */
    public void startTimer()
    {
        secondsElapsed = 0;
        timerLabel.setText("Time: 0");
        timer.start();
    }
    
    /**
     * Stops the game timer.
     */
    public void stopTimer()
    {
        timer.stop();
    }

    /**
     * Checks if the current game time is a new high score and saves it if true.
     */
    public void checkHighScore() 
    {
        int highScore = HighScoreManager.getHighScore(currentDifficulty);
        if (secondsElapsed < highScore) {
            HighScoreManager.saveHighScore(currentDifficulty, secondsElapsed);
        }
    }
}