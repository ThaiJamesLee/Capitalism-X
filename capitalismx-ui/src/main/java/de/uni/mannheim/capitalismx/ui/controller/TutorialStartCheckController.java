package de.uni.mannheim.capitalismx.ui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.PopOver;

import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.controller.general.UpdateableController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class TutorialStartCheckController implements UpdateableController {

	private PopOver popover;
	
	@FXML
	private Button accept, refuse;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		accept.setOnAction(e -> {
			UIManager.getInstance().getTutorial().start();
			popover.hide();
		});
		refuse.setOnAction(e -> {
			popover.hide();
		});
	}

	public void setPopover(PopOver p) {
		popover = p;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}
