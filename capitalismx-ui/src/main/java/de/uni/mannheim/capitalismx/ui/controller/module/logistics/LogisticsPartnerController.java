package de.uni.mannheim.capitalismx.ui.controller.module.logistics;

import java.net.URL;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.ui.components.logistics.LogisticsPartnerDetailListViewCell;
import de.uni.mannheim.capitalismx.ui.components.logistics.TruckListViewCell;
import javafx.scene.control.ListView;
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
	private ListView<ExternalPartner> externalPartnerListView;

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
			fireLogisticsPartnerButton.setVisible(true);
			externalPartnerListView.getItems().add(controller.getExternalPartner());
			logisticsPartnerButton.setText(UIManager.getLocalisedString("logistics.pselection.hiredifferent"));
		} else {
			fireLogisticsPartnerButton.setVisible(false);
			logisticsPartnerButton.setText(UIManager.getLocalisedString("logistics.pselection.hire"));
		}

		PopOverFactory factory = new PopOverFactory();
		factory.createStandardOverlay("fxml/popover/logistics_partner_detail_list.fxml");
		popover = factory.getPopover();
		
		logisticsPartnerButton.setOnAction(e -> {
			showPopover();
		});

		fireLogisticsPartnerButton.setOnAction(e -> {
			controller.removeExternalPartner();
			externalPartnerListView.getItems().clear();
			fireLogisticsPartnerButton.setVisible(false);
			logisticsPartnerButton.setText(UIManager.getLocalisedString("logistics.pselection.hire"));
		});

		externalPartnerListView.setCellFactory(partnerListView -> {
			LogisticsPartnerDetailListViewCell cell = new LogisticsPartnerDetailListViewCell(externalPartnerListView);
			cell.setAddMouseListener(false);
			return cell;
		});
		externalPartnerListView.setPlaceholder(new Label(UIManager.getLocalisedString("list.placeholder.externalpartner")));
	}

	/**
	 * Updates the logistics UI when an external partner is hired.
	 * @param externalPartner The new external logistics partner.
	 */
	public void addExternalPartner(ExternalPartner externalPartner) {
		externalPartnerListView.getItems().clear();
		fireLogisticsPartnerButton.setVisible(true);
		externalPartnerListView.getItems().add(externalPartner);
		logisticsPartnerButton.setText(UIManager.getLocalisedString("logistics.pselection.hiredifferent"));
		popover.hide();
	}

	/**
	 * Displays the external logistics partner selection popover.
	 */
	private void showPopover() {
		popover.show(UIManager.getInstance().getStage());
	}

}