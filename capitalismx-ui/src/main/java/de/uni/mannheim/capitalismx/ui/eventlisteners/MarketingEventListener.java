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
import de.uni.mannheim.capitalismx.ui.controller.module.marketing.PressReleaseListController;
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
        	System.out.println("Event caught!!!");
            PressReleaseListController pressReleaseController = (PressReleaseListController) UIManager.getInstance().getGameView(GameViewType.MARKETING).getModule(UIElementType.MARKETING_PRESSRELEASE).getController();
            pressReleaseController.hidePopover();
        }

    
       
    }
}
