import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

/**
 * <p>Represents a chess piece icon.</p>
 *
 * @author Nobel Zhou (nxz157)
 * @version 3.0, 12/4/20
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
    private ImageIcon imageIcon = null;

    // Stores the icon in an Image, to be used with JavaFX implementations
    private Image image = null;

    // Stores the side length of the chess icon, set to 1/20 the width of the screen
    private final int size = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width / 20;
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
            imageIcon = new ImageIcon(ImageIO.read(getClass().getResource("/images/" + fileName)).getScaledInstance(size, size, java.awt.Image.SCALE_SMOOTH));
            image = new Image("/images/" + fileName, size * 0.75, size * 0.75, false, true);
        } catch (Exception ignored) {}
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
        imageIcon.paintIcon(c, g, x, y);
    }

    /**
     * <p>Returns an int representing the chess icon's height.</p>
     *
     * @return  the height of the chess icon
     * @since 1.0
     */
    @Override
    public int getIconHeight() {
        return imageIcon.getIconHeight();
    }

    /**
     * <p>Returns an int representing the chess icon's width.</p>
     *
     * @return  the width of the chess icon
     * @since 1.0
     */
    @Override
    public int getIconWidth() {
        return imageIcon.getIconWidth();
    }

    /**
     * <p>Returns an <code>Image</code> representing the icon's image.</p>
     * <p>Used for JavaFX implementations.</p>
     *
     * @return  the icon's image
     * @since 2.0
     */
    public Image getImage() {
        return image;
    }

    /**
     * <p>Returns an <code>ImageIcon</code> representing the icon's image.</p>
     * <p>Used for Swing implementations.</p>
     *
     * @return  the icon's image
     * @since 2.0
     */
    public ImageIcon getImageIcon() {
        return imageIcon;
    }
    //endregion
}
