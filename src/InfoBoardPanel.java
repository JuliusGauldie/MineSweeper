
/**
 * Write a description of class InfoBoardPanel here.
 *
 * @author Julius Gauldie
 * @version 20/06/24
 */
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.Timer;
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
    private int secondsElapsed;
    
    // Difficulty dropdown menu
    private JComboBox<String> difficultyComboBox;
    
    // Difficulty Settings
    public static final int EASY_ROWS = 9;
    public static final int EASY_COLS = 9;
    public static final int EASY_MINES = 10;

    public static final int MEDIUM_ROWS = 16;
    public static final int MEDIUM_COLS = 16;
    public static final int MEDIUM_MINES = 40;

    public static final int HARD_ROWS = 16;
    public static final int HARD_COLS = 30;
    public static final int HARD_MINES = 99;    

    public InfoBoardPanel() 
    {
        super.setLayout(new GridLayout(1, 4)); // Changed to 1 row and 4 columns

        // JComboBox for difficulty selection
        String[] difficultyLevels = {"Easy", "Medium", "Hard"};
        difficultyComboBox = new JComboBox<>(difficultyLevels);
        difficultyComboBox.setSelectedItem("Medium"); // Set default to Medium
        difficultyComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedDifficulty = (String) difficultyComboBox.getSelectedItem();
                updateGameSettings(selectedDifficulty);
                stopTimer();
            }
        });
        super.add(difficultyComboBox);

        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        super.add(timerLabel);

        flagCount.setText("FLAGS LEFT: " + amountOfFlags);
        super.add(flagCount);

        newGameButton.setText("New Game");
        newGameButton.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e)
            {  
                mainPanel.newGame();
                timerLabel.setText("Time: 0");
                stopTimer();
            }  
        });  
        super.add(newGameButton);

        timer = new Timer(1000, new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                secondsElapsed++;
                timerLabel.setText("Time: " + secondsElapsed);
            }
        });
    }
    
    private void updateGameSettings(String difficulty) 
    {
        switch (difficulty) {
            case "Easy":
                mineMap.changeDifficulty(EASY_COLS, EASY_ROWS);
                mainPanel.updateDifficultySettings(EASY_COLS, EASY_ROWS, EASY_MINES);
                break;
            case "Medium":
                mineMap.changeDifficulty(MEDIUM_COLS, MEDIUM_COLS);
                mainPanel.updateDifficultySettings(MEDIUM_COLS, MEDIUM_ROWS, MEDIUM_MINES);
                break;
            case "Hard":
                mineMap.changeDifficulty(HARD_COLS, HARD_ROWS);
                mainPanel.updateDifficultySettings(HARD_COLS, HARD_ROWS, HARD_MINES);
                break;
            default:
                break;
        }
    }

    public void resetFlags(int mines) {
        amountOfFlags = mines;
        flagCount.setText("FLAGS LEFT: " + amountOfFlags); // Reset flags for new game
    }

    public void increaseFlag() {
        amountOfFlags++;
        flagCount.setText("FLAGS LEFT: " + amountOfFlags); // Increase flags with one
    }

    public void decreaseFlag() {
        amountOfFlags--;
        flagCount.setText("FLAGS LEFT: " + amountOfFlags); // Decrease flags with one
    }
    
    public void passPanel(MainBoardPanel panel, MineMap minePanel)
    {
        this.mainPanel = panel;
        this.mineMap = minePanel;
    }
    
    public void startTimer()
    {
        secondsElapsed = 0;
        timerLabel.setText("Time: 0");
        timer.start();
    }
    
    public void stopTimer()
    {
        timer.stop();
    }
}