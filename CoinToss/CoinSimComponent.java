import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;

/**
 * CoinSimComponent Class
 * This class is dependent on the CoinTossSimulator and Bar classes.
 * Perofrm the coin toss simulation with the amount of trials which user types in.
 * Compute the simulation result and draw it on a frame shows three labeled bars according to simulation result.
 * The three bars represent the result of Two-Heads, Teo-Tails and A Head and A Tails.
 */
public class CoinSimComponent extends JComponent {

    private int theTrials, frameHeight, frameWidth;
    private double scale;
    private double width; //the width between each bar, and between the end bars and each side of the window

    private final int SPACE_UNDER_BAR = 15; // space bewteen the bar and label
    private final int VERTICAL_BUFFER = 25; // vertical buffer is used to create space bewteen each bar and the frame
    private final int BAR_WIDTH = 50;

    private Bar left, center, right;

    CoinTossSimulator tossCoin = new CoinTossSimulator();

    /**
     Perform the coin toss simulation with the amount of trials which user types in

     @param numTrials  the amount of trials which the simulation performs
     */
    public CoinSimComponent(int numTrials) {
        tossCoin.run(numTrials);
    }

    /**
     Draw three labeled bars to show the simulation result of Two-Head, Two-Tails, and A Head and A tail.
     The three bars graph can get resized appropriately if the window gets resized.
     Use the draw method from bar class to display the bars.

     @param g  the Graphics object stores the graphics state which are used for drawing operations
     */
    public void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g; // Recover Graphics2D

        // acquire the frame height and width to do the bar resized function and locate the bars
        frameHeight = getHeight();
        frameWidth = getWidth();

        // Calculate the scale and width for the parameters of the Bar class
        // 2 vertical buffers means buffer at the top and bottom of the frame
        scale = (frameHeight - 2 * VERTICAL_BUFFER - SPACE_UNDER_BAR) / 100.00;
        width = (frameWidth - 3 * BAR_WIDTH) / 4.0;

        // Create and draw 3 bars by calling the Bar class
        // left bars represents Two-Heads result, center bar represents A Head and A Tail result and right bar represnts Two-Tails result
        left = new Bar(frameHeight - VERTICAL_BUFFER, (int) width, BAR_WIDTH, tossCoin.getTwoHeadsPercent(), scale, Color.RED,                       "Two Heads: " + tossCoin.getTwoHeads() + " (" + tossCoin.getTwoHeadsPercent() + "%)");

        center = new Bar(frameHeight - VERTICAL_BUFFER, 2 * (int) width + BAR_WIDTH, BAR_WIDTH, tossCoin.getHeadTailsPercent(), scale,                  Color.GREEN, " A Head and A Tail: " + tossCoin.getHeadTails() + " (" + tossCoin.getHeadTailsPercent() + "%)");

        right = new Bar(frameHeight - VERTICAL_BUFFER, 3 * (int) width + 2 * BAR_WIDTH, BAR_WIDTH, tossCoin.getTwoTailsPercent(), scale,               Color.BLUE, "Two Tails: " + tossCoin.getTwoTails() + " (" + tossCoin.getTwoTailsPercent() + "%)");

        left.draw(g2);
        center.draw(g2);
        right.draw(g2);
    }
}