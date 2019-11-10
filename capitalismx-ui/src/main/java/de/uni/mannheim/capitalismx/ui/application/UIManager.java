package de.uni.mannheim.capitalismx.ui.application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.gamelogic.GameController;
import de.uni.mannheim.capitalismx.gamelogic.GameState;
import de.uni.mannheim.capitalismx.ui.components.GameModule;
import de.uni.mannheim.capitalismx.ui.components.GameModuleDefinition;
import de.uni.mannheim.capitalismx.ui.components.GameScene;
import de.uni.mannheim.capitalismx.ui.components.GameSceneType;
import de.uni.mannheim.capitalismx.ui.components.GameView;
import de.uni.mannheim.capitalismx.ui.components.GameViewType;
import de.uni.mannheim.capitalismx.ui.controller.GameHudController;
import de.uni.mannheim.capitalismx.ui.controller.GamePageController;
import de.uni.mannheim.capitalismx.ui.controller.LoadingScreenController;
import de.uni.mannheim.capitalismx.ui.controller.module.GameModuleController;
import de.uni.mannheim.capitalismx.ui.controller.module.OverviewMap3DController;
import de.uni.mannheim.capitalismx.ui.utils.CssHelper;
import de.uni.mannheim.capitalismx.ui.utils.GameResolution;
import de.uni.mannheim.capitalismx.ui.utils.GridPosition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * The UIManager is the central Singleton class of the UI. It manages all the UI
 * components like the {@link GameScene}s, {@link GameView}s, the Controller of
 * the GamePage, as well as some utilities like the {@link GameResolution} and
 * the {@link Stage}. Additionally it provides some useful methods.
 * 
 * @author Jonathan
 *
 */
public class UIManager {

	/**
	 * The {@link GameScene}s of the game.
	 */
	private GameScene sceneMenuMain;
	private GameScene sceneGamePage;
	private GameScene sceneLoadingScreen;

	// List containing all GameViews
	private List<GameView> gameViews;

	private static UIManager instance;

	// The Stage object representing the window.
	private Stage window;

	private String language;
	
	//Provide access to correct Resource Bundle
	private static ResourceBundle resourceBundle;

	// Controller for the main scene of the game.
	private GamePageController gamePageController;
	private GameHudController gameHudController;
	private OverviewMap3DController gameMapController;

	// Get information about the resolution of the game.
	private GameResolution gameResolution;

	public GameResolution getGameResolution() {
		return gameResolution;
	}

	/**
	 * Constructor for the {@link UIManager}. Loads and saves all the FXML-files.
	 * 
	 * @param stage                The primary stage of the application.
	 * @param calculatedResolution The {@link GameResolution} that was initially
	 *                             calculated for the game.
	 */
	public UIManager(Stage stage, GameResolution calculatedResolution) {
		instance = this;
		this.window = stage;
		this.language = "EN";
		this.gameResolution = calculatedResolution;
		
		resourceBundle =  ResourceBundle.getBundle("properties.main_en");

		resetResolution();
		// static loading of the scenes
		loadScenes();

		// set the initial main menu scene as starting scene
		window.setScene(new Scene(sceneMenuMain.getScene()));
	}

	public static UIManager getInstance() {
		return instance;
	}

	public GameHudController getGameHudController() {
		return gameHudController;
	}

	public void setGameHudController(GameHudController gameHudController) {
		this.gameHudController = gameHudController;
	}

	public OverviewMap3DController getGameMapController() {
		return gameMapController;
	}

	public void setGameMapController(OverviewMap3DController gameMapController) {
		this.gameMapController = gameMapController;
	}

