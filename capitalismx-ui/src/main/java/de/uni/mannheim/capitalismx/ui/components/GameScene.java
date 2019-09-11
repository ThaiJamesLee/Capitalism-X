package de.uni.mannheim.capitalismx.ui.components;

import de.uni.mannheim.capitalismx.ui.controller.general.UpdateableController;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * A scene in the game, that contains the controller, the {@link GameSceneType}
 * and the root-fxml-object ({@link Parent}) that simulates a {@link Scene}.
 * <br>
 * Note: The reason to not actually use a scene is that switching Scenes breaks
 * the Fullscreen-Mode to switch scenes.
 * 
 * @author Jonathan
 *
 */
public class GameScene {

	// The Root element, simulating a Scene.
	private Parent scene;
	// The type of scene
	private GameSceneType sceneType;
	// The controller of the scene
	private UpdateableController controller;

	public Parent getScene() {
		return scene;
	}

	public void setScene(Parent scene) {
		this.scene = scene;
	}

	public GameSceneType getSceneType() {
		return sceneType;
	}

	public void setSceneType(GameSceneType sceneType) {
		this.sceneType = sceneType;
	}

	public UpdateableController getController() {
		return controller;
	}

	/**
	 * Create a new {@link GameScene}.
	 * 
	 * @param scene      The actual {@link Parent}.
	 * @param type       The type of scene.
	 * @param controller The controller of the fxml-element.
	 */
	public GameScene(Parent scene, GameSceneType type, UpdateableController controller) {
		this.scene = scene;
		this.sceneType = type;
		this.controller = controller;
	}

}
