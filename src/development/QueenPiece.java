package development;

/**
 * <p>Represents a queen chess piece.</p>
 * <p>Dictates how a queen can move.</p>
 *
 * @author Nobel Zhou (nxz157)
 * @version 1.0, 12/2/20
 */
public class QueenPiece extends ChessPiece implements CanStraightMove, CanDiagonalMove, Promotable {

    //region CONSTRUCTORS
    /**
     * <p>Initializes a queen piece based on the given side, chess board, and location.</p>
     *
     * @param side          the queen piece's side
     * @param chessBoard    the chess board the queen piece is on
     * @param icon          the queen piece's icon
     * @param row           the queen piece's starting row
     * @param column        the queen piece's starting column
     * @since 1.0
     */
    public QueenPiece(ChessGame.Side side, ChessBoard chessBoard, ChessIcon icon, int row, int column) {
        super(side, "Q", icon, chessBoard, row, column);
    }
    //endregion

    //region METHODS
    /**
     * <p>Returns a boolean representing if the proposed move is legal, assuming that it is unoccupied.</p>
     *
     * @param row       the queen piece's destination row
     * @param column    the queen piece's destination column
     * @return          <code>true</code> if the queen piece's proposed move is legal, assuming it is unoccupied
     * @since 1.0
     */
    @Override
    public boolean isLegalNonCaptureMove(int row, int column) {
        return isValidStraightMove(row, column, this) || isValidDiagonalMove(row, column, this);
    }

    /**
     * <p>Returns a boolean representing if the proposed move is legal, assuming that it is occupied.</p>
     *
     * @param row       the queen piece's destination row
     * @param column    the queen piece's destination column
     * @return          <code>true</code> if the queen piece's proposed move is legal, assuming it is occupied
     * @since 1.0
     */
    @Override
    public boolean isLegalCaptureMove(int row, int column) {
        return isValidStraightMove(row, column, this) || isValidDiagonalMove(row, column, this);
    }
    //endregion
}