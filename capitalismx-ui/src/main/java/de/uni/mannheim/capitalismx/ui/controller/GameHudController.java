package de.uni.mannheim.capitalismx.ui.controller;

import java.net.URL;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ResourceBundle;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconName;
import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.gamecontroller.GameState;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.GameNotification;
import de.uni.mannheim.capitalismx.ui.components.GameViewType;
import de.uni.mannheim.capitalismx.ui.components.general.TooltipFactory;
import de.uni.mannheim.capitalismx.ui.controller.general.UpdateableController;
import de.uni.mannheim.capitalismx.ui.eventlisteners.GameStateEventListener;
import de.uni.mannheim.capitalismx.ui.utils.AnchorPaneHelper;
import de.uni.mannheim.capitalismx.ui.utils.CssHelper;
import de.uni.mannheim.capitalismx.ui.utils.MessageObject;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.PopupWindow.AnchorLocation;
import javafx.util.Duration;

/**
 * Controller for the hud on the GamePage. The hud consists of various elements
 * on the borders of the Screen, that provide the user with useful information
 * and functionalities.
 * 
 * @author Jonathan
 *
 */
public class GameHudController implements UpdateableController {

	/**
	 * {@link Queue} of {@link GameNotification}s, that are currently waiting to be
	 * displayed
	 */
	private Queue<GameNotification> notificationQueue = new LinkedList<GameNotification>();

	/**
	 * flag whether a notification is currently being displayed
	 */
	private boolean displayingNotification = false;

	private HashMap<GameViewType, ToggleButton> departmentButtonMap;

	@FXML
	private Label departmentLabel, cashLabel, cashChangeLabel, employeeLabel, employeeChangeLabel, netWorthLabel,
			netWorthChangeLabel, dateLabel;

	@FXML
	private ToggleButton btnOverview, btnFinance, btnHr, btnSales, btnProduction, btnLogistics, btnWarehouse, btnRAndD;
	@FXML
	private ToggleGroup departmentButtons;
	// The GridPane that contains all the modules.
	@FXML
	private GridPane moduleGrid;

	@FXML
	private FontAwesomeIcon playPauseIconButton, forwardIconButton, skipIconButton;
	@FXML
	private Label playPauseIconLabel, forwardIconLabel, skipIconLabel, messageIconLabel, settingsIconLabel;

	@FXML
	private StackPane notificationPane;

	@FXML
	private AnchorPane root;

	

	/**
	 * Display a {@link GameNotification} on the GamePage, if another one is
	 * currently being displayed, it will be added to a queue and displayed
	 * afterwards. Notification will be displayed 4.5 seconds.
	 * 
	 * @param notification The {@link GameNotification} to display.
	 */
	public void addNotification(GameNotification notification) {
		notificationQueue.add(notification);
		displayNextNotification();
	}

	/**
	 * Display the notification by sliding it in from below.
	 * 
	 * @param notification
	 */
	private void displayNextNotification() {
		if (displayingNotification)
			return;
		// block display of other notifications
		displayingNotification = true;
		GameNotification notification = notificationQueue.poll();
		Parent root = notification.getRoot();
		//TODO set handler on root: do not remove while hover, show message on click
		AnchorPaneHelper.snapNodeToAnchorPane(root);
		root.setTranslateY(200);
		notificationPane.getChildren().add(root);
		// Slide the notification in
		KeyValue endOfSlide = new KeyValue(root.translateYProperty(), 0.0);
		Timeline slideIn = new Timeline(new KeyFrame(Duration.millis(500), endOfSlide),
				new KeyFrame(notification.getDisplayDuration(), e -> {
					removeNotification(notification);
				}));
		slideIn.setCycleCount(1);
		slideIn.play();
	}

	public GridPane getModuleGrid() {
		return moduleGrid;
	}

	/**
	 * Initiates the department buttons by adding the necessary EventHandlers.
	 * 
	 * @param button     The {@link ToggleButton} to inititate.
	 * @param department The {@link GameViewType} to create EventHandlers for.
	 */
	private void initDepartmentButton(ToggleButton button, GameViewType department) {
		departmentButtonMap.put(department, button);
		button.setOnAction(e -> {
			switchView(department);
		});
		button.setOnMouseEntered(e -> {
			button.setText(department.getTitle());
		});
		button.setOnMouseExited(e -> {
			button.setText("");
		});
	}

	public void selectDepartmentButton(GameViewType type) {
		getDepartmentButton(type).setSelected(true);
	}

	public void deselectDepartmentButton(GameViewType type) {
		getDepartmentButton(type).setSelected(false);
	}

