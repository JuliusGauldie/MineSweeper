
/**
 * Cell logic class
 *
 * @author Julius Gauldie
 * @version 24/06/24
 */
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Cell extends JButton {
    // Color and UI
    public static final Color BG_NOT_REVEALED = Color.GREEN;
    public static final Color FG_NOT_REVEALED = Color.RED; // Flag
    public static final Color BG_REVEALED = Color.DARK_GRAY;
    public static final Color FG_REVEALED = Color.YELLOW;
    public static final Font FONT_NUMBERS = new Font("Monospaced", Font.BOLD, 20);

    int row, col; // Row and column number of cell;
    boolean isRevealed; // Check if revealed
    boolean isMine; // Check if mine
    boolean isFlagged; // Check if flagged by player
    boolean isCrossed; // If cell wrongly flagged

    // Images
    private static BufferedImage flagImage;
    private static BufferedImage mineImage;
    private static BufferedImage crossImage;

    // Try to assign images
    static {
        try {
            flagImage = ImageIO.read(new File("assets/flag.png"));
            mineImage = ImageIO.read(new File("assets/mine.png"));
            crossImage = ImageIO.read(new File("assets/cross.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructor for objects of class Cell
     */
    public Cell(int row, int col) {
        super();
        this.row = row;
        this.col = col;

        super.setFont(FONT_NUMBERS); // Set font of JButton
    }

    public void newGame(boolean isMine) {
        this.isRevealed = false;
        this.isFlagged = false;
        this.isCrossed = false;
        this.isMine = isMine;
        super.setEnabled(true);
        super.setText("");
        paint();
    }

    public void setMine(boolean isMine) {
        this.isMine = isMine;
    }

    public void paint() {
        super.setForeground(isRevealed ? FG_REVEALED : FG_NOT_REVEALED);
        super.setBackground(isRevealed ? BG_REVEALED : BG_NOT_REVEALED);

        // Add flag image
        if (isFlagged) {
            super.setIcon(new ImageIcon(flagImage));
        } else {
            super.setIcon(null);
        }

        // Add mine image
        if (isRevealed && isMine)
        {
            super.setIcon(new ImageIcon(mineImage));
        }

        // Add cross image
        if (isCrossed)
        {
            super.setIcon(new ImageIcon(crossImage));
        }
    }
    
    public boolean isFlagged() {
        return isFlagged;
    }

    public void toggleFlag() {
        isFlagged = !isFlagged;
        paint(); // Update cell appearance after toggling flag
    }
}
