package de.uni.mannheim.capitalismx.ui.eventlisteners;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;
import java.util.TreeMap;

import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.GameViewType;
import de.uni.mannheim.capitalismx.ui.components.UIElementType;
import de.uni.mannheim.capitalismx.ui.controller.module.finance.OperationsTableController;
import de.uni.mannheim.capitalismx.ui.controller.module.marketing.MarketResearchListController;
import de.uni.mannheim.capitalismx.ui.controller.module.marketing.MarketingOverviewController;
import de.uni.mannheim.capitalismx.ui.controller.module.marketing.PressReleaseListController;
import de.uni.mannheim.capitalismx.ui.controller.popover.marketing.NewMarketResearchController;
import de.uni.mannheim.capitalismx.utils.data.PropertyChangeSupportBoolean;
import de.uni.mannheim.capitalismx.utils.data.PropertyChangeSupportDouble;
import de.uni.mannheim.capitalismx.utils.number.DecimalRound;

public class MarketingEventListener implements PropertyChangeListener {

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
//        if(evt.getPropertyName().equals("gameOver")){
//            PropertyChangeSupportBoolean newVal = (PropertyChangeSupportBoolean) evt.getSource();
//            if(newVal.getValue() == true){
//                GameController.getInstance().terminateGame();
//                //TODO popup
//                System.out.println("Game Over");
//            }
//        }

        if(evt.getPropertyName().equals("pressReleases")){
            PressReleaseListController pressReleaseController = (PressReleaseListController) UIManager.getInstance().getGameView(GameViewType.MARKETING).getModule(UIElementType.MARKETING_PRESSRELEASE).getController();
            pressReleaseController.hidePopover();
        }

        if(evt.getPropertyName().equals("level")){
            MarketingOverviewController overviewCon = (MarketingOverviewController) UIManager.getInstance().getGameView(GameViewType.MARKETING).getModule(UIElementType.MARKETING_OVERVIEW).getController();
            
           int newLevel = ((Double) evt.getNewValue()).intValue();
            switch(newLevel) {
            case 1:
            	overviewCon.updateInfoLabels();
            	break;
            case 2:
            	//TODO Wirkdauer von Pressemitteilung wird verdoppelt 
            	break;
            case 3:
            	MarketResearchListController mrCon = (MarketResearchListController) UIManager.getInstance().getGameView(GameViewType.MARKETING).getModule(UIElementType.MARKETING_MARKETRESEARCH).getController();
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
