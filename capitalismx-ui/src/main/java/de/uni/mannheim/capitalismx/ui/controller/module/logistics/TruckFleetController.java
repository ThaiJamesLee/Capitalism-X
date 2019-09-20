package de.uni.mannheim.capitalismx.ui.controller.module.logistics;

import de.uni.mannheim.capitalismx.gamelogic.GameController;
import de.uni.mannheim.capitalismx.gamelogic.GameState;
import de.uni.mannheim.capitalismx.logistic.logistics.Truck;
import de.uni.mannheim.capitalismx.ui.components.logistics.TruckListViewCell;
import de.uni.mannheim.capitalismx.ui.controller.module.GameModuleController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TruckFleetController  extends GameModuleController {

    @FXML
    ListView<Truck> truckFleetListView;

    @FXML
    private MenuButton truckSelectionDropdown;

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


        MenuItem[] truckMenuItems = new MenuItem[6];

        int i = 0;
        for(Truck truck : trucks) {
            truckMenuItems[i] = new MenuItem(truck.getName() + " Purchase Price: " + truck.getPurchasePrice());
            truckMenuItems[i].setOnAction(e -> {
                controller.addTruckToFleet(truck, GameState.getInstance().getGameDate());
                truckFleetListView.getItems().add(truck);
                //selectMenuItem((MenuItem) e.getSource());
            });
            //data.add("Partner " + i + " Contractual Costs: " + externalPartner.getContractualCost());
            i++;
        }

        truckSelectionDropdown.getItems().addAll(truckMenuItems);


        //truckFleetListView.setItems(truckFleetListObservable);
        truckFleetListView.setCellFactory(truckListView -> new TruckListViewCell(truckFleetListView));
    }

}