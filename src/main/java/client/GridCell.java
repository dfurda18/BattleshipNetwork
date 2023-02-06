package client;

import javax.swing.*;

/**
 * Represents a Cell in the grid.
 * @author Dario Urdapilleta
 * @version 1.0
 * @since 01/30/2023
 */
public class GridCell extends JLabel {
    /**
     * The cell's position in its parent
     */
    public int position;
    /**
     * Wheather or not this cell has been used to attack;
     */
    boolean attacked;
    /**
     * GridCell constructor.
     * @param position The cell's position in its parent
     * @author Dario Urdapilleta
     * @since 01/30/2023
     */
    public GridCell(int position) {
        super();
        this.attacked = false;
        this.position = position;
    }

    /**
     * Returns the X coordinate of the cell in the grid.
     * @return the X coordinate of the cell in the grid.
     * @author Dario Urdapilleta
     * @since 02/05/2023
     */
    public int getXCoordinate() {
        return (int)Math.floor(position / BoardContainer.WIDTH);
    }

    /**
     * Returns the Y coordinate of the cell in the grid.
     * @return the Y coordinate of the cell in the grid.
     * @author Dario Urdapilleta
     * @since 02/05/2023
     */
    public int getYCoordinate() {
        return position % BoardContainer.WIDTH;
    }
}
