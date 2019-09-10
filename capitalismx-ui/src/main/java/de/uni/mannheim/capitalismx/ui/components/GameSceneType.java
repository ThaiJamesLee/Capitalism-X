package de.uni.mannheim.capitalismx.ui.components;

/**
 * Determines the type of a {@link GameScene}.
 * 
 * @author Jonathan
 *
 */
public enum GameSceneType {

	// The default type. Probably means something went wrong.
	UNKNOWN,

	// The scenes for some menu.
	MENU_MAIN,

	// Scene for the Loading Screen.
	LOADING_SCREEN,

	// The scenes for the game itself.
	GAME_PAGE;

}