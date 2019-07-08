package de.uni.mannheim.capitalismx.gamesave;


import de.uni.mannheim.capitalismx.gamelogic.GameState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @author duly
 */
public class SaveGameHandler {

    private String dir;

    private static final String SAVE_GAME_FILE = "capitalismx.savegame";

    private static final Logger logger = LoggerFactory.getLogger(SaveGameHandler.class);

    public SaveGameHandler() {
        dir = System.getProperty("user.dir");
    }

    public SaveGameHandler(String dir) {
        this.dir = dir;
    }

    /**
     *  Save the GameState instance in a file. The GameState and every Object that the GameState instance contains
     *  must implement the Serializable interface.
     * @param state A {@link GameState} instance that should be saved.
     */
    public void saveGameState(GameState state) {
        File file = new File(dir + File.separator + SAVE_GAME_FILE);

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(state);

            objectOutputStream.close();
            fileOutputStream.close();

        } catch (IOException e) {
            logger.error(e.getMessage());
        }

    }


    /**
     *
     * @return Returns the GameState instance saved in the defined file.
     */
    public GameState loadGameState() throws ClassNotFoundException{
        GameState gameState = null;

        try {
            FileInputStream fileInputStream = new FileInputStream(new File(dir + File.separator + SAVE_GAME_FILE));
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            gameState = (GameState) objectInputStream.readObject();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        return gameState;
    }

}
