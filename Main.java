import java.util.Scanner;

public class Main {
    private static ChatClient client;
    private static final Scanner scanner = new Scanner(System.in);
    private static boolean loggedIn = false;

    public static void main(String[] args) {
        System.out.println("Welcome to the PROG1 chat board!");
        System.out.print("Please, enter your authentication key: ");
        String authKey = scanner.nextLine();

        client = new ChatClient("vm3.mcc.tu-berlin.de", 8080, authKey);

        while (true) {
            if (!loggedIn) {
                System.out.println("Please, enter one of the following commands:");
                System.out.println("1 - Register user");
                System.out.println("0 - Login");
                System.out.println("2 - Unblock user");
                System.out.println("3 - Exit");

                int command = scanner.nextInt();
                scanner.nextLine();

                switch (command) {
                    case 1:
                        System.out.println("Creating a new user...");
                        loggedIn = registerUser();
                        if (loggedIn) {
                            System.out.println("Registration successful. You are now logged in.");
                        }
                        break;
                    case 0:
                        System.out.println("Logging in...");
                        loggedIn = loginUser();
                        break;
                    case 2:
                        System.out.println("Unblocking user...");
                        unblockUser();
                        break;
                    case 3:
                        System.out.println("Exiting...");
                        System.exit(0);
                    default:
                        System.out.println("Invalid command, please try again.");
                }
            } else {
                showLoggedInMenu();
            }
        }
    }

    private static void showLoggedInMenu() {
        System.out.println("Please, enter one of the following commands:");
        System.out.println("4 - Create post");
        System.out.println("5 - Delete post");
        System.out.println("6 - Vote on a post");
        System.out.println("7 - Get board");
        System.out.println("8 - Logout");

        int command = scanner.nextInt();
        scanner.nextLine();

        switch (command) {
            case 4:
                createPost();
                break;
            case 5:
                deletePost();
                break;
            case 6:
                vote();
                break;
            case 7:
                getBoard();
                break;
            case 8:
                System.out.println("Logged out.");
                boolean loggedIn = false;
                break;
            default:
                System.out.println("Invalid command, please try again.");
        }
    }

    private static boolean loginUser() {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Security number: ");
        int secNr = scanner.nextInt();
        scanner.nextLine();
        String response = client.login(username, secNr);
        if (response.contains("Authentication ok")) {
            System.out.println("Login successful.");
            return true;
        } else {
            System.out.println("Login failed: " + response);
            return false;
        }
    }

    private static boolean registerUser() {
        System.out.print("Please, enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Please, enter your security number: ");
        int securityNumber = scanner.nextInt();
        scanner.nextLine();

        String response = client.register(username, securityNumber);
        System.out.println("Server response: " + response);

        if (response.toLowerCase().contains("registration successful")) {
            return true;
        } else {
            System.out.println("Registration failed: " + response);
            return false;
        }
    }

    private static void unblockUser() {
        System.out.print("Username to unblock: ");
        String username = scanner.nextLine();
        System.out.print("Security number: ");
        int secNr = scanner.nextInt();
        scanner.nextLine();
        client.unblockUser(username, secNr);
    }

    private static void createPost() {
        System.out.print("Post content: ");
        String content = scanner.nextLine();
        String username = "exampleUsername";
        int secNr = 1234;
        client.createPost(username, secNr, content);
    }

    private static void deletePost() {
        System.out.print("Post ID to delete: ");
        int postId = scanner.nextInt();
        scanner.nextLine();
        String username = "exampleUsername";
        int secNr = 1234;
        client.deletePost(username, secNr, postId);
    }

    private static void vote() {
        System.out.print("Post ID to vote on: ");
        int postId = scanner.nextInt();
        System.out.print("Vote type (up/down): ");
        String voteType = scanner.next();
        scanner.nextLine();
        String username = "exampleUsername";
        int secNr = 1234;
        client.vote(username, secNr, postId, voteType);
    }

    private static void getBoard() {
        System.out.print("Number of posts to retrieve: ");
        int count = scanner.nextInt();
        scanner.nextLine();
        String username = "exampleUsername";
        int secNr = 1234;
        client.getBoard(username, secNr, count);
    }
}
