/**
 * Represents a position in chess.
 * Used for determining 3 move repetition rule.
 *
 * @author Nobel Zhou (nxz157)
 * @version 1.0, 10/30/20
 */
public class ChessPosition {

    /* FIELDS */
    //Stores the board of the position
    private final ChessPiece[][] board;
    //Stores the side of the position
    private final ChessGame.Side player;

    /* CONSTRUCTORS */

    /**
     * Initializes a chess position to the board and player.
     *
     * @param board  The chess board
     * @param player The side of the chess player
     * @since 1.0
     */
    public ChessPosition(final ChessPiece[][] board, final ChessGame.Side player) {
        this.board = board;
        this.player = player;
    }

    /* METHODS */

    /**
     * Returns the 2-dimensional array of GamePieces.
     *
     * @return The 2-dimensional array of GamePieces
     * @since 1.0
     */
    public ChessPiece[][] getBoard() {
        return board;
    }

    /**
     * Returns the player's side of the game position.
     *
     * @return The player's side in the game position
     * @since 1.0
     */
    public ChessGame.Side getPlayer() {
        return player;
    }

    /**
     * Compares 2 ChessPositions.
     * Returns true if the two boards are the same and the side are equal.
     * Assumes a rectangular array.
     *
     * @param o The other chess position
     * @return If the chess positions are equal
     * @since 1.0
     */
    @Override
    public boolean equals(Object o) {
        //Checks if o can be typecasted into ChessPosition
        if (o instanceof ChessPosition) {
            ChessPosition position = (ChessPosition) o;
            //Checks for same dimensions
            if (position.getBoard().length != getBoard().length || position.getBoard()[0].length != getBoard()[0].length)
                return false;

            for (int i = 0; i < getBoard().length; i++) {
                for (int j = 0; j < getBoard()[i].length; j++) {
                    if (getBoard()[i][j] != null) {
                        //Checks if the pieces are the same
                        if (!getBoard()[i][j].equals(position.getBoard()[i][j]))
                            return false;
                    } else {
                        //Checks if both squares are empty
                        if (position.getBoard()[i][j] != null)
                            return false;
                    }
                }
            }

            //Check the same side
            return getPlayer().equals(position.getPlayer());
        } else
            return false; //Not ChessPosition object
    }
}
