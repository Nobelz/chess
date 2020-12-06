import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * <p>Represents a <code>ChessBoard</code>> that uses JavaFX implementation.</p<
 * <p>The JavaFX chessboard uses a <code>JavaFXChessDisplay</code> to determine how
 * the chessboard should be displayed.</p>
 * <p>The chessboard uses a <code>ChessGame</code> object to determine how the chess
 * game should be played.</p>
 *
 * @author Nobel Zhou (nxz157)
 * @version 1.0, 12/4/2020
 */
public class JavaFXChessBoard extends Application implements ChessBoard {

    //region FIELDS
    // Stores the squares of the JavaFX chessboard
    private Button[][] squares;

    // Stores the pieces of the JavaFX chessboard
    private ChessPiece[][] pieces;

    // Stores the rules for the JavaFX chessboard
    private ChessGame gameRules;

    // Stores the display rules for the JavaFX chessboard
    private JavaFXChessBoardDisplay boardDisplay;

    // Stores the primary stage of the JavaFX chessboard
    private static Stage primaryStage;

    // Stores the arguments from the parent application, if any
    public static String[] parentArguments = null;
    //endregion

    //region METHODS
    /**
     * <p>Main method that launches the application.</p>
     *
     * @param args  main method arguments; determines which game is played
     * @since 1.0
     */
    public static void main(String[] args) {
        Application.launch(args);
    }

