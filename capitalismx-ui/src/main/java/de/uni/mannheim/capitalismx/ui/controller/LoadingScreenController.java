package de.uni.mannheim.capitalismx.ui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.ui.components.GameScene;
import de.uni.mannheim.capitalismx.ui.utils.CssHelper;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;

/**
 * Controller for the LoadingScreen-{@link GameScene}. Mainly manages the
 * {@link ProgressBar}..
 * 
 * @author Jonathan
 *
 */
public class LoadingScreenController implements Initializable {

	@FXML
	private AnchorPane root;

	@FXML
	private ProgressBar progressBar;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		CssHelper.replaceStylesheets(root.getStylesheets());
	}

	/**
	 * Initialize the {@link ProgressBar} and provide it with a
	 * {@link ReadOnlyDoubleProperty}, that its progress can be bound to.
	 * 
	 * @param readOnlyDoubleProperty The {@link ReadOnlyDoubleProperty} to bind the
	 *                               progress of the {@link ProgressBar} to.
	 */
	public void initProgressBar(ReadOnlyDoubleProperty readOnlyDoubleProperty) {
		this.progressBar.progressProperty().bind(readOnlyDoubleProperty);
	}

}
