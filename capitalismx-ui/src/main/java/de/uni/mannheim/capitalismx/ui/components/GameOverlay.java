package de.uni.mannheim.capitalismx.ui.components;

import java.io.IOException;

import de.uni.mannheim.capitalismx.ui.controller.UIElementController;
import de.uni.mannheim.capitalismx.ui.utils.GridPosition;
import javafx.scene.Parent;

public class GameOverlay extends UIElement {


	/**
	 * Constructor for a {@link GameModule}.
	 * 
	 * @param contentRoot The root element of the overlay's content.
	 * @param definition        The {@link GameOverlayDefinition} of the overlay.
	 * @param viewType    The {@link GameViewType} of the {@link GameView} owning
	 *                    the overlay.
	 * @throws IOException 
	 */
	public GameOverlay(Parent contentRoot, GameOverlayDefinition definition, GameViewType viewType, GridPosition gridPosition,
			UIElementController controller, String title) throws IOException {
		super("fxml/overlay/standard.fxml", viewType, title, contentRoot, controller, definition.elementType);

	}


}
