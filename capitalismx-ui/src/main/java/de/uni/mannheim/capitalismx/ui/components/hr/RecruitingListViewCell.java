package de.uni.mannheim.capitalismx.ui.components.hr;

import java.io.IOException;

import de.uni.mannheim.capitalismx.hr.employee.Employee;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;

public class RecruitingListViewCell extends ListCell<Employee> {

	@FXML
	private Label nameLabel;

	@FXML
	private Label wageLabel;

	@FXML
	private Label skillLabel;

	@FXML
	private Label jobLabel;

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
						getClass().getClassLoader().getResource("fxml/components/recruiting_list_cell.fxml"));
				loader.setController(this);

				try {
					loader.load();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

			nameLabel.setText(employee.getName());
			wageLabel.setText((int) employee.getSalary() + " CC");
			skillLabel.setText(employee.getSkillLevel() + "");
			jobLabel.setText(employee.getEmployeeType().toString());

			setText(null);
			setGraphic(gridPane);
		}
	}

}
