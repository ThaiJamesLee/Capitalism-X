package de.uni.mannheim.capitalismx.ui.controller.overlay;

import java.lang.annotation.ElementType;
import java.util.Properties;

import de.uni.mannheim.capitalismx.ui.components.GameOverlay;
import de.uni.mannheim.capitalismx.ui.components.UIElementType;
import javafx.fxml.FXML;
import javafx.scene.Parent;

/**
 * The controller of a {@link GameOverlay}.
 * 
 * @author Jonathan
 *
 */
public class GameOverlayController {

	// Optional properties for the Overlay
	Properties properties = new Properties();
	// The type of element.
	private UIElementType elementType;

	@FXML
	private Parent rootElement;
	
	public Properties getProperties() {
		return this.properties;
	}
	

	/**
	 * Initialize the {@link GameOverlayController}.
	 * 
	 * @param elementType The {@link ElementType} of the {@link GameOverlay}.
	 * @param rootElement The root element of the module.
	 */
	public void initModuleController(UIElementType elementType, Parent rootElement) {
		this.elementType = elementType;
		this.rootElement = rootElement;
	}

	/**
	 * Method can be used to give the overlay some parameters, what information to
	 * contain before it is updated and displayed. (e.g. for which employee to
	 * display detailed information or which product should be edited)
	 * 
	 * @param properties
	 */
	public void updateProperties(Properties properties) {
		this.properties = properties;
	}
	
}
