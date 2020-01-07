package de.uni.mannheim.capitalismx.ui.controller.module.hr;

import java.net.URL;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.domain.employee.Employee;
import de.uni.mannheim.capitalismx.domain.employee.EmployeeType;
import de.uni.mannheim.capitalismx.domain.employee.Team;
import de.uni.mannheim.capitalismx.gamecontroller.GameState;
import de.uni.mannheim.capitalismx.hr.department.HRDepartment;
import de.uni.mannheim.capitalismx.ui.controller.module.GameModuleController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
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
	private Label numberEmployeesSales, employeeSatisfactionSales, employeeProductivitySales, employeeSalariesSales;

	@FXML
	private Label numberEmployeesProduction, employeeSatisfactionProduction, employeeProductivityProduction,
			employeeSalariesProduction;

	@FXML
	private Label numberEmployeesHr, employeeSatisfactionHr, employeeProductivityHr, employeeSalariesHr;

	@FXML
	private Label numberEmployeesOverall, employeeSatisfactionOverall, employeeProductivityOverall,
			employeeSalariesOverall;

	@FXML
	private Label firingCost, hiringCost, hrCapacity;

	@Override
	public void update() {

	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		HRDepartment hrDep = GameState.getInstance().getHrDepartment();
		employeeProductivityOverall.setText(hrDep.getTotalQualityOfWork() + "");
		employeeSatisfactionOverall.setText(hrDep.getTotalJSS() + "");

		int numOfSalesPeople = hrDep.getSalesTeam().getTeam().size();
		int numOfEngineers = hrDep.getEngineerTeam().getTeam().size();
		numberEmployeesSales.setText(numOfSalesPeople + "");
		numberEmployeesProduction.setText(numOfEngineers + "");
		numberEmployeesOverall.setText((numOfEngineers + numOfSalesPeople) + "");

		hiringCost.setText((int) hrDep.getHiringCost() + "");
		firingCost.setText((int) hrDep.getFiringCost() + "");
		hrCapacity.setText((int) hrDep.getTotalEmployeeCapacity() + "");

		Tooltip hrCapTooltip = new Tooltip(resourceBundle.getString("hr.stats.capacity.tooltip"));
		//hrCapTooltip.setShowDelay(Duration.millis(50));
		hrCapacity.setTooltip(hrCapTooltip);

	}

	/**
	 * Update the stats of the given team, that changed.
	 * 
	 * @param typeOfTeam          The {@link EmployeeType} of the updated
	 *                            {@link Team}.
	 * @param numberOfTeamMembers The number of {@link Employee}s in the
	 *                            {@link Team}.
	 */
	public void updateTeam(EmployeeType typeOfTeam, int numberOfTeamMembers) {
		Platform.runLater(() -> {
			updateNumberOfEmployees();
			switch (typeOfTeam) {
			case ENGINEER:
				updateNumberOfEngineers(numberOfTeamMembers);
				break;
			case SALESPERSON:
				updateNumberOfSalesPeople(numberOfTeamMembers);
				break;
			default:
				break;
			}
		});
	}

	private void updateNumberOfSalesPeople(int numberOfSalesPeople) {
		numberEmployeesSales.setText(numberOfSalesPeople + "");
	}

	private void updateNumberOfEngineers(int numberOfEngineers) {
		numberEmployeesProduction.setText(numberOfEngineers + "");
	}

	private void updateNumberOfEmployees() {
		numberEmployeesOverall.setText(GameState.getInstance().getHrDepartment().getTotalNumberOfEmployees() + "");
	}

}
