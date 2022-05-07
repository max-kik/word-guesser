package wordguess;

import java.util.Random;

public class WordguessGame {

    // instance variables
    private WordguessIO wordguessio;
    private GameHuman gamehuman;
    private GameComputer gamecomputer;
    private String namePlayer; // name of the player
    private boolean playerBegins; // looks if player won the toss
    private boolean playAgain;

    WordguessGame() {

        wordguessio = new WordguessIO();
        gamehuman = new GameHuman();
        gamecomputer = new GameComputer();
        playerBegins = true;
        playAgain = true;

    }

    public void start() {

        // intro text
        System.out.println("========================================================");
        System.out.println("| Welkom bij WordGuess!                                |");
        System.out.println("========================================================");
        System.out.println("| Je gaat tegen de computer spelen.                    |");
        System.out.println("| Het doel is zo snel mogelijk een woord te raden      |");
        System.out.println("| Het 12-letterwoord moet gekozen worden uit de lijst. |");
        System.out.println("| Jij raadt een woord van de computer en andersom      |");
        System.out.println("| Wie het snelst het woord raadt wint                  |");
        System.out.println("| Bij een gelijkspel spelen we nog een ronde.          |");
        System.out.println("========================================================");

        // asking the name of the player
        namePlayer = wordguessio.getPlayerName();
        System.out.println("Welkom, " + namePlayer + "!");

        // the game loop
        while (playAgain) {

            // getting the word size
            Words.WORDLENGTH = wordguessio.wordSize();
            System.out.println("Top! We spelen om een " + Words.WORDLENGTH + " letter woord.");

            doToss();

            if (playerBegins) {
                gamehuman.setSettings();
                gamehuman.play(namePlayer);
                gamecomputer.setSettings();
                gamecomputer.play(namePlayer);

            } else {
                gamecomputer.setSettings();
                gamecomputer.play(namePlayer);
                gamehuman.setSettings();
                gamehuman.play(namePlayer);
            }

            gameEnd();

        }

    }

    // checks if the games end
    private void gameEnd() {

        // variables
        int scoreHuman; // score of the human
        int scoreComputer;

        // makes a draw if cheatmode is on
        if (!Words.CHEATMODE) {
            scoreHuman = gamehuman.getGuesses();
            scoreComputer = gamecomputer.getGuesses();
        } else {
            scoreHuman = 0;
            scoreComputer = 0;
        }

        System.out.println();
        System.out.println(namePlayer + " score: " + gamehuman.getGuesses());
        System.out.println("COMPUTER score: " + gamecomputer.getGuesses());

        if (scoreHuman < scoreComputer) { // if the player won
            System.out.println("Bravo " + namePlayer + " je hebt de computer verslagen!");
            playAgain = false;
        } else if (scoreComputer < scoreHuman) { // if the computer won
            System.out.println("Helaas " + namePlayer + " je hebt verloren!");
            playAgain = false;
        } else { // if the game is a draw
            System.out.println("Het is gelijkspel, we spelen nog een ronde!");
            playAgain = true;
        }
    }

    // does a toss
    private void doToss() {

        // variables
        int head = 0;
        int tail = 0;
        String input = "?";
        Random headTail = new Random(); // creates random boolean

        System.out.println();
        System.out.println("Eerst doen we een TOSS om wie mag beginnen");
        input = wordguessio.getHeadorTail();

        System.out.println("Er wordt 5 keer gegooid. De kant die het meeste");
        System.out.println("keer gegooid wordt, mag beginnen");
        System.out.print("Flipping: ");

        // put head or tail in a print and count the head or tail
        for (int i = 0; i < 5; i++) {

            if (headTail.nextBoolean()) {
                head++;
                System.out.print("kop");
            } else {
                tail++;
                System.out.print("munt");
            }
            if (i < 4) {
                System.out.print("|");
            }
        }

        System.out.println();

        if (head > tail) { // if head won
            System.out.println("Het is geworden: kop (" + head + " keer gegooid)");
        } else { // if tail won
            System.out.println("Het is geworden: munt (" + tail + " keer gegooid)");
        }

        playerBegins = true;
        if (input.equals("kop") && (head > tail)) { // if player won
            System.out.println(namePlayer + " jij mag beginnen!");
        } else if (input.equals("munt") && (tail > head)) { // if player won
            System.out.println(namePlayer + " jij mag beginnen!");
        } else { // if computer won
            System.out.println("De computer mag beginnen!");
            playerBegins = false;
        }

    }

}
