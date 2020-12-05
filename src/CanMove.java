/**
 * <p>Represents the ability to make a move in chess.</p>
 *
 * @author Nobel Zhou (nxz157)
 * @version 1.0, 12/2/20
 */
public interface CanMove {
    /**
     * <p>Returns a boolean representing if the proposed row and column is a valid move from the piece's location.</p>
     * <p>Checks to make sure that the move is within the chessboard limits.</p>
     *
     * @param row       the piece's destination row
     * @param column    the piece's destination column
     * @param cp        the chess piece
     * @return          <code>true</code> if the proposed location is a valid move, assuming it is unoccupied
     * @since 1.0
     */
    default boolean isValidMove(int row, int column, ChessPiece cp) {
        return row < cp.getChessBoard().getGameRules().getNumRows() &&
                column < cp.getChessBoard().getGameRules().getNumColumns() &&
                row >= 0 && column >= 0 && (row != cp.getRow() || column != cp.getColumn());
    }
}