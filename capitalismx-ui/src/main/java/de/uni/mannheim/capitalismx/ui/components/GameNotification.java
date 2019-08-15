package de.uni.mannheim.capitalismx.ui.components;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class GameNotification {

	private final int MAX_LENGTH_OF_MESSAGE = 100;

	/**
	 * The root object of the fx-content.
	 */
	private Parent root;

	/**
	 * The message to display.
	 */
	private String message;

	/**
	 * The sender, that the message is from.
	 */
	private String sender;
	// TODO add some icon/image?

	public Parent getRoot() {
		return root;
	}




	public GameNotification(String sender, String message) {
		this.message = shortenMessage(message);
		this.sender = sender;
		this.root = createRoot();
	}
	
	

	private Parent createRoot() {
		VBox root = new VBox();
		root.getStylesheets().add(getClass().getClassLoader().getResource("css/general.css").toExternalForm());
		root.getStylesheets().add(getClass().getClassLoader().getResource("css/notificationPane.css").toExternalForm());
		root.getStyleClass().add("notificationBackground");
		root.setSpacing(10);
		root.setPadding(new Insets(10, 15, 10, 15));
		Label senderLabel = new Label("New message from " + this.sender);
		senderLabel.getStyleClass().add("senderLabel");
		Label messageLabel = new Label(this.message);
		senderLabel.getStyleClass().add("messageLabel");
		root.getChildren().add(senderLabel);
		root.getChildren().add(messageLabel);
		return root;
	}



	private String shortenMessage(String message) {
		if(message.length() > MAX_LENGTH_OF_MESSAGE) {
			int indexToSplit = message.lastIndexOf(' ', MAX_LENGTH_OF_MESSAGE);
			if(indexToSplit <= 0) {
				//TODO throw Exception?
				return "";
			}
			message = message.substring(0, indexToSplit) + "...";
		}
		return message;
	}

}
