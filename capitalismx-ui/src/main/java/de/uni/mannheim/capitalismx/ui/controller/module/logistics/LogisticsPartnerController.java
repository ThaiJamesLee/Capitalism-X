package de.uni.mannheim.capitalismx.ui.controller.module.logistics;

import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.PopOver;

import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.logistic.logistics.ExternalPartner;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.utils.PopOverFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class LogisticsPartnerController implements Initializable {

	@FXML
	private Button logisticsPartnerButton;

	@FXML
	private Label currentlyEmployingTextArea;

	private PopOver popover;

	public LogisticsPartnerController() {
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

		PopOverFactory factory = new PopOverFactory();
		factory.createStandardOverlay("fxml/popover/logistics_partner_detail_list.fxml");
		popover = factory.getPopover();
		
		logisticsPartnerButton.setOnAction(e -> {
			showPopover();
		});
	}

	public void addExternalPartner(ExternalPartner externalPartner) {
		currentlyEmployingTextArea
				.setText(UIManager.getLocalisedString("logistics.partner.currentlyEmpl") + externalPartner.getName());
		popover.hide();
	}

	private void showPopover() {
		popover.show(UIManager.getInstance().getStage());
	}

}