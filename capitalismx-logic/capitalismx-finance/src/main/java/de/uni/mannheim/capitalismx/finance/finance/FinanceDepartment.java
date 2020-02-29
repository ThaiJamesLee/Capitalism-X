package de.uni.mannheim.capitalismx.finance.finance;

import de.uni.mannheim.capitalismx.domain.department.DepartmentImpl;
import de.uni.mannheim.capitalismx.hr.department.HRDepartment;
import de.uni.mannheim.capitalismx.logistic.logistics.InternalFleet;
import de.uni.mannheim.capitalismx.logistic.logistics.LogisticsDepartment;
import de.uni.mannheim.capitalismx.logistic.logistics.Truck;
import de.uni.mannheim.capitalismx.logistic.logistics.exception.NotEnoughTruckCapacityException;
import de.uni.mannheim.capitalismx.logistic.support.ProductSupport;
import de.uni.mannheim.capitalismx.marketing.department.MarketingDepartment;
import de.uni.mannheim.capitalismx.production.Machinery;
import de.uni.mannheim.capitalismx.production.ProductionDepartment;
import de.uni.mannheim.capitalismx.sales.contracts.Contract;
import de.uni.mannheim.capitalismx.sales.department.SalesDepartment;
import de.uni.mannheim.capitalismx.utils.data.PropertyChangeSupportBoolean;
import de.uni.mannheim.capitalismx.utils.data.PropertyChangeSupportDouble;
import de.uni.mannheim.capitalismx.utils.number.DecimalRound;
import de.uni.mannheim.capitalismx.warehouse.Warehouse;
import de.uni.mannheim.capitalismx.warehouse.WarehouseType;
import de.uni.mannheim.capitalismx.warehouse.WarehousingDepartment;

import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.util.*;

/**
 * This class represents the finance department.
 * It implements the calculation of all relevant revenues and costs of the company.
 * Based on the report p.69-80
 *
 * @author sdupper
 */
public class FinanceDepartment extends DepartmentImpl {

    /**
     * The singleton pointer.
     */
    private static FinanceDepartment instance;

    /**
     * The current net worth of the company
     */
    private PropertyChangeSupportDouble netWorth;

    /**
     * The current cash of the company
     */
    private PropertyChangeSupportDouble cash;

    /**
     * The current assets of the company
     */
    private PropertyChangeSupportDouble assets;

    /**
     * The current liabilities of the company
     */
    private PropertyChangeSupportDouble liabilities;

    /**
     * The current amount invested into real estate.
     */
    private PropertyChangeSupportDouble realEstateInvestmentAmount;

    /**
     * The current amount invested into stocks.
     */
    private PropertyChangeSupportDouble stocksInvestmentAmount;

    /**
     * The current amount invested into venture capital.
     */
    private PropertyChangeSupportDouble ventureCapitalInvestmentAmount;

    /**
     * The factor by which the nopat is decreased, e.g., in case of penalties.
     */
    private double decreaseNopatFactor;

    /**
     * The constant by which the nopat is decreased, e.g., in case of penalties.
     */
    private double decreaseNopatConstant;

    /**
     * The difference between the current net worth compared to 30 days before.
     */
    private PropertyChangeSupportDouble netWorthDifference;

    /**
     * The difference between the current cash compared to 30 days before.
     */
    private PropertyChangeSupportDouble cashDifference;

    /**
     * A boolean indicating whether the game is over.
     */
    private PropertyChangeSupportBoolean gameOver;

    /**
     * A boolean indicating that the current loan was removed.
     */
    private PropertyChangeSupportBoolean loanRemoved;

    /**
     * A list of all warehouses that were sold on the current day.
     */
    private List<Warehouse> warehousesSold;

    /**
     * A list of all trucks that were sold on the current day.
     */
    private List<Truck> trucksSold;

    /**
     * A list of all machines that were sold on the current day.
     */
    private List<Machinery> machinesSold;

    /**
     * A list of the nopat over the last 5 years.
     */
    private List<Double> nopatLast5Years;

    /**
     * A list of all sales amounts with the corresponding date.
     */
    private TreeMap<LocalDate, Double> salesHistory;

    /**
     * A list of all hrCosts with the corresponding date.
     */
    private TreeMap<LocalDate, Double> hrCostsHistory;

    /**
     * A list of all warehouseCosts with the corresponding date.
     */
    private TreeMap<LocalDate, Double> warehouseCostsHistory;

    /**
     * A list of all logisticsCosts with the corresponding date.
     */
    private TreeMap<LocalDate, Double> logisticsCostsHistory;

    /**
     * A list of all productionCosts with the corresponding date.
     */
    private TreeMap<LocalDate, Double> productionCostsHistory;

    /**
     * A list of all marketingCosts with the corresponding date.
     */
    private TreeMap<LocalDate, Double> marketingCostsHistory;

    /**
     * A list of all supportCosts with the corresponding date.
     */
    private TreeMap<LocalDate, Double> supportCostsHistory;

    /**
     * A list of all ebit amounts with the corresponding date.
     */
    private TreeMap<LocalDate, Double> ebitHistory;

    /**
     * A list of all tax amounts with the corresponding date.
     */
    private TreeMap<LocalDate, Double> taxHistory;

    /**
     * A list of all nopat amounts with the corresponding date.
     */
    private TreeMap<LocalDate, Double> nopatHistory;

