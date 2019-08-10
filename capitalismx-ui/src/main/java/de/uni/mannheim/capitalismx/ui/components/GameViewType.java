package de.uni.mannheim.capitalismx.ui.components;

/**
 * Determines the type of GameView which is an abstraction 
 * of the displayed GameElements of a department.
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
