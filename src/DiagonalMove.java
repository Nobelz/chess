/**
 * Represents the ability to make a diagonal move in chess.
 *
 * @author Nobel Zhou (nxz157)
 * @version 1.0, 10/30/20
 */
public interface DiagonalMove extends NormalMove {
    /**
     * Returns a boolean representing if the proposed row and column is a valid diagonal move from the piece's location, assuming that the proposed location is unoccupied.
     *
     * @param row    The piece's destination row
     * @param column The piece's destination column
     * @param cp     The chess piece
     * @return Whether the proposed location is a valid diagonal move, assuming it is unoccupied
     * @since 1.0
     */
    default boolean isValidDiagonalNonCaptureMove(int row, int column, ChessPiece cp) {
        if (isValidNonCaptureMove(row, column, cp) && (row - cp.getRow() == column - cp.getColumn() || (row - cp.getRow() == (-1 * (column - cp.getColumn()))))) {
            //Checks if the move won't result in check and that all of the squares up to it are empty
            return checkEmptyDiagonalMove((row > cp.getRow()) ? row - 1 : row + 1, (column > cp.getColumn()) ? column - 1 : column + 1, cp);
        } else
            return false; //Not a valid diagonal move
    }

    /**
     * Returns a boolean representing if the proposed row and column is a valid diagonal move from the piece's location, assuming that the proposed location is occupied.
     *
     * @param row    The piece's destination row
     * @param column The piece's destination column
     * @param cp     The chess piece
     * @return Whether the proposed location is a valid diagonal move, assuming it is unoccupied
     * @since 1.0
     */
    default boolean isValidDiagonalCaptureMove(int row, int column, ChessPiece cp) {
        //Checks to make sure that it's a valid square first and that is a diagonal square
        if (isValidCaptureMove(row, column, cp) && (row - cp.getRow() == column - cp.getColumn() || (row - cp.getRow() == (-1 * (column - cp.getColumn()))))) {
            //Checks if the move won't result in check and that all of the squares up to it are empty
            return checkEmptyDiagonalMove((row > cp.getRow()) ? row - 1 : row + 1, (column > cp.getColumn()) ? column - 1 : column + 1, cp);
        } else
            return false; //Not a valid diagonal square
    }

    /**
     * Returns a boolean representing if the proposed row and column and all the squares up to the piece are empty for a diagonal move.
     * Assumes that row and column is a valid diagonal move from piece.
     *
     * @param row    The piece's destination row
     * @param column The piece's destination column
     * @param cp     The chess piece
     * @return Whether the proposed location and all the squares up to the piece are empty for a diagonal move
     * @since 1.0
     */
    default boolean checkEmptyDiagonalMove(int row, int column, ChessPiece cp) {
        //Iterates for each square on the diagonal
        for (int i = 0; i < Math.abs(cp.getColumn() - column); i++) {
            if (row > cp.getRow()) { //South movement
                if (column > cp.getColumn()) { //Southeast movement
                    if (!isValidNonCaptureMove(row - i, column - i, cp))
                        return false;
                } else {
                    if (!isValidNonCaptureMove(row - i, column + i, cp))
                        return false;
                }
            } else {
                if (column > cp.getColumn()) { //Southeast movement
                    if (!isValidNonCaptureMove(row + i, column - i, cp))
                        return false;
                } else {
                    if (!isValidNonCaptureMove(row + i, column + i, cp))
                        return false;
                }
            }
        }

        return true;
    }
}