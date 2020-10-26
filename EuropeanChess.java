import java.util.ArrayList;

/**
 * Represents a game of European chess.
 *
 * @author Nobel Zhou (nxz157)
 * @version 1.0, 10/30/20
 */
public class EuropeanChess implements ChessGame {
    
    /** 
     * The result of the game.
     * Can only be checkmate, stalemate, or insufficient material.
     * @since 1.0
     */
    public enum Result {CHECKMATE, STALEMATE, INSUFFICIENT_MATERIAL};
    
    /* FIELDS */
    //Stores the starting side; cannot be changed
    private final ChessGame.Side startingSide;
    //Stores the side that is currently playing
    private ChessGame.Side currentSide;
    
    /* CONSTRUCTORS */
    /**
     * Initializes European chess rules.
     * @param startingSide  The side who starts
     * @since 1.0
     */
    public EuropeanChess(ChessGame.Side startingSide) {
        this.startingSide = startingSide;
        currentSide = startingSide;
    }
    
    /* METHODS */
    /**
     * Flips the side so the other side is playing.
     * @since 1.0
     */
    private void flipSide() {
        //Flips the side to the opposite side
        switch(currentSide) {
            case NORTH:
                currentSide = ChessGame.Side.SOUTH;
                break;
            case SOUTH:
                currentSide = ChessGame.Side.NORTH;
                break;
            case WEST:
                currentSide = ChessGame.Side.EAST;
                break;
            default: //East
                currentSide = ChessGame.Side.WEST;
                break;
        }
    }
    
    /**
     * Returns a ChessGame.Side representing the side that is currently playing.
     * @return  The side that is currently playing
     * @since 1.0
     */
    private ChessGame.Side getCurrentSide() {
        return currentSide;
    }
    
    /** 
     * Determines if it is legal to play a given piece.
     * Only returns true if the piece is on the same side and if the piece has any legal moves to play.
     * @param piece   the piece to be played
     * @param row     the row of the square the piece is on
     * @param column  the column of the square the piece is on
     * @return true if the piece is allowed to move on this turn
     */
    public boolean legalPieceToPlay(ChessPiece piece, int row, int column) {
        //Checks if the piece is the correct side
        if (!piece.getSide().equals(currentSide))
            return false;
        
        //Checks for a legal move throughout the whole chess board
        for (int i = 0; i < piece.getChessBoard().numRows(); i++) {
            for (int j = 0; j < piece.getChessBoard().numColumns(); j++) {
                //Checks if the piece has a legal move at the row and column specified
                if ((i != row || j != column) && piece.isLegalMove(i, j))
                    return true;
            }
        }
        
        return false; //No legal move to play
    }
      
