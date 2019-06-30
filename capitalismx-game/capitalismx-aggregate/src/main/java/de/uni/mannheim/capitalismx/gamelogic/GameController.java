package de.uni.mannheim.capitalismx.gamelogic;

import de.uni.mannheim.capitalismx.finance.finance.BankingSystem;
import de.uni.mannheim.capitalismx.finance.finance.Finance;
import de.uni.mannheim.capitalismx.finance.finance.Investment;
import de.uni.mannheim.capitalismx.hr.department.HRDepartment;
import de.uni.mannheim.capitalismx.hr.domain.Training;
import de.uni.mannheim.capitalismx.hr.employee.Employee;
import de.uni.mannheim.capitalismx.hr.employee.Team;
import de.uni.mannheim.capitalismx.marketing.department.MarketingDepartment;
import de.uni.mannheim.capitalismx.marketing.domain.Campaign;
import de.uni.mannheim.capitalismx.marketing.domain.Media;
import de.uni.mannheim.capitalismx.marketing.domain.PressRelease;
import de.uni.mannheim.capitalismx.marketing.marketresearch.Reports;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    /* Finance */

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


    /* Marketing */

    public List<Campaign> getAllMarketingCampaigns() {
        return Campaign.getMarketingCampaigns();
    }

    public List<Campaign> getAllSocialEngagementCampaigns() {
        return Campaign.getSocialEngagementCampaigns();
    }

    public Media[] getAllMedia() {
        return Media.values();
    }

    public void makeCampaign(String campaignName, Media media) {
        MarketingDepartment.getInstance().startCampaign(campaignName, media);
    }

    public List<PressRelease> getPressReleases() {
        return MarketingDepartment.getInstance().getPressReleases();
    }

    public void makePressRelease(PressRelease pr) {
        MarketingDepartment.getInstance().makePressRelease(pr);
    }

    public Reports[] getAllMarketResearchReports() {
        return Reports.values();
    }


    /* Human Resources */

    public void hireEmployee(Employee e) {
        HRDepartment.getInstance().hire(e);
    }

    public void fireEmployee(Employee e) {
        HRDepartment.getInstance().fire(e);
    }

    public Team getEngineerTeam() {
        return HRDepartment.getInstance().getEngineerTeam();
    }

    public Team getSalesPeopleTeam() {
        return HRDepartment.getInstance().getSalesTeam();
    }

    public Training[] getAllEmployeeTraining() {
        return HRDepartment.getInstance().getAllTrainings();
    }

    public void trainEmployee(Employee e, Training t) {
        HRDepartment.getInstance().trainEmployee(e, t);
    }
}
