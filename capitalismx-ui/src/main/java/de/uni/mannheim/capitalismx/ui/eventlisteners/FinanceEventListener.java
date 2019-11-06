package de.uni.mannheim.capitalismx.ui.eventlisteners;

import de.uni.mannheim.capitalismx.gamelogic.GameController;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.GameViewType;
import de.uni.mannheim.capitalismx.ui.components.UIElementType;
import de.uni.mannheim.capitalismx.ui.controller.module.finance.FinanceOverviewController;
import de.uni.mannheim.capitalismx.ui.controller.module.logistics.TruckFleetController;
import de.uni.mannheim.capitalismx.utils.data.PropertyChangeSupportBoolean;
import de.uni.mannheim.capitalismx.utils.data.PropertyChangeSupportDouble;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class FinanceEventListener implements PropertyChangeListener {

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals("gameOver")){
            PropertyChangeSupportBoolean newVal = (PropertyChangeSupportBoolean) evt.getSource();
            if(newVal.getValue() == true){
                GameController.getInstance().terminateGame();
                //TODO popup
                System.out.println("Game Over");
            }
        }

        FinanceOverviewController financeOverviewController = (FinanceOverviewController) UIManager.getInstance().getGameView(GameViewType.FINANCES).getModule(UIElementType.FINANCE_OVERVIEW).getController();

        if (evt.getPropertyName().equals("netWorth")) {
            PropertyChangeSupportDouble newVal = (PropertyChangeSupportDouble) evt.getSource();
            financeOverviewController.setNetWorthLabel(String.valueOf(newVal.getValue()));
            UIManager.getInstance().getGameHudController().updateNetworthLabel(newVal.getValue());
        }

        if (evt.getPropertyName().equals("cash")) {
            PropertyChangeSupportDouble newVal = (PropertyChangeSupportDouble) evt.getSource();
            financeOverviewController.setCashLabel(String.valueOf(newVal.getValue()));
            UIManager.getInstance().getGameHudController().updateCashLabel(newVal.getValue());
        }

        if (evt.getPropertyName().equals("assets")) {
            PropertyChangeSupportDouble newVal = (PropertyChangeSupportDouble) evt.getSource();
            financeOverviewController.setAssetsLabel(String.valueOf(newVal.getValue()));
        }

        if (evt.getPropertyName().equals("liabilities")) {
            PropertyChangeSupportDouble newVal = (PropertyChangeSupportDouble) evt.getSource();
            financeOverviewController.setLiabilitiesLabel(String.valueOf(newVal.getValue()));
        }
    }
}
