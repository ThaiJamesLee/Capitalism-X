package de.uni.mannheim.capitalismx.ui.tutorial.page;

import java.io.IOException;
import java.util.ResourceBundle;

import org.controlsfx.control.PopOver;
import org.controlsfx.control.PopOver.ArrowLocation;

import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.tutorial.Highlighter;
import de.uni.mannheim.capitalismx.ui.tutorial.chapter.TutorialChapter;
import de.uni.mannheim.capitalismx.ui.utils.PopOverFactory;
import javafx.application.Platform;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
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

	private boolean propertyCheckSet;
	private Highlighter highlighter;
	private TutorialChapter owningChapter;
	private NextPageCondition condition;
	private PopOver infoPopover;
	private Node target;
	private ResourceBundle bundle = ResourceBundle.getBundle("properties.tutorial",
			UIManager.getResourceBundle().getLocale());

	public void setTargetNode(Node targetNode) {
		this.target = targetNode;
	}

	@FXML
	private Text tutorialMessage;

	private TutorialPage(TutorialChapter owningChapter, Node target, NextPageCondition condition) {
		this.target = target;
		this.condition = condition;
		this.owningChapter = owningChapter;
		highlighter = new Highlighter();
	}

	public TutorialPage(TutorialChapter owningChapter, Node target, PopOver popOver, NextPageCondition condition) {
		this(owningChapter, target, condition);
		infoPopover = popOver;
		initPopover();
	}

	// TODO boolean for confirm?
	public TutorialPage(TutorialChapter owningChapter, Node target, String messageKey, NextPageCondition condition) {
		this(owningChapter, target, condition);
		FXMLLoader loader = new FXMLLoader(PopOverFactory.class.getClassLoader().getResource("fxml/tutorial_pane.fxml"),
				UIManager.getResourceBundle());
		Parent root;
		try {
			loader.setController(this);
			root = loader.load();
			tutorialMessage.setText(bundle.getString(messageKey));
			infoPopover = new PopOver(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
		initPopover();
	}

	/**
	 * 
	 */
	private void initPopover() {
		infoPopover.setFadeInDuration(Duration.millis(300));
		infoPopover.setFadeOutDuration(Duration.millis(300));
		infoPopover.setArrowLocation(ArrowLocation.TOP_LEFT);
	}

	public void start() {
		Platform.runLater(() -> {
			highlighter.highlight(target);
			infoPopover.show(target);
			switch (condition) {
			case CONFIRM:
				// TODO create button
//				break;
			case TARGET_CLICK:
				target.setOnMouseReleased(e -> {
					turnPage();
				});
				break;
			case PROPERTY_EQUALS:
				if (!propertyCheckSet)
					System.err.println("Could not continue Tutorial, as Trigger was not correctly set.");
			default:
				break;
			}

		});
	}

	private void turnPage() {
		highlighter.removeHighlight(target);
		infoPopover.hide();
		owningChapter.nextPage();
	}

	public void setNextPageCondition(Property<?> p, Object expectedResult) {
		p.addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
				if (newValue.equals(expectedResult)) {
					turnPage();
				}
			}
		});
	}

	public enum NextPageCondition {
		CONFIRM, TARGET_CLICK, PROPERTY_EQUALS;
	}

}
