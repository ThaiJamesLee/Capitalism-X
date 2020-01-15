package de.uni.mannheim.capitalismx.ui.application;

import java.util.Optional;

import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.ui.components.GameAlert;
import de.uni.mannheim.capitalismx.ui.utils.CssHelper;
import de.uni.mannheim.capitalismx.ui.utils.GameResolution;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination.ModifierValue;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * The main class, starting the application.
 * 
 * @author Jonathan
 *
 */
public class CapXApplication extends Application {

	private static final boolean testMode = false;
	private Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

	@Override
	public void start(Stage primaryStage) {
		try {
			loadFonts();

			prepareScreen(primaryStage);

			GameResolution resolution = new GameResolution((int) primaryScreenBounds.getWidth(),
					(int) primaryScreenBounds.getHeight(), CssHelper.getOptimalResolution(primaryScreenBounds));

			new UIManager(primaryStage, resolution);
			
			primaryStage.setFullScreenExitHint(UIManager.getLocalisedString("hint.fullscreen"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void prepareScreen(Stage window) {
		// set Stage boundaries to visible bounds of the main screen TODO
		// adjust and move somewhere else
		window.setX(primaryScreenBounds.getMinX());
		window.setY(primaryScreenBounds.getMinY());
		window.setWidth(primaryScreenBounds.getWidth());
		window.setHeight(primaryScreenBounds.getHeight());
		// disable exiting the fullscreen with ESCAPE, set it to shift + escape for the
		// Main menu
		window.setFullScreenExitKeyCombination(new KeyCodeCombination(KeyCode.ESCAPE, ModifierValue.DOWN,
				ModifierValue.ANY, ModifierValue.ANY, ModifierValue.ANY, ModifierValue.ANY));
//		window.setMaximized(true);
		window.setResizable(false);
		window.setOnCloseRequest(e -> closeStage(e, window));
		window.show();
	}

	private void closeStage(WindowEvent e, Stage primaryStage) {
		if (!testMode) {
			GameAlert closeConfirmation = new GameAlert(AlertType.CONFIRMATION, "Quit the game", "Do you really want to quit?");
			Optional<ButtonType> response = closeConfirmation.showAndWait();

			// closes the application if the user confirms
		    if (response.isPresent() && response.get().equals(ButtonType.OK)) {

				UIManager.getInstance().stopGame();
		    	//TODO save game if ingame?
		    } else {
		    	e.consume();
		    }
		}
	}

	private void loadFonts() {
		Font.loadFont(CapXApplication.class.getResource("/fonts/Prime-Regular.ttf").toExternalForm(), 10);
		Font.loadFont(CapXApplication.class.getResource("/fonts/Prime-Light.ttf").toExternalForm(), 10);
		Font.loadFont(CapXApplication.class.getResource("/fonts/Reckoner.ttf").toExternalForm(), 10);
		Font.loadFont(CapXApplication.class.getResource("/fonts/Reckoner_Bold.ttf").toExternalForm(), 10);
	}

	public static void main(String[] args) {
		launch(args);
	}

}
