package de.uni.mannheim.capitalismx.ui.application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.gamecontroller.GameState;
import de.uni.mannheim.capitalismx.ui.components.GameModule;
import de.uni.mannheim.capitalismx.ui.components.GameModuleDefinition;
import de.uni.mannheim.capitalismx.ui.components.GameScene;
import de.uni.mannheim.capitalismx.ui.components.GameSceneType;
import de.uni.mannheim.capitalismx.ui.components.GameView;
import de.uni.mannheim.capitalismx.ui.components.GameViewType;
import de.uni.mannheim.capitalismx.ui.components.GameModuleType;
import de.uni.mannheim.capitalismx.ui.controller.LoadingScreenController;
import de.uni.mannheim.capitalismx.ui.controller.gamepage.GameHudController;
import de.uni.mannheim.capitalismx.ui.controller.gamepage.GamePageController;
import de.uni.mannheim.capitalismx.ui.controller.module.GameModuleController;
import de.uni.mannheim.capitalismx.ui.controller.module.OverviewMap3DController;
import de.uni.mannheim.capitalismx.ui.controller.module.warehouse.WarehouseListController;
import de.uni.mannheim.capitalismx.ui.tutorial.Tutorial;
import de.uni.mannheim.capitalismx.ui.utils.GameResolution;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
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
 */
public class UIManager {

	private static UIManager instance;
	// Provide access to correct Resource Bundle
	private static ResourceBundle resourceBundle = ResourceBundle.getBundle("properties.main", Locale.ENGLISH);

	// The game's Tutorial
	private Tutorial tutorial;

	// The custom cursor used in the game
	private Cursor cursor;

	/**
	 * The {@link GameScene}s of the game.
	 */
	private GameScene sceneMenuMain;
	private GameScene sceneGamePage;
	private GameScene sceneLoadingScreen;
	private GameScene sceneCreditsPage;
	private GameScene sceneLostPage;
	private GameScene sceneWonPage;

	// List containing all GameViews
	private List<GameView> gameViews;

	// The Stage object representing the window.
	private Stage window;
	private Locale language;

	// Various Controller classes.
	private GamePageController gamePageController;
	private GameHudController gameHudController;
	private OverviewMap3DController gameMapController;

	// Get information about the resolution of the game.
	private GameResolution gameResolution;

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
		this.language = Locale.ENGLISH;
		this.gameResolution = calculatedResolution;

		// static loading of the scenes
		loadGameScenes();
		gameViews = new ArrayList<GameView>();

