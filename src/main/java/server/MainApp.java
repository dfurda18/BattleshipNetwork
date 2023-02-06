package server;

/**
 * Battleship network server Main app
 * @author Dario Urdapilleta
 * @version 1.0
 * @since 01/30/2023
 */
public class MainApp {
    /**
     * Main method that contains the game's main functionality.
     * @param args The console arguments (not used).
     * @author Dario Urdapilleta
     * @version 1.0
     * @since 01/30/2023
     */
    public static void main(String[] args) {
        BattleshipNetwork_Server.startServer();
    }
}
