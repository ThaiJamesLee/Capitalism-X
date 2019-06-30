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
import de.uni.mannheim.capitalismx.marketing.marketresearch.MarketResearch;
import de.uni.mannheim.capitalismx.marketing.marketresearch.Reports;
import de.uni.mannheim.capitalismx.marketing.marketresearch.SurveyTypes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    /**
     *
     * @return Returns all pre defined marketing campaigns.
     */
    public List<Campaign> getAllMarketingCampaigns() {
        return Campaign.getMarketingCampaigns();
    }

    /**
     *
     * @return Returns all pre defined social engagements.
     */
    public List<Campaign> getAllSocialEngagementCampaigns() {
        return Campaign.getSocialEngagementCampaigns();
    }

    /**
     *
     * @return Returns all pre defined media types.
     */
    public Media[] getAllMedia() {
        return Media.values();
    }

    /**
     * Make a campaign with the specified campaign name and media type.
     * But you are restricted to the predefined campaigns!
     * @param campaignName the campaign name.
     * @param media the media type.
     */
    public void makeCampaign(String campaignName, Media media) {
        MarketingDepartment.getInstance().startCampaign(campaignName, media);
    }

    /**
     *
     * @return Returns all issued press releases.
     */
    public List<PressRelease> getPressReleases() {
        return MarketingDepartment.getInstance().getPressReleases();
    }

    /**
     * Makes a press release.
     * @param pr a press release.
     */
    public void makePressRelease(PressRelease pr) {
        MarketingDepartment.getInstance().makePressRelease(pr);
    }

    /**
     *
     * @return Returns an array of all defined press releases.
     */
    public PressRelease[] getAllPressReleases() {
        return PressRelease.values();
    }

    /**
     *
     * @return Returns all pre defined market research report types.
     */
    public Reports[] getAllMarketResearchReports() {
        return Reports.values();
    }

    /**
     *
     * @return Returns all pre defined survey types.
     */
    public SurveyTypes[] getAllSurveyTypes() {
        return SurveyTypes.values();
    }

    /**
     * Conduct a market research.
     * @param internal if conduct market research internally then set true, else if you outsource then set false.
     * @param report the report type you want to do.
     * @param surveyType the survey methodology.
     * @param data the data as a Map of string as key and double value pair.
     */
    public void conductMarketResearch(boolean internal, Reports report, SurveyTypes surveyType, Map<String, Double> data) {
        MarketingDepartment.getInstance().issueMarketResearch(internal, report, surveyType, data);
    }

    /**
     *
     * @return Returns list of conducted market researches.
     */
    public List<MarketResearch> getConductedMarketResearch() {
        return MarketingDepartment.getInstance().getMarketResearches();
    }


    /* Human Resources */

    /**
     * Hire the employee. He will be added to your team.
     * @param e the employee you want to hire.
     */
    public void hireEmployee(Employee e) {
        HRDepartment.getInstance().hire(e);
    }

    /**
     * Fire the employee. He will be removed from the team.
     * @param e the employee you want to fire.
     */
    public void fireEmployee(Employee e) {
        HRDepartment.getInstance().fire(e);
    }

    /**
     *
     * @return Returns the engineer team.
     */
    public Team getEngineerTeam() {
        return HRDepartment.getInstance().getEngineerTeam();
    }

    /**
     *
     * @return Returns the sales people team.
     */
    public Team getSalesPeopleTeam() {
        return HRDepartment.getInstance().getSalesTeam();
    }

    /**
     *
     * @return Returns all pre defined trainings for your employee.
     */
    public Training[] getAllEmployeeTraining() {
        return HRDepartment.getInstance().getAllTrainings();
    }

    /**
     *  Train the employee.
     *  Note: each employee maintains a list of trainings he did.
     *
     * @param e the employee you want to train.
     * @param t the training he should do.
     */
    public void trainEmployee(Employee e, Training t) {
        HRDepartment.getInstance().trainEmployee(e, t);
    }

}
