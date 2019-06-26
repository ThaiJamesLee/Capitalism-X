package de.uni.mannheim.capitalismx.finance.finance;

import de.uni.mannheim.capitalismx.hr.domain.Training;
import de.uni.mannheim.capitalismx.hr.employee.Employee;
import de.uni.mannheim.capitalismx.logistic.logistics.Logistics;
import de.uni.mannheim.capitalismx.logistic.logistics.Truck;
import de.uni.mannheim.capitalismx.production.Machinery;
import de.uni.mannheim.capitalismx.production.Product;
import de.uni.mannheim.capitalismx.production.Production;
import de.uni.mannheim.capitalismx.warehouse.Warehouse;
import de.uni.mannheim.capitalismx.warehouse.WarehouseType;
import de.uni.mannheim.capitalismx.warehouse.Warehousing;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * @author sdupper
 */
public class Finance {

    private static Finance instance;

    private double netWorth;
    private double cash;
    private double assets;
    private double liabilities;
    private double totalTruckValues;
    private double totalMachineValues;
    private double totalWarehousingValues;
    private double totalInvestmentAmount;
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

    private ArrayList<Warehouse> warehousesSold;
    private ArrayList<Truck> trucksSold;
    private ArrayList<Machinery> machinesSold;
    private ArrayList<Double> nopatLast5Years;

    private BankingSystem bankingSystem;
    private ArrayList<Investment> investments;


    private Finance(){
        this.cash = 1000000;
        this.taxRate = 0.2;
        this.bankingSystem = new BankingSystem();
        this.investments = new ArrayList<Investment>();
        this.decreaseNopatFactor = 0.0;
        this.decreaseNopatConstant = 0.0;
    }

    public static synchronized Finance getInstance() {
        if(Finance.instance == null) {
            Finance.instance = new Finance();
        }
        return Finance.instance;
    }

    // liabilities = loanAmount
    public double calculateNetWorth(LocalDate gameDate){
        //this.netWorth = this.cash + this.assets - this.liabilities;
        this.netWorth = this.calculateCash(gameDate) + this.calculateAssets(gameDate) - this.calculateLiabilities();
        return this.netWorth;
    }

    private double calculateAssets(LocalDate gameDate){
        //this.assets = this.totalTruckValues + this.totalMachineValues + this.totalWarehousingValues + this.totalInvestmentAmount;
        this.assets = this.calculateTotalTruckValues(gameDate) + this.calculateTotalMachineValues(gameDate) +
                this.calculateTotalWarehousingValues(gameDate) + this.calculateTotalInvestmentAmount();
        return this.assets;
    }

    // calculated daily
    private double calculateCash(LocalDate gameDate){
        //this.cash += this.nopat + this.assetsSold;
        this.cash += this.calculateNopat() + this.calculateAssetsSold(gameDate);
        return this.cash;
    }

    private double calculateAnnualDepreciation(double purchasePrice, double usefulLife){
        return purchasePrice / usefulLife;
    }

    private double calculateResellPrice(double purchasePrice, double usefulLife, double timeUsed){
        return purchasePrice - this.calculateAnnualDepreciation(purchasePrice, usefulLife) * timeUsed;
    }

    //TODO
    private double calculateTotalWarehousingValues(LocalDate gameDate){
        this.totalWarehousingValues = 0;
        ArrayList<Warehouse> warehouses = Warehousing.getInstance().getWarehouses();
        for(Warehouse warehouse : warehouses){
            if(warehouse.getWarehouseType() == WarehouseType.BUILT){
                 this.totalWarehousingValues += this.calculateResellPrice(warehouse.getBuildingCost(),
                 warehouse.getUsefulLife(), warehouse.calculateTimeUsed(gameDate));
            }
        }
        return this.totalWarehousingValues;
    }

    //TODO
    private double calculateTotalTruckValues(LocalDate gameDate){
        this.totalTruckValues = 0;
        ArrayList<Truck> trucks = Logistics.getInstance().getInternalFleet().getTrucks();
        for(Truck truck : trucks){
            this.totalTruckValues += this.calculateResellPrice(truck.getPurchasePrice(),
                    truck.getUsefulLife(), truck.calculateTimeUsed(gameDate));
        }
        return this.totalTruckValues;
    }

    //TODO
    private double calculateTotalMachineValues(LocalDate gameDate){
        this.totalMachineValues = 0;
        ArrayList<Machinery> machines = Production.getInstance().getMachines();
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
    }

    //TODO timestamp or thread in class that checks if new day
    public void sellMachine(Machinery machine){
        this.machinesSold.add(machine);
    }

