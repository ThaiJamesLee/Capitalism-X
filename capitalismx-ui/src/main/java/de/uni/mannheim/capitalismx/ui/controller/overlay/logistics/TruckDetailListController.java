package de.uni.mannheim.capitalismx.ui.controller.overlay.logistics;

import de.uni.mannheim.capitalismx.gamelogic.GameController;
import de.uni.mannheim.capitalismx.gamelogic.GameState;
import de.uni.mannheim.capitalismx.logistic.logistics.Truck;
import de.uni.mannheim.capitalismx.ui.components.logistics.TruckDetailListViewCell;
import de.uni.mannheim.capitalismx.ui.components.logistics.TruckListViewCell;
import de.uni.mannheim.capitalismx.ui.controller.overlay.GameOverlayController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;

public class TruckDetailListController extends GameOverlayController {

    @FXML
    ListView<Truck> truckDetailListView;

    @Override
    public void update() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GameController controller = GameController.getInstance();
        truckDetailListView.setCellFactory(truckListView -> new TruckDetailListViewCell(truckDetailListView));

        ArrayList<Truck> trucks = controller.generateTruckSelection();

        for(Truck truck : trucks) {
            //controller.addTruckToFleet(truck, GameState.getInstance().getGameDate());
            truckDetailListView.getItems().add(truck);
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