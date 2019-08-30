package de.uni.mannheim.capitalismx.ui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.ui.components.UIElement;
import de.uni.mannheim.capitalismx.ui.utils.AnchorPaneHelper;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * The controller class for a standard {@link UIElement}, that controls the
 * frame and title of the element.
 * 
 * @author Jonathan
 *
 */
public class UIElementFrameController extends UIController {

	@FXML
	private Label titleLabel;

	@FXML
	private AnchorPane contentPane;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	// TODO include localization
	/**
	 * Set the title of the {@link UIElement}, so it can be displayed.
	 * 
	 * @param title The title to be displayed.
	 */
	public void setTitleLabel(String title) {
		titleLabel.setText(title);
	}

	/**
	 * Adds the actual content of the {@link UIElement} to the standard module.
	 * 
	 * @param rootElement The content of the module.
	 */
	public void initContent(Parent rootElement) {
		this.contentPane.getChildren().add(rootElement);
		AnchorPaneHelper.snapNodeToAnchorPane(rootElement);
	}

}
