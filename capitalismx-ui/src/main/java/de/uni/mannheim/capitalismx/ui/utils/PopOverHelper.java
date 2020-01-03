package de.uni.mannheim.capitalismx.ui.utils;

import java.io.IOException;

import org.controlsfx.control.PopOver;

import de.uni.mannheim.capitalismx.ui.application.UIManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.util.Duration;

/**
 * Helper class, that provides some utility-methods to create PopOvers easily.
 * 
 * @author Jonathan
 *
 */
public class PopOverHelper {

	public static PopOver createStandardOverlay(String fxmlFile) {
		PopOver popover = null;
		FXMLLoader loader = new FXMLLoader(
				PopOverHelper.class.getClassLoader().getResource(fxmlFile),
				UIManager.getResourceBundle());
		Parent root;
		try {
			root = loader.load();
			CssHelper.replaceStylesheets(root.getStylesheets());
			root.getStyleClass().add("popover_pane");
			popover = new PopOver(root);
			popover.setDetachable(false);
			popover.setArrowSize(0.0);
			popover.centerOnScreen();
			popover.setFadeInDuration(Duration.millis(50));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return popover;
	}
	
}
