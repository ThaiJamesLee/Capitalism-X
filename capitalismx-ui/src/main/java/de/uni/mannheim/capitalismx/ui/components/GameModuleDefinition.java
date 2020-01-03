package de.uni.mannheim.capitalismx.ui.components;

/**
 * The {@link GameModuleDefinition} specifies various attributes of a
 * {@link GameModule}, so it can automatically be generated.
 * 
 * @author Jonathan
 *
 */
public enum GameModuleDefinition {

	// The modules for HR.
	HR_EMPLOYEES_TABLE("employee_table.fxml", GameViewType.HR, UIElementType.HR_EMPLOYEES_OVERVIEW, 0, 7, 0, 15),
	HR_RECRUITING_LIST("recruiting_list.fxml", GameViewType.HR, UIElementType.HR_RECRUITING_OVERVIEW, 7, 7, 0, 17),
	HR_STATISTICS("hr_statistics.fxml", GameViewType.HR, UIElementType.HR_STATISTICS, 14, 6, 0, 11),
	HR_WORKING_CONDITIONS("working_conditions.fxml", GameViewType.HR, UIElementType.HR_WORKING_CONDITIONS, 14, 6, 12,
			8),

	// The modules for Logistics.
	LOGISTICS_PARTNER_SELECTION("logistics_partner_selection.fxml", GameViewType.LOGISTIC,
			UIElementType.LOGISTICS_PARTNER_OVERVIEW, 0, 5, 0, 15),
	LOGISTICS_TRUCK_FLEET("truck_fleet_overview.fxml", GameViewType.LOGISTIC,
			UIElementType.LOGISTICS_TRUCK_FLEET_OVERVIEW, 5, 5, 0, 15),

	// The modules for Production.
	PRODUCTION_INTRODUCE_PRODUCT_MENU("introduce_product_menu.fxml", GameViewType.PRODUCTION,
			UIElementType.PRODUCTION_NEW_PRODUCT_OVERVIEW, 0, 15, 0, 18),
	PRODUCTION_MACHINERY_LIST("machinery_list.fxml", GameViewType.PRODUCTION,
			UIElementType.PRODUCTION_MACHINERY_OVERVIEW, 15, 5, 0, 15),

	// The modules for the warehouse.
	WAREHOUSE_LIST("warehouse_list.fxml", GameViewType.WAREHOUSE, UIElementType.WAREHOUSE_LIST, 5, 5, 0, 15),
	WAREHOUSE_STOCK_MANAGEMENT("stock_management.fxml", GameViewType.WAREHOUSE,
			UIElementType.WAREHOUSE_STOCK_MANAGEMENT, 12, 8, 0, 20),
	WAREHOUSE_SEGMENTS("warehouse_information.fxml", GameViewType.WAREHOUSE, UIElementType.WAREHOUSE_SEGMENTS, 0, 4, 0,
			20),

	// The modules for HR.
	FINANCE_OPERATIONS_TABLE("operations_table.fxml", GameViewType.FINANCES, UIElementType.FINANCE_OPERATIONS_TABLE, 7,
			6, 0, 19),
	FINANCE_OVERVIEW("finance_overview.fxml", GameViewType.FINANCES, UIElementType.FINANCE_OVERVIEW, 1, 5, 0, 19),
	FINANCE_SALES_CHART("finance_statistics_charts.fxml", GameViewType.FINANCES, UIElementType.FINANCE_SALES_CHART, 14,
			5, 0, 19);

	// The name of the fxml-file defining this type.
	public final String fxmlFile;
	// The contentType of the module.
	public final GameViewType viewType;
	// The contentType of the module.
	public final UIElementType elementType;
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
	 * @param fxmlFile    The name of the fxml-file defining this type.
	 * @param viewType    The contentType of this module.
	 * @param elementType The {@link UIElementType} of the module.
	 * @param colStart    Starting column position in the grid.
	 * @param colSpan     Number of columns spanned by the module in the grid.
	 * @param rowStart    Starting row position in the grid.
	 * @param rowSpan     Number of rows spanned by the module in the grid.
	 * 
	 */
	private GameModuleDefinition(String fxmlFile, GameViewType viewType, UIElementType elementType, int colStart,
			int colSpan, int rowStart, int rowSpan) {
		this.viewType = viewType;
		this.elementType = elementType;
		this.fxmlFile = fxmlFile;
		this.gridColStart = colStart;
		this.gridColSpan = colSpan;
		this.gridRowStart = rowStart;
		this.gridRowSpan = rowSpan;
	}

}
