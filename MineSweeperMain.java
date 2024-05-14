
/**
 * Write a description of class MineSweeperMain here.
 *
 * @author Julius Gauldie
 * @version 14/05/2024
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class MineSweeperMain extends JFrame
{
    GameBoardPanel gameBoard = new GameBoardPanel();
    InfoBoardPanel infoBoard = new InfoBoardPanel();
    
    JButton btnNewgame = new JButton("New Game");
    /**
     * Constructor for objects of class MineSweeperMain
     */
    public MineSweeperMain()
    {
        Container cp = this.getContentPane();
        cp.setLayout(new BorderLayout());
        
        cp.add(infoBoard, BorderLayout.NORTH);
        cp.add(gameBoard, BorderLayout.CENTER);
        
        gameBoard.newGame();
        
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Minesweeper");
        setVisible(true);
    }
    
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MineSweeperMain();
            }
        });
    }
}