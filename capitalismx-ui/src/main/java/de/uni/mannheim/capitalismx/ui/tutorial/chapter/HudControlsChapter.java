package de.uni.mannheim.capitalismx.ui.tutorial.chapter;

import java.util.List;

import org.controlsfx.control.PopOver.ArrowLocation;

import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.tutorial.page.TutorialPage.NextPageCondition;
import de.uni.mannheim.capitalismx.ui.tutorial.page.TutorialPage.TutorialPageBuilder;
import javafx.scene.Node;

/**
 * Tutorial chapter which introduces the Time Control Elements of the GameHud
 * (top left corner)
 * 
 * @author Alex
 *
 */
public class HudControlsChapter extends TutorialChapter {

	// TODO do we want to keep all of this in a separate chapter of tie the
	// explanations of the hud-elements better into the gameplay by including them
	// in other chapters, once they are needed/actually displays useful information
	public HudControlsChapter() {
		List<Node> nodes = UIManager.getInstance().getGameHudController().getTimeControlTutorialNodes();

		pages.add(new TutorialPageBuilder(this).setTargetNode(nodes.get(0)).setMessageKey("chapter.controls.hud.welcome").build());
		pages.add(new TutorialPageBuilder(this).setTargetNode(nodes.get(1)).setMessageKey("chapter.controls.hud.date").build());
		pages.add(new TutorialPageBuilder(this).setTargetNode(nodes.get(2)).setMessageKey("chapter.controls.hud.pause").setCondition(NextPageCondition.CLICK).build());
		pages.add(new TutorialPageBuilder(this).setTargetNode(nodes.get(3)).setMessageKey("chapter.controls.hud.networth").setArrowLocation(ArrowLocation.BOTTOM_CENTER).setCondition(NextPageCondition.CONFIRM).build());
		pages.add(new TutorialPageBuilder(this).setTargetNode(nodes.get(4)).setMessageKey("chapter.controls.hud.cash").setArrowLocation(ArrowLocation.BOTTOM_CENTER).build());
		pages.add(new TutorialPageBuilder(this).setTargetNode(nodes.get(5)).setMessageKey("chapter.controls.hud.employees").setArrowLocation(ArrowLocation.BOTTOM_CENTER).build());
		pages.add(new TutorialPageBuilder(this).setTargetNode(nodes.get(6)).setMessageKey("chapter.controls.hud.eco").setArrowLocation(ArrowLocation.BOTTOM_RIGHT).build());
		pages.add(new TutorialPageBuilder(this).setTargetNode(nodes.get(7)).setMessageKey("chapter.controls.hud.speed").setCondition(NextPageCondition.CLICK).build());
		pages.add(new TutorialPageBuilder(this).setTargetNode(nodes.get(8)).setMessageKey("chapter.controls.hud.skip").setCondition(NextPageCondition.CLICK).build());		
		pages.add(new TutorialPageBuilder(this).setTargetNode(nodes.get(9)).setMessageKey("chapter.controls.hud.messages").setArrowLocation(ArrowLocation.TOP_RIGHT).build());
		pages.add(new TutorialPageBuilder(this).setTargetNode(nodes.get(10)).setMessageKey("chapter.controls.hud.settings").build());
	}
	
}
