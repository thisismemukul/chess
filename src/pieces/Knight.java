package pieces;

import board.Board;
import enums.PieceColor;
import enums.PieceType;

public class Knight extends Piece {
    public Knight(PieceColor pieceColor) {
        super(PieceType.KNIGHT, pieceColor);
    }

    @Override
    public boolean isValidMove(Board board, int fromRow, int fromCol, int toRow, int toCol) {
        int dRow = Math.abs(fromRow - toRow);
        int dCol = Math.abs(fromCol - toCol);
        return dRow * dCol == 2;
    }
}
