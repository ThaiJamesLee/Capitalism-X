package de.uni.mannheim.capitalismx.ui.controller.overlay;

import java.util.Properties;

import de.uni.mannheim.capitalismx.ui.controller.general.UpdateableController;

/**
 * This abstract class defines the Controller of a {@link GameOverlay}. It
 * contains attributes, methods and interfaces that need to be implemented by
 * the controller.
 * 
 * @author Jonathan
 *
 */
public abstract class GameOverlayController implements UpdateableController {

	// Optional properties for the Overlay
	public Properties properties = new Properties();

	public abstract Properties getProperties();

	/**
	 * Method can be used to give the overlay some parameters, what information to
	 * contain before it is updated and displayed. (e.g. for which employee to
	 * display detailed information or which product should be edited)
	 * 
	 * @param properties
	 */
	public abstract void updateProperties(Properties properties);

}
