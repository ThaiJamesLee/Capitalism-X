package de.uni.mannheim.capitalismx.ui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.gamelogic.GameController;
import de.uni.mannheim.capitalismx.gamelogic.GameState;
import de.uni.mannheim.capitalismx.ui.application.CapXApplication;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.controller.general.UpdateableController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.WindowEvent;

/**
 * Controller for the main menu.
 * 
 * @author Jonathan
 *
 */
public class MainMenuController implements UpdateableController {

	@FXML
	public Button newGameButton;

	@FXML
	public Button switchButton;
	
	@FXML
	public Button fullscreenButton;
	
	@FXML
	public Button quitButton;
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		newGameButton.setOnAction(e -> {
			UIManager.getInstance().newGame();
		});
		
		
		switchButton.setOnAction(e -> {
			UIManager.getInstance().reloadProperties();
		});

		
		fullscreenButton.setOnAction(e -> {
			UIManager.getInstance().toggleFullscreen();
		});
		
		quitButton.setOnAction(e -> {
			UIManager.getInstance().quitApplication();
		});
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}
