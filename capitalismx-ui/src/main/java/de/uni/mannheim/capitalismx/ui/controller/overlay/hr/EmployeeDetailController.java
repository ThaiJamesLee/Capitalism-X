package de.uni.mannheim.capitalismx.ui.controller.overlay.hr;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

import org.controlsfx.control.PopOver;

import de.uni.mannheim.capitalismx.hr.domain.employee.Employee;
import de.uni.mannheim.capitalismx.gamecontroller.GameState;
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
		salaryLabel.setText(((int)employee.getSalary()) + " CC");
		skillLabel.setText(employee.getSkillLevel() + "");
		positionLabel.setText(employee.getEmployeeType().toString());
		employeeNameLabel.setText(employee.getName());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

	/**
	 * Initialize the EmployeeDetail-Overlay with a different {@link Employee}. 
	 * @param employee
	 */
	public void initForDifferentEmployee(Employee employee) {
		this.employee = employee;
		
		initTrainingPopover();
	}
	
	private void initTrainingPopover() {
		FXMLLoader popoverLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/components/training_popover.fxml"), UIManager.getResourceBundle());
 		PopOver trainPopover = new PopOver();
		try {
			trainPopover.setContentNode(popoverLoader.load());
			TrainingPopoverController popOverController = ((TrainingPopoverController)popoverLoader.getController());
			popOverController.init(trainPopover, employee, this);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		trainButton.setOnAction(e -> {
			trainPopover.show(trainButton, 1.0);
		});
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
