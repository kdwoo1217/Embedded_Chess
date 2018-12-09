package kr.ac.cau.embedded.a4chess;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import kr.ac.cau.embedded.a4chess.chess.Game;
import kr.ac.cau.embedded.a4chess.chess.Player;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link GameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private TextView gameStatusView;

    public GameFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment GameFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GameFragment newInstance() {
        GameFragment fragment = new GameFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.frag_chess, new ChessFragment())
                .commit();
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.frag_chatting, new ChatFragment())
                .commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Game.UI = this;

        View view = inflater.inflate(R.layout.fragment_game, container, false);
        gameStatusView = (TextView) view.findViewById(R.id.game_status);

        updateTurn();

        return view;
    }

    public void gameOverLocal(final Player winnerPlayer) {
        gameStatusView.setText(getString(R.string.gameover) + "\n" +
                "Team " + String.valueOf(winnerPlayer.team));
        getActivity().getSharedPreferences("localMatches", Context.MODE_PRIVATE).edit()
                .remove("match_" + Game.match.matchId).commit();
    }

    public void updateTurn() {
        StringBuilder stringBuilder = new StringBuilder();
        String current = Game.players[Game.turns % Game.players.length].id;
        for (Player p : Game.players) {
            stringBuilder.append("<font color='")
                    .append(String.format("#%06X", (0xFFFFFF & Game.getPlayerColor(p.id))))
                    .append("'>");
            if (p.id.equals(current)) stringBuilder.append("-> ");
            stringBuilder.append(p.name).append(" [").append(p.team).append("]</font><br />");
        }
        stringBuilder.delete(stringBuilder.lastIndexOf("<br />"), stringBuilder.length());
        gameStatusView.setText(Html.fromHtml(stringBuilder.toString()));
    }
}
