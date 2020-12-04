package development;

import java.util.ArrayList;

/**
 * <p>Represents a king chess piece.</p>
 * <p>Dictates how a king can move.</p>
 *
 * @author Nobel Zhou
 * @version 1.0, 12/2/20
 */
public class KingPiece extends ChessPiece implements CanSingleMove, CanCastleMove {

    //region CONSTRUCTORS
    /**
     * <p>Initializes a king piece based on the given side, chess board, and location.</p>
     *
     * @param side          the king piece's side
     * @param chessBoard    the chess board the king piece is on
     * @param icon          the king piece's icon
     * @param row           the king piece's starting row
     * @param column        the king piece's starting column
     * @since 1.0
     */
    public KingPiece(ChessGame.Side side, ChessBoard chessBoard, ChessIcon icon, int row, int column) {
        super(side, "K", icon, chessBoard, row, column);
    }
    //endregion

    //region METHODS
    /**
     * <p>Returns a boolean representing if the king piece's proposed move is legal or not.</p>
     *
     * @param toRow     the king piece's destination row
     * @param toColumn  the king piece's destination column
     * @return          <code>true</code> if the king piece's proposed move is legal
     * @since 1.0
     */
    @Override
    public boolean isLegalMove(int toRow, int toColumn) {
        // Checks also if it's a legal castling move
        return super.isLegalMove(toRow, toColumn) || (isValidCastlingMove(toRow, toColumn, this) && !isInCheck());
    }

    /**
     * <p>Returns a boolean representing if the proposed move is legal, assuming that it is unoccupied.</p>
     *
     * @param row       the king piece's destination row
     * @param column    the king piece's destination column
     * @return          <code>true</code> if the king piece's proposed move is legal, assuming it is unoccupied
     * @since 1.0
     */
    @Override
    public boolean isLegalNonCaptureMove(int row, int column) {
        return isValidSingleMove(row, column, this);
    }

    /**
     * <p>Returns a boolean representing if the proposed move is legal, assuming that it is occupied.</p>
     *
     * @param row       the king piece's destination row
     * @param column    the king piece's destination column
     * @return          <code>true</code> if the king piece's proposed move is legal, assuming it is occupied
     * @since 1.0
     */
    @Override
    public boolean isLegalCaptureMove(int row, int column) {
        return isValidSingleMove(row, column, this);
    }

    /**
     * <p>Returns an array of <code>ChessPiece.ProposedMove</code> objects that shows how to move the king pieces.</p>
     * <p>Will always return at least 1 <code>ChessPiece.ProposedMove</code>, but in the case of moves that require 2 or more pieces,
     * like castling moves, it might have more than 1; thus, this is an array.</p>
     *
     * @param row       the row of the move
     * @param column    the column of the move
     * @return          an array of <code>ChessPiece.ProposedMove</code> objects that show how to move the king pieces
     * @since 1.0
     */
    @Override
    public ProposedMove[] getMoveInstructions(int row, int column) {
        if (isValidCastlingMove(row, column, this)) {
            // Stores the row of the rook move
            int rookRow = getRow();
            // Stores the column of the rook move
            int rookColumn = getColumn();
            // Stores the rook
            RookPiece rook = getRook(row, column, this);

            // Sets the proposed location of the rook
            switch (getSide()) {
                case NORTH:
                case SOUTH:
                    rookColumn = (getColumn() < rook.getColumn()) ? column - 1 : column + 1;
                    break;
                default: // West and East
                    rookRow = (getRow() < rook.getRow()) ? row - 1 : row + 1;
            }

            return new ProposedMove[] {
                    new ProposedMove(getRook(row, column, this), null, rookRow, rookColumn, true),
                    new ProposedMove(this, null, row, column, true)
            };
        } else
            return super.getMoveInstructions(row, column); // Regular king move
    }

    /**
     * <p>Returns a boolean representing if the king piece is currently in check.</p>
     *
     * @return  <code>true</code> if the king is currently in check
     * @since 1.0
     */
    public boolean isInCheck() {
        return getChessBoard().squareThreatened(getRow(), getColumn(), this);
    }

    /**
     * <p>Returns an array of <code>KingPiece</code>s representing the opposing kings.</p>
     * <p>There should be at least 1 opposing king, but in the event of 4 player chess, there will be more than 1;
     * thus, this returns an array.</p>
     *
     * @return  the opposing kings
     * @since 1.0
     */
    public KingPiece[] getOpposingKings() {
        // Stores all the opposing kings; note that since we don't know how many opposing kings there are, we have to use an ArrayList
        ArrayList<KingPiece> opposingKings = new ArrayList<>();

        // Iterates the chess board to look for the opposite side king piece
        for (int i = 0; i < getChessBoard().getGameRules().getNumRows(); i++) {
            for (int j = 0; j < getChessBoard().getGameRules().getNumColumns(); j++) {
                // Looks for opposite side king piece
                try {
                    if (!getChessBoard().getPiece(i, j).getSide().equals(getSide()))
                        opposingKings.add((KingPiece) getChessBoard().getPiece(i, j));
                } catch (Exception ignored) {} // Not a KingPiece or piece does not exist at that location; continue searching
            }
        }

        // Returns in array form
        return opposingKings.toArray(new KingPiece[0]);
    }
    //endregion
}
