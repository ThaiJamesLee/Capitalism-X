package de.uni.mannheim.capitalismx.ui.controller.popover.logistics;

import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.logistic.logistics.Truck;
import de.uni.mannheim.capitalismx.ui.components.logistics.TruckDetailListViewCell;
import de.uni.mannheim.capitalismx.ui.controller.popover.GameOverlayController;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;

public class TruckDetailListController extends GameOverlayController {

    @FXML
    private ListView<Truck> truckDetailListView;

    @Override
    public void update() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GameController controller = GameController.getInstance();
        truckDetailListView.setCellFactory(truckListView -> new TruckDetailListViewCell(truckListView));

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
    
    public void clearListSelection() {
    	truckDetailListView.getSelectionModel().clearSelection();
    }

}