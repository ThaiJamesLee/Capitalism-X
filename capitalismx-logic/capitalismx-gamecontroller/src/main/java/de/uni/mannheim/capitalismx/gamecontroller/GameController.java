package de.uni.mannheim.capitalismx.gamecontroller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import de.uni.mannheim.capitalismx.procurement.component.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.uni.mannheim.capitalismx.customer.CustomerDemand;
import de.uni.mannheim.capitalismx.customer.CustomerSatisfaction;
import de.uni.mannheim.capitalismx.domain.employee.Employee;
import de.uni.mannheim.capitalismx.domain.employee.EmployeeType;
import de.uni.mannheim.capitalismx.domain.employee.Team;
import de.uni.mannheim.capitalismx.domain.employee.Training;
import de.uni.mannheim.capitalismx.gamecontroller.ecoindex.CompanyEcoIndex;
import de.uni.mannheim.capitalismx.gamecontroller.external_events.ExternalEvents;
import de.uni.mannheim.capitalismx.finance.finance.BankingSystem;
import de.uni.mannheim.capitalismx.finance.finance.FinanceDepartment;
import de.uni.mannheim.capitalismx.finance.finance.Investment;
import de.uni.mannheim.capitalismx.gamecontroller.gamesave.SaveGameHandler;
import de.uni.mannheim.capitalismx.hr.department.HRDepartment;
import de.uni.mannheim.capitalismx.logistic.logistics.ExternalPartner;
import de.uni.mannheim.capitalismx.logistic.logistics.InternalFleet;
import de.uni.mannheim.capitalismx.logistic.logistics.LogisticsDepartment;
import de.uni.mannheim.capitalismx.logistic.logistics.Truck;
import de.uni.mannheim.capitalismx.logistic.support.ProductSupport;
import de.uni.mannheim.capitalismx.marketing.department.MarketingDepartment;
import de.uni.mannheim.capitalismx.marketing.domain.Campaign;
import de.uni.mannheim.capitalismx.marketing.domain.Media;
import de.uni.mannheim.capitalismx.marketing.domain.PressRelease;
import de.uni.mannheim.capitalismx.marketing.marketresearch.MarketResearch;
import de.uni.mannheim.capitalismx.marketing.marketresearch.Reports;
import de.uni.mannheim.capitalismx.marketing.marketresearch.SurveyTypes;
import de.uni.mannheim.capitalismx.production.Machinery;
import de.uni.mannheim.capitalismx.production.Product;
import de.uni.mannheim.capitalismx.production.ProductCategory;
import de.uni.mannheim.capitalismx.production.ProductionDepartment;
import de.uni.mannheim.capitalismx.production.ProductionInvestment;
import de.uni.mannheim.capitalismx.production.ProductionTechnology;
import de.uni.mannheim.capitalismx.warehouse.Warehouse;
import de.uni.mannheim.capitalismx.warehouse.WarehouseType;
import de.uni.mannheim.capitalismx.warehouse.WarehousingDepartment;

public class GameController {

	private static final Logger LOGGER = LoggerFactory.getLogger(GameController.class);

	private static GameController instance;
	private boolean isNewYear;

