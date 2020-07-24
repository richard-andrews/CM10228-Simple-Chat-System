import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class DoDInputHandler {
    private static BufferedReader lineIn = null;

    /**
     * A method that handles the ongoing communication from the clients via the server through an endless loop. Detects
     * if the game is being addressed and calls methods to extract the message and find the appropriate reply before
     * sending the reply back to the clients via the server.
     */
    public static String getNextCommand(Socket serverSocket) {
        try {
            lineIn = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
        }
        catch (Exception ignored) {
        }

        String newMessageIn;
        String newCommand = null;

        while(newCommand == null){
            //asserts message as empty after each new message
            newMessageIn = "";

            //attempts to read new incoming message and parse it to lower case
            try {
                newMessageIn = lineIn.readLine().toLowerCase();
            }
            catch (Exception ignored) {
            }

            //checks if the game is being addressed
            if (newMessageIn.contains("@dod")){
                newCommand = extractCommand(newMessageIn);
            }
        }

        return newCommand;
    }


    /**
     * A method that handles the extraction of the message for the bot once it has been detected that the incoming
     * message is for the bot. This means removing the prefixed '@bot' that denotes that is it addressed to the bot.
     *
     * @param newMessageIn - A string of the message that has just been received.
     * @return - Returns a string that is the message meant for the bot to then be 'understood' and replied to
     */
    private static String extractCommand(String newMessageIn) {
        int commandStartPos;
        String command;
        commandStartPos = newMessageIn.indexOf("@dod") + 5;
        command = newMessageIn.substring(commandStartPos);
        return command;
    }
}
