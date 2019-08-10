package de.uni.mannheim.capitalismx.ui.components;

import java.io.IOException;

import de.uni.mannheim.capitalismx.ui.controller.UIElementController;
import de.uni.mannheim.capitalismx.ui.controller.UIElementFrameController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 * An element of the UI on the GamePage, that can be filled with custom content.
 * 
 * @author Jonathan
 *
 */
public class UIElement {

	// The root element of the module.
	private Parent rootElement;

	// The controller of the module's FX-Components.
	private UIElementController controller;

	// The controller for the frame of the element.
	private UIElementFrameController frameController;

	// The type of the view owning the module.
	private GameViewType owningViewType;

	/**
	 * Create a new {@link UIElement} and initialize its frame.
	 * 
	 * @param fxmlFileName The filename of the element's frame.
	 * @param viewType     The type of view, this belongs to.
	 * @param title        The title of the element.
	 * @param contentRoot  The root-element of the custom content.
	 * @param controller   The {@link UIElementController} of the element and its
	 *                     content.
	 * @throws IOException 
	 */
	public UIElement(String fxmlFileName, GameViewType viewType, String title, Parent contentRoot,
			UIElementController controller) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(fxmlFileName));
		this.rootElement = loader.load();
		frameController = ((UIElementFrameController) loader.getController());
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

	public UIElementController getController() {
		return controller;
	}

}
