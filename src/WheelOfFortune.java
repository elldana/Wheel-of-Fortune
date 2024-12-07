import java.util.*;

public class WheelOfFortune {

    public static final Scanner sc = new Scanner(System.in);
    public static final ArrayList<String> players = new ArrayList<>();
    public static final Random random = new Random();
    public static final ArrayList <Integer> scores = new ArrayList<>();
    public static final ArrayList <String> eliminatedPlayers = new ArrayList<>();

    public static void main(String[] args) {
        printWelcomeMessage();
        askPlayerName();
        playGame();
    }

    static void clearConsole() {
        System.out.println("\033[H\033[J");
        System.out.flush();
    }

    static void printWelcomeMessage() {
        String welcomeMessage =
                "----------------------------------------WELCOME!----------------------------------------\n" +
                        "--------------------------------WHEEL OF FORTUNE GAME-----------------------------------\n";

        System.out.println(ConsoleColors.GREEN_BACKGROUND + welcomeMessage + ConsoleColors.RESET_COLOR);
        System.out.println(ConsoleColors.GREEN_COLOR + "START OF THE GAME!" + ConsoleColors.RESET_COLOR);
        System.out.println("----------------------------------------------------------------------------------------");
    }

    static String askPlayerName() {
        System.out.print("Enter number of players: ");
        int playersNumber = sc.nextInt();
        if (playersNumber <= 0) {
            System.out.println("Invalid number of players!");
        }
        sc.nextLine();

        for (int i = 1; i <= playersNumber; i++) {
            System.out.println("----------------------------------------------------------------------------------------");
            System.out.print("Enter nickname #" + i + ": ");
            String playersName = sc.nextLine();
            if (!playersName.isEmpty()) {
                players.add(playersName);
                scores.add(0);
            }
            else {
                System.out.println("Invalid name. Please enter a valid nickname.");
                i--;
            }
        }
        Collections.shuffle(players);
        System.out.println("Player order: " + players);

        clearConsole();

        return players.getFirst();
    }

