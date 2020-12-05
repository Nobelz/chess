/**
 * <p>Represents the ability to make a xiangqi horse move in chess.</p>
 *
 * @author Nobel Zhou (nxz157)
 * @version 1.0, 12/4/20
 */
public interface CanHorseMove extends CanLMove {
    /**
     * <p>Returns a boolean representing if the proposed row and column is a valid elephant
     * move from the piece's location.</p>
     *
     * @param row       the piece's destination row
     * @param column    the piece's destination column
     * @param cp        the chess piece
     * @return          <code>true</code> if the proposed location is a valid elephant move
     * @since 1.0
     */
    default boolean isValidHorseMove(int row, int column, ChessPiece cp) {
        return isValidLMove(row, column, cp) && (Math.abs(row - cp.getRow()) == 2) ?
                ((row - cp.getRow()) < 0) ?
                        cp.getChessBoard().hasPiece(row + 1, cp.getColumn()) : cp.getChessBoard().hasPiece(row - 1, cp.getColumn()) :
                ((column - cp.getColumn()) < 0) ?
                        cp.getChessBoard().hasPiece(cp.getRow(), column + 1) : cp.getChessBoard().hasPiece(cp.getRow(), column - 1);
    }
}
