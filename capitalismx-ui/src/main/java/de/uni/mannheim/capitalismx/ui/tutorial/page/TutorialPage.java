package de.uni.mannheim.capitalismx.ui.tutorial.page;

import java.io.IOException;

import org.controlsfx.control.PopOver;
import org.controlsfx.control.PopOver.ArrowLocation;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconName;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.tutorial.TutorialHighlighter;
import de.uni.mannheim.capitalismx.ui.tutorial.Tutorial;
import de.uni.mannheim.capitalismx.ui.tutorial.chapter.TutorialChapter;
import de.uni.mannheim.capitalismx.ui.util.PopOverFactory;
import javafx.application.Platform;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * A {@link TutorialPage} is the smallest tutorial entity. It is a single step
 * of a {@link TutorialChapter} and highlights a given {@link Node}, while
 * displaying a message to the player, explaining how something works of what he
 * needs to do.
 * 
 * @author Jonathan
 *
 */
public class TutorialPage {

	/**
	 * Defines the trigger that allows for the TutorialPage to finish and continue
	 * with the next page.
	 * 
	 * <li>{@link #CONFIRM}</li>
	 * <li>{@link #CLICK}</li>
	 * <li>{@link #PROPERTY_CONDITION}</li>
	 * 
	 * @author Jonathan
	 */
	public enum NextPageTrigger {

		/**
		 * Confirm via a button next to the message.
		 */
		CONFIRM,
		/**
		 * Click on the {@link Node}, the message is attached to.
		 */
		CLICK,
		/**
		 * Set a {@link Property}, that needs to equal a certain Value. Once the
		 * {@link Property} is changed and has that value, the tutorial continues.
		 */
		PROPERTY_CONDITION;
	}

	/**
	 * Implementation of a Builder-Pattern for {@link TutorialPage}s.
	 * 
	 * @author Jonathan
	 *
	 */
	public static class TutorialPageBuilder {

		// required attributes
		private TutorialChapter parentChapter;
		private NextPageTrigger endTrigger;

		// optional attributes
		private ArrowLocation arrowLocation;
		private Node highlightedNode;
		private String messageKey;
		private PopOver messagePopover;
		private Property<?> propertyToCheck;
		private Object expectedPropertyValue;

		/**
		 * New builder for {@link TutorialPage}s.
		 * 
		 * @param parentChapter The parent {@link TutorialChapter} of this page.
		 */
		public TutorialPageBuilder(TutorialChapter parentChapter) {
			this.parentChapter = parentChapter;
			this.endTrigger = NextPageTrigger.CONFIRM;
			this.arrowLocation = ArrowLocation.TOP_LEFT;
		}

		/**
		 * Build a new {@link TutorialPage} from this builder.
		 * 
		 * @return New configured {@link TutorialPage}.
		 */
		public TutorialPage build() {
			return new TutorialPage(this);
		}

		/**
		 * Set the {@link NextPageTrigger} of the page. Default is CONFIRM.
		 * 
		 * @param endTrigger The {@link NextPageTrigger} of the page.
		 */
		public TutorialPageBuilder setEndTrigger(NextPageTrigger trigger) {
			this.endTrigger = trigger;
			return this;
		}

		/**
		 * Set the Node that will be highlighted for this page.
		 * 
		 * @param toHighlight The {@link Node} to highlight.
		 */
		public TutorialPageBuilder setTargetNode(Node toHighlight) {
			highlightedNode = toHighlight;
			return this;
		}

		/**
		 * Set the key for getting the message from the properties.
		 * 
		 * @param messageKey The Key to get the localized message-String.
		 */
		public TutorialPageBuilder setMessageKey(String messageKey) {
			this.messageKey = messageKey;
			return this;
		}

		/**
		 * Set a custom {@link PopOver} for this {@link TutorialPage}.
		 * 
		 * @param messagePopover Custom {@link PopOver}.
		 */
		public TutorialPageBuilder setMessagePopover(PopOver messagePopover) {
			this.messagePopover = messagePopover;
			return this;
		}

		/**
		 * Set the {@link Property} that should be checked. (See
		 * {@link NextPageTrigger})
		 * 
		 * @param propertyToCheck The {@link Property} that should be checked.
		 */
		public TutorialPageBuilder setPropertyToCheck(Property<?> propertyToCheck) {
			this.propertyToCheck = propertyToCheck;
			return this;
		}

		/**
		 * Set the value the {@link Property} should adopt. (See
		 * {@link NextPageTrigger})
		 * 
		 * @param propertyValue The value to check for.
		 */
		public TutorialPageBuilder setPropertyValue(Object propertyValue) {
			this.expectedPropertyValue = propertyValue;
			return this;
		}

		/**
		 * Set the location of the arrow on the {@link PopOver}.
		 * 
		 * @param arrowLocation {@link ArrowLocation} of the {@link PopOver}.
		 */
		public TutorialPageBuilder setArrowLocation(ArrowLocation arrowLocation) {
			this.arrowLocation = arrowLocation;
			return this;
		}
	}

	private EventHandler<? super MouseEvent> overwrittenHandler;
	private TutorialHighlighter highlighter;
	private TutorialChapter parentChapter;
	private NextPageTrigger endTrigger;
	private PopOver infoPopover;
	private Node target;
	private ArrowLocation arrowLocation;
	private Property<?> propertyToCheck;
	private Object expectedPropertyValue;

