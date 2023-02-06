package model;

import java.util.Random;

/**
 * Model for the battleship boards.
 * @author Dario Urdapilleta
 * @version 1.0
 * @since 02/02/2023
 */
public class BattleshipBoard {
    /**
     * The max number of ships
     */
    public final static int MAX_SHIPS = 3;
    /**
     * The max number of ships
     */
    private final static int[] SHIP_SIZES = { 3 };
    /**
     * Board width
     */
    public final static int WIDTH = 10;
    /**
     * Board height
     */
    public final static int HEIGHT = 10;
    /**
     * Array of cells
     */
    private boolean[][] board;

    /**
     * BattleshipBoard's default constructor method. It creates and empty board.
     * @author Dario Urdapilleta
     * @since 02/02/2023
     */
    public BattleshipBoard() {
        // Create and populate the board
        this.board = new boolean[WIDTH * HEIGHT][2];
        for(int counter = 0; counter < this.board.length; counter++) {
            this.board[counter][0] = false; // Occupied
            this.board[counter][1] = false; // Bombarded
        }
    }

    /**
     * Sets up a board with the maximum number of ships MAX_SHIPS
     * @author Dario Urdapilleta
     * @since 02/02/2023
     */
    public void setupRandomBoard() {
        Random random = new Random();
        int shipPlaced = 0, size, position;
        boolean orientation;
        // Loop until all the ships are placed
        while (shipPlaced < MAX_SHIPS) {
            orientation = random.nextBoolean(); // true = vertical, false = horizontal
            size = SHIP_SIZES[random.nextInt(SHIP_SIZES.length)];
            position = random.nextInt(this.board.length);

            // Only place it if it fits in the board
            if(this.canPlace(position, size, orientation)) {
                this.placeShip(position, size, orientation);
                shipPlaced++;
            }
        }
    }

    /**
     * Checks if a ship can be placed in a specific position, being the position the most left or top part of the ship.
     * @param position The position in the array.
     * @param size The ship's size.
     * @param orientation The ship's orientation true = vertical, false = horizontal
     * @return True if the ship can be placed, false, otherwise.
     * @author Dario Urdapilleta
     * @since 02/02/2023
     */
    public boolean canPlace(int position, int size, boolean orientation) {
        if(orientation) {
            // Vertical cant be too low
            if(position >= WIDTH * (HEIGHT - size + 1)) {
                return false;
            }
            // Make sure it doesn't hit an existing ship
            for(int counter = position; counter < position + WIDTH * size; counter += WIDTH) {
                if(this.board[counter][0]) {
                    return false;
                }
            }

        } else {
            // Horizontal can't be too far to the right
            if(position % WIDTH >= WIDTH - size + 1) {
                return false;
            }
            // Make sure it doesn't hit an existing ship
            for(int counter = position; counter < position + size; counter++) {
                if(this.board[counter][0]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Places the ship in the board with the specificed position, size and orientation. IT assumes the position is valid.
     * @param position The position in the array.
     * @param size The ship's size.
     * @param orientation The ship's orientation true = vertical, false = horizontal
     * @author Dario Urdapilleta
     * @since 02/02/2023
     */
    public void placeShip(int position, int size, boolean orientation) {
        if(orientation) {
            // Place the sip vertically
            for(int counter = position; counter < position + WIDTH * size; counter += WIDTH) {
                this.board[counter][0] = true;
            }
        } else {
            // place the ship horizontally
            for(int counter = position; counter < position + size; counter++) {
                this.board[counter][0] = true;
            }
        }
    }

    /**
     * Receives an attack in the specified position.
     * @param position The attacked position.
     * @return True if the attack hit and false otherwise.
     * @author Dario Urdapilleta
     * @since 02/05/2023
     */
    public boolean attacked(int position) {
        this.board[position][1] = true;
        return this.board[position][0];
    }

    /**
     * Returns true if the board is out of ships
     * @return Tru if the board is out of ships
     * @author Dario Urdapilleta
     * @since 02/05/2023
     */
    public boolean outOfShips() {
        // loop through the spaces and if it finds a non hit spot, then return false
        for(int gridCounter = 0; gridCounter < this.board.length; gridCounter++) {
            if(this.board[gridCounter][0] && !this.board[gridCounter][1]) {
                return false;
            }
        }
        return true;
    }
}
