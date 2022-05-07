package wordguess;

import java.util.ArrayList;
import java.util.Random;

public class GameComputer {

    // instance variables
    private ArrayList<Character> usedLetters; // a list of letters the that computer guessed good
    private ArrayList<Character> wrongLetters; // a list of letters that the computer guessed wrong
    private ArrayList<Character> possibleLetters; // all the letters that are in the words that the computer has to //
                                                  // guess
    private ArrayList<Character> forbiddenLetters; // a list with a collection of the used and wrong letters
    private ArrayList<String> wordList; // a list with all the possible words
    private WordguessIO wordguessio; // connect to io for the input
    private Words words; // connect to words to get a random word and the word list
    private char[] content; // the places in the game
    private char computerGuess; // the letter that the computer guesses
    private boolean inputIsGood; // checks if the input is good
    private boolean computerWon; // checks if the computer won
    private int guesses; // the times the computer guessed
    private int wordSize;

    // constructor
    GameComputer() {
        setSettings();
    }

    // set all the settings before the game begins
    public void setSettings() {

        wordSize = Words.WORDLENGTH;
        usedLetters = new ArrayList<>();
        wrongLetters = new ArrayList<>();
        wordList = new ArrayList<>();
        forbiddenLetters = new ArrayList<>();
        possibleLetters = new ArrayList<>();
        words = new Words();
        wordList = words.getAllWords(); // add all the words to a new list
        wordguessio = new WordguessIO();
        content = new char[wordSize];
        inputIsGood = false;
        computerWon = false;
        guesses = 0;

        for (int i = 0; i < wordSize; i++) { // fills the content
            content[i] = '.';

        }

        for (char i = 'A'; i <= 'X'; i++) { // fills the list with possible letters
            possibleLetters.add(i);
        }

    }

    public int getGuesses() {
        return guesses;
    }

    public void play(String namePlayer) { // the play method

        namePlayer = namePlayer.toUpperCase(); // set the name to upper case

        System.out.println("---------------------------------------------------------");
        System.out.println("| De computer gaat nu raden                             |");
        System.out.println("| Neem een " + wordSize + "-letterwoord uit de lijst in gedachten.    |");
        System.out.println("| De computer gaat letters raden (of het hele woord).   |");
        System.out.print("| " + namePlayer + " jij antwoord met postities,");

        for (int i = 0; i < (26 - namePlayer.length()); i++) { // makes space for the name
            System.out.print(" ");
        }

        System.out.println("|");
        System.out.println("|    gescheiden door spaties (vb: 3 9 10                |");
        System.out.println("|    of '-' als de letter niet voorkomt.                |");
        System.out.println("---------------------------------------------------------");

        System.out.println("[ENTER] om door te gaan.");
        while (wordguessio.getEntertoContinue()) { // waits for an enter
            wordguessio.getEntertoContinue();
        }

        while (!computerWon && wrongLetters.size() < 10 && !(wordList.size() == 0)) { // the game loop

            if (Words.CHEATMODE) { // prints all the possible words when in cheat mode
                System.out.println();
                for (int i = 0; i < wordList.size(); i++) {
                    System.out.println(wordList.get(i));
                }
                System.out.println(wordList.size());
            }

            scoreScreen();

            computerGuess = randomLetter(); // random word form the computer
            System.out.println("De computer raadt: " + computerGuess);

            inputIsGood = false;
            while (!inputIsGood) { // checks input till its good
                checkLetter(wordguessio.getPlayerAnswer(), computerGuess);
            }

            guesses++;
            checkWinner();

        }

        scoreScreen();

    }

    private boolean checkWinner() {

        // variables
        boolean inWords = true;
        for (int i = 0; i < possibleLetters.size(); i++) { // exclude all letters that are not in the words

            inWords = true;

            for (int j = 0; j < wordList.size(); j++) {

                if (wordList.get(j).indexOf(possibleLetters.get(i)) != -1) {
                    inWords = false;
                    break;
                }

            }

            if (inWords) {
                possibleLetters.remove(i);
                i = i - 1;
            }

        }

        // variables
        int winnerCounter = 0;

        if (wordList.size() == 1) { // fills the word if there is only one choice in the list with possible words

            for (int i = 0; i < wordSize; i++) {
                content[i] = wordList.get(0).charAt(i);
            }

        }

        for (int i = 0; i < wordSize; i++) { // checks if there is a winner

            if (content[i] == '.') {
                break;
            } else {
                winnerCounter++;
            }

        }

        if (winnerCounter == wordSize) {
            computerWon = true;
        }

        return computerWon;
    }

