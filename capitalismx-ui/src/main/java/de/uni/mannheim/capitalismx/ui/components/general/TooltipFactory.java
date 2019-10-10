package de.uni.mannheim.capitalismx.ui.components.general;

import org.controlsfx.control.PopOver;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
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
	 * The {@link Duration} it takes until the tooltip shows up. (Default 0.5)
	 */
	private Duration fadeInDuration = Duration.seconds(0.1);

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
		defaultLabel.setPadding(new Insets(5.0));
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

		// add listeners to the parent node
		owner.setOnMouseEntered(e -> {
			tooltip.show(owner, arrowOffset);
		});
		owner.setOnMouseExited(e -> {
			tooltip.hide();
		});
	}

}
