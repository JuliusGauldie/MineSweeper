
/**
 * Write a description of class WinPanel here.
 *
 * @author Julius Gauldie
 * @version 21/05/24
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class WinBoardPanel extends JPanel
{
    /**
     * Constructor for objects of class WinPanel
     */
    public WinBoardPanel()
    {
        super.setLayout(new GridLayout(50, 50, 50, 50));
        super.setVisible(false);
    }
    
    public void changeVisibility(boolean shouldBeVisible)
    {
        if (shouldBeVisible) super.setVisible(true);
        else super.setVisible(false);
        
        super.repaint();
    }
}
