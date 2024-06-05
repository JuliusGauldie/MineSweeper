
/**
 * Write a description of class WinPanel here.
 *
 * @author Julius Gauldie
 * @version 06/06/24
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class EndBoardPanel extends JPanel {
    private JLabel messageLabel;

    //Import MineSweeper Constants
    public static final int ROWS = 10; //Number of rows
    public static final int COLS = 10; //Number of columns
    
    //Constants for UI
    public static final int CELL_SIZE = 60; //Cell width and height
    public static final int CANVAS_WIDTH = CELL_SIZE * COLS; //Game Board widht/height
    public static final int CANVAS_HEIGHT = CELL_SIZE * ROWS;
    
    public EndBoardPanel() {
        setLayout(new BorderLayout());

        // Create the message label
        messageLabel = new JLabel();
        messageLabel.setHorizontalAlignment(JLabel.CENTER);
        messageLabel.setText("YOU WIN, CLICK NEW GAME TO START NEW GAME");
        add(messageLabel, BorderLayout.CENTER);

        setVisible(false); // Initially hide the end panel
        super.setPreferredSize(new Dimension(300, 300)); // Set preferred size
    }
}