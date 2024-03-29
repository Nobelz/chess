/**
 * <p>Represents a chess piece.</p>
 * <p>Dictates properties of the chess piece as well as how it moves.</p>
 *
 * @author Nobel Zhou (nxz157)
 * @version 2.0, 12/2/2020
 */
public abstract class ChessPiece {

    //region FIELDS
    /**
     * Stores the piece's affiliation to which side.
     */
    private final ChessGame.Side side;

    /**
     * Stores the label of the piece.
     */
    private final String label;

    /**
     * Stores the graphics icon or image of the piece and how it should be drawn onto the chess board.
     */
    private final Object icon;

    /**
     * Stores the row of the piece.
     */
    private int row;

    /**
     * Stores the column of the piece.
     */
    private int column;

    /**
     * Stores the chess board the piece is on.
     */
    private final ChessBoard chessBoard;

    /**
     * Stores the number of moves the chess piece has done.
     */
    private int numMoves;

    /**
     * Stores if the piece moved last turn.
     */
    private boolean justMoved;
    //endregion

    //region NESTED TYPES
    /**
     * <p>Represents a proposed move that allows the program to properly execute that move.</p>
     *
     * @author Nobel Zhou (nxz157)
     * @version 1.0, 12/2/2020
     */
    protected static class ProposedMove {
        /**
         * Stores the piece to be moved.
         */
        private final ChessPiece movedPiece;

        /**
         * Stores the piece to be captured, if any.
         */
        private final ChessPiece removedPiece;

        /**
         * Stores the new row of the piece to be moved.
         */
        private final int row;

        /**
         * Stores the new column of the piece to be moved.
         */
        private final int column;

        /**
         * Stores if the move can be reversible (can the move be manually reversed?).
         */
        private final boolean isReversible;

        /**
         * <p>Creates a <code>ProposedMove</code> object from 2 pieces and a proposed location.</p>
         *
         * @param movedPiece    the proposed move's primary piece to be moved
         * @param removedPiece  the proposed move's piece to be removed or captured
         * @param row           the new row of the piece to be moved
         * @param column        the new column of the piece to be moved
         * @param isReversible  if the proposed move is reversible
         * @since 1.0
         */
        public ProposedMove(ChessPiece movedPiece, ChessPiece removedPiece, int row, int column, boolean isReversible) {
            this.movedPiece = movedPiece;
            this.removedPiece = removedPiece;
            this.row = row;
            this.column = column;
            this.isReversible = isReversible;
        }

        /**
         * <p>Returns the piece that is going to be moved.</p>
         *
         * @return  the piece to be moved
         * @since 1.0
         */
        public ChessPiece getMovedPiece() {
            return movedPiece;
        }

        /**
         * <p>Returns the piece that is going to be removed or captured, if any.</p>
         *
         * @return  the piece to be captured, <code>null</code> if no capture
         * @since 1.0
         */
        public ChessPiece getRemovedPiece() {
            return removedPiece;
        }

        /**
         * <p>Returns the row of the proposed move.</p>
         *
         * @return  the new row
         * @since 1.0
         */
        public int getRow() {
            return row;
        }

        /**
         * <p>Returns the column of the proposed move.</p>
         *
         * @return  the new column
         */
        public int getColumn() {
            return column;
        }

        /**
         * <p>Returns a boolean representing if the proposed move is reversible.</p>
         *
         * @return  <code>true</code> if the proposed move is reversible
         */
        public boolean isReversible() {
            return isReversible;
        }
    }
    //endregion

    //region CONSTRUCTORS
    /**
     * <p>Initializes a chess piece based on the given side, label, icon, chessboard, and location.</p>
     *
     * @param side       the chess piece's side
     * @param label      the chess piece's label name
     * @param icon       the chess piece's graphic icon
     * @param chessBoard the chessboard the chess piece is on
     * @param row        the chess piece's starting row
     * @param column     the chess piece's starting column
     * @since 1.0
     */
    public ChessPiece(ChessGame.Side side, String label, Object icon, ChessBoard chessBoard, int row, int column) {
        this.side = side;
        this.label = label;
        this.icon = icon;
        this.chessBoard = chessBoard;
        this.row = row;
        this.column = column;
        numMoves = 0;
        justMoved = false;
    }
    //endregion

    //region ABSTRACT METHODS
    /**
     * <p>Checks if the proposed move is legal, assuming that it is unoccupied.</p>
     *
     * @param row       the piece's destination row
     * @param column    the piece's destination column
     * @return          <code>true</code> if the piece's proposed move is legal, assuming it is unoccupied
     * @since 1.0
     */
    public abstract boolean isLegalNonCaptureMove(int row, int column);

    /**
     * <p>Checks if the proposed move is legal, assuming that it is occupied.</p>
     *
     * @param row       the piece's destination row
     * @param column    the piece's destination column
     * @return          <code>true</code> if the piece's proposed move is legal, assuming it is occupied
     * @since 1.0
     */
    public abstract boolean isLegalCaptureMove(int row, int column);
    //endregion

    //region NON-ABSTRACT METHODS
    /**
     * <p>Returns the amount of moves the piece has done.</p>
     *
     * @return  the number of moves
     * @since 1.0
     */
    public int getNumMoves() {
        return numMoves;
    }

