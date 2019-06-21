package de.uni.mannheim.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * Controller for the menu on the GamePage.
 * 
 * @author Jonathan
 * @author Alex
 *
 */
public class SideMenuController extends UIController {
	
	@FXML
	private Button btnOverall;
	@FXML
	private Button btnFinance;
	@FXML
	private Button btnHR;
	@FXML
	private Button btnProcurement;
	@FXML
	private Button btnProduction;
	@FXML 
	private Button btnLogistics;
	@FXML 
	private Button btnWarehouse;
	@FXML
	private Button btnMarketing;
	@FXML
	private Button btnMessages;

	//StringProperty containing the current Title string, bound to Lable in parent GamePageController
	private StringProperty title = new SimpleStringProperty("Overall View");

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnOverall.setOnAction(e -> {
			setTitle("Overall View");
		});
		
		btnFinance.setOnAction(e -> {
			setTitle("Finance View");
		});
		
		btnHR.setOnAction(e -> {
			setTitle("HR View");
		});
		
		btnProcurement.setOnAction(e -> {
			setTitle("Procurement View");
		});
	
		btnProduction.setOnAction(e -> {
			setTitle("Production View");
		});
		
		btnWarehouse.setOnAction(e -> {
			setTitle("Warehouse View");
		});
		
		btnLogistics.setOnAction(e -> {
			setTitle("Logistics View");
		});
		
		btnMarketing.setOnAction(e -> {
			setTitle("Marketing View");
		});
		
		btnMessages.setOnAction(e -> {
			setTitle("Message Inbox");
		});
		
	}
	
	//Methods to set the current title, which is bound to the corresponding Label in the parent GamePage Controller
	public StringProperty titleProperty() {
		return title ;
	}
	
	public final String getText() {
		return titleProperty().get();
	}

	private final void setTitle(String title) {
		titleProperty().set(title);
	}
}
