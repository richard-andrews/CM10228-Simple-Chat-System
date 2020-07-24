//Importing the necessary libraries
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientSending extends Thread {
    //A variable holding the socket that the client connects to the server on
    private Socket serverSocket;


    /**
     * The constructor for the ClientSending class. This is extends the Thread class allowing it to run as a separate
     * process.
     *
     * @param serverSocket - A variable holding the socket that the client connects to the server on
     */
    public ClientSending(Socket serverSocket) {
        this.serverSocket = serverSocket;
    }


    /**
     * A method that continuously reads the input from the user terminal for the client and sends the entered message
     * to the server.
     */
    public void run() {
        String newMessageOut;

        //Attempts to read the user input and send it to the server
        try {
            BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter lineOut = new PrintWriter(serverSocket.getOutputStream(), true);

            while (true) {
                newMessageOut = userIn.readLine();
                lineOut.println(newMessageOut);
            }
        }
        catch (Exception e) {
            //Attempts to close the connection to the server.
            try{
                serverSocket.close();
            }
            catch (Exception ignored) {
            }
        }
    }
}