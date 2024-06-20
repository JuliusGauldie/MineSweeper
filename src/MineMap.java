
/**
 * Creates randomized locations for mines
 *
 * @author Julius Gauldie
 * @version 20/06/24
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
        
        // Update panel with mine locations
        int rowsToIterate = Math.min(ROWS, panel.cells.length);
        int colsToIterate = Math.min(COLS, panel.cells[0].length);
    
        for (int row = 0; row < rowsToIterate; row++) {
            for (int col = 0; col < colsToIterate; col++) {
                panel.cells[row][col].setMine(isMine[row][col]);
            }
        }
        // Update Flags Info Panel
        infoPanel.resetFlags(numMines);

        // Reveal First Cell
        panel.revealCell(firstClickedRow, firstClickedCol);
    }
    
    public void changeDifficulty(int rows, int cols)
    {
        ROWS = rows;
        COLS = cols;
        
        // Resize Array
        isMine = new boolean[ROWS][COLS];
    }
}