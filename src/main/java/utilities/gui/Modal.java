package utilities.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.border.EmptyBorder;

/**
 *  This class is a Modal that contains a body and an action button.
 * @author Dario Urdapilleta
 * @version 1.0
 * @since 05/04/2022
 */
public class Modal extends JPanel{
    /**
     * A boolean that keeps track if the Modal has a background image or not.
     */
    private boolean hasBackground = false;		// This data member knows if the modal has an image as a background

    /**
     * Creates a new instance of the Modal with default values. It will be hidden by default.
     * @param size The modal size.
     * @param margin The modal margin.
     * @author Dario Urdapilleta
     * @since 05/04/2022
     */
    public Modal (Dimension size, Dimension margin) {
        super();
        this.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.setLayout(null);
        this.setOpaque(false);
        this.setBounds(margin.width, margin.height, size.width, size.height);
        this.setVisible(false);
        this.hasBackground = false;
    }
    /**
     * Shows the modal without changing its contents.
     * @author Dario Urdapilleta
     * @since 05/04/2022
     */
    public void showModal () {
        this.setVisible(true);
    }

    /**
     * Shows the modal after changing its contents.
     * @param title The Modal's Title component.
     * @param button The modal's action button.
     * @author Dario Urdapilleta
     * @since 05/04/2022
     */
    public void showModal (ModalTitle title, Button button) {
        this.removeAll();
        this.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.add(title);
        this.add(button);
        this.hasBackground = false;
        this.setVisible(true);
    }
    /**
     * Shows the modal after changing its contents.
     * @param title The Modal's Title component.
     * @param modalBody The modal's body.
     * @param button The modal's action button.
     * @author Dario Urdapilleta
     * @since 05/04/2022
     */
    public void showModal (Background background, ModalTitle title, JPanel modalBody, Button button) {
        this.removeAll();
        this.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.setOpaque(false);
        this.add(button);
        this.add(title);
        this.add(modalBody);
        this.add(background);
        this.hasBackground = true;
        this.setVisible(true);
    }

    /**
     * Shows the modal after changing its contents.
     * @param modalBody The modal's body.
     * @param button The modal's action button.
     * @author Dario Urdapilleta
     * @since 05/04/2022
     */
    public void showModal (Background background, JPanel modalBody, Button button) {
        this.removeAll();
        this.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.setOpaque(false);
        this.add(button);
        this.add(modalBody);
        this.add(background);
        this.hasBackground = true;
        this.setVisible(true);
    }

    /**
     * Hides the modal.
     * @author Dario Urdapilleta
     * @since 05/04/2022
     */
    public void hideModal () {
        this.setVisible(false);
    }

    /**
     * This method extends the JPanel functionality to consistently paint the modal's Background with a color that
     * has alpha channel.
     * @param graphics the <code>Graphics</code> object to protect.
     * @author Dario Urdapilleta
     * @since 05/04/2022
     */
    protected void paintComponent(Graphics graphics) {
        if (!this.hasBackground) {
            graphics.setColor(new Color(0, 0, 0, 0.3f));
            graphics.fillRect(0, 0, getWidth(), getHeight());
        }
        super.paintComponent(graphics);
    }
}
