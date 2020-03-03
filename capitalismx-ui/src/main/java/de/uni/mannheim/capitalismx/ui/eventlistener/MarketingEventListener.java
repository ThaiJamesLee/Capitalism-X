package de.uni.mannheim.capitalismx.ui.eventlistener;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.component.GameModuleType;
import de.uni.mannheim.capitalismx.ui.component.GameViewType;
import de.uni.mannheim.capitalismx.ui.controller.module.marketing.MarketResearchListController;
import de.uni.mannheim.capitalismx.ui.controller.module.marketing.MarketingOverviewController;


/**
 * This class represents the EventListener for the Marketing UI. It handles UI updates relevant for the marketing UI.
 *
 * @author Alex
 *
 */
public class MarketingEventListener implements PropertyChangeListener {

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        if(evt.getPropertyName().equals("level")){
            MarketingOverviewController overviewCon = (MarketingOverviewController) UIManager.getInstance().getGameView(GameViewType.MARKETING).getModule(GameModuleType.MARKETING_OVERVIEW).getController();
            
           int newLevel = ((Double) evt.getNewValue()).intValue();
            switch(newLevel) {
            case 1:
            	overviewCon.updateInfoLabels();
            	break;
            case 2:
            	//TODO Wirkdauer von Pressemitteilung wird verdoppelt 
            	break;
            case 3:
            	MarketResearchListController mrCon = (MarketResearchListController) UIManager.getInstance().getGameView(GameViewType.MARKETING).getModule(GameModuleType.MARKETING_MARKETRESEARCH).getController();
            	mrCon.enableInternalMarketResearch();
            	break;
            case 4:
            	//TODO marketing.skill.description.4=Freischalten: Weltklasse-Unternehmensberatung
            	break;
            default:
            	//TODO
            	break; 
            }
        }      
    }
}
