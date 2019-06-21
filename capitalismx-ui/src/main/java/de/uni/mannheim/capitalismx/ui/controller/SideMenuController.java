package de.uni.mannheim.capitalismx.ui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
	private Button btnSkip;
	@FXML
	private Button btnForward;
	@FXML
	private Button btnPlayPause;
	@FXML 
	private ImageView iconPlayPause;
	private boolean isPaused;


	//StringProperty containing the current Title string, bound to Lable in parent GamePageController
	private StringProperty title = new SimpleStringProperty("Overall View");

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.isPaused = false;
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
		
		btnSkip.setOnAction(e -> {
			System.out.println("Skip a week (?)");
		});
		
		btnForward.setOnAction(e -> {
			//TODO
		});
	
		btnPlayPause.setOnAction(e -> {
			boolean pause = this.isPaused;
			if(this.isPaused) {
				System.out.println("It is currently paused!");
				this.resumeGame();
				iconPlayPause.setImage(new Image(getClass().getClassLoader().getResourceAsStream("icons/pause.png")));

			}
			else {
				System.out.println("Game is running now!");
				this.pauseGame();
				iconPlayPause.setImage(new Image(getClass().getClassLoader().getResourceAsStream("icons/play-button.png")));

		
			}
		});
	}
	
	private void pauseGame() {
		this.isPaused = true;
		System.out.println("GAme Paused");
		//TODO implement functionality
	}
	
	private void resumeGame() {
		this.isPaused = false;
		System.out.println("Game resumed");
		//TODO implement functionality
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