	public GamePageController getGamePageController() {
		return gamePageController;
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

	public void resetResolution() {
		// TODO adjust/force size of Scene/Stage to given Resolution or just switch css
		CssHelper.adjustCssToCurrentResolution();
	}

	public GameScene getSceneGame() {
		return sceneGamePage;
	}

	public GameScene getSceneMenuMain() {
		return sceneMenuMain;
	}

	/**
	 * Initializes all components needed for a new Game.
	 */
	public void initGame() {

		GameState.getInstance().initiate();

		switchToScene(GameSceneType.LOADING_SCREEN);
		// load all the modules and save them in the gameModules-list
		prepareGamePage();

	}

	/**
	 * Initialize the KeyboardControls on the GamePage.
	 */
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
				case F12:
					UIManager.getInstance().toggleFullscreen();
					break;
				case ESCAPE:
					// TODO open Menu
					break;
				default:
					break;
				}

			}
		});

		sceneGamePage.getScene().addEventHandler(ScrollEvent.SCROLL, gameMapController.getMouseEventHandler());
		sceneGamePage.getScene().setOnMousePressed(gameMapController.getMouseEventHandler());
		sceneGamePage.getScene().setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				gameMapController.getMouseEventHandler().handle(event);
			}
		});
	}

	/**
	 * Loads the main scenes and stores them in the respective variables.
	 */
	private void loadScenes() {
		Parent root;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/mainMenu2.fxml"), resourceBundle);
			root = loader.load();
			sceneMenuMain = new GameScene(root, GameSceneType.MENU_MAIN, loader.getController());

			loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/loadingScreen.fxml"), resourceBundle);
			root = loader.load();
			sceneLoadingScreen = new GameScene(root, GameSceneType.GAME_PAGE, loader.getController());
		} catch (IOException e) {
			// TODO Handle error if scenes cannot be initialized
			e.printStackTrace();
		}
	}

	/**
	 * Preloads all the {@link GameModule}s and adds them to the list of modules.
	 */
	private void prepareGamePage() {
		// Create a task to load all the Modules without freezing the GUI
		Task<Integer> task = new Task<Integer>() {

			private double progress = 0.0;
			int numOfComponents = GameModuleDefinition.values().length + 1;
			
			private void updateProgress() {
				progress += 1.0 / numOfComponents;
				this.updateProgress(progress, 1.0);
			}
			
			@Override
			protected Integer call() throws Exception {

				try {
					this.updateProgress(0.0, 1.0);
					
					// load GamePage
					FXMLLoader gamePageLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/gamepage.fxml"), resourceBundle);
					Parent gamePageRoot = gamePageLoader.load();
					gamePageController = (GamePageController) gamePageLoader.getController();
					sceneGamePage = new GameScene(gamePageRoot, GameSceneType.GAME_PAGE, gamePageLoader.getController());
					
					// init list of GameViews
					gameViews = new ArrayList<GameView>();
					// create a new GameView for each GameViewType
					for (GameViewType viewType : GameViewType.values()) {
						gameViews.add(new GameView(viewType));
					}

					// for each predefined GameModuleTypes load the fxml-file and create
					// a new GameModule, that is stored in the list
					for (GameModuleDefinition moduleDefinition : GameModuleDefinition.values()) {
						FXMLLoader loader = new FXMLLoader(
								getClass().getClassLoader().getResource("fxml/module/" + moduleDefinition.fxmlFile), resourceBundle);
						// create new GridPosition from the type.
						GridPosition position = new GridPosition(moduleDefinition.gridColStart,
								moduleDefinition.gridRowStart, moduleDefinition.gridColSpan,
								moduleDefinition.gridRowSpan);

						// load root and controller of the module from the fxml
						Parent root = loader.load();
						GameModuleController controller = loader.getController();

					

						// create new GameModule from the type and add it to its view.
						GameModule module = new GameModule(root, moduleDefinition, position, controller);
						getGameView(moduleDefinition.viewType).addModule(module);
						
						// update the progressbar
						updateProgress();
					}

					initKeyboardControls();
					// start the game once everything is loaded
					startGame();
				} catch (IOException e) {
					// TODO handle error if module could not be loaded.
					e.printStackTrace();
				}
				return 1;
			}

		};
		((LoadingScreenController) sceneLoadingScreen.getController()).initProgressBar(task.progressProperty());

		new Thread(task).start();
	}

	/**
	 * Start the Game by switching to the GamePage and starting the GameController
	 */
	private void startGame() {
		Platform.runLater(() -> {
			gamePageController.switchView(GameViewType.OVERVIEW);
			switchToScene(GameSceneType.GAME_PAGE);
		});
		Task task = new Task<Void>() {
			@Override public Void call() {
				GameController.getInstance().start();
				return null;
			}
		};
		new Thread(task).start();
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
		//TODO refactoring
		resourceBundle = ResourceBundle.getBundle(newProperties);
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/mainMenu2.fxml"), resourceBundle);
		try {

			Parent root = loader.load();
			GameScene scene = new GameScene(root, GameSceneType.MENU_MAIN, loader.getController());
			sceneMenuMain = scene;
			
			loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/loadingScreen.fxml"), resourceBundle);
			root = loader.load();
			sceneLoadingScreen = new GameScene(root, GameSceneType.GAME_PAGE, loader.getController());
			switchToScene(GameSceneType.MENU_MAIN);
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
			window.getScene().setRoot(sceneMenuMain.getScene());
			break;
		case GAME_PAGE:
			window.getScene().setRoot(sceneGamePage.getScene());
			break;
		case LOADING_SCREEN:
			window.getScene().setRoot(sceneLoadingScreen.getScene());
			break;
		default:
			// TODO handle if no scene found
			break;
		}
	}

	/**
	 * (De-)activates the FullscreenMode, depending on whether it is active.
	 */
	public void toggleFullscreen() {
		window.setFullScreen(!window.isFullScreen());
	}

	/**
	 * Quits the game: Triggers a new {@link WindowEvent}, containing a
	 * WINDOW_CLOSE_REQUEST, which can then be handled by the Application. TODO
	 * maybe handle more stuff when ingame. (eg autosave)
	 */
	public void quitGame() {
		window.fireEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
	}
	
	public static ResourceBundle getResourceBundle() {
		return resourceBundle;
	}

}
