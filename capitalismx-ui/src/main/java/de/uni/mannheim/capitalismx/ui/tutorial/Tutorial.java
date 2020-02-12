package de.uni.mannheim.capitalismx.ui.tutorial;

import de.uni.mannheim.capitalismx.ui.tutorial.chapter.IntroduceProductChapter;
import de.uni.mannheim.capitalismx.ui.tutorial.chapter.TimeControlsChapter;

public class Tutorial {
	
	
	//TODO add list of chapters and iterate over those -> how to implement sequence / Pauses as in chapter itself or EventHandler?
	//right now: all start in parallel...

	public void start() {
		//new TimeControlsChapter().prepareAndStart();
		new IntroduceProductChapter().prepareAndStart();
	}
	
	
	
	//TODO
//		order: 	1. Do you want tutorial?
//				2. Explanation of time Controls (-> GamHud Controller???)
//				3: Introduce Product chapter -> adjust for overlay -> Which Controllers???


}
