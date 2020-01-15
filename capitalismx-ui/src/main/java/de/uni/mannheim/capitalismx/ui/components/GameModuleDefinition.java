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
	HR_TEAM_DETAIL("team_details.fxml", GameViewType.HR, UIElementType.HR_TEAM_DETAIL, new GridPosition(0, 8, 0, 20), true),
	HR_RECRUITING_LIST("recruiting_list.fxml", GameViewType.HR, UIElementType.HR_RECRUITING_OVERVIEW, new GridPosition(13, 7, 7, 13),
			true),
	HR_STATISTICS("hr_statistics.fxml", GameViewType.HR, UIElementType.HR_STATISTICS, new GridPosition(8, 5, 0, 10), true),
	HR_WORKING_CONDITIONS("working_conditions.fxml", GameViewType.HR, UIElementType.HR_WORKING_CONDITIONS, new GridPosition(13, 7, 0, 7),
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
	PRODUCTION_PRODUCE_PRODUCT_MENU("produce_product_menu.fxml", GameViewType.PRODUCTION,
			UIElementType.PRODUCTION_PRODUCE_PRODUCT, new GridPosition(15, 9, 15, 15), true),

	// The modules for the warehouse.
	WAREHOUSE_LIST("warehouse_list.fxml", GameViewType.WAREHOUSE, UIElementType.WAREHOUSE_LIST, new GridPosition(0, 5, 0, 15), true),
	WAREHOUSE_STOCK_MANAGEMENT("stock_management.fxml", GameViewType.WAREHOUSE,
			UIElementType.WAREHOUSE_STOCK_MANAGEMENT, new GridPosition(12, 8, 0, 20), false),
	WAREHOUSE_SEGMENTS("warehouse_information.fxml", GameViewType.WAREHOUSE, UIElementType.WAREHOUSE_SEGMENTS, new GridPosition(6, 4, 0,
			20), false),

	// The modules for HR.
	FINANCE_OPERATIONS_TABLE("operations_table.fxml", GameViewType.FINANCES, UIElementType.FINANCE_OPERATIONS_TABLE, new GridPosition(5,
			9, 7, 19), true),
	FINANCE_OVERVIEW("finance_overview.fxml", GameViewType.FINANCES, UIElementType.FINANCE_OVERVIEW, new GridPosition(0, 19, 0, 7), true),
	FINANCE_BANKING_SYSTEM("finance_banking_system.fxml", GameViewType.FINANCES, UIElementType.FINANCE_BANKING_SYSTEM, new GridPosition(0, 5, 7, 19), true),
	//FINANCE_SALES_CHART("finance_statistics_charts.fxml", GameViewType.FINANCES, UIElementType.FINANCE_SALES_CHART, new GridPosition(14,
	//		5, 0, 19), true),
	
//	//The modules for Marketing
	MARKETING_PRESSRELEASE_LIST("mkt_pressReleases_list.fxml", GameViewType.MARKETING, UIElementType.MARKETING_PRESSRELEASE, new GridPosition(0, 6, 7, 11), true),
	MARKETING_CAMPAIGNS_LIST("mkt_campaigns_overview.fxml", GameViewType.MARKETING, UIElementType.MARKETING_CAMPAIGNS, new GridPosition(6, 7, 7, 11), true),
	MARKETING_MARKETRESEARCH_LIST("mkt_marketResearch_list.fxml", GameViewType.MARKETING, UIElementType.MARKETING_MARKETRESEARCH, new GridPosition(13, 7, 7, 11), true),
	MARKETING_OVERVIEW("mkt_general_overview.fxml", GameViewType.MARKETING, UIElementType.MARKETING_OVERVIEW, new GridPosition(0, 10, 2, 3), true);
	;

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
