package de.uni.mannheim.capitalismx.gamelogic;

public class GameController {

    private static GameController instance;

    private GameController() {}

    public static GameController getInstance() {
        if(instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    public void nextDay() {
        GameState.getInstance().setGameDate(GameState.getInstance().getGameDate().plusDays(1));
    }
}
