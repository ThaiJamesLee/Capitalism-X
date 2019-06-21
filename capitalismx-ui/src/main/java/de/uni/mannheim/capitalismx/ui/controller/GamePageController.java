package de.uni.mannheim.capitalismx.ui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.ui.components.GameModule;
import de.uni.mannheim.capitalismx.ui.components.GameView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

/**
 * The {@link GameController} managing all actions on the GamePage.
 * 
 * @author Jonathan
 *
 */
public class GamePageController extends GameController {

	// The GridPane that contains all the modules.
	@FXML
	private GridPane moduleGrid;
	
	@FXML
	private StackPane sidemenuPane;

	
	
	//Controll elements
	@FXML
	private Label viewTitleLabel;
	@FXML
	private Button btnSkip;
	@FXML
	private Button btnForward;
	@FXML
	private Button btnPlayPause;
	@FXML 
	private ImageView iconPlayPause;
	private boolean isPaused;
	
	
	//The SideMenuController
	@FXML
	private SideMenuController sidemenuController;

	// The type of content that is currently being displayed.
	private GameView currentActiveView;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		this.isPaused = false;
		
		//Bind titleLabel to StringProperty in SideMenuController
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/sidemenu.fxml"));
		Parent rootB;
		try {
			rootB = loader.load();
			SideMenuController controllerB = loader.getController();
			viewTitleLabel.textProperty().unbind();
			viewTitleLabel.textProperty().bind(controllerB.titleProperty());
			sidemenuPane.getChildren().setAll(rootB);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


		btnSkip.setOnAction(e -> {
			System.out.println("Skip a week (?)");
		});
		
		btnForward.setOnAction(e -> {
			//TODO
		});
	
		btnPlayPause.setOnAction(e -> {
			boolean pause = this.isPaused;
			if(pause) {
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

	@Override
	public void update() {

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

	/**
	 * Switches the displayed contentType by removing all {@link GameModule}s of
	 * that type.
	 */
	public void switchContentType() {
		// TODO new view
		for (GameModule module : currentActiveView.getModules()) {
			moduleGrid.getChildren().remove(module.getRootElement());
		}
	}

}
