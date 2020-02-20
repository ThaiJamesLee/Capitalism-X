package de.uni.mannheim.capitalismx.ui.tutorial.chapter;

import java.util.List;

import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.GameModuleType;
import de.uni.mannheim.capitalismx.ui.components.GameViewType;
import de.uni.mannheim.capitalismx.ui.controller.module.production.ProduceProductController;
import de.uni.mannheim.capitalismx.ui.tutorial.page.TutorialPage;
import javafx.scene.Node;
import javafx.scene.control.TextField;

public class IntroduceProductChapter extends TutorialChapter {

	public IntroduceProductChapter() {

		// TODO fix and adjust to popover for launching products
//		ProduceProductController -> IntroduceProductController
//		 		nodes.add(introduceProductButton); Button introduce Product  -> Overlay appears
//	   Popover: nodes.add(tutorialPane); --> choose components
//				nodes.add(tvProductNameTextField); --> 
//				nodes.add(tvSalesPriceTextField);
//				nodes.add(launchTvButton);

		ProduceProductController con = (ProduceProductController) UIManager.getInstance()
				.getGameView(GameViewType.PRODUCTION).getModule(GameModuleType.PRODUCTION_PRODUCE_PRODUCT)
				.getController();

		List<Node> nodes = con.getTutorialNodes();
		pages.add(new TutorialPage(this, UIManager.getInstance().getGameHudController().getProductionDepButton(),
				"Navigate to your ProductionDepartment!", TutorialPage.NextPageCondition.TARGET_CLICK));
		pages.add(new TutorialPage(this, nodes.get(0), "Press this Button to introduce a new Product.",
				TutorialPage.NextPageCondition.TARGET_CLICK));

		pages.add(new TutorialPage(this, nodes.get(1),
				"Here you can choose the components for \nthe Product, that you want to launch, \nas well as the quality of their supplier.",
				TutorialPage.NextPageCondition.CONFIRM));
		TutorialPage pageName = new TutorialPage(this, nodes.get(2), "Try to think of a unique name for your product.",
				TutorialPage.NextPageCondition.PROPERTY_EQUALS);
		pageName.setNextPageCondition(((TextField) nodes.get(2)).textProperty(), "CommunismX");
		pages.add(pageName);
		TutorialPage pagePrice = new TutorialPage(this, nodes.get(3),
				"Set a sensible price for your product. \nYou should take the prices of the single components into account.",
				TutorialPage.NextPageCondition.PROPERTY_EQUALS);
		pagePrice.setNextPageCondition(((TextField) nodes.get(3)).textProperty(), "500");

		pages.add(pagePrice);
		pages.add(new TutorialPage(this, nodes.get(4), "Perfect! \nNow you can launch your very first own product.", TutorialPage.NextPageCondition.TARGET_CLICK));

	}

}
