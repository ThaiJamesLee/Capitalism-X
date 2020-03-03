package de.uni.mannheim.capitalismx.ui.controller.module.sales;

import de.uni.mannheim.capitalismx.gamecontroller.GameState;
import de.uni.mannheim.capitalismx.sales.contracts.Contract;
import de.uni.mannheim.capitalismx.sales.department.SalesDepartment;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class SalesKPIController implements Initializable {
    int failedContractCounter;
    int doneContractCounter;
    SalesDepartment salesDep = GameState.getInstance().getSalesDepartment();

    @FXML
    Label fulfilledAll;
    @FXML
    Label fulfilledYear;
    @FXML
    Label fulfilledQuarter;

    @FXML
    Label failedAll;
    @FXML
    Label failedYear;
    @FXML
    Label failedQuarter;

    @FXML
    Label revenueAll;
    @FXML
    Label revenueYear;
    @FXML
    Label revenueQuarter;

    @FXML
    Label penaltyAll;
    @FXML
    Label penaltyYear;
    @FXML
    Label penaltyQuarter;

    /**
     * Updates the KPIs for the of fulfilled contracts and the contract revenue.
     */
    public void updateFulfilledKPIs(){
        doneContractCounter = salesDep.getDoneContracts().size();
        int yearContractCounter = 0;
        int quarterContractCounter = 0;
        double revenueAll = 0, revenueYear = 0, revenueQuarter = 0;

        for(Contract c : salesDep.getDoneContracts().getList()){
            revenueAll += c.getRevenue();

            if(c.getContractDoneDate().compareTo(GameState.getInstance().getGameDate().minusYears(1)) >= 0){
                yearContractCounter++;
                revenueYear += c.getRevenue();

                if(c.getContractDoneDate().compareTo(GameState.getInstance().getGameDate().minusMonths(3)) >= 0){
                    quarterContractCounter++;
                    revenueQuarter += c.getRevenue();
                }
            }
        }
        
        fulfilledAll.setText("" + doneContractCounter);
        fulfilledYear.setText("" + yearContractCounter);
        fulfilledQuarter.setText("" + quarterContractCounter);
        
        this.revenueAll.setText("" + revenueAll);
        this.revenueYear.setText("" + revenueYear);
        this.revenueQuarter.setText("" + revenueQuarter);
    }

    /**
     * Updates the KPIs for the of failed contracts and the contract losses.
     */
    public void updateFailedKPIs(){
        failedContractCounter = salesDep.getFailedContracts().size();
        int yearFailedCounter = 0;
        int quarterFailedCounter = 0;
        double penaltyAll = 0, penaltyYear = 0, penaltyQuarter = 0;

        for(Contract c : salesDep.getFailedContracts().getList()){
            penaltyAll += c.getRevenue();

            if(c.getTerminateDate().compareTo(GameState.getInstance().getGameDate().minusYears(1)) >= 0){
                yearFailedCounter++;
                penaltyYear += c.getRevenue();

                if(c.getTerminateDate().compareTo(GameState.getInstance().getGameDate().minusMonths(3)) >= 0){
                    quarterFailedCounter++;
                    penaltyQuarter += c.getRevenue();
                }
            }
        }

        failedAll.setText("" + failedContractCounter);
        failedYear.setText("" + yearFailedCounter);
        failedQuarter.setText("" + quarterFailedCounter);

        this.penaltyAll.setText("" + penaltyAll);
        this.penaltyYear.setText("" + penaltyYear);
        this.penaltyQuarter.setText("" + penaltyQuarter);

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        failedContractCounter = salesDep.getFailedContracts().size();
        doneContractCounter = salesDep.getDoneContracts().size();
    }
}
