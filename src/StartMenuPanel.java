/**
 * Logic for Main Menu - Shown on start
 *
 * Original concept by Robert Donner and Curt Johnson, 1989.
 * @author Julius Gauldie
 * @version 29/06/24
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class StartMenuPanel extends JPanel {
    private JButton startButton;
    private JCheckBox bigModeCheckBox;
    private JCheckBox tooltipCheckBox;

    // High Scores
    private JLabel easyHighScore;
    private JLabel mediumHighScore;
    private JLabel hardHighScore;

    /**
     * Constructor for StartMenuPanel class.
     * 
     * @param mainBoardPanel MainBoardPanel instance.
     */
    public StartMenuPanel(MainBoardPanel mainBoardPanel) {
        setLayout(new BorderLayout());

        // Title label
        JLabel titleLabel = new JLabel("MINESWEEPER", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 100));
        add(titleLabel, BorderLayout.NORTH);

        // Center panel for instructions and start button
        JPanel centerPanel = new JPanel(new GridLayout(3, 1));

        // Instructions label
        JLabel instructionsLabel = new JLabel("<html><div style='text-align: center;'>"
            + "Instructions:<br>"
            + "1. Left-click on a cell to reveal it.<br>"
            + "2. Right-click on a cell to place or remove a flag.<br>"
            + "3. The numbers on revealed cells indicate how many mines are adjacent to that cell.<br>"
            + "4. Use the numbers to deduce the locations of the mines.<br>"
            + "5. If you reveal a mine, you lose the game.<br>"
            + "6. To win, reveal all safe cells without revealing a mine.<br>"
            + "7. You can use the 'New Game' button at any time to start a new game.</div></html>");
        instructionsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        centerPanel.add(instructionsLabel);

        // Start button
        startButton = new JButton("Click To Start Game");
        startButton.setFont(new Font("Arial", Font.BOLD, 40));
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainBoardPanel.newGame();
                CardLayout cl = (CardLayout) (mainBoardPanel.getLayout());
                cl.show(mainBoardPanel, "GamePanel"); // Switch from starmenu panel to game board panel
            }
        });
        JPanel startButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        startButtonPanel.add(startButton);
        centerPanel.add(startButtonPanel);

        // Accessibility Menu
        JPanel accessibilityPanel = new JPanel(new GridLayout(2, 1));
        accessibilityPanel.setBorder(BorderFactory.createTitledBorder("Accessibility"));

        // Create and add Big Mode checkbox
        bigModeCheckBox = new JCheckBox("Big Mode - Makes timer and flag counter bigger");
        bigModeCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainBoardPanel.toggleBigMode(bigModeCheckBox.isSelected()); // Toggles big mode
            }
        });
        accessibilityPanel.add(bigModeCheckBox);

        // Create and add Tooltip checkbox
        tooltipCheckBox = new JCheckBox("Tooltip - When hovering over cell, show cell state");
        tooltipCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainBoardPanel.toggleTooltip(tooltipCheckBox.isSelected()); // Toggles tool tip
            }
        });
        accessibilityPanel.add(tooltipCheckBox);

        centerPanel.add(accessibilityPanel);

        add(centerPanel, BorderLayout.CENTER);

        // Bottom panel for high scores and credits
        JPanel bottomPanel = new JPanel(new GridLayout(2, 1));

        // High scores
        JPanel highScoresPanel = new JPanel(new GridLayout(3, 1));
        highScoresPanel.setBorder(BorderFactory.createTitledBorder("High Scores"));

        // Load high scores
        if (HighScoreManager.getHighScore("Easy") != Integer.MAX_VALUE)
            easyHighScore = new JLabel("Easy: " + HighScoreManager.getHighScore("Easy") + " Seconds");
        else
            easyHighScore = new JLabel("Easy: -");

        if (HighScoreManager.getHighScore("Medium") != Integer.MAX_VALUE)    
            mediumHighScore = new JLabel("Medium: " + HighScoreManager.getHighScore("Medium") + " Seconds");
        else
            mediumHighScore = new JLabel("Medium: -");

        if (HighScoreManager.getHighScore("Hard") != Integer.MAX_VALUE)
            hardHighScore = new JLabel("Hard: " + HighScoreManager.getHighScore("Hard") + " Seconds");
        else
            hardHighScore = new JLabel("Hard: -");

        highScoresPanel.add(easyHighScore);
        highScoresPanel.add(mediumHighScore);
        highScoresPanel.add(hardHighScore);

        bottomPanel.add(highScoresPanel);

        // Credits label
        JLabel creditsLabel = new JLabel("Created by Julius Gauldie", SwingConstants.CENTER);
        creditsLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        bottomPanel.add(creditsLabel);

        add(bottomPanel, BorderLayout.SOUTH);
    }
}

