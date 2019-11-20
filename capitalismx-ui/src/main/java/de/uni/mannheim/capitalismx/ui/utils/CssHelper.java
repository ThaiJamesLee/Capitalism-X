package de.uni.mannheim.capitalismx.ui.utils;

import java.util.ArrayList;
import java.util.List;

import de.uni.mannheim.capitalismx.ui.application.UIManager;
import javafx.scene.Parent;

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

}
