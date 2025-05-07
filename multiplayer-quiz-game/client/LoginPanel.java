package client;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {
    public LoginPanel(QuizClient client) {
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(50, 60, 50, 60));

        // Title
        JLabel titleLabel = new JLabel("Welcome to the Quiz Game", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        add(titleLabel, BorderLayout.NORTH);

        // Center form panel
        JPanel formPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        JLabel nameLabel = new JLabel("Enter your name:", SwingConstants.CENTER);
        nameLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        
        JTextField nameField = new JTextField();
        nameField.setFont(new Font("SansSerif", Font.PLAIN, 16));

        JButton connectBtn = new JButton("Connect");
        connectBtn.setFont(new Font("SansSerif", Font.BOLD, 16));

        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(connectBtn);

        add(formPanel, BorderLayout.CENTER);

        // Button action
        connectBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            if (!name.isEmpty()) {
                client.connectToServer(name);
            } else {
                JOptionPane.showMessageDialog(this, "Please enter your name.", "Missing Name", JOptionPane.WARNING_MESSAGE);
            }
        });
    }
}
