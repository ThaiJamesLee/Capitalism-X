package de.uni.mannheim.capitalismx.ui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.ui.controller.general.UpdateableController;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;

public class LoadingScreenController implements UpdateableController {

	@FXML 
	private ProgressBar progressBar;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}
	
	public void initProgressBar(ReadOnlyDoubleProperty readOnlyDoubleProperty) {
		this.progressBar.progressProperty().bind(readOnlyDoubleProperty);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}
