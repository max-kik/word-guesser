package wordguess;

import java.util.ArrayList;
import java.util.Scanner;

public class WordguessIO {

    // instance variables
    private Scanner scanner; // the scanner for the input

    WordguessIO() {

        scanner = new Scanner(System.in);

    }

    public String getPlayerName() { // method to get the name of the player

        // variables
        String input = "?";
        boolean repeat = true;

        while (repeat) {
            System.out.print("Wat is je naam? : ");
            input = scanner.nextLine();
            if (input.length() < 2) { // checks if the name is long enough
                System.out.println("Je naam moet minimaal twee letter bevatten");
            } else if (input.matches("[a-zA-Z]+") == false) { // checks if there is only A-Z and a-z used in the string
                System.out.println("Je kan alleen maar letters in je naam gebruiken");
            } else {
                input = input.toLowerCase(); // makes everything lower case
                input = input.substring(0, 1).toUpperCase() + input.substring(1); // makes the first letter upper case
                repeat = false;
            }
        }

        return input;
    }

    public int wordSize() { // method to get the size of the word

        // variables
        String input = "?";
        int wordSize = 0;
        boolean repeat = true;

        while (repeat) {
            System.out.println();
            System.out.print("Hoe veel letter woorden wil je spelen? : ");
            input = scanner.nextLine();

            if (input.length() != 2) {
                System.out.println("De input is te klein of te groot.");
            } else if ((input.charAt(0) != '1') || (input.charAt(1) < '0' || input.charAt(1) > '3')) {
                System.out.println("Het getal is te groot of te klein.");
            } else {
                wordSize = Integer.parseInt(input);
                repeat = false;
            }

        }

        return wordSize;
    }

    public String getHeadorTail() { // method to get head or tail

        // variables
        String input = "?";
        boolean check = false;
        boolean secondCheck = false;

        while (!check) {
            System.out.print("Wil je kop of munt? : ");
            input = scanner.nextLine();
            secondCheck = false;

            if (input.length() < 3 || input.length() > 4) { // checks if input is long enough
                System.out.println("Je input is te groot of te klein.");
            } else if (input.matches("[a-zA-Z]+") == false) { // checks if there is only A-Z and a-z used in the input
                System.out.println("Je invoer kan alleen maar uit letters bestaan.");
            } else { // set input to lower case, so lower case and upper case are possible
                input = input.toLowerCase();
                secondCheck = true;
            }

            if (secondCheck) {
                if (input.equals("kop") || input.equals("munt")) { // checks if input is "kop" or "munt"
                    check = true;
                } else {
                    System.out.println("Je input moet wel kop of munt zijn.");
                }
            }

        }

        return input;
    }

    public boolean getEntertoContinue() { // method to get an enter

        // variables
        String input = "?";
        boolean repeat = true;

        while (repeat) { // checks if the input is an enter
            input = scanner.nextLine();
            if (input.equals("")) {
                repeat = false;
            }
        }

        return repeat;

    }

    public String getPlayerGuess() { // method to get the guess of the player

        // variables
        String input = "?";
        boolean repeat = true;

        while (repeat) {
            System.out.print("raad een letter of het woord : ");
            input = scanner.nextLine();
            if (input.length() < 1 || input.length() > Words.WORDLENGTH) { // checks if the input is less than 1 or
                                                                           // greater than 10, 11, 12 or 13
                System.out.println("Je invoer is te groot of te klein.");
            } else if (input.matches("[a-zA-Z]+") == false) { // checks if there is only A-Z and a-z used in the input
                System.out.println("Je invoer kan alleen maar uit letters bestaan.");
            } else if (input.length() > 1 && input.length() < Words.WORDLENGTH) { // checks if the word is the right
                                                                                  // size
                System.out.println("Je kan alleen " + Words.WORDLENGTH + " letter woorden raden.");
            } else {
                input = input.toUpperCase(); // makes all the letters upper case
                repeat = false;
            }
        }
        return input;

    }

