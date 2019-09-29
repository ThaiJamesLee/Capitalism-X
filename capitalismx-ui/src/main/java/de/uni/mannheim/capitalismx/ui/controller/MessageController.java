package de.uni.mannheim.capitalismx.ui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.ui.application.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MessageController implements Initializable {
	
	@FXML
	private Button messageClose;
	
	@FXML
	private VBox messageList;
	
//	@Override
//	public void initialize(URL location, ResourceBundle resources) {
//		notificationAll = new ListView<String>();
//		ObservableList<String> items = FXCollections.observableArrayList("01.01.1990", "Capitalism-X simulation has started.");
//		notificationAll.setItems(items);
//		
//		messageClose.setOnAction(e -> {
//			((GamePageController)(Main.getManager().getSceneGame().getController())).removeMessagePane();
//		});
//		
//	}
	
	public void addMessage(String sender, String subject, String message) {
		HBox messageLine = new HBox();
		Label subjectLabel = new Label(subject);
		
		
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		messageClose.setOnAction(e -> {
			System.out.println("message click!");
			((GamePageController)(Main.getManager().getSceneGame().getController())).removeMessagePane();
		});
	}

}
