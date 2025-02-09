package pieces;

import board.Board;
import enums.PieceColor;
import enums.PieceType;

public class King extends Piece {

    public King(PieceColor pieceColor) {
        super(PieceType.KING, pieceColor);
    }

    @Override
    public boolean isValidMove(Board board, int fromRow, int fromCol, int toRow, int toCol) {
        int dRow = Math.abs(fromRow - toRow);
        int dCol = Math.abs(fromCol - toCol);
        return dRow <= 1 && dCol <= 1;
    }
}