    /** 
     * Moves a piece to a new position.
     * @param piece     the piece to move
     * @param toRow     the row of the square the piece is moving to
     * @param toColumn  the column of the square the piece is moving to
     * @return true if the move was made, false if the move was not made
     */
    public boolean makeMove(ChessPiece piece, int toRow, int toColumn) {
        //Stores the chess board
        EuropeanChessBoard board = (EuropeanChessBoard) piece.getChessBoard();
        
        if (piece.isLegalMove(toRow, toColumn)) {
            if (piece instanceof CheckMove && !((CheckMove) piece).check(toRow, toColumn, piece)) //Checks check
                return false;
            else {
                //Some move is being done
                
                //Stores the removed chess piece, if any
                ChessPiece save = null;
                
                //Checks for en passant move which requires special processing
                if (piece instanceof PawnPiece) {
                    //Stores piece as a pawn
                    PawnPiece pawn = (PawnPiece) piece;
                    
                    //Checks for valid en passant move
                    if (pawn.isValidEnPassantMove(toRow, toColumn, pawn)) {
                        //Stores the original row
                        int originalRow = pawn.getRow();
                        //Stores the original column
                        int originalColumn = pawn.getColumn();
                        
                        //Removes the pawn from the original location
                        board.removePiece(pawn.getRow(), pawn.getColumn());
                        
                        //Moves the pawn to the new space
                        board.addPiece(pawn, toRow, toColumn);
                        
                        //Removes the adjacent pawn from the board
                        switch (pawn.getSide()) {
                            case NORTH:
                            case SOUTH:
                                if (toColumn == originalColumn + 1)
                                    save = board.removePiece(originalRow, originalColumn + 1); //Removes and saves the pawn
                                else
                                    save = board.removePiece(originalRow, originalColumn - 1); //Removes and saves the pawn
                                break;
                            default: //East and West
                                if (toRow == originalRow + 1)
                                    save = board.removePiece(originalRow + 1, originalColumn); //Removes and saves the pawn
                                else
                                    save = board.removePiece(originalRow - 1, originalColumn); //Removes and saves the pawn
                                break;
                        }
                    }
                }
                
                //Checks for castling move which requires special processing
                if (piece instanceof KingPiece) {
                    //Stores piece as a king
                    KingPiece king = (KingPiece) piece;
                    //Stores the rook to be changed
                    RookPiece rook;
                    
                    //Checks for valid castling move
                    if (king.isValidCastlingMove(toRow, toColumn, king)) {
                        /* Gets the rook based on the direction
                         * Note that I'm not checking for the rook again because that was already checked for in CastlingMove
                         */
                        switch (king.getSide()) {
                            case NORTH:
                            case SOUTH:
                                if (king.getColumn() + 2 == toColumn)
                                    rook = (RookPiece) board.getPiece(king.getRow(), board.numColumns() - 1); //Kingside rook on the north/south direction
                                else
                                    rook = (RookPiece) board.getPiece(king.getRow(), 0); //Queenside rook on the north/south direction
                                break;
                            default: //West and East
                                if (king.getRow() + 2 == toRow)
                                    rook = (RookPiece) board.getPiece(board.numRows() - 1, king.getColumn()); //Kingside rook on the west/east direction
                                else
                                    rook = (RookPiece) board.getPiece(0, king.getColumn()); //Queenside rook on the west/east direction
                                break;
                        }
                        
                        //Removes the king from the original location
                        board.removePiece(king.getRow(), king.getColumn());
                        //Moves the king to the new space
                        board.addPiece(king, toRow, toColumn);                    
                        //Removes the rook from the original location
                        board.removePiece(rook.getRow(), rook.getColumn());
                        
                        //Finds and moves the rook based on the direction
                        switch (king.getSide()) {
                            case NORTH:
                            case SOUTH:
                                if (king.getColumn() < rook.getColumn()) //Kingside
                                    board.addPiece(rook, king.getRow(), king.getColumn() - 1);
                                else //Queenside
                                    board.addPiece(rook, king.getRow(), king.getColumn() + 1);
                                break;
                            default: //West and East
                                if (king.getRow() < rook.getRow()) //Kingside
                                    board.addPiece(rook, king.getRow() - 1, king.getColumn());
                                else //Queenside
                                    board.addPiece(rook, king.getRow() + 1, king.getColumn());
                                break;
                        }
                        
                        //Rook final processing; the king was the central piece so it will be done lated
                        rook.moveDone();              
                    }
                }
                
                //Checks for non-capture moves
                if (piece.isLegalNonCaptureMove(toRow, toColumn)) {
                    board.removePiece(piece.getRow(), piece.getColumn());
                    board.addPiece(piece, toRow, toColumn);
                } else if (piece.isLegalCaptureMove(toRow, toColumn)) { //Checks for capture moves
                    save = board.removePiece(toRow, toColumn);
                    board.removePiece(piece.getRow(), piece.getColumn());
                    board.addPiece(piece, toRow, toColumn);
                }
                
                //Processing after move done section
                
                piece.moveDone(); //Piece processing
                flipSide(); //Now opposite player's turn
                
                //Gets the king piece of the player's whose turn it is now
                KingPiece king = board.getKing(piece).getOpposingKing();
                
                if (king.isInCheck()) //Indicates that check happened
                    board.afterCheck(king); //Check processing
                
                if (piece instanceof KingPiece && ((KingPiece) piece).isValidCastlingMove(toRow, toColumn, piece)) //Indicates that castling took place
                    board.afterCastling(king); //Castling processing
                
                if (save != null) //Indicates that a piece has been captured
                    board.afterCapture(king); //Capture move processing
                else
                    board.afterNonCapture(king); //Non-capture move processing
                         
                return true; //Successful move
            }
        } else
            return false; //Unsuccessful move
    }
    
    /** 
     * Checks for any moves in a position.
     * @param piece     A piece of the game
     * @return          If there are any moves in the position for the side of the piece. If false, either stalemate or checkmate will result, resulting in the end of the game
     */
    public boolean isAbleToMove(ChessPiece piece) {
        //Stores the same side's pieces
        ArrayList<ChessPiece> pieces = new ArrayList<>();
        
        //Iterates for each square of the chess board in search of a piece
        for (int i = 0; i < piece.getChessBoard().numRows(); i++) {
            for (int j = 0; j < piece.getChessBoard().numColumns(); j++) {
                //Looks for same side piece in the chess board
                if (piece.getChessBoard().hasPiece(i, j) && piece.getChessBoard().getPiece(i, j).getSide().equals(piece.getSide()))
                    pieces.add(piece.getChessBoard().getPiece(i, j));
            }
        }
        
        //Iterates through each of the same side pieces
        for (ChessPiece cp : pieces) {
            if (legalPieceToPlay(cp, cp.getRow(), cp.getColumn()))
                return true;
        }
        
        //No move to play
        return false;
    }
}
