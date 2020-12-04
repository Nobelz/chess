import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

/**
 * <p>Represents a chess piece icon.</p>
 * <p></p>All image icons for the chess pieces are attributed to user CBurnett from Wikimedia Commons, which can be found
 * <a href="https://commons.wikimedia.org/wiki/Category:PNG_chess_pieces/Standard_transparent">here</a>,
 * licensed under the <a href="https://creativecommons.org/licenses/by-sa/3.0/deed.en">Creative Commons Attribution-Share Alike 3.0 Unported</a> license.</p>
 *
 * @author Nobel Zhou (nxz157)
 * @version 2.0, 12/2/20
 */
enum ChessIcon implements Icon {
    WHITE_KNIGHT("WhiteKnight.png"),
    WHITE_ROOK("WhiteRook.png"),
    WHITE_QUEEN("WhiteQueen.png"),
    WHITE_KING("WhiteKing.png"),
    WHITE_BISHOP("WhiteBishop.png"),
    WHITE_PAWN("WhitePawn.png"),
    BLACK_KNIGHT("BlackKnight.png"),
    BLACK_ROOK("BlackRook.png"),
    BLACK_QUEEN("BlackQueen.png"),
    BLACK_KING("BlackKing.png"),
    BLACK_BISHOP("BlackBishop.png"),
    BLACK_PAWN("BlackPawn.png");

    //region FIELDS
    // Stores the icon in an ImageIcon, to be used with Swing implementations
    private ImageIcon icon = null;
    //endregion

    //region CONSTRUCTORS
    /**
     * <p>Creates a <code>ChessIcon</code> from a file name.</p>
     *
     * @param fileName  the file name of the icon image
     * @since 1.0
     */
    ChessIcon(String fileName) {
        try {
            icon = new ImageIcon(ImageIO.read(getClass().getResource("/images/" + fileName)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //endregion

    //region METHODS
    /**
     * <p>Paints the chess icon.</p>
     *
     * @param c has properties useful for painting
     * @param g the graphics object
     * @param x the x coordinate
     * @param y the y coordinate
     * @since 1.0
     */
    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        icon.paintIcon(c, g, x, y);
    }

    /**
     * <p>Returns an int representing the chess icon's height.</p>
     *
     * @return  the height of the chess icon
     * @since 1.0
     */
    @Override
    public int getIconHeight() {
        return icon.getIconHeight();
    }

    /**
     * <p>Returns an int representing the chess icon's width.</p>
     *
     * @return  the width of the chess icon
     * @since 1.0
     */
    @Override
    public int getIconWidth() {
        return icon.getIconWidth();
    }
    //endregion
}
