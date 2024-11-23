import java.util.*;

public class WheelOfFortune {
    public static final Scanner sc = new Scanner(System.in);
    public static final ArrayList<String> players = new ArrayList<>();
    public static final Random random = new Random();
    public static final ArrayList <Integer> scores = new ArrayList<>();
    public static final ArrayList <String> eliminatedPlayers = new ArrayList<>();

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
        System.out.println(greenColor + "START OF THE GAME!" + resetColor);
    }

    static String askNameOfPlayers() {
        System.out.print("Enter number of players: ");
        int numberOfPlayers = sc.nextInt();
        if (numberOfPlayers <= 0) {
            System.out.println("Invalid number of players!");
        }
        sc.nextLine();

        for (int i = 1; i <= numberOfPlayers; i++) {
            System.out.print("Enter nickname #" + i + ": ");
            String name = sc.nextLine();
            players.add(name);
            scores.add(0);
        }

        Collections.shuffle(players);
        System.out.println("Player order: " + players);

        return players.get(0);
    }

    static String startGame() {
        String[][] wordsAndDescriptions = {
                {"programming", "A way to create instructions for computers."},
                {"software", "The field of creating applications."},
                {"computer", "An electronic device for processing data."},
                {"development", "The process of improving or creating new programs."},
                {"technology", "The application of scientific knowledge for practical purposes, especially in industry."},
                {"chocolate", "A sweet, usually brown, food made from roasted and ground cacao seeds, commonly used in desserts and candies."},
                {"happiness", "A state of well-being and contentment; feeling joy and satisfaction."},
                {"journey", "The act of traveling from one place to another, often over a long distance."},
                {"dinosaur", "A prehistoric reptile, often of gigantic size, that lived millions of years ago."},
                {"universe", "All of space and everything in it, including stars, planets, galaxies, and all forms of matter and energy."}
            };

        int randomIndex = random.nextInt(wordsAndDescriptions.length);

        String secretWord = wordsAndDescriptions[randomIndex][0];
        String description = wordsAndDescriptions[randomIndex][1];


        char[] hiddenWord = new char[secretWord.length()];
        Arrays.fill(hiddenWord, '⬜');

        ArrayList<Character> alphabet = new ArrayList<>();
        for (char c = 'A'; c <= 'Z'; c++) {
            alphabet.add(c);
        }
        System.out.println(blueColor + "LET'S GOOO !!!" + resetColor);

        boolean wordGuessed = false;
        int currentPlayerIndex = 0;
        int leaderIndex = 0;

        while (!wordGuessed) {
            String currentPlayer = players.get(currentPlayerIndex);

            System.out.println("----------------------------------------------------------------------------------------");
            System.out.println("Current player: " + currentPlayer + " | Scores: " + scores.get(currentPlayerIndex));
            System.out.println("----------------------------------------------------------------------------------------");
            System.out.println("The word has " + blueColor + secretWord.length() + resetColor + " letters.");
            System.out.println("Description: " + blueColor + description + resetColor);
            System.out.println("Word: " + new String(hiddenWord));
            System.out.println("Remaining letters: "
            + blueColor + alphabet + resetColor);
            System.out.println("----------------------------------------------------------------------------------------");
            System.out.println(currentPlayer + ", enter letter or guess the word: ");

            String guess = sc.nextLine().toUpperCase();


            if (guess.length() == 1) {
                char guessedLetter = guess.charAt(0);
                secretWord = secretWord.toUpperCase();
                guessedLetter = Character.toUpperCase(guessedLetter);
                if (!alphabet.contains(guessedLetter)) {
                    System.out.println("This letter has already been used.");
                }
                else {
                    alphabet.remove((Character) guessedLetter);
                    boolean isLetterFound = false;

                    for (int i = 0; i < secretWord.length(); i++) {
                        if (secretWord.charAt(i) == guessedLetter) {
                            hiddenWord[i] = guessedLetter;
                            isLetterFound = true;
                        }
                    }

                    if (isLetterFound) {
                        System.out.println(greenColor + "Congratulations!\n" + resetColor + currentPlayer + "! You guessed the letter '" + guessedLetter + "' correctly!");
                        scores.set(currentPlayerIndex, scores.get(currentPlayerIndex) + 100);

                        if (scores.get(currentPlayerIndex) >= 600) {
                            leaderIndex = currentPlayerIndex;
                            System.out.println(redColor + "ATTENTION! \n" + resetColor +
                                    greenColor + currentPlayer + " scored 600 points!\n" + resetColor +
                                    "Now each player is given the opportunity to guess the word.\n" +
                                    "If no one guesses, " + redColor + "THE WINNER IS " + currentPlayer + resetColor );
                            wordGuessed = ifPlayersGuess(secretWord, leaderIndex, currentPlayer);
                            break;
                        }
                    }
                    else {
                        System.out.println(redColor + "Incorrect guess!\n" + resetColor + "This letter is not in the word.");
                        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
                    }
                }
            }
            else if (guess.equals(secretWord)) {
                wordGuessed = true;
                System.out.println(greenColor + "WINNER!" + "\nCongratulations, " + currentPlayer +"! You guessed the word correctly!" + resetColor);
                scores.set(currentPlayerIndex, scores.get(currentPlayerIndex) + 1000);
            }
            else {
                System.out.println(redColor + "INCORRECT GUESS! \n" + currentPlayer + ", you are OUT of the game. " + resetColor);
                eliminatedPlayers.add(currentPlayer);
                players.remove(currentPlayerIndex);
                scores.remove(currentPlayerIndex);
                currentPlayerIndex = currentPlayerIndex % players.size();
                }

            if (String.valueOf(hiddenWord).equals(secretWord)) {
                wordGuessed = true;
                System.out.println(greenColor + "WINNER!" + "\nCongratulations, " + currentPlayer + "! You guessed the word correctly!" + resetColor);
                }
            }
            if (players.isEmpty()) {
                    System.out.println("All players are out of the game. The word was: " + secretWord);
                }



        System.out.println( "\nGame Over! Final Scores:");
        System.out.println("The word was: " + secretWord);
        System.out.println("Winner: " + players.get(0) + " with " + scores.get(0) + " scores!");

        System.out.println(greenColor + "THANK YOU FOR THE GAME!" + resetColor);

        sc.close();

    return alphabet.toString();}

    static boolean ifPlayersGuess(String secretWord, int leaderIndex, String currentPlayer) {
        String leaderByPoints = players.get(leaderIndex);
        for (int i = 0; i < players.size(); i++) {
            if (i ==leaderIndex) {
                continue;
            }
            String player = players.get(i);
            System.out.println(player + ", enter the word: ");
            String wordGuess = sc.nextLine().toUpperCase();

            if (wordGuess.equalsIgnoreCase(secretWord)) {
                System.out.println(greenColor + "WINNER!\n" + "Congratulations, " + player + "! You guessed the word correctly!" + resetColor);
                return true;
            }
            else {
                System.out.println(redColor + "INCORRECT GUESS! \n" + player + ", you are OUT of the game." + resetColor);
                eliminatedPlayers.add(player);
                players.remove(i);
                scores.remove(i);
                i--;
            }
        }
        System.out.println(greenColor + "WINNER BY POINTS!" + "\nCongratulations, " + leaderByPoints + "!"  + resetColor);
        return true; }
}