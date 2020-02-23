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

/**
 * This class represents the external logistics partner in the logistics UI. It allows the user to hire an external
 * logistics partner.
 *
 * @author sdupper
 */
public class LogisticsPartnerController implements Initializable {

	@FXML
	private Button logisticsPartnerButton;

	@FXML
	private Button fireLogisticsPartnerButton;

	@FXML
	private Label currentlyEmployingTextArea;

	/**
	 * The popover for the external logistics partner selection.
	 */
	private PopOver popover;

	public LogisticsPartnerController() {
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
			fireLogisticsPartnerButton.setVisible(true);
		} else {
			currentlyEmployingTextArea.setText(UIManager.getLocalisedString("logistics.partner.currentlyNo"));
			fireLogisticsPartnerButton.setVisible(false);
		}

		PopOverFactory factory = new PopOverFactory();
		factory.createStandardOverlay("fxml/popover/logistics_partner_detail_list.fxml");
		popover = factory.getPopover();
		
		logisticsPartnerButton.setOnAction(e -> {
			showPopover();
		});

		fireLogisticsPartnerButton.setOnAction(e -> {
			controller.removeExternalPartner();
			currentlyEmployingTextArea.setText(UIManager.getLocalisedString("logistics.partner.currentlyNo"));
			fireLogisticsPartnerButton.setVisible(false);
		});
	}

	/**
	 * Updates the logistics UI when an external partner is hired.
	 * @param externalPartner The new external logistics partner.
	 */
	public void addExternalPartner(ExternalPartner externalPartner) {
		currentlyEmployingTextArea.setText(UIManager.getLocalisedString("logistics.partner.currentlyEmpl")
				+ externalPartner.getName() + " "
				+ externalPartner.getContractualCost());
		popover.hide();
		fireLogisticsPartnerButton.setVisible(true);
	}

	/**
	 * Displays the external logistics partner selection popover.
	 */
	private void showPopover() {
		popover.show(UIManager.getInstance().getStage());
	}

}