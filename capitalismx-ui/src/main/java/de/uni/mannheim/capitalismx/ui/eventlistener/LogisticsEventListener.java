package de.uni.mannheim.capitalismx.ui.eventlistener;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;
import java.util.TreeMap;

import de.uni.mannheim.capitalismx.finance.finance.BankingSystem;
import de.uni.mannheim.capitalismx.finance.finance.Loan;
import de.uni.mannheim.capitalismx.gamecontroller.GameController;
import de.uni.mannheim.capitalismx.gamecontroller.GameState;
import de.uni.mannheim.capitalismx.logistic.logistics.Truck;
import de.uni.mannheim.capitalismx.ui.application.UIManager;
import de.uni.mannheim.capitalismx.ui.component.GameModuleType;
import de.uni.mannheim.capitalismx.ui.component.GameViewType;
import de.uni.mannheim.capitalismx.ui.controller.module.finance.FinanceBankingSystemController;
import de.uni.mannheim.capitalismx.ui.controller.module.finance.FinanceInvestmentsController;
import de.uni.mannheim.capitalismx.ui.controller.module.finance.FinanceOverviewController;
import de.uni.mannheim.capitalismx.ui.controller.module.finance.OperationsTableController;
import de.uni.mannheim.capitalismx.ui.controller.module.logistics.TruckFleetController;
import de.uni.mannheim.capitalismx.ui.util.CapCoinFormatter;
import de.uni.mannheim.capitalismx.utils.data.PropertyChangeSupportBoolean;
import de.uni.mannheim.capitalismx.utils.data.PropertyChangeSupportDouble;
import de.uni.mannheim.capitalismx.utils.number.DecimalRound;

/**
 * This class represents the EventListener for the logistics UI. It handles UI updates relevant for the logistics UI.
 *
 * @author sdupper
 */
public class LogisticsEventListener implements PropertyChangeListener {

    /**
     * The specific truck this LogisticsEventListener corresponds to. Optional.
     */
    Truck truck;

    /**
     * Constructor
     */
    public LogisticsEventListener(){
        super();
    }

    /**
     * Constructor
     * Allows to specify a truck that this LogisticsEventListener corresponds to.
     * @param truck The corresponding truck.
     */
    public LogisticsEventListener(Truck truck){
        super();
        this.truck = truck;
    }

    /*
     * Determines the name of the PropertyChangeEvent and updates the GUI accordingly, e.g., if the truck resell price
     * changes, the corresponding labels are updated.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals("resellPrice")){
            TruckFleetController truckFleetController = (TruckFleetController) UIManager.getInstance().getModule(GameModuleType.LOGISTICS_TRUCK_FLEET_OVERVIEW).getController();
            truckFleetController.updateTruck(this.truck);
        }
    }
}
