package command;

public class Command {
    public int curRow, curCol, desRow, desCol;

    public Command(int curRow, int curCol, int desRow, int desCol) {
        this.curRow = curRow;
        this.curCol = curCol;
        this.desRow = desRow;
        this.desCol = desCol;
    }
}
