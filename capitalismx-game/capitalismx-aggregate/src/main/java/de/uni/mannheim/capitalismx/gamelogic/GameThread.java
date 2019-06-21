package de.uni.mannheim.capitalismx.gamelogic;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class GameThread extends Thread {

    private static GameThread instance;
    private LocalDate gameDate;

    public static GameThread getInstance() {
        if(instance == null) {
            instance = new GameThread();
        }
        return  instance;
    }

    private GameThread() {
        this.gameDate = LocalDate.of(1990, 1, 1);
    }

    public void run() {

    }

    public LocalDate getGameDate() {
        return this.gameDate;
    }

    public static void main(String[] args) {
        System.out.println(GameThread.getInstance().gameDate);
    }
}
