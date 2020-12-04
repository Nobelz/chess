/**
 * <p>Represents the results of a chess game.</p>
 *
 * @author Nobel Zhou (nxz157)
 * @version 1.0, 12/2/2020
 */
public enum ChessResult {
    CHECKMATE,
    STALEMATE,
    INSUFFICIENT_MATERIAL,
    THREEFOLD_REPETITION,
    FIFTY_MOVE_RULE,
    DRAW_BY_AGREEMENT,
    RESIGNATION,
    TIMEOUT,
    TIMEOUT_INSUFFICIENT_MATERIAL
}
