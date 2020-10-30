import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Represents a class that tests methods in the chess program.
 * 
 * @author Nobel Zhou (nxz157)
 * @version 1.0, 10/30/20
 */
public class ChessTester {

    /* FIELDS */

    //Stores the empty chess board
    private ChessBoard southBoard, westBoard;

    /* CONSTRUCTORS */

    /**
     * Initializes the empty chess boards.
     * @since 1.0
     */
    public ChessTester() {
        southBoard = new ChessBoard(8, 8, new EuropeanChessDisplay(), new EuropeanChess(ChessGame.Side.SOUTH));
        westBoard = new ChessBoard(8, 8, new EuropeanChessDisplay(), new EuropeanChess(ChessGame.Side.WEST));
    }

    /* METHODS */
    /**
     * Resets all the boards.
     * @since 1.0
     */
    public void reset() {
        southBoard.close();
        westBoard.close();
        southBoard = new ChessBoard(8, 8, new EuropeanChessDisplay(), new EuropeanChess(ChessGame.Side.SOUTH));
        westBoard = new ChessBoard(8, 8, new EuropeanChessDisplay(), new EuropeanChess(ChessGame.Side.WEST));
    }

    /**
     * Tests the getIconHeight() and the getIconWidth() method in the ChessIcon enum.
     * Also tests the private constructors in the enum.
     * @since 1.0
     */
    @Test
    public void testChessIcon() {
        //Test private constructors for each chess icon
        ChessIcon whiteKnight = ChessIcon.WHITE_KNIGHT;
        ChessIcon whiteRook = ChessIcon.WHITE_ROOK;
        ChessIcon whiteQueen = ChessIcon.WHITE_QUEEN;
        ChessIcon whiteKing = ChessIcon.WHITE_KING;
        ChessIcon whitePawn = ChessIcon.WHITE_PAWN;
        ChessIcon blackKnight = ChessIcon.BLACK_KNIGHT;
        ChessIcon blackRook = ChessIcon.BLACK_ROOK;
        ChessIcon blackQueen = ChessIcon.BLACK_QUEEN;
        ChessIcon blackKing = ChessIcon.BLACK_KING;
        ChessIcon blackPawn = ChessIcon.BLACK_PAWN;

        //Tests getIconHeight() which should be 60
        assertEquals(whiteKnight.getIconHeight(), 60);
        //Tests getIconWidth() which should be 60
        assertEquals(whiteKnight.getIconWidth(), 60);
    }

    /**
     * Tests the setMoves(), getMoves(), getSide(), getIcon(), setLocation(), getChessBoard(), getRow(), and getColumn() method in the ChessPiece abstract class.
     * Tests the ChessPiece() and KnightPiece() constructors.
     * @since 1.0
     */
    @Test
    public void testChessPiece() {
        KnightPiece knight = new KnightPiece(ChessGame.Side.SOUTH, southBoard, 0, 0);
        //Tests number of moves
        assertEquals(knight.getMoves(), 0);       
        //Tests chess board
        assertEquals(knight.getChessBoard(), southBoard);        
        //Tests chess side
        assertEquals(knight.getSide(), ChessGame.Side.SOUTH);
        //Tests row
        assertEquals(knight.getRow(), 0);
        //Tests column
        assertEquals(knight.getColumn(), 0);
        //Tests label
        assertEquals(knight.getLabel(), "N");
        //Tests icon
        assertEquals(knight.getIcon(), ChessIcon.WHITE_KNIGHT);
        knight.setLocation(7, 7);        
        //Tests row
        assertEquals(knight.getRow(), 7);
        //Tests column
        assertEquals(knight.getColumn(), 7);
        reset();
    }

    /**
     * Tests the moveDone() method in the ChessPiece abstract class.
     * @since 1.0
     */
    @Test
    public void testChessPieceMoveDone() {
        KnightPiece knight = new KnightPiece(ChessGame.Side.SOUTH, southBoard, 0, 0);
        knight.moveDone();
        //Test if moves has been incremented
        assertEquals(1, knight.getMoves());
        reset();
    }

    /**
     * Tests the equals() method in the ChessPiece abstract class.
     * @since 1.0
     */
    @Test
    public void testChessPieceEquals() {
        KnightPiece knight1 = new KnightPiece(ChessGame.Side.SOUTH, southBoard, 0, 0);
        BishopPiece bishop = new BishopPiece(ChessGame.Side.SOUTH, southBoard, 0, 0);
        //Tests if it can tell difference between chess pieces
        assertFalse(knight1.equals(bishop));
        KnightPiece knight2 = new KnightPiece(ChessGame.Side.SOUTH, westBoard, 0, 0);
        //Tests the same piece
        assertTrue(knight1.equals(knight2));
        knight1.moveDone();
        //Tests the same piece even if number of moves changed, as long as that has no effect on the moves the piece can make
        assertTrue(knight1.equals(knight2));
        knight2.setLocation(0, 1);
        //Tests different columns
        assertFalse(knight1.equals(knight2));
        knight2.setLocation(1, 0);
        //Tests different rows
        assertFalse(knight1.equals(knight2));
        knight2.setLocation(0, 0);
        westBoard.addPiece(new KnightPiece(ChessGame.Side.NORTH, westBoard, 1, 2), 1, 2);
        //Tests the same moves
        assertTrue(knight1.equals(knight2));
        westBoard.addPiece(new KnightPiece(ChessGame.Side.SOUTH, westBoard, 1, 2), 1, 2);
        //Test different moves
        assertFalse(knight1.equals(knight2));
        KnightPiece knight3 = new KnightPiece(ChessGame.Side.NORTH, southBoard, 0, 0);
        //Test different sides
        assertFalse(knight1.equals(knight3));
        reset();
    }

    /**
     * Tests the isValidNonCaptureMove() and isValidCaptureMove() in the NormalMove interface.
     * @since 1.0
     */
    @Test
    public void testNormalMove() {
        KnightPiece knight = new KnightPiece(ChessGame.Side.SOUTH, southBoard, 3, 3);
        southBoard.addPiece(knight, 3, 3);
        southBoard.addPiece(new KnightPiece(ChessGame.Side.NORTH, southBoard, 1, 1), 1, 1);
        southBoard.addPiece(new QueenPiece(ChessGame.Side.NORTH, southBoard, 5, 2), 5, 2);
        southBoard.addPiece(new QueenPiece(ChessGame.Side.NORTH, southBoard, 6, 6), 6, 6);
        southBoard.addPiece(new BishopPiece(ChessGame.Side.SOUTH, southBoard, 7, 0), 7, 0);
        southBoard.addPiece(new RookPiece(ChessGame.Side.SOUTH, southBoard, 4, 5), 4, 5);

        //Tests for each square and outside 1 square for both capture and non capture
        for (int i = -1; i <= southBoard.numRows(); i++) {
            for (int j = -1; j <= southBoard.numColumns(); j++) {
                boolean capture = knight.isValidCaptureMove(i, j, knight);
                boolean nonCapture = knight.isValidNonCaptureMove(i, j, knight);
                if (i < 0 || i > 7 || j < 0 || j > 7) {
                    assertFalse(capture);
                    assertFalse(nonCapture);
                } else if ((i == 1 && j == 1) || (i == 5 && j == 2) || (i == 6 && j == 6)) {
                    assertFalse(nonCapture);
                    assertTrue(capture);
                } else if ((i == 3 && j == 3) || (i == 7 && j == 0) || (i == 4 && j == 5)) {
                    assertFalse(capture);
                    assertFalse(nonCapture);
                } else {
                    assertFalse(capture);
                    assertTrue(nonCapture);
                }
            }
        }
        reset();
    }

    /**
     * Tests the isValidLCaptureMove() and isValidNonCaptureMove() methods in the LMove interface.
     * @since 1.0
     */
    @Test
    public void testLMove() {
        KnightPiece knight = new KnightPiece(ChessGame.Side.SOUTH, southBoard, 3, 3);
        southBoard.addPiece(knight, 3, 3);
        southBoard.addPiece(new KnightPiece(ChessGame.Side.NORTH, southBoard, 1, 1), 1, 1);
        southBoard.addPiece(new QueenPiece(ChessGame.Side.NORTH, southBoard, 5, 2), 5, 2);
        southBoard.addPiece(new QueenPiece(ChessGame.Side.NORTH, southBoard, 6, 6), 6, 6);
        southBoard.addPiece(new BishopPiece(ChessGame.Side.SOUTH, southBoard, 7, 0), 7, 0);
        southBoard.addPiece(new RookPiece(ChessGame.Side.SOUTH, southBoard, 4, 5), 4, 5);

        //Tests for each square and outside 1 square for both capture and non capture for 1st position
        for (int i = -1; i <= southBoard.numRows(); i++) {
            for (int j = -1; j <= southBoard.numColumns(); j++) {
                boolean capture = knight.isValidLCaptureMove(i, j, knight);
                boolean nonCapture = knight.isValidLNonCaptureMove(i, j, knight);
                if (i < 0 || i > 7 || j < 0 || j > 7) {
                    assertFalse(capture);
                    assertFalse(nonCapture);
                } else if (i == 5 && j == 2) {
                    assertFalse(nonCapture);
                    assertTrue(capture);
                } else if ((i == 1 && j == 2) || (i == 1 && j == 4) || (i == 2 && j == 1) || (i == 2 && j == 1) || (i == 4 && j == 1) || (i == 5 && j == 4) || (i == 2 && j == 5)) {
                    assertFalse(capture);
                    assertTrue(nonCapture);
                } else {
                    assertFalse(capture);
                    assertFalse(nonCapture);
                }
            }
        }
        reset();

        knight = new KnightPiece(ChessGame.Side.SOUTH, southBoard, 6, 1);
        southBoard.addPiece(knight, 6, 1);
        southBoard.addPiece(new KnightPiece(ChessGame.Side.NORTH, southBoard, 5, 3), 5, 3);
        southBoard.addPiece(new RookPiece(ChessGame.Side.SOUTH, southBoard, 7, 3), 7, 3);

        //Tests for each square and outside 1 square for both capture and non capture for 2nd position
        for (int i = -1; i <= southBoard.numRows(); i++) {
            for (int j = -1; j <= southBoard.numColumns(); j++) {
                boolean capture = knight.isValidLCaptureMove(i, j, knight);
                boolean nonCapture = knight.isValidLNonCaptureMove(i, j, knight);
                if (i < 0 || i > 7 || j < 0 || j > 7) {
                    assertFalse(capture);
                    assertFalse(nonCapture);
                } else if (i == 5 && j == 3) {
                    assertFalse(nonCapture);
                    assertTrue(capture);
                } else if ((i == 4 && j == 2) || (i == 4 && j == 0)) {
                    assertFalse(capture);
                    assertTrue(nonCapture);
                } else {
                    assertFalse(capture);
                    assertFalse(nonCapture);
                }
            }
        }
        reset();
    }

