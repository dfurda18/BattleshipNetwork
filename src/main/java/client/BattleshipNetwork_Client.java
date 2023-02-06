package client;

import model.BattleshipBoard;
import model.BattleshipMatch;
import utilities.NetworkProtocols;

import java.awt.event.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Main class of the Battlesip Network game for the Client.
 * @author Dario Urdapilleta
 * @version 1.0
 * @since 01/30/2023
 */
public class BattleshipNetwork_Client {
    /**
     * The match played with the server.
     */
    private BattleshipMatch match;
    /**
     * The selected ship
     */
    private int selected = 0;
    /**
     * Variable that keeps track if the application is in grid setup mode.
     */
    private boolean selecting = false;
    /**
     * Variable that keeps track if the application is in attacking mode
     */
    private boolean attacking = false;
    /**
     * The selected ship size.
     */
    private int selectedShipSizze = 0;
    /**
     * The number of player's ships
     */
    private int playersShips = 0;
    /**
     * Keeps track if there's a game being played
     */
    private boolean playing = false;
    /**
     * The remaining player ship parts
     */
    private int playerShips;
    /**
     * The remaining opponent's ship parts
     */
    private int opponentShips;
    /**
     * The game interface
     */
    public BattleNetworkInterface gameInterface;
    /**
     * Class constructor
     * @author Dario Urdapilleta
     * @since 01/30/2023
     */
    public BattleshipNetwork_Client() {
        // Load the host and port
        try {
            NetworkProtocols.loadFromFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Create the interface
        this.gameInterface = new BattleNetworkInterface();
        this.gameInterface.createInterface();
    }
    /**
     * Creates the game interface and prepares it to start a new Battleship match.
     * @author Dario Urdapilleta
     * @since 01/30/2023
     */
    public void startGame() {
        BattleshipNetwork_Client currentObject = this;

        // Set the closing method to close the socket connection
        this.gameInterface.setWindowAdapter(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if(currentObject.match != null) {
                    currentObject.match.sendMessage(NetworkProtocols.QUIT);
                    currentObject.match.close();
                }

            }
        });

        // Set the horizontal ship select button functionality
        this.gameInterface.horizontalShip.setClickAction(() -> {
            if(this.playing) {
                if(this.gameInterface.horizontalShip.toggleSelected()) {
                    this.selected = 2;
                    this.selectedShipSizze = 3;
                    this.gameInterface.verticalShip.setUnselected();
                } else {
                    this.selected = 0;
                }
            }
        });

        // Set the vertical ship select button functionality
        this.gameInterface.verticalShip.setClickAction(() -> {
            if(this.playing) {
                if (this.gameInterface.verticalShip.toggleSelected()) {
                    this.selected = 1;
                    this.selectedShipSizze = 3;
                    this.gameInterface.horizontalShip.setUnselected();
                } else {
                    this.selected = 0;
                }
            }
        });

