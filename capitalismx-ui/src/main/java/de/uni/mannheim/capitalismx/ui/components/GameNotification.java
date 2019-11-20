package de.uni.mannheim.capitalismx.ui.components;

import java.io.IOException;

import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.utils.CssHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;

/**
 * An ingame notification, that can be used for the Messaging-System and
 * displays a custom message and the name of the sender.
 * 
 * @author Jonathan
 *
 */
public class GameNotification {

	/**
	 * Max length of a message that can be displayed on the notification. TODO check
	 * length (also for different scales?)
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

	@FXML
	private Label senderLabel, messageLabel;

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
		// TODO construct notification from Message-object, once implemented
		this.message = shortenMessage(message);
		this.sender = sender;
		this.root = loadRoot();
		
		CssHelper.replaceStylesheets(root.getStylesheets());
	}

	/**
	 * Loads the fxml-File specifying the structure of the notification and adds the
	 * content. //TODO direct link to message necessary
	 * 
	 * @return The root-{@link Parent} of the {@link GameNotification}.
	 */
	private Parent loadRoot() {
		FXMLLoader loader = new FXMLLoader(
				getClass().getClassLoader().getResource("fxml/components/game_notification.fxml"));
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		messageLabel.setText(message);
		senderLabel.setText(UIManager.getResourceBundle().getString("notification.sender") + " " + sender);
		
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
