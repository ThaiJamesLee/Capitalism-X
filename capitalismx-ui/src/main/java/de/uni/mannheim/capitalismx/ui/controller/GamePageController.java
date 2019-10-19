package de.uni.mannheim.capitalismx.ui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import org.controlsfx.control.PopOver.ArrowLocation;

import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.GameModule;
import de.uni.mannheim.capitalismx.ui.components.GameNotification;
import de.uni.mannheim.capitalismx.ui.components.GameOverlay;
import de.uni.mannheim.capitalismx.ui.components.GameView;
import de.uni.mannheim.capitalismx.ui.components.GameViewType;
import de.uni.mannheim.capitalismx.ui.components.UIElementType;
import de.uni.mannheim.capitalismx.ui.components.general.TooltipFactory;
import de.uni.mannheim.capitalismx.ui.controller.general.UpdateableController;
import de.uni.mannheim.capitalismx.ui.utils.AnchorPaneHelper;
import de.uni.mannheim.capitalismx.ui.utils.GridPosition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

/**
 * The {@link UIElementController} managing all actions on the GamePage.
 * 
 * @author Jonathan
 *
 */
public class GamePageController implements UpdateableController {

	/**
	 * Menu elements
	 */
	@FXML
	private StackPane sidemenuPane;
	@FXML
	private Button btnMessages, btnMenu;
	@FXML
	private Label viewTitleLabel;
	@FXML
	private SideMenuController sidemenuController;
	@FXML
	private AnchorPane notificationAnchor;

	/**
	 * Content elements
	 */
	@FXML
	private StackPane parentStackPane;
	@FXML
	private StackPane contentStack;
	// The GridPane that contains all the modules.
	@FXML
	private GridPane moduleGrid;
	@FXML
	private AnchorPane overlayPane;
	
	private AnchorPane pane3D;

	/**
	 * General controller related attributes
	 */
	// The type of content that is currently being displayed.
	private GameView currentActiveView;

	/**
	 * elements for the message-system
	 */
	private MessageController messageController;
	// saves the added element so it can be addressed for removal
	private Parent messagePaneReminder;
	// flag to know whether message Pane is open or not: true=open false=closed.
	private boolean openMessagePane;

	/**
	 * elements for the in-game menu
	 */
	private IngameMenuController ingameMenuController;
	// saves the added element so it can be addressed for removal
	private Parent menuPaneReminder;
	// flag to know whether menu Pane is open or not: true=open false=closed.
	private boolean openMenuPane;


	public StackPane getContentStack() {
		return contentStack;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Bind titleLabel to StringProperty in SideMenuController
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/sidemenu.fxml"));
		Parent rootB;
		try {
			rootB = loader.load();
			// TODO remove or adjust if necessary
//			SideMenuController controllerB = loader.getController();
//			viewTitleLabel.textProperty().unbind();
//			viewTitleLabel.textProperty().bind(controllerB.titleProperty());
			sidemenuPane.getChildren().setAll(rootB);

			FXMLLoader loader2 = new FXMLLoader(
					getClass().getClassLoader().getResource("fxml/module/overview_map3d.fxml"));
			pane3D = loader2.load();
			contentStack.getChildren().add(pane3D);
			pane3D.toBack();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// TODO Refactor: keep panes in memory, loading them each time is probably not
		// very performant
		btnMessages.setOnAction(e -> {
//			parentStackPane.getChildren().add(e);

			if (!openMessagePane) {
				FXMLLoader loader2 = new FXMLLoader(getClass().getClassLoader().getResource("fxml/messagePane.fxml"));
				Parent rootC;
				try {
					rootC = loader2.load();
					messageController = loader2.getController();
					contentStack.getChildren().add(rootC);
					messagePaneReminder = rootC;
					openMessagePane = true;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		});

		btnMenu.setOnAction(e -> {
			if (!openMenuPane) {
				FXMLLoader loader2 = new FXMLLoader(getClass().getClassLoader().getResource("fxml/ingameMenu.fxml"));
				Parent rootD;
				try {
					rootD = loader2.load();
					ingameMenuController = loader2.getController();
					parentStackPane.getChildren().add(rootD);
					menuPaneReminder = rootD;
					openMenuPane = true;
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		TooltipFactory factory = new TooltipFactory();
		factory.setLocationOfArrow(ArrowLocation.LEFT_CENTER);
		factory.addSimpleTooltipToNode(btnMenu, "Settings");
	}

	@Override
	public void update() {
		for (GameModule m : currentActiveView.getModules()) {
			m.getController().update();
		}
	}

	public void removeMessagePane() {
		contentStack.getChildren().remove(messagePaneReminder);
		openMessagePane = false;
	}

	public void removeIngameMenuPane() {
		parentStackPane.getChildren().remove(menuPaneReminder);
		openMenuPane = false;
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
		currentActiveView = UIManager.getInstance().getGameView(viewType);
		for (GameModule module : currentActiveView.getModules()) {
			GridPosition position = module.getGridPosition();
			module.getController().update();
			moduleGrid.add(module.getRootElement(), position.getxStart(), position.getyStart(), position.getxSpan(),
					position.getySpan());
		}
	}

	/**
	 * Display a {@link GameNotification} on the GamePage. TODO create Queue of
	 * {@link GameNotification}s, in case multiple are created at the same time.
	 * (make this private and call in update once an update-Thread exists?)
	 * 
	 * @param notification The {@link GameNotification} to display.
	 */
	public void showNotification(GameNotification notification) {
		Parent rootElement = notification.getRoot();
		AnchorPaneHelper.snapNodeToAnchorPane(rootElement);
		notificationAnchor.getChildren().add(rootElement);
		// schedule removal of notification for two seconds later
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(() -> {
					removeNotification();
				});
				this.cancel();
			}
		}, 2000);
	}

	private void removeNotification() {
		notificationAnchor.getChildren().clear();
	}

	/**
	 * Removes currently displayed overlay elements and displays the requested one
	 * 
	 * @param elementType The {@link UIElementType} of the {@link GameOverlay} to
	 *                    display.
	 * @param properties  Optional properties for the overlay.
	 */
	public void showOverlay(UIElementType elementType, Properties properties) {
		resetOverlay();

		// get requested overlay and display it, if module and overlay are not null
		GameModule module = currentActiveView.getModule(elementType);
		if (module == null)
			return;
		GameOverlay overlay = module.getOverlay();
		if (overlay == null)
			return;

		overlay.getController().updateProperties(properties);
		overlay.getController().update();
		Parent rootElement = overlay.getRootElement();
		AnchorPaneHelper.snapNodeToAnchorPaneWithPadding(rootElement, 10.0);
		overlayPane.getChildren().add(rootElement);
		overlayPane.toFront();
	}

	/**
	 * Removes currently displayed overlay elements and displays the requested one
	 * 
	 * @param elementType The {@link UIElementType} of the {@link GameOverlay} to
	 *                    display.
	 */
	public void showOverlay(UIElementType elementType) {
		showOverlay(elementType, new Properties());
	}

	/**
	 * Remove the displayed overlay from the view and hide it in the background.
	 */
	@FXML
	public void resetOverlay() {
		overlayPane.toBack();
		overlayPane.getChildren().clear();
	}

}
