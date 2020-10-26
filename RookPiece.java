/**
 * Represents a rook chess piece.
 * Dictates how a rook can move.
 *
 * @author Nobel Zhou
 * @version 1.0, 10/30/20
 */
public class RookPiece extends ChessPiece implements StraightMove, Promotable {

    /* CONSTRUCTORS */
    /**
     * Initializes a rook piece based on the given side, chess board, and location.
     * @param side          The rook piece's side
     * @param chessBoard    The chess board the rook piece is on
     * @param row           The rook piece's starting row
     * @param column        The rook piece's starting column
     */
    public RookPiece(ChessGame.Side side, ChessBoard chessBoard, int row, int column) {
        super(side, "R", ChessIcon.ROOK, chessBoard, row, column);
    }
    
    /* METHODS */
    /**
     * Returns a boolean representing if the proposed move is legal, assuming that it is unoccupied.
     * @param row       The rook piece's destination row
     * @param column    The rook piece's destination column
     * @return          If the rook piece's proposed move is legal or not, assuming it is unoccupied
     * @since 1.0
     */
    @Override
    public boolean isLegalNonCaptureMove(int row, int column) {
        return isValidStraightNonCaptureMove(row, column, this);
    }
    
    /**
     * Returns a boolean representing if the proposed move is legal, assuming that it is occupied.
     * @param row       The rook piece's destination row
     * @param column    The rook piece's destination column
     * @return          If the rook piece's proposed move is legal or not, assuming it is occupied
     * @since 1.0
     */
    @Override
    public boolean isLegalCaptureMove(int row, int column) {
        return isValidStraightCaptureMove(row, column, this);
    }
}
