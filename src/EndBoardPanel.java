
/**
 * Logic for End Board - shown on win
 *
 * @author Julius Gauldie
 * @version 20/06/24
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class EndBoardPanel extends JPanel {
    // JLabels
    JLabel winLabel;
    JLabel currentTimeLabel;
    JLabel highScoreLabel;

    // Button
    private JButton restartButton;

    // JPanels
    private InfoBoardPanel infoPanel;
    
    public EndBoardPanel() 
    {
        setLayout(new GridLayout(4, 1));

        // Create the win label
        winLabel = new JLabel("", JLabel.CENTER);
        add(winLabel);

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

    public void updateLabels(int currentTime, int highScore, boolean lostGame)
    {
        // Show if won
        if (lostGame)
            winLabel.setText("YOU LOSE");
        else
            winLabel.setText("YOU WIN");

        // Show time played
        if (lostGame)
            currentTimeLabel.setText("Current Time: -");
        else
            currentTimeLabel.setText("Current Time: " + currentTime);

        // Show high score
        if (highScore != Integer.MAX_VALUE) // Check highscore is not defaulted to max
            highScoreLabel.setText("High Score: " + highScore);
        else
            highScoreLabel.setText("High Score: -");
    }

    public void passPanel(InfoBoardPanel panel)
    {
        // Set InfoBoardPanel
        this.infoPanel = panel;
    }
}