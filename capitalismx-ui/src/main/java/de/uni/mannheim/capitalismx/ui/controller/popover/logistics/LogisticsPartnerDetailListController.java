package de.uni.mannheim.capitalismx.ui.controller.popover.logistics;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.logistic.logistics.ExternalPartner;
import de.uni.mannheim.capitalismx.ui.components.logistics.LogisticsPartnerDetailListViewCell;
import de.uni.mannheim.capitalismx.ui.controller.general.UpdateableController;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

/**
 * This class represents the selection (list) of external logistics partners in the logistics UI. Each available partner
 * is displayed with the corresponding characteristics.
 *
 * @author sdupper
 */
public class LogisticsPartnerDetailListController implements UpdateableController {

    @FXML
    ListView<ExternalPartner> logisticsPartnerDetailListView;

    @Override
    public void update() {
    }

    /*
     * Generates an entry in the logisticsPartnerDetailListView for each available external logistics partner.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GameController controller = GameController.getInstance();
        logisticsPartnerDetailListView.setCellFactory(truckListView -> new LogisticsPartnerDetailListViewCell(logisticsPartnerDetailListView));

        ArrayList<ExternalPartner> externalPartners;
        if(controller.getExternalPartnerSelection() == null){
            externalPartners = controller.generateExternalPartnerSelection();
        }else{
            externalPartners = controller.getExternalPartnerSelection();
        }

        for(ExternalPartner externalPartner : externalPartners) {
            logisticsPartnerDetailListView.getItems().add(externalPartner);
        }

    }

}