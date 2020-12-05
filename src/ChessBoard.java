/**
 * <p>Represents an arbitrary chessboard that can be implemented either using Swing or JavaFX.</p>
 * <p>The chessboard uses a <code>ChessGame</code> object to determine how the game should be played.</p>
 * <p>The chessboard checks for pieces and is able to add and remove them.</p>
 *
 * @author Nobel Zhou (nxz157)
 * @version 1.0, 12/2/2020
 */
public interface ChessBoard {

    //region ABSTRACT METHODS
    /**
     * <p>Returns a <code>ChessGame</code> representing the rules of the chessboard.</p>
     * <p>For example, it can return either the rules for Indo-European Chess or Xiangqi.</p>
     *
     * @return  the rules of the chessboard
     * @since 1.0
     */
    ChessGame getGameRules();

    /**
     * <p>Adds a <code>ChessPiece</code> to the chessboard at the given row and column.</p>
     *
     * @param piece     the chess piece to be added
     * @param row       the row of the chessboard
     * @param column    the column of the chessboard
     * @since 1.0
     */
    void addPiece(final ChessPiece piece, final int row, final int column);

    /**
     * <p>Adds a <code>ChessPiece</code> to the chessboard at the given row and column, but does not
     * update the display.</p>
     * <p>This is used to check for checks when doing a proposed move.</p>
     *
     * @param piece     the chess piece to be added
     * @param row       the row of the chessboard
     * @param column    the column of the chessboard
     * @since 1.0
     */
    void simulateAddPiece(final ChessPiece piece, final int row, final int column);

    /**
     * <p>Removes a <code>ChessPiece</code> from the chess board at the given row and column.</p>
     * <p>Returns the <code>ChessPiece</code> that was just removed, if any.</p>
     *
     * @param row       the row of the chessboard
     * @param column    the column of the chessboard
     * @return          the chess piece that was just removed, <code>null</code> if there is no chess piece
     * @since 1.0
     */
    ChessPiece removePiece(final int row, final int column);

    /**
     * <p>Removes a <code>ChessPiece</code> from the chess board at the given row and column, but does not
     * update the display./p>
     * <p>This is used to check for checks when doing a proposed move.</p>
     * <p>Returns the <code>ChessPiece</code> that was just removed, if any.</p>
     *
     * @param row       the row of the chessboard
     * @param column    the column of the chessboard
     * @return          the chess piece that was just removed, <code>null</code> if there is no chess piece
     * @since 1.0
     */
    ChessPiece simulateRemovePiece(final int row, final int column);

    /**
     * <p>Checks to see if a piece exists at the given row and column.</p>
     *
     * @param row       the row of the chessboard
     * @param column    the column of the chessboard
     * @return          <code>true</code> if the piece exists at the given row and column
     */
    boolean hasPiece(int row, int column);

    /**
     * <p>Returns the <code>ChessPiece</code> of the chess board at the given row and column, if any.</p>
     *
     * @param row       the row of the chessboard
     * @param column    the column of the chessboard
     * @return          the chess piece at the given row and column, <code>null</code> if there is no chess piece
     */
    ChessPiece getPiece(int row, int column);

    /**
     * <p>Generates a <code>ChessPosition</code> object for the chessboard position.</p>
     *
     * @return  the <code>ChessPosition</code> for the chessboard
     * @since 1.0
     */
    ChessPosition generateChessPosition();

    /**
     * <p>Handles how to stop the chess game.</p>
     *
     * @param result    the result of the chess game
     * @param side      the side of the winning player, if there was one
     * @since 1.0
     */
    void terminate(ChessResult result, ChessGame.Side side);
    //endregion

    //region DEFAULT METHODS
    /**
     * <p>Displays promotion window by letting user choose from <code>ChessPieces</code> that are <code>Promotable</code>.</p>
     * <p>This does nothing by default because some chessboards will not have to handle promotion.</p>
     *
     * @param piece the piece to be promoted
     * @since 1.0
     */
    default void invokePromotion(ChessPiece piece) {}

    /**
     * <p>Returns a <code>ChessPiece</code> that represents the central piece of the game, based on the passed in
     * piece's side.</p>
     *
     * @param piece a piece of the game that has the same side as the central piece
     * @return      the central piece of the same side
     * @since 1.0
     */
    default ChessPiece getCentralPiece(ChessPiece piece) {
        // Iterates the chess board to look for the same side king piece
        for (int i = 0; i < getGameRules().getNumRows(); i++) {
            for (int j = 0; j < getGameRules().getNumColumns(); j++) {
                // Looks for same side king piece
                if (hasPiece(i, j) && getPiece(i, j) instanceof CenterPiece && getPiece(i, j).getSide().equals(piece.getSide()))
                    return getPiece(i, j);
            }
        }

        return null; // Central piece not found
    }

    /**
     * <p>Checks if the <code>ChessPiece</code> in question is threatened by any of the chess pieces of the
     * opposing side.</p>
     *
     * @param row       the row of the chessboard
     * @param column    the column of the chessboard
     * @param piece     the chess piece that is possibly threatened
     * @return          <code>true</code> if the chess piece is threatened by the opposing side's pieces
     */
    default boolean squareThreatened(int row, int column, ChessPiece piece) {
        for (int i = 0; i < getGameRules().getNumRows(); i++) {
            for (int j = 0; j < getGameRules().getNumColumns(); j++) {
                if (hasPiece(i, j) && getPiece(i, j).getSide() != piece.getSide() &&
                        getPiece(i, j).isLegalCaptureMove(row, column))
                    return true;
            }
        }
        return false;
    }
    //endregion
}
