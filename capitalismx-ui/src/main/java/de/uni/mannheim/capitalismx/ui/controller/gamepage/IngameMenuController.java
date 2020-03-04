package de.uni.mannheim.capitalismx.ui.controller.gamepage;

import java.net.URL;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.component.GameSceneType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 * Controller for the ingame menu which allows saving and loading.
 * 
 * @author Jonathan
 *
 */
public class IngameMenuController implements Initializable {

	@FXML
	private Button ingameContinue;

	@FXML
	private Button ingameSave;

	@FXML
	private Button ingameLoad;

	@FXML
	private Button ingameSettings;

	@FXML
	private Button ingameQuit;
	
	private boolean wasPausedBefore;

	public void setWasPausedBefore(boolean pausedBefore) {
		this.wasPausedBefore = pausedBefore;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		ingameContinue.setOnAction(e -> {
			if(!wasPausedBefore) {
				GameController.getInstance().resumeGame();
			}
			((GamePageController) (UIManager.getInstance().getSceneGame().getController())).toggleIngameMenu();
			wasPausedBefore = false;
		});

		ingameSave.setOnAction(e -> {
			GameController.getInstance().saveGame();
		});

		ingameLoad.setOnAction(e -> {
			UIManager.getInstance().stopGame();
			UIManager.getInstance().loadGame();
		});

		ingameSettings.setDisable(true);
		ingameSettings.setOnAction(e -> {
			((GamePageController) (UIManager.getInstance().getSceneGame().getController())).toggleIngameMenu();
		});

		ingameQuit.setOnAction(e -> {
			UIManager.getInstance().stopGame();
			UIManager.getInstance().switchToScene(GameSceneType.MENU_MAIN);
		});

	}

}
