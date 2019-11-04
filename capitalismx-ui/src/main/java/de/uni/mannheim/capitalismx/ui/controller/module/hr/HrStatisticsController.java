package de.uni.mannheim.capitalismx.ui.controller.module.hr;

import java.net.URL;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.domain.employee.EmployeeType;
import de.uni.mannheim.capitalismx.gamelogic.GameState;
import de.uni.mannheim.capitalismx.hr.department.HRDepartment;
import de.uni.mannheim.capitalismx.ui.controller.module.GameModuleController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

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
	private Label numberEmployeesOverall, employeeSatisfactionOverall, employeeProductivityOverall,
			employeeSalariesOverall;

	@FXML
	private Label firingCost, hiringCost;

	@Override
	public void update() {

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
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
	}

	public void updateTeam(EmployeeType typeOfTeam, int numberOfTeamMembers) {
		Platform.runLater(() -> {
			switch (typeOfTeam) {
			case ENGINEER:
				updateNumberOfEngineers(numberOfTeamMembers);
				break;
			case SALESPERSON:
				updateNumberOfEmployees(numberOfTeamMembers);
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

	private void updateNumberOfEmployees(int numOfEmployees) {
		numberEmployeesOverall.setText(numOfEmployees + "");
	}

}
