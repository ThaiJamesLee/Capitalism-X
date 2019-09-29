package de.uni.mannheim.capitalismx.ui.controller.component;

import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.PopOver;

import de.uni.mannheim.capitalismx.gamelogic.GameState;
import de.uni.mannheim.capitalismx.hr.domain.Training;
import de.uni.mannheim.capitalismx.hr.employee.Employee;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class TrainingPopoverController implements Initializable {

	@FXML
	private AnchorPane trainingPopoverRoot;

	@FXML
	private GridPane workshopGrid, courseGrid;

	private Employee employee;

	private PopOver popover;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		workshopGrid.setOnMouseClicked(e -> {
			trainEmployeeWorkshop();
		});
		courseGrid.setOnMouseClicked(e -> {
			trainEmployeeCourse();
		});
	}

	public void init(PopOver popover, Employee employee) {
		this.popover = popover;
		this.employee = employee;
	}

	@FXML
	public void trainEmployeeWorkshop() {
		GameState.getInstance().getHrDepartment().trainEmployee(employee, Training.WORKSHOP);
		popover.hide();
	}

	@FXML
	public void trainEmployeeCourse() {
		GameState.getInstance().getHrDepartment().trainEmployee(employee, Training.WORKSHOP);
		popover.hide();
	}

}
