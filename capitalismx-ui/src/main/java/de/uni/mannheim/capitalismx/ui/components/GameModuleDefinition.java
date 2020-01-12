package de.uni.mannheim.capitalismx.ui.components;

import de.uni.mannheim.capitalismx.ui.utils.GridPosition;

/**
 * The {@link GameModuleDefinition} specifies various attributes of a
 * {@link GameModule}, so it can automatically be generated.
 * 
 * @author Jonathan
 *
 */
public enum GameModuleDefinition {

	// The modules for HR.
	HR_EMPLOYEES_TABLE("employee_table.fxml", GameViewType.HR, UIElementType.HR_EMPLOYEES_OVERVIEW, new GridPosition(0, 7, 0, 15), true),
	HR_RECRUITING_LIST("recruiting_list.fxml", GameViewType.HR, UIElementType.HR_RECRUITING_OVERVIEW, new GridPosition(7, 7, 0, 17),
			true),
	HR_STATISTICS("hr_statistics.fxml", GameViewType.HR, UIElementType.HR_STATISTICS, new GridPosition(14, 6, 0, 11), true),
	HR_WORKING_CONDITIONS("working_conditions.fxml", GameViewType.HR, UIElementType.HR_WORKING_CONDITIONS, new GridPosition(14, 6, 12, 8),
			true),

	// The modules for Logistics.
	LOGISTICS_PARTNER_SELECTION("logistics_partner_selection.fxml", GameViewType.LOGISTIC,
			UIElementType.LOGISTICS_PARTNER_OVERVIEW, new GridPosition(0, 5, 0, 15), true),
	LOGISTICS_TRUCK_FLEET("truck_fleet_overview.fxml", GameViewType.LOGISTIC,
			UIElementType.LOGISTICS_TRUCK_FLEET_OVERVIEW, new GridPosition(5, 5, 0, 15), true),

	// The modules for Production.
	PRODUCTION_INTRODUCE_PRODUCT_MENU("introduce_product_menu.fxml", GameViewType.PRODUCTION,
			UIElementType.PRODUCTION_NEW_PRODUCT_OVERVIEW, new GridPosition(0, 15, 0, 18), true),
	PRODUCTION_MACHINERY_LIST("machinery_list.fxml", GameViewType.PRODUCTION,
			UIElementType.PRODUCTION_MACHINERY_OVERVIEW, new GridPosition(15, 5, 0, 15), true),

	// The modules for the warehouse.
	WAREHOUSE_LIST("warehouse_list.fxml", GameViewType.WAREHOUSE, UIElementType.WAREHOUSE_LIST, new GridPosition(5, 5, 0, 15), true),
	WAREHOUSE_STOCK_MANAGEMENT("stock_management.fxml", GameViewType.WAREHOUSE,
			UIElementType.WAREHOUSE_STOCK_MANAGEMENT, new GridPosition(12, 8, 0, 20), false),
	WAREHOUSE_SEGMENTS("warehouse_information.fxml", GameViewType.WAREHOUSE, UIElementType.WAREHOUSE_SEGMENTS, new GridPosition(0, 4, 0,
			20), false),

	// The modules for HR.
	FINANCE_OPERATIONS_TABLE("operations_table.fxml", GameViewType.FINANCES, UIElementType.FINANCE_OPERATIONS_TABLE, new GridPosition(7,
			6, 0, 19), true),
	FINANCE_OVERVIEW("finance_overview.fxml", GameViewType.FINANCES, UIElementType.FINANCE_OVERVIEW, new GridPosition(1, 5, 0, 19), true),
	FINANCE_SALES_CHART("finance_statistics_charts.fxml", GameViewType.FINANCES, UIElementType.FINANCE_SALES_CHART, new GridPosition(14,
			5, 0, 19), true),

	// The modules for Sales
	SALES_CONTRACT_MANAGEMENT("sales_contracts.fxml", GameViewType.SALES, UIElementType.SALES_CONTRACT_OVERVIEW, new GridPosition(0, 10, 0, 15) , true);

	// The name of the fxml-file defining this type.
	public final String fxmlFile;
	// The contentType of the module.
	public final GameViewType viewType;
	// The contentType of the module.
	public final UIElementType elementType;
	// Starting position in the grid.
	public final GridPosition gridPosition;
	// If the module is initially activated. (Displayed from the beginning)
	public final boolean activated;

	/**
	 * Create a new {@link GameModuleDefinition} with default values for the actual
	 * modules.
	 * 
	 * @param fxmlFile    The name of the fxml-file defining this type.
	 * @param viewType    The contentType of this module.
	 * @param elementType The {@link UIElementType} of the module.
	 * @param gridPosition The initial {@link GridPosition} of the module on the grid.
	 * @param activated  If the module is initially activated. (Displayed from the beginning)
	 */
	private GameModuleDefinition(String fxmlFile, GameViewType viewType, UIElementType elementType, GridPosition gridPosition, boolean activated) {
		this.viewType = viewType;
		this.elementType = elementType;
		this.fxmlFile = fxmlFile;
		this.gridPosition = gridPosition;
		this.activated = activated;
	}

}
