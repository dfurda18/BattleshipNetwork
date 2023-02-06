package client;

import utilities.gui.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;

/**
 * Static class that handles the Battle Network Interface.
 * @author Dario Urdapilleta
 * @version 1.0
 * @since 01/30/2023
 */
public class BattleNetworkInterface {
    /**
     * Static attribute that stores the screen size.
     */
    private static final Dimension screenSize = new Dimension(1080, 720);
    /**
     * Static attribute that stores the modal size.
     */
    private static final Dimension modalSize = new Dimension(535, 411);
    /**
     * Static attribute that stores the modal's button size.
     */
    private static final Dimension modalButtonSize = new Dimension(263, 87);
    /**
     * Static attribute that stores the select button's button size.
     */
    private static final Dimension selectButtonSize = new Dimension(148, 148);
    /**
     * Size of the button space
     */
    private static final int BUTTON_MARGIN = 130;
    /**
     * The game's font.
     */
    private static final Font ceviche = new Font("Ceviche One", Font.PLAIN, 80);
    /**
     * The game's font smaller.
     */
    private static final Font cevicheSmaller = new Font("Ceviche One", Font.PLAIN, 50);
    /**
     * The game's font.
     */
    private static final Font cevicheSmall = new Font("Ceviche One", Font.PLAIN, 40);
    /**
     * The game's font color.
     */
    private static final Color fontColor = new Color(84, 249, 255);
    /**
     * Static attribute that stores the GUI main window.
     */
    private JFrame frame;
    /**
     * Static attribute that stores the main game interface.
     */
    private JLayeredPane gameInterface;
    /**
     * Modal element to display messages.
     */
    public Modal modal;
    /**
     * The Modal's Background.
     */
    private Background modalBackground;
    /**
     * The start modal Body
     */
    private JPanel startBody;
    /**
     * The select ship content
     */
    private JPanel selectShip;
    /**
     * The horizontal ship select.
     */
    public ImageSelectButton horizontalShip;
    /**
     * The vertical ship select.
     */
    public ImageSelectButton verticalShip;
    /**
     * The start modal button.
     */
    public ImageTextButton startButton;
    /**
     * The Continue to the opponet's turn button
     */
    public ImageTextButton yourTurnButton;
    /**
     * The Continue button
     */
    public ImageTextButton continueButton;
    /**
     * The left Board background
     */
    public BoardContainer leftBoardBackground;
    /**
     * The right Board background
     */
    public BoardContainer rightBoardBackground;
    /**
     * The Opponent's ship counter
     */
    public ShipCounter opponentShips;
    /**
     * The Player's ship counter
     */
    public ShipCounter playerShips;
    /**
     * Class constructor
     * @author Dario Urdapilleta
     * @since 01/30/2023
     */
    public BattleNetworkInterface() {

    }
    /**
     * Creates the interface elements.
     * @author Dario Urdapilleta
     * @since 01/30/2023
     */
    public void createInterface() {
        // Set up the window frame
        this.frame = new JFrame("Battle Network");
        this.frame.setSize(BattleNetworkInterface.screenSize.width, BattleNetworkInterface.screenSize.height);
        this.frame.setResizable(false);
        this.frame.setLocationRelativeTo(null);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE );

        // Set up the Layered panel
        this.gameInterface = new JLayeredPane();
        this.gameInterface.setBounds(0,-5, BattleNetworkInterface.screenSize.width, BattleNetworkInterface.screenSize.height);
        this.gameInterface.setPreferredSize(BattleNetworkInterface.screenSize);
        this.frame.add(this.gameInterface);

        // Set up the background image
        Background background = new Background(BattleNetworkInterface.screenSize,"images/Background.png");
        this.gameInterface.add(background, JLayeredPane.FRAME_CONTENT_LAYER,3);

        // Place the game title
        JPanel title = new JPanel();
        title.setOpaque(false);
        title.setBounds((BattleNetworkInterface.screenSize.width - 781) / 2, 20, 781, 74);
        title.add(new JLabel(new ImageIcon(BattleNetworkInterface.class.getClassLoader().getResource("images/Title.png"))));
        this.gameInterface.add(title, JLayeredPane.DEFAULT_LAYER);

        // Create the action modal
        this.createModal();