    /**
     * Tests the isLegalCaptureMove() and isLegalNonCaptureMove() in the KnightPiece class through the isLegalMove() that is provided in the ChessPiece class.
     * @since 1.0
     */
    @Test
    public void testKnightPieceIsLegalMove() {
        KnightPiece knight = new KnightPiece(ChessGame.Side.SOUTH, southBoard, 3, 3);
        southBoard.addPiece(knight, 3, 3);
        southBoard.addPiece(new KnightPiece(ChessGame.Side.NORTH, southBoard, 1, 1), 1, 1);
        southBoard.addPiece(new QueenPiece(ChessGame.Side.NORTH, southBoard, 5, 2), 5, 2);
        southBoard.addPiece(new QueenPiece(ChessGame.Side.NORTH, southBoard, 6, 6), 6, 6);
        southBoard.addPiece(new BishopPiece(ChessGame.Side.SOUTH, southBoard, 7, 0), 7, 0);
        southBoard.addPiece(new RookPiece(ChessGame.Side.SOUTH, southBoard, 4, 5), 4, 5);

        //Tests for each square and outside 1 square for both capture and non capture for 1st position
        for (int i = -1; i <= southBoard.numRows(); i++) {
            for (int j = -1; j <= southBoard.numColumns(); j++) {
                boolean move = knight.isLegalMove(i, j);
                if (i < 0 || i > 7 || j < 0 || j > 7) {
                    assertFalse(move);
                } else if ((i == 5 && j == 2) || (i == 1 && j == 2) || (i == 1 && j == 4) || (i == 2 && j == 1) || (i == 2 && j == 1) || (i == 4 && j == 1) || (i == 5 && j == 4) || (i == 2 && j == 5)) {
                    assertTrue(move);
                } else {
                    assertFalse(move);
                }
            }
        }
        reset();

        knight = new KnightPiece(ChessGame.Side.SOUTH, southBoard, 6, 1);
        southBoard.addPiece(knight, 6, 1);
        southBoard.addPiece(new KnightPiece(ChessGame.Side.NORTH, southBoard, 5, 3), 5, 3);
        southBoard.addPiece(new RookPiece(ChessGame.Side.SOUTH, southBoard, 7, 3), 7, 3);

        //Tests for each square and outside 1 square for both capture and non capture for 2nd position
        for (int i = -1; i <= southBoard.numRows(); i++) {
            for (int j = -1; j <= southBoard.numColumns(); j++) {
                boolean move = knight.isLegalMove(i, j);
                if (i < 0 || i > 7 || j < 0 || j > 7) {
                    assertFalse(move);
                } else if ((i == 5 && j == 3) || (i == 4 && j == 2) || (i == 4 && j == 0)) {
                    assertTrue(move);
                } else {
                    assertFalse(move);
                }
            }
        }
        reset();
    }

    /**
     * Tests the checkEmptyDiagonalMove() method that is provided in the DiagonalMove interface.
     * @since 1.0
     */
    @Test
    public void testCheckEmptyDiagonalMove() {
        BishopPiece bishop = new BishopPiece(ChessGame.Side.SOUTH, southBoard, 4, 3);
        southBoard.addPiece(bishop, 4, 3);
        southBoard.addPiece(new QueenPiece(ChessGame.Side.NORTH, southBoard, 6, 1), 6, 1);
        southBoard.addPiece(new RookPiece(ChessGame.Side.SOUTH, southBoard, 7, 6), 7, 6);

        int[] rows, columns;
        rows = new int[] {1, 2, 3, 5, 6, 7, 0, 1, 2, 3, 5, 6, 7};
        columns = new int[] {0, 1, 2, 2, 1, 0, 7, 6, 5, 4, 4, 5, 6};

        //Tests for the selected squares for position 1
        for (int i = 0; i < rows.length; i++) {
            boolean move = bishop.checkEmptyDiagonalMove(rows[i], columns[i], bishop);
            if ((rows[i] == 0 && columns[i] == 7) || (rows[i] == 1 && columns[i] == 0) || (rows[i] == 1 && columns[i] == 6) || (rows[i] == 2 && columns[i] == 1) || (rows[i] == 3 && columns[i] == 2) || (rows[i] == 5 && columns[i] == 4) || (rows[i] == 6 && columns[i] == 5) || (rows[i] == 5 && columns[i] == 2) || (rows[i] == 5 && columns[i] == 4) || (rows[i] == 3 && columns[i] == 4) || (rows[i] == 2 && columns[i] == 5)) {
                assertTrue(move);
            } else {
                assertFalse(move);
            }
        }
        reset();
        
        bishop = new BishopPiece(ChessGame.Side.SOUTH, southBoard, 5, 7);
        southBoard.addPiece(bishop, 5, 7);
        southBoard.addPiece(new RookPiece(ChessGame.Side.SOUTH, southBoard, 2, 4), 2, 4);
        
        rows = new int[] {0, 1, 2, 3, 4, 6, 7};
        columns = new int[] {2, 3, 4, 5, 6, 6, 5};
        
        //Tests for the selected squares for position 1
        for (int i = 0; i < rows.length; i++) {
            boolean move = bishop.checkEmptyDiagonalMove(rows[i], columns[i], bishop);
            if ((rows[i] == 3 && columns[i] == 5) || (rows[i] == 4 && columns[i] == 6) || (rows[i] == 6 && columns[i] == 6) || (rows[i] == 7 && columns[i] == 5)) {
                assertTrue(move);
            } else {

                assertFalse(move);
            }
        }
        reset();
    }
    
    /**
     * Tests the isValidLCaptureMove() and isValidNonCaptureMove() methods in the DiagonalMove interface.
     * @since 1.0
     */
    @Test
    public void testDiagonalMove() {
        BishopPiece bishop = new BishopPiece(ChessGame.Side.SOUTH, southBoard, 4, 3);
        southBoard.addPiece(bishop, 4, 3);
        southBoard.addPiece(new QueenPiece(ChessGame.Side.NORTH, southBoard, 6, 1), 6, 1);
        southBoard.addPiece(new RookPiece(ChessGame.Side.SOUTH, southBoard, 7, 6), 7, 6);
        
        //Tests for each square and outside 1 square for both capture and non capture for 1st position
        for (int i = -1; i <= southBoard.numRows(); i++) {
            for (int j = -1; j <= southBoard.numColumns(); j++) {
                boolean capture = bishop.isValidDiagonalCaptureMove(i, j, bishop);
                boolean nonCapture = bishop.isValidDiagonalNonCaptureMove(i, j, bishop);
                if (i < 0 || i > 7 || j < 0 || j > 7) {
                    assertFalse(capture);
                    assertFalse(nonCapture);
                } else if ((i == 0 && j == 7) || (i == 1 && j == 0) || (i == 1 && j == 6) || (i == 2 && j == 1) || (i == 3 && j == 2) || (i == 5 && j == 4) || (i == 6 && j == 5) || (i == 5 && j == 2) || (i == 5 && j == 4) || (i == 3 && j == 4) || (i == 2 && j == 5)) {
                    assertTrue(nonCapture);
                    assertFalse(capture);
                } else if (i == 6 && j == 1) {
                    assertFalse(nonCapture);
                    assertTrue(capture);
                } else {
                    assertFalse(capture);
                    assertFalse(nonCapture);
                }
            }
        }
        reset();
        
        bishop = new BishopPiece(ChessGame.Side.SOUTH, southBoard, 5, 7);
        southBoard.addPiece(bishop, 5, 7);
        southBoard.addPiece(new RookPiece(ChessGame.Side.NORTH, southBoard, 2, 4), 2, 4);
        
        //Tests for each square and outside 1 square for both capture and non capture for 2nd position
        for (int i = -1; i <= southBoard.numRows(); i++) {
            for (int j = -1; j <= southBoard.numColumns(); j++) {
                boolean capture = bishop.isValidDiagonalCaptureMove(i, j, bishop);
                boolean nonCapture = bishop.isValidDiagonalNonCaptureMove(i, j, bishop);
                if (i < 0 || i > 7 || j < 0 || j > 7) {
                    assertFalse(capture);
                    assertFalse(nonCapture);
                } else if ((i == 3 && j == 5) || (i == 4 && j == 6) || (i == 6 && j == 6) || (i == 7 && j == 5)) {
                    assertTrue(nonCapture);
                    assertFalse(capture);
                } else if (i == 2 && j == 4) {
                    assertFalse(nonCapture);
                    assertTrue(capture);
                } else {
                    assertFalse(capture);
                    assertFalse(nonCapture);
                }
            }
        }
        reset();
    }
    
