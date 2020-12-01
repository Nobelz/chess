import javax.swing.JButton;

/** Rules for how we want the chess board to display
  * 
  * @author Harold Connamacher
  */
public interface ChessBoardDisplay {
  
  /** 
   * The initial size of a square on the chess board, initially 1/40 the width of the screen 
   * @return the size of a square 
   */
  public default int getSquareSize() {
    return java.awt.Toolkit.getDefaultToolkit().getScreenSize().width / 40;
  }
  
  /**
   * Display a square that has no piece on it.
   * @param button the button that is used for the chessboard square
   * @param row    the row of this square on the board
   * @param column the column of this square on the board
   */
  public void displayEmptySquare(JButton button, int row, int column);
  
  /**
   * Display a square that has a piece on it.
   * @param button the button that is used for the chessboard square
   * @param row    the row of this square on the board
   * @param column the column of this square on the board
   * @param piece  the piece that is on this square
   */
  public void displayFilledSquare(JButton button, int row, int column, ChessPiece piece);
  
  /**
   * Highlight a square of the board.
   * @param highlight  do we want the highlight on (true) or off (false)?
   * @param button     the button that is used for the chessboard square
   * @param row        the row of this square on the board
   * @param column     the column of this square on the board
   * @param piece      the piece (if any) that is on this square
   */
  public void highlightSquare(boolean highlight, JButton button, int row, int column, ChessPiece piece);
}