
/**
 * Creates main program - should be run to start game
 *
 * Original concept by Robert Donner and Curt Johnson, 1989.
 * @author Julius Gauldie
 * @version 29/06/2024
 */
import java.awt.*;
import javax.swing.*;
public class MineSweeperMain extends JFrame
{
    MainBoardPanel mainBoard = new MainBoardPanel(); // Game Board Panel
    InfoBoardPanel infoBoard = new InfoBoardPanel(); // Info Board Panel

    /**
     * Constructor for objects of class MineSweeperMain
     */
    public MineSweeperMain()
    {
        Container cp = this.getContentPane(); // Main content pane for JFrame
        setResizable(false); // Prevent player from resizing game
        cp.setLayout(new BorderLayout()); 
        
        // Add InfoBoard to the top
        cp.add(infoBoard, BorderLayout.NORTH);

        // Add MainBoard below InfoBoard
        cp.add(mainBoard, BorderLayout.SOUTH);
        
        // Pass MainBoardPanel curernt instance and Infoboardpanel instance
        mainBoard.passPanels(infoBoard, this);

        // Start a new game initially
        mainBoard.newGame();
        
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
