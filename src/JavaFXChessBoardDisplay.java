import javafx.scene.control.Button;

/**
 * <p>Represents display rules for the JavaFX chessboard.</p>
 *
 * @author Nobel Zhou (nxz157)
 * @version 1.0, 12/4/2020
 */
public interface JavaFXChessBoardDisplay {

    //region ABSTRACT METHODS
    /**
     * <p>Displays an empty square.</p>
     *
     * @param button    the button that is used for the chessboard square
     * @param row       the row of this square on the board
     * @param column    the column of this square on the board
     * @since 1.0
     */
    void displayEmptySquare(Button button, int row, int column);

    /**
     * <p>Displays a nonempty square.</p>
     *
     * @param button    the button that is used for the chessboard square
     * @param row       the row of this square on the board
     * @param column    the column of this square on the board
     * @param piece     the piece that is on this square
     * @since 1.0
     */
    void displayFilledSquare(Button button, int row, int column, ChessPiece piece);

    /**
     * <p>Adds or removes a highlight from a square on the JavaFX chessboard.</p>
     *
     * @param highlight     if the square should be highlighted or not
     * @param button        the button that is used for the chessboard square
     * @param row           the row of this square on the board
     * @param column        the column of this square on the board
     * @param piece         the piece (if any) that is on this square
     * @since 1.0
     */
    void highlightSquare(boolean highlight, Button button, int row, int column, ChessPiece piece);

    /**
     * <p>Highlights the central piece in red if it's in check.</p>
     *
     * @param highlight     if the square should be highlighted or not
     * @param button        the button that is used for the chessboard square
     * @param row           the row of this square on the board
     * @param column        the column of this square on the board
     * @param piece         the central piece
     * @since 1.0
     */
    void highlightCheckSquare(boolean highlight, Button button, int row, int column, CenterPiece piece);
    //endregion

    //region DEFAULT METHODS
    /**
     * <p>Returns the initial size of a square on the chess board, initially set to 1/20 of the width of the screen.</p>
     *
     * @return  the size of a square
     * @since 1.0
     */
    default int getSquareSize() {
        return java.awt.Toolkit.getDefaultToolkit().getScreenSize().width / 20;
    }

    /**
     * <p>Returns if all of the possible moves should be displayed for a <code>ChessPiece</code>.</p>
     *
     * @return  <code>true</code> if all of the possible moves should be displayed
     * @since 1.0
     */
    default boolean shouldDisplayPossibleMoves() {
        return false;
    }
    //endregion
}