    public ArrayList<Integer> getPlayerAnswer() { // method to get the feedback of the player when the computer is
                                                  // guessing

        // variables
        String input = "?";
        boolean answerIsGood = false;
        ArrayList<Integer> letterPlace = new ArrayList<>();
        int secondNumber = Words.WORDLENGTH - 10;
        char secondNumberChar = (char) (secondNumber + '0');

        while (!answerIsGood) {

            System.out.print("Geef de (reeks) plaats(en) of '-' : ");
            input = scanner.nextLine();
            if (input.equals("-")) { // checks if the input is '-' an will add it to the array if so
                letterPlace.add(-99);
                answerIsGood = true;
            } else {

                String[] splitAnswer = input.split(" "); // splits the string

                for (int i = 0; i < splitAnswer.length; i++) { // a check for all split input

                    if (splitAnswer[i].length() < 1 || splitAnswer[i].length() > 2) { // checks if the split is to small
                                                                                      // or to big
                        System.out.println("Je invoer is te groot of te klein.");
                        answerIsGood = false;
                        break;
                    } else if ((splitAnswer[i].length() == 1)
                            && (splitAnswer[i].charAt(0) < '1' || splitAnswer[i].charAt(0) > '9')) { // checks if the
                                                                                                     // integer is to
                                                                                                     // small or to big
                        System.out.println("Het getal is te groot of te klein of het is geen getal.");
                        answerIsGood = false;
                        break;

                    } else if ((splitAnswer[i].length() == 2)
                            && ((splitAnswer[i].charAt(0) < '1' || splitAnswer[i].charAt(0) > '9')
                                    || (splitAnswer[i].charAt(1) < '0'
                                            || splitAnswer[i].charAt(1) > secondNumberChar))) { // checks if the
                                                                                                // integers are to small
                                                                                                // or to big
                        System.out.println("Het getal is te groot of te klein of het is geen getal.");
                        answerIsGood = false;
                        break;
                    } else {
                        letterPlace.add(Integer.parseInt(splitAnswer[i]) - 1); // add integers to the array, minus 1,
                                                                               // because the count started at zero
                        answerIsGood = true;
                    }
                }
            }

        }

        return letterPlace;

    }

    public void asciiArt(int artNumber) { // method for the ASCII-art

        // variables
        String[] art = new String[12];

        art[0] = "____________________________________________________________\n"
                + "                                                           \n"
                + "                                                           \n"
                + "              o       o       o          o                 \n"
                + "                      |      <|>        /|\\               \n"
                + "______________________________|_________/_\\_________________";

        art[1] = "____________________________________________________________\n"
                + "  ,--.                                                     \n"
                + " | oo |                                                    \n"
                + " | ~~ |       o       o      o          o                  \n"
                + " |/\\/\\|               |       |>         |\\             \n"
                + "______________________________|_________/_\\_________________";

        art[2] = "____________________________________________________________\n"
                + "      ,--                                                  \n"
                + "     |  oo|                                                \n"
                + "     |  ~~|   o       o       o         _o/                \n"
                + "     |/\\/\\|           |      <|\\         |              \n"
                + "______________________________|_________/_\\_________________";

        art[3] = "____________________________________________________________\n"
                + "         ,--.                                              \n"
                + "        |  oo|                              help           \n"
                + "        |  {}|o       o      _o/        \\o/               \n"
                + "        |/\\/\\|        |       |          |               \n"
                + "______________________________/)_________|__________________";

        art[4] = "____________________________________________________________\n"
                + "               ,--.                                        \n"
                + "              |  oo|                                       \n"
                + "              |  ~~|  o      __o         \\o               \n"
                + "              |/\\/\\|  |       |\\         |>             \n"
                + "______________________________/_)________|\\_________________";

        art[5] = "____________________________________________________________\n"
                + "                 ,--.                                      \n"
                + "                |  oo|                                     \n"
                + "                |  {}|o       \\o                          \n"
                + "                |/\\/\\|        |\\         __\\o          \n"
                + "______________________________|\\________|)__|_______________";

        art[6] = "____________________________________________________________\n"
                + "                      ,--.                                 \n"
                + "                     |  oo|                                \n"
                + "                     |  ~~|  \\o/       _|                \n"
                + "                     |/\\/\\|   |          \\o             \n"
                + "_____________________________/_\\_________(_\\________________";

        art[7] = "____________________________________________________________\n"
                + "                          ,--.                             \n"
                + "                         |  oo|                            \n"
                + "                         |  {}|o                           \n"
                + "                         |/\\/\\||        o/_              \n"
                + "_________________________________________|_(\\_______________";

        art[8] = "____________________________________________________________\n"
                + "                                  ,--.                     \n"
                + "                                 |  oo|                    \n"
                + "                                 |  ~~|  o_                \n"
                + "                                 |/\\/\\|  /\\             \n"
                + "_________________________________________/_|________________";

        art[9] = "____________________________________________________________\n"
                + "                                    ,--.                   \n"
                + "                                   |  oo|                  \n"
                + "                                   |  {}|o                 \n"
                + "                                   |/\\/\\||               \n"
                + "____________________________________________________________";

        art[10] = "____________________________________________________________\n"
                + "     ___    __    __  __  ____   _____  _  _  ____  ____   \n"
                + "    / __)  /__\\  (  \\/  )( ___) (  _  )( \\/ )( ___)(  _ \\  \n"
                + "   ( (_-. /(__)\\  )    (  )__)   )(_)(  \\  /  )__)  )   /  \n"
                + "    \\___/(__)(__)(_/\\/\\_)(____) (_____)  \\/  (____)(_)\\_)  \n"
                + "____________________________________________________________";

        art[11] = "____________________________________________________________\n"
                + "               ___  _____  ____   ____  ____                 \n"
                + "              / __)(  _  )(_  _) (_  _)(_  _)                \n"
                + "             ( (_-. )(_)(   )(    _)(_   )(                  \n"
                + "              \\___/(_____) (__)  (____) (__)                \n"
                + "____________________________________________________________";

        System.out.println(art[artNumber]);

    }

}
