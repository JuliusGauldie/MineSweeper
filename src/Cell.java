
/**
 * Cell logic class
 *
 * Original concept by Robert Donner and Curt Johnson, 1989. 
 * @author Julius Gauldie
 * @version 29/06/24
 */
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Cell extends JButton {
    // Colors for cell stats
    public static final Color BG_NOT_REVEALED = new Color(0,153,0); // Dark Green
    public static final Color BG_REVEALED = Color.DARK_GRAY;
    public static final Color FG_REVEALED = Color.YELLOW;
    public static final Color MINE_REVEALED = Color.RED;
    public static final Font FONT_NUMBERS = new Font("Monospaced", Font.BOLD, 20);

    // Ints and Booleans 
    int row, col; // Row and column of cell
    int surroundingMines = 0;
    boolean isRevealed; // Whether cell is revealed
    boolean isMine; // Whether cell contains mine
    boolean isFlagged; // Whether cell is flagged
    boolean isCrossed; // Whether cell is wrongly flagged

    // Images for cell states
    private static BufferedImage flagImage;
    private static BufferedImage mineImage;
    private static BufferedImage crossImage;

    // Try to assign images from assets
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
     * 
     * @param row is the row of the cell
     * @param col is the column of the cell
     */
    public Cell(int row, int col) {
        super();
        this.row = row;
        this.col = col;

        super.setFont(FONT_NUMBERS); // Set font for cell numbers
    }

    /**
     * Resets the cell for a new game
     * 
     * @param isMine Whether this cell has a mine or not
     */
    public void newGame(boolean isMine) {
        this.isRevealed = false;
        this.isFlagged = false;
        this.isCrossed = false;
        this.isMine = isMine;
        super.setEnabled(true);
        super.setText("");
        paint();
    }

    /**
     * Set if this cell contains mine or not
     * 
     * @param isMine True if cell contains mine, else false
     */
    public void setMine(boolean isMine) {
        this.isMine = isMine;
    }

    /**
     * Updates appearance of the cell
     */
    public void paint() {
        super.setForeground(FG_REVEALED);
        super.setBackground(isRevealed ? BG_REVEALED : BG_NOT_REVEALED);

        // Set icon for flagged state
        if (isFlagged) {
            super.setIcon(new ImageIcon(flagImage));
        } else {
            super.setIcon(null);
        }

        // Set icon for revealed mine
        if (isRevealed && isMine)
        {
            super.setIcon(new ImageIcon(mineImage));
            super.setBackground(MINE_REVEALED);
        }

        // Set icon for wrongly flagged cell
        if (isCrossed)
        {
            super.setIcon(new ImageIcon(crossImage));
        }
    }
    
    /**
     * Checks if cell is flagged
     *
     * @return True if cell is flagged, else false
     */
    public boolean isFlagged() {
        return isFlagged;
    }

    /**
     * Toggles the flagged state
     */
    public void toggleFlag() {
        isFlagged = !isFlagged;
        paint(); // Update cell appearance after toggling flag
    }
}
