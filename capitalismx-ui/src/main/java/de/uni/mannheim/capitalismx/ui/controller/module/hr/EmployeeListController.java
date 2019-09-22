package de.uni.mannheim.capitalismx.ui.controller.module.hr;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.hr.department.HRDepartment;
import de.uni.mannheim.capitalismx.hr.employee.Employee;
import de.uni.mannheim.capitalismx.hr.employee.EmployeeGenerator;
import de.uni.mannheim.capitalismx.ui.components.hr.EmployeeListViewCell;
import de.uni.mannheim.capitalismx.ui.controller.module.GameModuleController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;

public class EmployeeListController extends GameModuleController {

	@FXML
	private TabPane employeeTabPane;

	@FXML 
	private ListView<Employee> engineerList;
	private ObservableList<Employee> engineers;
	
	@FXML 
	private ListView<Employee> salesList;
	private ObservableList<Employee> salesPeople;

	@Override
	public void update() {
		HRDepartment hrDep = HRDepartment.getInstance();
		engineers = FXCollections.observableArrayList(hrDep.getEngineerTeam().getTeam());
		salesPeople = FXCollections.observableArrayList(hrDep.getSalesTeam().getTeam());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		HRDepartment hrDep = HRDepartment.getInstance();
		hrDep.hire(EmployeeGenerator.getInstance().generateEngineer(42));
		hrDep.hire(EmployeeGenerator.getInstance().generateSalesPeople(43));
		hrDep.hire(EmployeeGenerator.getInstance().generateEngineer(4));
		engineers = FXCollections.observableArrayList(hrDep.getEngineerTeam().getTeam());
		salesPeople = FXCollections.observableArrayList(hrDep.getSalesTeam().getTeam());
		
		engineerList.setItems(engineers);
		engineerList.setCellFactory(employeeListView -> new EmployeeListViewCell());
		salesList.setItems(salesPeople);
		salesList.setCellFactory(employeeListView -> new EmployeeListViewCell());
	}
	

}
