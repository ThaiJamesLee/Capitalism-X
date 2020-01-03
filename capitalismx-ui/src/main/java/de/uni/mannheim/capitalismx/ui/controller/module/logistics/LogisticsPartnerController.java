package de.uni.mannheim.capitalismx.ui.controller.module.logistics;

import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.logistic.logistics.ExternalPartner;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.UIElementType;
import de.uni.mannheim.capitalismx.ui.controller.module.GameModuleController;
import de.uni.mannheim.capitalismx.ui.utils.CssHelper;
import de.uni.mannheim.capitalismx.ui.utils.PopOverHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.PopOver;
import org.controlsfx.control.PopOver.ArrowLocation;

public class LogisticsPartnerController extends GameModuleController {

	@FXML
	private Button logisticsPartnerButton;

	@FXML
	private Label currentlyEmployingTextArea;

	private PopOver popover;

	public LogisticsPartnerController() {
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		GameController controller = GameController.getInstance();
		if (controller.getExternalPartner() != null) {
			currentlyEmployingTextArea.setText(UIManager.getLocalisedString("logistics.partner.currentlyEmpl")
					+ controller.getExternalPartner().getName() + " "
					+ controller.getExternalPartner().getContractualCost());
		} else {
			currentlyEmployingTextArea.setText(UIManager.getLocalisedString("logistics.partner.currentlyNo"));
		}

		popover = PopOverHelper.createStandardOverlay("fxml/overlay/logistics_partner_detail_list.fxml");
		
		logisticsPartnerButton.setOnAction(e -> {
			showPopover();
		});
	}

	public void addExternalPartner(ExternalPartner externalPartner) {
		currentlyEmployingTextArea
				.setText(UIManager.getLocalisedString("logistics.partner.currentlyEmpl") + externalPartner.getName());
	}

	private void showPopover() {
		popover.show(UIManager.getInstance().getStage());
	}

}