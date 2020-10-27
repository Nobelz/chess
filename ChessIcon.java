import javax.swing.ImageIcon;
import javax.swing.Icon;
import java.awt.*;

/**
 * Represents a chess piece icon.
 * All image icons for the chess pieces are attributed to user CBurnett from Wikimedia Commons, which can be found <a href="https://commons.wikimedia.org/wiki/Category:PNG_chess_pieces/Standard_transparent">here</a>, licensed under the <a href="https://creativecommons.org/licenses/by-sa/3.0/deed.en">Creative Commons Attribution-Share Alike 3.0 Unported</a> license.
 * @author Nobel Zhou (nxz157)
 * @version 2.0, 10/30/20
 */
enum ChessIcon implements Icon {
    WHITE_KNIGHT(new ImageIcon("Assets/WhiteKnight.png")), 
    WHITE_ROOK(new ImageIcon("Assets/WhiteRook.png")), 
    WHITE_QUEEN(new ImageIcon("Assets/WhiteQueen.png")), 
    WHITE_KING(new ImageIcon("Assets/WhiteKing.png")), 
    WHITE_BISHOP(new ImageIcon("Assets/WhiteBishop.png")), 
    WHITE_PAWN(new ImageIcon("Assets/WhitePawn.png")),
    BLACK_KNIGHT(new ImageIcon("Assets/BlackKnight.png")), 
    BLACK_ROOK(new ImageIcon("Assets/BlackRook.png")), 
    BLACK_QUEEN(new ImageIcon("Assets/BlackQueen.png")), 
    BLACK_KING(new ImageIcon("Assets/BlackKing.png")), 
    BLACK_BISHOP(new ImageIcon("Assets/BlackBishop.png")), 
    BLACK_PAWN(new ImageIcon("Assets/BlackPawn.png"));
    
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
