/**
 * Represents a knight chess piece.
 * Dictates how a knight can move.
 *
 * @author Nobel Zhou
 * @version 1.0, 10/30/20
 */
public class KnightPiece extends ChessPiece implements LMove, Promotable {

    /* CONSTRUCTORS */
    /**
     * Initializes a knight piece based on the given side, chess board, and location.
     * @param side          The knight piece's side
     * @param chessBoard    The chess board the knight piece is on
     * @param row           The knight piece's starting row
     * @param column        The knight piece's starting column
     */
    public KnightPiece(ChessGame.Side side, ChessBoard chessBoard, int row, int column) {
        super(side, "N", ChessIcon.KNIGHT, chessBoard, row, column);
    }
    
    /* METHODS */
    /**
     * Returns a boolean representing if the proposed move is legal, assuming that it is unoccupied.
     * @param row       The knight piece's destination row
     * @param column    The knight piece's destination column
     * @return          If the knight piece's proposed move is legal or not, assuming it is unoccupied
     * @since 1.0
     */
    @Override
    public boolean isLegalNonCaptureMove(int row, int column) {
        return isValidLNonCaptureMove(row, column, this);
    }
    
    /**
     * Returns a boolean representing if the proposed move is legal, assuming that it is occupied.
     * @param row       The knight piece's destination row
     * @param column    The knight piece's destination column
     * @return          If the knight piece's proposed move is legal or not, assuming it is occupied
     * @since 1.0
     */
    @Override
    public boolean isLegalCaptureMove(int row, int column) {
        return isValidLCaptureMove(row, column, this);
    }
}
