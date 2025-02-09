package gui;

import board.Board;
import command.Command;
import enums.PieceColor;
import enums.PieceType;
import game.Game;
import pieces.Pawn;
import pieces.Piece;
import spot.Spot;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ChessGUI {
    private JFrame frame;
    private JPanel boardPanel;
    private JButton[][] squares;
    private Game game;
    private int selectedRow = -1, selectedCol = -1;
    private JButton undoButton, redoButton;
    private JLabel turnLabel;
    private JPanel whitePawnsPanel;
    private JPanel whiteMainPiecesPanel;
    private JPanel blackPawnsPanel;
    private JPanel blackMainPiecesPanel;


    public ChessGUI() {
        game = new Game();
        initializeGUI();
        game.startGame();
        updateBoard();
    }

    private void initializeGUI() {
        frame = new JFrame("Chess Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(850, 800);  // Adjusted size for compact control panel

        // Initialize the board
        boardPanel = new JPanel(new GridLayout(8, 8));
        squares = new JButton[8][8];
        initializeBoard();

        // Create a panel for Undo and Redo buttons (top of the east side)
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 5, 5));  // 2 rows with small spacing
        undoButton = new JButton("Undo");
        redoButton = new JButton("Redo");
        buttonPanel.add(undoButton);
        buttonPanel.add(redoButton);

        // Add action listeners for Undo and Redo buttons
        undoButton.addActionListener(e -> undoMove());
        redoButton.addActionListener(e -> redoMove());

        // Create a small space for the turn label at the bottom of the east side
        JPanel turnPanel = new JPanel(new BorderLayout());
        turnLabel = new JLabel(game.isWhiteTurn() ? "White's Turn" : "Black's Turn", SwingConstants.CENTER);
        turnLabel.setFont(new Font("Arial", Font.BOLD, 16));  // Adjusted font for compact space
        turnPanel.add(turnLabel, BorderLayout.SOUTH);  // Position label at the bottom

        // Combine button and turn label panels
        JPanel eastPanel = new JPanel(new BorderLayout());
        eastPanel.add(buttonPanel, BorderLayout.NORTH);  // Buttons at the top
        eastPanel.add(turnPanel, BorderLayout.SOUTH);    // Turn label at the bottom

        // Add the control panel to the east (right side of the board)
        frame.add(eastPanel, BorderLayout.EAST);

        // Panels for captured pieces: 2 rows for White and 2 rows for Black
        whitePawnsPanel = new JPanel(new GridLayout(1, 8));       // Row for Black's captured pawns
        whiteMainPiecesPanel = new JPanel(new GridLayout(1, 8));  // Row for Black's captured main pieces
        blackPawnsPanel = new JPanel(new GridLayout(1, 8));       // Row for White's captured pawns
        blackMainPiecesPanel = new JPanel(new GridLayout(1, 8));  // Row for White's captured main pieces

        // White's captured pieces (south) - Black's captured pieces (north)
        JPanel whiteCapturedPanel = new JPanel(new GridLayout(2, 1));
        whiteCapturedPanel.add(whitePawnsPanel);
        whiteCapturedPanel.add(whiteMainPiecesPanel);
        frame.add(whiteCapturedPanel, BorderLayout.SOUTH);

        JPanel blackCapturedPanel = new JPanel(new GridLayout(2, 1));
        blackCapturedPanel.add(blackPawnsPanel);
        blackCapturedPanel.add(blackMainPiecesPanel);
        frame.add(blackCapturedPanel, BorderLayout.NORTH);

        // Add the chess board to the center
        frame.add(boardPanel, BorderLayout.CENTER);

        // Make the frame visible
        frame.setVisible(true);
    }

    private void initializeGUIOld() {
        frame = new JFrame("Chess Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(850, 800);

        boardPanel = new JPanel(new GridLayout(8, 8));
        squares = new JButton[8][8];
        initializeBoard();

        JPanel controlPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        undoButton = new JButton("Undo");
        redoButton = new JButton("Redo");
        turnLabel = new JLabel(game.isWhiteTurn() ? "White's Turn" : "Black's Turn", SwingConstants.CENTER);
        turnLabel.setFont(new Font("Arial", Font.BOLD, 18));

        controlPanel.add(undoButton);
        controlPanel.add(redoButton);
        controlPanel.add(turnLabel);

        frame.add(controlPanel, BorderLayout.EAST);

        undoButton.addActionListener(e -> undoMove());
        redoButton.addActionListener(e -> redoMove());

        whitePawnsPanel = new JPanel(new GridLayout(1, 8));
        whiteMainPiecesPanel = new JPanel(new GridLayout(1, 8));
        blackPawnsPanel = new JPanel(new GridLayout(1, 8));
        blackMainPiecesPanel = new JPanel(new GridLayout(1, 8));


        JPanel whiteCapturedPanel = new JPanel(new GridLayout(2, 1));
        whiteCapturedPanel.add(whitePawnsPanel);
        whiteCapturedPanel.add(whiteMainPiecesPanel);
        frame.add(whiteCapturedPanel, BorderLayout.SOUTH);

        JPanel blackCapturedPanel = new JPanel(new GridLayout(2, 1));
        blackCapturedPanel.add(blackPawnsPanel);
        blackCapturedPanel.add(blackMainPiecesPanel);
        frame.add(blackCapturedPanel, BorderLayout.NORTH);

        frame.add(boardPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }


    private void initializeBoard() {
        boolean isWhite = true;
        for (int col = 7; col >= 0; col--) {
            for (int row = 0; row <= 7; row++) {
                JButton square = new JButton();
                square.setBackground(isWhite ? Color.WHITE : Color.GRAY);
                square.setOpaque(true);
                square.setBorderPainted(false);
                square.setFont(new Font("Monospaced", Font.BOLD, 28));
                square.setHorizontalAlignment(SwingConstants.CENTER);
                int finalRow = row;
                int finalCol = col;
                square.addActionListener(e -> handleSquareClick(finalRow, finalCol));
                squares[row][col] = square;
                boardPanel.add(square);
                isWhite = !isWhite;
            }
            isWhite = !isWhite;
        }
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] availableFonts = ge.getAvailableFontFamilyNames();
        for (String font : availableFonts) {
            System.out.print(font + ",");
        }

    }


    private void handleSquareClick(int guiRow, int guiCol) {
        int boardY = 7 - guiRow;

        System.out.printf("Clicked square (Board: %d, %d)\n", guiCol, boardY);

        Board board = game.getBoard();

        if (selectedRow == -1 && selectedCol == -1) {
            Spot spot = board.getSpot(guiCol, boardY);
            if (spot.isOccupied() && spot.getPiece().getPieceColor() == (game.isWhiteTurn() ? PieceColor.WHITE : PieceColor.BLACK)) {
                selectedRow = guiCol;
                selectedCol = boardY;
                highlightValidMoves(guiCol, boardY);
            }
        } else {
            Command command = new Command(selectedRow, selectedCol, guiCol, boardY);
//            if (board.executeMove(command)) {
            if (game.executeMove(command)) {
//                    game.toggleTurn();

                //TODO: let user choose which piece he wanted to replace in Pawn promotion any one from Rook,Knight,Bishop,Queen
                // Pawn promotion
                Spot toSpot = board.getSpot(command.desRow, command.desCol);
                Piece piece = toSpot.getPiece();
                if (piece instanceof Pawn && (command.desRow == 0 || command.desRow == 7)) {
                    PieceType chosenType = getUserPromotionChoice(piece.getPieceColor());
                    board.promotePawn(toSpot, piece.getPieceColor(), chosenType);
                }

                updateBoard();
                if (board.isWin()) {
                    JOptionPane.showMessageDialog(frame, (game.isWhiteTurn() ? "Black" : "White") + " wins!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose();
                }
            }
            clearHighlights();
            selectedRow = -1;
            selectedCol = -1;
        }
    }

    private PieceType getUserPromotionChoice(PieceColor color) {
        String[] options = {
                PieceType.QUEEN.getSymbol(color == PieceColor.WHITE),
                PieceType.ROOK.getSymbol(color == PieceColor.WHITE),
                PieceType.BISHOP.getSymbol(color == PieceColor.WHITE),
                PieceType.KNIGHT.getSymbol(color == PieceColor.WHITE)
        };

        int choice = JOptionPane.showOptionDialog(
                frame,
                "Choose the piece for pawn promotion:",
                color == PieceColor.WHITE ? "White Pawn Promotion" : "Black Pawn Promotion",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        switch (choice) {
            case 1:
                return PieceType.ROOK;
            case 2:
                return PieceType.BISHOP;
            case 3:
                return PieceType.KNIGHT;
            default:
                return PieceType.QUEEN;
        }
    }

    private ImageIcon getPieceIcon(Piece piece) {
        String iconName = (piece.getPieceColor() == PieceColor.WHITE ? "w" : "b") + piece.getPieceType().toString().toLowerCase() + ".png";
        java.net.URL imgURL = getClass().getResource(iconName); // Assuming your icons are stored in a folder named "icons"
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + iconName);
            return null;
        }
    }


    private void updateBoard() {
        Board board = game.getBoard();
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                // Map Board's (x, y) to GUI's GridLayout
                int guiRow = 7 - y;
                Spot spot = board.getSpot(x, y);
                JButton square = squares[guiRow][x];
                if (spot.isOccupied()) {
                    Piece piece = spot.getPiece();
                    String pieceSymbol = getPieceSymbol(piece);
                    square.setText(pieceSymbol);
//                    square.setIcon(getPieceIcon(piece));  // Use icon instead of text for pieces
                } else {
                    square.setText("");
                }
            }
        }

        turnLabel.setText(game.isWhiteTurn() ? "White's Turn" : "Black's Turn");

        // Display captured pieces
        displayCapturedPieces(board);

        List<Piece> capturedAndKilledPiecesByColor = board.getCapturedAndKilledPiecesByColor(PieceColor.WHITE);

        int blackCapturedCount = board.getCapturedAndKilledPiecesByColor(PieceColor.BLACK).size();

