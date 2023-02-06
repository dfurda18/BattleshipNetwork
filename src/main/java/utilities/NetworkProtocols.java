package utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Dictionary class to store all the communications.
 * @author Dario Urdapilleta
 * @version 1.0
 * @since 02/01/2023
 */
public class NetworkProtocols {
    /**
     * Static PORT to use during the communication.
     */
    public static int PORT = 9876;
    /**
     * Static host to connect to the server
     */
    public static String HOST = "localhost";
    /**
     * Server Start game Message.
     */
    public final static String START_SRVR_MSG = "BATTLESHIP: GUESS";
    /**
     * Server Retry Start game Message.
     */
    public final static String RETRY = "SERVER_RETRY";
    /**
     * Client Start game Message.
     */
    public final static String START_CLNT_MSG = "BATTLESHIP: CONNECTED";
    /**
     * Client Attack message
     */
    public final static String ATTACK = "BATTLESHIP: ATTACK:";
    /**
     * Hit Response
     */
    public final static String HIT = "BATTLESHIP: HIT";
    /**
     * Miss Response
     */
    public final static String MISS = "BATTLESHIP: MISS";
    /**
     * Win Responso
     */
    public final static String YOU_WIN = "BATTLESHIP: HIT – GAME OVER";
    /**
     * Your turn response
     */
    public final static String YOUR_TURN = "BATTLESHIP: YOUR TURN";
    /**
     * Ignore message.
     */
    public final static String IGNORE = "BATTLESHIP: IGNORE";
    /**
     * Quit message
     */
    public static final String QUIT = "BATTLESHIP: QUIT";
    /**
     * Checks if a string is an IP Address V4
     * @param address The string to check if it is an IP address
     * @return True if the string is an IP address and False otherwise
     * @author Dario Urdapilleta
     * @since 02/02/2023
     */
    public static boolean isValidIPAddress(String address) {
        String regex = "^(([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])(\\.(?!$)|$)){4}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(address);
        return matcher.matches();
    }

    /**
     * Loads the host and port from a file.
     * @author Dario Urdapilleta
     * @since 02/05/2023
     */
    public static void loadFromFile() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(NetworkProtocols.class.getClassLoader().getResourceAsStream("config.dat")));
        if(reader.ready()) {
            NetworkProtocols.HOST = reader.readLine();
        }
        if(reader.ready()) {
            NetworkProtocols.PORT = Integer.parseInt(reader.readLine());
        }
    }
}
