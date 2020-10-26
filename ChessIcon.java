import javax.swing.ImageIcon;
import javax.swing.Icon;
import java.awt.*;

/**
 * Represents a chess piece icon.
 *
 * @author Nobel Zhou (nxz157)
 * @version 1.0, 10/30/20
 */
enum ChessIcon implements Icon {
    KNIGHT(new ImageIcon("Assets/WhiteKnight.png", "Knight")), 
    ROOK(new ImageIcon("Assets/WhiteRook.png")), 
    QUEEN(new ImageIcon("Assets/WhiteQueen.png")), 
    KING(new ImageIcon("Assets/WhiteKing.png")), 
    BISHOP(new ImageIcon("Assets/WhiteBishop.png")), 
    PAWN(new ImageIcon("Assets/WhitePawn.png"));
    
    private final ImageIcon icon;
    
    private ChessIcon(ImageIcon icon) {
        this.icon = icon;
    }
    
    /**
     * Paints the chess icon.
     * @since 1.0
     */
    public void paintIcon(Component c, Graphics g, int x, int y) {
        icon.paintIcon(c, g, x, y);
    }
    
    /**
     * Returns an int representing the chess icon's height.
     * @return  The height of the chess icon
     * @since 1.0
     */
    public int getIconHeight() {
        return icon.getIconHeight();
    }
    
    /**
     * Returns an int representing the chess icon's width.
     * @return  The width of the chess icon
     * @since 1.0
     */
    public int getIconWidth() {
        return icon.getIconWidth();
    }
}
