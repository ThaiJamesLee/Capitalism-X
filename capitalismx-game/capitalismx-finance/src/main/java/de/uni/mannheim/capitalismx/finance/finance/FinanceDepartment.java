package de.uni.mannheim.capitalismx.finance.finance;

import de.uni.mannheim.capitalismx.domain.department.DepartmentImpl;
import de.uni.mannheim.capitalismx.hr.department.HRDepartment;
import de.uni.mannheim.capitalismx.logistic.logistics.InternalFleet;
import de.uni.mannheim.capitalismx.logistic.logistics.LogisticsDepartment;
import de.uni.mannheim.capitalismx.logistic.logistics.Truck;
import de.uni.mannheim.capitalismx.logistic.support.ProductSupport;
import de.uni.mannheim.capitalismx.production.Machinery;
import de.uni.mannheim.capitalismx.production.ProductionDepartment;
import de.uni.mannheim.capitalismx.utils.data.PropertyChangeSupportBoolean;
import de.uni.mannheim.capitalismx.utils.data.PropertyChangeSupportDouble;
import de.uni.mannheim.capitalismx.warehouse.Warehouse;
import de.uni.mannheim.capitalismx.warehouse.WarehouseType;
import de.uni.mannheim.capitalismx.warehouse.WarehousingDepartment;

import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sdupper
 */
public class FinanceDepartment extends DepartmentImpl {

    private static FinanceDepartment instance;

    private PropertyChangeSupportDouble netWorth;
    private PropertyChangeSupportDouble cash;
    private PropertyChangeSupportDouble assets;
    private PropertyChangeSupportDouble liabilities;
    private double totalTruckValues;
    private double totalMachineValues;
    private double totalWarehousingValues;
    private double totalInvestmentAmount;
    private PropertyChangeSupportDouble realEstateInvestmentAmount;
    private PropertyChangeSupportDouble stocksInvestmentAmount;
    private PropertyChangeSupportDouble ventureCapitalInvestmentAmount;
    private double nopat;
    private double assetsSold;
    private double ebit;
    private double incomeTax;
    private double taxRate;
    private double totalRevenue;
    private double totalHRCosts;
    private double totalWarehouseCosts;
    private double totalLogisticsCosts;
    private double totalProductionCosts;
    private double totalMarketingCosts;
    private double totalSupportCosts;
    private double totalExpenses;
    private double decreaseNopatFactor;
    private double decreaseNopatConstant;
    private PropertyChangeSupportBoolean gameOver;

    private List<Warehouse> warehousesSold;
    private List<Truck> trucksSold;
    private List<Machinery> machinesSold;
    private List<Double> nopatLast5Years;

    //private BankingSystem bankingSystem;
    private List<Investment> investments;


    protected FinanceDepartment(){
        super("Finance");
        this.gameOver = new PropertyChangeSupportBoolean();
        this.gameOver.setValue(false);
        this.gameOver.setPropertyChangedName("gameOver");

        this.cash = new PropertyChangeSupportDouble();
        this.cash.setValue(1000000.0);
        this.cash.setPropertyChangedName("cash");

        this.netWorth = new PropertyChangeSupportDouble();
        this.netWorth.setValue(1000000.0);
        this.netWorth.setPropertyChangedName("netWorth");

        this.assets = new PropertyChangeSupportDouble();
        this.assets.setValue(0.0);
        this.assets.setPropertyChangedName("assets");

        this.liabilities = new PropertyChangeSupportDouble();
        this.liabilities.setValue(0.0);
        this.liabilities.setPropertyChangedName("liabilities");

        this.realEstateInvestmentAmount = new PropertyChangeSupportDouble();
        this.realEstateInvestmentAmount.setValue(0.0);
        this.realEstateInvestmentAmount.setPropertyChangedName("realEstateInvestmentAmount");

        this.stocksInvestmentAmount = new PropertyChangeSupportDouble();
        this.stocksInvestmentAmount.setValue(0.0);
        this.stocksInvestmentAmount.setPropertyChangedName("stocksInvestmentAmount");

        this.ventureCapitalInvestmentAmount = new PropertyChangeSupportDouble();
        this.ventureCapitalInvestmentAmount.setValue(0.0);
        this.ventureCapitalInvestmentAmount.setPropertyChangedName("ventureCapitalInvestmentAmount");

        this.taxRate = 0.2;
        //this.bankingSystem = new BankingSystem();
        this.investments = new ArrayList<Investment>();
        this.decreaseNopatFactor = 0.0;
        this.decreaseNopatConstant = 0.0;
        this.warehousesSold = new ArrayList<>();
        this.trucksSold = new ArrayList<>();
        this.machinesSold = new ArrayList<>();
        this.nopatLast5Years = new ArrayList<>();
        //this.bankingSystem = BankingSystem.getInstance();
    }

