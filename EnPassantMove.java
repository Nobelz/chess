/**
 * Represents the ability to make an en passant move in chess.
 *
 * @author Nobel Zhou (nxz157)
 * @version 1.0, 10/30/20
 */
public interface EnPassantMove extends CheckMove {
    /**
     * Returns a boolean representing if the proposed row and column is a valid en passant move from the piece's location.
     * The pawn must be 4 rows from the opposing side.
     * The pawn must also be right next to a pawn that just moved 2 spaces.
     * @param row       The piece's destination row
     * @param column    The piece's destination column
     * @param cp        The chess piece
     * @return          Whether the proposed location is a valid en passant move
     * @since 1.0
     */
    public default boolean isValidEnPassantMove(int row, int column, ChessPiece cp) {
        //Checks to see if the proposed move is within the chess board bounds
        if (row >= cp.getChessBoard().numRows() || column >= cp.getChessBoard().numColumns())
            return false;
         
        //Checks if pawn is in the correct column and row
        switch (cp.getSide()) {
            case SOUTH:
                //Checks if the pawn is in the correct row for en passant to happen
                if (cp.getRow() != 3)
                    return false;
                break;
            case NORTH:
                //Checks if the pawn is in the correct row for en passant to happen
                if (cp.getRow() != cp.getChessBoard().numRows() - 4)
                    return false;
                break;
            case WEST:
                //Checks if the pawn is in the correct column for en passant to happen
                if (cp.getColumn() != cp.getChessBoard().numColumns() - 4)
                    return false;
                break;
            default: //East
                //Checks if the pawn is in the correct column for en passant to happen
                if (cp.getColumn() != 3)
                    return false;
                break;
        }
        
        //Checks to see if the proposed move is diagonally from the pawn in the forward direction
        switch (cp.getSide()) {
            case SOUTH:
                if (row != cp.getRow() - 1 || (column != cp.getColumn() + 1 && column != cp.getColumn() - 1))
                    return false;
                break;
            case NORTH:
                if (row != cp.getRow() + 1 || (column != cp.getColumn() + 1 && column != cp.getColumn() - 1))
                    return false;
                break;
            case WEST: //ern     Get it? Ok I'll leave
                if (column != cp.getColumn() + 1 || (row != cp.getRow() + 1 && row != cp.getRow() - 1))
                    return false;
                break;
            default: //East
                if (column != cp.getColumn() - 1 || (row != cp.getRow() + 1 && row != cp.getRow() - 1))
                    return false;
                break;
        }
        
        //Stores the pawn to be captured by en passant
        PawnPiece pawn;
        //Checks to see if an opposing pawn is right next to the piece
        switch (cp.getSide()) {
            case NORTH:
            case SOUTH:
                if (column == cp.getColumn() - 1) {
                    //Checks if the piece exists and if it is a pawn
                    if (!cp.getChessBoard().hasPiece(cp.getRow(), cp.getColumn() - 1) || !(cp.getChessBoard().getPiece(cp.getRow(), cp.getColumn() - 1) instanceof PawnPiece))
                        return false;
                    else
                        pawn = (PawnPiece) cp.getChessBoard().getPiece(cp.getRow(), cp.getColumn() - 1); //Stores it into pawn for further examination
                } else {
                    //Checks if the piece exists and if it is a pawn
                    if (!cp.getChessBoard().hasPiece(cp.getRow(), cp.getColumn() + 1) || !(cp.getChessBoard().getPiece(cp.getRow(), cp.getColumn() + 1) instanceof PawnPiece))
                        return false;
                    else
                        pawn = (PawnPiece) cp.getChessBoard().getPiece(cp.getRow(), cp.getColumn() + 1); //Stores it into pawn for further examination
                }
                break;
            default: //West and East
                if (row == cp.getRow() - 1) {
                    //Checks if the piece exists and if it is a pawn
                    if (!cp.getChessBoard().hasPiece(cp.getRow() - 1, cp.getColumn()) || !(cp.getChessBoard().getPiece(cp.getRow() - 1, cp.getColumn()) instanceof PawnPiece))
                        return false;
                    else
                        pawn = (PawnPiece) cp.getChessBoard().getPiece(cp.getRow() - 1, cp.getColumn()); //Stores it into pawn for further examination
                } else {
                    //Checks if the piece exists and if it is a pawn
                    if (!cp.getChessBoard().hasPiece(cp.getRow() + 1, cp.getColumn()) || !(cp.getChessBoard().getPiece(cp.getRow() + 1, cp.getColumn()) instanceof PawnPiece))
                        return false;
                    else
                        pawn = (PawnPiece) cp.getChessBoard().getPiece(cp.getRow() + 1, cp.getColumn()); //Stores it into pawn for further examination
                }
                break;
        }
        
        //Checks to see if the pawn just moved
        return (pawn.getMoves() == 1 && pawn.getCanEnPassant());
    }
}
