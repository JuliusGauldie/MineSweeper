
/**
 * Write a description of class Cell here.
 *
 * @author Julius Gauldie
 * @version 07/05/24
 */
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
public class Cell extends JButton
{
    public static final Color BG_NOT_REVEALED = Color.GREEN;
    public static final Color FG_NOT_REVEALED = Color.RED; // Flag
    public static final Color BG_REVEALED = Color.DARK_GRAY;
    public static final Color FG_REVEALED = Color.YELLOW;
    public static final Font FONT_NUMBERS = new Font("Monospaced", Font.BOLD, 20);
    
    int row, col; //Row and column number of cell;
    boolean isRevealed; //Check if revealed
    boolean isMine; //Check if mine
    boolean isFlagged; //Check if flagged by player
    
    /**
     * Constructor for objects of class Cell
     */
    public Cell(int row, int col)
    {
        super(); //JTextField
        this.row = row;
        this.col = col;
        
        super.setFont(FONT_NUMBERS); //Set font of JButton
    }
    
    public void newGame(boolean isMined)
    {
        this.isRevealed = false;
        this.isFlagged = false;
        this.isMine = isMine;
        super.setEnabled(true);
        super.setText("");
        paint();
    }

    public void paint()
    {
        super.setForeground(isRevealed ? FG_REVEALED : FG_NOT_REVEALED);
        super.setBackground(isRevealed ? BG_REVEALED : BG_NOT_REVEALED);
    }
}
