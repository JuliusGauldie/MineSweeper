
/**
 * Write a description of class InfoBoardPanel here.
 *
 * @author Julius Gauldie
 * @version 27/05/24
 */
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class InfoBoardPanel extends JPanel {
    public int amountOfFlags = 0;
    private JLabel flagCount = new JLabel();
    private JButton newGameButton = new JButton();
    private GameBoardPanel gamePanel;

    public InfoBoardPanel() {
        super.setLayout(new GridLayout(1, 2));
        flagCount.setText("FLAGS LEFT: " + amountOfFlags);
        super.add(flagCount);
        
        newGameButton.setText("New Game");
        newGameButton.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e)
            {  
                gamePanel.newGame();
            }  
        });  
        super.add(newGameButton);
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
    

    public void passPanel(GameBoardPanel panel)
    {
        this.gamePanel = panel;
    }
}
