import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class ChatClient {
    private String host;
    private int port;
    private String authKey;

    public ChatClient(String host, int port, String authKey) {
        this.host = host;
        this.port = port;
        this.authKey = authKey;
    }

    private String communicateWithServer(String command) {
        String response = "";
        try (Socket socket = new Socket(host, port);
             OutputStream out = socket.getOutputStream();
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.write(authKey.getBytes());
            out.write(-1);

            out.write(command.getBytes());
            out.write(-1);
            out.flush();

            StringBuilder responseBuilder = new StringBuilder();
            int ch;
            while ((ch = in.read()) != -1) {
                responseBuilder.append((char) ch);
            }

            response = responseBuilder.toString();
            if (response.toLowerCase().contains("error") || response.toLowerCase().contains("invalid")) {
                System.err.println("Server responded with an error: " + response);
            }
        } catch (Exception e) {
            System.err.println("Error communicating with server: " + e.getMessage());
        }
        return response.trim();
    }

    public String register(String username, int securityNumber) {
        String command = "0 " + username + " " + securityNumber;
        String response = communicateWithServer(command);
        System.out.println("Response: " + response);
        return command;
    }

    public String login(String username, int securityNumber) {
        String command = "0 " + username + " " + securityNumber;
        String response = communicateWithServer(command);
        System.out.println("Response: " + response);
        return command;
    }

    public void unblockUser(String username, int securityNumber) {
        String command = "1 " + username + " " + securityNumber;
        String response = communicateWithServer(command);
        System.out.println("Response: " + response);
    }

    public void createPost(String username, int securityNumber, String content) {
        String command = "2 " + username + " " + securityNumber + " " + content;
        String response = communicateWithServer(command);
        System.out.println("Response: " + response);
    }

    public void deletePost(String username, int securityNumber, int postId) {
        String command = "3 " + username + " " + securityNumber + " " + postId;
        String response = communicateWithServer(command);
        System.out.println("Response: " + response);
    }

    public void vote(String username, int securityNumber, int postId, String voteType) {
        String command = "4 " + username + " " + securityNumber + " " + postId + " " + voteType;
        String response = communicateWithServer(command);
        System.out.println("Response: " + response);
    }

    public void getBoard(String username, int securityNumber, int count) {
        String command = "5 " + username + " " + securityNumber + " " + count;
        String response = communicateWithServer(command);
        System.out.println("Response: " + response);
    }
}
