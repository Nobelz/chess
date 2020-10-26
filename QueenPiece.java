/**
 * Represents a queen chess piece.
 * Dictates how a queen can move.
 *
 * @author Nobel Zhou
 * @version 1.0, 10/30/20
 */
public class QueenPiece extends ChessPiece implements StraightMove, DiagonalMove, Promotable {

    /* CONSTRUCTORS */
    /**
     * Initializes a queen piece based on the given side, chess board, and location.
     * @param side          The queen piece's side
     * @param chessBoard    The chess board the queen piece is on
     * @param row           The queen piece's starting row
     * @param column        The queen piece's starting column
     */
    public QueenPiece(ChessGame.Side side, ChessBoard chessBoard, int row, int column) {
        super(side, "Q", ChessIcon.QUEEN, chessBoard, row, column);
    }
    
    /* METHODS */
    /**
     * Returns a boolean representing if the proposed move is legal, assuming that it is unoccupied.
     * @param row       The queen piece's destination row
     * @param column    The queen piece's destination column
     * @return          If the queen piece's proposed move is legal or not, assuming it is unoccupied
     * @since 1.0
     */
    @Override
    public boolean isLegalNonCaptureMove(int row, int column) {
        return isValidStraightNonCaptureMove(row, column, this) || isValidDiagonalNonCaptureMove(row, column, this);
    }
    
    /**
     * Returns a boolean representing if the proposed move is legal, assuming that it is occupied.
     * @param row       The queen piece's destination row
     * @param column    The queen piece's destination column
     * @return          If the queen piece's proposed move is legal or not, assuming it is occupied
     * @since 1.0
     */
    @Override
    public boolean isLegalCaptureMove(int row, int column) {
        return isValidStraightCaptureMove(row, column, this) || isValidDiagonalCaptureMove(row, column, this);
    }
}
