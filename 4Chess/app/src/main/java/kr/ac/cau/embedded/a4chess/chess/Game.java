package kr.ac.cau.embedded.a4chess.chess;

import android.graphics.Color;

import java.util.LinkedList;
import java.util.List;

import kr.ac.cau.embedded.a4chess.GameFragment;

public class Game {
    public static Match match;
    public static Player[] players;
    public static int turns;

    public static GameFragment UI;

    private static List<String> deadPlayers;
    private final static int[] PLAYER_COLOR = {
            Color.parseColor("#FF8800"),
            Color.parseColor("#99CC00"),
            Color.parseColor("#33B5E5"),
            Color.parseColor("#CC0000")
    };

    public static int getPlayerColor(String id) {
        return getPlayer(id).color;
    }

    public static Player getPlayer(final String id) {
        for (Player player : players) {
            if (player.id.equals(id)) {
                return player;
            }
        }
        return null;
    }

    public static void newGame(final Match match) {
        Game.match = match;
        turns = 0;
        deadPlayers = new LinkedList<String>();
        createPlayers();
        Board.newGame(players);
    }

    private static void createPlayers() {
        int numPlayers = match.getNumPlayers();
        players = new Player[numPlayers];
        for (int i = 0; i < numPlayers; i++) {
            players[i] =
                    new Player(String.valueOf(i), i / 2,
                            PLAYER_COLOR[i], "Player " + (i + 1));
        }
    }

    public static boolean removePlayer(final String playerId) {
        if (players.length > 2) Board.removePlayer(playerId);
        deadPlayers.add(playerId);
        return isGameOver();
    }

    public static void moved() {
        turns++;
        String next = players[turns % players.length].id;
        while (deadPlayers.contains(next)) {
            turns++; // skip dead players
            next = players[turns % players.length].id;
        }
        if (next.startsWith("AutoMatch_")) {
            next = null;
        }
        if (UI != null) {
            UI.updateTurn();
        }
    }

    public static void over() {

    }

    public static boolean isGameOver() {
        return (players.length - deadPlayers.size() <= 1) ||
                (deadPlayers.size() == 2 && sameTeam(deadPlayers.get(0), deadPlayers.get(1)));
    }

    public static String currentPlayer() {
        return players[turns % players.length].id;
    }

    public static boolean myTurn() {
//        return match.isLocal || myPlayerId.equals(players[turns % players.length].id);
        return true;
    }

    public static boolean sameTeam(final String id1, final String id2) {
        return getPlayer(id1).team == getPlayer(id2).team;
    }
}
