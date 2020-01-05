package de.uni.mannheim.capitalismx.ui.components.logistics;

import java.io.IOException;

import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.warehouse.Warehouse;
import de.uni.mannheim.capitalismx.warehouse.WarehouseType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

public class WarehouseListViewCell extends ListCell<Warehouse> {

	private Warehouse warehouse;
	
    @FXML
    private AnchorPane root;

    @FXML
    private Button sellButton;
    
    @FXML
    private Label warehouseLabel;

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
                loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/components/warehouse_info_cell.fxml"), UIManager.getResourceBundle());
                loader.setController(this);

                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            this.warehouse = warehouse;

            if(warehouse.getWarehouseType() == WarehouseType.RENTED) {
            	sellButton.setText(UIManager.getLocalisedString("warehouse.rent.cancel"));
            	warehouseLabel.setText(UIManager.getLocalisedString("warehouse.list.info.rent"));
            } else {
            	warehouseLabel.setText(UIManager.getLocalisedString("warehouse.list.info.buy"));
            }
            GameController controller = GameController.getInstance();
            
            //TODO what happens when you sell a full Warehouse?
            sellButton.setOnAction(e -> {
                controller.sellWarehouse(warehouse);
                this.warehouseListView.getItems().remove(warehouse);
            });

            setText(null);
            setGraphic(root);
        }
    }

}