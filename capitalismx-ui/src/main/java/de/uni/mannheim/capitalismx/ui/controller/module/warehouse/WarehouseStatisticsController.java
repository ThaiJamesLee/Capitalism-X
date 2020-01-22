package de.uni.mannheim.capitalismx.ui.controller.module.warehouse;

import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.gamecontroller.GameState;
import de.uni.mannheim.capitalismx.procurement.component.Component;
import de.uni.mannheim.capitalismx.procurement.component.ComponentType;
import de.uni.mannheim.capitalismx.procurement.component.SupplierCategory;
import de.uni.mannheim.capitalismx.procurement.component.Unit;
import de.uni.mannheim.capitalismx.production.Product;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.general.CapXPieChart;
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
	private AnchorPane fillPane, productPane;

	private CapXPieChart fillPie, productPie;

	private PieChart.Data fillOccupied, fillEmpty;

	private HashMap<Product, PieChart.Data> productPieces;

	@Override
	public void update() {
		WarehousingDepartment warehouse = GameState.getInstance().getWarehousingDepartment();

		// init segmented bars
		int overallCapacity = warehouse.getTotalCapacity();
		int currentUnitsStored = warehouse.getStoredUnits();
		int freeStorage = warehouse.getFreeStorage();

		fillOccupied.setPieValue(currentUnitsStored);
		fillEmpty.setPieValue(freeStorage);

		Map<Unit, Integer> inventory = warehouse.getInventory();
		for (Unit unit : inventory.keySet()) {
			if (unit instanceof Product) {
				Product product = (Product) unit;
				if (productPieces.containsKey(product)) {
					productPieces.get(product).setPieValue(inventory.get(unit));
				} else {
					PieChart.Data data = new PieChart.Data(product.getProductName(), inventory.get(unit));
					productPieces.put(product, data);
					productPie.addData(data);
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
		fillOccupied = new PieChart.Data(UIManager.getLocalisedString("warehouse.stats.occupied"), 0);

		fillPie = new CapXPieChart(FXCollections.observableArrayList(fillEmpty, fillOccupied),
				UIManager.getLocalisedString("warehouse.stats.empty.pie"));
		AnchorPaneHelper.snapNodeToAnchorPane(fillPie);
		fillPane.getChildren().add(fillPie);

		productPie = new CapXPieChart(UIManager.getLocalisedString("warehouse.stats.empty.pie"));
		AnchorPaneHelper.snapNodeToAnchorPane(productPie);
		productPane.getChildren().add(productPie);

		update();
	}

}
