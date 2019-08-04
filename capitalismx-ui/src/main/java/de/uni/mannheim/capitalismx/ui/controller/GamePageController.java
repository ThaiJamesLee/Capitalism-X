package de.uni.mannheim.capitalismx.ui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.ui.application.Main;
import de.uni.mannheim.capitalismx.ui.components.GameElementType;
import de.uni.mannheim.capitalismx.ui.components.GameModule;
import de.uni.mannheim.capitalismx.ui.components.GameOverlay;
import de.uni.mannheim.capitalismx.ui.components.GameView;
import de.uni.mannheim.capitalismx.ui.components.GameViewType;
import de.uni.mannheim.capitalismx.ui.utils.GridPosition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

/**
 * The {@link GameElementController} managing all actions on the GamePage.
 * 
 * @author Jonathan
 *
 */
public class GamePageController extends GameElementController {

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

	@FXML
	private AnchorPane overlayPane;

	// The SideMenuController
	@FXML
	private SideMenuController sidemenuController;

	// The type of content that is currently being displayed.
	private GameView currentActiveView;

	// Elements for the notification-system
	private NotificationController notificationController;
	private Parent notificationPaneReminder;
	private boolean openNotificationPane;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// Bind titleLabel to StringProperty in SideMenuController
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/sidemenu.fxml"));
		Parent rootB;
		try {
			rootB = loader.load();
			//TODO remove or adjust if necessary
//			SideMenuController controllerB = loader.getController();
//			viewTitleLabel.textProperty().unbind();
//			viewTitleLabel.textProperty().bind(controllerB.titleProperty());
			sidemenuPane.getChildren().setAll(rootB);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		btnMessages.setOnAction(e -> {
//			parentStackPane.getChildren().add(e);

			if (!openNotificationPane) {
				FXMLLoader loader2 = new FXMLLoader(
						getClass().getClassLoader().getResource("fxml/notificationPane3.fxml"));
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
		// TODO only for testing purpose
		switchView(GameViewType.HR);
	}

	public void removeNotificationPane() {
		parentStackPane.getChildren().remove(notificationPaneReminder);
		openNotificationPane = false;
	}

	/**
	 * Switches the displayed contentType by removing all {@link GameModule}s of
	 * that type.
	 * 
	 * @param viewType The {@link GameViewType} of the {@link GameView} to switch
	 *                 to.
	 */
	public void switchView(GameViewType viewType) {
		resetOverlay();
		viewTitleLabel.setText(viewType.getTitle());
		
		if (currentActiveView != null) {
			// remove all modules of current view
			for (GameModule module : currentActiveView.getModules()) {
				moduleGrid.getChildren().remove(module.getRootElement());
			}
		}
		// change current view and add modules
		currentActiveView = Main.getManager().getGameView(viewType);
		for (GameModule module : currentActiveView.getModules()) {
			GridPosition position = module.getGridPosition();
			moduleGrid.add(module.getRootElement(), position.getxStart(), position.getyStart(), position.getxSpan(),
					position.getySpan());
		}
	}

	/**
	 * If a {@link GameOverlay} is currently being displayed, it is removed from the
	 * GamePage.
	 */
	private void resetOverlay() {
		overlayPane.getChildren().clear();
	}

	/**
	 * Removes currently displayed overlay elements and displays the requested one
	 * 
	 * @param elementType The {@link GameElementType} of the {@link GameOverlay} to
	 *                    display.
	 */
	public void showOverlay(GameElementType elementType) {
		resetOverlay();
		
		// get requested overlay and display it of module and overlay are not null
		GameModule module = currentActiveView.getModule(elementType);
		if (module == null)
			return;
		GameOverlay overlay = module.getOverlay();
		if (overlay == null)
			return;
		Parent rootElement = currentActiveView.getModule(elementType).getOverlay().getRootElement();
		AnchorPane.setTopAnchor(rootElement, 10.0);
		AnchorPane.setLeftAnchor(rootElement, 10.0);
		AnchorPane.setRightAnchor(rootElement, 10.0);
		AnchorPane.setBottomAnchor(rootElement, 10.0);
		overlayPane.getChildren().add(rootElement);
		overlayPane.toFront();
	}

	/**
	 * Remove the displayed overlay from the view and hide it in the background.
	 */
	@FXML
	public void hideOverlay() {
		overlayPane.toBack();
		overlayPane.getChildren().clear();
	}

}
