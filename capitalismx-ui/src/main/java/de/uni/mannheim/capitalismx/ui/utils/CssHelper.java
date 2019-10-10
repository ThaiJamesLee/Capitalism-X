package de.uni.mannheim.capitalismx.ui.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * This class provides some static methods that help with the automatic
 * selection-process of the css-Files.
 * 
 * @author Jonathan
 *
 */
public class CssHelper {

	/**
	 * Method that copies the css-Files of the specified {@link GameResolution} to
	 * the css-Directory, where they will be used by the game.
	 * 
	 * @param resolution The {@link GameResolution} to get the css for.
	 */
	public static void adjustCssToResolution(GameResolution resolution) {
		File cssSourceDirectory = new File("src/main/resources/css/" + resolution.getCssSourceFolder() + "/");
		File cssTargetDirectory = new File("target/classes/css/");
		for (File cssFile : cssSourceDirectory.listFiles()) {
			File targetFile = new File(cssTargetDirectory.getAbsolutePath() + "/"
					+ cssFile.getName().replace(resolution.getCssSourceFolder(), ""));
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
