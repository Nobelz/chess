import javax.swing.*;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * <p>Represents a <code>ChessBoard</code>> that uses Java Swing implementation.</p<
 * <p>The Swing chessboard uses a <code>SwingChessBoardDisplay</code> to determine how
 * the chessboard should be displayed.</p>
 * <p>The chessboard uses a <code>ChessGame</code> object to determine how the chess
 * game should be played.</p>
 *
 * @author Nobel Zhou (nxz157)
 * @author Harold Connamacher
 * @version 1.0, 12/2/2020
 */
public class SwingChessBoard implements ChessBoard {

    //region FIELDS
    // Stores the Swing chessboard display window
    protected JFrame board;

    // Stores the squares of the Swing chessboard
    private final JButton[][] squares;

    // Stores the pieces of the Swing chessboard
    private final ChessPiece[][] pieces;

    // Stores the rules for the Swing chessboard
    private final ChessGame gameRules;

    // Stores the display rules for the Swing chessboard
    protected SwingChessBoardDisplay boardDisplay;
    //endregion

    //region CONSTRUCTORS
    /**
     * <p>Creates a Swing chessboard using the given display and game rules.</p>
     *
     * @param boardDisplay the display rules for the Swing chessboard
     * @param gameRules    the game rules for the Swing chessboard
     * @since 1.0
     */
    public SwingChessBoard(SwingChessBoardDisplay boardDisplay, ChessGame gameRules) {
        // Allows users on Mac to change button background
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Initializes the board
        this.gameRules = gameRules;
        this.boardDisplay = boardDisplay;
        pieces = new ChessPiece[gameRules.getNumRows()][gameRules.getNumColumns()];
        squares = new JButton[gameRules.getNumRows()][gameRules.getNumColumns()];

        // Creates board visuals on Event Dispatch Thread
        try {
            SwingUtilities.invokeAndWait(() -> {
                board = new JFrame();
                board.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                // Uses GridLayout to display the squares
                JPanel panel = new JPanel(new GridLayout(gameRules.getNumRows(), gameRules.getNumColumns()));

                ActionListener responder = new ActionListener() {
                    // Stores whether we are selecting the piece
                    private boolean firstPick = true;  // If true, we a selecting a piece

                    // Stores the row of the piece
                    private int pieceRow;

                    // Stores the column of the piece
                    private int pieceCol;

                    /**
                     * <p>Highlights the square when the piece is first selected.</p>
                     *
                     * @param row   the row of the chosen piece
                     * @param col   the column of the chosen piece
                     */
                    private void processFirstSelection(int row, int col) {
                        // Checks if the piece exists at the selected row and column
                        if ((pieces[row][col] != null) &&
                                (getGameRules() == null ||
                                        getGameRules().legalPieceToPlay(pieces[row][col], row, col))) {
                            pieceRow = row;
                            pieceCol = col;
                            boardDisplay.highlightSquare(true, squares[row][col], row, col, pieces[row][col]);

                            // Checks if the board should display all highlighted moves
                            if (boardDisplay.shouldDisplayPossibleMoves()) {
                                for (int i = 0; i < getGameRules().getNumRows(); i++) {
                                    for (int j = 0; j < getGameRules().getNumColumns(); j++) {
                                        if (getPiece(row, col).isLegalMove(i, j) && getGameRules().isCheckMove(i, j, getPiece(row, col)))
                                            boardDisplay.highlightSquare(true, squares[i][j], i, j, pieces[row][col]);
                                    }
                                }
                            }

                            firstPick = false;
                        }
                    }

                    /**
                     * <p>What we do when the user chooses the square to move the piece to.</p>
                     *
                     * @param row   the row the piece will move to
                     * @param col   the column the piece will move to
                     */
                    private void processSecondSelection(int row, int col) {
                        // Checks if the square selected was the piece selected in the first selection; if so,
                        // the selection is reset
                        if (row == pieceRow && col == pieceCol) {
                            firstPick = true;
                            for (int i = 0; i < getGameRules().getNumRows(); i++) {
                                for (int j = 0; j < getGameRules().getNumColumns(); j++) {
                                    boardDisplay.highlightSquare(false, squares[i][j], i, j, pieces[i][j]);
                                }
                            }
                            return;
                        }

                        // Makes the move and returns if the move was successfully made
                        boolean moveMade = getGameRules().makeMove(pieces[pieceRow][pieceCol], row, col);

                        // Checks to see if the move was made or if it wasn't made, if the user can select a new
                        // piece; if so, the selection is reset
                        if (moveMade || getGameRules().canChangeSelection(pieces[pieceRow][pieceCol], pieceRow, pieceCol)) {
                            for (int i = 0; i < getGameRules().getNumRows(); i++) {
                                for (int j = 0; j < getGameRules().getNumColumns(); j++) {
                                    boardDisplay.highlightSquare(false, squares[i][j], i, j, pieces[i][j]);
                                }
                            }

                            firstPick = true;
                        }

                        // Checks to see if a move was made; if it was, ending conditions are checked
                        if (moveMade) {
                            // Runs code on different thread to it from blocking the Event Dispatch Thread
                            new Thread(() -> getGameRules().handleEndConditions(SwingChessBoard.this, getCentralPiece(pieces[row][col]))).start();
                        }
                    }

                    /**
                     * <p>Handles a button click.</p>
                     *
                     * @param e     the event that triggered the method
                     */
                    public void actionPerformed(ActionEvent e) {
                        JButton b = (JButton) e.getSource();
                        int col = -1;
                        int row = -1;

                        // Finds which button was clicked
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
                        } else {
                            processSecondSelection(row, col);
                        }
                    }
                };

                // Creates the buttons for each square
                for (int i = 0; i < gameRules.getNumRows(); i++) {
                    for (int j = 0; j < gameRules.getNumColumns(); j++) {
                        squares[i][j] = new JButton();
                        squares[i][j].addActionListener(responder);
                        boardDisplay.displayEmptySquare(squares[i][j], i, j);
                        panel.add(squares[i][j]);
                        pieces[i][j] = null;
                    }
                }
                board.add(panel);
                board.setSize(boardDisplay.getSquareSize() * gameRules.getNumColumns(), boardDisplay.getSquareSize() * gameRules.getNumRows());
                board.setVisible(true);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //endregion

    //region METHODS
    /**
     * <p>Returns the rules of the Swing chessboard.</p>
     *
     * @return  the rules of the game
     * @since 1.0
     */
    @Override
    public ChessGame getGameRules() {
        return gameRules;
    }

    /**
     * <p>Adds a piece to the Swing chessboard at the desired location.  Any piece currently
     * at that location is lost.</p>
     *
     * @param piece the piece to add
     * @param row   the row for the piece
     * @param col   the column for the piece
     * @since 1.0
     */
    @Override
    public void addPiece(final ChessPiece piece, final int row, final int col) {
        simulateAddPiece(piece, row, col);

        Runnable addPiece = () -> boardDisplay.displayFilledSquare(squares[row][col], row, col, piece);

        // Changes the display on the Event Dispatch Thread
        if (SwingUtilities.isEventDispatchThread())
            addPiece.run();
        else {
            try {
                SwingUtilities.invokeAndWait(addPiece);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * <p>Adds a piece to the Swing chessboard at the desired location without updating the board visually.</p>
     *
     * @param row The row of the piece
     * @param col The column of the piece
     * @since 1.0
     */
    @Override
    public void simulateAddPiece(final ChessPiece piece, final int row, final int col) {
        pieces[row][col] = piece;
        piece.setLocation(row, col);
    }

    /**
     * <p>Removes a piece from the Swing chessboard.</p>
     * <p>Returns the <code>ChessPiece</code> that was just removed.</p>
     *
     * @param row   the row of the piece
     * @param col   the column of the piece
     * @return      the piece that was just removed, <code>null</code> if there was no piece at that square
     * @since 1.0
     */
    @Override
    public ChessPiece removePiece(final int row, final int col) {
        ChessPiece save = simulateRemovePiece(row, col);

        Runnable removePiece = () -> boardDisplay.displayEmptySquare(squares[row][col], row, col);

        // Changes the display on the Event Dispatch Thread
        if (SwingUtilities.isEventDispatchThread())
            removePiece.run();
        else {
            try {
                SwingUtilities.invokeAndWait(removePiece);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return save;
    }

    /**
     * <p>Removes a piece from the Swing chessboard, without updating the display.</p>
     * <p>Returns the <code>ChessPiece</code> that was just removed.</p>
     *
     * @param row   the row of the chessboard
     * @param col   the column of the chessboard
     * @return      the piece that was just removed, <code>null</code> if there was no piece at that square
     * @since 1.0
     */
    @Override
    public ChessPiece simulateRemovePiece(final int row, final int col) {
        ChessPiece save = pieces[row][col];
        pieces[row][col] = null;
        return save;
    }

    /**
     * <p>Returns <code>true</code> if there is a piece at a specific location of the board.</p>
     *
     * @param row   the row to examine
     * @param col   the column to examine
     * @return      <code>true</code> if there is a piece at the specified row and column
     * @since 1.0
     */
    public boolean hasPiece(int row, int col) {
        return (pieces[row][col] != null);
    }

    /**
     * <p>Returns the <code>ChessPiece</code> at a specific location on the board.</p>
     *
     * @param row   the row for the piece
     * @param col   the column for the piece
     * @return      the piece at the row and column or null if there is no piece there
     */
    public ChessPiece getPiece(int row, int col) {
        return pieces[row][col];
    }

    /**
     * <p>Generates a <code>ChessPosition</code> object for the chessboard position.</p>
     *
     * @return  the <code>ChessPosition</code> for the chessboard
     * @since 1.0
     */
    @Override
    public ChessPosition generateChessPosition() {
        // Clones the pieces to put into a ChessPosition
        ChessPiece[][] chessPieces = pieces.clone();
        for (int i = 0; i < chessPieces.length; i++) {
            chessPieces[i] = chessPieces[i].clone();
        }

        return new ChessPosition(chessPieces, getGameRules().getCurrentSide());
    }

    /**
     * <p>Handles how to stop the chess game.</p>
     *
     * @param result    the result of the chess game
     * @param side      the side of the winning player, if there was one
     * @since 1.0
     */
    @Override
    public void terminate(ChessResult result, ChessGame.Side side) {
        Runnable resultDialog = () -> {
            // Stores the popup window
            JDialog dialog = new JDialog(board);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); // Exits on close so we can then interact with main JFrame

            String resultText;

            // Stores the player who won
            final String s = (side.equals(ChessGame.Side.NORTH)) ? "North" : (side.equals(ChessGame.Side.SOUTH)) ? "South" : (side.equals(ChessGame.Side.WEST)) ? "West" : "East";

            switch (result) {
                case CHECKMATE:
                    resultText = s + " has won the game by checkmate!";
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
                case THREEFOLD_REPETITION:
                    resultText = "The game is a draw by threefold repetition.";
                    break;
                default: // Not handled as of yet
                    resultText = result.toString();
            }

            // Shows the JOptionPane
            JOptionPane.showMessageDialog(board, resultText, "Game Over", JOptionPane.PLAIN_MESSAGE);
        };

        // Runs the code on Event Dispatch Thread
        if (SwingUtilities.isEventDispatchThread())
            resultDialog.run();
        else {
            try {
                SwingUtilities.invokeAndWait(resultDialog);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        board.dispose();
    }

    /**
     * <p>Displays promotion window by letting user choose from <code>ChessPieces</code> that are <code>Promotable</code>.</p>
     *
     * @param piece the piece to be promoted
     * @since 1.0
     */
    @Override
    public void invokePromotion(ChessPiece piece) {
        // Runs code on separate thread so it doesn't block the Event Dispatch or main threads
        new Thread(() -> {
            //Stores the promotion pieces used for JFrame
            ChessPiece[] promotionPieces = new ChessPiece[]{
                    new KnightPiece(piece.getSide(), piece.getChessBoard(), (piece.getSide().equals(piece.getChessBoard().getGameRules().getStartingSide()) ? ChessIcon.WHITE_KNIGHT : ChessIcon.BLACK_KNIGHT), -1, -1),
                    new BishopPiece(piece.getSide(), piece.getChessBoard(), (piece.getSide().equals(piece.getChessBoard().getGameRules().getStartingSide()) ? ChessIcon.WHITE_BISHOP : ChessIcon.BLACK_BISHOP), -1, -1),
                    new RookPiece(piece.getSide(), piece.getChessBoard(), (piece.getSide().equals(piece.getChessBoard().getGameRules().getStartingSide()) ? ChessIcon.WHITE_ROOK : ChessIcon.BLACK_ROOK), -1, -1),
                    new QueenPiece(piece.getSide(), piece.getChessBoard(), (piece.getSide().equals(piece.getChessBoard().getGameRules().getStartingSide()) ? ChessIcon.WHITE_QUEEN : ChessIcon.BLACK_QUEEN), -1, -1)
            };
            // Stores the JButtons used for JFrame
            JButton[] promotionSquares = new JButton[promotionPieces.length];

            // Stores the popup window
            JDialog promotionWindow = new JDialog(board);
            promotionWindow.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); // Disposes on close so we can then interact with main JFrame
            promotionWindow.setModal(true); // Stops actions in other JFrame until the popup window closes

            // Adds a method that sets the default promotion piece to queen if the user doesn't press the button
            promotionWindow.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    if (getPiece(piece.getRow(), piece.getColumn()).equals(piece)) // Checks if the pawn hasn't been promoted yet
                        getGameRules().promote(piece, promotionPieces[3]); // Promotes to queen
                }
            });

            // Stores the layout panel
            JPanel promotionPanel = new JPanel(new GridLayout(1, promotionPieces.length));

            for (int i = 0; i < promotionPieces.length; i++) {
                promotionSquares[i] = new JButton();
                // i needs to be final in order for us to use it in lambda expression
                int finalI = i;

                promotionSquares[i].addActionListener((e) -> {
                    getGameRules().promote(piece, promotionPieces[finalI]);
                    promotionWindow.dispose();
                });
                boardDisplay.displayFilledSquare(promotionSquares[i], -1, -1, promotionPieces[i]);
                promotionPanel.add(promotionSquares[i]);
            }

            promotionWindow.add(promotionPanel);
            promotionWindow.setSize(boardDisplay.getSquareSize() * promotionPieces.length, boardDisplay.getSquareSize() + 20);
            promotionWindow.setTitle("Pawn Promotion");

            promotionWindow.pack();
            promotionWindow.setVisible(true);
        }).start();
    }

    /**
     * <p>Main method that runs the Java Swing Indo-European chessboard.</p>
     *
     * @param args  main method arguments
     * @since 1.0
     */
    public static void main(String[] args) {
        EuropeanChess game = new EuropeanChess(ChessGame.Side.SOUTH);
        game.startGame(new SwingChessBoard(new SwingEuropeanChessDisplay(), game));
    }
}


