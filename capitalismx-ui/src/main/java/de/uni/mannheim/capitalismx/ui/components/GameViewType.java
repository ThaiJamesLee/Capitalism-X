package de.uni.mannheim.capitalismx.ui.components;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconName;
import de.uni.mannheim.capitalismx.ui.application.UIManager;

/**
 * Determines the type of GameView which is an abstraction of the displayed
 * GameElements of a department.
 * 
 * @author Jonathan
 *
 */
public enum GameViewType {


	OVERVIEW(1, "view.overview", FontAwesomeIconName.COMPASS, false), 
	FINANCES(2, "view.finance", FontAwesomeIconName.MONEY, false),
	HR(3, "view.hr", FontAwesomeIconName.USER, true), 
	SALES(4, "view.sales", FontAwesomeIconName.SHOPPING_CART, false),
	PRODUCTION(5, "view.production", FontAwesomeIconName.GEARS, true),
	LOGISTIC(6, "view.logistics", FontAwesomeIconName.TRUCK, true),
	WAREHOUSE(7, "view.warehouse", FontAwesomeIconName.CUBES, true),
	R_AND_D(8, "view.research", FontAwesomeIconName.FLASK, true),
	MARKETING(9, "view.marketing",FontAwesomeIconName.BULLHORN , true);

	private final String title;
	private final int id;
	private final FontAwesomeIconName iconName;
	private final boolean upgradeable;

	private GameViewType(int id, String title, FontAwesomeIconName icon, boolean upgradeable) {
		this.title = title;
		this.id = id;
		this.iconName = icon;
		this.upgradeable = upgradeable;
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
	
	public boolean isUpgradeable() {
		return upgradeable;
	}

	/**
	 * Get the icon for the {@link GameViewType}.
	 * 
	 * @param size The size of the icon. (Default is '1.0em')
	 * @return The {@link FontAwesomeIcon}
	 */
	public FontAwesomeIcon getGameViewIcon(String size) {
		FontAwesomeIcon icon = new FontAwesomeIcon();
		icon.setIcon(this.iconName);
		icon.setSize(size);
		return icon;
	}
}