    static String playGame() {
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

        String hiddenWord = wordsAndDescriptions[randomIndex][0];
        String hiddenWordDescription = wordsAndDescriptions[randomIndex][1];


        char[] charHiddenWord = new char[hiddenWord.length()];
        Arrays.fill(charHiddenWord, '⬜');

        ArrayList<Character> availableLetters = new ArrayList<>();
        for (char c = 'A'; c <= 'Z'; c++) {
            availableLetters.add(c);
        }
        System.out.println(ConsoleColors.BLUE_COLOR + "LET'S GOOO !!!" + ConsoleColors.RESET_COLOR);

        boolean isWordGuessed = false;
        int currentPlayerIndex = 0;
        int leaderIndex = 0;
        String winner = "";

        while (!isWordGuessed) {
            String currentPlayer = players.get(currentPlayerIndex);

            System.out.println("----------------------------------------------------------------------------------------");
            System.out.println("Current player: " + currentPlayer + " | Scores: " + scores.get(currentPlayerIndex));
            System.out.println("----------------------------------------------------------------------------------------");
            System.out.println("The word has " + ConsoleColors.BLUE_COLOR + hiddenWord.length() + ConsoleColors.RESET_COLOR + " letters.");
            System.out.println("Description: " + ConsoleColors.BLUE_COLOR + hiddenWordDescription + ConsoleColors.RESET_COLOR);
            System.out.println("Word: " + new String(charHiddenWord));
            System.out.println("----------------------------------------------------------------------------------------");
            System.out.println("Players: " + players);
            System.out.println("Remaining letters: "
            + ConsoleColors.BLUE_COLOR + availableLetters + ConsoleColors.RESET_COLOR);
            System.out.println("----------------------------------------------------------------------------------------");
            System.out.println(currentPlayer + ", enter letter or guess the word: ");

            String guess = sc.nextLine().toUpperCase(); // word or letter

            clearConsole();

            if (guess.length() == 1) {
                char guessedLetter = guess.charAt(0);
                hiddenWord = hiddenWord.toUpperCase();
                guessedLetter = Character.toUpperCase(guessedLetter);
                if (!availableLetters.contains(guessedLetter)) {
                    System.out.println("This letter has already been used.");
                }
                else if (availableLetters.contains(guessedLetter)) {
                    availableLetters.remove((Character) guessedLetter);
                    boolean isLetterFound = false;

                    for (int i = 0; i < hiddenWord.length(); i++) {
                        if (hiddenWord.charAt(i) == guessedLetter) {
                            charHiddenWord[i] = guessedLetter;
                            isLetterFound = true;
                        }
                    }

                    if (isLetterFound) {
                        System.out.println(ConsoleColors.GREEN_COLOR + "Congratulations!\n" + ConsoleColors.RESET_COLOR + currentPlayer + "! You guessed the letter '" + guessedLetter + "' correctly!");
                        scores.set(currentPlayerIndex, scores.get(currentPlayerIndex) + 100);

                        if (scores.get(currentPlayerIndex) >= 600) {
                            leaderIndex = currentPlayerIndex;
                            System.out.println("----------------------------------------------------------------------------------------");
                            System.out.println(ConsoleColors.RED_COLOR + "ATTENTION!" + ConsoleColors.RESET_COLOR);
                            System.out.println("----------------------------------------------------------------------------------------");
                            System.out.println(ConsoleColors.GREEN_COLOR + currentPlayer + " scored 600 points!\n" + ConsoleColors.RESET_COLOR +
                                    "Now for each player is given the opportunity to guess the word.\n" +
                                    "If no one guesses, THE WINNER will be " + ConsoleColors.RED_COLOR + currentPlayer + ConsoleColors.RESET_COLOR);
                            System.out.println("----------------------------------------------------------------------------------------");
                            System.out.println("The word has " + ConsoleColors.BLUE_COLOR + hiddenWord.length() + ConsoleColors.RESET_COLOR + " letters.");
                            System.out.println("Description: " + ConsoleColors.BLUE_COLOR + hiddenWordDescription + ConsoleColors.RESET_COLOR);
                            System.out.println("Word: " + new String(charHiddenWord));

                            ifPlayersGuessCorrect(hiddenWord, leaderIndex, currentPlayer);
                            break;
                        }
                    }
                    else if (!isLetterFound){
                        System.out.println(ConsoleColors.RED_COLOR + "Incorrect guess!\n" + ConsoleColors.RESET_COLOR + "This letter is not in the word.");
                        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
                    }
                }
            }

            else if (guess.equals(hiddenWord)) {
                isWordGuessed = true;
                winner = currentPlayer;
                System.out.println("----------------------------------------------------------------------------------------");
                System.out.println(ConsoleColors.GREEN_COLOR + "WINNER!" + "\nCongratulations, " + currentPlayer +"! You guessed the word correctly!" + ConsoleColors.RESET_COLOR);
                showFinalResults(hiddenWord, winner);
                scores.set(currentPlayerIndex, scores.get(currentPlayerIndex) + 1000);
            }

            else if (!guess.equals(hiddenWord)) {
                System.out.println(ConsoleColors.RED_COLOR + "INCORRECT GUESS! \n" + currentPlayer + ", you are OUT of the game. " + ConsoleColors.RESET_COLOR);
                eliminatedPlayers.add(currentPlayer);
                players.remove(currentPlayerIndex);
                scores.remove(currentPlayerIndex);

                if (players.size() == 1) {
                    System.out.println("----------------------------------------------------------------------------------------");
                    System.out.println("All players are out of the game.");
                    winner = players.get(0);
                    System.out.println("Congratulations! " + players.get(0) + "! You are the WINNER!");
                    showFinalResults(hiddenWord, winner);
                    break;
                }

                currentPlayerIndex = currentPlayerIndex % players.size();
            }

            if (String.valueOf(charHiddenWord).equals(hiddenWord)) {
                isWordGuessed = true;
                winner = currentPlayer;
                System.out.println("----------------------------------------------------------------------------------------");
                System.out.println(ConsoleColors.GREEN_COLOR + "WINNER!" + "\nCongratulations, " + currentPlayer + "! You guessed the word correctly!" + ConsoleColors.RESET_COLOR);
                showFinalResults(hiddenWord, winner);
                }
            }

            if (players.isEmpty()) {
                System.out.println("----------------------------------------------------------------------------------------");
                    System.out.println("All players are out of the game. The word was: " + hiddenWord);
                }

            clearConsole();

        sc.close();

    return availableLetters.toString();}

    static boolean ifPlayersGuessCorrect(String secretWord, int leaderIndex, String winner) { //for check
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
                System.out.println(ConsoleColors.GREEN_COLOR + "WINNER!\n" + "Congratulations, " + winner + "! You guessed the word correctly!" + ConsoleColors.RESET_COLOR);
                showFinalResults(secretWord, winner);
                return true;
            }
            else {
                System.out.println(ConsoleColors.RED_COLOR + "INCORRECT GUESS! \n" + player + ", you are OUT of the game." + ConsoleColors.RESET_COLOR);
                eliminatedPlayers.add(player);
                players.remove(i);
                scores.remove(i);
                i--;
            }
        }
        System.out.println(ConsoleColors.GREEN_COLOR + "WINNER BY POINTS!" + "\nCongratulations, " + leaderByPoints + "!"  + ConsoleColors.RESET_COLOR);
        winner = leaderByPoints;
        showFinalResults(secretWord, winner);
        return true;
    }

    static void showFinalResults(String secretWord, String winner) {
        clearConsole();
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.println( "\nGame Over! Final Scores:");
        System.out.println("The word was: " + ConsoleColors.BLUE_COLOR + secretWord + ConsoleColors.RESET_COLOR) ;
        System.out.println("Winner: " + winner + " with 1000 scores! ");
        System.out.println("----------------------------------------------------------------------------------------");

        System.out.println(ConsoleColors.GREEN_COLOR + "THANK YOU FOR THE GAME!" + ConsoleColors.RESET_COLOR);
    }
}

