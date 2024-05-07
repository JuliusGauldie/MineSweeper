
/**
 * Write a description of class MineSweeperMain here.
 *
 * @author Julius Gauldie
 * @version 07/05/2024
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class MineSweeperMain extends JFrame
{
    GameBoardPanel board = new GameBoardPanel();
    JButton btnNewgame = new JButton("New Game");
    /**
     * Constructor for objects of class MineSweeperMain
     */
    public MineSweeperMain()
    {
        Container cp = this.getContentPane();
        cp.setLayout(new BorderLayout());
        
        cp.add(board, BorderLayout.CENTER);
        
        board.newGame();
        
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
