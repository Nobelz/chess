/**
 * Represents the ability to make an L move in chess.
 *
 * @author Nobel Zhou (nxz157)
 * @version 1.0, 10/30/20
 */
public interface LMove extends NormalMove, CheckMove {
    /**
     * Returns a boolean representing if the proposed row and column is a valid L move from the piece's location, assuming that the proposed location is unoccupied.
     * @param row       The piece's destination row
     * @param column    The piece's destination column
     * @param cp        The chess piece
     * @return          Whether the proposed location is a valid L move, assuming it is unoccupied
     * @since 1.0
     */
    public default boolean isValidLNonCaptureMove(int row, int column, ChessPiece cp) {
        return isValidNonCaptureMove(row, column, cp) && ((row == cp.getRow() + 2 && (column == cp.getColumn() - 1 || column == cp.getColumn() + 1)) || (row == cp.getRow() - 2 && (column == cp.getColumn() - 1 || column == cp.getColumn() + 1)) || (row == cp.getRow() + 1 && (column == cp.getColumn() - 2 || column == cp.getColumn() + 2)) || (row == cp.getRow() - 1 && (column == cp.getColumn() - 2 || column == cp.getColumn() + 2)));
    }
    
    /**
     * Returns a boolean representing if the proposed row and column is a valid L move from the piece's location, assuming that the proposed location is occupied.
     * @param row       The piece's destination row
     * @param column    The piece's destination column
     * @param cp        The chess piece
     * @return          Whether the proposed location is a valid L move, assuming it is occupied
     * @since 1.0
     */
    public default boolean isValidLCaptureMove(int row, int column, ChessPiece cp) {
        return isValidCaptureMove(row, column, cp) && ((row == cp.getRow() + 2 && (column == cp.getColumn() - 1 || column == cp.getColumn() + 1)) || (row == cp.getRow() - 2 && (column == cp.getColumn() - 1 || column == cp.getColumn() + 1)) || (row == cp.getRow() + 1 && (column == cp.getColumn() - 2 || column == cp.getColumn() + 2)) || (row == cp.getRow() - 1 && (column == cp.getColumn() - 2 || column == cp.getColumn() + 2)));
    }
}
