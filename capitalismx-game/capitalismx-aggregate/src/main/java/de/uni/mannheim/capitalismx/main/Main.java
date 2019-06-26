package de.uni.mannheim.capitalismx.main;

import de.uni.mannheim.capitalismx.gamelogic.GameController;

public class Main {

    public static void main(String[] args) {
        GameController.getInstance().setInitialValues();
        GameController.getInstance().start();
    }
}
