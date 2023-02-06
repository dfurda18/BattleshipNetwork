package client;

import utilities.gui.Background;
import utilities.gui.GameAction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.net.URL;

/**
 * Graphical container for a board.
 * @author Dario Urdapilleta
 * @version 1.0
 * @since 02/02/2023
 */
public class BoardContainer extends JPanel {
    /**
     * The board size
     */
    public final static Dimension size = new Dimension(431, 436);
    /**
     * The cell size
     */
    private final static Dimension cellSize = new Dimension(41, 41);
    /**
     * The grid width
     */
    public final static int WIDTH = 10;
    /**
     * The grid height
     */
    private final static int HEIGHT = 10;
    /**
     * The grid color.
     */
    private static final Color GRID_COLOR = new Color(84, 249, 255);
    /**
     * The grid container
     */
    private JPanel gridContainer;
    /**
     * The ship container
     */
    private JPanel shipContainer;
    /**
     * The attack container
     */
    private JPanel attackContainer;
    /**
     * The message container
     */
    public JPanel messageContainer;
    /**
     * The BoardContainer constructor.
     * @author Dario Urdapilleta
     * @since 02/02/2023
     */
    public BoardContainer () {
        super();
        this.setOpaque(false);
        this.setLayout(null);
        JLabel tmpGridCell, tmpAttackCell;
        // Create the background
        Background background = new Background(BoardContainer.size, "images/Board.png");

        // Create the grid
        this.gridContainer = new JPanel();
        this.gridContainer.setBounds(1,3, BoardContainer.size.width, BoardContainer.size.height);
        this.gridContainer.setOpaque(false);
        this.gridContainer.setLayout(new GridBagLayout());

        // Create the ship Container
        this.shipContainer = new JPanel();
        this.shipContainer.setOpaque(false);
        this.shipContainer.setLayout(null);
        this.shipContainer.setBounds(4,8, BoardContainer.size.width, BoardContainer.size.height);

        // Create the attack Container
        this.attackContainer = new JPanel();
        this.attackContainer.setBounds(4,8, BoardContainer.size.width, BoardContainer.size.height);
        this.attackContainer.setOpaque(false);
        this.attackContainer.setLayout(null);

        // Create the message Container
        this.messageContainer = new JPanel();
        this.messageContainer.setLayout(null);
        this.messageContainer.setBounds(0,0, BoardContainer.size.width, BoardContainer.size.height);
        this.messageContainer.setOpaque(false);

        // Set the constraints
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.insets = new Insets(0,0,1,1);

        // Add the grid elements
        for(int counter = 0; counter < WIDTH * HEIGHT; counter++) {
            tmpGridCell = new GridCell(counter);
            tmpGridCell.setMinimumSize(cellSize);
            tmpGridCell.setPreferredSize(cellSize);
            tmpGridCell.setMaximumSize(cellSize);
            tmpGridCell.setBackground(GRID_COLOR);
            tmpGridCell.setOpaque(true);
            this.gridContainer.add(tmpGridCell, constraints);
            constraints.gridx++;
            if(constraints.gridx >= WIDTH) {
                constraints.gridx = 0;
                constraints.gridy++;
            }
        }

        // Add the components
        this.add(this.messageContainer);
        this.add(this.attackContainer);
        this.add(this.shipContainer);
        this.add(this.gridContainer);
        this.add(background);
    }
    /**
     * Hides all the grid elements.
     * @author Dario Urdapilleta
     * @since 02/02/2023
     */
    public void hideGrid() {
        this.gridContainer.setVisible(false);
        this.attackContainer.setVisible(false);
        this.shipContainer.setVisible(false);
    }
    /**
     * Shows something in the message container.
     * @param content The content to show.
     * @author Dario Urdapilleta
     * @since 02/02/2023
     */
    public void showMessage(JPanel content) {
        this.messageContainer.removeAll();
        content.setBounds(0,0, BoardContainer.size.width, BoardContainer.size.height);
        this.messageContainer.add(content);
        this.messageContainer.setVisible(true);
    }
    /**
     * Shows all the grid elements.
     * @author Dario Urdapilleta
     * @since 02/02/2023
     */
    public void showGrid() {
        this.gridContainer.setVisible(true);
        this.attackContainer.setVisible(true);
        this.shipContainer.setVisible(true);
    }
    /**
     * Hides something in the message container.
     * @author Dario Urdapilleta
     * @since 02/02/2023
     */
    public void hideMessage() {
        this.messageContainer.setVisible(false);
    }

