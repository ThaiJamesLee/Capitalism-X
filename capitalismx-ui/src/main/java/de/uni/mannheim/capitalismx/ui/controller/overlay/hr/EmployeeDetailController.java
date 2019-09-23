package de.uni.mannheim.capitalismx.ui.controller.overlay.hr;

import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.hr.employee.Employee;
import de.uni.mannheim.capitalismx.hr.employee.EmployeeMarketSample;
import de.uni.mannheim.capitalismx.ui.controller.overlay.GameOverlayController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Controller of the overlay, that displays details on a given employee.
 * 
 * @author Jonathan
 *
 */
public class EmployeeDetailController extends GameOverlayController {

	@FXML
	public Label employeeNameLabel, salaryLabel, skillLabel, positionLabel;

	private Employee employee;

	@Override
	public void update() {
		employeeNameLabel.setText(employee.getName());
		salaryLabel.setText(employee.getSalary() + " CC");
		skillLabel.setText(employee.getSkillLevel() + "");
		positionLabel.setText(employee.getEmployeeType().toString());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	@Override
	public Properties getProperties() {
		return this.properties;
	}

	@Override
	public void updateProperties(Properties properties) {
		this.properties = properties;
	}

}
