
/**
 * Creates randomized locations for mines
 *
 * Original concept by Robert Donner and Curt Johnson, 1989.
 * @author Julius Gauldie
 * @version 29/06/24
 */
import java.util.Random;
public class MineMap {
    public int curentRows = 16; //Number of rows
    public int currentCols = 16; //Number of columns
    
    int numMines; // Number of mines
    boolean[][] isMine = new boolean[curentRows][currentCols]; // Array for mines
    InfoBoardPanel infoPanel;

    /**
     * Constructor for objects of class MineMap
     * 
     * @param infoPanel The InfoBoardPanel instance to link for flag count updates.
     */
    public MineMap(InfoBoardPanel infoPanel) 
    {
        this.infoPanel = infoPanel;
    }

    /**
     * Generates a new mine map with random mine placements.
     * 
     * @param panel The MainBoardPanel instance where cells are managed.
     * @param numMines Number of mines to place.
     * @param firstClickedRow Row of the first clicked cell (to avoid placing a mine there).
     * @param firstClickedCol Column of the first clicked cell (to avoid placing a mine there).
     */
    public void newMineMap(MainBoardPanel panel, int numMines, int firstClickedRow, int firstClickedCol) 
    {
        this.numMines = numMines;
        
        // Clear all existing mines
        for (int i = 0; i < curentRows; i++) {
            for (int j = 0; j < currentCols; j++) {
                isMine[i][j] = false;
            }
        }

        Random rand = new Random();

        // Place random mines
        for (int i = 0; i < numMines; i++) 
        {
            int randomRow = rand.nextInt(curentRows);
            int randomCol = rand.nextInt(currentCols);

            // Make sure no mine is placed on the first clicked cell, or any of its adjecent cells
            if (isMine[randomRow][randomCol] || (Math.abs(randomRow - firstClickedRow) <= 1 && Math.abs(randomCol - firstClickedCol) <= 1))
            {
                i--;
                continue;
            }

            isMine[randomRow][randomCol] = true;
        }
        
        // Update panel with mine locations
        int rowsToIterate = Math.min(curentRows, panel.cells.length);
        int colsToIterate = Math.min(currentCols, panel.cells[0].length);
    
        for (int row = 0; row < rowsToIterate; row++) {
            for (int col = 0; col < colsToIterate; col++) {
                panel.cells[row][col].setMine(isMine[row][col]);
            }
        }

        // Reset Flags Info Panel
        infoPanel.resetFlags(numMines);

        // Reveal First clicked cell
        panel.revealCell(firstClickedRow, firstClickedCol);
    }
        
    /**
     * Changes the difficulty of the game by updating the number of rows and columns.
     * 
     * @param rows New number of rows.
     * @param cols New number of columns.
     */
    public void changeDifficulty(int rows, int cols)
    {
        curentRows = rows;
        currentCols = cols;
        
        // Resize Array
        isMine = new boolean[curentRows][currentCols];
    }
}