    /**
     * Tests the isLegalCaptureMove() and isLegalNonCaptureMove() in the BishopPiece class through the isLegalMove() that is provided in the ChessPiece class.
     * @since 1.0
     */
    @Test
    public void testBishopPieceIsLegalMove() {
        BishopPiece bishop = new BishopPiece(ChessGame.Side.SOUTH, southBoard, 4, 3);
        southBoard.addPiece(bishop, 4, 3);
        southBoard.addPiece(new QueenPiece(ChessGame.Side.NORTH, southBoard, 6, 1), 6, 1);
        southBoard.addPiece(new RookPiece(ChessGame.Side.SOUTH, southBoard, 7, 6), 7, 6);
        
        //Tests for each square and outside 1 square for both capture and non capture for 1st position
        for (int i = -1; i <= southBoard.numRows(); i++) {
            for (int j = -1; j <= southBoard.numColumns(); j++) {
                boolean move = bishop.isLegalMove(i, j);
                if (i < 0 || i > 7 || j < 0 || j > 7) {
                    assertFalse(move);
                } else if ((i == 6 && j == 1) || (i == 0 && j == 7) || (i == 1 && j == 0) || (i == 1 && j == 6) || (i == 2 && j == 1) || (i == 3 && j == 2) || (i == 5 && j == 4) || (i == 6 && j == 5) || (i == 5 && j == 2) || (i == 5 && j == 4) || (i == 3 && j == 4) || (i == 2 && j == 5)) {
                    assertTrue(move);
                } else {
                    assertFalse(move);
                }
            }
        }
        reset();
        
        bishop = new BishopPiece(ChessGame.Side.SOUTH, southBoard, 5, 7);
        southBoard.addPiece(bishop, 5, 7);
        southBoard.addPiece(new RookPiece(ChessGame.Side.NORTH, southBoard, 2, 4), 2, 4);
        
        //Tests for each square and outside 1 square for both capture and non capture for 2nd position
        for (int i = -1; i <= southBoard.numRows(); i++) {
            for (int j = -1; j <= southBoard.numColumns(); j++) {
                boolean move = bishop.isLegalMove(i, j);
                if (i < 0 || i > 7 || j < 0 || j > 7) {
                    assertFalse(move);
                } else if ((i == 2 && j == 4) || (i == 3 && j == 5) || (i == 4 && j == 6) || (i == 6 && j == 6) || (i == 7 && j == 5)) {
                    assertTrue(move);
                } else {
                    assertFalse(move);
                }
            }
        }
        reset();
    }
    
    /**
     * Tests the checkEmptyStraightMove() method that is provided in the StraightMove interface.
     * @since 1.0
     */
    @Test
    public void testCheckEmptyStraightMove() {
        RookPiece rook = new RookPiece(ChessGame.Side.SOUTH, southBoard, 3, 4);
        southBoard.addPiece(rook, 3, 4);
        southBoard.addPiece(new QueenPiece(ChessGame.Side.NORTH, southBoard, 6, 1), 6, 1);
        southBoard.addPiece(new BishopPiece(ChessGame.Side.NORTH, southBoard, 3, 6), 3, 6);
        southBoard.addPiece(new KnightPiece(ChessGame.Side.SOUTH, southBoard, 3, 2), 3, 2);
        
        //Tests for the vertically selected squares for position 1
        for (int i = 0; i < 8; i++) {
            boolean move = false;
            //Don't check actual piece
            if (i != 3) {
                move = rook.checkEmptyStraightMove(i, 4, rook);
            }
            

            if (i != 3)
                assertTrue(move);
            else
                assertFalse(move);
        }
        
        //Tests for the horizontally selected squares for position 1
        for (int i = 0; i < 8; i++) {
            boolean move = false;
            //Don't check actual piece
            if (i != 4) {
                move = rook.checkEmptyStraightMove(3, i, rook);
            }
            
            if (i == 3 || i == 5)
                assertTrue(move);
            else
                assertFalse(move);
        }
        reset();
        
        rook = new RookPiece(ChessGame.Side.SOUTH, southBoard, 6, 7);
        southBoard.addPiece(rook, 6, 7);
        southBoard.addPiece(new QueenPiece(ChessGame.Side.NORTH, southBoard, 6, 5), 6, 5);
        southBoard.addPiece(new KnightPiece(ChessGame.Side.SOUTH, southBoard, 2, 7), 2, 7);
        
        //Tests for the vertically selected squares for position 2
        for (int i = 0; i < 8; i++) {
            boolean move = false;
            //Don't check actual piece
            if (i != 6) {
                move = rook.checkEmptyStraightMove(i, 7, rook);
            }
            
            if (i == 3 || i == 4 || i == 5 || i == 7)
                assertTrue(move);
            else
                assertFalse(move);
        }
        
        //Tests for the horizontally selected squares for position 2
        for (int i = 0; i < 8; i++) {
            boolean move = false;
            //Don't check actual piece
            if (i != 7) {
                move = rook.checkEmptyStraightMove(6, i, rook);
            }
            
            if (i == 6)
                assertTrue(move);
            else
                assertFalse(move);
        }
        reset();
    }
    
     /**
     * Tests the isValidStraightCaptureMove() and isValidStraightCaptureMove() methods in the StraightMove interface.
     * @since 1.0
     */
    @Test
    public void testStraightMove() {
        RookPiece rook = new RookPiece(ChessGame.Side.SOUTH, southBoard, 3, 4);
        southBoard.addPiece(rook, 3, 4);
        southBoard.addPiece(new QueenPiece(ChessGame.Side.NORTH, southBoard, 6, 1), 6, 1);
        southBoard.addPiece(new BishopPiece(ChessGame.Side.NORTH, southBoard, 3, 6), 3, 6);
        southBoard.addPiece(new KnightPiece(ChessGame.Side.SOUTH, southBoard, 3, 2), 3, 2);
        
        //Tests for each square and outside 1 square for both capture and non capture for 1st position
        for (int i = -1; i <= southBoard.numRows(); i++) {
            for (int j = -1; j <= southBoard.numColumns(); j++) {
                boolean capture = rook.isValidStraightCaptureMove(i, j, rook);
                boolean nonCapture = rook.isValidStraightNonCaptureMove(i, j, rook);
                if (i < 0 || i > 7 || j < 0 || j > 7) {
                    assertFalse(capture);
                    assertFalse(nonCapture);
                } else if ((j == 4 && i != 3) || (i == 3 && (j == 3 || j == 5))) {
                    assertTrue(nonCapture);
                    assertFalse(capture);
                } else if (i == 3 && j == 6) {
                    assertFalse(nonCapture);
                    assertTrue(capture);
                } else {
                    assertFalse(capture);
                    assertFalse(nonCapture);
                }
            }
        }
        reset();
        
        rook = new RookPiece(ChessGame.Side.SOUTH, southBoard, 6, 7);
        southBoard.addPiece(rook, 6, 7);
        southBoard.addPiece(new QueenPiece(ChessGame.Side.NORTH, southBoard, 6, 5), 6, 5);
        southBoard.addPiece(new KnightPiece(ChessGame.Side.SOUTH, southBoard, 2, 7), 2, 7);
        
        //Tests for each square and outside 1 square for both capture and non capture for 2nd position
        for (int i = -1; i <= southBoard.numRows(); i++) {
            for (int j = -1; j <= southBoard.numColumns(); j++) {
                boolean capture = rook.isValidStraightCaptureMove(i, j, rook);
                boolean nonCapture = rook.isValidStraightNonCaptureMove(i, j, rook);
                if (i < 0 || i > 7 || j < 0 || j > 7) {
                    assertFalse(capture);
                    assertFalse(nonCapture);
                } else if ((j == 7 && (i == 4 || i == 3 || i == 5 || i == 7)) || (i == 6 && j == 6)) {
                    assertTrue(nonCapture);
                    assertFalse(capture);
                } else if (i == 6 && j == 5) {
                    assertFalse(nonCapture);
                    assertTrue(capture);
                } else {
                    assertFalse(capture);
                    assertFalse(nonCapture);
                }
            }
        }
        reset();
    }
    
