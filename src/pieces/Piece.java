package pieces;

import board.Board;
import enums.PieceColor;
import enums.PieceType;

public abstract class Piece {
    private PieceType pieceType;
    private PieceColor pieceColor;
    private boolean isAvailable;

    private boolean killed = false;
//    private boolean white = false;

    public Piece(PieceType pieceType, PieceColor pieceColor) {
        this.pieceType = pieceType;
        this.pieceColor = pieceColor;
        this.isAvailable = true;
    }

//    public abstract boolean isValidMove(int fromX, int fromY, int toX, int toY);
    public boolean isValidMove(Board board,int fromRow, int fromCol, int toRow, int toCol) {
        return false;
    }

    public PieceColor getPieceColor() {
        return pieceColor;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public boolean isKilled() {
        return killed;
    }

    public void setKilled(boolean killed) {
        this.killed = killed;
    }
}