    /**
     * Sets the grid elements a click action.
     * @param action Thea ction for each grid element.
     * @author Dario Urdapilleta
     * @since 02/02/2023
     */
    public void setGridActions(MouseListener action) {
        for(int component = 0; component < this.gridContainer.getComponentCount(); component++) {
            this.gridContainer.getComponent(component).addMouseListener(action);
        }
    }

    /**
     * Inserts graphically a ship in the grid.
     * @param position The grid cell position
     * @param orientation The ship orientation. True for vertical and false for horizontal.
     * @param width The ship's image width
     * @param height The ship's image height
     * @author Dario Urdapilleta
     * @since 02/03/2023
     */
    public void insertShip(int position, boolean orientation, int width, int height) {
        JLabel newShip;
        int x = position % WIDTH;
        int y = (int)Math.floor(position / WIDTH);
        int xOffset;
        int yOffset;

        // Create the ship depending on the orientation
        if(orientation) {
            newShip = new JLabel(new ImageIcon (BattleNetworkInterface.class.getClassLoader().getResource("images/ShipV.png")));
            xOffset = 0;
            yOffset = 10;
        } else {
            newShip = new JLabel(new ImageIcon (BattleNetworkInterface.class.getClassLoader().getResource("images/ShipH.png")));
            xOffset = 10;
            yOffset = 0;
        }
        newShip.setBounds(x * (cellSize.width + 1) + xOffset, y * (cellSize.height + 1) + yOffset, width, height);
        newShip.setOpaque(false);

        // Add the chip
        this.shipContainer.add(newShip);
        this.shipContainer.repaint();
    }
    /**
     * Clears the attack container.
     * @author Dario Urdapilleta
     * @since 02/03/2023
     */
    public void clearAttacks() {
        this.shipContainer.removeAll();
        this.attackContainer.removeAll();
    }

    /**
     * Displays a hit element in the attack panel.
     * @param position The position to locate the element
     * @author Dario Urdapilleta
     * @since 02/05/2023
     */
    public void hit(int position) {
        URL image = BattleNetworkInterface.class.getClassLoader().getResource("images/O.png");
        this.addMark(position, image);
    }

    /**
     * Displays a miss element in the attack panel.
     * @param position The position to locate the element
     * @author Dario Urdapilleta
     * @since 02/05/2023
     */
    public void miss(int position) {
        URL image = BattleNetworkInterface.class.getClassLoader().getResource("images/X.png");
        this.addMark(position, image);
    }

    /**
     * Adds a mark into the attack container.
     * @param position The position to locate the element
     * @param image The mark image
     * @author Dario Urdapilleta
     * @since 02/05/2023
     */
    private void addMark(int position, URL image) {
        // Create the mark label
        JLabel hitMark = new JLabel(new ImageIcon (image));
        int x = position % WIDTH;
        int y = (int)Math.floor(position / WIDTH);
        int xOffset = 3;
        int yOffset = 3;
        hitMark.setBounds(x * (cellSize.width + 1) + xOffset, y * (cellSize.height + 1) + yOffset, BoardContainer.cellSize.width, BoardContainer.cellSize.height);
        hitMark.setOpaque(false);

        // Add and repaint
        this.attackContainer.add(hitMark);
        this.shipContainer.repaint();
        this.attackContainer.repaint();
    }
}
