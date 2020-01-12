package de.uni.mannheim.capitalismx.ui.eventlisteners;

import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.GameViewType;
import de.uni.mannheim.capitalismx.ui.components.UIElementType;
import de.uni.mannheim.capitalismx.ui.controller.module.finance.FinanceBankingSystemController;
import de.uni.mannheim.capitalismx.ui.controller.module.finance.FinanceOverviewController;
import de.uni.mannheim.capitalismx.ui.controller.module.finance.FinanceStatisticsChartsController;
import de.uni.mannheim.capitalismx.ui.controller.module.finance.OperationsTableController;
import de.uni.mannheim.capitalismx.utils.data.PropertyChangeSupportBoolean;
import de.uni.mannheim.capitalismx.utils.data.PropertyChangeSupportDouble;
import de.uni.mannheim.capitalismx.utils.number.DecimalRound;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;

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

        if (evt.getPropertyName().equals("updatedMonthlyData")) {
            FinanceOverviewController financeOverviewController = (FinanceOverviewController) UIManager.getInstance().getGameView(GameViewType.FINANCES).getModule(UIElementType.FINANCE_OVERVIEW).getController();

            TreeMap<String, String[]> monthlyData = GameController.getInstance().getMonthlyData();
            String[] xNames = monthlyData.get("xNames");
            for (Map.Entry<String,String[]> entry : monthlyData.entrySet()) {
                if(!entry.getKey().equals("xNames")){
                    financeOverviewController.updateCharts(entry.getKey().replace("Monthly", ""), entry.getValue(), xNames);
                }
            }
        }

        if (evt.getPropertyName().equals("updatedQuarterlyData")) {
            OperationsTableController operationsTableController = (OperationsTableController) UIManager.getInstance().getGameView(GameViewType.FINANCES).getModule(UIElementType.FINANCE_OPERATIONS_TABLE).getController();

            TreeMap<String, String[]> quarterlyData = GameController.getInstance().getQuarterlyData();
            String[] colNames = quarterlyData.get("colNames");
            for (Map.Entry<String,String[]> entry : quarterlyData.entrySet()) {
                if(!entry.getKey().equals("colNames")){
                    operationsTableController.updateTable(entry.getKey().replace("Quarterly", ""), entry.getValue(), colNames);
                }
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
        }

        FinanceBankingSystemController bankingSystemController = (FinanceBankingSystemController) UIManager.getInstance().getGameView(GameViewType.FINANCES).getModule(UIElementType.FINANCE_BANKING_SYSTEM).getController();

        if (evt.getPropertyName().equals("realEstateInvestmentAmount")) {
            PropertyChangeSupportDouble newVal = (PropertyChangeSupportDouble) evt.getSource();
            bankingSystemController.setRealEstateLabel("Amount: " + String.valueOf(DecimalRound.round(newVal.getValue(), 2)));
        }else if (evt.getPropertyName().equals("stocksInvestmentAmount")) {
            PropertyChangeSupportDouble newVal = (PropertyChangeSupportDouble) evt.getSource();
            bankingSystemController.setStocksLabel("Amount: " + String.valueOf(DecimalRound.round(newVal.getValue(), 2)));
        }else if (evt.getPropertyName().equals("ventureCapitalInvestmentAmount")) {
            PropertyChangeSupportDouble newVal = (PropertyChangeSupportDouble) evt.getSource();
            bankingSystemController.setVentureCapitalLabel("Amount: " + String.valueOf(DecimalRound.round(newVal.getValue(), 2)));
        }
    }
}
