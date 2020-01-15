package de.uni.mannheim.capitalismx.ui.controller.module.hr;

import java.net.URL;
import java.text.NumberFormat;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.gamecontroller.GameState;
import de.uni.mannheim.capitalismx.hr.department.HRDepartment;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.controller.module.GameModuleController;
import de.uni.mannheim.capitalismx.ui.utils.CapCoinFormatter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;

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
	private Label firingCost, hiringCost, hrCapacity, hrWorkerCapacity;

	@Override
	public void update() {
		HRDepartment hrDep = GameState.getInstance().getHrDepartment();
		employeeProductivityOverall.setText(NumberFormat.getPercentInstance(UIManager.getResourceBundle().getLocale()).format(hrDep.getTotalQualityOfWork()));
		employeeSatisfactionOverall.setText(NumberFormat.getPercentInstance(UIManager.getResourceBundle().getLocale()).format(hrDep.getTotalJSS()));
		numberEmployeesOverall.setText(hrDep.getTotalNumberOfEmployees() + "");
		employeeSalariesOverall.setText(CapCoinFormatter.getCapCoins(hrDep.calculateTotalSalaries()));
		
		hiringCost.setText(CapCoinFormatter.getCapCoins(hrDep.getHiringCost()));
		firingCost.setText(CapCoinFormatter.getCapCoins(hrDep.getFiringCost()));
		hrCapacity.setText((int) hrDep.getTotalEmployeeCapacity() + "");
		hrWorkerCapacity.setText((int) hrDep.getHrCapacity() + "");
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {

		update();

		Tooltip hrCapTooltip = new Tooltip(resourceBundle.getString("hr.stats.capacity.tooltip"));
		//hrCapTooltip.setShowDelay(Duration.millis(50));
		hrCapacity.setTooltip(hrCapTooltip);

	}


}
