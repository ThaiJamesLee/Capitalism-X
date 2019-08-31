package de.uni.mannheim.capitalismx.ui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.ui.application.Main;
import de.uni.mannheim.capitalismx.ui.components.GameSceneType;
import de.uni.mannheim.capitalismx.ui.controller.general.UpdateableController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

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
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		newGameButton.setOnAction(e -> {
			Main.getManager().switchToScene(GameSceneType.GAME_PAGE);
		});
		
		
		switchButton.setOnAction(e -> {
			Main.getManager().reloadProperties();
		});

		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}
