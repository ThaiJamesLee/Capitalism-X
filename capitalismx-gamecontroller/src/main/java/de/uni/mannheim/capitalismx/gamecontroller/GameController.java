package de.uni.mannheim.capitalismx.gamecontroller;

import java.time.LocalDate;
import java.util.*;

import de.uni.mannheim.capitalismx.department.WarehousingDepartment;
import de.uni.mannheim.capitalismx.domain.exception.LevelingRequirementNotFulFilledException;
import de.uni.mannheim.capitalismx.finance.finance.Loan;
import de.uni.mannheim.capitalismx.hr.domain.employee.EmployeeGenerator;
import de.uni.mannheim.capitalismx.logistic.logistics.exception.NotEnoughTruckCapacityException;
import de.uni.mannheim.capitalismx.logistic.support.exception.NoExternalSupportPartnerException;
import de.uni.mannheim.capitalismx.procurement.component.*;
import de.uni.mannheim.capitalismx.production.department.ProductionDepartment;
import de.uni.mannheim.capitalismx.production.department.ProductionTechnology;
import de.uni.mannheim.capitalismx.production.exceptions.*;
import de.uni.mannheim.capitalismx.production.investment.ProductionInvestment;
import de.uni.mannheim.capitalismx.production.investment.ProductionInvestmentLevel;
import de.uni.mannheim.capitalismx.production.machinery.Machinery;
import de.uni.mannheim.capitalismx.production.product.Product;
import de.uni.mannheim.capitalismx.production.product.ProductCategory;
import de.uni.mannheim.capitalismx.warehouse.*;
import de.uni.mannheim.capitalismx.warehouse.exceptions.NoWarehouseSlotsAvailableException;
import de.uni.mannheim.capitalismx.warehouse.exceptions.StorageCapacityUsedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.uni.mannheim.capitalismx.customer.CustomerDemand;
import de.uni.mannheim.capitalismx.customer.CustomerSatisfaction;
import de.uni.mannheim.capitalismx.finance.finance.BankingSystem;
import de.uni.mannheim.capitalismx.finance.finance.FinanceDepartment;
import de.uni.mannheim.capitalismx.finance.finance.Investment;
import de.uni.mannheim.capitalismx.gamecontroller.ecoindex.CompanyEcoIndex;
import de.uni.mannheim.capitalismx.gamecontroller.external_events.ExternalEvents;
import de.uni.mannheim.capitalismx.gamecontroller.gamesave.SaveGameHandler;
import de.uni.mannheim.capitalismx.hr.department.HRDepartment;
import de.uni.mannheim.capitalismx.hr.domain.employee.Employee;
import de.uni.mannheim.capitalismx.hr.domain.employee.EmployeeType;
import de.uni.mannheim.capitalismx.hr.domain.employee.Team;
import de.uni.mannheim.capitalismx.hr.domain.employee.training.Training;
import de.uni.mannheim.capitalismx.logistic.logistics.ExternalPartner;
import de.uni.mannheim.capitalismx.logistic.logistics.InternalFleet;
import de.uni.mannheim.capitalismx.logistic.logistics.LogisticsDepartment;
import de.uni.mannheim.capitalismx.logistic.logistics.Truck;
import de.uni.mannheim.capitalismx.logistic.support.ProductSupport;
import de.uni.mannheim.capitalismx.marketing.consultancy.ConsultancyType;
import de.uni.mannheim.capitalismx.marketing.department.MarketingDepartment;
import de.uni.mannheim.capitalismx.marketing.domain.Campaign;
import de.uni.mannheim.capitalismx.marketing.domain.Media;
import de.uni.mannheim.capitalismx.marketing.domain.PressRelease;
import de.uni.mannheim.capitalismx.marketing.marketresearch.MarketResearch;
import de.uni.mannheim.capitalismx.marketing.marketresearch.Reports;
import de.uni.mannheim.capitalismx.marketing.marketresearch.SurveyTypes;
import de.uni.mannheim.capitalismx.resdev.department.ResearchAndDevelopmentDepartment;
import de.uni.mannheim.capitalismx.sales.department.SalesDepartment;

/**
 * This class is the entry point for the UI.
 * It aggregates the game-logic and allows all objects to be updated.
 *
 * @author duly
 * @author dzhao
 * @author sdupper
 */
public class GameController {
	
	private static final String GAME_ENDDATE = "2017-12-31";

	private static final Logger LOGGER = LoggerFactory.getLogger(GameController.class);

	private static GameController instance;

	private GameController() {}

	public static GameController getInstance() {
		if (instance == null) {
			instance = new GameController();
		}
		return instance;
	}

	/**
	 * Sets the day of GameState to the next day.
	 * It checks whether the end date is met.
	 * It calls monthlyUpdate if a new month begins.
	 * It also calls the updateAll method to update all departments.
	 */
	public synchronized void nextDay() {
		GameState state = GameState.getInstance();

		LocalDate oldDate = state.getGameDate();
		state.setGameDate(oldDate.plusDays(1));
		LocalDate newDate = state.getGameDate();
		
		//check if enddate is reached
		if(newDate.isAfter(LocalDate.parse(GAME_ENDDATE))){
			state.endGameReached(newDate);
		}
		
		if (oldDate.getMonth() != newDate.getMonth()) {
			monthlyUpdate();
		}
		this.updateAll();
	}

	/**
	 * All events or objects that are updated in a monthly fashion.
	 */
	private void monthlyUpdate() {
		GameState.getInstance().getProductionDepartment().resetMonthlyPerformanceMetrics();
		GameState.getInstance().getWarehousingDepartment().resetMonthlyStorageCost();
		updateSalesDepartment();
	}
	
	public void goToDay(LocalDate newDate) {
		GameState state = GameState.getInstance();

		while(newDate.isAfter(state.getGameDate())) {
			nextDay();
		}
	}

	/**
	 * Calls the update method of every department.
	 * The sequence is important as some of the departments are dependent on others.
	 */
	private void updateAll() {
		// TODO update all values of the departments
		this.updateCompanyEcoIndex();
		this.updateCustomer();
		this.updateExternalEvents(GameState.getInstance().getGameDate());
		this.updateFinance();
		this.updateHR();
		this.updateLogistics();
		this.updateMarketing();
		this.updateWarehouse();
		this.updateProcurement();
		this.updateProduction();
	}

	/**
	 * Save GameState.
	 */
	public void saveGame() {
		new SaveGameHandler().saveGameState(GameState.getInstance());
	}

