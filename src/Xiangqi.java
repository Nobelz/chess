import java.util.ArrayList;
import java.util.Collections;

/**
 * <p>Represents the ruleset for a game of Xiangqi.</p>
 *
 * @author Nobel Zhou (nxz157)
 * @version 1.0, 12/4/20
 */
public class Xiangqi implements ChessGame {

    //region FIELDS
    // Stores the starting side; cannot be changed
    private final Side startingSide;

    // Stores the side that is currently playing
    private Side currentSide;

    // Stores the number of non-pawn, non-capture moves in succession; used for determining the 50 move rule
    private int fiftyMoveRule;
    //endregion

    //region CONSTRUCTORS
    /**
     * <p>Initializes the rules of Xiangqi.</p>
     *
     * @param startingSide  the side who starts
     * @since 1.0
     */
    public Xiangqi(Side startingSide) {
        this.startingSide = startingSide;
        currentSide = startingSide;
    }
    //endregion

    //region METHODS
    /**
     * <p>Flips the side so the other side is playing.</p>
     *
     * @since 1.0
     */
    public void flipSide() {
        // Flips the side to the opposite side
        switch (currentSide) {
            case NORTH:
                currentSide = Side.SOUTH;
                break;
            case SOUTH:
                currentSide = Side.NORTH;
                break;
            case WEST:
                currentSide = Side.EAST;
                break;
            default: // East
                currentSide = Side.WEST;
        }
    }

    /**
     * <p>Returns an int representing the number of consecutive non-capture and non-pawn moves.</p>
     *
     * @return  the number of consecutive non-capture and non-pawn moves
     * @since 1.0
     */
    public int getFiftyMoveRule() {
        return fiftyMoveRule;
    }

    /**
     * <p>Sets the fifty move rule count.</p>
     *
     * @param fiftyMoveRule     the new fifty move rule count
     * @since 1.0
     */
    public void setFiftyMoveRule(int fiftyMoveRule) {
        this.fiftyMoveRule = fiftyMoveRule;
    }

    /**
     * <p>Returns a <code>ChessGame.Side</code> representing the side that is currently playing.</p>
     *
     * @return  the side that is currently playing
     * @since 1.0
     */
    @Override
    public ChessGame.Side getCurrentSide() {
        return currentSide;
    }

