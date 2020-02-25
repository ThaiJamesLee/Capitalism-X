package de.uni.mannheim.capitalismx.ui.components.logistics;

import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.logistic.logistics.ExternalPartner;
import de.uni.mannheim.capitalismx.logistic.support.ProductSupport;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.GameModuleType;
import de.uni.mannheim.capitalismx.ui.controller.module.logistics.LogisticsPartnerController;
import de.uni.mannheim.capitalismx.ui.controller.module.logistics.SupportPartnerController;
import de.uni.mannheim.capitalismx.ui.utils.CapCoinFormatter;
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
 * This class represents an entry in the external support partner selection in the logistics UI. It thus shows
 * information about the characteristics of a specific external partner.
 *
 * @author sdupper
 */
public class SupportPartnerListViewCell extends ListCell<ProductSupport.ExternalSupportPartner> {

    @FXML
    private Label nameLabel;

    @FXML
    private Label qualityIndexLabel;

    @FXML
    GridPane gridPane;

    @FXML
    private Label contractualCostsLabel;

    private FXMLLoader loader;

    private boolean addMouseListener;

    /**
     * The ListView of all available external support partners.
     */
    private ListView<ProductSupport.ExternalSupportPartner> supportPartnerDetailListView;

    /**
     * Constructor
     * @param supportPartnerDetailListView The ListView of all available external support partners.
     */
    public SupportPartnerListViewCell(ListView<ProductSupport.ExternalSupportPartner> supportPartnerDetailListView){
        this.supportPartnerDetailListView = supportPartnerDetailListView;
        addMouseListener = true;
    }

    /*
     * Generates an entry in the external support partner selection for each new external partner added to the
     * supportPartnerDetailListView according to the partner's characteristics. If the user clicks on one, the partner
     * is hired. These characteristics are also displayed for the currently hired partner. However, in that case, the
     * OnMouseClicked EventHandler is not added (addMouseListener=false).
     */
    @Override
    protected void updateItem(ProductSupport.ExternalSupportPartner externalPartner, boolean empty) {
        super.updateItem(externalPartner, empty);
        if (empty || externalPartner == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (loader == null) {
                loader = new FXMLLoader(
                        getClass().getClassLoader()
                                .getResource("fxml/components/support_partner_list_cell.fxml"),
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
            qualityIndexLabel
                    .setText("Quality: " + NumberFormat.getPercentInstance(UIManager.getResourceBundle().getLocale())
                            .format(externalPartner.getQualityIndex() / 100.0));
            contractualCostsLabel.setText(
                    "Contractual Costs: " + CapCoinFormatter.getCapCoins(externalPartner.getContractualCosts()));

            if(addMouseListener){
                gridPane.setOnMouseClicked(e -> {
                    controller.addExternalSupportPartner(externalPartner);
                    SupportPartnerController uiController = (SupportPartnerController) UIManager.getInstance()
                            .getModule(GameModuleType.LOGISTICS_SUPPORT_PARTNER_OVERVIEW).getController();
                    uiController.addExternalPartner(externalPartner);

                    supportPartnerDetailListView.getSelectionModel().clearSelection();
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