package de.uni.mannheim.capitalismx.ui.util;

import java.util.ArrayList;
import java.util.List;

import de.uni.mannheim.capitalismx.ui.application.UIManager;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.stage.Screen;

/**
 * This class provides some static methods that help with the automatic
 * selection-process of the css-Files.
 * 
 * @author Jonathan
 *
 */
public class CssHelper {

	/**
	 * Replaces the given List of Styleheets with the Stylesheets of the current
	 * {@link CssSettings} specified in the {@link UIManager}.
	 * 
	 * @param stylesheets {@link List} of Stylesheets to replace. (Obtainable by
	 *                    calling getStylesheets() on a {@link Parent})
	 */
	public static void replaceStylesheets(List<String> stylesheets) {
		ArrayList<String> newSheets = new ArrayList<String>();
		CssSettings cssSettings = UIManager.getInstance().getGameResolution().getCurrentCssSettings();
		for (String sheet : stylesheets) {
			sheet = sheet.replaceAll("\\/css\\/(\\d+p\\/)?", "/css/" + cssSettings.getCssSourceFolder() + "/");
			newSheets.add(sheet.replaceAll("(\\d+p)?\\.css", cssSettings.getCssSourceFolder() + ".css"));
		}
		stylesheets.clear();
		stylesheets.addAll(newSheets);
	}
	
	/**
	 * Given the bounds of a {@link Screen}. The method returns the
	 * {@link CssSettings} that fits best to the screen. To calculate that, the
	 * difference of the width and height of Resolution and screenbounds is
	 * compared.
	 * 
	 * @param screenBounds The {@link Rectangle2D} describing the bounds of the
	 *                     {@link Screen}.
	 * @return The optimal {@link CssSettings} for the given bounds.
	 */
	public static CssSettings getOptimalResolution(Rectangle2D screenBounds) {
		int minDiffToScreenBounds = 10000;
		CssSettings resolutionWithSmallestDiff = null;
		for (CssSettings resolution : CssSettings.values()) {
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
