/**
 * <p>Represents the ability to make a xiangqi facing king move in chess.</p>
 *
 * @author Nobel Zhou (nxz157)
 * @version 1.0, 12/5/20
 */
public interface CanFaceKingMove extends CanMove {
    /**
     * <p>Returns a boolean representing if the proposed row and column is a valid
     * facing king move from the piece's location.</p>
     *
     * @param row       the piece's destination row
     * @param column    the piece's destination column
     * @param cp        the chess piece
     * @return          <code>true</code> if the proposed location is a valid facing king move
     * @since 1.0
     */
    default boolean isValidFaceKingMove(int row, int column, ChessPiece cp) {
        // Checks to make sure that it's a valid move first and then if the move captures a xiangqi king piece
        if (isValidMove(row, column, cp) && cp.getChessBoard().getPiece(row, column) instanceof XiangqiKingPiece) {
            // Checks for which side the piece is
            switch (cp.getSide()) {
                case NORTH:
                case SOUTH:
                    // Checks to see if the move is in the same column
                    if (column != cp.getColumn())
                        return false;

                    // Checks to make sure space in between pieces is empty
                    if (row > cp.getRow()) { // Down movement
                        // Iterates for each square, starting at down and going up
                        for (int i = row - 1; i > cp.getRow(); i--) {
                            // If not a valid non-capture move, return false
                            if (!isValidMove(i, column, cp) || cp.getChessBoard().hasPiece(i, column))
                                return false;
                        }
                    } else { // Up movement
                        // Iterates for each square, starting at up and going down
                        for (int i = row + 1; i < cp.getRow(); i++) {
                            // If not a valid non-capture move, return false
                            if (!isValidMove(i, column, cp) || cp.getChessBoard().hasPiece(i, column))
                                return false;
                        }
                    }
                    break;
                default: // EAST and WEST
                    // Checks to see if the move is in the same row
                    if (row != cp.getRow())
                        return false;

                    // Checks to make sure space in between pieces is empty
                    if (column > cp.getColumn()) { // Right movement
                        // Iterates for each square, starting at right and going left
                        for (int i = column - 1; i > cp.getColumn(); i--) {
                            // If not a valid non-capture move, return false
                            if (!isValidMove(row, i, cp) || cp.getChessBoard().hasPiece(row, i))
                                return false;
                        }
                    } else { // Left movement
                        // Iterates for each square, starting at left and going right
                        for (int i = column + 1; i < cp.getColumn(); i++) {
                            //If not a valid non-capture move, return false
                            if (!isValidMove(row, i, cp) || cp.getChessBoard().hasPiece(row, i))
                                return false;
                        }
                    }
            }

            return true; // Check complete
        } else
            return false; // Not a valid facing king move
    }
}
