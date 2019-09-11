package de.uni.mannheim.capitalismx.ui.utils;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

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
	public static void snapNodeToAnchorPaneWithPadding(Node node, double distance) {
		AnchorPane.setBottomAnchor(node, distance);
		AnchorPane.setRightAnchor(node, distance);
		AnchorPane.setTopAnchor(node, distance);
		AnchorPane.setLeftAnchor(node, distance);
	}

}
