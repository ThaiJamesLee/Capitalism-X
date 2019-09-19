package de.uni.mannheim.capitalismx.ui.controller.module.logistics;

import de.uni.mannheim.capitalismx.gamelogic.GameController;
import de.uni.mannheim.capitalismx.logistic.logistics.Truck;
import de.uni.mannheim.capitalismx.ui.components.logistics.TruckListViewCell;
import de.uni.mannheim.capitalismx.ui.controller.module.GameModuleController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TruckFleetController  extends GameModuleController {

    @FXML
    ListView<Truck> truckFleetListView;

    public TruckFleetController() {
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GameController controller = GameController.getInstance();
        ObservableList<Truck> truckFleetListObservable = FXCollections.observableArrayList();
        ArrayList<Truck> trucks = controller.generateTruckSelection();
        for(Truck truck : trucks){
            truckFleetListObservable.add(truck);
        }

        truckFleetListView.setItems(truckFleetListObservable);
        truckFleetListView.setCellFactory(employeeListView -> new TruckListViewCell());
    }

}