    /**
     * Tests the isLegalCaptureMove() and isLegalNonCaptureMove() in the RookPiece class through the isLegalMove() that is provided in the ChessPiece class.
     * @since 1.0
     */
    @Test
    public void testRookPieceIsLegalMove() {
        RookPiece rook = new RookPiece(ChessGame.Side.SOUTH, southBoard, 3, 4);
        southBoard.addPiece(rook, 3, 4);
        southBoard.addPiece(new QueenPiece(ChessGame.Side.NORTH, southBoard, 6, 1), 6, 1);
        southBoard.addPiece(new BishopPiece(ChessGame.Side.NORTH, southBoard, 3, 6), 3, 6);
        southBoard.addPiece(new KnightPiece(ChessGame.Side.SOUTH, southBoard, 3, 2), 3, 2);
        
        //Tests for each square and outside 1 square for both capture and non capture for 1st position
        for (int i = -1; i <= southBoard.numRows(); i++) {
            for (int j = -1; j <= southBoard.numColumns(); j++) {
                boolean move = rook.isLegalMove(i, j);
                if (i < 0 || i > 7 || j < 0 || j > 7) {
                    assertFalse(move);
                } else if ((j == 4 && i != 3) || (i == 3 && (j == 3 || j == 5 || j == 6))) {
                    assertTrue(move);
                } else {
                    assertFalse(move);
                }
            }
        }
        reset();
        
        rook = new RookPiece(ChessGame.Side.SOUTH, southBoard, 6, 7);
        southBoard.addPiece(rook, 6, 7);
        southBoard.addPiece(new QueenPiece(ChessGame.Side.NORTH, southBoard, 6, 5), 6, 5);
        southBoard.addPiece(new KnightPiece(ChessGame.Side.SOUTH, southBoard, 2, 7), 2, 7);
        
        //Tests for each square and outside 1 square for both capture and non capture for 2nd position
        for (int i = -1; i <= southBoard.numRows(); i++) {
            for (int j = -1; j <= southBoard.numColumns(); j++) {
                boolean move = rook.isLegalMove(i, j);
                if (i < 0 || i > 7 || j < 0 || j > 7) {
                    assertFalse(move);
                } else if ((j == 7 && (i == 4 || i == 3 || i == 5 || i == 7)) || (i == 6 && (j == 6 || j == 5))) {
                    assertTrue(move);
                } else {
                    assertFalse(move);
                }
            }
        }
        reset();
    }
    
    /**
     * Tests the isLegalCaptureMove() and isLegalNonCaptureMove() in the QueenPiece class through the isLegalMove() that is provided in the ChessPiece class.
     * @since 1.0
     */
    @Test
    public void testQueenPieceIsLegalMove() {
        QueenPiece queen = new QueenPiece(ChessGame.Side.SOUTH, southBoard, 4, 3);
        southBoard.addPiece(queen, 4, 3);
        southBoard.addPiece(new RookPiece(ChessGame.Side.NORTH, southBoard, 5, 4), 5, 4);
        southBoard.addPiece(new RookPiece(ChessGame.Side.SOUTH, southBoard, 4, 7), 4, 7);
        southBoard.addPiece(new BishopPiece(ChessGame.Side.NORTH, southBoard, 1, 6), 1, 6);
        southBoard.addPiece(new KnightPiece(ChessGame.Side.SOUTH, southBoard, 2, 1), 2, 1);
        southBoard.addPiece(new KnightPiece(ChessGame.Side.NORTH, southBoard, 7, 3), 7, 3);
        
        //Tests for each square and outside 1 square for both capture and non capture for 1st position
        for (int i = -1; i <= southBoard.numRows(); i++) {
            for (int j = -1; j <= southBoard.numColumns(); j++) {
                boolean capture = queen.isLegalCaptureMove(i, j);
                boolean nonCapture = queen.isLegalNonCaptureMove(i, j);
                if (i < 0 || i > 7 || j < 0 || j > 7 || (i == 4 && j == 3)) {
                    assertFalse(capture);
                    assertFalse(nonCapture);
                } else if ((j == 3 && i != 4 && i != 7) || (i == 4 && j != 3 && j != 7)|| (i == 7 && j == 0) || (i == 6 && j == 1) || (i == 5 && j == 2) || (i == 3 && j == 4) || (i == 2 && j == 5) || (i == 3 && j == 2)) {
                    assertTrue(nonCapture);
                    assertFalse(capture);
                } else if ((i == 7 && j == 3) || (i == 5 && j == 4) || (i == 1 && j == 6)) {
                    assertFalse(nonCapture);
                    assertTrue(capture);
                } else {
                    assertFalse(capture);
                    assertFalse(nonCapture);
                }
            }
        }
        reset();
        
        queen = new QueenPiece(ChessGame.Side.SOUTH, southBoard, 6, 7);
        southBoard.addPiece(queen, 6, 7);
        southBoard.addPiece(new RookPiece(ChessGame.Side.NORTH, southBoard, 4, 7), 4, 7);
        southBoard.addPiece(new KnightPiece(ChessGame.Side.SOUTH, southBoard, 3, 4), 3, 4);
        
        //Tests for each square and outside 1 square for both capture and non capture for 2nd position
        for (int i = -1; i <= southBoard.numRows(); i++) {
            for (int j = -1; j <= southBoard.numColumns(); j++) {
                boolean capture = queen.isLegalCaptureMove(i, j);
                boolean nonCapture = queen.isLegalNonCaptureMove(i, j);
                if (i < 0 || i > 7 || j < 0 || j > 7 || (i == 6 && j == 7)) {
                    assertFalse(capture);
                    assertFalse(nonCapture);
                } else if ((i == 6 && (j != 7)) || (i == 4 && j == 5) || (i == 5 && j == 6) || (i == 7 && j == 6) || (i == 7 && j == 7) || (i == 5 && j == 7)) {
                    assertTrue(nonCapture);
                    assertFalse(capture);
                } else if (i == 4 && j == 7) {
                    assertFalse(nonCapture);
                    assertTrue(capture);
                } else {
                    assertFalse(capture);
                    assertFalse(nonCapture);
                }
            }
        }
        reset();
    }
    
