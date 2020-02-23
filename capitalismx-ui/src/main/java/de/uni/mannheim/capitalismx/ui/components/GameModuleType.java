package de.uni.mannheim.capitalismx.ui.components;

import de.uni.mannheim.capitalismx.ui.application.UIManager;

/**
 * The {@link GameModuleType} describes the role that the {@link GameModule}
 * has in its department and contains the title that is displayed on top of each
 * element as well as the {@link GameViewType} it belongs to.
 * 
 * @author Jonathan
 *
 */
public enum GameModuleType {

	HR_TEAM_DETAIL("module.hr.detail", GameViewType.HR),
	HR_STATISTICS("module.statistics", GameViewType.HR), 
	HR_WORKING_CONDITIONS("module.hr.conditions", GameViewType.HR), 
	HR_RECRUITING_OVERVIEW("module.hr.hire", GameViewType.HR),
	FINANCE_BANKING_SYSTEM("module.finance.bank", GameViewType.FINANCES),
    FINANCE_OPERATIONS_TABLE("module.finance.table", GameViewType.FINANCES),
	FINANCE_OVERVIEW("module.statistics", GameViewType.FINANCES),
	LOGISTICS_PARTNER_OVERVIEW("module.logistics.external", GameViewType.LOGISTIC),
	LOGISTICS_TRUCK_FLEET_OVERVIEW("module.logistics.trucks", GameViewType.LOGISTIC),
	PRODUCTION_PRODUCE_PRODUCT("module.production.produce", GameViewType.PRODUCTION),
	PRODUCTION_MACHINERY_OVERVIEW("module.production.machines", GameViewType.PRODUCTION),
    SALES_CONTRACT_OVERVIEW("module.sales.contract", GameViewType.SALES),
	SALES_KPI_OVERVIEW("module.sales.kpi", GameViewType.SALES),
    WAREHOUSE_LIST("module.warehouse.list", GameViewType.WAREHOUSE),
	WAREHOUSE_STATISTICS("module.statistics", GameViewType.WAREHOUSE),
	WAREHOUSE_STOCK_MANAGEMENT("module.warehouse.stock", GameViewType.WAREHOUSE),
	RESDEV_CATEGORY_UNLOCKER("module.randd.product.category", GameViewType.R_AND_D),
	MARKETING_PRESSRELEASE("module.marketing.press", GameViewType.MARKETING),
	MARKETING_CAMPAIGNS("module.marketing.campaign",GameViewType.MARKETING),
	MARKETING_MARKETRESEARCH("module.marketing.research", GameViewType.MARKETING),
	MARKETING_CONSULTANCIES("module.marketing.consult", GameViewType.MARKETING),
	MARKETING_OVERVIEW("module.marketing.overview", GameViewType.MARKETING);

	// The title of the GameElement, that will be displayed on top.
	private final String titleProperty;

	// The contentType of the module.
	public final GameViewType viewType;

	private GameModuleType(String title, GameViewType viewType) {
		this.viewType = viewType;
		this.titleProperty = title;
	}
	public String getTitle() {
		return UIManager.getLocalisedString(titleProperty);
	}
}
