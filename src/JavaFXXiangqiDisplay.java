import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * <p>Represents the rules for how we want a board to display for a game of Xiangqi.</p>
 *
 * @author Nobel Zhou (nxz157)
 * @version 1.0, 12/5/2020
 */
public class JavaFXXiangqiDisplay implements JavaFXChessBoardDisplay {

    //region NESTED TYPES
    /**
     * <p>Represents a Xiangqi square image.</p>
     *
     * @author Nobel Zhou (nxz157)
     * @version 1.0, 12/5/2020
     */
    private enum XiangqiSquare {
        XIANGQI_BOTTOM_EDGE("XiangqiBottomEdge.png"),
        XIANGQI_BOTTOM_LEFT_CORNER("XiangqiBottomLeftCorner.png"),
        XIANGQI_BOTTOM_LEFT_PALACE_CORNER("XiangqiBottomLeftPalaceCorner.png"),
        XIANGQI_BOTTOM_LEFT_PALACE_CORNER_EDGE("XiangqiBottomLeftPalaceCornerEdge.png"),
        XIANGQI_BOTTOM_RIGHT_CORNER("XiangqiBottomRightCorner.png"),
        XIANGQI_BOTTOM_RIGHT_PALACE_CORNER("XiangqiBottomRightPalaceCorner.png"),
        XIANGQI_BOTTOM_RIGHT_PALACE_CORNER_EDGE("XiangqiBottomRightPalaceCornerEdge.png"),
        XIANGQI_BOTTOM_RIVER("XiangqiBottomRiver.png"),
        XIANGQI_CENTER_PALACE("XiangqiCenterPalace.png"),
        XIANGQI_LEFT_EDGE("XiangqiLeftEdge.png"),
        XIANGQI_NORMAL("XiangqiNormal.png"),
        XIANGQI_RIGHT_EDGE("XiangqiRightEdge.png"),
        XIANGQI_TOP_EDGE("XiangqiTopEdge.png"),
        XIANGQI_TOP_LEFT_CORNER("XiangqiTopLeftCorner.png"),
        XIANGQI_TOP_LEFT_PALACE_CORNER("XiangqiTopLeftPalaceCorner.png"),
        XIANGQI_TOP_LEFT_PALACE_CORNER_EDGE("XiangqiTopLeftPalaceCornerEdge.png"),
        XIANGQI_TOP_RIGHT_CORNER("XiangqiTopRightCorner.png"),
        XIANGQI_TOP_RIGHT_PALACE_CORNER("XiangqiTopRightPalaceCorner.png"),
        XIANGQI_TOP_RIGHT_PALACE_CORNER_EDGE("XiangqiTopRightPalaceCornerEdge.png"),
        XIANGQI_TOP_RIVER("XiangqiTopRiver.png"),
        XIANGQI_TOP_PALACE("XiangqiTopPalace.png"),
        XIANGQI_BOTTOM_PALACE("XiangqiBottomPalace.png"),
        XIANGQI_TOP_PALACE_EDGE("XiangqiTopPalaceEdge.png"),
        XIANGQI_BOTTOM_PALACE_EDGE("XiangqiBottomPalaceEdge.png"),
        XIANGQI_LEFT_PALACE("XiangqiLeftPalace.png"),
        XIANGQI_RIGHT_PALACE("XiangqiRightPalace.png");

        // Stores the XiangqiSquare as a BackgroundImage
        private BackgroundImage image;

        // Stores the side length of the xiangqi square image, set to 1/20 the width of the screen
        private final int size = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width / 20;

        /**
         * <p>Creates a <code>XiangqiSquare</code> from a file name.</p>
         *
         * @param fileName  the file name of the xiangqi square image
         * @since 1.0
         */
        XiangqiSquare(String fileName) {
            image = new BackgroundImage(new Image("/images/xiangqi_squares/" + fileName, size, size, false, true), null, null, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        }

        /**
         * <p>Returns an <code>BackgroundImage</code> representing the xiangqi square's image.</p>
         *
         * @return  the xiangqi square's image
         * @since 1.0
         */
        public BackgroundImage getImage() {
            return image;
        }
    }
    //endregion

    //region FIELDS
    // Stores the color to highlight a square
    public static Color highlightColor = Color.rgb(0, 0, 255, 0.3);

