package development;

/**
 * <p>Represents the ability to make an en passant move in chess.</p>
 *
 * @author Nobel Zhou (nxz157)
 * @version 1.0, 12/2/20
 */
public interface CanEnPassantMove extends CanMove {
    /**
     * <p>Returns a boolean representing if the proposed row and column is a valid en passant move from the piece's location.</p>
     * <p>The pawn must be 4 rows from the opposing side.</p>
     * <p>The pawn must also be right next to a pawn that just moved 2 spaces.</p>
     *
     * @param row       the piece's destination row
     * @param column    the piece's destination column
     * @param cp        the chess piece
     * @return          <code>true</code> if the proposed location is a valid en passant move
     * @since 1.0
     */
    default boolean isValidEnPassantMove(int row, int column, ChessPiece cp) {
        // Checks to see if the proposed move is within the chess board bounds
        if (row >= cp.getChessBoard().getGameRules().getNumRows() || column >= cp.getChessBoard().getGameRules().getNumColumns())
            return false;

        // Checks if pawn is in the correct column and row
        switch (cp.getSide()) {
            case SOUTH:
                // Checks if the pawn is in the correct row for en passant to happen
                if (cp.getRow() != 3)
                    return false;
                break;
            case NORTH:
                // Checks if the pawn is in the correct row for en passant to happen
                if (cp.getRow() != cp.getChessBoard().getGameRules().getNumRows() - 4)
                    return false;
                break;
            case WEST:
                // Checks if the pawn is in the correct column for en passant to happen
                if (cp.getColumn() != cp.getChessBoard().getGameRules().getNumColumns() - 4)
                    return false;
                break;
            default: //East
                // Checks if the pawn is in the correct column for en passant to happen
                if (cp.getColumn() != 3)
                    return false;
        }

        // Checks to see if the proposed move is diagonally from the pawn in the forward direction
        switch (cp.getSide()) {
            case SOUTH:
                if (row != cp.getRow() - 1 || (column != cp.getColumn() + 1 && column != cp.getColumn() - 1))
                    return false;
                break;
            case NORTH:
                if (row != cp.getRow() + 1 || (column != cp.getColumn() + 1 && column != cp.getColumn() - 1))
                    return false;
                break;
            case WEST: // ern     Get it? Ok I'll leave
                if (column != cp.getColumn() + 1 || (row != cp.getRow() + 1 && row != cp.getRow() - 1))
                    return false;
                break;
            default: // East
                if (column != cp.getColumn() - 1 || (row != cp.getRow() + 1 && row != cp.getRow() - 1))
                    return false;
        }

        // Stores the pawn to be captured by en passant
        PawnPiece pawn;

        // Checks to see if an opposing pawn is right next to the piece
        try {
            switch (cp.getSide()) {
                case NORTH:
                case SOUTH:
                    if (column == cp.getColumn() - 1) {
                        // Checks if the piece exists
                        if (!cp.getChessBoard().hasPiece(cp.getRow(), cp.getColumn() - 1))
                            return false;
                        else
                            pawn = (PawnPiece) cp.getChessBoard().getPiece(cp.getRow(), cp.getColumn() - 1); // Stores it into pawn for further examination
                    } else {
                        // Checks if the piece exists and if it is a pawn
                        if (!cp.getChessBoard().hasPiece(cp.getRow(), cp.getColumn() + 1))
                            return false;
                        else
                            pawn = (PawnPiece) cp.getChessBoard().getPiece(cp.getRow(), cp.getColumn() + 1); // Stores it into pawn for further examination
                    }
                    break;
                default: // West and East
                    if (row == cp.getRow() - 1) {
                        // Checks if the piece exists and if it is a pawn
                        if (!cp.getChessBoard().hasPiece(cp.getRow() - 1, cp.getColumn()))
                            return false;
                        else
                            pawn = (PawnPiece) cp.getChessBoard().getPiece(cp.getRow() - 1, cp.getColumn()); // Stores it into pawn for further examination
                    } else {
                        //Checks if the piece exists and if it is a pawn
                        if (!cp.getChessBoard().hasPiece(cp.getRow() + 1, cp.getColumn()))
                            return false;
                        else
                            pawn = (PawnPiece) cp.getChessBoard().getPiece(cp.getRow() + 1, cp.getColumn()); // Stores it into pawn for further examination
                    }
            }
        } catch (ClassCastException e) {
            return false; // Piece is not a PawnPiece
        }

        // Checks to see if the pawn just moved and it moved 2 spaces
        return (pawn.getNumMoves() == 1 && pawn.isJustMoved());
    }

    /**
     * <p>Returns a <code>PawnPiece</code> that the en passant move captures.</p>
     * <p>Assumes that the move is a valid en passant move.</p>
     *
     * @param row       the pawn piece's destination row
     * @param column    the pawn piece's destination column
     * @param cp        the chess piece
     * @return          the pawn that is captured during the en passant move
     * @since 1.0
     * @throws UnsupportedOperationException    if the move is not a valid en passant move
     */
    default PawnPiece getCapturedEnPassantPawn(int row, int column, ChessPiece cp) throws UnsupportedOperationException {
        // Checks if the direction is north or south
        try {
            switch (cp.getSide()) {
                case NORTH:
                case SOUTH:
                    return (PawnPiece) cp.getChessBoard().getPiece(cp.getRow(), ((column == cp.getColumn() - 1)) ? cp.getColumn() - 1 : cp.getColumn() + 1);
                default: // West and East
                    return (PawnPiece) cp.getChessBoard().getPiece((row == cp.getRow() - 1) ? cp.getRow() - 1 : cp.getRow() + 1, cp.getColumn());
            }
        } catch (ClassCastException e) {
            throw new UnsupportedOperationException("Unsupported operation for invalid en passant moves. Please make sure that the move is a valid en passant move.");
        }
    }
}
