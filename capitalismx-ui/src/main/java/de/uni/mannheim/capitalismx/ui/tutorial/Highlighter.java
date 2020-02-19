package de.uni.mannheim.capitalismx.ui.tutorial;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.util.Duration;

public class Highlighter {

	private FadeTransition highlight;

	public Highlighter() {
		highlight = new FadeTransition();
		highlight.setCycleCount(FadeTransition.INDEFINITE);
		highlight.setAutoReverse(true);
		highlight.setFromValue(1.0);
		highlight.setToValue(0.8);
		highlight.setDuration(Duration.millis(1000));
	}

	public void highlight(Node node) {
		highlight.setNode(node);
		highlight.play();
		node.getStyleClass().add("tutorial_highlight");
	}

	// TODO optional keep eventlistener and add again afterwards
	public void removeHighlight(Node node) {
		Platform.runLater(() -> {
			highlight.stop();
			node.setOpacity(1.0);
			node.getStyleClass().remove("tutorial_highlight");
		});
	}

}
