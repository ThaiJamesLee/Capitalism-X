package de.uni.mannheim.capitalismx.ui.controller.component;

import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.PopOver;

import de.uni.mannheim.capitalismx.hr.domain.employee.Employee;
import de.uni.mannheim.capitalismx.hr.domain.employee.training.Training;
import de.uni.mannheim.capitalismx.gamecontroller.GameState;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.controller.general.UpdateableController;
import de.uni.mannheim.capitalismx.ui.utils.CapCoinFormatter;
import de.uni.mannheim.capitalismx.ui.utils.CssHelper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * Controller for the {@link PopOver} allowing the player to select a training
 * for an employee.
 * 
 * @author Jonathan
 *
 */
public class TrainingPopoverController implements Initializable {

	@FXML
	private AnchorPane trainingPopoverRoot;

	@FXML
	private Label training1NameLabel, training2NameLabel, training1CostLabel, training2CostLabel, training1EffectLabel,
			training2EffectLabel;

	@FXML
	private GridPane workshopGrid, courseGrid;

	private Employee employee;

	private UpdateableController parentController;

	private PopOver popover;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		CssHelper.replaceStylesheets(trainingPopoverRoot.getStylesheets());

		workshopGrid.setOnMouseClicked(e -> {
			trainEmployeeWorkshop();
		});
		courseGrid.setOnMouseClicked(e -> {
			trainEmployeeCourse();
		});
		this.training1CostLabel.setText(CapCoinFormatter.getCapCoins(Training.WORKSHOP.getPrice()));
		this.training2CostLabel.setText(CapCoinFormatter.getCapCoins(Training.COURSES.getPrice()));
		this.training1NameLabel.setText(Training.WORKSHOP.name());
		this.training2NameLabel.setText(Training.COURSES.name());
		this.training1EffectLabel.setText(UIManager.getLocalisedString("training.effect.skill")
				+ Training.WORKSHOP.getSkillLevelImprove() + UIManager.getLocalisedString("training.effect.salary")
				+ (int) ((Training.WORKSHOP.getSalaryIncreaseFactor() - 1) * 100) + "%");
		this.training2EffectLabel.setText(UIManager.getLocalisedString("training.effect.skill")
				+ Training.COURSES.getSkillLevelImprove() + UIManager.getLocalisedString("training.effect.salary")
				+ (int) ((Training.COURSES.getSalaryIncreaseFactor() - 1) * 100) + "%");
	}

	/**
	 * Initializes some attributes of the {@link PopOver}.
	 * 
	 * @param popover          The {@link PopOver} itself.
	 * @param employee         The employee, the Popover is for.
	 * @param parentController The parent {@link UpdateableController} that
	 *                         initialized the Popover.
	 */
	public void init(PopOver popover, Employee employee, UpdateableController parentController) {
		this.popover = popover;
		this.employee = employee;
		this.parentController = parentController;
	}

	@FXML
	/**
	 * Initiates a workshop for the Employee of the Popover.
	 */
	public void trainEmployeeWorkshop() {
		GameState.getInstance().getHrDepartment().trainEmployee(employee, Training.WORKSHOP, GameState.getInstance().getGameDate());
		parentController.update();
		popover.hide();
	}

	@FXML
	/**
	 * Initiates a course for the Employee of the Popover.
	 */
	public void trainEmployeeCourse() {
		GameState.getInstance().getHrDepartment().trainEmployee(employee, Training.WORKSHOP, GameState.getInstance().getGameDate());
		parentController.update();
		popover.hide();
	}

}
