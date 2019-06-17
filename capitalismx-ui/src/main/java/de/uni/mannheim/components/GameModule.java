package de.uni.mannheim.components;

import de.uni.mannheim.controller.GameController;
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

	// The controller of the module's FX-Components.
	private GameController controller;

	// Starting column position in the grid.
	private int gridColStart;
	// Number of columns spanned by the module in the grid.
	private int gridColSpan;
	// Starting row position in the grid.
	private int gridRowStart;
	// Number of rows spanned by the module in the grid.
	private int gridRowSpan;

	public Parent getRootElement() {
		return rootElement;
	}

	public void setRootElement(Parent rootElement) {
		this.rootElement = rootElement;
	}

	public int getGridColStart() {
		return gridColStart;
	}

	public void setGridColStart(int gridColStart) {
		this.gridColStart = gridColStart;
	}

	public int getGridColSpan() {
		return gridColSpan;
	}

	public void setGridColSpan(int gridColSpan) {
		this.gridColSpan = gridColSpan;
	}

	public int getGridRowStart() {
		return gridRowStart;
	}

	public void setGridRowStart(int gridRowStart) {
		this.gridRowStart = gridRowStart;
	}

	public int getGridRowSpan() {
		return gridRowSpan;
	}

	public void setGridRowSpan(int gridRowSpan) {
		this.gridRowSpan = gridRowSpan;
	}

	public GameModuleType getType() {
		return type;
	}

	public GameController getController() {
		return controller;
	}

	/**
	 * Constructor for a {@link GameModule}.
	 * 
	 * @param root     The root element of the module.
	 * @param type     The {@link GameModuleType} of the module.
	 * @param colStart The starting column position in the grid.
	 * @param colSpan  Number of columns spanned by the module in the grid.
	 * @param rowStart The starting column position in the grid.
	 * @param rowSpan  Number of rows spanned by the module in the grid.
	 */
	public GameModule(Parent root, GameModuleType type, int colStart, int colSpan, int rowStart, int rowSpan,
			GameController controller) {
		this.rootElement = root;
		this.gridColStart = colStart;
		this.gridColSpan = colSpan;
		this.gridRowStart = rowStart;
		this.gridRowSpan = rowSpan;
		this.controller = controller;
	}

}
