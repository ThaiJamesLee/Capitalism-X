package de.uni.mannheim.capitalismx.gamelogic;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class GameThread extends Thread {

    private static GameThread instance;
    private LocalDate gameDate;
    private int secondsPerDay;
    private boolean pause;

    public static GameThread getInstance() {
        if(instance == null) {
            instance = new GameThread();
        }
        return  instance;
    }

    private GameThread() {
        this.gameDate = LocalDate.of(1990, 1, 1);
        this.secondsPerDay = 1;
    }

    public void run() {
        while(true) {
            if(!pause) {
                try {
                    Thread.sleep(this.secondsPerDay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                GameController.getInstance().nextDay();
            }
        }
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

    public LocalDate getGameDate() {
        return this.gameDate;
    }
}
