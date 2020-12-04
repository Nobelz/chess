package development;

/**
 * <p>Placeholder class to run the Java Swing Indo-European chessboard.</p>
 *
 * @author Nobel Zhou (nxz157)
 * @version 1.0, 12/3/2020
 */
public class Main {
    /**
     * <p>Main method.</p>
     *
     * @param args  main method arguments
     * @since 1.0
     */
    public static void main(String[] args) {
        EuropeanChess game = new EuropeanChess(ChessGame.Side.SOUTH);
        game.startGame(new SwingEuropeanChessBoard(new SwingEuropeanChessDisplay(), game));
    }
}
