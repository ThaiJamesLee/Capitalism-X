package de.uni.mannheim.capitalismx.finance.finance;

import de.uni.mannheim.capitalismx.domain.department.DepartmentImpl;
import de.uni.mannheim.capitalismx.hr.department.HRDepartment;
import de.uni.mannheim.capitalismx.logistic.logistics.InternalFleet;
import de.uni.mannheim.capitalismx.logistic.logistics.LogisticsDepartment;
import de.uni.mannheim.capitalismx.logistic.logistics.Truck;
import de.uni.mannheim.capitalismx.logistic.support.ProductSupport;
import de.uni.mannheim.capitalismx.marketing.department.MarketingDepartment;
import de.uni.mannheim.capitalismx.production.Machinery;
import de.uni.mannheim.capitalismx.production.ProductionDepartment;
import de.uni.mannheim.capitalismx.utils.data.PropertyChangeSupportBoolean;
import de.uni.mannheim.capitalismx.utils.data.PropertyChangeSupportDouble;
import de.uni.mannheim.capitalismx.utils.number.DecimalRound;
import de.uni.mannheim.capitalismx.warehouse.Warehouse;
import de.uni.mannheim.capitalismx.warehouse.WarehouseType;
import de.uni.mannheim.capitalismx.warehouse.WarehousingDepartment;

