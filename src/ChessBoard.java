import javax.swing.*;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * <p>Creates a chessboard in a window on the desktop.  The ChessBoard has a ChessBoardDisplay object that determines
 * how the individual squares of the chessboard should be drawn.</p>
 * 
 * <p>The chessboard uses a ChessGame object to determine how the game should be played.  The way the chessboard works
 * is as follows.  The player selects a piece by clicking on the board, and
 * and the chessboard calls the <tt>legalPieceToPlay</tt> method of the ChessGame object.
 * If the player is allowed to select the piece, the board highlights it, and the player can select another square on
 * the board.  The chessboard then calls the <tt>makeMove</tt> method of the ChessGame object.  The ChessGame is 
 * responsible for determining if the move is valid, and if it is to update the game and the chessboard
 * with the results of making that move.</p>
 * 
 * @author Harold Connamacher and Nobel Zhou (nxz157)
 */
public class ChessBoard {

    private JFrame board;                          // the game board
    private JButton[][] squares;                   // the squares of the board
    private ChessPiece[][] pieces;                 // stores the pieces
    private ChessGame gameRules;                   // global rules for this particular game
    private ChessBoardDisplay boardDisplay;        // rules for how to draw the chess board 

    /**
     * Builds a board of the desired size, the display parameters, and the rules for the chess game.
     * @param numRows   the number of rows for the chessboard
     * @param numColumns  the number of columns for the chessboard
     * @param boardDisplay  an object that determines how the squares on the chessboard should be drawn
     * @param gameRules  an object that determines when player selection is valid and to update the game with the result of a move
     */
    public ChessBoard(final int numRows, final int numColumns, ChessBoardDisplay boardDisplay, ChessGame gameRules) {
        // for Mac computers: this allows us to change a button background
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        }
        catch (Exception e) {
        }

        // initialize the board
        this.gameRules = gameRules;
        this.boardDisplay = boardDisplay;
        pieces = new ChessPiece[numRows][numColumns];
        squares = new JButton[numRows][numColumns];

        // create the board visuals on the event dispatch thread
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    board = new JFrame();
                    board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    
                    // create a grid for the squares and the listener for the user clicks
                    JPanel panel = new JPanel(new GridLayout(numRows, numColumns));
                    ActionListener responder = new ChessAction();

