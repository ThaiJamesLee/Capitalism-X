package de.uni.mannheim.capitalismx.ui.components;

import java.util.ArrayList;
import java.util.List;

/**
 * The view of a department in the game and its {@link GameModule}s. TODO
 * implement differentiation between active and inactive modules (different
 * lists? attribute in module?)
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
		modules = new ArrayList<GameModule>();
		this.viewType = viewType;
	}

	public GameViewType getViewType() {
		return viewType;
	}

	public void addModule(GameModule module) {
		modules.add(module);
	}

	public List<GameModule> getModules() {
		return this.modules;
	}

	/**
	 * Get the {@link GameModule} of the given {@link GameModuleType}.
	 * 
	 * @param type
	 *            The {@link GameModuleType} of the module.
	 * @return The {@link GameModule} or null if no module was found.
	 */
	public GameModule getModule(GameModuleType type) {
		for (GameModule module : modules) {
			if (module.getType() == type) {
				return module;
			}
		}
		return null;
	}
}
