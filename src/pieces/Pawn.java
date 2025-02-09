package pieces;

import board.Board;
import enums.PieceColor;
import enums.PieceType;

public class Pawn extends Piece {
    public Pawn(PieceColor pieceColor) {
        super(PieceType.PAWN, pieceColor);
    }

    @Override
    public boolean isValidMove(Board board, int fromRow, int fromCol, int toRow, int toCol) {
        int direction = (getPieceColor() == PieceColor.WHITE) ? 1 : -1;
        int startRow = (getPieceColor() == PieceColor.WHITE) ? 1 : 6;

        // Forward move
        if (fromCol == toCol) {
            //Single step
            if (toRow == fromRow + direction && !board.getSpot(toRow, toCol).isOccupied()) {
                return true;
            }
            // Double step from start row
            if (fromRow == startRow && toRow == fromRow + 2 * direction
                    && !board.getSpot(toRow, toCol).isOccupied()
                    && !board.getSpot(fromRow + direction, fromCol).isOccupied()) {
                return true;
            }
        }
        // Diagonal capture
        else if (Math.abs(toCol - fromCol) == 1 && toRow == fromRow + direction) {
            if (board.getSpot(toRow, toCol).isOccupied()
                    && board.getSpot(toRow, toCol).getPiece().getPieceColor() != this.getPieceColor()) {
                return true;
            }
        }
        return false;
    }

//    public boolean isValidMove(Board board, int fromX, int fromY, int toX, int toY) {
//        int direction = (getPieceColor() == PieceColor.WHITE) ? 1 : -1; // White pawns move "down" (increment Y)
//        int startRow = (getPieceColor() == PieceColor.WHITE) ? 1 : 6;
//
//        if (fromY == toY) {
//            // Moving forward
//            System.err.println("fromX: " + fromX);
//            System.err.println("toX: " + toX);
//            System.err.println("direction: " + direction);
//            System.err.println("startRow: " + startRow);
//            if (toX - fromX == direction && !board.getSpot(toX, toY).isOccupied()) {
//                return true;
//            }
//            //This handles start row
//            if (fromX == startRow && toX - fromX == 2 * direction && !board.getSpot(toX, toY).isOccupied()) {
//                return true;
//            }
//        } else if (Math.abs(fromY - toY) == 1 && toX - fromX == direction) {
//            // Capturing diagonally
//            if (board.getSpot(toX, toY).isOccupied() && board.getSpot(toX, toY).getPiece().color != this.color) {
//                return true;
//            }
//        }
//        return false;
//    }
}
