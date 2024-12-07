import java.util.*;

public class WheelOfFortune {

    public static final Scanner sc = new Scanner(System.in);
    public static final ArrayList<String> players = new ArrayList<>();
    public static final Random random = new Random();
    public static final ArrayList <Integer> scores = new ArrayList<>();
    public static final ArrayList <String> eliminatedPlayers = new ArrayList<>();

    public static void main(String[] args) {
        displayWelcomeMessage();
        askPlayerName();
        playGame();
    }

    static void clearConsole() {
        System.out.println("\033[H\033[J");
        System.out.flush();
    }

    static void displayWelcomeMessage() {
        String welcomeMessage =
                "----------------------------------------WELCOME!----------------------------------------\n" +
                        "--------------------------------WHEEL OF FORTUNE GAME-----------------------------------\n";

        System.out.println(ConsoleColors.GREEN_BACKGROUND + ConsoleColors.BOLD + ConsoleColors.ITALIC + welcomeMessage + ConsoleColors.RESET_COLOR);
        System.out.println(ConsoleColors.GREEN_COLOR + ConsoleColors.BOLD + ConsoleColors.ITALIC + "START OF THE GAME!" + ConsoleColors.RESET_COLOR);
        System.out.println("----------------------------------------------------------------------------------------");
    }

