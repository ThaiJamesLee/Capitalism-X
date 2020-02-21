package de.uni.mannheim.capitalismx.ui.controller.module.logistics;

import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.logistic.logistics.ExternalPartner;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.controller.module.GameModuleController;
import de.uni.mannheim.capitalismx.ui.utils.PopOverFactory;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.PopOver;

/**
 * This class represents the external logistics partner in the logistics UI. It allows the user to hire an external
 * logistics partner.
 *
 * @author sdupper
 */
public class LogisticsPartnerController extends GameModuleController {

	@FXML
	private Button logisticsPartnerButton;

	@FXML
	private Label currentlyEmployingTextArea;

	/**
	 * The popover for the external logistics partner selection.
	 */
	private PopOver popover;

	public LogisticsPartnerController() {
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
	}

	/*
	 * Displays information about the current external logistics partner and generates the external logistics partner
	 * selection popover. The popover is displayed after clicking the logisticsPartnerButton.
	 */
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

	/**
	 * Updates the logistics UI when an external partner is hired.
	 * @param externalPartner The new external logistics partner.
	 */
	public void addExternalPartner(ExternalPartner externalPartner) {
		currentlyEmployingTextArea
				.setText(UIManager.getLocalisedString("logistics.partner.currentlyEmpl") + externalPartner.getName());
		popover.hide();
	}

	/**
	 * Displays the external logistics partner selection popover.
	 */
	private void showPopover() {
		popover.show(UIManager.getInstance().getStage());
	}

}