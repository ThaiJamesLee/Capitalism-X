package de.uni.mannheim.capitalismx.ui.tutorial.page;

import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.tutorial.Highlighter;
import de.uni.mannheim.capitalismx.ui.tutorial.chapter.TutorialChapter;
import de.uni.mannheim.capitalismx.ui.tutorial.page.TutorialPage.NextPageCondition;
import javafx.scene.Node;

public class TutorialPageFactory {

	private TutorialPage page;
	private Node targetNode;
	private ResourceBundle bundle = ResourceBundle.getBundle("properties.tutorial", UIManager.getResourceBundle().getLocale());
	private Highlighter highlighter = new Highlighter();
	private NextPageCondition nextPageCondition;
	
	public TutorialPage createTutorialPage(TutorialChapter owningChapter, String messageKey) {
		TutorialPage page = new TutorialPage(owningChapter, bundle.getString(messageKey));
		return page;
	}
	
	public void setTargetNode(Node targetNode) {
		this.targetNode = targetNode;
	}
	
	public void setNextPageCondition(NextPageCondition nextPageCondition) {
		this.nextPageCondition = nextPageCondition;
	}
	
}
