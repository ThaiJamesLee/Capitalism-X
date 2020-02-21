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
				.getModule(GameModuleType.PRODUCTION_PRODUCE_PRODUCT)
				.getController();

		List<Node> nodes = con.getTutorialNodes();
		pages.add(new TutorialPage(this, UIManager.getInstance().getGameHudController().getProductionDepButton(),
				"chapter.product.introduce.department", TutorialPage.NextPageCondition.CLICK));
		pages.add(new TutorialPage(this, nodes.get(0), "chapter.product.introduce.new",
				TutorialPage.NextPageCondition.CLICK));

		pages.add(new TutorialPage(this, nodes.get(1), "chapter.product.introduce.components",
				TutorialPage.NextPageCondition.CONFIRM));
		TutorialPage pageName = new TutorialPage(this, nodes.get(2), "chapter.product.introduce.name",
				TutorialPage.NextPageCondition.PROPERTY_EQUALS);
		pageName.setNextPageCondition(((TextField) nodes.get(2)).textProperty(), "CommunismX");
		pages.add(pageName);
		TutorialPage pagePrice = new TutorialPage(this, nodes.get(3), "chapter.product.introduce.price",
				TutorialPage.NextPageCondition.PROPERTY_EQUALS);
		pagePrice.setNextPageCondition(((TextField) nodes.get(3)).textProperty(), "500");

		pages.add(pagePrice);
		pages.add(new TutorialPage(this, nodes.get(4), "chapter.product.introduce.launch",
				TutorialPage.NextPageCondition.CLICK));

	}

}