        // Create board backgrounds
        this.leftBoardBackground = new BoardContainer();
        this.leftBoardBackground.setVisible(false);
        this.leftBoardBackground.setLayout(null);
        this.leftBoardBackground.setBounds(50,250,BoardContainer.size.width, BoardContainer.size.height);
        this.gameInterface.add(this.leftBoardBackground, JLayeredPane.DEFAULT_LAYER);
        this.rightBoardBackground = new BoardContainer();
        this.rightBoardBackground.setVisible(false);
        this.rightBoardBackground.setLayout(null);
        this.rightBoardBackground.setBounds(599,250,BoardContainer.size.width, BoardContainer.size.height);
        this.gameInterface.add(this.rightBoardBackground, JLayeredPane.DEFAULT_LAYER);

        // Create the Select ship page
        this.selectShip = new JPanel();
        this.selectShip.setLayout(null);
        this.selectShip.setBounds(0,0,BoardContainer.size.width, BoardContainer.size.height);
        this.selectShip.setOpaque(false);

        // Select ship text
        JLabel selectMessage = new JLabel();
        selectMessage.setText("<html><center>Select a ship and click on a location.</center></html>");
        selectMessage.setFont(ceviche);
        selectMessage.setForeground(fontColor);
        selectMessage.setBounds(0,0,BoardContainer.size.width, BoardContainer.size.height - 170);
        this.selectShip.add(selectMessage);

        // Select ship buttons
        int[] boundsX = {0,BattleNetworkInterface.selectButtonSize.width-1,BattleNetworkInterface.selectButtonSize.width-1,0};
        int[] boundsY = {0,0,BattleNetworkInterface.selectButtonSize.height-1,BattleNetworkInterface.selectButtonSize.height-1};

        // Vertical button
        this.verticalShip = new ImageSelectButton(
                new ButtonOptions(
                        new Dimension(BattleNetworkInterface.selectButtonSize.width,
                                BattleNetworkInterface.selectButtonSize.height
                        ),
                        BattleNetworkInterface.selectButtonSize,
                        new Polygon(boundsX, boundsY, 4),
                        cevicheSmall,
                        fontColor
                ),
                BattleNetworkInterface.class.getClassLoader().getResource("images/ShipV.png"),
                BattleNetworkInterface.class.getClassLoader().getResource("images/Selection.png")
        );
        this.verticalShip.setBounds(
                50,
                265, BattleNetworkInterface.selectButtonSize.width,
                BattleNetworkInterface.selectButtonSize.height);
        this.verticalShip.setContentBounds(50, 20, 46, 110);
        this.selectShip.add(this.verticalShip);

        // Horizontal button
        this.horizontalShip = new ImageSelectButton(
                new ButtonOptions(
                        new Dimension(BattleNetworkInterface.selectButtonSize.width,
                                BattleNetworkInterface.selectButtonSize.height
                        ),
                        BattleNetworkInterface.selectButtonSize,
                        new Polygon(boundsX, boundsY, 4),
                        cevicheSmall,
                        fontColor
                ),
                BattleNetworkInterface.class.getClassLoader().getResource("images/ShipH.png"),
                BattleNetworkInterface.class.getClassLoader().getResource("images/Selection.png")
        );
        this.horizontalShip.setBounds(
                248,
                265, BattleNetworkInterface.selectButtonSize.width,
                BattleNetworkInterface.selectButtonSize.height);
        this.horizontalShip.setContentBounds(20, 50, 110, 46);
        this.selectShip.add(this.horizontalShip);

        // Your Turn Button
        int[] boundsX2 = {0,BattleNetworkInterface.modalButtonSize.width-1,BattleNetworkInterface.modalButtonSize.width-1,0};
        int[] boundsY2 = {0,0,BattleNetworkInterface.modalButtonSize.height-1,BattleNetworkInterface.modalButtonSize.height-1};
        this.yourTurnButton = new ImageTextButton(
                new ButtonOptions(
                        new Dimension((BoardContainer.size.width - BattleNetworkInterface.modalButtonSize.width)/2 + 10,
                                BattleNetworkInterface.modalButtonSize.height
                        ),
                        BattleNetworkInterface.modalButtonSize,
                        new Polygon(boundsX2, boundsY2, 4),
                        cevicheSmall,
                        fontColor
                ),
                "Next Turn",
                BattleNetworkInterface.class.getClassLoader().getResource("images/Button.png")
        );
        this.yourTurnButton.setBounds((BoardContainer.size.width - BattleNetworkInterface.modalButtonSize.width)/2 + 10,BoardContainer.size.height - BattleNetworkInterface.BUTTON_MARGIN, BattleNetworkInterface.modalButtonSize.width, BattleNetworkInterface.modalButtonSize.height);

