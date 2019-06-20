package de.uni.mannheim.capitalismx.production;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Production {
    private HashMap<Product, Integer> inventar;
    private ArrayList<Machinery> machines;
    private double productionTechnologyFactor;
    private ProductionTechnology productionTechnology;
    private double researchAndDevelopmentFactor;
    private ProductionInvestment researchAndDevelopment;
    private double processAutomationFactor;
    private ProductionInvestment processAutomation;
    private double totalEngineerQualityOfWork;
    private double totalEngineerProductivity;
    private ProductionInvestment systemSecurity;
    private double productionVariableCosts;
    private double productionFixCosts;

    public Production() {
        this.researchAndDevelopment = new ProductionInvestment("Research and Development");
        this.processAutomation = new ProductionInvestment("Process Automation");
        this.systemSecurity = new ProductionInvestment("System Security");
    }

    public double calculateProductionVariableCosts() {
        this.productionVariableCosts = 0;
        for(HashMap.Entry<Product, Integer> entry : this.inventar.entrySet()) {
            this.productionVariableCosts += entry.getValue() * entry.getKey().calculateTotalVariableCosts();
        }
        return this.productionVariableCosts;
    }

    public double calculateProductionFixCosts() {
        this.productionFixCosts = 0;
        for(Machinery machinery : this.machines) {
            this.productionFixCosts += machinery.getPurchasePrice() + machinery.calculateMachineryDepreciation();
        }
        /* placeholder for facility rent*/
        int facilityRent = 30000;
        this.productionFixCosts += facilityRent;
        return this.productionFixCosts;
    }

    public void setProductsTotalProductCost() {
        for(HashMap.Entry<Product, Integer> entry : this.inventar.entrySet()) {
            entry.getKey().setTotalProductCosts(entry.getKey().calculateTotalVariableCosts() + this.productionFixCosts / this.inventar.size());
        }
    }

    public ArrayList<Machinery> getMachines() {
        return this.machines;
    }

    public double buyMachinery(Machinery machinery) {
        machines.add(machinery);
        return machinery.calculatePurchasePrice();
    }

    public double sellMachinery(Machinery machinery) {
        machines.remove(machinery);
        return machinery.calculateResellPrice();
    }

    public double launchProduct(Product product, int quantity) {
        int totalMachineCapacity = 0;
        // TO DO getTotalWareHouseCapacity;
        int amountOfProductsInStock = 0;
        for(HashMap.Entry<Product, Integer> entry : this.inventar.entrySet()) {
            amountOfProductsInStock += entry.getValue();
        }
        int totalTalWareHouseCapacity = 50000;
        int availableWareHouseCapacity = totalTalWareHouseCapacity - amountOfProductsInStock;
        for(Machinery machinery : this.machines) {
            totalMachineCapacity += machinery.getMachineryCapacity();
        }
        if(totalMachineCapacity <= quantity && availableWareHouseCapacity <= quantity) {
            // variable or total????????
            double variableProductCosts = 0;
            for (HashMap.Entry<Product, Integer> entry : this.inventar.entrySet()) {
                if (entry.getKey().getProductCategory() == product.getProductCategory()) {
                    this.inventar.remove(entry.getKey());
                }
            }
            this.inventar.put(product, quantity);
            /* LocalDate.now() placeholder for gameDate */
            LocalDate gameDate = LocalDate.now();
            product.setLaunchDate(gameDate);
            variableProductCosts = product.calculateTotalVariableCosts() * quantity;
            return variableProductCosts;
        } else {
            // throw error message "Your machinery capacity is not sufficient. Either produce a smaller amount or buy new machinery."
            return -1;
        }
    }

    public double produceProduct(Product product, int quantity) {
        int totalMachineCapacity = 0;
        // TO DO getAvailableWareHouseCapacity;
        int amountOfProductsInStock = 0;
        for(HashMap.Entry<Product, Integer> entry : this.inventar.entrySet()) {
            amountOfProductsInStock += entry.getValue();
        }
        int totalTalWareHouseCapacity = 50000;
        int availableWareHouseCapacity = totalTalWareHouseCapacity - amountOfProductsInStock;
        for(Machinery machinery : this.machines) {
            totalMachineCapacity += machinery.getMachineryCapacity();
        }
        if(totalMachineCapacity <= quantity && availableWareHouseCapacity <= quantity) {
            // variable or total????????
            double variableProductCosts = 0;
            /* LocalDate.now() placeholder for gameDate */
            LocalDate gameDate = LocalDate.now();
            variableProductCosts = product.calculateTotalVariableCosts() * quantity;
            return variableProductCosts;
        } else {
            // throw error message "Your machinery capacity is not sufficient. Either produce a smaller amount or buy new machinery."
            return -1;
        }
    }

    public double getAmountInStock(Product product) {
        return this.inventar.get(product);
    }

    public double getTotalProductCosts(Product product) {
        this.setProductsTotalProductCost();
        return product.getTotalProductCosts();
    }

    public void setProductSalesPrice(Product product, double salesPrice) {
        /* check for DecimalFormat ##,###.00 in GUI */
        if(salesPrice > 0 && salesPrice < 100000) {
            product.setSalesPrice(salesPrice);
        } else {
            // throw error "salesPrice has to between 0.01 and 99,999.99"
        }
    }

    public double getProductsSalesPrice(Product product) {
        return product.getSalesPrice();
    }

    public double getProductsProfitMargin(Product product) {
        return product.calculateProfitMargin();
    }

    public double calculateProductionTechnologyFactor() {
        this.productionTechnologyFactor = 0.7 + 0.1 * this.productionTechnology.getRange();
        return this.productionTechnologyFactor;
    }

    public double calculateResearchAndDevelopmentFactor() {
        this.researchAndDevelopmentFactor = 0.95 + 0.05 * this.researchAndDevelopmentFactor;
        return this.researchAndDevelopmentFactor;
    }

    public double calculateProcessAutomationFactor() {
        this.processAutomationFactor = 0.95 + 0.05 * this.processAutomation.getLevel();
        return this.processAutomationFactor;
    }

    public double calculateTotalEngineerQualityOfWork() {
        /* placeholder for the quality of work of the engineering team*/
        this.totalEngineerQualityOfWork = 0.7;
        return this.totalEngineerQualityOfWork;
    }

    public double calculateTotalEngineerProductivity() {
        this.totalEngineerProductivity = this.totalEngineerQualityOfWork * this.processAutomationFactor;
        return this.totalEngineerProductivity;
    }

    public ProductionTechnology getProductionTechnology() {
        return this.productionTechnology;
    }
}
