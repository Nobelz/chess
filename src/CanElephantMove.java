/**
 * <p>Represents the ability to make an elephant move in chess.</p>
 *
 * @author Nobel Zhou (nxz157)
 * @version 1.0, 12/4/20
 */
public interface CanElephantMove extends CanDiagonalMove {
    /**
     * <p>Returns a boolean representing if the proposed row and column is a valid elephant
     * move from the piece's location.</p>
     *
     * @param row       the piece's destination row
     * @param column    the piece's destination column
     * @param cp        the chess piece
     * @return          <code>true</code> if the proposed location is a valid elephant move
     * @since 1.0
     */
    default boolean isValidElephantMove(int row, int column, ChessPiece cp) {
        if (isValidDiagonalMove(row, column, cp) && Math.abs(row - cp.getRow()) == 2 && Math.abs(column - cp.getColumn()) == 2) {
            // Restricts the elephant move to half of the board, which varies depending on what side you are
            switch (cp.getSide()) {
                case NORTH:
                    return row <= 4;
                case SOUTH:
                    return row >= 5;
                case EAST:
                    return column >= 5;
                default: // WEST
                    return column <= 4;
            }
        } else
            return false;
    }
}
