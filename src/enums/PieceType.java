package enums;

public enum PieceType {
    NONE(" ", " "),
    PAWN("\u2659", "\u265F"),
    KNIGHT("\u2658", "\u265E"),
    BISHOP("\u2657", "\u265D"),
    ROOK("\u2656", "\u265C"),
    QUEEN("\u2655", "\u265B"),
    KING("\u2654", "\u265A");

    private final String whiteSymbol;
    private final String blackSymbol;

    PieceType(String whiteSymbol, String blackSymbol) {
        this.whiteSymbol = whiteSymbol;
        this.blackSymbol = blackSymbol;
    }

    public String getSymbol(boolean isWhite) {
        return isWhite ? whiteSymbol : blackSymbol;
    }
}
