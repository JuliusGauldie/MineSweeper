
/**
 * Write a description of class GameBoardPanel here.
 *
 * @author Julius Gauldie
 * @version 11/06/24
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class MainBoardPanel extends JPanel
{
    //Import MineSweeper Constants
    public static final int ROWS = 10; //Number of rows
    public static final int COLS = 10; //Number of columns
    
    //Constants for UI
    public static final int CELL_SIZE = 60; //Cell width and height
    public static final int CANVAS_WIDTH = CELL_SIZE * COLS; //Game Board widht/height
    public static final int CANVAS_HEIGHT = CELL_SIZE * ROWS;
    
    //Mouse Variables
    CellMouseListener listener = new CellMouseListener();
    
    public Cell cells[][] = new Cell[ROWS][COLS];
    int numMines = 10; //Set number of mines

    MineSweeperMain main;
    InfoBoardPanel infoPanel;
    private EndBoardPanel endPanel;
    private MineMap mineMap;
    private MainBoardPanel thisPanel = this;

    private boolean gameStarted = false;
    private boolean gameOver = false;
    private boolean gameWon = false;
    
    private JPanel mainMenuPanel;
    private JButton startButton;

    /**
     * Constructor for objects of class GameBoardPanel
     */
    public MainBoardPanel() 
    {
        super(new CardLayout()); // Use CardLayout for the panel

        // Create main menu panel
        mainMenuPanel = new JPanel(new BorderLayout());
        startButton = new JButton("Start Game");
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newGame();
                CardLayout cl = (CardLayout) (MainBoardPanel.this.getLayout());
                cl.show(MainBoardPanel.this, "GamePanel"); // Switch to the game panel
            }
        });
        mainMenuPanel.add(startButton, BorderLayout.CENTER);

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
        endPanel.setBounds((CANVAS_WIDTH / 2) - (2 * CELL_SIZE), (CANVAS_HEIGHT / 2) - (2  * CELL_SIZE), CELL_SIZE * 4, CELL_SIZE * 4);
        endPanel.setVisible(false);
        layeredPane.add(endPanel, JLayeredPane.PALETTE_LAYER); // Add end panel to higher layer

        // Add main menu panel and game layered pane to the card layout
        super.add(mainMenuPanel, "MainMenu");
        super.add(layeredPane, "GamePanel");

        // Set the preferred size of the main board panel
        super.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
    }
    
    public void passPanels(InfoBoardPanel panel, MineSweeperMain main)
    {
        this.infoPanel = panel;
        this.main = main;
        
        infoPanel.passPanel(this);
        mineMap = new MineMap(infoPanel);
    }
    
    public void newGame() //Reset game
    {
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
                        sourceCell.setText("*");
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
                if (!gameOver && !gameWon && gameStarted)
                {
                    if (sourceCell.isFlagged) //Unflag
                    {
                        sourceCell.isFlagged = false; //If flagged, unflag
                        cells[sourceCell.row][sourceCell.col].isRevealed = false;
                        cells[sourceCell.row][sourceCell.col].paint(); //Repaint Cell
 
                        infoPanel.increaseFlag();
                    }          
                    else //Flag
                    {
                        sourceCell.isFlagged = true; //If not flagged, flag
                        cells[sourceCell.row][sourceCell.col].isRevealed = true;
                        cells[sourceCell.row][sourceCell.col].paint(); //Repaint cell
                    
                        infoPanel.decreaseFlag();
                    }
                }
            }
            else if (e.getButton() == MouseEvent.BUTTON2) //Middle Mouse Button Click
            {
                if (!gameWon) gameWin();
            }
            
            if (infoPanel.amountOfFlags == 0) //Win Condition
            {
                boolean allRevealed = true;
                
                for (int row = 0; row < ROWS; row++)
                {
                    for (int col = 0; col < COLS; col++)
                    {
                        if (!cells[row][col].isRevealed)
                        {
                            allRevealed = false;
                        }
                    }
                }
                
                if (allRevealed) gameWin();
            }
        }
    }
    
    public void gameWin()
    {        
        gameWon = true;
        endPanel.setVisible(true);
        repaint();
        revalidate();
        
        infoPanel.stopTimer();
    }
    
    public void gameOver()
    {
        gameOver = true;
        infoPanel.stopTimer();
    }
}