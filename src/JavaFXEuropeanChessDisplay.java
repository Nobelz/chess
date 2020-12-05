import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Represents the rules for how we want a board to display for a game of Indo-European chess.</p>
 *
 * @author Nobel Zhou (nxz157)
 * @version 1.0, 12/4/2020
 */
public class JavaFXEuropeanChessDisplay implements JavaFXChessBoardDisplay {

    //region FIELDS
    // Stores the primary color of the chessboard
    public static Color primaryColor = Color.rgb(184, 139, 74);

    // Stores the secondary color of the checkerboard
    public static Color secondaryColor = Color.rgb(227, 193, 111);

    // Stores the color of the SOUTH player
    public static Color southPlayerColor = Color.YELLOW;

    // Stores the color of the NORTH player
    public static Color northPlayerColor = Color.GREEN;

    // Stores the color of the EAST player
    public static Color eastPlayerColor = Color.WHITE;

    // Stores the color of the WEST player
    public static Color westPlayerColor = Color.GRAY;

    // Stores the color to highlight a square
    public static Color highlightColor = Color.rgb(0, 0, 255, 0.3);
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
        button.setBackground(new Background(new BackgroundFill((row + column) % 2 == 0 ? primaryColor : secondaryColor, CornerRadii.EMPTY, Insets.EMPTY)));
        button.setText(null);
        button.setGraphic(null);
        button.resize(getSquareSize(), getSquareSize());
    }

    /**
     * <p>Displays a nonempty square.</p>
     *
     * @param button the button that is used for the chessboard square
     * @param row    the row of this square on the board
     * @param column the column of this square on the board
     * @param piece  the piece that is on this square
     * @since 1.0
     */
    @Override
    public void displayFilledSquare(Button button, int row, int column, ChessPiece piece) {
        button.setBackground(new Background(new BackgroundFill((row + column) % 2 == 0 ? primaryColor : secondaryColor, CornerRadii.EMPTY, Insets.EMPTY)));
        button.setText(null);
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
        fills.add(new BackgroundFill(highlightColor, CornerRadii.EMPTY, Insets.EMPTY));

        if (highlight) {
            if (piece == null)
                button.setBackground(new Background(fills.toArray(new BackgroundFill[0])));
            else {
                button.setBackground(new Background(fills.toArray(new BackgroundFill[0])));
                button.setGraphic(new ImageView((((ChessIcon) piece.getIcon()).getImage())));
            }
        } else if (piece == null)
            displayEmptySquare(button, row, column);
        else
            displayFilledSquare(button, row, column, piece);
        button.resize(getSquareSize(), getSquareSize());
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
