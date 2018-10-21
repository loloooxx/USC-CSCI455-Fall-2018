/**
 * CoinTossSimulatorTester Class
 * Test all the functions in the CoinTossSimulator class.
 */

import java.util.Scanner;

public class CoinTossSimulatorTester {
    public static void main (String args[]) {

        CoinTossSimulator coinTossSimulator = new CoinTossSimulator();

        coinTossSimulator.run(0);
        System.out.println("After constructor:");
        System.out.println("Number of trials [exp:0]: " + coinTossSimulator.getNumTrials());
        System.out.println("Two-head tosses: " + coinTossSimulator.getTwoHeads());
        System.out.println("Two-tail tosses: " + coinTossSimulator.getTwoTails());
        System.out.println("One-head one-tail tosses: " + coinTossSimulator.getHeadTails());
        System.out.print("Tosses add up correctly? ");
        if ( coinTossSimulator.getNumTrials() == 0) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }



        coinTossSimulator.run(1);
        System.out.println("");
        System.out.println("After run(1):");
        System.out.println("Number of trials [exp:1]: " + coinTossSimulator.getNumTrials());
        System.out.println("Two-head tosses: " + coinTossSimulator.getTwoHeads());
        System.out.println("Two-tail tosses: " + coinTossSimulator.getTwoTails());
        System.out.println("One-head one-tail tosses: " + coinTossSimulator.getHeadTails());
        System.out.print("Tosses add up correctly? ");
        if ( coinTossSimulator.getNumTrials() == 1) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }


        coinTossSimulator.run(10);
        System.out.println("");
        System.out.println("After run(10):");
        System.out.println("Number of trials [exp:11]: " + coinTossSimulator.getNumTrials());
        System.out.println("Two-head tosses: " + coinTossSimulator.getTwoHeads());
        System.out.println("Two-tail tosses: " + coinTossSimulator.getTwoTails());
        System.out.println("One-head one-tail tosses: " + coinTossSimulator.getHeadTails());
        System.out.print("Tosses add up correctly? ");
        if ( coinTossSimulator.getNumTrials() == 11) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }


        coinTossSimulator.run(100);
        System.out.println("");
        System.out.println("After run(100):");
        System.out.println("Number of trials [exp:111]: " + coinTossSimulator.getNumTrials());
        System.out.println("Two-head tosses: " + coinTossSimulator.getTwoHeads());
        System.out.println("Two-tail tosses: " + coinTossSimulator.getTwoTails());
        System.out.println("One-head one-tail tosses: " + coinTossSimulator.getHeadTails());
        System.out.print("Tosses add up correctly? ");
        if ( coinTossSimulator.getNumTrials() == 111) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }

        System.out.println("");
        System.out.println("[...output for tests with different number of trials were here...]");
        System.out.println("");


        coinTossSimulator.reset();
        System.out.println("After reset:");
        System.out.println("Number of trials [exp:0]: " + coinTossSimulator.getNumTrials());
        System.out.println("Two-head tosses: " + coinTossSimulator.getTwoHeads());
        System.out.println("Two-tail tosses: " + coinTossSimulator.getTwoTails());
        System.out.println("One-head one-tail tosses: " + coinTossSimulator.getHeadTails());
        System.out.print("Tosses add up correctly? ");
        if ( coinTossSimulator.getNumTrials() == 0) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }


        coinTossSimulator.run(1000);
        System.out.println("");
        System.out.println("After run(1000):");
        System.out.println("Number of trials [exp:1000]: " + coinTossSimulator.getNumTrials());
        System.out.println("Two-head tosses: " + coinTossSimulator.getTwoHeads());
        System.out.println("Two-tail tosses: " + coinTossSimulator.getTwoTails());
        System.out.println("One-head one-tail tosses: " + coinTossSimulator.getHeadTails());
        System.out.print("Tosses add up correctly? ");
        if ( coinTossSimulator.getNumTrials() == 1000) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }

        System.out.println("");
        System.out.println("[...output for tests on more runs were here...]");

    }
}