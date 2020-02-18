package de.uni.mannheim.capitalismx.ui.tutorial.chapter;

import java.util.List;

import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.GameViewType;
import de.uni.mannheim.capitalismx.ui.components.UIElementType;
import de.uni.mannheim.capitalismx.ui.controller.module.production.ProduceProductController;
import de.uni.mannheim.capitalismx.ui.tutorial.TutorialPage;
import javafx.scene.Node;

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
				.getGameView(GameViewType.PRODUCTION).getModule(UIElementType.PRODUCTION_PRODUCE_PRODUCT)
				.getController();

		List<Node> nodes = con.getTutorialNodes();
		pages.add(new TutorialPage(this, UIManager.getInstance().getGameHudController().getProductionDepButton(),
				"Navigate to your ProductionDepartment!"));
		pages.add(new TutorialPage(this, nodes.get(0),
				"Press this Button to open the Overlay with the options for your newq Prodcut."));

		pages.add(new TutorialPage(this, nodes.get(1),
				"Here you can choose the components for \nthe Product, that you want to launch, \nas well as the quality of their supplier."));
		pages.add(new TutorialPage(this, nodes.get(2), "Try to think of a unique name for your product."));
		pages.add(new TutorialPage(this, nodes.get(3),
				"Set a sensible price for your product. \nYou should take the prices of the single components into account."));
		pages.add(new TutorialPage(this, nodes.get(4), "Perfect! \nNow you can launch your very first own product."));

	}

}
