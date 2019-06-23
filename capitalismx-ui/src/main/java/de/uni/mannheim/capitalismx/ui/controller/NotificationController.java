package de.uni.mannheim.capitalismx.ui.controller;

import java.awt.List;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class NotificationController extends UIController{
	
	@FXML
	private Button notificationClose;
	
	@FXML
	private ListView notificationAll;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		notificationAll = new ListView<String>();
		ObservableList<String> items = FXCollections.observableArrayList("01.01.1990", "Capitalism-X simulation has started.");
		notificationAll.setItems(items);
	}
	
	public void addNotification() {
		
	}

}
