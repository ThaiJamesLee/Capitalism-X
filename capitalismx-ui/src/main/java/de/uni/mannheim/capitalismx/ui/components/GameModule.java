package de.uni.mannheim.capitalismx.ui.components;

import java.io.IOException;

import de.uni.mannheim.capitalismx.ui.controller.module.ModuleFrameController;
import de.uni.mannheim.capitalismx.ui.utils.CssHelper;
import de.uni.mannheim.capitalismx.ui.utils.GridPosition;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;

/**
 * A standard module in the game, holding information about it's own position on
 * the grid and the FX-elements it consists of.
 * 
 * @author Jonathan
 */
public class GameModule {


	// The type of the element.
	private GameModuleType type;

	// The root element of the module.
	private Parent rootElement;

	// The controller for the frame of the element.
	private ModuleFrameController frameController;

	private Initializable controller;

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
	 * @param controller  The {@link Initializable} of this module.
	 * @throws IOException If the {@link FXMLLoader}could not read the fxml-file
	 *                     correctly.
	 */
	public GameModule(Parent contentRoot, GameModuleDefinition definition, Initializable controller)
			throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/module/module_frame.fxml"));
		this.rootElement = loader.load();
		frameController = ((ModuleFrameController) loader.getController());
		frameController.setTitleLabel(definition.moduleType.getTitle());
		this.type = definition.moduleType;
		frameController.initContent(contentRoot, type);

		// Use css of the current resolution
		CssHelper.replaceStylesheets(this.rootElement.getStylesheets());
		CssHelper.replaceStylesheets(contentRoot.getStylesheets());
		
		// Initialize the module with the title
		this.setGridPosition(definition.gridPosition);
		this.activated = definition.activated;
		this.controller = controller;
	}

	public Parent getRootElement() {
		return rootElement;
	}

	public void setRootElement(Parent rootElement) {
		this.rootElement = rootElement;
	}
	
	public GameModuleType getType() {
		return type;
	}
	
	public GridPosition getGridPosition() {
		return gridPosition;
	}

	public void setGridPosition(GridPosition gridPosition) {
		this.gridPosition = gridPosition;
	}

	public Initializable getController() {
		return controller;
	}

}
