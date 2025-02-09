package pieces;

import board.Board;
import enums.PieceColor;
import enums.PieceType;

public class Bishop extends Piece {
    public Bishop(PieceColor pieceColor) {
        super(PieceType.BISHOP, pieceColor);
    }

    @Override
    public boolean isValidMove(Board board, int fromRow, int fromCol, int toRow, int toCol) {
        // Ensure the Bishop moves diagonally
        if (Math.abs(fromRow - toRow) == Math.abs(fromCol - toCol)) {
            // Check if the path is clear
            return board.isPathClear(fromRow, fromCol, toRow, toCol);
        }
        return false;
    }

}
