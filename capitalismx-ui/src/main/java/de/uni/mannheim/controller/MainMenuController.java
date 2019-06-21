package de.uni.mannheim.controller;

import java.net.URL;
import java.util.ResourceBundle;

import de.uni.mannheim.application.Main;
import de.uni.mannheim.components.GameSceneType;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Controller for the main menu.
 * 
 * @author Jonathan
 *
 */
public class MainMenuController extends UIController {

	@FXML
	private Button newGameButton;

	@FXML
	private Button switchButton;
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		newGameButton.setOnAction(e -> {
			Main.getManager().switchToScene(GameSceneType.GAME_PAGE);
		});
		
		
		switchButton.setOnAction(e -> {
			Main.getManager().reloadProperties();
		});

	}

}
