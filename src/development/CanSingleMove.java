package development;

/**
 * <p>Represents the ability to make a single move in chess.</p>
 * <p>A single move is a move of one space in any direction, including diagonal.</p>
 *
 * @author Nobel Zhou (nxz157)
 * @version 1.0, 12/2/20
 */
public interface CanSingleMove extends CanMove {
    /**
     * <p>Returns a boolean representing if the proposed row and column is a valid single move from the piece's location.</p>
     *
     * @param row       the piece's destination row
     * @param column    the piece's destination column
     * @param cp        the chess piece
     * @return          <code>true</code> if the proposed location is a valid single move
     * @since 1.0
     */
    default boolean isValidSingleMove(int row, int column, ChessPiece cp) {
        return isValidMove(row, column, cp) && (row - cp.getRow() <= 1 && cp.getRow() - row <= 1) && (column - cp.getColumn() <= 1 && cp.getColumn() - column <= 1);
    }
}