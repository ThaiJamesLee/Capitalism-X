package de.uni.mannheim.capitalismx.ui.components;

import java.io.IOException;

import de.uni.mannheim.capitalismx.ui.controller.overlay.GameOverlayController;
import de.uni.mannheim.capitalismx.ui.utils.GridPosition;
import javafx.scene.Parent;

/**
 * The GameOverlay is a UI-Element, that displays more detailed information or
 * more options for a given {@link GameModule}. It is displayed on top of the
 * modules on the GamePage.
 * 
 * @author Jonathan
 *
 */
@Deprecated
public class GameOverlay extends UIElement {

	// the controller of the overlay
	private GameOverlayController controller;

	/**
	 * Constructor for a {@link GameModule}.
	 * 
	 * @param contentRoot The root element of the overlay's content.
	 * @param definition  The {@link GameOverlayDefinition} of the overlay.
	 * @param viewType    The {@link GameViewType} of the {@link GameView} owning
	 *                    the overlay.
	 * @throws IOException
	 */
	public GameOverlay(Parent contentRoot, GameOverlayDefinition definition, GameViewType viewType,
			GridPosition gridPosition, GameOverlayController controller, String title) throws IOException {
		super("fxml/overlay/standard.fxml", viewType, title, contentRoot, definition.elementType);
		this.controller = controller;
	}

	public GameOverlayController getController() {
		return controller;
	}

}
