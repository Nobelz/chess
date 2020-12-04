import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * <p>Represents a <code>ChessBoard</code>> that uses Java Swing implementation for Indo-European chess.</p<
 *
 * @author Nobel Zhou (nxz157)
 * @version 1.0, 12/3/2020
 */
public class SwingEuropeanChessBoard extends SwingChessBoard {

    //region CONSTRUCTORS
    /**
     * <p>Creates a Swing Indo-European chessboard using the given display and game rules.</p>
     *
     * @param boardDisplay the display rules for the Swing chessboard
     * @param gameRules    the game rules for the Swing chessboard
     * @since 1.0
     */
    public SwingEuropeanChessBoard(SwingEuropeanChessDisplay boardDisplay, EuropeanChess gameRules) {
        super(boardDisplay, gameRules);
    }
    //endregion

    //region METHODS
    /**
     * <p>Displays promotion window by letting user choose from <code>ChessPieces</code> that are <code>Promotable</code>.</p>
     *
     * @param piece the piece to be promoted
     * @since 1.0
     */
    @Override
    public void invokePromotion(ChessPiece piece) {
        // Runs code on separate thread so it doesn't block the Event Dispatch or main threads
        new Thread(() -> {
            //Stores the promotion pieces used for JFrame
            ChessPiece[] promotionPieces = new ChessPiece[]{
                    new KnightPiece(piece.getSide(), piece.getChessBoard(), (piece.getSide().equals(piece.getChessBoard().getGameRules().getStartingSide()) ? ChessIcon.WHITE_KNIGHT : ChessIcon.BLACK_KNIGHT), -1, -1),
                    new BishopPiece(piece.getSide(), piece.getChessBoard(), (piece.getSide().equals(piece.getChessBoard().getGameRules().getStartingSide()) ? ChessIcon.WHITE_BISHOP : ChessIcon.BLACK_BISHOP), -1, -1),
                    new RookPiece(piece.getSide(), piece.getChessBoard(), (piece.getSide().equals(piece.getChessBoard().getGameRules().getStartingSide()) ? ChessIcon.WHITE_ROOK : ChessIcon.BLACK_ROOK), -1, -1),
                    new QueenPiece(piece.getSide(), piece.getChessBoard(), (piece.getSide().equals(piece.getChessBoard().getGameRules().getStartingSide()) ? ChessIcon.WHITE_QUEEN : ChessIcon.BLACK_QUEEN), -1, -1)
            };
            // Stores the JButtons used for JFrame
            JButton[] promotionSquares = new JButton[promotionPieces.length];

            // Stores the popup window
            JDialog promotionWindow = new JDialog(board);
            promotionWindow.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); // Disposes on close so we can then interact with main JFrame
            promotionWindow.setModal(true); // Stops actions in other JFrame until the popup window closes

            // Adds a method that sets the default promotion piece to queen if the user doesn't press the button
            promotionWindow.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    if (getPiece(piece.getRow(), piece.getColumn()).equals(piece)) // Checks if the pawn hasn't been promoted yet
                        getGameRules().promote(piece, promotionPieces[3]); // Promotes to queen
                }
            });

            // Stores the layout panel
            JPanel promotionPanel = new JPanel(new GridLayout(1, promotionPieces.length));

            for (int i = 0; i < promotionPieces.length; i++) {
                promotionSquares[i] = new JButton();
                // i needs to be final in order for us to use it in lambda expression
                int finalI = i;

                promotionSquares[i].addActionListener((e) -> {
                    getGameRules().promote(piece, promotionPieces[finalI]);
                    promotionWindow.dispose();
                });
                boardDisplay.displayFilledSquare(promotionSquares[i], -1, -1, promotionPieces[i]);
                promotionPanel.add(promotionSquares[i]);
            }

            promotionWindow.add(promotionPanel);
            promotionWindow.setSize(boardDisplay.getSquareSize() * promotionPieces.length, boardDisplay.getSquareSize() + 20);
            promotionWindow.setTitle("Pawn Promotion");

            promotionWindow.pack();
            promotionWindow.setVisible(true);
        }).start();
    }
    //endregion
}
