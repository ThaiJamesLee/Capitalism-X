package de.uni.mannheim.capitalismx.gamelogic;

import de.uni.mannheim.capitalismx.finance.finance.Finance;

import java.time.LocalDate;

public class GameController {

    private static GameController instance;

    private GameController() {}

    public static GameController getInstance() {
        if(instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    public void nextDay() {
        GameState.getInstance().setGameDate(GameState.getInstance().getGameDate().plusDays(1));
        this.updateAll();
    }

    private void updateAll() {
        //TODO update all values of the departments
        this.updateCompanyEcoIndex();
        this.updateCustomer();
        this.updateExternalEvents();
        this.updateFinance();
        this.updateHR();
        this.updateLogistics();
        this.updateMarketing();
        this.updateProcurement();
        this.updateProduction();
        this.updateWarehouse();
    }

    private void updateCompanyEcoIndex() {

    }

    private void updateExternalEvents() {

    }

    private void updateCustomer() {

    }

    private void updateFinance() {

    }

    private void updateHR() {

    }

    private void updateLogistics() {

    }

    private void updateMarketing() {

    }

    // TODO once procurement implementation is ready
    private void updateProcurement() {

    }

    private void updateProduction() {

    }

    private void updateWarehouse() {

    }

    public void start() {
        GameState.getInstance().initiate();
        GameThread.getInstance().start();
    }

    public void pauseGame() {
        GameThread.getInstance().pause();
    }

    public void resumeGame() {
        GameThread.getInstance().unpause();
    }

    public void setSecondsPerDay(int seconds) {
        GameThread.getInstance().setSecondsPerDay(seconds);
    }

    // TODO load game state

    // TODO calculate initial values of respective modules/ departments
    public void setInitialValues() {
        this.setInitialCompanyEcoIndexValues();
        this.setInitialCustomerValues();
        this.setInitialExternalEventsValues();
        this.setInitialFinanceValues();
        this.setInitialHRValues();
        this.setInitialLogisticsValues();
        this.setInitialMarketingValues();
        this.setInitialProcurementValues();
        this.setInitialProductionValues();
        this.setInitialWarehouseValues();
    }

    private void setInitialCompanyEcoIndexValues() {

    }

    private void setInitialExternalEventsValues() {

    }

    private void setInitialCustomerValues() {

    }

    private void setInitialFinanceValues() {

    }

    private void setInitialHRValues() {

    }

    private void setInitialLogisticsValues() {

    }

    private void setInitialMarketingValues() {

    }

    // TODO once procurement implementation is ready
    private void setInitialProcurementValues() {

    }

    private void setInitialProductionValues() {

    }

    private void setInitialWarehouseValues() {

    }

    public double getCash() {
        double cash = Finance.getInstance().getCash();
        return cash;
    }

    public double getNetWorth(LocalDate gameDate) {
        double netWorth = Finance.getInstance().calculateNetWorth(gameDate);
        return netWorth;
    }


}
