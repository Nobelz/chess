/**
 * Represents the ability to make a straight move in chess.
 *
 * @author Nobel Zhou (nxz157)
 * @version 1.0, 10/30/20
 */
public interface StraightMove extends NormalMove, CheckMove {
    /**
     * Returns a boolean representing if the proposed row and column is a valid straight move from the piece's location, assuming that the proposed location is unoccupied.
     * @param row       The piece's destination row
     * @param column    The piece's destination column
     * @param cp        The chess piece
     * @return          Whether the proposed location is a valid straight move, assuming it is unoccupied
     * @since 1.0
     */
    public default boolean isValidStraightNonCaptureMove(int row, int column, ChessPiece cp) {
        //Checks to make sure that it's a valid square first and that is a horizontal or vertical square
        if (isValidNonCaptureMove(row, column, cp) && (row == cp.getRow() || column == cp.getColumn())) {
            //Checks that all of the squares up to it are empty
            return checkEmptyStraightMove((row == cp.getRow()) ? row : (row > cp.getRow()) ? row - 1 : row + 1, (column == cp.getColumn()) ? column : (column > cp.getColumn()) ? column - 1 : column + 1, cp);
        } else
            return false; //Not a valid diagonal move
    }
    
    /**
     * Returns a boolean representing if the proposed row and column is a valid straight move from the piece's location, assuming that the proposed location is occupied.
     * @param row       The piece's destination row
     * @param column    The piece's destination column
     * @param cp        The chess piece
     * @return          Whether the proposed location is a valid straight move, assuming it is unoccupied
     * @since 1.0
     */
    public default boolean isValidStraightCaptureMove(int row, int column, ChessPiece cp) {
        //Checks to make sure that it's a valid square first and that is a horizontal or vertical square
        if (isValidCaptureMove(row, column, cp) && (row == cp.getRow() || column == cp.getColumn())) {
            //Checks if the move won't result in check and that all of the squares up to it are empty
            return checkEmptyStraightMove((row == cp.getRow()) ? row : (row > cp.getRow()) ? row - 1 : row + 1, (column == cp.getColumn()) ? column : (column > cp.getColumn()) ? column - 1 : column + 1, cp);
        } else
            return false; //Not a valid diagonal square
    }
    
    /**
     * Returns a boolean representing if the proposed row and column and all the squares up to the piece are empty for a straight move.
     * @param row       The piece's destination row
     * @param column    The piece's destination column
     * @param cp        The chess piece
     * @return          Whether the proposed location and all the squares up to the piece are empty for a straight move
     * @since 1.0
     */
    public default boolean checkEmptyStraightMove(int row, int column, ChessPiece cp) {
        if (row == cp.getRow()) { //Horizontal movement
            if (column > cp.getColumn()) { //Right movement
                //Iterates for each square, starting at right and going left
                for (int i = column; i > cp.getColumn(); i--) {
                    //If not a valid non-capture move, return false
                    if (!isValidNonCaptureMove(row, i, cp))
                        return false;
                }
                
                return true;
            } else { //Left movement
                //Iterates for each square, starting at left and going right
                for (int i = column; i < cp.getColumn(); i++) {
                    //If not a valid non-capture move, return false
                    if (!isValidNonCaptureMove(row, i, cp))
                        return false;
                }
                
                return true;
            }
        } else { //Vertical movement
            if (row > cp.getRow()) { //Down movement
                //Iterates for each square, starting at down and going up
                for (int i = row; i > cp.getRow(); i--) {
                    //If not a valid non-capture move, return false
                    if (!isValidNonCaptureMove(i, column, cp))
                        return false;
                }
                
                return true;
            } else { //Up movement
                //Iterates for each square, starting at up and going down
                for (int i = row; i < cp.getRow(); i++) {
                    //If not a valid non-capture move, return false
                    if (!isValidNonCaptureMove(i, column, cp))
                        return false;
                }
                
                return true;
            }
        }
    }
}
