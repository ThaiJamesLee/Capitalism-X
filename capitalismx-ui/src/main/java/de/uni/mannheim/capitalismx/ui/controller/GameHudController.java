package de.uni.mannheim.capitalismx.ui.controller;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ResourceBundle;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconName;
import de.uni.mannheim.capitalismx.domain.department.DepartmentImpl;
import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.gamecontroller.GameState;
import de.uni.mannheim.capitalismx.gamecontroller.ecoindex.CompanyEcoIndex;
import de.uni.mannheim.capitalismx.gamecontroller.ecoindex.CompanyEcoIndex.EcoIndex;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.GameNotification;
import de.uni.mannheim.capitalismx.ui.components.GameViewType;
import de.uni.mannheim.capitalismx.ui.components.general.TooltipFactory;
import de.uni.mannheim.capitalismx.ui.controller.component.DepartmentUpgradeController;
import de.uni.mannheim.capitalismx.ui.controller.general.UpdateableController;
import de.uni.mannheim.capitalismx.ui.eventlisteners.GameStateEventListener;
import de.uni.mannheim.capitalismx.ui.utils.AnchorPaneHelper;
import de.uni.mannheim.capitalismx.ui.utils.CapCoinFormatter;
import de.uni.mannheim.capitalismx.ui.utils.CssHelper;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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

	private DepartmentUpgradeController upgradeController;
	private AnchorPane departmentUpgradePane;

	/**
	 * {@link Queue} of {@link GameNotification}s, that are currently waiting to be
	 * displayed
	 */
	private Queue<GameNotification> notificationQueue = new LinkedList<GameNotification>();

	/**
	 * flag whether a notification is currently being displayed
	 */
	private boolean displayingNotification = false;
	private boolean levelUpDropdownOpen = false;

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
	private FontAwesomeIcon playPauseIconButton, forwardIconButton, skipIconButton, ecoIcon, departmentDropdownIcon;
	@FXML
	private Label playPauseIconLabel, forwardIconLabel, skipIconLabel, messageIconLabel, settingsIconLabel;

	@FXML
	private Button ecoButton;
	private Tooltip ecoTooltip;

	@FXML
	private StackPane notificationPane;

	@FXML
	private AnchorPane root;

	@FXML
	private VBox netWorthVBox, cashVBox, employeeVBox, departmentVBox;

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
	 * Colors a label either green, red or yellow, depending on the difference
	 * provided.
	 * 
	 * @param diff  The difference in the displayed value. (< 0 -> red, 0 -> yellow,
	 *              > 0 -> green)
	 * @param label The label to change the color of.
	 */
	private void colorHudLabel(double diff, Label label) {
		if (diff < 0) {
			label.getStyleClass().remove("hud_label");
			label.getStyleClass().remove("hud_label_green");
			label.getStyleClass().remove("hud_label_red");
			label.getStyleClass().add("hud_label_red");
		} else if (diff > 0) {
			label.getStyleClass().remove("hud_label");
			label.getStyleClass().remove("hud_label_green");
			label.getStyleClass().remove("hud_label_red");
			label.getStyleClass().add("hud_label_green");
		} else {
			label.getStyleClass().remove("hud_label");
			label.getStyleClass().add("hud_label");
			label.getStyleClass().remove("hud_label_green");
			label.getStyleClass().remove("hud_label_red");
		}
	}

	public void deselectDepartmentButton(GameViewType type) {
		getDepartmentButton(type).setSelected(false);
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
		// TODO set handler on root: do not remove while hover, show message on click
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

	private ToggleButton getDepartmentButton(GameViewType departmentViewType) {
		return departmentButtonMap.get(departmentViewType);
	}

	public GridPane getModuleGrid() {
		return moduleGrid;
	}

	/**
	 * Initiates the department buttons by adding the necessary EventHandlers.
	 * 
	 * @param button     The {@link ToggleButton} to initiate.
	 * @param department The {@link GameViewType} to create EventHandlers for.
	 */
	private void initDepartmentButton(ToggleButton button, GameViewType department) {
		// prepare the icon for the button
		button.setGraphic(department.getGameViewIcon("1.5em"));

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

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		CssHelper.replaceStylesheets(root.getStylesheets());

		FXMLLoader departmentUpgradeLoader = new FXMLLoader(
				getClass().getClassLoader().getResource("fxml/components/hud_department_dropdown.fxml"),
				UIManager.getResourceBundle());
		try {
			departmentUpgradePane = departmentUpgradeLoader.load();
			upgradeController = (DepartmentUpgradeController) departmentUpgradeLoader.getController();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		GameState gameState = GameState.getInstance();
		TooltipFactory tooltipFactory = new TooltipFactory();
		tooltipFactory.setFadeInDuration(Duration.millis(100));

		// Set initial captions of the info labels at the top
		cashLabel.setText(CapCoinFormatter.getCapCoins(gameState.getFinanceDepartment().getCash()));
		cashChangeLabel.setText("+" + CapCoinFormatter.getCapCoins(0));
		employeeLabel.setText(gameState.getHrDepartment().getTotalNumberOfEmployees() + "");
		netWorthChangeLabel.setText("+" + CapCoinFormatter.getCapCoins(0));
		netWorthLabel.setText(CapCoinFormatter.getCapCoins(gameState.getFinanceDepartment().getNetWorth()));
		gameState.addPropertyChangeListener(new GameStateEventListener());
		ecoTooltip = tooltipFactory.createTooltip("");
		ecoButton.setTooltip(ecoTooltip);
		updateEcoIndexIcon(gameState.getCompanyEcoIndex().getEcoIndex());

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

		playPauseIconLabel
				.setTooltip(tooltipFactory.createTooltip(UIManager.getLocalisedString("hud.tooltip.playPause")));
		skipIconLabel.setTooltip(tooltipFactory.createTooltip(UIManager.getLocalisedString("hud.tooltip.skip")));
		forwardIconLabel.setTooltip(tooltipFactory.createTooltip(UIManager.getLocalisedString("hud.tooltip.faster")));
		tooltipFactory.setAnchorLocation(AnchorLocation.WINDOW_TOP_RIGHT);
		settingsIconLabel
				.setTooltip(tooltipFactory.createTooltip(UIManager.getLocalisedString("hud.tooltip.settings")));
		messageIconLabel.setTooltip(tooltipFactory.createTooltip(UIManager.getLocalisedString("hud.tooltip.messages")));

		// Switch to view when clicking on hud info labels
		cashVBox.setOnMouseClicked(e -> {
			switchView(GameViewType.FINANCES);
		});
		netWorthVBox.setOnMouseClicked(e -> {
			switchView(GameViewType.FINANCES);
		});
		employeeVBox.setOnMouseClicked(e -> {
			switchView(GameViewType.HR);
		});

		UIManager.getInstance().setGameHudController(this);
		updateLevelUpDropdown(GameViewType.OVERVIEW);
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

	public void selectDepartmentButton(GameViewType type) {
		getDepartmentButton(type).setSelected(true);
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
		UIManager.getInstance().getGamePageController().switchView(viewType);
	}

	/**
	 * Updates all hud elements for the given {@link GameViewType}.
	 * 
	 * @param viewType The {@link GameViewType} to update for.
	 */
	protected void updateView(GameViewType viewType) {
		selectDepartmentButton(viewType);
		updateGameViewLabel(viewType);
		updateLevelUpDropdown(viewType);

		if (levelUpDropdownOpen)
			toggleLevelUpDropdown();
	}

	/**
	 * Update the department level-up dropdown for the given {@link GameViewType}.
	 * 
	 * @param viewType {@link GameViewType} being displayed.
	 */
	private void updateLevelUpDropdown(GameViewType viewType) {
		if (!viewType.isUpgradeable()) {
			departmentDropdownIcon.setOnMouseClicked(e -> {
			});
			departmentDropdownIcon.getStyleClass().remove("hud_icon_button");
		} else {
			DepartmentImpl dep;
			switch (viewType) {
			case HR:
				dep = GameState.getInstance().getHrDepartment();
				break;
			case R_AND_D:
				dep = GameState.getInstance().getResearchAndDevelopmentDepartment();
				break;
			case WAREHOUSE:
				dep = GameState.getInstance().getWarehousingDepartment();
				break;
			case PRODUCTION:
				dep = GameState.getInstance().getProductionDepartment();
				break;
			default:
				departmentDropdownIcon.getStyleClass().remove("hud_icon_button");
				return;
			}
			upgradeController.setDepartment(dep);
			
			if(!departmentDropdownIcon.getStyleClass().contains("hud_icon_button")) {
				departmentDropdownIcon.getStyleClass().add("hud_icon_button");
			}
			departmentDropdownIcon.setOnMouseClicked(e -> {
				toggleLevelUpDropdown();
			});
		}
	}

	/**
	 * Toggles the dropdown menu for the department level up.
	 */
	private void toggleLevelUpDropdown() {
		if (levelUpDropdownOpen) {
			departmentDropdownIcon.setIcon(FontAwesomeIconName.ANGLE_DOWN);
			departmentVBox.getChildren().remove(departmentUpgradePane);
			levelUpDropdownOpen = false;
		} else {
			departmentDropdownIcon.setIcon(FontAwesomeIconName.ANGLE_UP);
			departmentVBox.getChildren().add(1, departmentUpgradePane);
			levelUpDropdownOpen = true;
		}
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
		// called every ingame day
		Platform.runLater(() -> {
			// update date
			GameState gameState = GameState.getInstance();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd")
					.withLocale(UIManager.getResourceBundle().getLocale());
			dateLabel.setText(gameState.getGameDate().format(formatter));

			// update employee change label
			int diff = GameState.getInstance().getHrDepartment()
					.getEmployeeDifference(GameState.getInstance().getGameDate());
			colorHudLabel(diff, employeeChangeLabel);
			String diffText = ((diff >= 0) ? "+" : "") + diff;
			employeeChangeLabel.setText(diffText);
		});
	}

	public void updateCashLabel(double currentCash) {
		Platform.runLater(new Runnable() {
			public void run() {
				cashLabel.setText(CapCoinFormatter.getCapCoins(currentCash));
			}
		});
	}

	public void updateCashChangeLabel(Double diff) {
		Platform.runLater(new Runnable() {
			public void run() {
				if (diff != null) {
					colorHudLabel(diff, cashChangeLabel);
					cashChangeLabel.setText(((diff >= 0) ? "+" : "") + CapCoinFormatter.getCapCoins(diff));
				} else {
					cashChangeLabel.setText("+0 CC");
				}
			}
		});
	}

	/**
	 * This method updates the icon displaying the company's {@link EcoIndex}.
	 * 
	 * @param index The {@link EcoIndex} of the current {@link CompanyEcoIndex}.
	 */
	public void updateEcoIndexIcon(EcoIndex index) {
		// TODO Create and use actual localised name
		ecoTooltip.setText(index.name());
		switch (index) {
		case GOOD:
			ecoIcon.getStyleClass().clear();
			ecoIcon.getStyleClass().add("icon_green");
			break;
		case MODERATE:
			ecoIcon.getStyleClass().clear();
			ecoIcon.getStyleClass().add("icon_light");
			break;
		case UNHEALTHY:
			ecoIcon.getStyleClass().clear();
			ecoIcon.getStyleClass().add("icon_orange");
			break;
		case VERY_UNHEALTHY:
			ecoIcon.getStyleClass().clear();
			ecoIcon.getStyleClass().add("icon_red");
			break;
		case HAZARDOUS:
			ecoIcon.getStyleClass().clear();
			ecoIcon.setStyle("-fx-background-color: -fx-red;");
			break;
		default:
			break;
		}
		;
	}

	/**
	 * Updates the {@link Label} displaying the currently active
	 * {@link GameViewType}.
	 * 
	 * @param viewType The {@link GameViewType}, thats title should be displayed.
	 */
	public void updateGameViewLabel(GameViewType viewType) {
		this.departmentLabel.setText(viewType.getTitle());
		this.departmentLabel.setGraphic(viewType.getGameViewIcon("1.8em"));
	}

	public void updateNetworthLabel(double currentNetWorth) {
		Platform.runLater(new Runnable() {
			public void run() {
				netWorthLabel.setText(CapCoinFormatter.getCapCoins(currentNetWorth));
			}
		});
	}

	public void updateNetworthChangeLabel(Double diff) {
		Platform.runLater(new Runnable() {
			public void run() {
				if (diff != null) {
					colorHudLabel(diff, netWorthChangeLabel);
					netWorthChangeLabel.setText(((diff >= 0) ? "+" : "") + CapCoinFormatter.getCapCoins(diff));
				}
			}
		});
	}

	public void updateNumOfEmployees() {
		Platform.runLater(new Runnable() {
			public void run() {
				int numOfEmployees = GameState.getInstance().getHrDepartment().getTotalNumberOfEmployees();
				int capacity = GameState.getInstance().getHrDepartment().getTotalEmployeeCapacity();
				employeeLabel.setText(numOfEmployees + "/" + capacity);
			}
		});
	}

}
