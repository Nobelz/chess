import java.util.ArrayList;

/**
 * <p>Represents a xiangqi king chess piece.</p>
 * <p>Dictates how a xiangqi king can move.</p>
 *
 * @author Nobel Zhou
 * @version 1.0, 12/5/20
 */
public class XiangqiKingPiece extends CenterPiece implements CanSingleStraightMove, CanPalaceMove, CanFaceKingMove {

    //region CONSTRUCTORS
    /**
     * <p>Initializes a xiangqi king piece based on the given side, chess board, and location.</p>
     *
     * @param side          the xiangqi king piece's side
     * @param chessBoard    the chess board the xiangqi king piece is on
     * @param icon          the xiangqi king piece's icon
     * @param row           the xiangqi king piece's starting row
     * @param column        the xiangqi king piece's starting column
     * @since 1.0
     */
    public XiangqiKingPiece(ChessGame.Side side, ChessBoard chessBoard, ChessIcon icon, int row, int column) {
        super(side, "X", icon, chessBoard, row, column);
    }
    //endregion

    //region METHODS
    /**
     * <p>Returns a boolean representing if the proposed move is legal, assuming that it is unoccupied.</p>
     *
     * @param row       the xiangqi king piece's destination row
     * @param column    the xiangqi king piece's destination column
     * @return          <code>true</code> if the xiangqi king piece's proposed move is legal, assuming it is unoccupied
     * @since 1.0
     */
    @Override
    public boolean isLegalNonCaptureMove(int row, int column) {
        return isValidSingleStraightMove(row, column, this) && isValidPalaceMove(row, column, this);
    }

    /**
     * <p>Returns a boolean representing if the proposed move is legal, assuming that it is occupied.</p>
     *
     * @param row       the xiangqi king piece's destination row
     * @param column    the xiangqi king piece's destination column
     * @return          <code>true</code> if the xiangqi king piece's proposed move is legal, assuming it is occupied
     * @since 1.0
     */
    @Override
    public boolean isLegalCaptureMove(int row, int column) {
        return isLegalNonCaptureMove(row, column) || isValidFaceKingMove(row, column, this);
    }

    /**
     * <p>Returns an array of <code>XiangqiKingPiece</code>s representing the opposing kings.</p>
     * <p>For xiangqi, there will only be 1 opposing king.</p>
     *
     * @return  the opposing kings
     * @since 1.0
     */
    @Override
    public XiangqiKingPiece[] getOpposingKings() {
        // Stores all the opposing kings; note that since we don't know how many opposing kings there are, we have to use an ArrayList
        ArrayList<XiangqiKingPiece> opposingKings = new ArrayList<>();

        // Iterates the chess board to look for the opposite side xiangqi king piece
        for (int i = 0; i < getChessBoard().getGameRules().getNumRows(); i++) {
            for (int j = 0; j < getChessBoard().getGameRules().getNumColumns(); j++) {
                // Looks for opposite side king piece
                try {
                    if (!getChessBoard().getPiece(i, j).getSide().equals(getSide()))
                        opposingKings.add((XiangqiKingPiece) getChessBoard().getPiece(i, j));
                } catch (Exception ignored) {} // Not a XiangqiKingPiece or piece does not exist at that location; continue searching
            }
        }

        // Returns in array form
        return opposingKings.toArray(new XiangqiKingPiece[0]);
    }
    //endregion
}
