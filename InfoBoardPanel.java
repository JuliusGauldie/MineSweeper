
/**
 * Write a description of class InfoBoardPanel here.
 *
 * @author Julius Gauldie
 * @version 16/05/24
 */
import java.awt.*;
import javax.swing.*;

public class InfoBoardPanel extends JPanel {
    public int amountOfFlags = 0;
    private JLabel flagCount = new JLabel();

    public InfoBoardPanel() {
        super.setLayout(new GridLayout(1, 1, 1, 1));
        flagCount.setText("FLAGS LEFT: " + amountOfFlags);
        super.add(flagCount);
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
}
