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

	private WarehouseSegment occupied, free;

	@Override
	public void update() {
		WarehousingDepartment warehouse = GameState.getInstance().getWarehousingDepartment();

		// init segmented bars
		int overallCapacity = warehouse.getTotalCapacity();
		int currentUnitsStored = warehouse.getStoredUnits();
		int freeStorage = warehouse.getFreeStorage();
		int percentStorageUsed = (int) ((currentUnitsStored / (double) overallCapacity) * 100);
		overallBar.getSegments().clear();

		occupied.setValue(percentStorageUsed);
		free.setValue((int) ((freeStorage / (double) overallCapacity) * 100));
		// init stats
		this.capacity.setText(overallCapacity + "");
		this.inUse.setText(currentUnitsStored + "");
		this.cost.setText((int) warehouse.getMonthlyCostWarehousing() + "");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		occupied = new WarehouseSegment(0.0, "occupied");
		free = new WarehouseSegment(100.0, "free");
		overallBar.getSegments().add(occupied);
		overallBar.getSegments().add(free);

		update();
	}

}
