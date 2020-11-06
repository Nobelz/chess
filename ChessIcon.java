import javax.swing.ImageIcon;
import javax.swing.Icon;
import java.awt.*;
import javax.imageio.*;
import java.io.InputStream;

/**
 * Represents a chess piece icon.
 * All image icons for the chess pieces are attributed to user CBurnett from Wikimedia Commons, which can be found <a href="https://commons.wikimedia.org/wiki/Category:PNG_chess_pieces/Standard_transparent">here</a>, licensed under the <a href="https://creativecommons.org/licenses/by-sa/3.0/deed.en">Creative Commons Attribution-Share Alike 3.0 Unported</a> license.
 * @author Nobel Zhou (nxz157)
 * @version 2.0, 10/30/20
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
    
    /* FIELDS */
    //Stores the icon itself
    private ImageIcon icon = null;
    
    /* CONSTRUCTORS */
    
    /**
     * Creates a ChessIcon from an Icon.
     * @param icon  The icon
     * @since 1.0
     */
    private ChessIcon(String fileName) {
        try {
            icon = new ImageIcon(ImageIO.read(Main.class.getResourceAsStream("/Assets/" + fileName)));
        } catch (Exception e) {}
    }
    
    /* METHODS */
    
    /**
     * Paints the chess icon.
     * @since 1.0
     */
    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        icon.paintIcon(c, g, x, y);
    }
    
    /**
     * Returns an int representing the chess icon's height.
     * @return  The height of the chess icon
     * @since 1.0
     */
    @Override
    public int getIconHeight() {
        return icon.getIconHeight();
    }
    
    /**
     * Returns an int representing the chess icon's width.
     * @return  The width of the chess icon
     * @since 1.0
     */
    @Override
    public int getIconWidth() {
        return icon.getIconWidth();
    }
}
