package de.uni.mannheim.components;

/**
 * Determines which view is currently present on the GamePage.
 * 
 * @author Jonathan
 *
 */
public enum GameViewType {
	
	GAME_OVERVIEW("Overview"),
	GAME_HR("Human Resources"),
	GAME_LOGISTIC("Logistics"),
	GAME_PRODUCTION("Production"),
	GAME_PROCUREMENT("Procurement"),
	GAME_WAREHOUSE("Warehouse"),
	GAME_FINANCES("Finances"),
	GAME_MARKETING("Marketing");
	
	private final String title;
	
	private GameViewType(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
}
