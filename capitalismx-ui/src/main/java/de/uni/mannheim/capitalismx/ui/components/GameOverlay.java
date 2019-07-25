package de.uni.mannheim.capitalismx.ui.components;

import java.io.IOException;

import de.uni.mannheim.capitalismx.ui.controller.GameElementController;
import de.uni.mannheim.capitalismx.ui.utils.GridPosition;
import javafx.scene.Parent;

public class GameOverlay extends GameElement {

	// The type of GameOverlay.
	private GameOverlayDefinition type;

	/**
	 * Constructor for a {@link GameModule}.
	 * 
	 * @param contentRoot The root element of the overlay's content.
	 * @param type        The {@link GameOverlayDefinition} of the overlay.
	 * @param viewType    The {@link GameViewType} of the {@link GameView} owning
	 *                    the overlay.
	 * @throws IOException 
	 */
	public GameOverlay(Parent contentRoot, GameOverlayDefinition type, GameViewType viewType, GridPosition gridPosition,
			GameElementController controller, String title) throws IOException {
		super("fxml/overlay/standard.fxml", viewType, title, contentRoot, controller);

		// Initialize the module with the title
		this.type = type;
	}

	public GameOverlayDefinition getGameOverlayType() {
		return this.type;	}

}