		// set the initial main menu scene as starting scene
		prepareCustomCursor();
		Scene scene = new Scene(sceneMenuMain.getScene());
		scene.setCursor(cursor);
		window.setScene(scene);
	}

	/**
	 * Called when GameOver condition is reached and directs to the corresponding
	 * View for Lost or Won
	 */
	public void gameOver(boolean won) {
		GameSceneType next = won ? GameSceneType.GAMEWON_PAGE : GameSceneType.GAMELOST_PAGE;

		stopGame();
		switchToScene(next);
	}

	public static UIManager getInstance() {
		return instance;
	}

	/**
	 * Returns the corresponding message for the given key from the stored
	 * {@link ResourceBundle} - default englisch: properties.main_en
	 * 
	 * @param key The key to look up in the resources.
	 * @return The localised message as a String.
	 */
	public static String getLocalisedString(String key) {
		return resourceBundle.getString(key);
	}

	public static ResourceBundle getResourceBundle() {
		return resourceBundle;
	}

	public Cursor getCursor() {
		return this.cursor;
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

	public GameResolution getGameResolution() {
		return gameResolution;
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
	 * Returns the language file as java.util.locale.
	 * 
	 * @return The requested language file as Locale.
	 */
	public Locale getLanguage() {
		return language;
	}

	public GameScene getSceneGame() {
		return sceneGamePage;
	}

	public GameScene getSceneMenuMain() {
		return sceneMenuMain;
	}

	public Stage getStage() {
		return window;
	}

	public void setTutorial(Tutorial tutorial) {
		this.tutorial = tutorial;
	}

	public Tutorial getTutorial() {
		return tutorial;
	}

	/**
	 * This method is being called after initializing all {@link GameModule}s and
	 * can be used to check if some optional modules should be activated.
	 */
	private void initDepartments() {
		// check WAREHOUSE
		if (GameState.getInstance().getWarehousingDepartment().getWarehouses().size() > 0) {
			((WarehouseListController) getGameView(GameViewType.WAREHOUSE).getModule(GameModuleType.WAREHOUSE_LIST)
					.getController()).activateWarehouseModules();
		}
	}

	/**
	 * Initialize the KeyboardControls on the GamePage.
	 */
	private void initKeyboardControls() {

		sceneGamePage.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
				case P:
					gameHudController.playPause();
					break;
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
				case DIGIT9:
					gamePageController.switchView(GameViewType.getTypeById(9));
					break;
				case F12:
					UIManager.getInstance().toggleFullscreen();
					break;
				case F5:
					GameController.getInstance().saveGame();
					break;
				case F9:
					// TODO mechanism that stops loading when there is no saveGame!
					stopGame();
					loadGame();
					break;
				case ESCAPE:
					// first try to close open hud elements. Let GamePage handle input otherwise.
					if (!gameHudController.handleEscapeInput()) {
						gamePageController.handleEscapeInput();
					}
					break;
				default:
					break;
				}

			}
		});

		sceneGamePage.getScene().addEventHandler(ScrollEvent.SCROLL, gameMapController.getMouseEventHandler());
		sceneGamePage.getScene().setOnMousePressed(gameMapController.getMouseEventHandler());
		sceneGamePage.getScene().setOnMouseDragged(gameMapController.getMouseEventHandler());
	}

	/**
	 * Loads an existing Game via the {@link GameController} and prepares all
	 * necessary elements.
	 */
	public void loadGame() {
		GameController.getInstance().loadGame();
		prepareGame(false);
	}

	/**
	 * Loads the main {@link GameScene}s and stores them in the respective
	 * variables.
	 */
	private void loadGameScenes() {
		Parent root;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/main_menu.fxml"),
					resourceBundle);
			root = loader.load();
			sceneMenuMain = new GameScene(root, GameSceneType.MENU_MAIN, loader.getController());

			loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/loading_screen.fxml"), resourceBundle);
			root = loader.load();
			sceneLoadingScreen = new GameScene(root, GameSceneType.GAME_PAGE, loader.getController());

			loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/credits_page.fxml"), resourceBundle);
			root = loader.load();
			sceneCreditsPage = new GameScene(root, GameSceneType.CREDITS_PAGE, loader.getController());

			loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/game_lost_page.fxml"), resourceBundle);
			root = loader.load();
			sceneLostPage = new GameScene(root, GameSceneType.GAMELOST_PAGE, loader.getController());

			loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/game_won_page.fxml"), resourceBundle);
			root = loader.load();
			sceneWonPage = new GameScene(root, GameSceneType.GAMELOST_PAGE, loader.getController());

		} catch (IOException e) {
			// TODO Handle error if scenes cannot be initialized
			e.printStackTrace();
			Platform.exit();
		}
	}

	/**
	 * Starts a new Game by preparing all necessary resources.
	 */
	public void newGame() {
		prepareGame(true);
	}

	/**
	 * Prepares the custom cursor used in the Game.
	 */
	private void prepareCustomCursor() {
		Image image = new Image(getClass().getClassLoader().getResourceAsStream("img/cursor.png"));
		cursor = new ImageCursor(image);
	}

	/**
	 * Initializes all components needed for a new Game.
	 */
	private void prepareGame(boolean newGame) {
		resetUIElements();
		GameState.getInstance().initiate();

		switchToScene(GameSceneType.LOADING_SCREEN);
		// load all the modules and save them in the gameModules-list
		prepareGamePage(newGame);
	}

	/**
	 * Preloads all the {@link GameModule}s and adds them to the list of modules.
	 * 
	 * @param newGame Whether a new Game is being prepared or a save game is loaded.
	 */
	private void prepareGamePage(boolean newGame) {
		// Create a task to load all the Modules without freezing the GUI
		Task<Integer> task = new Task<Integer>() {

			private double progress = 0.0;
			int numOfComponents = GameModuleDefinition.values().length + 1;

			@Override
			protected Integer call() throws Exception {

				try {
					this.updateProgress(0.0, 1.0);

					// load GamePage
					FXMLLoader gamePageLoader = new FXMLLoader(
							getClass().getClassLoader().getResource("fxml/gamepage.fxml"), resourceBundle);
					Parent gamePageRoot = gamePageLoader.load();
					gamePageController = (GamePageController) gamePageLoader.getController();
					sceneGamePage = new GameScene(gamePageRoot, GameSceneType.GAME_PAGE,
							gamePageLoader.getController());

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
								getClass().getClassLoader().getResource("fxml/module/" + moduleDefinition.fxmlFile),
								resourceBundle);

						// load root and controller of the module from the fxml
						Parent root = loader.load();
						GameModuleController controller = loader.getController();

						// create new GameModule from the type and add it to its view.
						GameModule module = new GameModule(root, moduleDefinition, controller);
						getGameView(moduleDefinition.viewType).addModule(module);

						// update the progressbar
						updateProgress();
					}

					initKeyboardControls();

					// start the game once everything is loaded
					startGame();
					initDepartments();

					if (newGame) {
						gameHudController.initTutorialCheck();
					}
				} catch (IOException e) {
					// TODO handle error if module could not be loaded.
					e.printStackTrace();
				}
				return 1;
			}

			private void updateProgress() {
				progress += 1.0 / numOfComponents;
				this.updateProgress(progress, 1.0);
			}
		};
		((LoadingScreenController) sceneLoadingScreen.getController()).initProgressBar(task.progressProperty());
		task.setOnFailed(e -> {
			System.out.println("Failed");
			e.getSource().getException().printStackTrace();
		});
		task.setOnCancelled(e -> {
			System.out.println("Cancelled");
			e.getSource().getException().printStackTrace();
		});
		new Thread(task).start();
	}

	/**
	 * Quits the game: Triggers a new {@link WindowEvent}, containing a
	 * WINDOW_CLOSE_REQUEST, which can then be handled by the Application. TODO
	 * maybe handle more stuff when ingame. (eg autosave)
	 */
	public void quitApplication() {
		window.fireEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	/**
	 * Switch to other language scene by reloading message properties.
	 */
	public void reloadProperties() {
		String newProperties;
		if (this.language.equals(Locale.ENGLISH)) {
			newProperties = "properties.main";
			this.language = Locale.GERMAN;
		} else {
			newProperties = "properties.main";
			this.language = Locale.ENGLISH;
		}

		resourceBundle = ResourceBundle.getBundle(newProperties, this.language);
		loadGameScenes();
		switchToScene(GameSceneType.MENU_MAIN);
	}

	/**
	 * Resets all the {@link GameView}s and Controllers that might still contain
	 * data from the previous games.
	 */
	private void resetUIElements() {
		gameHudController = null;
		gamePageController = null;
		gameMapController = null;
		gameViews.clear();
	}

	/**
	 * Start the Game by switching to the GamePage and starting the GameController
	 */
	private void startGame() {
		Platform.runLater(() -> {
			gamePageController.switchView(GameViewType.OVERVIEW);
			switchToScene(GameSceneType.GAME_PAGE);
		});
		Task<Void> task = new Task<Void>() {
			@Override
			public Void call() {
				GameController.getInstance().start();
				return null;
			}
		};
		new Thread(task).start();
	}

	/**
	 * Stop the game and the {@link GameController}.
	 */
	public void stopGame() {
		GameController.getInstance().terminateGame();
	}

	/**
	 * Switch to the specified scene.
	 * 
	 * @param sceneType The type of the {@link GameScene} to switch to.
	 */
	public void switchToScene(GameSceneType sceneType) {
		switch (sceneType) {
		case MENU_MAIN:
			sceneMenuMain.getController().update();
			window.getScene().setRoot(sceneMenuMain.getScene());
			break;
		case GAME_PAGE:
			window.getScene().setRoot(sceneGamePage.getScene());
			break;
		case LOADING_SCREEN:
			window.getScene().setRoot(sceneLoadingScreen.getScene());
			break;
		case CREDITS_PAGE:
			window.getScene().setRoot(sceneCreditsPage.getScene());
			break;
		case GAMELOST_PAGE:
			window.getScene().setRoot(sceneLostPage.getScene());
			break;
		case GAMEWON_PAGE:
			window.getScene().setRoot(sceneWonPage.getScene());
			break;
		default:
			break;
		}
	}

	/**
	 * (De-)activates the FullscreenMode, depending on whether it is active.
	 */
	public void toggleFullscreen() {
		window.setFullScreen(!window.isFullScreen());
	}

}
