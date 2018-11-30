package kr.ac.cau.embedded.a4chess;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
<<<<<<< HEAD
import android.widget.ListView;
=======
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import kr.ac.cau.embedded.a4chess.Chat.ChatMessage;
import kr.ac.cau.embedded.a4chess.Chat.MessageAdapter;

>>>>>>> f934bf4d0e0fc682cbbc4c364c384fac11759377

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match

    private ListView listView;
    private Button btnSend;
    private Button btnReceive;

    private List<ChatMessage> chatMessages;
    private ArrayAdapter<ChatMessage> adapter;

    public ChatFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ChatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatFragment newInstance() {
        ChatFragment fragment = new ChatFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
<<<<<<< HEAD
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        ListView listView = (ListView)view.findViewById(R.id.chat_list);

        listView.invalidate();
=======

        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        chatMessages = new ArrayList<>();

        btnSend = (Button) view.findViewById(R.id.send_btn);
        btnReceive = (Button) view.findViewById(R.id.receive_btn);

        listView = (ListView) view.findViewById(R.id.chat_list);

        adapter = new MessageAdapter(getActivity(), R.layout.fragment_chat, chatMessages);

        listView.setAdapter(adapter);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChatMessage chatMessage = new ChatMessage("player1", true);
                chatMessages.add(chatMessage);
                adapter.notifyDataSetChanged();
                //listView.invalidate();
            }
        });

        btnReceive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChatMessage chatMessage = new ChatMessage("player2", false);
                chatMessages.add(chatMessage);
                adapter.notifyDataSetChanged();
                //listView.invalidate();
            }
        });
>>>>>>> f934bf4d0e0fc682cbbc4c364c384fac11759377

        return view;
    }
}
