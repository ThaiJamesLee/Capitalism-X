package de.uni.mannheim.capitalismx.gamelogic;

import de.uni.mannheim.capitalismx.finance.finance.BankingSystem;
import de.uni.mannheim.capitalismx.finance.finance.Finance;
import de.uni.mannheim.capitalismx.finance.finance.Investment;
import de.uni.mannheim.capitalismx.logistic.logistics.ExternalPartner;
import de.uni.mannheim.capitalismx.logistic.logistics.InternalFleet;
import de.uni.mannheim.capitalismx.logistic.logistics.Logistics;
import de.uni.mannheim.capitalismx.logistic.logistics.Truck;
import de.uni.mannheim.capitalismx.logistic.support.ProductSupport;
import de.uni.mannheim.capitalismx.production.Product;
import sun.rmi.runtime.Log;

import java.time.LocalDate;
import java.util.ArrayList;

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
        Finance.getInstance().calculateNetWorth(GameState.getInstance().getGameDate());
    }

    private void updateHR() {

    }

    private void updateLogistics() {
        InternalFleet.getInstance().calculateAll();
        if(Logistics.getInstance().getExternalPartner() != null){
            Logistics.getInstance().getExternalPartner().calculateExternalLogisticsIndex();
        }
        Logistics.getInstance().calculateAll();
        ProductSupport.getInstance().calculateAll();
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
        Finance.getInstance().calculateNetWorth(GameState.getInstance().getGameDate());
    }

    private void setInitialHRValues() {

    }

    private void setInitialLogisticsValues() {
        //Logistics.getInstance().calculateAll();
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

    public ArrayList<Investment> generateInvestmentSelection(double amount){
        return Finance.getInstance().generateInvestmentSelection(amount);
    }

    public void addInvestment(Investment investment){
        Finance.getInstance().addInvestment(investment);
    }

    public void removeInvestment(Investment investment){
        Finance.getInstance().removeInvestment(investment);
    }

    public ArrayList<BankingSystem.Loan> generateLoanSelection(double loanAmount){
        return Finance.getInstance().generateLoanSelection(loanAmount);
    }

    public void addLoan(BankingSystem.Loan loan){
        Finance.getInstance().addLoan(loan);
    }

    public ArrayList<ExternalPartner> generateExternalPartnerSelection(){
        return Logistics.getInstance().generateExternalPartnerSelection();
    }

    public ArrayList<Truck> generateTruckSelection(){
        return Logistics.getInstance().generateTruckSelection();
    }

    public void addExternalPartner(ExternalPartner externalPartner){
        Logistics.getInstance().addExternalPartner(externalPartner);
    }

    public void removeExternalPartner(){
        Logistics.getInstance().removeExternalPartner();
    }

    public void addTruckToFleet(Truck truck, LocalDate gameDate){
        Logistics.getInstance().addTruckToFleet(truck, gameDate);
    }

    public void removeTruckFromFleet(Truck truck){
        Logistics.getInstance().removeTruckFromFleet(truck);
    }

    public ArrayList<ProductSupport.SupportType> generateSupportTypeSelection(){
        return ProductSupport.getInstance().generateSupportTypeSelection();
    }

    public void addSupport(ProductSupport.SupportType supportType){
        ProductSupport.getInstance().addSupport(supportType);
    }

    public void removeSupport(ProductSupport.SupportType supportType){
        ProductSupport.getInstance().removeSupport(supportType);
    }

    public ArrayList<ProductSupport.ExternalSupportPartner> generateExternalSupportPartnerSelection(){
        return ProductSupport.getInstance().generateExternalSupportPartnerSelection();
    }

    public void addExternalSupportPartner(ProductSupport.ExternalSupportPartner externalSupportPartner){
        ProductSupport.getInstance().addExternalSupportPartner(externalSupportPartner);
    }

    public void removeExternalSupportPartner(){
        ProductSupport.getInstance().removeExternalSupportPartner();
    }

    public ArrayList<ProductSupport.SupportType> getSupportTypes() {
        return ProductSupport.getInstance().getSupportTypes();
    }

    public ProductSupport.ExternalSupportPartner getExternalSupportPartner() {
        return ProductSupport.getInstance().getExternalSupportPartner();
    }

    public int getTotalSupportTypeQuality() {
        return ProductSupport.getInstance().getTotalSupportTypeQuality();
    }

    public double getTotalSupportQuality() {
        return ProductSupport.getInstance().getTotalSupportQuality();
    }

    public double getTotalSupportCosts() {
        return ProductSupport.getInstance().getTotalSupportCosts();
    }

}
