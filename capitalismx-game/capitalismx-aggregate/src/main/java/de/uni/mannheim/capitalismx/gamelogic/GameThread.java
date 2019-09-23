package de.uni.mannheim.capitalismx.gamelogic;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class GameThread extends Thread {

    private static GameThread instance;
    private int secondsPerDay;
    private boolean pause;
    private boolean running;

    public static GameThread getInstance() {
        if (instance == null) {
            instance = new GameThread();
        }
        return instance;
    }

    private GameThread() {
        this.secondsPerDay = 1;
        this.running = true;
    }

    public synchronized void run() {
        while (running) {
            if (!pause) {
                GameController.getInstance().nextDay();
            }
            try {
                wait(this.secondsPerDay * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public void terminate() {
        this.running = false;
    }

    public void setSecondsPerDay(int secondsPerDay) {
        this.secondsPerDay = secondsPerDay;
    }

    public void pause() {
        this.pause = true;
    }

    public void unpause() {
        this.pause = false;
    }

    public boolean isPause() {
        return pause;
    }

}
