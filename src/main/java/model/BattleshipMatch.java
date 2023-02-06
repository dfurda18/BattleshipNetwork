package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import utilities.NetworkProtocols;

/**
 * This class represents a Battleship Network match from the server side.
 * It keeps track of all the actions and decisions to follow the match until the end.
 * It uses to client's IP and a random string to identify the client.
 * @author Dario Urdapilleta
 * @version 1.0
 * @since 01/30/2023
 */
public class BattleshipMatch {
    /**
     * The client's {@link java.net.Socket Socket}.
     */
    private Socket socket;
    /**
     * The Object output stream.
     */
    private ObjectOutputStream output;
    /**
     * The Object input stream
     */
    private ObjectInputStream input;
    /**
     * The player's board
     */
    private BattleshipBoard board;
    /**
     * Constructor method for the BattleshipMatch class. This is created when a connection with a client is started.
     * @param host The Host address.
     * @param port The port to create the Socket.
     * @author Dario Urdapilleta
     * @since 01/30/2023
     */
    public BattleshipMatch (String host, int port) {
        if(NetworkProtocols.isValidIPAddress(host)) {
            try {
                this.socket = new Socket(host, port);
                this.setup();
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    /**
     * Constructor method for the BattleshipMatch class. This is created when a connection with a client is started.
     * @param client The client's Socket.
     * @author Dario Urdapilleta
     * @since 01/30/2023
     */
    public BattleshipMatch (Socket client) {
        this.socket = client;
        this.setup();
    }
    /**
     * Sets up the Match.
     * @author Dario Urdapilleta
     * @since 01/30/2023
     */
    public void setup() {
        this.board = new BattleshipBoard();
    }
    /**
     * Gets the message from the input Stream
     * @return A string with the message.
     * @author Dario Urdapilleta
     * @since 01/30/2023
     */
    public String getMessage() {
        String message = "";
        try {
            this.input = new ObjectInputStream(this.socket.getInputStream());
            message = (String)this.input.readObject();
        } catch (IOException e) {
            this.close();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return message;
    }

    /**
     * Sends a message through the output stream.
     * @param message The message to send.
     * @author Dario Urdapilleta
     * @since 01/30/2023
     */
    public void sendMessage(String message) {
        try {
            this.output = new ObjectOutputStream(this.socket.getOutputStream());
            this.output.writeObject(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Closes connections.
     * @author Dario Urdapilleta
     * @since 01/30/2023
     */
    public void close() {
        try {
            if(this.input != null) {
                this.input.close();
            }
            if(this.output != null) {
                this.output.close();
            }
            if(this.socket != null) {
                this.socket.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Setas a random board for the match.
     * @author Dario Urdapilleta
     * @since 02/02/2023
     */
    public void setupRandomBoard() {
        this.board.setupRandomBoard();
    }

    /**
     * Returns the match's board.
     * @return The match's board.
     * @author Dario Urdapilleta
     * @since 02/02/2023
     */
    public BattleshipBoard getBoard() {
        return board;
    }
}
