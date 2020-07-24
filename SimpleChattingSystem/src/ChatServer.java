//Importing the necessary libraries
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ChatServer extends Thread {
    // declare necessary global variables:
    // to hold the sockets of all connected clients
    private ArrayList clientLog;
    // to determine whether or not the server user has entered the command to quit the server
    private static boolean userQuit;
    // to hold the socket the that the server is listening for clients on
    private ServerSocket serverSocket;


    /**
     * The constructor for the ChatServer class. This attempts to establish the server socket on the passed port number,
     * and initialises variables and data structures used by the server if the server socket is established.
     *
     * @param port The port number that the server will be listening on for connections from clients. By default this
     *             is 14001 however it may be changed through adding arguments to the programs execution.
     */
    private ChatServer(int port){
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("\nServer running.");

            userQuit = false;
            clientLog = new ArrayList<Socket>();
        }
        catch (IOException e){
            System.out.println("Server could not be created at this time.");
            System.exit(0);
        }
    }


    /**
     * A method overriding the run method of the super. This starts the server running, establishing that no clients
     * have been connected to begin with and then continuously listens on the server socket for clients attempting to
     * establish a connection. These are accepted and their socket added the array list of client sockets along with
     * incrementing the number of connected clients. The connected client is then split off on a separate thread so
     * that it can run simultaneously.
     *
     * If the server user enters the correct command the while loop condition will be met and the loop will be broken,
     * a method is then called to close the client connections and the server.
     */
    public void run(){
        int clientsConnected = 0;

        try{
            // While loop runs while the server user has not entered the exit command. Continuously accepts client
            // connections on the specified port. Connected clients have their sockets added to an array list and are
            // separated off onto another thread.
            while(!userQuit){
                System.out.println("Awaiting client connection...");
                Socket clientSocket = serverSocket.accept();
                clientsConnected ++;
                System.out.println("Connection establish on " + serverSocket.getLocalPort() + " with Client " + clientsConnected + " on " + clientSocket.getPort() + "\n");

                clientLog.add(clientSocket);

                ServerThread client = new ServerThread(clientSocket, clientsConnected, clientLog);
                client.start();
            }
        }
        catch (Exception ignored) {
        }
        finally {
            closeServer();
        }
    }


    /**
     * A method that handles the closing of the server. Loops through every object in the client log i.e. every
     * connected client, parses the object to a socket attempts to close the socket, outputting the success or failure.
     * Once all once all sockets have been attempted to closed the server shuts down.
     */
    private void closeServer() {
        for (Object client:clientLog){
            Socket clientSocket = (Socket) client;

            try {
                clientSocket.close();
                System.out.println("Connection to Client "+ (clientLog.indexOf(client)+1) + " closed.");
            }
            catch (Exception e) {
                System.out.println("Connection to client " + (clientLog.indexOf(client)+1) + " could not be closed.");
            }
        }

        System.out.println("Connection to all clients closed.\nThe server will now be terminated.\nGoodbye.");
        System.exit(0);
    }


    /**
     * A method that handles the input from the user into the server terminal. Scans the inputs until it matches the
     * command to close the server. When the correct command (case insensitive) is entered, the server interrupt
     * method is called, interrupting the server by closing the socket that the server is listening on.
     *
     * @param server - The ChatServer object that is running as a separate thread. This is passed so that it can be
     *               passed to the serverInterrupt method and the thread can be interrupted.
     */
    private static void serverTerminal(ChatServer server){
        Scanner scanner = new Scanner(System.in);
        String userIn = scanner.nextLine();

        while (!userIn.equalsIgnoreCase("exit")) {
            System.out.println("Command: '" + userIn + "' not recognised.\nEnter 'EXIT' if you wish to close the server.\n");
            userIn = scanner.nextLine();
        }

        // Changes the global boolean variable so that the condition for the running of the server while loop is no
        // longer true.
        userQuit = true;
        serverInterrupt(server);
    }


    /**
     * A method that handles the interrupting of the server thread by attempting to close the socket that the thread is
     * listening on for new clients. This allows the server to finish the current while loop, check the loop condition
     * again so that it can be closed.
     *
     * @param server - The ChatServer object that is running as a separate thread used to address the socket that the
     *               server is running on so that it can be closed.
     */
    private static void serverInterrupt(ChatServer server){
        System.out.println("Clients will be disconnected and server terminated.\n");

        try {
            server.serverSocket.close();
        } catch (IOException e) {
            System.out.println("Server could not be closed at this time.");
        }
    }


    /**
     * The main method that is run when the file is executed. Immediate calls the setPort method in the
     * CommandLineArgsHandler class which takes the arguments given to the program by the user when it's run from the
     * command line. A new ChatServer object is created using the port number and the start method called creating it
     * as a new thread. The serverTerminal method is called so the the terminal input is being read at the same time as
     * the server process is connecting with clients. The server object is passed to allow it to be interrupted.
     *
     * @param args - An array of strings of arguments passed to the program by the user when the program is run in
     *             either linux or windows command line.
     */
    public static void main(String[] args) {
        int port = CommandLineArgsHandler.setPort(args,"-csp", 14001);

        ChatServer server = new ChatServer(port);
        server.start();

        serverTerminal(server);
    }
}