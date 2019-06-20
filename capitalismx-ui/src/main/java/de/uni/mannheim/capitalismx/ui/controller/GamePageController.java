package de.uni.mannheim.capitalismx.ui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.ui.application.Main;
import de.uni.mannheim.capitalismx.ui.components.GameModule;
import de.uni.mannheim.capitalismx.ui.components.GameView;
import de.uni.mannheim.capitalismx.ui.components.GameViewType;
import de.uni.mannheim.capitalismx.ui.utils.GridPosition;
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
		if(this.currentActiveView == null) {
			switchView(GameViewType.GAME_HR);
		}
		
	}
	
	
	

	/**
	 * Switches the displayed contentType by removing all {@link GameModule}s of
	 * that type.
	 */
	public void switchView(GameViewType viewType) {
		if(currentActiveView != null) {
			// remove all modules of current view
			for (GameModule module : currentActiveView.getModules()) {
				moduleGrid.getChildren().remove(module.getRootElement());
			}
		}
		//change current view and add modules
		currentActiveView = Main.getManager().getGameView(viewType);
		for (GameModule module : currentActiveView.getModules()) {
			GridPosition position = module.getGridPosition();
			moduleGrid.add(module.getRootElement(), position.getxStart(), position.getyStart(), position.getxSpan(), position.getySpan());
		}
	}

}
