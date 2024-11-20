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
        System.out.print("Enter number of players: ");
        int numberOfPlayers = sc.nextInt();
        sc.nextLine();

        for (int i = 1; i <= numberOfPlayers; i++) {
            System.out.print("Enter nickname #" + i + ": ");
            String name = sc.nextLine();
            players.add(name);
        }
        return players.get(0);
    }
}