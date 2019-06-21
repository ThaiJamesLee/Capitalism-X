package de.uni.mannheim.capitalismx.ui.controller;

/**
 * Abstract class that defines methods an ingame controller should implement. All
 * controllers for ingame elements should extend the {@link GameController} or a
 * subclass.
 * 
 * @author Jonathan
 *
 */
public abstract class GameController extends UIController {

	/**
	 * Requests the controller to update its components independently.
	 */
	public abstract void update();


}
