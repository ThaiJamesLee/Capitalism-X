package de.uni.mannheim.capitalismx.ui.controller.overlay.logistics;

import de.uni.mannheim.capitalismx.gamelogic.GameController;
import de.uni.mannheim.capitalismx.logistic.logistics.ExternalPartner;
import de.uni.mannheim.capitalismx.logistic.logistics.Truck;
import de.uni.mannheim.capitalismx.ui.components.logistics.LogisticsPartnerDetailListViewCell;
import de.uni.mannheim.capitalismx.ui.components.logistics.TruckDetailListViewCell;
import de.uni.mannheim.capitalismx.ui.controller.overlay.GameOverlayController;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;

public class LogisticsPartnerDetailListController extends GameOverlayController {

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

    @Override
    public Properties getProperties() {
        return this.properties;
    }

    @Override
    public void updateProperties(Properties properties) {
        this.properties = properties;
    }

}