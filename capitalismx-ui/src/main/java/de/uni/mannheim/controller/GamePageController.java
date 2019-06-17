package de.uni.mannheim.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import de.uni.mannheim.components.GameModule;
import de.uni.mannheim.components.GameViewType;
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

	// List of the currently displayed Modules
	private List<GameModule> currentActiveModules;

	// The type of content that is currently being displayed.
	private GameViewType currentActiveContentType = GameViewType.GAME_OVERVIEW;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		currentActiveModules = new ArrayList<GameModule>();

		// load modules

	}

	@Override
	public void update() {

	}
	
	/**
	 * Switches the displayed contentType by removing all {@link GameModule}s of that type.
	 * @param contentType
	 */
	public void switchContentType(GameViewType contentType) {
		for(GameModule module : currentActiveModules) {
			moduleGrid.getChildren().remove(module.getRootElement());
		}
	}

}
