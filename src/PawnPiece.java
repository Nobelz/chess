/**
 * <p>Represents a pawn chess piece.</p>
 * <p>Dictates how a pawn can move.</p>
 *
 * @author Nobel Zhou
 * @version 1.0, 12/2/20
 */
public class PawnPiece extends ChessPiece implements CanPawnMove, CanEnPassantMove {

    //region CONSTRUCTORS
    /**
     * <p>Initializes a pawn piece based on the given side, chess board, and location.</p>
     *
     * @param side          the pawn piece's side
     * @param chessBoard    the chess board the pawn piece is on
     * @param icon          the pawn piece's icon
     * @param row           the pawn piece's starting row
     * @param column        the pawn piece's starting column
     * @since 1.0
     */
    public PawnPiece(ChessGame.Side side, ChessBoard chessBoard, ChessIcon icon, int row, int column) {
        super(side, "P", icon, chessBoard, row, column);
    }
    //endregion

    //region METHODS
    /**
     * <p>Returns a boolean representing if the pawn piece's proposed move is legal or not.</p>
     *
     * @param toRow     the pawn piece's destination row
     * @param toColumn  the pawn piece's destination column
     * @return          <code>true</code> if the pawn piece's proposed move is legal
     * @since 1.0
     */
    @Override
    public boolean isLegalMove(int toRow, int toColumn) {
        // Checks also if it's a legal en passant move
        return super.isLegalMove(toRow, toColumn) || isValidEnPassantMove(toRow, toColumn, this);
    }

    /**
     * <p>Returns a boolean representing if the proposed move is legal, assuming that it is unoccupied.</p>
     *
     * @param row       the pawn piece's destination row
     * @param column    the pawn piece's destination column
     * @return          <code>true</code> if the pawn piece's proposed move is legal, assuming it is unoccupied
     * @since 1.0
     */
    @Override
    public boolean isLegalNonCaptureMove(int row, int column) {
        return isValidPawnNonCaptureMove(row, column, this);
    }

    /**
     * <p>Returns a boolean representing if the proposed move is legal, assuming that it is occupied.</p>
     *
     * @param row       the pawn piece's destination row
     * @param column    the pawn piece's destination column
     * @return          <code>true</code> if the pawn piece's proposed move is legal, assuming it is occupied
     * @since 1.0
     */
    @Override
    public boolean isLegalCaptureMove(int row, int column) {
        return isValidPawnCaptureMove(row, column, this);
    }

    /**
     * <p>Handles any post-move processes, if any, once the pawn's move is completed.</p>
     * <p>Handles and checks for promotions.</p>
     *
     * @since 1.0
     */
    @Override
    public void moveDone() {
        super.moveDone();
        if (checkPawnPromotion())
            getChessBoard().invokePromotion(this);
    }

    /**
     * <p>Checks to see if the pawn is eligible to be promoted.</p>
     *
     * @return  <code>true</code> if the pawn is eligible to be promoted
     * @since 1.0
     */
    private boolean checkPawnPromotion() {
        switch (getSide()) {
            case SOUTH:
                return getRow() == 0;
            case NORTH:
                return getRow() == getChessBoard().getGameRules().getNumRows() - 1;
            case WEST:
                return getColumn() == getChessBoard().getGameRules().getNumColumns() - 1;
            default: // East
                return getColumn() == 0;
        }
    }

    /**
     * <p>Returns an array of <code>ChessPiece.ProposedMove</code> objects that shows how to move the pawn pieces.</p>
     * <p>For pawn pieces, this includes handling en passant moves.</p>
     *
     * @param row       the row of the move
     * @param column    the column of the move
     * @return          an array of <code>ChessPiece.ProposedMove</code> objects that show how to move the pawn pieces
     * @since 1.0
     */
    @Override
    public ProposedMove[] getMoveInstructions(int row, int column) {
        // Checks for en passant move
        if (isValidEnPassantMove(row, column, this)) {
            // Stores the removed pawn
            PawnPiece removed = getCapturedEnPassantPawn(row, column, this);

            return new ProposedMove[] {
                    new ProposedMove(this, removed, row, column, false)
            };
        } else
            return new ChessPiece.ProposedMove[] {new ProposedMove(this, getChessBoard().getPiece(row, column), row, column, false)};
    }
    //endregion
}