import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.util.*;

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
    private Double netWorthDifference;
    private Double cashDifference;
    private PropertyChangeSupportBoolean gameOver;

    private List<Warehouse> warehousesSold;
    private List<Truck> trucksSold;
    private List<Machinery> machinesSold;
    private List<Double> nopatLast5Years;

    private TreeMap<LocalDate, Double> salesHistory;
    private TreeMap<LocalDate, Double> hrCostsHistory;
    private TreeMap<LocalDate, Double> warehouseCostsHistory;
    private TreeMap<LocalDate, Double> logisticsCostsHistory;
    private TreeMap<LocalDate, Double> productionCostsHistory;
    private TreeMap<LocalDate, Double> marketingCostsHistory;
    private TreeMap<LocalDate, Double> supportCostsHistory;
    private TreeMap<LocalDate, Double> ebitHistory;
    private TreeMap<LocalDate, Double> taxHistory;
    private TreeMap<LocalDate, Double> nopatHistory;

    private TreeMap<LocalDate, Double> cashHistory;
    private TreeMap<LocalDate, Double> assetsHistory;
    private TreeMap<LocalDate, Double> liabilitiesHistory;
    private TreeMap<LocalDate, Double> netWorthHistory;
    private Map<String, TreeMap<LocalDate, Double>> histories;
    private Map<String, TreeMap<LocalDate, Double>> historiesForQuarterlyData;
    private TreeMap<String, String[]> monthlyData;
    private TreeMap<String, String[]> quarterlyData;
    //TODO just to notify gui to update finance table with new monthlyData / quarterlyData every day
    private PropertyChangeSupportBoolean updatedMonthlyData;
    private PropertyChangeSupportBoolean updatedQuarterlyData;


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

        this.cashHistory = new TreeMap<>();
        this.assetsHistory = new TreeMap<>();
        this.liabilitiesHistory = new TreeMap<>();
        this.netWorthHistory = new TreeMap<>();

        this.histories = new TreeMap<>();
        this.histories.put("cashHistory", this.cashHistory);
        this.histories.put("assetsHistory", this.assetsHistory);
        this.histories.put("liabilitiesHistory", this.liabilitiesHistory);
        this.histories.put("netWorthHistory", this.netWorthHistory);

        this.salesHistory = new TreeMap<>();
        this.hrCostsHistory = new TreeMap<>();
        this.warehouseCostsHistory = new TreeMap<>();
        this.logisticsCostsHistory = new TreeMap<>();
        this.productionCostsHistory = new TreeMap<>();
        this.marketingCostsHistory = new TreeMap<>();
        this.supportCostsHistory = new TreeMap<>();
        this.ebitHistory = new TreeMap<>();
        this.taxHistory = new TreeMap<>();
        this.nopatHistory = new TreeMap<>();

        this.historiesForQuarterlyData = new TreeMap<>();
        this.historiesForQuarterlyData.put("salesHistory", this.salesHistory);
        this.historiesForQuarterlyData.put("hrCostsHistory", this.hrCostsHistory);
        this.historiesForQuarterlyData.put("warehouseCostsHistory", this.warehouseCostsHistory);
        this.historiesForQuarterlyData.put("logisticsCostsHistory", this.logisticsCostsHistory);
        this.historiesForQuarterlyData.put("productionCostsHistory", this.productionCostsHistory);
        this.historiesForQuarterlyData.put("marketingCostsHistory", this.marketingCostsHistory);
        this.historiesForQuarterlyData.put("supportCostsHistory", this.supportCostsHistory);
        this.historiesForQuarterlyData.put("ebitHistory", this.ebitHistory);
        this.historiesForQuarterlyData.put("taxHistory", this.taxHistory);
        this.historiesForQuarterlyData.put("nopatHistory", this.nopatHistory);

        this.monthlyData = new TreeMap<>();
        this.quarterlyData = new TreeMap<>();

        this.updatedMonthlyData = new PropertyChangeSupportBoolean();
        this.updatedMonthlyData.setValue(false);
        this.updatedMonthlyData.setPropertyChangedName("updatedMonthlyData");

        this.updatedQuarterlyData = new PropertyChangeSupportBoolean();
        this.updatedQuarterlyData.setValue(false);
        this.updatedQuarterlyData.setPropertyChangedName("updatedQuarterlyData");

        this.taxRate = 0.2;
        //this.bankingSystem = new BankingSystem();
        this.investments = new ArrayList<Investment>();
        this.investments.add(new Investment(0, Investment.InvestmentType.REAL_ESTATE));
        this.investments.add(new Investment(0, Investment.InvestmentType.STOCKS));
        this.investments.add(new Investment(0, Investment.InvestmentType.VENTURE_CAPITAL));
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

    // liabilities = loanAmount
    public double calculateNetWorth(LocalDate gameDate){
        //this.netWorth = this.cash + this.assets - this.liabilities;
        //TODO maybe getCash() instead of calculateCash(), because calculateCash() only once per day?
        this.netWorth.setValue(this.calculateCash(gameDate) + this.calculateAssets(gameDate) - this.calculateLiabilities(gameDate));
        this.netWorthHistory.put(gameDate, this.netWorth.getValue());
        return this.netWorth.getValue();
    }

    protected double calculateAssets(LocalDate gameDate){
        //this.assets = this.totalTruckValues + this.totalMachineValues + this.totalWarehousingValues + this.totalInvestmentAmount;
        this.assets.setValue(this.calculateTotalTruckValues(gameDate) + this.calculateTotalMachineValues(gameDate) +
                this.calculateTotalWarehousingValues(gameDate) + this.calculateTotalInvestmentAmount());
        this.assetsHistory.put(gameDate, this.assets.getValue());
        return this.assets.getValue();
    }

    // calculated daily
    //TODO reset assetsSold and nopat of current day?
    //TODO consider liabilities for cash calculation?
    protected double calculateCash(LocalDate gameDate){
        //this.cash += this.nopat + this.assetsSold;
        double cash = this.cash.getValue() +  this.calculateNopat(gameDate) + this.calculateAssetsSold(gameDate);
        cash -= BankingSystem.getInstance().calculateMonthlyLoanRate(gameDate);
        if(cash < 0){
            this.gameOver.setValue(true);
        }else{
            this.cash.setValue(cash);
        }
        this.cashHistory.put(gameDate, this.cash.getValue());
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
        //TODO what to do when ebit is negative
        //TODO display decreaseNopatFactor/decreaseNopatConstant in operations table (finance ui)
        this.nopat = ((this.calculateEbit(gameDate) - Math.max(this.calculateIncomeTax(gameDate), 0.0)) * (1 - this.decreaseNopatFactor)) - this.decreaseNopatConstant;
        this.nopatHistory.put(gameDate, this.nopat);
        return this.nopat;
    }

    //TODO negative EBIT
    protected double calculateIncomeTax(LocalDate gameDate){
        //this.incomeTax = this.ebit * this.taxRate;
        this.incomeTax = 0;
        if(this.calculateEbit(gameDate) >= 0){
            this.incomeTax = this.ebit * this.taxRate;
        }
        this.taxHistory.put(gameDate, this.incomeTax);
        return this.incomeTax;
    }

    //TODO
    private double calculateTotalRevenue(LocalDate gameDate){
        /**
        ArrayList<Product> productsSold = null;
        this.totalRevenue = 0;
        for(Product product : productsSold){
            this.totalRevenue += product.getSalesFigures() * product.getSalesPrice();
        }
         **/
        //return this.totalRevenue;
        //TODO
        //this.salesHistory.put(gameDate, this.totalRevenue);
        this.salesHistory.put(gameDate, 0.0);
        return 0.0;
    }

    private double calculateTotalExpenses(LocalDate gameDate){
        //this.totalExpenses = this.totalHRCosts + this.totalWarehouseCosts + this.totalLogisticsCosts + this.totalProductionCosts + this.totalMarketingCosts + this.totalSupportCosts;
        this.totalExpenses = this.calculateTotalHRCosts(gameDate) + this.calculateTotalWarehouseCosts(gameDate) + this.calculateTotalLogisticsCosts(gameDate) + this.calculateTotalProductionCosts(gameDate)
                + this.calculateTotalMarketingCosts(gameDate) + this.calculateTotalSupportCosts(gameDate);
        return this.totalExpenses;
    }

    protected double calculateTotalHRCosts(LocalDate gameDate){
        //TODO only trainings of current day
        double totalTrainingCosts = HRDepartment.getInstance().calculateTotalTrainingCosts();
        double totalSalaries = HRDepartment.getInstance().calculateTotalSalaries();
        totalSalaries /= gameDate.lengthOfYear();
        this.totalHRCosts = totalSalaries + totalTrainingCosts;
        this.hrCostsHistory.put(gameDate, this.totalHRCosts);
        return this.totalHRCosts;
    }

    //TODO
    protected double calculateTotalWarehouseCosts(LocalDate gameDate){
        double warehouseCosts = WarehousingDepartment.getInstance().calculateMonthlyCostWarehousing(gameDate);
        warehouseCosts /= gameDate.lengthOfMonth();
        double storageCosts = WarehousingDepartment.getInstance().calculateDailyStorageCost();

        this.totalWarehouseCosts = warehouseCosts + storageCosts;
        this.warehouseCostsHistory.put(gameDate, this.totalWarehouseCosts);
        return this.totalWarehouseCosts;
    }

    //TODO
    protected double calculateTotalLogisticsCosts(LocalDate gameDate){
        this.totalLogisticsCosts = LogisticsDepartment.getInstance().getTotalLogisticsCosts(gameDate);
        this.logisticsCostsHistory.put(gameDate, this.totalLogisticsCosts);
        return this.totalLogisticsCosts;
    }

    //TODO
    protected double calculateTotalProductionCosts(LocalDate gameDate){
        //double totalProductionCosts = Production.getInstance().calculateProductionVariableCosts() + Production.getInstance().calculateProductionFixCosts();

        this.totalProductionCosts = ProductionDepartment.getInstance().getTotalProductionCosts();
        //ProductionDepartment.getInstance().getProductionVariableCosts() + ProductionDepartment.getInstance().getProductionFixCosts();
        this.productionCostsHistory.put(gameDate, this.totalProductionCosts);
        return this.totalProductionCosts;
    }

    //TODO
    private double calculateTotalMarketingCosts(LocalDate gameDate){
        /**
        double priceManagementConsultancy = ;
        double priceMarketResearch = ;
        double priceCampaign = ;
        double priceLobbyist = ;
        this.totalMarketingCosts = priceManagementConsultancy + priceMarketResearch + priceCampaign + priceLobbyist;
        return this.totalMarketingCosts;**/
        this.totalMarketingCosts = MarketingDepartment.getInstance().getTotalMarketingCosts();
        this.marketingCostsHistory.put(gameDate, this.totalMarketingCosts);
        this.marketingCostsHistory.put(gameDate, 0.0);
        return 0.0;
    }

    private double calculateTotalSupportCosts(LocalDate gameDate){
        double totalSupportCosts = ProductSupport.getInstance().calculateTotalSupportCosts();
        this.totalSupportCosts = totalSupportCosts / gameDate.lengthOfMonth();
        this.supportCostsHistory.put(gameDate, this.totalSupportCosts);
        return this.totalSupportCosts;
    }

    protected double calculateEbit(LocalDate gameDate){
        //this.ebit = this.totalRevenue - this.totalExpenses;
        this.ebit = this.calculateTotalRevenue(gameDate) - this.calculateTotalExpenses(gameDate);
        this.ebitHistory.put(gameDate, this.ebit);
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

    public void addLoan(BankingSystem.Loan loan, LocalDate gameDate){
        BankingSystem.getInstance().addLoan(loan, gameDate);
        this.increaseCash(gameDate, loan.getLoanAmount());
        this.increaseLiabilities(gameDate, loan.getLoanAmount());
    }

    //TODO
    protected double calculateLiabilities(LocalDate gameDate){
        //this.liabilities = BankingSystem.getInstance().getAnnualPrincipalBalance();
        this.liabilities.setValue(BankingSystem.getInstance().calculateAnnualPrincipalBalance(gameDate));
        this.liabilitiesHistory.put(gameDate, this.liabilities.getValue());
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

    public void decreaseCash(LocalDate gameDate, double amount){
        double cash = this.cash.getValue() - amount;
        if(cash < 0){
            this.gameOver.setValue(true);
        }else{
            this.cash.setValue(cash);
            this.cashHistory.put(gameDate, this.cash.getValue());
            this.updateMonthlyData(gameDate);
        }
    }

    public void increaseCash(LocalDate gameDate, double amount){
        this.cash.setValue(this.cash.getValue() + amount);
        this.cashHistory.put(gameDate, this.cash.getValue());
        this.updateMonthlyData(gameDate);
    }

    public void decreaseNetWorth(LocalDate gameDate, double amount){
        this.netWorth.setValue(this.netWorth.getValue() - amount);
        this.netWorthHistory.put(gameDate, this.netWorth.getValue());
        this.updateMonthlyData(gameDate);
    }

    public void increaseNetWorth(LocalDate gameDate, double amount){
        this.netWorth.setValue(this.netWorth.getValue() + amount);
        this.netWorthHistory.put(gameDate, this.netWorth.getValue());
        this.updateMonthlyData(gameDate);
    }

    public void increaseAssets(LocalDate gameDate, double amount){
        this.assets.setValue(this.assets.getValue() + amount);
        this.assetsHistory.put(gameDate, this.assets.getValue());
        this.updateMonthlyData(gameDate);
    }

    public void decreaseAssets(LocalDate gameDate, double amount){
        this.assets.setValue(this.assets.getValue() - amount);
        this.assetsHistory.put(gameDate, this.assets.getValue());
        this.updateMonthlyData(gameDate);
    }

    public void increaseLiabilities(LocalDate gameDate, double amount){
        this.liabilities.setValue(this.liabilities.getValue() + amount);
        this.liabilitiesHistory.put(gameDate, this.liabilities.getValue());
        this.updateMonthlyData(gameDate);
    }

    //TODO decide on suitable consequences of acquisition, e.g., increase assets
    public void acquireCompany(LocalDate gameDate){
        this.increaseCash(gameDate, 10000);
        this.decreaseCash(gameDate, this.calculateNopat(gameDate) * 0.70);
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

    public void nopatFine(LocalDate gameDate, double amount){
        //this.decreaseCash(this.calculateNopat(gameDate) * amount);
        this.decreaseCash(gameDate, this.nopat * amount);
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

    public boolean increaseInvestmentAmount(LocalDate gameDate, double amount, Investment.InvestmentType investmentType){
        if(amount > this.cash.getValue()){
            //TODO popup
            System.out.println("Not enough cash!");
            return false;
        }

        for(Investment investment : investments){
            if(investment.getInvestmentType() == investmentType){
                investment.increaseAmount(amount);
                break;
            }
        }

        switch(investmentType){
            case REAL_ESTATE:
                this.realEstateInvestmentAmount.setValue(this.realEstateInvestmentAmount.getValue() + amount);
                break;
            case STOCKS:
                this.stocksInvestmentAmount.setValue(this.stocksInvestmentAmount.getValue() + amount);
                break;
            case VENTURE_CAPITAL:
                this.ventureCapitalInvestmentAmount.setValue(this.ventureCapitalInvestmentAmount.getValue() + amount);
                break;
        }
        this.decreaseCash(gameDate, amount);
        this.increaseAssets(gameDate, amount);
        return true;
    }

    public boolean decreaseInvestmentAmount(LocalDate gameDate, double amount, Investment.InvestmentType investmentType){
        if(amount < 0){
            //TODO popup
            return false;
        }

        for(Investment investment : investments){
            if(investment.getInvestmentType() == investmentType){
                if(amount > investment.getAmount()){
                    //return false;
                    //TODO popup/notification
                    System.out.println("Using max possible amount");
                    amount = investment.getAmount();
                }
                investment.decreaseAmount(amount);
                break;
            }
        }

        switch(investmentType){
            case REAL_ESTATE:
                this.realEstateInvestmentAmount.setValue(this.realEstateInvestmentAmount.getValue() - amount);
                break;
            case STOCKS:
                this.stocksInvestmentAmount.setValue(this.stocksInvestmentAmount.getValue() - amount);
                break;
            case VENTURE_CAPITAL:
                this.ventureCapitalInvestmentAmount.setValue(this.ventureCapitalInvestmentAmount.getValue() - amount);
                break;
        }

        this.increaseCash(gameDate, amount);
        this.decreaseAssets(gameDate, amount);
        return true;
    }

    public void updateQuarterlyData(LocalDate gameDate){
        String[] colNames = new String[4];
        for(Map.Entry<String,TreeMap<LocalDate,Double>> history : historiesForQuarterlyData.entrySet()){
            TreeMap<LocalDate,Double> map = history.getValue();
            double[] cols = {0.0, 0.0, 0.0, 0.0};
            String[] colValues = new String[4];

            //current quarter
            int gameQuarter = ((gameDate.getMonthValue() - 1) / 3) + 1;
            LocalDate quarterStart = LocalDate.of(gameDate.getYear(), ((gameQuarter - 1) * 3) + 1, 1);
            LocalDate quarterEnd = quarterStart.withMonth(quarterStart.getMonthValue() + 2);
            quarterEnd = quarterEnd.withDayOfMonth(quarterEnd.lengthOfMonth());
            SortedMap<LocalDate,Double> submap = map.subMap(quarterStart, quarterEnd);
            for (Map.Entry<LocalDate,Double> entry : submap.entrySet()) {
                cols[3] += entry.getValue();
            }
            colNames[3] = new String("Q" + gameQuarter + "/" + quarterStart.getYear());

            //previous quarters
            for(int i = (cols.length - 2); i >= 0; i--){
                quarterStart = quarterStart.minusMonths(3);
                quarterEnd = quarterStart.withMonth(quarterStart.getMonthValue() + 2);
                quarterEnd = quarterEnd.withDayOfMonth(quarterEnd.lengthOfMonth());
                gameQuarter = ((quarterStart.getMonthValue() - 1) / 3) + 1;
                submap = map.subMap(quarterStart, quarterEnd);
                for (Map.Entry<LocalDate,Double> entry : submap.entrySet()) {
                    cols[i] += entry.getValue();
                }
                colNames[i] = "Q" + gameQuarter + "/" + quarterStart.getYear();
            }

            for(int i = 0; i < colNames.length; i++){
                colValues[i] = String.valueOf(DecimalRound.round(cols[i], 2));
            }

            this.quarterlyData.put(history.getKey().replace("History", "Quarterly"), colValues);
        }
        this.quarterlyData.put("colNames", colNames);
        //TODO boolean toggle is only used to fire propertyChange event
        this.updatedQuarterlyData.setValue(!this.updatedQuarterlyData.getValue());
    }

    public void updateMonthlyData(LocalDate gameDate){
        String[] xNames = new String[12];
        for(Map.Entry<String,TreeMap<LocalDate,Double>> history : histories.entrySet()){
            TreeMap<LocalDate,Double> map = history.getValue();
            double[] values = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
            String[] yValues = new String[12];

            //current value
            LocalDate monthEnd = LocalDate.of(gameDate.getYear(), gameDate.getMonthValue(), gameDate.lengthOfMonth());;

            Double value = map.get(gameDate);
            if(value == null){
                values[11] = 0.0;
            }else{
                values[11] = value;
            }
            xNames[11] = gameDate.getYear() + "-" + String.format("%02d", gameDate.getMonthValue());
            //xNames[11] = getLocalisedString("finance.month.short." + gameDate.getMonthValue());

            //last day of previous months
            for(int i = (values.length - 2); i >= 0; i--){
                monthEnd = monthEnd.minusMonths(1);
                monthEnd = monthEnd.withDayOfMonth(monthEnd.lengthOfMonth());

                value = map.get(monthEnd);
                if(value == null){
                    values[i] = 0.0;
                }else{
                    values[i] = map.get(monthEnd);
                }
                xNames[i] = monthEnd.getYear() + "-" + String.format("%02d", monthEnd.getMonthValue());
                //xNames[i] = getLocalisedString("finance.month.short." + monthEnd.getMonthValue());
            }

            for(int i = 0; i < xNames.length; i++){
                yValues[i] = String.valueOf(DecimalRound.round(values[i], 2));
            }

            this.monthlyData.put(history.getKey().replace("History", "Monthly"), yValues);
        }
        this.monthlyData.put("xNames", xNames);
        //TODO boolean toggle is only used to fire propertyChange event
        this.updatedMonthlyData.setValue(!this.updatedMonthlyData.getValue());
    }

    public void updateNetWorthDifference(LocalDate gameDate){
        Double oldNetWorth = this.netWorthHistory.get(gameDate.minusDays(30));
        Double newNetWorth = this.netWorthHistory.get(gameDate);
        if((oldNetWorth != null) && (newNetWorth != null)){
            this.netWorthDifference = newNetWorth - oldNetWorth;
        }else{
            this.netWorthDifference = null;
        }
    }

    public void updateCashDifference(LocalDate gameDate){
        Double oldCash = this.cashHistory.get(gameDate.minusDays(30));
        Double newCash = this.cashHistory.get(gameDate);
        if((oldCash != null) && (newCash != null)){
            this.cashDifference = this.cashHistory.get(gameDate) - oldCash;
        }else{
            this.cashDifference = null;
        }
    }

    public TreeMap<String, String[]> getMonthlyData() {
        return this.monthlyData;
    }

    public TreeMap<String, String[]> getQuarterlyData() {
        return this.quarterlyData;
    }

    public TreeMap<LocalDate, Double> getCashHistory() {
        return this.cashHistory;
    }

    public TreeMap<LocalDate, Double> getNetWorthHistory() {
        return this.netWorthHistory;
    }

    public Double getNetWorthDifference(){
        return this.netWorthDifference;
    }

    public Double getCashDifference(){
        return this.cashDifference;
    }

    @Override
    public void registerPropertyChangeListener(PropertyChangeListener listener) {
        this.gameOver.addPropertyChangeListener(listener);
        this.netWorth.addPropertyChangeListener(listener);
        this.cash.addPropertyChangeListener(listener);
        this.assets.addPropertyChangeListener(listener);
        this.liabilities.addPropertyChangeListener(listener);
        this.realEstateInvestmentAmount.addPropertyChangeListener(listener);
        this.stocksInvestmentAmount.addPropertyChangeListener(listener);
        this.ventureCapitalInvestmentAmount.addPropertyChangeListener(listener);
        this.updatedMonthlyData.addPropertyChangeListener(listener);
        this.updatedQuarterlyData.addPropertyChangeListener(listener);
    }

    public String getLocalisedString(String text) {
        //TODO other languages
        ResourceBundle langBundle = ResourceBundle.getBundle("finance-module", Locale.ENGLISH);
        return langBundle.getString(text);
    }
}
