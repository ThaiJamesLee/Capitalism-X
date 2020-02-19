package de.uni.mannheim.capitalismx.ui.tutorial.chapter;

import java.util.List;

import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.tutorial.TutorialPage;
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

		pages.add(new TutorialPage(this, nodes.get(0), "chapter.controls.hud.welcome"));
		pages.add(new TutorialPage(this, nodes.get(1), "chapter.controls.hud.date"));
		pages.add(new TutorialPage(this, nodes.get(2), "chapter.controls.hud.pause"));
		pages.add(new TutorialPage(this, nodes.get(3), "chapter.controls.hud.networth"));
		pages.add(new TutorialPage(this, nodes.get(4), "chapter.controls.hud.cash")); // TODO what last actions?
		pages.add(new TutorialPage(this, nodes.get(5), "chapter.controls.hud.employees"));
		pages.add(new TutorialPage(this, nodes.get(6), "chapter.controls.hud.eco"));
		pages.add(new TutorialPage(this, nodes.get(7), "chapter.controls.hud.speed"));
		pages.add(new TutorialPage(this, nodes.get(8), "chapter.controls.hud.skip"));
		pages.add(new TutorialPage(this, nodes.get(9), "chapter.controls.hud.messages"));
		pages.add(new TutorialPage(this, nodes.get(10), "chapter.controls.hud.settings"));
	}

}
