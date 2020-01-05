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

	/**
	 * Constructor for a {@link GameModule}.
	 * 
	 * @param contentRoot  The root element of the module's content.
	 * @param definition         The {@link GameModuleDefinition} of the module.
	 * @param viewType     The {@link GameViewType} of the {@link GameView} owning
	 *                     the module.
	 * @param gridPosition The {@link GridPosition} of the module.
	 * @param controller   The {@link GameModuleController} of this module.
	 * @throws IOException
	 */
	public GameModule(Parent contentRoot, GameModuleDefinition definition,
			GridPosition gridPosition, GameModuleController controller) throws IOException {

		super("fxml/module/standard.fxml", definition.viewType, definition.elementType.title, contentRoot, definition.elementType);

		//TODO nutze richtiges RessourceBundle
		ResourceBundle bundle = UIManager.getResourceBundle();
		
		// Initialize the module with the title
		this.setGridPosition(gridPosition);
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
