# 🎓 Multiplayer Quiz Game

This project is a Java-based application designed to **demonstrate socket programming** using a simple multiplayer quiz game. It shows how TCP communication can be used between a server and multiple clients to coordinate a quiz session in real-time.

---

## 📁 Folder Structure

```
multiplayer-quiz-game/
├── server/   # Server-side logic (listens for clients, sends questions, collects answers)
├── client/   # Client-side GUI to receive questions and submit answers
└── shared/   # Shared classes (e.g., Question, Answer, common utils)
```

---

## 🔧 Requirements

- Java 8 or later
- Terminal or Command Prompt

---

## 🚀 How to Compile and Run

### Step 1: Update QuizClient.java (If you are using different machines) 
From the root `multiplayer-quiz-game/client` folder:
Line no 51
```code
Socket socket = new Socket("localhost",5000);
```
Here replace "localhost" with server machines IP address.
To check IP run command
```bash
ipconfig  (windows)
ifconfig  (linux)
```
### Step 2: Compile All The Codes
From the root `multiplayer-quiz-game/` folder:

```bash
javac server/*.java client/*.java shared/*.java
```
### Step 3: Run the Server
```bash
java server.QuizServer
```
### Step 4: Run the Clients (in separate terminals or in different machines)
```bash
java client.QuizClient
```
## 📡 How It Works
- The Server starts
- The Server waits for multiple clients to connect.
- Once all players are connected, it sends questions one by one using sockets.
- Each Client displays the questions in a Swing GUI and allows user input.
- After each questions , the server collects all answers and sends scores and updates the player's points.
- At the end, a leaderboard is displayed.

## 🎯 Learning Objectives
- Understand TCP socket communication in Java
- Learn to use ServerSocket, Socket, ObjectInputStream, ObjectOutputStream
- Handle concurrent clients using threads
- Share data using serializable objects
- Build a basic Swing GUI application

## 📷 Screenshots
- Player1 Connecting To Server
- ![NatashaLogin](https://github.com/user-attachments/assets/a950aa8d-6f2e-460a-a740-c1edb0f812b1)
- Waiting for other players
- ![WaitingForOtherPlayers](https://github.com/user-attachments/assets/d84b4bd5-3bdd-40a9-8cac-9203cdc24058)
- Player2 Connecting To Server
- ![WandaLogin](https://github.com/user-attachments/assets/664a9d2c-f99e-4079-9f20-a3f3c0c2b936)
- Questions
- ![Questions](https://github.com/user-attachments/assets/a8ccdefc-5a30-4f6e-8362-dd57ae0edf93)
- Leader Board
- ![LeaderBoard](https://github.com/user-attachments/assets/bd87ce3f-cc78-4918-987d-b3752a98fa4d)

## 👨‍💻 Author
- Lomesh Badhai
- Somnath Ratrey
