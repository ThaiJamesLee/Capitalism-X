package de.uni.mannheim.capitalismx.ui.components;

public enum GameOverlayDefinition {

	HR_EDIT_EMPLOYEE("employee_detail.fxml", GameViewType.HR, GameElementType.HR_EMPLOYEES_OVERVIEW);

	// The name of the fxml-file defining this type.
	public final String fxmlFile;
	// The viewType of the module.
	public GameViewType viewType;
	// The elementType of the overlay.
	public GameElementType elementType;

	/**
	 * Create a new {@link GameViewType} with default values for the actual modules.
	 * 
	 * @param fxmlFile    The name of the fxml-file defining this type.
	 * @param viewType    The viewType of this module.
	 * @param elementType The elementType of the overlay.
	 * 
	 */
	private GameOverlayDefinition(String fxmlFile, GameViewType viewType, GameElementType elementType) {
		this.viewType = viewType;
		this.fxmlFile = fxmlFile;
		this.elementType = elementType;
	}
}
