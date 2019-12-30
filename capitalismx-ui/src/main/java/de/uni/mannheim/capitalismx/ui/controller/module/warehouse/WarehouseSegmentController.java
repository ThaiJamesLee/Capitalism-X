package de.uni.mannheim.capitalismx.ui.controller.module.warehouse;

import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.SegmentedBar;

import de.uni.mannheim.capitalismx.gamecontroller.GameState;
import de.uni.mannheim.capitalismx.ui.components.warehouse.WarehouseSegment;
import de.uni.mannheim.capitalismx.ui.controller.module.GameModuleController;
import de.uni.mannheim.capitalismx.warehouse.WarehousingDepartment;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class WarehouseSegmentController extends GameModuleController {

	@FXML
	private Label capacity, inUse, cost;

	@FXML
	private SegmentedBar<WarehouseSegment> overallBar, productBar, componentBar, qualityBar;

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		WarehousingDepartment warehouse = GameState.getInstance().getWarehousingDepartment();
		// init segmented bars
		int overallCapacity = warehouse.getTotalCapacity();
		int currentUnitsStored = warehouse.getStoredUnits();
		int freeStorage = warehouse.getFreeStorage();
		int percentStorageUsed = (int) ((currentUnitsStored / (double) overallCapacity) * 100);
		overallBar.getSegments().add(new WarehouseSegment(percentStorageUsed, "occupied"));
		overallBar.getSegments()
				.add(new WarehouseSegment((int) ((freeStorage / (double) overallCapacity) * 100), "free"));

		// init stats
		this.capacity.setText(overallCapacity + "");
		this.inUse.setText(currentUnitsStored + "");
		this.cost.setText((int) warehouse.calculateTotalMonthlyWarehousingCost() + "");

	}

}
