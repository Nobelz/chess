/**
 * Represents a king chess piece.
 * Dictates how a king can move.
 *
 * @author Nobel Zhou
 * @version 1.0, 10/30/20
 */
public class KingPiece extends ChessPiece implements KingMove, CastlingMove {

    /* CONSTRUCTORS */
    /**
     * Initializes a king piece based on the given side, chess board, and location.
     * @param side          The king piece's side
     * @param chessBoard    The chess board the king piece is on
     * @param row           The king piece's starting row
     * @param column        The king piece's starting column
     */
    public KingPiece(ChessGame.Side side, ChessBoard chessBoard, int row, int column) {
        super(side, "K", (side.equals(((EuropeanChess) chessBoard.getGameRules()).getStartingSide()) ? ChessIcon.WHITE_KING : ChessIcon.BLACK_KING), chessBoard, row, column);
    }
    
    /* METHODS */
    /**
     * Returns a boolean representing if the king piece's proposed move is legal or not.
     * @param toRow     The king piece's destination row
     * @param toColumn  The king piece's destination column
     * @return          If the king piece's proposed move is legal or not
     * @since 1.0
     */
    @Override
    public boolean isLegalMove(int toRow, int toColumn) {
        //Checks also if it's a legal castling move
        return super.isLegalMove(toRow, toColumn) || isValidCastlingMove(toRow, toColumn, this);
    }
    
    /**
     * Returns a boolean representing if the proposed move is legal, assuming that it is unoccupied.
     * @param row       The king piece's destination row
     * @param column    The king piece's destination column
     * @return          If the king piece's proposed move is legal or not, assuming it is unoccupied
     * @since 1.0
     */
    @Override
    public boolean isLegalNonCaptureMove(int row, int column) {
        //Also makes sure that the king doesn't move into check
        return isValidKingNonCaptureMove(row, column, this);
    }
    
    /**
     * Returns a boolean representing if the proposed move is legal, assuming that it is occupied.
     * @param row       The king piece's destination row
     * @param column    The king piece's destination column
     * @return          If the king piece's proposed move is legal or not, assuming it is occupied
     * @since 1.0
     */
    @Override
    public boolean isLegalCaptureMove(int row, int column) {
        //Also makes sure that the king doesn't move into check
        return isValidKingCaptureMove(row, column, this);
    }
    
    /**
     * Returns a boolean representing if the king piece is currently in check.
     * @return  If the king piece is currently in check
     * @since 1.0
     */
    public boolean isInCheck() {
        return getChessBoard().squareThreatened(getRow(), getColumn(), this);
    }
    
    /**
     * Returns a KingPiece representing the opposing king.
     * @return  The opposing king
     * @since 1.0
     */
    public KingPiece getOpposingKing() {
        //Stores the king piece of the opposite side; placeholder needed
        KingPiece king = new KingPiece(getSide(), getChessBoard(), -1, -1); //This will never return because we always know that a king with the opposite side will be present
        
        //Iterates the chess board to look for the opposite side king piece
        for (int i = 0; i < getChessBoard().numRows(); i++) {
            for (int j = 0; j < getChessBoard().numColumns(); j++) {
                //Looks for opposite side king piece
                if (getChessBoard().hasPiece(i, j) && getChessBoard().getPiece(i, j) instanceof KingPiece && !getChessBoard().getPiece(i, j).getSide().equals(getSide()))
                    king = (KingPiece) getChessBoard().getPiece(i,j);
            }
        }
          
        return king;
    }
}
