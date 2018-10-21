import java.util.Scanner;
import javax.swing.JFrame;

/**
 * CoinSimViewer class
 * This class is dependent on the CoinSimComponent.
 * Users can type in the amount of trails they want. Create a frame containing the CoinSimComponent.
 * Display the graphics which is created from CoinSimComponent class.
 */
public class CoinSimViewer {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        int numTrials;

        System.out.print("Enter number of trials: ");
        numTrials = in.nextInt();

        //check for the validity of the input tries
        while (numTrials <= 0) {
            System.out.println("ERROR: Number entered must be greater than 0.");
            System.out.print("Enter number of trials: ");
            numTrials = in.nextInt();
        }

        in.close(); // close the scanner

        // Construct a frame and CoinSimComponent object, add the CoinSimComponent to the frame and make the frame visible
        JFrame frame = new JFrame();

        frame.setSize(800, 500);
        frame.setTitle("CoinSim");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        CoinSimComponent component = new CoinSimComponent(numTrials);
        frame.add(component);

        frame.setVisible(true);


    }
}