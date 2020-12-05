/**
 * <p>Represents a horse chess piece.</p>
 * <p>Dictates how a horse can move.</p>
 *
 * @author Nobel Zhou
 * @version 1.0, 12/4/20
 */
public class HorsePiece extends ChessPiece implements CanHorseMove {

    //region CONSTRUCTORS
    /**
     * <p>Initializes a horse piece based on the given side, chess board, and location.</p>
     *
     * @param side          the horse piece's side
     * @param chessBoard    the chess board the horse piece is on
     * @param icon          the horse piece's icon
     * @param row           the horse piece's starting row
     * @param column        the horse piece's starting column
     * @since 1.0
     */
    public HorsePiece(ChessGame.Side side, ChessBoard chessBoard, ChessIcon icon, int row, int column) {
        super(side, "H", icon, chessBoard, row, column);
    }
    //endregion

    //region METHODS
    /**
     * <p>Returns a boolean representing if the proposed move is legal, assuming that it is unoccupied.</p>
     *
     * @param row       the horse piece's destination row
     * @param column    the horse piece's destination column
     * @return          <code>true</code> if the horse piece's proposed move is legal, assuming it is unoccupied
     * @since 1.0
     */
    @Override
    public boolean isLegalNonCaptureMove(int row, int column) {
        return isValidHorseMove(row, column, this);
    }

    /**
     * <p>Returns a boolean representing if the proposed move is legal, assuming that it is occupied.</p>
     *
     * @param row       the horse piece's destination row
     * @param column    the horse piece's destination column
     * @return          <code>true</code> if the horse piece's proposed move is legal, assuming it is occupied
     * @since 1.0
     */
    @Override
    public boolean isLegalCaptureMove(int row, int column) {
        return isLegalNonCaptureMove(row, column);
    }
    //endregion
}
