package utilities.gui;

import client.BattleNetworkInterface;

import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

/**
 * This class represents an interface element that works as a background in an interface. It is meant to be used as a frame layer in a JLayeredPane.
 * @author Dario Urdapilleta
 * @version 1.0
 * @since 2022-05-04
 */
public class Background extends JPanel {

    /**
     * Creates a new instance of the Background class with the specified size and image.
     * @param size The background's size.
     * @param fileURL The image to paint as background.
     * @return A new instance of the class Background.
     * @author Dario Urdapilleta
     * @version 1.0
     * @since 2022-05-04
     */
    public Background (Dimension size, String fileURL) {
        super();
        this.setOpaque(false);
        this.add(new JLabel(new ImageIcon(BattleNetworkInterface.class.getClassLoader().getResource(fileURL))));
        this.setPreferredSize(size);
        this.setBounds(0,0,size.width, size.height);
    }
}
