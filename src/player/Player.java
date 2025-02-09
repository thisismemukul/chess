package player;

import board.Board;
import enums.PieceColor;
import pieces.*;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private PieceColor pieceColor;
    private List<Piece> pieces;

    public Player(PieceColor pieceColor) {
        this.pieceColor = pieceColor;
        pieces = new ArrayList<>();
    }

    public void initializePieces(Board board) {
        //TODO: handle take input from user and choose white/black
        int pawnRow = (pieceColor == PieceColor.WHITE) ? 1 : 6;
        int mainRow = (pieceColor == PieceColor.WHITE) ? 0 : 7;

        for (int row = 0; row < 8; row++) {
            pieces.add(new Pawn(pieceColor));
            board.getSpot(pawnRow, row).occupySpot(pieces.get(pieces.size() - 1));
        }

        pieces.add(new Rook(pieceColor));
        pieces.add(new Knight(pieceColor));
        pieces.add(new Bishop(pieceColor));
        pieces.add(new Queen(pieceColor));
        pieces.add(new King(pieceColor));
        pieces.add(new Bishop(pieceColor));
        pieces.add(new Knight(pieceColor));
        pieces.add(new Rook(pieceColor));

        for (int row = 0; row < 8; row++) {
            board.getSpot(mainRow, row).occupySpot(pieces.get(pieces.size() - 8 + row));
        }
    }

}
