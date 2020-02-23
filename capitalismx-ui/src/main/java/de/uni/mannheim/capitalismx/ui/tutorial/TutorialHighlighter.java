package de.uni.mannheim.capitalismx.ui.tutorial;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 * Helper class for the {@link Tutorial}, that highlights a given {@link Node}.
 * 
 * @author Jonathan
 *
 */
public class TutorialHighlighter {

	private FadeTransition highlight;

	public TutorialHighlighter() {
		highlight = new FadeTransition();
		highlight.setCycleCount(FadeTransition.INDEFINITE);
		highlight.setAutoReverse(true);
		highlight.setFromValue(1.0);
		highlight.setToValue(0.8);
		highlight.setDuration(Duration.millis(1000));
	}

	/**
	 * Highlight the given {@link Node}, by giving it a blue shadow and adding a
	 * {@link FadeTransition}.
	 * 
	 * @param node The {@link Node} to highlight.
	 */
	public void highlight(Node node) {
		highlight.setNode(node);
		highlight.play();
		node.getStyleClass().add("tutorial_highlight");
	}

	/**
	 * Remove the changes made to the {@link Node} and reset it to its original
	 * state.
	 * 
	 * @param node The node to reset.
	 */
	public void removeHighlight(Node node) {
		Platform.runLater(() -> {
			highlight.stop();
			node.setOpacity(1.0);
			node.getStyleClass().remove("tutorial_highlight");
		});
	}

}
