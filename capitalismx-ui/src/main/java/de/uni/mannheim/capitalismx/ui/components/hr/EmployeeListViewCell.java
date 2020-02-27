package de.uni.mannheim.capitalismx.ui.components.hr;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ResourceBundle;

import org.controlsfx.control.PopOver;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.uni.mannheim.capitalismx.hr.domain.employee.Employee;
import de.uni.mannheim.capitalismx.gamecontroller.GameState;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.controller.component.TrainingPopoverController;
import de.uni.mannheim.capitalismx.ui.controller.general.UpdateableController;
import de.uni.mannheim.capitalismx.ui.utils.CapCoinFormatter;
import de.uni.mannheim.capitalismx.ui.utils.GraphicHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

/**
 * The custom {@link ListCell} for Employees of the company.
 * 
 * @author Jonathan
 *
 */
public class EmployeeListViewCell extends ListCell<Employee> implements UpdateableController {

	@FXML
	private Label nameLabel;

	@FXML
	private Label wageLabel;

	private Label satisfactionLabel;

	private FontAwesomeIcon satisfactionIcon = new FontAwesomeIcon();

	private Button trainButton, fireButton;

	@FXML
	private AnchorPane skillPane;

	@FXML
	private GridPane gridPane;

	private FXMLLoader loader;

	private Employee employee;

	private ResourceBundle bundle;

	{
		// Adds a ChangeListener to the SelectedProperty to toggle the extended cell
		// when (de-)selected.
		selectedProperty().addListener((observable, oldValue, newValue) -> {
			Employee employee = getItem();
			if (!isEmpty() && employee != null) {
				if (oldValue && !newValue) {
					hideExtended();
				} else if (!oldValue && newValue) {
					displayExtended();
				}
			}
		});
	}

	/**
	 * Shows the extended version of the Cell with the buttons for firing and hiring
	 * the employee.
	 */
	private void displayExtended() {
		if (isSelected()) {
			gridPane.add(fireButton, 4, 1, 2, 1);
			gridPane.add(trainButton, 3, 1, 1, 1);
			gridPane.add(satisfactionLabel, 1, 1);
			gridPane.add(satisfactionIcon, 0, 1);
		}
	}

	/**
	 * Hides the extended version of the Cell with the buttons for firing and hiring
	 * the employee.
	 */
	private void hideExtended() {
		if (!isSelected()) {
			gridPane.getChildren().remove(fireButton);
			gridPane.getChildren().remove(trainButton);
			gridPane.getChildren().remove(satisfactionIcon);
			gridPane.getChildren().remove(satisfactionLabel);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	/**
	 * Prepare all the content (buttons/labels and the {@link PopOver} for training)
	 * of the extended list cell, that will be displayed when clicked on the cell.
	 */
	private void prepareExtendedCell() {
		trainButton = new Button(bundle.getString("employeeList.train"));
		fireButton = new Button(bundle.getString("employeeList.fire"));
		trainButton.getStyleClass().add("btn_standard");
		fireButton.getStyleClass().add("btn_standard");
		satisfactionLabel = new Label(NumberFormat.getPercentInstance(UIManager.getResourceBundle().getLocale())
				.format(employee.getJobSatisfaction()));
		satisfactionLabel.getStyleClass().add("list-label-large");
		satisfactionIcon.setIconName("SMILE_ALT");
		satisfactionIcon.setSize("1.5em");

		fireButton.setOnAction(eFire -> {
			GameState.getInstance().getHrDepartment().fire(employee);
		});

		// Prepare the Popover for the trainButton
		FXMLLoader popoverLoader = new FXMLLoader(
				getClass().getClassLoader().getResource("fxml/components/training_popover.fxml"), bundle);
		PopOver trainPopover = new PopOver();
		try {
			trainPopover.setContentNode(popoverLoader.load());
			trainPopover.setFadeInDuration(Duration.millis(50));
			TrainingPopoverController popOverController = ((TrainingPopoverController) popoverLoader.getController());
			popOverController.init(trainPopover, employee, this);
		} catch (IOException e) {
			e.printStackTrace();
		}

		trainButton.setOnAction(eTrain -> {
			trainPopover.show(trainButton);
			trainPopover.setOpacity(1.0);
		});
	}

	@Override
	public void update() {
		nameLabel.setText(employee.getName());
		wageLabel.setText(CapCoinFormatter.getCapCoins(employee.getSalary()));
		skillPane.getChildren().clear();
		skillPane.getChildren().add(GraphicHelper.createSkillGraphic(employee.getSkillLevel(), "1.5em"));
		satisfactionLabel.setText(employee.getJobSatisfaction() + "");
	}

	@Override
	protected void updateItem(Employee employee, boolean empty) {
		super.updateItem(employee, empty);
		this.employee = employee;
		if (empty || employee == null) {
			setText(null);
			setGraphic(null);
		} else {
			if (loader == null) {
				bundle = UIManager.getResourceBundle();
				loader = new FXMLLoader(
						getClass().getClassLoader().getResource("fxml/components/employee_list_cell.fxml"), bundle);
				loader.setController(this);

				try {
					loader.load();
				} catch (IOException e) {
					e.printStackTrace();
				}

				prepareExtendedCell();
			}

			update();

			setText(null);
			setGraphic(gridPane);
		}
	}

}
