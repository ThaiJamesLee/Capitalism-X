package de.uni.mannheim.capitalismx.ui.controller.overlay.hr;

import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.hr.department.HRDepartment;
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
	public Label employeeNameLabel;

	@Override
	public void update() {
		String employeeId = this.getProperties().getProperty("employeeId", "no employee selected");
		//TODO getEmployee(ID)
		employeeNameLabel.setText("Employee number: " + employeeId);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

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
