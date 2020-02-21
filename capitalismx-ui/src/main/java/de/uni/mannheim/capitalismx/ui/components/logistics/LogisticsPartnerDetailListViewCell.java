package de.uni.mannheim.capitalismx.ui.components.logistics;

import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.logistic.logistics.ExternalPartner;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.GameViewType;
import de.uni.mannheim.capitalismx.ui.components.GameModuleType;
import de.uni.mannheim.capitalismx.ui.controller.module.logistics.LogisticsPartnerController;
import de.uni.mannheim.capitalismx.ui.utils.CapCoinFormatter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.text.NumberFormat;

public class LogisticsPartnerDetailListViewCell extends ListCell<ExternalPartner> {

	@FXML
	private Label nameLabel;

	@FXML
	private Label reliabilityIndexLabel;

	@FXML
	private Label ecoIndexLabel;

	@FXML
	private Label qualityIndexLabel;

	@FXML
	GridPane gridPane;

	@FXML
	private Label contractualCostsLabel;

	@FXML
	private Label variableCostsLabel;

	@FXML
	private Button buyButton;

	private FXMLLoader loader;

	private ListView<ExternalPartner> logisticsPartnerDetailListView;

	public LogisticsPartnerDetailListViewCell(ListView<ExternalPartner> logisticsPartnerDetailListView) {
		this.logisticsPartnerDetailListView = logisticsPartnerDetailListView;
	}

	@Override
	protected void updateItem(ExternalPartner externalPartner, boolean empty) {
		super.updateItem(externalPartner, empty);
		if (empty || externalPartner == null) {
			setText(null);
			setGraphic(null);
		} else {
			if (loader == null) {
				loader = new FXMLLoader(
						getClass().getClassLoader()
								.getResource("fxml/components/logistics_partner_detail_list_cell.fxml"),
						UIManager.getResourceBundle());
				loader.setController(this);

				try {
					loader.load();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

			GameController controller = GameController.getInstance();
			nameLabel.setText(externalPartner.getName()); // TODO localization
			reliabilityIndexLabel.setText(
					"Reliability: " + NumberFormat.getPercentInstance(UIManager.getResourceBundle().getLocale())
							.format(externalPartner.getReliabilityIndexPartner()));
			ecoIndexLabel
					.setText("Eco Index: " + NumberFormat.getPercentInstance(UIManager.getResourceBundle().getLocale())
							.format(externalPartner.getEcoIndexPartner()));
			qualityIndexLabel
					.setText("Quality: " + NumberFormat.getPercentInstance(UIManager.getResourceBundle().getLocale())
							.format(externalPartner.getQualityIndexPartner()));
			contractualCostsLabel.setText(
					"Contractual Costs: " + CapCoinFormatter.getCapCoins(externalPartner.getContractualCost()));
			variableCostsLabel.setText(
					"Delivery Costs: " + CapCoinFormatter.getCapCoins(externalPartner.getVariableDeliveryCost()));
			gridPane.setOnMouseClicked(e -> {
				controller.addExternalPartner(externalPartner);
				LogisticsPartnerController uiController = (LogisticsPartnerController) UIManager.getInstance()
						.getModule(GameModuleType.LOGISTICS_PARTNER_OVERVIEW).getController();
				uiController.addExternalPartner(externalPartner);

				logisticsPartnerDetailListView.getSelectionModel().clearSelection();
			});

			setText(null);
			setGraphic(gridPane);
		}
	}

}