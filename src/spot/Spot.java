package spot;

import pieces.Piece;

public class Spot {
    int x, y;
    private Piece piece;

    public Spot(int y, int x) {
        this.piece = null;
        this.y = y;
        this.x = x;
    }

    public Piece getPiece() {
        return piece;
    }

    public boolean isOccupied() {
        return piece != null;
    }

    public void releaseSpot(){
        System.out.printf("Releasing spot (%d, %d)...\n", x, y);
        this.piece =null;
    }
    public Piece occupySpot(Piece piece){
        Piece original = this.piece;
        this.piece = piece;
        return original;
    }

}
