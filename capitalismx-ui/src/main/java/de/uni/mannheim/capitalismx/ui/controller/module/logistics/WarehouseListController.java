package de.uni.mannheim.capitalismx.ui.controller.module.logistics;

import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.ui.components.logistics.WarehouseListViewCell;
import de.uni.mannheim.capitalismx.ui.controller.module.GameModuleController;
import de.uni.mannheim.capitalismx.warehouse.Warehouse;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class WarehouseListController extends GameModuleController {

    @FXML
    ListView<Warehouse> warehouseListView;

    @FXML
    private Button buyButton;

    public WarehouseListController() {
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GameController controller = GameController.getInstance();

        buyButton.setOnAction(e -> {
            double costs = controller.buildWarehouse();
            controller.decreaseCash(costs);
            List<Warehouse> warehouses = controller.getWarehouses();
            warehouseListView.getItems().add(warehouses.get(warehouses.size() - 1));
        });

        warehouseListView.setCellFactory(warehouseListView -> new WarehouseListViewCell(warehouseListView));
    }

}