//        System.out.printf("White has lost %d pieces.\n", capturedAndKilledPiecesByColor);
        System.out.printf("Black has lost %d pieces.\n", blackCapturedCount);

       board.displayCapturedPieces();
    }

    private void displayCapturedPieces(Board board) {
        whitePawnsPanel.removeAll();
        whiteMainPiecesPanel.removeAll();
        blackPawnsPanel.removeAll();
        blackMainPiecesPanel.removeAll();

        // Display Black's captured pieces on White's side (South)
        for (Piece piece : board.getCapturedAndKilledPiecesByColor(PieceColor.BLACK)) {
            if (piece.getPieceType() == PieceType.PAWN) {
                whitePawnsPanel.add(new JLabel(piece.getPieceType().getSymbol(false)));
//                whitePawnsPanel.add(new JLabel(getPieceIcon(piece))); // Add pawns to the pawns panel
            } else {
                whiteMainPiecesPanel.add(new JLabel(piece.getPieceType().getSymbol(false)));
//                whiteMainPiecesPanel.add(new JLabel(getPieceIcon(piece))); // Add main pieces to the main pieces panel
            }
        }

        // Display White's captured pieces on Black's side (North)
        for (Piece piece : board.getCapturedAndKilledPiecesByColor(PieceColor.WHITE)) {
            if (piece.getPieceType() == PieceType.PAWN) {
                blackPawnsPanel.add(new JLabel(piece.getPieceType().getSymbol(true)));
//                blackPawnsPanel.add(new JLabel(getPieceIcon(piece))); // Add pawns to the pawns panel
            } else {
                blackMainPiecesPanel.add(new JLabel(piece.getPieceType().getSymbol(true)));
//                blackMainPiecesPanel.add(new JLabel(getPieceIcon(piece))); // Add main pieces to the main pieces panel
            }
        }

        whitePawnsPanel.revalidate();
        whitePawnsPanel.repaint();
        whiteMainPiecesPanel.revalidate();
        whiteMainPiecesPanel.repaint();
        blackPawnsPanel.revalidate();
        blackPawnsPanel.repaint();
        blackMainPiecesPanel.revalidate();
        blackMainPiecesPanel.repaint();
    }

    private void undoMove() {
        game.undoMove();
        updateBoard();
    }

    private void redoMove() {
        game.redoMove();
        updateBoard();
    }


    private String getPieceSymbol(Piece piece) {
        return piece.getPieceType().getSymbol(piece.getPieceColor() == PieceColor.WHITE);
    }


    private void highlightValidMoves(int boardX, int boardY) {
        Board board = game.getBoard();
        Spot spot = board.getSpot(boardX, boardY);
        Piece piece = spot.getPiece();

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (piece.isValidMove(board, boardX, boardY, x, y)) {
                    Spot targetSpot = board.getSpot(x, y);
                    if (!targetSpot.isOccupied() || targetSpot.getPiece().getPieceColor() != piece.getPieceColor()) {
                        int guiRow = 7 - y;
                        int guiCol = x;
                        squares[guiRow][guiCol].setBackground(new Color(255, 255, 204));
                    }
                }
            }
        }
    }

    private void clearHighlights() {
        boolean isWhite = true;
        for (int row = 7; row >= 0; row--) {
            for (int col = 0; col < 8; col++) {
                squares[row][col].setBackground(isWhite ? Color.WHITE : Color.GRAY);
                isWhite = !isWhite;
            }
            isWhite = !isWhite;
        }
    }
}
