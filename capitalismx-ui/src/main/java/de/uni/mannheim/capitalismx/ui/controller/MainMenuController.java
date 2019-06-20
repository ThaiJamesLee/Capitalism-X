package de.uni.mannheim.capitalismx.ui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.ui.application.Main;
import de.uni.mannheim.capitalismx.ui.components.GameSceneType;
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		newGameButton.setOnAction(e -> {
			System.out.println("Main Menu Button pressed!");
			Main.getManager().switchToScene(GameSceneType.GAME_PAGE);
		});

	}


}
