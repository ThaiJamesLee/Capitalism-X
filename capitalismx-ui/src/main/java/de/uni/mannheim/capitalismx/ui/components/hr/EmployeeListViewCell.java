package de.uni.mannheim.capitalismx.ui.components.hr;

import java.io.IOException;

import org.controlsfx.control.PopOver;

import de.uni.mannheim.capitalismx.hr.department.HRDepartment;
import de.uni.mannheim.capitalismx.hr.domain.Training;
import de.uni.mannheim.capitalismx.hr.employee.Employee;
import de.uni.mannheim.capitalismx.ui.application.CapXApplication;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.GameViewType;
import de.uni.mannheim.capitalismx.ui.components.UIElementType;
import de.uni.mannheim.capitalismx.ui.controller.component.TrainingPopoverController;
import de.uni.mannheim.capitalismx.ui.controller.overlay.hr.EmployeeDetailController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;

public class EmployeeListViewCell extends ListCell<Employee> {

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
	
	 @Override
	    protected void updateItem(Employee employee, boolean empty) {
	        super.updateItem(employee, empty);
	        if(empty || employee == null) {
	            setText(null);
	            setGraphic(null);
	        } else { 
	        	if (loader == null) {
	        		loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/components/employee_list_cell.fxml"));
	        		loader.setController(this);

	                try {
	                	loader.load();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	                //Open Overlay on click
	                //TODO remove header click and maybe set listener only to name label
	                gridPane.setOnMouseClicked(e -> {
	                	EmployeeDetailController overlayController = (EmployeeDetailController) UIManager.getInstance().getGameView(GameViewType.HR).getModule(UIElementType.HR_EMPLOYEES_OVERVIEW).getOverlay().getController();
	                	overlayController.setEmployee(employee);
	                	UIManager.getInstance().getGamePageController().showOverlay(UIElementType.HR_EMPLOYEES_OVERVIEW);
	                });
	                
	                fireButton.setOnAction(e -> {
		        		HRDepartment.getInstance().fire(employee);
		        	});
	                
	                FXMLLoader popoverLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/components/training_popover.fxml"));
	        		PopOver trainPopover = new PopOver();
	        		try {
	        			trainPopover.setContentNode(popoverLoader.load());
	        			TrainingPopoverController popOverController = ((TrainingPopoverController)popoverLoader.getController());
	        			popOverController.init(trainPopover, employee);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	                
	                trainButton.setOnAction(e -> {
		        		trainPopover.show(trainButton);
		        		HRDepartment.getInstance().trainEmployee(employee, Training.COURSES);
		        	});
	            }
	        	
	        	nameLabel.setText(employee.getName());
	        	wageLabel.setText((int)employee.getSalary() + " CC");
	        	skillLabel.setText(employee.getSkillLevel() + "");
	        	
	        	

	        	setText(null);
	        	setGraphic(gridPane);
	        }
	 }
	
}
