import java.util.ArrayList;

/**
 * <p>Creates a European chessboard in a window on the desktop.  The EuropeanChessBoard has a EuropeanChessDisplay object that determines
 * how the individual squares of the chessboard should be drawn.</p>
 * 
 * <p>The chessboard uses a EuropeanChess object to determine how the game should be played.  The way the chessboard works
 * is as follows.  The player selects a piece by clicking on the board, and
 * and the chessboard calls the <tt>legalPieceToPlay</tt> method of the EuropeanChess object.
 * If the player is allowed to select the piece, the board highlights it, and the player can select another square on
 * the board.  The chessboard then calls the <tt>makeMove</tt> method of the EuropeanChess object.  The EuropeanChess is 
 * responsible for determining if the move is valid, and if it is to update the game and the chessboard
 * with the results of making that move.</p>
 * 
 * <p>The European chessboard also provides functionality that handles for stalemate and checkmate. It also can handle castling, capture moves, and non-capture moves.</p>
 * 
 * @author Nobel Zhou (nxz157)
 * @version 1.0, 10/30/20
 */
public class EuropeanChessBoard extends ChessBoard{
    /**
     * Builds a board of the desired size, the display parameters, and the rules for the European chess game.
     * @param numRows       The number of rows for the chessboard
     * @param numColumns    The number of columns for the chessboard
     * @param boardDisplay  An object that determines how the squares on the chessboard should be drawn
     * @param gameRules     An object that determines when player selection is valid and to update the game with the result of a move
     */
    public EuropeanChessBoard(final int numRows, final int numColumns, EuropeanChessDisplay boardDisplay, EuropeanChess gameRules) {
        super(numRows, numColumns, boardDisplay, gameRules);
    }
    
    /**
     * Returns the European chess rules of the game.
     * @return The European chess rules of the game
     * @since 1.0
     */
    @Override
    public EuropeanChess getGameRules() {
        return (EuropeanChess) super.getGameRules();
    }
    
    /**
     * Runs when a move results in a check.
     * @param king    The king that is in check
     * @since 1.0
     */
    public void afterCheck(KingPiece king) {
        System.out.println("Check");
        //Checks if it's checkmate
        if (!(getGameRules()).isAbleToMove(king))
            terminate(EuropeanChess.Result.CHECKMATE, king.getSide());
    }
  
    /**
     * Checks for stalemate.
     * @param king    The king of the side that is currently playing
     * @since 1.0
     */
    private void checkStalemate(KingPiece king) {
        if (!(getGameRules()).isAbleToMove(king))
            terminate(EuropeanChess.Result.STALEMATE, null);
    }
    
    /**
     * Checks for insufficient material.
     * There are only 4 cases for insufficient material:
     * King + King
     * 
     * @param king    The king of the side that is currently playing
     * @since 1.0
     */
    private void checkInsufficientMaterial() {
        //Stores the all of the chess pieces on the board
        ArrayList<ChessPiece> pieces = new ArrayList<>();
        
        //Iterates for each square of the chess board in search of a piece
        for (int i = 0; i < numRows(); i++) {
            for (int j = 0; j < numColumns(); j++) {
                //Looks for same side piece in the chess board
                if (hasPiece(i, j))
                    pieces.add(getPiece(i, j));
            }
        }
        
        //Checks if it's just bare kings
        if (pieces.size() == 2) {
            terminate(EuropeanChess.Result.INSUFFICIENT_MATERIAL, null);
        }
        
        //Looks for insufficient material. All insufficient material must have less than 4 pieces on the board
        if (pieces.size() <= 4) {
            //Stores the all of the knight pieces on the board
            ArrayList<KnightPiece> knights = new ArrayList<>(); 
            //Stores the all of the biushop pieces on the board
            ArrayList<BishopPiece> bishops = new ArrayList<>(); 
            
            //Looks for pieces that are knights or bishops
            for (ChessPiece piece : pieces) {
                if (piece instanceof BishopPiece)
                    bishops.add((BishopPiece) piece);
                else if (piece instanceof KnightPiece)
                    knights.add((KnightPiece) piece);
            }
            
            //If there are not no more 2 knights and bishops, then that must mean that there are pieces other than knights or bishops. In that case, there is not insufficient material
            if (knights.size() + bishops.size() <= 2) {
                if (pieces.size() == 3) //King + Knight vs. King or King + Bishop vs. King is insufficient material
                    terminate(EuropeanChess.Result.INSUFFICIENT_MATERIAL, null);
                else if (bishops.size() == 2 && bishops.get(0).isDarkSquared() == bishops.get(1).isDarkSquared()) //King + Bishop vs. King + Bishop of same color is insufficient material
                    terminate(EuropeanChess.Result.INSUFFICIENT_MATERIAL, null);
            }
        }
    }
    
    /**
     * Runs when a capture move is played. This includes en passant.
     * @param king    The king of the side that is currently playing
     * @since 1.0
     */
    public void afterCapture(KingPiece king) {
        System.out.println("Capture");
        checkStalemate(king);
        checkInsufficientMaterial();
    }
  
    /**
     * Runs when a castling move is played.
     * @param king    The king of the side that is currently playing
     * @since 1.0
     */
    public void afterCastling(KingPiece king) {
        System.out.println("Castle");
        checkStalemate(king);
    }
  
    /**
     * Runs when a normal, non-capture move is played.
     * @param king    The king of the side that is currently playing
     * @since 1.0
     */
    public void afterNonCapture(KingPiece king) {
        System.out.println("Non-Capture");
        checkStalemate(king);
    }
  
    /**
     * Ends the chess game.
     * @param result    The result of the chess game
     * @param side      The side that possibly won the chess game
     * @since 1.0
     */
    private void terminate(EuropeanChess.Result result, ChessGame.Side side) {
        System.out.println(result);
        board.dispose();
    }
}
