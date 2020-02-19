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

public class LogisticsPartnerDetailListController implements UpdateableController {

    @FXML
    ListView<ExternalPartner> logisticsPartnerDetailListView;

    @Override
    public void update() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GameController controller = GameController.getInstance();
        logisticsPartnerDetailListView.setCellFactory(truckListView -> new LogisticsPartnerDetailListViewCell(logisticsPartnerDetailListView));

        ArrayList<ExternalPartner> externalPartners = controller.generateExternalPartnerSelection();

        for(ExternalPartner externalPartner : externalPartners) {
            logisticsPartnerDetailListView.getItems().add(externalPartner);
        }

    }

}