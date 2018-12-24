/**
 A Rack of Scrabble tiles
 */
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class Rack {

    /**
     Wrapper method of the private allSubsets function.
     Create unique and mult for the allSubsets function which has three arguments.
     Unique and mult describe a multiset such that mult[i] is the multiplicity of
     the char unique.charAt(i).

     @param s  a rack users type in to process

     @return all subsets of the indicated multiset
     */
    public ArrayList<String> allSubsets(String s) {

        String unique = "";

        // use a TreeMap to find unique letter of string s
        Map<Character, Integer> temp = new TreeMap<Character, Integer>();


        for (int i = 0; i < s.length(); i++) {

            char c = s.charAt(i);

            if (temp.containsKey(c)) {
                temp.put(c, temp.get(c) + 1);
            } else {
                temp.put(c, 1);
            }
        }

        // string s has temp.size() unique letters
        int[] mult = new int[temp.size()];
        int index = 0;

        // build the string unique and mult[]
        for (Map.Entry<Character, Integer> entry : temp.entrySet()) {
            unique = unique + entry.getKey();
            mult[index++] = entry.getValue();
        }

        // pass parameters to allSubsets function which has three arguments
        // start from position 0
        return allSubsets(unique, mult, 0);
    }

    /**
     Finds all subsets of the multiset starting at position k in unique and mult.
     unique and mult describe a multiset such that mult[i] is the multiplicity of the char
     unique.charAt(i).
     PRE: mult.length must be at least as big as unique.length()
     0 <= k <= unique.length()

     @param unique a string of unique letters
     @param mult the multiplicity of each letter from unique.
     @param k the smallest index of unique and mult to consider.

     @return all subsets of the indicated multiset
     @author Claire Bono
     */
    private static ArrayList<String> allSubsets(String unique, int[] mult, int k) {
        ArrayList<String> allCombos = new ArrayList<>();

        if (k == unique.length()) {  // multiset is empty
            allCombos.add("");
            return allCombos;
        }

        // get all subsets of the multiset without the first unique char
        ArrayList<String> restCombos = allSubsets(unique, mult, k+1);

        // prepend all possible numbers of the first char (i.e., the one at position k)
        // to the front of each string in restCombos.  Suppose that char is 'a'...

        String firstPart = "";          // in outer loop firstPart takes on the values: "", "a", "aa", ...
        for (int n = 0; n <= mult[k]; n++) {
            for (int i = 0; i < restCombos.size(); i++) {  // for each of the subsets
                // we found in the recursive call
                // create and add a new string with n 'a's in front of that subset
                allCombos.add(firstPart + restCombos.get(i));
            }
            firstPart += unique.charAt(k);  // append another instance of 'a' to the first part
        }

        return allCombos;
    }
}
