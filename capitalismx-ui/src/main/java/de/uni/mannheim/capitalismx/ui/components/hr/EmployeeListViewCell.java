package de.uni.mannheim.capitalismx.ui.components.hr;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.domain.employee.Employee;
import org.controlsfx.control.PopOver;

import de.uni.mannheim.capitalismx.gamelogic.GameState;
import de.uni.mannheim.capitalismx.domain.employee.Training;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.GameViewType;
import de.uni.mannheim.capitalismx.ui.components.UIElementType;
import de.uni.mannheim.capitalismx.ui.controller.component.TrainingPopoverController;
import de.uni.mannheim.capitalismx.ui.controller.general.UpdateableController;
import de.uni.mannheim.capitalismx.ui.controller.overlay.hr.EmployeeDetailController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;

public class EmployeeListViewCell extends ListCell<Employee> implements UpdateableController {

	@FXML
	private Label nameLabel;

	@FXML
	private Label wageLabel;

	@FXML
	private Label skillLabel;

	@FXML
	private Button trainButton, fireButton;

	@FXML
	private GridPane gridPane;

	private FXMLLoader loader;
	
	private Employee employee;

	@Override
	protected void updateItem(Employee employee, boolean empty) {
		super.updateItem(employee, empty);
		this.employee = employee;
		if (empty || employee == null) {
			setText(null);
			setGraphic(null);
		} else {
			if (loader == null) {
				//TODO richtiges ressourceBundle übergeben
				ResourceBundle bundle = UIManager.getResourceBundle();
				loader = new FXMLLoader(
						getClass().getClassLoader().getResource("fxml/components/employee_list_cell.fxml"), bundle);
				loader.setController(this);

				try {
					loader.load();
				} catch (IOException e) {
					e.printStackTrace();
				}
				// Open Overlay on click
				// TODO remove header click and maybe set listener only to name label
				gridPane.setOnMouseClicked(e -> {
					EmployeeDetailController overlayController = (EmployeeDetailController) UIManager.getInstance()
							.getGameView(GameViewType.HR).getModule(UIElementType.HR_EMPLOYEES_OVERVIEW).getOverlay()
							.getController();
					overlayController.initForDifferentEmployee(employee);
					UIManager.getInstance().getGamePageController().showOverlay(UIElementType.HR_EMPLOYEES_OVERVIEW);
				});

				fireButton.setOnAction(e -> {
					GameState.getInstance().getHrDepartment().fire(employee);
				});

				FXMLLoader popoverLoader = new FXMLLoader(
						getClass().getClassLoader().getResource("fxml/components/training_popover.fxml"), bundle);
				PopOver trainPopover = new PopOver();
				try {
					trainPopover.setContentNode(popoverLoader.load());
					TrainingPopoverController popOverController = ((TrainingPopoverController) popoverLoader
							.getController());
					popOverController.init(trainPopover, employee, this);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				trainButton.setOnAction(e -> {
					trainPopover.show(trainButton);
					GameState.getInstance().getHrDepartment().trainEmployee(employee, Training.COURSES);
				});
			}

			update();
			
			setText(null);
			setGraphic(gridPane);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		nameLabel.setText(employee.getName());
		wageLabel.setText((int) employee.getSalary() + " CC");
		skillLabel.setText(employee.getSkillLevel() + "");
	}

}
