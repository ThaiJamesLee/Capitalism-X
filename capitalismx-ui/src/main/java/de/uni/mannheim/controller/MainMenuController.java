package de.uni.mannheim.controller;

import java.net.URL;
import java.util.ResourceBundle;

import de.uni.mannheim.application.Main;
import de.uni.mannheim.components.GameSceneType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class MainMenuController implements Initializable {

	@FXML
	private Button newGameButton;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		newGameButton.setOnAction(e -> {
			Main.getManager().switchToScene(GameSceneType.GAME_PAGE);
		});
		
	}
	
	
}
