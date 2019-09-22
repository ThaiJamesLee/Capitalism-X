package de.uni.mannheim.capitalismx.ui.application;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * The main class, starting the application.
 * 
 * @author Jonathan
 *
 */
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Font;
import javafx.scene.control.ButtonType;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 * The main class, starting the application.
 * 
 * @author Jonathan
 *
 */
public class Main extends Application {

	private static final boolean testMode = true;
	private static UIManager manager;
	private Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

	public static UIManager getManager() {
		return manager;
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			loadFonts();
			
			manager = new UIManager(primaryStage);

			// set Stage boundaries to visible bounds of the main screen TODO
			// adjust and move somewhere else
//			primaryStage.setFullScreen(true);
			primaryStage.setX(primaryScreenBounds.getMinX());
			primaryStage.setY(primaryScreenBounds.getMinY());
			primaryStage.setWidth(primaryScreenBounds.getWidth());
			primaryStage.setHeight(primaryScreenBounds.getHeight());
			primaryStage.setMaximized(true);
//			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.setOnCloseRequest(e -> closeStage(e, primaryStage));
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void closeStage(WindowEvent e, Stage primaryStage) {
		if (!testMode) {
			Alert closeConfirmation = new Alert(AlertType.CONFIRMATION, "Do you really want to quit?", ButtonType.YES,
					ButtonType.NO);
			closeConfirmation.showAndWait();
		}
	}
	
	private void loadFonts() {
		Font.loadFont(Main.class.getResource("/fonts/Prime-Regular.ttf").toExternalForm(), 10);
		Font.loadFont(Main.class.getResource("/fonts/Prime-Light.ttf").toExternalForm(), 10);
	}

	public static void main(String[] args) {
		launch(args);
	}

}
