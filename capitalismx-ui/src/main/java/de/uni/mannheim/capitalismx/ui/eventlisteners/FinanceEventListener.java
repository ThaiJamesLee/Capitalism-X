package de.uni.mannheim.capitalismx.ui.eventlisteners;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;
import java.util.TreeMap;

import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.GameViewType;
import de.uni.mannheim.capitalismx.ui.components.UIElementType;
import de.uni.mannheim.capitalismx.ui.controller.module.finance.FinanceBankingSystemController;
import de.uni.mannheim.capitalismx.ui.controller.module.finance.FinanceOverviewController;
import de.uni.mannheim.capitalismx.ui.controller.module.finance.OperationsTableController;
import de.uni.mannheim.capitalismx.utils.data.PropertyChangeSupportBoolean;
import de.uni.mannheim.capitalismx.utils.data.PropertyChangeSupportDouble;
import de.uni.mannheim.capitalismx.utils.number.DecimalRound;

/**
 * This class represents the EventListener for the finance UI. It handles UI updates relevant for the finance UI.
 *
 * @author sdupper
 */
public class FinanceEventListener implements PropertyChangeListener {

    /*
     * Determines the name of the PropertyChangeEvent and updates the GUI accordingly, e.g., if the net worth changes,
     * the corresponding labels are updated.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals("gameOver")){
            PropertyChangeSupportBoolean newVal = (PropertyChangeSupportBoolean) evt.getSource();
            if(newVal.getValue() == true){
                UIManager.getInstance().gameOver(false);
            }
        }

        if(evt.getPropertyName().equals("loanRemoved")){
            PropertyChangeSupportBoolean newVal = (PropertyChangeSupportBoolean) evt.getSource();
            if(newVal.getValue() == true){
                FinanceBankingSystemController financeBankingSystemController = (FinanceBankingSystemController) UIManager.getInstance().getGameView(GameViewType.FINANCES).getModule(UIElementType.FINANCE_BANKING_SYSTEM).getController();
                financeBankingSystemController.removeLoan();
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
        }else if (evt.getPropertyName().equals("netWorthDifference")) {
            PropertyChangeSupportDouble newVal = (PropertyChangeSupportDouble) evt.getSource();
            UIManager.getInstance().getGameHudController().updateNetworthChangeLabel(newVal.getValue());
        }else if (evt.getPropertyName().equals("cashDifference")) {
            PropertyChangeSupportDouble newVal = (PropertyChangeSupportDouble) evt.getSource();
            UIManager.getInstance().getGameHudController().updateCashChangeLabel(newVal.getValue());
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
