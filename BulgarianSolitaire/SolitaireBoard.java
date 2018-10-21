import java.util.ArrayList;
import java.util.Random;
import java.util.HashSet;

/*
  class SolitaireBoard
  The board for Bulgarian Solitaire.  You can change what the total number of cards is for the game
  by changing NUM_FINAL_PILES, below.  Don't change CARD_TOTAL directly, because there are only some values
  for CARD_TOTAL that result in a game that terminates.
  (See comments below next to named constant declarations for more details on this.)
*/


public class SolitaireBoard {

    public static final int NUM_FINAL_PILES = 9;
    // number of piles in a final configuration
    // (note: if NUM_FINAL_PILES is 9, then CARD_TOTAL below will be 45)

    public static final int CARD_TOTAL = NUM_FINAL_PILES * (NUM_FINAL_PILES + 1) / 2;
    // bulgarian solitaire only terminates if CARD_TOTAL is a triangular number.
    // see: http://en.wikipedia.org/wiki/Bulgarian_solitaire for more details
    // the above formula is the closed form for 1 + 2 + 3 + . . . + NUM_FINAL_PILES

    // Note to students: you may not use an ArrayList -- see assgt description for details.


    /**
     Representation invariant:
     - cardPiles array stores the number of cards for every piles
     - 1 <= the number of cards in each pile <= CARD_TOTAL
     - the number of cards each pile cannot be zero
     - numPiles is the number of card piles
     - 1 <= numPiles <= CARD_TOTAL
     - sum of cards in every piles equals CARD_TOTAL
     - the number of cards for every piles are in cardPiles locations: [0, numPiles - 1]
     */

    private int[] cardPiles = new int[CARD_TOTAL];
    // the element at one index means the number of cards in each pile

    private int numPiles = 0;
    // the number of card piles, it would be changed value after playing rounds


    /**
     Creates a solitaire board with the configuration specified in piles.
     piles has the number of cards in the first pile, then the number of cards in the second pile, etc.
     PRE: piles contains a sequence of positive numbers that sum to SolitaireBoard.CARD_TOTAL

     @param piles  the number of cards in each pile from user input
     */
    public SolitaireBoard(ArrayList<Integer> piles) {

        // store the number of cards in each pile on Partially-filled arrays according to assignment requirement
        for (int i = 0; i < piles.size(); i++) {
            cardPiles[i] = piles.get(i);
            numPiles++;
        }

        // assert statement, check the solitaire board data is in a valid state
        assert isValidSolitaireBoard();

    }


    /**
     Creates a solitaire board with a random initial configuration.
     */
    public SolitaireBoard() {

        int cardsLeft = CARD_TOTAL; // create "CARD_TOTAL" cards, check the cards is enough or not after random creating
        int index = 0;
        Random rand = new Random();

        // random creating cards till the number of cards equals CARD_TOTAL
        while (cardsLeft > 0) {

            // nextInt(int bound) method return the random number between 0 (inclusive) and bound (exclusive), so we need to add one
            cardPiles[index] = rand.nextInt(cardsLeft) + 1;
            cardsLeft = cardsLeft - cardPiles[index];
            index++;

        }

        numPiles = index; // the number of cards in each pile stores in cardPiles array location: [0, insex - 1]
        assert isValidSolitaireBoard();
    }


    /**
     Plays one round of Bulgarian solitaire.  Updates the configuration according to the rules
     of Bulgarian solitaire: Takes one card from each pile, and puts them all together in a new pile.
     The old piles that are left will be in the same relative order as before,
     and the new pile will be at the end.
     */
    public void playRound() {

        int index = 0;

        // take one card from each pile
        // and remove "0" element in cardPiles array, put positive number behind "0" into it's position as the same relative order
        // time complexity is O(n) and no new array using
        for (int i = 0; i < numPiles; i++) {
            cardPiles[i] -= 1;

            if (cardPiles[i] > 0) {
                cardPiles[index++] = cardPiles[i];
            }

        }

        // puts the cards from each pile all together in a new pile
        cardPiles[index] = numPiles;

        // numPiles add one because a new pile created
        numPiles = index + 1;

        assert isValidSolitaireBoard();

    }

    /**
     Returns true iff the current board is at the end of the game.  That is, there are NUM_FINAL_PILES
     piles and their sizes are 1, 2, 3, . . . , NUM_FINAL_PILES in any order.

     @ return  turn or false means the current board is at the end of the game or not
     */

    public boolean isDone() {

        HashSet<Integer> set = new HashSet<Integer>();

        // check there are NUM_FINAL_PILES piles or not
        if (numPiles == NUM_FINAL_PILES) {

            // check the piles sizes are 1, 2 , 3, . . . , NUM_FINAL_PILES or not
            for (int i = 0; i < numPiles; i++) {

                // the number of cards in each pile in the final configuration should greater than 0 and less or equel than NUM_FINAL_PILES
                if (cardPiles[i] <= 0 || cardPiles[i] > NUM_FINAL_PILES) {

                    assert isValidSolitaireBoard();
                    return false;
                }

                // a kind of pile sizes ( 1, 2 , 3, . . . , NUM_FINAL_PILES ) can noly occur once
                // so if the kind of size has already put into hashset, it would return false
                if(set.contains(cardPiles[i])) {

                    assert isValidSolitaireBoard();
                    return false;
                }

                set.add(cardPiles[i]);
            }

            assert isValidSolitaireBoard();
            return true;

        } else {

            assert isValidSolitaireBoard();
            return false;
        }

    }


    /**
     Returns current board configuration as a string with the format of
     a space-separated list of numbers with no leading or trailing spaces.
     The numbers represent the number of cards in each non-empty pile.

     @ return   current board configuration
     */
    public String configString() {

        String currBoardConfig = new String();

        // put the elements in the cardPiles array into a String, and we don't care about the elements behind numPile
        for (int i = 0; i < numPiles - 1; i++) {
            currBoardConfig = currBoardConfig + cardPiles[i] + " ";
        }

        currBoardConfig = currBoardConfig + cardPiles[numPiles - 1];

        assert isValidSolitaireBoard();
        return currBoardConfig;
    }


    /**
     Returns true iff the solitaire board data is in a valid state
     (See representation invariant comment for more details.)

     @ return  true or false means the solitaire board data is in a valid state or not
     */

    private boolean isValidSolitaireBoard() {

        int totalCards = 0;

        // 1 <= the number of cards in each pile <= CARD_TOTAL
        if ((numPiles < 1) || (numPiles > CARD_TOTAL)) {
            return false;
        }

        // 1 <= numPiles <= CARD_TOTAL
        for (int i = 0; i < numPiles; i++) {

            if (cardPiles[i] < 1 || cardPiles[i] > CARD_TOTAL) {
                return false;
            }

            totalCards = totalCards + cardPiles[i];
        }

        // sum of cards in every piles equals CARD_TOTAL
        if (totalCards != CARD_TOTAL) {
            return false;
        }

        return true;

    }

}
