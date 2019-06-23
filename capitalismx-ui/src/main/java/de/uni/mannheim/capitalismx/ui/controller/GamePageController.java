package de.uni.mannheim.capitalismx.ui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.ui.application.Main;
import de.uni.mannheim.capitalismx.ui.components.GameModule;
import de.uni.mannheim.capitalismx.ui.components.GameView;
import de.uni.mannheim.capitalismx.ui.components.GameViewType;
import de.uni.mannheim.capitalismx.ui.utils.GridPosition;
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

	@FXML
	private Label viewTitleLabel;

	@FXML
	private Button btnMessages;

	@FXML
	private StackPane parentStackPane;
	
	//The SideMenuController
	@FXML
	private SideMenuController sidemenuController;

	// The type of content that is currently being displayed.
	private GameView currentActiveView;
	
	private NotificationController notificationController;
	private Parent notificationPaneReminder;
	private boolean openNotificationPane;
	@Override
	public void initialize(URL location, ResourceBundle resources) {

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

		
		btnMessages.setOnAction(e -> {
//			parentStackPane.getChildren().add(e);
			
			if(!openNotificationPane) {
				FXMLLoader loader2 = new FXMLLoader(getClass().getClassLoader().getResource("fxml/notificationPane3.fxml"));
				Parent rootC;
				try {
					rootC = loader2.load();
					notificationController = loader2.getController();
					parentStackPane.getChildren().add(rootC);
					notificationPaneReminder = rootC;
					openNotificationPane = true;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			
		});


	}

	@Override
	public void update() {
		//TODO only for testing purpose
		switchView(GameViewType.GAME_HR);
	}
	
	public void removeNotificationPane() {
		parentStackPane.getChildren().remove(notificationPaneReminder);
		openNotificationPane = false;
	}
	
	
	/**
	 * Switches the displayed contentType by removing all {@link GameModule}s of
	 * that type.
	 */
	public void switchView(GameViewType viewType) {
		if(currentActiveView != null) {
			// remove all modules of current view
			for (GameModule module : currentActiveView.getModules()) {
				moduleGrid.getChildren().remove(module.getRootElement());
			}
		}
		//change current view and add modules
		currentActiveView = Main.getManager().getGameView(viewType);
		for (GameModule module : currentActiveView.getModules()) {
			GridPosition position = module.getGridPosition();
			moduleGrid.add(module.getRootElement(), position.getxStart(), position.getyStart(), position.getxSpan(), position.getySpan());
		}
	}
	

}
