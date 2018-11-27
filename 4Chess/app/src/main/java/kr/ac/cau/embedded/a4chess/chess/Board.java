package kr.ac.cau.embedded.a4chess.chess;

import kr.ac.cau.embedded.a4chess.chess.pieces.Piece;

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
}
