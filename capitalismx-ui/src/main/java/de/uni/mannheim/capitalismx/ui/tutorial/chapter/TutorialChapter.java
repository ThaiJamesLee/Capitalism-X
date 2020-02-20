package de.uni.mannheim.capitalismx.ui.tutorial.chapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.tutorial.page.TutorialPage;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.util.Duration;

/**
 * A {@link TutorialChapter} is a collection of {@link TutorialPage}s. The
 * chapter has one specific topic/mechanic that it covers and that should be
 * explained step by step using its pages. A chapter must override the
 * constructor to initiate its own pages.
 * 
 * @author Jonathan
 *
 */
public class TutorialChapter {

	protected List<TutorialPage> pages = new ArrayList<TutorialPage>();
	protected Iterator<TutorialPage> currentPage;

	private final int PAUSE_BETWEEN_STEPS = 1200;

	/**
	 * Can be called to begin with the first {@link TutorialPage} of this chapter.
	 */
	public void beginChapter() {
		currentPage = pages.iterator();
		nextPage();
	}

	/**
	 * Waits for a short amount of time and then starts the next
	 * {@link TutorialPage} of this chapter.
	 */
	public void nextPage() {
		Platform.runLater(() -> {
			PauseTransition pause = new PauseTransition(Duration.millis(PAUSE_BETWEEN_STEPS));
			pause.play();
			pause.setOnFinished(e -> {
				if (currentPage.hasNext()) {
					currentPage.next().start();
				} else {
					UIManager.getInstance().getTutorial().nextChapter();
				}
			});
		});
	}

}
