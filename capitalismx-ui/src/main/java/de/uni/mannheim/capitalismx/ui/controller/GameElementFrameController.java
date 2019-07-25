package de.uni.mannheim.capitalismx.ui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.ui.components.GameElement;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * The controller class for a standard {@link GameElement}, that controls the
 * frame and title of the element.
 * 
 * @author Jonathan
 *
 */
public class GameElementFrameController extends UIController {

	@FXML
	private Label titleLabel;

	@FXML
	private AnchorPane contentPane;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	// TODO include localization
	/**
	 * Set the title of the {@link GameElement}, so it can be displayed.
	 * 
	 * @param title The title to be displayed.
	 */
	public void setTitleLabel(String title) {
		titleLabel.setText(title);
	}

	/**
	 * Adds the actual content of the {@link GameElement} to the standard module.
	 * 
	 * @param rootElement The content of the module.
	 */
	public void initContent(Parent rootElement) {
		this.contentPane.getChildren().add(rootElement);
		AnchorPane.setBottomAnchor(rootElement, 0.0);
		AnchorPane.setRightAnchor(rootElement, 0.0);
		AnchorPane.setLeftAnchor(rootElement, 0.0);
		AnchorPane.setTopAnchor(rootElement, 0.0);
	}

}
