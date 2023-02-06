package utilities.gui;

/**
 * This interface allows adding interaction to graphical elements. It must implement the performAction method.
 * @author Dario Urdapilleta
 * @version 1.0
 * @since 05/04/2022
 */
public interface GameAction {
    /**
     * This method will be executed when the GameAction must perform an action.
     * @author Dario Urdapilleta
     * @since 05/04/2022
     */
    public void performAction ();
}