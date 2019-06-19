package de.uni.mannheim.capitalismx.finance.finance;

import de.uni.mannheim.capitalismx.logistic.logistics.Logistics;

import java.util.ArrayList;

/**
 *
 * @author sdupper
 */
public class Finance {
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

    private ArrayList<Warehouse> warehousesSold;
    private ArrayList<Truck> trucksSold;
    private ArrayList<Machine> machinesSold;

    private BankingSystem bankingSystem;
    private ArrayList<Investment> investments;


    public Finance(){
        this.taxRate = 0.2;
        this.bankingSystem = new BankingSystem();
        this.investments = new ArrayList<Investment>();
    }

    // liabilities = loanAmount
    private double calculateNetWorth(){
        //this.netWorth = this.cash + this.assets - this.liabilities;
        this.netWorth = this.calculateCash() + this.calculateAssets() - this.calculateLiabilities();
        return this.netWorth;
    }

    private double calculateAssets(){
        //this.assets = this.totalTruckValues + this.totalMachineValues + this.totalWarehousingValues + this.totalInvestmentAmount;
        this.assets = this.calculateTotalTruckValues() + this.calculateTotalMachineValues() + this.calculateTotalWarehousingValues() + this.calculateTotalInvestmentAmount();
        return this.assets;
    }

    // calculated daily
    private double calculateCash(){
        //this.cash += this.nopat + this.assetsSold;
        this.cash += this.calculateNopat() + this.calculateAssetsSold();
        return this.cash;
    }

    private double calculateAnnualDepreciation(double purchasePrice, double usefulLife){
        return purchasePrice / usefulLife;
    }

    private double calculateResellPrice(double purchasePrice, double usefulLife, double timeUsed){
        return purchasePrice - this.calculateAnnualDepreciation(purchasePrice, usefulLife) * timeUsed;
    }

    //TODO
    private double calculateTotalWarehousingValues(){
        this.totalWarehousingValues = 0;
        ArrayList<Warehouse> warehouses = null;
        for(Warehouse warehouse : warehouses){
            this.totalWarehousingValues += this.calculateResellPrice(warehouse.getPurchasePrice(), warehouse.getUsefulLife(), warehouse.getTimeUsed());
        }
        return this.totalWarehousingValues;
    }

    //TODO
    private double calculateTotalTruckValues(){
        this.totalTruckValues = 0;
        ArrayList<Truck> trucks = null;
        for(Truck truck : trucks){
            this.totalTruckValues += this.calculateResellPrice(truck.getPurchasePrice(), truck.getUsefulLife(), truck.getTimeUsed());
        }
        return this.totalTruckValues;
    }

    //TODO
    private double calculateTotalMachineValues(){
        this.totalMachineValues = 0;
        ArrayList<Machine> machines = null;
        for(Machine machine : machines){
            this.totalMachineValues += this.calculateResellPrice(machine.getPurchasePrice(), machine.getUsefulLife(), machine.getTimeUsed());
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
    public void sellMachine(Machine machine){
        this.machinesSold.add(machine);
    }

    private void calculateAssetsSold(){
        this.assetsSold = 0;
        for(Warehouse warehouse : this.warehousesSold){
            this.assetsSold += this.calculateResellPrice(warehouse.getPurchasePrice(), warehouse.getUsefulLife(), warehouse.getTimeUsed());
        }
        for(Truck truck : this.trucksSold){
            this.assetsSold += this.calculateResellPrice(truck.getPurchasePrice(), truck.getUsefulLife(), truck.getTimeUsed());
        }
        for(Machine machine : this.machinesSold){
            this.assetsSold += this.calculateResellPrice(machine.getPurchasePrice(), machine.getUsefulLife(), machine.getTimeUsed());
        }
    }

    // corrected formula in documentation
    private double calculateNopat(){
        //this.nopat = this.ebit - this.incomeTax;
        this.nopat = this.calculateEbit() - this.calculateIncomeTax();
        return this.nopat;
    }

    private double calculateIncomeTax(){
        //this.incomeTax = this.ebit * this.taxRate;
        this.incomeTax = this.calculateEbit() * this.taxRate;
        return this.incomeTax;
    }

    //TODO
    private double calculateTotalRevenue(){
        ArrayList<Product> productsSold = null;
        this.totalRevenue = 0;
        for(Product product : productsSold){
            this.totalRevenue += product.getSalesFigures() * product.getSalesPrice();
        }
        return this.totalRevenue;
    }

    private double calculateTotalExpenses(Logistics logistics){
        //this.totalExpenses = this.totalHRCosts + this.totalWarehouseCosts + this.totalLogisticsCosts + this.totalProductionCosts + this.totalMarketingCosts + this.totalSupportCosts;
        this.totalExpenses = this.calculateTotalHRCosts() + this.calculateTotalWarehouseCosts() + this.calculateTotalLogisticsCosts(logistics) + this.calculateTotalProductionCosts()
                + this.calculateTotalMarketingCosts() + this.calculateTotalSupportCosts();
        return this.totalExpenses;
    }

    //TODO
    private double calculateTotalHRCosts(){
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
        return this.totalHRCosts;
    }

    //TODO
    private double calculateTotalWarehouseCosts(){
        double warehouseCosts = ;
        double storageCosts = ;

        this.totalWarehouseCosts = warehouseCosts + storageCosts;
        return this.totalWarehouseCosts;
    }

    //TODO
    private double calculateTotalLogisticsCosts(Logistics logistics){
        this.totalLogisticsCosts = logistics.getTotalLogisticsCosts();
        return this.totalLogisticsCosts;
    }

    //TODO
    private double calculateTotalProductionCosts(){
        //get from other class
        return 0;
    }

    //TODO
    private double calculateTotalMarketingCosts(){
        double priceManagementConsultancy = ;
        double priceMarketResearch = ;
        double priceCampaign = ;
        double priceLobbyist = ;
        this.totalMarketingCosts = priceManagementConsultancy + priceMarketResearch + priceCampaign + priceLobbyist;
        return this.totalMarketingCosts;
    }

    //TODO
    private double calculateTotalSupportCosts(){
        //get from other class
        return 0;
    }

    private double calculateEbit(Logistics logistics){
        //this.ebit = this.totalRevenue - this.totalExpenses;
        this.ebit = this.calculateTotalRevenue() - this.calculateTotalExpenses(logistics);
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

    private void calculateLiabilities(){
        this.liabilities = bankingSystem.getAnnualPrincipalBalance();
    }

    private double calculateTotalInvestmentAmount(){
        this.totalInvestmentAmount = 0;
        for(Investment investment : this.investments){
            this.totalInvestmentAmount += investment.getAmount();
        }
        return this.totalInvestmentAmount;
    }
}
