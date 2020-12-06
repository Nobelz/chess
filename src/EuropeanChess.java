import java.util.ArrayList;
import java.util.Collections;

/**
 * <p>Represents the ruleset for a game of Indo-European chess.</p>
 *
 * @author Nobel Zhou (nxz157)
 * @version 2.0, 12/2/20
 */
public class EuropeanChess implements ChessGame {

    //region FIELDS
    // Stores the starting side; cannot be changed
    private final Side startingSide;

    // Stores the side that is currently playing
    private Side currentSide;

    // Stores the game positions of the whole game
    private final ArrayList<ChessPosition> positions;

    // Stores the number of non-pawn, non-capture moves in succession; used for determining the 50 move rule
    private int fiftyMoveRule;
    //endregion

    //region CONSTRUCTORS
    /**
     * <p>Initializes the rules of Indo-European chess.</p>
     *
     * @param startingSide  the side who starts
     * @since 1.0
     */
    public EuropeanChess(Side startingSide) {
        this.startingSide = startingSide;
        currentSide = startingSide;
        positions = new ArrayList<>();
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

            // Adds the new position to the ArrayList of ChessPositions to check for threefold repetition later
            positions.add(board.generateChessPosition());

            // Now opposite player's turn
            flipSide();

            return true; // Successful move
        } else
            return false; // Unsuccessful move
    }

    /**
     * <p>Returns the number of rows in the chessboard.</p>
     *
     * @return  the number of rows in the chessboard
     * @since 1.0
     */
    @Override
    public int getNumRows() {
        return 8;
    }

    /**
     * <p>Returns the number of columns in the chessboard.</p>
     *
     * @return  the number of columns in the chessboard
     * @since 1.0
     */
    @Override
    public int getNumColumns() {
        return 8;
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
        KingPiece king = ((KingPiece) centralPiece).getOpposingKings()[0];

        // Checks for ending conditions
        checkCheckmate(king);
        checkStalemate(king);
        checkInsufficientMaterial(king);
        checkThreefoldRepetition(king.getChessBoard());
        checkFiftyMoveRule(king.getChessBoard());
    }

    /**
     * <p>Checks for checkmate.</p>
     *
     * @param king  the king piece that is subject to checkmate
     * @since 1.0
     */
    private void checkCheckmate(KingPiece king) {
        if (cannotMove(king) && king.isInCheck())
            king.getChessBoard().terminate(ChessResult.CHECKMATE, king.getOpposingKings()[0].getSide());
    }

    /**
     * <p>Checks for stalemate.</p>
     *
     * @param king  the king piece that is subject to stalemate
     */
    private void checkStalemate(KingPiece king) {
        if (cannotMove(king) && !king.isInCheck())
            king.getChessBoard().terminate(ChessResult.STALEMATE, null);
    }

    /**
     * <p>Checks for draw by threefold repetition.</p>
     *
     * @param board  the chessboard
     * @since 1.0
     */
    private void checkThreefoldRepetition(ChessBoard board) {
        if (positions.size() >= 6 && Collections.frequency(positions, positions.get(positions.size() - 1)) >= 3)
            board.terminate(ChessResult.THREEFOLD_REPETITION, null);
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
     * <p>Checks for insufficient material.</p>
     * <p>There are only 4 cases for insufficient material:</p>
     * <p>King vs. King</p>
     * <p>King + Knight vs. King</p>
     * <p>King + Bishop vs. King</p>
     * <p>King + Bishop vs. King + Bishop of same color</p>
     *
     * @param king  the king of the side that is currently playing
     * @since 1.0
     */
    private void checkInsufficientMaterial(KingPiece king) {
        // Stores the all of the chess pieces on the board
        ArrayList<ChessPiece> pieces = new ArrayList<>();

        // Iterates for each square of the chess board in search of a piece
        for (int i = 0; i < getNumRows(); i++) {
            for (int j = 0; j < getNumColumns(); j++) {
                //Looks for same side piece in the chess board
                if (king.getChessBoard().hasPiece(i, j))
                    pieces.add(king.getChessBoard().getPiece(i, j));
            }
        }

        // Checks if it's just bare kings
        if (pieces.size() == 2) {
            king.getChessBoard().terminate(ChessResult.INSUFFICIENT_MATERIAL, null);
        }

        // Looks for insufficient material. All insufficient material must have less than 4 pieces on the board
        if (pieces.size() <= 4) {
            // Stores the all of the knight pieces on the board
            ArrayList<KnightPiece> knights = new ArrayList<>();
            // Stores the all of the bishop pieces on the board
            ArrayList<BishopPiece> bishops = new ArrayList<>();

            //Looks for pieces that are knights or bishops
            for (ChessPiece piece : pieces) {
                if (piece instanceof BishopPiece)
                    bishops.add((BishopPiece) piece);
                else if (piece instanceof KnightPiece)
                    knights.add((KnightPiece) piece);
            }

            // If there are not no more 2 knights and bishops, then that must mean that there are pieces other than knights or bishops. In that case, there is not insufficient material
            if (knights.size() + bishops.size() <= 2) {
                // King + Bishop vs. King + Bishop of same color is insufficient material
                if (pieces.size() == 3 || (bishops.size() == 2 && bishops.get(0).isDarkSquared() == bishops.get(1).isDarkSquared()))
                    king.getChessBoard().terminate(ChessResult.INSUFFICIENT_MATERIAL, null);
            }
        }
    }

    /**
     * <p>Promotes the <code>ChessPiece</code> to the desired <code>ChessPiece</code> type.</p>
     *
     * @param oldPiece  the <code>ChessPiece</code> to be replaced
     * @param newPiece  the <code>ChessPiece</code> type to be replaced with
     * @since 1.0
     */
    @Override
    public void promote(ChessPiece oldPiece, ChessPiece newPiece) {
        oldPiece.getChessBoard().addPiece(newPiece, oldPiece.getRow(), oldPiece.getColumn());
    }

    /**
     * <p>Starts a game of Indo-European chess in the North-South orientation.</p>
     *
     * @param chessBoard    the chess board
     * @since 1.0
     */
    @Override
    public void startGame(ChessBoard chessBoard) {
        // Pawns
        for (int i = 0; i < 8; i++) {
            chessBoard.addPiece(new PawnPiece(ChessGame.Side.SOUTH, chessBoard, ChessIcon.WHITE_PAWN, 6, i), 6, i);
            chessBoard.addPiece(new PawnPiece(ChessGame.Side.NORTH, chessBoard, ChessIcon.BLACK_PAWN, 1, i), 1, i);
        }

        // Rooks
        chessBoard.addPiece(new RookPiece(ChessGame.Side.SOUTH, chessBoard, ChessIcon.WHITE_ROOK, 7, 0), 7, 0);
        chessBoard.addPiece(new RookPiece(ChessGame.Side.SOUTH, chessBoard, ChessIcon.WHITE_ROOK, 7, 7), 7, 7);
        chessBoard.addPiece(new RookPiece(ChessGame.Side.NORTH, chessBoard, ChessIcon.BLACK_ROOK, 0, 0), 0, 0);
        chessBoard.addPiece(new RookPiece(ChessGame.Side.NORTH, chessBoard, ChessIcon.BLACK_ROOK, 0, 7), 0, 7);

        // Knights
        chessBoard.addPiece(new KnightPiece(ChessGame.Side.SOUTH, chessBoard, ChessIcon.WHITE_KNIGHT, 7, 1), 7, 1);
        chessBoard.addPiece(new KnightPiece(ChessGame.Side.SOUTH, chessBoard, ChessIcon.WHITE_KNIGHT, 7, 6), 7, 6);
        chessBoard.addPiece(new KnightPiece(ChessGame.Side.NORTH, chessBoard, ChessIcon.BLACK_KNIGHT, 0, 1), 0, 1);
        chessBoard.addPiece(new KnightPiece(ChessGame.Side.NORTH, chessBoard, ChessIcon.BLACK_KNIGHT, 0, 6), 0, 6);

        // Bishops
        chessBoard.addPiece(new BishopPiece(ChessGame.Side.SOUTH, chessBoard, ChessIcon.WHITE_BISHOP, 7, 2), 7, 2);
        chessBoard.addPiece(new BishopPiece(ChessGame.Side.SOUTH, chessBoard, ChessIcon.WHITE_BISHOP, 7, 5), 7, 5);
        chessBoard.addPiece(new BishopPiece(ChessGame.Side.NORTH, chessBoard, ChessIcon.BLACK_BISHOP, 0, 2), 0, 2);
        chessBoard.addPiece(new BishopPiece(ChessGame.Side.NORTH, chessBoard, ChessIcon.BLACK_BISHOP, 0, 5), 0, 5);

        // Queens
        chessBoard.addPiece(new QueenPiece(ChessGame.Side.SOUTH, chessBoard, ChessIcon.WHITE_QUEEN, 7, 3), 7, 3);
        chessBoard.addPiece(new QueenPiece(ChessGame.Side.NORTH, chessBoard, ChessIcon.BLACK_QUEEN, 0, 3), 0, 3);

        // Kings
        chessBoard.addPiece(new KingPiece(ChessGame.Side.SOUTH, chessBoard, ChessIcon.WHITE_KING, 7, 4), 7, 4);
        chessBoard.addPiece(new KingPiece(ChessGame.Side.NORTH, chessBoard, ChessIcon.BLACK_KING, 0, 4), 0, 4);
    }
}
