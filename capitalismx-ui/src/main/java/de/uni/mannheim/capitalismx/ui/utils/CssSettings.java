package de.uni.mannheim.capitalismx.ui.utils;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

/**
 * All the resolutions the game supports. Each {@link CssSettings} contains
 * information about the width and height of the resolution, as well as a String
 * that specifies the folder, containing the cssFiles for that resolution.
 * 
 * @author Jonathan
 *
 */
public enum CssSettings {

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

	private CssSettings(int width, int height, String cssSourceFolder) {
		this.cssSourceFolder = cssSourceFolder;
		this.height = height;
		this.width = width;
	}

}
