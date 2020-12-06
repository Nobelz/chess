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
    private BasicChessBoard southBoard, westBoard;
    //endregion

    //region CONSTRUCTORS
    /**
     * <p>Initializes the empty chess boards.</p>
     *
     * @since 1.0
     */
    public XiangqiChessTester() {
        southBoard = new BasicChessBoard(new Xiangqi(ChessGame.Side.SOUTH));
        westBoard = new BasicChessBoard(new Xiangqi(ChessGame.Side.WEST));
    }
    //endregion

    //region METHODS
    /**
     * <p>Resets all the boards.</p>
     *
     * @since 1.0
     */
    public void reset() {
        southBoard = new BasicChessBoard(new Xiangqi(ChessGame.Side.SOUTH));
        westBoard = new BasicChessBoard(new Xiangqi(ChessGame.Side.WEST));
    }

    /**
     * <p>Tests the constructor of the <code>XiangqiKingPiece</code> class.</p>
     *
     * @since 1.0
     */
    @Test
    public void testXiangqiKingPiece() {
        // Instantiates king piece
        XiangqiKingPiece king = new XiangqiKingPiece(ChessGame.Side.SOUTH, southBoard, ChessIcon.RED_GENERAL, 1, 1);

        // Checks to see if label was set correctly
        assertEquals(king.getLabel(), "X");

        // Reset board
        reset();
    }

    /**
     * <p>Tests the <code>isValidSingleStraightMove</code> method in the <code>CanSingleStraightMove</code> interface.</p>
     *
     * @since 1.0
     */
    @Test
    public void testSingleStraightMove() {
        // Create pieces
        XiangqiKingPiece king1 = new XiangqiKingPiece(ChessGame.Side.NORTH, southBoard, ChessIcon.BLACK_GENERAL, 2, 0);
        XiangqiKingPiece king2 = new XiangqiKingPiece(ChessGame.Side.SOUTH, southBoard, ChessIcon.RED_GENERAL, 8, 4);
        XiangqiKingPiece king3 = new XiangqiKingPiece(ChessGame.Side.SOUTH, southBoard, ChessIcon.RED_GENERAL, 9, 0);

        // Add pieces
        southBoard.addPiece(king1, 2, 0);
        southBoard.addPiece(king2, 8, 4);
        southBoard.addPiece(king3, 9, 0);
        southBoard.addPiece(new GuardPiece(ChessGame.Side.NORTH, southBoard, ChessIcon.BLACK_GUARD, 2, 1), 2, 1);

        // Iterates through whole board
        for (int i = 0; i < southBoard.getGameRules().getNumRows(); i++) {
            for (int j = 0; j < southBoard.getGameRules().getNumColumns(); j++) {
                if ((i == 2 && j == 1) || (j == 0 && (i == 1 || i == 3))) { // Moves that should return true for king1
                    assertTrue(king1.isValidSingleStraightMove(i, j, king1));
                    assertFalse(king2.isValidSingleStraightMove(i, j, king2));
                    assertFalse(king3.isValidSingleStraightMove(i, j, king3));
                } else if ((i == 8 && (j == 3 || j == 5)) || (j == 4 && (i == 7 || i == 9))) { // Moves that should return true for king2
                    assertFalse(king1.isValidSingleStraightMove(i, j, king1));
                    assertTrue(king2.isValidSingleStraightMove(i, j, king2));
                    assertFalse(king3.isValidSingleStraightMove(i, j, king3));
                } else if ((i == 8 && j == 0) || (i == 9 && j == 1)) { // Moves that should return true for king3
                    assertFalse(king1.isValidSingleStraightMove(i, j, king1));
                    assertFalse(king2.isValidSingleStraightMove(i, j, king2));
                    assertTrue(king3.isValidSingleStraightMove(i, j, king3));
                } else { // Moves that are false for every piece
                    assertFalse(king1.isValidSingleStraightMove(i, j, king1));
                    assertFalse(king2.isValidSingleStraightMove(i, j, king2));
                    assertFalse(king3.isValidSingleStraightMove(i, j, king3));
                }
            }
        }

        // Reset board
        reset();
    }

    /**
     * <p>Tests the <code>isValidPalaceMove</code> method in the <code>CanPalaceMove</code> interface.</p>
     *
     * @since 1.0
     */
    @Test
    public void testPalaceMove() {
        // Create pieces
        XiangqiKingPiece king1 = new XiangqiKingPiece(ChessGame.Side.SOUTH, southBoard, ChessIcon.RED_GENERAL, 3, 6);
        XiangqiKingPiece king2 = new XiangqiKingPiece(ChessGame.Side.NORTH, southBoard, ChessIcon.BLACK_GENERAL, 6, 1);
        XiangqiKingPiece king3 = new XiangqiKingPiece(ChessGame.Side.WEST, westBoard, ChessIcon.BLACK_GENERAL, 2, 3);
        XiangqiKingPiece king4 = new XiangqiKingPiece(ChessGame.Side.EAST, westBoard, ChessIcon.RED_GENERAL, 6, 6);


        // Add pieces
        southBoard.addPiece(king1, 3, 6);
        southBoard.addPiece(king2, 6, 1);
        westBoard.addPiece(king3, 2, 2);
        westBoard.addPiece(king4, 6, 6);

        // Iterates through south board
        for (int i = 0; i < southBoard.getGameRules().getNumRows(); i++) {
            for (int j = 0; j < southBoard.getGameRules().getNumColumns(); j++) {
                if (i >= 7 && j <= 5 && j >= 3) { // Moves that should return true for king1
                    assertTrue(king1.isValidPalaceMove(i, j, king1));
                    assertFalse(king2.isValidPalaceMove(i, j, king2));
                } else if (i <= 2 && j <= 5 && j >= 3) { // Moves that should return true for king2
                    assertFalse(king1.isValidPalaceMove(i, j, king1));
                    assertTrue(king2.isValidPalaceMove(i, j, king2));
                } else { // Moves that are false for every piece
                    assertFalse(king1.isValidPalaceMove(i, j, king1));
                    assertFalse(king2.isValidPalaceMove(i, j, king2));
                }
            }
        }

        // Iterates through west board
        for (int i = 0; i < westBoard.getGameRules().getNumRows(); i++) {
            for (int j = 0; j < westBoard.getGameRules().getNumColumns(); j++) {
                if (j <= 2 && i <= 5 && i >= 3) { // Moves that should return true for king3
                    assertTrue(king3.isValidPalaceMove(i, j, king3));
                    assertFalse(king4.isValidPalaceMove(i, j, king4));
                } else if (j >= 7 && i <= 5 && i >= 3) { // Moves that should return true for king4
                    assertFalse(king3.isValidPalaceMove(i, j, king3));
                    assertTrue(king4.isValidPalaceMove(i, j, king4));
                } else { // Moves that are false for every piece
                    assertFalse(king3.isValidPalaceMove(i, j, king3));
                    assertFalse(king4.isValidPalaceMove(i, j, king4));
                }
            }
        }

        // Reset board
        reset();
    }

    /**
     * <p>Tests the <code>isValidFaceKingMove</code> method in the <code>CanFaceKingMove</code> interface.</p>
     *
     * @since 1.0
     */
    @Test
    public void testFaceKingMove() {
        // Create pieces
        XiangqiKingPiece king1 = new XiangqiKingPiece(ChessGame.Side.SOUTH, southBoard, ChessIcon.RED_GENERAL, 8, 3);
        XiangqiKingPiece king2 = new XiangqiKingPiece(ChessGame.Side.SOUTH, southBoard, ChessIcon.RED_GENERAL, 8, 4);
        XiangqiKingPiece king3 = new XiangqiKingPiece(ChessGame.Side.SOUTH, southBoard, ChessIcon.RED_GENERAL, 8, 5);
        XiangqiKingPiece king4 = new XiangqiKingPiece(ChessGame.Side.NORTH, southBoard, ChessIcon.BLACK_GENERAL, 1, 2);
        XiangqiKingPiece king5 = new XiangqiKingPiece(ChessGame.Side.NORTH, southBoard, ChessIcon.BLACK_GENERAL, 1, 4);
        XiangqiKingPiece king6 = new XiangqiKingPiece(ChessGame.Side.NORTH, southBoard, ChessIcon.BLACK_GENERAL, 1, 5);
        XiangqiKingPiece king7 = new XiangqiKingPiece(ChessGame.Side.WEST, westBoard, ChessIcon.BLACK_GENERAL, 2, 1);
        XiangqiKingPiece king8 = new XiangqiKingPiece(ChessGame.Side.WEST, westBoard, ChessIcon.BLACK_GENERAL, 4, 1);
        XiangqiKingPiece king9 = new XiangqiKingPiece(ChessGame.Side.WEST, westBoard, ChessIcon.BLACK_GENERAL, 5, 1);
        XiangqiKingPiece king10 = new XiangqiKingPiece(ChessGame.Side.EAST, westBoard, ChessIcon.RED_GENERAL, 3, 8);
        XiangqiKingPiece king11 = new XiangqiKingPiece(ChessGame.Side.EAST, westBoard, ChessIcon.RED_GENERAL, 4, 8);
        XiangqiKingPiece king12 = new XiangqiKingPiece(ChessGame.Side.EAST, westBoard, ChessIcon.RED_GENERAL, 5, 8);


        // Add pieces
        southBoard.addPiece(king1, 8, 3);
        southBoard.addPiece(king2, 8, 4);
        southBoard.addPiece(king3, 8, 5);
        southBoard.addPiece(king4, 1, 2);
        southBoard.addPiece(king5, 1, 4);
        southBoard.addPiece(king6, 1, 5);
        westBoard.addPiece(king7, 2, 1);
        westBoard.addPiece(king8, 4, 1);
        westBoard.addPiece(king9, 5, 1);
        westBoard.addPiece(king10, 3, 8);
        westBoard.addPiece(king11, 4, 8);
        westBoard.addPiece(king12, 5, 8);
        southBoard.addPiece(new CannonPiece(ChessGame.Side.SOUTH, southBoard, ChessIcon.RED_CANNON, 5, 4), 5, 4);
        westBoard.addPiece(new CannonPiece(ChessGame.Side.WEST, westBoard, ChessIcon.RED_CANNON, 4, 4), 4, 4);

        // Iterates through south board
        for (int i = 0; i < southBoard.getGameRules().getNumRows(); i++) {
            for (int j = 0; j < southBoard.getGameRules().getNumColumns(); j++) {
                assertFalse(king1.isValidFaceKingMove(i, j, king1));
                assertFalse(king2.isValidFaceKingMove(i, j, king2));
                assertFalse(king4.isValidFaceKingMove(i, j, king4));
                assertFalse(king5.isValidFaceKingMove(i, j, king5));

                if (i == 1 && j == 5) // Move that should return true for king3
                    assertTrue(king3.isValidFaceKingMove(i, j, king3));
                else
                    assertFalse(king3.isValidFaceKingMove(i, j, king3));

                if (i == 8 && j == 5) // Move that should return true for king6
                    assertTrue(king6.isValidFaceKingMove(i, j, king6));
                else
                    assertFalse(king6.isValidFaceKingMove(i, j, king6));
            }
        }

        // Iterates through west board
        for (int i = 0; i < westBoard.getGameRules().getNumRows(); i++) {
            for (int j = 0; j < westBoard.getGameRules().getNumColumns(); j++) {
                assertFalse(king7.isValidFaceKingMove(i, j, king7));
                assertFalse(king8.isValidFaceKingMove(i, j, king8));
                assertFalse(king10.isValidFaceKingMove(i, j, king10));
                assertFalse(king11.isValidFaceKingMove(i, j, king11));

                if (j == 8 && i == 5) // Move that should return true for king9
                    assertTrue(king9.isValidFaceKingMove(i, j, king9));
                else
                    assertFalse(king9.isValidFaceKingMove(i, j, king9));

                if (j == 1 && i == 5) // Move that should return true for king12
                    assertTrue(king12.isValidFaceKingMove(i, j, king12));
                else
                    assertFalse(king12.isValidFaceKingMove(i, j, king12));
            }
        }

        // Reset board
        reset();
    }

    /**
     * <p>Tests the <code>getOpposingKings</code> method of the <code>XiangqiKingPiece</code> class.</p>
     *
     * @since 1.0
     */
    @Test
    public void testOpposingKings() {
        // Create pieces
        XiangqiKingPiece king1 = new XiangqiKingPiece(ChessGame.Side.SOUTH, southBoard, ChessIcon.RED_GENERAL, 8, 3);
        XiangqiKingPiece king2 = new XiangqiKingPiece(ChessGame.Side.SOUTH, southBoard, ChessIcon.RED_GENERAL, 8, 4);
        XiangqiKingPiece king3 = new XiangqiKingPiece(ChessGame.Side.SOUTH, southBoard, ChessIcon.RED_GENERAL, 8, 5);
        XiangqiKingPiece king4 = new XiangqiKingPiece(ChessGame.Side.NORTH, southBoard, ChessIcon.BLACK_GENERAL, 1, 2);
        XiangqiKingPiece king5 = new XiangqiKingPiece(ChessGame.Side.NORTH, southBoard, ChessIcon.BLACK_GENERAL, 1, 4);
        XiangqiKingPiece king6 = new XiangqiKingPiece(ChessGame.Side.NORTH, southBoard, ChessIcon.BLACK_GENERAL, 1, 5);

        // Add pieces
        southBoard.addPiece(king1, 8, 3);
        southBoard.addPiece(king2, 8, 4);
        southBoard.addPiece(king3, 8, 5);
        southBoard.addPiece(king4, 1, 2);
        southBoard.addPiece(king5, 1, 4);
        southBoard.addPiece(king6, 1, 5);
        southBoard.addPiece(new CannonPiece(ChessGame.Side.SOUTH, southBoard, ChessIcon.RED_CANNON, 5, 4), 5, 4); // Not necessary but included here to match scenario tested in previous tests

        // Get opposing kings; these should all be the same
        XiangqiKingPiece[] opposingKings1 = king1.getOpposingKings();
        XiangqiKingPiece[] opposingKings2 = king1.getOpposingKings();
        XiangqiKingPiece[] opposingKings3 = king1.getOpposingKings();
        assertArrayEquals(opposingKings1, opposingKings2);
        assertArrayEquals(opposingKings1, opposingKings3);

        // Make sure exactly 3 opposing kings were found
        assertEquals(opposingKings1.length, 3);

        // Checks to make sure each of the opposing kings were found in the array
        assertTrue(findInArray(king4, opposingKings1));
        assertTrue(findInArray(king5, opposingKings1));
        assertTrue(findInArray(king6, opposingKings1));
    }
    //endregion

    /**
     * <p>Searches for a <code>XiangqiKingPiece</code> in an array.</p>
     *
     * @param piece         the xiangqi king piece
     * @param pieceArray    the array of xiangqi king pieces
     * @return              <code>true</code> if the piece is found in the array
     * @since 1.0
     */
    private boolean findInArray(XiangqiKingPiece piece, XiangqiKingPiece[] pieceArray) {
        // Iterates through piece array
        for (XiangqiKingPiece p : pieceArray) {
            if (p.equals(piece)) // Checks if piece is found
                return true;
        }

        return false; // Piece not found
    }

    /**
     * <p>Tests the constructor of the <code>GuardPiece</code> class.</p>
     *
     * @since 1.0
     */
    @Test
    public void testGuardPiece() {
        // Instantiates guard piece
        GuardPiece guard = new GuardPiece(ChessGame.Side.SOUTH, southBoard, ChessIcon.RED_GUARD, 1, 1);

        // Checks to see if label was set correctly
        assertEquals(guard.getLabel(), "G");

        // Reset board
        reset();
    }

    /**
     * <p>Tests the <code>isValidSingleDiagonal</code> method in the <code>CanSingleDiagonalMove</code> interface.</p>
     *
     * @since 1.0
     */
    @Test
    public void testSingleDiagonalMove() {
        // Create pieces
        GuardPiece guard1 = new GuardPiece(ChessGame.Side.NORTH, southBoard, ChessIcon.BLACK_GUARD, 2, 0);
        GuardPiece guard2 = new GuardPiece(ChessGame.Side.SOUTH, southBoard, ChessIcon.RED_GUARD, 9, 0);
        GuardPiece guard3 = new GuardPiece(ChessGame.Side.SOUTH, southBoard, ChessIcon.RED_GUARD, 8, 4);

        // Add pieces
        southBoard.addPiece(guard1, 2, 0);
        southBoard.addPiece(guard2, 9, 0);
        southBoard.addPiece(guard3, 8, 4);
        southBoard.addPiece(new CannonPiece(ChessGame.Side.NORTH, southBoard, ChessIcon.BLACK_CANNON, 3, 1), 3, 1);

        // Iterates through whole board
        for (int i = 0; i < southBoard.getGameRules().getNumRows(); i++) {
            for (int j = 0; j < southBoard.getGameRules().getNumColumns(); j++) {
                if (j == 1 && (i == 1 || i == 3)) { // Moves that should return true for guard1
                    assertTrue(guard1.isValidSingleDiagonalMove(i, j, guard1));
                    assertFalse(guard2.isValidSingleDiagonalMove(i, j, guard2));
                    assertFalse(guard3.isValidSingleDiagonalMove(i, j, guard3));
                } else if (j == 1 && i == 8) { // Moves that should return true for guard2
                    assertFalse(guard1.isValidSingleDiagonalMove(i, j, guard1));
                    assertTrue(guard2.isValidSingleDiagonalMove(i, j, guard2));
                    assertFalse(guard3.isValidSingleDiagonalMove(i, j, guard3));
                } else if ((j == 3 && (i == 7 || i == 9)) || (j == 5 && (i == 7 || i == 9))) { // Moves that should return true for guard3
                    assertFalse(guard1.isValidSingleDiagonalMove(i, j, guard1));
                    assertFalse(guard2.isValidSingleDiagonalMove(i, j, guard2));
                    assertTrue(guard3.isValidSingleDiagonalMove(i, j, guard3));
                } else { // Moves that are false for every piece
                    assertFalse(guard1.isValidSingleDiagonalMove(i, j, guard1));
                    assertFalse(guard2.isValidSingleDiagonalMove(i, j, guard2));
                    assertFalse(guard3.isValidSingleDiagonalMove(i, j, guard3));
                }
            }
        }

        // Reset board
        reset();
    }

    /**
     * <p>Tests the constructor of the <code>ElephantPiece</code> class.</p>
     *
     * @since 1.0
     */
    @Test
    public void testElephantPiece() {
        // Instantiates elephant piece
        ElephantPiece elephant = new ElephantPiece(ChessGame.Side.SOUTH, southBoard, ChessIcon.RED_ELEPHANT, 1, 1);

        // Checks to see if label was set correctly
        assertEquals(elephant.getLabel(), "E");

        // Reset board
        reset();
    }

    /**
     * <p>Tests the <code>isValidElephantMove</code> method in the <code>CanElephantMove</code> interface.</p>
     *
     * @since 1.0
     */
    @Test
    public void testElephantMove() {
        // Create pieces
        ElephantPiece elephant1 = new ElephantPiece(ChessGame.Side.SOUTH, southBoard, ChessIcon.RED_ELEPHANT, 7, 0);
        ElephantPiece elephant2 = new ElephantPiece(ChessGame.Side.SOUTH, southBoard, ChessIcon.RED_ELEPHANT, 7, 4);
        ElephantPiece elephant3 = new ElephantPiece(ChessGame.Side.SOUTH, southBoard, ChessIcon.RED_ELEPHANT, 5, 6);
        ElephantPiece elephant4 = new ElephantPiece(ChessGame.Side.SOUTH, southBoard, ChessIcon.RED_ELEPHANT, 9, 6);
        ElephantPiece elephant5 = new ElephantPiece(ChessGame.Side.NORTH, southBoard, ChessIcon.BLACK_ELEPHANT, 0, 2);
        ElephantPiece elephant6 = new ElephantPiece(ChessGame.Side.NORTH, southBoard, ChessIcon.BLACK_ELEPHANT, 4, 2);
        ElephantPiece elephant7 = new ElephantPiece(ChessGame.Side.NORTH, southBoard, ChessIcon.BLACK_ELEPHANT, 2, 4);
        ElephantPiece elephant8 = new ElephantPiece(ChessGame.Side.NORTH, southBoard, ChessIcon.BLACK_ELEPHANT, 2, 8);
        ElephantPiece elephant9 = new ElephantPiece(ChessGame.Side.WEST, westBoard, ChessIcon.RED_ELEPHANT, 0, 2);
        ElephantPiece elephant10 = new ElephantPiece(ChessGame.Side.WEST, westBoard, ChessIcon.RED_ELEPHANT, 4, 2);
        ElephantPiece elephant11 = new ElephantPiece(ChessGame.Side.WEST, westBoard, ChessIcon.RED_ELEPHANT, 6, 0);
        ElephantPiece elephant12 = new ElephantPiece(ChessGame.Side.WEST, westBoard, ChessIcon.RED_ELEPHANT, 6, 4);
        ElephantPiece elephant13 = new ElephantPiece(ChessGame.Side.EAST, westBoard, ChessIcon.BLACK_ELEPHANT, 2, 5);
        ElephantPiece elephant14 = new ElephantPiece(ChessGame.Side.EAST, westBoard, ChessIcon.BLACK_ELEPHANT, 2, 9);
        ElephantPiece elephant15 = new ElephantPiece(ChessGame.Side.EAST, westBoard, ChessIcon.BLACK_ELEPHANT, 4, 7);
        ElephantPiece elephant16 = new ElephantPiece(ChessGame.Side.EAST, westBoard, ChessIcon.BLACK_ELEPHANT, 8, 7);

        // Add pieces
        southBoard.addPiece(elephant1, 7, 0);
        southBoard.addPiece(elephant2, 7, 4);
        southBoard.addPiece(elephant3, 5, 6);
        southBoard.addPiece(elephant4, 9, 6);
        southBoard.addPiece(elephant5, 0, 2);
        southBoard.addPiece(elephant6, 4, 2);
        southBoard.addPiece(elephant7, 2, 4);
        southBoard.addPiece(elephant8, 2, 8);
        westBoard.addPiece(elephant9, 0, 2);
        westBoard.addPiece(elephant10, 4, 2);
        westBoard.addPiece(elephant11, 6, 0);
        westBoard.addPiece(elephant12, 6, 4);
        westBoard.addPiece(elephant13, 2, 5);
        westBoard.addPiece(elephant14, 2, 9);
        westBoard.addPiece(elephant15, 4, 7);
        westBoard.addPiece(elephant16, 8, 7);

        // Iterates through south board
        for (int i = 0; i < southBoard.getGameRules().getNumRows(); i++) {
            for (int j = 0; j < southBoard.getGameRules().getNumColumns(); j++) {
                if (j == 2 && (i == 5 || i == 9)) {
                    assertTrue(elephant1.isValidElephantMove(i, j, elephant1));
                    assertTrue(elephant2.isValidElephantMove(i, j, elephant2));
                    assertFalse(elephant3.isValidElephantMove(i, j, elephant3));
                    assertFalse(elephant4.isValidElephantMove(i, j, elephant4));
                    assertFalse(elephant5.isValidElephantMove(i, j, elephant5));
                    assertFalse(elephant6.isValidElephantMove(i, j, elephant6));
                    assertFalse(elephant7.isValidElephantMove(i, j, elephant7));
                    assertFalse(elephant8.isValidElephantMove(i, j, elephant8));
                } else if (i == 7 && j == 4) {
                    assertFalse(elephant1.isValidElephantMove(i, j, elephant1));
                    assertFalse(elephant2.isValidElephantMove(i, j, elephant2));
                    assertTrue(elephant3.isValidElephantMove(i, j, elephant3));
                    assertTrue(elephant4.isValidElephantMove(i, j, elephant4));
                    assertFalse(elephant5.isValidElephantMove(i, j, elephant5));
                    assertFalse(elephant6.isValidElephantMove(i, j, elephant6));
                    assertFalse(elephant7.isValidElephantMove(i, j, elephant7));
                    assertFalse(elephant8.isValidElephantMove(i, j, elephant8));
                } else if (j == 6 && (i == 5 || i == 9)) {
                    assertFalse(elephant1.isValidElephantMove(i, j, elephant1));
                    assertTrue(elephant2.isValidElephantMove(i, j, elephant2));
                    assertFalse(elephant3.isValidElephantMove(i, j, elephant3));
                    assertFalse(elephant4.isValidElephantMove(i, j, elephant4));
                    assertFalse(elephant5.isValidElephantMove(i, j, elephant5));
                    assertFalse(elephant6.isValidElephantMove(i, j, elephant6));
                    assertFalse(elephant7.isValidElephantMove(i, j, elephant7));
                    assertFalse(elephant8.isValidElephantMove(i, j, elephant8));
                } else if (i == 7 && j == 8) {
                    assertFalse(elephant1.isValidElephantMove(i, j, elephant1));
                    assertFalse(elephant2.isValidElephantMove(i, j, elephant2));
                    assertTrue(elephant3.isValidElephantMove(i, j, elephant3));
                    assertTrue(elephant4.isValidElephantMove(i, j, elephant4));
                    assertFalse(elephant5.isValidElephantMove(i, j, elephant5));
                    assertFalse(elephant6.isValidElephantMove(i, j, elephant6));
                    assertFalse(elephant7.isValidElephantMove(i, j, elephant7));
                    assertFalse(elephant8.isValidElephantMove(i, j, elephant8));
                } else if (i == 2 && j == 0) {
                    assertFalse(elephant1.isValidElephantMove(i, j, elephant1));
                    assertFalse(elephant2.isValidElephantMove(i, j, elephant2));
                    assertFalse(elephant3.isValidElephantMove(i, j, elephant3));
                    assertFalse(elephant4.isValidElephantMove(i, j, elephant4));
                    assertTrue(elephant5.isValidElephantMove(i, j, elephant5));
                    assertTrue(elephant6.isValidElephantMove(i, j, elephant6));
                    assertFalse(elephant7.isValidElephantMove(i, j, elephant7));
                    assertFalse(elephant8.isValidElephantMove(i, j, elephant8));
                } else if (j == 2 && (i == 0 || i == 4)) {
                    assertFalse(elephant1.isValidElephantMove(i, j, elephant1));
                    assertFalse(elephant2.isValidElephantMove(i, j, elephant2));
                    assertFalse(elephant3.isValidElephantMove(i, j, elephant3));
                    assertFalse(elephant4.isValidElephantMove(i, j, elephant4));
                    assertFalse(elephant5.isValidElephantMove(i, j, elephant5));
                    assertFalse(elephant6.isValidElephantMove(i, j, elephant6));
                    assertTrue(elephant7.isValidElephantMove(i, j, elephant7));
                    assertFalse(elephant8.isValidElephantMove(i, j, elephant8));
                } else if (i == 2 && j == 4) {
                    assertFalse(elephant1.isValidElephantMove(i, j, elephant1));
                    assertFalse(elephant2.isValidElephantMove(i, j, elephant2));
                    assertFalse(elephant3.isValidElephantMove(i, j, elephant3));
                    assertFalse(elephant4.isValidElephantMove(i, j, elephant4));
                    assertTrue(elephant5.isValidElephantMove(i, j, elephant5));
                    assertTrue(elephant6.isValidElephantMove(i, j, elephant6));
                    assertFalse(elephant7.isValidElephantMove(i, j, elephant7));
                    assertFalse(elephant8.isValidElephantMove(i, j, elephant8));
                } else if (j == 6 && (i == 0 || i == 4)) {
                    assertFalse(elephant1.isValidElephantMove(i, j, elephant1));
                    assertFalse(elephant2.isValidElephantMove(i, j, elephant2));
                    assertFalse(elephant3.isValidElephantMove(i, j, elephant3));
                    assertFalse(elephant4.isValidElephantMove(i, j, elephant4));
                    assertFalse(elephant5.isValidElephantMove(i, j, elephant5));
                    assertFalse(elephant6.isValidElephantMove(i, j, elephant6));
                    assertTrue(elephant7.isValidElephantMove(i, j, elephant7));
                    assertTrue(elephant8.isValidElephantMove(i, j, elephant8));
                } else { // Not a move for any piece
                    assertFalse(elephant1.isValidElephantMove(i, j, elephant1));
                    assertFalse(elephant2.isValidElephantMove(i, j, elephant2));
                    assertFalse(elephant3.isValidElephantMove(i, j, elephant3));
                    assertFalse(elephant4.isValidElephantMove(i, j, elephant4));
                    assertFalse(elephant5.isValidElephantMove(i, j, elephant5));
                    assertFalse(elephant6.isValidElephantMove(i, j, elephant6));
                    assertFalse(elephant7.isValidElephantMove(i, j, elephant7));
                    assertFalse(elephant8.isValidElephantMove(i, j, elephant8));
                }
            }
        }

        // Iterates through west board
        for (int i = 0; i < southBoard.getGameRules().getNumRows(); i++) {
            for (int j = 0; j < southBoard.getGameRules().getNumColumns(); j++) {
                if (i == 2 && (j == 0 || j == 4)) {
                    assertTrue(elephant9.isValidElephantMove(i, j, elephant9));
                    assertTrue(elephant10.isValidElephantMove(i, j, elephant10));
                    assertFalse(elephant11.isValidElephantMove(i, j, elephant11));
                    assertFalse(elephant12.isValidElephantMove(i, j, elephant12));
                    assertFalse(elephant13.isValidElephantMove(i, j, elephant13));
                    assertFalse(elephant14.isValidElephantMove(i, j, elephant14));
                    assertFalse(elephant15.isValidElephantMove(i, j, elephant15));
                    assertFalse(elephant16.isValidElephantMove(i, j, elephant16));
                } else if (i == 4 && j == 2) {
                    assertFalse(elephant9.isValidElephantMove(i, j, elephant9));
                    assertFalse(elephant10.isValidElephantMove(i, j, elephant10));
                    assertTrue(elephant11.isValidElephantMove(i, j, elephant11));
                    assertTrue(elephant12.isValidElephantMove(i, j, elephant12));
                    assertFalse(elephant13.isValidElephantMove(i, j, elephant13));
                    assertFalse(elephant14.isValidElephantMove(i, j, elephant14));
                    assertFalse(elephant15.isValidElephantMove(i, j, elephant15));
                    assertFalse(elephant16.isValidElephantMove(i, j, elephant16));
                } else if (i == 6 && (j == 0 || j == 4)) {
                    assertFalse(elephant9.isValidElephantMove(i, j, elephant9));
                    assertTrue(elephant10.isValidElephantMove(i, j, elephant10));
                    assertFalse(elephant11.isValidElephantMove(i, j, elephant11));
                    assertFalse(elephant12.isValidElephantMove(i, j, elephant12));
                    assertFalse(elephant13.isValidElephantMove(i, j, elephant13));
                    assertFalse(elephant14.isValidElephantMove(i, j, elephant14));
                    assertFalse(elephant15.isValidElephantMove(i, j, elephant15));
                    assertFalse(elephant16.isValidElephantMove(i, j, elephant16));
                } else if (i == 8 && j == 2) {
                    assertFalse(elephant9.isValidElephantMove(i, j, elephant9));
                    assertFalse(elephant10.isValidElephantMove(i, j, elephant10));
                    assertTrue(elephant11.isValidElephantMove(i, j, elephant11));
                    assertTrue(elephant12.isValidElephantMove(i, j, elephant12));
                    assertFalse(elephant13.isValidElephantMove(i, j, elephant13));
                    assertFalse(elephant14.isValidElephantMove(i, j, elephant14));
                    assertFalse(elephant15.isValidElephantMove(i, j, elephant15));
                    assertFalse(elephant16.isValidElephantMove(i, j, elephant16));
                } else if (i == 0 && j == 7) {
                    assertFalse(elephant9.isValidElephantMove(i, j, elephant9));
                    assertFalse(elephant10.isValidElephantMove(i, j, elephant10));
                    assertFalse(elephant11.isValidElephantMove(i, j, elephant11));
                    assertFalse(elephant12.isValidElephantMove(i, j, elephant12));
                    assertTrue(elephant13.isValidElephantMove(i, j, elephant13));
                    assertTrue(elephant14.isValidElephantMove(i, j, elephant14));
                    assertFalse(elephant15.isValidElephantMove(i, j, elephant15));
                    assertFalse(elephant16.isValidElephantMove(i, j, elephant16));
                } else if (i == 2 && (j == 5 || j == 9)) {
                    assertFalse(elephant9.isValidElephantMove(i, j, elephant9));
                    assertFalse(elephant10.isValidElephantMove(i, j, elephant10));
                    assertFalse(elephant11.isValidElephantMove(i, j, elephant11));
                    assertFalse(elephant12.isValidElephantMove(i, j, elephant12));
                    assertFalse(elephant13.isValidElephantMove(i, j, elephant13));
                    assertFalse(elephant14.isValidElephantMove(i, j, elephant14));
                    assertTrue(elephant15.isValidElephantMove(i, j, elephant15));
                    assertFalse(elephant16.isValidElephantMove(i, j, elephant16));
                } else if (i == 4 && j == 7) {
                    assertFalse(elephant9.isValidElephantMove(i, j, elephant9));
                    assertFalse(elephant10.isValidElephantMove(i, j, elephant10));
                    assertFalse(elephant11.isValidElephantMove(i, j, elephant11));
                    assertFalse(elephant12.isValidElephantMove(i, j, elephant12));
                    assertTrue(elephant13.isValidElephantMove(i, j, elephant13));
                    assertTrue(elephant14.isValidElephantMove(i, j, elephant14));
                    assertFalse(elephant15.isValidElephantMove(i, j, elephant15));
                    assertFalse(elephant16.isValidElephantMove(i, j, elephant16));
                } else if (i == 6 && (j == 5 || j == 9)) {
                    assertFalse(elephant9.isValidElephantMove(i, j, elephant9));
                    assertFalse(elephant10.isValidElephantMove(i, j, elephant10));
                    assertFalse(elephant11.isValidElephantMove(i, j, elephant11));
                    assertFalse(elephant12.isValidElephantMove(i, j, elephant12));
                    assertFalse(elephant13.isValidElephantMove(i, j, elephant13));
                    assertFalse(elephant14.isValidElephantMove(i, j, elephant14));
                    assertTrue(elephant15.isValidElephantMove(i, j, elephant15));
                    assertTrue(elephant16.isValidElephantMove(i, j, elephant16));
                } else { // Not a move for any piece
                    assertFalse(elephant13.isValidElephantMove(i, j, elephant13));
                    assertFalse(elephant14.isValidElephantMove(i, j, elephant14));
                    assertFalse(elephant15.isValidElephantMove(i, j, elephant15));
                    assertFalse(elephant16.isValidElephantMove(i, j, elephant16));
                    assertFalse(elephant9.isValidElephantMove(i, j, elephant9));
                    assertFalse(elephant10.isValidElephantMove(i, j, elephant10));
                    assertFalse(elephant11.isValidElephantMove(i, j, elephant11));
                    assertFalse(elephant12.isValidElephantMove(i, j, elephant12));
                }
            }
        }

        // Reset board
        reset();
    }

    /**
     * <p>Tests the constructor of the <code>SoldierPiece</code> class.</p>
     *
     * @since 1.0
     */
    @Test
    public void testSoldierPiece() {
        // Instantiates elephant piece
        SoldierPiece soldier = new SoldierPiece(ChessGame.Side.SOUTH, southBoard, ChessIcon.RED_SOLDIER, 1, 1);

        // Checks to see if label was set correctly
        assertEquals(soldier.getLabel(), "S");

        // Reset board
        reset();
    }

    /**
     * <p>Tests the <code>isValidSoldierMove</code> method in the <code>CanSoldierMove</code> interface.</p>
     *
     * @since 1.0
     */
    @Test
    public void testSoldierMove() {
        // Create pieces
        SoldierPiece soldier1 = new SoldierPiece(ChessGame.Side.SOUTH, southBoard, ChessIcon.RED_SOLDIER, 6, 2);
        SoldierPiece soldier2 = new SoldierPiece(ChessGame.Side.SOUTH, southBoard, ChessIcon.RED_SOLDIER, 3, 2);
        SoldierPiece soldier3 = new SoldierPiece(ChessGame.Side.SOUTH, southBoard, ChessIcon.RED_SOLDIER, 0, 2);
        SoldierPiece soldier4 = new SoldierPiece(ChessGame.Side.NORTH, southBoard, ChessIcon.BLACK_SOLDIER, 3, 6);
        SoldierPiece soldier5 = new SoldierPiece(ChessGame.Side.NORTH, southBoard, ChessIcon.BLACK_SOLDIER, 6, 6);
        SoldierPiece soldier6 = new SoldierPiece(ChessGame.Side.NORTH, southBoard, ChessIcon.BLACK_SOLDIER, 9, 6);
        SoldierPiece soldier7 = new SoldierPiece(ChessGame.Side.WEST, westBoard, ChessIcon.RED_SOLDIER, 2, 3);
        SoldierPiece soldier8 = new SoldierPiece(ChessGame.Side.WEST, westBoard, ChessIcon.RED_SOLDIER, 2, 6);
        SoldierPiece soldier9 = new SoldierPiece(ChessGame.Side.WEST, westBoard, ChessIcon.RED_SOLDIER, 2, 9);
        SoldierPiece soldier10 = new SoldierPiece(ChessGame.Side.EAST, westBoard, ChessIcon.BLACK_SOLDIER, 6, 6);
        SoldierPiece soldier11 = new SoldierPiece(ChessGame.Side.EAST, westBoard, ChessIcon.BLACK_SOLDIER, 6, 3);
        SoldierPiece soldier12 = new SoldierPiece(ChessGame.Side.EAST, westBoard, ChessIcon.BLACK_SOLDIER, 6, 0);

        // Add pieces
        southBoard.addPiece(soldier1, 6, 2);
        southBoard.addPiece(soldier2, 3, 2);
        southBoard.addPiece(soldier3, 0, 2);
        southBoard.addPiece(soldier4, 3, 6);
        southBoard.addPiece(soldier5, 6, 6);
        southBoard.addPiece(soldier6, 9, 6);
        westBoard.addPiece(soldier7, 2, 3);
        westBoard.addPiece(soldier8, 2, 6);
        westBoard.addPiece(soldier9, 2, 9);
        westBoard.addPiece(soldier10, 6, 6);
        westBoard.addPiece(soldier11, 6, 3);
        westBoard.addPiece(soldier12, 6, 0);

        // Iterates through south board
        for (int i = 0; i < southBoard.getGameRules().getNumRows(); i++) {
            for (int j = 0; j < southBoard.getGameRules().getNumColumns(); j++) {
                if (i == 5 && j == 2) {
                    assertTrue(soldier1.isValidSoldierMove(i, j, soldier1));
                    assertFalse(soldier2.isValidSoldierMove(i, j, soldier2));
                    assertFalse(soldier3.isValidSoldierMove(i, j, soldier3));
                    assertFalse(soldier4.isValidSoldierMove(i, j, soldier4));
                    assertFalse(soldier5.isValidSoldierMove(i, j, soldier5));
                    assertFalse(soldier6.isValidSoldierMove(i, j, soldier6));
                } else if ((i == 3 && (j == 1 || j == 3)) || (i == 2 && j == 2)) {
                    assertFalse(soldier1.isValidSoldierMove(i, j, soldier1));
                    assertTrue(soldier2.isValidSoldierMove(i, j, soldier2));
                    assertFalse(soldier3.isValidSoldierMove(i, j, soldier3));
                    assertFalse(soldier4.isValidSoldierMove(i, j, soldier4));
                    assertFalse(soldier5.isValidSoldierMove(i, j, soldier5));
                    assertFalse(soldier6.isValidSoldierMove(i, j, soldier6));
                } else if (i == 0 && (j == 1 || j == 3)) {
                    assertFalse(soldier1.isValidSoldierMove(i, j, soldier1));
                    assertFalse(soldier2.isValidSoldierMove(i, j, soldier2));
                    assertTrue(soldier3.isValidSoldierMove(i, j, soldier3));
                    assertFalse(soldier4.isValidSoldierMove(i, j, soldier4));
                    assertFalse(soldier5.isValidSoldierMove(i, j, soldier5));
                    assertFalse(soldier6.isValidSoldierMove(i, j, soldier6));
                } else if (i == 4 && j == 6) {
                    assertFalse(soldier1.isValidSoldierMove(i, j, soldier1));
                    assertFalse(soldier2.isValidSoldierMove(i, j, soldier2));
                    assertFalse(soldier3.isValidSoldierMove(i, j, soldier3));
                    assertTrue(soldier4.isValidSoldierMove(i, j, soldier4));
                    assertFalse(soldier5.isValidSoldierMove(i, j, soldier5));
                    assertFalse(soldier6.isValidSoldierMove(i, j, soldier6));
                } else if ((i == 6 && (j == 5 || j == 7)) || (i == 7 && j == 6)) {
                    assertFalse(soldier1.isValidSoldierMove(i, j, soldier1));
                    assertFalse(soldier2.isValidSoldierMove(i, j, soldier2));
                    assertFalse(soldier3.isValidSoldierMove(i, j, soldier3));
                    assertFalse(soldier4.isValidSoldierMove(i, j, soldier4));
                    assertTrue(soldier5.isValidSoldierMove(i, j, soldier5));
                    assertFalse(soldier6.isValidSoldierMove(i, j, soldier6));
                } else if (i == 9 && (j == 5 || j == 7)) {
                    assertFalse(soldier1.isValidSoldierMove(i, j, soldier1));
                    assertFalse(soldier2.isValidSoldierMove(i, j, soldier2));
                    assertFalse(soldier3.isValidSoldierMove(i, j, soldier3));
                    assertFalse(soldier4.isValidSoldierMove(i, j, soldier4));
                    assertFalse(soldier5.isValidSoldierMove(i, j, soldier5));
                    assertTrue(soldier6.isValidSoldierMove(i, j, soldier6));
                } else { // Not a move for any piece
                    assertFalse(soldier1.isValidSoldierMove(i, j, soldier1));
                    assertFalse(soldier2.isValidSoldierMove(i, j, soldier2));
                    assertFalse(soldier3.isValidSoldierMove(i, j, soldier3));
                    assertFalse(soldier4.isValidSoldierMove(i, j, soldier4));
                    assertFalse(soldier5.isValidSoldierMove(i, j, soldier5));
                    assertFalse(soldier6.isValidSoldierMove(i, j, soldier6));
                }
            }
        }

        // Iterates through west board
        for (int i = 0; i < westBoard.getGameRules().getNumRows(); i++) {
            for (int j = 0; j < westBoard.getGameRules().getNumColumns(); j++) {
                if (i == 2 && j == 4) {
                    assertTrue(soldier7.isValidSoldierMove(i, j, soldier7));
                    assertFalse(soldier8.isValidSoldierMove(i, j, soldier8));
                    assertFalse(soldier9.isValidSoldierMove(i, j, soldier9));
                    assertFalse(soldier10.isValidSoldierMove(i, j, soldier10));
                    assertFalse(soldier11.isValidSoldierMove(i, j, soldier11));
                    assertFalse(soldier12.isValidSoldierMove(i, j, soldier12));
                } else if ((j == 6 && (i == 1 || i == 3)) || (i == 2 && j == 7)) {
                    assertFalse(soldier7.isValidSoldierMove(i, j, soldier7));
                    assertTrue(soldier8.isValidSoldierMove(i, j, soldier8));
                    assertFalse(soldier9.isValidSoldierMove(i, j, soldier9));
                    assertFalse(soldier10.isValidSoldierMove(i, j, soldier10));
                    assertFalse(soldier11.isValidSoldierMove(i, j, soldier11));
                    assertFalse(soldier12.isValidSoldierMove(i, j, soldier12));
                } else if (j == 9 && (i == 1 || i == 3)) {
                    assertFalse(soldier7.isValidSoldierMove(i, j, soldier7));
                    assertFalse(soldier8.isValidSoldierMove(i, j, soldier8));
                    assertTrue(soldier9.isValidSoldierMove(i, j, soldier9));
                    assertFalse(soldier10.isValidSoldierMove(i, j, soldier10));
                    assertFalse(soldier11.isValidSoldierMove(i, j, soldier11));
                    assertFalse(soldier12.isValidSoldierMove(i, j, soldier12));
                } else if (i == 6 && j == 5) {
                    assertFalse(soldier7.isValidSoldierMove(i, j, soldier7));
                    assertFalse(soldier8.isValidSoldierMove(i, j, soldier8));
                    assertFalse(soldier9.isValidSoldierMove(i, j, soldier9));
                    assertTrue(soldier10.isValidSoldierMove(i, j, soldier10));
                    assertFalse(soldier11.isValidSoldierMove(i, j, soldier11));
                    assertFalse(soldier12.isValidSoldierMove(i, j, soldier12));
                } else if ((j == 3 && (i == 5 || i == 7)) || (i == 6 && j == 2)) {
                    assertFalse(soldier7.isValidSoldierMove(i, j, soldier7));
                    assertFalse(soldier8.isValidSoldierMove(i, j, soldier8));
                    assertFalse(soldier9.isValidSoldierMove(i, j, soldier9));
                    assertFalse(soldier10.isValidSoldierMove(i, j, soldier10));
                    assertTrue(soldier11.isValidSoldierMove(i, j, soldier11));
                    assertFalse(soldier12.isValidSoldierMove(i, j, soldier12));
                } else if (j == 0 && (i == 5 || i == 7)) {
                    assertFalse(soldier7.isValidSoldierMove(i, j, soldier7));
                    assertFalse(soldier8.isValidSoldierMove(i, j, soldier8));
                    assertFalse(soldier9.isValidSoldierMove(i, j, soldier9));
                    assertFalse(soldier10.isValidSoldierMove(i, j, soldier10));
                    assertFalse(soldier11.isValidSoldierMove(i, j, soldier11));
                    assertTrue(soldier12.isValidSoldierMove(i, j, soldier12));
                } else { // Not a move for any piece
                    assertFalse(soldier7.isValidSoldierMove(i, j, soldier7));
                    assertFalse(soldier8.isValidSoldierMove(i, j, soldier8));
                    assertFalse(soldier9.isValidSoldierMove(i, j, soldier9));
                    assertFalse(soldier10.isValidSoldierMove(i, j, soldier10));
                    assertFalse(soldier11.isValidSoldierMove(i, j, soldier11));
                    assertFalse(soldier12.isValidSoldierMove(i, j, soldier12));
                }
            }
        }

        // Reset board
        reset();
    }

    /**
     * <p>Tests the constructor of the <code>CannonPiece</code> class.</p>
     *
     * @since 1.0
     */
    @Test
    public void testCannonPiece() {
        // Instantiates elephant piece
        CannonPiece cannon = new CannonPiece(ChessGame.Side.SOUTH, southBoard, ChessIcon.RED_CANNON, 1, 1);

        // Checks to see if label was set correctly
        assertEquals(cannon.getLabel(), "C");

        // Reset board
        reset();
    }

    /**
     * <p>Tests the <code>isValidCannonMove</code> method in the <code>CanCannonMove</code> interface.</p>
     *
     * @since 1.0
     */
    @Test
    public void testCannonMove() {
        // Create pieces
        CannonPiece cannon1 = new CannonPiece(ChessGame.Side.SOUTH, southBoard, ChessIcon.RED_CANNON, 0, 8);
        CannonPiece cannon2 = new CannonPiece(ChessGame.Side.SOUTH, southBoard, ChessIcon.RED_CANNON, 5, 3);

        // Add pieces
        southBoard.addPiece(cannon1, 0, 8);
        southBoard.addPiece(cannon2, 5, 3);
        southBoard.addPiece(new RookPiece(ChessGame.Side.NORTH, southBoard, ChessIcon.BLACK_CHARIOT, 0, 0), 0, 0);
        southBoard.addPiece(new RookPiece(ChessGame.Side.NORTH, southBoard, ChessIcon.BLACK_CHARIOT, 3, 3), 3, 3);
        southBoard.addPiece(new RookPiece(ChessGame.Side.SOUTH, southBoard, ChessIcon.RED_CHARIOT, 5, 5), 5, 5);
        southBoard.addPiece(new ElephantPiece(ChessGame.Side.NORTH, southBoard, ChessIcon.BLACK_ELEPHANT, 0, 3), 0, 3);
        southBoard.addPiece(new HorsePiece(ChessGame.Side.NORTH, southBoard, ChessIcon.BLACK_HORSE, 5, 0), 5, 0);
        southBoard.addPiece(new SoldierPiece(ChessGame.Side.NORTH, southBoard, ChessIcon.BLACK_SOLDIER, 5, 7), 5, 7);
        southBoard.addPiece(new GuardPiece(ChessGame.Side.NORTH, southBoard, ChessIcon.RED_GUARD, 9, 3), 9, 3);

        // Iterates through entire board
        for (int i = 0; i < southBoard.getGameRules().getNumRows(); i++) {
            for (int j = 0; j < southBoard.getGameRules().getNumColumns(); j++) {
                if (i == 0 && j <= 2) { // Move for cannon1
                    assertTrue(cannon1.isValidCannonMove(i, j, cannon1));
                    assertFalse(cannon2.isValidCannonMove(i, j, cannon2));
                } else if ((j == 3 && i <= 2) || (i == 5 && (j == 6 || j == 7))) { // Move for cannon2
                    assertFalse(cannon1.isValidCannonMove(i, j, cannon1));
                    assertTrue(cannon2.isValidCannonMove(i, j, cannon2));
                } else { // Move for neither
                    assertFalse(cannon1.isValidCannonMove(i, j, cannon1));
                    assertFalse(cannon2.isValidCannonMove(i, j, cannon2));
                }
            }
        }

        // Reset board
        reset();
    }

    /**
     * <p>Tests the constructor of the <code>HorsePiece</code> class.</p>
     *
     * @since 1.0
     */
    @Test
    public void testHorsePiece() {
        // Instantiates horse piece
        HorsePiece cannon = new HorsePiece(ChessGame.Side.SOUTH, southBoard, ChessIcon.RED_HORSE, 1, 1);

        // Checks to see if label was set correctly
        assertEquals(cannon.getLabel(), "H");

        // Reset board
        reset();
    }


    /**
     * <p>Tests the <code>isValidHorseMove</code> method in the <code>CanHorseMove</code> interface.</p>
     *
     * @since 1.0
     */
    @Test
    public void testHorseMove() {
        // Create pieces
        HorsePiece horse1 = new HorsePiece(ChessGame.Side.SOUTH, southBoard, ChessIcon.RED_HORSE, 5, 4);
        HorsePiece horse2 = new HorsePiece(ChessGame.Side.NORTH, southBoard, ChessIcon.BLACK_HORSE, 3, 5);

        // Add pieces
        southBoard.addPiece(horse1, 5, 4);
        southBoard.addPiece(horse2, 3, 5);
        southBoard.addPiece(new CannonPiece(ChessGame.Side.NORTH, southBoard, ChessIcon.BLACK_CANNON, 4, 4), 4, 4);
        southBoard.addPiece(new SoldierPiece(ChessGame.Side.NORTH, southBoard, ChessIcon.BLACK_SOLDIER, 3, 6), 3, 6);

        // Iterates through entire board
        for (int i = 0; i < southBoard.getGameRules().getNumRows(); i++) {
            for (int j = 0; j < southBoard.getGameRules().getNumColumns(); j++) {
                if ((i == 1 && (j == 4 || j == 6)) || (j == 3 && (i == 2 || i == 4)) || (i == 5 && (j == 4 || j == 6))) { // Move for horse2
                    assertTrue(horse2.isValidHorseMove(i, j, horse2));
                    assertFalse(horse1.isValidHorseMove(i, j, horse1));
                } else if ((i == 4 && (j == 2 || j == 6)) || (i == 6 && (j == 2 || j == 6)) || (i == 7 && (j == 3 || j == 5))) { // Move for horse1
                    assertFalse(horse2.isValidHorseMove(i, j, horse2));
                    assertTrue(horse1.isValidHorseMove(i, j, horse1));
                } else { // Move for neither
                    assertFalse(horse1.isValidHorseMove(i, j, horse1));
                    assertFalse(horse2.isValidHorseMove(i, j, horse2));
                }
            }
        }

        // Reset board
        reset();
    }

    /**
     * <p>Tests the <code>getNumRows</code> and <code>getNumColumns</code> methods in the <code>Xiangqi</code> class.</p>
     *
     * @since 1.0
     */
    @Test
    public void testXiangqiDimensions() {
        // Create Xiangqi games
        Xiangqi northSouth = new Xiangqi(ChessGame.Side.SOUTH);
        Xiangqi westEast = new Xiangqi(ChessGame.Side.WEST);

        // North-South should return 10 rows and 9 columns, West-East should return 9 rows and 10 columns
        assertEquals(northSouth.getNumRows(), 10);
        assertEquals(northSouth.getNumColumns(), 9);
        assertEquals(westEast.getNumRows(), 9);
        assertEquals(westEast.getNumColumns(), 10);

        // Reset board
        reset();
    }

    /**
     * <p>Tests the <code>startGame</code> method in the <code>Xiangqi</code> class.</p>
     *
     * @since 1.0
     */
    @Test
    public void testStartGame() {
        // Create Xiangqi games
        Xiangqi northSouth = new Xiangqi(ChessGame.Side.SOUTH);

        // Start game
        southBoard = new BasicChessBoard(northSouth);
        northSouth.startGame(southBoard);

        // Tests soldiers
        for (int i = 0; i < 9; i += 2) {
            assertEquals(southBoard.getPiece(6, i), new SoldierPiece(ChessGame.Side.SOUTH, southBoard, ChessIcon.RED_SOLDIER, 6, i));
            assertEquals(southBoard.getPiece(3, i), new SoldierPiece(ChessGame.Side.NORTH, southBoard, ChessIcon.BLACK_SOLDIER, 3, i));
        }

        // Test rooks
        assertEquals(southBoard.getPiece(9, 0), new RookPiece(ChessGame.Side.SOUTH, southBoard, ChessIcon.RED_CHARIOT, 9, 0));
        assertEquals(southBoard.getPiece(9, 8), new RookPiece(ChessGame.Side.SOUTH, southBoard, ChessIcon.RED_CHARIOT, 9, 8));
        assertEquals(southBoard.getPiece(0, 0), new RookPiece(ChessGame.Side.NORTH, southBoard, ChessIcon.BLACK_CHARIOT, 0, 0));
        assertEquals(southBoard.getPiece(0, 8), new RookPiece(ChessGame.Side.NORTH, southBoard, ChessIcon.BLACK_CHARIOT, 0, 8));

        // Tests cannons
        assertEquals(southBoard.getPiece(7, 1), new CannonPiece(ChessGame.Side.SOUTH, southBoard, ChessIcon.RED_CANNON, 7, 1));
        assertEquals(southBoard.getPiece(7, 7), new CannonPiece(ChessGame.Side.SOUTH, southBoard, ChessIcon.RED_CANNON, 7, 7));
        assertEquals(southBoard.getPiece(2, 1), new CannonPiece(ChessGame.Side.NORTH, southBoard, ChessIcon.BLACK_CANNON, 2, 1));
        assertEquals(southBoard.getPiece(2, 7), new CannonPiece(ChessGame.Side.NORTH, southBoard, ChessIcon.BLACK_CANNON, 2, 7));

        // Tests horses
        assertEquals(southBoard.getPiece(9, 1), new HorsePiece(ChessGame.Side.SOUTH, southBoard, ChessIcon.RED_HORSE, 9, 1));
        assertEquals(southBoard.getPiece(9, 7), new HorsePiece(ChessGame.Side.SOUTH, southBoard, ChessIcon.RED_HORSE, 9, 7));
        assertEquals(southBoard.getPiece(0, 1), new HorsePiece(ChessGame.Side.NORTH, southBoard, ChessIcon.BLACK_HORSE, 0, 1));
        assertEquals(southBoard.getPiece(0, 7), new HorsePiece(ChessGame.Side.NORTH, southBoard, ChessIcon.BLACK_HORSE, 0, 7));

        // Tests elephants
        assertEquals(southBoard.getPiece(9, 2), new ElephantPiece(ChessGame.Side.SOUTH, southBoard, ChessIcon.RED_ELEPHANT, 9, 2));
        assertEquals(southBoard.getPiece(9, 6), new ElephantPiece(ChessGame.Side.SOUTH, southBoard, ChessIcon.RED_ELEPHANT, 9, 6));
        assertEquals(southBoard.getPiece(0, 2), new ElephantPiece(ChessGame.Side.NORTH, southBoard, ChessIcon.BLACK_ELEPHANT, 0, 2));
        assertEquals(southBoard.getPiece(0, 6), new ElephantPiece(ChessGame.Side.NORTH, southBoard, ChessIcon.BLACK_ELEPHANT, 0, 6));

        // Tests guards
        assertEquals(southBoard.getPiece(9, 3), new GuardPiece(ChessGame.Side.SOUTH, southBoard, ChessIcon.RED_GUARD, 9, 3));
        assertEquals(southBoard.getPiece(9, 5), new GuardPiece(ChessGame.Side.SOUTH, southBoard, ChessIcon.RED_GUARD, 9, 5));
        assertEquals(southBoard.getPiece(0, 3), new GuardPiece(ChessGame.Side.NORTH, southBoard, ChessIcon.BLACK_GUARD, 0, 3));
        assertEquals(southBoard.getPiece(0, 5), new GuardPiece(ChessGame.Side.NORTH, southBoard, ChessIcon.BLACK_GUARD, 0, 5));

        // Tests kings
        assertEquals(southBoard.getPiece(9, 4), new XiangqiKingPiece(ChessGame.Side.SOUTH, southBoard, ChessIcon.RED_GENERAL, 9, 4));
        assertEquals(southBoard.getPiece(0, 4), new XiangqiKingPiece(ChessGame.Side.NORTH, southBoard, ChessIcon.BLACK_GENERAL, 0, 4));

        // Reset board
        reset();
    }
    //endregion
}