package de.uni.mannheim.capitalismx.production;

import de.uni.mannheim.capitalismx.domain.department.DepartmentImpl;
import de.uni.mannheim.capitalismx.domain.department.DepartmentSkill;
import de.uni.mannheim.capitalismx.domain.department.LevelingMechanism;
import de.uni.mannheim.capitalismx.domain.exception.InconsistentLevelException;
import de.uni.mannheim.capitalismx.procurement.component.Component;
import de.uni.mannheim.capitalismx.procurement.component.ComponentType;
import de.uni.mannheim.capitalismx.procurement.component.ComponentCategory;
import de.uni.mannheim.capitalismx.production.skill.ProductionSkill;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.util.*;

public class ProductionDepartment extends DepartmentImpl {

    private static ProductionDepartment instance;
    private Map<Product, Integer> numberProducedProducts;
    private List<Machinery> machines;
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
    private int numberUnitsProducedPerMonth;
    private double monthlyAvailableMachineCapacity;
    private double manufactureEfficiency;
    private double productionProcessProductivity;
    private double normalizedProductionProcessProductivity;
    private double averageProductBaseQuality;
    private List<ComponentType> allAvailableComponents;
    private List<Product> launchedProducts;
    private boolean machineSlotsAvailable;

    private static final Logger logger = LoggerFactory.getLogger(ProductionDepartment.class);

    private int initialProductionSlots;

    private int baseCost;
    private int productionSlots;

    private static final String LEVELING_PROPERTIES = "production-leveling-definition";
    private static final String MAX_LEVEL_PROPERTY = "production.department.max.level";
    private static final String INITIAL_SLOTS_PROPERTY = "production.department.init.slots";

    private static final String SKILL_COST_PROPERTY_PREFIX = "production.skill.cost.";
    private static final String SKILL_SLOTS_PREFIX = "production.skill.slots.";


    private ProductionDepartment() {
        super("Production");
        this.numberUnitsProducedPerMonth = 0;
        this.monthlyAvailableMachineCapacity = 0;
        this.numberProducedProducts = new HashMap<>();
        this.machines = new ArrayList<>();
        this.allAvailableComponents = new ArrayList<>();
        this.researchAndDevelopment = new ProductionInvestment("Research and Development");
        this.processAutomation = new ProductionInvestment("Process Automation");
        this.systemSecurity = new ProductionInvestment("System Security");
        this.productionFixCosts = 0.0;
        this.productionVariableCosts = 0.0;
        this.launchedProducts = new ArrayList<>();
        this.machineSlotsAvailable = true;
        this.productionSlots = 0;

        this.init();
    }

    private void init() {
        this.initProperties();
        this.initSkills();
    }

    private void initProperties() {
        this.setMaxLevel(Integer.parseInt(ResourceBundle.getBundle(LEVELING_PROPERTIES).getString(MAX_LEVEL_PROPERTY)));
        this.initialProductionSlots = Integer.parseInt(ResourceBundle.getBundle(LEVELING_PROPERTIES).getString(INITIAL_SLOTS_PROPERTY));
    }

    private void initSkills() {
        Map<Integer, Double> costMap = initCostMap();
        try {
            setLevelingMechanism(new LevelingMechanism(this, costMap));
        } catch (InconsistentLevelException e) {
            String error = "The costMap size " + costMap.size() +  " does not match the maximum level " + this.getMaxLevel() + " of this department!";
            logger.error(error, e);
        }

        ResourceBundle skillBundle = ResourceBundle.getBundle(LEVELING_PROPERTIES);
        for(int i = 1; i <= getMaxLevel(); i++) {
            int slots = Integer.parseInt(skillBundle.getString(SKILL_SLOTS_PREFIX + i));
            skillMap.put(i, new ProductionSkill(i, slots));
        }
    }

    private Map<Integer, Double> initCostMap() {
        Map<Integer, Double> costMap = new HashMap<>();
        ResourceBundle bundle = ResourceBundle.getBundle(LEVELING_PROPERTIES);
        for(int i = 1; i <= getMaxLevel(); i++) {
            double cost = Integer.parseInt(bundle.getString(SKILL_COST_PROPERTY_PREFIX + i));
            costMap.put(i, cost);
        }
        return costMap;
    }

    private void updateProductionSlots() {
        int numberOfSlots = this.initialProductionSlots;
        List<DepartmentSkill> availableSkills = getAvailableSkills();

        for(DepartmentSkill skill : availableSkills) {
            numberOfSlots += ((ProductionSkill) skill).getNewProductionSlots();
        }
        this.productionSlots = numberOfSlots;
        this.machineSlotsAvailable = true;
    }

    public void setLevel(int level) {
        super.setLevel(level);
        this.updateProductionSlots();
    }

    public static synchronized ProductionDepartment getInstance() {
        if(ProductionDepartment.instance == null) {
            ProductionDepartment.instance = new ProductionDepartment();
        }
        return ProductionDepartment.instance;
    }

