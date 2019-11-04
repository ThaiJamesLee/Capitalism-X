package de.uni.mannheim.capitalismx.ui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.ui.application.CapXApplication;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MessageController implements Initializable {
	
	@FXML
	private Button messageClose;
	
	@FXML
	private VBox messageList;
	@FXML
	private ScrollPane messageContentPane;

	private GamePageController controllerReference;

	private ArrayList<MessageSubjectController> messageSubjectList;
	private ArrayList<MessageContentController> messageContentList;

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
	
	public void addMessage(String sender, String date, String message, String subject) {
		Parent messageSubject;
		Parent messageContent;
		MessageSubjectController msc;
		MessageContentController mcc;

		FXMLLoader subjectLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/messagePaneSubject.fxml"));
		FXMLLoader contentLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/messagePaneContent.fxml"));

		try {
			messageSubject = subjectLoader.load();
			msc = subjectLoader.getController();
			msc.setSubjectSender(sender);
			msc.setSubjectDate(date);
			msc.setSubjectSubject(subject);
			messageList.getChildren().add(messageSubject);

			messageContent = contentLoader.load();
			mcc = contentLoader.getController();
			mcc.setContentSender(sender);
			mcc.setContentDate(date);
			mcc.setContentSubject(subject);
			mcc.setContentContent(message);
			messageContentPane.setContent(messageContent);
			//messageContent = contentLoader.load();
			//messageSubject.subject = "Hi!";
			//system.out.print(messageSubject.subject);

			//Label btn = (Label) messageSubject.lookup("#sender");
			//((Label) messageSubject.lookup("#sender")).setText("Human Ressource Department");
			//controllerReference = ((GamePageController)(UIManager.getInstance().getSceneGame().getController()));
			//controllerReference.
			//((VBox) controllerReference.getMessagePaneReminder().lookup("#messageList")).
		} catch (IOException e) {
			e.printStackTrace();
		}

		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {





		messageClose.setOnAction(e -> {
			((GamePageController)(UIManager.getInstance().getSceneGame().getController())).toggleMessageWindow();
		});
	}

}
