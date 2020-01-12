package de.uni.mannheim.capitalismx.gamecontroller;

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
        instance = null;
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
