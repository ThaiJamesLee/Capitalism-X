package de.uni.mannheim.capitalismx.ui.controller.general;

import javafx.fxml.Initializable;

/**
 * A controller that is updateable should implement this interface.
 * 
 * @author Jonathan
 *
 */
public interface UpdateableController extends Initializable {

	/**
	 * Updates all of the components of the Controller and initiates updates of
	 * updateable sub-controllers.
	 */
	public void update();

}
