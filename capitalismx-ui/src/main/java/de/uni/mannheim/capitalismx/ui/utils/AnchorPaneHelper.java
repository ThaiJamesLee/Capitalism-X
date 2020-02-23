package de.uni.mannheim.capitalismx.ui.utils;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

/**
 * Helper Class that provides some useful functionalities to automatically snap
 * {@link Node}s to their parent {@link AnchorPane}, without having to set the
 * distance for each anchor individually.
 * 
 * @author Jonathan
 *
 */
public class AnchorPaneHelper {

	/**
	 * Sets all anchors of the given {@link Node} which is a child of an
	 * {@link AnchorPane} to 0.
	 * 
	 * @param node The {@link Node} to snap to the {@link AnchorPane}.
	 */
	public static void snapNodeToAnchorPane(Node node) {
		AnchorPane.setBottomAnchor(node, 0.0);
		AnchorPane.setRightAnchor(node, 0.0);
		AnchorPane.setTopAnchor(node, 0.0);
		AnchorPane.setLeftAnchor(node, 0.0);
	}

	/**
	 * Sets all anchors of the given {@link Node} which is a child of an
	 * {@link AnchorPane} to the given distance.
	 * 
	 * @param node The {@link Node} to snap to the {@link AnchorPane}.
	 */
	public static void snapNodeToAnchorPane(Node node, double distance) {
		AnchorPane.setBottomAnchor(node, distance);
		AnchorPane.setRightAnchor(node, distance);
		AnchorPane.setTopAnchor(node, distance);
		AnchorPane.setLeftAnchor(node, distance);
	}

	/**
	 * Sets all anchors (except the bottom one) of the given {@link Node} which is a child of an
	 * {@link AnchorPane} to 0.
	 * 
	 * @param node The {@link Node} to snap to the {@link AnchorPane}.
	 */
	public static void snapNodeToAnchorPaneNoBottom(Node node) {
		AnchorPane.setRightAnchor(node, 0.0);
		AnchorPane.setTopAnchor(node, 0.0);
		AnchorPane.setLeftAnchor(node, 0.0);
	}

}