    public static synchronized FinanceDepartment getInstance() {
        if(FinanceDepartment.instance == null) {
            FinanceDepartment.instance = new FinanceDepartment();
        }
        return FinanceDepartment.instance;
    }

    public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
        // TODO add all property changelisteners here
        this.gameOver.addPropertyChangeListener(propertyChangeListener);
        this.netWorth.addPropertyChangeListener(propertyChangeListener);
        this.cash.addPropertyChangeListener(propertyChangeListener);
        this.assets.addPropertyChangeListener(propertyChangeListener);
        this.liabilities.addPropertyChangeListener(propertyChangeListener);
        this.realEstateInvestmentAmount.addPropertyChangeListener(propertyChangeListener);
        this.stocksInvestmentAmount.addPropertyChangeListener(propertyChangeListener);
        this.ventureCapitalInvestmentAmount.addPropertyChangeListener(propertyChangeListener);
    }

    // liabilities = loanAmount
    public double calculateNetWorth(LocalDate gameDate){
        //this.netWorth = this.cash + this.assets - this.liabilities;
        //TODO maybe getCash() instead of calculateCash(), because calculateCash() only once per day?
        this.netWorth.setValue(this.calculateCash(gameDate) + this.calculateAssets(gameDate) - this.calculateLiabilities(gameDate));
        return this.netWorth.getValue();
    }

    protected double calculateAssets(LocalDate gameDate){
        //this.assets = this.totalTruckValues + this.totalMachineValues + this.totalWarehousingValues + this.totalInvestmentAmount;
        this.assets.setValue(this.calculateTotalTruckValues(gameDate) + this.calculateTotalMachineValues(gameDate) +
                this.calculateTotalWarehousingValues(gameDate) + this.calculateTotalInvestmentAmount());
        return this.assets.getValue();
    }

    // calculated daily
    //TODO reset assetsSold and nopat of current day?
    protected double calculateCash(LocalDate gameDate){
        //this.cash += this.nopat + this.assetsSold;
        double cash = this.cash.getValue() +  this.calculateNopat(gameDate) + this.calculateAssetsSold(gameDate);
        if(cash < 0){
            this.gameOver.setValue(true);
        }else{
            this.cash.setValue(cash);
        }
        return this.cash.getValue();
    }

    private double calculateAnnualDepreciation(double purchasePrice, double usefulLife){
        return purchasePrice / usefulLife;
    }

    public double calculateResellPrice(double purchasePrice, double usefulLife, double timeUsed){
        return purchasePrice - this.calculateAnnualDepreciation(purchasePrice, usefulLife) * timeUsed;
    }

    protected double calculateTotalWarehousingValues(LocalDate gameDate){
        this.totalWarehousingValues = 0;
        List<Warehouse> warehouses = WarehousingDepartment.getInstance().getWarehouses();
        for(Warehouse warehouse : warehouses){
            if(warehouse.getWarehouseType() == WarehouseType.BUILT){
                 this.totalWarehousingValues += this.calculateResellPrice(warehouse.getBuildingCost(),
                 warehouse.getUsefulLife(), warehouse.calculateTimeUsed(gameDate));
            }
        }
        return this.totalWarehousingValues;
    }

    protected double calculateTotalTruckValues(LocalDate gameDate){
        this.totalTruckValues = 0;
        ArrayList<Truck> trucks = InternalFleet.getInstance().getTrucks();
        for(Truck truck : trucks){
            this.totalTruckValues += this.calculateResellPrice(truck.getPurchasePrice(),
                    truck.getUsefulLife(), truck.calculateTimeUsed(gameDate));
        }
        return this.totalTruckValues;
    }

    protected double calculateTotalMachineValues(LocalDate gameDate){
        this.totalMachineValues = 0;
        List<Machinery> machines = ProductionDepartment.getInstance().getMachines();
        for(Machinery machine : machines){
            this.totalMachineValues += this.calculateResellPrice(machine.getPurchasePrice(),
                    machine.getUsefulLife(), machine.calculateTimeUsed(gameDate));
        }
        return this.totalMachineValues;
    }

    //TODO timestamp or thread in class that checks if new day
    public void sellWarehouse(Warehouse warehouse){
        this.warehousesSold.add(warehouse);
    }

    //TODO timestamp or thread in class that checks if new day
    public void sellTruck(Truck truck){
        this.trucksSold.add(truck);
        LogisticsDepartment.getInstance().removeTruckFromFleet(truck);
    }

    //TODO timestamp or thread in class that checks if new day
    public void sellMachine(Machinery machine){
        this.machinesSold.add(machine);
    }

    protected double calculateAssetsSold(LocalDate gameDate){
        this.assetsSold = 0;
        for(Warehouse warehouse : this.warehousesSold){
            this.assetsSold += this.calculateResellPrice(warehouse.getBuildingCost(),
                    warehouse.getUsefulLife(), warehouse.calculateTimeUsed(gameDate));
        }
        for(Truck truck : this.trucksSold){
            this.assetsSold += this.calculateResellPrice(truck.getPurchasePrice(),
                    truck.getUsefulLife(), truck.calculateTimeUsed(gameDate));
        }
        for(Machinery machine : this.machinesSold){
            this.assetsSold += this.calculateResellPrice(machine.getPurchasePrice(),
                    machine.getUsefulLife(), machine.calculateTimeUsed(gameDate));
        }
        return this.assetsSold;
    }

    // corrected formula in documentation
    public double calculateNopat(LocalDate gameDate){
        //this.nopat = this.ebit - this.incomeTax;
        this.nopat = ((this.calculateEbit(gameDate) - this.calculateIncomeTax(gameDate)) * (1 - this.decreaseNopatFactor)) - this.decreaseNopatConstant;
        return this.nopat;
    }

    //TODO negative EBIT
    protected double calculateIncomeTax(LocalDate gameDate){
        //this.incomeTax = this.ebit * this.taxRate;
        this.incomeTax = 0;
        if(this.calculateEbit(gameDate) >= 0){
            this.incomeTax = this.ebit * this.taxRate;
        }
        return this.incomeTax;
    }

    //TODO
    private double calculateTotalRevenue(){
        /**
        ArrayList<Product> productsSold = null;
        this.totalRevenue = 0;
        for(Product product : productsSold){
            this.totalRevenue += product.getSalesFigures() * product.getSalesPrice();
        }
         **/
        //return this.totalRevenue;
        return 0.0;
    }

    private double calculateTotalExpenses(LocalDate gameDate){
        //this.totalExpenses = this.totalHRCosts + this.totalWarehouseCosts + this.totalLogisticsCosts + this.totalProductionCosts + this.totalMarketingCosts + this.totalSupportCosts;
        this.totalExpenses = this.calculateTotalHRCosts(gameDate) + this.calculateTotalWarehouseCosts() + this.calculateTotalLogisticsCosts() + this.calculateTotalProductionCosts()
                + this.calculateTotalMarketingCosts() + this.calculateTotalSupportCosts();
        return this.totalExpenses;
    }

    protected double calculateTotalHRCosts(LocalDate gameDate){
        double totalTrainingCosts = HRDepartment.getInstance().calculateTotalTrainingCosts();
        double totalSalaries = HRDepartment.getInstance().calculateTotalSalaries();
        totalSalaries /= gameDate.lengthOfYear();
        this.totalHRCosts = totalSalaries + totalTrainingCosts;
        return this.totalHRCosts;
    }

    //TODO
    protected double calculateTotalWarehouseCosts(){
        double warehouseCosts = WarehousingDepartment.getInstance().calculateMonthlyCostWarehousing();
        double storageCosts = WarehousingDepartment.getInstance().calculateDailyStorageCost();

        this.totalWarehouseCosts = warehouseCosts + storageCosts;
        return this.totalWarehouseCosts;
    }

    //TODO
    protected double calculateTotalLogisticsCosts(){
        this.totalLogisticsCosts = LogisticsDepartment.getInstance().getTotalLogisticsCosts();
        return this.totalLogisticsCosts;
    }

    //TODO
    protected double calculateTotalProductionCosts(){
        //double totalProductionCosts = Production.getInstance().calculateProductionVariableCosts() + Production.getInstance().calculateProductionFixCosts();
        this.totalProductionCosts = ProductionDepartment.getInstance().getProductionVariableCosts() + ProductionDepartment.getInstance().getProductionFixCosts();
        return this.totalProductionCosts;
    }

    //TODO
    private double calculateTotalMarketingCosts(){
        /**
        double priceManagementConsultancy = ;
        double priceMarketResearch = ;
        double priceCampaign = ;
        double priceLobbyist = ;
        this.totalMarketingCosts = priceManagementConsultancy + priceMarketResearch + priceCampaign + priceLobbyist;
        return this.totalMarketingCosts;**/
        return 0.0;
    }

    private double calculateTotalSupportCosts(){
        double totalSupportCosts = ProductSupport.getInstance().calculateTotalSupportCosts();
        return totalSupportCosts;
    }

    protected double calculateEbit(LocalDate gameDate){
        //this.ebit = this.totalRevenue - this.totalExpenses;
        this.ebit = this.calculateTotalRevenue() - this.calculateTotalExpenses(gameDate);
        return this.ebit;
    }

    //TODO update cash and netWorth?
    public ArrayList<BankingSystem.Loan> generateLoanSelection(double desiredLoanAmount){
        if(this.cash.getValue() == 0.0){
            //TODO popup
            return null;
        }
        if(desiredLoanAmount > 0.7 * this.netWorth.getValue()){
            desiredLoanAmount = 0.7 * this.netWorth.getValue();
        }
        return BankingSystem.getInstance().generateLoanSelection(desiredLoanAmount);
    }

    public void addLoan(BankingSystem.Loan loan, LocalDate loanDate){
        BankingSystem.getInstance().addLoan(loan, loanDate);
        this.cash.setValue(this.cash.getValue() + loan.getLoanAmount());
    }

    //TODO update cash?
    public ArrayList<Investment> generateInvestmentSelection(double amount){
        if(amount > this.cash.getValue()){
            //TODO popup
            System.out.println("Not enough cash!");
            return null;
        }
        ArrayList<Investment> investmentSelection = new ArrayList<Investment>();
        //Real Estate
        investmentSelection.add(new Investment(amount, Investment.InvestmentType.REAL_ESTATE));
        //Stocks
        investmentSelection.add(new Investment(amount, Investment.InvestmentType.STOCKS));
        //Venture Capital
        investmentSelection.add(new Investment(amount, Investment.InvestmentType.VENTURE_CAPITAL));
        return  investmentSelection;
    }

    public void addInvestment(Investment investment){
        this.investments.add(investment);
        this.cash.setValue(this.cash.getValue() - investment.getAmount());
        this.assets.setValue(this.assets.getValue() + investment.getAmount());
        //this.calculateTotalInvestmentAmount();
    }

    //TODO taxes
    //TODO update cash?
    public void removeInvestment(Investment investment){
        this.investments.remove(investment);
        this.cash.setValue(this.cash.getValue() + investment.getAmount());
        //this.calculateTotalInvestmentAmount();
    }

    //TODO
    protected double calculateLiabilities(LocalDate gameDate){
        //this.liabilities = BankingSystem.getInstance().getAnnualPrincipalBalance();
        this.liabilities.setValue(BankingSystem.getInstance().calculateAnnualPrincipalBalance(gameDate));
        return this.liabilities.getValue();
    }

    protected double calculateTotalInvestmentAmount(){
        this.totalInvestmentAmount = 0;
        this.realEstateInvestmentAmount.setValue(0.0);
        this.stocksInvestmentAmount.setValue(0.0);
        this.ventureCapitalInvestmentAmount.setValue(0.0);
        for(Investment investment : this.investments){
            investment.updateAmount();
            this.totalInvestmentAmount += investment.getAmount();
            switch(investment.getInvestmentType()){
                case REAL_ESTATE:
                    this.realEstateInvestmentAmount.setValue(this.realEstateInvestmentAmount.getValue() + investment.getAmount());
                    break;
                case STOCKS:
                    this.stocksInvestmentAmount.setValue(this.stocksInvestmentAmount.getValue() + investment.getAmount());
                    break;
                case VENTURE_CAPITAL:
                    this.ventureCapitalInvestmentAmount.setValue(this.ventureCapitalInvestmentAmount.getValue() + investment.getAmount());
                    break;

            }
        }
        return this.totalInvestmentAmount;
    }

    public boolean checkIncreasingNopat(){
        for(int i = 0; i < this.nopatLast5Years.size() - 1; i++){
            if(this.nopatLast5Years.get(i + 1) < this.nopatLast5Years.get(i) * 1.30){
                return false;
            }
        }
        return true;
    }

    public void decreaseCash(double amount){
        double cash = this.cash.getValue() - amount;
        if(cash < 0){
            this.gameOver.setValue(true);
        }else{
            this.cash.setValue(cash);
        }
    }

    public void increaseCash(double amount){
        this.cash.setValue(this.cash.getValue() + amount);
    }

    public void decreaseNetWorth(double amount){
        this.netWorth.setValue(this.netWorth.getValue() - amount);
    }

    public void increaseNetWorth(double amount){
        this.netWorth.setValue(this.netWorth.getValue() + amount);
    }

    public void increaseAssets(double amount){
        this.assets.setValue(this.assets.getValue() + amount);
    }

    public void increaseLiabilities(double amount){
        this.liabilities.setValue(this.liabilities.getValue() + amount);
    }

    //TODO decide on suitable consequences of acquisition, e.g., increase assets
    public void acquireCompany(LocalDate gameDate){
        this.increaseCash(10000);
        this.decreaseCash(this.calculateNopat(gameDate) * 0.70);
    }

    public void decreaseNopatRelPermanently(double amount){
        this.decreaseNopatFactor += amount;
    }

    public void increaseNopatRelPermanently(double amount){
        this.decreaseNopatFactor -= amount;
    }

    public void increaseTaxRate(double amount){
        this.taxRate += amount;
    }

    //TODO only for one year?
    public void decreaseTaxRate(double amount){
        this.taxRate -= amount;
    }

    public void nopatFine(double amount){
        //this.decreaseCash(this.calculateNopat(gameDate) * amount);
        this.decreaseCash(this.nopat * amount);
    }

    public void decreaseNopatConstant(double amount){
        this.decreaseNopatConstant += amount;
    }

    public void setDecreaseNopatConstant(double amount){
        this.decreaseNopatConstant = amount;
    }

    public double getCash() {
        return this.cash.getValue();
    }

    public List<Investment> getInvestments() {
        return this.investments;
    }

    public static void setInstance(FinanceDepartment instance) {
        FinanceDepartment.instance = instance;
    }

    public double getAssets() {
        return this.assets.getValue();
    }

    public double getLiabilities() {
        return this.liabilities.getValue();
    }

    public double getNetWorth() {
        return this.netWorth.getValue();
    }

    public BankingSystem.Loan getLoan(){
        return BankingSystem.getInstance().getLoan();
    }

    public double getRealEstateInvestmentAmount() {
        return this.realEstateInvestmentAmount.getValue();
    }

    public double getStocksInvestmentAmount() {
        return this.stocksInvestmentAmount.getValue();
    }

    public double getVentureCapitalInvestmentAmount() {
        return this.ventureCapitalInvestmentAmount.getValue();
    }

    public void increaseRealEstateInvestmentAmount(double amount){
        this.realEstateInvestmentAmount.setValue(this.realEstateInvestmentAmount.getValue() + amount);
    }

    public void increaseStocksInvestmentAmount(double amount){
        this.stocksInvestmentAmount.setValue(this.stocksInvestmentAmount.getValue() + amount);
    }

    public void increaseVentureCapitalInvestmentAmount(double amount){
        this.ventureCapitalInvestmentAmount.setValue(this.ventureCapitalInvestmentAmount.getValue() + amount);
    }
}
