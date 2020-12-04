/**
 * <p>Represents a knight chess piece.</p>
 * <p>Dictates how a knight can move.</p>
 *
 * @author Nobel Zhou
 * @version 1.0, 10/30/20
 */
public class KnightPiece extends ChessPiece implements CanLMove, Promotable {

    //region CONSTRUCTORS
    /**
     * <p>Initializes a knight piece based on the given side, chess board, and location.</p>
     *
     * @param side          the knight piece's side
     * @param chessBoard    the chess board the knight piece is on
     * @param icon          the knight piece's icon
     * @param row           the knight piece's starting row
     * @param column        the knight piece's starting column
     * @since 1.0
     */
    public KnightPiece(ChessGame.Side side, ChessBoard chessBoard, ChessIcon icon, int row, int column) {
        super(side, "N", icon, chessBoard, row, column);
    }
    //endregion

    //region METHODS
    /**
     * <p>Returns a boolean representing if the proposed move is legal, assuming that it is unoccupied.</p>
     *
     * @param row       the knight piece's destination row
     * @param column    the knight piece's destination column
     * @return          <code>true</code> if the knight piece's proposed move is legal, assuming it is unoccupied
     * @since 1.0
     */
    @Override
    public boolean isLegalNonCaptureMove(int row, int column) {
        return isValidLMove(row, column, this);
    }

    /**
     * <p>Returns a boolean representing if the proposed move is legal, assuming that it is occupied.</p>
     *
     * @param row       the knight piece's destination row
     * @param column    the knight piece's destination column
     * @return          <code>true</code> if the knight piece's proposed move is legal, assuming it is occupied
     * @since 1.0
     */
    @Override
    public boolean isLegalCaptureMove(int row, int column) {
        return isValidLMove(row, column, this);
    }
    //endregion
}
