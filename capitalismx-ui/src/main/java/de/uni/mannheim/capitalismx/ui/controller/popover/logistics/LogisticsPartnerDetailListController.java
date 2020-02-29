package de.uni.mannheim.capitalismx.ui.controller.popover.logistics;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.logistic.logistics.ExternalPartner;
import de.uni.mannheim.capitalismx.ui.component.logistics.LogisticsPartnerDetailListViewCell;
import de.uni.mannheim.capitalismx.ui.controller.general.UpdateableController;
import javafx.application.Platform;
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

        ArrayList<ExternalPartner> externalPartners = controller.generateExternalPartnerSelection();
        /*if(controller.getExternalPartnerSelection() == null){
            externalPartners = controller.generateExternalPartnerSelection();
        }else{
            externalPartners = controller.getExternalPartnerSelection();
        }*/

        for(ExternalPartner externalPartner : externalPartners) {
            logisticsPartnerDetailListView.getItems().add(externalPartner);
        }

        //Remove current external logistics partner from list of available partners (Comparison is based on the name, as the ExternalPartner object might change.)
        //this.updateAvailablePartners();

    }

    /**
     * Updates the list of available logistics partners. In particular, the currently employed partner is removed from
     * the list of available partners. Comparison is based on the name, as the ExternalPartner object might change.
     */
    public void updateAvailablePartners(){
        Platform.runLater(new Runnable() {
            public void run() {
                logisticsPartnerDetailListView.getItems().clear();
                for(ExternalPartner externalPartner : GameController.getInstance().getExternalPartnerSelection()){
                    if((GameController.getInstance().getExternalPartner() == null) || (!GameController.getInstance().getExternalPartner().getName().equals(externalPartner.getName()))){
                        logisticsPartnerDetailListView.getItems().add(externalPartner);
                    }
                }
            }
        });
    }

}