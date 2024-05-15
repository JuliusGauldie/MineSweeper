
/**
 * Write a description of class MineMap here.
 *
 * @author Julius Gauldie
 * @version 16/05/24
 */
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
        
        //For testing, assume numMines == 10
        isMine[0][0] = true;
        isMine[5][2] = true;
        isMine[9][5] = true;
        isMine[6][7] = true;
        isMine[8][2] = true;
        isMine[2][4] = true;
        isMine[5][7] = true;
        isMine[7][7] = true;
        isMine[3][6] = true;
        isMine[4][8] = true;
        
        //Update Flags Info Panel
        infoPanel.resetFlags(numMines); 
    }
}