	@FXML
	private Text tutorialMessage;

	@FXML
	private VBox vBox;

	/**
	 * Constructor that creates a new {@link TutorialPage}, given a
	 * {@link TutorialPageBuilder}.
	 * 
	 * @param builder The configured {@link TutorialPageBuilder} to create a new
	 *                Page from.
	 */
	private TutorialPage(TutorialPageBuilder builder) {
		this.parentChapter = builder.parentChapter;
		this.arrowLocation = builder.arrowLocation;

		if (builder.messagePopover != null) {
			this.infoPopover = builder.messagePopover;
		} else {
			createPopOver(builder.highlightedNode, builder.messageKey);
			initPopover();
		}

		if (builder.highlightedNode != null) {
			this.target = builder.highlightedNode;
			highlighter = new TutorialHighlighter();
		}

		this.endTrigger = builder.endTrigger;
		if (this.endTrigger == NextPageTrigger.PROPERTY_CONDITION) {
			if (builder.propertyToCheck != null && builder.expectedPropertyValue != null) {
				this.expectedPropertyValue = builder.expectedPropertyValue;
				this.propertyToCheck = builder.propertyToCheck;
			} else {
				System.err.println(
						"Could not set NextPageTrigger.PROPERTY_CONDITION, as propertyToCheck or propertyValue were not set.");
				this.endTrigger = NextPageTrigger.CONFIRM;
			}
		}
	}

	/**
	 * Add a {@link Button} for confirming this message.
	 * ({@link NextPageTrigger}.CONFIRM)
	 */
	private void addConfirmButton() {
		FontAwesomeIcon icon = new FontAwesomeIcon();
		icon.setIcon(FontAwesomeIconName.CHECK);
		icon.getStyleClass().add("icon_light");
		Button confirm = new Button("", icon);
		confirm.getStyleClass().add("btn_standard");
		confirm.setOnAction(e -> {
			infoPopover.setOnHidden(null);
			turnPage(null);
		});
		vBox.getChildren().add(confirm);
	}

	/**
	 * Load the {@link PopOver} from the tutorial template and add the localized
	 * message.
	 * 
	 * @param target     The {@link Node} to attach the {@link PopOver} to. Can be
	 *                   null to create an overlay.
	 * @param messageKey The key to get the localized message from the
	 *                   tutorial-properties.
	 */
	private void createPopOver(Node target, String messageKey) {
		FXMLLoader loader = new FXMLLoader(PopOverFactory.class.getClassLoader().getResource("fxml/tutorial_pane.fxml"),
				UIManager.getResourceBundle());
		Parent root;
		try {
			loader.setController(this);
			root = loader.load();
			tutorialMessage.setText(Tutorial.getBundle().getString(messageKey));
			infoPopover = new PopOver(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Configure some standard values for the {@link PopOver}.
	 */
	private void initPopover() {
		infoPopover.setFadeInDuration(Duration.millis(300));
		// setting the fadeout higher may result in nullpointers when closing an
		// underlying popover
		infoPopover.setFadeOutDuration(Duration.millis(0));
		infoPopover.setArrowLocation(arrowLocation);
	}

	/**
	 * Set the {@link ChangeListener} on the {@link Property}, to turn the page when
	 * it equals the expected result.
	 * 
	 * @param p              The {@link Property} to check.
	 * @param expectedResult The value the {@link Property} needs to equal to
	 *                       continue.
	 */
	private void setPropertyCondition(Property<?> p, Object expectedResult) {
		p.addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
				if (newValue.equals(expectedResult)) {
					turnPage(null);
					p.removeListener(this);
				}
			}
		});
	}

	/**
	 * Display the {@link PopOver} and optionally highlight a {@link Node}.
	 */
	private void showPage() {
		highlighter.highlight(target);
		if (target != null) {
			infoPopover.show(target);
		} else {
			infoPopover.show(UIManager.getInstance().getStage());
		}
	}

	/**
	 * Start this {@link TutorialPage}, by displaying the {@link PopOver} and
	 * preparing the next page depending on the {@link NextPageTrigger}.
	 */
	public void start() {
		Platform.runLater(() -> {
			showPage();
			switch (endTrigger) {
			case CONFIRM:
				addConfirmButton();
				infoPopover.setOnHidden(e -> {
					turnPage(null);
				});
				break;
			case CLICK:
				overwrittenHandler = target.getOnMouseClicked();
				target.setOnMouseClicked(e -> {
					turnPage(e);
				});
				break;
			case PROPERTY_CONDITION:
				setPropertyCondition(propertyToCheck, expectedPropertyValue);
				break;
			default:
				break;
			}
		});
	}

	/**
	 * Finish this {@link TutorialPage} and continue with the parent
	 * {@link TutorialChapter}.
	 * 
	 * @param m Optional {@link MouseEvent}, that triggered the turning of the page.
	 *          Is used to reset the setOnMouseClicked-Listener of the highlighted
	 *          {@link Node}. (null if not needed.)
	 */
	private void turnPage(MouseEvent m) {
		infoPopover.hide();
		highlighter.removeHighlight(target);
		parentChapter.nextPage();
		if (m != null && overwrittenHandler != null) {
			target.setOnMouseClicked(overwrittenHandler);
			overwrittenHandler.handle(m);
		}
	}

}
