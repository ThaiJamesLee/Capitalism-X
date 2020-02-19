package de.uni.mannheim.capitalismx.ui.controller.component;

import java.net.URL;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.domain.department.DepartmentImpl;
import de.uni.mannheim.capitalismx.gamecontroller.GameState;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.utils.CapCoinFormatter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * Controller for the dropdown in the hud that controls the level up of
 * Departments.
 * 
 * @author Jonathan
 *
 */
public class DepartmentUpgradeController implements Initializable {

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

	private void update() {
		int level = department.getLevel();
		currentLevelLabel.setText("Level: " + level);
		if (level < department.getMaxLevel()) {
			if (!upgradeVBox.getChildren().contains(upgradeGrid)) {
				upgradeVBox.getChildren().add(upgradeGrid);
			}
			nextLevelDescriptionLabel.setText(department.getSkillMap().get(level + 1).getDescription(UIManager.getResourceBundle().getLocale()));
			nextLevelLabel.setText(UIManager.getLocalisedString("hud.dropdown.next.benefits").replace("XXX", (level+1) + ""));
			levelUpButton.setText(UIManager.getLocalisedString("hud.dropdown.next.button")
					+ CapCoinFormatter.getCapCoins(department.getLevelingMechanism().getNextLevelUpCost()));
		} else {
			upgradeVBox.getChildren().remove(upgradeGrid);
		}
	}

	public void setDepartment(DepartmentImpl department) {
		this.department = department;
		update();
	}

	/**
	 * Level the department up.
	 */
	@FXML
	private void levelUp() {
		Double cost = department.getLevelingMechanism().levelUp();
		GameState.getInstance().getFinanceDepartment().decreaseCash(GameState.getInstance().getGameDate(), cost);
		update();
	}

}
