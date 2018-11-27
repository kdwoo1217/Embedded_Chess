package kr.ac.cau.embedded.a4chess;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import kr.ac.cau.embedded.a4chess.device.DeviceController;

public class MainActivity extends AppCompatActivity {

//    // Used to load the 'native-lib' library on application startup.
//    static {
//        System.loadLibrary("native-lib");
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frag_container, new StartFragment())
                .commit();
    }

    public void changeRoomFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frag_container, new RoomFragment())
                .commit();
    }

    public void changeGameFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frag_container, new GameFragment())
                .commit();
    }
}
