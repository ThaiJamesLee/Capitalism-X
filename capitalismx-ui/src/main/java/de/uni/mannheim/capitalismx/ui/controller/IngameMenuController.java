package de.uni.mannheim.capitalismx.ui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.ui.application.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class IngameMenuController implements Initializable{
	
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
			((GamePageController)(Main.getManager().getSceneGame().getController())).removeIngameMenuPane();
		});
		
		ingameSave.setOnAction(e -> {
			((GamePageController)(Main.getManager().getSceneGame().getController())).removeIngameMenuPane();
		});
		
		ingameLoad.setOnAction(e -> {
			((GamePageController)(Main.getManager().getSceneGame().getController())).removeIngameMenuPane();
		});
		
		ingameSettings.setOnAction(e -> {
			((GamePageController)(Main.getManager().getSceneGame().getController())).removeIngameMenuPane();
		});
		
		ingameQuit.setOnAction(e -> {
			((GamePageController)(Main.getManager().getSceneGame().getController())).removeIngameMenuPane();
		});
		
	}
	
}
