package de.uni.mannheim.capitalismx.ui.application;

import java.util.Optional;

import de.uni.mannheim.capitalismx.ui.components.general.GameAlert;
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

	private Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

	@Override
	public void start(Stage primaryStage) {
		try {
			loadFonts();

			prepareStage(primaryStage);

			GameResolution resolution = new GameResolution((int) primaryScreenBounds.getWidth(),
					(int) primaryScreenBounds.getHeight(), CssHelper.getOptimalResolution(primaryScreenBounds));

			new UIManager(primaryStage, resolution);

			primaryStage.setFullScreenExitHint(UIManager.getLocalisedString("hint.fullscreen"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Configure and display the {@link Stage}.
	 * 
	 * @param window The {@link Stage} to configure.
	 */
	private void prepareStage(Stage window) {
		// set Stage boundaries to visible bounds of the main screen
		window.setX(primaryScreenBounds.getMinX());
		window.setY(primaryScreenBounds.getMinY());
		window.setWidth(primaryScreenBounds.getWidth());
		window.setHeight(primaryScreenBounds.getHeight());
		// disable exiting the fullscreen with ESCAPE, set it to shift + escape for the
		// Main menu
		window.setFullScreenExitKeyCombination(new KeyCodeCombination(KeyCode.F12, ModifierValue.ANY,
				ModifierValue.ANY, ModifierValue.ANY, ModifierValue.ANY, ModifierValue.ANY));
//		window.setMaximized(true);
		window.setResizable(false);
		window.setOnCloseRequest(e -> closeStage(e));
		window.show();
	}

	/**
	 * Can be called on when a {@link WindowEvent} is caught. Asks the player to
	 * confirm and stops the game before closing the {@link Application}.
	 * 
	 * @param e The {@link WindowEvent} that was fired.
	 */
	private void closeStage(WindowEvent e) {
		GameAlert closeConfirmation = new GameAlert(AlertType.CONFIRMATION, UIManager.getLocalisedString("dialog.quit.title"),
				UIManager.getLocalisedString("dialog.quit.description"));
		Optional<ButtonType> response = closeConfirmation.showAndWait();

		// closes the application if the user confirms
		if (response.isPresent() && response.get().equals(ButtonType.OK)) {
			UIManager.getInstance().stopGame();
		} else {
			e.consume();
		}
	}

	/**
	 * Loads the fonts defined in the css. Necessary, so that the application can
	 * find them.
	 */
	private void loadFonts() {
		Font.loadFont(CapXApplication.class.getResource("/fonts/Prime-Regular.ttf").toExternalForm(), 10);
		Font.loadFont(CapXApplication.class.getResource("/fonts/Prime-Light.ttf").toExternalForm(), 10);
		Font.loadFont(CapXApplication.class.getResource("/fonts/Reckoner.ttf").toExternalForm(), 10);
		Font.loadFont(CapXApplication.class.getResource("/fonts/Reckoner_Bold.ttf").toExternalForm(), 10);
	}

	/**
	 * Starts the {@link Application}.
	 * 
	 * @param args Optional arguments. (Not used)
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
