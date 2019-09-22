package de.uni.mannheim.capitalismx.ui.controller.module.hr;

import java.net.URL;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.hr.department.HRDepartment;
import de.uni.mannheim.capitalismx.hr.employee.Employee;
import de.uni.mannheim.capitalismx.hr.employee.EmployeeGenerator;
import de.uni.mannheim.capitalismx.ui.application.Main;
import de.uni.mannheim.capitalismx.ui.components.hr.RecruitingListViewCell;
import de.uni.mannheim.capitalismx.ui.controller.GamePageController;
import de.uni.mannheim.capitalismx.ui.controller.module.GameModuleController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

public class RecruitingListController extends GameModuleController {

	@FXML
	private ListView<Employee> recruitingList;

	@FXML
	private Button hireButton;

	private ObservableList<Employee> employeeListObservable;

	public RecruitingListController() {
		employeeListObservable = FXCollections.observableArrayList();
		EmployeeGenerator generator = EmployeeGenerator.getInstance();
		for (int i = 0; i < 15; i++) {
			int rand = (int)(Math.random() * 100);
			employeeListObservable.add((rand % 2 == 0) ? generator.generateEngineer(rand) : generator.generateSalesPeople(rand));
		}
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		recruitingList.setItems(employeeListObservable);
		recruitingList.setCellFactory(employeeListView -> new RecruitingListViewCell());
		recruitingList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	}

	@FXML
	private void hireEmployee() {
		Employee employeeToHire = recruitingList.getSelectionModel().getSelectedItem();
		HRDepartment.getInstance().hire(employeeToHire);
		employeeListObservable.remove(employeeToHire);
		recruitingList.getSelectionModel().clearSelection();
	}

}
