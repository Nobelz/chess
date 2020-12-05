import javax.swing.*;
import java.awt.*;

/**
 * <p>Represents the rules for how we want a board to display for a game of Indo-European chess.</p>
 *
 * @author Harold Connamacher
 * @author Nobel Zhou (nxz157)
 * @version 1.0, 12/3/2020
 */
public class SwingEuropeanChessDisplay implements SwingChessBoardDisplay {

    //region FIELDS
    // Stores the primary color of the chessboard
    public static Color primaryColor = new Color(184, 139, 74);

    // Stores the secondary color of the checkerboard
    public static Color secondaryColor = new Color(227, 193, 111);

    // Stores the color of the SOUTH player
    public static Color southPlayerColor = Color.yellow;

    // Stores the color of the NORTH player
    public static Color northPlayerColor = Color.green;

    // Stores the color of the EAST player
    public static Color eastPlayerColor = Color.white;

    // Stores the color of the WEST player
    public static Color westPlayerColor = Color.gray;

    // Stores the color to highlight a square
    public static Color highlightColor = Color.blue;
    //endregion

    //region METHODS
    /**
     * <p>Displays a square that has no piece on it. </p>
     *
     * @param button    the button that is used for the chessboard square
     * @param row       the row of this square on the board
     * @param column    the column of this square on the board
     * @since 1.0
     */
    public void displayEmptySquare(JButton button, int row, int column) {
        button.setBackground((row + column) % 2 == 0 ? primaryColor : secondaryColor);
        button.setText(null);
        button.setIcon(null);
    }

    /**
     * <p>Displays a square that has a piece on it.</p>
     *
     * @param button    the button that is used for the chessboard square
     * @param row       the row of this square on the board
     * @param column    the column of this square on the board
     * @param piece     the piece that is on this square
     * @since 1.0
     */
    public void displayFilledSquare(JButton button, int row, int column, ChessPiece piece) {
        Color pieceColor;

        switch (piece.getSide()) {
            case NORTH:
                pieceColor = northPlayerColor;
                break;
            case SOUTH:
                pieceColor = southPlayerColor;
                break;
            case EAST:
                pieceColor = eastPlayerColor;
                break;
            default:
                pieceColor = westPlayerColor;
        }

        button.setBackground(pieceColor);
        button.setIcon(((ChessIcon) piece.getIcon()).getImageIcon());
    }

    /**
     * <p>Highlights a square of the board.</p>
     *
     * @param highlight <code>true</code> if the highlight is supposed to be on
     * @param button    the button that is used for the chessboard square
     * @param row       the row of this square on the board
     * @param column    the column of this square on the board
     * @since 1.0
     */
    public void highlightSquare(boolean highlight, JButton button, int row, int column, ChessPiece piece) {
        if (highlight) {
            button.setBackground(highlightColor);
        } else if (piece == null)
            displayEmptySquare(button, row, column);
        else
            displayFilledSquare(button, row, column, piece);
    }

    /**
     * <p>Returns if all of the possible moves should be displayed for a <code>ChessPiece</code>.</p>
     *
     * @return  <code>true</code> if all of the possible moves should be displayed
     * @since 1.0
     */
    @Override
    public boolean shouldDisplayPossibleMoves() {
        return true;
    }
    //endregion
}