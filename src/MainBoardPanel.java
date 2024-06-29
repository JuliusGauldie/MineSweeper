
/**
 * Logic for Main Board - Grid
 *
 * Original concept by Robert Donner and Curt Johnson, 1989.
 * @author Julius Gauldie
 * @version 29/06/24
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class MainBoardPanel extends JPanel
{
    // Grid Variables
    public int currentRows = 16; //Number of rows
    public int currentCols = 16; //Number of columns
    
    // UI Variables
    public static final int CELL_SIZE = 50; //Cell width and height
    public int canvasWidth = CELL_SIZE * currentRows; //Game Board widht/height
    public int canvasHeight = CELL_SIZE * currentCols;
    
    // Mouse Variables
    CellMouseListener listener = new CellMouseListener();
    
    // Cell Variables
    public Cell cells[][] = new Cell[currentRows][currentCols];
    int numMines = 40; //Set number of mines

    // Other Classes
    MineSweeperMain main;
    public InfoBoardPanel infoPanel;
    private EndBoardPanel endPanel;
    private MineMap mineMap;
    private MainBoardPanel thisPanel = this;
    private StartMenuPanel menuPanel;

    // Game Booleans
    private boolean gameStarted = false;
    public boolean gameOver = false;
    private boolean gameWon = false;
    
    // Acccesiblity Booleans
    public boolean bigMode = false;
    public boolean toolTip = false;

    /**
     * Constructor for objects of class GameBoardPanel
     */
    public MainBoardPanel() 
    {
        super(new CardLayout()); // Use CardLayout for the panel

        // Create Main Menu
        menuPanel = new StartMenuPanel(this);

        // Create game layered pane
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(canvasWidth, canvasHeight));

        // Create game panel
        JPanel gamePanel = new JPanel(new GridLayout(currentRows, currentCols, 2, 2));
        gamePanel.setBounds(0, 0, canvasWidth, canvasHeight);

        // Initialize cells and add mouse listener
        for (int row = 0; row < currentRows; row++) {
            for (int col = 0; col < currentCols; col++) {
                cells[row][col] = new Cell(row, col);
                cells[row][col].addMouseListener(listener);
                gamePanel.add(cells[row][col]);
            }
        }
        layeredPane.add(gamePanel, JLayeredPane.DEFAULT_LAYER); // Add game panel to default layer

        // Create and add the end panel to a higher layer
        endPanel = new EndBoardPanel();
        endPanel.setBounds((canvasWidth - CELL_SIZE * 6) / 2, (canvasHeight - CELL_SIZE * 6) / 2, CELL_SIZE * 6, CELL_SIZE * 6);
        endPanel.setVisible(false);
        layeredPane.add(endPanel, JLayeredPane.PALETTE_LAYER); // Add end panel to higher layer

        // Add main menu panel and game layered pane to the card layout
        super.add(menuPanel, "MainMenu");
        super.add(layeredPane, "GamePanel");

        // Set the preferred size of the main board panel
        super.setPreferredSize(new Dimension(canvasWidth, canvasHeight));
    }

    /**
     * Starts a new game, resetting all necessary game elements.
     */
    public void newGame() //Reset game
    {
        // Set endpanel to invisible
        endPanel.setVisible(false);
        repaint();
        revalidate();
        
        // Calculate updated canvas size, make cells bigger on big mode on easy difficulty
        canvasWidth = bigMode && currentCols == 9 ? (CELL_SIZE + 15) * currentCols : CELL_SIZE * currentCols;
        canvasHeight = bigMode && currentRows == 9 ? (CELL_SIZE + 15) * currentRows : CELL_SIZE * currentRows;

        // Reset game booleans
        gameStarted = false;
        gameOver = false;
        gameWon = false;
        
        // Reset mines in cells
        for (int row = 0; row < currentRows; row++)
        {
            for (int col = 0; col < currentCols; col++)
            {
                cells[row][col].isMine = false;
            }
        }
        
        // Reset cells for new game
        for (int row = 0; row < currentRows; row++)
        {
            for (int col = 0; col < currentCols; col++)
            {
                cells[row][col].newGame(mineMap.isMine[row][col]);
            }
        }
        
        // Reset flag count in the info panel
        infoPanel.resetFlags(numMines);
    }

    /**
     * Updates the game board with new difficulty settings.
     * 
     * @param rows Number of rows for the new game board.
     * @param cols Number of columns for the new game board.
     * @param mines Number of mines for the new game board.
     */   
    public void updateDifficultySettings(int rows, int cols, int mines) {
        // Set new rows, cols and mines
        currentRows = rows;
        currentCols = cols;
        numMines = mines;
        
        // Resize Cell array and initialize new cells
        cells = new Cell[currentRows][currentCols];
        for (int row = 0; row < currentRows; row++) {
            for (int col = 0; col < currentCols; col++) {
                cells[row][col] = new Cell(row, col);
                cells[row][col].addMouseListener(listener);
            }
        }
        
        // Start a new game with updated settings
        newGame();
    
        // Remove all components from the panel
        removeAll();

        // Create game layered pane
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(canvasWidth, canvasHeight));

        // Create game panel
        JPanel gamePanel = new JPanel(new GridLayout(currentRows, currentCols, 2, 2));
        gamePanel.setBounds(0, 0, canvasWidth, canvasHeight);
        for (int row = 0; row < currentRows; row++) {
            for (int col = 0; col < currentCols; col++) {
                gamePanel.add(cells[row][col]);
            }
        }
        layeredPane.add(gamePanel, JLayeredPane.DEFAULT_LAYER); // Add game panel to default layer

        // Create and add the end panel to a higher layer
        endPanel = new EndBoardPanel();
        endPanel.passPanel(infoPanel); // Pass infoPanel to new endPanel

        // Calculate position and size relative to the game board
        int endPanelWidth = CELL_SIZE * 6;
        int endPanelHeight = CELL_SIZE * 6;
        int endPanelX = (canvasWidth - endPanelWidth) / 2;
        int endPanelY = (canvasHeight - endPanelHeight) / 2;
        endPanel.setBounds(endPanelX, endPanelY, endPanelWidth, endPanelHeight);
        endPanel.setVisible(false);
        layeredPane.add(endPanel, JLayeredPane.PALETTE_LAYER); // Add end panel to higher layer

        // Add game layered pane to the card layout
        add(layeredPane, "GamePanel");

        // Set the preferred size of the main board panel
        setPreferredSize(new Dimension(canvasWidth, canvasHeight));
    
        // Revalidate and repaint the parent container
        getParent().revalidate();
        getParent().repaint();

        // Resize the main JFrame
        SwingUtilities.getWindowAncestor(this).pack();
    }

    /**
     * Calculates the number of mines surrounding a given cell.
     * 
     * @param srcRow Row of the cell.
     * @param srcCol Column of the cell.
     * @return Number of mines surrounding the cell.
     */    
    private int getSurroundingMines(int srcRow, int srcCol)
    {
        int numMines = 0; // Set surrounding mines to 0
        
        // Check all 9 adjecent cells for mines
        for (int row = srcRow -1; row <= srcRow + 1; row++)
        {
            for (int col = srcCol -1; col <= srcCol + 1; col++)
            { 
                if (row >= 0 && row < currentRows && col >= 0 && col < currentCols) // Ensure valid cell
                {
                    if (cells[row][col].isMine) numMines++; // Add 1 to numines if adjecent cell is mine
                }
            }
        }
        
        // UPdate cell with number of surrounding mines
        cells[srcRow][srcCol].surroundingMines = numMines;
        return numMines;
    }

    /**
     * Reveals a cell on the game board and its surrounding cells if applicable.
     * 
     * @param srcRow Row of the cell to reveal.
     * @param srcCol Column of the cell to reveal.
     */    
    public void revealCell(int srcRow, int srcCol)
    {
        // Check if any surrounding mines
        int numMines = getSurroundingMines(srcRow, srcCol);

        // Update cell to number for revealed state
        cells[srcRow][srcCol].setText(numMines + "");
        cells[srcRow][srcCol].isRevealed = true;
        cells[srcRow][srcCol].paint();
        
        // Recursively reveal adjacent cells if no surrounding mines
        if (numMines == 0)
        {
            cells[srcRow][srcCol].setText("");
            cells[srcRow][srcCol].paint();

            for (int row = srcRow -1; row <= srcRow + 1; row++)
            {
                for (int col = srcCol -1; col <= srcCol + 1; col++)
                {
                    if (row >= 0 && row < currentRows && col >= 0 && col < currentCols) //Ensure valid cell
                        if (!cells[row][col].isRevealed && !cells[row][col].isFlagged) revealCell(row, col);
                }
            }
        }
    }
    
    /**
     * Mouse listener for handling user interactions with game cells.
     */
    private class CellMouseListener extends MouseAdapter
    {
        public void mouseClicked(MouseEvent e) 
        {
            Cell sourceCell = (Cell)e.getSource(); // Get Cell Clicked
            
            if (e.getButton() == MouseEvent.BUTTON1) // Left Mouse Click
            {                    
                if (!gameOver && !gameWon && gameStarted)
                { 
                    if (sourceCell.isMine && !sourceCell.isFlagged) // If cell is mine and not flagged
                    {
                        gameOver();
                    }
                    else if (!sourceCell.isFlagged && !sourceCell.isMine) // If cell isn't mine or flagged
                    {
                        revealCell(sourceCell.row, sourceCell.col);
                    }
                }
                
                if (!gameStarted) // If first click
                {
                    mineMap.newMineMap(thisPanel, numMines, sourceCell.row, sourceCell.col);
                    gameStarted = true;
                    
                    infoPanel.startTimer(); // Start game timer
                }
                
            }
            else if (e.getButton() == MouseEvent.BUTTON3) // Right Mouse Click
            {
                if (!gameOver && !gameWon && gameStarted && !cells[sourceCell.row][sourceCell.col].isRevealed)
                {
                    if (sourceCell.isFlagged) // Unflag
                    {
                        sourceCell.isFlagged = false; // If flagged, unflag
                        cells[sourceCell.row][sourceCell.col].paint(); // Repaint Cell
 
                        infoPanel.increaseFlag(); // Increase flag counter by one
                    }          
                    else //Flag
                    {
                        sourceCell.isFlagged = true; // If not flagged, flag
                        cells[sourceCell.row][sourceCell.col].paint(); // Repaint cell
                    
                        infoPanel.decreaseFlag(); // Decrease flag counter by one
                    }
                }
            }
            
            // Win Condition - All safe cells are revealed
            boolean allRevealed = true;

            for (int row = 0; row < currentRows; row++)
            {
                for (int col = 0; col < currentCols; col++)
                {
                    if (!cells[row][col].isRevealed && !mineMap.isMine[row][col]) // If not revealed && not a mine
                        allRevealed = false;
                }
            }
            
            if (allRevealed) gameWin(); // If all safe cells are revealed
        }

        public void mouseEntered(MouseEvent e) { // When mouse hovers over cell
            Cell sourceCell = (Cell) e.getSource();
            if (toolTip == true)   
                sourceCell.setToolTipText(getCellTooltipText(sourceCell)); // Update tooltip on hover
        }
    
        public void mouseExited(MouseEvent e) { // When mouse stops hovering over cell
            Cell sourceCell = (Cell) e.getSource();
            sourceCell.setToolTipText(null); // Clear tooltip when mouse exits
        }

        // Method to determine tooltip text based on cell state
        private String getCellTooltipText(Cell cell) {
            if (cell.isRevealed) {
                if (cell.isMine) {
                    return "Mine"; // Tooltip text for revealed mine
                } else {
                    return "Surrounding mines: " + String.valueOf(cell.surroundingMines); // Tooltip text for revealed cell with surrounding mines
                }
            } else if (cell.isFlagged) {
                return "Flagged"; // Tooltip text for flagged cell
            } else {
                return "Click to reveal"; // Default tooltip when not revealed or flagged
            }
        }
    }

    /**
     * Handles the game win scenario.
     */
    public void gameWin()
    {        
        gameWon = true;
        endPanel.setVisible(true);
        repaint();
        revalidate();
        
        // Check if new highscore
        infoPanel.checkHighScore();

        // Pass to End Panel
        endPanel.updateLabels(infoPanel.secondsElapsed, HighScoreManager.getHighScore(infoPanel.currentDifficulty), false);

        // Stop timer
        infoPanel.stopTimer();
    }
    
    /**
     * Handles the game over scenario.
     */
    public void gameOver()
    {
        gameOver = true;
        
        // Reveal all mines to player
        for (int row = 0; row < currentRows; row++)
        {
            for (int col = 0; col < currentCols; col++)
            {
                if (cells[row][col].isMine && !cells[row][col].isFlagged) // If a mine and is not revealed or flagged
                    cells[row][col].isRevealed = true;

                if (cells[row][col].isFlagged && !cells[row][col].isMine) // If flagged but not a mine
                    cells[row][col].isCrossed = true;

                cells[row][col].paint(); // Update cell apearance
            }
        }

        endPanel.setVisible(true);
        repaint();
        revalidate();
        
        // Stop timer
        infoPanel.stopTimer();

        // Update end panel with game results
        endPanel.updateLabels(0, HighScoreManager.getHighScore(infoPanel.currentDifficulty), true);
    }
    
    /**
     * Passes necessary panels to this class for interaction.
     * 
     * @param panel InfoBoardPanel instance for game information.
     * @param main MineSweeperMain instance for main game control.
     */
    public void passPanels(InfoBoardPanel panel, MineSweeperMain main)
    {
        this.infoPanel = panel;
        this.main = main;
        
        mineMap = new MineMap(infoPanel);
        infoPanel.passPanel(this, mineMap);
        endPanel.passPanel(infoPanel);
    }

    /**
     * Toggles big mode setting for the game.
     * 
     * @param isSelected Boolean indicating if big mode is selected.
     */
    public void toggleBigMode(Boolean isSelected)
    {
        bigMode = isSelected;
        infoPanel.updateFontSize(isSelected); // Update font size in info panel

        newGame(); // Start new game with updated settings
    }

    /**
     * Toggles tooltip setting for the game.
     * 
     * @param isSelected Boolean indicating if tooltip is selected.
     */
    public void toggleTooltip(Boolean isSelected)
    {
        toolTip = isSelected;
    }
}