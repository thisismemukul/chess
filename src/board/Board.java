package board;

import command.Command;
import enums.PieceColor;
import enums.PieceType;
import pieces.*;
import player.Player;
import spot.Spot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
    private Spot[][] spots;
    private boolean win;
    private List<Piece> capturedAndKilledPieces; // To keep track of killed pieces

    public Board() {
        spots = new Spot[8][8];
        capturedAndKilledPieces = new ArrayList<>();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                spots[row][col] = new Spot(row, col);
            }
        }
    }

    public Spot getSpot(int row, int col) {
        return spots[row][col];
    }

    public boolean executeMove(Command command) {
        Spot fromSpot = getSpot(command.curRow, command.curCol);
        Spot toSpot = getSpot(command.desRow, command.desCol);
        Piece piece = fromSpot.getPiece();

        if (piece == null ||
                !piece.isValidMove(
                        this,
                        command.curRow,
                        command.curCol,
                        command.desRow,
                        command.desCol)) {
            System.out.println("Invalid move.");
            return false;
        }

        if (toSpot.isOccupied() && toSpot.getPiece().getPieceColor() == piece.getPieceColor()) {
            System.out.println("Cannot move to a spot occupied by your own piece.");
            return false;
        }

        Piece capturedPiece = toSpot.occupySpot(piece);
        if (capturedPiece != null) {
            capturedAndKilledPieces.add(capturedPiece);
            capturedPiece.setKilled(true);
            if (capturedPiece.getPieceType() == PieceType.KING) {
                System.out.printf("Captured piece: %s of color %s\n",
                        capturedPiece.getPieceType(), capturedPiece.getPieceColor());
                setWin(true);
            }
        }

        fromSpot.releaseSpot();
        return true;
    }

    public void promotePawn(Spot spot, PieceColor color, PieceType chosenType) {
        Piece newPiece;
        switch (chosenType) {
            case ROOK:
                newPiece = new Rook(color);
                break;
            case KNIGHT:
                newPiece = new Knight(color);
                break;
            case BISHOP:
                newPiece = new Bishop(color);
                break;
            default:
                newPiece = new Queen(color); // Default is Queen
                break;
        }
        spot.occupySpot(newPiece);
    }


    //TODO: init player
    public void initialize(Player p1, Player p2) {
        p1.initializePieces(this);
        p2.initializePieces(this);
    }

    // Add this method to check if the path is clear between two points (no jumping allowed except for knights)
    public boolean isPathClear(int fromRow, int fromCol, int toRow, int toCol) {
        int dx = Integer.compare(toRow, fromRow);  // Direction of movement in X
        int dy = Integer.compare(toCol, fromCol);  // Direction of movement in Y

        // Start checking from the next spot in the direction of the move
        int x = fromRow + dx;
        int y = fromCol + dy;

        // Traverse the path to the destination, checking if spots are occupied
        while (x != toRow || y != toCol) {
            if (getSpot(x, y).isOccupied()) {
                return false;  // Path is blocked
            }
            x += dx;
            y += dy;
        }
        return true;  // Path is clear
    }

    public List<Piece> getCapturedPieces() {
        return capturedAndKilledPieces;
    }

    public List<Piece> getCapturedAndKilledPiecesByColor(PieceColor color) {
        List<Piece> filteredPieces = new ArrayList<>();
        for (Piece piece : capturedAndKilledPieces) {
            if (piece.getPieceColor() == color) {
                filteredPieces.add(piece);
            }
        }
        return filteredPieces;
    }

    public Map<PieceColor, List<Piece>> getCapturedAndKilledPiecesGroupedByColor() {
        Map<PieceColor, List<Piece>> groupedCapturedAndKilledPieces = new HashMap<>();
        groupedCapturedAndKilledPieces.put(PieceColor.WHITE, new ArrayList<>());
        groupedCapturedAndKilledPieces.put(PieceColor.BLACK, new ArrayList<>());

        for (Piece piece : capturedAndKilledPieces) {
            groupedCapturedAndKilledPieces.get(piece.getPieceColor()).add(piece);
        }
        return groupedCapturedAndKilledPieces;
    }

    public void displayCapturedPieces() {
        Map<PieceColor, List<Piece>> groupedCapturedAndKilledPieces = getCapturedAndKilledPiecesGroupedByColor();

        System.out.println("White captured pieces:");
        for (Piece piece : groupedCapturedAndKilledPieces.get(PieceColor.WHITE)) {
            System.out.printf("%s ", piece.getPieceType());
        }
        System.out.println();

        System.out.println("Black captured pieces:");
        for (Piece piece : groupedCapturedAndKilledPieces.get(PieceColor.BLACK)) {
            System.out.printf("%s ", piece.getPieceType());
        }
        System.out.println();
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public boolean isWin() {
        return win;
    }
}
