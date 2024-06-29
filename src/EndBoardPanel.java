
/**
 * Logic for End Board - shown on win or loss
 *
 * Original concept by Robert Donner and Curt Johnson, 1989.
 * @author Julius Gauldie
 * @version 29/06/24
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class EndBoardPanel extends JPanel {
    // JLabels displayed on end panel
    JLabel outcomeLabel; // Tells player if won or lost
    JLabel currentTimeLabel;
    JLabel highScoreLabel;

    // Button
    private JButton restartButton;

    // JPanels
    private InfoBoardPanel infoPanel;
        
    /**
     * Constructor for objects of class EndBoardPanel
     */
    public EndBoardPanel() 
    {
        setLayout(new GridLayout(4, 1));

        // Create the outcome label
        outcomeLabel = new JLabel("", JLabel.CENTER);
        add(outcomeLabel);

        // Create current time label
        currentTimeLabel = new JLabel("", JLabel.CENTER);
        add(currentTimeLabel);

        // Create high score label
        highScoreLabel = new JLabel("", JLabel.CENTER);
        add(highScoreLabel);

        // Create the restart button
        restartButton = new JButton("New Game");
        restartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Call the restart game method in InfoBoardPanel
                infoPanel.newGame();
            }
        });
        add(restartButton);

        setVisible(false); // Initially hide the end panel
        super.setPreferredSize(new Dimension(600, 600)); // Set preferred size
    }

    /**
     * Updates the labels with current game information.
     * @param currentTime The current time played.
     * @param highScore The highest score for that difficulty.
     * @param lostGame True if player won game, false if player won.
     */
    public void updateLabels(int currentTime, int highScore, boolean lostGame)
    {
        // Show if won
        outcomeLabel.setText(lostGame ? "YOU LOSE" : "YOU WIN");

        // Show current time played
        currentTimeLabel.setText(lostGame ? "Current Time: -" : "Current Time: " + currentTime);

        // Show high score
        highScoreLabel.setText(highScore != Integer.MAX_VALUE ? "High Score: " + highScore : "High Score -");
    }

    /**
     * Passes the InfoBoardPanel instance to this panel for restarting the game.
     * @param panel The InfoBoardPanel passed.
     */
    public void passPanel(InfoBoardPanel panel)
    {
        // Set InfoBoardPanel
        this.infoPanel = panel;
    }
}