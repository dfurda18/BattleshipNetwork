package utilities.gui;

import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;

/**
 * This class is a body or title contained in a modal component.
 * @author Dario Urdapilleta
 * @version 1.0
 * @since 05/04/2022
 */
public class ModalTitle extends JPanel {
    /**
     * The modal's size.
     */
    private Dimension modalSize;
    /**
     * The Modal's margin.
     */
    private Dimension margin;

    /**
     * Creates a new instance of the ModalTitle with the specified image, size, margin and title size.
     * @param imageUrl The image to place in the body.
     * @param modalSize The Modal's size
     * @param margin The Modal's margin
     * @param titleSize The Title's size
     * @author Dario Urdapilleta
     * @since 05/04/2022
     */
    public ModalTitle (String imageUrl, Dimension modalSize, Dimension margin, Dimension titleSize) {
        super();
        this.modalSize = modalSize;
        this.margin = margin;
        this.add(new JLabel(new ImageIcon(imageUrl)));
        this.setPreferredSize(titleSize);
        this.setBounds((modalSize.width-titleSize.width)/2,margin.height,titleSize.width, titleSize.height);
        this.setOpaque(false);
    }

    /**
     * Creates a new instance of the ModalTitle with the specified text, size, margin and title size.
     * @param text The text to show.
     * @param modalSize The Modal's size
     * @param margin The Modal's margin
     * @param titleSize The Title's size
     * @param options The text's configuration.
     * @author Dario Urdapilleta
     * @since 05/04/2022
     */
    public ModalTitle (String text, Dimension modalSize, Dimension margin, Dimension titleSize, ButtonOptions options ) {
        super();
        this.setLayout(new GroupLayout(this));
        this.modalSize = modalSize;
        this.margin = margin;
        ShadowText shadowText = new ShadowText (options, text);
        this.add(shadowText.getText());
        this.add(shadowText.getShadow());
        shadowText.getText().setBounds(2,0,modalSize.width+2,titleSize.height);
        shadowText.getShadow().setBounds(0,2,modalSize.width+2,titleSize.height);
        this.setBounds(5,15,titleSize.width, titleSize.height);
        this.setOpaque(false);
    }

    /**
     * Returns the modal's size stored in the title.
     * @return The modal's size
     * @author Dario Urdapilleta
     * @since 05/04/2022
     */
    public Dimension getModalSize () {
        return this.modalSize;			// Return the modal's size
    }

    /**
     * Returns the modal's margin stored in the title.
     * @return The modal's margin
     * @author Dario Urdapilleta
     * @since 05/04/2022
     */
    public Dimension getMargin () {
        return this.margin;				// Return the modal's margin
    }
}
