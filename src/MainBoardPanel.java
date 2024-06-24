
/**
 * Logic for Main Board - Grid
 *
 * @author Julius Gauldie
 * @version 24/06/24
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class MainBoardPanel extends JPanel
{
    // Grid Variables
    public int ROWS = 16; //Number of rows
    public int COLS = 16; //Number of columns
    
    // UI Variables
    public static final int CELL_SIZE = 50; //Cell width and height
    public int CANVAS_WIDTH = CELL_SIZE * COLS; //Game Board widht/height
    public int CANVAS_HEIGHT = CELL_SIZE * ROWS;
    
    // Mouse Variables
    CellMouseListener listener = new CellMouseListener();
    
    // Cell Variables
    public Cell cells[][] = new Cell[ROWS][COLS];
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
        layeredPane.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

        // Create game panel
        JPanel gamePanel = new JPanel(new GridLayout(ROWS, COLS, 2, 2));
        gamePanel.setBounds(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                cells[row][col] = new Cell(row, col);
                cells[row][col].addMouseListener(listener);
                gamePanel.add(cells[row][col]);
            }
        }
        layeredPane.add(gamePanel, JLayeredPane.DEFAULT_LAYER); // Add game panel to default layer

        // Create and add the end panel to a higher layer
        endPanel = new EndBoardPanel();
        endPanel.setBounds((CANVAS_WIDTH - CELL_SIZE * 6) / 2, (CANVAS_HEIGHT - CELL_SIZE * 6) / 2, CELL_SIZE * 6, CELL_SIZE * 6);
        endPanel.setVisible(false);
        layeredPane.add(endPanel, JLayeredPane.PALETTE_LAYER); // Add end panel to higher layer

        // Add main menu panel and game layered pane to the card layout
        super.add(menuPanel, "MainMenu");
        super.add(layeredPane, "GamePanel");

        // Set the preferred size of the main board panel
        super.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
    }
    
    public void newGame() //Reset game
    {
        // Set endpanel to invisible
        endPanel.setVisible(false);
        repaint();
        revalidate();
        
        gameStarted = false;
        gameOver = false;
        gameWon = false;
        
        for (int row = 0; row < ROWS; row++)
        {
            for (int col = 0; col < COLS; col++)
            {
                cells[row][col].isMine = false;
            }
        }
        
        //Reset rest
        for (int row = 0; row < ROWS; row++)
        {
            for (int col = 0; col < COLS; col++)
            {
                cells[row][col].newGame(mineMap.isMine[row][col]);
            }
        }
        
        infoPanel.resetFlags(numMines);
    }
    
    public void updateDifficultySettings(int rows, int cols, int mines) {
        // Set new rows, cols and mines
        ROWS = rows;
        COLS = cols;
        numMines = mines;
        
        // Resize Array
        cells = new Cell[ROWS][COLS];
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                cells[row][col] = new Cell(row, col);
                cells[row][col].addMouseListener(listener);
            }
        }
        
        newGame();
        
        // Calculate updated canvas size
        CANVAS_WIDTH = CELL_SIZE * cols;
        CANVAS_HEIGHT = CELL_SIZE * rows;
    
        // Remove all components from the panel
        removeAll();

        // Create game layered pane
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

        // Create game panel
        JPanel gamePanel = new JPanel(new GridLayout(ROWS, COLS, 2, 2));
        gamePanel.setBounds(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                gamePanel.add(cells[row][col]);
            }
        }
        layeredPane.add(gamePanel, JLayeredPane.DEFAULT_LAYER); // Add game panel to default layer

        // Create and add the end panel to a higher layer
        endPanel = new EndBoardPanel();
        endPanel.passPanel(infoPanel); // Pass infoPanel to new endPanel
        // Calculate position and size relative to the game board
        int endPanelWidth = CELL_SIZE * 6; // Adjust width as needed
        int endPanelHeight = CELL_SIZE * 6; // Adjust height as needed
        int endPanelX = (CANVAS_WIDTH - endPanelWidth) / 2;
        int endPanelY = (CANVAS_HEIGHT - endPanelHeight) / 2;
        endPanel.setBounds(endPanelX, endPanelY, endPanelWidth, endPanelHeight);
        endPanel.setVisible(false);
        layeredPane.add(endPanel, JLayeredPane.PALETTE_LAYER); // Add end panel to higher layer

        // Add game layered pane to the card layout
        add(layeredPane, "GamePanel");

        // Set the preferred size of the main board panel
        setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
    
        // Revalidate and repaint the parent container
        getParent().revalidate();
        getParent().repaint();

        //Resize the main JFrame
        SwingUtilities.getWindowAncestor(this).pack();
    }
    
    private int getSurroundingMines(int srcRow, int srcCol)
    {
        int numMines = 0; //Set surrounding mines to 0
        
        for (int row = srcRow -1; row <= srcRow + 1; row++)
        {
            for (int col = srcCol -1; col <= srcCol + 1; col++)
            { 
                if (row >= 0 && row < ROWS && col >= 0 && col < COLS) //Ensure valid cell
                {
                    if (cells[row][col].isMine) numMines++;    
                }
            }
        }
        
        return numMines;
    }
    
    public void revealCell(int srcRow, int srcCol)
    {
        int numMines = getSurroundingMines(srcRow, srcCol);
        cells[srcRow][srcCol].setText(numMines + "");
        cells[srcRow][srcCol].isRevealed = true;
        cells[srcRow][srcCol].paint();
        
        if (numMines == 0)
        {
            cells[srcRow][srcCol].setText("");
            cells[srcRow][srcCol].paint();

            for (int row = srcRow -1; row <= srcRow + 1; row++)
            {
                for (int col = srcCol -1; col <= srcCol + 1; col++)
                {
                    if (row >= 0 && row < ROWS && col >= 0 && col < COLS) //Ensure valid cell
                        if (!cells[row][col].isRevealed && !cells[row][col].isFlagged) revealCell(row, col);
                }
            }
        }
    }
    
    private class CellMouseListener extends MouseAdapter
    {
        public void mouseClicked(MouseEvent e) 
        {
            Cell sourceCell = (Cell)e.getSource(); //Get Cell Clicked
            
            if (e.getButton() == MouseEvent.BUTTON1) //Left Mouse Click
            {                    
                if (!gameOver && !gameWon && gameStarted)
                { 
                    if (sourceCell.isMine && !sourceCell.isFlagged) //If cell is mine and not flagged
                    {
                        gameOver();
                    }
                    else if (!sourceCell.isFlagged && !sourceCell.isMine)
                    {
                        revealCell(sourceCell.row, sourceCell.col);
                    }
                }
                
                if (!gameStarted) // If first click
                {
                    mineMap.newMineMap(thisPanel, numMines, sourceCell.row, sourceCell.col);
                    gameStarted = true;
                    
                    infoPanel.startTimer();
                }
                
            }
            else if (e.getButton() == MouseEvent.BUTTON3) //Right Mouse Click
            {
                if (!gameOver && !gameWon && gameStarted && !cells[sourceCell.row][sourceCell.col].isRevealed)
                {
                    if (sourceCell.isFlagged) //Unflag
                    {
                        sourceCell.isFlagged = false; //If flagged, unflag
                        cells[sourceCell.row][sourceCell.col].paint(); //Repaint Cell
 
                        infoPanel.increaseFlag();
                    }          
                    else //Flag
                    {
                        sourceCell.isFlagged = true; //If not flagged, flag
                        cells[sourceCell.row][sourceCell.col].paint(); //Repaint cell
                    
                        infoPanel.decreaseFlag();
                    }
                }
            }
            
            // Win Condition
            boolean allRevealed = true;

            for (int row = 0; row < ROWS; row++)
            {
                for (int col = 0; col < COLS; col++)
                {
                    if (!cells[row][col].isRevealed && !mineMap.isMine[row][col]) // If not revealed && not a mine
                        allRevealed = false;
                }
            }
            
            if (allRevealed) gameWin(); // If all safe cells are revealed
        }
    }
    
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
     
    public void gameOver()
    {
        gameOver = true;
        
        // Reveal all mines to player
        for (int row = 0; row < ROWS; row++)
        {
            for (int col = 0; col < COLS; col++)
            {
                if (cells[row][col].isMine && !cells[row][col].isFlagged) // If a mine and is not revealed or flagged
                    cells[row][col].isRevealed = true;

                if (cells[row][col].isFlagged && !cells[row][col].isMine) // If flagged but not a mine
                    cells[row][col].isCrossed = true;

                cells[row][col].paint();
            }
        }

        endPanel.setVisible(true);
        repaint();
        revalidate();
        
        // Stop timer
        infoPanel.stopTimer();

        // Pass to End Panel
        endPanel.updateLabels(0, HighScoreManager.getHighScore(infoPanel.currentDifficulty), true);
    }
       
    public void passPanels(InfoBoardPanel panel, MineSweeperMain main)
    {
        this.infoPanel = panel;
        this.main = main;
        
        mineMap = new MineMap(infoPanel, ROWS, COLS);
        infoPanel.passPanel(this, mineMap);
        endPanel.passPanel(infoPanel);
    }
}