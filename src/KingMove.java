/**
 * Represents the ability to make a single move in chess.
 * A single move is a move of one space in any direction, including diagonal.
 *
 * @author Nobel Zhou (nxz157)
 * @version 1.0, 10/30/20
 */
public interface KingMove extends NormalMove {
    /**
     * Returns a boolean representing if the proposed row and column is a valid king move from the piece's location, assuming that the proposed location is unoccupied.
     *
     * @param row    The piece's destination row
     * @param column The piece's destination column
     * @param cp     The chess piece
     * @return Whether the proposed location is a valid king move, assuming it is unoccupied
     * @since 1.0
     */
    default boolean isValidKingNonCaptureMove(int row, int column, ChessPiece cp) {
        //Checks to make sure that it's a valid unoccupied square and a single move away from the current square
        return isValidNonCaptureMove(row, column, cp) && (row - cp.getRow() <= 1 && cp.getRow() - row <= 1) && (column - cp.getColumn() <= 1 && cp.getColumn() - column <= 1);
    }

    /**
     * Returns a boolean representing if the proposed row and column is a valid king move from the piece's location, assuming that the proposed location is occupied.
     *
     * @param row    The piece's destination row
     * @param column The piece's destination column
     * @param cp     The chess piece
     * @return Whether the proposed location is a valid king move, assuming it is unoccupied
     * @since 1.0
     */
    default boolean isValidKingCaptureMove(int row, int column, ChessPiece cp) {
        //Checks to make sure that it's a valid occupied square and a single move away from the current square
        return isValidCaptureMove(row, column, cp) && (row - cp.getRow() <= 1 && cp.getRow() - row <= 1) && (column - cp.getColumn() <= 1 && cp.getColumn() - column <= 1);
    }
}
