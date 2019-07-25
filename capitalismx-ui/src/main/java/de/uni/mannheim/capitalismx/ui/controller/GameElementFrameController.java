package de.uni.mannheim.capitalismx.ui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.ui.components.GameModule;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class GameModuleController extends UIController {

	@FXML
	private Label titleLabel;

	@FXML
	private AnchorPane contentPane;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	// TODO include localization
	/**
	 * Set the title of the {@link GameModule}, so it can be displayed.
	 * 
	 * @param title The title to be displayed.
	 */
	public void setTitleLabel(String title) {
		System.out.println("Set title to " + title);
		titleLabel.setText(title);
	}

	/**
	 * Adds the actual content of the {@link GameModule} to the standard module.
	 * 
	 * @param rootElement The content of the module.
	 */
	public void setContent(Parent rootElement) {
		this.contentPane.getChildren().add(rootElement);
		AnchorPane.setBottomAnchor(rootElement, 0.0);
		AnchorPane.setRightAnchor(rootElement, 0.0);
		AnchorPane.setLeftAnchor(rootElement, 0.0);
		AnchorPane.setTopAnchor(rootElement, 0.0);
	}

}
