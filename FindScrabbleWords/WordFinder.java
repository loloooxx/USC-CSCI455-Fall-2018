// Name: Wei-Hsiu Chang
// USC NetID: 6625095261
// CS 455 PA4
// Fall 2018


/**
 A console-based program that finds all possible words that can be made from a rack of Scrabble tiles.
 Taking an optional command-line argument for the dictionary file name. If that argument is left off,
 it will use the Scrabble dictionary file sowpods.txt. If the dictionary file does not exist, responsing
 an error message and exit.

 Note: common format is as following. java WordFinder [dictionaryFile]
 */
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.Scanner;
import java.util.Collections;

public class WordFinder {

    public static void main(String[] args) {

        String fileName = null;

        // If that argument is left off, it will use the Scrabble dictionary file sowpods.txt.
        // If the dictionary file does not exist, responsing an error message and exit.
        try
        {
            if (args.length == 0) {
                fileName = "sowpods.txt";
                scrabble(fileName);
            } else {
                fileName = args[0];
                scrabble(fileName);
            }
        }

        catch(FileNotFoundException exception)
        {
            System.out.println("File not found: " + fileName);
        }
    }


    /**
     Create an anagram dictionary from a given dictionary file. Find all subsets of
     a given rack and match them with the anagram dictionary. Print the result with
     scores got from score table.

     @param fileName  the name of the dictionary to read from

     @throws FileNotFoundException  if the file is not found
     */
    private static void scrabble(String fileName) throws FileNotFoundException {

        AnagramDictionary ad = new AnagramDictionary(fileName);
        Rack rack = new Rack();
        Scanner in = new Scanner(System.in);
        String target = null;
        String filterTarget = null;
        boolean isOver = false;

        System.out.println("Type . to quit.");

        // Repeat asking for a rack if players do not type in "." to exit
        while(!isOver) {

            System.out.print("Rack? ");
            target = in.next(); // users type in a rack
            filterTarget = target.replaceAll("[^a-zA-Z]+", ""); // ignore any non-letter components on a rack

            if (target.equals(".")) {

                isOver = true;

            } else {

                ArrayList<String> subsets = rack.allSubsets(filterTarget); // find all subsets of a rack
                ArrayList<String> result = new ArrayList<String>();

                for (int i = 0; i < subsets.size(); i++) {

                    ArrayList<String> temp = new ArrayList<String>();
                    temp = ad.getAnagramsOf(subsets.get(i)); // find anagrams from a subset of a rack

                    for (int j = 0; j < temp.size(); j++) {
                        result.add(temp.get(j));
                    }
                }

                System.out.println("We can make " + result.size() + " words from " + "\"" + filterTarget + "\"");

                // no result to print out, keep asking for a rack
                if (result.size() == 0) {
                    continue;
                }

                System.out.println("All of the words with their scores (sorted by score):");
                printResult(result);

            }
        }

        in.close();
    }

    /**
     Print out all the anagrams of all subsets of a rack in descending order of scores
     and then ascending order of anagrans if the scores are the same

     @param result  a list of all the anagrams of all subsets of a rack without correctly ordering
     */
    private static void printResult(ArrayList<String> result) {

        // use a TreeMap to store scores in key and a list of anagrams in value
        // Because TreeMap's defalut ordering is ascending, we have to change to
        // descending as we want ordering the result by scores in descending order.
        Map<Integer, ArrayList<String>> orderResult = new TreeMap<Integer, ArrayList<String>>(Collections.reverseOrder());
        ArrayList<String> temp = new ArrayList<String>();
        ScoreTable st = new ScoreTable();

        // find every anagrams's scores and put the anagrams with the same scores into the list mapping the same key
        for (int i = 0; i < result.size(); i++) {

            int score = st.getScore(result.get(i));


            if (orderResult.containsKey(score)) {

                temp = orderResult.get(score);
                temp.add(result.get(i));

            } else {

                temp = new ArrayList<String>();
                temp.add(result.get(i));
            }

            orderResult.put(score, temp);
        }

        for (Map.Entry<Integer, ArrayList<String>> entry : orderResult.entrySet()) {

            ArrayList<String> sameScore = entry.getValue();
            Collections.sort(sameScore); // order the list of the anagrams with same scores by alphabet in descending order

            // print out the result
            for (int i = 0; i < sameScore.size(); i++) {
                System.out.println(entry.getKey() + ": " + sameScore.get(i));
            }
        }
    }
}