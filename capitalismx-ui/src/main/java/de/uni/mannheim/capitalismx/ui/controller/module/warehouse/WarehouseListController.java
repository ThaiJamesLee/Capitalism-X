package de.uni.mannheim.capitalismx.ui.controller.module.warehouse;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.gamecontroller.GameState;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.logistics.WarehouseListViewCell;
import de.uni.mannheim.capitalismx.ui.controller.module.GameModuleController;
import de.uni.mannheim.capitalismx.ui.utils.CapCoinFormatter;
import de.uni.mannheim.capitalismx.warehouse.Warehouse;
import de.uni.mannheim.capitalismx.warehouse.WarehouseType;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

public class WarehouseListController extends GameModuleController {

	@FXML
	ListView<Warehouse> warehouseListView;

	@FXML
	private Label buyCostLabel, rentCostLabel, capacityLabel, fixCostLabel;

	@FXML
	private GridPane buyGridButton, rentGridButton;

	public WarehouseListController() {
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO buy rented warehouse?
		GameController controller = GameController.getInstance();
		Warehouse standardWarehouse = new Warehouse(WarehouseType.BUILT);

		fixCostLabel.setText(UIManager.getLocalisedString("warehouse.list.fixcost").replace("XXX",
				CapCoinFormatter.getCapCoins(standardWarehouse.getMonthlyFixCostWarehouse())));
		capacityLabel.setText(UIManager.getLocalisedString("warehouse.list.capacity").replace("XXX",
				standardWarehouse.getCapacity() + ""));
		buyCostLabel.setText(UIManager.getLocalisedString("warehouse.buy.cost").replace("XXX",
				CapCoinFormatter.getCapCoins(new Warehouse(WarehouseType.BUILT).getBuildingCost())));
		rentCostLabel.setText(UIManager.getLocalisedString("warehouse.rent.cost").replace("XXX",
				CapCoinFormatter.getCapCoins(new Warehouse(WarehouseType.RENTED).getMonthlyRentalCost())));
		warehouseListView.setPlaceholder(new Label(UIManager.getLocalisedString("warehouse.list.placeholder")));

		buyGridButton.setOnMouseClicked(e -> {
			double costs = controller.buildWarehouse();
			controller.decreaseCash(costs);
			List<Warehouse> warehouses = controller.getWarehouses();
			warehouseListView.getItems().add(warehouses.get(warehouses.size() - 1));
		});

		rentGridButton.setOnMouseClicked(e -> {
			controller.rentWarehouse();
			List<Warehouse> warehouses = controller.getWarehouses();
			warehouseListView.getItems().add(warehouses.get(warehouses.size() - 1));
		});

		warehouseListView.setCellFactory(warehouseListView -> new WarehouseListViewCell(warehouseListView));
		warehouseListView.getItems().addAll(GameState.getInstance().getWarehousingDepartment().getWarehouses());
	}

}