        // Set the left board click functionality
        this.gameInterface.leftBoardBackground.setGridActions(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int position = ((GridCell)e.getSource()).position;

                // only do anything in the select page and as long as one type of ship is selected
                if(currentObject.playing && currentObject.selecting && currentObject.selected > 0) {
                    // Vertical
                    if(currentObject.selected == 1) {
                        if(currentObject.match.getBoard().canPlace(position, currentObject.selectedShipSizze, true)) {
                            currentObject.match.getBoard().placeShip(position, currentObject.selectedShipSizze, true);
                            currentObject.gameInterface.leftBoardBackground.insertShip(position, true, 46, 110);
                            currentObject.playersShips++;
                        }
                    // Horizontal
                    } else if (currentObject.selected == 2) {
                        if(currentObject.match.getBoard().canPlace(position, currentObject.selectedShipSizze, false)) {
                            currentObject.match.getBoard().placeShip(position, currentObject.selectedShipSizze, false);
                            currentObject.gameInterface.leftBoardBackground.insertShip(position, false, 110, 46);
                            currentObject.playersShips++;
                        }
                    }

                    // Move on when three ships are selected
                    if(currentObject.playersShips >= BattleshipBoard.MAX_SHIPS) {
                        currentObject.selecting = false;
                        currentObject.attacking = true;
                        currentObject.gameInterface.showAttackScreen("Select a quadrant to attack.", null);
                    }
                }

            }
        });
        // Set the right board click actions
        this.gameInterface.rightBoardBackground.setGridActions(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int position = -1;
                String response, message = "";
                // Only if attacking and its not previously attacked
                if(currentObject.playing && currentObject.attacking && !((GridCell)e.getSource()).attacked) {
                    // Set attacked to tru and attacking to false
                    ((GridCell)e.getSource()).attacked = true;
                    currentObject.attacking = false;

                    // Get the position and send a message with it
                    position = ((GridCell)e.getSource()).position;
                    currentObject.match.sendMessage(NetworkProtocols.ATTACK + position);

                    // Handle the response
                    response = currentObject.match.getMessage();
                    if(response.equals(NetworkProtocols.YOU_WIN)) {
                        // Handle if won the game
                        currentObject.gameInterface.rightBoardBackground.hit(position);
                        currentObject.opponentShips--;
                        currentObject.gameInterface.updatePlayerShips(currentObject.opponentShips);
                        currentObject.gameInterface.showStartModal("You Win!");

                        // Send a quit message to the server and close the connection
                        currentObject.match.sendMessage(NetworkProtocols.QUIT);
                        currentObject.playing = false;
                        currentObject.match.close();
                    } else if(response.equals(NetworkProtocols.HIT)) {
                        // Handle a hit
                        currentObject.gameInterface.rightBoardBackground.hit(position);
                        message = "You landed a hit on (" + ((GridCell)e.getSource()).getXCoordinate() + "," + ((GridCell)e.getSource()).getYCoordinate() + ")";
                        currentObject.opponentShips--;
                        currentObject.gameInterface.updatePlayerShips(currentObject.opponentShips);
                    } else {
                        // Handle a miss
                        currentObject.gameInterface.rightBoardBackground.miss(position);
                        message = "You missed the hit on (" + ((GridCell)e.getSource()).getXCoordinate() + "," + ((GridCell)e.getSource()).getYCoordinate() + ")";
                    }
                    // Show the attack screen
                    currentObject.gameInterface.showAttackScreen(message, currentObject.gameInterface.yourTurnButton);
                }
            }
        });

        // Set the start button logic
        this.gameInterface.startButton.setClickAction(() -> {
            String message = "";
            String host;
            // Connect to the Server
            try {
                host = NetworkProtocols.HOST.equals("localhost") ? InetAddress.getLocalHost().getHostAddress() : NetworkProtocols.HOST;
                this.match = new BattleshipMatch(host, NetworkProtocols.PORT);
                this.playing = true;

                // Send the start message
                this.match.sendMessage(NetworkProtocols.START_CLNT_MSG);

                //Wait for the response
                message = this.match.getMessage();
                if(message.equals(NetworkProtocols.START_SRVR_MSG)) {
                    // Get things ready and show the setup board screen
                    playersShips = 0;
                    this.playerShips = 9;
                    this.opponentShips = 9;
                    this.gameInterface.updatePlayerShips(this.playerShips);
                    this.gameInterface.updateOpponentShips(this.playerShips);
                    this.selecting = true;
                    this.selected = 0;
                    this.setupBoard();
                } else {
                    // Show error
                    this.showError(message);
                }
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            }

        });

        // Implement the Your Turn button
        this.gameInterface.yourTurnButton.setClickAction(() -> {
            if(this.playing) {
                String message = "";
                int x, y;
                // Send the message to the server
                this.match.sendMessage(NetworkProtocols.YOUR_TURN);

                // get the response
                String response = this.match.getMessage();
                if(response.startsWith(NetworkProtocols.ATTACK)) {
                    // If the server attacked get the position
                    int position = Integer.parseInt(response.substring(19));
                    x = (int) Math.floor(position / BoardContainer.WIDTH);
                    y = position % BoardContainer.WIDTH;
                    // Check if the hit was successful
                    if (this.match.getBoard().attacked(position)) {
                        this.gameInterface.leftBoardBackground.hit(position);
                        message = "Your opponent landed an attack on (" + x + "," + y + ")";
                        this.playerShips--;
                        this.gameInterface.updateOpponentShips(currentObject.playerShips);
                        this.match.sendMessage(NetworkProtocols.HIT);
                    } else {
                        this.gameInterface.leftBoardBackground.miss(position);
                        message = "Your opponent attack on (" + x + "," + y + ") but it missed";
                        this.match.sendMessage(NetworkProtocols.MISS);
                    }
                    response = this.match.getMessage();
                    // ignore the next response and show the screen where it says the attack result.
                    this.gameInterface.showAttackedScreen(message, this.gameInterface.continueButton);
                }
            }
        });
        // Impleemnt the continue button
        this.gameInterface.continueButton.setClickAction(() -> {
            if(this.playing) {
                // Check is the player lost
                if (this.match.getBoard().outOfShips()) {
                    this.gameInterface.showStartModal("You Lose!");
                    this.match.sendMessage(NetworkProtocols.QUIT);
                    this.playing = false;
                    this.match.close();
                } else {
                    // Show the attack screen
                    this.attacking = true;
                    this.gameInterface.showAttackScreen("Select a quadrant to attack.", null);
                }
            }
        });
        this.gameInterface.showStartModal("Welcome!");
    }

    /**
     * Generates the process to set up the player's board.
     * @author Dario Urdapilleta
     * @since 02/02/2023
     */
    private void setupBoard() {
        this.gameInterface.modal.hideModal();
        this.gameInterface.showShipEdit();
    }
    /**
     * Handles any unhandled message and restarts the game.
     * @author Dario Urdapilleta
     * @since 02/02/2023
     */
    private void showError(String message) {
        this.gameInterface.showStartModal(message);
    }

}
