package de.uni.mannheim.capitalismx.ui.utils;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconName;
import de.uni.mannheim.capitalismx.domain.employee.Employee;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.util.Duration;

/**
 * Helper class that can create useful gtaphics for the game.
 * 
 * @author Jonathan
 *
 */
public class GraphicHelper {

	/**
	 * Create a Graphic representing the skill level of an {@link Employee}. The
	 * skill can be expressed as 0-5 stars and the exact value is accessible as a
	 * {@link Tooltip} on the graphic.
	 * 
	 * @param skillLevel The level of skill, the {@link Employee} has.
	 * @return Node containing the graphic.
	 */
	public static Node createSkillGraphic(int skillLevel) {
		GridPane starGrid = createStarGrid();

		for (int i = 0; i < 5; i++) {
			FontAwesomeIcon icon = new FontAwesomeIcon();
			icon.setStyle("-fx-text-fill:-fx-primary; -fx-fill:-fx-primary");
			if (skillLevel <= i * 20) {
				icon.setIcon(FontAwesomeIconName.STAR_ALT);
			} else if (skillLevel <= i * 20 + 10) {
				icon.setIcon(FontAwesomeIconName.STAR_HALF_FULL);
			} else {
				icon.setIcon(FontAwesomeIconName.STAR);
			}
			starGrid.add(icon, i, 0);
		}

		starGrid.add(createTooltipAnchor(skillLevel), 0, 0, 5, 1);
		AnchorPaneHelper.snapNodeToAnchorPane(starGrid);

		return starGrid;
	}

	/**
	 * Create the {@link AnchorPane}, holding the invisible {@link Button}, that has
	 * the {@link Tooltip} with the exact skill level attached.
	 * 
	 * @param skillLevel The exakt skill level to display in the {@link Tooltip}.
	 * @return {@link AnchorPane} with button and {@link Tooltip}.
	 */
	private static AnchorPane createTooltipAnchor(int skillLevel) {
		Tooltip tooltip = new Tooltip("Skill: " + skillLevel);
		tooltip.setShowDelay(Duration.millis(150));

		Button tooltipButton = new Button();
		tooltipButton.getStyleClass().add("icon_button_invisible");
		tooltipButton.setTooltip(tooltip);

		AnchorPane buttonAnchor = new AnchorPane();
		buttonAnchor.getChildren().add(tooltipButton);
		AnchorPaneHelper.snapNodeToAnchorPane(tooltipButton);
		return buttonAnchor;
	}

	/**
	 * Create the GridPane with 5 columns and 1 row, that will hold the
	 * {@link FontAwesomeIcon}s for the stars.
	 * 
	 * @return The configured Gridane.
	 */
	private static GridPane createStarGrid() {
		GridPane starGrid = new GridPane();
		ColumnConstraints c = new ColumnConstraints();
		c.setPercentWidth(20);
		c.setHalignment(HPos.CENTER);
		for (int i = 0; i < 5; i++) {
			starGrid.getColumnConstraints().add(c);
		}
		RowConstraints r = new RowConstraints();
		r.setPercentHeight(100);
		r.setValignment(VPos.CENTER);
		starGrid.getRowConstraints().add(r);
		return starGrid;
	}

}
