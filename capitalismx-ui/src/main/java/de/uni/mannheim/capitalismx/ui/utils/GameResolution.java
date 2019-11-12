package de.uni.mannheim.capitalismx.ui.utils;

/**
 * Contains information about the resolution of the device, as well as the
 * currently active {@link CssResolution}.
 * 
 * @author Jonathan
 *
 */
public class GameResolution {

	private int width;

	private int height;

	private CssResolution currentlyActiveResolution;

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

	public CssResolution getCurrentlyActiveResolution() {
		return currentlyActiveResolution;
	}

	public void setCurrentlyActiveResolution(CssResolution currentlyActiveResolution) {
		this.currentlyActiveResolution = currentlyActiveResolution;
	}

	public GameResolution(int width, int height, CssResolution activeResolution) {
		this.currentlyActiveResolution = activeResolution;
		this.height = height;
		this.width = width;
	}

}
