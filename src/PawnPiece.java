/**
 * Represents a pawn chess piece.
 * Dictates how a pawn can move.
 * Includes en passant rule.
 *
 * @author Nobel Zhou
 * @version 1.0, 10/30/20
 */
public class PawnPiece extends ChessPiece implements PawnMove, EnPassantMove {

    /* FIELDS */
    //Stores if the pawn can be captured by en passant; in other words, it checks if the move was the immediate previous move. Note that this does not check for single or double moves, as that is checked within the EnPassantMove interface
    private boolean canEnPassant;

    /* CONSTRUCTORS */

    /**
     * Initializes a pawn piece based on the given side, chess board, and location.
     *
     * @param side       The pawn piece's side
     * @param chessBoard The chess board the pawn piece is on
     * @param row        The pawn piece's starting row
     * @param column     The pawn piece's starting column
     */
    public PawnPiece(ChessGame.Side side, ChessBoard chessBoard, int row, int column) {
        super(side, "P", (side.equals(((EuropeanChess) chessBoard.getGameRules()).getStartingSide()) ? ChessIcon.WHITE_PAWN : ChessIcon.BLACK_PAWN), chessBoard, row, column);
        canEnPassant = false;
    }

    /* METHODS */

    /**
     * Returns a boolean representing if the pawn piece's proposed move is legal or not.
     *
     * @param toRow    The pawn piece's destination row
     * @param toColumn The pawn piece's destination column
     * @return If the pawn piece's proposed move is legal or not
     * @since 1.0
     */
    @Override
    public boolean isLegalMove(int toRow, int toColumn) {
        //Checks also if it's a legal en passant move
        return super.isLegalMove(toRow, toColumn) || isValidEnPassantMove(toRow, toColumn, this);
    }

    /**
     * Returns a boolean representing if the proposed move is legal, assuming that it is unoccupied.
     *
     * @param row    The pawn piece's destination row
     * @param column The pawn piece's destination column
     * @return If the pawn piece's proposed move is legal or not, assuming it is unoccupied
     * @since 1.0
     */
    @Override
    public boolean isLegalNonCaptureMove(int row, int column) {
        return isValidPawnNonCaptureMove(row, column, this);
    }

    /**
     * Returns a boolean representing if the proposed move is legal, assuming that it is occupied.
     *
     * @param row    The pawn piece's destination row
     * @param column The pawn piece's destination column
     * @return If the pawn piece's proposed move is legal or not, assuming it is occupied
     * @since 1.0
     */
    @Override
    public boolean isLegalCaptureMove(int row, int column) {
        return isValidPawnCaptureMove(row, column, this);
    }

    /**
     * Returns a boolean representing if the pawn can be captured via en passant.
     *
     * @return If the pawn piece can be captured via en passant
     * @since 1.0
     */
    public boolean getCanEnPassant() {
        return canEnPassant;
    }

    /**
     * Sets if the pawn can be captured via en passant.
     *
     * @param canEnPassant If the pawn piece can now be captured by en passant.
     * @since 1.0
     */
    public void setCanEnPassant(boolean canEnPassant) {
        this.canEnPassant = canEnPassant;
    }


    /**
     * Handles any post-move processes, if any, once the pawn's move is completed.
     * Allows en passant after 1st move. Note that this also includes 1 and 2 space moves, but that is checked elsewhere.
     *
     * @since 1.0
     */
    @Override
    public void moveDone() {
        super.moveDone();
        if (getMoves() == 1)
            setCanEnPassant(true);
        if (checkPawnPromotion())
            getChessBoard().invokePromotion(this);
    }

    /**
     * Checks to see if the pawn is eligible to be promoted.
     * If so, it calls the promote() method in ChessBoard to handle promotions.
     *
     * @return If the pawn is eligible to be promoted.
     * @since 1.0
     */
    public boolean checkPawnPromotion() {
        switch (getSide()) {
            case SOUTH:
                if (getRow() == 0)
                    return true;
                break;
            case NORTH:
                if (getRow() == getChessBoard().numRows() - 1)
                    return true;
                break;
            case WEST:
                if (getColumn() == getChessBoard().numColumns() - 1)
                    return true;
                break;
            default: //East
                if (getColumn() == 0)
                    return true;
                break;
        }

        return false; //Not at the end of the board
    }
}
