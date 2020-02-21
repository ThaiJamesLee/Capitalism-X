package de.uni.mannheim.capitalismx.gamecontroller.gamesave;


import de.uni.mannheim.capitalismx.gamecontroller.GameState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author duly
 *
 * This class handles saving and loading the game state in a savegame file.
 * The default location is the current directory, when initialzing with the empty constructor.
 * Alternatively, one can specify the path, where to save or load.
 */
public class SaveGameHandler {

    private static final String SAVE_GAME_FILE = "capitalismx.savegame";
    private static final Logger logger = LoggerFactory.getLogger(SaveGameHandler.class);

    /**
     * The path of the savegame file.
     */
    private String filePath;

    /**
     * The path to the directory of the savegame file.
     */
    private String dir;

    /**
     * This constructor uses the current directory.
     * It creates a ./data/ directory in the current directory to write the gamesave file.
     */
    public SaveGameHandler() {
        dir = System.getProperty("user.dir") + File.separator + "data";
        filePath = dir + File.separator + SAVE_GAME_FILE;
        createDir(dir);
    }

    /**
     * This constructor allows to specify a path.
     * Saves the savegame in {dir}/data/*.savegame
     * @param dir The path to the savegame directory.
     */
    public SaveGameHandler(String dir) {
        this.dir = dir;
        filePath = dir + File.separator + SAVE_GAME_FILE;
        createDir(dir);
    }

    /**
     *  Save the GameState instance in a file. The GameState and every Object that the GameState instance contains
     *  must implement the Serializable interface.
     * @param state A {@link GameState} instance that should be saved.
     */
    public void saveGameState(GameState state) {
        File file = new File(filePath);

        String message = "Save Game in: " + filePath;
        logger.info(message);

        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(state);

            objectOutputStream.close();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

    }

    /**
     *  Save the GameState instance in a file. The GameState and every Object that the GameState instance contains
     *  must implement the Serializable interface.
     * @param filePath the path to the savegame file.
     * @param state A {@link GameState} instance that should be saved.
     */
    public void saveGameState(GameState state, String filePath) {
        File file = new File(filePath);

        String message = "Save Game in: " + filePath;
        logger.info(message);

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
        logger.info("Load " + filePath);
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(new File(filePath)))) {
            gameState = (GameState) objectInputStream.readObject();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        return gameState;
    }

    /**
     * @param filePath specify the path to the file savegame file.
     * @return Returns the GameState instance saved in the defined file.
     */
    public GameState loadGameState(String filePath) throws ClassNotFoundException {
        GameState gameState = null;
        logger.info("Load " + filePath);
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

    /**
     *  Checks the default location.
     * @return Returns true, if the /data folder contains a .savegame file. Else, returns false.
     */
    public boolean saveGameExists() {
        File f = new File(dir);
        if(f.isDirectory()) {
            File[] dataDir = f.listFiles();

            if(dataDir != null) {
                for(File d : dataDir) {
                    if(d.getName().contains(".savegame")) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     *
     * @return Returns list of files in the default savegame location. If empty, it returns null.
     */
    public File[] getSaveGames() {
        File f = new File(dir);
        if(f.isDirectory()) {
            return f.listFiles();
        }
        return null;
    }

    private void createDir(String dir) {
        try {
            Files.createDirectories(Paths.get(dir));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

}
