package de.uni.mannheim.capitalismx.ui.tutorial.page;

import java.io.IOException;
import java.util.ResourceBundle;

import org.controlsfx.control.PopOver;
import org.controlsfx.control.PopOver.ArrowLocation;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconName;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.tutorial.Highlighter;
import de.uni.mannheim.capitalismx.ui.tutorial.chapter.TutorialChapter;
import de.uni.mannheim.capitalismx.ui.utils.PopOverFactory;
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

	public enum NextPageCondition {
		CONFIRM, CLICK, PROPERTY_EQUALS;
	}

	private EventHandler<? super MouseEvent> overwrittenHandler;
	private boolean propertyCheckSet;
	private Highlighter highlighter;
	private TutorialChapter owningChapter;
	private NextPageCondition condition;
	private PopOver infoPopover;
	private Node target;
	private ResourceBundle bundle = ResourceBundle.getBundle("properties.tutorial",
			UIManager.getResourceBundle().getLocale());
	@FXML
	private Text tutorialMessage;

	@FXML
	private VBox vBox;

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

	private void addButton() {
		FontAwesomeIcon icon = new FontAwesomeIcon();
		icon.setIcon(FontAwesomeIconName.CHECK);
		icon.getStyleClass().add("icon_light");
		Button confirm = new Button("", icon);
		confirm.getStyleClass().add("btn_standard");
		confirm.setOnAction(e -> {
			turnPage(null);
		});
		vBox.getChildren().add(confirm);
	}

	/**
	 * 
	 */
	private void initPopover() {
		infoPopover.setFadeInDuration(Duration.millis(300));
		infoPopover.setFadeOutDuration(Duration.millis(0));
		infoPopover.setArrowLocation(ArrowLocation.TOP_LEFT);
	}

	public void setNextPageCondition(Property<?> p, Object expectedResult) {
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

	public void setTargetNode(Node targetNode) {
		this.target = targetNode;
	}

	private void showPage() {
		highlighter.highlight(target);
		infoPopover.show(target);
	}

	public void start() {
		if (target != null) {
			Platform.runLater(() -> {
				showPage();
				switch (condition) {
				case CONFIRM:
					addButton();
					break;
				case CLICK:
					overwrittenHandler = target.getOnMouseClicked();
					target.setOnMouseClicked(e -> {
						turnPage(e);
					});
					break;
				case PROPERTY_EQUALS:
					if (!propertyCheckSet)
						System.err.println("Could not continue Tutorial, as Trigger was not correctly set.");
					break;
				default:
					break;
				}
			});
		} else {
			owningChapter.restart();
		}

	}

	private void turnPage(MouseEvent m) {
		infoPopover.hide();
		highlighter.removeHighlight(target);
		owningChapter.nextPage();
		if (m != null && overwrittenHandler != null) {
			target.setOnMouseClicked(overwrittenHandler);
			overwrittenHandler.handle(m);
		}
	}

}
