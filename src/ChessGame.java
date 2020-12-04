/**
 * <p>Represents the rule set for a game of chess.</p>
 *
 * @author Harold Connamacher
 * @author Nobel Zhou (nxz157)
 * @version 1.0, 12/2/2020
 */
public interface ChessGame {

    //region NESTED TYPES
    /**
     * <p>Represents the "side" of the player the piece belongs to.</p>
     * <p>The "players" are named by the compass positions around the board.</p>
     *
     * @since 1.0
     */
    enum Side {NORTH, SOUTH, EAST, WEST}
    //endregion

    //region ABSTRACT METHODS
    /**
     * <p>Returns a <code>ChessGame.Side</code> representing the side that is currently playing.</p>
     *
     * @return  the side that is currently playing
     * @since 1.0
     */
    Side getCurrentSide();

    /**
     * <p>Returns a <code>ChessGame.Side</code> representing the side that started.</p>
     *
     * @return  the side that started
     * @since 1.0
     */
    Side getStartingSide();

    /**
     * <p>Determines if it is legal to play a given piece.</p>
     *
     * @param piece     the piece to be played
     * @param row       the row of the square the piece is on
     * @param column    the column of the square the piece is on
     * @return          <code>true</code> if the piece is allowed to move on this turn
     * @since 1.0
     */
    boolean legalPieceToPlay(ChessPiece piece, int row, int column);

    /**
     * <p>Moves a piece to a new position.</p>
     * <p>Returns if the move was successfully made.</p>
     *
     * @param piece     the piece to move
     * @param toRow     the row of the square the piece is moving to
     * @param toColumn  the column of the square the piece is moving to
     * @return          <code>true</code> if the move was made
     * @since 1.0
     */
    boolean makeMove(ChessPiece piece, int toRow, int toColumn);

    /**
     * <p>Checks if the move will result in check.</p>
     * <p>Calls the <code>ChessPiece</code>'s <code>isLegalMove</code> function to make sure the move is legal before
     * checking if it results in the central piece being in check.</p>
     * <p>Also returns <code>true</code> if the move is not a valid move.</p>
     *
     * @param row       the row of the square the piece is moving to
     * @param column    the column of the square the piece is moving to
     * @param piece     the chess piece to move
     * @return          <code>true</code> if the move results in the central piece being in check
     * @since 1.0
     */
    boolean isCheckMove(int row, int column, ChessPiece piece);

    /**
     * <p>Returns the number of rows in the chessboard.</p>
     *
     * @return  the number of rows in the chessboard
     * @since 1.0
     */
    int getNumRows();

    /**
     * <p>Returns the number of columns in the chessboard.</p>
     *
     * @return  the number of columns in the chessboard
     * @since 1.0
     */
    int getNumColumns();

    /**
     * <p>Sets up the chessboard by placing all the initial pieces for the game into the board in the correct
     * starting positions.</p>
     *
     * @param board the chess board
     * @since 1.0
     */
    void startGame(ChessBoard board);

    /**
     * <p>Handles ending conditions of the chess game.</p>
     *
     * @param board         the chess board
     * @param centralPiece  the central piece to check the ending conditions
     * @since 1.0
     */
    void handleEndConditions(ChessBoard board, ChessPiece centralPiece);

    /**
     * <p>Generates all the legal moves that can be played.</p>
     *
     * @param piece a chess piece of the board
     * @return      an array of <code>Move</code> objects that can be played
     * @since 1.0
     */
    ChessMove[] generateMoves(ChessPiece piece);
    //endregion

    //region DEFAULT METHODS
    /**
     * <p>Returns whether a user can choose a different piece from the one selected or if they have to move the
     * selected piece.</p>
     * <p>If this method returns false, then the <code>legalPieceToPlay</code> method must not return true if that piece
     * has no legal moves.  Otherwise the game could freeze with a player not permitted to change selection of a piece
     * with no legal moves.</p>
     * <p>This method is <code>false</code> by default.</p>
     *
     * @param piece     the piece that was selected
     * @param row       the row of the square the piece is on
     * @param column    the column of the square the piece is on
     * @return          <code>true</code> if the player can change the piece they selected
     * @since 1.0
     */
    default boolean canChangeSelection(ChessPiece piece, int row, int column) {
        return true;
    }

    /**
     * <p>Promotes the <code>ChessPiece</code> to the desired <code>ChessPiece</code> type.</p>
     * <p>This does nothing by default because not all chessboards have to deal with promotion.</p>
     *
     * @param oldPiece  the <code>ChessPiece</code> to be replaced
     * @param newPiece  the <code>ChessPiece</code> type to be replaced with
     * @since 1.0
     */
    default void promote(ChessPiece oldPiece, ChessPiece newPiece) {}
    //endregion
}