package client;

import shared.PlayerData;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ResultPanel extends JPanel {
    private JLabel winnerLabel;
    private JTextArea leaderboardArea;

    public ResultPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30)); // Padding

        winnerLabel = new JLabel("", SwingConstants.CENTER);
        winnerLabel.setFont(new Font("Arial", Font.BOLD, 28));
        winnerLabel.setForeground(new Color(0, 128, 0));
        add(winnerLabel, BorderLayout.NORTH);

        leaderboardArea = new JTextArea();
        leaderboardArea.setEditable(false);
        leaderboardArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
        leaderboardArea.setBackground(new Color(245, 245, 245));
        leaderboardArea.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(leaderboardArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Leaderboard"));
        add(scrollPane, BorderLayout.CENTER);
    }

    public void displayResults(List<PlayerData> players) {
        players.sort((a, b) -> b.score - a.score); // sort descending

        // Winner
        PlayerData winner = players.get(0);
        winnerLabel.setText("🏆 Winner: " + winner.name + " - " + winner.score + " pts");

        // Leaderboard
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-3s %-20s %s\n", "#", "Player", "Score"));
        sb.append("-------------------------------------\n");

        int rank = 1;
        for (PlayerData p : players) {
            sb.append(String.format("%-3d %-20s %d pts\n", rank++, p.name, p.score));
        }

        leaderboardArea.setText(sb.toString());
    }
}
