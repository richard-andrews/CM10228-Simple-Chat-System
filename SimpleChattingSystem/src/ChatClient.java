//Importing the necessary libraries
import java.io.IOException;
import java.net.Socket;

public class ChatClient extends CommandLineArgsHandler {
    //A variable holding the socket that the client connects to the server on
    private Socket serverSocket;


    /**
     * The constructor for the ChatClient class. Calls methods to attempt to extract the IP address and port number
     * from the entered arguments. Attempts to set up the connection to the server.
     *
     * @param args - An array of strings of the arguments given by the user at the execution of the file.
     */
    private ChatClient(String[] args) {
        int port = setPort(args,"-ccp", 14001);
        String address = setAddress(args, "localhost");

        try {
            serverSocket = new Socket(address, port);
            System.out.println("\nConnecting to the server...\nConnection established.");
        }
        catch (Exception e) {
            System.out.println("\nServer connection could not be established.");
        }
    }


    /**
     * A method that handles the initial running of the server before creating two processes that are split off and used
     * to handle sending and receiving messages.
     */
    private void runClient() throws IOException {
        ClientSending messageOut = new ClientSending(serverSocket);
        ClientReceiving messageIn = new ClientReceiving(serverSocket);

        messageIn.start();
        messageOut.start();
    }


    /**
     * The main method that is called immediately when the file is executed. Calls class constructor and method to
     * begin the main functionality of the client.
     *
     * @param args - An array of strings consisting of the command line arguments passed to the program when it is
     *             executed
     */
    public static void main(String[] args) throws Exception {
            new ChatClient(args).runClient();
    }
}