    static String askPlayerName() {
        int playersNumber;
        while (true) {
            System.out.print("Enter number of players: ");
            if (sc.hasNextInt()) { // Проверяем, действительно ли введено число
                playersNumber = sc.nextInt();
                sc.nextLine();
                if (playersNumber > 0) {
                    break; // Выходим из цикла при корректном вводе
                }
                else {
                    System.out.println("Invalid number of players! Please enter a positive number.");
                }
            }
            else {
                System.out.println("Invalid input! Please enter a valid integer.");
                sc.nextLine();
            }
        }

        int i = 0; // Начинаем с 0
        while (i < playersNumber) {
            System.out.println("----------------------------------------------------------------------------------------");
            System.out.print("Enter nickname #" + (i + 1) + ": "); // (i + 1) для  отображения номера игрока
            String playerName = sc.nextLine();

            if (!playerName.isEmpty()) {
                players.add(playerName);
                scores.add(0);
                i++;
            }
            else {
                System.out.println("Invalid name. Please enter a valid nickname.");
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

        int randomWordIndex = random.nextInt(wordsAndDescriptions.length);

        String selectedWord = wordsAndDescriptions[randomWordIndex][0];
        String selectedWordDescription = wordsAndDescriptions[randomWordIndex][1];

        char[] maskedWord = new char[selectedWord.length()];
        Arrays.fill(maskedWord, '⬜');

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

            printGameState(currentPlayer,currentPlayerIndex, maskedWord, selectedWord, selectedWordDescription, availableLetters);

            System.out.println("----------------------------------------------------------------------------------------");
            System.out.println(currentPlayer + ", enter letter or guess the word: ");

            String playerGuess = sc.nextLine().toUpperCase(); // если 1 символ - пытаются угадать букву, если больше - слово

            clearConsole();

            if (playerGuess.length() == 1) {
                char guessedLetter = playerGuess.charAt(0);
                selectedWord = selectedWord.toUpperCase();
                guessedLetter = Character.toUpperCase(guessedLetter);

                boolean isLetterFound = isLetterGuessed(guessedLetter, selectedWord, maskedWord, availableLetters);;

                if (isLetterFound) {
                    scores.set(currentPlayerIndex, scores.get(currentPlayerIndex) + 100);
                        if (scores.get(currentPlayerIndex) >= 600) {
                            // игра после достижения кем-то 600 очков.
                            // дается возможность отгадать слово
                            leaderIndex = currentPlayerIndex;
                            gameAfter600scores(currentPlayerIndex, selectedWord, leaderIndex, winner, currentPlayer, selectedWordDescription, maskedWord, availableLetters) ;
                            break;
                        }
                    }
                else if (!isLetterFound) {
                        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
                    }
                }

            else if (playerGuess.equals(selectedWord)) {
                if (isWordGuessed(playerGuess, selectedWord, currentPlayer, currentPlayerIndex)) {
                    isWordGuessed = true;
                    winner = currentPlayer;
                    showFinalResults(selectedWord, winner);
                }
            }

            else if (!playerGuess.equals(selectedWord)) {
                System.out.println(ConsoleColors.RED_COLOR + "INCORRECT GUESS! \n" + currentPlayer + ", you are OUT of the game. " + ConsoleColors.RESET_COLOR);

                eliminatedPlayers.add(currentPlayer);
                players.remove(currentPlayerIndex);
                scores.remove(currentPlayerIndex);

                if (players.size() == 1) { // если все игроки выбыли и остался только один, он автоматически победитель
                    onePlayerRemain(winner, selectedWord);
                    break; // соответственно игра завершается
                }
                currentPlayerIndex = currentPlayerIndex % players.size(); // переход на следующего игрока
            }

            if (String.valueOf(maskedWord).equals(selectedWord)) {
                isWordGuessed = true;
                winner = currentPlayer;
                System.out.println("----------------------------------------------------------------------------------------");
                System.out.println(ConsoleColors.GREEN_COLOR + "WINNER!" + "\nCongratulations, " + currentPlayer + "! You guessed the word correctly!" + ConsoleColors.RESET_COLOR);
                showFinalResults(selectedWord, winner);
                }
            }

            if (players.isEmpty()) {
                System.out.println("----------------------------------------------------------------------------------------");
                System.out.println("All players are out of the game. The word was: " + selectedWord);
            }
            clearConsole();

        sc.close();

    return availableLetters.toString();
    }

    static void printGameState(String currentPlayer, int currentPlayerIndex, char[] maskedWord, String selectedWord, String selectedWordDescription, ArrayList<Character> availableLetters) {
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.println("Current player: " + currentPlayer + " | Scores: " + scores.get(currentPlayerIndex));
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.println("The word has " + ConsoleColors.BLUE_COLOR + selectedWord.length() + ConsoleColors.RESET_COLOR + " letters.");
        System.out.println("Description: " + ConsoleColors.BLUE_COLOR + selectedWordDescription + ConsoleColors.RESET_COLOR);
        System.out.println("Word: " + new String(maskedWord));
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.println("Players: " + players);
        System.out.println("Remaining letters: "
                + ConsoleColors.BLUE_COLOR + availableLetters + ConsoleColors.RESET_COLOR);
    }

    static boolean isLetterGuessed(char guessedLetter, String selectedWord, char[] maskedWord, ArrayList<Character> availableLetters) {
        if (!availableLetters.contains(guessedLetter)) {
            System.out.println("This letter has already been used.");
            return false;
        }

        availableLetters.remove((Character) guessedLetter);
        boolean isLetterFound = false;

        for (int i = 0; i < selectedWord.length(); i++) {
            if (selectedWord.charAt(i) == guessedLetter) {
                maskedWord[i] = guessedLetter;
                isLetterFound = true;
            }
        }

        if (isLetterFound) {
            System.out.println(ConsoleColors.GREEN_COLOR + "Congratulations! You guessed the letter '" + guessedLetter + "' correctly!" + ConsoleColors.RESET_COLOR);
        }
        else {
            System.out.println(ConsoleColors.RED_COLOR + "Incorrect guess! This letter is not in the word." + ConsoleColors.RESET_COLOR);
        }

        return isLetterFound;
    }

    static boolean isWordGuessed(String playerGuess, String selectedWord, String currentPlayer, int currentPlayerIndex) {
        if (playerGuess.equals(selectedWord)) {
            System.out.println(ConsoleColors.GREEN_COLOR + "WINNER!\nCongratulations, " + currentPlayer + "! You guessed the word correctly!" + ConsoleColors.RESET_COLOR);
            scores.set(currentPlayerIndex, scores.get(currentPlayerIndex) + 1000);
            return true;
        }
        else if (!playerGuess.equals(selectedWord)) {
            System.out.println(ConsoleColors.RED_COLOR + "Incorrect guess! " + currentPlayer + ", you are OUT of the game." + ConsoleColors.RESET_COLOR);
            eliminatedPlayers.add(currentPlayer);
            players.remove(currentPlayerIndex);
            scores.remove(currentPlayerIndex);
            return false;
        }
        else {
            return false;
        }
    }

    static boolean gameAfter600scores(int currentPlayerIndex, String selectedWord, int leaderIndex, String winner, String currentPlayer, String selectedWordDescription, char[] maskedWord, ArrayList<Character> availableLetters) {
        // Игра после достижения кем-то из игроков 600 очков
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.println(ConsoleColors.RED_COLOR + "ATTENTION!" + ConsoleColors.RESET_COLOR);
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.println(ConsoleColors.GREEN_COLOR + currentPlayer + " scored 600 points!\n" + ConsoleColors.RESET_COLOR +
                "Now for each player is given the opportunity to guess the word.\n" +
                "If no one guesses, THE WINNER will be " + ConsoleColors.RED_COLOR + currentPlayer + ConsoleColors.RESET_COLOR);

        printGameState(currentPlayer, currentPlayerIndex, maskedWord, selectedWord, selectedWordDescription, availableLetters);

        String leaderByPoints = players.get(leaderIndex);
        for (int i = 0; i < players.size(); i++) {
            if (i ==leaderIndex) {
                continue;
            }
            String player = players.get(i);
            System.out.println("----------------------------------------------------------------------------------------");
            System.out.println(player + ", enter the word: ");
            String wordGuess = sc.nextLine().toUpperCase();

            if (wordGuess.equalsIgnoreCase(selectedWord)) {
                winner = player;
                System.out.println(ConsoleColors.GREEN_COLOR + "WINNER!\n" + "Congratulations, " + winner + "! You guessed the word correctly!" + ConsoleColors.RESET_COLOR);
                showFinalResults(selectedWord, winner);
                return true; // Завершаем, так как игрок угадал слово
            }
            else {
                System.out.println(ConsoleColors.RED_COLOR + "INCORRECT GUESS! \n" + player + ", you are OUT of the game." + ConsoleColors.RESET_COLOR);
                eliminatedPlayers.add(player);
                players.remove(i);
                scores.remove(i);
                i--;
            }
        }
        // Если никто не угадал, победитель — игрок с наибольшими очками
        System.out.println(ConsoleColors.GREEN_COLOR + "WINNER BY POINTS!" + "\nCongratulations, " + leaderByPoints + "!"  + ConsoleColors.RESET_COLOR);
        winner = leaderByPoints;
        showFinalResults(selectedWord, winner);
        return true;
    }

    static void onePlayerRemain(String winner, String selectedWord) {
        // Если все игроки выбыли и остался только один, он автоматически победитель
            System.out.println("----------------------------------------------------------------------------------------");
            System.out.println("All players are out of the game.");
            winner = players.get(0);
            System.out.println("Congratulations! " + players.get(0) + "! You are the WINNER!");
            showFinalResults(selectedWord, winner);
    }

    static void showFinalResults(String selectedWord, String winner) {
        clearConsole();
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.println( "\nGame Over! Final Scores:");
        System.out.println("The word was: " + ConsoleColors.BLUE_COLOR + ConsoleColors.BOLD + ConsoleColors.ITALIC + selectedWord + ConsoleColors.RESET_COLOR) ;
        System.out.println("Winner: " + winner + " with 1000 scores! ");
        System.out.println("Eliminated players: " + eliminatedPlayers);
        System.out.println("----------------------------------------------------------------------------------------");

        System.out.println(ConsoleColors.GREEN_COLOR + ConsoleColors.BOLD + ConsoleColors.ITALIC + "THANK YOU FOR THE GAME!" + ConsoleColors.RESET_COLOR);
    }
}

