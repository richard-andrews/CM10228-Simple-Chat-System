//Importing the necessary libraries
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ServerThread extends Thread {
    //a variable holding the number assigned to the client handled on this thread, this is set to 0 if the name has
    //been assigned
    private int clientID;
    //a variable holding the name assigned to the client by the client that is handled on this thread
    private String clientName;
    //a variable holding the socket of the client that is handled on this thread
    private Socket clientSocket;
    //a variable holding the list of all sockets connected to the server
    private ArrayList clientLog;
    //a variable enabling the receipt of messages from the socket
    private BufferedReader lineIn;
    //a variable enabling the broadcasting of messages to call clients connected to the server
    private PrintWriter lineOut;
    //a variable holding the result of whether or not the last message sent by the client that is handled by this
    // thread was empty therefore indicating whether the client is still connected and whether the thread should still
    //listen to this socket
    private boolean streamLoud;


    /**
     * Constructor for ServerThread class which handles an individual client that has connected to the server. The
     * constructor is called as soon as the client has connected and been accepted by the server. This sets up the
     * necessary objects for two way communication between the client and the server. These are then assigned to
     * global variables.
     *
     * @param clientSocket  - A socket that maintains the connection between the client operating on this ServerThread
     *                     and the server
     * @param clientID      - An integer that maintains the ID of this client.
     * @param clientLog     - An ArrayList of the sockets connected to the server. This allows messages to be broadcast to
     *                  all clients connected at the time.
     */
    public ServerThread(Socket clientSocket, int clientID, ArrayList clientLog) throws IOException {
        lineIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        lineOut = new PrintWriter(clientSocket.getOutputStream(), true);
        this.clientID = clientID;
        this.clientLog = clientLog;
        this.clientSocket = clientSocket;
        clientName = "";
        streamLoud = true;
    }


    /**
     * A method that overrides the run method for the super. This is run when the thread is first created. Broadcasts
     * a message to all connected clients in the log from the server that a new client has connected.  Broadcasts a
     * message informing how a user can change their name on the server. Calls method that receives messages from the
     * client connected to the thread's socket.
     */
    public void run() {
        sendMessage(0, "Server", "Client " + clientID + " has joined.");
        sendMessage(0, "Server", "To change your username use 'Name:' followed directly by the name you wish to appear for all clients.");
        receiveMessage();
    }


    /**
     * A method that handles the receiving of messages from the client connected to the thread's socket. Attempts to
     * read the next line inputted from the stream reader. Tests whether or not the client is still sending messages
     * (i.e. the messages are not empty or null). If the message is not empty then determines whether it is a name
     * assignment or the bot and calls methods to handle these cases appropriately. All messages are then passed to the
     * method which broadcasts them to all clients connected to the server.
     */
    private void receiveMessage() {
        String newMessageIn;

        //while the last message received was not empty
        while (streamLoud) {
            //clears the contents of the message variable
            newMessageIn = "";

            //attempts to read the next line sent by the client
            try {
                newMessageIn = lineIn.readLine();
            }
            catch (Exception e) {
                System.out.println("Disconnect.");
                clientDisconnected();
            }


            //tests whether the message is empty, if not the message is tested to see if it matches conditions
            // requiring special method calls and message is broadcast. If it is blank, the client is assumed to have disconnected and the
            // appropriate method is called.
            if (newMessageIn.equalsIgnoreCase("Bot has joined.")) {
                botJoined();
            }
            else if (newMessageIn.contains("Name:")) {
                nameAssigned(newMessageIn);
            }

            sendMessage(clientID, clientName, newMessageIn);
        }
    }


    /**
     * A method that handles if the client is assumed to have disconnected. This breaks the while loop that listens on
     * the socket by making the condition false, broadcasts to all clients that the client has disconnected and removes
     * the socket from the client log preventing further messages from being sent to the client.
     */
    private void clientDisconnected() {
        streamLoud = false;
        System.out.println("Client " + clientID + " " + clientName + " disconnected.\nClient handler will be closed.");
        sendMessage(0, "Server", "Client " + clientID + " " +  clientName + " disconnected.");
        clientLog.remove(clientSocket);
    }


    /**
     * A method that handles the client requesting their name to be changed or assigned. *IT MAY BE USEFUL TO READ THE
     * MESSAGE FORMAT COMMENT IN THE README* The name is found directly after the colon of the name and assigned to the
     * appropriate variable. A message is broadcast, stating that the clients name has been assigned and the client's
     * ID is set to 0 to show that the name has been assigned.
     *
     * @param newMessageIn - A string that contains the message that has been received by the server from the client.
     */
    private void nameAssigned(String newMessageIn) {
        // Assumes that the clients name starts immediately after the colon and continues until the end of the message
        int nameStartPos = newMessageIn.indexOf(":") + 1;
        clientName = newMessageIn.substring(nameStartPos);

        sendMessage(0, "Server", "Client " + clientID + " is now " + clientName);
        clientID = 0;
    }


    /**
     * A method that handles the bot joining the server. Broadcasts to the connected clients that the bot has joined
     * and informs as to how to talk to the bot.
     */
    private void botJoined() {
        sendMessage(0, "Server", "Chat bot is connected.\nTo address the bot, prepend your message with @bot.");
    }


    /**
     * A method that handles the broadcasting of messages to all the clients connected to the server. Cycles through
     * the sockets listed, establishes a connection with them and sends the messages in the correct format.
     *
     * @param clientID - A variable containing the ID of the client the message is from
     * @param name - A variable containing the name of the client the message is from
     * @param message - The message that is to be broadcast
     */
    private void sendMessage(int clientID, String name, String message) {
        Socket tempSocket;

        //Cycles through each object in the client log
        for (Object o : clientLog) {
            //Parses the object to a socket
            tempSocket = (Socket) o;

            try {
                //Establishes the connection to the socket
                lineOut = new PrintWriter(tempSocket.getOutputStream(), true);
                //Sends the message
                lineOut.println(clientID + "-" + name + ":" + message);
            }
            catch (Exception e) {
                System.out.println("Message '" + message + "' could not be sent to Client " + clientID + " " + name + ".");
            }
        }

    }
}