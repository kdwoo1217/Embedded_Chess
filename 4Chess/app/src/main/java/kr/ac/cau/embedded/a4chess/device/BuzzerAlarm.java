package kr.ac.cau.embedded.a4chess.device;

import java.util.Timer;
import java.util.TimerTask;

public class BuzzerAlarm {

    int time;
    TimerTask task;

    public BuzzerAlarm(int time){
        this.time = time;
    }

    public void run() {
        Timer timer = new Timer();

        task = new TimerTask(){
            @Override
            public void run()
            {
                DeviceController.BuzzerWrite(1);
            }
        };

        timer.schedule(task, 10);
    }
}