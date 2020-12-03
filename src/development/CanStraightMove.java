package development;

/**
 * <p>Represents the ability to make a straight move in chess.</p>
 *
 * @author Nobel Zhou (nxz157)
 * @version 1.0, 12/2/20
 */
public interface CanStraightMove extends CanMove {
    /**
     * <p>Returns a boolean representing if the proposed row and column is a valid
     * horizontal or vertical move from the piece's location.</p>
     *
     * @param row       the piece's destination row
     * @param column    the piece's destination column
     * @param cp        the chess piece
     * @return          <code>true</code> if the proposed location is a valid horizontal or vertical move
     * @since 1.0
     */
    default boolean isValidStraightMove(int row, int column, ChessPiece cp) {
        // Checks to make sure that it's a valid square first and that is a horizontal or vertical square
        if (isValidMove(row, column, cp) && (row == cp.getRow() || column == cp.getColumn())) {
            // Moves row or column 1 space closer to piece
            row = (row == cp.getRow()) ? row : (row > cp.getRow()) ? row - 1 : row + 1;
            column = (column == cp.getColumn()) ? column : (column > cp.getColumn()) ? column - 1 : column + 1;

            // Checks that all of the squares up to it are empty
            if (row == cp.getRow()) { // Horizontal movement
                if (column > cp.getColumn()) { // Right movement
                    // Iterates for each square, starting at right and going left
                    for (int i = column; i > cp.getColumn(); i--) {
                        // If not a valid non-capture move, return false
                        if (!cp.getChessBoard().hasPiece(row, i))
                            return false;
                    }
                } else { // Left movement
                    // Iterates for each square, starting at left and going right
                    for (int i = column; i < cp.getColumn(); i++) {
                        //If not a valid non-capture move, return false
                        if (!cp.getChessBoard().hasPiece(row, i))
                            return false;
                    }
                }
            } else { // Vertical movement
                if (row > cp.getRow()) { // Down movement
                    // Iterates for each square, starting at down and going up
                    for (int i = row; i > cp.getRow(); i--) {
                        // If not a valid non-capture move, return false
                        if (!cp.getChessBoard().hasPiece(i, column))
                            return false;
                    }
                } else { // Up movement
                    // Iterates for each square, starting at up and going down
                    for (int i = row; i < cp.getRow(); i++) {
                        // If not a valid non-capture move, return false
                        if (!cp.getChessBoard().hasPiece(i, column))
                            return false;
                    }
                }
            }
            return true;
        } else
            return false; // Not a valid diagonal move
    }
}
