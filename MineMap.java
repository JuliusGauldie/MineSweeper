
/**
 * Write a description of class MineMap here.
 *
 * @author Julius Gauldie
 * @version 07/05/24
 */
public class MineMap
{
    MineSweeperConstants constants = new MineSweeperConstants();
    int ROWS = constants.ROWS;
    int COLS = constants.COLS;

    int numMines; //Number of mines
        
    boolean[][] isMined = new boolean[ROWS][COLS]; //Array for mines
        
    /**
     * Constructor for objects of class MineMap
     */
    public MineMap()
    {
        super();
    }
    
    public void newMineMap(int numMines) 
    {
        this.numMines = numMines;
        
        //For testing, assume numMines == 10
        isMined[0][0] = true;
        isMined[5][2] = true;
        isMined[9][5] = true;
        isMined[6][7] = true;
        isMined[8][2] = true;
        isMined[2][4] = true;
        isMined[5][7] = true;
        isMined[7][7] = true;
        isMined[3][6] = true;
        isMined[4][8] = true;
    }
}
