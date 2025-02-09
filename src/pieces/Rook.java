package pieces;

import board.Board;
import enums.PieceColor;
import enums.PieceType;

public class Rook extends Piece {
    public Rook(PieceColor pieceColor) {
        super(PieceType.ROOK, pieceColor);
    }

    @Override
    public boolean isValidMove(Board board, int fromRow, int fromCol, int toRow, int toCol) {
        // Ensure the Rook moves in a straight line
        if (fromRow == toRow || fromCol == toCol) {
            // Check if the path is clear
            return board.isPathClear(fromRow, fromCol, toRow, toCol);
        }
        return false;
    }

}
