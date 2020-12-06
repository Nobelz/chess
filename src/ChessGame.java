import java.util.ArrayList;

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
     * <p>Flips the side so the other side is playing.</p>
     *
     * @since 1.0
     */
    void flipSide();

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
     * <p>Determines if it is legal to play a given piece.</p>
     * <p>Only returns <code>true</code> if the piece is on the same side and if the piece has any legal moves to play.</p>
     *
     * @param piece     the piece to be played
     * @param row       the row of the square the piece is on
     * @param column    the column of the square the piece is on
     * @return          <code>true</code> if the piece is allowed to move on this turn
     */
    default boolean legalPieceToPlay(ChessPiece piece, int row, int column) {
        // Checks if the piece is the correct side
        if (!piece.getSide().equals(getCurrentSide()))
            return false;

        // Checks for a legal move throughout the whole chess board
        for (int i = 0; i < getNumRows(); i++) {
            for (int j = 0; j < getNumColumns(); j++) {
                // Checks if the piece has a legal move at the row and column specified
                if ((i != row || j != column) && piece.isLegalMove(i, j) && isCheckMove(i, j, piece))
                    return true;
            }
        }

        return false; // No legal move to play
    }

    /**
     * <p>Generates all the legal moves that can be played.</p>
     *
     * @param piece a chess piece of the board
     * @return      an array of <code>Move</code> objects that can be played
     * @since 1.0
     */
    default ChessMove[] generateMoves(ChessPiece piece) {

        // Stores the same side's pieces
        ArrayList<ChessPiece> pieces = new ArrayList<>();

        for (int i = 0; i < getNumRows(); i++) {
            for (int j = 0; j < getNumColumns(); j++) {
                // Looks for same side piece in the chess board
                if (piece.getChessBoard().hasPiece(i, j) && piece.getChessBoard().getPiece(i, j).getSide().equals(piece.getSide()))
                    pieces.add(piece.getChessBoard().getPiece(i, j));
            }
        }

        // Stores all the available moves
        ArrayList<ChessMove> moves = new ArrayList<>();

        //Iterates through each of the same side pieces
        for (ChessPiece cp : pieces) {
            if (legalPieceToPlay(cp, cp.getRow(), cp.getColumn())) {
                for (int i = 0; i < getNumRows(); i++) {
                    for (int j = 0; j < getNumColumns(); j++) {
                        if (cp.isLegalMove(i, j) && isCheckMove(i, j, cp))
                            moves.add(new ChessMove(cp, i, j));
                    }
                }
            }
        }

        // Change to array format
        return moves.toArray(new ChessMove[0]);
    }

    /**
     * <p>Checks for any moves in a position.</p>
     * <p>If there are no moves for the position, the end result is either stalemate or checkmate.</p>
     *
     * @param piece the center piece
     * @return      <code>true</code> if there are no moves in the position for the side of the piece
     * @since 1.0
     */
    default boolean cannotMove(CenterPiece piece) {
        // Stores the same side's pieces
        ArrayList<ChessPiece> pieces = new ArrayList<>();

        for (int i = 0; i < getNumRows(); i++) {
            for (int j = 0; j < getNumColumns(); j++) {
                // Looks for same side piece in the chess board
                if (piece.getChessBoard().hasPiece(i, j) && piece.getChessBoard().getPiece(i, j).getSide().equals(piece.getSide()))
                    pieces.add(piece.getChessBoard().getPiece(i, j));
            }
        }

        // Iterates through each of the same side pieces
        for (ChessPiece cp : pieces) {
            if (legalPieceToPlay(cp, cp.getRow(), cp.getColumn()))
                return false;
        }

        // No move to play
        return true;
    }


    /**
     * <p>Returns a boolean representing if the move will result in the king being in check.</p>
     * <p>Also returns <code>true</code> if the move is not a valid move.</p>
     *
     * @param row       the piece's destination row
     * @param column    the piece's destination column
     * @param cp        the chess piece
     * @return          <code>true</code> if the move results in the center piece being in check
     * @since 1.0
     */
    default boolean isCheckMove(int row, int column, ChessPiece cp) {
        // Checks if it's a valid move
        if (cp.isLegalMove(row, column)) {
            // Stores the chess board
            ChessBoard board = cp.getChessBoard();
            // Stores the move instructions
            ChessPiece.ProposedMove[] moveInstructions = cp.getMoveInstructions(row, column);
            // Stores the original rows of the pieces to be moved
            int[] originalRows = new int[moveInstructions.length];
            // Stores the original columns of the pieces to be moved
            int[] originalColumns = new int[moveInstructions.length];

            for (int i = 0; i < moveInstructions.length; i++) {
                ChessPiece.ProposedMove instruction = moveInstructions[i];
                originalRows[i] = instruction.getMovedPiece().getRow();
                originalColumns[i] = instruction.getMovedPiece().getColumn();

                // Simulates the move to check if king is in check
                board.simulateRemovePiece(originalRows[i], originalColumns[i]);
                if (instruction.getRemovedPiece() != null)
                    board.simulateRemovePiece(instruction.getRemovedPiece().getRow(), instruction.getRemovedPiece().getColumn());
                board.simulateAddPiece(instruction.getMovedPiece(), instruction.getRow(), instruction.getColumn());
            }

            // Checks to see if the king is in check
            boolean isInCheck = cp.getChessBoard().getCentralPiece(cp).isInCheck();

            for (int i = 0; i < moveInstructions.length; i++) {
                ChessPiece.ProposedMove instruction = moveInstructions[i];

                // Reverts the move
                board.simulateRemovePiece(instruction.getRow(), instruction.getColumn());
                if (instruction.getRemovedPiece() != null)
                    board.simulateAddPiece(instruction.getRemovedPiece(), instruction.getRemovedPiece().getRow(), instruction.getRemovedPiece().getColumn());
                board.simulateAddPiece(instruction.getMovedPiece(), originalRows[i], originalColumns[i]);
            }

            return !isInCheck;
        } else
            return false; // Not a valid move
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