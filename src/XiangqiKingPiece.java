/**
 * <p>Represents a xiangqi king chess piece.</p>
 * <p>Dictates how a xiangqi king can move.</p>
 *
 * @author Nobel Zhou
 * @version 1.0, 12/2/20
 */
public class XiangqiKingPiece extends ChessPiece implements CenterPiece, CanSingleStraightMove, CanPalaceMove {
    //region CONSTRUCTORS
    /**
     * <p>Initializes a king piece based on the given side, chess board, and location.</p>
     *
     * @param side          the king piece's side
     * @param chessBoard    the chess board the king piece is on
     * @param icon          the king piece's icon
     * @param row           the king piece's starting row
     * @param column        the king piece's starting column
     * @since 1.0
     */
    public XiangqiKingPiece(ChessGame.Side side, ChessBoard chessBoard, ChessIcon icon, int row, int column) {
        super(side, "X", icon, chessBoard, row, column);
    }
    //endregion

    //region METHODS
    /**
     * <p>Returns a boolean representing if the proposed move is legal, assuming that it is unoccupied.</p>
     *
     * @param row       the xiangqi king piece's destination row
     * @param column    the xiangqi king piece's destination column
     * @return          <code>true</code> if the xiangqi king piece's proposed move is legal, assuming it is unoccupied
     * @since 1.0
     */
    @Override
    public boolean isLegalNonCaptureMove(int row, int column) {
        return isValidSingleStraightMove(row, column, this) && isValidPalaceMove(row, column, this);
    }

    /**
     * <p>Returns a boolean representing if the proposed move is legal, assuming that it is occupied.</p>
     *
     * @param row       the xiangqi king piece's destination row
     * @param column    the xiangqi king piece's destination column
     * @return          <code>true</code> if the xiangqi king piece's proposed move is legal, assuming it is occupied
     * @since 1.0
     */
    @Override
    public boolean isLegalCaptureMove(int row, int column) {
        return isLegalNonCaptureMove(row, column);
    }

    /**
     * <p>Returns a boolean representing if the xiangqi king piece is currently in check.</p>
     *
     * @return <code>true</code> if the xiangqi king piece is currently in check
     * @since 1.0
     */
    @Override
    public boolean isInCheck() {
        return getChessBoard().squareThreatened(getRow(), getColumn(), this);
    }
    //endregion
}
