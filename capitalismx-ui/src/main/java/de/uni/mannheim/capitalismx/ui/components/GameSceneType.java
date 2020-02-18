package de.uni.mannheim.capitalismx.ui.components;

/**
 * Determines the type of a {@link GameScene}.
 * 
 * @author Jonathan
 *
 */
public enum GameSceneType {

	/**
	 * The default type. Probably means something went wrong.
	 */
	UNKNOWN,

	/**
	 *  The scenes for some menu.
	 */
	MENU_MAIN,

	/**
	 *  Scene for the Loading Screen.
	 */
	LOADING_SCREEN,

	/**
	 *  The scenes for the game itself.
	 */
	GAME_PAGE,
	
	/**
	 * The page containing credits info, accessible from the MainMenu
	 */
	CREDITS_PAGE,

//	TODO
//	/**
//	 * The page containing the leaderboard with previously achieved highscores, accessible from the MainMenu
//	 */
//	LEADERBOARD_PAGE,
	
	/**
	 * Displayed if the players lost the game
	 */
	GAMELOST_PAGE,
	
	/**
	 * Displayed if the player reaches the enddate and thus won the game
	 */
	GAMEWON_PAGE
	;
}
