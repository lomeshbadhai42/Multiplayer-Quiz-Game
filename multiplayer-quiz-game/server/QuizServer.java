package server;

import shared.Question;
import shared.PlayerData;

import java.io.*;
import java.net.*;
import java.util.*;

public class QuizServer {
    private static final int PORT = 5000;
    private static final int MAX_PLAYERS = 2; // you can increase this later

    private static List<ClientHandler> clients = new ArrayList<>();
    private static List<Question> questions = new ArrayList<>();

    public static void main(String[] args) {
        loadQuestions();

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started on port " + PORT + ". Waiting for players...");

            while (clients.size() < MAX_PLAYERS) {
                Socket socket = serverSocket.accept();
                System.out.println("Player connected: " + socket);
                ClientHandler client = new ClientHandler(socket);
                clients.add(client);
                new Thread(client).start();
            }

            // Wait for all threads to initialize
            Thread.sleep(1000);

            // All players connected
            broadcastMessage("START");

            // Send all questions
            for (Question q : questions) {
                broadcastQuestion(q);

                for (ClientHandler client : clients) {
                    client.waitForAnswer(q);
                }
            }

            // Send results
            broadcastResults();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loadQuestions() {
        questions.add(new Question("1.Which is not a transmission medium?", new String[]{"Twisted pair", "Coaxial", "Optical fiber", "Router"}, 3));
questions.add(new Question("2.What does HTTP stand for?", new String[]{"Hyper Text Transfer Protocol", "High Text Transfer Protocol", "Hyperlink Transfer Protocol", "Hyper Transfer Text Protocol"}, 0));
questions.add(new Question("3.Which OSI layer handles end-to-end delivery?", new String[]{"Network", "Session", "Transport", "Data Link"}, 2));
questions.add(new Question("4.Which protocol sends emails?", new String[]{"FTP", "HTTP", "SMTP", "SNMP"}, 2));
questions.add(new Question("5.Which IP class has most hosts?", new String[]{"Class A", "Class B", "Class C", "Class D"}, 0));
questions.add(new Question("6.Which is a connection-oriented protocol?", new String[]{"UDP", "IP", "TCP", "ICMP"}, 2));
questions.add(new Question("7.Default port for HTTPS?", new String[]{"21", "80", "443", "23"}, 2));
questions.add(new Question("8.Which device links networks?", new String[]{"Switch", "Hub", "Bridge", "Router"}, 3));
questions.add(new Question("9. protocol gives IPs?", new String[]{"DNS", "DHCP", "FTP", "ARP"}, 1));
questions.add(new Question("10.MAC address length?", new String[]{"32 bits", "48 bits", "64 bits", "128 bits"}, 1));

        // Add more as needed
    }

    private static void broadcastMessage(String msg) throws IOException {
        for (ClientHandler client : clients) {
            client.sendText(msg);
        }
    }

    private static void broadcastQuestion(Question q) throws IOException {
        for (ClientHandler client : clients) {
            client.sendQuestion(q);
        }
    }

    private static void broadcastResults() throws IOException {
        List<PlayerData> resultList = new ArrayList<>();
        for (ClientHandler client : clients) {
            resultList.add(client.getPlayer());
        }

        for (ClientHandler client : clients) {
            client.sendResults(resultList);
        }
    }

    static class ClientHandler implements Runnable {
        private Socket socket;
        private ObjectOutputStream out;
        private ObjectInputStream in;
        private PlayerData player;
        private int currentAnswer = -1;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public PlayerData getPlayer() {
            return player;
        }

        public void run() {
            try {
                out = new ObjectOutputStream(socket.getOutputStream());
                in = new ObjectInputStream(socket.getInputStream());

                String name = (String) in.readObject();
                player = new PlayerData(name);

                System.out.println("Player name: " + name);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void waitForAnswer(Question currentQuestion) {
            try {
                currentAnswer = in.readInt();
                if (currentAnswer == currentQuestion.correctIndex) {
                    player.score++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void sendText(String msg) throws IOException {
            out.writeObject(msg);
            out.flush();
        }

        public void sendQuestion(Question q) throws IOException {
            out.writeObject(q);
            out.flush();
        }

        public void sendResults(List<PlayerData> players) throws IOException {
            out.writeObject(players);
            out.flush();
        }
    }
}
