/**
 * <p>Represents a cannon chess piece.</p>
 * <p>Dictates how a cannon can move.</p>
 *
 * @author Nobel Zhou
 * @version 1.0, 12/4/20
 */
public class CannonPiece extends ChessPiece implements CanCannonMove, CanStraightMove {

    //region CONSTRUCTORS
    /**
     * <p>Initializes a cannon piece based on the given side, chess board, and location.</p>
     *
     * @param side          the cannon piece's side
     * @param chessBoard    the chess board the cannon piece is on
     * @param icon          the cannon piece's icon
     * @param row           the cannon piece's starting row
     * @param column        the cannon piece's starting column
     * @since 1.0
     */
    public CannonPiece(ChessGame.Side side, ChessBoard chessBoard, ChessIcon icon, int row, int column) {
        super(side, "C", icon, chessBoard, row, column);
    }
    //endregion

    //region METHODS
    /**
     * <p>Returns a boolean representing if the proposed move is legal, assuming that it is unoccupied.</p>
     *
     * @param row       the cannon piece's destination row
     * @param column    the cannon piece's destination column
     * @return          <code>true</code> if the cannon piece's proposed move is legal, assuming it is unoccupied
     * @since 1.0
     */
    @Override
    public boolean isLegalNonCaptureMove(int row, int column) {
        return isValidStraightMove(row, column, this);
    }

    /**
     * <p>Returns a boolean representing if the proposed move is legal, assuming that it is occupied.</p>
     *
     * @param row       the cannon piece's destination row
     * @param column    the cannon piece's destination column
     * @return          <code>true</code> if the cannon piece's proposed move is legal, assuming it is occupied
     * @since 1.0
     */
    @Override
    public boolean isLegalCaptureMove(int row, int column) {
        return isValidCannonMove(row, column, this);
    }
    //endregion
}
