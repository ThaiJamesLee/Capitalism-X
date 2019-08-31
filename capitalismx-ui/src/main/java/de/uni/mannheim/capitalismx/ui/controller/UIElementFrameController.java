package de.uni.mannheim.capitalismx.ui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.ui.application.Main;
import de.uni.mannheim.capitalismx.ui.components.UIElement;
import de.uni.mannheim.capitalismx.ui.components.UIElementType;
import de.uni.mannheim.capitalismx.ui.utils.AnchorPaneHelper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
public class UIElementFrameController implements Initializable {

	@FXML
	private Label titleLabel;

	@FXML
	private AnchorPane contentPane, headerPane;

	private UIElementType elementType;

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
	 * @param elementType The {@link UIElementType} of the {@link UIElement}
	 *                    contained by this frame.
	 */
	public void initContent(Parent rootElement, UIElementType elementType) {
		this.contentPane.getChildren().add(rootElement);
		AnchorPaneHelper.snapNodeToAnchorPane(rootElement);
		this.elementType = elementType;
	}

	@FXML
	private void showOverlay() {
		Main.getManager().getGamePageController().showOverlay(elementType);
	}

}
