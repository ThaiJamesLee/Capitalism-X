package de.uni.mannheim.capitalismx.production;

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
            this.productionVariableCosts += entry.getValue() * entry.getKey().getTotalProductVariableCosts();
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

    public void setTotalProductCost() {
        int totalProductCosts = 0;
        for(HashMap.Entry<Product, Integer> entry : this.inventar.entrySet()) {
            entry.getKey().setTotalProductCosts(entry.getKey().getTotalProductVariableCosts() + this.productionFixCosts / this.inventar.size());
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
        double purchasePrice = 0;
        for(HashMap.Entry<Product, Integer> entry : this.inventar.entrySet()) {
            if(entry.getKey().getProductCategory() == product.getProductCategory()) {
                this.inventar.remove(entry.getKey());
            }
        }
        this.inventar.put(product, quantity);
        /*
        *
        *
        *
        *
        *
        * HIER
        *
        *
        *
        * */
        return purchasePrice;
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

    /* once every*/

    public ProductionTechnology getProductionTechnology() {
        return this.productionTechnology;
    }
}
