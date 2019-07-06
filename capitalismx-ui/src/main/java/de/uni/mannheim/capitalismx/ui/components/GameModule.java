package de.uni.mannheim.capitalismx.ui.components;

import java.io.IOException;

import de.uni.mannheim.capitalismx.ui.controller.GameController;
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
public class GameModule {

	// The root element of the module.
	private Parent rootElement;

	// The type of the module.
	private GameModuleType type;

	// The type of the view owning the module.
	private GameViewType owningViewType;

	// The controller of the module's FX-Components.
	private GameController controller;

	// The module's position on the grid.
	private GridPosition gridPosition;

	/**
	 * Constructor for a {@link GameModule}.
	 * 
	 * @param contentRoot
	 *            The root element of the module's content.
	 * @param type
	 *            The {@link GameModuleType} of the module.
	 * @param viewType
	 *            The {@link GameViewType} of the {@link GameView} owning the
	 *            module.
	 * @param gridPosition
	 *            The {@link GridPosition} of the module.
	 */
	public GameModule(Parent contentRoot, GameModuleType type, GameViewType viewType, GridPosition gridPosition,
			GameController controller, String title) {
		//Initialize the module with the title
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/module/standard.fxml"));
			this.rootElement = loader.load();
			GameModuleController standardController = ((GameModuleController)loader.getController());
			standardController.setTitleLabel(title);
			standardController.setContent(contentRoot);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.type = type;
		this.owningViewType = viewType;
		this.setGridPosition(gridPosition);
		this.controller = controller;
	}

	public GameController getController() {
		return controller;
	}

	public GridPosition getGridPosition() {
		return gridPosition;
	}

	public GameViewType getOwningViewType() {
		return this.owningViewType;
	}

	public Parent getRootElement() {
		return rootElement;
	}

	public GameModuleType getType() {
		return type;
	}

	public void setGridPosition(GridPosition gridPosition) {
		this.gridPosition = gridPosition;
	}

	public void setRootElement(Parent rootElement) {
		this.rootElement = rootElement;
	}

}
