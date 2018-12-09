package kr.ac.cau.embedded.a4chess;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import kr.ac.cau.embedded.a4chess.device.DeviceController;

public class MainActivity extends AppCompatActivity {

//    // Used to load the 'native-lib' library on application startup.
//    static {
//        System.loadLibrary("native-lib");
//    }

    private static final int SERVER_TEXT_UPDATE = 100;
    private static final int CLIENT_TEXT_UPDATE = 200;
    public static final int port = 9988;
    public static String info_name = new String();
    public static String info_ip = new String();

    // server setting
    private ServerSocket serverSocket;
    private Socket socket;
    private int player_num;
    public static String msg;
    public static StringBuilder serverMsg = new StringBuilder();
    public static StringBuilder clientMsgBuilder = new StringBuilder();
    public static Map<String, DataOutputStream> clientsMap = new HashMap<String, DataOutputStream>();

    // client setting
    private Socket clientSocket;
    private DataInputStream clientIn;
    private DataOutputStream clientOut;
    public static String clientMsg;
    private String nickName;

    public void clientSend() {
        String msg = nickName + " : " + "test!\n"; //transClientText.getText() + "\n";
//                clientMsgBuilder.append(msg);
//                clientText.setText(clientMsgBuilder.toString());
        try {
            clientOut.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void serverSend() {
        String msg = "Server : " + "test!\n"; //transServerText.getText().toString() + "\n";
        serverMsg.append(msg);
        sendMessage(msg);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msgg) {
            super.handleMessage(msgg);
            switch (msgg.what) {
                case SERVER_TEXT_UPDATE: {
                    serverMsg.append(msg);
                    // serverText.setText(serverMsg.toString()); // 메세지 갱신
                }
                break;
                case CLIENT_TEXT_UPDATE: {
                    clientMsgBuilder.append(clientMsg);
                    // clientText.setText(clientMsgBuilder.toString()); // 메세지 갱신
                }
                break;
            }
        }
    };

    public String getLocalIpAddress() {
        WifiManager wifiMgr = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
        int ip = wifiInfo.getIpAddress();
        String ipAddress = String.format("%d.%d.%d.%d"
                , (ip & 0xff)
                , (ip >> 8 & 0xff)
                , (ip >> 16 & 0xff)
                , (ip >> 24 & 0xff));
        return ipAddress;
    }

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

    public void serverCreate() {
        Collections.synchronizedMap(clientsMap);
        player_num = 0;
        try {
            serverSocket = new ServerSocket(port);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        /** XXX 01. 첫번째. 서버가 할일 분담. 계속 접속받는것. */
                        Log.v("", "서버 대기중...");
                        try {
                            socket = serverSocket.accept();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Log.v("", socket.getInetAddress() + "에서 접속했습니다.");
                        msg = socket.getInetAddress() + "에서 접속했습니다.\n";
                        handler.sendEmptyMessage(SERVER_TEXT_UPDATE);

                        new Thread(new Runnable() {
                            private DataInputStream in;
                            private DataOutputStream out;
                            private String nick;

                            @Override
                            public void run() {
                                try { // setting
                                    out = new DataOutputStream(socket.getOutputStream());
                                    in = new DataInputStream(socket.getInputStream());
                                    player_num++;
                                    nick = in.readUTF() + Integer.toString(player_num);
                                    addClient(nick, out);
                                    sendMessage("info_" + nick);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                try { // keep receiving state
                                    while (in != null) {
                                        msg = in.readUTF();
                                        sendMessage(msg);
                                        handler.sendEmptyMessage(SERVER_TEXT_UPDATE);
                                    }
                                } catch (IOException e) {
                                    // disconnect -> removeClient
                                    removeClient(nick);
                                }
                            }
                        }).start();
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addClient(String nick, DataOutputStream out) throws IOException {
        clientsMap.put(nick, out);
    }

    public void removeClient(String nick) {
        clientsMap.remove(nick);
    }

    public void sendMessage(String msg) {
        Iterator<String> it = clientsMap.keySet().iterator();
        String key = "";
        while (it.hasNext()) {
            key = it.next();
            try {
                clientsMap.get(key).writeUTF(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void joinServer(final String ipAddress) {
        if(nickName==null){
            nickName="client";
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    clientSocket = new Socket(ipAddress, port);
                    Log.v("", "Client : Connection Complete.");
                    info_name += "(Connected)";

                    clientOut = new DataOutputStream(clientSocket.getOutputStream());
                    clientIn = new DataInputStream(clientSocket.getInputStream());

                    // client name setting
                    clientOut.writeUTF(nickName);
                    Log.v("", "Client : Data Send.");

                    while (clientIn != null) {
                        try {
                            clientMsg = clientIn.readUTF();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        handler.sendEmptyMessage(CLIENT_TEXT_UPDATE);
                    }
                } catch (UnknownHostException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }).start();
    }
}
