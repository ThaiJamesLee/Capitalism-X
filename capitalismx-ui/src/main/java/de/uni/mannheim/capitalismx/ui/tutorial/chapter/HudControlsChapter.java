package de.uni.mannheim.capitalismx.ui.tutorial.chapter;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.tutorial.TutorialPage;
import javafx.animation.PauseTransition;
import javafx.scene.Node;
import javafx.util.Duration;


/**
 * Tutorial chapter which introduces the Time Control Elements of the GameHud (top left corner)
 * @author Alex
 *
 */
public class TimeControlsChapter implements TutorialChapter {

	//TODO order of nodes 
	//Elements 
	//1. GamePage Title
	//Date label
	//Pause Button
	//Networth 
	//Cash  --> vBox
	//Employees --> vBox
	//Skip Btn
	//Fast Forward Btn
	//Messages  Btn
	//Settings Btn
	
	//TODO how to switch over to next tutorial chapter????
	
	
	private List<TutorialPage> pages;
	private Iterator<TutorialPage> currentPage;
	
	private final int PAUSE_BETWEEN_STEPS = 1500;

	@Override
	public void prepareAndStart() {
		pages = new LinkedList<TutorialPage>();
		UIManager manager = UIManager.getInstance();
		
		List<Node> nodes = manager.getGameHudController().getTimeControlTutorialNodes();
				
//      DepartmentLabel
//		nodes.add(dateLabel);
//		nodes.add(playPauseIconButton);
//		nodes.add(netWorthVBox);
//		nodes.add(cashVBox);
//		nodes.add(employeeVBox);
//		nodes.add(ecoIcon);
//		nodes.add(forwardIconButton);
//		nodes.add(skipIconButton);
//		nodes.add(messageIconLabel);
//		nodes.add(settingsIconLabel);
		
		//TODO Remove node 1
		pages.add(new TutorialPage(this, manager.getGameHudController().getOverviewDepButton(),
				"Navigate to your ProductionDepartment!"));

		pages.add(new TutorialPage(this, nodes.get(0),
				"Welcome to the tutorial. At first take a look at the GameHud which provides the most important informations. On top you see what department dashboard you have currently opened. The overview is the map of your company, change perspective if you want. "));
		pages.add(new TutorialPage(this, nodes.get(1), "On the top left you see the current date. Default setting is that one day in the game equals one second."));
		pages.add(new TutorialPage(this, nodes.get(2),
				"You can pause the game here. It might be a good idea to do so for the rest of this tutorial."));
		pages.add(new TutorialPage(this, nodes.get(3), "On the button some KPIs are monitored. First one is the networth of your company. Should be self explanatory..."));

		pages.add(new TutorialPage(this, nodes.get(4), "Next one is Cash, not much more complicated. Below you can see how your last actions have changed this metric"));

		pages.add(new TutorialPage(this, nodes.get(5), "This one shows how many employees you have. Still a lot room for improvement I see."));


		pages.add(new TutorialPage(this, nodes.get(6), "The last one displays the EcoIndex, which is actually quite complicated. So we will come back to that later. Just remember that most of your actions can influence it and that pollution can fire back!"));


		pages.add(new TutorialPage(this, nodes.get(7), "Use this button to change game speed. (Not implemented yet - Sorry!)"));
		pages.add(new TutorialPage(this, nodes.get(8), "By klicking here you can skip a week"));

		pages.add(new TutorialPage(this, nodes.get(9), "In the top right corner you can access you mailbox. Not much there yet, but it will become important for you later.)"));
		pages.add(new TutorialPage(this, nodes.get(10), "Next to it you can access the settings Menu. Thats it already on the GameHud. Time to really start by introducing your first product."));

		
		currentPage = pages.iterator();
		nextPage();
	}

	@Override
	public void nextPage() {
		PauseTransition pause = new PauseTransition(Duration.millis(PAUSE_BETWEEN_STEPS));
		pause.play();
		pause.setOnFinished(e -> {
			if (currentPage.hasNext())
				currentPage.next().start();
		});
	}

}
