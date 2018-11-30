package kr.ac.cau.embedded.a4chess;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import kr.ac.cau.embedded.a4chess.chess.Game;
import kr.ac.cau.embedded.a4chess.chess.Match;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link RoomFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RoomFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match

    private TextView tvName;
    private TextView tvIp;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_room, container, false);
        Button testButton = (Button)view.findViewById(R.id.test_button);

        tvName = (TextView)view.findViewById(R.id.tv_name);
        tvIp = (TextView)view.findViewById(R.id.tv_ip);
        tvName.setText(((MainActivity) getActivity()).info_name);
        tvIp.setText(((MainActivity) getActivity()).info_ip);

        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((MainActivity) getActivity()).clientsMap.isEmpty()) {
                    tvName.setText("Not Connected : ");
                }
                else {
                    tvName.setText("Connected : ");
                }
                /*
                Match match = new Match(String.valueOf(System.currentTimeMillis()));
                Game.newGame(match);
                ((MainActivity) getActivity()).changeGameFragment();
                */
            }
        });

        return view;
    }
}
