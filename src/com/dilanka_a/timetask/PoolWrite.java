package com.dilanka_a.timetask;

import java.io.BufferedWriter;
import java.io.IOException;

public class PoolWrite extends Thread {

    private String ID;
    private BufferedWriter bufferedWriter;

    public PoolWrite(String ID, BufferedWriter bufferedWriter) {
        this.ID = ID;
        this.bufferedWriter = bufferedWriter;
    }

    public String getID() {
        return ID;
    }

    public BufferedWriter getBufferedWriter() {
        return bufferedWriter;
    }

    @Override
    public void run() {
        try {
            getBufferedWriter().write(ID);
            getBufferedWriter().newLine();
        } catch (IOException e) {
        }
    }
}
