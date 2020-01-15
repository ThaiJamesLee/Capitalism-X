package de.uni.mannheim.capitalismx.ui.tutorial.chapter;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.GameViewType;
import de.uni.mannheim.capitalismx.ui.components.UIElementType;
import de.uni.mannheim.capitalismx.ui.controller.module.production.IntroduceProductController;
import de.uni.mannheim.capitalismx.ui.tutorial.TutorialPage;
import javafx.animation.PauseTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class IntroduceProductChapter implements TutorialChapter {

	private List<TutorialPage> pages;
	private Iterator<TutorialPage> currentPage;

	@Override
	public void prepareAndStart() {
		pages = new LinkedList<TutorialPage>();
		UIManager manager = UIManager.getInstance();
		List<Node> nodes = ((IntroduceProductController) manager.getGameView(GameViewType.PRODUCTION)
				.getModule(UIElementType.PRODUCTION_NEW_PRODUCT_OVERVIEW).getController()).getTutorialNodes();
		pages.add(new TutorialPage(this, manager.getGameHudController().getProductionDepButton(),
				"Switch to your ProductionDepartment!"));
		pages.add(new TutorialPage(this, nodes.get(0),
				"Here you can choose the components for the Product, that you want to launch."));
		pages.add(new TutorialPage(this, nodes.get(1), "Try to think of a unique name for your product."));
		pages.add(new TutorialPage(this, nodes.get(2),
				"Set a sensible price for your product. You should take the prices of the single components into account."));
		pages.add(new TutorialPage(this, nodes.get(3), "Perfect! Now you can launch your very first own product."));

		currentPage = pages.iterator();
		nextPage();
	}

	@Override
	public void nextPage() {
		PauseTransition pause = new PauseTransition(Duration.seconds(1));
		pause.play();
		pause.setOnFinished(e -> {
			if (currentPage.hasNext())
				currentPage.next().start();
		});
	}

}
