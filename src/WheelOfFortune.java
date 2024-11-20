import java.util.*;

public class WheelOfFortune {
    public static final Scanner sc = new Scanner(System.in);
    public static final ArrayList<String> players = new ArrayList<>();
    public static final Random random = new Random();
    public static final String redColor = "\u001B[31m";
    public static final String resetColor = "\u001B[0m";
    public static final String blueColor = "\u001B[44m";

    public static void main(String[] args) {
        printWelcomeMessage();
        cleanAll();
        askNameOfPlayers();
    }

    static void cleanAll() {
        System.out.println("\033[H\033[J");
    }
    static void printWelcomeMessage() {
        String welcomeMessage =
                "----------------------------------------WELCOME!----------------------------------------\n" +
                        "--------------------------------WHEEL OF FORTUNE GAME-----------------------------------\n";

        System.out.println(blueColor + welcomeMessage + resetColor);
        System.out.println("START OF THE GAME!");
    }

    static String askNameOfPlayers() {
        System.out.println("Enter your nicknames." +
                    "\nTo finish leave the line blank and press" + redColor + " ENTER. " + resetColor);

            while (true) {
                System.out.println("Nickname:");
                String nickname = sc.nextLine();
                if (nickname.isEmpty()) {
                    break;
                }
                players.add(nickname);
            }
            if (players.isEmpty()) {
                System.out.println("No players found. Game is over.");
                return null;
            }
            Collections.shuffle(players);
            System.out.println("Players: " + players);
            System.out.println("The order of moves is determined randomly.");

        return players.get(0);}
    }