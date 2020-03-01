package de.uni.mannheim.capitalismx.ui.tutorial.chapter;

import java.util.List;

import de.uni.mannheim.capitalismx.production.product.Product;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.component.GameModuleType;
import de.uni.mannheim.capitalismx.ui.controller.module.production.ProduceProductController;
import de.uni.mannheim.capitalismx.ui.tutorial.page.TutorialPage.NextPageTrigger;
import de.uni.mannheim.capitalismx.ui.tutorial.page.TutorialPage.TutorialPageBuilder;
import javafx.scene.Node;
import javafx.scene.control.TextField;

/**
 * A {@link TutorialChapter}, that explains how the player can introduce new
 * {@link Product}s.
 * 
 * @author Jonathan
 *
 */
public class IntroduceProductChapter extends TutorialChapter {

	public IntroduceProductChapter() {

		ProduceProductController con = (ProduceProductController) UIManager.getInstance()
				.getModule(GameModuleType.PRODUCTION_PRODUCE_PRODUCT).getController();

		List<Node> nodes = con.getTutorialNodes();
		pages.add(new TutorialPageBuilder(this)
				.setTargetNode(UIManager.getInstance().getGameHudController().getProductionDepButton())
				.setMessageKey("chapter.product.introduce.department").setEndTrigger(NextPageTrigger.CLICK).build());
		// TODO explain other product-tabs on top

		pages.add(new TutorialPageBuilder(this).setTargetNode(nodes.get(0))
				.setMessageKey("chapter.product.introduce.new").setEndTrigger(NextPageTrigger.CLICK).build());

		pages.add(new TutorialPageBuilder(this).setTargetNode(nodes.get(1))
				.setMessageKey("chapter.product.introduce.components").build());

		pages.add(new TutorialPageBuilder(this).setTargetNode(nodes.get(2))
				.setMessageKey("chapter.product.introduce.name").setEndTrigger(NextPageTrigger.PROPERTY_CONDITION)
				.setPropertyToCheck(((TextField) nodes.get(2)).textProperty()).setPropertyValue("CommunismX").build());

		pages.add(new TutorialPageBuilder(this).setTargetNode(nodes.get(3))
				.setMessageKey("chapter.product.introduce.price").setEndTrigger(NextPageTrigger.PROPERTY_CONDITION)
				.setPropertyToCheck(((TextField) nodes.get(3)).textProperty()).setPropertyValue("500").build());

		pages.add(new TutorialPageBuilder(this).setTargetNode(nodes.get(4))
				.setMessageKey("chapter.product.introduce.launch").setEndTrigger(NextPageTrigger.CLICK).build());

	}
}
