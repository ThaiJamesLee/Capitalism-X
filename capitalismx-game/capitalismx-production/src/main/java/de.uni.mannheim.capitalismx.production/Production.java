package de.uni.mannheim.capitalismx.production;

import de.uni.mannheim.capitalismx.procurement.component.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Production {

    private static Production instance;
    private HashMap<Product, Integer> numberProducedProducts;
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
    private double numberUnitsProducedPerMonth;
    private double monthlyAvailableMachineCapacity;
    private double manufactureEfficiency;
    private double productionProcessProductivity;
    private double normalizedProductionProcessProductivity;
    private double averageProductBaseQuality;

    private Production() {
        this.numberUnitsProducedPerMonth = 0;
        this.monthlyAvailableMachineCapacity = 0;
        this.numberProducedProducts = new HashMap<Product, Integer>();
        this.machines = new ArrayList<Machinery>();
        this.researchAndDevelopment = new ProductionInvestment("Research and Development");
        this.processAutomation = new ProductionInvestment("Process Automation");
        this.systemSecurity = new ProductionInvestment("System Security");
    }

    public static synchronized Production getInstance() {
        if(Production.instance == null) {
            Production.instance = new Production();
        }
        return Production.instance;
    }

    public double calculateProductionVariableCosts() {
        this.productionVariableCosts = 0;
        for(HashMap.Entry<Product, Integer> entry : this.numberProducedProducts.entrySet()) {
            this.productionVariableCosts += entry.getValue() * entry.getKey().calculateTotalVariableCosts();
        }
        return this.productionVariableCosts;
    }

    public double calculateProductionFixCosts() {
        this.productionFixCosts = 0;
        for(Machinery machinery : this.machines) {
            this.productionFixCosts += machinery.getPurchasePrice() + machinery.calculateMachineryDepreciation();
        }
        /* TODO placeholder for facility rent -> are they talking about the warehouses? */
        int facilityRent = 2000;
        this.productionFixCosts += facilityRent;
        return this.productionFixCosts;
    }

    public void setProductsTotalProductCost() {
        for(HashMap.Entry<Product, Integer> entry : this.numberProducedProducts.entrySet()) {
            entry.getKey().setTotalProductCosts(entry.getKey().calculateTotalVariableCosts() + this.productionFixCosts / this.numberProducedProducts.size());
        }
    }

    public ArrayList<Machinery> getMachines() {
        return this.machines;
    }

    public void updateMonthlyAvailableMachineCapacity() {
        for(Machinery machinery : this.machines) {
            this.monthlyAvailableMachineCapacity += machinery.getMachineryCapacity();
        }
    }

    /* This method should always be followed up by updateMonthlyAvailableMachineCapacity and calculate perfomance metric methods*/
    public void resetMonthlyPerformanceMetrics() {
        this.numberUnitsProducedPerMonth = 0;
        this.monthlyAvailableMachineCapacity = 0;
        this.manufactureEfficiency = 0;
    }

    public double buyMachinery(Machinery machinery) {
        //TODO
        machinery.setPurchaseDate(LocalDate.now());
        this.machines.add(machinery);
        this.monthlyAvailableMachineCapacity += machinery.getMachineryCapacity();
        return machinery.calculatePurchasePrice();
    }

    public double sellMachinery(Machinery machinery) {
        machines.remove(machinery);
        this.monthlyAvailableMachineCapacity -= machinery.getMachineryCapacity();
        return machinery.calculateResellPrice();
    }

    public double maintainAndRepairMachinery(Machinery machinery) {
        return machinery.maintainAndRepairMachinery();
    }

    public double upgradeMachinery(Machinery machinery) {
        this.monthlyAvailableMachineCapacity -= machinery.getMachineryCapacity();
        this.monthlyAvailableMachineCapacity += machinery.getMachineryCapacity() * 1.2;
        return machinery.upgradeMachinery();
    }

    public double launchProduct(String productname, ProductCategory productCategory, List<Component> componentList, int quantity, int freeStorage) {
        Product product = new Product(productname, productCategory, componentList);
        int totalMachineCapacity = 0;
        for(Machinery machinery : this.machines) {
            totalMachineCapacity += machinery.getMachineryCapacity();
        }
        if(totalMachineCapacity <= quantity && freeStorage <= quantity) {
            double variableProductCosts = 0;
            for (HashMap.Entry<Product, Integer> entry : this.numberProducedProducts.entrySet()) {
                this.numberProducedProducts.put(product, quantity);
            }
            /* LocalDate.now() placeholder for gameDate TODO*/
            LocalDate gameDate = LocalDate.now();
            product.setLaunchDate(gameDate);
            this.numberUnitsProducedPerMonth += quantity;
            variableProductCosts = product.calculateTotalVariableCosts() * quantity;
            return variableProductCosts;
        } else {
            // TODO throw error message "Your machinery capacity is not sufficient. Either produce a smaller amount or buy new machinery."
            return -1;
        }
    }

    public double produceProduct(Product product, int quantity, int freeStorage) {
        int totalMachineCapacity = 0;
        for(Machinery machinery : this.machines) {
            totalMachineCapacity += machinery.getMachineryCapacity();
        }
        if(totalMachineCapacity <= quantity && freeStorage <= quantity) {
            double variableProductCosts = 0;
            int newQuantity = quantity;
            for(HashMap.Entry<Product, Integer> entry : this.numberProducedProducts.entrySet()) {
                if(product == entry.getKey()) {
                    newQuantity += entry.getValue();
                }
            }
            this.numberProducedProducts.put(product, newQuantity);
            /* LocalDate.now() placeholder for gameDate */ // NEEDED? TODO
            //LocalDate gameDate = LocalDate.now();
            this.numberUnitsProducedPerMonth += quantity;
            variableProductCosts = product.calculateTotalVariableCosts() * quantity;
            return variableProductCosts;
        } else {
            // TODO throw error message "Your machinery capacity is not sufficient. Either produce a smaller amount or buy new machinery."
            return -1;
        }
    }

    public double getAmountInStock(Product product) {
        return this.numberProducedProducts.get(product);
    }

    public double getTotalProductCosts(Product product) {
        this.setProductsTotalProductCost();
        return product.getTotalProductCosts();
    }

    public void setProductSalesPrice(Product product, double salesPrice) {
        /* TODO check for DecimalFormat ##,###.00 in GUI */
        if(salesPrice > 0 && salesPrice < 100000) {
            product.setSalesPrice(salesPrice);
        } else {
            // TODO throw error "salesPrice has to between 0.01 and 99,999.99"
        }
    }

    public double calculateProfitMargin(Product product) {
        return product.calculateProfitMargin();
    }

    public void calculateAllProductsProfitMargin() {
        for(HashMap.Entry<Product, Integer> entry : this.numberProducedProducts.entrySet()) {
            entry.getKey().calculateProfitMargin();
        }
    }

    public double getProductsSalesPrice(Product product) {
        return product.getSalesPrice();
    }

    public double getProductsProfitMargin(Product product) {
        return product.calculateProfitMargin();
    }

    public double calculateProductionTechnologyFactor() {
        this.productionTechnology = ProductionTechnology.DEPRECIATED;
        double averageProductionTechnologyRange = 0;
        for(Machinery machinery : this.machines) {
            averageProductionTechnologyRange += machinery.getProductionTechnology().getRange();
        }
        averageProductionTechnologyRange /= this.machines.size();
        switch((int) Math.round(averageProductionTechnologyRange)) {
            case 1:
                this.productionTechnology = ProductionTechnology.DEPRECIATED;
                break;
            case 2:
                this.productionTechnology = ProductionTechnology.OLD;
                break;
            case 3:
                this.productionTechnology = ProductionTechnology.GOOD_CONDITIONS;
                break;
            case 4:
                this.productionTechnology = ProductionTechnology.PURCHASED_MORE_THAN_FIVE_YEARS_AGO;
                break;
            case 5:
                this.productionTechnology = ProductionTechnology.BRANDNEW;
                break;
            default: // Do nothing
        }
        this.productionTechnologyFactor = 0.7 + 0.1 * this.productionTechnology.getRange();
        return this.productionTechnologyFactor;
    }

    public ProductionTechnology calculateProductionTechnology() {
        this.productionTechnology = ProductionTechnology.DEPRECIATED;
        double averageProductionTechnologyRange = 0;
        for(Machinery machinery : this.machines) {
            averageProductionTechnologyRange += machinery.getProductionTechnology().getRange();
        }
        averageProductionTechnologyRange /= this.machines.size();
        switch((int) Math.round(averageProductionTechnologyRange)) {
            case 1:
                this.productionTechnology = ProductionTechnology.DEPRECIATED;
                break;
            case 2:
                this.productionTechnology = ProductionTechnology.OLD;
                break;
            case 3:
                this.productionTechnology = ProductionTechnology.GOOD_CONDITIONS;
                break;
            case 4:
                this.productionTechnology = ProductionTechnology.PURCHASED_MORE_THAN_FIVE_YEARS_AGO;
                break;
            case 5:
                this.productionTechnology = ProductionTechnology.BRANDNEW;
                break;
            default: // Do nothing
        }
        return this.productionTechnology;
    }

    public double calculateResearchAndDevelopmentFactor() {
        this.researchAndDevelopmentFactor = 0.95 + 0.05 * this.researchAndDevelopment.getLevel();
        return this.researchAndDevelopmentFactor;
    }

    public double calculateProcessAutomationFactor() {
        this.processAutomationFactor = 0.95 + 0.05 * this.processAutomation.getLevel();
        return this.processAutomationFactor;
    }

    public double calculateTotalEngineerQualityOfWork() {
        /* TODO placeholder for the quality of work of the engineering team*/
        this.totalEngineerQualityOfWork = 0.7;
        return this.totalEngineerQualityOfWork;
    }

    public double calculateTotalEngineerProductivity() {
        this.totalEngineerProductivity = this.totalEngineerQualityOfWork * this.processAutomationFactor;
        return this.totalEngineerProductivity;
    }

    public void setTotalProductQuality() {
        for(HashMap.Entry<Product, Integer> entry : this.numberProducedProducts.entrySet()) {
            entry.getKey().calculateTotalProductQuality(this.calculateProductionTechnologyFactor(), this.calculateTotalEngineerProductivity(), this.calculateResearchAndDevelopmentFactor());
        }
    }

    public ProductionTechnology getProductionTechnology() {
        return this.productionTechnology;
    }

    /* use this order to calculate mE -> pPP -> nPPP*/
    public double calculateManufactureEfficiency() {
        this.manufactureEfficiency = 0;
        if(this.monthlyAvailableMachineCapacity != 0) {
            this.manufactureEfficiency = this.numberUnitsProducedPerMonth /this. monthlyAvailableMachineCapacity;
        }
        return this.manufactureEfficiency;
    }

    public double calculateProductionProcessProductivity() {
        this.productionProcessProductivity = (this.calculateProductionTechnology().getRange() + Math.pow(Math.E, Math.log(this.calculateTotalEngineerProductivity())/10)) * this.manufactureEfficiency;
        return this.productionProcessProductivity;
    }

    public double calculateNormalizedProductionProcessProductivty() {
        this.normalizedProductionProcessProductivity = (this.productionProcessProductivity - 0.2) / (3.2 - 0.2);
        return this.normalizedProductionProcessProductivity;
    }

    public double investInSystemSecurity(int level) {
        this.systemSecurity = systemSecurity.invest(level);
        return 5000 * level;
    }

    public double investInResearchAndDevelopment(int level) {
        this.researchAndDevelopment = researchAndDevelopment.invest(level);
        return 5000 * level;
    }

    public double investInProcessAutomation(int level) {
        this.processAutomation = processAutomation.invest(level);
        return 5000 * level;
    }

    public void depreciateProductInvestment() {
        this.systemSecurity = this.systemSecurity.updateInvestment();
        this.researchAndDevelopment = this.researchAndDevelopment.updateInvestment();
        this.processAutomation = this.processAutomation.updateInvestment();
    }

    public HashMap<Product, Integer> getNumberProducedProducts() {
        return this.numberProducedProducts;
    }

    public void clearInventory() {
        this.numberProducedProducts.clear();
    }

    public double calculateAverageProductBaseQuality() {
        this.averageProductBaseQuality = 0;
        for(HashMap.Entry<Product, Integer> entry : this.numberProducedProducts.entrySet()) {
            this.averageProductBaseQuality += entry.getKey().calculateAverageBaseQuality();
        }
        return this.averageProductBaseQuality / this.numberProducedProducts.size();
    }

    /* TODO duration 1 month, winter month*/
    public void decreaseTotalEngineerQualityOfWorkRel(double decrease) {
        this.totalEngineerQualityOfWork *= (1 - decrease);
    }

    /* TODO only after year 2000 and for 3 months, used processAutomationFactor as processAutomation is on a Likert scale from 1 to 5*/
    public void decreaseProcessAutomationRel(double decrease) {
        this.processAutomationFactor *= (1 - decrease);
    }

    public boolean checkBaseQualityAboveThreshold() {
        return calculateAverageProductBaseQuality() >= 80;
    }

    public boolean checkProductionTechnologyBelowThreshold() {
        return this.calculateProductionTechnology().getRange() < 2;
    }
}