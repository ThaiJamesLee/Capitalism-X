package de.uni.mannheim.capitalismx.ui.controller.popover.logistics;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.logistic.logistics.ExternalPartner;
import de.uni.mannheim.capitalismx.logistic.support.ProductSupport;
import de.uni.mannheim.capitalismx.ui.components.logistics.LogisticsPartnerDetailListViewCell;
import de.uni.mannheim.capitalismx.ui.components.logistics.SupportPartnerListViewCell;
import de.uni.mannheim.capitalismx.ui.controller.general.UpdateableController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

/**
 * This class represents the selection (list) of external support partners in the logistics UI. Each available partner
 * is displayed with the corresponding characteristics.
 *
 * @author sdupper
 */
public class SupportPartnerDetailListController implements UpdateableController {

    @FXML
    ListView<ProductSupport.ExternalSupportPartner> supportPartnerDetailListView;

    @Override
    public void update() {
    }

    /*
     * Generates an entry in the supportPartnerDetailListView for each available external support partner.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GameController controller = GameController.getInstance();
        supportPartnerDetailListView.setCellFactory(truckListView -> new SupportPartnerListViewCell(supportPartnerDetailListView));

        ArrayList<ProductSupport.ExternalSupportPartner> externalPartners = controller.generateExternalSupportPartnerSelection();

        for(ProductSupport.ExternalSupportPartner externalPartner : externalPartners) {
            supportPartnerDetailListView.getItems().add(externalPartner);
        }

    }

    /**
     * Updates the list of available support partners. In particular, the currently employed partner is removed from
     * the list of available partners.
     */
    public void updateAvailablePartners(){
        Platform.runLater(new Runnable() {
            public void run() {
                supportPartnerDetailListView.getItems().clear();
                for(ProductSupport.ExternalSupportPartner externalSupportPartner : GameController.getInstance().generateExternalSupportPartnerSelection()){
                    supportPartnerDetailListView.getItems().add(externalSupportPartner);
                }
                ProductSupport.ExternalSupportPartner externalSupportPartner = GameController.getInstance().getExternalSupportPartner();
                if(externalSupportPartner != null){
                    supportPartnerDetailListView.getItems().remove(externalSupportPartner);
                }
            }
        });
    }

}