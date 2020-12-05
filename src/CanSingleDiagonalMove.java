/**
 * <p>Represents the ability to make a single diagonal move in chess.</p>
 *
 * @author Nobel Zhou (nxz157)
 * @version 1.0, 12/4/20
 */
public interface CanSingleDiagonalMove extends CanSingleMove, CanDiagonalMove {
    /**
     * <p>Returns a boolean representing if the proposed row and column is a valid single
     * diagonal move from the piece's location.</p>
     *
     * @param row       the piece's destination row
     * @param column    the piece's destination column
     * @param cp        the chess piece
     * @return          <code>true</code> if the proposed location is a valid single diagonal move
     * @since 1.0
     */
    default boolean isValidSingleDiagonalMove(int row, int column, ChessPiece cp) {
        return isValidSingleMove(row, column, cp) && isValidDiagonalMove(row, column, cp);
    }
}
