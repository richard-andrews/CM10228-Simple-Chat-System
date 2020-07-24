import java.awt.*;
import java.util.Random;

public class DoDBot {
    /*Instantiate the variables for the position of the bot on the map, a random number generator and the direction generated */
    public Point position;
    public Random random;
    private char direction;


    /**
     * The default constructor for Bot. Initialises the random number generator.
     */
    public DoDBot(){
        random = new Random();
    }


    /**
     * A method that handles the bots look function. This allows the bot to scan the 5x5 grid surrounding and determine
     * whether or not the humanplayer, the user, is within this area. Returns whether this search was successful in
     * finding the player or not.
     *
     * @param playerPosition Point - A point containing the x and y co ordinates of the human player.
     * @return result boolean - A boolean containing whether or not the bot was able to find the player within this
     * 5x5 search grid
     */
    public boolean botLook(Point playerPosition){
        /* Assumes the bot will not be able to find the player*/
        boolean result = false;

        /* Tests if the human player is within 2 squares both vertically and horizontally of the player, if so the bot
        has found the player and the result is set to true*/
        if ((Math.abs(position.x - playerPosition.x)<=2) && (Math.abs(position.y - playerPosition.y)<=2)){
            result = true;
        }
        return result;
    }


    /**
     * A method that handles the movement of the bot in a random direction according to a compass direction. This
     * uses a 'random' number generator to generate a number between 0 and 3 inclusive. Each number is a assigned a
     * character representing a compass direction eg. 0 = n = North .
     *
     * @return direction char - Returns a character representing the compass direction that the bot wishes to move in
     */
    public char botRandomMove(){
        /* Uses random number generator to generate a random number between 0 and 3 inclusive.*/
        int randDirection = random.nextInt(4);

        /* Filters through the cases until a case matches the randDirection variable  and the direction is assigned,
        if no cases match the direction is set to north. Once the case has been matched the direction is returned*/
        switch (randDirection){
            case 0:
                direction = 'n';
                break;
            case 1:
                direction = 'e';
                break;
            case 2:
                direction = 's';
                break;
            case 3:
                direction =  'w';
                break;
            default:
                direction = 'n';
                break;

        }
        return direction;
    }


    /**
     * A method that handles the targeted direction of the bot with the aim of the bot effectively tracking the player.
     * This is called once the bot has caught sight of the player on the previous turn. This calculates the absolute
     * difference between the bot's and player's vertical positions and horizontal positions. If the horizontal
     * distance is greater than the vertical distance, the bot will attempt travel East e or West w depending on which
     * brings it closer to the player. Alternatively if the horizontal distance is not greater than the vertical
     * distance, the bot attempts to move either North n or South s, again, such that the bot get closer to the player.
     *
     * @param playerPosition Point - The human player's position, this allows the two positions to be compared.
     * @return direction char - A single character representing the compass direction in which the bot wishes to move.
     */
    public char botChaseMove(Point playerPosition){
        /* Calculates the horizontal and vertical distances between the bot and the player*/
        int horizontalDiff = Math.abs(position.x-playerPosition.x);
        int verticalDiff = Math.abs(position.y - playerPosition.y);

        /* Compares the horizontal and vertical differences. If the horizontal is larger move either East or West*/
        if (horizontalDiff>verticalDiff){
            if (position.x > playerPosition.x){
                direction = 'w';
            }
            else{
                direction = 'e';
            }
        }
        /* Otherwise move either North or South */
        else{
            if (position.y < playerPosition.y){
                direction = 's';
            }
            else{
                direction = 'n';
            }
        }
        return direction;
    }
}


