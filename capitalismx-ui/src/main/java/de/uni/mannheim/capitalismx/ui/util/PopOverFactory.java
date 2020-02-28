package de.uni.mannheim.capitalismx.ui.util;

import java.io.IOException;

import org.controlsfx.control.PopOver;

import de.uni.mannheim.capitalismx.ui.application.UIManager;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
	
	private Initializable popoverController;

	/**
	 * @return The generated {@link PopOver}.
	 */
	public PopOver getPopover() {
		return popover;
	}

	/**
	 * @return The {@link Initializable} Controller of the PopOver.
	 */
	public Initializable getPopoverController() {
		return popoverController;
	}
	
	/**
	 * Loads the content of the {@link PopOver} from the given fxml-File and creates
	 * a standard overlay with it.
	 * 
	 * @param fxmlFile The path and name of the fxml-file to load from.
	 */
	public void createStandardPopover(String fxmlFile) {
		FXMLLoader loader = new FXMLLoader(PopOverFactory.class.getClassLoader().getResource(fxmlFile),
				UIManager.getResourceBundle());
		Parent root;
		try {
			root = loader.load();
			popoverController = loader.getController();
			CssHelper.replaceStylesheets(root.getStylesheets());

			createStandardPopover(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates a {@link PopOver} with the given {@link Node} as content. Sets some
	 * default parameters for the {@link PopOver}s.
	 * 
	 * @param content The {@link Node} to be displayed inside of the
	 *                {@link PopOver}.
	 */
	public void createStandardPopover(Node content) {
		popover = new PopOver(content);
		content.getStyleClass().add("popover_pane");
		popover.setDetachable(false);
		popover.setFadeInDuration(Duration.millis(50));
	}

	/**
	 * Create a standard overlay. Compared to the regular {@link PopOver}, this is
	 * centered on the screen and not attached to any node.
	 * 
	 * @param fxmlFile The path of the fxml-File, starting at the resource
	 *                 directory.
	 */
	public void createStandardOverlay(String fxmlFile) {
		createStandardPopover(fxmlFile);
		popover.centerOnScreen();
		popover.setArrowSize(0.0);
	}

}
