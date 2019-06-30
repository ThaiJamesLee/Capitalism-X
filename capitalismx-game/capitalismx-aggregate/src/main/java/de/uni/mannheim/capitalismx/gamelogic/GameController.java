package de.uni.mannheim.capitalismx.gamelogic;

import de.uni.mannheim.capitalismx.ecoindex.CompanyEcoIndex;
import de.uni.mannheim.capitalismx.external_events.ExternalEvents;
import de.uni.mannheim.capitalismx.finance.finance.BankingSystem;
import de.uni.mannheim.capitalismx.finance.finance.Finance;
import de.uni.mannheim.capitalismx.finance.finance.Investment;
import de.uni.mannheim.capitalismx.logistic.logistics.ExternalPartner;
import de.uni.mannheim.capitalismx.logistic.logistics.InternalFleet;
import de.uni.mannheim.capitalismx.logistic.logistics.Logistics;
import de.uni.mannheim.capitalismx.logistic.logistics.Truck;
import de.uni.mannheim.capitalismx.logistic.support.ProductSupport;
import de.uni.mannheim.capitalismx.procurement.component.Component;
import de.uni.mannheim.capitalismx.procurement.component.ComponentCategory;
import de.uni.mannheim.capitalismx.procurement.component.SupplierCategory;
import de.uni.mannheim.capitalismx.production.*;
import de.uni.mannheim.capitalismx.warehouse.Warehouse;
import de.uni.mannheim.capitalismx.warehouse.WarehouseType;
import de.uni.mannheim.capitalismx.warehouse.Warehousing;
import jdk.vm.ci.meta.Local;
import sun.rmi.runtime.Log;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameController {

    private static GameController instance;
    private boolean isNewYear;

    private GameController() {
    }

    public static GameController getInstance() {
        if(instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    public void nextDay() {
        LocalDate oldDate = GameState.getInstance().getGameDate();
        GameState.getInstance().setGameDate(GameState.getInstance().getGameDate().plusDays(1));
        LocalDate newDate = GameState.getInstance().getGameDate();
        if(oldDate.getMonth() != newDate.getMonth()) {
            Production.getInstance().resetMonthlyPerformanceMetrics();
        }
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
        this.updateWarehouse();
        this.updateProcurement();
        this.updateProduction();
    }

    private void updateCompanyEcoIndex() {
        CompanyEcoIndex.getInstance().calculateAll();
    }

    private void updateExternalEvents() {
        ExternalEvents.getInstance().checkEvents();
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
        Production.getInstance().calculateAll(GameState.getInstance().getGameDate());
    }

    private void updateWarehouse() {
        Warehousing.getInstance().calculateAll();
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
        Production.getInstance().calculateAll(GameState.getInstance().getGameDate());
    }

    private void setInitialWarehouseValues() {
        // TODO really needed?
        Warehousing.getInstance().calculateAll();
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

    public CompanyEcoIndex.EcoIndex getEcoIndex(){
        return CompanyEcoIndex.getInstance().getEcoIndex();
    }

    public List<ExternalEvents.ExternalEvent> getExternalEvents() {
        return ExternalEvents.getInstance().getExternalEvents();
    }


    /*
    *  PROCUREMENT
    * */
    public String getComponentName(Component component) {
        return component.getComponentName();
    }

    public int getComponentLevel(Component component) {
        return component.getComponentLevel();
    }

    public double getInitialComponentPrice(Component component) {
        return component.getInitialComponentPrice();
    }

    public int getComponentBaseUtility(Component component) {
        return component.getBaseUtility();
    }

    public int getComponentAvailabilityDate (Component component) {
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

    public List<Component> getAllAvailableComponents() {
        return Production.getInstance().getAllAvailableComponents(GameState.getInstance().getGameDate());
    }

    public List<Component> getAvailableComponentsOfComponentCategory(ComponentCategory componentCategory) {
        return Production.getInstance().getAvailableComponentsOfComponentCategory(GameState.getInstance().getGameDate(), componentCategory);
    }

    /*
     *  PRODUCTION
     * */

    /* Production getter */
    public Map<Product, Integer> getProducedProducts() {
        return Production.getInstance().getNumberProducedProducts();
    }

    public List<Machinery> getMachines() {
        return Production.getInstance().getMachines();
    }

    public ProductionTechnology getProductionTechnology() {
        return Production.getInstance().getProductionTechnology();
    }

    public ProductionInvestment getResearchAndDevelopment() {
        return Production.getInstance().getResearchAndDevelopment();
    }

    public ProductionInvestment getProcessAutomation() {
        return Production.getInstance().getProcessAutomation();
    }

    public double getTotalEngineerProductivity() {
        return Production.getInstance().getTotalEngineerProductivity();
    }

    public ProductionInvestment getSystemSecurity() {
        return Production.getInstance().getSystemSecurity();
    }

    public double getProductionVariableCosts() {
        return Production.getInstance().getProductionVariableCosts();
    }

    public double getProductionFixCosts() {
        return Production.getInstance().getProductionFixCosts();
    }

    public double getNumberUnitsProducedPerMonth() {
        return Production.getInstance().getNumberUnitsProducedPerMonth();
    }

    public double getMonthlyAvailableMachineCapacity() {
        return Production.getInstance().getMonthlyAvailableMachineCapacity();
    }

    public double getManufactureEfficiency() {
        return Production.getInstance().getManufactureEfficiency();
    }

    public double getProductionProcessProductivity() {
        return Production.getInstance().getProductionProcessProductivity();
    }

    public double getNormalizedProductionProcessProductivity() {
        return Production.getInstance().getNormalizedProductionProcessProductivity();
    }

    /* machinery game mechanics */
    public double buyMachinery(Machinery machinery, LocalDate gameDate) {
        return Production.getInstance().buyMachinery(machinery, gameDate);
    }

    public double sellMachinery(Machinery machinery) {
        return Production.getInstance().sellMachinery(machinery);
    }

    public Map<Machinery, Double> getMachineryResellPrices() {
        return Production.getInstance().calculateMachineryResellPrices();
    }

    public double maintainAndRepairMachinery(Machinery machinery) {
        return Production.getInstance().maintainAndRepairMachinery(machinery, GameState.getInstance().getGameDate());
    }

    public double updateMachinery(Machinery machinery) {
        return Production.getInstance().upgradeMachinery(machinery, GameState.getInstance().getGameDate());
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
    public double launchProduct(String productname, ProductCategory productCategory, List<Component> componentList, int quantity) {
        return Production.getInstance().launchProduct(productname, productCategory, componentList, quantity, Warehousing.getInstance().calculateFreeStorage());
    }

    public double produceProduct(Product product, int quantity) {
        return  Production.getInstance().produceProduct(product, quantity, Warehousing.getInstance().calculateFreeStorage());
    }

    public double getAmountProductInProduction(Product product) {
        return Production.getInstance().getAmountInProduction(product);
    }

    public void setProductSalesPrice(Product product, double salesPrice) {
        Production.getInstance().setProductSalesPrice(product, salesPrice);
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

    public double getProductCosts(Product product) {
        return product.getTotalProductCosts();
    }

    public double getProductsSalesPrice(Product product) {
        return product.getSalesPrice();
    }

    public double getProductProfitMargin(Product product) {
        return product.getProfitMargin();
    }

    /* production investment game mechanics*/
    public double investInSystemSecurity(int level, LocalDate gameDate) {
        return Production.getInstance().investInSystemSecurity(level, gameDate);
    }

    public double investInResearchAndDevelopment(int level, LocalDate gameDate) {
        return Production.getInstance().investInResearchAndDevelopment(level, gameDate);
    }

    public double investInProcessAutomation(int level, LocalDate gameDate) {
        return Production.getInstance().investInProcessAutomation(level, gameDate);
    }

    public LocalDate getLastInvestmentDateSystemSecurity() {
        return Production.getInstance().getSystemSecurity().getLastInvestmentDate();
    }

    public LocalDate getLastInvestmentDateResearchAndDevelopment() {
        return Production.getInstance().getResearchAndDevelopment().getLastInvestmentDate();
    }

    public LocalDate getLastInvestmentDateProcessAutomation() {
        return Production.getInstance().getProcessAutomation().getLastInvestmentDate();
    }

    public int getSystemSecurityLevel() {
        return Production.getInstance().getSystemSecurity().getLevel();
    }

    public int getResearchAndDevelopmentLevel() {
        return Production.getInstance().getResearchAndDevelopment().getLevel();
    }

    public int getProcessAutomationLevel() {
        return Production.getInstance().getProcessAutomation().getLevel();
    }

    /*
    * WAREHOUSING
    * */

    /* warehousing getter and game mechanic */
    // TODO SALE OF PRODUCTS
    /*public double sellProducts() {
        return Warehousing.getInstance().sellProducts()
    }*/

    public List<Warehouse> getWarehouses() {
        return Warehousing.getInstance().getWarehouses();
    }

    public Map<Product, Integer> getWarehousingInventory() {
        return Warehousing.getInstance().getInventory();
    }

    public int getTotalWarehousingCapacity() {
        return Warehousing.getInstance().getTotalCapacity();
    }

    public int getFreeStorage() {
        return Warehousing.getInstance().getFreeStorage();
    }

    public int getStoredUnits() {
        return Warehousing.getInstance().getStoredUnits();
    }

    public double getMonthlyWarehousingCost() {
        return Warehousing.getInstance().getMonthlyCostWarehousing();
    }

    public double getDailyWarehousingStorageCost() {
        return Warehousing.getInstance().getDailyStorageCost();
    }

    public double getMonthlyWarehousingStorageCost() {
        return Warehousing.getInstance().getMonthlyStorageCost();
    }

    public double getMonthlyTotalWarehousingCost() {
        return Warehousing.getInstance().getMonthlyTotalCostWarehousing();
    }

    /* warehouse game mechanics*/
    public double buildWarehouse() {
        return Warehousing.getInstance().buildWarehouse(GameState.getInstance().getGameDate());
    }

    public double rentWarehouse() {
        return Warehousing.getInstance().rentWarehouse();
    }

    public double sellWarehouse(Warehouse warehouse) {
        return Warehousing.getInstance().sellWarehouse(warehouse);
    }

    public Map<Warehouse, Double> getAllWarehouseResaleValues() {
        return Warehousing.getInstance().getAllWarehouseResaleValues();
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
}
