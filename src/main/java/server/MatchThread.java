package server;

import model.BattleshipBoard;
import model.BattleshipMatch;
import utilities.NetworkProtocols;

import java.io.IOException;
import java.net.Socket;
import java.util.*;

/**
 * This class handles a match thread.
 * @author Dario Urdapilleta
 * @version 1.0
 * @since 02/02/2023
 */
public class MatchThread implements Runnable {
    /**
     * Orientation enumerator
     */
    private enum Directions {NORTH, SOUTH, EAST, WEST}
    /**
     * The Match tracking element.
     */
    private BattleshipMatch match;
    /**
     * The match's socket
     */
    private Socket socket;
    /**
     * Keeps the status of the match
     */
    private boolean running;
    /**
     * Thread number
     */
    private int threadNumber;
    /**
     * The list of attacks
     */
    private ArrayList<Integer> nextAttacks;
    /**
     * The latest position a ship was hit
     */
    private Stack<Integer> latestHits;
    /**
     * The last position the server attacked on
     */
    private int lastAttack;
    /**
     * The track of the used attacks
     */
    private boolean[][] attacks;
    /**
     * Constructor for the match thread
     * @param number The thread Number
     * @param socket The client socket
     * @author Dario Urdapilleta
     * @since 02/02/2023
     */
    public MatchThread(int number, Socket socket) {
        super();
        this.socket = socket;
        this.latestHits = new Stack<Integer>();

        // Create and populate the attack board
        this.attacks = new boolean[BattleshipBoard.WIDTH * BattleshipBoard.HEIGHT][2];
        for(int counter = 0; counter < this.attacks.length; counter++) {
            this.attacks[counter][0] = false;
            this.attacks[counter][1] = false;
        }
        this.threadNumber = number;
    }
    /**
     * Runs the thread.
     * @author Dario Urdapilleta
     * @since 02/02/2023
     */
    public void run()
    {
        this.running = true;
        this.match = new BattleshipMatch(this.socket);
        System.out.println("Match created");
        while(running) {
            this.handleMessage();
        }
        try {
            this.socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Handles a received message;
     * @author Dario Urdapilleta
     * @since 02/02/2023
     */
    public void handleMessage() {
        System.out.println("SERVER: Thread: " + this.threadNumber + ". Waiting for client's message.");
        String message = match.getMessage();
        String outputMessage;
        System.out.println("SERVER: Thread: " + this.threadNumber + ". Message: " + message + " received");
        // Start Game
        if(message.equals(NetworkProtocols.START_CLNT_MSG)) {
            this.match = new BattleshipMatch(this.socket);
            this.match.setupRandomBoard();
            this.createAttacks();
            outputMessage = NetworkProtocols.START_SRVR_MSG;
        // Attack from Client
        } else if(message.startsWith(NetworkProtocols.ATTACK)) {
            int position = Integer.parseInt(message.substring(19));
            if(this.match.getBoard().attacked(position)) {
                if(this.match.getBoard().outOfShips()) {
                    outputMessage = NetworkProtocols.YOU_WIN;
                } else {
                    outputMessage = NetworkProtocols.HIT;
                }
            } else {
                outputMessage = NetworkProtocols.MISS;
            }
        // Turn to Attack
        } else if(message.equals(NetworkProtocols.YOUR_TURN)) {
            this.lastAttack = this.nextAttack();
            this.attacks[this.lastAttack][0] = true;
            outputMessage = NetworkProtocols.ATTACK + this.lastAttack;
        // Attack Feedback
        } else if (message.equals(NetworkProtocols.HIT)) {
            this.latestHits.push(this.lastAttack);
            outputMessage = NetworkProtocols.IGNORE;
        // Miss Feedback
        } else if (message.equals(NetworkProtocols.MISS)) {
            outputMessage = NetworkProtocols.IGNORE;
        // Quit message
        } else if (message.equals(NetworkProtocols.QUIT)) {
            this.match.close();
            this.running = false;
            return;
        }else {
            outputMessage = NetworkProtocols.RETRY;
        }

        // Send a message
        System.out.println("SERVER: Thread: " + this.threadNumber + ". Sending message: " + outputMessage);
        match.sendMessage(outputMessage);
    }

    /**
     * Creates a random list of attacks
     * @author Dario Urdapilleta
     * @since 02/02/2023
     */
    private void createAttacks() {
        this.nextAttacks = new ArrayList<Integer>();
        for(int positionCounter = 0; positionCounter < BattleshipBoard.WIDTH * BattleshipBoard.HEIGHT; positionCounter++) {
            this.nextAttacks.add(positionCounter);
        }
        Collections.shuffle(this.nextAttacks);
    }

    /**
     * Returns the next place for the Servert o attack.
     * @return The int that represents the position of the next attack.
     * @author Dario Urdapilleta
     * @since 02/02/2023
     */
    private int nextAttack() {
        int tempPosition;
        boolean keepTrying;
        // First try to sink the ships if a ship was found
        while(!this.latestHits.empty()) {

            // Loop for all directions
            for(Directions direction : Directions.values()) {
                tempPosition = this.latestHits.peek();
                keepTrying = true;
                do {
                    // Try each direction until no options found
                    if(this.canMoveFrom(tempPosition, direction)) {
                        tempPosition = this.positionFrom(tempPosition, direction);

                        // If a possible candidate is found, try this one and remove from the random list
                        if(!this.attacks[tempPosition][0]) {
                            Integer nextLocation = tempPosition;
                            this.nextAttacks.remove(nextLocation);
                            return tempPosition;
                        }
                        if(!this.attacks[tempPosition][1]) {
                            keepTrying = false;
                        }
                    } else {
                        keepTrying = false;
                    }
                } while (keepTrying);
            }
            this.latestHits.pop();
        }
        // Try the next random number
        int next = this.nextAttacks.get(this.nextAttacks.size() - 1);
        this.nextAttacks.remove(this.nextAttacks.size() - 1);
        return next;
    }

    /**
     * Determines if a shot can be done from the position torward the direction.
     * @param position The current position
     * @param direction The direction
     * @return True if the position towards the direction from the specified position will be inside the array.
     * @author Dario Urdapilleta
     * @since 02/02/2023
     */
    private boolean canMoveFrom(int position, MatchThread.Directions direction) {
        switch(direction) {
            case NORTH -> {
                return position >= BattleshipBoard.WIDTH;
            }
            case SOUTH -> {
                return position < BattleshipBoard.WIDTH * (BattleshipBoard.HEIGHT - 1);
            }
            case EAST -> {
                return position % BattleshipBoard.WIDTH < BattleshipBoard.WIDTH - 1;
            }
            case WEST -> {
                return position % BattleshipBoard.WIDTH > 0;
            }
        }
        return false;
    }

    /**
     * Returns the next position torwards a direction.
     * @param position The current position.
     * @param direction The Direction.
     * @return The next position from the current position going to the specified direction.
     * @author Dario Urdapilleta
     * @since 02/02/2023
     */
    private int positionFrom(int position, MatchThread.Directions direction) {
        switch(direction) {
            case NORTH -> {
                return position - BattleshipBoard.WIDTH;
            }
            case SOUTH -> {
                return position + BattleshipBoard.WIDTH;
            }
            case EAST -> {
                return position + 1;
            }
            case WEST -> {
                return position - 1;
            }
        }
        return -1;
    }
}
