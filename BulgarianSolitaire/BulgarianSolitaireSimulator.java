import java.util.ArrayList;
import java.util.Scanner;

/**
 Class BulgarianSolitaireSimulator
 playing Bulgarian Solitaire game with class SolitaireBoard
 users can type in initial configuration or start game with random initial configuration depend on different command-line argument
 command-line argument -u prompts for the initial configuration from the user, instead of generating a random configuration.
 command-line argument -s stops between every round of the game. The game only continues when the user hits enter
 */

public class BulgarianSolitaireSimulator {


    /**
     start Bulgarian Solitaire game with initial configuration by typing in from user or random creating

     @ param  args
     */
    public static void main(String[] args) {

        boolean singleStep = false; // iff ture game stops between every round
        boolean userConfig = false; // iff true game starts with initial configuration typing in from user


        // check command-line argument to control initial configuration and game stops between every round or not
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-u")) {
                userConfig = true;
            }
            else if (args[i].equals("-s")) {
                singleStep = true;
            }
        }

        SolitaireBoard boardGame;
        Scanner scannerInput = new Scanner(System.in);


        if (userConfig) {

            System.out.println("Number of total cards is " + SolitaireBoard.CARD_TOTAL);
            System.out.println("You will be entering the initial configuration of the cards (i.e., how many in each pile).");
            System.out.println("Please enter a space-separated list of positive integers followed by newline:");

            String inputString = scannerInput.nextLine();

            // if user types in invalid configuration, they should type in again
            while (!isValidConfigString(inputString)) {

                System.out.println("ERROR: Each pile must have at least one card and the total number of cards must be " +                                                  SolitaireBoard.CARD_TOTAL);
                System.out.println("Please enter a space-separated list of positive integers followed by newline:");
                inputString = scannerInput.nextLine();

            }

            Scanner readString = new Scanner(inputString);
            ArrayList<Integer> piles = new ArrayList<Integer>();

            while (readString.hasNext()) {
                piles.add(readString.nextInt());
            }

            // initial configuration from user passes to SolitaireBoard
            boardGame = new SolitaireBoard(piles);

        } else {

            // random initial configuration
            boardGame = new SolitaireBoard();
        }

        gameStart(boardGame, scannerInput, singleStep);
        System.out.println("Done!");

    }


    /**
     Return true iff th initial configuration from user inputs is in a valid state
     a valid state means all of inputs are positive integers only , 0 < all on input integers <= CARD_TOTAL
     and sum of input integers equals CARD_TOTAL

     @ param inputString  the initial configuration from user inputs

     @ return  true or false means the initial configuration is in a valid state or not
     */

    private static boolean isValidConfigString(String inputString) {

        Scanner readString = new Scanner(inputString);
        int sumInput = 0;

        while (readString.hasNext()) {


            // check inputs are integers or not
            if (readString.hasNextInt()) {

                int inputNumber = readString.nextInt();

                // check input integers's values are between 0 (exclusive) and CARD_TOTAL (inclusive) or not
                if (inputNumber > 0 && inputNumber <= SolitaireBoard.CARD_TOTAL) {

                    sumInput = sumInput + inputNumber;

                } else {

                    return false;

                }

            } else {

                return false;

            }

        }

        // check sum of input integers equals CARD_TOTAL or not
        if (sumInput != SolitaireBoard.CARD_TOTAL) {
            return false;
        }

        return true;
    }

    /**
     print out game results every round until game is done
     If command-line argument with -s, game stops between every round.
     The game only continues when the user hits enter

     @ param  boardGame initial a Solitaire Board game configuration
     @ nextStep  the user hits enter to continue the game
     @ singleStep  true or false means command-line argument with -s or not
     */

    private static void gameStart(SolitaireBoard boardGame, Scanner nextStep, boolean singleStep) {

        int round = 1;


        System.out.println("Initial configuration: " + boardGame.configString());

        // check the current board is at the end of the game or not
        while (!boardGame.isDone()) {

            boardGame.playRound();
            System.out.println("[" + round + "] Current configuration: " + boardGame.configString());

            // check the game should stop between every round or not
            if (singleStep) {
                System.out.print("<Type return to continue>");
                nextStep.nextLine(); // user hits enter to continue the game
            }

            round++;
        }
    }

}
