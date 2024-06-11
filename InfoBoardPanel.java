
/**
 * Write a description of class InfoBoardPanel here.
 *
 * @author Julius Gauldie
 * @version 11/06/24
 */
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.Timer;
public class InfoBoardPanel extends JPanel {
    private MainBoardPanel mainPanel;
    private JButton newGameButton = new JButton();
    
    // Flag Count
    public int amountOfFlags = 0;
    private JLabel flagCount = new JLabel();
    
    // Timer
    private JLabel timerLabel = new JLabel("Time: 0");
    private Timer timer;
    private int secondsElapsed;

    public InfoBoardPanel() {
        super.setLayout(new GridLayout(1, 3));
        flagCount.setText("FLAGS LEFT: " + amountOfFlags);
        super.add(flagCount);
        
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        super.add(timerLabel);
        
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
    

    public void passPanel(MainBoardPanel panel)
    {
        this.mainPanel = panel;
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