    /**
     * <p>Returns if the <code>ChessPiece</code> moved last turn.</p>
     *
     * @return  if the piece just moved last turn
     * @since 1.0
     */
    public boolean isJustMoved() {
        return justMoved;
    }

    /**
     * <p>Sets the <code>justMoved</code> to <code>false</code>.</p>
     *
     * @since 1.0
     */
    public void resetJustMoved() {
        justMoved = false;
    }

    /**
     * <p>Returns a <code>ChessGame.Side</code> representing the side of the piece.</p>
     *
     * @return  the piece's side
     * @since 1.0
     */
    public ChessGame.Side getSide() {
        return side;
    }

    /**
     * <p>Returns a <code>String</code> representing the label of the piece.</p>
     *
     * @return  the piece's label
     * @since 1.0
     */
    public String getLabel() {
        return label;
    }

    /**
     * <p>Returns the graphic icon of the piece.</p>
     *
     * @return  the piece's graphic icon
     * @since 1.0
     */
    public Object getIcon() {
        return icon;
    }

    /**
     * <p>Sets the location of the piece.</p>
     * <p>Sets the row and column of the piece.</p>
     *
     * @param row    the piece's row number
     * @param column the piece's column number
     * @since 1.0
     */
    public void setLocation(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * <p>Returns a boolean representing if the piece's proposed move is legal or not.</p>
     *
     * @param toRow     the piece's destination row
     * @param toColumn  the piece's destination column
     * @return          <code>true</code> if the piece's proposed move is legal
     * @since 1.0
     */
    public boolean isLegalMove(int toRow, int toColumn) {
        //Checks if it's either a capture move or a non capture move
        return (isLegalCaptureMove(toRow, toColumn) && getChessBoard().hasPiece(toRow, toColumn) && getChessBoard().getPiece(toRow, toColumn).getSide() != getSide()) ||
                (isLegalNonCaptureMove(toRow, toColumn) && !getChessBoard().hasPiece(toRow, toColumn));
    }

    /**
     * Returns a <code>ChessBoard</code> object representing the chessboard the piece is on.
     *
     * @return  the chessboard the piece is on
     * @since 1.0
     */
    public ChessBoard getChessBoard() {
        return chessBoard;
    }

    /**
     * <p>Returns the current row of the piece.</p>
     *
     * @return  the piece's current row
     * @since 1.0
     */
    public int getRow() {
        return row;
    }

    /**
     * <p>Returns the current column of the piece.</p>
     *
     * @return  the piece's current column
     * @since 1.0
     */
    public int getColumn() {
        return column;
    }

    /**
     * <p>Handles any post-move processes once the move is completed.</p>
     * <p>Increments number of moves, and resets the <code>justMoved</code> of all of the pieces.</p>
     *
     * @since 1.0
     */
    public void moveDone() {
        // Adds 1 to the amount of moves the piece has done
        numMoves++;
        justMoved = true;

        // Sets the justMoved to false of every other piece
        for (int i = 0; i < getChessBoard().getGameRules().getNumRows(); i++) {
            for (int j = 0; j < getChessBoard().getGameRules().getNumColumns(); j++) {
                // Checks if a chess piece exists at a square
                if ((i != getRow() || j != getColumn()) && getChessBoard().hasPiece(i, j))
                    getChessBoard().getPiece(i, j).resetJustMoved(); // Resets the justMoved of the piece
            }
        }
    }

    /**
     * <p>Compares 2 <code>ChessPiece</code>s.</p>
     *
     * @return  <code>true</code> if the two chess pieces are equal
     * @since 1.0
     */
    @Override
    public boolean equals(Object o) {
        // Check typecast of 2 chess pieces
        if (o instanceof ChessPiece) {
            ChessPiece piece = (ChessPiece) o;

            // Check location, piece type, and side
            if (!piece.getLabel().equals(getLabel()) || !piece.getSide().equals(getSide()) || piece.getRow() != getRow() || piece.getColumn() != getColumn())
                return false;

            // Checks that all the same moves are available to be done
            for (int i = 0; i < getChessBoard().getGameRules().getNumRows(); i++) {
                for (int j = 0; j < getChessBoard().getGameRules().getNumColumns(); j++) {
                    if (isLegalMove(i, j) != piece.isLegalMove(i, j))
                        return false;
                }
            }

            // Checks for location
            return getRow() == piece.getRow() && getColumn() == piece.getColumn();
        } else
            return false;
    }

    /**
     * <p>Returns an array of <code>ChessPiece.ProposedMove</code> objects that shows how to move the pieces.</p>
     * <p>Will always return at least 1 <code>ChessPiece.ProposedMove</code>, but in the case of moves that require 2 or more pieces,
     * like castling moves, it might have more than 1; thus, this is an array.</p>
     *
     * @param row       the row of the move
     * @param column    the column of the move
     * @return          an array of <code>ChessPiece.ProposedMove</code> objects that show how to move the pieces
     * @since 1.0
     */
    public ProposedMove[] getMoveInstructions(int row, int column) {
        // Stores if the proposed move is reversible
        boolean reversible = (!getChessBoard().hasPiece(row, column) || getChessBoard().getPiece(row, column).getSide() == getSide());
        return new ProposedMove[] {new ProposedMove(this, getChessBoard().getPiece(row, column), row, column, reversible)};
    }
    //endregion
}
