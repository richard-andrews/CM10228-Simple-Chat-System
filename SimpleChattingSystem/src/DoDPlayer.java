import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class DoDPlayer {
    /* instantiate global variables*/
    public Point position;
    public int goldCollected;

    /**
     * The default constructor for HumanPlayer. Initialise global variables with values.
     */
    public DoDPlayer() {
        position = new Point(0, 0);
        goldCollected = 0;
    }
}