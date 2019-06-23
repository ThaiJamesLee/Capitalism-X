package de.uni.mannheim.capitalismx.ui.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

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
	private ToggleButton btnForward;
	@FXML
	private Button btnPlayPause;
	@FXML 
	private ImageView iconPlayPause;
	@FXML
	private Label timeLabel;
	private Timeline  timeline;

	
	//needed for correct timedisplay
	//TODO replace current mock implementation
	private boolean isPaused;
	private LocalDate gameDay;


	//StringProperty containing the current Title string, bound to Lable in parent GamePageController
	private StringProperty title;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.title  = new SimpleStringProperty("Overall View");
		
		//set up date and bind to timeLabel
		this.isPaused = false;
		this.gameDay = LocalDate.of(1990, 1, 1);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMM dd yyy").withLocale(Locale.ENGLISH);
		
		// update once every second (as long as rate remains 1)
		timeline = new Timeline(new KeyFrame(
				
				  Duration.seconds(1),
			        event -> {
			        	if(!isPaused) {
			        	this.gameDay = gameDay.plusDays(1);
			        	}
			        	timeLabel.setText(dtf.format(gameDay));
			        }
		));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();


		
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
			this.gameDay = gameDay.plusDays(7);
			this.timeLabel.setText(dtf.format(gameDay));
			
		});
		
		btnForward.setOnAction(e -> {
			if(btnForward.isSelected()) {
				this.timeline.setRate(2);	
			}
			else {
				this.timeline.setRate(1);	
			}

		});
	
		btnPlayPause.setOnAction(e -> {
			if(this.isPaused) {
				this.resumeGame();
				iconPlayPause.setImage(new Image(getClass().getClassLoader().getResourceAsStream("icons/pause.png")));

			}
			else {
				this.pauseGame();
				iconPlayPause.setImage(new Image(getClass().getClassLoader().getResourceAsStream("icons/play-button.png")));

		
			}
		});
	}
	
	private void pauseGame() {
		this.isPaused = true;
		this.timeline.pause();
		//TODO implement functionality
	}
	
	private void resumeGame() {
		this.isPaused = false;
		this.timeline.play();
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
