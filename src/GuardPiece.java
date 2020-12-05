/**
 * <p>Represents a guard chess piece.</p>
 * <p>Dictates how a guard can move.</p>
 *
 * @author Nobel Zhou
 * @version 1.0, 12/4/20
 */
public class GuardPiece extends ChessPiece implements CanSingleDiagonalMove, CanPalaceMove {

    //region CONSTRUCTORS
    /**
     * <p>Initializes a guard piece based on the given side, chess board, and location.</p>
     *
     * @param side          the guard piece's side
     * @param chessBoard    the chess board the guard piece is on
     * @param icon          the guard piece's icon
     * @param row           the guard piece's starting row
     * @param column        the guard piece's starting column
     * @since 1.0
     */
    public GuardPiece(ChessGame.Side side, ChessBoard chessBoard, ChessIcon icon, int row, int column) {
        super(side, "G", icon, chessBoard, row, column);
    }
    //endregion

    //region METHODS
    /**
     * <p>Returns a boolean representing if the proposed move is legal, assuming that it is unoccupied.</p>
     *
     * @param row       the guard piece's destination row
     * @param column    the guard piece's destination column
     * @return          <code>true</code> if the guard piece's proposed move is legal, assuming it is unoccupied
     * @since 1.0
     */
    @Override
    public boolean isLegalNonCaptureMove(int row, int column) {
        return isValidSingleDiagonalMove(row, column, this) && isValidPalaceMove(row, column, this);
    }

    /**
     * <p>Returns a boolean representing if the proposed move is legal, assuming that it is occupied.</p>
     *
     * @param row       the guard piece's destination row
     * @param column    the guard piece's destination column
     * @return          <code>true</code> if the guard piece's proposed move is legal, assuming it is occupied
     * @since 1.0
     */
    @Override
    public boolean isLegalCaptureMove(int row, int column) {
        return isLegalNonCaptureMove(row, column);
    }
    //endregion
}
