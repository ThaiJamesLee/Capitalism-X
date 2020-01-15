package de.uni.mannheim.capitalismx.ui.tutorial;

import org.controlsfx.control.PopOver;

import javafx.scene.Node;
import javafx.scene.control.Label;

public class TutorialPage {

	private Highlighter highlighter;
	private TutorialChapter owningPath;
	private PopOver infoPopover;
	private Node target;
	

	public TutorialPage(TutorialChapter owningPath, Node target, String message) {
		this(owningPath, target);
		infoPopover = new PopOver();
		infoPopover.setContentNode(new Label(message));
	}

	public TutorialPage(TutorialChapter owningPath, Node target, PopOver popOver) {
		this(owningPath, target);
		infoPopover = popOver;
	}

	private TutorialPage(TutorialChapter owningPath, Node target) {
		this.target = target;
		this.owningPath = owningPath;
		highlighter = new Highlighter();
	}

	public void start() {
		highlighter.highlight(target);
		infoPopover.show(target);
		target.setOnMouseReleased(e -> {
			highlighter.removeHighlight(target);
			owningPath.nextStep();
		});
	}

}
