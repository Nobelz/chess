/**
 * <p>Represents the ability to make a single straight move in chess.</p>
 *
 * @author Nobel Zhou (nxz157)
 * @version 1.0, 12/4/20
 */
public interface CanSingleStraightMove extends CanStraightMove, CanSingleMove {
    /**
     * <p>Returns a boolean representing if the proposed row and column is a valid single
     * horizontal or vertical move from the piece's location.</p>
     *
     * @param row       the piece's destination row
     * @param column    the piece's destination column
     * @param cp        the chess piece
     * @return          <code>true</code> if the proposed location is a valid single horizontal or vertical move
     * @since 1.0
     */
    default boolean isValidSingleStraightMove(int row, int column, ChessPiece cp) {
        return isValidSingleMove(row, column, cp) && isValidStraightMove(row, column, cp);
    }
}
