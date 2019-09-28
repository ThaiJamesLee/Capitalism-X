package de.uni.mannheim.capitalismx.ui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.ui.application.Main;
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
			Main.getManager().initGame();
		});
		
		
		switchButton.setOnAction(e -> {
			Main.getManager().reloadProperties();
		});

		
		fullscreenButton.setOnAction(e -> {
			Main.getManager().toggleFullscreen();
		});
		
		quitButton.setOnAction(e -> {
			Main.getManager().quitGame();
		});
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}
