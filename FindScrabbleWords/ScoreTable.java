/**
 The scores of scrabble letters and compute the total score of a word.
 Letters that occur more often are worth less and letters that occur less often are worth more
 */
import java.util.Map;
import java.util.HashMap;

public class ScoreTable {

    // use a Map to set scores of letters
    private Map<Character, Integer> score;

    // ScoreTable would be initialized by the ScoreTable Constructor
    public ScoreTable() {

        score = new HashMap<Character, Integer>();
        score.put('a', 1);
        score.put('b', 3);
        score.put('c', 3);
        score.put('d', 2);
        score.put('e', 1);
        score.put('f', 4);
        score.put('g', 2);
        score.put('h', 4);
        score.put('i', 1);
        score.put('j', 8);
        score.put('k', 5);
        score.put('l', 1);
        score.put('m', 3);
        score.put('n', 1);
        score.put('o', 1);
        score.put('p', 3);
        score.put('q', 10);
        score.put('r', 1);
        score.put('s', 1);
        score.put('t', 1);
        score.put('u', 1);
        score.put('v', 4);
        score.put('w', 4);
        score.put('x', 8);
        score.put('y', 4);
        score.put('z', 10);
    }


    /**
     Compute the total score of string s. Both of upper and lower case
     versions of the letters would have the same score , e.g., 'a' and
     'A' will have the same score.

     @param s  a word to be computed for scores

     @return total score of a word
     */
    public int getScore(String s) {

        int totalScore = 0;

        // turn all letters to lower case becauace both of the upper or lower case have the same score
        s = s.toLowerCase();

        // compute the total score
        for (int i = 0; i < s.length(); i++){
            totalScore += score.get(s.charAt(i));
        }

        return totalScore;
    }
}