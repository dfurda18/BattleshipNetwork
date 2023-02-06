package utilities.gui;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * This class is a Button that shows an image, but has a background to toggle when clicking.
 * @author Dario Urdapilleta
 * @version 1.0
 * @since 02/03/2022
 */
public class ImageSelectButton extends Button {
    /**
     * A pointer to the background image to toggle it.
     */
    JPanel background;
    /**
     * A pointer to the content image to resize it.
     */
    JPanel content;
    /**
     * Creates a new instance of the ImageSelectButton class with the specified size and margin, clickable area and
     * background image and foreground image.
     * @param options The options that define the Button's characteristics:
     *                - Size: Dimension
     *                - Margin: Dimension
     *                - Clickable Area: Polygon
     *                - font size: String
     *                - color: Color
     *                - font size: int
     * @param foreground The foreground image.
     * @param background The background image.
     * @author Dario Urdapilleta
     * @since 02/03/2022
     */
    public ImageSelectButton (ButtonOptions options, URL foreground, URL background) {
        super(options);
        this.setLayout(null);
        this.background = new JPanel();
        this.background.add(new JLabel(
                new ImageIcon(
                        new ImageIcon (background).getImage().getScaledInstance(
                                options.getSize().width,
                                options.getSize().height-5,
                                Image.SCALE_DEFAULT)
                )
        ));
        this.background.setBounds(0, 0, options.getSize().width, options.getSize().height);
        this.background.setOpaque(false);
        this.background.setVisible(false);

        content = new JPanel();
        this.content.add(new JLabel(new ImageIcon (foreground)));
        this.content.setBounds(0, 0, options.getSize().width, options.getSize().height);
        this.content.setOpaque(false);

        this.add(this.content);
        this.add(this.background);
    }

    /**
     * Toggles the backgroung and returns the current state.
     * @return True if the background is on and false otherwise.
     * @author Dario Urdapilleta
     * @since 02/03/2022
     */
    public boolean toggleSelected () {
        if(this.background.isVisible()) {
            this.background.setVisible(false);
            return false;
        } else {
            this.background.setVisible(true);
            return true;
        }
    }

    /**
     * Turns the backgrounf image on.
     * @author Dario Urdapilleta
     * @since 02/03/2022
     */
    public void setSelected () {
        this.background.setVisible(true);
    }

    /**
     * Turns the backgrounf image off.
     * @author Dario Urdapilleta
     * @since 02/03/2022
     */
    public void setUnselected () {
        this.background.setVisible(false);
    }

    /**
     * Sets the content position and height.
     * @param x The x position
     * @param y The y position
     * @param width The image width
     * @param height The image height
     */
    public void setContentBounds(int x, int y, int width, int height) {
        this.content.setBounds(x, y, width, height);
    }
}
