package de.uni.mannheim.capitalismx.gamelogic;

public class GameState {

    private static GameState instance;

    private GameState() {}

    public static GameState getInstance() {
        if(instance == null) {
            instance = new GameState();
        }
        return  instance;
    }
}
