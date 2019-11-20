package de.uni.mannheim.capitalismx.ui.components;

/**
 * Determines the type of GameView which is an abstraction of the displayed
 * GameElements of a department.
 * 
 * @author Jonathan
 *
 */
public enum GameViewType {

	OVERVIEW(1, "Overview"), 
	FINANCES(2, "Finance"),
	HR(3, "Human Resources"), 
	PRODUCTION(4, "Production"), 
	LOGISTIC(5, "Logistics"),
	WAREHOUSE(6, "Warehouse"), 
	R_AND_D(7, "Research & Development");

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
		return OVERVIEW;
	}

	public int getId() {
		return id;
	}
}
