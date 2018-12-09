package kr.ac.cau.embedded.a4chess;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;

import org.xmlpull.v1.XmlPullParser;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ChessFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChessFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match

    public ChessFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChessFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChessFragment newInstance(String param1, String param2) {
        ChessFragment fragment = new ChessFragment();
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
        View view =  inflater.inflate(R.layout.fragment_chess, container, false);

        return view;
    }
}