    private double calculateAssetsSold(LocalDate gameDate){
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
    public double calculateNopat(){
        //this.nopat = this.ebit - this.incomeTax;
        this.nopat = ((this.calculateEbit() - this.calculateIncomeTax()) * (1 - this.decreaseNopatFactor)) - this.decreaseNopatConstant;
        return this.nopat;
    }

    private double calculateIncomeTax(){
        //this.incomeTax = this.ebit * this.taxRate;
        this.incomeTax = this.calculateEbit() * this.taxRate;
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
        return this.totalRevenue;
    }

    private double calculateTotalExpenses(){
        //this.totalExpenses = this.totalHRCosts + this.totalWarehouseCosts + this.totalLogisticsCosts + this.totalProductionCosts + this.totalMarketingCosts + this.totalSupportCosts;
        this.totalExpenses = this.calculateTotalHRCosts() + this.calculateTotalWarehouseCosts() + this.calculateTotalLogisticsCosts() + this.calculateTotalProductionCosts()
                + this.calculateTotalMarketingCosts() + this.calculateTotalSupportCosts();
        return this.totalExpenses;
    }

    //TODO
    private double calculateTotalHRCosts(){
        /**
        ArrayList<Training> trainings = null;
        ArrayList<Employee> employees = null;
        double totalTrainingCosts = 0;
        double totalSalaries = 0;

        for(Training training : trainings){
            totalTrainingCosts += training.getCosts();
        }

        for(Employee employee : employees){
            totalSalaries += employee.getSalary();
        }

        this.totalHRCosts = totalSalaries + totalTrainingCosts;
         **/
        return this.totalHRCosts;
    }

    //TODO
    private double calculateTotalWarehouseCosts(){
        double warehouseCosts = Warehousing.getInstance().calculateMonthlyCostWarehousing();
        double storageCosts = Warehousing.getInstance().calculateDailyStorageCost();

        this.totalWarehouseCosts = warehouseCosts + storageCosts;
        return this.totalWarehouseCosts;
    }

    //TODO
    private double calculateTotalLogisticsCosts(){
        this.totalLogisticsCosts = Logistics.getInstance().getTotalLogisticsCosts();
        return this.totalLogisticsCosts;
    }

    //TODO
    private double calculateTotalProductionCosts(){
        //get from other class
        return 0;
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

    //TODO
    private double calculateTotalSupportCosts(){
        //get from other class
        return 0;
    }

    private double calculateEbit(){
        //this.ebit = this.totalRevenue - this.totalExpenses;
        this.ebit = this.calculateTotalRevenue() - this.calculateTotalExpenses();
        return this.ebit;
    }

    //TODO update cash and netWorth?
    public ArrayList<BankingSystem.Loan> generateLoanSelection(double desiredLoanAmount){
        if(this.cash == 0){
            return null;
        }
        if(desiredLoanAmount > 0.7 * this.netWorth){
            desiredLoanAmount = 0.7 * this.netWorth;
        }
        return this.bankingSystem.generateLoanSelection(desiredLoanAmount);
    }

    public void addLoan(BankingSystem.Loan loan){
        bankingSystem.addLoan(loan);
    }

    //TODO update cash?
    public ArrayList<Investment> generateInvestmentSelection(double amount){
        if(amount > this.cash){
            return null;
        }
        ArrayList<Investment> investmentSelection = new ArrayList<Investment>();
        //Real Estate
        investmentSelection.add(new Investment(amount, 0.07, 0.2));
        //stocks
        investmentSelection.add(new Investment(amount, 0.1, 0.3));
        //Venture Capital
        investmentSelection.add(new Investment(amount, 0.142, 0.5));
        return  investmentSelection;
    }

    //TODO update cash?
    private void addInvestment(Investment investment){
        this.investments.add(investment);
        this.cash -= investment.getAmount();
        this.calculateTotalInvestmentAmount();
    }

    //TODO taxes
    //TODO update cash?
    private void removeInvestment(Investment investment){
        this.investments.remove(investment);
        this.cash += investment.getAmount();
        this.calculateTotalInvestmentAmount();
    }

    private double calculateLiabilities(){
        this.liabilities = bankingSystem.getAnnualPrincipalBalance();
        return this.liabilities;
    }

    private double calculateTotalInvestmentAmount(){
        this.totalInvestmentAmount = 0;
        for(Investment investment : this.investments){
            this.totalInvestmentAmount += investment.getAmount();
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

    private void decreaseCash(double amount){
        this.cash -= amount;
    }

    private void increaseCash(double amount){
        this.cash += amount;
    }

    //TODO decide on suitable consequences of acquisition, e.g., increase assets
    public void acquireCompany(){
        this.increaseCash(10000);
        this.decreaseCash(this.calculateNopat() * 0.70);
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
        this.decreaseCash(this.calculateNopat() * amount);
    }

    public void decreaseNopatConstant(double amount){
        this.decreaseNopatConstant += amount;
    }

    public void setDecreaseNopatConstant(double amount){
        this.decreaseNopatConstant = amount;
    }

    public double getCash() {
        return this.cash;
    }
}
