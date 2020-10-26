/**
 * Runs a chess game.
 *
 * @author Nobel Zhou (nxz157)
 * @version 1.0, 10/30/20
 */
public class Main {
    /**
     * Main method that runs the chess game.
     * @param args  Arguments of main method 
     * @since 1.0
     */
    public static void main(String[] args) {
        ChessBoard chessBoard = new ChessBoard(8, 8, new EuropeanChessDisplay(), new EuropeanChess(ChessGame.Side.SOUTH));
        //Add pawns
        for (int i = 0; i < 8; i++) {
            chessBoard.addPiece(new PawnPiece(ChessGame.Side.SOUTH, chessBoard, 6, i), 6, i);
            chessBoard.addPiece(new PawnPiece(ChessGame.Side.NORTH, chessBoard, 1, i), 1, i);
        }
        //Add other pieces
        //Rooks
        chessBoard.addPiece(new RookPiece(ChessGame.Side.SOUTH, chessBoard, 7, 0), 7, 0);
        chessBoard.addPiece(new RookPiece(ChessGame.Side.SOUTH, chessBoard, 7, 7), 7, 7);
        chessBoard.addPiece(new RookPiece(ChessGame.Side.NORTH, chessBoard, 0, 0), 0, 0);
        chessBoard.addPiece(new RookPiece(ChessGame.Side.NORTH, chessBoard, 0, 7), 0, 7);
        
        //Knights
        chessBoard.addPiece(new KnightPiece(ChessGame.Side.SOUTH, chessBoard, 7, 1), 7, 1);
        chessBoard.addPiece(new KnightPiece(ChessGame.Side.SOUTH, chessBoard, 7, 6), 7, 6);
        chessBoard.addPiece(new KnightPiece(ChessGame.Side.NORTH, chessBoard, 0, 1), 0, 1);
        chessBoard.addPiece(new KnightPiece(ChessGame.Side.NORTH, chessBoard, 0, 6), 0, 6);
        
        //Bishops
        chessBoard.addPiece(new BishopPiece(ChessGame.Side.SOUTH, chessBoard, 7, 2), 7, 2);
        chessBoard.addPiece(new BishopPiece(ChessGame.Side.SOUTH, chessBoard, 7, 5), 7, 5);
        chessBoard.addPiece(new BishopPiece(ChessGame.Side.NORTH, chessBoard, 0, 2), 0, 2);
        chessBoard.addPiece(new BishopPiece(ChessGame.Side.NORTH, chessBoard, 0, 5), 0, 5);
        
        //Queens
        chessBoard.addPiece(new QueenPiece(ChessGame.Side.SOUTH, chessBoard, 7, 3), 7, 3);
        chessBoard.addPiece(new QueenPiece(ChessGame.Side.NORTH, chessBoard, 0, 3), 0, 3);
        
        //Kings
        chessBoard.addPiece(new KingPiece(ChessGame.Side.SOUTH, chessBoard, 7, 4), 7, 4);
        chessBoard.addPiece(new KingPiece(ChessGame.Side.NORTH, chessBoard, 0, 4), 0, 4);
        
    }
}
