package de.uni.mannheim.capitalismx.ui.eventlisteners;

import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.GameViewType;
import de.uni.mannheim.capitalismx.ui.components.UIElementType;
import de.uni.mannheim.capitalismx.ui.controller.module.finance.FinanceOverviewController;
import de.uni.mannheim.capitalismx.ui.controller.module.logistics.TruckFleetController;
import de.uni.mannheim.capitalismx.utils.data.PropertyChangeSupportDouble;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class FinanceEventListener implements PropertyChangeListener {

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("netWorth")) {
            PropertyChangeSupportDouble newVal = (PropertyChangeSupportDouble) evt.getSource();
            FinanceOverviewController financeOverviewController = (FinanceOverviewController) UIManager.getInstance().getGameView(GameViewType.FINANCES).getModule(UIElementType.FINANCE_OVERVIEW).getController();
            financeOverviewController.setNetWorthLabel(String.valueOf(newVal.getValue()));
        }

        if (evt.getPropertyName().equals("cash")) {
            PropertyChangeSupportDouble newVal = (PropertyChangeSupportDouble) evt.getSource();
            FinanceOverviewController financeOverviewController = (FinanceOverviewController) UIManager.getInstance().getGameView(GameViewType.FINANCES).getModule(UIElementType.FINANCE_OVERVIEW).getController();
            financeOverviewController.setCashLabel(String.valueOf(newVal.getValue()));
        }
    }
}
