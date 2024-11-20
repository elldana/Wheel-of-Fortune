import java.util.*;

public class WheelOfFortune {
    public static final Scanner sc = new Scanner(System.in);
    public static final ArrayList<String> players = new ArrayList<>();
    public static final Random random = new Random();

    public static void main(String[] args) {
        printWelcomeMessage();
        cleanAll();
    }

    static void cleanAll() {
        System.out.println("\033[H\033[J");
    }
    static void printWelcomeMessage() {
        String resetColor = "\u001B[0m";
        String blueColor = "\u001B[44m";
        String welcomeMessage =
                "----------------------------------------WELCOME!----------------------------------------\n" +
                        "--------------------------------WHEEL OF FORTUNE GAME-----------------------------------\n";

        System.out.println(blueColor + welcomeMessage + resetColor);
    }
    }