    /**
     * <p>Returns a <code>ChessGame.Side</code> representing the side that started.</p>
     *
     * @return  the side that started
     * @since 1.0
     */
    @Override
    public ChessGame.Side getStartingSide() {
        return startingSide;
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
    @Override
    public boolean legalPieceToPlay(ChessPiece piece, int row, int column) {
        // Checks if the piece is the correct side
        if (!piece.getSide().equals(currentSide))
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
     * <p>Moves a piece to a new position.</p>
     *
     * @param piece     the piece to move
     * @param toRow     the row of the square the piece is moving to
     * @param toColumn  the column of the square the piece is moving to
     * @return          <code>true</code> if the move was made
     */
    @Override
    public boolean makeMove(ChessPiece piece, int toRow, int toColumn) {
        // Checks if the move can be played
        if (isCheckMove(toRow, toColumn, piece)) {
            // Stores the chess board
            ChessBoard board = piece.getChessBoard();
            // Stores the move instructions
            ChessPiece.ProposedMove[] moveInstructions = piece.getMoveInstructions(toRow, toColumn);

            for (ChessPiece.ProposedMove instruction : moveInstructions) {
                // Checks for capture move
                if (!instruction.isReversible())
                    setFiftyMoveRule(0); // Capture just took place; reset fifty move rule counter
                else
                    setFiftyMoveRule(getFiftyMoveRule() + 1);

                // Moves the chess pieces
                board.removePiece(instruction.getMovedPiece().getRow(), instruction.getMovedPiece().getColumn());
                ChessPiece save = instruction.getRemovedPiece();
                if (save != null)
                    board.removePiece(save.getRow(), save.getColumn());
                board.addPiece(instruction.getMovedPiece(), instruction.getRow(), instruction.getColumn());

                // Piece post-move processing
                instruction.getMovedPiece().moveDone();
            }

            //Now opposite player's turn
            flipSide();

            return true; //Successful move
        } else
            return false; //Unsuccessful move
    }

    /**
     * <p>Returns a boolean representing if the move will result in the king being in check.</p>
     * <p>Also returns <code>true</code> if the move is not a valid move.</p>
     *
     * @param row       the piece's destination row
     * @param column    the piece's destination column
     * @param cp        the chess piece
     * @return          <code>true</code> if the move results in the king being in check
     * @since 1.0
     */
    @Override
    public boolean isCheckMove(int row, int column, ChessPiece cp) {
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
            boolean isInCheck = ((XiangqiKingPiece) cp.getChessBoard().getCentralPiece(cp)).isInCheck();

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
     * <p>Returns the number of rows in the chessboard.</p>
     *
     * @return  the number of rows in the chessboard
     * @since 1.0
     */
    @Override
    public int getNumRows() {
        return 10;
    }

    /**
     * <p>Returns the number of columns in the chessboard.</p>
     *
     * @return  the number of columns in the chessboard
     * @since 1.0
     */
    @Override
    public int getNumColumns() {
        return 9;
    }

    /**
     * <p>Handles ending conditions of the Indo-European chess game.</p>
     *
     * @param board         the chess board
     * @param centralPiece  the central piece to check the ending conditions
     * @since 1.0
     */
    @Override
    public void handleEndConditions(ChessBoard board, ChessPiece centralPiece) {
        // Gets the king piece of the player's whose turn it is now
        XiangqiKingPiece king = ((XiangqiKingPiece) centralPiece).getOpposingKing();

        // Checks for ending conditions
        checkCheckmate(king);
        checkStalemate(king);
        checkFiftyMoveRule(king.getChessBoard());
    }

    /**
     * <p>Checks for checkmate.</p>
     *
     * @param king  the king piece that is subject to checkmate
     * @since 1.0
     */
    private void checkCheckmate(XiangqiKingPiece king) {
        if (cannotMove(king) && king.isInCheck())
            king.getChessBoard().terminate(ChessResult.CHECKMATE, king.getOpposingKing().getSide());
    }

    /**
     * <p>Checks for stalemate.</p>
     *
     * @param king  the king piece that is subject to stalemate
     */
    private void checkStalemate(XiangqiKingPiece king) {
        if (cannotMove(king) && !king.isInCheck())
            king.getChessBoard().terminate(ChessResult.STALEMATE, king.getOpposingKing().getSide());
    }

    /**
     * <p>Generates all the legal moves that can be played.</p>
     *
     * @param piece a chess piece of the board
     * @return      an array of <code>Move</code> objects that can be played
     * @since 1.0
     */
    public ChessMove[] generateMoves(ChessPiece piece) {
        // Stores the same side's pieces
        ChessPiece[] pieces = getSameSidePieces(piece);

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
     * @param piece the xiangqi king piece
     * @return      <code>true</code> if there are no moves in the position for the side of the piece
     * @since 1.0
     */
    private boolean cannotMove(XiangqiKingPiece piece) {
        // Stores the same side's pieces
        ChessPiece[] pieces = getSameSidePieces(piece);

        // Iterates through each of the same side pieces
        for (ChessPiece cp : pieces) {
            if (legalPieceToPlay(cp, cp.getRow(), cp.getColumn()))
                return false;
        }

        // No move to play
        return true;
    }

    /**
     * <p>Gets all of the pieces that are on the same side as the parameter piece.</p>
     *
     * @param piece a piece of the game
     * @return      an array of <code>ChessPiece</code> that contains all the pieces for that side
     */
    private ChessPiece[] getSameSidePieces(ChessPiece piece) {
        // Stores the same side's pieces
        ArrayList<ChessPiece> pieces = new ArrayList<>();

        for (int i = 0; i < getNumRows(); i++) {
            for (int j = 0; j < getNumColumns(); j++) {
                //Looks for same side piece in the chess board
                if (piece.getChessBoard().hasPiece(i, j) && piece.getChessBoard().getPiece(i, j).getSide().equals(piece.getSide()))
                    pieces.add(piece.getChessBoard().getPiece(i, j));
            }
        }

        return pieces.toArray(new ChessPiece[0]);
    }

    /**
     * <p>Checks for draw by the fifty move rule.</p>
     *
     * @param board the chessboard
     * @since 1.0
     */
    private void checkFiftyMoveRule(ChessBoard board) {
        if (getFiftyMoveRule() >= 50)
            board.terminate(ChessResult.FIFTY_MOVE_RULE, null);
    }

    /**
     * <p>Starts a game of Xiangqi in the North-South orientation.</p>
     *
     * @param chessBoard    the chess board
     * @since 1.0
     */
    @Override
    public void startGame(ChessBoard chessBoard) {
        // Soldiers
        for (int i = 0; i < 9; i += 2) {
            chessBoard.addPiece(new SoldierPiece(ChessGame.Side.SOUTH, chessBoard, null, 6, i), 6, i);
            chessBoard.addPiece(new SoldierPiece(ChessGame.Side.NORTH, chessBoard, null, 3, i), 3, i);
        }

        // Rooks
        chessBoard.addPiece(new RookPiece(ChessGame.Side.SOUTH, chessBoard, null, 9, 0), 9, 0);
        chessBoard.addPiece(new RookPiece(ChessGame.Side.SOUTH, chessBoard, null, 9, 8), 9, 8);
        chessBoard.addPiece(new RookPiece(ChessGame.Side.NORTH, chessBoard, null, 0, 0), 0, 0);
        chessBoard.addPiece(new RookPiece(ChessGame.Side.NORTH, chessBoard, null, 0, 8), 0, 8);

        // Cannons
        chessBoard.addPiece(new CannonPiece(ChessGame.Side.SOUTH, chessBoard, null, 7, 1), 7, 1);
        chessBoard.addPiece(new CannonPiece(ChessGame.Side.SOUTH, chessBoard, null, 7, 7), 7, 7);
        chessBoard.addPiece(new CannonPiece(ChessGame.Side.NORTH, chessBoard, null, 2, 1), 2, 1);
        chessBoard.addPiece(new CannonPiece(ChessGame.Side.NORTH, chessBoard, null, 2, 7), 2, 7);

        // Horses
        chessBoard.addPiece(new HorsePiece(ChessGame.Side.SOUTH, chessBoard, null, 9, 1), 9, 1);
        chessBoard.addPiece(new HorsePiece(ChessGame.Side.SOUTH, chessBoard, null, 9, 7), 9, 7);
        chessBoard.addPiece(new HorsePiece(ChessGame.Side.NORTH, chessBoard, null, 0, 1), 0, 1);
        chessBoard.addPiece(new HorsePiece(ChessGame.Side.NORTH, chessBoard, null, 0, 7), 0, 7);

        // Elephants
        chessBoard.addPiece(new ElephantPiece(ChessGame.Side.SOUTH, chessBoard, null, 9, 2), 9, 2);
        chessBoard.addPiece(new ElephantPiece(ChessGame.Side.SOUTH, chessBoard, null, 9, 6), 9, 6);
        chessBoard.addPiece(new ElephantPiece(ChessGame.Side.NORTH, chessBoard, null, 0, 2), 0, 2);
        chessBoard.addPiece(new ElephantPiece(ChessGame.Side.NORTH, chessBoard, null, 0, 6), 0, 6);

        // Guards
        chessBoard.addPiece(new GuardPiece(ChessGame.Side.SOUTH, chessBoard, null, 9, 3), 9, 3);
        chessBoard.addPiece(new GuardPiece(ChessGame.Side.SOUTH, chessBoard, null, 9, 5), 9, 5);
        chessBoard.addPiece(new GuardPiece(ChessGame.Side.NORTH, chessBoard, null, 0, 3), 0, 3);
        chessBoard.addPiece(new GuardPiece(ChessGame.Side.NORTH, chessBoard, null, 0, 5), 0, 5);

        // Kings
        chessBoard.addPiece(new XiangqiKingPiece(ChessGame.Side.SOUTH, chessBoard, null, 9, 4), 9, 4);
        chessBoard.addPiece(new XiangqiKingPiece(ChessGame.Side.NORTH, chessBoard, null, 0, 4), 0, 4);
    }
}
