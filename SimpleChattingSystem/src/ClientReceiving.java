//Importing the necessary libraries
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientReceiving extends Thread {
    //A variable holding the socket that the client connects to the server on
    private Socket serverSocket;
    //A variable allowing the client to read messages from the socket it connects to the server on
    private BufferedReader lineIn;
    //A variable holding the location of the first hyphen in the received message
    private int hyphenIndex;
    //A variable holding the location of the first colon in the received message
    private int colonIndex;
    //A variable holding the ID of the sender of the received message
    private int senderID;
    //A variable holding the body of the received message
    private String senderMessage;


    /**
     * The constructor for the ClientReceiving class.  This is extends the Thread class allowing it to run as a separate
     * process. Creates the necessary variables for reading incoming messages
     * the socket. 
     *
     * @param serverSocket - A variable holding the socket that the client connects to the server on
     */
    public ClientReceiving(Socket serverSocket) throws IOException {
        lineIn = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
        this.serverSocket = serverSocket;
    }


    /**
     * A method that continuously receives messages sent by the server. Calls a method that splits received messages
     * into their constituent elements. Calls a method that prints the message out to the client terminal according in
     * a consistent format.
     */
    public void run(){
        String newMessageIn = "";

        while(true){
            //attempts to read the next line from the socket
            try {
                newMessageIn = lineIn.readLine();
            }
            catch (Exception e) {
                System.out.println("An error has occurred.\nClosing server connection.");

                //attempts to close the socket and then finishes the process
                try {
                    serverSocket.close();
                }
                catch (Exception ignored) {
                }

                break;
            }

            splitMessage(newMessageIn);
            printMessage(newMessageIn);
        }
    }


    /**
     * A method that handles the splitting of the received message into the ID of the sender, the name of the sender
     * and the body of the message according to the format specified in the README.
     *
     * @param newMessageIn - A variable holding the whole message that has been received on the socket.
     */
    private void splitMessage(String newMessageIn) {
        //finds the location of the first hyphen
        hyphenIndex = newMessageIn.indexOf('-');
        //finds the location of the first colon
        colonIndex = newMessageIn.indexOf(':');

        //finds the ID of the sender ie. from the beginning to the hyphen not inclusive
        senderID = Integer.parseInt(newMessageIn.substring(0,hyphenIndex));
        //finds the body of the message ie. from the colon to the end of the message not inclusive
        senderMessage = newMessageIn.substring(colonIndex+1);
    }


    /**
     * A method that handles the final outputting of the message to the client. If the ID is 0 then, the name is used
     * to identify the sender of the message, otherwise the ID is used to identify the sender.
     *
     * @param newMessageIn - A variable holding the whole message that has been received on the socket.
     */
    private void printMessage(String newMessageIn) {

        if (senderID == 0 ){
            //If the ID is 0 then the name of the sender is found from in between the hyphen and the colon no inclusive
            String senderName = newMessageIn.substring(hyphenIndex + 1, colonIndex);

            System.out.println("<"+ senderName +"> "+senderMessage);
        }
        else{
            System.out.println("<Client "+ senderID +"> "+ senderMessage);
        }
    }


}
