package de.uni.mannheim.capitalismx.ui.components;

import de.uni.mannheim.capitalismx.ui.controller.GameController;
import de.uni.mannheim.capitalismx.ui.controller.UIController;
import javafx.scene.Scene;

/**
 * A scene in the game, that contains the actual {@link Scene}, the controller
 * and the {@link GameSceneType}.
 * 
 * @author Jonathan
 *
 */
public class GameScene {

	// The JavaFX-Scene-Element
	private Scene scene;
	// The type of scene
	private GameSceneType sceneType;
	// The controller of the scene
	private UIController controller;

	public Scene getScene() {
		return scene;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}

	public GameSceneType getSceneType() {
		return sceneType;
	}

	public void setSceneType(GameSceneType sceneType) {
		this.sceneType = sceneType;
	}

	public UIController getController() {
		return controller;
	}

	/**
	 * Create a new {@link GameScene}.
	 * 
	 * @param scene      The actual {@link Scene}.
	 * @param type       The type of scene.
	 * @param controller The controller of the fxml-element.
	 */
	public GameScene(Scene scene, GameSceneType type, UIController controller) {
		this.scene = scene;
		this.sceneType = type;
		this.controller = controller;
	}

}
