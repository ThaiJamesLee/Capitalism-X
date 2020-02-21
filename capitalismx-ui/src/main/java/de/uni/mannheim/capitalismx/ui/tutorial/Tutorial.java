package de.uni.mannheim.capitalismx.ui.tutorial;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.uni.mannheim.capitalismx.ui.tutorial.chapter.HudControlsChapter;
import de.uni.mannheim.capitalismx.ui.tutorial.chapter.IntroduceProductChapter;
import de.uni.mannheim.capitalismx.ui.tutorial.chapter.TutorialChapter;

/**
 * The {@link Tutorial} of the game. Contains multiple {@link TutorialChapter}s
 * (each of them explaining one topic/mechanic), that will be started
 * sequentially.
 * 
 * @author Jonathan
 *
 */
public class Tutorial {

	private List<TutorialChapter> chapters = new ArrayList<TutorialChapter>();
	private Iterator<TutorialChapter> currentChapter;

	public Tutorial() {
		chapters.add(new HudControlsChapter());
		chapters.add(new IntroduceProductChapter());
		
		currentChapter = chapters.iterator();
	}
	
	public void nextChapter() {
		if (currentChapter.hasNext()) {
			currentChapter.next().beginChapter();
		}
	}

}
