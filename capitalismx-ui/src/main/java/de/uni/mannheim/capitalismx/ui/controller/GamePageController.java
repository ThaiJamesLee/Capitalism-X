package de.uni.mannheim.capitalismx.ui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.GameModule;
import de.uni.mannheim.capitalismx.ui.components.GameOverlay;
import de.uni.mannheim.capitalismx.ui.components.GameView;
import de.uni.mannheim.capitalismx.ui.components.GameViewType;
import de.uni.mannheim.capitalismx.ui.components.UIElementType;
import de.uni.mannheim.capitalismx.ui.controller.general.UpdateableController;
import de.uni.mannheim.capitalismx.ui.utils.AnchorPaneHelper;
import de.uni.mannheim.capitalismx.ui.utils.CssHelper;
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

	public StackPane getContentStack() {
		return contentStack;
	}

	public boolean isMapControlsEnabled() {
		return mapControlsEnabled;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		CssHelper.replaceStylesheets(contentStack.getStylesheets());
		
		// Bind titleLabel to StringProperty in SideMenuController
//		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/sidemenu.fxml"));
//		Parent rootB;
		try {
//			rootB = loader.load();
			// TODO remove or adjust if necessary
//			SideMenuController controllerB = loader.getController();
//			viewTitleLabel.textProperty().unbind();
//			viewTitleLabel.textProperty().bind(controllerB.titleProperty());
//			sidemenuPane.getChildren().setAll(rootB);

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
			AnchorPaneHelper.snapNodeToAnchorPaneWithPadding(rootC, 500);;
			messageController = loaderMessageWindow.getController();
			messageLayer.getChildren().add(rootC);
			messageLayer.toBack();
			messageController.addMessage("sen.event1", "01.01.1990", "sub.event1", "con.event1", true);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			FXMLLoader loaderIngameMenu = new FXMLLoader(getClass().getClassLoader().getResource("fxml/ingameMenu.fxml"), UIManager.getResourceBundle());
			Parent root = loaderIngameMenu.load();
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

	public void toggleMessageWindow() {
		if (!openMessagePane) {
			messageLayer.toFront();
			openMessagePane = true;
		} else {
			messageLayer.toBack();
			openMessagePane = false;
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

	@Override
	public void update() {
		for (GameModule m : currentActiveView.getModules()) {
			m.getController().update();
		}
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
				moduleGrid.getChildren().remove(module.getRootElement());
			}
			
			UIManager.getInstance().getGameHudController().deselectDepartmentButton(currentActiveView.getViewType());
		}
		// change current view and add modules
		UIManager.getInstance().getGameHudController().selectDepartmentButton(viewType);
		currentActiveView = UIManager.getInstance().getGameView(viewType);
		UIManager.getInstance().getGameHudController().updateGameViewLabel(viewType);
		for (GameModule module : currentActiveView.getModules()) {
			GridPosition position = module.getGridPosition();
			module.getController().update();
			moduleGrid.add(module.getRootElement(), position.getxStart(), position.getyStart(), position.getxSpan(),
					position.getySpan());
		}
		//enable map controls if in GameView is OVERVIEW
		if (viewType.equals(GameViewType.OVERVIEW)) {
			mapControlsEnabled = true;
		}
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
		overlayLayer.getChildren().add(rootElement);
		overlayLayer.toFront();
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
		overlayLayer.toBack();
		overlayLayer.getChildren().clear();
	}

	public MessageController getMessageController() {
		return messageController;
	}

}
