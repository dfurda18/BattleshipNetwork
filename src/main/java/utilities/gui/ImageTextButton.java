package utilities.gui;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * This class is a Button that is shown as a text witch a background image that can be clicked only when the mouse
 * is inside a specified polygon.
 * @author Dario Urdapilleta
 * @version 1.0
 * @since 05/04/2022
 */
public class ImageTextButton extends Button {
    /**
     * Creates a new instance of the TextButton class with the specified size and margin, clickable area and text.
     * @param options The options that define the Button's characteristics:
     *                - Size: Dimension
     *                - Margin: Dimension
     *                - Clickable Area: Polygon
     *                - font size: String
     *                - color: Color
     *                - font size: int
     * @param text The text.
     * @param url The url for the background image.
     * @author Dario Urdapilleta
     * @since 02/01/2023
     */
    public ImageTextButton (ButtonOptions options, String text, URL url) {
        super(options);
        JPanel background = new JPanel();
        background.add(new JLabel(
                new ImageIcon(
                        new ImageIcon (url).getImage().getScaledInstance(
                                options.getSize().width,
                                options.getSize().height-5,
                                Image.SCALE_DEFAULT)
                )
        ));
        background.setBounds(0, 0, options.getSize().width, options.getSize().height);
        background.setOpaque(false);

        ShadowText buttonText = new ShadowText(options, text);
        buttonText.getText().setBounds(-2,0,options.getSize().width - 2,options.getSize().height);
        buttonText.getShadow().setBounds(-4,2,options.getSize().width - 4,options.getSize().height + 2);
        this.add(buttonText.getText(), JLayeredPane.DEFAULT_LAYER);
        this.add(buttonText.getShadow(), JLayeredPane.FRAME_CONTENT_LAYER);
        this.add(background);
    }
}
