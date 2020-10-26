/**
 * Represents a chess piece - knight, bishop, queen, king, pawn, or rook.
 * Dictates properties of the chess piece as well as how it moves.
 * 
 * @author Nobel Zhou
 * @version 1.0, 10/30/20
 */
public abstract class ChessPiece {
    
    /* FIELDS */
    //Stores the piece's affiliation to which side
    private ChessGame.Side side;
    //Stores the label of the piece
    private String label;
    //Stores the graphics icon or image of the piece and how it should be drawn onto the chess board
    private Object icon;
    //Stores the row of the piece
    private int row;
    //Stores the column of the piece
    private int column;
    //Stores the chess board the piece is on
    private ChessBoard chessBoard;
    //Stores the number of moves the chess piece has done
    private int moves;
    
    /* CONSTRUCTORS */
    /**
     * Initializes a chess piece based on the given side, label, icon, chess board, and location.
     * @param side          The chess piece's side
     * @param label         The chess piece's label name
     * @param icon          The chess piece's graphic icon
     * @param chessBoard    The chess board the chess piece is on
     * @param row           The chess piece's starting row
     * @param column        The chess piece's starting column
     */
    public ChessPiece(ChessGame.Side side, String label, Object icon, ChessBoard chessBoard, int row, int column) {
        this.side = side;
        this.label = label;
        this.icon = icon;
        this.chessBoard = chessBoard;
        this.row = row;
        this.column = column;
        moves = 0;
    }
    
    /* METHODS */
    /**
     * Returns an int representing the amount of moves the piece has done.
     * @return  The number of moves
     * @since 1.0
     */
    public int getMoves() {
        return moves;
    }
    
    /**
     * Sets the amount of moves of the chess piece.
     * @param   The chess piece's new move count
     * @since 1.0
     */
    private void setMoves(int moves) {
        this.moves = moves;
    }
    
    /**
     * Returns a ChessGame.Side representing the side of the piece
     * @return   The piece's side
     * @since 1.0
     */
    public ChessGame.Side getSide() {
        return side;
    }
    
    /**
     * Returns a String representing the label of the piece
     * @return  The piece's label
     * @since 1.0
     */
    public String getLabel() {
        return label;
    }
    
    /**
     * Returns an Object representing the graphics icon of the piece
     * @return  The piece's graphic icon
     * @since 1.0
     */
    public Object getIcon() {
        return icon;
    }
    
    /**
     * Sets the location of the piece.
     * Sets the row and column of the piece.
     * @param row       The piece's row number
     * @param column    The piece's column number
     * @since 1.0
     */
    public void setLocation(int row, int column) {
        this.row = row;
        this.column = column;
    }
    
    /**
     * Returns a boolean representing if the piece's proposed move is legal or not.
     * @param toRow     The piece's destination row
     * @param toColumn  The piece's destination column
     * @return          If the piece's proposed move is legal or not
     * @since 1.0
     */
    public boolean isLegalMove(int toRow, int toColumn) {
        //Checks if it's either a capture move or a non capture move
        return isLegalCaptureMove(toRow, toColumn) || isLegalNonCaptureMove(toRow, toColumn);
    }
    
    /**
     * Returns a ChessBoard object representing the chessboard the piece is on.
     * @return  The chessboard the piece is on
     * @since 1.0
     */
    public ChessBoard getChessBoard() {
        return chessBoard;
    }
    
    /**
     * Returns an int representing the current row of the piece.
     * @return  The piece's current row
     * @since 1.0
     */
    public int getRow() {
        return row;
    }
    
    /**
     * Returns an int representing the current column of the piece.
     * @return  The piece's current column
     * @since 1.0
     */
    public int getColumn() {
        return column;
    }
    
    /**
     * Returns a boolean representing if the proposed move is legal, assuming that it is unoccupied.
     * @param row       The piece's destination row
     * @param column    The piece's destination column
     * @return          If the piece's proposed move is legal or not, assuming it is unoccupied
     * @since 1.0
     */
    public abstract boolean isLegalNonCaptureMove(int row, int column);
    
    /**
     * Returns a boolean representing if the proposed move is legal, assuming that it is occupied.
     * @param row       The piece's destination row
     * @param column    The piece's destination column
     * @return          If the piece's proposed move is legal or not, assuming it is occupied
     * @since 1.0
     */
    public abstract boolean isLegalCaptureMove(int row, int column);
    
    /**
     * Handles any post-move processes, if any, once the move is completed.
     * Increments number of moves so castling and 2 space pawn moves are disallowed.
     * @since 1.0
     */
    public void moveDone() {
        //Adds 1 to the amount of moves the piece has done
        setMoves(getMoves() + 1);
        
        //Checks for each pawn piece on the chess board
        for (int i = 0; i < getChessBoard().numRows(); i++) {
            for (int j = 0; j < getChessBoard().numColumns(); j++) {
                //Checks if a pawn exists at a square
                if (getChessBoard().hasPiece(i, j) && getChessBoard().getPiece(i, j) instanceof PawnPiece) {
                    //Stores the pawn at the square
                    PawnPiece pawn = (PawnPiece) getChessBoard().getPiece(i, j);
                    //Checks if any can be captured by en passant; if it can, a move has just passed so it cannot be captured by en passant anymore
                    if (pawn.getCanEnPassant() == true)
                        pawn.setCanEnPassant(false);
                }
            }
        }
    }
}
