package com.dilanka_a.timetask;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class SetTimer {
    public static void main(String[] args) {
        PoolMain poolmain = new PoolMain();
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                poolmain.runMain();
            }
        };

        timer.scheduleAtFixedRate(timerTask, 0, 30000);
    }
}
