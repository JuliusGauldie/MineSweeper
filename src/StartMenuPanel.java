/**
 * Logic for Main Menu - Shown on start
 *
 * @author Julius Gauldie
 * @version 24/06/24
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class StartMenuPanel extends JPanel {
    private JButton startButton;

    public StartMenuPanel(MainBoardPanel mainBoardPanel) {
        setLayout(new BorderLayout());

        // Title label
        JLabel titleLabel = new JLabel("MINESWEEPER", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        add(titleLabel, BorderLayout.NORTH);

        // Start button
        startButton = new JButton("Click To Start Game");
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainBoardPanel.newGame();
                CardLayout cl = (CardLayout) (mainBoardPanel.getLayout());
                cl.show(mainBoardPanel, "GamePanel");
            }
        });
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.add(startButton);
        add(centerPanel, BorderLayout.CENTER);

        // Credits label
        JLabel creditsLabel = new JLabel("Created by Julius Gauldie", SwingConstants.CENTER);
        creditsLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        add(creditsLabel, BorderLayout.SOUTH);
    }
}

