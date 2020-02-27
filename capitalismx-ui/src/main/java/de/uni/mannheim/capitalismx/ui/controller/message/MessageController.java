package de.uni.mannheim.capitalismx.ui.controller.message;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.gamecontroller.GameState;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.GameNotification;
import de.uni.mannheim.capitalismx.ui.controller.gamepage.GamePageController;
import de.uni.mannheim.capitalismx.ui.eventlisteners.MessageEventListener;
import de.uni.mannheim.capitalismx.ui.utils.CssHelper;
import de.uni.mannheim.capitalismx.utils.data.MessageObject;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;

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
	private ArrayList<MessageSubjectController> messageSubjectSave;


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
		messageSubjectSave = new ArrayList<MessageSubjectController>();
	}

    /**
     * Receives a @MessageObject and adds it's content as components to the message window.
     *
     * @param m
     */
	public void addMessage(MessageObject m) {
		Parent messageSubject;
		Parent messageContent;
		MessageSubjectController msc;
		MessageContentController mcc;
		Locale lang = UIManager.getInstance().getLanguage();

		ResourceBundle bundle = ResourceBundle.getBundle(LangFILE, lang);

		FXMLLoader subjectLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/message/message_pane_subject.fxml"));
		FXMLLoader contentLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/message/message_pane_content.fxml"));

		try {
			messageSubject = subjectLoader.load();
			msc = subjectLoader.getController();
			try {
                msc.setSubjectSender(bundle.getString(m.getSender()));
                msc.setSubjectSubject(bundle.getString(m.getSubject()));
            } catch (Exception e){
                //System.out.println("Message Object loading exception" + e.getClass());
                //class java.util.MissingResourceException
			    msc.setSubjectSender(m.getSender());
			    msc.setSubjectSubject(m.getSender());
            }


			msc.setSubjectDate(m.getDate());
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    messages.add(0, messageSubject);
                    messageList.setItems(messages);
                    messageSubjectSave.add(0, msc);
                }
            });


			messageContent = contentLoader.load();
			mcc = contentLoader.getController();
			try {
                mcc.setContentSender(bundle.getString(m.getSender()));
                mcc.setContentSubject(bundle.getString(m.getSubject()));
                mcc.setContentContent(bundle.getString(m.getContent()));
            } catch (Exception e) {
                //System.out.println("Message Object loading exception" + e.getClass());
                //class java.util.MissingResourceException
                mcc.setContentSender(m.getSender());
                mcc.setContentSubject(m.getSubject());
                mcc.setContentContent(m.getContent());
            }
            mcc.setContentDate(m.getDate());
			if(m.getJumpTo()!=0){
				mcc.addJumpButton(m.getJumpTo());
			}
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    messageContentPane.setContent(messageContent);
                }
            });


			//m.setSubjectPanel(messageSubject);
			//m.setMessageContent(messageContent);
			messageSave.add(0, m);
			msc.setMessageContent(messageContent);

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		GameNotification notification = new GameNotification(m);
		UIManager.getInstance().getGameHudController().addNotification(notification);
	}

	public void setContent(Parent message){
		messageContentPane.setContent(message);


	}

	//todo:
	public void showMessage(MessageObject messageToShow){
		int index = messageSave.indexOf(messageToShow);
		messageList.getSelectionModel().select(index);
		setContent(messageSubjectSave.get(index).getMessageContent());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		CssHelper.replaceStylesheets(root.getStylesheets());
		GameState state = GameState.getInstance();
		state.addPropertyChangeListener(new MessageEventListener());


		/*
		messageClose.setOnAction(e -> {
			((GamePageController)(UIManager.getInstance().getSceneGame().getController())).toggleMessageWindow();
		});
		*/
	}
}
