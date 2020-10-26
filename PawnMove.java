/**
 * Represents the ability to make a pawn move in chess.
 * A pawn move is a move of one space (or two spaces if the pawn hasn't moved yet) in the forward direction.
 * A pawn can also only capture diagonally and cannot capture by going forward.
 *
 * @author Nobel Zhou (nxz157)
 * @version 1.0, 10/30/20
 */
public interface PawnMove extends NormalMove, CheckMove {
    /**
     * Returns a boolean representing if the proposed row and column is a valid straight move from the piece's location, assuming that the proposed location is unoccupied.
     * @param row       The piece's destination row
     * @param column    The piece's destination column
     * @param cp        The chess piece
     * @return          Whether the proposed location is a valid straight move, assuming it is unoccupied
     * @since 1.0
     */
    public default boolean isValidPawnNonCaptureMove(int row, int column, ChessPiece cp) {
        //Checks if the square is empty
        if (isValidNonCaptureMove(row, column, cp)) {
            //Checks the forward 1 space for each side
            switch (cp.getSide()) {
                case NORTH:
                    if (row == cp.getRow() + 1 && column == cp.getColumn())
                        return true;
                    break;
                case SOUTH:
                    if (row == cp.getRow() - 1 && column == cp.getColumn())
                        return true;
                    break;
                case WEST:
                    if (column == cp.getColumn() + 1 && row == cp.getRow())
                        return true;
                    break;
                default: //East
                    if (column == cp.getColumn() - 1 && row == cp.getRow())
                        return true;
                    break;
            }
            
            //Checks forward 2 spaces for each side if and only if the pawn hasn't been moved yet
            if (cp.getMoves() == 0) {
                switch (cp.getSide()) {
                    case NORTH:
                        if (row == cp.getRow() + 2 && column == cp.getColumn())
                            return isValidPawnNonCaptureMove(row - 1, column, cp); //Checks the space before it (closer to the square) to make sure that it is empty
                        break;
                    case SOUTH:
                        if (row == cp.getRow() - 2 && column == cp.getColumn())
                            return isValidPawnNonCaptureMove(row + 1, column, cp); //Checks the space before it (closer to the square) to make sure that it is empty
                        break;
                    case WEST:
                        if (column == cp.getColumn() + 2 && row == cp.getRow())
                            return isValidPawnNonCaptureMove(row, column - 1, cp); //Checks the space before it (closer to the square) to make sure that it is empty
                        break;
                    default: //East
                        if (column == cp.getColumn() - 2 && row == cp.getRow())
                            return isValidPawnNonCaptureMove(row, column + 1, cp); //Checks the space before it (closer to the square) to make sure that it is empty
                        break;
                }
            }
            
            return false; //Doesn't match any pawn move
        } else
            return false; //Square not empty or square doesn't exist
        
    }
    
    /**
     * Returns a boolean representing if the proposed row and column is a valid straight move from the piece's location, assuming that the proposed location is occupied.
     * @param row       The piece's destination row
     * @param column    The piece's destination column
     * @param cp        The chess piece
     * @return          Whether the proposed location is a valid straight move, assuming it is unoccupied
     * @since 1.0
     */
    public default boolean isValidPawnCaptureMove(int row, int column, ChessPiece cp) {
        //Checks if the square is occupied by opposing side
        if (isValidCaptureMove(row, column, cp)) {
            //Checks to see if the proposed move is diagonally from the pawn in the forward direction
            switch (cp.getSide()) {
                case NORTH:
                    return row == cp.getRow() + 1 && (column == cp.getColumn() + 1 || column == cp.getColumn() - 1);
                case SOUTH:
                    return row == cp.getRow() - 1 && (column == cp.getColumn() + 1 || column == cp.getColumn() - 1);
                case WEST:
                    return column == cp.getColumn() + 1 && (row == cp.getRow() + 1 || row == cp.getRow() - 1);
                default: //East
                    return column == cp.getColumn() - 1 && (row == cp.getRow() + 1 || row == cp.getRow() - 1);
            }
        } else
            return false;
    }
}
