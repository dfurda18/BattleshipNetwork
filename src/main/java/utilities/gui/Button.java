package utilities.gui;

import javax.swing.JPanel;

import java.awt.Cursor;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.GroupLayout;

/**
 * This class is a Button that can be clicked only when the mouse is inside an specified polygon.
 * @author Dario Urdapilleta
 * @version 1.0
 * @since 05/04/2022
 */
public class Button extends JPanel {
    /**
     * Data member of the polygon representing the button's clickable area.
     */
    private Polygon polygon;
    /**
     * Data member of the action take when the button is clicked.
     */
    private GameAction action;
    /**
     * Data member that knows if the button is enabled.
     */
    private boolean isEnabled;

    /**
     * Creates a new instance of the Button class with the specified size and margin and clickable area.
     * @param options The options that define the Button's characteristics:
     *                - Size: Dimension
     *                - Margin: Dimension
     *                - Clickable Area: Polygon
     * @author Dario Urdapilleta
     * @since 05/04/2022
     */
    public Button (ButtonOptions options) {
        super();
        this.isEnabled = true;
        this.setOpaque(false);
        this.setLayout(new GroupLayout(this));
        this.setPreferredSize(options.getSize());
        this.setBounds(options.getMargin().width,options.getMargin().height,options.getSize().width, options.getSize().height);
        this.polygon = options.getPolygon();
        Button currentButton = this;
        this.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if(currentButton.isEnabled && currentButton.getPolygon().contains(e.getX(), e.getY())) {
                    currentButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                } else {
                    currentButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
            }

        });
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(currentButton.isEnabled && currentButton.getPolygon().contains(e.getX(), e.getY())) {
                    currentButton.action.performAction();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    /**
     * Returns the button's polygon.
     * @return This Button's polygon.
     * @author Dario Urdapilleta
     * @since 05/04/2022
     */
    public Polygon getPolygon () {
        return this.polygon;
    }

    /**
     * Enables the button.
     * @author Dario Urdapilleta
     * @since 05/04/2022
     */
    public void enable () {
        this.isEnabled = true;
    }

    /**
     * Disables the button.
     * @author Dario Urdapilleta
     * @since 05/04/2022
     */
    public void disable () {
        this.isEnabled = false;
    }

    /**
     * Sets the Button's action.
     * @param action The action that the button will perform when clicked.
     * @author Dario Urdapilleta
     * @since 05/04/2022
     */
    public void setClickAction (GameAction action) {
        this.action = action;
    }
}

