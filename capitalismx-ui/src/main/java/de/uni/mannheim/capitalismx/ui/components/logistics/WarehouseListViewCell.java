package de.uni.mannheim.capitalismx.ui.components.logistics;

import de.uni.mannheim.capitalismx.gamecontroller.GameController;

import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.warehouse.Warehouse;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class WarehouseListViewCell extends ListCell<Warehouse> {

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

    private ListView<Warehouse> warehouseListView;

    public WarehouseListViewCell(ListView<Warehouse> warehouseListView){
        this.warehouseListView = warehouseListView;
    }

    @Override
    protected void updateItem(Warehouse warehouse, boolean empty) {
        super.updateItem(warehouse, empty);
        if(empty || warehouse == null) {
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
            indexLabel.setText(controller.getWarehouses().indexOf(warehouse) + "");
            valueLabel.setText(warehouse.getResaleValue() + " CC");
            dateLabel.setText(warehouse.getBuildDate() + "");
            sellButton.setOnAction(e -> {
                controller.sellWarehouse(warehouse);
                this.warehouseListView.getItems().remove(warehouse);
            });

            setText(null);
            setGraphic(gridPane);
        }
    }

}