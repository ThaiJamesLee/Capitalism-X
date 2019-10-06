package de.uni.mannheim.capitalismx.ui.controller.overlay.hr;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.domain.employee.Employee;
import org.controlsfx.control.PopOver;

import de.uni.mannheim.capitalismx.gamelogic.GameState;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.controller.component.TrainingPopoverController;
import de.uni.mannheim.capitalismx.ui.controller.overlay.GameOverlayController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * Controller of the overlay, that displays details on a given employee.
 * 
 * @author Jonathan
 *
 */
public class EmployeeDetailController extends GameOverlayController {

	@FXML 
	public Button trainButton;
	
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
		FXMLLoader popoverLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/components/training_popover.fxml"));
 		PopOver trainPopover = new PopOver();
		try {
			trainPopover.setContentNode(popoverLoader.load());
			TrainingPopoverController popOverController = ((TrainingPopoverController)popoverLoader.getController());
			popOverController.init(trainPopover, employee);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		trainButton.setOnAction(e -> {
			trainPopover.show(trainButton, 1.0);
		});
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
	
	@FXML
	public void fireEmployee() {
		GameState.getInstance().getHrDepartment().fire(employee);
		UIManager.getInstance().getGamePageController().resetOverlay();
	}
	
}
