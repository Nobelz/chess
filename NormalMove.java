/**
 * Represents the ability to make a capture and non-capture move in chess.
 *
 * @author Nobel Zhou (nxz157)
 * @version 1.0, 10/30/20
 */
public interface NormalMove {
    /**
     * Returns a boolean representing if the proposed row and column is a valid move from the piece's location, assuming that the proposed location is unoccupied.
     * @param row       The piece's destination row
     * @param column    The piece's destination column
     * @param cp        The chess piece
     * @return          Whether the proposed location is a valid move, assuming it is unoccupied
     * @since 1.0
     */
    public default boolean isValidNonCaptureMove(int row, int column, ChessPiece cp) {
        return row < cp.getChessBoard().numRows() && column < cp.getChessBoard().numColumns() && !cp.getChessBoard().hasPiece(row, column);
    }
    
    /**
     * Returns a boolean representing if the proposed row and column is a valid move from the piece's location, assuming that the proposed location is occupied.
     * @param row       The piece's destination row
     * @param column    The piece's destination column
     * @param cp        The chess piece
     * @return          Whether the proposed location is a valid move, assuming it is occupied
     * @since 1.0
     */
    public default boolean isValidCaptureMove(int row, int column, ChessPiece cp) {
        return row < cp.getChessBoard().numRows() && column < cp.getChessBoard().numColumns() && cp.getChessBoard().hasPiece(row, column) && !cp.getChessBoard().getPiece(row, column).getSide().equals(cp.getSide());
    }
}
