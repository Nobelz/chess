/**
 * <p>Represents a chess piece that is the center piece of the game.</p>
 *
 * @author Nobel Zhou (nxz157)
 * @version 1.0, 12/4/2020
 */
public interface CenterPiece {
    /**
     * <p>Returns a boolean representing if the center piece is currently in check.</p>
     *
     * @return  <code>true</code> if the center piece is currently in check
     * @since 1.0
     */
    boolean isInCheck();
}
