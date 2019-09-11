package de.uni.mannheim.capitalismx.ui.components;

import de.uni.mannheim.capitalismx.ui.utils.AnchorPaneHelper;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;

/**
 * An ingame notification, that can be used for the Messaging-System and
 * displays a custom message and the name of the sender.
 * 
 * @author Jonathan
 *
 */
public class GameNotification {

	/**
	 * Max length of a message that can be displayed on the notification. TODO check length (also for different scales?)
	 */
	private final static int MAX_LENGTH_OF_MESSAGE = 120;

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

	/**
	 * Creates a new {@link GameNotification} for the given name of the sender and
	 * message, that can than be displayed on the GamePage.
	 * 
	 * @param sender  The name of the sender the message is from.
	 * @param message The message to display a preview of. <b>Note</b>: The message
	 *                will be abbreviated if it exceeds the given length limit
	 *                MAX_LENGTH_OF_MESSAGE, so that it fits on the notification.
	 */
	public GameNotification(String sender, String message) {
		this.message = shortenMessage(message);
		this.sender = sender;
		this.root = createRoot();
	}

	private Parent createRoot() {
		// create new GridPane as root for the notification
		GridPane root = new GridPane();

		// general styling the notification
		root.getStylesheets().add(getClass().getClassLoader().getResource("css/general.css").toExternalForm());
		root.getStylesheets().add(getClass().getClassLoader().getResource("css/notificationPane.css").toExternalForm());
		root.getStyleClass().add("notificationBackground");
		root.setPadding(new Insets(10, 10, 10, 10));
		root.setHgap(10);

		// Prepare label for the sender
		Label senderLabel = new Label(this.sender);
		senderLabel.getStyleClass().add("senderLabel");
		senderLabel.setTextAlignment(TextAlignment.CENTER);
		AnchorPaneHelper.snapNodeToAnchorPane(senderLabel);

		// Prepare label for the message
		Label messageLabel = new Label(this.message);
		messageLabel.getStyleClass().add("messageLabel");
		messageLabel.setWrapText(true);

		// Prepare an anchorPane for the sender
		AnchorPane senderPane = new AnchorPane();
		senderPane.getChildren().add(senderLabel);

		senderPane.getStyleClass().add("senderPane");
		// add two columns for the sender and the message
		ColumnConstraints col1 = new ColumnConstraints();
		col1.setPercentWidth(20);
		ColumnConstraints col2 = new ColumnConstraints();
		col2.setPercentWidth(80);
		root.getColumnConstraints().addAll(col1, col2);

		root.add(senderPane, 0, 0);
		root.add(messageLabel, 1, 0);
		return root;
	}

	/**
	 * Checks whether the given message is to long to be displayed and creates a
	 * preview of it.
	 * 
	 * @param message The message to display.
	 * @return The abbreviated version of the message.
	 */
	private String shortenMessage(String message) {
		if (message.length() > MAX_LENGTH_OF_MESSAGE) {
			int indexToSplit = message.lastIndexOf(' ', MAX_LENGTH_OF_MESSAGE);
			if (indexToSplit <= 0) {
				// TODO throw Exception?
				return "";
			}
			message = message.substring(0, indexToSplit) + "...";
		}
		return message;
	}

}
