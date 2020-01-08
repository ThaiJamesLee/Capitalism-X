package de.uni.mannheim.capitalismx.ui.utils;

import java.io.IOException;

import org.controlsfx.control.PopOver;

import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.controller.general.UpdateableController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.util.Duration;

/**
 * Helper class, that provides some utility-methods to create PopOvers easily.
 * 
 * @author Jonathan
 *
 */
public class PopOverFactory {

	private PopOver popover = null;

	private UpdateableController popoverController;

	/**
	 * @return The generated {@link PopOver}.
	 */
	public PopOver getPopover() {
		return popover;
	}

	/**
	 * @return The {@link UpdateableController} of the PopOver.
	 */
	public UpdateableController getPopoverController() {
		return popoverController;
	}

	/**
	 * Create a standard overlay using the {@link PopOver}.
	 * 
	 * @param fxmlFile The path of the fxml-File, starting at the resource
	 *                 directory.
	 */
	public void createStandardOverlay(String fxmlFile) {
		FXMLLoader loader = new FXMLLoader(PopOverFactory.class.getClassLoader().getResource(fxmlFile),
				UIManager.getResourceBundle());
		Parent root;
		try {
			root = loader.load();
			popoverController = loader.getController();
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
	}

}