	/**
	 * Load existing GameState and restore singletons.
	 */
	public void loadGame() {
		try {
			GameState state = new SaveGameHandler().loadGameState();
			GameState.setInstance(state);

			// initialize and restore singleton properties
			HRDepartment.setInstance(state.getHrDepartment());
			ProductionDepartment.setInstance(state.getProductionDepartment());
			WarehousingDepartment.setInstance(state.getWarehousingDepartment());
			FinanceDepartment.setInstance(state.getFinanceDepartment());
			MarketingDepartment.setInstance(state.getMarketingDepartment());
			LogisticsDepartment.setInstance(state.getLogisticsDepartment());
			CustomerDemand.setInstance(state.getCustomerDemand());
			CustomerSatisfaction.setInstance(state.getCustomerSatisfaction());
			ExternalEvents.setInstance(state.getExternalEvents());
			CompanyEcoIndex.setInstance(state.getCompanyEcoIndex());
			InternalFleet.setInstance(state.getInternalFleet());
			BankingSystem.setInstance(state.getBankingSystem());
			ResearchAndDevelopmentDepartment.setInstance(state.getResearchAndDevelopmentDepartment());
			ProductSupport.setInstance(state.getProductSupport());
			SalesDepartment.setInstance(state.getSalesDepartment());
			EmployeeGenerator.setInstance(state.getEmployeeGenerator());

		} catch (ClassNotFoundException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	/**
	 * Updates the relevant values regarding the company eco index.
	 */
	private void updateCompanyEcoIndex() {
		
		double  campaignPoints = MarketingDepartment.getInstance().getPointsFromEcoCampaigns(getIssuedMarketingCampaigns()); 
		double currentExtraLevels = GameState.getInstance().getCompanyEcoIndex().getExtraLevelsFromCampaigns();
		if(campaignPoints > 9 && currentExtraLevels == 0) {
			GameState.getInstance().getCompanyEcoIndex().setExtraLevelsFromCampaigns(1);
			GameState.getInstance().getCompanyEcoIndex().addCampaignPoints();
		}
		if(campaignPoints > 10 && currentExtraLevels == 1) {
			GameState.getInstance().getCompanyEcoIndex().setExtraLevelsFromCampaigns(2);
			GameState.getInstance().getCompanyEcoIndex().addCampaignPoints();
		}
			
		GameState.getInstance().getCompanyEcoIndex().checkMachinery(GameState.getInstance().getGameDate());
		GameState.getInstance().getCompanyEcoIndex().checkVehicles();
		GameState.getInstance().getCompanyEcoIndex().calculateAll();
		//set the eco costs in the finance department
		GameState.getInstance().getFinanceDepartment().setEcoCosts(GameState.getInstance().getCompanyEcoIndex().getEcoCosts());
	}

	/**
	 * Checks which external events occurred on the current day.
	 * @param gameDate The current date in the game.
	 */
	private void updateExternalEvents(LocalDate gameDate) {
		GameState.getInstance().getExternalEvents().checkEvents(gameDate);
	}

	private void updateCustomer() {
		GameState state =  GameState.getInstance();
		LocalDate gameDate = state.getGameDate();
		CustomerSatisfaction customerSatisfaction = state.getCustomerSatisfaction();
		CustomerDemand customerDemand = state.getCustomerDemand();

		// get launched products from production department
		ProductionDepartment productionDepartment = state.getProductionDepartment();
		customerSatisfaction.setProducts(productionDepartment.getLaunchedProducts());

		// get totalSupportQuality score from Logistics
		ProductSupport productSupport = state.getProductSupport();
		customerSatisfaction.setTotalSupportQuality(productSupport.getTotalSupportQuality());

		// get companyImage score from Marketing
		MarketingDepartment marketingDepartment = state.getMarketingDepartment();
		double companyImage = marketingDepartment.getCompanyImageScore();
		customerSatisfaction.setCompanyImage(companyImage);

		// get jobSatisfaction score from HR
		HRDepartment hrDepartment = state.getHrDepartment();
		double jss = hrDepartment.getTotalJSS();

		// get employerBranding score
		double employerBranding = jss * 0.6 + companyImage * 0.4;
		customerSatisfaction.setEmployerBranding(employerBranding);

		state.setEmployerBranding(employerBranding);

		// get logisticsIndex from Logistics
		LogisticsDepartment logisticsDepartment = state.getLogisticsDepartment();
		customerSatisfaction.setLogisticIndex(logisticsDepartment.getLogisticsIndex());

		customerSatisfaction.calculateAll(gameDate);

		// caluclate customer demand
		double qoW = this.getTotalQualityOfWorkByEmployeeType(EmployeeType.SALESPERSON);
		customerDemand.calculateAll(qoW, gameDate);
		customerDemand.calculateOverallAppealDemand(qoW, gameDate);
		customerDemand.calculateDemandPercentage(qoW, gameDate);
		/*String message = "jss= " + jss + "; companyImage=" + companyImage + "; employerBranding=" + employerBranding;
		LOGGER.info(message);*/
	}

	/**
	 * Updates the relevant values in the finance department.
	 */
	private void updateFinance() {
		GameState.getInstance().getFinanceDepartment().calculateNetWorth(GameState.getInstance().getGameDate());
		GameState.getInstance().getFinanceDepartment().updateMonthlyData(GameState.getInstance().getGameDate());
		GameState.getInstance().getFinanceDepartment().updateQuarterlyData(GameState.getInstance().getGameDate());
		GameState.getInstance().getFinanceDepartment().updateNetWorthDifference(GameState.getInstance().getGameDate());
		GameState.getInstance().getFinanceDepartment().updateCashDifference(GameState.getInstance().getGameDate());
	}

	private void updateHR() {
		GameState.getInstance().getHrDepartment().updateEmployeeHistory(GameState.getInstance().getGameDate());
		GameState.getInstance().getHrDepartment().calculateAndUpdateEmployeesMeta();
	}

	/**
	 * Updates the relevant values in the logistics department.
	 */
	private void updateLogistics() {
		GameState.getInstance().getInternalFleet().calculateAll();
		if (GameState.getInstance().getLogisticsDepartment().getExternalPartner() != null) {
			GameState.getInstance().getLogisticsDepartment().getExternalPartner().calculateExternalLogisticsIndex();
		}
		GameState.getInstance().getLogisticsDepartment().calculateAll(GameState.getInstance().getGameDate());
		GameState.getInstance().getProductSupport().calculateAll();
	}

	private void updateMarketing() {
		//TODO update CompanyImage und EmployerBranding
		
		CustomerSatisfaction customerSatisfaction = GameState.getInstance().getCustomerSatisfaction();
		GameState.getInstance().getMarketingDepartment().setEmployerBranding(customerSatisfaction.getEmployerBranding());
		
		//TODO set values used for consultancies here!!!
		
	}

	/**
	 * Updates relevant values in the procurement department.
	 */
	private void updateProcurement() {
		GameState.getInstance().getProcurementDepartment().updateAll(GameState.getInstance().getGameDate());
	}

	/**
	 * Updates relevant values in the production department.
	 */
	private void updateProduction() {
		this.setInitialTotalEngineerQualityOfWork();
		GameState.getInstance().getProductionDepartment().calculateAll(GameState.getInstance().getGameDate());
		this.updateNumberOfProductionWorkers();
	}

	/**
	 * Updates relevant values in the warehouse department.
	 */
	private void updateWarehouse() {
		GameState.getInstance().getWarehousingDepartment().calculateAll(GameState.getInstance().getGameDate());
	}

	private void updateSalesDepartment() {
		GameState state = GameState.getInstance();
		SalesDepartment salesDepartment = state.getSalesDepartment();
		salesDepartment.generateContracts(state.getGameDate(), state.getProductionDepartment(), state.getCustomerDemand().getDemandPercentage());

	}

	/**
	 * Refresh available contracts if possible. If not possible, cost are still subtracted.
	 * Reason: The company tries to get contract offers, but is not attractive enough, since it does not
	 * have any products, production capacity.
	 *
	 * @throws LevelingRequirementNotFulFilledException Exception thrown, if {@link SalesDepartment#getLevel()} is smaller than 1. Notify the player!
	 */
	public void refreshContracts() throws LevelingRequirementNotFulFilledException {
		GameState state = GameState.getInstance();
		SalesDepartment salesDepartment = state.getSalesDepartment();
		double cost = salesDepartment.refreshAvailableContracts(state.getGameDate(), state.getProductionDepartment(), state.getCustomerDemand().getDemandPercentage());

		decreaseCash(state.getGameDate(), cost);
	}

	public void start() {
		GameThread.getInstance().run();
	}

	public void pauseGame() {
		GameThread.getInstance().pause();
	}

	public void terminateGame() {
		GameThread.getInstance().terminate();
		GameState.getInstance().resetDepartments();
		GameState.setInstance(null);
	}

	public void resumeGame() {
		GameThread.getInstance().unpause();
	}

	public boolean isGamePaused() {
		return GameThread.getInstance().isPause();
	}

	public GameThread.Speed getSpeed() {
		return GameThread.getInstance().getSpeed();
	}
	
	public void setSpeed(GameThread.Speed speed) {
		GameThread.getInstance().setSpeed(speed);
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
		this.setInitialWarehouseValues();
		this.setInitialProcurementValues();
		this.setInitialProductionValues();
	}

	private void setInitialCompanyEcoIndexValues() {

	}

	private void setInitialExternalEventsValues() {

	}

	private void setInitialCustomerValues() {

	}

	private void setInitialFinanceValues() {
		GameState.getInstance().getFinanceDepartment().calculateNetWorth(GameState.getInstance().getGameDate());
	}

	private void setInitialHRValues() {

	}

	private void setInitialLogisticsValues() {
		// Logistics.getInstance().calculateAll();
	}

	private void setInitialMarketingValues() {

	}

	// TODO once procurement implementation is ready
	private void setInitialProcurementValues() {

	}

	private void setInitialProductionValues() {
		GameState.getInstance().getProductionDepartment().calculateAll(GameState.getInstance().getGameDate());
	}

	private void setInitialWarehouseValues() {
		// TODO really needed?
		GameState.getInstance().getWarehousingDepartment().calculateAll(GameState.getInstance().getGameDate());
	}


	public CompanyEcoIndex.EcoIndex getEcoIndex() {
		return GameState.getInstance().getCompanyEcoIndex().getEcoIndex();
	}

	public List<ExternalEvents.ExternalEvent> getExternalEvents() {
		return GameState.getInstance().getExternalEvents().getExternalEvents();
	}

	public List<ExternalEvents.ExternalEvent> getExternalEvents(LocalDate date) {
		if(GameState.getInstance().getExternalEvents().getExternalEventsHistory().containsKey(date)) {
			return ExternalEvents.getInstance().getExternalEventsHistory().get(date);
		} else {
			return null;
		}
	}

	public Map<LocalDate, List<ExternalEvents.ExternalEvent>> getExternalEventsHistory() {
		return  GameState.getInstance().getExternalEvents().getExternalEventsHistory();
	}


	/*
	 * FINANCE
	 */

	public double getCash() {
		return GameState.getInstance().getFinanceDepartment().getCash();
	}

	public double getNetWorth() {
		// double netWorth = Finance.getInstance().calculateNetWorth(gameDate);
		double netWorth = GameState.getInstance().getFinanceDepartment().getNetWorth();
		return netWorth;
	}

    public List<Investment> getInvestments() {
        return GameState.getInstance().getFinanceDepartment().getInvestments();
    }

	/**
	 * Generates a selection of three loans according to p.76. The actual generation is implemented in the banking
	 * system. The desired loan amount can not exceed 70 percent of the current net worth.
	 * @param loanAmount The requested loan amount.
	 * @param gameDate The current date in the game.
	 * @param locale The Locale object of the desired language.
	 * @return Returns a list of three loans with different characteristics.
	 */
	public List<Loan> generateLoanSelection(double loanAmount, LocalDate gameDate, Locale locale) {
		return GameState.getInstance().getFinanceDepartment().generateLoanSelection(loanAmount, gameDate, locale);
	}

	/**
	 * Adds a loan to the company (implemented in the banking system) and updates the cash and liabilities amount
	 * accordingly.
	 * @param loan The loan to be added.
	 * @param loanDate The current date in the game.
	 */
	public void addLoan(Loan loan, LocalDate loanDate) {
		GameState.getInstance().getFinanceDepartment().addLoan(loan, loanDate);
	}

	public List<Loan> getLoans() {
		return GameState.getInstance().getFinanceDepartment().getLoans();
	}

	/**
	 * Calculates the resell price of assets according to p.72.
	 * @param purchasePrice The purchase price of the asset.
	 * @param usefulLife The useful life of the asset.
	 * @param timeUsed The time that the asset was used for.
	 * @return Returns the resell price of the asset.
	 */
	public double calculateResellPrice(double purchasePrice, double usefulLife, double timeUsed) {
		return GameState.getInstance().getFinanceDepartment().calculateResellPrice(purchasePrice, usefulLife, timeUsed);
	}

	/**
	 * Processes the purchase of a truck. The new truck is added to the internal fleet, the cash and net worth amount
	 * are decreased, and the asset amount is increased according to the purchase and resell price.
	 * @param truck The truck to be bought.
	 * @param gameDate The current date in the game
	 * @throws NotEnoughTruckCapacityException if the internal fleet does not have enough capacity.
	 */
	public void buyTruck(Truck truck, LocalDate gameDate) throws NotEnoughTruckCapacityException {
		GameState.getInstance().getFinanceDepartment().buyTruck(truck, gameDate);
	}

	/**
	 * Processes the sale of a truck. The truck is removed from the internal fleet, the cash amount is increased, and
	 * the asset amount is decreased according to the resell price.
	 * @param truck The truck to be sold.
	 * @param gameDate The current date in the game.
	 */
	public void sellTruck(Truck truck, LocalDate gameDate) {
		GameState.getInstance().getFinanceDepartment().sellTruck(truck, gameDate);
	}

	public double getAssets() {
		return GameState.getInstance().getFinanceDepartment().getAssets();
	}

	public double getLiabilities() {
		return GameState.getInstance().getFinanceDepartment().getLiabilities();
	}

	/**
	 * Calculates the net worth based on the cash, assets, and liabilities according to p.70 and adds it to the
	 * respective history.
	 * @param gameDate The current date in the game.
	 * @return Returns the net worth.
	 */
	public double calculateNetWorth(LocalDate gameDate) {
		return GameState.getInstance().getFinanceDepartment().calculateNetWorth(gameDate);
	}

	/**
	 * Increases the cash amount of the company by the specified amount. The new cash amount is added to the respective
	 * history and the monthly data and cash difference are updated.
	 * @param gameDate The current date in the game.
	 * @param amount The amount by which the cash should be increased.
	 */
	public void increaseCash(LocalDate gameDate, double amount) {
		GameState.getInstance().getFinanceDepartment().increaseCash(gameDate, amount);
	}

	/**
	 * Decreases the cash amount of the company by the specified amount. If the new cash amount is < 0, the game is
	 * over. Otherwise, the new cash amount is added to the respective history and the monthly data and cash difference
	 * are updated.
	 * @param gameDate The current date in the game.
	 * @param amount The amount by which the cash should be decreased.
	 */
	public void decreaseCash(LocalDate gameDate, double amount) {
		GameState.getInstance().getFinanceDepartment().decreaseCash(gameDate, amount);
	}

	/**
	 * Increases the net worth of the company by the specified amount. The new net worth is added to the respective
	 * history and the monthly data and net worth difference are updated.
	 * @param gameDate The current date in the game.
	 * @param amount The amount by which the net worth should be increased.
	 */
	public void increaseNewWorth(LocalDate gameDate, double amount) {
		GameState.getInstance().getFinanceDepartment().increaseNetWorth(gameDate, amount);
	}

	/**
	 * Decreases the net worth of the company by the specified amount. The new net worth is added to the respective
	 * history and the monthly data and net worth difference are updated.
	 * @param gameDate The current date in the game.
	 * @param amount The amount by which the net worth should be decreased.
	 */
	public void decreaseNetWorth(LocalDate gameDate, double amount) {
		GameState.getInstance().getFinanceDepartment().decreaseNetWorth(gameDate, amount);
	}

	/**
	 * Increases the asset amount of the company by the specified amount. The new asset amount is added to the
	 * respective history and the monthly data is updated.
	 * @param gameDate The current date in the game.
	 * @param amount The amount by which the assets should be increased.
	 */
	public void increaseAssets(LocalDate gameDate, double amount) {
		GameState.getInstance().getFinanceDepartment().increaseAssets(gameDate, amount);
	}

	/**
	 * Decreases the asset amount of the company by the specified amount. The new asset amount is added to the
	 * respective history and the monthly data is updated.
	 * @param gameDate The current date in the game.
	 * @param amount The amount by which the assets should be decreased.
	 */
	public void decreaseAssets(LocalDate gameDate, double amount) {
		GameState.getInstance().getFinanceDepartment().decreaseAssets(gameDate, amount);
	}

	/**
	 * Increases the liabilities amount of the company by the specified amount. The new liabilities amount is added to
	 * the respective history and the monthly data is updated.
	 * @param gameDate The current date in the game.
	 * @param amount The amount by which the liabilities should be increased.
	 */
	public void increaseLiabilities(LocalDate gameDate, double amount) {
		GameState.getInstance().getFinanceDepartment().increaseLiabilities(gameDate, amount);
	}

	public double getRealEstateInvestmentAmount() {
		return GameState.getInstance().getFinanceDepartment().getRealEstateInvestmentAmount();
	}

	public double getStocksInvestmentAmount() {
		return GameState.getInstance().getFinanceDepartment().getStocksInvestmentAmount();
	}

	public double getVentureCapitalInvestmentAmount() {
		return GameState.getInstance().getFinanceDepartment().getVentureCapitalInvestmentAmount();
	}

	/**
	 * Increases the investment amount for a specified investment type if the company has enough cash. Consequently, the
	 * cash is decreased and the asset amount is increased.
	 * @param gameDate The current date in the game.
	 * @param amount The amount to be invested.
	 * @param investmentType The type of investment that should be invested in.
	 * @return Returns true if the investment was processed successfully. Returns false otherwise.
	 */
	public boolean increaseInvestmentAmount(LocalDate gameDate, double amount, Investment.InvestmentType investmentType){
		return GameState.getInstance().getFinanceDepartment().increaseInvestmentAmount(gameDate, amount, investmentType);
	}

	/**
	 * Decreases the investment amount for a specified investment type. If the specified amount exceeds the amount that
	 * the company owns, the maximum possible amount is used. Consequently, the cash is increased and the asset amount
	 * is decreased.
	 * @param gameDate The current date in the game.
	 * @param amount The amount to be disinvested.
	 * @param investmentType The type of investment that should be disinvested in.
	 * @return Returns true if the disinvestment was processed successfully. Returns false otherwise.
	 */
    public boolean decreaseInvestmentAmount(LocalDate gameDate, double amount, Investment.InvestmentType investmentType){
        return GameState.getInstance().getFinanceDepartment().decreaseInvestmentAmount(gameDate, amount, investmentType);
    }

	public Map<String, String[]> getMonthlyData() {
		return GameState.getInstance().getFinanceDepartment().getMonthlyData();
	}

	public Map<String, String[]> getQuarterlyData() {
		return GameState.getInstance().getFinanceDepartment().getQuarterlyData();
	}

	public Double getNetWorthDifference(){
		return GameState.getInstance().getFinanceDepartment().getNetWorthDifference();
	}

	public Double getCashDifference(){
		return GameState.getInstance().getFinanceDepartment().getCashDifference();
	}

	/*
	 * LOGISTICS
	 */

	/**
	 * Generates a selection of external logistics partners with different characteristics according to p.52.
	 * @return Returns a list of 9 different external logistics partners.
	 */
	public List<ExternalPartner> generateExternalPartnerSelection() {
		return GameState.getInstance().getLogisticsDepartment().generateExternalPartnerSelection();
	}

	/**
	 * Generates a selection of trucks with different characteristics according to p.49.
	 * @param locale The Locale object of the desired language.
	 * @return Returns a list of 6 different trucks.
	 */
	public List<Truck> generateTruckSelection(Locale locale) {
		return GameState.getInstance().getLogisticsDepartment().generateTruckSelection(locale);
	}

	public ExternalPartner getExternalPartner() {
		return GameState.getInstance().getLogisticsDepartment().getExternalPartner();
	}

	public List<ExternalPartner> getExternalPartnerSelection() {
		return GameState.getInstance().getLogisticsDepartment().getExternalPartnerSelection();
	}

	public InternalFleet getInternalFleet() {
		return GameState.getInstance().getLogisticsDepartment().getInternalFleet();
	}

	/**
	 * Adds an external logistics partner.
	 * @param externalPartner The external logistics partner to be added.
	 */
	public void addExternalPartner(ExternalPartner externalPartner) {
		GameState.getInstance().getLogisticsDepartment().addExternalPartner(externalPartner);
	}

	/**
	 * Removes the currently hired external logistics partner.
	 */
	public void removeExternalPartner() {
		GameState.getInstance().getLogisticsDepartment().removeExternalPartner();
	}

	/**
	 * Generates a list of the available support types to choose from.
	 * @return Returns a list of the available support types.
	 */
	public List<ProductSupport.SupportType> generateSupportTypeSelection() {
		return GameState.getInstance().getProductSupport().generateSupportTypeSelection();
	}

	/**
	 * Adds a new support type to the list of support types provided by the company. Only possible if an external
	 * support partner is hired.
	 * @param supportType The new support type to be added.
	 * @throws NoExternalSupportPartnerException if no external support partner is hired.
	 */
	public void addSupport(ProductSupport.SupportType supportType) throws NoExternalSupportPartnerException{
		GameState.getInstance().getProductSupport().addSupport(supportType);
	}

	/**
	 * Removes a support type from the list of support types provided by the company.
	 * @param supportType The support type to be removed.
	 */
	public void removeSupport(ProductSupport.SupportType supportType) {
		GameState.getInstance().getProductSupport().removeSupport(supportType);
	}

	/**
	 * Generates a list of the available external support partners to choose from.
	 * @return Returns a list of the available external support partners.
	 */
	public List<ProductSupport.ExternalSupportPartner> generateExternalSupportPartnerSelection() {
		return GameState.getInstance().getProductSupport().generateExternalSupportPartnerSelection();
	}

	/**
	 * Hires the specified external support partner.
	 * @param externalSupportPartner The external support partner to be hired.
	 */
	public void addExternalSupportPartner(ProductSupport.ExternalSupportPartner externalSupportPartner) {
		GameState.getInstance().getProductSupport().addExternalSupportPartner(externalSupportPartner);
	}

	/**
	 * Fires the currently hired external support partner.
	 */
	public void removeExternalSupportPartner() {
		GameState.getInstance().getProductSupport().removeExternalSupportPartner();
	}

	public List<ProductSupport.SupportType> getSupportTypes() {
		return GameState.getInstance().getProductSupport().getSupportTypes();
	}

	public ProductSupport.ExternalSupportPartner getExternalSupportPartner() {
		return GameState.getInstance().getProductSupport().getExternalSupportPartner();
	}

	public int getTotalSupportTypeQuality() {
		return GameState.getInstance().getProductSupport().getTotalSupportTypeQuality();
	}

	public double getTotalSupportQuality() {
		return GameState.getInstance().getProductSupport().getTotalSupportQuality();
	}

	public double getTotalSupportCosts() {
		return GameState.getInstance().getProductSupport().getTotalSupportCosts();
	}

	/*
	 * PROCUREMENT
	 */

	/**
	 * Gets component name.
	 *
	 * @param component the component
	 * @return the component name
	 */
	public String getComponentName(ComponentType component) {
		return component.getComponentName();
	}

	/**
	 * Gets component level.
	 *
	 * @param component the component
	 * @return the component level
	 */
	public int getComponentLevel(ComponentType component) {
		return component.getComponentLevel();
	}

	/**
	 * Gets initial component price.
	 *
	 * @param component the component
	 * @return the initial component price
	 */
	public double getInitialComponentPrice(ComponentType component) {
		return component.getInitialComponentPrice();
	}

	/**
	 * Gets component base utility.
	 *
	 * @param component the component
	 * @return the component base utility
	 */
	public int getComponentBaseUtility(ComponentType component) {
		return component.getBaseUtility();
	}

	/**
	 * Gets component availability date.
	 *
	 * @param component the component
	 * @return the component availability date
	 */
	public int getComponentAvailabilityDate(ComponentType component) {
		return component.getAvailabilityDate();
	}

	/**
	 * Gets component supplier category.
	 *
	 * @param component the component
	 * @return the component supplier category
	 */
	public SupplierCategory getComponentSupplierCategory(Component component) {
		return component.getSupplierCategory();
	}

	/**
	 * Gets component supplier cost multiplicator.
	 *
	 * @param component the component
	 * @return the component supplier cost multiplicator
	 */
	public double getComponentSupplierCostMultiplicator(Component component) {
		return component.getSupplierCostMultiplicator();
	}

	/**
	 * Gets component supplier quality.
	 *
	 * @param component the component
	 * @return the component supplier quality
	 */
	public int getComponentSupplierQuality(Component component) {
		return component.getSupplierQuality();
	}

	/**
	 * Gets component supplier eco index.
	 *
	 * @param component the component
	 * @return the component supplier eco index
	 */
	public int getComponentSupplierEcoIndex(Component component) {
		return component.getSupplierEcoIndex();
	}

	/**
	 * Gets component base cost.
	 *
	 * @param component the component
	 * @return the component base cost
	 */
	public double getComponentBaseCost(Component component) {
		return component.getBaseCost();
	}

	/**
	 * Gets all available components.
	 *
	 * @return the all available components
	 */
	public List<ComponentType> getAllAvailableComponents() {
		return GameState.getInstance().getProductionDepartment().getAllAvailableComponents(GameState.getInstance().getGameDate());
	}

	/**
	 * Gets available components of component category.
	 *
	 * @param componentCategory the component category
	 * @return the available components of component category
	 */
	public List<ComponentType> getAvailableComponentsOfComponentCategory(ComponentCategory componentCategory) {
		return GameState.getInstance().getProductionDepartment()
				.getAvailableComponentsOfComponentCategory(GameState.getInstance().getGameDate(), componentCategory);
	}


	/**
	 * Buy (order) components.
	 *
	 * @param component the component
	 * @param quantity  the quantity of the component
	 * @return the costs of the order
	 */
	public double buyComponents(Component component, int quantity) {
		return GameState.getInstance().getProcurementDepartment().buyComponents(GameState.getInstance().getGameDate(), component, quantity, getFreeStorage());
    }

	/**
	 * Buy (order) components.
	 *
	 * @param gameDate    the game date
	 * @param component   the component
	 * @param quantity    the quantity of the component
	 * @param freeStorage the free storage of the warehouse
	 * @return the costs of the order
	 */
	public double buyComponents(LocalDate gameDate, Component component, int quantity, int freeStorage) {
		return GameState.getInstance().getProcurementDepartment().buyComponents(gameDate, component, quantity, freeStorage);
	}

	/**
	 * Receives the components of the component orders in the procurement department.
	 * It checks whether the the arrival date of the component is met and adds them to receivedComponents of the procurement department.
	 */
	public void receiveComponents() {
		GameState.getInstance().getProcurementDepartment().receiveComponents(GameState.getInstance().getGameDate());
	}

	/**
	 * Gets received components of the procurement department.
	 *
	 * @return the received components
	 */
	public Map<Component, Integer> getReceivedComponents() {
		return GameState.getInstance().getProcurementDepartment().getReceivedComponents();
	}

	/**
	 * Gets component orders of the procurement department.
	 *
	 * @return the component orders
	 */
	public List<ComponentOrder> getComponentOrders() {
		return GameState.getInstance().getProcurementDepartment().getComponentOrders();
	}

	/**
	 * Gets quantity of ordered components of the procurement department.
	 *
	 * @return the quantity of ordered components
	 */
	public int getQuantityOfOrderedComponents() {
		return GameState.getInstance().getProcurementDepartment().getQuantityOfOrderedComponents();
	}

	/**
	 * Clear received components of the procurement department.
	 */
	public void clearReceivedComponents() {
		GameState.getInstance().getProcurementDepartment().clearReceivedComponents();
	}



	/*
	 * PRODUCTION
	 */

	/**
	 * Sets initial total engineer quality of work of the production.
	 * It is set every day.
	 */
	public void setInitialTotalEngineerQualityOfWork() {
		GameState.getInstance().getProductionDepartment().setInitialTotalEngineerQualityOfWork(GameState.getInstance().getHrDepartment().getTotalQualityOfWorkByEmployeeType(EmployeeType.ENGINEER));
	}

	/**
	 * Update number of production workers.
	 * It is set every day.
	 */
	public void updateNumberOfProductionWorkers() {
		Map<EmployeeType, Team> teams = HRDepartment.getInstance().getTeams();
		int numberOfProductionWorkers = teams.get(EmployeeType.PRODUCTION_WORKER).getTeam().size();
		GameState.getInstance().getProductionDepartment().setNumberOfProductionWorkers(numberOfProductionWorkers);
	}

	/**
	 * Gets produced products of the production.
	 *
	 * @return the map of produced products and their respective quantity
	 */
	/* Production getter */
	public Map<Product, Integer> getProducedProducts() {
		return GameState.getInstance().getProductionDepartment().getNumberProducedProducts();
	}

	/**
	 * Gets machines of the production.
	 *
	 * @return the list of machines
	 */
	public List<Machinery> getMachines() {
		return GameState.getInstance().getProductionDepartment().getMachines();
	}

	/**
	 * Gets production technology of the production.
	 *
	 * @return the production technology
	 */
	public ProductionTechnology getProductionTechnology() {
		return GameState.getInstance().getProductionDepartment().getProductionTechnology();
	}

	/**
	 * Gets quality assurance of the production.
	 *
	 * @return the quality assurance
	 */
	public ProductionInvestment getQualityAssurance() {
		return GameState.getInstance().getProductionDepartment().getQualityAssurance();
	}

	/**
	 * Gets process automation of the production.
	 *
	 * @return the process automation
	 */
	public ProductionInvestment getProcessAutomation() {
		return GameState.getInstance().getProductionDepartment().getProcessAutomation();
	}

	/**
	 * Gets total engineer productivity of the production.
	 *
	 * @return the total engineer productivity
	 */
	public double getTotalEngineerProductivity() {
		return GameState.getInstance().getProductionDepartment().getTotalEngineerProductivity();
	}

	/**
	 * Gets system security of the production.
	 *
	 * @return the system security
	 */
	public ProductionInvestment getSystemSecurity() {
		return GameState.getInstance().getProductionDepartment().getSystemSecurity();
	}

	/**
	 * Gets production variable costs.
	 *
	 * @return the production variable costs
	 */
	public double getProductionVariableCosts() {
		return GameState.getInstance().getProductionDepartment().getProductionVariableCosts();
	}

	/**
	 * Gets production fix costs.
	 *
	 * @return the production fix costs
	 */
	public double getProductionFixCosts() {
		return GameState.getInstance().getProductionDepartment().getProductionFixCosts();
	}

	/**
	 * Gets number units produced per month of the production.
	 *
	 * @return the number units produced per month
	 */
	public double getNumberUnitsProducedPerMonth() {
		return GameState.getInstance().getProductionDepartment().getNumberUnitsProducedPerMonth();
	}

	/**
	 * Gets monthly available machine capacity of the production.
	 *
	 * @return the monthly available machine capacity
	 */
	public double getMonthlyAvailableMachineCapacity() {
		return GameState.getInstance().getProductionDepartment().getMonthlyAvailableMachineCapacity();
	}

	/**
	 * Gets manufacture efficiency of the production.
	 *
	 * @return the manufacture efficiency
	 */
	public double getManufactureEfficiency() {
		return GameState.getInstance().getProductionDepartment().getManufactureEfficiency();
	}

	/**
	 * Gets production process productivity.
	 *
	 * @return the production process productivity
	 */
	public double getProductionProcessProductivity() {
		return GameState.getInstance().getProductionDepartment().getProductionProcessProductivity();
	}

	/**
	 * Gets normalized production process productivity.
	 *
	 * @return the normalized production process productivity
	 */
	public double getNormalizedProductionProcessProductivity() {
		return GameState.getInstance().getProductionDepartment().getNormalizedProductionProcessProductivity();
	}

	/**
	 * Gets daily machine capacity of the production.
	 *
	 * @return the daily machine capacity
	 */
	public int getDailyMachineCapacity() {
		return GameState.getInstance().getProductionDepartment().getDailyMachineCapacity();
	}

	/**
	 * Gets boolean flag whether machine slots available of the production.
	 *
	 * @return the boolean whether machine slots available
	 */
	/* machinery game mechanics */
	public boolean getMachineSlotsAvailable() {
		return GameState.getInstance().getProductionDepartment().getMachineSlotsAvailable();
	}

	/**
	 * Gets the number production slots for machines.
	 *
	 * @return the number production slots
	 */
	public int getProductionSlots() {
		return GameState.getInstance().getProductionDepartment().getProductionSlots();
	}

	/**
	 * Buys machinery.
	 *
	 * @param machinery the machinery
	 * @param gameDate  the game date
	 * @return the cost of buying
	 * @throws NoMachinerySlotsAvailableException the no machinery slots available exception
	 */
	public double buyMachinery(Machinery machinery, LocalDate gameDate) throws NoMachinerySlotsAvailableException {
		try {
			double purchasePrice = GameState.getInstance().getProductionDepartment().buyMachinery(machinery, gameDate);
			GameState.getInstance().getFinanceDepartment().buyMachinery(machinery, gameDate);
			return purchasePrice;
		} catch (NoMachinerySlotsAvailableException e) {
			throw new NoMachinerySlotsAvailableException("No more Capacity available to buy new Machine.");
		}
	}

	/**
	 * Sells machinery double.
	 *
	 * @param machinery the machinery
	 * @param gameDate  the game date
	 * @return the revenues of selling
	 */
	public double sellMachinery(Machinery machinery, LocalDate gameDate) {
		double resellPrice = GameState.getInstance().getProductionDepartment().sellMachinery(machinery);
		GameState.getInstance().getFinanceDepartment().sellMachinery(machinery, gameDate);
		return resellPrice;
	}

	/**
	 * Gets machinery resell prices.
	 * Gets the resell prices of machines without selling them.
	 *
	 * @return the machinery resell prices
	 */
	public Map<Machinery, Double> getMachineryResellPrices() {
		return GameState.getInstance().getProductionDepartment().calculateMachineryResellPrices();
	}

	/**
	 * Maintains and repairs machinery.
	 *
	 * @param machinery the machinery
	 * @return the costs
	 */
	public double maintainAndRepairMachinery(Machinery machinery) {
		return GameState.getInstance().getProductionDepartment().maintainAndRepairMachinery(machinery,
				GameState.getInstance().getGameDate());
	}

	/**
	 * Upgrades machinery double.
	 *
	 * @param machinery the machinery
	 * @return the costs
	 */
	public double upgradeMachinery(Machinery machinery) {
		return GameState.getInstance().getProductionDepartment().upgradeMachinery(machinery, GameState.getInstance().getGameDate());
	}

	/**
	 * Gets machinery production technology.
	 *
	 * @param machinery the machinery
	 * @return the machinery production technology
	 */
	public ProductionTechnology getMachineryProductionTechnology(Machinery machinery) {
		return machinery.getProductionTechnology();
	}

	/**
	 * Gets machinery purchase price.
	 *
	 * @return the machinery purchase price
	 */
	public double getMachineryPurchasePrice() {
		return GameState.getInstance().getProductionDepartment().getMachineryPurchasePrice(GameState.getInstance().getGameDate());
	}

	/**
	 * Gets machinery capacity.
	 *
	 * @param machinery the machinery
	 * @return the machinery capacity
	 */
	public int getMachineryCapacity(Machinery machinery) {
		return machinery.getMachineryCapacity();
	}

	/**
	 * Gets machinery purchase price.
	 *
	 * @param machinery the machinery
	 * @return the machinery purchase price
	 */
	public double getMachineryPurchasePrice(Machinery machinery) {
		return machinery.getPurchasePrice();
	}

	/**
	 * Gets machinery resell price.
	 *
	 * @param machinery the machinery
	 * @return the machinery resell price
	 */
	public double getMachineryResellPrice(Machinery machinery) {
		return machinery.getResellPrice();
	}

	/**
	 * Gets machinery depreciation.
	 *
	 * @param machinery the machinery
	 * @return the machinery depreciation
	 */
	public double getMachineryDepreciation(Machinery machinery) {
		return machinery.getMachineryDepreciation();
	}

	/**
	 * Gets machinery last investment.
	 *
	 * @param machinery the machinery
	 * @return the machinery last investment
	 */
	public LocalDate getMachineryLastInvestment(Machinery machinery) {
		return machinery.getLastInvestmentDate();
	}

	/**
	 * Gets machinery years since last investment.
	 *
	 * @param machinery the machinery
	 * @return the machinery years since last investment
	 */
	public int getMachineryYearsSinceLastInvestment(Machinery machinery) {
		return machinery.getYearsSinceLastInvestment();
	}

	/**
	 * Gets machinery useful life.
	 *
	 * @param machinery the machinery
	 * @return the machinery useful life
	 */
	public int getMachineryUsefulLife(Machinery machinery) {
		return machinery.getUsefulLife();
	}

	/**
	 * Gets machinery purchase date.
	 *
	 * @param machinery the machinery
	 * @return the machinery purchase date
	 */
	public LocalDate getMachineryPurchaseDate(Machinery machinery) {
		return machinery.getPurchaseDate();
	}

	/**
	 * Gets product costs.
	 *
	 * @param product the product
	 * @return the product costs
	 */
	/* product game mechanics */
	public double getProductCosts(Product product) {
		return product.getProductCosts(GameState.getInstance().getGameDate());
	}

	/**
	 * Launches a product.
	 * It checks whether the product category was unlocked and whether the product name is unique.
	 * Adds the product to the list of launched products.
	 *
	 * @param product the product
	 * @return the costs
	 * @throws ProductCategoryNotUnlockedException the product category not unlocked exception
	 * @throws ProductNameAlreadyInUseException    the product name already in use exception
	 */
	public double launchProduct(Product product) throws ProductCategoryNotUnlockedException, ProductNameAlreadyInUseException {
		try {
			LocalDate gameDate = GameState.getInstance().getGameDate();
			ResearchAndDevelopmentDepartment researchAndDevelopmentDepartment = GameState.getInstance().getResearchAndDevelopmentDepartment();
			boolean productCategoryUnlocked = researchAndDevelopmentDepartment.isCategoryUnlocked(product.getProductCategory());
			double launchCosts = GameState.getInstance().getProductionDepartment().launchProduct(product, gameDate, productCategoryUnlocked);
			GameState.getInstance().getFinanceDepartment().decreaseCash(gameDate, launchCosts);
			return launchCosts;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Gets product launch costs
	 *
	 * @return products launch costs
	 */
	public double getProductLaunchCosts() {
		return GameState.getInstance().getProductionDepartment().getProductLaunchCosts();
	}

	/**
	 * Produces product double.
	 * It checks whether the product can be produced in the specified quantity (enough components, components unlocked, enough free storage, enough machine capacity),
	 * otherwise it throws an exception.
	 *
	 * @param product  the product
	 * @param quantity the quantity
	 * @return the costs
	 * @throws NotEnoughComponentsException      the not enough components exception
	 * @throws NotEnoughMachineCapacityException the not enough machine capacity exception
	 * @throws NotEnoughFreeStorageException     the not enough free storage exception
	 * @throws ComponentLockedException          the component locked exception
	 */
	public double produceProduct(Product product, int quantity) throws NotEnoughComponentsException, NotEnoughMachineCapacityException, NotEnoughFreeStorageException, ComponentLockedException {
		try {
		    ResearchAndDevelopmentDepartment researchAndDevelopmentDepartment = GameState.getInstance().getResearchAndDevelopmentDepartment();
		    boolean allComponentsUnlocked = true;
		    for(Component component : product.getComponents()) {
		        if(!researchAndDevelopmentDepartment.isComponentUnlocked(component)) {
		            allComponentsUnlocked = false;
                }
            }
			return GameState.getInstance().getProductionDepartment().produceProduct(product, quantity,
					GameState.getInstance().getWarehousingDepartment().calculateFreeStorage(), allComponentsUnlocked);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Gets amount of products in production.
	 *
	 * @param product the product
	 * @return the amount product in production
	 */
	public double getAmountProductInProduction(Product product) {
		return GameState.getInstance().getProductionDepartment().getAmountInProduction(product);
	}

	/**
	 * Sets product sales price.
	 *
	 * @param product    the product
	 * @param salesPrice the sales price
	 */
	public void setProductSalesPrice(Product product, double salesPrice) {
		GameState.getInstance().getProductionDepartment().setProductSalesPrice(product, salesPrice);
	}

	/**
	 * Gets product name.
	 *
	 * @param product the product
	 * @return the product name
	 */
	public String getProductName(Product product) {
		return product.getProductName();
	}

	/**
	 * Gets product category.
	 *
	 * @param product the product
	 * @return the product category
	 */
	public ProductCategory getProductCategory(Product product) {
		return product.getProductCategory();
	}

	/**
	 * Gets product components.
	 *
	 * @param product the product
	 * @return the product components
	 */
	public List<Component> getProductComponents(Product product) {
		return product.getComponents();
	}

	/**
	 * Gets product procurement quality.
	 *
	 * @param product the product
	 * @return the product procurement quality
	 */
	public double getProductProcurementQuality(Product product) {
		return product.getTotalProcurementQuality();
	}

	/**
	 * Gets product total product quality.
	 *
	 * @param product the product
	 * @return the product total product quality
	 */
	public double getProductTotalProductQuality(Product product) {
		return product.getTotalProductQuality();
	}

	/**
	 * Gets product launch date.
	 *
	 * @param product the product
	 * @return the product launch date
	 */
	public LocalDate getProductLaunchDate(Product product) {
		return product.getLaunchDate();
	}

	/**
	 * Gets product component costs.
	 *
	 * @param product the product
	 * @return the product component costs
	 */
	public double getProductComponentCosts(Product product) {
		return product.getTotalComponentCosts();
	}

	/**
	 * Gets total product costs.
	 *
	 * @param product the product
	 * @return the total product costs
	 */
	public double getTotalProductCosts(Product product) {
		return product.getTotalProductCosts();
	}

	/**
	 * Gets products sales price.
	 *
	 * @param product the product
	 * @return the products sales price
	 */
	public double getProductsSalesPrice(Product product) {
		return product.getSalesPrice();
	}

	/**
	 * Gets product profit margin.
	 *
	 * @param product the product
	 * @return the product profit margin
	 */
	public double getProductProfitMargin(Product product) {
		return product.getProfitMargin();
	}

	/**
	 * Gets launched products.
	 *
	 * @return the launched products
	 */
	public List<Product> getLaunchedProducts() {
		return GameState.getInstance().getProductionDepartment().getLaunchedProducts();
	}

	/**
	 * Calculate average eco index of launched products.
	 *
	 * @return the average eco index of launched products
	 */
	public double calculateAverageEcoIndexOfLaunchedProducts() {
	    return GameState.getInstance().getProductionDepartment().calculateAverageEcoIndexOfLaunchedProducts();
    }

	/* production investment game mechanics */

	/**
	 * Gets production investment price.
	 * Gets the price without actually investing in the production investment.
	 *
	 * @param productionInvestmentLevel the production investment level
	 * @return the production investment price for that investment level
	 */
	public double getProductionInvestmentPrice(ProductionInvestmentLevel productionInvestmentLevel) {
		return GameState.getInstance().getProductionDepartment().getProductionInvestmentPrice(productionInvestmentLevel);
	}

	/**
	 * Invest in system security.
	 * It checks the number of trained engineers before calling the method of the production department.
	 *
	 * @param level the level
	 * @return the costs
	 * @throws NotEnoughTrainedEngineersException the not enough trained engineers exception
	 */
	public double investInSystemSecurity(int level) throws NotEnoughTrainedEngineersException{
		try {
			int numberOfTrainedEngineers = 0;
			Team team = GameState.getInstance().getHrDepartment().getEngineerTeam();
			for (Employee employee : team.getTeam()) {
				if (employee.getTrainingsList().size() > 0) {
					numberOfTrainedEngineers++;
				}
			}
			return GameState.getInstance().getProductionDepartment().investInSystemSecurity(level, GameState.getInstance().getGameDate(), numberOfTrainedEngineers);
		} catch (NotEnoughTrainedEngineersException e) {
			throw e;
		}
	}

	/**
	 * Invest in quality assurance.
	 * It checks the number of trained engineers before calling the method of the production department.
	 *
	 * @param level the level
	 * @return the costs
	 * @throws NotEnoughTrainedEngineersException the not enough trained engineers exception
	 */
	public double investInQualityAssurance(int level) throws NotEnoughTrainedEngineersException{
		try {
			int numberOfTrainedEngineers = 0;
			Team team = GameState.getInstance().getHrDepartment().getEngineerTeam();
			for (Employee employee : team.getTeam()) {
				if (employee.getTrainingsList().size() > 0) {
					numberOfTrainedEngineers++;
				}
			}
			return GameState.getInstance().getProductionDepartment().investInQualityAssurance(level, GameState.getInstance().getGameDate(), numberOfTrainedEngineers);
		} catch (NotEnoughTrainedEngineersException e) {
			throw e;
		}
	}

	/**
	 * Invest in process automation.
	 * It checks the number of trained engineers before calling the method of the production department.
	 *
	 * @param level the level
	 * @return the costs
	 * @throws NotEnoughTrainedEngineersException the not enough trained engineers exception
	 */
	public double investInProcessAutomation(int level) throws NotEnoughTrainedEngineersException {
		try {
			int numberOfTrainedEngineers = 0;
			Team team = GameState.getInstance().getHrDepartment().getEngineerTeam();
			for (Employee employee : team.getTeam()) {
				if (employee.getTrainingsList().size() > 0) {
					numberOfTrainedEngineers++;
				}
			}
			return GameState.getInstance().getProductionDepartment().investInProcessAutomation(level, GameState.getInstance().getGameDate(), numberOfTrainedEngineers);
		} catch (NotEnoughTrainedEngineersException e) {
			throw e;
		}
	}

	/**
	 * Gets last investment date system security.
	 *
	 * @return the last investment date system security
	 */
	public LocalDate getLastInvestmentDateSystemSecurity() {
		return GameState.getInstance().getProductionDepartment().getSystemSecurity().getLastInvestmentDate();
	}

	/**
	 * Gets last investment date quality assurance.
	 *
	 * @return the last investment date quality assurance
	 */
	public LocalDate getLastInvestmentDateQualityAssurance() {
		return GameState.getInstance().getProductionDepartment().getQualityAssurance().getLastInvestmentDate();
	}

	/**
	 * Gets last investment date process automation.
	 *
	 * @return the last investment date process automation
	 */
	public LocalDate getLastInvestmentDateProcessAutomation() {
		return GameState.getInstance().getProductionDepartment().getProcessAutomation().getLastInvestmentDate();
	}

	/**
	 * Gets system security level.
	 *
	 * @return the system security level
	 */
	public int getSystemSecurityLevel() {
		return GameState.getInstance().getProductionDepartment().getSystemSecurity().getLevel();
	}

	/**
	 * Gets quality assurance level.
	 *
	 * @return the quality assurance level
	 */
	public int getQualityAssuranceLevel() {
		return GameState.getInstance().getProductionDepartment().getQualityAssurance().getLevel();
	}

	/**
	 * Gets process automation level.
	 *
	 * @return the process automation level
	 */
	public int getProcessAutomationLevel() {
		return GameState.getInstance().getProductionDepartment().getProcessAutomation().getLevel();
	}

	/*
	 * WAREHOUSING
	 */

	/* warehousing getter and game mechanic */

	/**
	 * Gets warehouse slots of the warehousing department.
	 *
	 * @return the warehouse slots
	 */
	public int getWarehouseSlots() {
		return GameState.getInstance().getWarehousingDepartment().getWarehouseSlots();
	}

	/**
	 * Gets warehouse slots available.
	 *
	 * @return the warehouse slots available
	 */
	public boolean getWarehouseSlotsAvailable() {
		return GameState.getInstance().getWarehousingDepartment().getWarehouseSlotsAvailable();
	}

	/**
	 * Gets warehouses of the warehousing department.
	 *
	 * @return the warehouses
	 */
	public List<Warehouse> getWarehouses() {
		return GameState.getInstance().getWarehousingDepartment().getWarehouses();
	}

	/**
	 * Gets warehousing inventory of the warehousing department.
	 * The inventory includes all units (products, components) that are stored in the warehouse.
	 *
	 * @return the warehousing inventory
	 */
	public Map<Unit, Integer> getWarehousingInventory() {
		return GameState.getInstance().getWarehousingDepartment().getInventory();
	}

	/**
	 * Gets total warehousing capacity for units.
	 *
	 * @return the total warehousing capacity
	 */
	public int getTotalWarehousingCapacity() {
		return GameState.getInstance().getWarehousingDepartment().getTotalCapacity();
	}

	/**
	 * Gets free storage of the warehousing department.
	 *
	 * @return the free storage
	 */
	public int getFreeStorage() {
		return GameState.getInstance().getWarehousingDepartment().getFreeStorage();
	}

	/**
	 * Gets stored units of the warehousing department.
	 *
	 * @return the stored units
	 */
	public int getStoredUnits() {
		return GameState.getInstance().getWarehousingDepartment().getStoredUnits();
	}

	/**
	 * Gets monthly warehousing cost.
	 *
	 * @return the monthly warehousing cost
	 */
	public double getMonthlyWarehousingCost() {
		return GameState.getInstance().getWarehousingDepartment().getMonthlyCostWarehousing();
	}

	/**
	 * Gets daily warehousing storage cost.
	 *
	 * @return the daily warehousing storage cost
	 */
	public double getDailyWarehousingStorageCost() {
		return GameState.getInstance().getWarehousingDepartment().getDailyStorageCost();
	}

	/**
	 * Gets monthly warehousing storage cost.
	 *
	 * @return the monthly warehousing storage cost
	 */
	public double getMonthlyWarehousingStorageCost() {
		return GameState.getInstance().getWarehousingDepartment().getMonthlyStorageCost();
	}

	/**
	 * Gets monthly total warehousing cost.
	 *
	 * @return the monthly total warehousing cost
	 */
	public double getMonthlyTotalWarehousingCost() {
		return GameState.getInstance().getWarehousingDepartment().getMonthlyTotalCostWarehousing();
	}

	/**
	 * Sell warehouse products.
	 * Removes sold products from stored units of production department.
	 *
	 * @param unit     the product
	 * @param quantity the quantity
	 * @return the revenue
	 */
	public double sellWarehouseProducts(Unit unit, int quantity) {
		return GameState.getInstance().getWarehousingDepartment().sellWarehouseProducts(unit, quantity);
	}

	/**
	 * Sell warehouse components double.
	 * Removes sold components from stored units of production department.
	 *
	 * @param unit     the component
	 * @param quantity the quantity
	 * @return the revenue
	 */
	public double sellWarehouseComponents(Unit unit, int quantity) {
		return GameState.getInstance().getWarehousingDepartment().sellWarehouseComponents(unit, quantity);
	}

	/* warehouse game mechanics */

	/**
	 * Builds a warehouse.
	 * Adds warehouse to the list of warehouses and add its relevant properties to the warehousing department.
	 * Checks whether there are still warehouse slots available before building one.
	 *
	 * @return the costs
	 * @throws NoWarehouseSlotsAvailableException the no warehouse slots available exception
	 */
	public double buildWarehouse() throws NoWarehouseSlotsAvailableException {
		try {
			return GameState.getInstance().getWarehousingDepartment().buildWarehouse(GameState.getInstance().getGameDate());
		} catch (NoWarehouseSlotsAvailableException e) {
			throw new NoWarehouseSlotsAvailableException("No more Capacity available to build or rent a new Warehouse.");
		}
	}

	/**
	 * Rents a warehouse.
	 * Adds warehouse to the list of warehouses and add its relevant properties to the warehousing department.
	 * Checks whether there are still warehouse slots available before building one.
	 *
	 * @return the costs
	 * @throws NoWarehouseSlotsAvailableException the no warehouse slots available exception
	 */
	public double rentWarehouse() throws NoWarehouseSlotsAvailableException{
		try {
			return GameState.getInstance().getWarehousingDepartment().rentWarehouse(GameState.getInstance().getGameDate());
		} catch	(NoWarehouseSlotsAvailableException e) {
			throw new NoWarehouseSlotsAvailableException("No more Capacity available to build or rent a new Warehouse.");
		}
	}

	/**
	 * Sells a warehouse.
	 *
	 * @param warehouse the warehouse
	 * @return the resale price
	 * @throws StorageCapacityUsedException the storage capacity used exception
	 */
	public double sellWarehouse(Warehouse warehouse) throws StorageCapacityUsedException {
		try {
			return GameState.getInstance().getWarehousingDepartment().sellWarehouse(warehouse);
		} catch(StorageCapacityUsedException e) {
			throw e;
		}
	}

	/**
	 * Depreciate all warehouse resale values.
	 */
	public void depreciateAllWarehouseResaleValues() {
		GameState.getInstance().getWarehousingDepartment().depreciateAllWarehouseResaleValues(GameState.getInstance().getGameDate());
	}

	/**
	 * Gets all warehouse resale values.
	 *
	 * @return the all warehouse resale values
	 */
	public Map<Warehouse, Double> getAllWarehouseResaleValues() {
		return GameState.getInstance().getWarehousingDepartment().getAllWarehouseResaleValues();
	}

	/**
	 * Gets monthly warehouse cost.
	 *
	 * @return the monthly warehouse cost
	 */
	public double getMonthlyWarehouseCost() {
		return GameState.getInstance().getWarehousingDepartment().getMonthlyWarehouseCost(GameState.getInstance().getGameDate());
	}

	/**
	 * Gets warehouse type.
	 *
	 * @param warehouse the warehouse
	 * @return the warehouse type
	 */
	public WarehouseType getWarehouseType(Warehouse warehouse) {
		return warehouse.getWarehouseType();
	}

	/**
	 * Gets warehouse building cost.
	 *
	 * @param warehouse the warehouse
	 * @return the warehouse building cost
	 */
	public double getWarehouseBuildingCost(Warehouse warehouse) {
		return warehouse.getBuildingCost();
	}

	/**
	 * Gets warehouse monthly rental cost.
	 *
	 * @param warehouse the warehouse
	 * @return the warehouse monthly rental cost
	 */
	public double getWarehouseMonthlyRentalCost(Warehouse warehouse) {
		return warehouse.getMonthlyRentalCost();
	}

	/**
	 * Gets warehouse variable storage cost.
	 *
	 * @param warehouse the warehouse
	 * @return the warehouse variable storage cost
	 */
	public double getWarehouseVariableStorageCost(Warehouse warehouse) {
		return warehouse.getVariableStorageCost();
	}

	/**
	 * Gets warehouse monthly fix cost.
	 *
	 * @param warehouse the warehouse
	 * @return the warehouse monthly fix cost
	 */
	public double getWarehouseMonthlyFixCost(Warehouse warehouse) {
		return warehouse.getMonthlyFixCostWarehouse();
	}

	/**
	 * Gets warehouse resale value.
	 *
	 * @param warehouse the warehouse
	 * @return the warehouse resale value
	 */
	public double getWarehouseResaleValue(Warehouse warehouse) {
		return warehouse.getResaleValue();
	}

	/**
	 * Gets warehouse build date.
	 *
	 * @param warehouse the warehouse
	 * @return the warehouse build date
	 */
	public LocalDate getWarehouseBuildDate(Warehouse warehouse) {
		return warehouse.getBuildDate();
	}

	/**
	 * Gets warehouse depreciation rate.
	 *
	 * @param warehouse the warehouse
	 * @return the warehouse depreciation rate
	 */
	public double getWarehouseDepreciationRate(Warehouse warehouse) {
		return warehouse.getDepreciationRateWarehouse();
	}

	/**
	 * Gets warehouse useful life.
	 *
	 * @param warehouse the warehouse
	 * @return the warehouse useful life
	 */
	public int getWarehouseUsefulLife(Warehouse warehouse) {
		return warehouse.getUsefulLife();
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
	 * @return Returns all marketing campaigns that the player did issue.
	 */
	public List<Campaign> getIssuedMarketingCampaigns() {
		return GameState.getInstance().getMarketingDepartment().getCampaignsWithDates();
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
	 * Make a campaign with the specified campaign name and media type. But you are
	 * restricted to the predefined campaigns!
	 * 
	 * @param campaignName the campaign name.
	 * @param media        the media type.
	 */
	public void makeCampaign(String campaignName, Media media) {
		int cost = GameState.getInstance().getMarketingDepartment().startCampaign(campaignName, media);
		decreaseCash(GameState.getInstance().getGameDate(), cost);
	}
	
	/**
	 * Computes the current CompanyImage from issued Campaigns
	 */
	public double computeCompanyImage() {
		return GameState.getInstance().getMarketingDepartment().getCompanyImageScore();
	}
	
	
	/**
	 *
	 * @return Returns all issued press releases.
	 */
	public List<PressRelease> getPressReleases() {
		return GameState.getInstance().getMarketingDepartment().getPressReleases();
	}

	/**
	 * Makes a press release.
	 * 
	 * @param pr a press release.
	 */
	public void makePressRelease(PressRelease pr) {
		int cost = GameState.getInstance().getMarketingDepartment().makePressRelease(pr);
		decreaseCash(GameState.getInstance().getGameDate(), cost);
		
		//add ExternalEvent of issued PressRelease
		GameState.getInstance().getExternalEvents().addPressReleaseEvent(pr);
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
	 * 
	 * @param internal   if conduct market research internally then set true, else
	 *                   if you outsource then set false.
	 * @param report     the report type you want to do.
	 * @param surveyType the survey methodology.
	 * @param data       the data as a Map of string as key and double value pair.
	 */
	public void conductMarketResearch(boolean internal, Reports report, SurveyTypes surveyType,
			Map<String, Double> data) {
		double cost = GameState.getInstance().getMarketingDepartment().issueMarketResearch(internal, report, surveyType, data, GameState.getInstance().getGameDate());
		decreaseCash(GameState.getInstance().getGameDate(), cost);
	}

	/*** @param 
	 * @return Returns list of conducted market researches.
	 */

	public List<MarketResearch> getConductedMarketResearch() {
		return GameState.getInstance().getMarketingDepartment().getMarketResearches();
	}
	
	
	//TODO check ranges of all that stuff...
//  * @param totalSupportQuality
//  * @param logisticIndex
//  * @param companyImage
//  * @param productionTechnology 		????
//  * @param manufactureEfficiency
//  * @param totalJobSatisfaction
	public String orderConsultancyReport(ConsultancyType conType) {
		
		//Money first
		decreaseCash(GameState.getInstance().getGameDate(), conType.getCost());
		
		//gather metrics
		Double[] values = new Double[6];		
		values[0] = getTotalSupportQuality();
		values[1] = GameState.getInstance().getLogisticsDepartment().getLogisticsIndex();
		values[2] = computeCompanyImage();
		values[3] =	new Double (getProductionTechnology().getRange());	//Quatsch - besser: ProductQualtity?	
		values[4] =	getManufactureEfficiency();	
		values[5] = getTotalJSS();
		
		return GameState.getInstance().getMarketingDepartment().orderConsultantReport(conType, values);
	}
	
	/* Human Resources */

	/**
	 * Hire the employee. He will be added to your team.
	 * 
	 * @param e the employee you want to hire.
	 */
	public void hireEmployee(Employee e) {
		GameState.getInstance().getHrDepartment().hire(e);
		this.updateNumberOfProductionWorkers();
	}

	/**
	 * Fire the employee. He will be removed from the team.
	 * 
	 * @param e the employee you want to fire.
	 */
	public void fireEmployee(Employee e) {
		GameState.getInstance().getHrDepartment().fire(e);
		this.updateNumberOfProductionWorkers();
	}

	/**
	 *
	 * @return Returns the engineer team.
	 */
	public Team getEngineerTeam() {
		return GameState.getInstance().getHrDepartment().getEngineerTeam();
	}

	/**
	 *
	 * @return Returns the sales people team.
	 */
	public Team getSalesPeopleTeam() {
		return GameState.getInstance().getHrDepartment().getSalesTeam();
	}

	/**
	 *
	 * @return Returns all pre defined trainings for your employee.
	 */
	public Training[] getAllEmployeeTraining() {
		return GameState.getInstance().getHrDepartment().getAllTrainings();
	}


	public double getTotalQualityOfWorkByEmployeeType(EmployeeType employeeType) {
		return GameState.getInstance().getHrDepartment().getTotalQualityOfWorkByEmployeeType(employeeType);
	}
	
	public double getTotalJSS() {
		return GameState.getInstance().getHrDepartment().getTotalJSS();
	}
	
	
	/*  Customer */	
	public void updateCompanyImageInCustomerSatisfaction() {
		GameState.getInstance().getCustomerSatisfaction().setCompanyImage(MarketingDepartment.getInstance().getCompanyImageScore());
	}
	
	public double getEmployerBranding() {
		return GameState.getInstance().getCustomerSatisfaction().getEmployerBranding();
	}
}
