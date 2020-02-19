package de.uni.mannheim.capitalismx.ui.components.warehouse;

import java.io.IOException;

import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.gamecontroller.GameState;
import de.uni.mannheim.capitalismx.hr.department.HRDepartment;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.GameAlert;
import de.uni.mannheim.capitalismx.ui.components.GameModule;
import de.uni.mannheim.capitalismx.ui.components.GameViewType;
import de.uni.mannheim.capitalismx.ui.components.GameModuleType;
import de.uni.mannheim.capitalismx.warehouse.StorageCapacityUsedException;
import de.uni.mannheim.capitalismx.warehouse.Warehouse;
import de.uni.mannheim.capitalismx.warehouse.WarehouseType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

/**
 * {@link ListCell} for the List of {@link Warehouse}s, that contains
 * information about the Warehouse as well as a {@link Button} to sell it/cancel
 * the rent.
 * 
 * @author Joni
 *
 */
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

	public WarehouseListViewCell(ListView<Warehouse> warehouseListView) {
		this.warehouseListView = warehouseListView;
	}

	@Override
	protected void updateItem(Warehouse warehouse, boolean empty) {
		super.updateItem(warehouse, empty);
		if (empty || warehouse == null) {
			setText(null);
			setGraphic(null);
		} else {
			if (loader == null) {
				loader = new FXMLLoader(
						getClass().getClassLoader().getResource("fxml/components/warehouse_info_cell.fxml"),
						UIManager.getResourceBundle());
				loader.setController(this);

				try {
					loader.load();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			this.warehouse = warehouse;

			if (warehouse.getWarehouseType() == WarehouseType.RENTED) {
				sellButton.setText(UIManager.getLocalisedString("warehouse.rent.cancel"));
				warehouseLabel.setText(UIManager.getLocalisedString("warehouse.list.info.rent"));
			} else {
				sellButton.setText(UIManager.getLocalisedString("warehouse.buy.sell"));
				warehouseLabel.setText(UIManager.getLocalisedString("warehouse.list.info.buy"));
			}

			sellButton.setOnAction(e -> {
				sellWarehouse();
			});

			setText(null);
			setGraphic(root);
		}
	}

	/**
	 * Sells the {@link Warehouse} of this cell.
	 */
	private void sellWarehouse() {
		try {
			GameController.getInstance().sellWarehouse(warehouse);
			GameController.getInstance().increaseCash(GameState.getInstance().getGameDate(), warehouse.getResaleValue());
			this.warehouseListView.getItems().remove(warehouse);
			if (GameController.getInstance().getWarehouses().isEmpty()) {
				deactivateWarehouseModules();
			}
		} catch (StorageCapacityUsedException e) {//TODO localize
			GameAlert error = new GameAlert(Alert.AlertType.WARNING, "Storage Capacity still in Use", e.getMessage() + "\nThe warehouse provides storage capacity of " + e.getWarehouseCapacity() + " of which " + e.getFreeStorage() + " is not in use");
			error.showAndWait();
		}
	}
	
	/**
	 * Activates all the other {@link GameModule}s, that depend on the existence of
	 * a Warehouse.
	 */
	private void deactivateWarehouseModules() {
		UIManager manager = UIManager.getInstance();
		manager.getGameView(GameViewType.WAREHOUSE).getModule(GameModuleType.WAREHOUSE_STOCK_MANAGEMENT)
				.setActivated(false);
		manager.getGameView(GameViewType.WAREHOUSE).getModule(GameModuleType.WAREHOUSE_STATISTICS).setActivated(false);
		manager.getGamePageController().updateDisplayOfCurrentView(GameViewType.WAREHOUSE);
	}

}