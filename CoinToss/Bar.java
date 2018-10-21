import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

/**
 * Bar class
 * A labeled bar that can serve as a single bar in a bar graph.
 * The text for the label is centered under the bar.
 *
 * NOTE: we have provided the public interface for this class. Do not change
 * the public interface. You can add private instance variables, constants,
 * and private methods to the class. You will also be completing the
 * implementation of the methods given.
 *
 */
public class Bar {

    private int theBottom;
    private int theLeft;
    private int theWidth;
    private double height;
    private Color theColor;
    private String theLabel;
    private final int SPACE_UNDER_BAR = 15; // space bewteen the bar and label



    /**
     Creates a labeled bar.  You give the height of the bar in application
     units (e.g., population of a particular state), and then a scale for how
     tall to display it on the screen (parameter scale).

     @param bottom  location of the bottom of the label
     @param left  location of the left side of the bar
     @param width  width of the bar (in pixels)
     @param barHeight  height of the bar in application units
     @param scale  how many pixels per application unit
     @param color  the color of the bar
     @param label  the label at the bottom of the bar
     */
    public Bar(int bottom, int left, int width, int barHeight,
               double scale, Color color, String label) {

        theBottom = bottom;
        theLeft = left;
        theWidth = width;
        height = scale * barHeight; // barHeight in application units is converted to height in pixels
        theColor = color;
        theLabel = label;
    }

    /**
     Draw the labeled bar and colour it.
     Put the label unber the bar and centre it.

     @param g2  the graphics context
     */
    public void draw(Graphics2D g2) {

        Rectangle bar = new Rectangle(theLeft, theBottom - SPACE_UNDER_BAR - (int) height, theWidth, (int) height);
        g2.setColor(theColor);
        g2.fill(bar);

        g2.setColor(Color.BLACK);
        int labelWidth = g2.getFontMetrics().stringWidth(theLabel); // get the width of the label
        g2.drawString(theLabel, (theLeft + theWidth / 2) - labelWidth / 2, theBottom); // put the label unber the bar and centre it

    }
}
