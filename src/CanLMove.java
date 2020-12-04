/**
 * <p>Represents the ability to make an L move in chess.</p>
 *
 * @author Nobel Zhou (nxz157)
 * @version 1.0, 12/2/20
 */
public interface CanLMove extends CanMove {
    /**
     * <p>Returns a boolean representing if the proposed row and column is a valid L move from the piece's location.</p>
     *
     * @param row       the piece's destination row
     * @param column    the piece's destination column
     * @param cp        the chess piece
     * @return          <code>true</code> if the proposed location is a valid L move
     * @since 1.0
     */
    default boolean isValidLMove(int row, int column, ChessPiece cp) {
        return isValidMove(row, column, cp) && ((row == cp.getRow() + 2 && (column == cp.getColumn() - 1 || column == cp.getColumn() + 1)) || (row == cp.getRow() - 2 && (column == cp.getColumn() - 1 || column == cp.getColumn() + 1)) || (row == cp.getRow() + 1 && (column == cp.getColumn() - 2 || column == cp.getColumn() + 2)) || (row == cp.getRow() - 1 && (column == cp.getColumn() - 2 || column == cp.getColumn() + 2)));
    }
}
