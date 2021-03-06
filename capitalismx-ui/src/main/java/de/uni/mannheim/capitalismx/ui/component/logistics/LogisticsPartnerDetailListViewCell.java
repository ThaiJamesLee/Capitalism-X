package de.uni.mannheim.capitalismx.ui.component.logistics;

import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.logistic.logistics.ExternalPartner;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.component.GameModuleType;
import de.uni.mannheim.capitalismx.ui.component.GameViewType;
import de.uni.mannheim.capitalismx.ui.controller.module.logistics.LogisticsPartnerController;
import de.uni.mannheim.capitalismx.ui.util.CapCoinFormatter;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.text.NumberFormat;

/**
 * This class represents an entry in the external logistics partner selection in the logistics UI. It thus shows
 * information about the characteristics of a specific external partner.
 *
 * @author sdupper
 */
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

	private FXMLLoader loader;

	private boolean addMouseListener;

    /**
     * The ListView of all available external logistics partners.
     */
    private ListView<ExternalPartner> logisticsPartnerDetailListView;

    /**
     * Constructor
     * @param logisticsPartnerDetailListView The ListView of all available external logistics partners.
     */
    public LogisticsPartnerDetailListViewCell(ListView<ExternalPartner> logisticsPartnerDetailListView){
        this.logisticsPartnerDetailListView = logisticsPartnerDetailListView;
        addMouseListener = true;
    }

    /*
     * Generates an entry in the external logistics partner selection for each new external partner added to the
     * logisticsPartnerDetailListView according to the partner's characteristics. If the user clicks on one, the partner
     * is hired. These characteristics are also displayed for the currently hired partner. However, in that case, the
     * OnMouseClicked EventHandler is not added (addMouseListener=false).
     */
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
								.getResource("fxml/component/logistics_partner_detail_list_cell.fxml"),
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
					UIManager.getLocalisedString("logistics.pselection.reliability") + ": " + NumberFormat.getPercentInstance(UIManager.getResourceBundle().getLocale())
							.format(externalPartner.getReliabilityIndexPartner() / 100.0));
			ecoIndexLabel
					.setText(UIManager.getLocalisedString("logistics.pselection.ecoindex") + ": " + NumberFormat.getPercentInstance(UIManager.getResourceBundle().getLocale())
							.format(externalPartner.getEcoIndexPartner() / 100.0));
			qualityIndexLabel
					.setText(UIManager.getLocalisedString("logistics.pselection.quality") + ": " + NumberFormat.getPercentInstance(UIManager.getResourceBundle().getLocale())
							.format(externalPartner.getQualityIndexPartner() / 100.0));
			contractualCostsLabel.setText(
					UIManager.getLocalisedString("logistics.pselection.contractual") + ": " + CapCoinFormatter.getCapCoins(externalPartner.getContractualCost()));
			variableCostsLabel.setText(
					UIManager.getLocalisedString("logistics.pselection.delivery") + ": " + CapCoinFormatter.getCapCoins(externalPartner.getVariableDeliveryCost()));

			if(addMouseListener){
				gridPane.setOnMouseClicked(e -> {
					controller.addExternalPartner(externalPartner);
					LogisticsPartnerController uiController = (LogisticsPartnerController) UIManager.getInstance()
							.getModule(GameModuleType.LOGISTICS_PARTNER_OVERVIEW).getController();
					uiController.addExternalPartner(externalPartner);

					logisticsPartnerDetailListView.getSelectionModel().clearSelection();
					//this.updateAvailablePartners();
				});
			}

			setText(null);
			setGraphic(gridPane);
		}
	}

	public void setAddMouseListener(boolean addMouseListener){
		this.addMouseListener = addMouseListener;
	}
}