    /**
     * A list of all cash amounts with the corresponding date.
     */
    private TreeMap<LocalDate, Double> cashHistory;

    /**
     * A list of all asset amounts with the corresponding date.
     */
    private TreeMap<LocalDate, Double> assetsHistory;

    /**
     * A list of all liability amounts with the corresponding date.
     */
    private TreeMap<LocalDate, Double> liabilitiesHistory;

    /**
     * A list of all net worth amounts with the corresponding date.
     */
    private TreeMap<LocalDate, Double> netWorthHistory;

    /**
     * A map containing the cash, assets, liabilities, and net worth histories.
     */
    private Map<String, TreeMap<LocalDate, Double>> histories;

    /**
     * A map containing the sales, hrCosts, warehouseCosts, logisticsCosts, productionCosts, marketingCosts,
     * supportCosts, ebit, tax, and nopat histories.
     */
    private Map<String, TreeMap<LocalDate, Double>> historiesForQuarterlyData;

    /**
     * A map containing the monthly data for the finance charts.
     */
    private TreeMap<String, String[]> monthlyData;

    /**
     * A map containing the quarterly data for the operations table in the finance department GUI.
     */
    private TreeMap<String, String[]> quarterlyData;

    //TODO just to notify gui to update finance table with new monthlyData / quarterlyData every day
    /**
     * Notifies the GUI about updates to the monthly data.
     */
    private PropertyChangeSupportBoolean updatedMonthlyData;

    /**
     * Notifies the GUI about updates to the quarterly data.
     */
    private PropertyChangeSupportBoolean updatedQuarterlyData;

    /**
     * Specifies the cash amount at the start of a new game.
     */
    private double initialCash;

    /**
     * Specifies the tax rate at the start of a new game.
     */
    private double taxRate;

    /**
     * A list that contains all investments of the company.
     */
    private List<Investment> investments;

    private double totalTruckValues;
    private double totalMachineValues;
    private double totalWarehousingValues;
    private double totalInvestmentAmount;
    private double nopat;
    private double assetsSold;
    private double ebit;
    private double incomeTax;
    private double totalRevenue;
    private double totalHRCosts;
    private double totalWarehouseCosts;
    private double totalLogisticsCosts;
    private double totalProductionCosts;
    private double totalMarketingCosts;
    private double totalSupportCosts;
    private double totalExpenses;

    private static final String DEFAULTS_PROPERTIES_FILE = "finance-defaults";
    private static final String LANGUAGE_PROPERTIES_FILE = "finance-module";

    /**
     * Constructor
     * Initializes all variables, including the PropertyChangeSupport variables and the different histories.
     */
    protected FinanceDepartment(){
        super("Finance");

        this.initProperties();

        this.gameOver = new PropertyChangeSupportBoolean();
        this.gameOver.setValue(false);
        this.gameOver.setPropertyChangedName("gameOver");

        this.loanRemoved = new PropertyChangeSupportBoolean();
        this.loanRemoved.setValue(false);
        this.loanRemoved.setPropertyChangedName("loanRemoved");

        this.cash = new PropertyChangeSupportDouble();
        this.cash.setValue(this.initialCash);
        this.cash.setPropertyChangedName("cash");

        this.netWorth = new PropertyChangeSupportDouble();
        this.netWorth.setValue(this.initialCash);
        this.netWorth.setPropertyChangedName("netWorth");

        this.assets = new PropertyChangeSupportDouble();
        this.assets.setValue(0.0);
        this.assets.setPropertyChangedName("assets");

        this.liabilities = new PropertyChangeSupportDouble();
        this.liabilities.setValue(0.0);
        this.liabilities.setPropertyChangedName("liabilities");

        this.netWorthDifference = new PropertyChangeSupportDouble();
        this.netWorthDifference.setValue(0.0);
        this.netWorthDifference.setPropertyChangedName("netWorthDifference");

        this.cashDifference = new PropertyChangeSupportDouble();
        this.cashDifference.setValue(0.0);
        this.cashDifference.setPropertyChangedName("cashDifference");

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

        //set values before game start to initialCash
        for(int i = 2; i <= 12; i++){
            this.cashHistory.put(LocalDate.of(1989, i, LocalDate.of(1989, i, 1).lengthOfMonth()), this.initialCash);
        }
        for(int i = 2; i <= 12; i++){
            this.netWorthHistory.put(LocalDate.of(1989, i, LocalDate.of(1989, i, 1).lengthOfMonth()), this.initialCash);
        }

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

        this.investments = new ArrayList<>();
        this.investments.add(new Investment(0, Investment.InvestmentType.REAL_ESTATE));
        this.investments.add(new Investment(0, Investment.InvestmentType.STOCKS));
        this.investments.add(new Investment(0, Investment.InvestmentType.VENTURE_CAPITAL));
        this.warehousesSold = new ArrayList<>();
        this.trucksSold = new ArrayList<>();
        this.machinesSold = new ArrayList<>();
        this.nopatLast5Years = new ArrayList<>();
    }

