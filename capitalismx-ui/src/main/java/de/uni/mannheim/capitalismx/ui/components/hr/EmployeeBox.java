package de.uni.mannheim.capitalismx.ui.components.hr;

import java.io.IOException;
import java.util.Properties;

import de.uni.mannheim.capitalismx.hr.employee.Employee;
import de.uni.mannheim.capitalismx.ui.application.CapXApplication;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.UIElementType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class EmployeeBox {

	@FXML
	private GridPane gridPane;

	@FXML
	private Label nameLabel;

	@FXML
	private Label wageLabel;

	@FXML
	private Label skillLabel;

	private FXMLLoader loader;

	public EmployeeBox(Employee employee) {
		loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/components/recruiting_list_cell.fxml"));
		loader.setController(this);

		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		nameLabel.setText(employee.getName());
		wageLabel.setText((int) employee.getSalary() + " CC");
		skillLabel.setText(employee.getSkillLevel() + "");
		
		gridPane.setOnMouseClicked(e -> {
			Properties prop = new Properties();
			prop.setProperty("employeeId", employee.getID());
			UIManager.getInstance().getGamePageController().showOverlay(UIElementType.HR_EMPLOYEES_OVERVIEW, prop);
		});
	}

	public Parent getRoot() {
		return this.gridPane;
	}

}
