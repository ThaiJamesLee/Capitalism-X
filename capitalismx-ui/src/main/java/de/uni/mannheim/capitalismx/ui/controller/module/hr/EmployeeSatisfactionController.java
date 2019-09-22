package de.uni.mannheim.capitalismx.ui.controller.module.hr;

import java.net.URL;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.hr.department.HRDepartment;
import de.uni.mannheim.capitalismx.ui.controller.module.GameModuleController;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;

public class EmployeeSatisfactionController extends GameModuleController {

	@FXML
	BarChart<CategoryAxis, NumberAxis> satisfactionChart;
	
	@Override
	public void update() {
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}
	
}
