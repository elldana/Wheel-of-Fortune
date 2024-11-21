import java.util.*;

public class WheelOfFortune {
    public static final Scanner sc = new Scanner(System.in);
    public static final ArrayList<String> players = new ArrayList<>();
    public static final Random random = new Random();

    public static final String greenColor = "\u001B[32m";
    public static final String redColor = "\u001B[31m";
    public static final String resetColor = "\u001B[0m";
    public static final String blueColorBackground = "\u001B[44m";
    public static final String blueColor = "\u001B[34m";

    public static void main(String[] args) {
        printWelcomeMessage();
        cleanAll();
        askNameOfPlayers();
        startGame();
    }

    static void cleanAll() {
        System.out.println("\033[H\033[J");
    }

    static void printWelcomeMessage() {
        String welcomeMessage =
                "----------------------------------------WELCOME!----------------------------------------\n" +
                        "--------------------------------WHEEL OF FORTUNE GAME-----------------------------------\n";

        System.out.println(blueColorBackground + welcomeMessage + resetColor);
        System.out.println(blueColor + "START OF THE GAME!" + resetColor);
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

    static String startGame() {
        String[] words = {"programming", "software", "computer", "development", "technology", "chocolate", "happiness","journey","dinosaur","universe"};
        String[] descriptions = {"A way to create instructions for computers.",
                "The field of creating applications.",
                "An electronic device for processing data.",
                "The process of improving or creating new programs.",
                "The application of scientific knowledge for practical purposes, especially in industry.",
                "A sweet, usually brown, food made from roasted and ground cacao seeds, commonly used in desserts and candies.",
                "A state of well-being and contentment; feeling joy and satisfaction.",
                "The act of traveling from one place to another, often over a long distance.",
                "A prehistoric reptile, often of gigantic size, that lived millions of years ago.",
                "All of space and everything in it, including stars, planets, galaxies, and all forms of matter and energy."};

        int randomIndex = random.nextInt(words.length);
        String word = words[randomIndex];
        String description = descriptions[randomIndex];

        ArrayList<Character> alphabet = new ArrayList<>();
        for (char c = 'A'; c <= 'Z'; c++) {
            alphabet.add(c);
        }

        System.out.println(blueColor + "LET'S GOOO" + resetColor);

        char[] hiddenWord = new char[word.length()];
        Arrays.fill(hiddenWord, '⬜');

        Map<String, Integer> scores = new HashMap<>();
        for (String player : players) {
            scores.put(player, 0);
        }


    return alphabet.toString();}
}