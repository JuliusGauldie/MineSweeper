
/**
 * Write a description of class MineMap here.
 *
 * @author Julius Gauldie
 * @version 06/06/24
 */
import java.util.Random;
public class MineMap
{
    MineSweeperConstants constants = new MineSweeperConstants();
    int ROWS = constants.ROWS;
    int COLS = constants.COLS;

    int numMines; //Number of mines
        
    boolean[][] isMine = new boolean[ROWS][COLS]; //Array for mines
    
    InfoBoardPanel infoPanel;
        
    /**
     * Constructor for objects of class MineMap
     */
    public MineMap(InfoBoardPanel infoPanel)
    {
        super();
        this.infoPanel = infoPanel;
    }
    
    public void newMineMap(int numMines) 
    {
        this.numMines = numMines;
        
        //Clear all existing mines
        for (int i = 0; i < ROWS; i++)
        {
            for (int j = 0; j < COLS; j++)
            {
                isMine[i][j] = false;
            }
        }
        
        Random rand = new Random();
        
        //Place random mines
        for (int i = 0; i < numMines; i++)
        {
            int randomRow = rand.nextInt(ROWS);
            int randomCol = rand.nextInt(COLS);
            
            if (!isMine[randomRow][randomCol])
            {
                isMine[randomRow][randomCol] = true;
            }
            else i--;
        }
  
        //Update Flags Info Panel
        infoPanel.resetFlags(numMines); 
    }
}
