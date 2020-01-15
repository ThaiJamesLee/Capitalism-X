package de.uni.mannheim.capitalismx.ui.components;

/**
 * The {@link UIElementType} describes the role that the {@link UIElement}
 * has in its department and contains the title that is displayed on top of each
 * element.
 * 
 * @author Jonathan
 *
 */
public enum UIElementType {

	OVERVIEW_MAP_3D("Company Map"),
	
	HR_TEAM_DETAIL("Employees"), 
	HR_STATISTICS("Statistics"), 
	HR_WORKING_CONDITIONS("Working Conditions"), 
	HR_RECRUITING_OVERVIEW("Recruiting"),
	FINANCE_OVERVIEW("Finance Overview"),
	FINANCE_BANKING_SYSTEM("Banking System"),
    FINANCE_OPERATIONS_TABLE("Operations"),
	FINANCE_SALES_CHART("Statistics"),
	LOGISTICS_PARTNER_OVERVIEW("External Partner"),
	WAREHOUSE_LIST("Warehouses"),
	LOGISTICS_TRUCK_FLEET_OVERVIEW("Truck Fleet"),
	PRODUCTION_NEW_PRODUCT_OVERVIEW("Introduce Product"),
	PRODUCTION_PRODUCE_PRODUCT("Produce Products"),
	PRODUCTION_MACHINERY_OVERVIEW("Machines"),
	PROCUREMENT_BUY_COMPONENT_OVERVIEW("Buy Component"),
    PROCUREMENT_ORDERED_COMPONENTS_OVERVIEW("Ordered Components"),
	RESDEV_CATEGORY_UNLOCKER("Unlock Categories"),
	WAREHOUSE_SEGMENTS("Partition"),
	WAREHOUSE_STOCK_MANAGEMENT("Stock Management"),
	MARKETING_PRESSRELEASE("Press Releases"),
	MARKETING_CAMPAIGNS("Marketing Campaigns"),
	MARKETING_MARKETRESEARCH("Market Researches"),
	MARKETING_CONSULTANCIES("Consultancies"),
	MARKETING_OVERVIEW("Overview");

	// The title of the GameElement, that will be displayed on top.
	public final String title;

	private UIElementType(String title) {
		this.title = title;
	}
}
