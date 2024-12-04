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
    public static final String GREEN_BACKGROUND = "\u001B[42m";
    public static final String blueColor = "\u001B[34m";

    public static void main(String[] args) {
        printWelcomeMessage();
        askNameOfPlayers();
        displayGame();
    }

    static void cleanAll() {
        System.out.println("\033[H\033[J");
        System.out.flush();
    }

    static void printWelcomeMessage() {
        String welcomeMessage =
                "----------------------------------------WELCOME!----------------------------------------\n" +
                        "--------------------------------WHEEL OF FORTUNE GAME-----------------------------------\n";

        System.out.println(GREEN_BACKGROUND + welcomeMessage + resetColor);
        System.out.println(greenColor + "START OF THE GAME!" + resetColor);
        System.out.println("----------------------------------------------------------------------------------------");
    }

    static String askNameOfPlayers() {
        System.out.print("Enter number of players: ");
        int numberOfPlayers = sc.nextInt();
        if (numberOfPlayers <= 0) {
            System.out.println("Invalid number of players!");
        }
        sc.nextLine();

        for (int i = 1; i <= numberOfPlayers; i++) {
            System.out.println("----------------------------------------------------------------------------------------");
            System.out.print("Enter nickname #" + i + ": ");
            String name = sc.nextLine();
            if (!name.isEmpty()) {
                players.add(name);
                scores.add(0);
            }
            else {
                System.out.println("Invalid name. Please enter a valid nickname.");
                i--;
            }
        }
        Collections.shuffle(players);
        System.out.println("Player order: " + players);

        cleanAll();

        return players.get(0);
    }

    static String displayGame() {
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
        String winner = "";

        while (!wordGuessed) {
            String currentPlayer = players.get(currentPlayerIndex);

            System.out.println("----------------------------------------------------------------------------------------");
            System.out.println("Current player: " + currentPlayer + " | Scores: " + scores.get(currentPlayerIndex));
            System.out.println("----------------------------------------------------------------------------------------");
            System.out.println("The word has " + blueColor + secretWord.length() + resetColor + " letters.");
            System.out.println("Description: " + blueColor + description + resetColor);
            System.out.println("Word: " + new String(hiddenWord));
            System.out.println("----------------------------------------------------------------------------------------");
            System.out.println("Players: " + players);
            System.out.println("Remaining letters: "
            + blueColor + alphabet + resetColor);
            System.out.println("----------------------------------------------------------------------------------------");
            System.out.println(currentPlayer + ", enter letter or guess the word: ");

            String guess = sc.nextLine().toUpperCase();

            cleanAll();

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
                            System.out.println("----------------------------------------------------------------------------------------");
                            System.out.println(redColor + "ATTENTION!" + resetColor);
                            System.out.println("----------------------------------------------------------------------------------------");
                            System.out.println(greenColor + currentPlayer + " scored 600 points!\n" + resetColor +
                                    "Now for each player is given the opportunity to guess the word.\n" +
                                    "If no one guesses, THE WINNER will be " + redColor + currentPlayer + resetColor );
                            System.out.println("----------------------------------------------------------------------------------------");
                            System.out.println("The word has " + blueColor + secretWord.length() + resetColor + " letters.");
                            System.out.println("Description: " + blueColor + description + resetColor);
                            System.out.println("Word: " + new String(hiddenWord));

                            ifPlayersGuessCorrect(secretWord, leaderIndex, currentPlayer);
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
                winner = currentPlayer;
                System.out.println("----------------------------------------------------------------------------------------");
                System.out.println(greenColor + "WINNER!" + "\nCongratulations, " + currentPlayer +"! You guessed the word correctly!" + resetColor);
                displayGameResults(secretWord, winner);
                scores.set(currentPlayerIndex, scores.get(currentPlayerIndex) + 1000);
            }
            else {
                System.out.println(redColor + "INCORRECT GUESS! \n" + currentPlayer + ", you are OUT of the game. " + resetColor);
                eliminatedPlayers.add(currentPlayer);
                players.remove(currentPlayerIndex);
                scores.remove(currentPlayerIndex);
                if (players.size() == 1) {
                    System.out.println("----------------------------------------------------------------------------------------");
                    System.out.println("All players are out of the game.");
                    winner = players.get(0);
                    System.out.println("Congratulations! " + players.get(0) + "! You are the WINNER!");
                    displayGameResults(secretWord, winner);
                    break;
                }
                currentPlayerIndex = currentPlayerIndex % players.size();
                }

            if (String.valueOf(hiddenWord).equals(secretWord)) {
                wordGuessed = true;
                winner = currentPlayer;
                System.out.println("----------------------------------------------------------------------------------------");
                System.out.println(greenColor + "WINNER!" + "\nCongratulations, " + currentPlayer + "! You guessed the word correctly!" + resetColor);
                displayGameResults(secretWord, winner);
                }
            }
            if (players.isEmpty()) {
                System.out.println("----------------------------------------------------------------------------------------");
                    System.out.println("All players are out of the game. The word was: " + secretWord);
                }

            cleanAll();

        sc.close();

    return alphabet.toString();}

    static boolean ifPlayersGuessCorrect(String secretWord, int leaderIndex, String winner) {
        String leaderByPoints = players.get(leaderIndex);
        for (int i = 0; i < players.size(); i++) {
            if (i ==leaderIndex) {
                continue;

            }
            String player = players.get(i);
            System.out.println("----------------------------------------------------------------------------------------");
            System.out.println(player + ", enter the word: ");
            String wordGuess = sc.nextLine().toUpperCase();

            if (wordGuess.equalsIgnoreCase(secretWord)) {
                winner = player;
                System.out.println(greenColor + "WINNER!\n" + "Congratulations, " + winner + "! You guessed the word correctly!" + resetColor);
                displayGameResults(secretWord, winner);
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
        winner = leaderByPoints;
        displayGameResults(secretWord, winner);
        return true;
    }

    static void displayGameResults(String secretWord, String winner) {
        cleanAll();
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.println( "\nGame Over! Final Scores:");
        System.out.println("The word was: " + blueColor + secretWord + resetColor) ;
        System.out.println("Winner: " + winner + " with 1000 scores! ");
        System.out.println("----------------------------------------------------------------------------------------");

        System.out.println(greenColor + "THANK YOU FOR THE GAME!" + resetColor);
    }
}