    // Stores the images used to paint the chessboard
    private final XiangqiSquare[][] squares = {
            {XiangqiSquare.XIANGQI_TOP_LEFT_CORNER, XiangqiSquare.XIANGQI_TOP_EDGE, XiangqiSquare.XIANGQI_TOP_EDGE,
                    XiangqiSquare.XIANGQI_TOP_LEFT_PALACE_CORNER_EDGE, XiangqiSquare.XIANGQI_TOP_PALACE_EDGE,
                    XiangqiSquare.XIANGQI_TOP_RIGHT_PALACE_CORNER_EDGE, XiangqiSquare.XIANGQI_TOP_EDGE,
                    XiangqiSquare.XIANGQI_TOP_EDGE, XiangqiSquare.XIANGQI_TOP_RIGHT_CORNER},
            {XiangqiSquare.XIANGQI_LEFT_EDGE, XiangqiSquare.XIANGQI_NORMAL, XiangqiSquare.XIANGQI_NORMAL,
                    XiangqiSquare.XIANGQI_LEFT_PALACE, XiangqiSquare.XIANGQI_CENTER_PALACE, XiangqiSquare.XIANGQI_RIGHT_PALACE,
                    XiangqiSquare.XIANGQI_NORMAL, XiangqiSquare.XIANGQI_NORMAL, XiangqiSquare.XIANGQI_RIGHT_EDGE},
            {XiangqiSquare.XIANGQI_LEFT_EDGE, XiangqiSquare.XIANGQI_NORMAL, XiangqiSquare.XIANGQI_NORMAL,
                    XiangqiSquare.XIANGQI_BOTTOM_LEFT_PALACE_CORNER, XiangqiSquare.XIANGQI_BOTTOM_PALACE,
                    XiangqiSquare.XIANGQI_BOTTOM_RIGHT_PALACE_CORNER, XiangqiSquare.XIANGQI_NORMAL,
                    XiangqiSquare.XIANGQI_NORMAL, XiangqiSquare.XIANGQI_RIGHT_EDGE},
            {XiangqiSquare.XIANGQI_LEFT_EDGE, XiangqiSquare.XIANGQI_NORMAL, XiangqiSquare.XIANGQI_NORMAL,
                    XiangqiSquare.XIANGQI_NORMAL, XiangqiSquare.XIANGQI_NORMAL, XiangqiSquare.XIANGQI_NORMAL,
                    XiangqiSquare.XIANGQI_NORMAL, XiangqiSquare.XIANGQI_NORMAL, XiangqiSquare.XIANGQI_RIGHT_EDGE},
            {XiangqiSquare.XIANGQI_LEFT_EDGE, XiangqiSquare.XIANGQI_TOP_RIVER, XiangqiSquare.XIANGQI_TOP_RIVER,
                    XiangqiSquare.XIANGQI_TOP_RIVER, XiangqiSquare.XIANGQI_TOP_RIVER, XiangqiSquare.XIANGQI_TOP_RIVER,
                    XiangqiSquare.XIANGQI_TOP_RIVER, XiangqiSquare.XIANGQI_TOP_RIVER, XiangqiSquare.XIANGQI_RIGHT_EDGE},
            {XiangqiSquare.XIANGQI_LEFT_EDGE, XiangqiSquare.XIANGQI_BOTTOM_RIVER, XiangqiSquare.XIANGQI_BOTTOM_RIVER,
                    XiangqiSquare.XIANGQI_BOTTOM_RIVER, XiangqiSquare.XIANGQI_BOTTOM_RIVER, XiangqiSquare.XIANGQI_BOTTOM_RIVER,
                    XiangqiSquare.XIANGQI_BOTTOM_RIVER, XiangqiSquare.XIANGQI_BOTTOM_RIVER, XiangqiSquare.XIANGQI_RIGHT_EDGE},
            {XiangqiSquare.XIANGQI_LEFT_EDGE, XiangqiSquare.XIANGQI_NORMAL, XiangqiSquare.XIANGQI_NORMAL,
                    XiangqiSquare.XIANGQI_NORMAL, XiangqiSquare.XIANGQI_NORMAL, XiangqiSquare.XIANGQI_NORMAL,
                    XiangqiSquare.XIANGQI_NORMAL, XiangqiSquare.XIANGQI_NORMAL, XiangqiSquare.XIANGQI_RIGHT_EDGE},
            {XiangqiSquare.XIANGQI_LEFT_EDGE, XiangqiSquare.XIANGQI_NORMAL, XiangqiSquare.XIANGQI_NORMAL,
                    XiangqiSquare.XIANGQI_TOP_LEFT_PALACE_CORNER, XiangqiSquare.XIANGQI_TOP_PALACE,
                    XiangqiSquare.XIANGQI_TOP_RIGHT_PALACE_CORNER, XiangqiSquare.XIANGQI_NORMAL,
                    XiangqiSquare.XIANGQI_NORMAL, XiangqiSquare.XIANGQI_RIGHT_EDGE},
            {XiangqiSquare.XIANGQI_LEFT_EDGE, XiangqiSquare.XIANGQI_NORMAL, XiangqiSquare.XIANGQI_NORMAL,
                    XiangqiSquare.XIANGQI_LEFT_PALACE, XiangqiSquare.XIANGQI_CENTER_PALACE, XiangqiSquare.XIANGQI_RIGHT_PALACE,
                    XiangqiSquare.XIANGQI_NORMAL, XiangqiSquare.XIANGQI_NORMAL, XiangqiSquare.XIANGQI_RIGHT_EDGE},
            {XiangqiSquare.XIANGQI_BOTTOM_LEFT_CORNER, XiangqiSquare.XIANGQI_BOTTOM_EDGE, XiangqiSquare.XIANGQI_BOTTOM_EDGE,
                    XiangqiSquare.XIANGQI_BOTTOM_LEFT_PALACE_CORNER_EDGE, XiangqiSquare.XIANGQI_BOTTOM_PALACE_EDGE,
                    XiangqiSquare.XIANGQI_BOTTOM_RIGHT_PALACE_CORNER_EDGE, XiangqiSquare.XIANGQI_BOTTOM_EDGE,
                    XiangqiSquare.XIANGQI_BOTTOM_EDGE, XiangqiSquare.XIANGQI_BOTTOM_RIGHT_CORNER}
    };

    // Stores the highlight background image
    private final BackgroundImage highlightImage = new BackgroundImage(new Image("/images/xiangqi_squares/Highlight.png", getSquareSize(), getSquareSize(), false, true), null, null, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
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
        button.setBackground(new Background(squares[row][column].getImage()));
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
        button.setBackground(new Background(squares[row][column].getImage()));
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
        ArrayList<BackgroundImage> images = new ArrayList<>(button.getBackground().getImages());
        images.add(highlightImage);

        if (highlight) {
            button.setBackground(new Background(fills, images));
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
