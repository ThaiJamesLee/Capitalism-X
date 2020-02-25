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
	HR_TEAM_DETAIL("team_details.fxml", GameModuleType.HR_TEAM_DETAIL, new GridPosition(0, 8, 0, 20), true),
	HR_RECRUITING_LIST("recruiting_list.fxml", GameModuleType.HR_RECRUITING_OVERVIEW, new GridPosition(13, 7, 7, 13), true),
	HR_STATISTICS("hr_statistics.fxml", GameModuleType.HR_STATISTICS, new GridPosition(8, 5, 0, 20), true),
	HR_WORKING_CONDITIONS("working_conditions.fxml", GameModuleType.HR_WORKING_CONDITIONS, new GridPosition(13, 7, 0, 7), true),

	// The modules for Logistics.
	LOGISTICS_PARTNER_SELECTION("logistics_partner_selection.fxml", GameModuleType.LOGISTICS_PARTNER_OVERVIEW, new GridPosition(2, 8, 0, 9), true),
	LOGISTICS_TRUCK_FLEET("truck_fleet_overview.fxml", GameModuleType.LOGISTICS_TRUCK_FLEET_OVERVIEW, new GridPosition(2, 8, 9, 11), true),
	LOGISTICS_SUPPORT_PARTNER_SELECTION("support_partner_selection.fxml", GameModuleType.LOGISTICS_SUPPORT_PARTNER_OVERVIEW, new GridPosition(10, 8, 0, 9), true),
	LOGISTICS_SUPPORT_TYPES("product_support_overview.fxml", GameModuleType.LOGISTICS_SUPPORT_TYPE_OVERVIEW, new GridPosition(10, 8, 9, 11), true),

	// The modules for Production.
	PRODUCTION_MACHINERY_LIST("machinery_list.fxml",  GameModuleType.PRODUCTION_MACHINERY_OVERVIEW, new GridPosition(14, 6, 0, 10), true),
	PRODUCTION_PRODUCE_PRODUCT_MENU("produce_product_menu.fxml",  GameModuleType.PRODUCTION_PRODUCE_PRODUCT, new GridPosition(0, 14, 0, 15), true),

	// The modules for the warehouse.
	WAREHOUSE_STOCK_MANAGEMENT("stock_management.fxml",  GameModuleType.WAREHOUSE_STOCK_MANAGEMENT, new GridPosition(10, 10, 0, 20), false),
	WAREHOUSE_STATISTICS("warehouse_statistics.fxml",  GameModuleType.WAREHOUSE_STATISTICS, new GridPosition(6, 4, 0, 20), false),
	WAREHOUSE_LIST("warehouse_list.fxml",  GameModuleType.WAREHOUSE_LIST, new GridPosition(0, 6, 0, 20), true),

	// The modules for Finance.
	FINANCE_OPERATIONS_TABLE("operations_table.fxml",  GameModuleType.FINANCE_OPERATIONS_TABLE, new GridPosition(10, 10, 7, 13), true),
	FINANCE_OVERVIEW("finance_overview.fxml",  GameModuleType.FINANCE_OVERVIEW, new GridPosition(0, 20, 0, 7), true),
	FINANCE_BANKING_SYSTEM("finance_banking_system.fxml",  GameModuleType.FINANCE_BANKING_SYSTEM, new GridPosition(5, 5, 7, 13), true),
	FINANCE_INVESTMENTS("finance_investments.fxml",  GameModuleType.FINANCE_INVESTMENTS, new GridPosition(0, 5, 7, 13), true),

	//	//The modules for Marketing
	MARKETING_PRESSRELEASE_LIST("mkt_pressReleases_list.fxml",  GameModuleType.MARKETING_PRESSRELEASE, new GridPosition(0, 6, 7, 11), true),
	MARKETING_CAMPAIGNS_LIST("mkt_campaigns_overview.fxml",  GameModuleType.MARKETING_CAMPAIGNS, new GridPosition(6, 7, 7, 11), true),
	MARKETING_MARKETRESEARCH_LIST("mkt_marketResearch_list.fxml",  GameModuleType.MARKETING_MARKETRESEARCH, new GridPosition(13, 7, 7, 11), true),
	MARKETING_OVERVIEW("mkt_general_overview.fxml",  GameModuleType.MARKETING_OVERVIEW, new GridPosition(5, 10, 2, 3), true),

    //The modules for Research and Development.
    RESDEV_CATEGORY_UNLOCKER("resdev_category_unlocker.fxml", GameModuleType.RESDEV_CATEGORY_UNLOCKER, new GridPosition(5, 8, 5, 8),true),

    // The modules for Sales
    SALES_CONTRACT_MANAGEMENT("sales_contracts.fxml",  GameModuleType.SALES_CONTRACT_OVERVIEW, new GridPosition(3, 15, 5, 15) , true),
	SALES_KPI_OVERVIEW("sales_kpi.fxml", GameModuleType.SALES_KPI_OVERVIEW, new GridPosition(1, 18, 0, 5), true);
	;

	// The name of the fxml-file defining this type.
	public final String fxmlFile;
	// The contentType of the module.
	public final GameModuleType moduleType;
	// Starting position in the grid.
	public final GridPosition gridPosition;
	// If the module is initially activated. (Displayed from the beginning)
	public final boolean activated;

	/**
	 * Create a new {@link GameModuleDefinition} with default values for the actual
	 * modules.
	 * 
	 * @param fxmlFile    The name of the fxml-file defining this type.
	 * @param moduleType The {@link GameModuleType} of the module.
	 * @param gridPosition The initial {@link GridPosition} of the module on the grid.
	 * @param activated  If the module is initially activated. (Displayed from the beginning)
	 */
	private GameModuleDefinition(String fxmlFile, GameModuleType moduleType, GridPosition gridPosition, boolean activated) {
		this.moduleType = moduleType;
		this.fxmlFile = fxmlFile;
		this.gridPosition = gridPosition;
		this.activated = activated;
	}

}
