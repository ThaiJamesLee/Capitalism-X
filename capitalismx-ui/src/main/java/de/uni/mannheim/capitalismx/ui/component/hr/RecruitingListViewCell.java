package de.uni.mannheim.capitalismx.ui.component.hr;

import java.io.IOException;

import de.uni.mannheim.capitalismx.gamecontroller.GameState;
import de.uni.mannheim.capitalismx.hr.domain.employee.Employee;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.component.GameModuleType;
import de.uni.mannheim.capitalismx.ui.component.general.GameAlert;
import de.uni.mannheim.capitalismx.ui.controller.module.hr.RecruitingListController;
import de.uni.mannheim.capitalismx.ui.util.CapCoinFormatter;
import de.uni.mannheim.capitalismx.ui.util.GraphicHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * {@link ListCell} for the displaying lists of possible {@link Employee}.
 * 
 * @author Jonathan
 *
 */
public class RecruitingListViewCell extends ListCell<Employee> {

	private Employee employee;

	@FXML
	private Label nameLabel;

	@FXML
	private Label wageLabel;

	@FXML
	private AnchorPane skillPane;

	@FXML
	private GridPane gridPane;

	private FXMLLoader loader;

	@Override
	protected void updateItem(Employee employee, boolean empty) {
		super.updateItem(employee, empty);
		if (empty || employee == null) {
			setText(null);
			setGraphic(null);
		} else {
			if (loader == null) {
				loader = new FXMLLoader(
						getClass().getClassLoader().getResource("fxml/component/recruiting_list_cell.fxml"),
						UIManager.getResourceBundle());
				loader.setController(this);

				try {
					loader.load();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			this.employee = employee;
			nameLabel.setText(employee.getName());
			wageLabel.setText(CapCoinFormatter.getCapCoins(employee.getSalary()));
			// add skill graphic
			skillPane.getChildren().clear();
			skillPane.getChildren().add(GraphicHelper.createSkillGraphic(employee.getSkillLevel()));
			gridPane.setOnMouseClicked(e -> {
				hireEmployee();
			});

			setText(null);
			setGraphic(gridPane);
		}
	}

	/**
	 * Hire the selected employee and remove him from the lists.
	 */
	public void hireEmployee() {
		double hireCost = GameState.getInstance().getHrDepartment().hire(employee);
		if (hireCost > 0) {
			RecruitingListController recruitingController = (RecruitingListController) UIManager.getInstance()
					.getModule(GameModuleType.HR_RECRUITING_OVERVIEW).getController();
			recruitingController.removeEmployee(employee);
			GameState.getInstance().getFinanceDepartment().decreaseCash(GameState.getInstance().getGameDate(),
					hireCost);
		} else {
			GameAlert error = new GameAlert(AlertType.WARNING,
					UIManager.getLocalisedString("hr.recruiting.alert.title"),
					UIManager.getLocalisedString("hr.recruiting.alert.description"));
			error.showAndWait();
		}
	}

}
