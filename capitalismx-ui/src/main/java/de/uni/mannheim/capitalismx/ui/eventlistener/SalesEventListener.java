package de.uni.mannheim.capitalismx.ui.eventlistener;

import de.uni.mannheim.capitalismx.sales.contracts.Contract;
import de.uni.mannheim.capitalismx.sales.department.SalesDepartment;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.component.GameModuleType;
import de.uni.mannheim.capitalismx.ui.component.GameViewType;
import de.uni.mannheim.capitalismx.ui.controller.module.sales.SalesContractController;
import de.uni.mannheim.capitalismx.ui.controller.module.sales.SalesKPIController;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class SalesEventListener implements PropertyChangeListener {

    /**
     * Receives PropertyChangeEvents from the SalesDepartment each time the lists of available, accepted, or done contracts change.
     * Depending on what has changed, it calls methods to reflect changes in UI.
     * @param evt
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        //evt.getPropertyName()
        SalesContractController salesController = (SalesContractController) UIManager.getInstance().getGameView(GameViewType.SALES).getModule(GameModuleType.SALES_CONTRACT_OVERVIEW).getController();
        if(evt.getPropertyName().equals(SalesDepartment.AVAILABLE_CONTRACTS_EVENT)){
            System.out.println("AVAILABLE_CONTRACTS_EVENT");
            ((SalesContractController) UIManager.getInstance().getGameView(GameViewType.SALES).getModule(GameModuleType.SALES_CONTRACT_OVERVIEW).getController()).refreshAvailableContracts();

        } else if(evt.getPropertyName().equals(SalesDepartment.ACTIVE_CONTRACTS_EVENT)){
            System.out.println("ACTIVE_CONTRACTS_EVENT");
            ((SalesContractController) UIManager.getInstance().getGameView(GameViewType.SALES).getModule(GameModuleType.SALES_CONTRACT_OVERVIEW).getController()).refreshAcceptedContracts();
            ((SalesContractController) UIManager.getInstance().getGameView(GameViewType.SALES).getModule(GameModuleType.SALES_CONTRACT_OVERVIEW).getController()).refreshAvailableContracts();
            //((SalesKPIController) UIManager.getInstance().getGameView(GameViewType.SALES).getModule(GameModuleType.SALES_KPI_OVERVIEW).getController()).updateFailedKPIs();

        } else if(evt.getPropertyName().equals(SalesDepartment.DONE_CONTRACTS_EVENT)){
            System.out.println("DONE_CONTRACTS_EVENT");
            ((SalesContractController) UIManager.getInstance().getGameView(GameViewType.SALES).getModule(GameModuleType.SALES_CONTRACT_OVERVIEW).getController()).refreshAcceptedContracts();
            ((SalesKPIController) UIManager.getInstance().getGameView(GameViewType.SALES).getModule(GameModuleType.SALES_KPI_OVERVIEW).getController()).updateFulfilledKPIs();
            //((SalesContractController) UIManager.getInstance().getGameView(GameViewType.SALES).getModule(GameModuleType.SALES_CONTRACT_OVERVIEW).getController()).refreshAvailableContracts();
        } else if(evt.getPropertyName().equals(SalesDepartment.FAILED_CONTRACTS_EVENT)){
            ((SalesKPIController) UIManager.getInstance().getGameView(GameViewType.SALES).getModule(GameModuleType.SALES_KPI_OVERVIEW).getController()).updateFailedKPIs();
            System.out.println("Failed Contract Event");
        }
    }
}