    /**
     * Tests the isValidCastlingMove() method in the CastlingMove interface.
     * @since 1.0
     */
    @Test
    public void testCastlingMove() {
        KingPiece whiteKing = new KingPiece(ChessGame.Side.SOUTH, southBoard, 7, 4);
        KingPiece blackKing = new KingPiece(ChessGame.Side.NORTH, southBoard, 0, 4);
        RookPiece whiteRook = new RookPiece(ChessGame.Side.SOUTH, southBoard, 7, 7);
        RookPiece blackRook = new RookPiece(ChessGame.Side.NORTH, southBoard, 0, 0);
        southBoard.addPiece(whiteKing, 7, 4);
        southBoard.addPiece(blackKing, 0, 4);
        southBoard.addPiece(new RookPiece(ChessGame.Side.SOUTH, southBoard, 7, 0), 7, 0);   
        southBoard.addPiece(whiteRook, 7, 7);
        southBoard.addPiece(blackRook, 0, 0);   
        southBoard.addPiece(new RookPiece(ChessGame.Side.NORTH, southBoard, 0, 7), 0, 7);
        
        //Tests for castling for south side
        for (int i = -1; i <= southBoard.numRows(); i++) {
            for (int j = -1; j <= southBoard.numColumns(); j++) {
                boolean move = whiteKing.isValidCastlingMove(i, j, whiteKing);
                if (i < 0 || i > 7 || j < 0 || j > 7) {
                    assertFalse(move);
                } else if (i == 7 && (j == 2 || j == 6)) {
                    assertTrue(move);
                } else {
                    assertFalse(move);
                }
            }
        }
        
        //Tests for castling for north side
        for (int i = -1; i <= southBoard.numRows(); i++) {
            for (int j = -1; j <= southBoard.numColumns(); j++) {
                boolean move = blackKing.isValidCastlingMove(i, j, blackKing);
                if (i < 0 || i > 7 || j < 0 || j > 7) {
                    assertFalse(move);
                } else if (i == 0 && (j == 2 || j == 6)) {
                    assertTrue(move);
                } else {
                    assertFalse(move);
                }
            }
        }
        
        whiteRook.moveDone();
        blackRook.moveDone();
        
        //Tests for castling for south side
        for (int i = -1; i <= southBoard.numRows(); i++) {
            for (int j = -1; j <= southBoard.numColumns(); j++) {
                boolean move = whiteKing.isValidCastlingMove(i, j, whiteKing);
                if (i < 0 || i > 7 || j < 0 || j > 7) {
                    assertFalse(move);
                } else if (i == 7 && j == 2) {
                    assertTrue(move);
                } else {
                    assertFalse(move);
                }
            }
        }
        
        //Tests for castling for north side
        for (int i = -1; i <= southBoard.numRows(); i++) {
            for (int j = -1; j <= southBoard.numColumns(); j++) {
                boolean move = blackKing.isValidCastlingMove(i, j, blackKing);
                if (i < 0 || i > 7 || j < 0 || j > 7) {
                    assertFalse(move);
                } else if (i == 0 && j == 6) {
                    assertTrue(move);
                } else {
                    assertFalse(move);
                }
            }
        }        
        
        //Reset rooks so they can castle again
        whiteRook = new RookPiece(ChessGame.Side.SOUTH, southBoard, 7, 7);
        blackRook = new RookPiece(ChessGame.Side.NORTH, southBoard, 0, 0);
        southBoard.addPiece(whiteRook, 7, 7);
        southBoard.addPiece(blackRook, 0, 0);
        whiteKing.moveDone();
        blackKing.moveDone();
        
        //Tests for castling for south side
         for (int i = -1; i <= southBoard.numRows(); i++) {
            for (int j = -1; j <= southBoard.numColumns(); j++) {
                boolean move = whiteKing.isValidCastlingMove(i, j, whiteKing);
                assertFalse(move);
            }
        }
        
        //Tests for castling for north side
        for (int i = -1; i <= southBoard.numRows(); i++) {
            for (int j = -1; j <= southBoard.numColumns(); j++) {
                boolean move = blackKing.isValidCastlingMove(i, j, blackKing);
                assertFalse(move);
            }
        }        
        
        //Reset kings so they can castle again
        whiteKing = new KingPiece(ChessGame.Side.SOUTH, southBoard, 7, 4);
        blackKing = new KingPiece(ChessGame.Side.NORTH, southBoard, 0, 4);
        southBoard.addPiece(whiteKing, 7, 4);
        southBoard.addPiece(blackKing, 0, 4);
        southBoard.addPiece(new QueenPiece(ChessGame.Side.SOUTH, southBoard, 7, 5), 7, 5);
        southBoard.addPiece(new QueenPiece(ChessGame.Side.NORTH, southBoard, 0, 3), 0, 3);
        
        //Tests for castling for south side
         for (int i = -1; i <= southBoard.numRows(); i++) {
            for (int j = -1; j <= southBoard.numColumns(); j++) {
                boolean move = whiteKing.isValidCastlingMove(i, j, whiteKing);
                assertFalse(move);
            }
        }
        
        //Tests for castling for north side
        for (int i = -1; i <= southBoard.numRows(); i++) {
            for (int j = -1; j <= southBoard.numColumns(); j++) {
                boolean move = blackKing.isValidCastlingMove(i, j, blackKing);
                assertFalse(move);
            }
        }        
        
        reset();
        
        whiteKing = new KingPiece(ChessGame.Side.WEST, westBoard, 4, 0);
        blackKing = new KingPiece(ChessGame.Side.EAST, westBoard, 4, 7);
        whiteRook = new RookPiece(ChessGame.Side.WEST, westBoard, 7, 0);
        blackRook = new RookPiece(ChessGame.Side.EAST, westBoard, 0, 7);
        westBoard.addPiece(whiteKing, 4, 0);
        westBoard.addPiece(blackKing, 4, 7);
        westBoard.addPiece(new RookPiece(ChessGame.Side.WEST, westBoard, 0, 0), 0, 0);   
        westBoard.addPiece(whiteRook, 7, 0);
        westBoard.addPiece(new RookPiece(ChessGame.Side.EAST, westBoard, 7, 7), 7, 7);   
        westBoard.addPiece(blackRook, 0, 7);
        
        //Tests for castling for west side
        for (int i = -1; i <= southBoard.numRows(); i++) {
            for (int j = -1; j <= southBoard.numColumns(); j++) {
                boolean move = whiteKing.isValidCastlingMove(i, j, whiteKing);
                if (i < 0 || i > 7 || j < 0 || j > 7) {
                    assertFalse(move);
                } else if (j == 0 && (i == 2 || i == 6)) {
                    assertTrue(move);
                } else {
                    assertFalse(move);
                }
            }
        }
        
        //Tests for castling for north side
        for (int i = -1; i <= southBoard.numRows(); i++) {
            for (int j = -1; j <= southBoard.numColumns(); j++) {
                boolean move = blackKing.isValidCastlingMove(i, j, blackKing);
                if (i < 0 || i > 7 || j < 0 || j > 7) {
                    assertFalse(move);
                } else if (j == 7 && (i == 2 || i == 6)) {
                    assertTrue(move);
                } else {
                    assertFalse(move);
                }
            }
        }
        
        whiteRook.moveDone();
        blackRook.moveDone();
        
        //Tests for castling for south side
        for (int i = -1; i <= southBoard.numRows(); i++) {
            for (int j = -1; j <= southBoard.numColumns(); j++) {
                boolean move = whiteKing.isValidCastlingMove(i, j, whiteKing);
                if (i < 0 || i > 7 || j < 0 || j > 7) {
                    assertFalse(move);
                } else if (i == 2 && j == 0) {
                    assertTrue(move);
                } else {
                    assertFalse(move);
                }
            }
        }
        
        //Tests for castling for north side
        for (int i = -1; i <= southBoard.numRows(); i++) {
            for (int j = -1; j <= southBoard.numColumns(); j++) {
                boolean move = blackKing.isValidCastlingMove(i, j, blackKing);
                if (i < 0 || i > 7 || j < 0 || j > 7) {
                    assertFalse(move);
                } else if (i == 6 && j == 7) {
                    assertTrue(move);
                } else {
                    assertFalse(move);
                }
            }
        }
        
        //Reset rooks
        whiteRook = new RookPiece(ChessGame.Side.WEST, westBoard, 7, 0);
        blackRook = new RookPiece(ChessGame.Side.EAST, westBoard, 0, 7);  
        westBoard.addPiece(whiteRook, 7, 0);
        westBoard.addPiece(blackRook, 0, 7);
        whiteKing.moveDone();
        blackKing.moveDone();
        
        //Tests for castling for west side
        for (int i = -1; i <= southBoard.numRows(); i++) {
            for (int j = -1; j <= southBoard.numColumns(); j++) {
                boolean move = whiteKing.isValidCastlingMove(i, j, whiteKing);
                assertFalse(move);
            }
        }
        
        //Tests for castling for north side
        for (int i = -1; i <= southBoard.numRows(); i++) {
            for (int j = -1; j <= southBoard.numColumns(); j++) {
                boolean move = blackKing.isValidCastlingMove(i, j, blackKing);
                assertFalse(move);
            }
        }
        
        //Reset kings
        whiteKing = new KingPiece(ChessGame.Side.WEST, westBoard, 4, 0);
        blackKing = new KingPiece(ChessGame.Side.EAST, westBoard, 4, 7);
        westBoard.addPiece(whiteKing, 4, 0);
        westBoard.addPiece(blackKing, 4, 7);
        westBoard.addPiece(new QueenPiece(ChessGame.Side.WEST, westBoard, 5, 0), 5, 0);
        westBoard.addPiece(new QueenPiece(ChessGame.Side.EAST, westBoard, 3, 7), 3, 7);
        
        //Tests for castling for west side
        for (int i = -1; i <= southBoard.numRows(); i++) {
            for (int j = -1; j <= southBoard.numColumns(); j++) {
                boolean move = whiteKing.isValidCastlingMove(i, j, whiteKing);
                assertFalse(move);
            }
        }
        
        //Tests for castling for north side
        for (int i = -1; i <= southBoard.numRows(); i++) {
            for (int j = -1; j <= southBoard.numColumns(); j++) {
                boolean move = blackKing.isValidCastlingMove(i, j, blackKing);
                assertFalse(move);
            }
        }
        
        reset();
    }
    
    /**
     * Tests the isValidKingCaptureMove() and isValidKingCaptureMove() methods in the KingMove interface.
     * @since 1.0
     */
    @Test
    public void testKingMove() {
        KingPiece king = new KingPiece(ChessGame.Side.SOUTH, southBoard, 3, 4);
        southBoard.addPiece(king, 3, 4);
        southBoard.addPiece(new QueenPiece(ChessGame.Side.NORTH, southBoard, 2, 5), 2, 5);
        southBoard.addPiece(new RookPiece(ChessGame.Side.SOUTH, southBoard, 4, 4), 4, 4);
        
        //Tests for each square and outside 1 square for both capture and non capture for 1st position
        for (int i = -1; i <= southBoard.numRows(); i++) {
            for (int j = -1; j <= southBoard.numColumns(); j++) {
                boolean capture = king.isValidKingCaptureMove(i, j, king);
                boolean nonCapture = king.isValidKingNonCaptureMove(i, j, king);
                if (i < 0 || i > 7 || j < 0 || j > 7) {
                    assertFalse(capture);
                    assertFalse(nonCapture);
                } else if ((j == 3 && i >= 2 && i <= 4) || (i == 2 && j == 4) || (i == 3 && j == 5) || (i == 4 && j == 5)) {
                    assertTrue(nonCapture);
                    assertFalse(capture);
                } else if (i == 2 && j == 5) {
                    assertFalse(nonCapture);
                    assertTrue(capture);
                } else {
                    assertFalse(capture);
                    assertFalse(nonCapture);
                }
            }
        }
        reset();
        
        king = new KingPiece(ChessGame.Side.SOUTH, southBoard, 6, 7);
        southBoard.addPiece(king, 6, 7);
        southBoard.addPiece(new QueenPiece(ChessGame.Side.SOUTH, southBoard, 7, 7), 7, 7);
        southBoard.addPiece(new RookPiece(ChessGame.Side.NORTH, southBoard, 5, 6), 5, 6);
        
        //Tests for each square and outside 1 square for both capture and non capture for 2nd position
        for (int i = -1; i <= southBoard.numRows(); i++) {
            for (int j = -1; j <= southBoard.numColumns(); j++) {
                boolean capture = king.isValidKingCaptureMove(i, j, king);
                boolean nonCapture = king.isValidKingNonCaptureMove(i, j, king);
                if (i < 0 || i > 7 || j < 0 || j > 7) {
                    assertFalse(capture);
                    assertFalse(nonCapture);
                } else if ((i == 5 && j == 7) || (i == 6 && j == 6) || (i == 7 && j == 6)) {
                    assertTrue(nonCapture);
                    assertFalse(capture);
                } else if (i == 5 && j == 6) {
                    assertFalse(nonCapture);
                    assertTrue(capture);
                } else {
                    assertFalse(capture);
                    assertFalse(nonCapture);
                }
            }
        }
        reset();
    }
    
