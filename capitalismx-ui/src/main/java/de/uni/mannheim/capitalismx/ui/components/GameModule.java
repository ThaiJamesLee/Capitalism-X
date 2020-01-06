package de.uni.mannheim.capitalismx.ui.components;

import java.io.IOException;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.controller.module.GameModuleController;
import de.uni.mannheim.capitalismx.ui.utils.GridPosition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 * A standard module in the game, holding information about it's own position on
 * the grid and the FX-elements it consists of.
 * 
 * @author Jonathan
 *
 */
public class GameModule extends UIElement {

	private GameModuleController controller;

	// The module's position on the grid.
	private GridPosition gridPosition;

	// Whether the module is currently being displayed and accessible for the user.
	private boolean activated;

	public boolean isActivated() {
		return activated;
	}

	/**
	 * Sets activated-attribute of the {@link GameModule}, so that it can be
	 * displayed to the user or hidden from him.
	 */
	public void setActivated(boolean activated) {
		this.activated = activated;
	}

	/**
	 * Constructor for a {@link GameModule}.
	 * 
	 * @param contentRoot The root element of the module's content.
	 * @param definition  The {@link GameModuleDefinition} of the module.
	 * @param controller  The {@link GameModuleController} of this module.
	 * @throws IOException If the {@link FXMLLoader}could not read the fxml-file
	 *                     correctly.
	 */
	public GameModule(Parent contentRoot, GameModuleDefinition definition, GameModuleController controller)
			throws IOException {

		super("fxml/module/standard.fxml", definition.viewType, definition.elementType.title, contentRoot,
				definition.elementType);

		// TODO nutze richtiges ResourceBundle
		ResourceBundle bundle = UIManager.getResourceBundle();

		// Initialize the module with the title
		this.setGridPosition(definition.gridPosition);
		this.activated = definition.activated;
		this.controller = controller;
	}

	public GridPosition getGridPosition() {
		return gridPosition;
	}

	public void setGridPosition(GridPosition gridPosition) {
		this.gridPosition = gridPosition;
	}

	public GameModuleController getController() {
		return controller;
	}

}
