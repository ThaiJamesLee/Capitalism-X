package de.uni.mannheim.capitalismx.ui.controller.hr;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import de.uni.mannheim.capitalismx.hr.employee.Employee;
import de.uni.mannheim.capitalismx.hr.employee.EmployeeGenerator;
import de.uni.mannheim.capitalismx.ui.controller.GameElementController;
import de.uni.mannheim.capitalismx.ui.controller.GameModuleController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

public class EmployeeListController extends GameModuleController {

	@FXML
	private AnchorPane employeeTreeAnchor;

	private ObservableList<EmployeeTree> employeeListObservable;

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		JFXTreeTableView<EmployeeTree> employeeTreeView = new JFXTreeTableView<EmployeeTree>();
		JFXTreeTableColumn<EmployeeTree, String> roleCol = new JFXTreeTableColumn<>("Role");
		roleCol.setPrefWidth(100);
		roleCol.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<EmployeeTree, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<EmployeeTree, String> param) {
						return param.getValue().getValue().role;
					}
				});
		JFXTreeTableColumn<EmployeeTree, String> nameCol = new JFXTreeTableColumn<>("Name");
		nameCol.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<EmployeeTree, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<EmployeeTree, String> param) {
						return param.getValue().getValue().name;
					}
				});
		JFXTreeTableColumn<EmployeeTree, String> salaryCol = new JFXTreeTableColumn<>("Salary");
		salaryCol.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<EmployeeTree, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<EmployeeTree, String> param) {
						return param.getValue().getValue().salary;
					}
				});
		JFXTreeTableColumn<EmployeeTree, String> skillLevelCol = new JFXTreeTableColumn<>("Skill");
		skillLevelCol.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<EmployeeTree, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<EmployeeTree, String> param) {
						return param.getValue().getValue().skill;
					}
				});

		employeeListObservable = FXCollections.observableArrayList();
		EmployeeGenerator generator = new EmployeeGenerator();
		for (int i = 0; i < 10; i++) {
			Employee employee = generator.generateEngineer((int) (Math.random() * 100));
			employeeListObservable.add(new EmployeeTree("Engineer", employee.getName(),
					(int) employee.getSalary() + " CC", employee.getSkillLevel() + "%"));
		}

		final TreeItem<EmployeeTree> rootTree = new RecursiveTreeItem<EmployeeTree>(employeeListObservable,
				RecursiveTreeObject::getChildren);
		employeeTreeView.getColumns().setAll(roleCol, nameCol, salaryCol, skillLevelCol);
		employeeTreeView.setRoot(rootTree);
		employeeTreeView.setShowRoot(false);

		employeeTreeAnchor.getChildren().add(employeeTreeView);
		AnchorPane.setTopAnchor(employeeTreeView, 0.0);
		AnchorPane.setLeftAnchor(employeeTreeView, 0.0);
		AnchorPane.setRightAnchor(employeeTreeView, 0.0);
		AnchorPane.setBottomAnchor(employeeTreeView, 0.0);

	}

	class EmployeeTree extends RecursiveTreeObject<EmployeeTree> {

		StringProperty role;
		StringProperty name;
		StringProperty salary;
		StringProperty skill;

		public EmployeeTree(String role, String name, String salary, String skill) {
			this.role = new SimpleStringProperty(role);
			this.name = new SimpleStringProperty(name);
			this.salary = new SimpleStringProperty(salary);
			this.skill = new SimpleStringProperty(skill);
		}

	}

}
