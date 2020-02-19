package de.uni.mannheim.capitalismx.ui.controller.module.warehouse;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.gamecontroller.GameState;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.GameModule;
import de.uni.mannheim.capitalismx.ui.components.GameViewType;
import de.uni.mannheim.capitalismx.ui.components.UIElementType;
import de.uni.mannheim.capitalismx.ui.components.warehouse.WarehouseListViewCell;
import de.uni.mannheim.capitalismx.ui.controller.module.GameModuleController;
import de.uni.mannheim.capitalismx.ui.utils.CapCoinFormatter;
import de.uni.mannheim.capitalismx.warehouse.NoWarehouseSlotsAvailableException;
import de.uni.mannheim.capitalismx.warehouse.Warehouse;
import de.uni.mannheim.capitalismx.warehouse.WarehouseType;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

/**
 * Controller for the GameModule managing the list of warehouses.
 * 
 * @author Jonathan
 */
public class WarehouseListController extends GameModuleController {

	@FXML
	ListView<Warehouse> warehouseListView;

	@FXML
	private Label buyCostLabel, rentCostLabel, capacityLabel, costLabel;

	@FXML
	private GridPane buyGridButton, rentGridButton;

	public WarehouseListController() {
	}

	/**
	 * Activates all the other {@link GameModule}s, that depend on the existence of
	 * a Warehouse.
	 */
	public void activateWarehouseModules() {
		UIManager manager = UIManager.getInstance();
		manager.getGameView(GameViewType.WAREHOUSE).getModule(UIElementType.WAREHOUSE_STOCK_MANAGEMENT)
				.setActivated(true);
		manager.getGameView(GameViewType.WAREHOUSE).getModule(UIElementType.WAREHOUSE_STATISTICS).setActivated(true);
		manager.getGamePageController().updateDisplayOfCurrentView(GameViewType.WAREHOUSE);
	}

	/**
	 * Adds the latest {@link Warehouse} to the {@link ListView}.
	 */
	private void addLatestWarehouseToList() {
		List<Warehouse> warehouses = GameController.getInstance().getWarehouses();
		warehouseListView.getItems().add(warehouses.get(warehouses.size() - 1));
	}

	/**
	 * Initiates the purchase of a new {@link Warehouse}.
	 */
	private void buyWarehouse(LocalDate gameDate) {
		GameController controller = GameController.getInstance();

		boolean firstWarehouse = controller.getWarehouses().isEmpty();
		try {
			double costs = controller.buildWarehouse();
			controller.decreaseCash(gameDate, costs);
			addLatestWarehouseToList();
			if (firstWarehouse)
				activateWarehouseModules();
		} catch (NoWarehouseSlotsAvailableException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Warehouse standardWarehouse = new Warehouse(WarehouseType.BUILT);

		costLabel.setText(CapCoinFormatter.getCapCoins(standardWarehouse.getMonthlyFixCostWarehouse()));
		capacityLabel.setText(standardWarehouse.getCapacity() + "");
		buyCostLabel.setText(UIManager.getLocalisedString("warehouse.buy.cost").replace("XXX",
				CapCoinFormatter.getCapCoins(new Warehouse(WarehouseType.BUILT).getBuildingCost())));
		rentCostLabel.setText(UIManager.getLocalisedString("warehouse.rent.cost").replace("XXX",
				CapCoinFormatter.getCapCoins(new Warehouse(WarehouseType.RENTED).getMonthlyRentalCost())));
		warehouseListView.setPlaceholder(new Label(UIManager.getLocalisedString("list.placeholder.warehouse")));

		buyGridButton.setOnMouseClicked(e -> {
			buyWarehouse(GameState.getInstance().getGameDate());
		});

		rentGridButton.setOnMouseClicked(e -> {
			rentWarehouse();
		});

		warehouseListView.setCellFactory(warehouseListView -> new WarehouseListViewCell(warehouseListView));
		warehouseListView.getItems().addAll(GameState.getInstance().getWarehousingDepartment().getWarehouses());
	}

	/**
	 * Initiates the rental of a new {@link Warehouse}.
	 */
	private void rentWarehouse() {
		GameController controller = GameController.getInstance();

		boolean firstWarehouse = controller.getWarehouses().isEmpty();
		try {
			controller.rentWarehouse();
			addLatestWarehouseToList();
			if (firstWarehouse)
				activateWarehouseModules();
		} catch (NoWarehouseSlotsAvailableException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
	}

}