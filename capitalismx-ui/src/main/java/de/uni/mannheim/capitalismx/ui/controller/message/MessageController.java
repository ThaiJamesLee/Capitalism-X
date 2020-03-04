package de.uni.mannheim.capitalismx.ui.controller.message;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.gamecontroller.GameState;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.component.GameViewType;
import de.uni.mannheim.capitalismx.ui.component.general.GameNotification;
import de.uni.mannheim.capitalismx.ui.controller.gamepage.GamePageController;
import de.uni.mannheim.capitalismx.ui.eventlistener.MessageEventListener;
import de.uni.mannheim.capitalismx.ui.util.CssHelper;
import de.uni.mannheim.capitalismx.utils.data.MessageObject;
import javafx.application.Platform;
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
import javafx.scene.layout.VBox;

public class MessageController implements Initializable {
	
	private static final String LangFILE = "properties.messages";

	private GameViewType viewID;

	private int index = 0;

	Locale lang = UIManager.getInstance().getLanguage();
	ResourceBundle bundle = ResourceBundle.getBundle(LangFILE, lang);

	@FXML
	private AnchorPane root;
	
	@FXML
	private Button messageClose;
	
	@FXML
	private ListView messageList;


	private ObservableList<Parent> messages = FXCollections.observableArrayList();
	@FXML
	private ScrollPane messageContentPane;

	@FXML
	private Label contentSender;
	@FXML
	private Label contentDate;
	@FXML
	private Label contentSubject;
	@FXML
	private Label contentContent;
	@FXML
	private VBox contentVBox;
	@FXML
	private Button jumpButton;

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



		FXMLLoader subjectLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/message/message_pane_subject.fxml"));
		//FXMLLoader contentLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/message/message_pane_content.fxml"));

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
			    msc.setSubjectSubject(m.getSubject());
            }


			msc.setSubjectDate(m.getDate());
			/*
            Platform.runLater(new Runnable() {
                @Override
                public void run() {

			 */
			messages.add(0, messageSubject);
			messageList.setItems(messages);
			messageSubjectSave.add(0, msc);
                    /*
                }
            });

                     */


			//messageContent = contentLoader.load();
			//mcc = contentLoader.getController();
			try {
				/*
                mcc.setContentSender(bundle.getString(m.getSender()));
                mcc.setContentSubject(bundle.getString(m.getSubject()));
                mcc.setContentContent(bundle.getString(m.getContent()));
				 */
				contentSender.setText(bundle.getString(m.getSender()));
				contentSubject.setText(bundle.getString(m.getSubject()));
				contentContent.setText(bundle.getString(m.getContent()));
            } catch (Exception e) {
                //System.out.println("Message Object loading exception" + e.getClass());
                //class java.util.MissingResourceException
				/*
                mcc.setContentSender(m.getSender());
                mcc.setContentSubject(m.getSubject());
                mcc.setContentContent(m.getContent());

				 */
				contentSender.setText(m.getSender());
				contentSubject.setText(m.getSubject());
				contentContent.setText(m.getContent());

            }
            contentDate.setText(m.getDate());
			if(m.getJumpTo()!=0){
				viewID = GameViewType.getTypeById(m.getJumpTo());
				jumpButton.setDisable(false);
				jumpButton.setOpacity(1);
				/*
				GameViewType viewID = GameViewType.getTypeById(m.getJumpTo());
				Button btn = new Button("Jump to Menu");
				btn.getStyleClass().add("btn_standard");
				btn.setOnAction(action -> {
					UIManager.getInstance().getGamePageController().switchView(viewID);
					UIManager.getInstance().getGamePageController().toggleMessageWindow();
				});
				contentVBox.getChildren().add(btn);

				 */
			} else {
				jumpButton.setDisable(true);
				jumpButton.setOpacity(0);
			}


			//m.setSubjectPanel(messageSubject);
			//m.setMessageContent(messageContent);
			messageSave.add(m);
			msc.setIndex(index);
			index++;

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		GameNotification notification = new GameNotification(m);
		UIManager.getInstance().getGameHudController().addNotification(notification);
	}

	public void jumpView(){
		UIManager.getInstance().getGamePageController().switchView(viewID);
		UIManager.getInstance().getGamePageController().toggleMessageWindow();
	}

	public void setContent(int index){
		MessageObject m = messageSave.get(index);
		try {
			contentSender.setText(bundle.getString(m.getSender()));
			contentSubject.setText(bundle.getString(m.getSubject()));
			contentContent.setText(bundle.getString(m.getContent()));
		} catch (Exception e) {
			contentSender.setText(m.getSender());
			contentSubject.setText(m.getSubject());
			contentContent.setText(m.getContent());

		}
		contentDate.setText(m.getDate());
		if(m.getJumpTo()!=0){
			viewID = GameViewType.getTypeById(m.getJumpTo());
			jumpButton.setDisable(false);
			jumpButton.setOpacity(1);
		} else {
			jumpButton.setDisable(true);
			jumpButton.setOpacity(0);
		}
	}

	//todo:
	public void showMessage(MessageObject messageToShow){
		int index = messageSave.indexOf(messageToShow);
		messageList.getSelectionModel().select(index);
		setContent(messageSubjectSave.get(index).getIndex());
	}

	public ArrayList<MessageObject> getMessageSave() {
		return messageSave;
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		CssHelper.replaceStylesheets(root.getStylesheets());
		GameState state = GameState.getInstance();
		state.addPropertyChangeListener(new MessageEventListener());

		jumpButton.setDisable(true);
		jumpButton.setOpacity(0);
		/*
		messageClose.setOnAction(e -> {
			((GamePageController)(UIManager.getInstance().getSceneGame().getController())).toggleMessageWindow();
		});
		*/
	}
}
