package de.uni.mannheim.capitalismx.ui.component;

import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * A scene in the game, that contains its controller, the {@link GameSceneType}
 * and the root-fxml-object ({@link Parent}) that simulates a {@link Scene}.
 * <br>
 * Note: The reason not to use actual {@link Scene}s is that switching between them breaks
 * the Fullscreen-Mode.
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
	private Initializable controller;

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

	public Initializable getController() {
		return controller;
	}

	/**
	 * Create a new {@link GameScene}.
	 * 
	 * @param scene      The actual {@link Parent}.
	 * @param type       The type of scene.
	 * @param controller The {@link Initializable} controller of the fxml-element.
	 */
	public GameScene(Parent scene, GameSceneType type, Initializable controller) {
		this.scene = scene;
		this.sceneType = type;
		this.controller = controller;
	}

}
