package de.uni.mannheim.capitalismx.gamecontroller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameThread extends Thread {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameThread.class);

    private static GameThread instance;

    private long secondsPerDay;
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

    @Override
    public synchronized void run() {
        while (running) {
            if (!pause) {
                GameController.getInstance().nextDay();
            }
            try {
                wait(this.secondsPerDay * 1000);
            } catch (InterruptedException e) {
                LOGGER.error(e.getMessage(), e);
                Thread.currentThread().interrupt();
            }
        }
    }


    public void terminate() {
        this.running = false;
        GameThread.instance = null;
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
