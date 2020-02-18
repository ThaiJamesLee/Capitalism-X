package de.uni.mannheim.capitalismx.ui.eventlisteners;

import de.uni.mannheim.capitalismx.sales.contracts.Contract;
import de.uni.mannheim.capitalismx.sales.department.SalesDepartment;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.components.GameViewType;
import de.uni.mannheim.capitalismx.ui.components.UIElementType;
import de.uni.mannheim.capitalismx.ui.controller.module.sales.SalesContractController;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class SalesEventListener implements PropertyChangeListener {


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        //evt.getPropertyName()
        System.out.println("SalesEventListener called");
        SalesContractController salesController = (SalesContractController) UIManager.getInstance().getGameView(GameViewType.SALES).getModule(UIElementType.SALES_CONTRACT_OVERVIEW).getController();
        if(evt.getPropertyName().equals(SalesDepartment.AVAILABLE_CONTRACTS_EVENT)){
            System.out.println("Available Contracts changed");
            List<Contract> availableContracts = ((List<Contract>)evt.getNewValue());

            for(int i = 0; i < availableContracts.size(); i++){
                salesController.addContractOffer(availableContracts.get(i), i);
            }
        } else if(evt.getPropertyName().equals(SalesDepartment.ACTIVE_CONTRACTS_EVENT)){
            System.out.println(evt.getPropertyName());
        } else if(evt.getPropertyName().equals(SalesDepartment.DONE_CONTRACTS_EVENT)){
            System.out.println(evt.getPropertyName());
        }

    }
}
