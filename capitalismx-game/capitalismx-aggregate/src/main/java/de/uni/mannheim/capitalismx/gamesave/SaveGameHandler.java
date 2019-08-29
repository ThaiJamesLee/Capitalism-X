package de.uni.mannheim.capitalismx.gamesave;


import de.uni.mannheim.capitalismx.gamelogic.GameState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @author duly
 *
 * This class handles saving and loading the game state in a savegame file.
 * The default location is the current directory, when initialzing with the empty constructor.
 * Alternatively, one can specify the path, where to save or load.
 */
public class SaveGameHandler {

    private String dir;

    private static final String SAVE_GAME_FILE = "capitalismx.savegame";
    private static final Logger logger = LoggerFactory.getLogger(SaveGameHandler.class);

    private String filePath;

    public SaveGameHandler() {
        dir = System.getProperty("user.dir");
        filePath = dir + File.separator + SAVE_GAME_FILE;

    }

    public SaveGameHandler(String dir) {
        this.dir = dir;
        filePath = dir + File.separator + SAVE_GAME_FILE;
    }

    /**
     *  Save the GameState instance in a file. The GameState and every Object that the GameState instance contains
     *  must implement the Serializable interface.
     * @param state A {@link GameState} instance that should be saved.
     */
    public void saveGameState(GameState state) {
        File file = new File(filePath);

        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(state);

            objectOutputStream.close();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

    }


    /**
     *
     * @return Returns the GameState instance saved in the defined file.
     */
    public GameState loadGameState() throws ClassNotFoundException {
        GameState gameState = null;

        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(new File(filePath)))) {
            gameState = (GameState) objectInputStream.readObject();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        return gameState;
    }

    /**
     *
     * @return Returns path of the save game file.
     */
    public String getFilePath() {
        return filePath;
    }

}
