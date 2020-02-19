package de.uni.mannheim.capitalismx.ui.controller.popover.logistics;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.logistic.logistics.Truck;
import de.uni.mannheim.capitalismx.ui.components.logistics.TruckDetailListViewCell;
import de.uni.mannheim.capitalismx.ui.controller.general.UpdateableController;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class TruckDetailListController implements UpdateableController {

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

    public void clearListSelection() {
    	truckDetailListView.getSelectionModel().clearSelection();
    }

}