    private void scoreScreen() {

        if (computerWon) { // prints got it if the computer won
            wordguessio.asciiArt(11);
        } else { // print the ascii art
            wordguessio.asciiArt(wrongLetters.size());
        }

        System.out.print(wrongLetters.size() + " fouten"); // print all the faults

        // shows the wrong letters
        if (wrongLetters.size() != 0) {
            System.out.print("(");
            for (int i = 0; i < wrongLetters.size(); i++) {

                System.out.print(wrongLetters.get(i));

            }
            System.out.print(")");
        }

        System.out.println(", aantal invoer: " + guesses);

        // print the content
        for (int i = 0; i < wordSize; i++) {

            System.out.print(content[i] + " ");

        }

        System.out.println();

        if (wrongLetters.size() == 10 || computerWon) { // prints if the game ends

            System.out.println("De computerscore is: " + guesses);

        }

        System.out.println();

        // if the player gives some wrong places
        if (wordList.size() == 0) {
            System.out.println(
                    "Het lijkt erop dat je een verkeerde positie hebt ingevuld, waardoor ik het woord niet meer kan vinden.");
            System.out.println("Het raden stopt hier dan ook.");
        }

    }

    private char randomLetter() {

        // variables
        Random l = new Random();
        char letter = '0';
        boolean loop = true;
        boolean inList = false;
        boolean again = false;

        while (loop) {

            letter = (char) (l.nextInt(25) + 'a'); // creates a random letter
            letter = Character.toUpperCase(letter); // capitalize it

            inList = false;
            again = false;

            // adds all the wrond and used letters to the forbiddenLetter list
            forbiddenLetters.addAll(wrongLetters);
            forbiddenLetters.addAll(usedLetters);

            // checks if the letter is in the possibleLetters list
            for (int i = 0; i < possibleLetters.size(); i++) {

                if (letter == possibleLetters.get(i)) {
                    again = true;
                }

            }

            // checks if the letter is in the forbiddenLetters list
            for (int i = 0; i < forbiddenLetters.size(); i++) {

                if (letter == forbiddenLetters.get(i)) {
                    inList = true;
                }
            }

            // if the letter is in the possibleletter list and the letter not in the
            // forbiddenletterlist than the loop stops
            if (again && !inList) {
                loop = false;
            }

        }
        return letter;
    }

    private void checkLetter(ArrayList<Integer> answers, char letter) {

        if (answers.get(0) == -99) { // when the input is a '-'

            wrongLetters.add(letter);
            inputIsGood = true;

            for (int i = 0; i < wordList.size(); i++) { // removes all the words with this letter

                if (wordList.get(i).indexOf(letter) != -1) {

                    wordList.remove(i);
                    i = i - 1;
                }
            }

        } else {

            // put the used letter in the list
            usedLetters.add(letter);
            boolean scan = true;

            for (int i = 0; i < answers.size(); i++) { // checks if content is already full

                if (content[answers.get(i)] != '.') {
                    System.out.println("Het lijkt erop dat op deze locatie al een letter staat.");
                    scan = false;
                    break;
                }

            }

            if (scan) {

                for (int i = 0; i < answers.size(); i++) { // adds letter to the content
                    content[answers.get(i)] = letter;
                }

                inputIsGood = true;

                improveGuessList(answers, letter); // improves the guess list by excluding letters

            }

        }

    }

    private void improveGuessList(ArrayList<Integer> answers, char letter) {

        // variables
        boolean lettersInPlace = true;

        for (int i = 0; i < wordList.size(); i++) { // removes all words from the list where the letters are not in the
                                                    // same place

            lettersInPlace = true;

            for (int j = 0; j < answers.size(); j++) {
                if (wordList.get(i).charAt(answers.get(j)) != letter) {
                    lettersInPlace = false;
                    break;
                }

            }

            if (!lettersInPlace) {
                wordList.remove(i);
                i = i - 1;
            }

        }

        // variables
        int counter = 0;

        for (int i = 0; i < wordList.size(); i++) { // removes all words where the letter occurs in other places

            counter = 0;
            for (int j = 0; j < Words.WORDLENGTH; j++) {

                if (wordList.get(i).charAt(j) == letter) {
                    counter++;
                }

            }

            if (counter > answers.size()) {
                wordList.remove(i);
                i = i - 1;
            }

        }

    }

}
