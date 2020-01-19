package de.uni.mannheim.capitalismx.ui.controller.module.warehouse;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.gamecontroller.GameState;
import de.uni.mannheim.capitalismx.procurement.component.Component;
import de.uni.mannheim.capitalismx.procurement.component.ComponentType;
import de.uni.mannheim.capitalismx.procurement.component.SupplierCategory;
import de.uni.mannheim.capitalismx.procurement.component.Unit;
import de.uni.mannheim.capitalismx.production.Product;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.controller.module.GameModuleController;
import de.uni.mannheim.capitalismx.ui.utils.AnchorPaneHelper;
import de.uni.mannheim.capitalismx.warehouse.WarehousingDepartment;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class WarehouseStatisticsController extends GameModuleController {

	@FXML
	private Label capacityLabel, costLabel;

	@FXML
	private AnchorPane fillPane, productPane, componentPane;

	private PieChart fillPie, productPie, componentPie;

	private PieChart.Data fillOccupied, fillEmpty;

	private HashMap<Product, PieChart.Data> productPieces;

	private HashMap<ComponentType, PieChart.Data> componentPieces;

	private HashMap<SupplierCategory, PieChart.Data> qualityPieces;

	@Override
	public void update() {
		WarehousingDepartment warehouse = GameState.getInstance().getWarehousingDepartment();

		// init segmented bars
		int overallCapacity = warehouse.getTotalCapacity();
		int currentUnitsStored = warehouse.getStoredUnits();
		int freeStorage = warehouse.getFreeStorage();
		int percentStorageUsed = (int) ((currentUnitsStored / (double) overallCapacity) * 100);

		fillOccupied.setPieValue(currentUnitsStored);
		fillEmpty.setPieValue(freeStorage);

		Map<Unit, Integer> inventory = warehouse.getInventory();
		for (Unit unit : inventory.keySet()) {
			if (unit instanceof Component) {
				// TODO check if actually something in inventory?
			} else if (unit instanceof Product) {
				Product product = (Product) unit;
				if (productPieces.containsKey(product)) {
					productPieces.get(product).setPieValue(inventory.get(unit));
				} else {
					productPieces.put(product, new PieChart.Data(product.getProductName(), inventory.get(unit)));
				}
			}
		}

		// init stats
		this.capacityLabel.setText(overallCapacity + "");
		this.costLabel.setText((int) warehouse.getMonthlyCostWarehousing() + "");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fillEmpty = new PieChart.Data(UIManager.getLocalisedString("warehouse.stats.empty"), 0);
		fillOccupied = new PieChart.Data(UIManager.getLocalisedString("warehouse.stats.occupied"), 0); // TODO localize
		fillPie = new PieChart(FXCollections.observableArrayList(fillEmpty, fillOccupied));
		AnchorPaneHelper.snapNodeToAnchorPane(fillPie);
		fillPane.getChildren().add(fillPie);

		productPie = new PieChart();
		AnchorPaneHelper.snapNodeToAnchorPane(productPie);
		productPane.getChildren().add(productPie);

		componentPie = new PieChart();
		AnchorPaneHelper.snapNodeToAnchorPane(componentPie);
		componentPane.getChildren().add(componentPie);

		update();
	}

}
