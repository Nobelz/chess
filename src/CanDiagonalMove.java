/**
 * <code>Represents the ability to make a diagonal move in chess.</code>
 *
 * @author Nobel Zhou (nxz157)
 * @version 1.0, 12/2/20
 */
public interface CanDiagonalMove extends CanMove {
    /**
     * <p>Returns a boolean representing if the proposed row and column is a valid diagonal move from the piece's location.</p>
     *
     * @param row       the piece's destination row
     * @param column    the piece's destination column
     * @param cp        the chess piece
     * @return          <code>true</code> if the proposed location is a valid diagonal move
     * @since 1.0
     */
    default boolean isValidDiagonalMove(int row, int column, ChessPiece cp) {
        // Checks to make sure that it's a valid square first and that is a diagonal square
        if (isValidMove(row, column, cp) && (row - cp.getRow() == column - cp.getColumn() || (row - cp.getRow() == (-1 * (column - cp.getColumn()))))) {
            // Moves row and column 1 space closer to piece
            row = (row > cp.getRow()) ? row - 1 : row + 1;
            column = (column > cp.getColumn()) ? column - 1 : column + 1;

            // Checks if the move won't result in check and that all of the squares up to it are empty
            // Iterates for each square on the diagonal
            for (int i = 0; i < Math.abs(cp.getColumn() - column); i++) {
                if (row > cp.getRow()) { // South movement
                    if (column > cp.getColumn()) { // Southeast movement
                        if (!isValidMove(row - i, column - i, cp) || cp.getChessBoard().hasPiece(row - i, column - i))
                            return false;
                    } else { // Southwest movement
                        if (!isValidMove(row - i, column + i, cp) || cp.getChessBoard().hasPiece(row - i, column + i))
                            return false;
                    }
                } else { // North movement
                    if (column > cp.getColumn()) { // Northeast movement
                        if (!isValidMove(row + i, column - i, cp) || cp.getChessBoard().hasPiece(row + i, column - i))
                            return false;
                    } else { // Northwest movement
                        if (!isValidMove(row + i, column + i, cp) || cp.getChessBoard().hasPiece(row + i, column + i))
                            return false;
                    }
                }
            }
            return true;
        } else
            return false; // Not a valid diagonal move
    }
}
