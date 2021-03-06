package de.uni.mannheim.capitalismx.ui.util;

import org.controlsfx.control.PopOver;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.stage.PopupWindow.AnchorLocation;
import javafx.util.Duration;

/**
 * A Factory that can create simple Tooltips with text and more complex ones
 * with a custom node as a root object. The generated Tooltips can be customized
 * via the setters of the factory.
 * 
 * @author Jonathan
 *
 */
public class TooltipFactory {

	/**
	 * Location of the tooltip's arrow.
	 */
	private PopOver.ArrowLocation locationOfArrow = PopOver.ArrowLocation.BOTTOM_LEFT;

	/**
	 * Offset, that the arrow has to the owning {@link Node}.
	 */
	private double arrowOffset = 5.0;

	/**
	 * The {@link Duration} it takes until the tooltip shows up. (Default 0.1s)
	 */
	private Duration fadeInDuration = Duration.millis(100);

	/**
	 * The {@link AnchorLocation} of the Tooltip.
	 */
	private AnchorLocation anchorLocation = AnchorLocation.CONTENT_BOTTOM_LEFT;

	public AnchorLocation getAnchorLocation() {
		return anchorLocation;
	}

	/**
	 * The {@link AnchorLocation} of the Tooltip.
	 */
	public void setAnchorLocation(AnchorLocation anchorLocation) {
		this.anchorLocation = anchorLocation;
	}

	public PopOver.ArrowLocation getLocationOfArrow() {
		return locationOfArrow;
	}

	/**
	 * {@link PopOver.ArrowLocation} of the tooltip's arrow.
	 */
	public void setLocationOfArrow(PopOver.ArrowLocation locationOfArrow) {
		this.locationOfArrow = locationOfArrow;
	}

	public double getArrowOffset() {
		return arrowOffset;
	}

	/**
	 * Offset, that the arrow has to the owning {@link Node}.
	 */
	public void setArrowOffset(double arrowOffset) {
		this.arrowOffset = arrowOffset;
	}

	public Duration getFadeInDuration() {
		return fadeInDuration;
	}

	/**
	 * The {@link Duration} it takes until the tooltip shows up. (Default 0.5)
	 */
	public void setFadeInDuration(Duration fadeInDuration) {
		this.fadeInDuration = fadeInDuration;
	}

	/**
	 * Adds a simple text-tooltip to the given {@link Node}.
	 * 
	 * @param owner         The {@link Node} to add the tooltip to.
	 * @param textToDisplay The text to display in the Tooltip.
	 */
	public void addSimpleTooltipToNode(Node owner, String textToDisplay) {
		Label defaultLabel = new Label(textToDisplay);
		defaultLabel.setPadding(new Insets(3.0));
		addComplexTooltipToNode(owner, defaultLabel);
	}

	/**
	 * Adds a more complex Tooltip to the given {@link Node}.
	 * 
	 * @param owner   The {@link Node} to add the tooltip to.
	 * @param content The {@link Node} to display as the content of the tooltip.
	 */
	public void addComplexTooltipToNode(Node owner, Node content) {
		// Configure tooltip
		PopOver tooltip = new PopOver(content);
		tooltip.setDetachable(false);
		tooltip.setFadeOutDuration(fadeInDuration);
		tooltip.setArrowLocation(locationOfArrow);
		tooltip.setFadeInDuration(fadeInDuration);
		tooltip.setAnchorLocation(anchorLocation);

		// add listeners to the parent node
		owner.setOnMouseEntered(e -> {
			tooltip.show(owner, arrowOffset);
		});
		owner.setOnMouseExited(e -> {
			tooltip.hide();
		});
	}

	/**
	 * Create a new standard JavaFX-{@link Tooltip}. (Does not support arrows or
	 * more complex nodes as content).
	 * 
	 * @param textToDisplay The text to display on the {@link Tooltip}.
	 * @return The created {@link Tooltip}.
	 */
	public Tooltip createTooltip(String textToDisplay) {
		Tooltip tooltip = new Tooltip();
		tooltip.setText(textToDisplay);
		tooltip.setShowDelay(fadeInDuration);
		tooltip.setAnchorLocation(anchorLocation);
		return tooltip;
	}

}
