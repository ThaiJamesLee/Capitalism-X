package de.uni.mannheim.capitalismx.ui.utils;

/**
 * Contains information about the resolution of the device, as well as the
 * currently active {@link SupportedResolution}.
 * 
 * @author Jonathan
 *
 */
public class GameResolution {

	private int width;

	private int height;

	private SupportedResolution currentlyActiveResolution;

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

	public SupportedResolution getCurrentlyActiveResolution() {
		return currentlyActiveResolution;
	}

	public void setCurrentlyActiveResolution(SupportedResolution currentlyActiveResolution) {
		this.currentlyActiveResolution = currentlyActiveResolution;
	}

	public GameResolution(int width, int height, SupportedResolution activeResolution) {
		this.currentlyActiveResolution = activeResolution;
		this.height = height;
		this.width = width;
	}

}
