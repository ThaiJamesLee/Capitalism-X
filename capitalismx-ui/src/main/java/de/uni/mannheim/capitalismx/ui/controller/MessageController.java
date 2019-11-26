package de.uni.mannheim.capitalismx.ui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.ui.application.CapXApplication;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.utils.CssHelper;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MessageController implements Initializable {
	
	private static final String LangFILE = "properties.messages";
	
	@FXML
	private AnchorPane root;
	
	@FXML
	private Button messageClose;
	
	@FXML
	private ListView messageList;

	private ObservableList<Parent> messages = FXCollections.observableArrayList();
	@FXML
	private ScrollPane messageContentPane;

	private GamePageController controllerReference;
	private ArrayList<MessageObject> messageSave;

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
	public MessageController(){
		messageSave = new ArrayList<MessageObject>();
	}


	public void addMessage(String sender, String date, String subject, String message, boolean isInternal) {
		Parent messageSubject;
		Parent messageContent;
		MessageSubjectController msc;
		MessageContentController mcc;
		Locale lang = UIManager.getInstance().getLanguage();

		ResourceBundle bundle = ResourceBundle.getBundle(LangFILE, lang);

		FXMLLoader subjectLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/messagePaneSubject.fxml"));
		FXMLLoader contentLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/messagePaneContent.fxml"));

		try {
			messageSubject = subjectLoader.load();
			msc = subjectLoader.getController();
			msc.setSubjectSender(bundle.getString(sender));
			msc.setSubjectDate(date);
			msc.setSubjectSubject(bundle.getString(subject));
			messages.add(messageSubject);
			messageList.setItems(messages);

			messageContent = contentLoader.load();
			mcc = contentLoader.getController();
			mcc.setContentSender(bundle.getString(sender));
			mcc.setContentDate(date);
			mcc.setContentSubject(bundle.getString(subject));
			mcc.setContentContent(bundle.getString(message));
			messageContentPane.setContent(messageContent);

			MessageObject messageReference = new MessageObject(sender, date, subject, message, isInternal, messageSubject, messageContent);
			messageSave.add(messageReference);
			msc.setMessageReference(messageReference);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void setContent(MessageObject message){
		messageContentPane.setContent(message.getMessageContent());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		CssHelper.replaceStylesheets(root.getStylesheets());



		/*
		messageClose.setOnAction(e -> {
			((GamePageController)(UIManager.getInstance().getSceneGame().getController())).toggleMessageWindow();
		});
		*/
	}
}
