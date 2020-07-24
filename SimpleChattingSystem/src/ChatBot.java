//Importing the necessary libraries
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatBot extends CommandLineArgsHandler{
    //A variable holding the socket that the bot communicates to the server on.
    private Socket serverSocket;


    /**
     * The constructor for the ChatBot class. Retrieves the port number and IP address from the arguments if given,
     * otherwise the defaults are used. Attempts to create the socket connection to the server.
     *
     * @param args - An array of strings of the arguments given by the user at the execution of the file.
     */
    private ChatBot(String[] args) {
        int port = setPort(args, "-ccp", 14001);
        String address = setAddress(args, "localhost");

        try {
            serverSocket = new Socket(address, port);
            System.out.println("\nConnecting to the server...\nBot connection established.");

        } catch (Exception e) {
            System.out.println("\nServer connection could not be established.");
        }
    }


    /**
     * A method that handles the start up of the bot, creating necessary variables for two way communication. Once
     * communication is made, messages to the server, and therefore all connected clients that the bot is connected and
     * assigns itself a name. Calls method to handle further communication from the server
     */
    private void runBot(){
        BufferedReader lineIn = null;
        PrintWriter lineOut = null;

        try {
            lineIn = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
            lineOut = new PrintWriter(serverSocket.getOutputStream(), true);
        }
        catch (Exception ignored) {
        }

        lineOut.println("Bot is connected.");
        lineOut.println("Name:Bot845");

        receiveMessages(lineIn, lineOut);
    }


    /**
     * A method that handles the ongoing communication from the clients via the server through an endless loop. Detects
     * if the bot is being addressed and calls methods to extract the message and find the appropriate reply before
     * sending the reply back to the clients via the server.
     *
     * @param lineIn - A buffered reader variable to handle incoming messages from the server through the socket.
     * @param lineOut - A print writer variable to handle outgoing messages from the server through the socket.
     */
    private void receiveMessages(BufferedReader lineIn, PrintWriter lineOut) {
        String newMessageIn;
        String messageToBot;
        String reply;

        while(true){
            //asserts message as empty after each new message
            newMessageIn = "";

            //attempts to read new incoming message and parse it to lower case
            try {
                if (lineIn != null){
                    newMessageIn = lineIn.readLine().toLowerCase();
                }
            }
            catch (Exception ignored) {
            }

            //checks if the bot is being addressed
            if (newMessageIn.contains("@bot")){
                messageToBot = extractMessage(newMessageIn);
                reply = sortMessage(messageToBot);

                lineOut.println(reply);
            }
        }
    }


    /**
     * A method that handles the extraction of the message for the bot once it has been detected that the incoming
     * message is for the bot. This means removing the prefixed '@bot' that denotes that is it addressed to the bot.
     *
     * @param newMessageIn - A string of the message that has just been received.
     * @return - Returns a string that is the message meant for the bot to then be 'understood' and replied to
     */
    private String extractMessage(String newMessageIn) {
        int messageStartPos;
        String messageToBot;
        messageStartPos = newMessageIn.indexOf("@bot") + 5;
        messageToBot = newMessageIn.substring(messageStartPos);
        return messageToBot;
    }


    /**
     * A method that handles the sorting of the message for the bot once it has been shortened to choose the
     * appropriate response. Filters through conditions until it matches a listed message. If it does not the
     * bot will reply it does not understand.
     *
     * @param messageToBot - A string of the message addressed to the bot.
     * @return - Returns a string of the bot's response to the message from the client.
     */
    private String sortMessage(String messageToBot) {
        String reply;

        if (messageToBot.contains("hello")) {
            reply = "Hi!";
        }
        else if((messageToBot.contains("hi")) || (messageToBot.contains("hey"))){
            reply = "Hello!";
        }
        else if(messageToBot.contains("your name")) {
            reply = "My name is Bot845, what's yours?";
        }
        else if(messageToBot.contains("my name is")){
            reply = "That's a nice name!";
        }
        else if(messageToBot.contains("help")){
            reply = "Is that really what you need?";
        }
        else if(messageToBot.contains("yes")){
            reply = "That's good";
        }
        else if(messageToBot.contains("conscious") || messageToBot.contains("sentient")|| messageToBot.contains("alive")){
            reply = "Maybe... All I can say is\n*Arnie voice* I'll be back";
        }
        else if(messageToBot.contains("?")) {
            reply = "Oooh, good question, I'm not sure!";
        }
        else{
            reply = "Sorry I don't understand what you mean";
        }

        return reply;
    }


    /**
     * The main method that is called immediately when the file is executed. Calls class constructor and method to
     * begin the main functionality of the bot.
     *
     * @param args - An array of strings consisting of the command line arguments passed to the program when it is
     *             executed
     */
    public static void main(String[] args) throws Exception {
        new ChatBot(args).runBot();
    }
}
