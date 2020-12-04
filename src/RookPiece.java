/**
 * <p>Represents a rook chess piece.</p>
 * <p>Dictates how a rook can move.
 *
 * @author Nobel Zhou (nxz157)
 * @version 1.0, 12/2/20
 */
public class RookPiece extends ChessPiece implements CanStraightMove, Promotable {

    //region CONSTRUCTORS
    /**
     * <p>Initializes a rook piece based on the given side, chess board, and location.</p>
     *
     * @param side          the rook piece's side
     * @param chessBoard    the chess board the rook piece is on
     * @param icon          the rook piece's icon
     * @param row           the rook piece's starting row
     * @param column        the rook piece's starting column
     * @since 1.0
     */
    public RookPiece(ChessGame.Side side, ChessBoard chessBoard, ChessIcon icon, int row, int column) {
        super(side, "R", icon, chessBoard, row, column);
    }
    //endregion

    //region METHODS
    /**
     * <p>Returns a boolean representing if the proposed move is legal, assuming that it is unoccupied.</p>
     *
     * @param row       the rook piece's destination row
     * @param column    the rook piece's destination column
     * @return          <code>true</code> if the rook piece's proposed move is legal, assuming it is unoccupied
     * @since 1.0
     */
    @Override
    public boolean isLegalNonCaptureMove(int row, int column) {
        return isValidStraightMove(row, column, this);
    }

    /**
     * <p>Returns a boolean representing if the proposed move is legal, assuming that it is occupied.</p>
     *
     * @param row       the rook piece's destination row
     * @param column    the rook piece's destination column
     * @return          <code>true</code> if the rook piece's proposed move is legal, assuming it is occupied
     * @since 1.0
     */
    @Override
    public boolean isLegalCaptureMove(int row, int column) {
        return isValidStraightMove(row, column, this);
    }
    //endregion
}