    /**
     * <p>Sets up the JavaFX chessboard on the Application Thread.</p>
     *
     * @param primaryStage  the primary stage of the application
     * @since 1.0
     */
    @Override
    public void start(Stage primaryStage) {
        JavaFXChessBoard.primaryStage = primaryStage;
        
        try {
            // Stores the game type
            String gameType;

            // Indicates that it was invoked from parent application
            if (parentArguments != null) {
                gameType = parentArguments[0];
            }  else
                gameType = getParameters().getRaw().get(0);

            // Checks the game type from function parameters: it's either Indo-European chess or Xiangqi
            switch (gameType) {
                case "chess":
                    boardDisplay = new JavaFXEuropeanChessDisplay();
                    gameRules = new EuropeanChess(ChessGame.Side.SOUTH);
                    squares = new Button[gameRules.getNumRows()][gameRules.getNumColumns()];
                    pieces = new ChessPiece[gameRules.getNumRows()][gameRules.getNumColumns()];
                    break;
                case "xiangqi":
                    boardDisplay = new JavaFXXiangqiDisplay();
                    gameRules = new Xiangqi(ChessGame.Side.SOUTH);
                    squares = new Button[gameRules.getNumRows()][gameRules.getNumColumns()];
                    pieces = new ChessPiece[gameRules.getNumRows()][gameRules.getNumColumns()];
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Command arguments needed to determine which game is played.");
            System.exit(-1);
        } catch (IllegalArgumentException e) {
            System.out.println("Incorrect arguments entered.");
            System.exit(-1);
        }

        GridPane layout = new GridPane();

        EventHandler<ActionEvent> responder = new EventHandler<ActionEvent>() {
            // Stores whether we are selecting the piece
            private boolean firstPick = true;  // If true, we a selecting a piece

            // Stores the row of the piece
            private int pieceRow;

            // Stores the column of the piece
            private int pieceCol;

            @Override
            public void handle(ActionEvent e) {
                Button b = (Button) e.getSource();
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
                                    boardDisplay.highlightSquare(true, squares[i][j], i, j, pieces[i][j]);
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
                    // Stores the central piece of the side that just played
                    CenterPiece king = getCentralPiece(pieces[row][col]);

                    // Un-highlights the central piece because it is no longer in check, if it was in check before
                    boardDisplay.highlightCheckSquare(false, squares[king.getRow()][king.getColumn()], king.getRow(), king.getColumn(), king);

                    // Highlights the opposing kings or xiangqi kings if the piece is in check
                    for (CenterPiece c: king.getOpposingKings()) {
                        if (c.isInCheck())
                            boardDisplay.highlightCheckSquare(true, squares[c.getRow()][c.getColumn()], c.getRow(), c.getColumn(), c);
                    }

                    // Runs code on different thread to it from blocking the Application Thread
                    new Thread(new Task<Void>() {
                        /**
                         * <p>Invoked when the Task is executed, the call method must be overridden and
                         * implemented by subclasses. The call method actually performs the
                         * background thread logic. Only the updateProgress, updateMessage, updateValue and
                         * updateTitle methods of Task may be called from code within this method.
                         * Any other interaction with the Task from the background thread will result
                         * in runtime exceptions.</p>
                         *
                         * @return  the result of the background work, if any
                         */
                        @Override
                        protected Void call() {
                            // Checks for ending conditions
                            getGameRules().handleEndConditions(JavaFXChessBoard.this, getCentralPiece(pieces[row][col]));
                            return null;
                        }
                    }).start();
                }
            }
        };

        for (int i = 0; i < getGameRules().getNumRows(); i++) {
            for (int j = 0; j < getGameRules().getNumColumns(); j++) {
                squares[i][j] = new Button();
                squares[i][j].setOnAction(responder);
                squares[i][j].setPrefSize(boardDisplay.getSquareSize(), boardDisplay.getSquareSize());
                layout.add(squares[i][j], j, i);
                boardDisplay.displayEmptySquare(squares[i][j], i, j);
                pieces[i][j] = null;
            }
        }

        Scene scene = new Scene(layout);
        gameRules.startGame(this);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * <p>Returns a <code>ChessGame</code> representing the rules of the chessboard.</p>
     * <p>For example, it can return either the rules for Indo-European Chess or Xiangqi.</p>
     *
     * @return the rules of the chessboard
     * @since 1.0
     */
    @Override
    public ChessGame getGameRules() {
        return gameRules;
    }

    /**
     * <p>Adds a <code>ChessPiece</code> to the chessboard at the given row and column.</p>
     *
     * @param piece  the chess piece to be added
     * @param row    the row of the chessboard
     * @param column the column of the chessboard
     * @since 1.0
     */
    @Override
    public void addPiece(ChessPiece piece, int row, int column) {
        simulateAddPiece(piece, row, column);

        Runnable addPiece = () -> boardDisplay.displayFilledSquare(squares[row][column], row, column, piece);

        addPiece.run();
    }

    /**
     * <p>Adds a <code>ChessPiece</code> to the chessboard at the given row and column, but does not
     * update the display.</p>
     * <p>This is used to check for checks when doing a proposed move.</p>
     *
     * @param piece  the chess piece to be added
     * @param row    the row of the chessboard
     * @param column the column of the chessboard
     * @since 1.0
     */
    @Override
    public void simulateAddPiece(ChessPiece piece, int row, int column) {
        pieces[row][column] = piece;
        piece.setLocation(row, column);
    }

    /**
     * <p>Removes a <code>ChessPiece</code> from the chess board at the given row and column.</p>
     * <p>Returns the <code>ChessPiece</code> that was just removed, if any.</p>
     *
     * @param row    the row of the chessboard
     * @param column the column of the chessboard
     * @return the chess piece that was just removed, <code>null</code> if there is no chess piece
     * @since 1.0
     */
    @Override
    public ChessPiece removePiece(int row, int column) {
        ChessPiece save = simulateRemovePiece(row, column);

        Runnable removePiece = () -> boardDisplay.displayEmptySquare(squares[row][column], row, column);

        removePiece.run();
        return save;
    }

    /**
     * <p>Removes a <code>ChessPiece</code> from the chess board at the given row and column, but does not
     * update the display./p>
     * <p>This is used to check for checks when doing a proposed move.</p>
     * <p>Returns the <code>ChessPiece</code> that was just removed, if any.</p>
     *
     * @param row    the row of the chessboard
     * @param column the column of the chessboard
     * @return the chess piece that was just removed, <code>null</code> if there is no chess piece
     * @since 1.0
     */
    @Override
    public ChessPiece simulateRemovePiece(int row, int column) {
        ChessPiece save = pieces[row][column];
        pieces[row][column] = null;
        return save;
    }

    /**
     * <p>Checks to see if a piece exists at the given row and column.</p>
     *
     * @param row    the row of the chessboard
     * @param column the column of the chessboard
     * @return <code>true</code> if the piece exists at the given row and column
     */
    @Override
    public boolean hasPiece(int row, int column) {
        return (pieces[row][column] != null);
    }

    /**
     * <p>Returns the <code>ChessPiece</code> of the chess board at the given row and column, if any.</p>
     *
     * @param row    the row of the chessboard
     * @param column the column of the chessboard
     * @return the chess piece at the given row and column, <code>null</code> if there is no chess piece
     */
    @Override
    public ChessPiece getPiece(int row, int column) {
        return pieces[row][column];
    }

    /**
     * <p>Generates a <code>ChessPosition</code> object for the chessboard position.</p>
     *
     * @return the <code>ChessPosition</code> for the chessboard
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
     * @param result the result of the chess game
     * @param side   the side of the winning player, if there was one
     * @since 1.0
     */
    @Override
    public void terminate(ChessResult result, ChessGame.Side side) {
        Platform.runLater(() -> {
            // Stores the popup window
            Alert resultDialog = new Alert(Alert.AlertType.NONE, "", ButtonType.OK);
            resultDialog.setTitle("Game Over");
            resultDialog.setHeaderText(null);
            resultDialog.setOnCloseRequest(e -> primaryStage.close());

            String resultText;

            switch (result) {
                case CHECKMATE:
                    resultText = ((side.equals(ChessGame.Side.NORTH)) ? "North" : (side.equals(ChessGame.Side.SOUTH)) ? "South" : (side.equals(ChessGame.Side.WEST)) ? "West" : "East") + " has won the game by checkmate!";
                    break;
                case STALEMATE:
                    if (side != null) {
                        resultText = ((side.equals(ChessGame.Side.NORTH)) ? "North" : (side.equals(ChessGame.Side.SOUTH)) ? "South" : (side.equals(ChessGame.Side.WEST)) ? "West" : "East") + " has won the game by stalemate!";
                    } else
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

            resultDialog.setContentText(resultText);
            resultDialog.showAndWait();
        });
    }

    /**
     * <p>Displays promotion window by letting user choose from <code>ChessPieces</code> that are <code>Promotable</code>.</p>
     * <p>This does nothing by default because some chessboards will not have to handle promotion.</p>
     *
     * @param piece the piece to be promoted
     * @since 1.0
     */
    @Override
    public void invokePromotion(ChessPiece piece) {
        // Runs code on Application Thread
        Platform.runLater(() -> {
            // Stores the promotion pieces used for JFrame
            ChessPiece[] promotionPieces = new ChessPiece[]{
                    new KnightPiece(piece.getSide(), piece.getChessBoard(), (piece.getSide().equals(piece.getChessBoard().getGameRules().getStartingSide()) ? ChessIcon.WHITE_KNIGHT : ChessIcon.BLACK_KNIGHT), -1, -1),
                    new BishopPiece(piece.getSide(), piece.getChessBoard(), (piece.getSide().equals(piece.getChessBoard().getGameRules().getStartingSide()) ? ChessIcon.WHITE_BISHOP : ChessIcon.BLACK_BISHOP), -1, -1),
                    new RookPiece(piece.getSide(), piece.getChessBoard(), (piece.getSide().equals(piece.getChessBoard().getGameRules().getStartingSide()) ? ChessIcon.WHITE_ROOK : ChessIcon.BLACK_ROOK), -1, -1),
                    new QueenPiece(piece.getSide(), piece.getChessBoard(), (piece.getSide().equals(piece.getChessBoard().getGameRules().getStartingSide()) ? ChessIcon.WHITE_QUEEN : ChessIcon.BLACK_QUEEN), -1, -1)
            };
            Button[] promotionSquares = new Button[promotionPieces.length];

            Stage promotionDialog = new Stage();
            promotionDialog.setTitle("Pawn Promotion");
            promotionDialog.setResizable(false);

            HBox layout = new HBox();

            for (int i = 0; i < promotionPieces.length; i++) {
                promotionSquares[i] = new Button();
                promotionSquares[i].setPrefSize(boardDisplay.getSquareSize(), boardDisplay.getSquareSize());

                // i needs to be final in order for us to use it in lambda expression
                int finalI = i;

                promotionSquares[i].setOnAction(new EventHandler<ActionEvent>() {
                    /**
                     * <p>Invoked when a specific event of the type for which this handler is
                     * registered happens.</p>
                     *
                     * @param event the event which occurred
                     */
                    @Override
                    public void handle(ActionEvent event) {
                        getGameRules().promote(piece, promotionPieces[finalI]);
                        promotionDialog.close();
                    }
                });

                // Displays the button
                boardDisplay.displayEmptySquare(promotionSquares[i], i, -1);
                boardDisplay.displayFilledSquare(promotionSquares[i], i, -1, promotionPieces[i]);
            }

            // Adds the promotion pieces to the layout
            layout.getChildren().addAll(promotionSquares);
            layout.setPrefSize(promotionPieces.length * boardDisplay.getSquareSize(), boardDisplay.getSquareSize());

            // Adds a method that sets the default promotion piece to queen if the user doesn't press the button
            promotionDialog.setOnCloseRequest((e) -> {
                if (getPiece(piece.getRow(), piece.getColumn()).equals(piece)) // Checks if the pawn hasn't been promoted yet
                    getGameRules().promote(piece, promotionPieces[3]); // Promotes to queen
            });

            promotionDialog.setScene(new Scene(layout));
            promotionDialog.sizeToScene();
            promotionDialog.initModality(Modality.APPLICATION_MODAL);
            promotionDialog.showAndWait();
        });
    }
    //endregion
}
