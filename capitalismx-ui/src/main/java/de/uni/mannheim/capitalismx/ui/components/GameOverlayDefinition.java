package de.uni.mannheim.capitalismx.ui.components;

/**
 * The {@link GameOverlayDefinition} specifies various attributes of a
 * {@link GameOverlay}, so it can automatically be generated.
 * @author Jonathan
 *
 */
public enum GameOverlayDefinition {

	PRODUCTION_SUPPLIER_OPTIONS("supplier_options.fxml", GameViewType.PRODUCTION, UIElementType.PRODUCTION_NEW_PRODUCT_OVERVIEW),
	LOAN_REQUEST("loan_request_list.fxml", GameViewType.FINANCES, UIElementType.FINANCE_OVERVIEW);

	// The name of the fxml-file defining this type.
	public final String fxmlFile;
	// The viewType of the module.
	public GameViewType viewType;
	// The elementType of the overlay.
	public UIElementType elementType;

	/**
	 * Create a new {@link GameViewType} with default values for the actual modules.
	 * 
	 * @param fxmlFile    The name of the fxml-file defining this type.
	 * @param viewType    The viewType of this module.
	 * @param elementType The elementType of the overlay.
	 * 
	 */
	private GameOverlayDefinition(String fxmlFile, GameViewType viewType, UIElementType elementType) {
		this.viewType = viewType;
		this.fxmlFile = fxmlFile;
		this.elementType = elementType;
	}
}
