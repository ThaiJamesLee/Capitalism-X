package de.uni.mannheim.application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.uni.mannheim.components.GameModule;
import de.uni.mannheim.components.GameModuleType;
import de.uni.mannheim.components.GameScene;
import de.uni.mannheim.components.GameSceneType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class UIManager {

	private GameScene sceneMenuMain;
	private GameScene sceneGamePage;
	private List<GameModule> gameModules;

	private Stage window;

	public GameScene getSceneMenuMain() {
		return sceneMenuMain;
	}

	public GameScene getSceneGame() {
		return sceneGamePage;
	}

	/**
	 * Constructor for the {@link UIManager}. Loads and saves all the FXML-files.
	 * 
	 * @param stage The primary stage of the application.
	 */
	public UIManager(Stage stage) {
		this.window = stage;
		this.gameModules = new ArrayList<GameModule>();

		// static loading of the scenes
		loadScenes();

		// set the main menu scene as starting scene
		window.setScene(sceneMenuMain.getScene());

		// load all the modules and save them in the gameModules-list
		preloadModules();
	}

	/**
	 * Get the requested {@link GameModule} from the list of modules in use.
	 * 
	 * @param moduleType The type of the module.
	 * @return The requested {@link GameModule} or null, if no module was found.
	 */
	public GameModule getGameModule(GameModuleType moduleType) {
		for (GameModule module : gameModules) {
			if (module.getType() == moduleType) {
				return module;
			}
		}
		// TODO error handling? Custom Exceptions?
		return null;
	}

	/**
	 * Loads the main scenes and stores them in the respective variables.
	 */
	private void loadScenes() {
		Parent root;
		try {
			FXMLLoader loader = new FXMLLoader();
			root = loader.load(getClass().getResource("../fxml/mainmenu.fxml"));
			GameScene scene = new GameScene(new Scene(root), GameSceneType.MENU_MAIN, loader.getController());
			sceneMenuMain = scene;
			root = loader.load(getClass().getResource("../fxml/gamepage.fxml"));
			scene = new GameScene(new Scene(root), GameSceneType.GAME_PAGE, loader.getController());
			sceneGamePage = scene;
		} catch (IOException e) {
			// TODO Handle error if scenes cannot be initialized
			e.printStackTrace();
		}
	}

	/**
	 * Preloads all the {@link GameModule}s and adds them to the list of modules.
	 */
	private void preloadModules() {
		try {
			// for each predefined GameModuleTypes load the fxml-file and create a new
			// GameModule, that is stored in the list
			for (GameModuleType type : GameModuleType.values()) {
				FXMLLoader loader = new FXMLLoader();
				gameModules.add(new GameModule(loader.load(getClass().getResource("../fxml/" + type.fxmlFile)), type,
						type.gridColStart, type.gridColSpan, type.gridRowStart, type.gridRowSpan,
						loader.getController()));
			}
		} catch (IOException e) {
			// TODO handle error if module could not be loaded.
			e.printStackTrace();
		}
	}

	/**
	 * Switch to the specified scene.
	 * 
	 * @param sceneType The type of the {@link GameScene} to switch to.
	 */
	public void switchToScene(GameSceneType sceneType) {
		// TODO static scene choice at the moment, maybe change that later if more
		// scenes are needed
		switch (sceneType) {
		case MENU_MAIN:
			window.setScene(sceneMenuMain.getScene());
			break;
		case GAME_PAGE:
			window.setScene(sceneGamePage.getScene());
			break;
		default:
			// TODO handle if no scene found
			break;
		}
	}
	

}
