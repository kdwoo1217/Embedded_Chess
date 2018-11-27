package kr.ac.cau.embedded.a4chess.chess;

public class Game {
    public static Match match;

    public static int getPlayerColor(String id) {
        return getPlayer(id).color;
    }

    public static Player getPlayer(final String id) {
        for (Player player : players) {
            if (p.id.equals(id)) return p;
        }
        return null;
    }
}
