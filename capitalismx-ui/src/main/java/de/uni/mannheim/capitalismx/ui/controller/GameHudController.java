package de.uni.mannheim.capitalismx.ui.controller;

import java.net.URL;
import java.text.NumberFormat;
import java.util.ResourceBundle;

import org.controlsfx.control.PopOver.ArrowLocation;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconName;
import de.uni.mannheim.capitalismx.gamelogic.GameController;
import de.uni.mannheim.capitalismx.gamelogic.GameState;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.GameViewType;
import de.uni.mannheim.capitalismx.ui.components.general.TooltipFactory;
import de.uni.mannheim.capitalismx.ui.controller.general.UpdateableController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.stage.PopupWindow.AnchorLocation;
import javafx.util.Duration;

public class GameHudController implements UpdateableController {

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
	
	public GridPane getModuleGrid() {
		return moduleGrid;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Set initial captions of the info labels at the top
		GameState gameState = GameState.getInstance();
		cashLabel.setText(NumberFormat.getIntegerInstance().format(gameState.getFinanceDepartment().getCash()));
		employeeLabel.setText(gameState.getHrDepartment().getEngineerTeam().getTeam().size() + "");
		netWorthLabel.setText(NumberFormat.getIntegerInstance().format(gameState.getFinanceDepartment().getNetWorth()));

		//TODO Tooltip on the changelabels, with period described by the label
		
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

	@Override
	public void update() {
		// TODO Auto-generated method stub

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

	@FXML
	private void toggleMessageWindow() {
		UIManager.getInstance().getGamePageController().toggleMessageWindow();
	}

	@FXML
	private void toggleMenu() {
		GameController.getInstance().pauseGame();
		UIManager.getInstance().getGamePageController().toggleIngameMenu();
	}

	private void switchView(GameViewType viewType) {
		UIManager.getInstance().getGamePageController().switchView(viewType);
	}

	public void updateGameViewLabel(GameViewType viewType) {
		this.departmentLabel.setText(viewType.getTitle());
	}
	
	@FXML 
	public void playPause() {
		GameController controller = GameController.getInstance();
		if(controller.isGamePaused()) {
			controller.resumeGame();
			playPauseIconButton.setIcon(FontAwesomeIconName.PAUSE);
		} else {
			controller.pauseGame();
			playPauseIconButton.setIcon(FontAwesomeIconName.PLAY);
		}
	}

	@FXML
	public void skipDay() {
		GameController.getInstance().nextDay();
	}

	public void updateCashLabel(double currentCash) {
		Platform.runLater(new Runnable() {
			public void run() {
				cashLabel.setText(NumberFormat.getIntegerInstance().format(currentCash));
			}
		});
	}

	public void updateNetworthLabel(double currentNetWorth) {
		Platform.runLater(new Runnable() {
			public void run() {
				netWorthLabel.setText(NumberFormat.getIntegerInstance().format(currentNetWorth));
			}
		});
	}

}