    /**
     * Tests the isInCheck() method in the KingPiece class.
     * @since 1.0
     */
    @Test
    public void testIsInCheck() {
        KingPiece king = new KingPiece(ChessGame.Side.SOUTH, southBoard, 5, 3);
        southBoard.addPiece(king, 5, 3);
        southBoard.addPiece(new QueenPiece(ChessGame.Side.SOUTH, southBoard, 5, 5), 5, 5);
        southBoard.addPiece(new RookPiece(ChessGame.Side.NORTH, southBoard, 3, 3), 3, 3);
        
        //Checks if the king is in check
        assertTrue(king.isInCheck());
        reset();
        
        king = new KingPiece(ChessGame.Side.SOUTH, southBoard, 5, 3);
        southBoard.addPiece(king, 5, 3);
        southBoard.addPiece(new QueenPiece(ChessGame.Side.NORTH, southBoard, 2, 5), 2, 5);
        southBoard.addPiece(new RookPiece(ChessGame.Side.SOUTH, southBoard, 4, 4), 4, 4);
        southBoard.addPiece(new BishopPiece(ChessGame.Side.NORTH, southBoard, 7, 2), 7, 2);
        southBoard.addPiece(new BishopPiece(ChessGame.Side.NORTH, southBoard, 3, 5), 3, 5);
        southBoard.addPiece(new KnightPiece(ChessGame.Side.NORTH, southBoard, 6, 4), 6, 4);
        
        //Checks if the king is in check
        assertFalse(king.isInCheck());
        reset();
        
        king = new KingPiece(ChessGame.Side.SOUTH, southBoard, 5, 4);
        southBoard.addPiece(king, 5, 4);
        southBoard.addPiece(new PawnPiece(ChessGame.Side.NORTH, southBoard, 4, 4), 4, 4);
        
        //Checks if the king is in check
        assertFalse(king.isInCheck());
        reset();
    }
    
    /**
     * Tests the getOpposingKing() method in the KingPiece class.
     * @since 1.0
     */
    @Test
    public void testGetOpposingKing() {
        KingPiece whiteKing = new KingPiece(ChessGame.Side.SOUTH, southBoard, 5, 3);
        KingPiece blackKing = new KingPiece(ChessGame.Side.NORTH, southBoard, 6, 5);
        southBoard.addPiece(whiteKing, 5, 3);
        southBoard.addPiece(blackKing, 6, 5);
        southBoard.addPiece(new QueenPiece(ChessGame.Side.NORTH, southBoard, 2, 5), 2, 5);
        southBoard.addPiece(new RookPiece(ChessGame.Side.SOUTH, southBoard, 4, 4), 4, 4);
        southBoard.addPiece(new BishopPiece(ChessGame.Side.NORTH, southBoard, 7, 2), 7, 2);
        southBoard.addPiece(new BishopPiece(ChessGame.Side.NORTH, southBoard, 3, 5), 3, 5);
        southBoard.addPiece(new KnightPiece(ChessGame.Side.NORTH, southBoard, 6, 4), 6, 4);
        
        //Tests if the kings can find the opposing king
        assertEquals(whiteKing.getOpposingKing(), blackKing);
        assertEquals(blackKing.getOpposingKing(), whiteKing);
        reset();
    }
    
    /**
     * Tests the isLegalCaptureMove() and isLegalNonCaptureMove() in the KingPiece class through the isLegalMove() that is provided in the ChessPiece class.
     * @since 1.0
     */
    @Test
    public void testKingPieceIsLegalMove() {
        KingPiece king = new KingPiece(ChessGame.Side.SOUTH, southBoard, 3, 4);
        southBoard.addPiece(king, 3, 4);
        southBoard.addPiece(new QueenPiece(ChessGame.Side.NORTH, southBoard, 2, 5), 2, 5);
        southBoard.addPiece(new RookPiece(ChessGame.Side.SOUTH, southBoard, 4, 4), 4, 4);
        
        //Tests for each square and outside 1 square for both capture and non capture for 1st position
        for (int i = -1; i <= southBoard.numRows(); i++) {
            for (int j = -1; j <= southBoard.numColumns(); j++) {
                boolean move = king.isLegalMove(i, j);
                if (i < 0 || i > 7 || j < 0 || j > 7) {
                    assertFalse(move);
                } else if ((j == 3 && i >= 2 && i <= 4) || (i == 2 && j == 4) || (i == 3 && j == 5) || (i == 4 && j == 5) || (i == 2 && j == 5)) {
                    assertTrue(move);
                } else {
                    assertFalse(move);
                }
            }
        }
        reset();
        
        king = new KingPiece(ChessGame.Side.SOUTH, southBoard, 6, 7);
        southBoard.addPiece(king, 6, 7);
        southBoard.addPiece(new QueenPiece(ChessGame.Side.SOUTH, southBoard, 7, 7), 7, 7);
        southBoard.addPiece(new RookPiece(ChessGame.Side.NORTH, southBoard, 5, 6), 5, 6);
        
        //Tests for each square and outside 1 square for both capture and non capture for 2nd position
        for (int i = -1; i <= southBoard.numRows(); i++) {
            for (int j = -1; j <= southBoard.numColumns(); j++) {
                boolean move = king.isLegalMove(i, j);
                if (i < 0 || i > 7 || j < 0 || j > 7) {
                    assertFalse(move);
                } else if ((i == 5 && j == 7) || (i == 6 && j == 6) || (i == 7 && j == 6) || (i == 5 && j == 6)) {
                    assertTrue(move);
                } else {
                    assertFalse(move);
                }
            }
        }
        reset();
    }
    
    /**
     * Tests the isValidEnPassantMove() in the EnPassantMove interface.
     * @since 1.0
     */
    @Test
    public void testEnPassantMove() {
        PawnPiece whitePawn1 = new PawnPiece(ChessGame.Side.WEST, westBoard, 6, 4);
        PawnPiece blackPawn1 = new PawnPiece(ChessGame.Side.EAST, westBoard, 3, 3);
        PawnPiece whitePawn2 = new PawnPiece(ChessGame.Side.WEST, westBoard, 4, 3);
        PawnPiece blackPawn2 = new PawnPiece(ChessGame.Side.EAST, westBoard, 7, 4);
        KingPiece whiteKing = new KingPiece(ChessGame.Side.WEST, westBoard, 4, 0);
        KingPiece blackKing = new KingPiece(ChessGame.Side.EAST, westBoard, 4, 7);
        
        westBoard.addPiece(whitePawn1, 6, 4);
        westBoard.addPiece(blackPawn1, 3, 3);
        westBoard.addPiece(whitePawn2, 4, 3);
        westBoard.addPiece(blackPawn2, 7, 4);
        westBoard.addPiece(whiteKing, 4, 0);
        westBoard.addPiece(blackKing, 4, 7);
        whitePawn2.moveDone();
        
        //Tests for en passant move for white pawn
        for (int i = -1; i <= westBoard.numRows(); i++) {
            for (int j = -1; j <= westBoard.numColumns(); j++) {
                boolean move = blackPawn1.isValidEnPassantMove(i, j, blackPawn1);
                if (i < 0 || i > 7 || j < 0 || j > 7) {
                    assertFalse(move);
                } else if (i == 4 && j == 2) {
                    assertTrue(move);
                } else {
                    assertFalse(move);
                }
            }
        }
        
        blackPawn2.moveDone();
        //Tests for en passant move for black pawn
        for (int i = -1; i <= westBoard.numRows(); i++) {
            for (int j = -1; j <= westBoard.numColumns(); j++) {
                boolean move = whitePawn1.isValidEnPassantMove(i, j, whitePawn1);
                if (i < 0 || i > 7 || j < 0 || j > 7) {
                    assertFalse(move);
                } else if (i == 7 && j == 5) {
                    assertTrue(move);
                } else {
                    assertFalse(move);
                }
            }
        }
        
        whiteKing.moveDone();
        //Tests for en passant move for both pawns
        for (int i = -1; i <= westBoard.numRows(); i++) {
            for (int j = -1; j <= westBoard.numColumns(); j++) {
                boolean white = whitePawn1.isValidEnPassantMove(i, j, whitePawn1);
                boolean black = blackPawn1.isValidEnPassantMove(i, j, blackPawn1);
                
                assertFalse(white);
                assertFalse(black);
            }
        }
        reset();
        
        whitePawn1 = new PawnPiece(ChessGame.Side.SOUTH, southBoard, 3, 1);
        blackPawn1 = new PawnPiece(ChessGame.Side.NORTH, southBoard, 4, 3);
        whitePawn2 = new PawnPiece(ChessGame.Side.SOUTH, southBoard, 4, 4);
        blackPawn2 = new PawnPiece(ChessGame.Side.NORTH, southBoard, 3, 0);
        whiteKing = new KingPiece(ChessGame.Side.SOUTH, southBoard, 0, 4);
        blackKing = new KingPiece(ChessGame.Side.NORTH, southBoard, 7, 4);
        
        southBoard.addPiece(whitePawn1, 3, 1);
        southBoard.addPiece(blackPawn1, 4, 3);
        southBoard.addPiece(whitePawn2, 4, 4);
        southBoard.addPiece(blackPawn2, 3, 0);
        southBoard.addPiece(blackKing, 0, 4);
        southBoard.addPiece(whiteKing, 7, 4);
        whitePawn2.moveDone();
        
        //Tests for en passant move for white pawn
        for (int i = -1; i <= southBoard.numRows(); i++) {
            for (int j = -1; j <= southBoard.numColumns(); j++) {
                boolean move = blackPawn1.isValidEnPassantMove(i, j, blackPawn1);
                if (i < 0 || i > 7 || j < 0 || j > 7) {
                    assertFalse(move);
                } else if (i == 5 && j == 4) {
                    assertTrue(move);
                } else {
                    assertFalse(move);
                }
            }
        }
        
        blackPawn2.moveDone();
        //Tests for en passant move for black pawn
        for (int i = -1; i <= southBoard.numRows(); i++) {
            for (int j = -1; j <= southBoard.numColumns(); j++) {
                boolean move = whitePawn1.isValidEnPassantMove(i, j, whitePawn1);
                if (i < 0 || i > 7 || j < 0 || j > 7) {
                    assertFalse(move);
                } else if (i == 2 && j == 0) {
                    assertTrue(move);
                } else {
                    assertFalse(move);
                }
            }
        }
        
        whiteKing.moveDone();
        
        //Tests for en passant move for both pawns
        for (int i = -1; i <= southBoard.numRows(); i++) {
            for (int j = -1; j <= southBoard.numColumns(); j++) {
                boolean white = whitePawn1.isValidEnPassantMove(i, j, whitePawn1);
                boolean black = blackPawn1.isValidEnPassantMove(i, j, blackPawn1);
                
                assertFalse(white);
                assertFalse(black);
            }
        }
        reset();
    }
    
