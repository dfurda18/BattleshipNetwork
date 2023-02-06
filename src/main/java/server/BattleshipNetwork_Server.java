package server;

import utilities.NetworkProtocols;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

/**
 * Main class of the Battlesip Network game for the Server.
 * @author Dario Urdapilleta
 * @version 1.0
 * @since 01/30/2023
 */
public class BattleshipNetwork_Server{
    /**
     * The Server socket used for the communication.
     */
    private ServerSocket server = null;
    /**
     * Max number of threads
     */
    private static final int MAX_THREADS = 5;
    /**
     * Main method that contains the game's main functionality.
     * @author Dario Urdapilleta
     * @since 01/30/2023
     */
    public static void startServer() {
        // Load the host and port
        try {
            NetworkProtocols.loadFromFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Runnable thread;
        Boolean keepGoing = true;
        BattleshipNetwork_Server application = new BattleshipNetwork_Server();
        Socket currentClient;
        int threadNumber = 1;
        ExecutorService executor = Executors.newFixedThreadPool(MAX_THREADS);
        System.out.println("SERVER: Listening on PORT: " + NetworkProtocols.PORT);
        try {
            while(keepGoing) {
                System.out.println("SERVER: waiting for the client connection ...");
                currentClient = application.server.accept();
                System.out.println("SERVER: Connected to client: "+ threadNumber);
                thread = new MatchThread(threadNumber, currentClient);
                executor.execute(thread);
                threadNumber++;
            }
            // Close the server Connection
            application.server.close();
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
        }

    }
    /**
     * Contructor method for the BattleshipNetwork server class.
     * @author Dario Urdapilleta
     * @since 01/30/2023
     */
    public BattleshipNetwork_Server() {
        try {
            this.server = new ServerSocket(NetworkProtocols.PORT);
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
        }
    }
}