	private GameController() {
	}

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
		if (oldDate.getMonth() != newDate.getMonth()) {
			ProductionDepartment.getInstance().resetMonthlyPerformanceMetrics();
			WarehousingDepartment.getInstance().resetMonthlyStorageCost();
			WarehousingDepartment.getInstance().calculateMonthlyCostWarehousing();
		}
		this.updateAll();
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

		} catch (ClassNotFoundException e) {
			LOGGER.error(e.getMessage());
		}
	}

	private void updateCompanyEcoIndex() {
		CompanyEcoIndex.getInstance().calculateAll();
	}

	private void updateExternalEvents(LocalDate gameDate) {
		ExternalEvents.getInstance().checkEvents(gameDate);
	}

	private void updateCustomer() {
		LocalDate gameDate = GameState.getInstance().getGameDate();
		CustomerSatisfaction.getInstance().calculateAll(gameDate);
		CustomerDemand.getInstance().calculateAll(this.getTotalQualityOfWorkByEmployeeType(EmployeeType.SALESPERSON),
				gameDate);
	}

	private void updateFinance() {
		FinanceDepartment.getInstance().calculateNetWorth(GameState.getInstance().getGameDate());
		FinanceDepartment.getInstance().updateQuarterlyData(GameState.getInstance().getGameDate());
	}

	private void updateHR() {

	}

	private void updateLogistics() {
		InternalFleet.getInstance().calculateAll();
		if (LogisticsDepartment.getInstance().getExternalPartner() != null) {
			LogisticsDepartment.getInstance().getExternalPartner().calculateExternalLogisticsIndex();
		}
		LogisticsDepartment.getInstance().calculateAll();
		ProductSupport.getInstance().calculateAll();
	}

	private void updateMarketing() {

	}

	// TODO once procurement implementation is ready
	private void updateProcurement() {

	}

	private void updateProduction() {
		ProductionDepartment.getInstance().calculateAll(GameState.getInstance().getGameDate());
	}

	private void updateWarehouse() {
		WarehousingDepartment.getInstance().calculateAll();
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
		WarehousingDepartment.getInstance().calculateAll();
	}

	/* Finance */

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

	public ArrayList<BankingSystem.Loan> generateLoanSelection(double loanAmount) {
		return FinanceDepartment.getInstance().generateLoanSelection(loanAmount);
	}

	public void addLoan(BankingSystem.Loan loan, LocalDate loanDate) {
		FinanceDepartment.getInstance().addLoan(loan, loanDate);
	}

	public BankingSystem.Loan getLoan() {
		return FinanceDepartment.getInstance().getLoan();
	}

	public ArrayList<ExternalPartner> generateExternalPartnerSelection() {
		return LogisticsDepartment.getInstance().generateExternalPartnerSelection();
	}

	public ArrayList<Truck> generateTruckSelection() {
		return LogisticsDepartment.getInstance().generateTruckSelection();
	}

	public void addExternalPartner(ExternalPartner externalPartner) {
		LogisticsDepartment.getInstance().addExternalPartner(externalPartner);
	}

	public void removeExternalPartner() {
		LogisticsDepartment.getInstance().removeExternalPartner();
	}

	public void addTruckToFleet(Truck truck, LocalDate gameDate) {
		LogisticsDepartment.getInstance().addTruckToFleet(truck, gameDate);
	}

	public void removeTruckFromFleet(Truck truck) {
		LogisticsDepartment.getInstance().removeTruckFromFleet(truck);
	}

	public ArrayList<ProductSupport.SupportType> generateSupportTypeSelection() {
		return ProductSupport.getInstance().generateSupportTypeSelection();
	}

	public void addSupport(ProductSupport.SupportType supportType) {
		ProductSupport.getInstance().addSupport(supportType);
	}

	public void removeSupport(ProductSupport.SupportType supportType) {
		ProductSupport.getInstance().removeSupport(supportType);
	}

	public ArrayList<ProductSupport.ExternalSupportPartner> generateExternalSupportPartnerSelection() {
		return ProductSupport.getInstance().generateExternalSupportPartnerSelection();
	}

	public void addExternalSupportPartner(ProductSupport.ExternalSupportPartner externalSupportPartner) {
		ProductSupport.getInstance().addExternalSupportPartner(externalSupportPartner);
	}

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

	public CompanyEcoIndex.EcoIndex getEcoIndex() {
		return CompanyEcoIndex.getInstance().getEcoIndex();
	}

	public List<ExternalEvents.ExternalEvent> getExternalEvents() {
		return ExternalEvents.getInstance().getExternalEvents();
	}

	public double calculateResellPrice(double purchasePrice, double usefulLife, double timeUsed) {
		return FinanceDepartment.getInstance().calculateResellPrice(purchasePrice, usefulLife, timeUsed);
	}

	public void sellTruck(Truck truck) {
		FinanceDepartment.getInstance().sellTruck(truck);
	}

	public double getAssets() {
		return FinanceDepartment.getInstance().getAssets();
	}

	public double getLiabilities() {
		return FinanceDepartment.getInstance().getLiabilities();
	}

	public double calculateNetWorth(LocalDate gameDate) {
		return FinanceDepartment.getInstance().calculateNetWorth(gameDate);
	}

	public void increaseCash(double amount) {
		FinanceDepartment.getInstance().increaseCash(amount);
	}

	public void decreaseCash(double amount) {
		FinanceDepartment.getInstance().decreaseCash(amount);
	}

	public void increaseNewWorth(double amount) {
		FinanceDepartment.getInstance().increaseNetWorth(amount);
	}

	public void decreaseNetWorth(double amount) {
		FinanceDepartment.getInstance().decreaseNetWorth(amount);
	}

	public void increaseAssets(double amount) {
		FinanceDepartment.getInstance().increaseAssets(amount);
	}

	public void increaseLiabilities(double amount) {
		FinanceDepartment.getInstance().increaseLiabilities(amount);
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

	public boolean increaseInvestmentAmount(double amount, Investment.InvestmentType investmentType){
		return FinanceDepartment.getInstance().increaseInvestmentAmount(amount, investmentType);
	}

    public boolean decreaseInvestmentAmount(double amount, Investment.InvestmentType investmentType){
        return FinanceDepartment.getInstance().decreaseInvestmentAmount(amount, investmentType);
    }

	public TreeMap<String, String[]> getQuarterlyData() {
		return FinanceDepartment.getInstance().getQuarterlyData();
	}

	/*
	 * LOGISTICS
	 */

	public ExternalPartner getExternalPartner() {
		return LogisticsDepartment.getInstance().getExternalPartner();
	}

	public InternalFleet getInternalFleet() {
		return LogisticsDepartment.getInstance().getInternalFleet();
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

	/*
	 * PRODUCTION
	 */

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
	public double buyMachinery(Machinery machinery, LocalDate gameDate) {
		return ProductionDepartment.getInstance().buyMachinery(machinery, gameDate);
	}

	public double sellMachinery(Machinery machinery) {
		return ProductionDepartment.getInstance().sellMachinery(machinery);
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

	public double launchProduct(Product product, int quantity) {
		return ProductionDepartment.getInstance().launchProduct(product, quantity,
				WarehousingDepartment.getInstance().calculateFreeStorage());
	}

	public double produceProduct(Product product, int quantity) {
		return ProductionDepartment.getInstance().produceProduct(product, quantity,
				WarehousingDepartment.getInstance().calculateFreeStorage());
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
	public double buildWarehouse() {
		return WarehousingDepartment.getInstance().buildWarehouse(GameState.getInstance().getGameDate());
	}

	public double rentWarehouse() {
		return WarehousingDepartment.getInstance().rentWarehouse();
	}

	public double sellWarehouse(Warehouse warehouse) {
		return WarehousingDepartment.getInstance().sellWarehouse(warehouse);
	}

	public Map<Warehouse, Double> getAllWarehouseResaleValues() {
		return WarehousingDepartment.getInstance().getAllWarehouseResaleValues();
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
	 * 
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
	 * 
	 * @param internal   if conduct market research internally then set true, else
	 *                   if you outsource then set false.
	 * @param report     the report type you want to do.
	 * @param surveyType the survey methodology.
	 * @param data       the data as a Map of string as key and double value pair.
	 */
	public void conductMarketResearch(boolean internal, Reports report, SurveyTypes surveyType,
			Map<String, Double> data) {
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
	 * 
	 * @param e the employee you want to hire.
	 */
	public void hireEmployee(Employee e) {
		HRDepartment.getInstance().hire(e);
	}

	/**
	 * Fire the employee. He will be removed from the team.
	 * 
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
	 * Train the employee. Note: each employee maintains a list of trainings he did.
	 *
	 * @param e the employee you want to train.
	 * @param t the training he should do.
	 */
	public void trainEmployee(Employee e, Training t) {
		HRDepartment.getInstance().trainEmployee(e, t);
	}

	public double getTotalQualityOfWorkByEmployeeType(EmployeeType employeeType) {
		return HRDepartment.getInstance().getTotalQualityOfWorkByEmployeeType(employeeType);
	}

}