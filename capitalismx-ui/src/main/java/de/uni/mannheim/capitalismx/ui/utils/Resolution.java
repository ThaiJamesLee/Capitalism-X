package de.uni.mannheim.capitalismx.ui.utils;

/**
 * Contains information about the resolution of the device, as well as the
 * currently active {@link SupportedGameResolution}.
 * 
 * @author Jonathan
 *
 */
public class Resolution {

	private int width;

	private int height;

	private SupportedGameResolution currentlyActiveResolution;

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

	public SupportedGameResolution getCurrentlyActiveResolution() {
		return currentlyActiveResolution;
	}

	public void setCurrentlyActiveResolution(SupportedGameResolution currentlyActiveResolution) {
		this.currentlyActiveResolution = currentlyActiveResolution;
	}

	public Resolution(int width, int height, SupportedGameResolution activeResolution) {
		this.currentlyActiveResolution = activeResolution;
		this.height = height;
		this.width = width;
	}

}
