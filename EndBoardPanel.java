
/**
 * Write a description of class WinPanel here.
 *
 * @author Julius Gauldie
 * @version 27/05/24
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class EndBoardPanel extends JPanel {
    private JLabel messageLabel;

    public EndBoardPanel() {
        setLayout(new BorderLayout());

        // Create the message label
        messageLabel = new JLabel();
        messageLabel.setHorizontalAlignment(JLabel.CENTER);
        add(messageLabel, BorderLayout.CENTER);

        setVisible(false); // Initially hide the end panel
    }

    // Method to set the message displayed in the end panel
    public void setMessage(String message) {
        messageLabel.setText(message);
    }
}