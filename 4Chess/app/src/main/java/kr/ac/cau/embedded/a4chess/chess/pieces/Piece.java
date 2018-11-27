package kr.ac.cau.embedded.a4chess.chess.pieces;

import java.util.List;

import kr.ac.cau.embedded.a4chess.chess.Board;
import kr.ac.cau.embedded.a4chess.chess.Coordinate;

public abstract class Piece {

    public Coordinate position;
    public abstract List<Coordinate> getPossiblePositions();

    private final String playerId;

    Piece(Coordinate coordinate, String id) {
        this.position = coordinate;
        this.playerId = id;
    }

    public String getPlayerId() {
        return playerId;
    }

    @Override
    public String toString() {
        Coordinate coordinate = new Coordinate(position.x, position.y, (4 - Board.getRotation()) % 4);
        return coordinate.toString() + "," + playerId + "," + getClass().getSimpleName();
    }
}
