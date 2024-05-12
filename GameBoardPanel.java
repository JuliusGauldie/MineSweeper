
/**
 * Write a description of class GameBoardPanel here.
 *
 * @author Julius Gauldie
 * @version 07/05/24
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class GameBoardPanel extends JPanel
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
    
    Cell cells[][] = new Cell[ROWS][COLS];
    int numMines = 10; //Set number of mines

    /**
     * Constructor for objects of class GameBoardPanel
     */
    public GameBoardPanel()
    {
        super.setLayout(new GridLayout(ROWS, COLS, 2, 2)); //JPanel
        
        //Populate array
        for (int row = 0; row < ROWS; row++)
        {
            for (int col = 0; col < COLS; col++)
            {
                cells[row][col] = new Cell(row, col);
                cells[row][col].addMouseListener(listener); //On all rows and cols
                super.add(cells[row][col]);
            }
        }
        
        newGame();
        
        super.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
    }
    
    public void newGame() //Reset game
    {
        MineMap mineMap = new MineMap(); //Reset bombs
        mineMap.newMineMap(numMines);
        
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
                    if (cells[row][col].isMine) numMines++;
            }
        }
        
        return numMines;
    }
    
    private void revealCell(int srcRow, int srcCol)
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
    
    public boolean hasWon() 
    {
        return true;
    }
    
    private class CellMouseListener extends MouseAdapter
    {
        public void mouseClicked(MouseEvent e) 
        {
            Cell sourceCell = (Cell)e.getSource(); //Get Cell Clicked
            
            if (e.getButton() == MouseEvent.BUTTON1) //Left Mouse Click
            {
                if (sourceCell.isMine) //If cell is mine
                {
                    System.out.println("GAME OVER");
                    sourceCell.setText("*");
                }
                else if (!sourceCell.isFlagged && !sourceCell.isMine)
                {
                    System.out.println(sourceCell.row + " " + sourceCell.col);
                    revealCell(sourceCell.row, sourceCell.col);
                }
            }
            else if (e.getButton() == MouseEvent.BUTTON3) //Right Mouse Click
            {
                if (sourceCell.isFlagged)
                {
                    sourceCell.isFlagged = false; //If flagged, unflag
                    cells[sourceCell.row][sourceCell.col].paint(); //Repaint Cell
                }          
                else
                {
                    sourceCell.isFlagged = true; //If not flagged, flag
                    System.out.println(sourceCell.row + " " + sourceCell.col + " " + sourceCell.isFlagged);
                    cells[sourceCell.row][sourceCell.col].paint(); //Repaint cell
                }
            }
        }
    }
}
