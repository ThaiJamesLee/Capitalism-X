package de.uni.mannheim.capitalismx.ui.controller.module.hr;

import java.net.URL;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.gamecontroller.GameState;
import de.uni.mannheim.capitalismx.hr.department.HRDepartment;
import de.uni.mannheim.capitalismx.ui.controller.module.GameModuleController;
import de.uni.mannheim.capitalismx.ui.utils.CapCoinFormatter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

/**
 * Controller for the HRStatistics-Module, that displays stats about employees,
 * their satisfaction and quality of work.
 * 
 * @author Jonathan
 *
 */
public class HrStatisticsController extends GameModuleController {

	@FXML
	private GridPane statGrid;

	@FXML
	private Label numberEmployeesOverall, employeeSatisfactionOverall, employeeProductivityOverall,
			employeeSalariesOverall;

	@FXML
	private Label firingCost, hiringCost, hrCapacity;

	@Override
	public void update() {
		HRDepartment hrDep = GameState.getInstance().getHrDepartment();
		employeeProductivityOverall.setText(hrDep.getTotalQualityOfWork() + "");
		employeeSatisfactionOverall.setText(hrDep.getTotalJSS() + "");
		numberEmployeesOverall.setText(hrDep.getTotalNumberOfEmployees() + "");
		employeeSalariesOverall.setText(CapCoinFormatter.getCapCoins(hrDep.calculateTotalSalaries()));
		
		hiringCost.setText((int) hrDep.getHiringCost() + "");
		firingCost.setText((int) hrDep.getFiringCost() + "");
		hrCapacity.setText((int) hrDep.getTotalEmployeeCapacity() + "");
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {

		update();

		Tooltip hrCapTooltip = new Tooltip(resourceBundle.getString("hr.stats.capacity.tooltip"));
		//hrCapTooltip.setShowDelay(Duration.millis(50));
		hrCapacity.setTooltip(hrCapTooltip);

	}


}
