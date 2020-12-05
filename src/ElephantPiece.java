/**
 * <p>Represents an elephant chess piece.</p>
 * <p>Dictates how an elephant can move.</p>
 *
 * @author Nobel Zhou
 * @version 1.0, 12/4/20
 */
public class ElephantPiece extends ChessPiece implements CanElephantMove {

    //region CONSTRUCTORS
    /**
     * <p>Initializes an elephant piece based on the given side, chess board, and location.</p>
     *
     * @param side          the elephant piece's side
     * @param chessBoard    the chess board the elephant piece is on
     * @param icon          the elephant piece's icon
     * @param row           the elephant piece's starting row
     * @param column        the elephant piece's starting column
     * @since 1.0
     */
    public ElephantPiece(ChessGame.Side side, ChessBoard chessBoard, ChessIcon icon, int row, int column) {
        super(side, "E", icon, chessBoard, row, column);
    }
    //endregion

    //region METHODS
    /**
     * <p>Returns a boolean representing if the proposed move is legal, assuming that it is unoccupied.</p>
     *
     * @param row       the elephant piece's destination row
     * @param column    the elephant piece's destination column
     * @return          <code>true</code> if the elephant piece's proposed move is legal, assuming it is unoccupied
     * @since 1.0
     */
    @Override
    public boolean isLegalNonCaptureMove(int row, int column) {
        return isValidElephantMove(row, column, this);
    }

    /**
     * <p>Returns a boolean representing if the proposed move is legal, assuming that it is occupied.</p>
     *
     * @param row       the elephant piece's destination row
     * @param column    the elephant piece's destination column
     * @return          <code>true</code> if the elephant piece's proposed move is legal, assuming it is occupied
     * @since 1.0
     */
    @Override
    public boolean isLegalCaptureMove(int row, int column) {
        return isLegalNonCaptureMove(row, column);
    }
    //endregion
}