    /**
     * Initializes the finance department values using the corresponding properties file.
     */
    private void initProperties(){
        ResourceBundle resourceBundle = ResourceBundle.getBundle(DEFAULTS_PROPERTIES_FILE);
        this.initialCash = Double.valueOf(resourceBundle.getString("finance.initial.cash"));
        this.taxRate = Double.valueOf(resourceBundle.getString("finance.initial.tax.rate"));
        this.decreaseNopatFactor = this.taxRate = Double.valueOf(resourceBundle.getString("finance.initial.decrease.nopat.factor"));
        this.decreaseNopatConstant = this.taxRate = Double.valueOf(resourceBundle.getString("finance.initial.decrease.nopat.constant"));
    }

    /**
     *
     * @return Returns the singleton instance
     */
    public static synchronized FinanceDepartment getInstance() {
        if(FinanceDepartment.instance == null) {
            FinanceDepartment.instance = new FinanceDepartment();
        }
        return FinanceDepartment.instance;
    }

    /**
     * Calculates the net worth based on the cash, assets, and liabilities according to p.70 and adds it to the
     * respective history.
     * @param gameDate The current date in the game.
     * @return Returns the net worth.
     */
    public double calculateNetWorth(LocalDate gameDate){
        //TODO maybe getCash() instead of calculateCash(), because calculateCash() only once per day?
        this.netWorth.setValue(this.calculateCash(gameDate) + this.calculateAssets(gameDate) - this.calculateLiabilities(gameDate));
        this.netWorthHistory.put(gameDate, this.netWorth.getValue());
        return this.netWorth.getValue();
    }

    /**
     * Calculates the company's assets based on the totalTruckValues, totalMachineValues, totalWarehousingValues, and
     * totalInvestmentAmount according to p.71 and adds it to the respective history.
     * @param gameDate The current date in the game.
     * @return Returns the asset amount.
     */
    protected double calculateAssets(LocalDate gameDate){
        this.assets.setValue(this.calculateTotalTruckValues(gameDate) + this.calculateTotalMachineValues(gameDate) +
                this.calculateTotalWarehousingValues(gameDate) + this.calculateTotalInvestmentAmount());
        this.assetsHistory.put(gameDate, this.assets.getValue());
        return this.assets.getValue();
    }

    //TODO reset assetsSold and nopat of current day?
    //TODO consider liabilities for cash calculation?

