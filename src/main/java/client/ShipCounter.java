package client;

import utilities.gui.ButtonOptions;
import utilities.gui.ShadowText;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * Class that represents a graphical element of the remaining ships.
 * @author Dario Urdapilleta
 * @version 1.0
 * @since 02/05/2023
 */
public class ShipCounter extends JPanel {
    /**
     * The ship counter
     */
    private int ships;
    /**
     * The label with the text
     */
    private ShadowText text;
    /**
     * Creates a new instance of the ShipContainer class.
     * @param ships The initial number of ships
     * @param image The image URL
     * @param font The font
     * @author Dario Urdapilleta
     * @since 02/05/2023
     */
    public ShipCounter(int ships, URL image, Font font, Color color) {
        super();
        this.setOpaque(false);
        this.setLayout(null);
        this.ships = ships;

        // Create the image label
        JLabel imageLabel = new JLabel(new ImageIcon(image));
        imageLabel.setOpaque(false);
        imageLabel.setBounds(0,0, 97, 100);

        // Create the text label
        this.text = new ShadowText(new ButtonOptions(
                new Dimension(95,10),
                new Dimension(160, 85),
                new Dimension(-4,4),
                font,
                font,
                color,
                Color.BLACK
        ), "<html><center>" + this.ships + "</center></html>");

        // Add the components
        this.add(imageLabel);
        this.add(this.text.getText());
        this.add(this.text.getShadow());
    }

    /**
     * Sets the amount of ships and updates the label.
     * @param ships The amount of ships
     * @author Dario Urdapilleta
     * @since 02/05/2023
     */
    public void setShips(int ships) {
        this.ships = ships;
        this.text.getText().setText("<html><center>x " + this.ships + "</center></html>");
        this.text.getShadow().setText("<html><center>x " + this.ships + "</center></html>");
        this.repaint();
    }
}
