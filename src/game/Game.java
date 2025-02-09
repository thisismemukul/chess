package game;

import board.Board;
import command.Command;
import enums.PieceColor;
import player.Player;
import spot.Spot;

import java.util.Stack;

public class Game {
    private Board board;
    private Player playerOne;
    private Player playerTwo;
    private boolean whiteTurn;
    private Stack<Command> moveHistory;
    private Stack<Command> redoStack;


    public Game() {
        board = new Board();
        playerOne = new Player(PieceColor.WHITE);
        playerTwo = new Player(PieceColor.BLACK);
        whiteTurn = true;
        moveHistory = new Stack<>();
        redoStack = new Stack<>();
    }

    public void startGame() {
        board.initialize(playerOne, playerTwo);
    }

    public Board getBoard() {
        return board;
    }

    public boolean isWhiteTurn() {
        return whiteTurn;
    }

    public void toggleTurn() {
        whiteTurn = !whiteTurn;
    }

    public boolean executeMove(Command command) {
        if (board.executeMove(command)) {
            moveHistory.push(command);  // Add move to history
            redoStack.clear();          // Clear redo stack on new move
            toggleTurn();
            return true;
        }
        return false;
    }

    // Undo the last move
    public void undoMove() {
        if (!moveHistory.isEmpty()) {
            Command lastMove = moveHistory.pop();
            redoStack.push(lastMove);  // Add to redo stack
            Spot fromSpot = board.getSpot(lastMove.curRow, lastMove.curCol);
            Spot toSpot = board.getSpot(lastMove.desRow, lastMove.desCol);
            fromSpot.occupySpot(toSpot.getPiece());
            toSpot.releaseSpot();
//            redoStack.push(lastMove);
            toggleTurn();
        }
    }

    // Redo the move
    public void redoMove() {
        if (!redoStack.isEmpty()) {
            Command moveToRedo = redoStack.pop();
            executeMove(moveToRedo);
//            moveHistory.push(moveToRedo);
        }
    }

}
