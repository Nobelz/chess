/**
 * <p>Represents the ability to make a palace move in chess.</p>
 * <p>A palace move is basically a move that can is confined in a 3x3 area known as the "palace."</p>
 *
 * @author Nobel Zhou (nxz157)
 * @version 1.0, 12/4/20
 */
public interface CanPalaceMove extends CanMove {
    /**
     * <p>Returns a boolean representing if the proposed row and column is a valid palace
     * move from the piece's location.</p>
     *
     * @param row       the piece's destination row
     * @param column    the piece's destination column
     * @param cp        the chess piece
     * @return          <code>true</code> if the proposed location is a valid palace move
     * @since 1.0
     */
    default boolean isValidPalaceMove(int row, int column, ChessPiece cp) {
        if (isValidMove(row, column, cp)) {
            // Checks the side to restrict to 3x3 area
            switch (cp.getSide()) {
                case SOUTH:
                    return row >= 7 && column <= 5 && column >= 3;
                case NORTH:
                    return row <= 2 && column <= 5 && column >= 3;
                case WEST:
                    return column <= 2 && row <= 5 && row >= 3;
                default: // EAST
                    return column >= 7 && row <= 5 && row >= 3;
            }
        } else
            return false;
    }
}
