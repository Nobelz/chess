/**
 * Represents a bishop chess piece.
 * Dictates how a bishop can move.
 *
 * @author Nobel Zhou
 * @version 1.0, 10/30/20
 */
public class BishopPiece extends ChessPiece implements DiagonalMove, Promotable {

    /* CONSTRUCTORS */
    /**
     * Initializes a bishop piece based on the given side, chess board, and location.
     * @param side          The bishop piece's side
     * @param chessBoard    The chess board the bishop piece is on
     * @param row           The bishop piece's starting row
     * @param column        The bishop piece's starting column
     */
    public BishopPiece(ChessGame.Side side, ChessBoard chessBoard, int row, int column) {
        super(side, "B", (side.equals(((EuropeanChess) chessBoard.getGameRules()).getStartingSide()) ? ChessIcon.WHITE_BISHOP : ChessIcon.BLACK_BISHOP), chessBoard, row, column);
    }
    
    /* METHODS */
    /**
     * Returns a boolean representing if the proposed move is legal, assuming that it is unoccupied.
     * @param row       The bishop piece's destination row
     * @param column    The bishop piece's destination column
     * @return          If the bishop piece's proposed move is legal or not, assuming it is unoccupied
     * @since 1.0
     */
    @Override
    public boolean isLegalNonCaptureMove(int row, int column) {
        return isValidDiagonalNonCaptureMove(row, column, this);
    }
    
    /**
     * Returns a boolean representing if the proposed move is legal, assuming that it is occupied.
     * @param row       The bishop piece's destination row
     * @param column    The bishop piece's destination column
     * @return          If the bishop piece's proposed move is legal or not, assuming it is occupied
     * @since 1.0
     */
    @Override
    public boolean isLegalCaptureMove(int row, int column) {
        return isValidDiagonalCaptureMove(row, column, this);
    }
    
    /**
     * Returns a boolean representing if the bishop piece is dark squared or not.
     * @return          If the bishop piece is dark squared or not
     * @since 1.0
     */
    public boolean isDarkSquared() {
        return getRow() % 2 == 0;
    }
}
