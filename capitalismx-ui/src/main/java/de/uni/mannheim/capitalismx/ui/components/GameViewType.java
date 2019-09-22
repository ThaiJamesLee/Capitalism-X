package de.uni.mannheim.capitalismx.ui.components;

/**
 * Determines the type of GameView which is an abstraction of the displayed
 * GameElements of a department.
 * 
 * @author Jonathan
 *
 */
public enum GameViewType {

	UNKNOWN(0, "Unknown"), 
	OVERVIEW(1, "Overview"), 
	HR(2, "Human Resources"), 
	LOGISTIC(3, "Logistics"),
	PRODUCTION(4, "Production"), 
	PROCUREMENT(5, "Procurement"), 
	WAREHOUSE(6, "Warehouse"), 
	FINANCES(7, "Finance"),
	MARKETING(8, "Marketing");

	private final String title;
	private final int id;

	private GameViewType(int id, String title) {
		this.title = title;
		this.id = id;
	}

	public String getTitle() {
		return title;
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
		return UNKNOWN;
	}

	public int getId() {
		return id;
	}
}
