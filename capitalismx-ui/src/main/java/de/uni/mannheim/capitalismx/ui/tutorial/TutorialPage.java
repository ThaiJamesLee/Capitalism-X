package de.uni.mannheim.capitalismx.ui.tutorial;

import java.io.IOException;

import org.controlsfx.control.PopOver;
import org.controlsfx.control.PopOver.ArrowLocation;

import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.tutorial.chapter.TutorialChapter;
import de.uni.mannheim.capitalismx.ui.utils.PopOverFactory;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class TutorialPage {

	private Highlighter highlighter;
	private TutorialChapter owningPath;
	private PopOver infoPopover;
	private Node target;
	private EventHandler<ActionEvent> onCompletedHandler;
	
	@FXML
	private Text tutorialMessage;

	private TutorialPage(TutorialChapter owningPath, Node target) {
		this.target = target;
		this.owningPath = owningPath;
		highlighter = new Highlighter();
		onCompletedHandler = e -> {
			//TODO 
		};
	}
	
	public TutorialPage(TutorialChapter owningPath, Node target, PopOver popOver) {
		this(owningPath, target);
		infoPopover = popOver;
		initPopover();
	}

	public TutorialPage(TutorialChapter owningPath, Node target, String message) {
		this(owningPath, target);
		FXMLLoader loader = new FXMLLoader(PopOverFactory.class.getClassLoader().getResource("fxml/tutorial_pane.fxml"),
				UIManager.getResourceBundle());
		Parent root;
		try {
			loader.setController(this);
			root = loader.load();
			tutorialMessage.setText(message);
			infoPopover = new PopOver(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
		initPopover();
	}

	private void initPopover() {
		infoPopover.setFadeInDuration(Duration.millis(300));
		infoPopover.setFadeOutDuration(Duration.millis(300));
		infoPopover.setArrowLocation(ArrowLocation.TOP_LEFT);
	}

	/**
	 * Sets an EventHandler that is called after this {@link TutorialPage} is
	 * completed.
	 * 
	 * @param handler
	 */
	public void setOnCompleted(EventHandler<ActionEvent> handler) {
		onCompletedHandler = handler;
	}

	public void start() {
		Platform.runLater(() -> {
			highlighter.highlight(target);
			infoPopover.show(target);
			target.setOnMouseReleased(e -> {
				highlighter.removeHighlight(target);
				infoPopover.hide();
				onCompletedHandler.handle(new ActionEvent());
				owningPath.nextPage();
			});
		});
	}

}