    public List<ComponentType> getAllAvailableComponents(LocalDate gameDate) {
        List<ComponentType> allAvailableComponents = new ArrayList<>();
        ComponentType[] allComponents = ComponentType.values();
        for(int i = 0; i < allComponents.length; i++) {
            if(allComponents[i].getAvailabilityDate() <= gameDate.getYear()) {
                allAvailableComponents.add(allComponents[i]);
            }
        }
        this.allAvailableComponents = allAvailableComponents;
        return this.allAvailableComponents;
    }

    public List<ComponentType> getAvailableComponentsOfComponentCategory(LocalDate gameDate, ComponentCategory componentCategory) {
        List<ComponentType> availableComponentsOfComponentType = new ArrayList<>();
        this.getAllAvailableComponents(gameDate);
        for(ComponentType component : this.allAvailableComponents) {
            if(component.getComponentCategory() == componentCategory) {
                availableComponentsOfComponentType.add(component);
            }
        }
        return availableComponentsOfComponentType;
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
        int facilityRent = 0;
        this.productionFixCosts += facilityRent;
        return this.productionFixCosts;
    }

    public void setProductsTotalProductCost() {
        for(HashMap.Entry<Product, Integer> entry : this.numberProducedProducts.entrySet()) {
            entry.getKey().setTotalProductCosts(entry.getKey().calculateTotalVariableCosts() + this.productionFixCosts / this.numberProducedProducts.size());
        }
    }

    public List<Machinery> getMachines() {
        return this.machines;
    }

    public void updateMonthlyAvailableMachineCapacity() {
        for(Machinery machinery : this.machines) {
            this.monthlyAvailableMachineCapacity += machinery.getMachineryCapacity();
        }
    }

    /* This method should always be followed up by updateMonthlyAvailableMachineCapacity and calculate performance metric methods*/
    public void resetMonthlyPerformanceMetrics() {
        this.numberUnitsProducedPerMonth = 0;
        this.monthlyAvailableMachineCapacity = 0;
        this.manufactureEfficiency = 0;
    }

    public double buyMachinery(Machinery machinery, LocalDate gameDate) {
        if(this.productionSlots > this.machines.size()) {
            machinery.setPurchaseDate(gameDate);
            this.machines.add(machinery);
            this.monthlyAvailableMachineCapacity += machinery.getMachineryCapacity();
            if(this.productionSlots == this.machines.size()) {
                this.machineSlotsAvailable = false;
            }
            return machinery.calculatePurchasePrice();
        }
        this.machineSlotsAvailable = false;
        return 0;
    }

    public double sellMachinery(Machinery machinery) {
        this.machines.remove(machinery);
        this.monthlyAvailableMachineCapacity -= machinery.getMachineryCapacity();
        return machinery.calculateResellPrice();
    }

    public Map<Machinery, Double> calculateMachineryResellPrices() {
        Map<Machinery, Double> machineryResellPrice = new HashMap<>();
        for(Machinery machinery : this.machines) {
            machineryResellPrice.put(machinery, machinery.calculateResellPrice());
        }
        return machineryResellPrice;
    }

    public double maintainAndRepairMachinery(Machinery machinery, LocalDate gameDate) {
        return machinery.maintainAndRepairMachinery(gameDate);
    }

    public double upgradeMachinery(Machinery machinery, LocalDate gameDate) {
        this.monthlyAvailableMachineCapacity -= machinery.getMachineryCapacity();
        this.monthlyAvailableMachineCapacity += machinery.getMachineryCapacity() * 1.2;
        return machinery.upgradeMachinery(gameDate);
    }

    public void depreciateMachinery(boolean naturalDisaster, LocalDate gameDate) {
        for(Machinery machinery : this.machines) {
            machinery.depreciateMachinery(naturalDisaster, gameDate);
        }
    }

