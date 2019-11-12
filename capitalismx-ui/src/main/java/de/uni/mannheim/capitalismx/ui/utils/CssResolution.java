package de.uni.mannheim.capitalismx.ui.utils;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

/**
 * All the resolutions the game supports. Each {@link CssResolution} contains
 * information about the width and height of the resolution, as well as a String
 * that specifies the folder, containing the cssFiles for that resolution.
 * 
 * @author Jonathan
 *
 */
public enum CssResolution {

	RES_1080(1920, 1080, "1080p"),
	RES_720(1280, 720, "1080p");

	// width of the resolution in pixel
	private int width;

	// height of the resolution in pixel
	private int height;

	// Name of the folder, the css-Files of this Resolution can be found in, as well
	// as the part of the filenames that will be replaced.
	private String cssSourceFolder;

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public String getCssSourceFolder() {
		return cssSourceFolder;
	}

	private CssResolution(int width, int height, String cssSourceFolder) {
		this.cssSourceFolder = cssSourceFolder;
		this.height = height;
		this.width = width;
	}

	/**
	 * Given the bounds of a {@link Screen}. The method returns the
	 * {@link CssResolution} that fits best to the screen. To calculate that, the
	 * difference of the width and height of Resolution and screenbounds is
	 * compared.
	 * 
	 * @param screenBounds The {@link Rectangle2D} describing the bounds of the
	 *                     {@link Screen}.
	 * @return The optimal {@link CssResolution} for the given bounds.
	 */
	public static CssResolution getOptimalResolution(Rectangle2D screenBounds) {
		int minDiffToScreenBounds = 10000;
		CssResolution resolutionWithSmallestDiff = null;
		for (CssResolution resolution : CssResolution.values()) {
			int diffToScreenBounds = (int) Math.abs(screenBounds.getWidth() - resolution.getWidth())
					+ (int) Math.abs(screenBounds.getHeight() - resolution.getHeight());
			if (diffToScreenBounds < minDiffToScreenBounds) {
				minDiffToScreenBounds = diffToScreenBounds;
				resolutionWithSmallestDiff = resolution;
			}
		}
		return resolutionWithSmallestDiff;
	}

}
