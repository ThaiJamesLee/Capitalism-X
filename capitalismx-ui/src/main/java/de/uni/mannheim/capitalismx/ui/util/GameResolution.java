package de.uni.mannheim.capitalismx.ui.util;

/**
 * Contains information about the resolution of the device, as well as the
 * currently active {@link CssSettings}.
 * 
 * @author Jonathan
 *
 */
public class GameResolution {

	private int width;

	private int height;

	private CssSettings currentCssSettings;

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public CssSettings getCurrentCssSettings() {
		return currentCssSettings;
	}

	public void setCurrentCssSettings(CssSettings currentlyActiveResolution) {
		this.currentCssSettings = currentlyActiveResolution;
	}

	public GameResolution(int width, int height, CssSettings activeResolution) {
		this.currentCssSettings = activeResolution;
		this.height = height;
		this.width = width;
	}

}