    public double launchProduct(Product product, int quantity, int freeStorage) {
        int totalMachineCapacity = 0;
        for(Machinery machinery : this.machines) {
            totalMachineCapacity += machinery.getMachineryCapacity();
        }
        if(totalMachineCapacity >= quantity && freeStorage >= quantity) {
            double variableProductCosts = 0;
            //for (HashMap.Entry<Product, Integer> entry : this.numberProducedProducts.entrySet()) {
            this.numberProducedProducts.put(product, quantity);
            //}
            /* LocalDate.now() placeholder for gameDate TODO*/
            LocalDate gameDate = LocalDate.now();
            product.setLaunchDate(gameDate);
            this.launchedProducts.add(product);
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
        if(totalMachineCapacity >= quantity && freeStorage >= quantity) {
            double variableProductCosts = 0;
            int newQuantity = quantity;
            for(HashMap.Entry<Product, Integer> entry : this.numberProducedProducts.entrySet()) {
                if(product == entry.getKey()) {
                    newQuantity += this.numberProducedProducts.get(product);
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

    public double getAmountInProduction(Product product) {
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

    // TODO really necessary?
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

    public void setTotalProcurementQuality() {
        for(HashMap.Entry<Product, Integer> entry : this.numberProducedProducts.entrySet()) {
            entry.getKey().calculateTotalProcurementQuality();
        }
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

    public double calculateNormalizedProductionProcessProductivity() {
        this.normalizedProductionProcessProductivity = (this.productionProcessProductivity - 0.2) / (3.2 - 0.2);
        return this.normalizedProductionProcessProductivity;
    }

    public double investInSystemSecurity(int level, LocalDate gameDate) {
        this.systemSecurity = systemSecurity.invest(level, gameDate);
        return 5000 * level;
    }

    public double investInResearchAndDevelopment(int level, LocalDate gameDate) {
        this.researchAndDevelopment = researchAndDevelopment.invest(level, gameDate);
        return 5000 * level;
    }

    public double investInProcessAutomation(int level, LocalDate gameDate) {
        this.processAutomation = processAutomation.invest(level, gameDate);
        return 5000 * level;
    }

    public void depreciateProductInvestment(LocalDate gameDate) {
        this.systemSecurity = this.systemSecurity.updateInvestment(gameDate);
        this.researchAndDevelopment = this.researchAndDevelopment.updateInvestment(gameDate);
        this.processAutomation = this.processAutomation.updateInvestment(gameDate);
    }

    public Map<Product, Integer> getNumberProducedProducts() {
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

    public void updateComponentBaseCosts(LocalDate gameDate) {
        for(Map.Entry<Product, Integer> entry : this.numberProducedProducts.entrySet()) {
            for(Component component : entry.getKey().getComponents()) {
                component.calculateBaseCost(gameDate);
            }
        }
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

    public void calculateAll(LocalDate gameDate) {
        this.getAllAvailableComponents(gameDate);
        this.updateComponentBaseCosts(gameDate);
        this.depreciateProductInvestment(gameDate);
        this.depreciateMachinery(false, gameDate);
        this.calculateMachineryResellPrices();
        this.calculateProductionTechnologyFactor();
        this.calculateResearchAndDevelopmentFactor();
        this.calculateProcessAutomationFactor();
        this.calculateTotalEngineerQualityOfWork();
        this.calculateTotalEngineerProductivity();
        this.setTotalProcurementQuality();
        this.setTotalProductQuality();
        this.calculateProductionVariableCosts();
        this.calculateProductionFixCosts();
        this.setProductsTotalProductCost();
        this.updateMonthlyAvailableMachineCapacity();
        this.calculateManufactureEfficiency();
        this.calculateProductionProcessProductivity();
        this.calculateNormalizedProductionProcessProductivity();
    }

    public int getDailyMachineCapacity() {
        int capacity = 0;
        for(Machinery machinery : this.machines) {
            capacity += machinery.getMachineryCapacity();
        }
        return  capacity;
    }

    public List<Product> getLaunchedProducts() {
        return this.launchedProducts;
    }

    public double getProductionTechnologyFactor() {
        return this.productionTechnologyFactor;
    }

    public double getResearchAndDevelopmentFactor() {
        return this.researchAndDevelopmentFactor;
    }

    public ProductionInvestment getResearchAndDevelopment() {
        return this.researchAndDevelopment;
    }

    public double getProcessAutomationFactor() {
        return this.processAutomationFactor;
    }

    public ProductionInvestment getProcessAutomation() {
        return this.processAutomation;
    }

    public double getTotalEngineerQualityOfWork() {
        return this.totalEngineerQualityOfWork;
    }

    public double getTotalEngineerProductivity() {
        return this.totalEngineerProductivity;
    }

    public ProductionInvestment getSystemSecurity() {
        return this.systemSecurity;
    }

    public double getProductionVariableCosts() {
        return this.productionVariableCosts;
    }

    public double getProductionFixCosts() {
        return this.productionFixCosts;
    }

    public int getNumberUnitsProducedPerMonth() {
        return this.numberUnitsProducedPerMonth;
    }

    public double getMonthlyAvailableMachineCapacity() {
        return this.monthlyAvailableMachineCapacity;
    }

    public double getManufactureEfficiency() {
        return this.manufactureEfficiency;
    }

    public double getProductionProcessProductivity() {
        return this.productionProcessProductivity;
    }

    public double getNormalizedProductionProcessProductivity() {
        return this.normalizedProductionProcessProductivity;
    }

    public double getAverageProductBaseQuality() {
        return this.averageProductBaseQuality;
    }

    public int getProductionSlots() {
        return productionSlots;
    }

    public boolean getMachineSlotsAvailable() {
        this.machineSlotsAvailable = this.productionSlots > this.machines.size();
        return this.machineSlotsAvailable;
    }

    public static void setInstance(ProductionDepartment instance) {
        ProductionDepartment.instance = instance;
    }

    @Override
    public void registerPropertyChangeListener(PropertyChangeListener listener) {

    }
}