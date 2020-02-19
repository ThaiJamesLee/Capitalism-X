package de.uni.mannheim.capitalismx.ui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.GameModule;
import de.uni.mannheim.capitalismx.ui.components.GameView;
import de.uni.mannheim.capitalismx.ui.components.GameViewType;
import de.uni.mannheim.capitalismx.ui.components.UIElementType;
import de.uni.mannheim.capitalismx.ui.controller.general.UpdateableController;
import de.uni.mannheim.capitalismx.ui.utils.AnchorPaneHelper;
import de.uni.mannheim.capitalismx.ui.utils.CssHelper;
import de.uni.mannheim.capitalismx.ui.utils.GridPosition;
import de.uni.mannheim.capitalismx.utils.data.MessageObject;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/**
 * The {@link UpdateableController} managing all actions on the GamePage.
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
	private AnchorPane notificationAnchor, messageLayer;

	/**
	 * Content elements
	 */
	@FXML
	private StackPane contentStack;
	// The GridPane that contains all the modules.
	private GridPane moduleGrid;
	@FXML
	private AnchorPane overlayLayer, menuLayer, mapLayer;

	/**
	 * General controller related attributes
	 */
	// The type of content that is currently being displayed.
	private GameView currentActiveView;

	private boolean mapControlsEnabled = false;

	/**
	 * elements for the message-system
	 */
	private MessageController messageController;
	// flag to know whether message Pane is open or not: true=open false=closed.
	private boolean openMessagePane = false;

	/**
	 * elements for the in-game menu
	 */
	private IngameMenuController ingameMenuController;
	// flag to know whether menu Pane is open or not: true=open false=closed.
	private boolean openMenuPane = false;

	/**
	 * Use this method to add a message to the message list and to open the message
	 * window. It will display the just added message.
	 * 
	 * @param messageToShow The MessageObject that should be added and shown.
	 */
	public void addAndShowMessage(MessageObject messageToShow) {
		messageController.addMessage(messageToShow);
		toggleMessageWindow();
		messageController.showMessage(messageToShow);
	}

	/**
	 * Adds a {@link GameModule} to the grid on the GamePage, so that it is
	 * displayed. (This will only display the module if it is activated)
	 *
	 * @param module   The {@link GameModule} to add to the grid.
	 * @param animated Whether the Module should be added with an animation.
	 */
	private void addModuleToGrid(GameModule module, boolean animated) {
		GridPosition position = module.getGridPosition();
		Parent root = module.getRootElement();

		module.getController().update();
		if (module.isActivated()) {
			moduleGrid.add(root, position.getxStart(), position.getyStart(), position.getxSpan(), position.getySpan());
		}

		if (animated) {
			// Create a transition for fading in the module
			FadeTransition fade = new FadeTransition(Duration.millis(700), root);
			fade.setFromValue(0.0);
			fade.setToValue(1.0);
			fade.setCycleCount(1);
			fade.play();
		}
	}

	public StackPane getContentStack() {
		return contentStack;
	}

	public MessageController getMessageController() {
		return messageController;
	}

	/**
	 * Handle escape key -> If any menu elements are open, close them. If not open
	 * ingame menu.
	 */
	public void handleEscapeInput() {
		// TODO add auto close of hud elements -> connect to GameHudController
		if (openMessagePane) {
			toggleMessageWindow();
		} else {
			toggleIngameMenu();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		CssHelper.replaceStylesheets(contentStack.getStylesheets());

		try {
			FXMLLoader loader2 = new FXMLLoader(
					getClass().getClassLoader().getResource("fxml/module/overview_map3d.fxml"));
			mapLayer.getChildren().add(loader2.load());
			UIManager.getInstance().setGameMapController(loader2.getController());
			mapLayer.toBack();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			FXMLLoader loaderMessageWindow = new FXMLLoader(
					getClass().getClassLoader().getResource("fxml/messagePane3.fxml"), UIManager.getResourceBundle());
			Parent rootC = loaderMessageWindow.load();
			AnchorPaneHelper.snapNodeToAnchorPane(rootC, 500);
			;
			messageController = loaderMessageWindow.getController();
			messageLayer.getChildren().add(rootC);
			messageLayer.toBack();

			MessageObject m1 = new MessageObject("sen.event1", "01.01.1990", "sub.event1", "con.event1", true, 0);
			MessageObject m2 = new MessageObject("sen.event1", "01.01.1990", "sub.event1", "con.event2", true, 5);
			messageController.addMessage(m1);
			messageController.addMessage(m2);

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			FXMLLoader loaderIngameMenu = new FXMLLoader(
					getClass().getClassLoader().getResource("fxml/ingameMenu.fxml"), UIManager.getResourceBundle());
			Parent root = loaderIngameMenu.load();
			CssHelper.replaceStylesheets(root.getStylesheets());
			AnchorPaneHelper.snapNodeToAnchorPane(root);
			ingameMenuController = loaderIngameMenu.getController();
			menuLayer.getChildren().add(root);
			menuLayer.toBack();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		moduleGrid = UIManager.getInstance().getGameHudController().getModuleGrid();
		overlayLayer.setOnMouseClicked(e -> {
			resetOverlay();
		});
		messageLayer.setOnMouseClicked(e -> {
			toggleMessageWindow();
		});
	}

	public boolean isMapControlsEnabled() {
		return mapControlsEnabled;
	}

	/**
	 * Removes a {@link GameModule} from the grid on the GamePage, if it is
	 * currently displayed.
	 *
	 * @param module The {@link GameModule} to remove.
	 */
	private void removeModuleFromGrid(GameModule module, boolean animated) {
		if (animated) {
			// Create a transition for fading out the module
			FadeTransition fade = new FadeTransition(Duration.millis(700), module.getRootElement());
			fade.setFromValue(1.0);
			fade.setToValue(0.0);
			fade.setCycleCount(1);
			fade.play();
			fade.setOnFinished(e -> {
				moduleGrid.getChildren().remove(module.getRootElement());
			});
		} else {
			moduleGrid.getChildren().remove(module.getRootElement());
		}

	}

	/**
	 * Remove the displayed overlay from the view and hide it in the background.
	 */
	@Deprecated
	@FXML
	public void resetOverlay() {
		overlayLayer.toBack();
		overlayLayer.getChildren().clear();
	}

	/**
	 * Use this method to open the message window and to display a specific message.
	 * The displayed message must already exist in the message list.
	 * 
	 * @param messageToShow The mMessageObject that should be shown.
	 */
	public void showMessage(MessageObject messageToShow) {
		toggleMessageWindow();
		messageController.showMessage(messageToShow);
	}

	/**
	 * Removes currently displayed overlay elements and displays the requested one
	 * 
	 * @param elementType The {@link UIElementType} of the {@link GameOverlay} to
	 *                    display.
	 */
	@Deprecated
	public void showOverlay(UIElementType elementType) {
		showOverlay(elementType, new Properties());
	}

	/**
	 * Removes currently displayed overlay elements and displays the requested one
	 * 
	 * @param elementType The {@link UIElementType} of the {@link GameOverlay} to
	 *                    display.
	 * @param properties  Optional properties for the overlay.
	 */
	// TODO remove methods including overlay layer
	@Deprecated
	public void showOverlay(UIElementType elementType, Properties properties) {
		resetOverlay();

		// get requested overlay and display it, if module and overlay are not null
		GameModule module = currentActiveView.getModule(elementType);
		if (module == null)
			return;
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

		if (currentActiveView != null) {
			if (currentActiveView.getViewType().equals(GameViewType.OVERVIEW)) {
				mapControlsEnabled = false;
			}
			// remove all modules of current view
			for (GameModule module : currentActiveView.getModules()) {
				removeModuleFromGrid(module, false);
			}

			UIManager.getInstance().getGameHudController().deselectDepartmentButton(currentActiveView.getViewType());
		}
		// change current view and add modules
		UIManager.getInstance().getGameHudController().updateView(viewType);
		currentActiveView = UIManager.getInstance().getGameView(viewType);
		for (GameModule module : currentActiveView.getModules()) {
			addModuleToGrid(module, false);
		}
		// enable map controls if GameView is of type OVERVIEW
		if (viewType.equals(GameViewType.OVERVIEW)) {
			mapControlsEnabled = true;
		}
	}

	public void toggleIngameMenu() {
		if (!openMenuPane) {
			menuLayer.toFront();
			openMenuPane = true;
		} else {
			menuLayer.toBack();
			openMenuPane = false;
		}
	}

	public void toggleMessageWindow() {
		if (!openMessagePane) {
			messageLayer.toFront();
			openMessagePane = true;
		} else {
			messageLayer.toBack();
			openMessagePane = false;
		}

	}

	@Override
	public void update() {
	}
	
	/**
	 * Checks whether all {@link GameModule} of the currently active
	 * {@link GameView} are present on the grid, if it is the of the given
	 * {@link GameViewType}. If the module is activated but not present, it will be
	 * added. If it is deactivated but present on the grid, it will be removed.
	 *
	 * @param viewType The {@link GameViewType} to update if it is currently
	 *                 displayed.
	 */
	public void updateDisplayOfCurrentView(GameViewType viewType) {
		// if the current view is not of the given type, do not update
		if (currentActiveView == null || currentActiveView.getViewType() != viewType)
			return;

		for (GameModule module : currentActiveView.getModules()) {
			if (module.isActivated() && !moduleGrid.getChildren().contains(module.getRootElement())) {
				addModuleToGrid(module, true);
			} else if (!module.isActivated() && moduleGrid.getChildren().contains(module.getRootElement())) {
				removeModuleFromGrid(module, true);
			}
		}
	}

}
