package de.uni.mannheim.capitalismx.ui.components;

/**
 * The {@link GameModuleDefinition} specifies various attributes of a
 * {@link GameModule}, so it can automatically be generated.
 * 
 * @author Jonathan
 *
 */
public enum GameModuleDefinition {

	// TODO remove all the placeholder module types!
	// The default type. Probably means something went wrong.
//	UNKNOWN(0, "", GameViewType.GAME_OVERVIEW, 0, 0, 0, 0),

	// The modules for the overview. (1-10)
//	OVERVIEW_PLACEHOLDER(1, "m_overview_placeholder.fxml", GameViewType.GAME_OVERVIEW, 0, 1, 0, 1),

	// The modules for HR. (11-20)
	HR_EMPLOYEES_TABLE(11, "employee_table.fxml", GameViewType.HR, GameOverlayDefinition.HR_EDIT_EMPLOYEE,
			UIElementType.HR_EMPLOYEES_OVERVIEW, 0, 7, 0, 15),
	HR_RECRUITING_LIST(12, "recruiting_list.fxml", GameViewType.HR, null, UIElementType.HR_RECRUITING_OVERVIEW, 7, 7,
			0, 17),
	HR_EMPLOYEE_SATISFACTION(13, "employee_satisfaction.fxml", GameViewType.HR, null, UIElementType.HR_EMPLOYEE_SATISFACTION, 14, 6,
			0, 9),
	HR_WORKING_CONDITIONS(13, "working_conditions.fxml", GameViewType.HR, null, UIElementType.HR_WORKING_CONDITIONS, 14, 6,
			9, 11),

	// The modules for Logistics. (21-30)
	LOGISTICS_PARTNER_SELECTION(21, "logistics_partner_selection.fxml", GameViewType.LOGISTIC, null, UIElementType.LOGISTICS_PARTNER_OVERVIEW, 0, 5, 0, 15),
	LOGISTICS_TRUCK_FLEET(22, "truck_fleet_overview.fxml", GameViewType.LOGISTIC, GameOverlayDefinition.TRUCK_DETAIL, UIElementType.LOGISTICS_TRUCK_FLEET_OVERVIEW, 5, 5, 0, 15),
	LOGISTICS_WAREHOUSE_LIST(23, "warehouse_list.fxml", GameViewType.LOGISTIC, null, UIElementType.LOGISTICS_WAREHOUSE_OVERVIEW, 10, 5, 0, 15),

	// The modules for Production. (31-40)
//	PRODUCTION_PLACEHOLDER(31, "m_production_placeholder.fxml", GameViewType.GAME_PRODUCTION, 0, 1, 0, 1),
	PRODUCTION_INTRODUCE_PRODUCT_MENU(31, "introduce_product_menu.fxml", GameViewType.PRODUCTION, GameOverlayDefinition.PRODUCTION_SUPPLIER_OPTIONS, UIElementType.PRODUCTION_NEW_PRODUCT_OVERVIEW, 0, 12, 0 ,5),
	// The modules for Procurement. (41-50)
//	PROCUREMENT_PLACEHOLDER(41, "m_procurement_placeholder.fxml", GameViewType.GAME_PROCUREMENT, 0, 1, 0, 1),

	// The modules for the warehouse. (51-60)
//	WAREHOUSE_PLACEHOLDER(51, "m_warehouse_placeholder.fxml", GameViewType.GAME_WAREHOUSE, 0, 1, 0, 1),

	// The modules for HR. (61-70)
	FINANCE_OPERATIONS_TABLE(61, "operations_table.fxml", GameViewType.FINANCES, null, UIElementType.FINANCE_OPERATIONS_TABLE, 5, 10, 0, 19),
	FINANCE_OVERVIEW(61, "finance_overview.fxml", GameViewType.FINANCES, null, UIElementType.FINANCE_OVERVIEW, 0, 5, 0, 19)

	// The modules for HR. (71-80)
//	MARKETING_PLACEHOLDER(71, "m_marketing_placeholder.fxml", GameViewType.GAME_MARKETING, 0, 1, 0, 1)
	;

	// The unique id of the type.
	public final int id;
	// The name of the fxml-file defining this type.
	public final String fxmlFile;
	// The contentType of the module.
	public final GameViewType viewType;
	// The contentType of the module.
	public final UIElementType elementType;
	// The type of the overlay of the module.
	public final GameOverlayDefinition overlayDefinition;
	// Starting column position in the grid.
	public final int gridColStart;
	// Number of columns spanned by the module in the grid.
	public final int gridColSpan;
	// Starting row position in the grid.
	public final int gridRowStart;
	// Number of rows spanned by the module in the grid.
	public final int gridRowSpan;

	/**
	 * Create a new {@link GameModuleDefinition} with default values for the actual
	 * modules.
	 * 
	 * @param id                The unique id of the type.
	 * @param fxmlFile          The name of the fxml-file defining this type.
	 * @param viewType          The contentType of this module.
	 * @param overlayDefinition The {@link GameOverlayDefinition} of the module's
	 *                          overlay. (optional)
	 * @param elementType       The {@link UIElementType} of the module.
	 * @param colStart          Starting column position in the grid.
	 * @param colSpan           Number of columns spanned by the module in the grid.
	 * @param rowStart          Starting row position in the grid.
	 * @param rowSpan           Number of rows spanned by the module in the grid.
	 * 
	 */
	private GameModuleDefinition(int id, String fxmlFile, GameViewType viewType,
			GameOverlayDefinition overlayDefinition, UIElementType elementType, int colStart, int colSpan,
			int rowStart, int rowSpan) {
		this.id = id;
		this.viewType = viewType;
		this.overlayDefinition = overlayDefinition;
		this.elementType = elementType;
		this.fxmlFile = fxmlFile;
		this.gridColStart = colStart;
		this.gridColSpan = colSpan;
		this.gridRowStart = rowStart;
		this.gridRowSpan = rowSpan;
	}

}
