package utilities.gui;

import javax.swing.JLayeredPane;

/**
 * This class is a Button that is shown as a text that can be clicked only when the mouse is inside an specified polygon.
 * @author Dario Urdapilleta
 * @version 1.0
 * @since 05/04/2022
 */
public class TextButton extends Button {
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
     * @author Dario Urdapilleta
     * @since 05/04/2022
     */
    public TextButton (ButtonOptions options, String text) {
        super(options);
        ShadowText buttonText = new ShadowText(options, text);
        buttonText.getShadow().setBounds(0,2,options.getSize().width+2,options.getSize().height);
        this.add(buttonText.getText(), JLayeredPane.DEFAULT_LAYER);
        this.add(buttonText.getShadow(), JLayeredPane.FRAME_CONTENT_LAYER);
    }
}
