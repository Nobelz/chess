/**
 * <p>Represents a move in chess.</p>
 *
 * @author Nobel Zhou (nxz157)
 * @version 1.0
 */
public class ChessMove {

    //region FIELDS
    // Stores the chess piece to be moved
    private final ChessPiece piece;
    // Stores the destination row
    private final int row;
    // Stores the destination column
    private final int column;
    //endregion

    //region CONSTRUCTORS
    /**
     * <p>Initializes a <code>ChessMove</code> from a given piece, row, and column.</p>
     *
     * @param piece     the chess piece
     * @param row       the destination row
     * @param column    the destination column
     * @since 1.0
     */
    public ChessMove(ChessPiece piece, int row, int column) {
        this.piece = piece;
        this.row = row;
        this.column = column;
    }
    //endregion

    //region METHODS
    /**
     * <p>Returns the <code>ChessPiece</code> of the move.</p>
     *
     * @return  the move's chess piece
     * @since 1.0
     */
    public ChessPiece getPiece() {
        return piece;
    }

    /**
     * <p>Returns an int representing the destination row.</p>
     *
     * @return  the destination row of the move
     * @since 1.0
     */
    public int getRow() {
        return row;
    }

    /**
     * <p>Returns an int representing the destination column.</p>
     *
     * @return  the destination column of the move
     * @since 1.0
     */
    public int getColumn() {
        return column;
    }
    //endregion
}