    /**
     * Calculates the company's cash based on the previous cash amount, the current nopat, and the assets sold similarly
     * to p.71 (here, monthly loan rate is deducted from the cash amount) and adds it to the respective history.
     * Moreover, ends the game if cash < 0.
     * @param gameDate The current date in the game.
     * @return Returns the cash amount.
     */
    protected double calculateCash(LocalDate gameDate){
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

    /**
     * Calculates the annual depreciation of assets according to p.71.
     * @param purchasePrice The purchase price of the asset.
     * @param usefulLife The useful life of the asset.
     * @return Returns the annual depreciation of the asset.
     */
    private double calculateAnnualDepreciation(double purchasePrice, double usefulLife){
        return purchasePrice / usefulLife;
    }

    /**
     * Calculates the resell price of assets according to p.72.
     * @param purchasePrice The purchase price of the asset.
     * @param usefulLife The useful life of the asset.
     * @param timeUsed The time that the asset was used for.
     * @return Returns the resell price of the asset.
     */
    public double calculateResellPrice(double purchasePrice, double usefulLife, double timeUsed){
        return purchasePrice - this.calculateAnnualDepreciation(purchasePrice, usefulLife) * timeUsed;
    }

    /**
     * Calculates the total warehousing values of the company by adding up the resell prices of built warehouses
     * according to p.72.
     * @param gameDate The current date in the game.
     * @return Returns the total warehousing values.
     */
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

    /**
     * Calculates the total truck values of the company by adding up the resell prices of all trucks in the internal
     * fleet according to p.72.
     * @param gameDate The current date in the game.
     * @return Returns the total truck values.
     */
    protected double calculateTotalTruckValues(LocalDate gameDate){
        this.totalTruckValues = 0;
        ArrayList<Truck> trucks = InternalFleet.getInstance().getTrucks();
        double truckValue;
        for(Truck truck : trucks){
            truckValue = this.calculateResellPrice(truck.getPurchasePrice(),
                    truck.getUsefulLife(), truck.calculateTimeUsed(gameDate));
            truck.setResellPrice(truckValue);
            this.totalTruckValues += truckValue;
        }
        return this.totalTruckValues;
    }

    /**
     * Calculates the total machine values of the company by adding up the resell prices of all machines
     * according to p.72.
     * @param gameDate The current date in the game.
     * @return Returns the total machine values.
     */
    protected double calculateTotalMachineValues(LocalDate gameDate){
        this.totalMachineValues = 0;
        List<Machinery> machines = ProductionDepartment.getInstance().getMachines();
        for(Machinery machine : machines){
            //this.totalMachineValues += this.calculateResellPrice(machine.getPurchasePrice(),
            //        machine.getUsefulLife(), machine.calculateTimeUsed(gameDate));
            this.totalMachineValues += machine.calculateResellPrice();
        }
        return this.totalMachineValues;
    }

    //TODO timestamp or thread in class that checks if new day
    /**
     * Adds a warehouse to the list of sold warehouses.
     * @param warehouse The warehouse to be sold.
     */
    public void sellWarehouse(Warehouse warehouse){
        this.warehousesSold.add(warehouse);
    }

    /**
     * Processes the purchase of a truck. The new truck is added to the internal fleet, the cash and net worth amount
     * are decreased, and the asset amount is increased according to the purchase and resell price.
     * @param truck The truck to be bought.
     * @param gameDate The current date in the game
     * @throws NotEnoughTruckCapacityException if the internal fleet does not have enough capacity.
     */
    public void buyTruck(Truck truck, LocalDate gameDate) throws NotEnoughTruckCapacityException {
        LogisticsDepartment.getInstance().addTruckToFleet(truck, gameDate);
        this.decreaseCash(gameDate, truck.getPurchasePrice());
        double resellPrice = this.calculateResellPrice(truck.getPurchasePrice(),
                truck.getUsefulLife(), truck.calculateTimeUsed(gameDate));
        this.increaseAssets(gameDate, resellPrice);
        this.decreaseNetWorth(gameDate, truck.getPurchasePrice() - resellPrice);
    }

    /**
     * Processes the sale of a truck. The truck is removed from the internal fleet, the cash amount is increased, and
     * the asset amount is decreased according to the resell price.
     * @param truck The truck to be sold.
     * @param gameDate The current date in the game.
     */
    public void sellTruck(Truck truck, LocalDate gameDate){
        //TODO timestamp or thread in class that checks if new day
        //this.trucksSold.add(truck);
        double resellPrice = this.calculateResellPrice(truck.getPurchasePrice(),
                truck.getUsefulLife(), truck.calculateTimeUsed(gameDate));
        this.increaseCash(gameDate, resellPrice);
        this.decreaseAssets(gameDate, resellPrice);
        LogisticsDepartment.getInstance().removeTruckFromFleet(truck);
    }

    /**
     * Processes the purchase of a machine. The cash and net worth amount are decreased and the asset amount is
     * increased according to the purchase and resell price.
     * @param machinery The machine to be bought.
     * @param gameDate The current date in the game.
     */
    public void buyMachinery(Machinery machinery, LocalDate gameDate){
        this.decreaseCash(gameDate, machinery.getPurchasePrice());
        this.increaseAssets(gameDate, machinery.calculateResellPrice());
        this.decreaseNetWorth(gameDate, machinery.getPurchasePrice() - machinery.getResellPrice());
    }

    /**
     * Processes the sale of a machine. The cash amount is increased and the asset amount is decreased according to the
     * resell price.
     * @param machinery The machine to be sold.
     * @param gameDate The current date in the game.
     */
    public void sellMachinery(Machinery machinery, LocalDate gameDate){
        //TODO timestamp or thread in class that checks if new day
        //this.machinesSold.add(machinery);
        this.increaseCash(gameDate, machinery.calculateResellPrice());
        this.decreaseAssets(gameDate, machinery.calculateResellPrice());
    }

    /**
     * Calculates the value of all assets sold on the current day, e.g., warehouses.
     * @param gameDate The current date in the game.
     * @return Returns the total value of assets sold on the current day.
     */
    protected double calculateAssetsSold(LocalDate gameDate){
        this.assetsSold = 0;
        for(Warehouse warehouse : this.warehousesSold){
            this.assetsSold += this.calculateResellPrice(warehouse.getBuildingCost(),
                    warehouse.getUsefulLife(), warehouse.calculateTimeUsed(gameDate));
        }
        //TODO already decreased from cash in sellTruck()
        /*for(Truck truck : this.trucksSold){
            this.assetsSold += this.calculateResellPrice(truck.getPurchasePrice(),
                    truck.getUsefulLife(), truck.calculateTimeUsed(gameDate));
        }*/
        //TODO already decreased from cash in sellTruck()
        /*for(Machinery machine : this.machinesSold){
            this.assetsSold += this.calculateResellPrice(machine.getPurchasePrice(),
                    machine.getUsefulLife(), machine.calculateTimeUsed(gameDate));
        }*/
        return this.assetsSold;
    }

    /**
     * Calculates the NOPAT of the company based on the EBIT and income tax according to p.73. Note that unlike the
     * report, this method does not consider interest loan for calculating the NOPAT, because the NOPAT does not depend
     * on interest loan. Moreover, NOPAT decreases (factor and constant, e.g., due to penalties) are considered in the
     * NOPAT calculation. Finally, the NOPAT amount is added to the respective history.
     * @param gameDate The current date in the game.
     * @return Returns the NOPAT of the company.
     */
    public double calculateNopat(LocalDate gameDate){
        //this.nopat = this.ebit - this.incomeTax;
        //TODO display decreaseNopatFactor/decreaseNopatConstant in operations table (finance ui)
        this.nopat = ((this.calculateEbit(gameDate) - Math.max(this.calculateIncomeTax(gameDate), 0.0))
                * (1 - this.decreaseNopatFactor)) - this.decreaseNopatConstant;
        this.nopatHistory.put(gameDate, this.nopat);
        return this.nopat;
    }

    /**
     * Calculates the income tax based on the EBIT and tax rate according to p.73 and adds it to the respective history.
     * If the EBIT is <= 0, the income tax is 0.
     * @param gameDate The current date in the game.
     * @return Returns the income tax of the company.
     */
    protected double calculateIncomeTax(LocalDate gameDate){
        this.incomeTax = 0;
        if(this.calculateEbit(gameDate) >= 0){
            this.incomeTax = this.ebit * this.taxRate;
        }
        this.taxHistory.put(gameDate, this.incomeTax);
        return this.incomeTax;
    }

    /**
     * Calculates the daily revenue.
     * <br>
     * In case of contracts, when one is done, the whole contract revenue is counted for the day it was fulfilled.
     * Thus, the total revenue, is the earnings of all fulfilled contracts in that day.
     *
     * @param gameDate The current date in the game.
     * @return Returns the total revenue for the day.
     *
     * @author duly
     */
    private double calculateTotalRevenue(LocalDate gameDate){
        SalesDepartment salesDepartment = SalesDepartment.getInstance();
        List<Contract> doneContracts  = salesDepartment.getDoneContracts().getList();

        double dailyRevenue = 0.0;

        for(Contract c : doneContracts) {
            // -1 day, because the gameDate is already updated, when checking
            if(c.getContractDoneDate().equals(gameDate.minusDays(1))) {
                dailyRevenue += c.getRevenue();
            }
        }
        this.totalRevenue = dailyRevenue;
        this.salesHistory.put(gameDate, this.totalRevenue);
        return this.totalRevenue;
    }

    /**
     * Calculates the total expenses of the company by adding up the total HR, warehousing, logistics, production,
     * marketing, and support costs according to p.76.
     * @param gameDate The current date in the game.
     * @return Returns the total expenses of the company
     */
    private double calculateTotalExpenses(LocalDate gameDate){
        this.totalExpenses = this.calculateTotalHRCosts(gameDate) + this.calculateTotalWarehouseCosts(gameDate)
                + this.calculateTotalLogisticsCosts(gameDate) + this.calculateTotalProductionCosts(gameDate)
                + this.calculateTotalMarketingCosts(gameDate) + this.calculateTotalSupportCosts(gameDate);
        return this.totalExpenses;
    }

    /**
     * Calculates the total HR costs of the company based on the training costs and salaries according to p.74. The
     * salaries are divided by the length of the year to calculate the daily salary costs. The total HR costs are then
     * added to the respective history.
     * @param gameDate The current date in the game.
     * @return Returns the total HR costs of the company.
     */
    protected double calculateTotalHRCosts(LocalDate gameDate){
        HRDepartment hrDepartment = HRDepartment.getInstance();
        double totalTrainingCosts = hrDepartment.calculateTotalTrainingCosts(gameDate);
        // the salaries are per year.
        double totalSalaries = hrDepartment.calculateTotalSalaries();
        totalSalaries /= gameDate.lengthOfYear();
        // the benefit costs are per month.
        double totalBenefitsCost = hrDepartment.getBenefitSettingsCost();
        totalBenefitsCost /= gameDate.lengthOfMonth();
        this.totalHRCosts = totalSalaries + totalTrainingCosts + totalBenefitsCost;
        this.hrCostsHistory.put(gameDate, this.totalHRCosts);
        return this.totalHRCosts;
    }

    /**
     * Calculates the total warehouse costs of the company based on the monthly warehousing costs and the daily storage
     * costs according to p.75. The monthly warehousing costs are divided by the length of the month to calculate the
     * daily warehousing costs. The total warehouse costs are then added to the respective history.
     * @param gameDate The current date in the game.
     * @return Returns the total warehouse costs of the company.
     */
    protected double calculateTotalWarehouseCosts(LocalDate gameDate){
        double warehouseCosts = WarehousingDepartment.getInstance().calculateMonthlyCostWarehousing();
        warehouseCosts /= gameDate.lengthOfMonth();
        double storageCosts = WarehousingDepartment.getInstance().calculateDailyStorageCost();

        this.totalWarehouseCosts = warehouseCosts + storageCosts;
        this.warehouseCostsHistory.put(gameDate, this.totalWarehouseCosts);
        return this.totalWarehouseCosts;
    }

    /**
     * Calculates the total logistics costs of the company according to p.75. The actual calculation is implemented in
     * the logistics department. The total logistics costs are then added to the respective history.
     * @param gameDate The current date in the game.
     * @return Returns the total logistics costs of the company.
     */
    protected double calculateTotalLogisticsCosts(LocalDate gameDate){
        this.totalLogisticsCosts = LogisticsDepartment.getInstance().getTotalLogisticsCosts(gameDate);
        this.logisticsCostsHistory.put(gameDate, this.totalLogisticsCosts);
        return this.totalLogisticsCosts;
    }

    /**
     * Calculates the total production costs of the company according to p.75. The actual calculation is implemented in
     * the production department. The total production costs are then added to the respective history.
     * @param gameDate The current date in the game.
     * @return Returns the total production costs of the company.
     */
    protected double calculateTotalProductionCosts(LocalDate gameDate){
        this.totalProductionCosts = ProductionDepartment.getInstance().getTotalProductionCosts();
        this.productionCostsHistory.put(gameDate, this.totalProductionCosts);
        return this.totalProductionCosts;
    }

    /**
     * Calculates the total marketing costs of the company according to p.76. The actual calculation is implemented in
     * the marketing department. The total marketing costs are then added to the respective history.
     * @param gameDate The current date in the game.
     * @return Returns the total marketing costs of the company.
     */
    private double calculateTotalMarketingCosts(LocalDate gameDate){
        this.totalMarketingCosts = MarketingDepartment.getInstance().getTotalMarketingCosts();
        this.marketingCostsHistory.put(gameDate, this.totalMarketingCosts);
        //TODO
        this.marketingCostsHistory.put(gameDate, 0.0);
        return 0.0;
    }

    /**
     * Calculates the total support costs of the company according to p.76. The actual calculation is implemented in
     * the product support class. The total support costs are divided by the length of the month to calculate the daily
     * total support costs. The daily total support costs are then added to the respective history.
     * @param gameDate The current date in the game.
     * @return Returns the total support costs of the company.
     */
    private double calculateTotalSupportCosts(LocalDate gameDate){
        double totalSupportCosts = ProductSupport.getInstance().calculateTotalSupportCosts();
        this.totalSupportCosts = totalSupportCosts / gameDate.lengthOfMonth();
        this.supportCostsHistory.put(gameDate, this.totalSupportCosts);
        return this.totalSupportCosts;
    }

    /**
     * Calculates the EBIT of the company based on the total revenues and expenses according to p.76. The EBIT is then
     * added to the respective history.
     * @param gameDate The current date in the game.
     * @return Returns the EBIT of the company.
     */
    protected double calculateEbit(LocalDate gameDate){
        this.ebit = this.calculateTotalRevenue(gameDate) - this.calculateTotalExpenses(gameDate);
        this.ebitHistory.put(gameDate, this.ebit);
        return this.ebit;
    }

    //TODO update cash and netWorth?

    /**
     * Generates a selection of three loans according to p.76. The actual generation is implemented in the banking
     * system. The desired loan amount can not exceed 70 percent of the current net worth.
     * @param desiredLoanAmount The requested loan amount.
     * @param gameDate The current date in the game.
     * @param locale The Locale object of the desired language.
     * @return Returns a list of three loans with different characteristics.
     */
    public ArrayList<Loan> generateLoanSelection(double desiredLoanAmount, LocalDate gameDate, Locale locale){
        if(this.cash.getValue() == 0.0){
            //TODO popup
            return null;
        }
        if((desiredLoanAmount + BankingSystem.getInstance().calculateAnnualPrincipalBalance(gameDate)) > 0.7 * this.netWorth.getValue()){
            desiredLoanAmount = (0.7 * this.netWorth.getValue()) - BankingSystem.getInstance().calculateAnnualPrincipalBalance(gameDate);
            if(desiredLoanAmount <= 0.0){
                return null;
            }
        }
        return BankingSystem.getInstance().generateLoanSelection(desiredLoanAmount, locale);
    }

    /**
     * Adds a loan to the company (implemented in the banking system) and updates the cash and liabilities amount
     * accordingly.
     * @param loan The loan to be added.
     * @param gameDate The current date in the game.
     */
    public void addLoan(Loan loan, LocalDate gameDate){
        BankingSystem.getInstance().addLoan(loan, gameDate);
        this.increaseCash(gameDate, loan.getLoanAmount());
        this.increaseLiabilities(gameDate, loan.getLoanAmount());
        //this.loanRemoved.setValue(false);
    }

    /**
     * Notifies the GUI when a loan is removed.
     */
    protected void removeLoan(){
        this.loanRemoved.setValue(true);
        //return to initial state
        this.loanRemoved.setValue(false);
    }

    /**
     * Calculates the liabilities of the company according to p.70. The actual calculation is implemented in the
     * banking system. The liabilities amount is then added to the respective history.
     * @param gameDate The current date in the game.
     * @return Returns the liabilities amount of the company.
     */
    protected double calculateLiabilities(LocalDate gameDate){
        this.liabilities.setValue(BankingSystem.getInstance().calculateAnnualPrincipalBalance(gameDate));
        this.liabilitiesHistory.put(gameDate, this.liabilities.getValue());
        return this.liabilities.getValue();
    }

    /**
     * Calculates the total investment amount of the company. This corresponds to the value of all real estate, stock,
     * and venture capital investments added up. It further calculates the values of the individual investment
     * categories (real estate, stocks, venture capital). The value of each investment is calculated in the investment
     * class.
     * @return Returns the total investment amount of the company.
     */
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

    /**
     * Checks if NOPAT increased by at least 30 percent per year for the last 5 years (according to Table A.1).
     * @return Returns true if the NOPAT increased by at least 30 percent per year. Returns false otherwise.
     */
    public boolean checkIncreasingNopat(){
        if(this.nopatLast5Years.size() < 5){
            return false;
        }
        for(int i = 0; i < this.nopatLast5Years.size() - 1; i++){
            if(this.nopatLast5Years.get(i + 1) < this.nopatLast5Years.get(i) * 1.30){
                return false;
            }
        }
        return true;
    }

    /**
     * Decreases the cash amount of the company by the specified amount. If the new cash amount is < 0, the game is
     * over. Otherwise, the new cash amount is added to the respective history and the monthly data and cash difference
     * are updated.
     * @param gameDate The current date in the game.
     * @param amount The amount by which the cash should be decreased.
     */
    public void decreaseCash(LocalDate gameDate, double amount){
        double cash = this.cash.getValue() - amount;
        if(cash < 0){
            this.gameOver.setValue(true);
        }else{
            this.cash.setValue(cash);
            this.cashHistory.put(gameDate, this.cash.getValue());
            this.updateMonthlyData(gameDate);
            this.updateCashDifference(gameDate);
        }
    }

    /**
     * Increases the cash amount of the company by the specified amount. The new cash amount is added to the respective
     * history and the monthly data and cash difference are updated.
     * @param gameDate The current date in the game.
     * @param amount The amount by which the cash should be increased.
     */
    public void increaseCash(LocalDate gameDate, double amount){
        this.cash.setValue(this.cash.getValue() + amount);
        this.cashHistory.put(gameDate, this.cash.getValue());
        this.updateMonthlyData(gameDate);
        this.updateCashDifference(gameDate);
    }

    /**
     * Decreases the net worth of the company by the specified amount. The new net worth is added to the respective
     * history and the monthly data and net worth difference are updated.
     * @param gameDate The current date in the game.
     * @param amount The amount by which the net worth should be decreased.
     */
    public void decreaseNetWorth(LocalDate gameDate, double amount){
        this.netWorth.setValue(this.netWorth.getValue() - amount);
        this.netWorthHistory.put(gameDate, this.netWorth.getValue());
        this.updateMonthlyData(gameDate);
        this.updateNetWorthDifference(gameDate);
    }

    /**
     * Increases the net worth of the company by the specified amount. The new net worth is added to the respective
     * history and the monthly data and net worth difference are updated.
     * @param gameDate The current date in the game.
     * @param amount The amount by which the net worth should be increased.
     */
    public void increaseNetWorth(LocalDate gameDate, double amount){
        this.netWorth.setValue(this.netWorth.getValue() + amount);
        this.netWorthHistory.put(gameDate, this.netWorth.getValue());
        this.updateMonthlyData(gameDate);
        this.updateNetWorthDifference(gameDate);
    }

    /**
     * Increases the asset amount of the company by the specified amount. The new asset amount is added to the
     * respective history and the monthly data is updated.
     * @param gameDate The current date in the game.
     * @param amount The amount by which the assets should be increased.
     */
    public void increaseAssets(LocalDate gameDate, double amount){
        this.assets.setValue(this.assets.getValue() + amount);
        this.assetsHistory.put(gameDate, this.assets.getValue());
        this.updateMonthlyData(gameDate);
    }

    /**
     * Decreases the asset amount of the company by the specified amount. The new asset amount is added to the
     * respective history and the monthly data is updated.
     * @param gameDate The current date in the game.
     * @param amount The amount by which the assets should be decreased.
     */
    public void decreaseAssets(LocalDate gameDate, double amount){
        this.assets.setValue(this.assets.getValue() - amount);
        this.assetsHistory.put(gameDate, this.assets.getValue());
        this.updateMonthlyData(gameDate);
    }

    /**
     * Increases the liabilities amount of the company by the specified amount. The new liabilities amount is added to
     * the respective history and the monthly data is updated.
     * @param gameDate The current date in the game.
     * @param amount The amount by which the liabilities should be increased.
     */
    public void increaseLiabilities(LocalDate gameDate, double amount){
        this.liabilities.setValue(this.liabilities.getValue() + amount);
        this.liabilitiesHistory.put(gameDate, this.liabilities.getValue());
        this.updateMonthlyData(gameDate);
    }

    /**
     * Processes the acquisition of another company according to Table A.1.
     * @param gameDate The current date in the game.
     */
    //TODO decide on suitable consequences of acquisition, e.g., increase assets
    public void acquireCompany(LocalDate gameDate){
        this.increaseCash(gameDate, 10000);
        this.decreaseCash(gameDate, this.calculateNopat(gameDate) * 0.70);
    }

    /**
     * Increases the factor by which the NOPAT is decreased (e.g., due to penalties).
     * @param amount The amount by which the factor should be increased.
     */
    public void decreaseNopatRelPermanently(double amount){
        this.decreaseNopatFactor += amount;
    }

    /**
     * Decreases the factor by which the NOPAT is decreased (e.g., due to the end of penalties).
     * @param amount The amount by which the factor should be decreased.
     */
    public void increaseNopatRelPermanently(double amount){
        this.decreaseNopatFactor -= amount;
    }

    /**
     * Increases the tax rate (e.g., due to penalties).
     * @param amount The amount by which the tax rate should be increased.
     */
    public void increaseTaxRate(double amount){
        this.taxRate += amount;
    }

    //TODO only for one year?

    /**
     * Decreases the tax rate (e.g., due to the end of penalties).
     * @param amount The amount by which the tax rate should be decreased.
     */
    public void decreaseTaxRate(double amount){
        this.taxRate -= amount;
    }

    /**
     * Represents a NOPAT fine that decreases the cash of the company relative to its NOPAT.
     * @param gameDate The current date in the game.
     * @param amount The percentage of the NOPAT by which the company should be fined.
     */
    public void nopatFine(LocalDate gameDate, double amount){
        this.decreaseCash(gameDate, this.nopat * amount);
    }

    /**
     * Increases the constant amount by which the NOPAT is decreased (e.g., due to penalties)
     * @param amount The amount by which the constant should be increased.
     */
    public void decreaseNopatConstant(double amount){
        this.decreaseNopatConstant += amount;
    }

    /**
     * Sets the constant amount by which the NOPAT is decreased (e.g., due to penalties)
     * @param amount The new value of the constant.
     */
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

    public ArrayList<Loan> getLoans(){
        return BankingSystem.getInstance().getLoans();
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

    /**
     * Increases the investment amount for a specified investment type if the company has enough cash. Consequently, the
     * cash is decreased and the asset amount is increased.
     * @param gameDate The current date in the game.
     * @param amount The amount to be invested.
     * @param investmentType The type of investment that should be invested in.
     * @return Returns true if the investment was processed successfully. Returns false otherwise.
     */
    public boolean increaseInvestmentAmount(LocalDate gameDate, double amount, Investment.InvestmentType investmentType){
        if(amount > this.cash.getValue()){
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

    /**
     * Updates the quarterly data (of the 4 most recent quarters) for the operations table in the finance ui, e.g., HR
     * costs. For each of the histories represented in the operations table, the daily data in the relevant time frame
     * (4 quarters) is extracted and added up for each quarter. Lastly, the GUI is notified about the changes.
     * @param gameDate The current date in the game.
     */
    public void updateQuarterlyData(LocalDate gameDate){
        String[] colNames = new String[4];
        //Iterate over the different histories represented in the operations table, e.g., the history of HR costs
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

    /**
     * Updates the monthly data (of the 12 most recent months) for the charts in the finance ui, e.g., cash. For each
     * of the histories in the finance charts, the data of the last day of each month in the relevant time frame
     * (12 months) is extracted. For the current month, the most recent value is used. Lastly, the GUI is notified
     * about the changes.
     * @param gameDate The current date in the game.
     */
    public void updateMonthlyData(LocalDate gameDate){
        String[] xNames = new String[12];
        //Iterate over the different histories represented in the finance charts, e.g., the history of cash amounts
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
            //xNames[11] = gameDate.getYear() + "-" + String.format("%02d", gameDate.getMonthValue());
            //xNames[11] = getLocalisedString("finance.month.short." + gameDate.getMonthValue());
            //xNames[11] = gameDate.getYear() + String.format("%02d", gameDate.getMonthValue());
            xNames[11] = "11";

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
                //xNames[i] = monthEnd.getYear() + "-" + String.format("%02d", monthEnd.getMonthValue());
                //xNames[i] = getLocalisedString("finance.month.short." + monthEnd.getMonthValue());
                //xNames[i] = monthEnd.getYear() + String.format("%02d", monthEnd.getMonthValue());
                xNames[i] = i + "";
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

    /**
     * Updates the net worth difference between the current day and 30 days before. If there is no data for 30 days ago,
     * the oldest value is used.
     * @param gameDate The current date in the game.
     */
    public void updateNetWorthDifference(LocalDate gameDate){
        Double oldNetWorth = this.netWorthHistory.get(gameDate.minusDays(30));
        // if no entry 30 days ago, use oldest value
        if(oldNetWorth == null){
            oldNetWorth = this.netWorthHistory.firstEntry().getValue();
        }
        Double newNetWorth = this.netWorthHistory.get(gameDate);
        if((oldNetWorth != null) && (newNetWorth != null)){
            this.netWorthDifference.setValue(newNetWorth - oldNetWorth);
        }else{
            this.netWorthDifference.setValue(null);
        }
    }

    /**
     * Updates the cash difference between the current day and 30 days before. If there is no data for 30 days ago, the
     * oldest value is used.
     * @param gameDate The current date in the game.
     */
    public void updateCashDifference(LocalDate gameDate){
        Double oldCash = this.cashHistory.get(gameDate.minusDays(30));
        // if no entry 30 days ago, use oldest value
        if(oldCash == null){
            oldCash = this.cashHistory.firstEntry().getValue();
        }
        Double newCash = this.cashHistory.get(gameDate);
        if((oldCash != null) && (newCash != null)){
            this.cashDifference.setValue(newCash - oldCash);
        }else{
            this.cashDifference.setValue(null);
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
        return this.netWorthDifference.getValue();
    }

    public Double getCashDifference(){
        return this.cashDifference.getValue();
    }

    /**
     * Register a change listener that notifies, if a property has changed.
     *
     * @param listener A property change listener.
     */
    @Override
    public void registerPropertyChangeListener(PropertyChangeListener listener) {
        this.gameOver.addPropertyChangeListener(listener);
        this.loanRemoved.addPropertyChangeListener(listener);
        this.netWorth.addPropertyChangeListener(listener);
        this.cash.addPropertyChangeListener(listener);
        this.assets.addPropertyChangeListener(listener);
        this.liabilities.addPropertyChangeListener(listener);
        this.netWorthDifference.addPropertyChangeListener(listener);
        this.cashDifference.addPropertyChangeListener(listener);
        this.realEstateInvestmentAmount.addPropertyChangeListener(listener);
        this.stocksInvestmentAmount.addPropertyChangeListener(listener);
        this.ventureCapitalInvestmentAmount.addPropertyChangeListener(listener);
        this.updatedMonthlyData.addPropertyChangeListener(listener);
        this.updatedQuarterlyData.addPropertyChangeListener(listener);
    }

    public static FinanceDepartment createInstance() {
        return new FinanceDepartment();
    }

    public String getLocalisedString(String text, Locale locale) {
        ResourceBundle langBundle = ResourceBundle.getBundle(LANGUAGE_PROPERTIES_FILE, locale);
        return langBundle.getString(text);
    }
}
