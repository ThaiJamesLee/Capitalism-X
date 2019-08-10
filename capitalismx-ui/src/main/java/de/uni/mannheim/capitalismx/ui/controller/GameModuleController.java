package de.uni.mannheim.capitalismx.ui.controller;

import java.lang.annotation.ElementType;
import java.net.URL;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.ui.application.Main;
import de.uni.mannheim.capitalismx.ui.components.UIElementType;
import de.uni.mannheim.capitalismx.ui.components.GameModule;
import javafx.fxml.FXML;
import javafx.scene.Parent;

public class GameModuleController extends UIElementController {

	// The type of element.
	private UIElementType elementType;

	@FXML
	private Parent rootElement;

	// No default implementation for now
	@Override
	public void update() {

	}

	// No default implementation for now
	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	/**
	 * Initialize the {@link GameModuleController}.
	 * 
	 * @param elementType The {@link ElementType} of the {@link GameModule}.
	 * @param rootElement The root element of the module.
	 */
	public void initModuleController(UIElementType elementType, Parent rootElement) {
		this.elementType = elementType;
		this.rootElement = rootElement;

		// open the overlay, if the content pane of the module is clicked
		this.rootElement.getParent().setOnMouseClicked(e -> {
			openOverlay();
		});
	}

	/**
	 * Opens the overlay of the given module.
	 */
	private void openOverlay() {
		Main.getManager().getGamePageController().showOverlay(elementType);
	}

}
