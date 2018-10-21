/**
 * class CoinTossSimulator
 *
 * Simulates trials of repeatedly tossing two coins and allows the user to access the
 * cumulative results.
 *
 * NOTE: we have provided the public interface for this class.  Do not change
 * the public interface.  You can add private instance variables, constants,
 * and private methods to the class.  You will also be completing the
 * implementation of the methods given.
 *
 * Invariant: getNumTrials() = getTwoHeads() + getTwoTails() + getHeadTails()
 *
 */

import java.util.Random;

public class CoinTossSimulator {

    private Random generator;
    private int theNumTrials, numTwoHeads, numTwoTails, numHeadTails;
    private double percentTwoHeads, percentTwoTails, percentHeadTails;

    /**
     Creates a coin toss simulator with no trials done yet.
     */
    public CoinTossSimulator() {
        theNumTrials = 0;
        numTwoHeads = 0;
        numTwoTails = 0;
        numHeadTails = 0;
        generator = new Random();
    }


    /**
     Runs the simulation for numTrials more trials. Multiple calls to this method
     without a reset() between them *add* these trials to the current simulation.

     @param numTrials  number of trials to for simulation; must be >= 1
     */
    public void run(int numTrials) {
        for (int i = 1; i <= numTrials; i++) {
            int resultA = generator.nextInt(2);
            int resultB = generator.nextInt(2);

            if (resultA == 0 && resultB == 0) {
                numTwoHeads++;
            } else if (resultA == 1 && resultB == 1) {
                numTwoTails++;
            } else {
                numHeadTails++;
            }
        }
    }

    /**
     Get number of trials performed since last reset.

     @return  the number of trials the simulator performed
     */
    public int getNumTrials() {
        theNumTrials = numTwoHeads + numTwoTails + numHeadTails;
        return theNumTrials;
    }

    /**
     Get number of trials that came up two heads since last reset.

     @return  the number of trials thich came up two heads
     */
    public int getTwoHeads() {
        return numTwoHeads;
    }


    /**
     Get number of trials that came up two tails since last reset.

     @return  the number of trials thich came up two tails
     */
    public int getTwoTails() {
        return numTwoTails;
    }

    /**
     Get number of trials that came up one head and one tail since last reset.

     @return  the number of trials thich came up a head and a tail
     */
    public int getHeadTails() {
        return numHeadTails;
    }

    /**
     Resets the simulation, so that subsequent runs start from 0 trials done.
     */
    public void reset() {
        theNumTrials = 0;
        numTwoHeads = 0;
        numTwoTails = 0;
        numHeadTails = 0;
    }

    /**
     Calculate the percentage of Two-Heads result since last reset.
     Also rounds the percentage to the next integer value.

     @return the percentage of Two-Heads result
     */
    public int getTwoHeadsPercent() {
        percentTwoHeads = Math.round((getTwoHeads() * 100) / getNumTrials());
        return (int) percentTwoHeads;
    }

    /**
     Calculate the percentage of Two-Tails result since last reset.
     Also rounds the percentage to the next integer value.

     @return the percentage of Two-Tails result
     */
    public int getTwoTailsPercent() {
        percentTwoTails = Math.round((getTwoTails() * 100) / getNumTrials());
        return (int) percentTwoTails;
    }

    /**
     Calculate the percentage of A Head and A Tails result since last reset.
     Also rounds the percentage to the next integer value.

     @return the percentage of A Head and A Tails result
     */
    public int getHeadTailsPercent() {
        percentHeadTails = Math.round((getHeadTails() * 100) / getNumTrials());
        return (int) percentHeadTails;
    }

}
