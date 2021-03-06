package de.uni.mannheim.capitalismx.ui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.component.GameSceneType;
import de.uni.mannheim.capitalismx.ui.util.CssHelper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

/**
 * Controller for the credits info Page
 * 
 * @author Alex
 *
 */
public class CreditsPageController implements Initializable {

	@FXML 
	public AnchorPane root;
	
	@FXML
	public Button mainMenuBtn;
	
	@FXML
	public Button quitBtn;
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		CssHelper.replaceStylesheets(root.getStylesheets());
		
		mainMenuBtn.setOnAction(e -> {
			UIManager.getInstance().switchToScene(GameSceneType.MENU_MAIN);
		});
		
		quitBtn.setOnAction(e -> {
			UIManager.getInstance().quitApplication();
		});
	}

}
