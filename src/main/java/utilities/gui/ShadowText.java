package utilities.gui;

import javax.swing.JLabel;

/**
 * This class is a component that has a text and a shadow.
 * @author Dario Urdapilleta
 * @version 1.0
 * @since 05/04/2022
 */
public class ShadowText {
    /**
     * The text
     */
    private JLabel text;
    /**
     * The shadow
     */
    private JLabel shadow;

    /**
     * Creates a new instance of the ShadowText which is a text with a drop shadow.
     * @param options The text's configuration.
     *                  - font size: String
     *                  - color: Color
     *                  - margin: Dimension
     *                  - size: Dimension
     *                  - font size: int
     * @param text The text
     * @author Dario Urdapilleta
     * @since 05/04/2022
     */
    public ShadowText (ButtonOptions options, String text) {
        int shadowTextMarginX = 0;
        int shadowTextMarginY = 0;
        int textMarginX = 0;
        int textMarginY = 0;

        // Adjust the margin from the options
        if (options.getShadowMargin().width < 0) {
            textMarginX = options.getShadowMargin().width * -1;
        } else {
            shadowTextMarginX = options.getShadowMargin().width;
        }
        if (options.getShadowMargin().height < 0) {
            textMarginY = options.getShadowMargin().height * -1;
        } else {
            shadowTextMarginY = options.getShadowMargin().height;
        }
        if(options.isPositionAbsolute()) {
            textMarginX += options.getMargin().width;
            textMarginY += options.getMargin().height;
            shadowTextMarginX += options.getMargin().width;
            shadowTextMarginY += options.getMargin().height;
        }

        // Create the graphical elements
        this.text = new JLabel(text);
        this.shadow = new JLabel(text);
        this.text.setFont(options.getFont());
        this.text.setForeground(options.getColor());
        this.text.setBounds(textMarginX,
                textMarginY,
                options.getSize().width,
                options.getSize().height);
        this.text.setHorizontalAlignment(JLabel.CENTER);
        this.text.setOpaque(false);
        this.shadow.setFont(options.getShadowFont());
        this.shadow.setForeground(options.getShadowColor());
        this.shadow.setBounds(shadowTextMarginX,
                shadowTextMarginY,
                options.getSize().width,
                options.getSize().height);
        this.shadow.setHorizontalAlignment(JLabel.CENTER);
        this.shadow.setOpaque(false);
    }

    /**
     * Returns the shadow text's width.
     * @return The text's width
     * @author Dario Urdapilleta
     * @since 05/04/2022
     */
    public int getWidth () {
        return this.shadow.getPreferredSize().width+3;
    }

    /**
     * Set the text's content.
     * @param text The new text.
     * @author Dario Urdapilleta
     * @since 05/04/2022
     */
    public void setText (String text) {
        this.text.setText(text);
        this.shadow.setText(text);
    }

    /**
     * Returns the text component.
     * @return The text component.
     * @author Dario Urdapilleta
     * @since 05/04/2022
     */
    public JLabel getText() {
        return this.text;
    }

    /**
     * Returns the shadow component.
     * @return The shadow component.
     * @author Dario Urdapilleta
     * @since 05/04/2022
     */
    public JLabel getShadow() {
        return this.shadow;
    }

    /**
     * Shows this component.
     * @author Dario Urdapilleta
     * @since 05/04/2022
     */
    public void show() {
        this.text.setVisible(true);
        this.shadow.setVisible(true);
    }

    /**
     * Hides this component.
     * @author Dario Urdapilleta
     * @since 05/04/2022
     */
    public void hide() {
        this.text.setVisible(false);
        this.shadow.setVisible(false);
    }
}
