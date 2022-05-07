package wordguess;

import java.util.ArrayList;

public class GameHuman {

    // instance variables
    private ArrayList<Character> wrongLetters; // a list of all the letters that are wrong
    private WordguessIO wordguessio; // to receive the input
    private Words words; // connects to words so the player gets a random word
    private String namePlayer; // the name of the player
    private String word; // the word that the player has to guess
    private char[] wordSplit; // splits the word in 12 characters
    private char[] content; // the places in the game
    private boolean playerWon; // checks if the player has won already
    private boolean repeat; // checks if player tried already this input
    private int guesses; // the times a player tried to guess
    private int wordSize;

    // constructor
    GameHuman() {
        setSettings();
    }

    public void setSettings() {

        wordSize = Words.WORDLENGTH;
        wrongLetters = new ArrayList<>();
        wordguessio = new WordguessIO();
        words = new Words();
        wordSplit = new char[wordSize];
        content = new char[wordSize];
        playerWon = false;
        guesses = 0;

        word = words.getRandomWord();

        // fills the places of the letters with an . and split the word
        for (int i = 0; i < wordSize; i++) {
            wordSplit[i] = word.charAt(i);
            content[i] = '.';
        }

    }

    public int getGuesses() {
        return guesses;
    }

    public void play(String namePlayer) {

        // variables
        this.namePlayer = namePlayer.toUpperCase();

        System.out.println();
        System.out.println("---------------------------------------------------------");
        System.out.print("| " + this.namePlayer + " jij gaat nu raden.");

        for (int i = 0; i < (35 - this.namePlayer.length()); i++) { // makes room for the name
            System.out.print(" ");
        }

        System.out.println("|");
        System.out.println("| Probeer het " + wordSize + "-letterwoord zo snel mogelijk te raden. | ");
        System.out.println("| Geef per keer een letter in, of het hele woord        |");
        System.out.println("| Bij 10 keer een foute letter ben je af.               |");
        System.out.println("| Als je het woord fout raadt, krijg je 5 strafpunten.  |");
        System.out.println("| Succes!                                               |");
        System.out.println("---------------------------------------------------------");

        System.out.print("[ENTER] om door te gaan.");
        while (wordguessio.getEntertoContinue()) { // wait for an enter
            wordguessio.getEntertoContinue();
        }

        if (Words.CHEATMODE) { // shows the word when cheatmode is on
            System.out.println();
            System.out.println("Het woord is: " + word);
        }

        // the game plays till this loop ends
        while (!playerWon && wrongLetters.size() < 10) {

            scoreScreen(); // gets the score screen

            repeat = true;
            while (repeat) { // checks if the word or letter is good
                checkLetter(wordguessio.getPlayerGuess());
            }

            guesses++;
            checkWinner(); // checks every round if the player won already

        }

        scoreScreen();

    }

    private boolean checkWinner() {

        int winnerCounter = 0;

        for (int i = 0; i < wordSize; i++) { // checks if there is a winner

            if (content[i] == '.') {
                break;
            } else {
                winnerCounter++;
            }

        }

        if (winnerCounter == wordSize) {
            playerWon = true;
        }

        return playerWon;
    }

    private void scoreScreen() {

        System.out.println();

        if (playerWon) { // got it screen if a player wins
            wordguessio.asciiArt(11);
        } else {
            wordguessio.asciiArt(wrongLetters.size());
        }

        if (wrongLetters.size() == 10) {
            System.out.println("Je hebt het niet geraden. Het  woord was: " + word);
        }

        System.out.print(wrongLetters.size() + " fouten");

        if (wrongLetters.size() != 0) { // the wrong guessed letters

            System.out.print("(");
            for (int i = 0; i < wrongLetters.size(); i++) {

                System.out.print(wrongLetters.get(i));

            }
            System.out.print(")");

        }

        System.out.println(", aantal invoer: " + guesses);
        System.out.println();

        for (int i = 0; i < wordSize; i++) { // prints out the content

            System.out.print(content[i] + " ");

        }

        System.out.println();

        if (wrongLetters.size() == 10 || playerWon) {

            System.out.println(this.namePlayer + " jouw score is: " + guesses);

        }

    }

    private void checkLetter(String word) {

        // variables
        boolean letterNotInWord = true; // checks if the letter is in the word
        boolean wordAlreadyUsed = false; // checks if the player already tried the letter
        boolean alreadyTriedWrong = false; // checks if the player tried this letter that isn't in the word

        if (word.length() == 1) {

            for (int i = 0; i < wordSize; i++) {

                if (word.charAt(0) == content[i]) { // checks if letter is already used

                    System.out.println("Het lijkt er op dat je deze letter al een keer hebt ingevuld.");
                    wordAlreadyUsed = true;
                    letterNotInWord = false;
                    break;

                }

            }

            if (!wordAlreadyUsed) { // if letter isn't tried before, then this will add the letter to the content

                for (int j = 0; j < wordSize; j++) {
                    if (wordSplit[j] == word.charAt(0)) {

                        content[j] = wordSplit[j];
                        letterNotInWord = false;
                        repeat = false;

                    }
                }

            }

            if (letterNotInWord) { // starts when the letter is not in the word

                for (int i = 0; i < wrongLetters.size(); i++) {

                    if (word.charAt(0) == wrongLetters.get(i)) { // checks if the player already tried the wrong letter

                        System.out.println("Je hebt deze letter al een keer verkeerd geraden.");
                        alreadyTriedWrong = true;
                        break;

                    }

                }

                if (!alreadyTriedWrong) { // add wrong letter to the wrong letter list

                    wrongLetters.add(word.charAt(0));
                    repeat = false;

                }

            }

        } else { // happens when the player tries to guess the whole word

            if (!word.equals(this.word)) { // when the guessed word is wrong

                System.out.println("Het woord is fout, je krijgt 5 strafpunten.");
                guesses = guesses + 4; // 4 because a turn = already 1
                repeat = false;

            } else { // add the letters to the content when the word guess is good

                for (int i = 0; i < wordSize; i++) {

                    content[i] = this.word.charAt(i);
                    repeat = false;

                }

            }

        }

    }

}
