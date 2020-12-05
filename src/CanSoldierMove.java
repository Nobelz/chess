/**
 * <p>Represents the ability to make a soldier move in chess.</p>
 *
 * @author Nobel Zhou (nxz157)
 * @version 1.0, 12/4/20
 */
public interface CanSoldierMove extends CanSingleStraightMove {
    /**
     * <p>Returns a boolean representing if the proposed row and column is a valid soldier move from the piece's location.</p>
     *
     * @param row       the piece's destination row
     * @param column    the piece's destination column
     * @param cp        the chess piece
     * @return          <code>true</code> if the proposed location is a valid soldier move
     * @since 1.0
     */
    default boolean isValidSoldierMove(int row, int column, ChessPiece cp) {
        if (isValidSingleStraightMove(row, column, cp)) {
            // Checks to see if the pawn is still on the same side; if so, it is restricted to only moving forward.
            // Otherwise, it can move sideways or forwards but never back
            switch (cp.getSide()) {
                case NORTH:
                    if (row != cp.getRow() - 1) {
                        return (row <= 4 || row == cp.getRow() + 1);
                    } else
                        return false;
                case SOUTH:
                    if (row != cp.getRow() + 1) {
                        return (row >= 5 || row == cp.getRow() - 1);
                    } else
                        return false;
                case WEST:
                    if (column != cp.getColumn() - 1) {
                        return (column >= 5 || column == cp.getColumn() + 1);
                    } else
                        return false;
                default: // EAST
                    if (column != cp.getColumn() + 1) {
                        return (column <= 4 || column == cp.getColumn() - 1);
                    } else
                        return false;
            }
        } else
            return false;
    }

    /**
     * <p>Returns a boolean representing if the proposed row and column is a valid forward soldier move from the piece's location.</p>
     *
     * @param row       the piece's destination row
     * @param column    the piece's destination column
     * @param cp        the chess piece
     * @return          <code>true</code> if the proposed location is a valid forward soldier move
     * @since 1.0
     */
    default boolean isValidForwardSoldierMove(int row, int column, ChessPiece cp) {
        if (isValidSoldierMove(row, column, cp)) {
            // Checks to see if the pawn is still on the same side; if so, it is restricted to only moving forward.
            // Otherwise, it can move sideways or forwards but never back
            switch (cp.getSide()) {
                case NORTH:
                    return row == cp.getRow() + 1;
                case SOUTH:
                    return row == cp.getRow() - 1;
                case WEST:
                    return column == cp.getColumn() - 1;
                default: // EAST
                    return column == cp.getColumn() + 1;
            }
        } else
            return false;
    }
}
