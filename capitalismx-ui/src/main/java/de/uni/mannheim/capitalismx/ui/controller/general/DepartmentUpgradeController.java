package de.uni.mannheim.capitalismx.ui.controller.general;

import java.net.URL;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.domain.department.DepartmentImpl;
import de.uni.mannheim.capitalismx.domain.exception.LevelingRequirementNotFulFilledException;
import de.uni.mannheim.capitalismx.gamecontroller.GameState;
import de.uni.mannheim.capitalismx.hr.department.HRDepartment;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.component.GameModuleType;
import de.uni.mannheim.capitalismx.ui.component.general.GameAlert;
import de.uni.mannheim.capitalismx.ui.controller.module.hr.RecruitingListController;
import de.uni.mannheim.capitalismx.ui.util.CapCoinFormatter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller for the dropdown in the hud that controls the level up of
 * Departments.
 * 
 * @author Jonathan
 *
 */
public class DepartmentUpgradeController implements Initializable {

	private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentUpgradeController.class);

	@FXML
	private Label currentLevelLabel, nextLevelDescriptionLabel, nextLevelLabel;

	@FXML
	private GridPane upgradeGrid;

	@FXML
	private Button levelUpButton;

	@FXML
	private VBox upgradeVBox;

	private DepartmentImpl department;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	/**
	 * Level the department up.
	 */
	@FXML
	private void levelUp() {
		Double cost = null;
		try {
			cost = department.getLevelingMechanism().getNextLevelUpCost();
			if (GameState.getInstance().getFinanceDepartment().getCash() < cost) {
				GameAlert alert = new GameAlert(AlertType.WARNING,
						UIManager.getLocalisedString("alert.general.cash.title"),
						UIManager.getLocalisedString("alert.general.cash.description"));
				alert.showAndWait();
				return;
			}

			department.getLevelingMechanism().levelUp();
			GameState.getInstance().getFinanceDepartment().decreaseCash(GameState.getInstance().getGameDate(), cost);
			update();
			if (department instanceof HRDepartment) {
				((RecruitingListController) UIManager.getInstance().getModule(GameModuleType.HR_RECRUITING_OVERVIEW)
						.getController()).regenerateRecruitingProspects();
			}
		} catch (LevelingRequirementNotFulFilledException e) {
			GameAlert error = new GameAlert(Alert.AlertType.WARNING, "Level up was not possible.", e.getMessage());
			error.showAndWait();
			LOGGER.error(e.getMessage(), e);
		}
	}

	public void setDepartment(DepartmentImpl department) {
		this.department = department;
		update();
	}

	/**
	 * 
	 */
	private void update() {
		int level = department.getLevel();
		currentLevelLabel.setText("Level: " + level);
		if (level < department.getMaxLevel()) {
			if (!upgradeVBox.getChildren().contains(upgradeGrid)) {
				upgradeVBox.getChildren().add(upgradeGrid);
			}
			nextLevelDescriptionLabel.setText(
					department.getSkillMap().get(level + 1).getDescription(UIManager.getResourceBundle().getLocale()));
			nextLevelLabel.setText(
					UIManager.getLocalisedString("hud.dropdown.next.benefits").replace("XXX", (level + 1) + ""));
			levelUpButton.setText(UIManager.getLocalisedString("hud.dropdown.next.button")
					+ CapCoinFormatter.getCapCoins(department.getLevelingMechanism().getNextLevelUpCost()));
		} else {
			upgradeVBox.getChildren().remove(upgradeGrid);
		}
	}

}
