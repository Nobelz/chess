/**
 * <p>Represents a chess piece that is the center piece of the game.</p>
 *
 * @author Nobel Zhou (nxz157)
 * @version 1.0, 12/5/2020
 */
public abstract class CenterPiece extends ChessPiece {

    //region CONSTRUCTORS
    /**
     * <p>Initializes a center piece based on the given side, chess board, and location.</p>
     *
     * @param side          the center piece's side
     * @param label      the chess piece's label name
     * @param chessBoard    the chess board the king piece is on
     * @param icon          the center piece's icon
     * @param row           the center piece's starting row
     * @param column        the center piece's starting column
     * @since 1.0
     */
    public CenterPiece(ChessGame.Side side, String label, ChessIcon icon, ChessBoard chessBoard,  int row, int column) {
        super(side, label, icon, chessBoard, row, column);
    }
    //endregion

    // NON-ABSTRACT METHODS
    /**
     * <p>Returns a boolean representing if the center piece is currently in check.</p>
     *
     * @return  <code>true</code> if the center piece is currently in check
     * @since 1.0
     */
    public boolean isInCheck() {
        return getChessBoard().squareThreatened(getRow(), getColumn(), this);
    }
    //endregion

    // ABSTRACT METHODS
    /**
     * <p>Returns an array of <code>CenterPiece</code>s representing the opposing kings.</p>
     * <p>There should be at least 1 opposing king, but in the event of 4 player chess, there will be more than 1;
     * thus, this returns an array.</p>
     *
     * @return  the opposing kings
     * @since 1.0
     */
    public abstract CenterPiece[] getOpposingKings();
    //endregion
}
