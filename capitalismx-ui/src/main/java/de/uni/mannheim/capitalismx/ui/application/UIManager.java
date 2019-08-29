package de.uni.mannheim.capitalismx.ui.application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.ui.components.GameModule;
import de.uni.mannheim.capitalismx.ui.components.GameModuleDefinition;
import de.uni.mannheim.capitalismx.ui.components.GameScene;
import de.uni.mannheim.capitalismx.ui.components.GameSceneType;
import de.uni.mannheim.capitalismx.ui.components.GameView;
import de.uni.mannheim.capitalismx.ui.components.GameViewType;
import de.uni.mannheim.capitalismx.ui.controller.GamePageController;
import de.uni.mannheim.capitalismx.ui.utils.GridPosition;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class UIManager {

	private GameScene sceneMenuMain;
	private GameScene sceneGamePage;
	private List<GameView> gameViews;

	private Stage window;

	private String language;

	// Controller for the main scene of the game.
	private GamePageController gamePageController;

	public GameScene getSceneMenuMain() {
		return sceneMenuMain;
	}

	public GameScene getSceneGame() {
		return sceneGamePage;
	}

	public GamePageController getGamePageController() {
		return gamePageController;
	}

	/**
	 * Constructor for the {@link UIManager}. Loads and saves all the FXML-files.
	 * 
	 * @param stage The primary stage of the application.
	 */
	public UIManager(Stage stage) {
		this.window = stage;
		this.language = "EN";

		// static loading of the scenes
		loadScenes();

		// set the main menu scene as starting scene
		window.setScene(sceneMenuMain.getScene());

		// load all the modules and save them in the gameModules-list
		preloadViewsAndModules();
	}

	public void init() {
		sceneGamePage.getController().update();
		initKeyboardControls();
	}
	
	private void initKeyboardControls() {
		sceneGamePage.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
				case DIGIT1:
					gamePageController.switchView(GameViewType.getTypeById(1));
					break;
				case DIGIT2:
					gamePageController.switchView(GameViewType.getTypeById(2));
					break;
				case DIGIT3:
					gamePageController.switchView(GameViewType.getTypeById(3));
					break;
				case DIGIT4:
					gamePageController.switchView(GameViewType.getTypeById(4));
					break;
				case DIGIT5:
					gamePageController.switchView(GameViewType.getTypeById(5));
					break;
				case DIGIT6:
					gamePageController.switchView(GameViewType.getTypeById(6));
					break;
				case DIGIT7:
					gamePageController.switchView(GameViewType.getTypeById(7));
					break;
				case DIGIT8:
					gamePageController.switchView(GameViewType.getTypeById(8));
					break;
				default:
					break;
				}
				
			}
			
		});
	}

	/**
	 * Get the requested {@link GameView } from the list of views.
	 * 
	 * @param viewType The {@link GameViewType} of the view.
	 * @return The requested {@link GameView} or null, if no view was found.
	 */
	public GameView getGameView(GameViewType viewType) {
		for (GameView view : gameViews) {
			if (view.getViewType() == viewType) {
				return view;
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
			ResourceBundle bundle = ResourceBundle.getBundle("properties.main_en");
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/mainmenu.fxml"), bundle);
			root = loader.load();
			GameScene scene = new GameScene(new Scene(root), GameSceneType.MENU_MAIN, loader.getController());
			sceneMenuMain = scene;
			loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/gamepage.fxml"));
			root = loader.load();
			gamePageController = (GamePageController) loader.getController();
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
	private void preloadViewsAndModules() {
		// init list of GameViews
		gameViews = new ArrayList<GameView>();
		// create a new GameView for each GameViewType
		for (GameViewType viewType : GameViewType.values()) {
			gameViews.add(new GameView(viewType));
		}
		try {
			// for each predefined GameModuleTypes load the fxml-file and create
			// a new GameModule, that is stored in the list
			for (GameModuleDefinition moduleDefinition : GameModuleDefinition.values()) {
				FXMLLoader loader = new FXMLLoader(
						getClass().getClassLoader().getResource("fxml/module/" + moduleDefinition.fxmlFile));
				// create new GridPosition from the type.
				GridPosition position = new GridPosition(moduleDefinition.gridColStart, moduleDefinition.gridRowStart,
						moduleDefinition.gridColSpan, moduleDefinition.gridRowSpan);
				// create new GameModule from the type and add it to its view.
				GameModule module = new GameModule(loader.load(), moduleDefinition, position, loader.getController());
				getGameView(moduleDefinition.viewType).addModule(module);
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
		// TODO static scene choice at the moment, maybe change that later if
		// more scenes are needed
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

	/**
	 * Switch to other language scene by reloading message properties.
	 */
	public void reloadProperties() {
		String newProperties;
		if ("EN".equals(this.language)) {
			newProperties = "properties.main_de";
			this.language = "DE";
		} else {
			newProperties = "properties.main_en";
			this.language = "EN";
		}
		ResourceBundle bundle = ResourceBundle.getBundle(newProperties);
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/mainmenu.fxml"), bundle);
		try {

			Parent root = loader.load();
			GameScene scene = new GameScene(new Scene(root), GameSceneType.MENU_MAIN, loader.getController());
			sceneMenuMain = scene;
			window.setScene(sceneMenuMain.getScene());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
