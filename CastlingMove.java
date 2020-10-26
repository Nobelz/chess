/**
 * Represents the ability to make a castling move in chess.
 *
 * @author Nobel Zhou (nxz157)
 * @version 1.0, 10/30/20
 */
public interface CastlingMove {
    /**
     * Returns a boolean representing if the proposed row and column is a valid castling move from the piece's location.
     * The only valid move will be 2 spaces to the left or right of the king if north/south and up or down of the king if west/east.
     * The rook and king must not have moved.
     * The spaces must not be threatened by other pieces.
     * The king must not also be in check.
     * @param row       The piece's destination row
     * @param column    The piece's destination column
     * @param cp        The chess piece
     * @return          Whether the proposed location is a valid castling move
     * @since 1.0
     */
    public default boolean isValidCastlingMove(int row, int column, ChessPiece cp) {
        //Checks if the piece has moved and if the valid move is 2 squares away in the correct direction
        if (cp.getMoves() == 0 && ((cp.getSide().equals(ChessGame.Side.NORTH) || cp.getSide().equals(ChessGame.Side.SOUTH)) ? cp.getRow() == row && (cp.getColumn() + 2 == column || cp.getColumn() - 2 == column) : cp.getColumn() == column && (cp.getRow() + 2 == row || cp.getRow() - 2 == row))) {
            //Stores the chess board
            ChessBoard board = cp.getChessBoard();
            //Stores the rook
            ChessPiece rook;
            //Checks if the direction is north or south
            if (cp.getSide().equals(ChessGame.Side.NORTH) || cp.getSide().equals(ChessGame.Side.SOUTH)) {
                if (cp.getColumn() + 2 == column) {
                    //Kingside rook on the north/south direction
                    rook = board.getPiece(cp.getRow(), board.numColumns() - 1); 
                    //Checks if any of the squares are threatened
                    if (board.squareThreatened(cp.getRow(), cp.getColumn() + 1, cp) || board.squareThreatened(cp.getRow(), cp.getColumn() + 2, cp))
                        return false;
                }
                else {
                    //Queenside rook on the north/south direction
                    rook = board.getPiece(cp.getRow(), 0); 
                    //Checks if any of the squares are threatened
                    if (board.squareThreatened(cp.getRow(), cp.getColumn() - 1, cp) || board.squareThreatened(cp.getRow(), cp.getColumn() - 2, cp))
                        return false;
                }
            } else { //Otherwise, the direction is west or east
                if (cp.getRow() + 2 == row) {
                    //Kingside rook on the west/east direction
                    rook = board.getPiece(board.numRows() - 1, cp.getColumn()); 
                    //Checks if any of the squares are threatened
                    if (board.squareThreatened(cp.getRow() + 1, cp.getColumn(), cp) || board.squareThreatened(cp.getRow() + 2, cp.getColumn(), cp))
                        return false;
                } else {
                    //Queenside rook on the west/east direction
                    rook = board.getPiece(0, cp.getColumn()); 
                    //Checks if any of the squares are threatened
                    if (board.squareThreatened(cp.getRow() - 1, cp.getColumn(), cp) || board.squareThreatened(cp.getRow() - 2, cp.getColumn(), cp))
                        return false;
                }
            }
            
            //Checks if the piece at the location exists, is a rook, and the rook hasn't moved
            if (rook != null && rook instanceof RookPiece && rook.getMoves() == 0) {
                switch (cp.getSide()) {
                    case NORTH:
                    case SOUTH:
                        if (cp.getColumn() < column) {
                            for (int i = cp.getColumn() + 1; i < rook.getColumn(); i++) {
                                if (cp.getChessBoard().hasPiece(row, i))
                                    return false;
                            }
                        } else {
                            for (int i = cp.getColumn() - 1; i > rook.getColumn(); i--) {
                                if (cp.getChessBoard().hasPiece(row, i))
                                    return false;
                            }
                        }
                        break;
                    default: //West and East
                        if (cp.getRow() < row) {
                            for (int i = cp.getRow() + 1; i < rook.getRow(); i++) {
                                if (cp.getChessBoard().hasPiece(i, column))
                                    return false;
                            }
                        } else {
                            for (int i = cp.getRow() - 1; i > rook.getRow(); i--) {
                                if (cp.getChessBoard().hasPiece(i, column))
                                    return false;
                            }
                        }
                        break;
                }
                return true;
            } else
                return false;
        }
         
        return false;
    }
}
