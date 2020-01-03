package de.uni.mannheim.capitalismx.ui.utils;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconName;
import de.uni.mannheim.capitalismx.ui.components.general.TooltipFactory;
import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.util.Duration;

public class GraphicHelper {

	public static Node createSkillGraphic(int skillLevel) {
		GridPane starGrid = createStarGrid();
		for(int i = 0; i < 5; i++) {
			FontAwesomeIcon icon = new FontAwesomeIcon();
			icon.setStyle("-fx-text-fill:-fx-primary");
			if(skillLevel <= i*20) {
				icon.setIcon(FontAwesomeIconName.STAR_ALT);
			} else if (skillLevel <= i*20 + 10) {
				icon.setIcon(FontAwesomeIconName.STAR_HALF_FULL);
			} else {
				icon.setIcon(FontAwesomeIconName.STAR);
			}
			starGrid.add(icon, i, 0);
		}
		
		TooltipFactory factory = new TooltipFactory();
		factory.setFadeInDuration(Duration.millis(100));
		factory.addSimpleTooltipToNode(starGrid, "Skill: " + skillLevel); //TODO local.
		AnchorPaneHelper.snapNodeToAnchorPane(starGrid);
		
		return starGrid;
	}

	private static GridPane createStarGrid() {
		GridPane starGrid = new GridPane();
		ColumnConstraints c = new ColumnConstraints();
		c.setPercentWidth(20);
		for (int i = 0; i < 5; i++) {
			starGrid.getColumnConstraints().add(c);
		}
		RowConstraints r = new RowConstraints();
		r.setPercentHeight(100);
		starGrid.getRowConstraints().add(r);
		return starGrid;
	}

}
