package de.uni.mannheim.capitalismx.ui.controller.overlay.hr;

import de.uni.mannheim.capitalismx.ui.controller.interfaces.Updateable;
import de.uni.mannheim.capitalismx.ui.controller.overlay.GameOverlayController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class EmployeeDetailController extends GameOverlayController implements Updateable{

	@FXML
	public Label employeeNameLabel;

	@Override
	public void update() {
		int employeeId = Integer.parseInt(this.getProperties().getProperty("employeeId", ""));
		System.out.println("update: " +employeeId);
		employeeNameLabel.setText("Employee number: " + employeeId);
	}
	
	
	
}
