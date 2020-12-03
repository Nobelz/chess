package development;

import java.rmi.NoSuchObjectException;

/**
 * <p>Represents the ability to make a castling move in chess.</p>
 *
 * @author Nobel Zhou (nxz157)
 * @version 1.0, 12/2/20
 */
public interface CanCastleMove extends CanMove {
    /**
     * <p>Returns a boolean representing if the proposed row and column is a valid castling move from the piece's location.</p>
     * <p>The only valid move will be 2 spaces to the left or right of the king if north/south and up or down of the king if west/east.</p>
     * <p>The rook and king must not have moved.</p>
     * <p>The spaces must not be threatened by other pieces.</p>
     * <p>The king must not also be in check.</p>
     *
     * @param row       the piece's destination row
     * @param column    the piece's destination column
     * @param cp        the chess piece
     * @return          <code>true</code> if the proposed location is a valid castling move
     * @since 1.0
     */
    default boolean isValidCastlingMove(int row, int column, ChessPiece cp) {
        // Checks if the piece has moved and if the valid move is 2 squares away in the correct direction
        if (isValidMove(row, column, cp) && cp.getNumMoves() == 0 &&
                ((cp.getSide().equals(ChessGame.Side.NORTH) || cp.getSide().equals(ChessGame.Side.SOUTH)) ?
                cp.getRow() == row && (cp.getColumn() + 2 == column || cp.getColumn() - 2 == column) :
                cp.getColumn() == column && (cp.getRow() + 2 == row || cp.getRow() - 2 == row))) {

            // Stores the chess board
            ChessBoard board = cp.getChessBoard();

            // Stores the rook
            RookPiece rook;

            try {
                // Checks if the direction is north or south
                switch (cp.getSide()) {
                    case NORTH:
                    case SOUTH:
                        if (cp.getColumn() + 2 == column) {
                            // Kingside rook on the north/south direction
                            rook = (RookPiece) board.getPiece(cp.getRow(), board.getGameRules().getNumColumns() - 1);
                            // Checks if any of the squares are threatened
                            if (board.squareThreatened(cp.getRow(), cp.getColumn() + 1, cp) || board.squareThreatened(cp.getRow(), cp.getColumn() + 2, cp))
                                return false;
                        } else {
                            // Queenside rook on the north/south direction
                            rook = (RookPiece) board.getPiece(cp.getRow(), 0);
                            // Checks if any of the squares are threatened
                            if (board.squareThreatened(cp.getRow(), cp.getColumn() - 1, cp) || board.squareThreatened(cp.getRow(), cp.getColumn() - 2, cp))
                                return false;
                        }
                        break;
                    default: // East and West
                        if (cp.getRow() + 2 == row) {
                            // Kingside rook on the west/east direction
                            rook = (RookPiece) board.getPiece(board.getGameRules().getNumRows() - 1, cp.getColumn());
                            // Checks if any of the squares are threatened
                            if (board.squareThreatened(cp.getRow() + 1, cp.getColumn(), cp) || board.squareThreatened(cp.getRow() + 2, cp.getColumn(), cp))
                                return false;
                        } else {
                            // Queenside rook on the west/east direction
                            rook = (RookPiece) board.getPiece(0, cp.getColumn());
                            // Checks if any of the squares are threatened
                            if (board.squareThreatened(cp.getRow() - 1, cp.getColumn(), cp) || board.squareThreatened(cp.getRow() - 2, cp.getColumn(), cp))
                                return false;
                        }
                }
            } catch (ClassCastException e) {
                return false; // The "rook" is not a rook
            }

            // Checks if the piece at the location exists, is a rook, hasn't moved, and is the same side as the king
            if (rook != null && rook.getNumMoves == 0 && rook.getSide().equals(cp.getSide())) {
                // Checks to make sure space between king and rook is empty
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
                    default: // West and East
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
                }
                return true;
            } else
                return false;
        }
        return false;
    }

    /**
     * <p>Returns a <code>RookPiece</code> that the castling move interacts with.</p>
     * <p>Assumes that the move is a valid castling move.</p>
     *
     * @param row       the piece's destination row
     * @param column    the piece's destination column
     * @param cp        the chess piece
     * @return          the rook associated with the castling move
     * @since 1.0
     * @throws UnsupportedOperationException    if the move is not a valid castling move
     */
    default RookPiece getRook(int row, int column, ChessPiece cp) throws UnsupportedOperationException {
        // Checks if the direction is north or south
        try {
            switch (cp.getSide()) {
                case NORTH:
                case SOUTH:
                    if (cp.getColumn() + 2 == column) { // Kingside rook on the north/south direction
                        return (RookPiece) cp.getChessBoard().getPiece(cp.getRow(), cp.getChessBoard().getGameRules().getNumColumns() - 1);
                    } else { // Queenside rook on the north/south direction
                        return (RookPiece) cp.getChessBoard().getPiece(cp.getRow(), 0);
                    }
                    break;
                default: // East and West
                    if (cp.getRow() + 2 == row) { // Kingside rook on the west/east direction
                        return (RookPiece) cp.getChessBoard().getPiece(cp.getChessBoard().getGameRules().getNumRows() - 1, cp.getColumn());
                    } else { // Queenside rook on the west/east direction
                        return (RookPiece) cp.getChessBoard().getPiece(0, cp.getColumn());
                    }
            }
        } catch (ClassCastException e) {
            throw new UnsupportedOperationException("Unsupported operation for invalid castling moves. Please make sure that the move is a valid castling move.");
        }
    }
}
