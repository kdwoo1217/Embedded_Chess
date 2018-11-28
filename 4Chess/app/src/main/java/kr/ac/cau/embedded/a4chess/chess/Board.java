package kr.ac.cau.embedded.a4chess.chess;

import kr.ac.cau.embedded.a4chess.chess.pieces.DownPawn;
import kr.ac.cau.embedded.a4chess.chess.pieces.LeftPawn;
import kr.ac.cau.embedded.a4chess.chess.pieces.Pawn;
import kr.ac.cau.embedded.a4chess.chess.pieces.Piece;
import kr.ac.cau.embedded.a4chess.chess.pieces.RightPawn;

public class Board {
    private static Piece[][] BoardState;
    private static final int boardSize = 14;

    private Board() { }

    public static int getBoardSize() {
        return boardSize;
    }
    public static Piece getPiece(Coordinate coordinate) {
        return BoardState[coordinate.x][coordinate.y];
    }

    public static int getRotation() {
//        if (Game.match.isLocal) return 0;
//        for (int i = 0; i < 4; i++) {
//            if (Game.players[i].id.equals(Game.myPlayerId))
//                return Game.players.length > 2 ? i : i * 2;
//        }
        return 0;
    }

    public static void newGame(final Player[] players) {
        BoardState = new Piece[boardSize][boardSize];

        // setup player 1 (bottom)
        setupPlayerTopBottom(3, 1, 0, players[0].id);

        // setup player 2 (right)
        setupPlayerLeftRight(boardSize - 2, boardSize - 1, players[1].id);

        // setup player 3 (top)
        setupPlayerTopBottom(3, boardSize - 2, boardSize - 1, players[2].id);

        // setup player 4 (left)
        setupPlayerLeftRight(1, 0, players[3].id);
    }

    private static void setupPlayerTopBottom(int xStart, int yStart, int yEnd, final String playerId) {
        for (int x = xStart; x < xStart + 8; x++) {
            BoardState[x][yStart] = yStart == boardSize - 2 ?
                    new DownPawn(new Coordinate(x, yStart), playerId) :
                    new Pawn(new Coordinate(x, yStart), playerId);
        }
//        BoardState[xStart][yEnd] = new Rook(new Coordinate(xStart, yEnd), playerId);
//        BoardState[xStart + 1][yEnd] = new Knight(new Coordinate(xStart + 1, yEnd), playerId);
//        BoardState[xStart + 2][yEnd] = new Bishop(new Coordinate(xStart + 2, yEnd), playerId);
//        BoardState[xStart + 3][yEnd] = new Queen(new Coordinate(xStart + 3, yEnd), playerId);
//        BoardState[xStart + 4][yEnd] = new King(new Coordinate(xStart + 4, yEnd), playerId);
//        BoardState[xStart + 5][yEnd] = new Bishop(new Coordinate(xStart + 5, yEnd), playerId);
//        BoardState[xStart + 6][yEnd] = new Knight(new Coordinate(xStart + 6, yEnd), playerId);
//        BoardState[xStart + 7][yEnd] = new Rook(new Coordinate(xStart + 7, yEnd), playerId);
    }

    private static void setupPlayerLeftRight(int xStart, int xEnd, final String playerId) {
        for (int y = 3; y < boardSize - 3; y++) {
            BoardState[xStart][y] =
                    xStart == 1 ?
                            new RightPawn(new Coordinate(xStart, y), playerId) :
                            new LeftPawn(new Coordinate(xStart, y), playerId);
        }
//        BoardState[xEnd][2] = new Rook(new Coordinate(xEnd, 2), playerId);
//        BoardState[xEnd][3] = new Knight(new Coordinate(xEnd, 3), playerId);
//        BoardState[xEnd][4] = new Bishop(new Coordinate(xEnd, 4), playerId);
//        BoardState[xEnd][5] = new King(new Coordinate(xEnd, 5), playerId);
//        BoardState[xEnd][6] = new Queen(new Coordinate(xEnd, 6), playerId);
//        BoardState[xEnd][7] = new Bishop(new Coordinate(xEnd, 7), playerId);
//        BoardState[xEnd][8] = new Knight(new Coordinate(xEnd, 8), playerId);
//        BoardState[xEnd][9] = new Rook(new Coordinate(xEnd, 9), playerId);
    }
}
