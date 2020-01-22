package de.uni.mannheim.capitalismx.ui.components;

import de.uni.mannheim.capitalismx.ui.application.UIManager;

/**
 * The {@link UIElementType} describes the role that the {@link UIElement}
 * has in its department and contains the title that is displayed on top of each
 * element.
 * 
 * @author Jonathan
 *
 */
public enum UIElementType {

	HR_TEAM_DETAIL("module.hr.detail"),
	HR_STATISTICS("module.statistics"), 
	HR_WORKING_CONDITIONS("module.hr.conditions"), 
	HR_RECRUITING_OVERVIEW("module.hr.hire"),
	FINANCE_BANKING_SYSTEM("module.finance.bank"), //TODO better name in german properties?
    FINANCE_OPERATIONS_TABLE("module.finance.table"),
	FINANCE_OVERVIEW("module.statistics"),
	LOGISTICS_PARTNER_OVERVIEW("module.logistics.external"),
	LOGISTICS_TRUCK_FLEET_OVERVIEW("module.logistics.trucks"),
	PRODUCTION_NEW_PRODUCT_OVERVIEW("module.statistics"),
	PRODUCTION_PRODUCE_PRODUCT("module.production.produce"),
	PRODUCTION_MACHINERY_OVERVIEW("module.production.machines"),
	PROCUREMENT_BUY_COMPONENT_OVERVIEW("Buy Component"),
    SALES_CONTRACT_OVERVIEW("module.sales.contract"),
    PROCUREMENT_ORDERED_COMPONENTS_OVERVIEW("module.warehouse.ordered"),
    WAREHOUSE_LIST("module.warehouse.list"),
	WAREHOUSE_STATISTICS("module.statistics"),
	WAREHOUSE_STOCK_MANAGEMENT("module.warehouse.stock"),
	RESDEV_CATEGORY_UNLOCKER("module.randd.product.category"),
	MARKETING_PRESSRELEASE("module.marketing.press"),
	MARKETING_CAMPAIGNS("module.marketing.campaign"),
	MARKETING_MARKETRESEARCH("module.marketing.research"),
	MARKETING_CONSULTANCIES("module.marketing.consult"),
	MARKETING_OVERVIEW("module.marketing.overview");

	// The title of the GameElement, that will be displayed on top.
	private final String titleProperty;

	private UIElementType(String title) {
		this.titleProperty = title;
	}
	public String getTitle() {
		return UIManager.getLocalisedString(titleProperty);
	}
}
