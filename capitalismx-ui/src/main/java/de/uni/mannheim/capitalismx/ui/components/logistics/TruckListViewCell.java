package de.uni.mannheim.capitalismx.ui.components.logistics;

import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.gamecontroller.GameState;
import de.uni.mannheim.capitalismx.logistic.logistics.Truck;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.utils.CapCoinFormatter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

import java.io.IOException;

/**
 * This class represents an entry in the list of trucks in the internal logistics fleet. It displays information like
 * the value of a specific truck the company owns.
 *
 * @author sdupper
 */
public class TruckListViewCell extends ListCell<Truck> {

    @FXML
    private Label valueLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private GridPane gridPane;

    @FXML
    private Button sellButton;

    private FXMLLoader loader;

    /**
     * The ListView of all trucks in the internal logistics fleet.
     */
    private ListView<Truck> truckFleetListView;

    /**
     * Constructor
     * @param truckFleetListView The ListView of all trucks in the internal logistics fleet.
     */
    public TruckListViewCell(ListView<Truck> truckFleetListView){
        this.truckFleetListView = truckFleetListView;
    }

    /*
     * Generates an entry in the list of trucks in the internal fleet for each new truck added to the truckFleetListView
     * according to the truck's characteristics. If the user clicks on the sell button of a truck, the truck is sold and
     * removed from the internal fleet.
     */
    @Override
    protected void updateItem(Truck truck, boolean empty) {
        super.updateItem(truck, empty);
        if(empty || truck == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (loader == null) {
                loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/components/truck_list_cell.fxml"), UIManager.getResourceBundle());
                loader.setController(this);

                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            GameController controller = GameController.getInstance();
            valueLabel.setText(CapCoinFormatter.getCapCoins(controller.calculateResellPrice(truck.getPurchasePrice(), truck.getUsefulLife(), truck.calculateTimeUsed(GameState.getInstance().getGameDate()))));
            dateLabel.setText(truck.getPurchaseDate() + "");
            sellButton.setOnAction(e -> {
                controller.sellTruck(truck, GameState.getInstance().getGameDate());
                this.truckFleetListView.getItems().remove(truck);
            });

            setText(null);
            setGraphic(gridPane);
        }
    }

}