        // Continue Button
        this.continueButton = new ImageTextButton(
                new ButtonOptions(
                        new Dimension((BoardContainer.size.width - BattleNetworkInterface.modalButtonSize.width)/2 + 10,
                                BattleNetworkInterface.modalButtonSize.height
                        ),
                        BattleNetworkInterface.modalButtonSize,
                        new Polygon(boundsX2, boundsY2, 4),
                        cevicheSmall,
                        fontColor
                ),
                "Continue",
                BattleNetworkInterface.class.getClassLoader().getResource("images/Button.png")
        );
        this.continueButton.setBounds((BoardContainer.size.width - BattleNetworkInterface.modalButtonSize.width)/2 + 10,BoardContainer.size.height - BattleNetworkInterface.BUTTON_MARGIN, BattleNetworkInterface.modalButtonSize.width, BattleNetworkInterface.modalButtonSize.height);

        // Create the Opponent's ships counter
        this.opponentShips = new ShipCounter(9, BattleNetworkInterface.class.getClassLoader().getResource("images/Ship.png"), ceviche, fontColor);
        this.opponentShips.setBounds(160,150,290, 100);
        this.opponentShips.setVisible(false);
        this.gameInterface.add(this.opponentShips, JLayeredPane.DEFAULT_LAYER);

        // Create the Player's ship counter
        this.playerShips = new ShipCounter(9, BattleNetworkInterface.class.getClassLoader().getResource("images/Ship.png"), ceviche, fontColor);
        this.playerShips.setBounds(740,150,290, 100);
        this.playerShips.setVisible(false);
        this.gameInterface.add(this.playerShips, JLayeredPane.DEFAULT_LAYER);

