import javax.swing.*;
import java.awt.*;

/**
 * Rules for how we want a board to display for a game of European chess
 *
 * @author Harold Connamacher
 */
public class EuropeanChessDisplay implements ChessBoardDisplay {

    /**
     * The primary color of the checkerboard
     */
    public static Color redColor = Color.red;

    /**
     * The secondary color of the checkerboard
     */
    public static Color blackColor = Color.black;

    /* The color of the SOUTH player */
    public static Color southPlayerColor = Color.yellow;

    /* The color of the NORTH player */
    public static Color northPlayerColor = Color.green;

    /* The color of the EAST player */
    public static Color eastPlayerColor = Color.white;

    /* The color of the WEST player */
    public static Color westPlayerColor = Color.gray;

    /**
     * The color used to highlight a square
     */
    public static Color highlightColor = Color.blue;

    /**
     * The initial size of a square on the chess board, 1/20 the width of the screen
     *
     * @return the size of a square
     */
    @Override
    public int getSquareSize() {
        return java.awt.Toolkit.getDefaultToolkit().getScreenSize().width / 20;
    }

    /**
     * Display a square that has no piece on it.  Will produce a red/black checkerboard.
     *
     * @param button the button that is used for the chessboard square
     * @param row    the row of this square on the board
     * @param column the column of this square on the board
     */
    public void displayEmptySquare(JButton button, int row, int column) {
        button.setBackground((row + column) % 2 == 0 ? blackColor : redColor);
        button.setText(null);
        button.setIcon(null);
    }

    /**
     * Display a square that has a piece on it.
     *
     * @param button the button that is used for the chessboard square
     * @param row    the row of this square on the board
     * @param column the column of this square on the board
     * @param piece  the piece that is on this square
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
        // button.setText(piece.getLabel());
        button.setIcon((Icon) piece.getIcon());
    }

    /**
     * Highlight a square of the board.
     *
     * @param highlight do we want the highlight on (true) or off (false)?
     * @param button    the button that is used for the chessboard square
     * @param row       the row of this square on the board
     * @param column    the column of this square on the board
     */
    public void highlightSquare(boolean highlight, JButton button, int row, int column, ChessPiece piece) {
        if (highlight) {
            button.setBackground(highlightColor);
        } else if (piece == null)
            displayEmptySquare(button, row, column);
        else
            displayFilledSquare(button, row, column, piece);
    }
}