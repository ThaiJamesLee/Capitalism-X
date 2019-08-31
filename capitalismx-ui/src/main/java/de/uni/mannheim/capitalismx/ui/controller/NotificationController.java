package de.uni.mannheim.capitalismx.ui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.ui.application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class NotificationController implements Initializable {
	
	@FXML
	private Button notificationClose;
	
	@FXML
	private ListView notificationAll;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		notificationAll = new ListView<String>();
		ObservableList<String> items = FXCollections.observableArrayList("01.01.1990", "Capitalism-X simulation has started.");
		notificationAll.setItems(items);
		
		notificationClose.setOnAction(e -> {
			((GamePageController)(Main.getManager().getSceneGame().getController())).removeNotificationPane();
		});
		
	}
	
	public void addNotification() {
		
	}

}
