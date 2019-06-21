package de.uni.mannheim.capitalismx.ui.components;

/**
 * The type of a {@link GameModule} specifies the fxml-File and it's default
 * position and size on the module-grid.
 * 
 * @author Jonathan
 *
 */
public enum GameModuleType {

	// TODO remove all the placeholder module types!
	// The default type. Probably means something went wrong.
//	UNKNOWN(0, "", GameViewType.GAME_OVERVIEW, 0, 0, 0, 0),

	// The modules for the overview. (1-10)
//	OVERVIEW_PLACEHOLDER(1, "m_overview_placeholder.fxml", GameViewType.GAME_OVERVIEW, 0, 1, 0, 1),

	// The modules for HR. (11-20)
	HR_EMPLOYEES_TABLE(11, "employee_table.fxml", GameViewType.GAME_HR, 0, 2, 0, 3),
	HR_RECRUITING_LIST(12, "recruiting_list.fxml", GameViewType.GAME_HR, 3, 2, 0, 2)

	// The modules for Logistics. (21-30)
//	LOGISTIC_PLACEHOLDER(21, "m_logistic_placeholder.fxml", GameViewType.GAME_LOGISTIC, 0, 1, 0, 1),

	// The modules for Production. (31-40)
//	PRODUCTION_PLACEHOLDER(31, "m_production_placeholder.fxml", GameViewType.GAME_PRODUCTION, 0, 1, 0, 1),

	// The modules for Procurement. (41-50)
//	PROCUREMENT_PLACEHOLDER(41, "m_procurement_placeholder.fxml", GameViewType.GAME_PROCUREMENT, 0, 1, 0, 1),

	// The modules for the warehouse. (51-60)
//	WAREHOUSE_PLACEHOLDER(51, "m_warehouse_placeholder.fxml", GameViewType.GAME_WAREHOUSE, 0, 1, 0, 1),

	// The modules for HR. (61-70)
//	FINANCES_PLACEHOLDER(61, "m_finances_placeholder.fxml", GameViewType.GAME_FINANCES, 0, 1, 0, 1),

	// The modules for HR. (71-80)
//	MARKETING_PLACEHOLDER(71, "m_marketing_placeholder.fxml", GameViewType.GAME_MARKETING, 0, 1, 0, 1)
	;

	// The unique id of the type.
	public final int id;
	// The name of the fxml-file defining this type.
	public final String fxmlFile;
	// The contentType of the module.
	public GameViewType viewType;
	// Starting column position in the grid.
	public final int gridColStart;
	// Number of columns spanned by the module in the grid.
	public final int gridColSpan;
	// Starting row position in the grid.
	public final int gridRowStart;
	// Number of rows spanned by the module in the grid.
	public final int gridRowSpan;

	/**
	 * Create a new {@link GameModuleType} with default values for the actual
	 * modules.
	 * 
	 * @param id
	 *            The unique id of the type.
	 * @param fxmlFile
	 *            The name of the fxml-file defining this type.
	 * @param viewType
	 *            The contentType of this module.
	 * @param colStart
	 *            Starting column position in the grid.
	 * @param colSpan
	 *            Number of columns spanned by the module in the grid.
	 * @param rowStart
	 *            Starting row position in the grid.
	 * @param rowSpan
	 *            Number of rows spanned by the module in the grid.
	 */
	private GameModuleType(int id, String fxmlFile, GameViewType viewType, int colStart, int colSpan, int rowStart,
			int rowSpan) {
		this.id = id;
		this.viewType = viewType;
		this.fxmlFile = fxmlFile;
		this.gridColStart = colStart;
		this.gridColSpan = colSpan;
		this.gridRowStart = rowStart;
		this.gridRowSpan = rowSpan;
	}

}
