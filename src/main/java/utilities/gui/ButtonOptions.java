package utilities.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Polygon;

/**
 * This class stores sets of parameters used as options by different interface objects.
 * @author Dario Urdapilleta
 * @version 1.0
 * @since 05/04/2022
 */
public class ButtonOptions {
    /**
     * The element's size.
     */
    private Dimension size;
    /**
     * The left and top margin of the element relative to its container.
     */
    private Dimension margin;
    /**
     * The left and top margin of the shadow element.
     */
    private Dimension shadowMargin;
    /**
     * The font type for the text elements.
     */
    private Font font;
    /**
     * The font type for the text elements.
     */
    private Font shadowFont;
    /**
     * The color of the text elements.
     */
    private Color color;
    /**
     * The shadow color of the text elements.
     */
    private Color shadowColor;
    /**
     * The polygon representing the area of the element.
     */
    private Polygon polygon;
    /**
     * If the position is absolute or relative to the parent.
     */
    private boolean absolute;

    /**
     * Creates a new instance of the ButtonOptions class with the specified information.
     * @param margin The element's margin.
     * @param size The element's size.
     * @param polygon The polygon representing the element's area.
     * @param font The text component's font.
     * @param color The text component's size.
     * @author Dario Urdapilleta
     * @since 05/04/2022
     */
    public ButtonOptions (Dimension margin,  Dimension size, Polygon polygon, Font font, Color color) {
        this.size = size;
        this.margin = margin;
        this.shadowMargin = new Dimension(-4, 4);
        this.font = font;
        this.color = color;
        this.shadowFont = font;
        this.shadowColor = Color.BLACK;
        this.polygon = polygon;
        this.absolute = false;
    }

    /**
     * Creates a new instance of the ButtonOptions class with the specified information.
     * @param margin The element's margin.
     * @param size The element's size.
     * @param font The text component's font.
     * @param color The text component's color.
     * @author Dario Urdapilleta
     * @since 05/04/2022
     */
    public ButtonOptions (Dimension margin,  Dimension size, Font font, Color color) {
        this.size = size;
        this.margin = margin;
        this.shadowMargin = new Dimension(-4, 4);
        this.font = font;
        this.color = color;
        this.shadowFont = font;
        this.shadowColor = Color.BLACK;
        this.absolute = true;
    }

    /**
     * Creates a new instance of the ButtonOptions class with the specified information.
     * @param margin The element's margin.
     * @param size The element's size.
     * @param shawodMargin The shadow text.
     * @param font The font used.
     * @param shadowFont The shadow's font.
     * @param color The color used.
     * @param shadowColor The shadow color.
     * @author Dario Urdapilleta
     * @since 05/04/2022
     */
    public ButtonOptions (Dimension margin,  Dimension size, Dimension shawodMargin, Font font, Font shadowFont, Color color, Color shadowColor) {
        this.size = size;
        this.margin = margin;
        this.shadowMargin = shawodMargin;
        this.font = font;
        this.shadowFont = shadowFont;
        this.color = color;
        this.shadowColor = shadowColor;
        this.absolute = true;
    }

    /**
     * Creates a new instance of the ButtonOptions class with the specified information.
     * @param margin The element's margin.
     * @param size The element's size.
     * @param polygon The polygon representing the element's area.
     * @author Dario Urdapilleta
     * @since 05/04/2022
     */
    public ButtonOptions (Dimension margin,  Dimension size, Polygon polygon) {
        this.size = size;
        this.margin = margin;
        this.polygon = polygon;
        this.absolute = false;
    }

    /**
     * Returns the size information stored.
     * @return The size stored.
     * @author Dario Urdapilleta
     * @since 05/04/2022
     */
    public Dimension getSize () {
        return this.size;
    }

    /**
     * Returns the margin information stored.
     * @return The margin stored.
     * @author Dario Urdapilleta
     * @since 05/04/2022
     */
    public Dimension getMargin () {
        return this.margin;
    }

    /**
     * Returns the shadow's margin information stored.
     * @return The shadow's margin stored.
     * @author Dario Urdapilleta
     * @since 05/04/2022
     */
    public Dimension getShadowMargin () {
        return this.shadowMargin;
    }

    /**
     * Returns the font information stored.
     * @return The font stored.
     * @author Dario Urdapilleta
     * @since 05/04/2022
     */
    public Font getFont () {
        return this.font;
    }

    /**
     * Returns the shadow's font information stored.
     * @return The shadow's font stored.
     * @author Dario Urdapilleta
     * @since 05/04/2022
     */
    public Font getShadowFont () {
        return this.shadowFont;
    }

    /**
     * Returns the color information stored.
     * @return The color stored.
     * @author Dario Urdapilleta
     * @since 05/04/2022
     */
    public Color getColor () {
        return this.color;
    }

    /**
     * Returns the shadow's color information stored.
     * @return The shadow's color stored.
     * @author Dario Urdapilleta
     * @since 05/04/2022
     */
    public Color getShadowColor () {
       return this.shadowColor;
    }

    /**
     * Returns the polygon information stored.
     * @return The polygon stored.
     * @author Dario Urdapilleta
     * @since 05/04/2022
     */
    public Polygon getPolygon () {
        return this.polygon;
    }

    /**
     * Returns true is the position should be absolute and false if it is relative.
     * @return True if the position is absolute and false if it is relative.
     * @author Dario Urdapilleta
     * @since 05/04/2022
     */
    public boolean isPositionAbsolute () {
        return this.absolute;
    }
}
