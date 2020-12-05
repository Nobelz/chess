/**
 * <p>Represents the ability to make a cannon move in chess.</p>
 *
 * @author Nobel Zhou (nxz157)
 * @version 1.0, 12/4/20
 */
public interface CanCannonMove extends CanMove {
    /**
     * <p>Returns a boolean representing if the proposed row and column is a valid cannon
     * move from the piece's location.</p>
     *
     * @param row       the piece's destination row
     * @param column    the piece's destination column
     * @param cp        the chess piece
     * @return          <code>true</code> if the proposed location is a valid cannon move
     * @since 1.0
     */
    default boolean isValidCannonMove(int row, int column, ChessPiece cp) {
        // Checks to make sure that it's a valid square first and that is a horizontal or vertical square
        if (isValidMove(row, column, cp) && (row == cp.getRow() || column == cp.getColumn())) {
            // Stores the number of pieces between the proposed move and the chess piece's current location
            int numOfPiecesBetween = 0;

            // Moves row or column 1 space closer to piece
            row = (row == cp.getRow()) ? row : (row > cp.getRow()) ? row - 1 : row + 1;
            column = (column == cp.getColumn()) ? column : (column > cp.getColumn()) ? column - 1 : column + 1;

            // Checks that all of the squares up to it except one are empty
            if (row == cp.getRow()) { // Horizontal movement
                if (column > cp.getColumn()) { // Right movement
                    // Iterates for each square, starting at right and going left
                    for (int i = column; i > cp.getColumn(); i--) {
                        // Checks that it is a valid move
                        if (!isValidMove(row, i, cp))
                            return false;
                        // If there is a piece, add one to pieces in between count
                        else if (cp.getChessBoard().hasPiece(row, i))
                            numOfPiecesBetween++;
                    }
                } else { // Left movement
                    // Iterates for each square, starting at left and going right
                    for (int i = column; i < cp.getColumn(); i++) {
                        // Checks that it is a valid move
                        if (!isValidMove(row, i, cp))
                            return false;
                            // If there is a piece, add one to pieces in between count
                        else if (cp.getChessBoard().hasPiece(row, i))
                            numOfPiecesBetween++;
                    }
                }
            } else { // Vertical movement
                if (row > cp.getRow()) { // Down movement
                    // Iterates for each square, starting at down and going up
                    for (int i = row; i > cp.getRow(); i--) {
                        // Checks that it is a valid move
                        if (!isValidMove(i, column, cp))
                            return false;
                            // If there is a piece, add one to pieces in between count
                        else if (cp.getChessBoard().hasPiece(i, column))
                            numOfPiecesBetween++;
                    }
                } else { // Up movement
                    // Iterates for each square, starting at up and going down
                    for (int i = row; i < cp.getRow(); i++) {
                        // Checks that it is a valid move
                        if (!isValidMove(i, column, cp))
                            return false;
                            // If there is a piece, add one to pieces in between count
                        else if (cp.getChessBoard().hasPiece(i, column))
                            numOfPiecesBetween++;
                    }
                }
            }

            // Checks to make sure only 1 piece is in between proposed move and chess piece
            return numOfPiecesBetween == 1;
        } else
            return false; // Not a valid diagonal move
    }
}
