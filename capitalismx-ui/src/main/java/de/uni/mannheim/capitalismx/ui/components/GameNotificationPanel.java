package de.uni.mannheim.capitalismx.ui.components;

import java.io.IOException;

import de.uni.mannheim.capitalismx.ui.controller.NotificationController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class GameNotificationPanel {
	
	private Parent root;
	private NotificationController controller;
	/**
	 * Constructor for the {@link GameNotificationPanel}. Loads and saves all the
	 * FXML-files.
	 * @author Lixiang
	 */
	public GameNotificationPanel() {
		FXMLLoader loader = new FXMLLoader();
		try {
			setRoot(loader.load(getClass().getClassLoader().getResource("fxml/NotificationWindow2.fxml")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("");
		setController(loader.getController());
	}
	
	
	public NotificationController getController() {
		return controller;
	}
	public void setController(NotificationController controller) {
		this.controller = controller;
	}
	public Parent getRoot() {
		return root;
	}
	public void setRoot(Parent root) {
		this.root = root;
	}
	
	
}
