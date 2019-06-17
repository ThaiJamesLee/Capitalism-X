package de.uni.mannheim.controller;

import javafx.fxml.Initializable;

/**
 * Abstract class that defines methods a controller should implement. All
 * controllers for ingame elements should extend the {@link GameController} or a
 * subclass.
 * 
 * @author Jonathan
 *
 */
public abstract class GameController implements Initializable {

	/**
	 * Requests the controller to update its components independently.
	 */
	public abstract void update();

}
