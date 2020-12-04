/**
 * <p>Represents a bishop chess piece.</p>
 * <p>Dictates how a bishop can move.</p>
 *
 * @author Nobel Zhou (nxz157)
 * @version 1.0, 12/2/20
 */
public class BishopPiece extends ChessPiece implements CanDiagonalMove, Promotable {

    //region CONSTRUCTORS
    /**
     * <p>Initializes a bishop piece based on the given side, chess board, and location.</p>
     *
     * @param side          the bishop piece's side
     * @param chessBoard    the chess board the bishop piece is on
     * @param icon          the bishop piece's icon
     * @param row           the bishop piece's starting row
     * @param column        the bishop piece's starting column
     * @since 1.0
     */
    public BishopPiece(ChessGame.Side side, ChessBoard chessBoard, ChessIcon icon, int row, int column) {
        super(side, "B", icon, chessBoard, row, column);
    }
    //endregion

    //region METHODS
    /**
     * <p>Returns a boolean representing if the proposed move is legal, assuming that it is unoccupied.</p>
     * @param row       the bishop piece's destination row
     * @param column    the bishop piece's destination column
     * @return          <code>true</code> if the bishop piece's proposed move is legal, assuming it is unoccupied
     * @since 1.0
     */
    @Override
    public boolean isLegalNonCaptureMove(int row, int column) {
        return isValidDiagonalMove(row, column, this);
    }

    /**
     * <p>Returns a boolean representing if the proposed move is legal, assuming that it is occupied.</p>
     * @param row       the bishop piece's destination row
     * @param column    the bishop piece's destination column
     * @return          <code>true</code> if the bishop piece's proposed move is legal, assuming it is occupied
     * @since 1.0
     */
    @Override
    public boolean isLegalCaptureMove(int row, int column) {
        return isValidDiagonalMove(row, column, this);
    }
    
    /**
     * <p>Returns a boolean representing if the bishop piece is dark squared or not.</p>
     * @return  <code>true</code> if the bishop piece is dark squared
     * @since 1.0
     */
    public boolean isDarkSquared() {
        return (getRow() + getColumn()) % 2 == 0;
    }
    //endregion
}
