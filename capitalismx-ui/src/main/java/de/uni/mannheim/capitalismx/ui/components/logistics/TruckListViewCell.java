package de.uni.mannheim.capitalismx.ui.components.logistics;

import de.uni.mannheim.capitalismx.gamelogic.GameController;
import de.uni.mannheim.capitalismx.gamelogic.GameState;
import de.uni.mannheim.capitalismx.logistic.logistics.Truck;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class TruckListViewCell extends ListCell<Truck> {

    @FXML
    private Label indexLabel;

    @FXML
    private Label valueLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private GridPane gridPane;

    @FXML
    private Button sellButton;

    private FXMLLoader loader;

    private ListView<Truck> truckFleetListView;

    public TruckListViewCell(ListView<Truck> truckFleetListView){
        this.truckFleetListView = truckFleetListView;
    }

    @Override
    protected void updateItem(Truck truck, boolean empty) {
        super.updateItem(truck, empty);
        if(empty || truck == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (loader == null) {
                loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/components/truck_list_cell.fxml"));
                loader.setController(this);

                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            GameController controller = GameController.getInstance();
            indexLabel.setText(controller.getInternalFleet().getTrucks().indexOf(truck) + "");
            valueLabel.setText(controller.calculateResellPrice(truck.getPurchasePrice(), truck.getUsefulLife(), truck.calculateTimeUsed(GameState.getInstance().getGameDate())) + " CC");
            dateLabel.setText(truck.getPurchaseDate() + "");
            sellButton.setOnAction(e -> {
                controller.sellTruck(truck);
                this.truckFleetListView.getItems().remove(truck);
            });

            setText(null);
            setGraphic(gridPane);
        }
    }

}