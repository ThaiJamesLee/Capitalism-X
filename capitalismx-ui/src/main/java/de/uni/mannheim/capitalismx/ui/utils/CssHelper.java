package de.uni.mannheim.capitalismx.ui.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import de.uni.mannheim.capitalismx.ui.application.UIManager;

/**
 * This class provides some static methods that help with the automatic
 * selection-process of the css-Files.
 * 
 * @author Jonathan
 *
 */
public class CssHelper {

	/**
	 * Method that copies the css-Files of the specified {@link CssResolution} to
	 * the css-Directory, where they will be used by the game.
	 */
	public static void adjustCssToCurrentResolution() {
		File cssSourceDirectory = new File(CssHelper.class.getResource("/css/" + UIManager.getInstance().getGameResolution().getCurrentlyActiveResolution().getCssSourceFolder() + "/").getFile());
		File cssTargetDirectory = new File(CssHelper.class.getResource("/css/").getFile());
		for (File cssFile : cssSourceDirectory.listFiles()) {
			File targetFile = new File(cssTargetDirectory.getAbsolutePath() + "/"
					+ cssFile.getName().replace(UIManager.getInstance().getGameResolution().getCurrentlyActiveResolution().getCssSourceFolder(), ""));
			if (!targetFile.exists()) {
				targetFile.mkdirs();
			}
			try {
				Files.copy(cssFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
