package wordguess;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * @author Max Kik
 * @version 1.0
 * @date 16-01-2022
 * 
 **/

public class Words {

    // constants
    static int WORDLENGTH;
    static final boolean CHEATMODE = false;

    // instance variables
    private ArrayList<String> wordlist = new ArrayList<>();
    private Random random = new Random();

    // constructor
    public Words() {

        String listLocation;
        // otherwise the system crashes
        if (WORDLENGTH == 0) {
            listLocation = "resources/woordenlijst_12.txt";
        } else {
            listLocation = "resources/woordenlijst_" + WORDLENGTH + ".txt";
        }

        try {
            Scanner inscan = new Scanner(
                    new File(listLocation));
            while (inscan.hasNextLine()) {
                wordlist.add(inscan.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getRandomWord() {
        int index = random.nextInt(wordlist.size());
        return (wordlist.get(index));
    }

    public ArrayList<String> getAllWords() {
        return wordlist;
    }

}
