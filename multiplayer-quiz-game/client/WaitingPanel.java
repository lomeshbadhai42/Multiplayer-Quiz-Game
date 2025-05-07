package client;

import javax.swing.*;
import java.awt.*;

public class WaitingPanel extends JPanel {
    public WaitingPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(100, 50, 100, 50));

        JLabel waitingLabel = new JLabel("⏳ Waiting for other players to join...", SwingConstants.CENTER);
        waitingLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        waitingLabel.setForeground(new Color(50, 50, 150));

        add(waitingLabel, BorderLayout.CENTER);
    }
}
