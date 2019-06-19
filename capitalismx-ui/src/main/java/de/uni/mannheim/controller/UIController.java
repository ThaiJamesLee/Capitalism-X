package de.uni.mannheim.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;

/**
 * Abstract class that defines methods each controller should implement.
 * 
 * @author Jonathan
 *
 */
public abstract class UIController implements Initializable {

	/**
	 * Initializes the controller.
	 * 
	 * @param location
	 * @param resources
	 */
	public abstract void initialize(URL location, ResourceBundle resources);
}