                    // create the squares
                    for (int i = 0; i < numRows; i++) {
                        for (int j = 0; j < numColumns; j++) {
                            squares[i][j] = new JButton();
                            squares[i][j].addActionListener(responder);
                            boardDisplay.displayEmptySquare(squares[i][j], i, j);
                            panel.add(squares[i][j]);
                            pieces[i][j] = null;
                        }
                    }
                    board.add(panel);
                    board.setSize(boardDisplay.getSquareSize() * numColumns, boardDisplay.getSquareSize() * numRows);
                    board.setVisible(true);
                }
            });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the rules of the game.
     * @return the rules of the game
     */
    public ChessGame getGameRules() {
        return gameRules;
    }
    
    /**
     * Returns the pieces of the game.
     * @return  The pieces of the game
     * @since 1.0
     */
    public ChessPiece[][] getPieces() {
        return pieces;
    }
    
    /**
     * Changes the rules of the game
     * @param newRules the new rules for the game
     */
    public void setGameRules(ChessGame newRules) {
        this.gameRules = newRules;
    }

    /**
     * Returns the number of rows in the board.
     * @return the number of rows
     */
    public final int numRows() {
        return squares.length;
    }

    /**
     * Returns the number of columns in the board.
     * @return the number of columns
     */
    public final int numColumns() {
        return squares[0].length;
    }

    /**
     *  Adds a piece to the board at the desired location.  Any piece currently
     *  at that location is lost.
     *  @param piece   the piece to add
     *  @param row     the row for the piece
     *  @param col     the column for the piece
     */
    public void addPiece(final ChessPiece piece, final int row, final int col) {
        // set the piece on the board, tell the piece where it is, and then use the display rules to display the square
        // run the display code on the event dispatch thread

        pieces[row][col] = piece;
        piece.setLocation(row, col);

        Runnable addPiece = new Runnable() {
                public void run() {
                    boardDisplay.displayFilledSquare(squares[row][col], row, col, piece);
                }
            };

        // run the code to change the display on the event dispatch to avoid drawing errors
        if (SwingUtilities.isEventDispatchThread())
            addPiece.run();
        else {
            try {
                SwingUtilities.invokeAndWait(addPiece);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Adds a piece to the board at the desired location WITHOUT updating the board visually.
     * Returns the ChessPiece that was at the desired location.
     * @param row The row of the piece
     * @param col The column of the piece
     * @return    The piece that was replaced, if any    
     */   
    public ChessPiece simulateAddPiece(ChessPiece piece, int row, int col) {
        ChessPiece save = pieces[row][col];
        pieces[row][col] = piece;
        piece.setLocation(row, col);

        return save;
    }

    /**
     *  Removes a piece from the board
     *  @param row  the row of the piece
     *  @param col  the column of the piece
     *  @return  the piece removed of null if there was no piece at that square
     */
    public ChessPiece removePiece(final int row, final int col) {
        // remove the piece from the board, use the display rules to show an empty square,
        // and run the display code on the event dispatch thread
        ChessPiece save = pieces[row][col];
        pieces[row][col] = null;

        Runnable removePiece = new Runnable() {
                public void run() {
                    boardDisplay.displayEmptySquare(squares[row][col], row, col);
                }
            };    
        if (SwingUtilities.isEventDispatchThread())
            removePiece.run();
        else {
            try {
                SwingUtilities.invokeAndWait(removePiece);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        return save;
    }

    /**
     * Removes a piece to the board at the desired location WITHOUT updating the board visually.
     * Returns the ChessPiece that was at the desired location.
     * @param row The row of the piece
     * @param col The column of the piece
     * @return    The piece that was removed, if any  
     */   
    public ChessPiece simulateRemovePiece(int row, int col) {
        ChessPiece save = pieces[row][col];
        pieces[row][col] = null;

        return save;
    }

    /**
     *  Returns true if there is a piece at a specific location of the board.
     *  @param row   the row to examine
     *  @param col   the column to examine
     *  @return   true if there is a piece a this row and column and false
     *            if the square is empty
     */
    public boolean hasPiece(int row, int col) {
        return (pieces[row][col] != null);
    }

    /**
     *  Returns the chess piece at a specific location on the board.
     *  @param row   the row for the piece
     *  @param col   the column for the piece
     *  @return      the piece at the row and column or null if there is no piece there.
     */
    public ChessPiece getPiece(int row, int col) {
        return pieces[row][col];
    }

    /**
     * Represents a promotion button action.
     * @since 1.0
     */
    private class PromotionAction implements ActionListener {
        //Stores the chess piece that the pawn should be promoted to
        private ChessPiece promotedPiece;
        //Stores the chess piece to be promoted
        private ChessPiece oldPiece;
        //Stores the JDialog that the JButton is contained in
        private JDialog promotionWindow;
        
        /**
         * Initializes PromotionAction with the promoted piece.
         * @param   The chess piece that the pawn should be promoted to
         * @since 1.0
         */
        private PromotionAction(ChessPiece newPiece, ChessPiece oldPiece, JDialog promotionWindow) {
            promotedPiece = newPiece;
            this.oldPiece = oldPiece;
            this.promotionWindow = promotionWindow;
        }

        /**
         *  Handle a button click.  The method alternates between selecting a piece
         *  and selecting any square.  After both are selected, the piece's 
         *  legalMove is called, and if the move is legal, the piece is moved.
         *  @param e   the event that triggered the method
         */
        public void actionPerformed(ActionEvent e) {
            ((EuropeanChess) getGameRules()).promote(oldPiece, promotedPiece);
            promotionWindow.dispose();
        }
    }

    /** The code the responds when the user clicks on the game board */
    private class ChessAction implements ActionListener {
        private boolean firstPick = true;  // if true, we a selecting a piece
        private int pieceRow;              // remember row of selected piece
        private int pieceCol;              // remember column of selected piece

        /** 
         * What we do when the user chooses the piece to move.
         * @param row the row of the chosen piece
         * @param col the column of the chosen piece
         */
        private void processFirstSelection(int row, int col) {
            if ((pieces[row][col] != null) && 
            (getGameRules() == null || getGameRules().legalPieceToPlay(pieces[row][col], row, col))) {
                /*
                 * if this is the first pick and a square with a piece was picked,
                 * remember the piece's location and highlight the square.
                 */
                pieceRow = row;
                pieceCol = col;
                boardDisplay.highlightSquare(true, squares[row][col], row, col, pieces[row][col]);

                //Loops and displays each possible move
                for (int i = 0; i < numRows(); i++) {
                    for (int j = 0; j < numColumns(); j++) {    
                        if (getPiece(row, col).isLegalMove(i, j) && (!(getGameRules() instanceof EuropeanChess) || ((EuropeanChess) getGameRules()).isGoodMove(i, j, getPiece(row, col))))
                            boardDisplay.highlightSquare(true, squares[i][j], i, j, pieces[row][col]);
                    }
                }

                firstPick = false;
            }
        }

        /**
         * What we do when the user chooses the square to move the piece to.
         * @param row the row the piece will move to
         * @param col the column the piece will move to
         */
        private void processSecondSelection(int row, int col) {
            if (row == pieceRow && col == pieceCol)
                return;

            boolean moveMade = getGameRules().makeMove(pieces[pieceRow][pieceCol], row, col);

            // if the move was made or if it was not made and the user select a new piece, then reset to choose a new move
            if (moveMade || getGameRules().canChangeSelection(pieces[pieceRow][pieceCol], pieceRow, pieceCol)) {
                for (int i = 0; i < numRows(); i++) {
                    for (int j = 0; j < numColumns(); j++) {
                        boardDisplay.highlightSquare(false, squares[i][j], i, j, pieces[i][j]);
                    }
                }

                firstPick = true;
            }
        }

        /**
         *  Handle a button click.  The method alternates between selecting a piece
         *  and selecting any square.  After both are selected, the piece's 
         *  legalMove is called, and if the move is legal, the piece is moved.
         *  @param e   the event that triggered the method
         */
        public void actionPerformed(ActionEvent e) {
            JButton b = (JButton)e.getSource();
            int col = -1;
            int row = -1;

            // first find which button (board square) was clicked.
            for (int i = 0; i < squares.length; i++) {
                for (int j = 0; j < squares[i].length; j++) {
                    if (squares[i][j] == b) {
                        row = i;
                        col = j;
                    }
                }
            }

            if (firstPick) {
                processFirstSelection(row, col);
            }
            else {
                processSecondSelection(row, col);
            }
        }
    }

    /**
     * Returns true if a particular square is threatened by an opposing piece.
     * @param row     the row of the square
     * @param column  the column of the square
     * @param piece   a piece of the game
     * @return  true if the square can be attacked by a piece of an opposing side as the parameter piece
     */
    public boolean squareThreatened(int row, int column, ChessPiece piece) {
        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares[i].length; j++) {
                if (hasPiece(i, j) && getPiece(i, j).getSide() != piece.getSide() &&
                getPiece(i, j).isLegalMove(row, column)) {
                    //Checks for pawn non-capture move because they can't capture right in front of them even though they can move there
                    return !(getPiece(i, j) instanceof PawnPiece) || !((PawnPiece) getPiece(i, j)).isLegalNonCaptureMove(i, j);
                }
            }
        }
        return false;
    }

    /**
     * Returns a KingPiece representing the king of the same side.
     * @param piece   A piece of the game
     * @return        The king of the same side
     */
    public KingPiece getKing(ChessPiece piece) {
        //Stores the king piece of the same side; placeholder needed
        KingPiece king = new KingPiece(piece.getSide(), this, -1, -1); //This will never return because we always know that a king with the same side will be present

        //Iterates the chess board to look for the same side king piece
        for (int i = 0; i < numRows(); i++) {
            for (int j = 0; j < numColumns(); j++) {
                //Looks for same side king piece
                if (hasPiece(i, j) && getPiece(i, j) instanceof KingPiece && getPiece(i, j).getSide().equals(piece.getSide()))
                    king = (KingPiece) getPiece(i,j);
            }
        }

        return king;
    }

    /**
     * Runs promotion processes by letting user choose from Promotable ChessPieces.
     * @param piece The piece to be promoted
     * @since 1.0
     */
    public void invokePromotion(ChessPiece piece) {

        Runnable promotePiece = new Runnable() {
            public void run() {
                //Stores the promotion pieces used for JFrame
                ChessPiece[] promotionPieces = new ChessPiece[] {new KnightPiece(piece.getSide(), piece.getChessBoard(), -1, -1), new BishopPiece(piece.getSide(), piece.getChessBoard(), -1, -1), new RookPiece(piece.getSide(), piece.getChessBoard(), -1, -1), new QueenPiece(piece.getSide(), piece.getChessBoard(), -1, -1)};
                //Stores the JButtons used for JFrame
                JButton[] promotionSquares = new JButton[promotionPieces.length];

                //Stores the popup window
                JDialog promotionWindow = new JDialog(board);
                promotionWindow.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); //Disposes on close so we can then interact with main JFrame
                promotionWindow.setModal(true); //Stops actions in other JFrame until the popup window closes

                //Adds a method that sets the default promotion piece to queen if the user doesn't press the button
                promotionWindow.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        if (getPiece(piece.getRow(), piece.getColumn()).equals(piece)) //Checks if the pawn hasn't been promoted yet
                            ((EuropeanChess) getGameRules()).promote(piece, new QueenPiece(piece.getSide(), piece.getChessBoard(), -1, -1)); //Promotes to queen
                    }
                });

                //Stores the layout panel
                JPanel promotionPanel = new JPanel(new GridLayout(1, promotionPieces.length));

                for (int i = 0; i < promotionPieces.length; i++) {
                    promotionSquares[i] = new JButton();
                    promotionSquares[i].addActionListener(new PromotionAction(promotionPieces[i], piece, promotionWindow)); //Adds event handler to each button
                    boardDisplay.displayFilledSquare(promotionSquares[i], -1, -1, promotionPieces[i]);
                    promotionPanel.add(promotionSquares[i]);
                }

                promotionWindow.add(promotionPanel);
                promotionWindow.setSize(boardDisplay.getSquareSize() * promotionPieces.length, boardDisplay.getSquareSize() + 20);
                promotionWindow.setTitle("Pawn Promotion");

                promotionWindow.pack();
                promotionWindow.setVisible(true);
            }
        };

        // run the code to change the display on the event dispatch to avoid drawing errors
        if (SwingUtilities.isEventDispatchThread())
            promotePiece.run();
        else {
            try {
                SwingUtilities.invokeAndWait(promotePiece);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Ends the chess game.
     * @param result    The result of the chess game
     * @param side      The side that possibly won the chess game, may be null if a draw
     * @since 1.0
     */
    public void terminate(EuropeanChess.Result result, ChessGame.Side side) {
        Runnable resultDialog = new Runnable() {
            public void run() {
                //Stores the popup window
                JDialog dialog = new JDialog(board);
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); //Exits on close so we can then interact with main JFrame
                
                String resultText;
                
                switch (result) {
                    case CHECKMATE:
                        resultText = ((side.equals(ChessGame.Side.NORTH)) ? "North" : (side.equals(ChessGame.Side.SOUTH)) ? "South" : (side.equals(ChessGame.Side.WEST)) ? "West" : "East") + " has won the game by checkmate!";
                        break;
                    case STALEMATE:
                        resultText = "The game is a draw by stalemate.";
                        break;
                    case INSUFFICIENT_MATERIAL:
                        resultText = "The game is a draw by insufficient material.";
                        break;
                    case FIFTY_MOVE_RULE:
                        resultText = "The game is a draw by the fifty move rule.";
                        break;
                    default: //THREEFOLD_REPETITION
                        resultText = "The game is a draw by threefold repetition.";
                        break;
                }
                
                //Shows the JOptionPane
                JOptionPane.showMessageDialog(board, resultText, "Game Over", JOptionPane.PLAIN_MESSAGE);
            }
        };

        // run the code to change the display on the event dispatch to avoid drawing errors
        if (SwingUtilities.isEventDispatchThread())
            resultDialog.run();
        else {
            try {
                SwingUtilities.invokeAndWait(resultDialog);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        close();
    }
    
    /**
     * Closes the chess board.
     * @since 1.0
     */
    public void close() {
        board.setVisible(false);
        board.dispose();
    }
}

