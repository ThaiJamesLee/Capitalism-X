package de.uni.mannheim.components;

import java.util.ArrayList;

/**
 * The view of a department in the game and its {@link GameModule}s.
 * 
 * @author Jonathan
 *
 */
public class GameView {

	// The type of displayed content
	private GameViewType viewType;
	// The modules, that are part of this view
	private ArrayList<GameModule> modules;

	public GameView(GameViewType viewType) {
		this.viewType = viewType;
	}

	public ArrayList<GameModule> getModules() {
		return modules;
	}

	public GameViewType getViewType() {
		return viewType;
	}

	public void setModules(ArrayList<GameModule> modules) {
		this.modules = modules;
	}

}
