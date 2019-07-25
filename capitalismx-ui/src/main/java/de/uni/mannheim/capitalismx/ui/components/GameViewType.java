package de.uni.mannheim.capitalismx.ui.components;

/**
 * Determines which view is currently present on the GamePage.
 * 
 * @author Jonathan
 *
 */
public enum GameViewType {
	
	OVERVIEW("Overview"),
	HR("Human Resources"),
	LOGISTIC("Logistics"),
	PRODUCTION("Production"),
	PROCUREMENT("Procurement"),
	WAREHOUSE("Warehouse"),
	FINANCES("Finances"),
	MARKETING("Marketing");
	
	private final String title;
	
	private GameViewType(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
}
