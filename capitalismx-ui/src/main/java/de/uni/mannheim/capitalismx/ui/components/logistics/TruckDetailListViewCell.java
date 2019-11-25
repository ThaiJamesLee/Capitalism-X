package de.uni.mannheim.capitalismx.ui.components.logistics;

import de.uni.mannheim.capitalismx.gamelogic.GameController;
import de.uni.mannheim.capitalismx.gamelogic.GameState;
import de.uni.mannheim.capitalismx.logistic.logistics.Truck;
import de.uni.mannheim.capitalismx.ui.application.CapXApplication;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.GameViewType;
import de.uni.mannheim.capitalismx.ui.components.UIElementType;
import de.uni.mannheim.capitalismx.ui.controller.module.logistics.TruckFleetController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class TruckDetailListViewCell extends ListCell<Truck> {

    @FXML
    private Label nameLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private Label ecoIndexLabel;

    @FXML
    private Label qualityIndexLabel;

    @FXML
    GridPane gridPane;

    @FXML
    private Label fixCostsLabel;

    @FXML
    private Button buyButton;

    private FXMLLoader loader;

    private ListView<Truck> truckDetailListView;

    public TruckDetailListViewCell(ListView<Truck> truckDetailListView){
        this.truckDetailListView = truckDetailListView;
    }

    @Override
    protected void updateItem(Truck truck, boolean empty) {
        super.updateItem(truck, empty);
        if(empty || truck == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (loader == null) {
                loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/components/truck_detail_list_cell.fxml"), UIManager.getResourceBundle());
                loader.setController(this);

                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            GameController controller = GameController.getInstance();
            nameLabel.setText("Name: " + truck.getName());
            priceLabel.setText("Price: " + truck.getPurchasePrice() + " CC");
            ecoIndexLabel.setText("Eco Index: " + truck.getEcoIndex());
            qualityIndexLabel.setText("Quality Index: " + truck.getQualityIndex());
            fixCostsLabel.setText("Fix Costs Delivery: " + truck.getFixCostsDelivery());
            buyButton.setOnAction(e -> {
                controller.addTruckToFleet(truck, GameState.getInstance().getGameDate());
                TruckFleetController uiController = (TruckFleetController) UIManager.getInstance().getGameView(GameViewType.LOGISTIC).getModule(UIElementType.LOGISTICS_TRUCK_FLEET_OVERVIEW).getController();
                uiController.addTruck(truck);
            });

            setText(null);
            setGraphic(gridPane);
        }
    }

}