package de.uni.mannheim.capitalismx.ui.components;

import java.io.IOException;

import de.uni.mannheim.capitalismx.ui.controller.GameModuleController;
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

	// The module's position on the grid.
	private GridPosition gridPosition;

	// The overlay containing the detailed menu for the module.
	private GameOverlay overlay;

	/**
	 * Constructor for a {@link GameModule}.
	 * 
	 * @param contentRoot  The root element of the module's content.
	 * @param definition         The {@link GameModuleDefinition} of the module.
	 * @param viewType     The {@link GameViewType} of the {@link GameView} owning
	 *                     the module.
	 * @param overlayDefinition  The {@link GameOverlayDefinition} of the {@link GameOverlay} of
	 *                     the module.
	 * @param gridPosition The {@link GridPosition} of the module.
	 * @param controller   The {@link GameModuleController} of this module.
	 * @throws IOException
	 */
	public GameModule(Parent contentRoot, GameModuleDefinition definition,
			GridPosition gridPosition, GameModuleController controller) throws IOException {

		super("fxml/module/standard.fxml", definition.viewType, definition.elementType.title, contentRoot, controller,  definition.elementType);

		// Init optional overlay if one is defined
		if (definition.overlayDefinition != null) {
			FXMLLoader overlayLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/overlay/" + definition.overlayDefinition.fxmlFile));
			Parent overlayRoot = overlayLoader.load();
			this.overlay = new GameOverlay(overlayRoot, definition.overlayDefinition, definition.viewType, gridPosition, overlayLoader.getController(), definition.elementType.title);
		}

		// Initialize the module with the title
		this.setGridPosition(gridPosition);
		controller.initModuleController(this.getType(), contentRoot);
	}

	public GridPosition getGridPosition() {
		return gridPosition;
	}

	public void setGridPosition(GridPosition gridPosition) {
		this.gridPosition = gridPosition;
	}

	public GameOverlay getOverlay() {
		return overlay;
	}

}
