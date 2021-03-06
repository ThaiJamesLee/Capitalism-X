package de.uni.mannheim.capitalismx.ui.controller.gamepage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.component.GameModule;
import de.uni.mannheim.capitalismx.ui.component.GameView;
import de.uni.mannheim.capitalismx.ui.component.GameViewType;
import de.uni.mannheim.capitalismx.ui.controller.general.UpdateableController;
import de.uni.mannheim.capitalismx.ui.controller.message.MessageController;
import de.uni.mannheim.capitalismx.ui.util.AnchorPaneHelper;
import de.uni.mannheim.capitalismx.ui.util.CssHelper;
import de.uni.mannheim.capitalismx.ui.util.GridPosition;
import de.uni.mannheim.capitalismx.utils.data.MessageObject;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/**
 * The {@link UpdateableController} managing all actions on the GamePage.
 * 
 * @author Jonathan
 *
 */
public class GamePageController implements Initializable {

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
	private AnchorPane menuLayer, mapLayer;

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

	private IngameMenuController ingameMenuController;
	// flag to know whether menu Pane is open or not: true=open false=closed.
	private boolean openMenuPane = false;

	public StackPane getContentStack() {
		return contentStack;
	}

	public MessageController getMessageController() {
		return messageController;
	}
	
	public IngameMenuController getIngameMenuController() {
		return ingameMenuController;
	}

	public boolean isMapControlsEnabled() {
		return mapControlsEnabled;
	}

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

	/**
	 * Handle escape key -> If any menu elements are open, close them. If not open
	 * ingame menu.
	 */
	public void handleEscapeInput() {
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
			FXMLLoader loader3DMap = new FXMLLoader(
					getClass().getClassLoader().getResource("fxml/overview_map3d.fxml"));
			mapLayer.getChildren().add(loader3DMap.load());
			UIManager.getInstance().setGameMapController(loader3DMap.getController());
			mapLayer.toBack();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {
			FXMLLoader loaderMessageWindow = new FXMLLoader(
					getClass().getClassLoader().getResource("fxml/message/message_pane.fxml"),
					UIManager.getResourceBundle());
			Parent rootMessage = loaderMessageWindow.load();

			messageController = loaderMessageWindow.getController();
			GridPane grid = new GridPane();
			AnchorPaneHelper.snapNodeToAnchorPane(grid);
			grid.setAlignment(Pos.CENTER);
			grid.getChildren().add(rootMessage);
			messageLayer.getChildren().add(grid);
			messageLayer.toBack();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {
			FXMLLoader loaderIngameMenu = new FXMLLoader(
					getClass().getClassLoader().getResource("fxml/ingame_menu.fxml"), UIManager.getResourceBundle());
			Parent root = loaderIngameMenu.load();
			ingameMenuController = loaderIngameMenu.getController();
			CssHelper.replaceStylesheets(root.getStylesheets());
			AnchorPaneHelper.snapNodeToAnchorPane(root);
			menuLayer.getChildren().add(root);
			menuLayer.toBack();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		moduleGrid = UIManager.getInstance().getGameHudController().getModuleGrid();
		messageLayer.setOnMouseClicked(e -> {
			toggleMessageWindow();
		});
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
	 * Switches the displayed contentType by removing all {@link GameModule}s of
	 * that type.
	 * 
	 * @param viewType The {@link GameViewType} of the {@link GameView} to switch
	 *                 to.
	 */
	public void switchView(GameViewType viewType) {
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

	/**
	 * Shows/Hides the ingame menu, depending on whether it is currently being
	 * displayed.
	 */
	public void toggleIngameMenu() {
		if (!openMenuPane) {
			menuLayer.toFront();
			openMenuPane = true;
		} else {
			menuLayer.toBack();
			openMenuPane = false;
		}
	}

	/**
	 * Shows/Hides the message window, depending on whether it is currently being
	 * displayed.
	 */
	public void toggleMessageWindow() {
		if (!openMessagePane) {
			messageLayer.toFront();
			openMessagePane = true;
		} else {
			messageLayer.toBack();
			openMessagePane = false;
		}

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
