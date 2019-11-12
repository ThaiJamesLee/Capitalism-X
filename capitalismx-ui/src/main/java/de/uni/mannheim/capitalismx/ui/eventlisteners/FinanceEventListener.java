package de.uni.mannheim.capitalismx.ui.eventlisteners;

import de.uni.mannheim.capitalismx.gamelogic.GameController;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.GameViewType;
import de.uni.mannheim.capitalismx.ui.components.UIElementType;
import de.uni.mannheim.capitalismx.ui.controller.module.finance.FinanceOverviewController;
import de.uni.mannheim.capitalismx.ui.controller.module.logistics.TruckFleetController;
import de.uni.mannheim.capitalismx.utils.data.PropertyChangeSupportBoolean;
import de.uni.mannheim.capitalismx.utils.data.PropertyChangeSupportDouble;
import de.uni.mannheim.capitalismx.utils.number.DecimalRound;

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
            financeOverviewController.setNetWorthLabel(String.valueOf(DecimalRound.round(newVal.getValue(), 2)));
            UIManager.getInstance().getGameHudController().updateNetworthLabel(newVal.getValue());
        }else if (evt.getPropertyName().equals("cash")) {
            PropertyChangeSupportDouble newVal = (PropertyChangeSupportDouble) evt.getSource();
            financeOverviewController.setCashLabel(String.valueOf(DecimalRound.round(newVal.getValue(), 2)));
            UIManager.getInstance().getGameHudController().updateCashLabel(newVal.getValue());
        }else if (evt.getPropertyName().equals("assets")) {
            PropertyChangeSupportDouble newVal = (PropertyChangeSupportDouble) evt.getSource();
            financeOverviewController.setAssetsLabel(String.valueOf(DecimalRound.round(newVal.getValue(), 2)));
        }else if (evt.getPropertyName().equals("liabilities")) {
            PropertyChangeSupportDouble newVal = (PropertyChangeSupportDouble) evt.getSource();
            financeOverviewController.setLiabilitiesLabel(String.valueOf(DecimalRound.round(newVal.getValue(), 2)));
        }else if (evt.getPropertyName().equals("realEstateInvestmentAmount")) {
            PropertyChangeSupportDouble newVal = (PropertyChangeSupportDouble) evt.getSource();
            financeOverviewController.setRealEstateLabel("Amount: " + String.valueOf(DecimalRound.round(newVal.getValue(), 2)));
        }else if (evt.getPropertyName().equals("stocksInvestmentAmount")) {
            PropertyChangeSupportDouble newVal = (PropertyChangeSupportDouble) evt.getSource();
            financeOverviewController.setStocksLabel("Amount: " + String.valueOf(DecimalRound.round(newVal.getValue(), 2)));
        }else if (evt.getPropertyName().equals("ventureCapitalInvestmentAmount")) {
            PropertyChangeSupportDouble newVal = (PropertyChangeSupportDouble) evt.getSource();
            financeOverviewController.setVentureCapitalLabel("Amount: " + String.valueOf(DecimalRound.round(newVal.getValue(), 2)));
        }
    }
}
