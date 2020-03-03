package de.uni.mannheim.capitalismx.ui.controller.module.resdev;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.finance.finance.FinanceDepartment;
import de.uni.mannheim.capitalismx.gamecontroller.GameState;
import de.uni.mannheim.capitalismx.production.product.ProductCategory;
import de.uni.mannheim.capitalismx.resdev.department.ResearchAndDevelopmentDepartment;
import de.uni.mannheim.capitalismx.resdev.skills.ProductCategorySkill;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.util.CapCoinFormatter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class CategoryUpgraderController implements Initializable {

	@FXML
	private GridPane categoryGrid;

	@FXML
	private Button btnTV;

	@FXML
	private Button btnConsole;

	@FXML
	private Button btnNotebook;

	@FXML
	private Button btnPhone;


	@FXML
	public void unlockTV() {
		ResearchAndDevelopmentDepartment researchAndDevelopmentDepartment = GameState.getInstance()
				.getResearchAndDevelopmentDepartment();
		FinanceDepartment financeDepartment = GameState.getInstance().getFinanceDepartment();
		financeDepartment.decreaseCash(GameState.getInstance().getGameDate(),
				researchAndDevelopmentDepartment.unlockProductCategory(ProductCategory.TELEVISION));
		categoryGrid.getChildren().remove(btnTV);
		categoryGrid.add(new Label("Unlocked"), 0, 1);
	}

	/**
	 * This Method unlocks the console product category.
	 */
	@FXML
	public void unlockConsole() {
		ResearchAndDevelopmentDepartment researchAndDevelopmentDepartment = GameState.getInstance()
				.getResearchAndDevelopmentDepartment();
		FinanceDepartment financeDepartment = GameState.getInstance().getFinanceDepartment();
		financeDepartment.decreaseCash(GameState.getInstance().getGameDate(),
				researchAndDevelopmentDepartment.unlockProductCategory(ProductCategory.GAME_BOY));
		categoryGrid.getChildren().remove(btnConsole);
		categoryGrid.add(new Label("Unlocked"), 1, 1);
	}

	/**
	 * This Method unlocks the notebook product category.
	 */
	@FXML
	public void unlockNotebook() {
		ResearchAndDevelopmentDepartment researchAndDevelopmentDepartment = GameState.getInstance()
				.getResearchAndDevelopmentDepartment();
		FinanceDepartment financeDepartment = GameState.getInstance().getFinanceDepartment();
		financeDepartment.decreaseCash(GameState.getInstance().getGameDate(),
				researchAndDevelopmentDepartment.unlockProductCategory(ProductCategory.NOTEBOOK));
		categoryGrid.getChildren().remove(btnNotebook);
		categoryGrid.add(new Label("Unlocked"), 2, 1);
	}

	/**
	 * This Method unlocks the phone product category.
	 */
	@FXML
	public void unlockPhone() {
		ResearchAndDevelopmentDepartment researchAndDevelopmentDepartment = GameState.getInstance()
				.getResearchAndDevelopmentDepartment();
		FinanceDepartment financeDepartment = GameState.getInstance().getFinanceDepartment();
		financeDepartment.decreaseCash(GameState.getInstance().getGameDate(),
				researchAndDevelopmentDepartment.unlockProductCategory(ProductCategory.PHONE));
		categoryGrid.getChildren().remove(btnPhone);
		categoryGrid.add(new Label("Unlocked"), 3, 1);
	}

	private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
		for (Node node : gridPane.getChildren()) {
			if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
				return node;
			}
		}
		return null;
	}

	public CategoryUpgraderController() {

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ResearchAndDevelopmentDepartment researchAndDevelopmentDepartment = GameState.getInstance()
				.getResearchAndDevelopmentDepartment();

		// Prepare the button labels
		for (ProductCategorySkill skill : researchAndDevelopmentDepartment.getAllProductCategoriesSkills()) {
			String buttonText = UIManager.getLocalisedString("resdev.unlock");
			
			switch (skill.getUnlockedProductCategory()) {
			case GAME_BOY:
				btnConsole.setText(buttonText.replace("CCC", CapCoinFormatter.getCapCoins(skill.getCost())));
				break;
			case NOTEBOOK:
				btnNotebook.setText(buttonText.replace("CCC", CapCoinFormatter.getCapCoins(skill.getCost())));
				break;
			case PHONE:
				btnPhone.setText(buttonText.replace("CCC", CapCoinFormatter.getCapCoins(skill.getCost())));
				break;
			case TELEVISION:
				btnTV.setText(buttonText.replace("CCC", CapCoinFormatter.getCapCoins(skill.getCost())));
				break;
			default:
				break;
			}
		}

		List<ProductCategorySkill> unlockedProductCategorySkills = researchAndDevelopmentDepartment
				.getUnlockedProductCategorySkills();
		for (ProductCategorySkill productCategorySkill : unlockedProductCategorySkills) {
			switch (productCategorySkill.getUnlockedProductCategory()) {
			case TELEVISION:
				categoryGrid.getChildren().remove(btnTV);
				categoryGrid.add(new Label("Unlocked"), 0, 1);
				break;
			case GAME_BOY:
				categoryGrid.getChildren().remove(btnConsole);
				categoryGrid.add(new Label("Unlocked"), 1, 1);
				break;
			case NOTEBOOK:
				categoryGrid.getChildren().remove(btnNotebook);
				categoryGrid.add(new Label("Unlocked"), 2, 1);
				break;
			case PHONE:
				categoryGrid.getChildren().remove(btnPhone);
				categoryGrid.add(new Label("Unlocked"), 3, 1);
				break;
			default:
				break;
			}
		}
	}

}