	private ToggleButton getDepartmentButton(GameViewType departmentViewType) {
		return departmentButtonMap.get(departmentViewType);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		CssHelper.replaceStylesheets(root.getStylesheets());
		// Set initial captions of the info labels at the top
		GameState gameState = GameState.getInstance();
		cashLabel.setText(NumberFormat.getIntegerInstance().format(gameState.getFinanceDepartment().getCash()));
		employeeLabel.setText(gameState.getHrDepartment().getTotalNumberOfEmployees() + "");
		netWorthLabel.setText(NumberFormat.getIntegerInstance().format(gameState.getFinanceDepartment().getNetWorth()));
		GameState.getInstance().addPropertyChangeListener(new GameStateEventListener());

		// TODO Tooltip on the changelabels, with period described by the label

		// Set the actions for the buttons that switch the views of the departments.
		departmentButtonMap = new HashMap<GameViewType, ToggleButton>();
		initDepartmentButton(btnOverview, GameViewType.OVERVIEW);
		initDepartmentButton(btnFinance, GameViewType.FINANCES);
		initDepartmentButton(btnHr, GameViewType.HR);
		initDepartmentButton(btnSales, GameViewType.SALES);
		initDepartmentButton(btnProduction, GameViewType.PRODUCTION);
		initDepartmentButton(btnWarehouse, GameViewType.WAREHOUSE);
		initDepartmentButton(btnLogistics, GameViewType.LOGISTIC);
		initDepartmentButton(btnRAndD, GameViewType.R_AND_D);

		TooltipFactory tooltipFactory = new TooltipFactory();
		tooltipFactory.setFadeInDuration(Duration.millis(100));

		playPauseIconLabel
				.setTooltip(tooltipFactory.createTooltip(UIManager.getLocalisedString("hud.tooltip.playPause")));
		skipIconLabel.setTooltip(tooltipFactory.createTooltip(UIManager.getLocalisedString("hud.tooltip.skip")));
		forwardIconLabel.setTooltip(tooltipFactory.createTooltip(UIManager.getLocalisedString("hud.tooltip.faster")));
		tooltipFactory.setAnchorLocation(AnchorLocation.WINDOW_TOP_RIGHT);
		settingsIconLabel
				.setTooltip(tooltipFactory.createTooltip(UIManager.getLocalisedString("hud.tooltip.settings")));
		messageIconLabel.setTooltip(tooltipFactory.createTooltip(UIManager.getLocalisedString("hud.tooltip.messages")));

		UIManager.getInstance().setGameHudController(this);
	}

	/**
	 * Checks whether the game is currently playing or paused, changes the state to
	 * the other one and updates the hud accordingly.
	 */
	@FXML
	public void playPause() {
		GameController controller = GameController.getInstance();
		if (controller.isGamePaused()) {
			controller.resumeGame();
			playPauseIconButton.setIcon(FontAwesomeIconName.PAUSE);
		} else {
			controller.pauseGame();
			playPauseIconButton.setIcon(FontAwesomeIconName.PLAY);
		}
	}

	/**
	 * Remove the notification with an animation and display the next one waiting in
	 * the queue.
	 * 
	 * @param notification The {@link GameNotification} to remove.
	 */
	private void removeNotification(GameNotification notification) {
		KeyValue keyDisappear = new KeyValue(notification.getRoot().opacityProperty(), 0.0);
		KeyValue keyMoveOut = new KeyValue(notification.getRoot().translateYProperty(), -200);

		Timeline moveOut = new Timeline(new KeyFrame(Duration.millis(500), e -> {
			notificationPane.getChildren().remove(notification.getRoot());
		}, keyDisappear), new KeyFrame(Duration.millis(500), keyMoveOut), new KeyFrame(Duration.millis(100), e -> {
			displayingNotification = false;
			if (!notificationQueue.isEmpty()) {
				displayNextNotification();
			}
		}));

		moveOut.setCycleCount(1);
		moveOut.play();
	}

	@FXML
	public void skipDay() {
		GameController.getInstance().nextDay();
	}

	/**
	 * Switches to the given type of view, by calling the method in the
	 * {@link GamePageController}.
	 * 
	 * @param viewType The {@link GameViewType} to display on the GamePage.
	 */
	private void switchView(GameViewType viewType) {
		addNotification(new GameNotification(new MessageObject(viewType.getTitle(), "2019-01-05",
				"Welcome in the department",
				"Hello boss. Please let me show you the great possibilities this department offers you! It is vital for the company and there is no way, the company would survive shutting it down or outsourcing it, so I hope you will keep that in mind. Thank you! Sincerely, some guy from whatever dep this is.",
				true)));
		UIManager.getInstance().getGamePageController().switchView(viewType);
	}

	@FXML
	private void togglePressReleaseWindow() {

	}

	@FXML
	private void toggleMenu() {
		GameController.getInstance().pauseGame();
		UIManager.getInstance().getGamePageController().toggleIngameMenu();
	}

	@FXML
	private void toggleMessageWindow() {
		UIManager.getInstance().getGamePageController().toggleMessageWindow();
	}

	@Override
	public void update() {
		Platform.runLater(() -> {
			// update date
			GameState gameState = GameState.getInstance();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd")
					.withLocale(UIManager.getResourceBundle().getLocale());
			dateLabel.setText(gameState.getGameDate().format(formatter));
		});
	}

	public void updateCashLabel(double currentCash) {
		Platform.runLater(new Runnable() {
			public void run() {
				cashLabel.setText(NumberFormat.getIntegerInstance().format(currentCash));
			}
		});
	}

	/**
	 * Updates the {@link Label} displaying the currently active
	 * {@link GameViewType}.
	 * 
	 * @param viewType The {@link GameViewType}, thats title should be displayed.
	 */
	public void updateGameViewLabel(GameViewType viewType) {
		this.departmentLabel.setText(viewType.getTitle());
	}

	public void updateNetworthLabel(double currentNetWorth) {
		Platform.runLater(new Runnable() {
			public void run() {
				netWorthLabel.setText(NumberFormat.getIntegerInstance().format(currentNetWorth));
			}
		});
	}

	public void updateNumOfEmployees() {
		employeeLabel.setText(GameState.getInstance().getHrDepartment().getTotalNumberOfEmployees() + "");
	}

}
