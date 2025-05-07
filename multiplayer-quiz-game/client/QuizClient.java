package client;

import shared.Question;
import shared.PlayerData;

import javax.swing.*;
import java.awt.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class QuizClient extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private LoginPanel loginPanel;
    private WaitingPanel waitingPanel;
    private QuestionPanel questionPanel;
    private ResultPanel resultPanel;

    private ObjectInputStream in;
    private ObjectOutputStream out;

    public QuizClient() {
        setTitle("Multiplayer Quiz Game");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        loginPanel = new LoginPanel(this);
        waitingPanel = new WaitingPanel();
        questionPanel = new QuestionPanel(this);
        resultPanel = new ResultPanel();

        mainPanel.add(loginPanel, "login");
        mainPanel.add(waitingPanel, "waiting");
        mainPanel.add(questionPanel, "question");
        mainPanel.add(resultPanel, "result");

        add(mainPanel);
        cardLayout.show(mainPanel, "login");

        setVisible(true);
    }

    public void connectToServer(String name) {
        try {
            Socket socket = new Socket("localhost", 5000);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            out.writeObject(name);
            out.flush();

            cardLayout.show(mainPanel, "waiting");

            new Thread(() -> listenToServer()).start();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Connection failed!");
        }
    }

    private void listenToServer() {
        try {
            while (true) {
                Object obj = in.readObject();

                if (obj instanceof String) {
                    String message = (String) obj;
                    if (message.equals("START")) {
                        cardLayout.show(mainPanel, "question");
                    }
                } else if (obj instanceof Question) {
                    questionPanel.displayQuestion((Question) obj);
                } else if (obj instanceof List<?>) {
                    List<?> list = (List<?>) obj;

                    if (!list.isEmpty() && list.get(0) instanceof PlayerData) {
                        @SuppressWarnings("unchecked")
                        List<PlayerData> players = (List<PlayerData>) list;
                        resultPanel.displayResults(players);
                        cardLayout.show(mainPanel, "result");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendAnswer(int selectedIndex) {
        try {
            out.writeInt(selectedIndex);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Main method to launch the client
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new QuizClient());
    }
}
