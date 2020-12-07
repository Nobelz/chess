import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import java.util.ArrayList;

/**
 * <p>Represents the rules for how we want a board to display for a game of Indo-European chess.</p>
 *
 * @author Nobel Zhou (nxz157)
 * @version 1.0, 12/4/2020
 */
public class JavaFXEuropeanChessDisplay implements JavaFXChessBoardDisplay {

    //region FIELDS
    /**
     * Stores the primary color of the chessboard.
     */
    public static BackgroundFill primaryColorFill = new BackgroundFill(Color.rgb(184, 139, 74), CornerRadii.EMPTY, Insets.EMPTY);

    /**
     * Stores the secondary color of the checkerboard.
     */
    public static BackgroundFill secondaryColorFill = new BackgroundFill(Color.rgb(227, 193, 111), CornerRadii.EMPTY, Insets.EMPTY);

    /**
     * Stores the color to highlight a check square.
     */
    public static BackgroundFill checkFill = new BackgroundFill(Color.rgb(255, 0, 0, 0.3), CornerRadii.EMPTY, Insets.EMPTY);

    /**
     * Stores the color to highlight a square.
     */
    public static BackgroundFill highlightFill = new BackgroundFill(Color.rgb(0, 0, 255, 0.3), CornerRadii.EMPTY, Insets.EMPTY);
    //endregion

    //region METHODS
    /**
     * <p>Displays an empty square.</p>
     *
     * @param button the button that is used for the chessboard square
     * @param row    the row of this square on the board
     * @param column the column of this square on the board
     * @since 1.0
     */
    @Override
    public void displayEmptySquare(Button button, int row, int column) {
        button.setBackground(new Background((row + column) % 2 == 0 ? primaryColorFill : secondaryColorFill));
        button.setGraphic(null);
        button.resize(getSquareSize(), getSquareSize());
    }

    /**
     * <p>Displays a nonempty square.</p>
     * <p>Warning: <code>displayEmptySquare</code> must be called on the button before calling this method; otherwise,
     * this method will return a <code>NullPointerException</code></p>
     *
     * @param button the button that is used for the chessboard square
     * @param row    the row of this square on the board
     * @param column the column of this square on the board
     * @param piece  the piece that is on this square
     * @since 1.0
     */
    @Override
    public void displayFilledSquare(Button button, int row, int column, ChessPiece piece) {
        // Stores the background fills
        ArrayList<BackgroundFill> fills = new ArrayList<>(button.getBackground().getFills());

        // Removes the highlight
        fills.remove(highlightFill);

        // Checks if there should be a check highlight around the piece
        if (fills.remove(checkFill))
            button.setBackground(new Background(((row + column) % 2 == 0 ? primaryColorFill : secondaryColorFill), checkFill));
        else
            button.setBackground(new Background((row + column) % 2 == 0 ? primaryColorFill : secondaryColorFill));

        button.setGraphic(new ImageView((((ChessIcon) piece.getIcon()).getImage())));
        button.resize(getSquareSize(), getSquareSize());
    }

    /**
     * <p>Adds or removes a highlight from a square on the JavaFX chessboard.</p>
     *
     * @param highlight if the square should be highlighted or not
     * @param button    the button that is used for the chessboard square
     * @param row       the row of this square on the board
     * @param column    the column of this square on the board
     * @param piece     the piece (if any) that is on this square
     * @since 1.0
     */
    @Override
    public void highlightSquare(boolean highlight, Button button, int row, int column, ChessPiece piece) {
        ArrayList<BackgroundFill> fills = new ArrayList<>(button.getBackground().getFills());
        fills.add(highlightFill);

        if (highlight) {
            button.setBackground(new Background(fills.toArray(new BackgroundFill[0])));
        } else if (piece == null)
            displayEmptySquare(button, row, column);
        else
            displayFilledSquare(button, row, column, piece);
        button.resize(getSquareSize(), getSquareSize());
    }

    /**
     * <p>Highlights the central piece in red if it's in check.</p>
     *
     * @param highlight if the square should be highlighted or not
     * @param button    the button that is used for the chessboard square
     * @param row       the row of this square on the board
     * @param column    the column of this square on the board
     * @param piece     the central piece
     * @since 1.0
     */
    @Override
    public void highlightCheckSquare(boolean highlight, Button button, int row, int column, CenterPiece piece) {
        if (highlight) {
            ArrayList<BackgroundFill> fills = new ArrayList<>(button.getBackground().getFills());
            fills.add(checkFill);

            button.setBackground(new Background(fills.toArray(new BackgroundFill[0])));
        } else {
            button.setBackground(new Background((row + column) % 2 == 0 ? primaryColorFill : secondaryColorFill));
        }
    }

    /**
     * <p>Returns if all of the possible moves should be displayed for a <code>ChessPiece</code>.</p>
     *
     * @return <code>true</code> if all of the possible moves should be displayed
     * @since 1.0
     */
    @Override
    public boolean shouldDisplayPossibleMoves() {
        return true;
    }
    //endregion
}
