package de.uni.mannheim.capitalismx.ui.controller.module.hr;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.hr.department.HRDepartment;
import de.uni.mannheim.capitalismx.hr.employee.Employee;
import de.uni.mannheim.capitalismx.hr.employee.EmployeeGenerator;
import de.uni.mannheim.capitalismx.ui.components.hr.EmployeeBox;
import de.uni.mannheim.capitalismx.ui.components.hr.RecruitingListViewCell;
import de.uni.mannheim.capitalismx.ui.controller.module.GameModuleController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;

public class EmployeeListController extends GameModuleController {

	@FXML
	private TabPane employeeTabPane;

	@FXML 
	private VBox engineerList;
	
	@FXML 
	private VBox salesList;

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		HRDepartment hrDep = HRDepartment.getInstance();
		hrDep.hire(EmployeeGenerator.getInstance().generateEngineer(15));
		hrDep.hire(EmployeeGenerator.getInstance().generateEngineer(45));
		hrDep.hire(EmployeeGenerator.getInstance().generateEngineer(23));

		List<Employee> engineers = hrDep.getEngineerTeam().getTeam();
		
		for(Employee engineer : engineers) {
			engineerList.getChildren().add(new EmployeeBox(engineer).getRoot());
		}
		
//		salesList.setItems(salesPeopleObservable);
//		salesList.setCellFactory(employeeListView -> new RecruitingListViewCell());
	}

	

}
