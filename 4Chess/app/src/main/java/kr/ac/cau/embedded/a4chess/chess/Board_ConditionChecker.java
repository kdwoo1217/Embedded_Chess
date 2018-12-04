package kr.ac.cau.embedded.a4chess.chess;

import java.util.List;
import java.util.ArrayList;
import android.util.Log;

import kr.ac.cau.embedded.a4chess.chess.pieces.Piece;
import kr.ac.cau.embedded.a4chess.chess.pieces.King;

public class Board_ConditionChecker {

    public static String checkPlayerCondition(String PlayerId) {
        if(isPlyaerChecked(PlayerId)){
            if(isCheckMated(PlayerId))
                return "checkmate";
            else
                return "check";
        }
        else{
            if(isStaleMated(PlayerId))
                return "stalemate";
            else
                return "none";
        }
    }

    public static boolean isPlyaerChecked(String PlayerId) {

        Coordinate currentPlayerKingCoordinate = getKingCoordinate(PlayerId).position;

        //check all enemy piece's possiblemoves.
        //If possiblemoves list contains currentplayerking coordinate, current player is determined
        //being under check condition.
        for(int i = 0 ; i < Board.getBoardSize(); i ++) {
            for(int j = 0; j < Board.getBoardSize(); j++) {
                if(Board.getPiece(new Coordinate(i,j)) == null){
                    continue;
                }
                if( !Game.sameTeam(Board.getPiece(new Coordinate(i, j)).getPlayerId(), PlayerId)
                        && Board.getPiece(new Coordinate(i, j)).getPossiblePositions().contains(currentPlayerKingCoordinate)){
                        return true;
                }
            }
        }

        return false;
    }

    public static boolean isCheckMated(String PlayerId) {

        /* if Player is not under check condition, return. */
        if(!isPlyaerChecked(PlayerId))
            return false;

        /* Look All possible moves of player */
        boolean isCheckMated = true;

        for (int i = 0; i < Board.getBoardSize(); i++) {
            for (int j = 0; j < Board.getBoardSize(); j++) {
                if(Board.getPiece(new Coordinate(i,j)) == null){
                    continue;
                }
                Coordinate playerPiecePos = new Coordinate(i, j);
                if (Board.getPiece(playerPiecePos).getPlayerId() == PlayerId) {
                    for(Coordinate elem : Board.getPiece(playerPiecePos).getPossiblePositions()){
                        Board.test_move(playerPiecePos, elem);
                        isCheckMated = isPlyaerChecked(PlayerId);
                        Board.test_retreat();
                        if(!isCheckMated)
                            return false;
                    }
                }
            }
        }

        return true;
    }

    public static boolean isStaleMated(String PlayerId) {

        /* if Player is not under check condition, return. */
        if(isPlyaerChecked(PlayerId))
            return false;

        /* Look All possible moves of player */
        boolean isCheckMated = true;

        for (int i = 0; i < Board.getBoardSize(); i++) {
            for (int j = 0; j < Board.getBoardSize(); j++) {
                if(Board.getPiece(new Coordinate(i,j)) == null){
                    continue;
                }
                Coordinate playerPiecePos = new Coordinate(i, j);
                if (Board.getPiece(playerPiecePos).getPlayerId() == PlayerId) {
                    for(Coordinate elem : Board.getPiece(playerPiecePos).getPossiblePositions()){
                        Board.test_move(playerPiecePos, elem);
                        isCheckMated &= isPlyaerChecked(PlayerId);
                        Board.test_retreat();
                        if(!isCheckMated)
                            return isCheckMated;
                    }
                }
            }
        }

        return isCheckMated;
    }

    private static Piece getKingCoordinate(String PlayerId){
        //get Player's current king coordinate
        for(int i = 0 ; i < Board.getBoardSize(); i ++) {
            for(int j = 0; j < Board.getBoardSize(); j++) {
                if(Board.getPiece(new Coordinate(i,j)) == null){
                    continue;
                }
                if(Board.getPiece(new Coordinate(i, j)).getPlayerId() == PlayerId
                        && Board.getPiece(new Coordinate(i, j)) instanceof King){
                    return Board.getPiece(new Coordinate(i, j));
                }
            }
        }

        return null;
    }
}
