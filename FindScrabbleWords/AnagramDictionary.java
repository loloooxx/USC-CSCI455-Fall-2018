/**
 A dictionary of all anagram sets.
 Note: the processing is case-sensitive; so if the dictionary has all lower
 case words, you will likely want any string you test to have all lower case
 letters too, and likewise if the dictionary words are all upper case.
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Scanner;

public class AnagramDictionary {

    // A anagram dictionary built by a map.
    // The key is also a map comprised of the unique letter in key and its multiplicity in value.
    // the value is a list of anagrams of the same key
    private Map<Map<Character, Integer>, ArrayList<String>> anagrams;

    /**
     Create an anagram dictionary from the list of words given in the file
     indicated by fileName.
     PRE: The strings in the file are unique.

     @param fileName  the name of the file to read from

     @throws FileNotFoundException  if the file is not found
     */
    public AnagramDictionary(String fileName) throws FileNotFoundException {

        anagrams = new HashMap<Map<Character, Integer> , ArrayList<String>>();
        ArrayList<String> temp = new ArrayList<String>();
        Scanner sc = new Scanner(new File(fileName)); // read the given dictionary file
        String words = null;

        while(sc.hasNext()) {

            words = sc.next(); // read every words from the dictionary file

            // use TreeMap for storing the key for building the anagram dictionary
            // the anagrams would have the same key of the anagram dictionary
            Map<Character, Integer> word = new TreeMap<Character, Integer>();

            // find every word's component, the unique letters and its multiplicity
            for (int i = 0; i < words.length(); i++) {
                char c = words.charAt(i);

                if (word.containsKey(c)) {
                    word.put(c, word.get(c) + 1);
                } else {
                    word.put(c, 1);
                }
            }

            // build the anagram dictionary by puting the anagrams together in a arraylist
            if (anagrams.containsKey(word)) {
                temp = anagrams.get(word);
                temp.add(words);
                anagrams.put(word, temp);
            } else {
                temp = new ArrayList<String>();
                temp.add(words);
                anagrams.put(word, temp);
            }
        }
    }


    /**
     Get all anagrams of the given string. This method is case-sensitive.
     E.g. "CARE" and "race" would not be recognized as anagrams.

     @param s string to process

     @return a list of the anagrams of s
     */
    public ArrayList<String> getAnagramsOf(String s) {

        // find the string s's component, the unique letters and its multiplicity, storing it by TreeMap
        Map<Character, Integer> temp = new TreeMap<Character, Integer>();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (temp.containsKey(c)) {
                temp.put(c, temp.get(c) + 1);
            } else {
                temp.put(c, 1);
            }
        }

        // return anagrams of string s if its component is a key from the anagram dictionary
        // if its component is not a key, return empty list
        if (anagrams.containsKey(temp)) {
            return anagrams.get(temp);
        } else {
            return new ArrayList<String>();
        }
    }
}
