package de.uni.mannheim.capitalismx.ui.components;

import de.uni.mannheim.capitalismx.ui.application.UIManager;

/**
 * Determines the type of GameView which is an abstraction of the displayed
 * GameElements of a department.
 * 
 * @author Jonathan
 *
 */
public enum GameViewType {

	OVERVIEW(1, "view.overview"), 
	FINANCES(2, "view.finance"),
	HR(3, "view.hr"), 
	SALES(4, "view.sales"),
	PRODUCTION(5, "view.production"), 
	LOGISTIC(6, "view.logistics"),
	WAREHOUSE(7, "view.warehouse"), 
	R_AND_D(8, "view.research"),
	MARKETING(9, "view.marketing");
	
	private final String title;
	private final int id;

	private GameViewType(int id, String title) {
		this.title = title;
		this.id = id;
	}

	public String getTitle() {
		return UIManager.getLocalisedString(title);
	}

	/**
	 * Get the {@link GameViewType} with the given id.
	 * 
	 * @param id Id of the {@link GameViewType}.
	 * @return {@link GameViewType} with the id.
	 */
	public static GameViewType getTypeById(int id) {
		for (GameViewType type : GameViewType.values()) {
			if (type.id == id) {
				return type;
			}
		}
		return OVERVIEW;
	}

	public int getId() {
		return id;
	}
}
