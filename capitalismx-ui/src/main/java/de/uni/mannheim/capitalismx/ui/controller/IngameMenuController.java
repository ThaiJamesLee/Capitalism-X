package de.uni.mannheim.capitalismx.ui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.GameSceneType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class IngameMenuController implements Initializable {

	@FXML
	public Button ingameContinue;

	@FXML
	public Button ingameSave;

	@FXML
	public Button ingameLoad;

	@FXML
	public Button ingameSettings;

	@FXML
	public Button ingameQuit;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		ingameContinue.setOnAction(e -> {
			GameController.getInstance().resumeGame();
			((GamePageController) (UIManager.getInstance().getSceneGame().getController())).toggleIngameMenu();
		});

		ingameSave.setOnAction(e -> {
			GameController.getInstance().saveGame();
		});

		ingameLoad.setOnAction(e -> {
			UIManager.getInstance().stopGame();
			UIManager.getInstance().loadGame();
		});

		ingameSettings.setOnAction(e -> {
			((GamePageController) (UIManager.getInstance().getSceneGame().getController())).toggleIngameMenu();
		});

		ingameQuit.setOnAction(e -> {
			UIManager.getInstance().stopGame();
			UIManager.getInstance().switchToScene(GameSceneType.MENU_MAIN);
		});

	}

}
