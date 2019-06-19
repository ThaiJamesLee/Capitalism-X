package de.uni.mannheim.controller;

import java.net.URL;
import java.util.ResourceBundle;

import de.uni.mannheim.components.GameModule;
import de.uni.mannheim.components.GameView;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

/**
 * The {@link GameController} managing all actions on the GamePage.
 * 
 * @author Jonathan
 *
 */
public class GamePageController extends GameController {

	// The GridPane that contains all the modules.
	@FXML
	private GridPane moduleGrid;

	// The type of content that is currently being displayed.
	private GameView currentActiveView;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@Override
	public void update() {

	}

	/**
	 * Switches the displayed contentType by removing all {@link GameModule}s of
	 * that type.
	 */
	public void switchContentType() {
		// TODO new view
		for (GameModule module : currentActiveView.getModules()) {
			moduleGrid.getChildren().remove(module.getRootElement());
		}
	}

}
