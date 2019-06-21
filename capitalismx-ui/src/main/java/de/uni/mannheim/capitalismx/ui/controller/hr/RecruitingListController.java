package de.uni.mannheim.capitalismx.ui.controller.hr;

import java.net.URL;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.hr.employee.Employee;
import de.uni.mannheim.capitalismx.hr.employee.EmployeeGenerator;
import de.uni.mannheim.capitalismx.ui.components.hr.RecruitingListViewCell;
import de.uni.mannheim.capitalismx.ui.controller.GameController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

public class RecruitingListController extends GameController {

	@FXML
	private ListView<Employee> employeeList;
	
	private ObservableList<Employee> employeeListObservable;
	
	public RecruitingListController() {
		employeeListObservable = FXCollections.observableArrayList();
		EmployeeGenerator generator = new EmployeeGenerator();
		for(int i = 0; i < 10; i++) {
			employeeListObservable.add(generator.generateEngineer((int)(Math.random()*100)));
		}
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		employeeList.setItems(employeeListObservable);
		employeeList.setCellFactory(employeeListView -> new RecruitingListViewCell());
		employeeList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
	}

}
