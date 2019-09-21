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

	HR_EMPLOYEES_OVERVIEW("Employees"), 
	HR_RECRUITING_OVERVIEW("Recruiting"),
	FINANCE_OVERVIEW("Finance"),
    FINANCE_OPERATIONS_TABLE("Operations Table"),
	LOGISTICS_PARTNER_OVERVIEW("External Partner"),
	LOGISTICS_WAREHOUSE_OVERVIEW("Warehouses"),
	LOGISTICS_TRUCK_FLEET_OVERVIEW("Truck Fleet");

	// The title of the GameElement, that will be displayed on top.
	public final String title;

	private UIElementType(String title) {
		this.title = title;
	}
}
