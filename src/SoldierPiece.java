/**
 * <p>Represents a soldier chess piece.</p>
 * <p>Dictates how a soldier can move.</p>
 *
 * @author Nobel Zhou
 * @version 1.0, 12/4/20
 */
public class SoldierPiece extends ChessPiece implements CanSoldierMove {

    //region CONSTRUCTORS
    /**
     * <p>Initializes a soldier piece based on the given side, chess board, and location.</p>
     *
     * @param side          the soldier piece's side
     * @param chessBoard    the chess board the soldier piece is on
     * @param icon          the soldier piece's icon
     * @param row           the soldier piece's starting row
     * @param column        the soldier piece's starting column
     * @since 1.0
     */
    public SoldierPiece(ChessGame.Side side, ChessBoard chessBoard, ChessIcon icon, int row, int column) {
        super(side, "S", icon, chessBoard, row, column);
    }
    //endregion

    //region METHODS
    /**
     * <p>Returns a boolean representing if the proposed move is legal, assuming that it is unoccupied.</p>
     *
     * @param row       the soldier piece's destination row
     * @param column    the soldier piece's destination column
     * @return          <code>true</code> if the soldier piece's proposed move is legal, assuming it is unoccupied
     * @since 1.0
     */
    @Override
    public boolean isLegalNonCaptureMove(int row, int column) {
        return isValidSoldierMove(row, column, this);
    }

    /**
     * <p>Returns a boolean representing if the proposed move is legal, assuming that it is occupied.</p>
     *
     * @param row       the soldier piece's destination row
     * @param column    the soldier piece's destination column
     * @return          <code>true</code> if the soldier piece's proposed move is legal, assuming it is occupied
     * @since 1.0
     */
    @Override
    public boolean isLegalCaptureMove(int row, int column) {
        return isLegalNonCaptureMove(row, column);
    }

    /**
     * <p>Returns an array of <code>ChessPiece.ProposedMove</code> objects that shows how to move the soldier pieces.</p>
     *
     * @param row       the row of the move
     * @param column    the column of the move
     * @return          an array of <code>ChessPiece.ProposedMove</code> objects that show how to move the soldier pieces
     * @since 1.0
     */
    @Override
    public ProposedMove[] getMoveInstructions(int row, int column) {
        // Checks for forward soldier moves; these are not reversible
        if (isValidForwardSoldierMove(row, column, this)) {
            return new ChessPiece.ProposedMove[] {new ProposedMove(this, getChessBoard().getPiece(row, column), row, column, false)};
        } else
            return super.getMoveInstructions(row, column);
    }
    //endregion
}
