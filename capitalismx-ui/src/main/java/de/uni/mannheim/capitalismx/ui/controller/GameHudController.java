package de.uni.mannheim.capitalismx.ui.controller;

import java.net.URL;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ResourceBundle;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconName;
import de.uni.mannheim.capitalismx.gamelogic.GameController;
import de.uni.mannheim.capitalismx.gamelogic.GameState;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.GameNotification;
import de.uni.mannheim.capitalismx.ui.components.GameViewType;
import de.uni.mannheim.capitalismx.ui.components.general.TooltipFactory;
import de.uni.mannheim.capitalismx.ui.controller.general.UpdateableController;
import de.uni.mannheim.capitalismx.ui.eventlisteners.GameStateEventListener;
import de.uni.mannheim.capitalismx.ui.utils.AnchorPaneHelper;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
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

	@FXML
	private Label departmentLabel, cashLabel, cashChangeLabel, employeeLabel, employeeChangeLabel, netWorthLabel,
			netWorthChangeLabel, dateLabel;

	@FXML
	private ToggleButton btnOverview, btnFinance, btnHr, btnProduction, btnLogistics, btnWarehouse, btnMarketing;
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

	/**
	 * Display the notification by sliding it in from below.
	 * 
	 * @param notification
	 */
	private void displayNotification(GameNotification notification) {
		// block display of other notifications
		displayingNotification = true;
		Parent root = notification.getRoot();
		AnchorPaneHelper.snapNodeToAnchorPane(root);
		root.setTranslateY(200);
		notificationPane.getChildren().add(root);
		// Slide the notification in
		KeyValue endOfSlide = new KeyValue(root.translateYProperty(), 0.0);
		Timeline slideIn = new Timeline(new KeyFrame(Duration.millis(500), endOfSlide),
				new KeyFrame(Duration.millis(3500), e -> {
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Set initial captions of the info labels at the top
		GameState gameState = GameState.getInstance();
		cashLabel.setText(NumberFormat.getIntegerInstance().format(gameState.getFinanceDepartment().getCash()));
		employeeLabel.setText(gameState.getHrDepartment().getTotalNumberOfEmployees() + "");
		netWorthLabel.setText(NumberFormat.getIntegerInstance().format(gameState.getFinanceDepartment().getNetWorth()));
		GameState.getInstance().addPropertyChangeListener(new GameStateEventListener());

		// TODO Tooltip on the changelabels, with period described by the label

		// Set the actions for the buttons that switch the views of the departments.
		initDepartmentButton(btnOverview, GameViewType.OVERVIEW);
		initDepartmentButton(btnFinance, GameViewType.FINANCES);
		initDepartmentButton(btnHr, GameViewType.HR);
		initDepartmentButton(btnProduction, GameViewType.PRODUCTION);
		initDepartmentButton(btnWarehouse, GameViewType.WAREHOUSE);
		initDepartmentButton(btnLogistics, GameViewType.LOGISTIC);
		initDepartmentButton(btnMarketing, GameViewType.MARKETING);

		TooltipFactory tooltipFactory = new TooltipFactory();
		tooltipFactory.setFadeInDuration(Duration.millis(100));

		playPauseIconLabel.setTooltip(tooltipFactory.createTooltip("Play/Pause"));
		skipIconLabel.setTooltip(tooltipFactory.createTooltip("Skip"));
		forwardIconLabel.setTooltip(tooltipFactory.createTooltip("Fast Forward"));
		tooltipFactory.setAnchorLocation(AnchorLocation.WINDOW_TOP_RIGHT);
		settingsIconLabel.setTooltip(tooltipFactory.createTooltip("Settings"));
		messageIconLabel.setTooltip(tooltipFactory.createTooltip("Messages"));

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
			if (!notificationQueue.isEmpty()) {
				displayNotification(notificationQueue.poll());
			} else {
				displayingNotification = false;
			}
		}));

		moveOut.setCycleCount(1);
		moveOut.play();
	}

	/**
	 * Display a {@link GameNotification} on the GamePage, if another one is
	 * currently being displayed, it will be added to a queue and displayed
	 * afterwards.
	 * 
	 * @param notification The {@link GameNotification} to display.
	 */
	public void showNotification(GameNotification notification) {
		if (displayingNotification) {
			notificationQueue.add(notification);
		} else {
			displayNotification(notification);
		}
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
		showNotification(new GameNotification(viewType.getTitle(),
				"Hello, please do not reply to this Message. If this is multiline, all is fine. If it is not, be sad a lot."));
		UIManager.getInstance().getGamePageController().switchView(viewType);
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