    /**
     * Tests the isValidPawnNonCaptureMove() in the PawnMove interface.
     * @since 1.0
     */
    @Test
    public void testPawnNonCaptureMove() {
        PawnPiece whitePawn = new PawnPiece(ChessGame.Side.WEST, westBoard, 2, 1);
        PawnPiece blackPawn = new PawnPiece(ChessGame.Side.EAST, westBoard, 4, 6);
        KingPiece whiteKing = new KingPiece(ChessGame.Side.WEST, westBoard, 4, 0);
        KingPiece blackKing = new KingPiece(ChessGame.Side.EAST, westBoard, 4, 7);
        
        westBoard.addPiece(whitePawn, 2, 1);
        westBoard.addPiece(blackPawn, 4, 6);
        westBoard.addPiece(whiteKing, 4, 0);
        westBoard.addPiece(blackKing, 4, 7);
        
        //Tests for noncapture move for both pawns west-east direction
        for (int i = -1; i <= southBoard.numRows(); i++) {
            for (int j = -1; j <= southBoard.numColumns(); j++) {
                boolean white = whitePawn.isValidPawnNonCaptureMove(i, j, whitePawn);
                boolean black = blackPawn.isValidPawnNonCaptureMove(i, j, blackPawn);
                
                if (i < 0 || i > 7 || j < 0 || j > 7) {
                    assertFalse(white);
                    assertFalse(black);
                } else if (i == 2 && (j == 2 || j == 3)) {
                    assertTrue(white);
                    assertFalse(black);
                } else if (i == 4 && (j == 4 || j == 5)) {
                    assertTrue(black);
                    assertFalse(white);
                } else {
                    assertFalse(white);
                    assertFalse(black);
                }
            }
        }
        reset();
        
        whitePawn = new PawnPiece(ChessGame.Side.WEST, westBoard, 2, 3);
        blackPawn = new PawnPiece(ChessGame.Side.EAST, westBoard, 4, 4);
        whiteKing = new KingPiece(ChessGame.Side.WEST, westBoard, 4, 0);
        blackKing = new KingPiece(ChessGame.Side.EAST, westBoard, 4, 7);
        
        westBoard.addPiece(whitePawn, 2, 3);
        westBoard.addPiece(blackPawn, 4, 4);
        westBoard.addPiece(whiteKing, 4, 0);
        westBoard.addPiece(blackKing, 4, 7);
        
        whitePawn.moveDone();
        blackPawn.moveDone();
        
        //Tests for non capture move for both pawns west-east direction
        for (int i = -1; i <= southBoard.numRows(); i++) {
            for (int j = -1; j <= southBoard.numColumns(); j++) {
                boolean white = whitePawn.isValidPawnNonCaptureMove(i, j, whitePawn);
                boolean black = blackPawn.isValidPawnNonCaptureMove(i, j, blackPawn);
                
                if (i < 0 || i > 7 || j < 0 || j > 7) {
                    assertFalse(white);
                    assertFalse(black);
                } else if (i == 2 && j == 4) {
                    assertTrue(white);
                    assertFalse(black);
                } else if (i == 4 && j == 3) {
                    assertTrue(black);
                    assertFalse(white);
                } else {
                    assertFalse(white);
                    assertFalse(black);
                }
            }
        }
        
        westBoard.addPiece(new BishopPiece(ChessGame.Side.WEST, westBoard, 2, 4), 2, 4);
        westBoard.addPiece(new KnightPiece(ChessGame.Side.EAST, westBoard, 4, 3), 4, 3);
        
        //Tests for non capture move for both pawns west-east direction
        for (int i = -1; i <= southBoard.numRows(); i++) {
            for (int j = -1; j <= southBoard.numColumns(); j++) {
                boolean white = whitePawn.isValidPawnNonCaptureMove(i, j, whitePawn);
                boolean black = blackPawn.isValidPawnNonCaptureMove(i, j, blackPawn);
                
                assertFalse(white);
                assertFalse(black);
            }
        }
        reset();
        
        whitePawn = new PawnPiece(ChessGame.Side.SOUTH, southBoard, 6, 2);
        blackPawn = new PawnPiece(ChessGame.Side.NORTH, southBoard, 1, 4);
        whiteKing = new KingPiece(ChessGame.Side.SOUTH, southBoard, 7, 4);
        blackKing = new KingPiece(ChessGame.Side.NORTH, southBoard, 1, 4);
        
        southBoard.addPiece(whitePawn, 6, 2);
        southBoard.addPiece(blackPawn, 1, 4);
        southBoard.addPiece(whiteKing, 7, 4);
        southBoard.addPiece(blackKing, 0, 4);
        
        //Tests for noncapture move for both pawns north-south direction
        for (int i = -1; i <= southBoard.numRows(); i++) {
            for (int j = -1; j <= southBoard.numColumns(); j++) {
                boolean white = whitePawn.isValidPawnNonCaptureMove(i, j, whitePawn);
                boolean black = blackPawn.isValidPawnNonCaptureMove(i, j, blackPawn);
                
                if (i < 0 || i > 7 || j < 0 || j > 7) {
                    assertFalse(white);
                    assertFalse(black);
                } else if (j == 2 && (i == 4 || i == 5)) {
                    assertTrue(white);
                    assertFalse(black);
                } else if (j == 4 && (i == 2 || i == 3)) {
                    assertTrue(black);
                    assertFalse(white);
                } else {
                    assertFalse(white);
                    assertFalse(black);
                }
            }
        }
        reset();
        
        whitePawn = new PawnPiece(ChessGame.Side.SOUTH, southBoard, 4, 2);
        blackPawn = new PawnPiece(ChessGame.Side.NORTH, southBoard, 3, 4);
        whiteKing = new KingPiece(ChessGame.Side.SOUTH, southBoard, 7, 4);
        blackKing = new KingPiece(ChessGame.Side.NORTH, southBoard, 1, 4);
        
        southBoard.addPiece(whitePawn, 4, 2);
        southBoard.addPiece(blackPawn, 3, 4);
        southBoard.addPiece(whiteKing, 7, 4);
        southBoard.addPiece(blackKing, 0, 4);
        
        whitePawn.moveDone();
        blackPawn.moveDone();
        
        //Tests for noncapture move for both pawns north-south direction
        for (int i = -1; i <= southBoard.numRows(); i++) {
            for (int j = -1; j <= southBoard.numColumns(); j++) {
                boolean white = whitePawn.isValidPawnNonCaptureMove(i, j, whitePawn);
                boolean black = blackPawn.isValidPawnNonCaptureMove(i, j, blackPawn);
                
                if (i < 0 || i > 7 || j < 0 || j > 7) {
                    assertFalse(white);
                    assertFalse(black);
                } else if (i == 3 && j == 2) {
                    assertTrue(white);
                    assertFalse(black);
                } else if (i == 4 && j == 4) {
                    assertTrue(black);
                    assertFalse(white);
                } else {
                    assertFalse(white);
                    assertFalse(black);
                }
            }
        }
        
        southBoard.addPiece(new BishopPiece(ChessGame.Side.NORTH, southBoard, 4, 4), 4, 4);
        southBoard.addPiece(new KnightPiece(ChessGame.Side.SOUTH, southBoard, 3, 2), 3, 2);
        
        //Tests for non capture move for both pawns wnorth-south direction
        for (int i = -1; i <= southBoard.numRows(); i++) {
            for (int j = -1; j <= southBoard.numColumns(); j++) {
                boolean white = whitePawn.isValidPawnNonCaptureMove(i, j, whitePawn);
                boolean black = blackPawn.isValidPawnNonCaptureMove(i, j, blackPawn);
                
                assertFalse(white);
                assertFalse(black);
            }
        }
        reset();
    }
    
