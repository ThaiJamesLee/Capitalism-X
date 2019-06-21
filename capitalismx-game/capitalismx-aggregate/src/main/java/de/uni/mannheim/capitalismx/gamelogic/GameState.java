package de.uni.mannheim.capitalismx.gamelogic;

import java.time.LocalDate;

public class GameState {

    private static GameState instance;
    private LocalDate gameDate;

    private GameState() {
        this.gameDate = LocalDate.of(1990, 1, 1);
    }


    public static GameState getInstance() {
        if(instance == null) {
            instance = new GameState();
        }
        return  instance;
    }

    public LocalDate getGameDate() {
        return this.gameDate;
    }

    public void setGameDate(LocalDate gameDate) {
        this.gameDate = gameDate;
    }
}
