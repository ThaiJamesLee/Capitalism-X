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
import de.uni.mannheim.capitalismx.utils.data.PropertyChangeSupportMap;
import de.uni.mannheim.capitalismx.utils.number.DecimalRound;
import de.uni.mannheim.capitalismx.warehouse.Warehouse;
import de.uni.mannheim.capitalismx.warehouse.WarehouseType;
import de.uni.mannheim.capitalismx.warehouse.WarehousingDepartment;

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
    private PropertyChangeSupportBoolean gameOver;

    private List<Warehouse> warehousesSold;
    private List<Truck> trucksSold;
    private List<Machinery> machinesSold;
    private List<Double> nopatLast5Years;

    private TreeMap<LocalDate, Double> salesHistory;
    private TreeMap<LocalDate, Double> salariesHistory;
    private TreeMap<LocalDate, Double> logisticsHistory;
    private TreeMap<LocalDate, Double> ebitHistory;
    private TreeMap<LocalDate, Double> nopatHistory;
    private TreeMap<LocalDate, Double> cashHistory;
    private TreeMap<LocalDate, Double> netWorthHistory;
    private Map<String, TreeMap<LocalDate, Double>> histories;
    private TreeMap<String, String[]> quarterlyData;
    //TODO just to notify gui to update finance table with new quarterlyData every day
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

        this.salesHistory = new TreeMap<>();
        this.salariesHistory = new TreeMap<>();
        this.logisticsHistory = new TreeMap<>();
        this.ebitHistory = new TreeMap<>();
        this.nopatHistory = new TreeMap<>();
        this.cashHistory = new TreeMap<>();
        this.netWorthHistory = new TreeMap<>();
        this.histories = new TreeMap<>();
        this.histories.put("salesHistory", this.salesHistory);
        this.histories.put("salariesHistory", this.salariesHistory);
        this.histories.put("logisticsHistory", this.logisticsHistory);
        this.histories.put("ebitHistory", this.ebitHistory);
        this.histories.put("nopatHistory", this.nopatHistory);

        this.quarterlyData = new TreeMap<>();

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
        this.nopat = ((this.calculateEbit(gameDate) - Math.max(this.calculateIncomeTax(gameDate), 0.0)) * (1 - this.decreaseNopatFactor)) - this.decreaseNopatConstant;
        this.nopatHistory.put(gameDate, nopat);
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
        this.totalExpenses = this.calculateTotalHRCosts(gameDate) + this.calculateTotalWarehouseCosts(gameDate) + this.calculateTotalLogisticsCosts(gameDate) + this.calculateTotalProductionCosts()
                + this.calculateTotalMarketingCosts() + this.calculateTotalSupportCosts();
        return this.totalExpenses;
    }

    protected double calculateTotalHRCosts(LocalDate gameDate){
        double totalTrainingCosts = HRDepartment.getInstance().calculateTotalTrainingCosts();
        double totalSalaries = HRDepartment.getInstance().calculateTotalSalaries();
        totalSalaries /= gameDate.lengthOfYear();
        this.salariesHistory.put(gameDate, totalSalaries);
        this.totalHRCosts = totalSalaries + totalTrainingCosts;
        return this.totalHRCosts;
    }

    //TODO
    protected double calculateTotalWarehouseCosts(LocalDate gameDate){
        double warehouseCosts = WarehousingDepartment.getInstance().calculateMonthlyCostWarehousing(gameDate);
        double storageCosts = WarehousingDepartment.getInstance().calculateDailyStorageCost();

        this.totalWarehouseCosts = warehouseCosts + storageCosts;
        return this.totalWarehouseCosts;
    }

    //TODO
    protected double calculateTotalLogisticsCosts(LocalDate gameDate){
        this.totalLogisticsCosts = LogisticsDepartment.getInstance().getTotalLogisticsCosts(gameDate);
        this.logisticsHistory.put(gameDate, totalLogisticsCosts);
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
        this.ebitHistory.put(gameDate, ebit);
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

    public void decreaseAssets(double amount){
        this.assets.setValue(this.assets.getValue() - amount);
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

    public boolean increaseInvestmentAmount(double amount, Investment.InvestmentType investmentType){
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
        this.cash.setValue(this.cash.getValue() - amount);
        this.assets.setValue(this.assets.getValue() + amount);
        return true;
    }

    public boolean decreaseInvestmentAmount(double amount, Investment.InvestmentType investmentType){
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

        this.cash.setValue(this.cash.getValue() + amount);
        this.assets.setValue(this.assets.getValue() - amount);
        return true;
    }

    public void updateQuarterlyData(LocalDate gameDate){
        String[] colNames = new String[4];
        for(Map.Entry<String,TreeMap<LocalDate,Double>> history : histories.entrySet()){
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
                colNames[i] = new String("Q" + gameQuarter + "/" + quarterStart.getYear());
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

    public Double updateNetWorthDifference(LocalDate gameDate){
        Double oldNetWorth = this.netWorthHistory.get(gameDate.minusDays(30));
        Double newNetWorth = this.netWorthHistory.get(gameDate);
        if((oldNetWorth != null) && (newNetWorth != null)){
            return oldNetWorth - newNetWorth;
        }else{
            return null;
        }
    }

    public Double updateCashDifference(LocalDate gameDate){
        Double oldCash = this.cashHistory.get(gameDate.minusDays(30));
        Double newCash = this.cashHistory.get(gameDate);
        if((oldCash != null) && (newCash != null)){
            return oldCash - this.cashHistory.get(gameDate);
        }else{
            return null;
        }
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
        this.updatedQuarterlyData.addPropertyChangeListener(listener);
    }
}
