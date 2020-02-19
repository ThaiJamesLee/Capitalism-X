package de.uni.mannheim.capitalismx.ui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.ui.components.GameModule;
import de.uni.mannheim.capitalismx.ui.components.GameModuleType;
import de.uni.mannheim.capitalismx.ui.utils.AnchorPaneHelper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * The controller class for the frame of a {@link GameModule}. Controls the
 * frame and title of the element.
 * 
 * @author Jonathan
 *
 */
public class ModuleFrameController implements Initializable {

	@FXML
	private Label titleLabel;

	@FXML
	private AnchorPane contentPane, headerPane;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	/**
	 * Set the title of the {@link GameModule}, so it can be displayed.
	 * 
	 * @param title The title to be displayed.
	 */
	public void setTitleLabel(String title) {
		titleLabel.setText(title);
	}

	/**
	 * Adds the actual content of the {@link GameModule} to the standard module.
	 * 
	 * @param rootElement The content of the module.
	 * @param elementType The {@link GameModuleType} of the {@link GameModule}
	 *                    contained by this frame.
	 */
	public void initContent(Parent rootElement, GameModuleType elementType) {
		this.contentPane.getChildren().add(rootElement);
		AnchorPaneHelper.snapNodeToAnchorPane(rootElement);
	}

}