    /**
     * Tests the isValidPawnCaptureMove() in the PawnMove interface.
     * @since 1.0
     */
    @Test
    public void testPawnCaptureMove() {
        PawnPiece whitePawn = new PawnPiece(ChessGame.Side.WEST, westBoard, 4, 2);
        PawnPiece blackPawn = new PawnPiece(ChessGame.Side.EAST, westBoard, 3, 4);
        
        westBoard.addPiece(whitePawn, 4, 2);
        westBoard.addPiece(blackPawn, 3, 4);
        westBoard.addPiece(new BishopPiece(ChessGame.Side.WEST, westBoard, 5, 3), 5, 3);
        westBoard.addPiece(new BishopPiece(ChessGame.Side.WEST, westBoard, 4, 3), 4, 3);
        westBoard.addPiece(new KnightPiece(ChessGame.Side.EAST, westBoard, 3, 3), 3, 3);
        westBoard.addPiece(new BishopPiece(ChessGame.Side.EAST, westBoard, 2, 3), 2, 3);
        
        //Tests for capture move for both pawns west-east direction
        for (int i = -1; i <= southBoard.numRows(); i++) {
            for (int j = -1; j <= southBoard.numColumns(); j++) {
                boolean white = whitePawn.isValidPawnCaptureMove(i, j, whitePawn);
                boolean black = blackPawn.isValidPawnCaptureMove(i, j, blackPawn);
                
                if (i < 0 || i > 7 || j < 0 || j > 7) {
                    assertFalse(white);
                    assertFalse(black);
                } else if (i == 3 && j == 3) {
                    assertTrue(white);
                    assertFalse(black);
                } else if (i == 4 && j == 3) {
                    assertTrue(black);
                    assertFalse(white);
                } else {
                    assertFalse(white);
                    assertFalse(black);
                }
            }
        }
        reset();
        
        whitePawn = new PawnPiece(ChessGame.Side.SOUTH, southBoard, 2, 3);
        blackPawn = new PawnPiece(ChessGame.Side.NORTH, southBoard, 4, 4);
        
        southBoard.addPiece(whitePawn, 2, 3);
        southBoard.addPiece(blackPawn, 4, 4);
        southBoard.addPiece(new BishopPiece(ChessGame.Side.SOUTH, southBoard, 1, 2), 1, 2);
        southBoard.addPiece(new KnightPiece(ChessGame.Side.NORTH, southBoard, 1, 4), 1, 4);
        southBoard.addPiece(new BishopPiece(ChessGame.Side.SOUTH, southBoard, 5, 3), 5, 3);
        southBoard.addPiece(new BishopPiece(ChessGame.Side.NORTH, southBoard, 5, 5), 5, 5);
        
        //Tests for  capture move for both pawns south-north direction
        for (int i = -1; i <= southBoard.numRows(); i++) {
            for (int j = -1; j <= southBoard.numColumns(); j++) {
                boolean white = whitePawn.isValidPawnCaptureMove(i, j, whitePawn);
                boolean black = blackPawn.isValidPawnCaptureMove(i, j, blackPawn);
                
                if (i < 0 || i > 7 || j < 0 || j > 7) {
                    assertFalse(white);
                    assertFalse(black);
                } else if (i == 1 && j == 4) {
                    assertTrue(white);
                    assertFalse(black);
                } else if (i == 5 && j == 3) {
                    assertTrue(black);
                    assertFalse(white);
                } else {
                    assertFalse(white);
                    assertFalse(black);
                }
            }
        }
        reset();
    }
    
    /**
     * Tests the getCanEnPassant() and setCanEnPassant() methods for the PawnPiece class.
     * @since 1.0
     */
    @Test
    public void testCanEnPassant() {
        PawnPiece pawn = new PawnPiece(ChessGame.Side.SOUTH, southBoard, 4, 4);
        //Tests to see if canEnPassant is false, which is the default
        assertFalse(pawn.getCanEnPassant());
        pawn.moveDone();
        //Tests to see if canEnPassant is true after moveDone()
        assertTrue(pawn.getCanEnPassant());
        pawn.setCanEnPassant(false);
        //Tests to see if canEnPassant is false
        assertFalse(pawn.getCanEnPassant());
    }
    
    /**
     * Tests the checkPawnPromotion() function for the PawnPiece class.
     * @since 1.0
     */
    @Test
    public void testCheckPawnPromotion() {
        PawnPiece pawn = new PawnPiece(ChessGame.Side.SOUTH, southBoard, 0, 0);
        
        //Tests for promtion squares for the piece for south side
        for (int i = 0; i < southBoard.numRows(); i++) {
            for (int j = 0; j < southBoard.numColumns(); j++) {
                pawn.setLocation(i, j);
                boolean check = pawn.checkPawnPromotion();
                
                if (i == 0)
                    assertTrue(check);
                else
                    assertFalse(check);
            }
        }
        
        pawn = new PawnPiece(ChessGame.Side.WEST, southBoard, 0, 0);
        
        //Tests for promtion squares for the piece for west side
        for (int i = 0; i < southBoard.numRows(); i++) {
            for (int j = 0; j < southBoard.numColumns(); j++) {
                pawn.setLocation(i, j);
                boolean check = pawn.checkPawnPromotion();
                
                if (j == 7)
                    assertTrue(check);
                else
                    assertFalse(check);
            }
        }
        
        pawn = new PawnPiece(ChessGame.Side.NORTH, southBoard, 0, 0);
        
        //Tests for promtion squares for the piece for north side
        for (int i = 0; i < southBoard.numRows(); i++) {
            for (int j = 0; j < southBoard.numColumns(); j++) {
                pawn.setLocation(i, j);
                boolean check = pawn.checkPawnPromotion();
                
                if (i == 7)
                    assertTrue(check);
                else
                    assertFalse(check);
            }
        }
        
        pawn = new PawnPiece(ChessGame.Side.EAST, southBoard, 0, 0);
        
        //Tests for promotion squares for the piece for east side
        for (int i = 0; i < southBoard.numRows(); i++) {
            for (int j = 0; j < southBoard.numColumns(); j++) {
                pawn.setLocation(i, j);
                boolean check = pawn.checkPawnPromotion();
                
                if (j == 0)
                    assertTrue(check);
                else
                    assertFalse(check);
            }
        }
    }
    
    /**
     * Tests the EuropeanChess() constructor as well as the getCurrentSide(), getStartingSide(), and flipSide() functions in the EuropeanChess class.
     * @since 1.0
     */
    @Test
    public void testEuropeanChess() {
        EuropeanChess chessGame = new EuropeanChess(ChessGame.Side.SOUTH);
        //Tests to see if the starting side equals the current side
        assertEquals(chessGame.getStartingSide(), chessGame.getCurrentSide());
        //Tests to make sure the side is the same
        assertEquals(ChessGame.Side.SOUTH, chessGame.getCurrentSide());
        chessGame.flipSide();
        //Tests to make sure that the side changed
        assertEquals(ChessGame.Side.NORTH, chessGame.getCurrentSide());
    }
    
    /**
     * Tests the makeMove() method in the EuropeanChess class.
     * @since 1.0
     */
    @Test
    public void testMakeMove() {
        ChessBoard chessBoard = new ChessBoard(8, 8, new EuropeanChessDisplay(), new EuropeanChess(ChessGame.Side.SOUTH));
        EuropeanChess rules = (EuropeanChess) chessBoard.getGameRules();
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
        chessBoard.addPiece(new KnightPiece(ChessGame.Side.SOUTH, chessBoard, 3, 3), 3, 3);
        chessBoard.addPiece(new KnightPiece(ChessGame.Side.SOUTH, chessBoard, 7, 6), 7, 6);
        chessBoard.addPiece(new KnightPiece(ChessGame.Side.NORTH, chessBoard, 0, 1), 0, 1);
        chessBoard.addPiece(new KnightPiece(ChessGame.Side.NORTH, chessBoard, 0, 6), 0, 6);
        
        //Bishops
        chessBoard.addPiece(new BishopPiece(ChessGame.Side.SOUTH, chessBoard, 3, 5), 3, 5);
        chessBoard.addPiece(new BishopPiece(ChessGame.Side.SOUTH, chessBoard, 7, 5), 7, 5);
        chessBoard.addPiece(new BishopPiece(ChessGame.Side.NORTH, chessBoard, 0, 2), 0, 2);
        chessBoard.addPiece(new BishopPiece(ChessGame.Side.NORTH, chessBoard, 0, 5), 0, 5);
        
        //Queens
        chessBoard.addPiece(new QueenPiece(ChessGame.Side.SOUTH, chessBoard, 3, 4), 3, 4);
        chessBoard.addPiece(new QueenPiece(ChessGame.Side.NORTH, chessBoard, 0, 3), 0, 3);
        
        //Kings
        chessBoard.addPiece(new KingPiece(ChessGame.Side.SOUTH, chessBoard, 7, 4), 7, 4);
        chessBoard.addPiece(new KingPiece(ChessGame.Side.NORTH, chessBoard, 0, 4), 0, 4);
        
        //Tests pawn non capture move
        assertTrue(rules.makeMove(chessBoard.getPiece(6, 2), 4, 2));
        //Tests opposite side non capture move
        assertTrue(rules.makeMove(chessBoard.getPiece(1, 1), 3, 1));
        //Tests pawn capture move
        assertTrue(rules.makeMove(chessBoard.getPiece(4, 2), 3, 1));
        assertTrue(rules.makeMove(chessBoard.getPiece(1, 2), 3, 2));
        //Tests en passant move
        assertTrue(rules.makeMove(chessBoard.getPiece(3, 1), 2, 2));
        //Tests opposite side capture move
        assertTrue(rules.makeMove(chessBoard.getPiece(1, 3), 2, 2));
        //Tests castling move
        assertTrue(rules.makeMove(chessBoard.getPiece(7, 4), 7, 2));
        
        chessBoard.close();
    }
    
    /**
     * Tests the legalPieceToPlay() method in the EuropeanChess class.
     * @since 1.0
     */
    @Test
    public void testLegalPieceToPlay() {
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
        
        for (int i = 0; i < chessBoard.numRows(); i++) {
            for (int j = 0; j < chessBoard.numColumns(); j++) {
                ChessPiece piece = chessBoard.getPiece(i, j);
                if (i < 2 || i > 5) {
                    if ((i == 7 && (j == 1 || j == 6)) || i == 6)
                        assertTrue(chessBoard.getGameRules().legalPieceToPlay(piece, i, j));
                    else
                        assertFalse(chessBoard.getGameRules().legalPieceToPlay(piece, i, j));
                }
            }
        }
    }
}