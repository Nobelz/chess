/**
 * <p>Represents a chessboard with no GUI made for Unit Testing.</p>
 *
 * @author Nobel Zhou (nxz157)
 * @version 1.0, 12/5/2020
 */
public class TestChessBoard implements ChessBoard {

    //region FIELDS
    // Stores the pieces of the test chessboard
    private final ChessPiece[][] pieces;

    // Stores the rules for the test chessboard
    private final ChessGame gameRules;
    //endregion

    //region CONSTRUCTORS
    /**
     * <p>Creates a test chessboard using the given game rules.</p>
     *
     * @param gameRules    the game rules for the test chessboard
     * @since 1.0
     */
    public TestChessBoard(ChessGame gameRules) {
        // Initializes the board
        this.gameRules = gameRules;
        pieces = new ChessPiece[gameRules.getNumRows()][gameRules.getNumColumns()];
    }
    //endregion

    //region METHODS
    /**
     * <p>Returns a <code>ChessGame</code> representing the rules of the chessboard.</p>
     * <p>For example, it can return either the rules for Indo-European Chess or Xiangqi.</p>
     *
     * @return the rules of the chessboard
     * @since 1.0
     */
    @Override
    public ChessGame getGameRules() {
        return gameRules;
    }

    /**
     * <p>Adds a <code>ChessPiece</code> to the chessboard at the given row and column.</p>
     *
     * @param piece  the chess piece to be added
     * @param row    the row of the chessboard
     * @param column the column of the chessboard
     * @since 1.0
     */
    @Override
    public void addPiece(ChessPiece piece, int row, int column) {
        pieces[row][column] = piece;
        piece.setLocation(row, column);
    }

    /**
     * <p>Adds a <code>ChessPiece</code> to the chessboard at the given row and column, but does not
     * update the display.</p>
     * <p>This is used to check for checks when doing a proposed move.</p>
     * <p>For a test chessboard, this does the exact same thing as the <code>addPiece</code> function.</p>
     *
     * @param piece  the chess piece to be added
     * @param row    the row of the chessboard
     * @param column the column of the chessboard
     * @since 1.0
     */
    @Override
    public void simulateAddPiece(ChessPiece piece, int row, int column) {
        addPiece(piece, row, column);
    }

    /**
     * <p>Removes a <code>ChessPiece</code> from the chess board at the given row and column.</p>
     * <p>Returns the <code>ChessPiece</code> that was just removed, if any.</p>
     *
     * @param row    the row of the chessboard
     * @param column the column of the chessboard
     * @return the chess piece that was just removed, <code>null</code> if there is no chess piece
     * @since 1.0
     */
    @Override
    public ChessPiece removePiece(int row, int column) {
        ChessPiece save = pieces[row][column];
        pieces[row][column] = null;
        return save;
    }

    /**
     * <p>Removes a <code>ChessPiece</code> from the chess board at the given row and column, but does not
     * update the display./p>
     * <p>This is used to check for checks when doing a proposed move.</p>
     * <p>Returns the <code>ChessPiece</code> that was just removed, if any.</p>
     * <p>For a test chessboard, this does the exact same thing as the <code>removePiece</code> function.</p>
     *
     * @param row    the row of the chessboard
     * @param column the column of the chessboard
     * @return the chess piece that was just removed, <code>null</code> if there is no chess piece
     * @since 1.0
     */
    @Override
    public ChessPiece simulateRemovePiece(int row, int column) {
        return removePiece(row, column);
    }

    /**
     * <p>Checks to see if a piece exists at the given row and column.</p>
     *
     * @param row    the row of the chessboard
     * @param column the column of the chessboard
     * @return <code>true</code> if the piece exists at the given row and column
     */
    @Override
    public boolean hasPiece(int row, int column) {
        return pieces[row][column] != null;
    }

    /**
     * <p>Returns the <code>ChessPiece</code> of the chess board at the given row and column, if any.</p>
     *
     * @param row    the row of the chessboard
     * @param column the column of the chessboard
     * @return the chess piece at the given row and column, <code>null</code> if there is no chess piece
     */
    @Override
    public ChessPiece getPiece(int row, int column) {
        return pieces[row][column];
    }

    /**
     * <p>Generates a <code>ChessPosition</code> object for the chessboard position.</p>
     *
     * @return the <code>ChessPosition</code> for the chessboard
     * @since 1.0
     */
    @Override
    public ChessPosition generateChessPosition() {
        // Clones the pieces to put into a ChessPosition
        ChessPiece[][] chessPieces = pieces.clone();
        for (int i = 0; i < chessPieces.length; i++) {
            chessPieces[i] = chessPieces[i].clone();
        }

        return new ChessPosition(chessPieces, getGameRules().getCurrentSide());
    }

    /**
     * <p>Handles how to stop the chess game.</p>
     * <p>For a test chessboard, this method does nothing.</p>
     *
     * @param result the result of the chess game
     * @param side   the side of the winning player, if there was one
     * @since 1.0
     */
    @Override
    public void terminate(ChessResult result, ChessGame.Side side) {}
    //endregion
}
