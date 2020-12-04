package development;

/**
 * <p>Represents the ability to make a pawn move in chess.</p>
 * <p>A pawn move is a move of one space (or two spaces if the pawn hasn't moved yet) in the forward direction.</p>
 * <p>A pawn can also only capture diagonally and cannot capture by going forward.</p>
 *
 * @author Nobel Zhou (nxz157)
 * @version 1.0, 12/2/20
 */
public interface CanPawnMove extends CanMove {
    /**
     * <p>Returns a boolean representing if the proposed row and column is a valid pawn non-capture move from the piece's location,
     * assuming that the proposed location is unoccupied.</p>
     *
     * @param row       the piece's destination row
     * @param column    the piece's destination column
     * @param cp        the chess piece
     * @return          <code>true</code> if the proposed location is a valid pawn non-capture move, assuming it is unoccupied
     * @since 1.0
     */
    default boolean isValidPawnNonCaptureMove(int row, int column, ChessPiece cp) {
        // Checks if the square is empty
        if (isValidMove(row, column, cp) && !cp.getChessBoard().hasPiece(row, column)) {
            // Checks the forward 1 space for each side
            switch (cp.getSide()) {
                case NORTH:
                    if (row == cp.getRow() + 1 && column == cp.getColumn())
                        return true;
                    break;
                case SOUTH:
                    if (row == cp.getRow() - 1 && column == cp.getColumn())
                        return true;
                    break;
                case WEST:
                    if (column == cp.getColumn() + 1 && row == cp.getRow())
                        return true;
                    break;
                default: // East
                    if (column == cp.getColumn() - 1 && row == cp.getRow())
                        return true;
            }

            // Checks forward 2 spaces for each side if and only if the pawn hasn't been moved yet
            if (cp.getNumMoves() == 0) {
                switch (cp.getSide()) {
                    case NORTH:
                        if (row == cp.getRow() + 2 && column == cp.getColumn())
                            return isValidPawnNonCaptureMove(row - 1, column, cp); // Checks the space before it (closer to the square) to make sure that it is empty
                        break;
                    case SOUTH:
                        if (row == cp.getRow() - 2 && column == cp.getColumn())
                            return isValidPawnNonCaptureMove(row + 1, column, cp); // Checks the space before it (closer to the square) to make sure that it is empty
                        break;
                    case WEST:
                        if (column == cp.getColumn() + 2 && row == cp.getRow())
                            return isValidPawnNonCaptureMove(row, column - 1, cp); // Checks the space before it (closer to the square) to make sure that it is empty
                        break;
                    default: // East
                        if (column == cp.getColumn() - 2 && row == cp.getRow())
                            return isValidPawnNonCaptureMove(row, column + 1, cp); // Checks the space before it (closer to the square) to make sure that it is empty
                }
            }
        }
        return false; // Doesn't match any pawn move
    }

    /**
     * <p>Returns a boolean representing if the proposed row and column is a valid pawn capture move from the piece's location,
     * assuming that the proposed location is occupied.</p>
     *
     * @param row       the piece's destination row
     * @param column    the piece's destination column
     * @param cp        the chess piece
     * @return          <code>true</code> if the proposed location is a valid pawn capture move, assuming it is occupied
     * @since 1.0
     */
    default boolean isValidPawnCaptureMove(int row, int column, ChessPiece cp) {
        // Checks if the square is occupied by opposing side
        if (isValidMove(row, column, cp)) {
            // Checks to see if the proposed move is diagonally from the pawn in the forward direction
            switch (cp.getSide()) {
                case NORTH:
                    return row == cp.getRow() + 1 && (column == cp.getColumn() + 1 || column == cp.getColumn() - 1);
                case SOUTH:
                    return row == cp.getRow() - 1 && (column == cp.getColumn() + 1 || column == cp.getColumn() - 1);
                case WEST:
                    return column == cp.getColumn() + 1 && (row == cp.getRow() + 1 || row == cp.getRow() - 1);
                default: // East
                    return column == cp.getColumn() - 1 && (row == cp.getRow() + 1 || row == cp.getRow() - 1);
            }
        } else
            return false;
    }
}
