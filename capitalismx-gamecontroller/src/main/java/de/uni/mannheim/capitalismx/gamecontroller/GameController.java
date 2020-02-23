package de.uni.mannheim.capitalismx.gamecontroller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import de.uni.mannheim.capitalismx.hr.domain.employee.EmployeeGenerator;
import de.uni.mannheim.capitalismx.logistic.logistics.exception.NotEnoughTruckCapacityException;
import de.uni.mannheim.capitalismx.procurement.component.*;
import de.uni.mannheim.capitalismx.production.*;
import de.uni.mannheim.capitalismx.production.exceptions.*;
import de.uni.mannheim.capitalismx.warehouse.*;
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

import javax.swing.*;

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
		ProductionDepartment.getInstance().resetMonthlyPerformanceMetrics();
		WarehousingDepartment.getInstance().resetMonthlyStorageCost();
		updateSalesDepartment();
	}
	
	public void goToDay(LocalDate newDate) {
		GameState state = GameState.getInstance();

		while(newDate.isAfter(state.getGameDate())) {
			nextDay();
		}
	}

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
		CompanyEcoIndex.getInstance().calculateAll();
	}

	/**
	 * Checks which external events occurred on the current day.
	 * @param gameDate The current date in the game.
	 */
	private void updateExternalEvents(LocalDate gameDate) {
		ExternalEvents.getInstance().checkEvents(gameDate);
	}

	private void updateCustomer() {
		GameState state =  GameState.getInstance();
		LocalDate gameDate = state.getGameDate();
		CustomerSatisfaction customerSatisfaction = CustomerSatisfaction.getInstance();
		CustomerDemand customerDemand = CustomerDemand.getInstance();

		// get launched products from production department
		ProductionDepartment productionDepartment = ProductionDepartment.getInstance();
		customerSatisfaction.setProducts(productionDepartment.getLaunchedProducts());

		// get totalSupportQuality score from Logistics
		ProductSupport productSupport = ProductSupport.getInstance();
		customerSatisfaction.setTotalSupportQuality(productSupport.getTotalSupportQuality());

		// get companyImage score from Marketing
		MarketingDepartment marketingDepartment = MarketingDepartment.getInstance();
		double companyImage = marketingDepartment.getCompanyImageScore();
		customerSatisfaction.setCompanyImage(companyImage);

		// get jobSatisfaction score from HR
		HRDepartment hrDepartment = HRDepartment.getInstance();
		double jss = hrDepartment.getTotalJSS();

		// get employerBranding score
		double employerBranding = jss * 0.6 + companyImage * 0.4;
		customerSatisfaction.setEmployerBranding(employerBranding);

		state.setEmployerBranding(employerBranding);

		// get logisticsIndex from Logistics
		LogisticsDepartment logisticsDepartment = LogisticsDepartment.getInstance();
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
		FinanceDepartment.getInstance().calculateNetWorth(GameState.getInstance().getGameDate());
		FinanceDepartment.getInstance().updateMonthlyData(GameState.getInstance().getGameDate());
		FinanceDepartment.getInstance().updateQuarterlyData(GameState.getInstance().getGameDate());
		FinanceDepartment.getInstance().updateNetWorthDifference(GameState.getInstance().getGameDate());
		FinanceDepartment.getInstance().updateCashDifference(GameState.getInstance().getGameDate());
	}

	private void updateHR() {
		HRDepartment.getInstance().updateEmployeeHistory(GameState.getInstance().getGameDate());
		HRDepartment.getInstance().calculateAndUpdateEmployeesMeta();
	}

	/**
	 * Updates the relevant values in the logistics department.
	 */
	private void updateLogistics() {
		InternalFleet.getInstance().calculateAll();
		if (LogisticsDepartment.getInstance().getExternalPartner() != null) {
			LogisticsDepartment.getInstance().getExternalPartner().calculateExternalLogisticsIndex();
		}
		LogisticsDepartment.getInstance().calculateAll(GameState.getInstance().getGameDate());
		ProductSupport.getInstance().calculateAll();
	}

	private void updateMarketing() {
		//TODO update CompanyImage und EmployerBranding
		
		CustomerSatisfaction customerSatisfaction = CustomerSatisfaction.getInstance();
		MarketingDepartment.getInstance().setEmployerBranding(customerSatisfaction.getEmployerBranding());
		
		//TODO set values used for consultancies here!!!
		
	}

	// TODO once procurement implementation is ready
	private void updateProcurement() {
		ProcurementDepartment.getInstance().updateAll(GameState.getInstance().getGameDate());
	}

	private void updateProduction() {
		ProductionDepartment.getInstance().calculateAll(GameState.getInstance().getGameDate());
		this.updateNumberOfProductionWorkers();
	}

	private void updateWarehouse() {
		WarehousingDepartment.getInstance().calculateAll(GameState.getInstance().getGameDate());
	}

	private void updateSalesDepartment() {
		GameState state = GameState.getInstance();
		SalesDepartment salesDepartment = SalesDepartment.getInstance();
		salesDepartment.generateContracts(state.getGameDate(), state.getProductionDepartment(), state.getCustomerDemand().getDemandPercentage());

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
		FinanceDepartment.getInstance().calculateNetWorth(GameState.getInstance().getGameDate());
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
		ProductionDepartment.getInstance().calculateAll(GameState.getInstance().getGameDate());
	}

	private void setInitialWarehouseValues() {
		// TODO really needed?
		WarehousingDepartment.getInstance().calculateAll(GameState.getInstance().getGameDate());
	}


	public CompanyEcoIndex.EcoIndex getEcoIndex() {
		return CompanyEcoIndex.getInstance().getEcoIndex();
	}

	public List<ExternalEvents.ExternalEvent> getExternalEvents() {
		return ExternalEvents.getInstance().getExternalEvents();
	}

	public List<ExternalEvents.ExternalEvent> getExternalEvents(LocalDate date) {
		if(ExternalEvents.getInstance().getExternalEventsHistory().containsKey(date)) {
			return ExternalEvents.getInstance().getExternalEventsHistory().get(date);
		} else {
			return null;
		}
	}

	public Map<LocalDate, List<ExternalEvents.ExternalEvent>> getExternalEventsHistory() {
		return ExternalEvents.getInstance().getExternalEventsHistory();
	}


	/*
	 * FINANCE
	 */

	public double getCash() {
		double cash = FinanceDepartment.getInstance().getCash();
		return cash;
	}

	public double getNetWorth() {
		// double netWorth = Finance.getInstance().calculateNetWorth(gameDate);
		double netWorth = FinanceDepartment.getInstance().getNetWorth();
		return netWorth;
	}

    public List<Investment> getInvestments() {
        return FinanceDepartment.getInstance().getInvestments();
    }

	/**
	 * Generates a selection of three loans according to p.76. The actual generation is implemented in the banking
	 * system. The desired loan amount can not exceed 70 percent of the current net worth.
	 * @param loanAmount The requested loan amount.
	 * @return Returns a list of three loans with different characteristics.
	 */
	public ArrayList<BankingSystem.Loan> generateLoanSelection(double loanAmount) {
		return FinanceDepartment.getInstance().generateLoanSelection(loanAmount);
	}

	/**
	 * Adds a loan to the company (implemented in the banking system) and updates the cash and liabilities amount
	 * accordingly.
	 * @param loan The loan to be added.
	 * @param loanDate The current date in the game.
	 */
	public void addLoan(BankingSystem.Loan loan, LocalDate loanDate) {
		FinanceDepartment.getInstance().addLoan(loan, loanDate);
	}

	public BankingSystem.Loan getLoan() {
		return FinanceDepartment.getInstance().getLoan();
	}

	/**
	 * Calculates the resell price of assets according to p.72.
	 * @param purchasePrice The purchase price of the asset.
	 * @param usefulLife The useful life of the asset.
	 * @param timeUsed The time that the asset was used for.
	 * @return Returns the resell price of the asset.
	 */
	public double calculateResellPrice(double purchasePrice, double usefulLife, double timeUsed) {
		return FinanceDepartment.getInstance().calculateResellPrice(purchasePrice, usefulLife, timeUsed);
	}

	/**
	 * Processes the purchase of a truck. The new truck is added to the internal fleet, the cash and net worth amount
	 * are decreased, and the asset amount is increased according to the purchase and resell price.
	 * @param truck The truck to be bought.
	 * @param gameDate The current date in the game
	 * @throws NotEnoughTruckCapacityException if the internal fleet does not have enough capacity.
	 */
	public void buyTruck(Truck truck, LocalDate gameDate) throws NotEnoughTruckCapacityException {
		FinanceDepartment.getInstance().buyTruck(truck, gameDate);
	}

	/**
	 * Processes the sale of a truck. The truck is removed from the internal fleet, the cash amount is increased, and
	 * the asset amount is decreased according to the resell price.
	 * @param truck The truck to be sold.
	 * @param gameDate The current date in the game.
	 */
	public void sellTruck(Truck truck, LocalDate gameDate) {
		FinanceDepartment.getInstance().sellTruck(truck, gameDate);
	}

	public double getAssets() {
		return FinanceDepartment.getInstance().getAssets();
	}

	public double getLiabilities() {
		return FinanceDepartment.getInstance().getLiabilities();
	}

	/**
	 * Calculates the net worth based on the cash, assets, and liabilities according to p.70 and adds it to the
	 * respective history.
	 * @param gameDate The current date in the game.
	 * @return Returns the net worth.
	 */
	public double calculateNetWorth(LocalDate gameDate) {
		return FinanceDepartment.getInstance().calculateNetWorth(gameDate);
	}

	/**
	 * Increases the cash amount of the company by the specified amount. The new cash amount is added to the respective
	 * history and the monthly data and cash difference are updated.
	 * @param gameDate The current date in the game.
	 * @param amount The amount by which the cash should be increased.
	 */
	public void increaseCash(LocalDate gameDate, double amount) {
		FinanceDepartment.getInstance().increaseCash(gameDate, amount);
	}

	/**
	 * Decreases the cash amount of the company by the specified amount. If the new cash amount is < 0, the game is
	 * over. Otherwise, the new cash amount is added to the respective history and the monthly data and cash difference
	 * are updated.
	 * @param gameDate The current date in the game.
	 * @param amount The amount by which the cash should be decreased.
	 */
	public void decreaseCash(LocalDate gameDate, double amount) {
		FinanceDepartment.getInstance().decreaseCash(gameDate, amount);
	}

	/**
	 * Increases the net worth of the company by the specified amount. The new net worth is added to the respective
	 * history and the monthly data and net worth difference are updated.
	 * @param gameDate The current date in the game.
	 * @param amount The amount by which the net worth should be increased.
	 */
	public void increaseNewWorth(LocalDate gameDate, double amount) {
		FinanceDepartment.getInstance().increaseNetWorth(gameDate, amount);
	}

	/**
	 * Decreases the net worth of the company by the specified amount. The new net worth is added to the respective
	 * history and the monthly data and net worth difference are updated.
	 * @param gameDate The current date in the game.
	 * @param amount The amount by which the net worth should be decreased.
	 */
	public void decreaseNetWorth(LocalDate gameDate, double amount) {
		FinanceDepartment.getInstance().decreaseNetWorth(gameDate, amount);
	}

	/**
	 * Increases the asset amount of the company by the specified amount. The new asset amount is added to the
	 * respective history and the monthly data is updated.
	 * @param gameDate The current date in the game.
	 * @param amount The amount by which the assets should be increased.
	 */
	public void increaseAssets(LocalDate gameDate, double amount) {
		FinanceDepartment.getInstance().increaseAssets(gameDate, amount);
	}

	/**
	 * Decreases the asset amount of the company by the specified amount. The new asset amount is added to the
	 * respective history and the monthly data is updated.
	 * @param gameDate The current date in the game.
	 * @param amount The amount by which the assets should be decreased.
	 */
	public void decreaseAssets(LocalDate gameDate, double amount) {
		FinanceDepartment.getInstance().decreaseAssets(gameDate, amount);
	}

	/**
	 * Increases the liabilities amount of the company by the specified amount. The new liabilities amount is added to
	 * the respective history and the monthly data is updated.
	 * @param gameDate The current date in the game.
	 * @param amount The amount by which the liabilities should be increased.
	 */
	public void increaseLiabilities(LocalDate gameDate, double amount) {
		FinanceDepartment.getInstance().increaseLiabilities(gameDate, amount);
	}

	public double getRealEstateInvestmentAmount() {
		return FinanceDepartment.getInstance().getRealEstateInvestmentAmount();
	}

	public double getStocksInvestmentAmount() {
		return FinanceDepartment.getInstance().getStocksInvestmentAmount();
	}

	public double getVentureCapitalInvestmentAmount() {
		return FinanceDepartment.getInstance().getVentureCapitalInvestmentAmount();
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
		return FinanceDepartment.getInstance().increaseInvestmentAmount(gameDate, amount, investmentType);
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
        return FinanceDepartment.getInstance().decreaseInvestmentAmount(gameDate, amount, investmentType);
    }

	public TreeMap<String, String[]> getMonthlyData() {
		return FinanceDepartment.getInstance().getMonthlyData();
	}

	public TreeMap<String, String[]> getQuarterlyData() {
		return FinanceDepartment.getInstance().getQuarterlyData();
	}

	public Double getNetWorthDifference(){
		return FinanceDepartment.getInstance().getNetWorthDifference();
	}

	public Double getCashDifference(){
		return FinanceDepartment.getInstance().getCashDifference();
	}

	/*
	 * LOGISTICS
	 */

	/**
	 * Generates a selection of external logistics partners with different characteristics according to p.52.
	 * @return Returns a list of 9 different external logistics partners.
	 */
	public ArrayList<ExternalPartner> generateExternalPartnerSelection() {
		return LogisticsDepartment.getInstance().generateExternalPartnerSelection();
	}

	/**
	 * Generates a selection of trucks with different characteristics according to p.49.
	 * @return Returns a list of 6 different trucks.
	 */
	public ArrayList<Truck> generateTruckSelection() {
		return LogisticsDepartment.getInstance().generateTruckSelection();
	}

	public ExternalPartner getExternalPartner() {
		return LogisticsDepartment.getInstance().getExternalPartner();
	}

	public ArrayList<ExternalPartner> getExternalPartnerSelection() {
		return LogisticsDepartment.getInstance().getExternalPartnerSelection();
	}

	public InternalFleet getInternalFleet() {
		return LogisticsDepartment.getInstance().getInternalFleet();
	}

	/**
	 * Adds an external logistics partner.
	 * @param externalPartner The external logistics partner to be added.
	 */
	public void addExternalPartner(ExternalPartner externalPartner) {
		LogisticsDepartment.getInstance().addExternalPartner(externalPartner);
	}

	/**
	 * Removes the currently hired external logistics partner.
	 */
	public void removeExternalPartner() {
		LogisticsDepartment.getInstance().removeExternalPartner();
	}

	/**
	 * Generates a list of the available support types to choose from.
	 * @return Returns a list of the available support types.
	 */
	public ArrayList<ProductSupport.SupportType> generateSupportTypeSelection() {
		return ProductSupport.getInstance().generateSupportTypeSelection();
	}

	/**
	 * Adds a new support type to the list of support types provided by the company. Only possible if an external
	 * support partner is hired.
	 * @param supportType The new support type to be added.
	 */
	public void addSupport(ProductSupport.SupportType supportType) {
		ProductSupport.getInstance().addSupport(supportType);
	}

	/**
	 * Removes a support type from the list of support types provided by the company.
	 * @param supportType The support type to be removed.
	 */
	public void removeSupport(ProductSupport.SupportType supportType) {
		ProductSupport.getInstance().removeSupport(supportType);
	}

	/**
	 * Generates a list of the available external support partners to choose from.
	 * @return Returns a list of the available external support partners.
	 */
	public ArrayList<ProductSupport.ExternalSupportPartner> generateExternalSupportPartnerSelection() {
		return ProductSupport.getInstance().generateExternalSupportPartnerSelection();
	}

	/**
	 * Hires the specified external support partner.
	 * @param externalSupportPartner The external support partner to be hired.
	 */
	public void addExternalSupportPartner(ProductSupport.ExternalSupportPartner externalSupportPartner) {
		ProductSupport.getInstance().addExternalSupportPartner(externalSupportPartner);
	}

	/**
	 * Fires the currently hired external support partner.
	 */
	public void removeExternalSupportPartner() {
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

	/*
	 * PROCUREMENT
	 */

	public String getComponentName(ComponentType component) {
		return component.getComponentName();
	}

	public int getComponentLevel(ComponentType component) {
		return component.getComponentLevel();
	}

	public double getInitialComponentPrice(ComponentType component) {
		return component.getInitialComponentPrice();
	}

	public int getComponentBaseUtility(ComponentType component) {
		return component.getBaseUtility();
	}

	public int getComponentAvailabilityDate(ComponentType component) {
		return component.getAvailabilityDate();
	}

	public SupplierCategory getComponentSupplierCategory(Component component) {
		return component.getSupplierCategory();
	}

	public double getComponentSupplierCostMultiplicator(Component component) {
		return component.getSupplierCostMultiplicator();
	}

	public int getComponentSupplierQuality(Component component) {
		return component.getSupplierQuality();
	}

	public int getComponentSupplierEcoIndex(Component component) {
		return component.getSupplierEcoIndex();
	}

	public double getComponentBaseCost(Component component) {
		return component.getBaseCost();
	}

	public List<ComponentType> getAllAvailableComponents() {
		return ProductionDepartment.getInstance().getAllAvailableComponents(GameState.getInstance().getGameDate());
	}

	public List<ComponentType> getAvailableComponentsOfComponentCategory(ComponentCategory componentCategory) {
		return ProductionDepartment.getInstance()
				.getAvailableComponentsOfComponentCategory(GameState.getInstance().getGameDate(), componentCategory);
	}


    public double buyComponents(Component component, int quantity) {
		return ProcurementDepartment.getInstance().buyComponents(GameState.getInstance().getGameDate(), component, quantity, getFreeStorage());
    }
	
	public double buyComponents(LocalDate gameDate, Component component, int quantity, int freeStorage) {
		return ProcurementDepartment.getInstance().buyComponents(gameDate, component, quantity, freeStorage);
	}

	public void receiveComponents() {
		ProcurementDepartment.getInstance().receiveComponents(GameState.getInstance().getGameDate());
	}

	public Map<Component, Integer> getReceivedComponents() {
		return ProcurementDepartment.getInstance().getReceivedComponents();
	}

	public List<ComponentOrder> getComponentOrders() {
		return ProcurementDepartment.getInstance().getComponentOrders();
	}

	public int getQuantityOfOrderedComponents() {
		return ProcurementDepartment.getInstance().getQuantityOfOrderedComponents();
	}

	public void clearReceivedComponents() {
		ProcurementDepartment.getInstance().clearReceivedComponents();
	}



	/*
	 * PRODUCTION
	 */

	public void updateNumberOfProductionWorkers() {
		Map<EmployeeType, Team> teams = HRDepartment.getInstance().getTeams();
		int numberOfProductionWorkers = teams.get(EmployeeType.PRODUCTION_WORKER).getTeam().size();
		ProductionDepartment.getInstance().setNumberOfProductionWorkers(numberOfProductionWorkers);
	}

	/* Production getter */
	public Map<Product, Integer> getProducedProducts() {
		return ProductionDepartment.getInstance().getNumberProducedProducts();
	}

	public List<Machinery> getMachines() {
		return ProductionDepartment.getInstance().getMachines();
	}

	public ProductionTechnology getProductionTechnology() {
		return ProductionDepartment.getInstance().getProductionTechnology();
	}

	public ProductionInvestment getResearchAndDevelopment() {
		return ProductionDepartment.getInstance().getResearchAndDevelopment();
	}

	public ProductionInvestment getProcessAutomation() {
		return ProductionDepartment.getInstance().getProcessAutomation();
	}

	public double getTotalEngineerProductivity() {
		return ProductionDepartment.getInstance().getTotalEngineerProductivity();
	}

	public ProductionInvestment getSystemSecurity() {
		return ProductionDepartment.getInstance().getSystemSecurity();
	}

	public double getProductionVariableCosts() {
		return ProductionDepartment.getInstance().getProductionVariableCosts();
	}

	public double getProductionFixCosts() {
		return ProductionDepartment.getInstance().getProductionFixCosts();
	}

	public double getNumberUnitsProducedPerMonth() {
		return ProductionDepartment.getInstance().getNumberUnitsProducedPerMonth();
	}

	public double getMonthlyAvailableMachineCapacity() {
		return ProductionDepartment.getInstance().getMonthlyAvailableMachineCapacity();
	}

	public double getManufactureEfficiency() {
		return ProductionDepartment.getInstance().getManufactureEfficiency();
	}

	public double getProductionProcessProductivity() {
		return ProductionDepartment.getInstance().getProductionProcessProductivity();
	}

	public double getNormalizedProductionProcessProductivity() {
		return ProductionDepartment.getInstance().getNormalizedProductionProcessProductivity();
	}

	public int getDailyMachineCapacity() {
		return ProductionDepartment.getInstance().getDailyMachineCapacity();
	}

	/* machinery game mechanics */
	public boolean getMachineSlotsAvailable() {
		return ProductionDepartment.getInstance().getMachineSlotsAvailable();
	}

	public int getProductionSlots() {
		return ProductionDepartment.getInstance().getProductionSlots();
	}

	public double buyMachinery(Machinery machinery, LocalDate gameDate) throws NoMachinerySlotsAvailableException {
		try {
			double purchasePrice = ProductionDepartment.getInstance().buyMachinery(machinery, gameDate);
			FinanceDepartment.getInstance().buyMachinery(machinery, gameDate);
			return purchasePrice;
		} catch (NoMachinerySlotsAvailableException e) {
			throw new NoMachinerySlotsAvailableException("No more Capacity available to buy new Machine.");
		}
	}

	public double sellMachinery(Machinery machinery, LocalDate gameDate) {
		double resellPrice = ProductionDepartment.getInstance().sellMachinery(machinery);
		FinanceDepartment.getInstance().sellMachinery(machinery, gameDate);
		return resellPrice;
	}

	public Map<Machinery, Double> getMachineryResellPrices() {
		return ProductionDepartment.getInstance().calculateMachineryResellPrices();
	}

	public double maintainAndRepairMachinery(Machinery machinery) {
		return ProductionDepartment.getInstance().maintainAndRepairMachinery(machinery,
				GameState.getInstance().getGameDate());
	}

	public double updateMachinery(Machinery machinery) {
		return ProductionDepartment.getInstance().upgradeMachinery(machinery, GameState.getInstance().getGameDate());
	}

	public ProductionTechnology getMachineryProductionTechnology(Machinery machinery) {
		return machinery.getProductionTechnology();
	}

	public double getMachineryPurchasePrice() {
		return ProductionDepartment.getInstance().getMachineryPurchasePrice(GameState.getInstance().getGameDate());
	}

	public int getMachineryCapacity(Machinery machinery) {
		return machinery.getMachineryCapacity();
	}

	public double getMachineryPurchasePrice(Machinery machinery) {
		return machinery.getPurchasePrice();
	}

	public double getMachineryResellPrice(Machinery machinery) {
		return machinery.getResellPrice();
	}

	public double getMachineryDepreciation(Machinery machinery) {
		return machinery.getMachineryDepreciation();
	}

	public LocalDate getMachineryLastInvestment(Machinery machinery) {
		return machinery.getLastInvestmentDate();
	}

	public int getMachineryYearsSinceLastInvestment(Machinery machinery) {
		return machinery.getYearsSinceLastInvestment();
	}

	public int getMachineryUsefulLife(Machinery machinery) {
		return machinery.getUsefulLife();
	}

	public LocalDate getMachineryPurchaseDate(Machinery machinery) {
		return machinery.getPurchaseDate();
	}

	/* product game mechanics */
	public double getProductCosts(Product product) {
		return product.getProductCosts(GameState.getInstance().getGameDate());
	}

	public double launchProduct(Product product) throws ProductCategoryNotUnlockedException, ProductNameAlreadyInUseException {
		try {
			LocalDate gameDate = GameState.getInstance().getGameDate();
			ResearchAndDevelopmentDepartment researchAndDevelopmentDepartment = GameState.getInstance().getResearchAndDevelopmentDepartment();
			boolean productCategoryUnlocked = researchAndDevelopmentDepartment.isCategoryUnlocked(product.getProductCategory());
			double launchCosts = ProductionDepartment.getInstance().launchProduct(product, gameDate, productCategoryUnlocked);
			FinanceDepartment.getInstance().decreaseCash(gameDate, launchCosts);
			return launchCosts;
		} catch (Exception e) {
			throw e;
		}
	}

	public double produceProduct(Product product, int quantity) throws NotEnoughComponentsException, NotEnoughMachineCapacityException, NotEnoughFreeStorageException, ComponenLockedException {
		try {
		    ResearchAndDevelopmentDepartment researchAndDevelopmentDepartment = GameState.getInstance().getResearchAndDevelopmentDepartment();
		    boolean allComponentsUnlocked = true;
		    for(Component component : product.getComponents()) {
		        if(!researchAndDevelopmentDepartment.isComponentUnlocked(component)) {
		            allComponentsUnlocked = false;
                }
            }
			return ProductionDepartment.getInstance().produceProduct(product, quantity,
					WarehousingDepartment.getInstance().calculateFreeStorage(), allComponentsUnlocked);
		} catch (Exception e) {
			throw e;
		}
	}

	public double getAmountProductInProduction(Product product) {
		return ProductionDepartment.getInstance().getAmountInProduction(product);
	}

	public void setProductSalesPrice(Product product, double salesPrice) {
		ProductionDepartment.getInstance().setProductSalesPrice(product, salesPrice);
	}

	public String getProductName(Product product) {
		return product.getProductName();
	}

	public ProductCategory getProductCategory(Product product) {
		return product.getProductCategory();
	}

	public List<Component> getProductComponents(Product product) {
		return product.getComponents();
	}

	public double getProductProcurementQuality(Product product) {
		return product.getTotalProcurementQuality();
	}

	public double getProductTotalProductQuality(Product product) {
		return product.getTotalProductQuality();
	}

	public LocalDate getProductLaunchDate(Product product) {
		return product.getLaunchDate();
	}

	public double getProductComponentCosts(Product product) {
		return product.getTotalComponentCosts();
	}

	public double getTotalProductCosts(Product product) {
		return product.getTotalProductCosts();
	}

	public double getProductsSalesPrice(Product product) {
		return product.getSalesPrice();
	}

	public double getProductProfitMargin(Product product) {
		return product.getProfitMargin();
	}

	public List<Product> getLaunchedProducts() {
		return ProductionDepartment.getInstance().getLaunchedProducts();
	}

	/* production investment game mechanics */
	public double investInSystemSecurity(int level, LocalDate gameDate) {
		return ProductionDepartment.getInstance().investInSystemSecurity(level, gameDate);
	}

	public double investInResearchAndDevelopment(int level, LocalDate gameDate) {
		return ProductionDepartment.getInstance().investInResearchAndDevelopment(level, gameDate);
	}

	public double investInProcessAutomation(int level, LocalDate gameDate) {
		return ProductionDepartment.getInstance().investInProcessAutomation(level, gameDate);
	}

	public LocalDate getLastInvestmentDateSystemSecurity() {
		return ProductionDepartment.getInstance().getSystemSecurity().getLastInvestmentDate();
	}

	public LocalDate getLastInvestmentDateResearchAndDevelopment() {
		return ProductionDepartment.getInstance().getResearchAndDevelopment().getLastInvestmentDate();
	}

	public LocalDate getLastInvestmentDateProcessAutomation() {
		return ProductionDepartment.getInstance().getProcessAutomation().getLastInvestmentDate();
	}

	public int getSystemSecurityLevel() {
		return ProductionDepartment.getInstance().getSystemSecurity().getLevel();
	}

	public int getResearchAndDevelopmentLevel() {
		return ProductionDepartment.getInstance().getResearchAndDevelopment().getLevel();
	}

	public int getProcessAutomationLevel() {
		return ProductionDepartment.getInstance().getProcessAutomation().getLevel();
	}

	/*
	 * WAREHOUSING
	 */

	/* warehousing getter and game mechanic */
	// TODO SALE OF PRODUCTS
	/*
	 * public double sellProducts() { return
	 * Warehousing.getInstance().sellProducts() }
	 */

	public int getWarehouseSlots() {
		return WarehousingDepartment.getInstance().getWarehouseSlots();
	}

	public boolean getWarehouseSlotsAvailable() {
		return WarehousingDepartment.getInstance().getWarehouseSlotsAvailable();
	}

	public List<Warehouse> getWarehouses() {
		return WarehousingDepartment.getInstance().getWarehouses();
	}

	public Map<Unit, Integer> getWarehousingInventory() {
		return WarehousingDepartment.getInstance().getInventory();
	}

	public int getTotalWarehousingCapacity() {
		return WarehousingDepartment.getInstance().getTotalCapacity();
	}

	public int getFreeStorage() {
		return WarehousingDepartment.getInstance().getFreeStorage();
	}

	public int getStoredUnits() {
		return WarehousingDepartment.getInstance().getStoredUnits();
	}

	public double getMonthlyWarehousingCost() {
		return WarehousingDepartment.getInstance().getMonthlyCostWarehousing();
	}

	public double getDailyWarehousingStorageCost() {
		return WarehousingDepartment.getInstance().getDailyStorageCost();
	}

	public double getMonthlyWarehousingStorageCost() {
		return WarehousingDepartment.getInstance().getMonthlyStorageCost();
	}

	public double getMonthlyTotalWarehousingCost() {
		return WarehousingDepartment.getInstance().getMonthlyTotalCostWarehousing();
	}

	/* warehouse game mechanics */
	public double buildWarehouse() throws NoWarehouseSlotsAvailableException {
		try {
			return WarehousingDepartment.getInstance().buildWarehouse(GameState.getInstance().getGameDate());
		} catch (NoWarehouseSlotsAvailableException e) {
			throw new NoWarehouseSlotsAvailableException("No more Capacity available to build or rent a new Warehouse.");
		}
	}

	public double rentWarehouse() throws NoWarehouseSlotsAvailableException{
		try {
			return WarehousingDepartment.getInstance().rentWarehouse(GameState.getInstance().getGameDate());
		} catch	(NoWarehouseSlotsAvailableException e) {
			throw new NoWarehouseSlotsAvailableException("No more Capacity available to build or rent a new Warehouse.");
		}
	}

	public double sellWarehouse(Warehouse warehouse) throws StorageCapacityUsedException {
		try {
			return WarehousingDepartment.getInstance().sellWarehouse(warehouse);
		} catch(StorageCapacityUsedException e) {
			throw e;
		}
	}

	public void depreciateAllWarehouseResaleValues() {
		WarehousingDepartment.getInstance().depreciateAllWarehouseResaleValues(GameState.getInstance().getGameDate());
	}

	public Map<Warehouse, Double> getAllWarehouseResaleValues() {
		return WarehousingDepartment.getInstance().getAllWarehouseResaleValues();
	}

	public double getMonthlyWarehouseCost() {
		return WarehousingDepartment.getInstance().getMonthlyWarehouseCost(GameState.getInstance().getGameDate());
	}

	public WarehouseType getWarehouseType(Warehouse warehouse) {
		return warehouse.getWarehouseType();
	}

	public double getWarehouseBuildingCost(Warehouse warehouse) {
		return warehouse.getBuildingCost();
	}

	public double getWarehouseMonthlyRentalCost(Warehouse warehouse) {
		return warehouse.getMonthlyRentalCost();
	}

	public double getWarehouseVariableStorageCost(Warehouse warehouse) {
		return warehouse.getVariableStorageCost();
	}

	public double getWarehouseMonthlyFixCost(Warehouse warehouse) {
		return warehouse.getMonthlyFixCostWarehouse();
	}

	public double getWarehouseResaleValue(Warehouse warehouse) {
		return warehouse.getResaleValue();
	}

	public LocalDate getWarehouseBuildDate(Warehouse warehouse) {
		return warehouse.getBuildDate();
	}

	public double getWarehouseDepreciationRate(Warehouse warehouse) {
		return warehouse.getDepreciationRateWarehouse();
	}

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
		return MarketingDepartment.getInstance().getCampaignsWithDates();
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
		int cost = MarketingDepartment.getInstance().startCampaign(campaignName, media);
		decreaseCash(GameState.getInstance().getGameDate(), cost);
	}
	
	/**
	 * Computes the current CompanyImage from issued Campaigns
	 */
	public double computeCompanyImage() {
		return MarketingDepartment.getInstance().getCompanyImageScore();
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
	 * 
	 * @param pr a press release.
	 */
	public void makePressRelease(PressRelease pr) {
		int cost = MarketingDepartment.getInstance().makePressRelease(pr);
		decreaseCash(GameState.getInstance().getGameDate(), cost);
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
		double cost = MarketingDepartment.getInstance().issueMarketResearch(internal, report, surveyType, data, GameState.getInstance().getGameDate());
		decreaseCash(GameState.getInstance().getGameDate(), cost);
	}

	/*** @param 
	 * @return Returns list of conducted market researches.
	 */

	public List<MarketResearch> getConductedMarketResearch() {
		return MarketingDepartment.getInstance().getMarketResearches();
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
		values[1] = LogisticsDepartment.getInstance().getLogisticsIndex();
		values[2] = computeCompanyImage();
		values[3] =	new Double (getProductionTechnology().getRange());	//Quatsch - besser: ProductQualtity?	
		values[4] =	getManufactureEfficiency();	
		values[5] = getTotalJSS();
		
		return MarketingDepartment.getInstance().orderConsultantReport(conType, values);	
	}
	
	/* Human Resources */

	/**
	 * Hire the employee. He will be added to your team.
	 * 
	 * @param e the employee you want to hire.
	 */
	public void hireEmployee(Employee e) {
		HRDepartment.getInstance().hire(e);
		this.updateNumberOfProductionWorkers();
	}

	/**
	 * Fire the employee. He will be removed from the team.
	 * 
	 * @param e the employee you want to fire.
	 */
	public void fireEmployee(Employee e) {
		HRDepartment.getInstance().fire(e);
		this.updateNumberOfProductionWorkers();
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


	public double getTotalQualityOfWorkByEmployeeType(EmployeeType employeeType) {
		return HRDepartment.getInstance().getTotalQualityOfWorkByEmployeeType(employeeType);
	}
	
	public double getTotalJSS() {
		return HRDepartment.getInstance().getTotalJSS();
	}
	
	
	/*  Customer */	
	public void updateCompanyImageInCustomerSatisfaction() {
		CustomerSatisfaction.getInstance().setCompanyImage(MarketingDepartment.getInstance().getCompanyImageScore());
	}
	
	public double getEmployerBranding() {
		return CustomerSatisfaction.getInstance().getEmployerBranding();
	}
}
