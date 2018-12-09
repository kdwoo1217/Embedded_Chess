package kr.ac.cau.embedded.a4chess;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import kr.ac.cau.embedded.a4chess.chess.Game;
import kr.ac.cau.embedded.a4chess.chess.Match;
import kr.ac.cau.embedded.a4chess.chess.Player;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link RoomFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RoomFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private final static int[] PLAYER_COLOR = {
            Color.parseColor("#FF8800"),
            Color.parseColor("#99CC00"),
            Color.parseColor("#33B5E5"),
            Color.parseColor("#CC0000")
    };

    private ArrayList<Player> playerItems;
    private Integer playerNum = 0;

    private PlayerListAdapter playerListAdapter;
    private ListView listView;
    private Button startButton;

    public RoomFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RoomFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RoomFragment newInstance(String param1, String param2) {
        RoomFragment fragment = new RoomFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        playerItems = new ArrayList<Player>();
        Player roomMaster = new Player(playerNum.toString(), playerNum / 2,
                PLAYER_COLOR[playerNum], "Player1", "192.168.0.1");
        playerItems.add(roomMaster);
        playerNum++;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_room, container, false);
        TextView roomName = (TextView)view.findViewById(R.id.room_name);
        roomName.setText(" " + playerItems.get(0).ip);

        playerListAdapter = new PlayerListAdapter(view.getContext(), R.layout.context_player, playerItems);
        listView = (ListView)view.findViewById(R.id.player_list);
        listView.setAdapter(playerListAdapter);

        Button testButton = (Button)view.findViewById(R.id.test_button);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(playerItems.size() < 4) {
                    Player player = new Player(playerNum.toString(), playerNum / 2,
                            PLAYER_COLOR[playerNum], "Player" + (++playerNum), "192.168.0." + playerNum);
                    playerItems.add(player);
                    playerListAdapter.notifyDataSetChanged();
                }
                if(playerItems.size() == 4) {
                    startButton.setVisibility(View.VISIBLE);
                }
            }
        });
        startButton = (Button)view.findViewById(R.id.start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Match match = new Match(String.valueOf(System.currentTimeMillis()));
                Game.newGame(match, playerItems);
                ((MainActivity) getActivity()).changeGameFragment();
            }
        });
        startButton.setVisibility(View.GONE);

        return view;
    }
}
