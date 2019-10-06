package de.uni.mannheim.capitalismx.ui.controller.component;

import java.net.URL;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.domain.employee.Employee;
import org.controlsfx.control.PopOver;

import de.uni.mannheim.capitalismx.gamelogic.GameState;
import de.uni.mannheim.capitalismx.domain.employee.Training;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class TrainingPopoverController implements Initializable {

	@FXML
	private AnchorPane trainingPopoverRoot;

	@FXML
	private Label training1NameLabel, training2NameLabel, training1CostLabel, training2CostLabel, training1EffectLabel,
			training2EffectLabel;

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
		this.training1CostLabel.setText(Training.WORKSHOP.getPrice() + "CC");
		this.training2CostLabel.setText(Training.COURSES.getPrice() + "CC");
		this.training1NameLabel.setText(Training.WORKSHOP.name());
		this.training2NameLabel.setText(Training.COURSES.name());
		this.training1EffectLabel.setText("Skill +" + Training.WORKSHOP.getSkillLevelImprove() + ", Salary " + (int)(Training.WORKSHOP.getSalaryIncreaseFactor() - 1)*100 + "%");
		this.training2EffectLabel.setText("Skill +" + Training.COURSES.getSkillLevelImprove() + ", Salary " + (int)(Training.COURSES.getSalaryIncreaseFactor() - 1)*100 + "%");
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