        // Show the frame
        this.frame.pack();
        this.frame.setVisible(true);
    }
    /**
     * Creates the modal.
     * @author Dario Urdapilleta
     * @since 01/30/2023
     */
    private void createModal() {
        this.modal = new Modal(
                BattleNetworkInterface.modalSize,
                new Dimension(
                        (BattleNetworkInterface.screenSize.width - BattleNetworkInterface.modalSize.width) / 2,
                        200
                )
        );
        this.modalBackground = new Background(BattleNetworkInterface.modalSize, "images/Modal.png");
        // Modal Body
        this.startBody = new JPanel();
        this.startBody.setOpaque(false);
        this.startBody.setBounds(30,80,BattleNetworkInterface.modalSize.width-40, 150);

        JLabel startMessage = new JLabel("Welcome!");
        startMessage.setFont(ceviche);
        startMessage.setForeground(fontColor);
        this.startBody.add(startMessage);

        // Start Button
        int[] boundsX = {0,BattleNetworkInterface.modalButtonSize.width-1,BattleNetworkInterface.modalButtonSize.width-1,0};
        int[] boundsY = {0,0,BattleNetworkInterface.modalButtonSize.height-1,BattleNetworkInterface.modalButtonSize.height-1};
        this.startButton = new ImageTextButton(
                new ButtonOptions(
                        new Dimension((BattleNetworkInterface.modalSize.width - BattleNetworkInterface.modalButtonSize.width)/2 + 10,
                                BattleNetworkInterface.modalButtonSize.height
                        ),
                        BattleNetworkInterface.modalButtonSize,
                        new Polygon(boundsX, boundsY, 4),
                        cevicheSmall,
                        fontColor
                ),
                "Start New Game",
                BattleNetworkInterface.class.getClassLoader().getResource("images/Button.png")
        );
        this.startButton.setBounds((BattleNetworkInterface.modalSize.width - BattleNetworkInterface.modalButtonSize.width)/2 + 10,261, BattleNetworkInterface.modalButtonSize.width, BattleNetworkInterface.modalButtonSize.height);

        // Add the modal
        this.gameInterface.add(this.modal, JLayeredPane.MODAL_LAYER,3);
    }
    /**
     * Shows the start modal.
     * @param text The text to show in the modal.
     * @author Dario Urdapilleta
     * @since 01/30/2023
     */
    public void showStartModal(String text) {
        // Set the text
        ((JLabel)this.startBody.getComponent(0)).setText(text);
        // Show the modal
        this.modal.showModal(this.modalBackground, this.startBody, this.startButton);
    }
    /**
     * Hides the modal.
     * @author Dario Urdapilleta
     * @since 01/30/2023
     */
    /**
     * Shows the select ship page.
     * @author Dario Urdapilleta
     * @since 02/02/2023
     */
    public void showShipEdit() {
        // Hide both containers
        this.leftBoardBackground.setVisible(false);
        this.rightBoardBackground.setVisible(false);

        // Deselect the buttons
        this.horizontalShip.setUnselected();
        this.verticalShip.setUnselected();

        // Clear all the previous elements
        this.rightBoardBackground.clearAttacks();
        this.leftBoardBackground.clearAttacks();

        // Left: show grid and hide message
        this.leftBoardBackground.showGrid();
        this.leftBoardBackground.hideMessage();

        // Right: Hide grid and show message
        this.rightBoardBackground.hideGrid();
        this.rightBoardBackground.showMessage(this.selectShip);

        // Show both containers and the ship count
        this.opponentShips.setVisible(true);
        this.playerShips.setVisible(true);
        this.leftBoardBackground.setVisible(true);
        this.rightBoardBackground.setVisible(true);
    }

    /**
     * Shows the attack result message.
     * @param textMessage The text message
     * @param button The message button.
     * @author Dario Urdapilleta
     * @since 02/03/2023
     */
    public void showAttackScreen(String textMessage, ImageTextButton button) {
        // Hide both containers
        this.leftBoardBackground.setVisible(false);
        this.rightBoardBackground.setVisible(false);

        // Left hide Grid and show message

        this.leftBoardBackground.hideGrid();
        JPanel panel = this.createTextLabel(textMessage, button, ceviche);
        this.leftBoardBackground.showMessage(panel);

        // Right show grid and hide message
        this.rightBoardBackground.showGrid();
        this.rightBoardBackground.hideMessage();

        // Show both containers
        this.leftBoardBackground.setVisible(true);
        this.rightBoardBackground.setVisible(true);
    }
    /**
     * Shows the attacked result message.
     * @param textMessage The text message
     * @param button The message button.
     * @author Dario Urdapilleta
     * @since 02/03/2023
     */
    public void showAttackedScreen(String textMessage, ImageTextButton button) {
        // Hide both containers
        this.rightBoardBackground.setVisible(false);
        this.leftBoardBackground.setVisible(false);

        // Left show grid and hide message
        this.leftBoardBackground.showGrid();
        this.leftBoardBackground.hideMessage();

        // Right hide grid and show message
        this.rightBoardBackground.hideGrid();
        JPanel panel = this.createTextLabel(textMessage, button, cevicheSmaller);
        this.rightBoardBackground.showMessage(panel);

        // Show both containers
        this.leftBoardBackground.setVisible(true);
        this.rightBoardBackground.setVisible(true);
    }

    /**
     * Creates a text label to display information.
     * @param textMessage The message.
     * @param button The button.
     * @param font The font.
     * @return Returns a {@link javax.swing.JPanel JPanel} with the text and the specified button.
     * @author Dario Urdapilleta
     * @since 02/03/2023
     */
    private JPanel createTextLabel(String textMessage, ImageTextButton button, Font font) {
        // create the container
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(30,30,BoardContainer.size.width - 15, BoardContainer.size.height - 15);
        panel.setOpaque(false);

        // Create the label
        JLabel message = new JLabel();
        message.setText("<html><center>" + textMessage + "</center></html>");
        message.setBorder(new EmptyBorder(0,10,0,10));
        message.setFont(font);
        message.setForeground(fontColor);
        panel.add(message);

        // Check if it has a button and adjust accordingly
        if(button != null) {
            panel.add(button);
            message.setBounds(0,0,BoardContainer.size.width, BoardContainer.size.height - BattleNetworkInterface.BUTTON_MARGIN);
        } else {
            message.setBounds(0,0,BoardContainer.size.width, BoardContainer.size.height);
        }
        return panel;
    }

    /**
     * Sets the frame'w window adapter.
     * @param handle The {@link java.awt.event.WindowAdapter Window Adapter} to set to the frame.
     * @author Dario Urdapilleta
     * @since 02/05/2023
     */
    public void setWindowAdapter(WindowAdapter handle) {
        this.frame.addWindowListener(handle);
    }

    /**
     * Updates the opponent ships label.
     * @param ships The amount of ships to display
     * @author Dario Urdapilleta
     * @since 02/05/2023
     */
    public void updateOpponentShips(int ships) {
        this.opponentShips.setShips(ships);
    }
    /**
     *
     * @param ships
     * @author Dario Urdapilleta
     * @since 02/05/2023
     */
    public void updatePlayerShips(int ships) {
        this.playerShips.setShips(ships);
    }
}
