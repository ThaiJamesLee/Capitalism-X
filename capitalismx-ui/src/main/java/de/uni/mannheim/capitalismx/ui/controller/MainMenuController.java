package de.uni.mannheim.capitalismx.ui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.gamecontroller.gamesave.SaveGameHandler;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.GameSceneType;
import de.uni.mannheim.capitalismx.ui.controller.general.UpdateableController;
import de.uni.mannheim.capitalismx.ui.utils.CssHelper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

/**
 * Controller for the main menu.
 * 
 * @author Jonathan
 *
 */
public class MainMenuController implements UpdateableController {

	@FXML
	public AnchorPane root;

	@FXML
	public Button newGameButton;

	@FXML
	public Button continueGameButton;

//	@FXML
//	public Button leaderboardButton;

	@FXML
	public Button creditsButton;

	@FXML
	public Button switchButton;

	@FXML
	public Button fullscreenButton;

	@FXML
	public Button quitButton;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		CssHelper.replaceStylesheets(root.getStylesheets());

		newGameButton.setOnAction(e -> {
			UIManager.getInstance().newGame();
		});

		creditsButton.setOnAction(e -> {
			UIManager.getInstance().switchToScene(GameSceneType.CREDITS_PAGE);
		});

//		leaderboardButton.setOnAction(e -> {
//			UIManager.getInstance().switchToScene(GameSceneType.GAMELOST_PAGE);
//		});

		switchButton.setOnAction(e -> {
			UIManager.getInstance().reloadProperties();
		});

		fullscreenButton.setOnAction(e -> {
			UIManager.getInstance().toggleFullscreen();
		});

		quitButton.setOnAction(e -> {
			UIManager.getInstance().quitApplication();
		});
		
		update();
	}

	@Override
	public void update() {
		SaveGameHandler saveHandler = new SaveGameHandler();
		if (saveHandler.saveGameExists()) {
			continueGameButton.setDisable(false);
			continueGameButton.setOnAction(e -> {
				UIManager.getInstance().loadGame();
			});
		} else {
			continueGameButton.setDisable(true);
		}
	}

}
