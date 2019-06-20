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
public class Main extends Application {

	private static UIManager manager;

	public static UIManager getManager() {
		return manager;
	}

	@Override
	public void start(Stage primaryStage) {
		try {

			manager = new UIManager(primaryStage);
			manager.init();

			// set Stage boundaries to visible bounds of the main screen TODO adjust and
			// move somewhere else
			Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
			primaryStage.setX(primaryScreenBounds.getMinX());
			primaryStage.setY(primaryScreenBounds.getMinY());
			primaryStage.setWidth(primaryScreenBounds.getWidth());
			primaryStage.setHeight(primaryScreenBounds.getHeight());
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}
