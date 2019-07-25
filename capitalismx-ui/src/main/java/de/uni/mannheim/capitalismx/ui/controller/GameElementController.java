package de.uni.mannheim.capitalismx.ui.controller;

/**
 * Abstract class that defines methods an ingame controller should implement. All
 * controllers for ingame elements should extend the {@link GameElementController} or a
 * subclass.
 * 
 * @author Jonathan
 *
 */
public abstract class GameElementController extends UIController {

	/**
	 * Requests the controller to update its components independently.
	 */
	public abstract void update();


}
