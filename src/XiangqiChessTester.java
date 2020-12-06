import org.junit.Test;
import static org.junit.Assert.*;

/**
 * <p>Represents a class that tests methods relating to Xiangqi.</p>
 *
 * @author Nobel Zhou (nxz157)
 * @version 1.0, 12/5/20
 */
public class XiangqiChessTester {

    //region FIELDS
    //Stores the empty chess boards
    private TestChessBoard southBoard, westBoard;
    //endregion

    //region CONSTRUCTORS
    /**
     * <p>Initializes the empty chess boards.</p>
     *
     * @since 1.0
     */
    public XiangqiChessTester() {
        southBoard = new TestChessBoard(new Xiangqi(ChessGame.Side.SOUTH));
        westBoard = new TestChessBoard(new Xiangqi(ChessGame.Side.WEST));
    }
    //endregion

    //region METHODS
    /**
     * <p>Resets all the boards.</p>
     *
     * @since 1.0
     */
    public void reset() {
        southBoard = new TestChessBoard(new Xiangqi(ChessGame.Side.SOUTH));
        westBoard = new TestChessBoard(new Xiangqi(ChessGame.Side.WEST));
    }

    /**
     * <p>Tests the constructor of the <code>XiangqiKingPiece</code> class.</p>
     *
     * @since 1.0
     */
    @Test
    public void testXiangqiKingPiece() {

    }

    //endregion
}