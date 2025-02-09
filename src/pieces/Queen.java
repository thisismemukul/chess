package pieces;

import board.Board;
import enums.PieceColor;
import enums.PieceType;

public class Queen extends Piece {
    public Queen(PieceColor pieceColor) {
        super(PieceType.QUEEN, pieceColor);
    }

    @Override
    public boolean isValidMove(Board board, int fromRow, int fromCol, int toRow, int toCol) {
        // Queen can move like both a Rook and a Bishop
        if (fromRow == toRow || fromCol == toCol || Math.abs(fromRow - toRow) == Math.abs(fromCol - toCol)) {
            // Check if the path is clear
            return board.isPathClear(fromRow, fromCol, toRow, toCol);
        }
        return false;
    }
}
