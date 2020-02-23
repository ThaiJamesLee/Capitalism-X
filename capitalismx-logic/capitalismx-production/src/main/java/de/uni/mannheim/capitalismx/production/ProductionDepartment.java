package de.uni.mannheim.capitalismx.production;

import de.uni.mannheim.capitalismx.domain.department.DepartmentImpl;
import de.uni.mannheim.capitalismx.domain.department.DepartmentSkill;
import de.uni.mannheim.capitalismx.domain.department.LevelingMechanism;
import de.uni.mannheim.capitalismx.domain.exception.InconsistentLevelException;
import de.uni.mannheim.capitalismx.procurement.component.Component;
import de.uni.mannheim.capitalismx.procurement.component.ComponentType;
import de.uni.mannheim.capitalismx.procurement.component.ComponentCategory;
import de.uni.mannheim.capitalismx.production.exceptions.*;
import de.uni.mannheim.capitalismx.production.skill.ProductionSkill;
import de.uni.mannheim.capitalismx.utils.data.PropertyChangeSupportList;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

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
    private Map<Component, Integer> storedComponents;
    /*private Map<ComponentType, Integer> componentTypeOfStoredComponents;
    private Map<SupplierCategory, Integer> supplierCategoryOfStoredComponents;*/
    private int totalWarehouseCapacity;
    private int decreasedProcessAutomationLevel;
    private double totalEngineerQualityOfWorkDecreasePercentage;

    private static final double LAUNCH_COSTS = 10000;

    private PropertyChangeSupportList launchedProductsChange;

    private static final Logger logger = LoggerFactory.getLogger(ProductionDepartment.class);

    private int initialProductionSlots;
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
        this.launchedProducts = new CopyOnWriteArrayList<>();
        this.machineSlotsAvailable = true;
        this.productionTechnology = ProductionTechnology.DEPRECIATED;
        this.storedComponents = new HashMap<>();
        /*this.componentTypeOfStoredComponents = new HashMap<>();
        this.supplierCategoryOfStoredComponents = new HashMap<>();*/

        this.totalWarehouseCapacity = 0;
        this.decreasedProcessAutomationLevel = 0;
        this.totalEngineerQualityOfWorkDecreasePercentage = 0;

        this.launchedProductsChange = new PropertyChangeSupportList();
        this.launchedProductsChange.setList(this.launchedProducts);
        this.launchedProductsChange.setAddPropertyName("launchedProductsChange");

        this.init();
    }

    private void init() {
        this.initProperties();
        this.initSkills();
    }

    private void initProperties() {
        this.setMaxLevel(Integer.parseInt(ResourceBundle.getBundle(LEVELING_PROPERTIES).getString(MAX_LEVEL_PROPERTY)));
        this.initialProductionSlots = Integer.parseInt(ResourceBundle.getBundle(LEVELING_PROPERTIES).getString(INITIAL_SLOTS_PROPERTY));
        this.productionSlots = initialProductionSlots;
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

    public void resetMonthlyPerformanceMetrics() {
        this.numberUnitsProducedPerMonth = 0;
        this.monthlyAvailableMachineCapacity = 0;
        this.manufactureEfficiency = 0;
        this.updateMonthlyAvailableMachineCapacity();
    }

    public double buyMachinery(Machinery machinery, LocalDate gameDate) throws NoMachinerySlotsAvailableException {
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
        throw new NoMachinerySlotsAvailableException("No more Capacity available to buy new Machine.");
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

    public double launchProduct(Product product, LocalDate gameDate, boolean productCategoryUnlocked) throws ProductCategoryNotUnlockedException {
        if(productCategoryUnlocked) {
            product.setLaunchDate(gameDate);
            this.launchedProductsChange.add(product);
        } else {
            throw new ProductCategoryNotUnlockedException("The product category " +  product.getProductCategory() + " is not unlocked yet.", product.getProductCategory());
        }
        return LAUNCH_COSTS;
    }

    public void setStoredComponents(Map<Component, Integer> storedComponents) {
        this.storedComponents = storedComponents;
    }

    public void setTotalWarehouseCapacity(int totalWarehouseCapacity) {
        this.totalWarehouseCapacity = totalWarehouseCapacity;
    }

    /*
    private void setComponentTypesAndSupplierCategoriesOfStoredComponents() {
        for(HashMap.Entry<Component, Integer> entry : this.storedComponents.entrySet()) {
            this.componentTypeOfStoredComponents.put(entry.getKey().getComponentType(), entry.getValue());
            this.supplierCategoryOfStoredComponents.put(entry.getKey().getSupplierCategory(), entry.getValue());
        }
    }
    */

    public double produceProduct(Product product, int quantity, int freeStorage, boolean allComponentsUnlocked) throws NotEnoughComponentsException, NotEnoughMachineCapacityException, NotEnoughFreeStorageException, ComponenLockedException {
        int totalMachineCapacity = 0;
        for(Machinery machinery : this.machines) {
            totalMachineCapacity += machinery.getMachineryCapacity();
        }

        if(quantity > 0) {
            if (freeStorage >= quantity) {
                if (totalMachineCapacity >= quantity) {
                    int maximumProducable = this.totalWarehouseCapacity;
                    for (Component component : product.getComponents()) {
                        boolean matched = false;
                        //if (this.componentTypeOfStoredComponents.containsKey(component.getComponentType()) && this.supplierCategoryOfStoredComponents.containsKey(component.getSupplierCategory())) {
                        for (HashMap.Entry<Component, Integer> entry : this.storedComponents.entrySet()) {
                            if (component.getComponentType() == entry.getKey().getComponentType() && component.getSupplierCategory() == entry.getKey().getSupplierCategory()) {
                                matched = true;
                                if (maximumProducable >= this.storedComponents.get(entry.getKey())) {
                                    maximumProducable = this.storedComponents.get(entry.getKey());
                                }
                                break;
                            }
                        }
                        if(!matched) {
                            maximumProducable = 0;
                        }
                    }

                    if (maximumProducable < quantity) {
                        throw new NotEnoughComponentsException("There are not enough components available to produce " + quantity + " unit(s).", maximumProducable);
                    }

                    if (!allComponentsUnlocked) {
                        throw new ComponenLockedException("At least on of the components is not unlocked yet. You might need to do some research first.");
                    }

                    for (Component component : product.getComponents()) {
                        for (HashMap.Entry<Component, Integer> entry : this.storedComponents.entrySet()) {
                            if(component.getComponentType() == entry.getKey().getComponentType() && component.getSupplierCategory() == entry.getKey().getSupplierCategory()) {
                                int newStoredQuantity = this.storedComponents.get(entry.getKey()) - quantity;
                                this.storedComponents.put(entry.getKey(), newStoredQuantity);
                                break;
                            }
                        }
                    }

                    double variableProductCosts = 0;
                    int newQuantity = quantity;
                    for (HashMap.Entry<Product, Integer> entry : this.numberProducedProducts.entrySet()) {
                        if (product == entry.getKey()) {
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
                    throw new NotEnoughMachineCapacityException("There is not enough machine capacity available to produce " + quantity + " unit(s).", totalMachineCapacity);
                }
            } else {
                throw new NotEnoughFreeStorageException("There is not enough warehouse capacity available to produce " + quantity + " unit(s).", freeStorage);
            }
        }
        return 0;
    }

    public double getAmountInProduction(Product product) {
        return this.numberProducedProducts.get(product);
    }

    public double getTotalProductCosts(Product product) {
        this.setProductsTotalProductCost();
        return product.getTotalProductCosts();
    }

    public double getTotalProductionCosts() {
        this.setProductsTotalProductCost();
        double totalProductionCosts = 0;
        for(HashMap.Entry<Product, Integer> entry : this.numberProducedProducts.entrySet()) {
            totalProductionCosts += entry.getKey().getTotalProductCosts();
        }
        return totalProductionCosts;
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
        this.totalEngineerQualityOfWork = 0.7 * (1 - this.totalEngineerQualityOfWorkDecreasePercentage);
        return this.totalEngineerQualityOfWork;
    }

    public double calculateTotalEngineerProductivity() {
        this.totalEngineerProductivity = this.totalEngineerQualityOfWork * this.processAutomationFactor;
        return this.totalEngineerProductivity;
    }

    public void setTotalProcurementQuality() {
        /*for(HashMap.Entry<Product, Integer> entry : this.numberProducedProducts.entrySet()) {
            entry.getKey().calculateTotalProcurementQuality();
        }*/
        for(Product product : this.launchedProducts) {
            product.calculateTotalProcurementQuality();
        }
    }

    public void setTotalProductQuality() {
        /*for(HashMap.Entry<Product, Integer> entry : this.numberProducedProducts.entrySet()) {
            entry.getKey().calculateTotalProductQuality(this.calculateProductionTechnologyFactor(), this.calculateTotalEngineerProductivity(), this.calculateResearchAndDevelopmentFactor());
        }*/
        for(Product product : this.launchedProducts) {
            product.calculateTotalProductQuality(this.calculateProductionTechnologyFactor(), this. calculateTotalEngineerProductivity(), this.calculateResearchAndDevelopmentFactor());
        }
    }

    public void setTotalProductQualityOfProduct(Product product) {
        product.calculateTotalProductQuality(this.calculateProductionTechnologyFactor(), this.calculateTotalEngineerProductivity(), this.calculateResearchAndDevelopmentFactor());
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

    /* public void updateComponentBaseCosts(LocalDate gameDate) {
        for(Map.Entry<Product, Integer> entry : this.numberProducedProducts.entrySet()) {
            for(Component component : entry.getKey().getComponents()) {
                component.calculateBaseCost(gameDate);
            }
        }
    } */

    public void decreaseTotalEngineerQualityOfWorkRel(double decrease) {
        this.totalEngineerQualityOfWorkDecreasePercentage = decrease;
        this.calculateTotalEngineerQualityOfWork();
    }

    public void increaseTotalEngineerQualityOfWorkRel() {
        this.totalEngineerQualityOfWorkDecreasePercentage = 0;
        this.calculateTotalEngineerQualityOfWork();
    }

    /* TODO used processAutomationFactor as processAutomation is on a Likert scale from 1 to 5*/
    public void decreaseProcessAutomationRel(double decrease) {
        int levelDecrease = (int) Math.round(this.processAutomation.getLevel() * (1 - decrease));
        this.processAutomation.decreaseLevel(levelDecrease);
        this.decreasedProcessAutomationLevel = levelDecrease;
    }

    public void increaseProcessAutomationRel() {
        this.processAutomation.increaseLevel(this.decreasedProcessAutomationLevel);
    }

    public boolean checkBaseQualityAboveThreshold() {
        return calculateAverageProductBaseQuality() >= 80;
    }

    public boolean checkProductionTechnologyBelowThreshold() {
        return this.calculateProductionTechnology().getRange() < 2;
    }

    public void calculateAll(LocalDate gameDate) {
        this.getAllAvailableComponents(gameDate);
        // this.updateComponentBaseCosts(gameDate);
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

    public Map<Component, Integer> getStoredComponents() {
        return this.storedComponents;
    }

    public int getDailyMachineCapacity() {
        int capacity = 0;
        for(Machinery machinery : this.machines) {
            capacity += machinery.getMachineryCapacity();
        }
        return  capacity;
    }

    public int getMonthlyMachineCapacity(LocalDate gameDate) {
        int dailyCapacity = this.getDailyMachineCapacity();
        int numberOfDaysInMonth = gameDate.lengthOfMonth();
        return numberOfDaysInMonth * dailyCapacity;
    }

    public synchronized List<Product> getLaunchedProducts() {
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

    public static ProductionDepartment createInstance() {
        return new ProductionDepartment();
    }

    public synchronized PropertyChangeSupportList getLaunchedProductsChange() {
        return launchedProductsChange;
    }

    @Override
    public void registerPropertyChangeListener(PropertyChangeListener listener) {
        this.launchedProductsChange.addPropertyChangeListener(listener);
    }
}