package de.uni.mannheim.capitalismx.ui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.ui.application.CapXApplication;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.utils.MessageObject;
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

	private ArrayList<MessageObject> messages = new ArrayList<MessageObject>();
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

	public String langFileSwitcher(String lang){
		String langFile;
		switch (lang) {
			case "EN":
				langFile = "properties.messages_en";
				break;
			case "DE":
				langFile = "properties.messages_de";
				break;
			default:
				langFile = "properties.messages";
		}
		return langFile;
	}

	public void addMessage(String sender, String date, String subject, String message, boolean isInternal) {
		Parent messageSubject;
		Parent messageContent;
		MessageSubjectController msc;
		MessageContentController mcc;
		String lang = UIManager.getInstance().getLanguage();
		String langFile;
		ResourceBundle langBundle;

		messages.add(new MessageObject(sender, date, subject, message, isInternal));

		langFile = langFileSwitcher(lang);
		ResourceBundle bundle = ResourceBundle.getBundle(langFile);

		FXMLLoader subjectLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/messagePaneSubject.fxml"));
		FXMLLoader contentLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/messagePaneContent.fxml"));
/*
		bundle.getString("sen.event1");
		bundle.getString("sub.event1");
		bundle.getString("con.event1");
*/
		try {
			messageSubject = subjectLoader.load();
			msc = subjectLoader.getController();
			msc.setSubjectSender(bundle.getString("sen.event1"));
			msc.setSubjectDate(date);
			msc.setSubjectSubject(bundle.getString("sub.event1"));
			messageList.getChildren().add(messageSubject);

			messageContent = contentLoader.load();
			mcc = contentLoader.getController();
			mcc.setContentSender(bundle.getString("sen.event1"));
			mcc.setContentDate(date);
			mcc.setContentSubject(bundle.getString("sub.event1"));
			mcc.setContentContent(bundle.getString("con.event1"));
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




		/*
		messageClose.setOnAction(e -> {
			((GamePageController)(UIManager.getInstance().getSceneGame().getController())).toggleMessageWindow();
		});
		*/
	}

}
