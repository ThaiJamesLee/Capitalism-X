package de.uni.mannheim.capitalismx.ui.tutorial.chapter;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.GameViewType;
import de.uni.mannheim.capitalismx.ui.components.UIElementType;
import de.uni.mannheim.capitalismx.ui.controller.module.production.IntroduceProductController;
import de.uni.mannheim.capitalismx.ui.controller.module.production.ProduceProductController;
import de.uni.mannheim.capitalismx.ui.tutorial.TutorialPage;
import javafx.animation.PauseTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class IntroduceProductChapter implements TutorialChapter {

	private List<TutorialPage> pages;
	private Iterator<TutorialPage> currentPage;
	
	private final int PAUSE_BETWEEN_STEPS = 1500;

	@Override
	public void prepareAndStart() {
		pages = new LinkedList<TutorialPage>();
		UIManager manager = UIManager.getInstance();
		
		//TODO fix and adjust to popover for launching products
//		ProduceProductController -> IntroduceProductController
//		 		nodes.add(introduceProductButton); Button introduce Product  -> Overlay appears
//	   Popover: nodes.add(tutorialPane); --> choose components
//				nodes.add(tvProductNameTextField); --> 
//				nodes.add(tvSalesPriceTextField);
//				nodes.add(launchTvButton);
		
		ProduceProductController con = (ProduceProductController) manager.getGameView(GameViewType.PRODUCTION)
				.getModule(UIElementType.PRODUCTION_PRODUCE_PRODUCT).getController();
		
		List<Node> nodes = con.getTutorialNodes();
		pages.add(new TutorialPage(this, manager.getGameHudController().getProductionDepButton(),
				"Navigate to your ProductionDepartment!"));
		pages.add(new TutorialPage(this, nodes.get(0), "Press this Button to open the Overlay with the options for your newq Prodcut."));
		
		pages.add(new TutorialPage(this, nodes.get(1),
				"Here you can choose the components for \nthe Product, that you want to launch, \nas well as the quality of their supplier."));
		pages.add(new TutorialPage(this, nodes.get(2), "Try to think of a unique name for your product."));
		pages.add(new TutorialPage(this, nodes.get(3),
				"Set a sensible price for your product. \nYou should take the prices of the single components into account."));
		pages.add(new TutorialPage(this, nodes.get(4), "Perfect! \nNow you can launch your very first own product."));

		currentPage = pages.iterator();
		nextPage();
	}

	@Override
	public void nextPage() {
		PauseTransition pause = new PauseTransition(Duration.millis(PAUSE_BETWEEN_STEPS));
		pause.play();
		pause.setOnFinished(e -> {
			if (currentPage.hasNext())
				currentPage.next().start();
		});
	}

}
