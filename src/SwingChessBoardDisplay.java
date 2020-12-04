import javax.swing.*;

/**
 * <p>Represents display rules for the Swing chessboard.</p>
 *
 * @author Harold Connamacher
 * @author Nobel Zhou (nxz157)
 * @version 1.0, 12/2/2020
 */
public interface SwingChessBoardDisplay {

    //region ABSTRACT METHODS
    /**
     * <p>Displays an empty square.</p>
     *
     * @param button    the button that is used for the chessboard square
     * @param row       the row of this square on the board
     * @param column    the column of this square on the board
     * @since 1.0
     */
    void displayEmptySquare(JButton button, int row, int column);

    /**
     * <p>Displays a nonempty square.</p>
     *
     * @param button    the button that is used for the chessboard square
     * @param row       the row of this square on the board
     * @param column    the column of this square on the board
     * @param piece     the piece that is on this square
     * @since 1.0
     */
    void displayFilledSquare(JButton button, int row, int column, ChessPiece piece);

    /**
     * <p>Adds or removes a highlight from a square on the Swing chessboard.</p>
     *
     * @param highlight     if the square should be highlighted or not
     * @param button        the button that is used for the chessboard square
     * @param row           the row of this square on the board
     * @param column        the column of this square on the board
     * @param piece         the piece (if any) that is on this square
     * @since 1.0
     */
    void highlightSquare(boolean highlight, JButton button, int row, int column, ChessPiece piece);
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