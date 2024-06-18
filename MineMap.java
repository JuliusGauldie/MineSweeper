
/**
 * Write a description of class MineMap here.
 *
 * @author Julius Gauldie
 * @version 11/06/24
 */
import java.util.Random;
public class MineMap {
    public int ROWS = 16; //Number of rows
    public int COLS = 16; //Number of columns
    
    int numMines; // Number of mines
    boolean[][] isMine = new boolean[ROWS][COLS]; // Array for mines
    InfoBoardPanel infoPanel;

    public MineMap(InfoBoardPanel infoPanel, int rows, int cols) 
    {
        this.infoPanel = infoPanel;
    }

    public void newMineMap(MainBoardPanel panel, int numMines, int firstClickedRow, int firstClickedCol) 
    {
        this.numMines = numMines;
        
        // Clear all existing mines
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                isMine[i][j] = false;
            }
        }

        Random rand = new Random();

        // Place random mines
        for (int i = 0; i < numMines; i++) 
        {
            int randomRow = rand.nextInt(ROWS);
            int randomCol = rand.nextInt(COLS);

            if (isMine[randomRow][randomCol] || (Math.abs(randomRow - firstClickedRow) <= 1 && Math.abs(randomCol - firstClickedCol) <= 1))
            {
                i--;
                continue;
            }

            isMine[randomRow][randomCol] = true;
        }
        
        for (int row = 0; row < ROWS; row++) 
        {
            for (int col = 0; col < COLS; col++) {
                panel.cells[row][col].setMine(isMine[row][col]);
            }
        }

        // Update Flags Info Panel
        infoPanel.resetFlags(numMines);

        // Reveal First Cell
        panel.revealCell(firstClickedRow, firstClickedCol);
    }
}