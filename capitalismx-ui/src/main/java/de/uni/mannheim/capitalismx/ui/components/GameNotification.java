package de.uni.mannheim.capitalismx.ui.components;

import java.io.IOException;

import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.utils.CssHelper;
import de.uni.mannheim.capitalismx.ui.utils.MessageObject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.util.Duration;

/**
 * An ingame notification, that can be used for the Messaging-System and
 * displays a custom message and the name of the sender.
 * 
 * @author Jonathan
 *
 */
public class GameNotification {

	/**
	 * The {@link MessageObject} the notification should display.
	 */
	private MessageObject message;

	/**
	 * Max length of a message that can be displayed on the notification. TODO check
	 * length (also for different scales?)
	 */
	private final static int MAX_LENGTH_OF_MESSAGE = 140;
	/**
	 * The standard Duration to display a notification for.
	 */
	public final static Duration STANDARD_DISPLAY_DURATION = Duration.millis(4500);

	/**
	 * The root object of the fx-content.
	 */
	private Parent root;

	/**
	 * The {@link Duration} to display this notification for.
	 */
	private Duration displayDuration;

	// TODO add some icon/image?

	public Parent getRoot() {
		return root;
	}

	public Duration getDisplayDuration() {
		return displayDuration;
	}

	@FXML
	private Label senderLabel, subjectLabel, messageLabel;

	/**
	 * Creates a new {@link GameNotification} for the given message, that can than
	 * be displayed on the GamePage.
	 * 
	 * @param message The message to display a preview of. <b>Note</b>: The text of
	 *                the message will be abbreviated if it exceeds the given length
	 *                limit MAX_LENGTH_OF_MESSAGE, so that it fits on the
	 *                notification.
	 */
	public GameNotification(MessageObject message) {
		this.message = message;
		this.root = createRoot();
		this.displayDuration = STANDARD_DISPLAY_DURATION;

		CssHelper.replaceStylesheets(root.getStylesheets());
	}

	/**
	 * Creates a new {@link GameNotification} for the given message, that can than
	 * be displayed on the GamePage.
	 * 
	 * @param message         The message to display a preview of. <b>Note</b>: The
	 *                        text of the message will be abbreviated if it exceeds
	 *                        the given length limit MAX_LENGTH_OF_MESSAGE, so that
	 *                        it fits on the notification.
	 * @param displayDuration The {@link Duration} to display this notification for.
	 */
	public GameNotification(MessageObject message, Duration displayDuration) {
		this.message = message;
		this.root = createRoot();
		this.displayDuration = displayDuration;

		CssHelper.replaceStylesheets(root.getStylesheets());
	}

	/**
	 * Loads the fxml-File specifying the structure of the notification and adds the
	 * content. //TODO direct link to message necessary
	 * 
	 * @return The root-{@link Parent} of the {@link GameNotification}.
	 */
	private Parent createRoot() {
		FXMLLoader loader = new FXMLLoader(
				getClass().getClassLoader().getResource("fxml/components/game_notification.fxml"));
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		messageLabel.setText(shortenMessage(message.getContent()));
		subjectLabel.setText(message.getSubject());
		senderLabel.setText(UIManager.getResourceBundle().getString("notification.sender") + " " + message.getSender());

		return loader.getRoot();
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
