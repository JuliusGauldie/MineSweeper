
/**
 * Write a description of class InfoBoardPanel here.
 *
 * @author Julius Gauldie
 * @version 14/05/24
 */
import java.awt.*;
import javax.swing.*;
public class InfoBoardPanel extends JPanel
{
    // instance variables - replace the example below with your own
    private int amountOfFlags = 0;
    private int currentTime = 0;
    
     JLabel l1 = new JLabel(" "); 

    /**
     * Constructor for objects of class InfoBoardPanel
     */
    public InfoBoardPanel()
    {
        super.setLayout(new GridLayout(1, 1, 1, 1));
        l1.setText("AMOUNT OF FLAGS LEFT: " + amountOfFlags);  
        super.add(l1);
    }
    
    public void ResetFlags()
    {
        System.out.println("check");
        l1.setText("TEST");
    }
}
