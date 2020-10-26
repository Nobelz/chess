/**
 * Represents a move that may result in the king being in check.
 * Checks to make sure the proposed move will not result in the king being in check.
 *
 * COMBINE INTO EUROPEAN CHESS
 *
 * @author Nobel Zhou (nxz157)
 * @version 1.0, 10/30/20
 */
public interface CheckMove {
    /**
     * Returns a boolean representing if the move will not result in the king being in check.
     * Assumes that the move is a legal move.
     * @param row       The piece's destination row
     * @param column    The piece's destination column
     * @param cp        The chess piece
     * @return          Whether the proposed location is a valid king move, assuming it is unoccupied; returns true if it doesn't result in the king in check
     * @since 1.0
     */
    public default boolean check(int row, int column, ChessPiece cp) {
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
                    if (column == cp.getColumn() + 1)
                        save = board.simulateRemovePiece(originalRow, originalColumn + 1); //Removes and saves the pawn
                    else
                        save = board.simulateRemovePiece(originalRow, originalColumn - 1); //Removes and saves the pawn
                    break;
                default: //East and West
                    if (row == cp.getRow() + 1)
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
}
