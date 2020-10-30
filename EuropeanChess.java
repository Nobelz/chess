import java.util.ArrayList;
import java.util.Collections;

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
    public enum Result {CHECKMATE, STALEMATE, INSUFFICIENT_MATERIAL, THREEFOLD_REPETITION, FIFTY_MOVE_RULE};

    /* FIELDS */
    //Stores the starting side; cannot be changed
    private final ChessGame.Side startingSide;
    //Stores the side that is currently playing
    private ChessGame.Side currentSide;
    //Stores the game positions of the whole game
    private ArrayList<ChessPosition> positions;
    //Stores the number of non-pawn, non-capture moves
    private int fiftyMoveRule;

    /* CONSTRUCTORS */
    /**
     * Initializes European chess rules.
     * @param startingSide  The side who starts
     * @since 1.0
     */
    public EuropeanChess(ChessGame.Side startingSide) {
        this.startingSide = startingSide;
        currentSide = startingSide;
        positions = new ArrayList<>();
    }

    /* METHODS */
    /**
     * Flips the side so the other side is playing.
     * @since 1.0
     */
    public void flipSide() {
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
     * Returns an int representing the number of consecutive non-capture and non-pawn moves.
     * @return  The number of consecutive non-capture and non-pawn moves
     * @since 1.0
     */
    public int getFiftyMoveRule() {
        return fiftyMoveRule;
    }
    
    /**
     * Sets the fifty move rule count.
     * @param fiftyMoveRule The new fifty move rule count
     * @since 1.0
     */
    public void setFiftyMoveRule(int fiftyMoveRule) {
        this.fiftyMoveRule = fiftyMoveRule;
    }
    
    /**
     * Returns a ChessGame.Side representing the side that is currently playing.
     * @return  The side that is currently playing
     * @since 1.0
     */
    public ChessGame.Side getCurrentSide() {
        return currentSide;
    }

    /**
     * Returns a ChessGame.Side representing the side that started.
     * @return  The side that is currently playing
     * @since 1.0
     */
    public ChessGame.Side getStartingSide() {
        return startingSide;
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
                //Checks if the piece has a legal move at the row and column specified; also checks for the castling move and if the king is in check
                if ((i != row || j != column) && piece.isLegalMove(i, j) && isGoodMove(i, j, piece))
                    return true;
            }
        }

        return false; //No legal move to play
    }

    /**
     * Determines if it is legal to play a certain move.
     * Checks to make sure castling move isn't already in check.
     * Checks to make sure that move doesn't result in check.
     */
    public boolean isGoodMove(int row, int column, ChessPiece piece) {
        return check(row, column, piece) && (!(piece instanceof CastlingMove) || (((CastlingMove) piece).isValidCastlingMove(row, column, piece) ? !piece.getChessBoard().getKing(piece).isInCheck() : true));
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
        ChessBoard board = piece.getChessBoard();

        if (piece.isLegalMove(toRow, toColumn) && isGoodMove(toRow, toColumn, piece)) {
            //Some move is being done
            //Stores the removed chess piece, if any
            ChessPiece save = null;
            //Stores if the move was a castle
            boolean hasCastled = false;

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
                    hasCastled = true;
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
                afterCheck(king); //Check processing

            if (hasCastled) //Indicates that castling took place
                afterCastling(king); //Castling processing

            if (save != null) { //Indicates that a piece has been captured
                afterCapture(king); //Capture move processing
                setFiftyMoveRule(0);
            } else {
                //Increment fifty move rule if it was a pawn move
                if (piece instanceof PawnPiece)
                    setFiftyMoveRule(0);
                else
                    setFiftyMoveRule(getFiftyMoveRule() + 1); //Adds 1 to the 50 move rule
                afterNonCapture(king); //Non-capture move processing
            }
            
            ChessPiece[][] pieces = board.getPieces().clone();
            for (int i = 0; i < pieces.length; i++) {
                pieces[i] = pieces[i].clone();
            }
            
            //Checks draw by 50 move rule
            if (getFiftyMoveRule() == 50)
                board.terminate(EuropeanChess.Result.FIFTY_MOVE_RULE, null);
 
            //Checks draw by three-fold repetition
            if (checkThreeFoldRepetition(new ChessPosition(pieces, getCurrentSide())))
                board.terminate(EuropeanChess.Result.THREEFOLD_REPETITION, null);

            return true; //Successful move
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

    /**
     * Returns a boolean representing if the move will not result in the king being in check.
     * Assumes that the move is a legal move.
     * @param row       The piece's destination row
     * @param column    The piece's destination column
     * @param cp        The chess piece
     * @return          Whether the proposed location is a valid king move, assuming it is unoccupied; returns true if it doesn't result in the king in check
     * @since 1.0
     */
    public boolean check(int row, int column, ChessPiece cp) {
        //Stores the chess board
        ChessBoard board = cp.getChessBoard();
        //Stores the removed chess piece
        ChessPiece save;
        //Stores the king piece of the same side
        KingPiece king = board.getKing(cp);
        //Stores the original row of the piece
        int originalRow = cp.getRow();
        //Stores the original column of the piece
        int originalColumn = cp.getColumn();

        //Checks en passant move
        if (cp instanceof PawnPiece && ((PawnPiece) cp).isValidEnPassantMove(row, column, cp)) {
            //Removes the piece from the original location
            board.simulateRemovePiece(originalRow, originalColumn);
            //Moves the piece; note, we do not have to store the replaced piece because by very definition of the en passant move, the space has to be empty
            board.simulateAddPiece(cp, row, column);            

            //Removes the adjacent pawn from the board
            switch (cp.getSide()) {
                case NORTH:
                case SOUTH:
                    if (column == originalColumn + 1)
                        save = board.simulateRemovePiece(originalRow, originalColumn + 1); //Removes and saves the pawn
                    else
                        save = board.simulateRemovePiece(originalRow, originalColumn - 1); //Removes and saves the pawn
                    break;
                default: //East and West
                    if (row == originalRow + 1)
                        save = board.simulateRemovePiece(originalRow + 1, originalColumn); //Removes and saves the pawn
                    else
                        save = board.simulateRemovePiece(originalRow - 1, originalColumn); //Removes and saves the pawn
                    break;
            }

            //Stores whether the king is in check
            boolean inCheck = king.isInCheck();

            //Reverts to original position
            //Removes the pawn
            board.simulateRemovePiece(row, column);
            //Adds the pawn back to the original location
            board.simulateAddPiece(cp, originalRow, originalColumn);
            //Adds the captured pawn back to the original location
            board.simulateAddPiece(save, save.getRow(), save.getColumn());

            return !inCheck; //Returns if the move is valid for check
        } else { //Checks all other moves
            //Removes the piece from the original location
            board.simulateRemovePiece(originalRow, originalColumn);
            //Moves the piece and stores the replaced piece
            save = board.simulateAddPiece(cp, row, column);

            //Stores whether the king is in check
            boolean inCheck = king.isInCheck();

            //Reverts to original position
            //Removes the piece
            board.simulateRemovePiece(row, column);
            //Adds the piece back to the original location
            board.simulateAddPiece(cp, originalRow, originalColumn);
            //Adds the captured piece back to the original location
            if (save != null)
                board.simulateAddPiece(save, save.getRow(), save.getColumn());

            return !inCheck; //Returns if the move is valid for check
        }
    }

    /**
     * Runs when a move results in a check.
     * @param king    The king that is in check
     * @since 1.0
     */
    public void afterCheck(KingPiece king) {
        //Checks if it's checkmate
        if (!isAbleToMove(king))
            king.getChessBoard().terminate(EuropeanChess.Result.CHECKMATE, king.getOpposingKing().getSide());
    }

    /**
     * Checks for insufficient material.
     * There are only 4 cases for insufficient material:
     * King vs. King
     * King + Knight vs. King
     * King + Bishop vs. King
     * King + Bishop vs. King + Bishop of same color
     * @param king  The king of the side that is currently playing
     * @return      If there is insufficient material 
     * @since 1.0
     */
    private boolean checkInsufficientMaterial(KingPiece king) {
        //Stores the all of the chess pieces on the board
        ArrayList<ChessPiece> pieces = new ArrayList<>();

        //Iterates for each square of the chess board in search of a piece
        for (int i = 0; i < king.getChessBoard().numRows(); i++) {
            for (int j = 0; j < king.getChessBoard().numColumns(); j++) {
                //Looks for same side piece in the chess board
                if (king.getChessBoard().hasPiece(i, j))
                    pieces.add(king.getChessBoard().getPiece(i, j));
            }
        }

        //Checks if it's just bare kings
        if (pieces.size() == 2) {
            return true;
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
                    return true;
                else if (bishops.size() == 2 && bishops.get(0).isDarkSquared() == bishops.get(1).isDarkSquared()) //King + Bishop vs. King + Bishop of same color is insufficient material
                    return true;
            }
        }
        
        return false;
    }
    
    /**
     * Checks for three-fold repetition.
     * @param position  The new ChessPosition
     * @return          If three-fold repeition was found
     * @since 1.0
     */
    private boolean checkThreeFoldRepetition(ChessPosition position) {
        positions.add(position);
        
        //If less than 6 moves have been played (3 for each side), then obviously no three-fold repetition
        if (positions.size() >= 6) {
            if (Collections.frequency(positions, position) >= 3) { //3 of the same move
                return true;
            }
        }
        return false;
    }
    
    /**
     * Runs when a capture move is played. This includes en passant.
     * @param king    The king of the side that is currently playing
     * @since 1.0
     */
    public void afterCapture(KingPiece king) {
        if (!king.isInCheck()) {
            if (!isAbleToMove(king) || checkInsufficientMaterial(king))
                king.getChessBoard().terminate(EuropeanChess.Result.INSUFFICIENT_MATERIAL, null);
        }
    }

    /**
     * Runs when a castling move is played.
     * @param king    The king of the side that is currently playing
     * @since 1.0
     */
    public void afterCastling(KingPiece king) {
        if (!king.isInCheck() && !isAbleToMove(king))
            king.getChessBoard().terminate(EuropeanChess.Result.INSUFFICIENT_MATERIAL, null);
    }

    /**
     * Runs when a normal, non-capture move is played.
     * @param king    The king of the side that is currently playing
     * @since 1.0
     */
    public void afterNonCapture(KingPiece king) {
        if (!king.isInCheck() && !isAbleToMove(king))
            king.getChessBoard().terminate(EuropeanChess.Result.STALEMATE, null);
    }
    
        
    /**
     * Promotes the ChessPiece to the desired ChessPiece type.
     * @param oldPiece  The ChessPiece to be replaced
     * @param newPiece  The ChessPiece type to be replaced with
     * @since 1.0
     */
    public void promote(ChessPiece oldPiece, ChessPiece newPiece) {
        oldPiece.getChessBoard().addPiece(newPiece, oldPiece.getRow(), oldPiece.getColumn());
    }
}
