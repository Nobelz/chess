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
    @Override
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
     * <p>Returns the number of rows in the chessboard.</p>
     *
     * @return  the number of rows in the chessboard
     * @since 1.0
     */
    @Override
    public int getNumRows() {
        return (getStartingSide() == Side.WEST || getStartingSide() == Side.EAST) ? 9 : 10;
    }

    /**
     * <p>Returns the number of columns in the chessboard.</p>
     *
     * @return  the number of columns in the chessboard
     * @since 1.0
     */
    @Override
    public int getNumColumns() {
        return (getStartingSide() == Side.WEST || getStartingSide() == Side.EAST) ? 10 : 9;
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
        XiangqiKingPiece king = ((XiangqiKingPiece) centralPiece).getOpposingKings()[0];

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
            king.getChessBoard().terminate(ChessResult.CHECKMATE, king.getOpposingKings()[0].getSide());
    }

    /**
     * <p>Checks for stalemate.</p>
     *
     * @param king  the king piece that is subject to stalemate
     */
    private void checkStalemate(XiangqiKingPiece king) {
        if (cannotMove(king) && !king.isInCheck())
            king.getChessBoard().terminate(ChessResult.STALEMATE, king.getOpposingKings()[0].getSide());
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
            chessBoard.addPiece(new SoldierPiece(ChessGame.Side.SOUTH, chessBoard, ChessIcon.RED_SOLDIER, 6, i), 6, i);
            chessBoard.addPiece(new SoldierPiece(ChessGame.Side.NORTH, chessBoard, ChessIcon.BLACK_SOLDIER, 3, i), 3, i);
        }

        // Rooks
        chessBoard.addPiece(new RookPiece(ChessGame.Side.SOUTH, chessBoard, ChessIcon.RED_CHARIOT, 9, 0), 9, 0);
        chessBoard.addPiece(new RookPiece(ChessGame.Side.SOUTH, chessBoard, ChessIcon.RED_CHARIOT, 9, 8), 9, 8);
        chessBoard.addPiece(new RookPiece(ChessGame.Side.NORTH, chessBoard, ChessIcon.BLACK_CHARIOT, 0, 0), 0, 0);
        chessBoard.addPiece(new RookPiece(ChessGame.Side.NORTH, chessBoard, ChessIcon.BLACK_CHARIOT, 0, 8), 0, 8);

        // Cannons
        chessBoard.addPiece(new CannonPiece(ChessGame.Side.SOUTH, chessBoard, ChessIcon.RED_CANNON, 7, 1), 7, 1);
        chessBoard.addPiece(new CannonPiece(ChessGame.Side.SOUTH, chessBoard, ChessIcon.RED_CANNON, 7, 7), 7, 7);
        chessBoard.addPiece(new CannonPiece(ChessGame.Side.NORTH, chessBoard, ChessIcon.BLACK_CANNON, 2, 1), 2, 1);
        chessBoard.addPiece(new CannonPiece(ChessGame.Side.NORTH, chessBoard, ChessIcon.BLACK_CANNON, 2, 7), 2, 7);

        // Horses
        chessBoard.addPiece(new HorsePiece(ChessGame.Side.SOUTH, chessBoard, ChessIcon.RED_HORSE, 9, 1), 9, 1);
        chessBoard.addPiece(new HorsePiece(ChessGame.Side.SOUTH, chessBoard, ChessIcon.RED_HORSE, 9, 7), 9, 7);
        chessBoard.addPiece(new HorsePiece(ChessGame.Side.NORTH, chessBoard, ChessIcon.BLACK_HORSE, 0, 1), 0, 1);
        chessBoard.addPiece(new HorsePiece(ChessGame.Side.NORTH, chessBoard, ChessIcon.BLACK_HORSE, 0, 7), 0, 7);

        // Elephants
        chessBoard.addPiece(new ElephantPiece(ChessGame.Side.SOUTH, chessBoard, ChessIcon.RED_ELEPHANT, 9, 2), 9, 2);
        chessBoard.addPiece(new ElephantPiece(ChessGame.Side.SOUTH, chessBoard, ChessIcon.RED_ELEPHANT, 9, 6), 9, 6);
        chessBoard.addPiece(new ElephantPiece(ChessGame.Side.NORTH, chessBoard, ChessIcon.BLACK_ELEPHANT, 0, 2), 0, 2);
        chessBoard.addPiece(new ElephantPiece(ChessGame.Side.NORTH, chessBoard, ChessIcon.BLACK_ELEPHANT, 0, 6), 0, 6);

        // Guards
        chessBoard.addPiece(new GuardPiece(ChessGame.Side.SOUTH, chessBoard, ChessIcon.RED_GUARD, 9, 3), 9, 3);
        chessBoard.addPiece(new GuardPiece(ChessGame.Side.SOUTH, chessBoard, ChessIcon.RED_GUARD, 9, 5), 9, 5);
        chessBoard.addPiece(new GuardPiece(ChessGame.Side.NORTH, chessBoard, ChessIcon.BLACK_GUARD, 0, 3), 0, 3);
        chessBoard.addPiece(new GuardPiece(ChessGame.Side.NORTH, chessBoard, ChessIcon.BLACK_GUARD, 0, 5), 0, 5);

        // Kings
        chessBoard.addPiece(new XiangqiKingPiece(ChessGame.Side.SOUTH, chessBoard, ChessIcon.RED_GENERAL, 9, 4), 9, 4);
        chessBoard.addPiece(new XiangqiKingPiece(ChessGame.Side.NORTH, chessBoard, ChessIcon.BLACK_GENERAL, 0, 4), 0, 4);
    }
}
