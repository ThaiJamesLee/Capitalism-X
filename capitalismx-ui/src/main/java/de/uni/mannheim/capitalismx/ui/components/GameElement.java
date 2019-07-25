package de.uni.mannheim.capitalismx.ui.components;

import java.io.IOException;

import de.uni.mannheim.capitalismx.ui.controller.GameElementController;
import de.uni.mannheim.capitalismx.ui.controller.GameElementFrameController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 * An element of the UI on the GamePage, that can be filled with custom content.
 * 
 * @author Jonathan
 *
 */
public class GameElement {

	// The root element of the module.
	private Parent rootElement;

	// The controller of the module's FX-Components.
	private GameElementController controller;

	// The controller for the frame of the element.
	private GameElementFrameController frameController;

	// The type of the view owning the module.
	private GameViewType owningViewType;

	/**
	 * Create a new {@link GameElement} and initialize its frame.
	 * 
	 * @param fxmlFileName The filename of the element's frame.
	 * @param viewType     The type of view, this belongs to.
	 * @param title        The title of the element.
	 * @param contentRoot  The root-element of the custom content.
	 * @param controller   The {@link GameElementController} of the element and its
	 *                     content.
	 * @throws IOException 
	 */
	public GameElement(String fxmlFileName, GameViewType viewType, String title, Parent contentRoot,
			GameElementController controller) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(fxmlFileName));
		this.rootElement = loader.load();
		frameController = ((GameElementFrameController) loader.getController());
		frameController.setTitleLabel(title);
		frameController.initContent(contentRoot);
		this.controller = controller;
	}

	public GameViewType getOwningViewType() {
		return this.owningViewType;
	}

	public Parent getRootElement() {
		return rootElement;
	}

	public void setRootElement(Parent rootElement) {
		this.rootElement = rootElement;
	}

	public GameElementController getController() {
		return controller;
	}

}
