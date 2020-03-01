package de.uni.mannheim.capitalismx.production.department;

import de.uni.mannheim.capitalismx.domain.department.DepartmentImpl;
import de.uni.mannheim.capitalismx.domain.department.DepartmentSkill;
import de.uni.mannheim.capitalismx.domain.department.LevelingMechanism;
import de.uni.mannheim.capitalismx.domain.exception.InconsistentLevelException;
import de.uni.mannheim.capitalismx.domain.exception.LevelingRequirementNotFulFilledException;
import de.uni.mannheim.capitalismx.procurement.component.Component;
import de.uni.mannheim.capitalismx.procurement.component.ComponentType;
import de.uni.mannheim.capitalismx.procurement.component.ComponentCategory;
import de.uni.mannheim.capitalismx.production.machinery.Machinery;
import de.uni.mannheim.capitalismx.production.product.Product;
import de.uni.mannheim.capitalismx.production.exceptions.*;
import de.uni.mannheim.capitalismx.production.investment.ProductionInvestment;
import de.uni.mannheim.capitalismx.production.skill.ProductionSkill;
import de.uni.mannheim.capitalismx.utils.data.PropertyChangeSupportList;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * This class represents the ProductionDepartment.
 * It is used to produce products.
 * Machinery can be bought to increase production capacity and production investments can be used to increase quality of products.
 * Based on the report of predecessor group on p.36 - 45
 *
 * @author dzhao
 */
public class ProductionDepartment extends DepartmentImpl {

    private static ProductionDepartment instance;

    /**
     * Map of the products produced and their numbers of this day.
     */
    private Map<Product, Integer> numberProducedProducts;
    private List<Machinery> machines;
    private double productionTechnologyFactor;
    private ProductionTechnology productionTechnology;
    private double qualityAssuranceFactor;
    private ProductionInvestment qualityAssurance;
    private double processAutomationFactor;
    private ProductionInvestment processAutomation;
    private double totalEngineerQualityOfWork;
    private double totalEngineerProductivity;
    private ProductionInvestment systemSecurity;
    private double productionVariableCosts;
    private double productionFixCosts;
    private int numberUnitsProducedPerMonth;

    /**
     * The accumulated daily available machine capacity during this month.
     */
    private double monthlyAvailableMachineCapacity;
    private double manufactureEfficiency;
    private double productionProcessProductivity;
    private double normalizedProductionProcessProductivity;
    private double averageProductBaseQuality;
    private List<ComponentType> allAvailableComponents;

    /**
     * List of all launched products.
     */
    private List<Product> launchedProducts;

    /**
     * Flag whether there are machine slots available.
     */
    private boolean machineSlotsAvailable;

    /**
     * A Map of the components stored in the warehouse.
     */
    private Map<Component, Integer> storedComponents;
    private int totalWarehouseCapacity;
    private int decreasedProcessAutomationLevel;
    private double totalEngineerQualityOfWorkDecreasePercentage;

    /**
     * Number of production workers hired in HR.
     */
    private int numberOfProductionWorkers;

    /**
     * The costs of launching a product.
     */
    private static final double PRODUCT_LAUNCH_COSTS = 10000;

    /**
     * PropertyChangeSupportList used for notifying the GUI of changes to launchedProducts.
     */
    private PropertyChangeSupportList<Product> launchedProductsChange;

    private static final Logger logger = LoggerFactory.getLogger(ProductionDepartment.class);

    /**
     * Initial slots for machines in the department. Determines how many machines can be used in the department.
     */
    private int initialProductionSlots;

    /**
     * Slots for machines in the department. Determines how many machines can be used in the department.
     */
    private int productionSlots;

    private static final String LEVELING_PROPERTIES = "production-leveling-definition";
    private static final String MAX_LEVEL_PROPERTY = "production.department.max.level";
    private static final String INITIAL_SLOTS_PROPERTY = "production.department.init.slots";

    private static final String SKILL_COST_PROPERTY_PREFIX = "production.skill.cost.";
    private static final String SKILL_SLOTS_PREFIX = "production.skill.slots.";
    private static final String SKILL_WORKERS_NEEDED_PREFIX = "production.skill.workers.";


    /**
     * Constructor of the production department.
     * Initializes the variables, including PropertyChangeSupport variables.
     */
    private ProductionDepartment() {
        super("Production");
        this.numberUnitsProducedPerMonth = 0;
        this.monthlyAvailableMachineCapacity = 0;
        this.numberProducedProducts = new HashMap<>();
        this.machines = new ArrayList<>();
        this.allAvailableComponents = new ArrayList<>();
        this.qualityAssurance = new ProductionInvestment("Quality Assurance");
        this.processAutomation = new ProductionInvestment("Process Automation");
        this.systemSecurity = new ProductionInvestment("System Security");
        this.productionFixCosts = 0.0;
        this.productionVariableCosts = 0.0;
        this.launchedProducts = new CopyOnWriteArrayList<>();
        this.machineSlotsAvailable = true;
        this.productionTechnology = ProductionTechnology.DEPRECIATED;
        this.storedComponents = new HashMap<>();
        this.numberOfProductionWorkers = 0;
        this.totalWarehouseCapacity = 0;
        this.decreasedProcessAutomationLevel = 0;
        this.totalEngineerQualityOfWorkDecreasePercentage = 0;

        this.launchedProductsChange = new PropertyChangeSupportList<>();
        this.launchedProductsChange.setList(this.launchedProducts);
        this.launchedProductsChange.setAddPropertyName("launchedProductsChange");

        this.init();
    }

    /**
     * Initializes necessary properties and skills.
     */
    private void init() {
        this.initProperties();
        this.initSkills();
    }

    /**
     * Initializes properties
     */
    private void initProperties() {
        this.setMaxLevel(Integer.parseInt(ResourceBundle.getBundle(LEVELING_PROPERTIES).getString(MAX_LEVEL_PROPERTY)));
        this.initialProductionSlots = Integer.parseInt(ResourceBundle.getBundle(LEVELING_PROPERTIES).getString(INITIAL_SLOTS_PROPERTY));
        this.productionSlots = initialProductionSlots;
    }

    /**
     * Initializes skills
     */
    private void initSkills() {
        Map<Integer, Double> costMap = initCostMap();
        try {
            setLevelingMechanism(new LevelingMechanism(this, costMap));
        } catch (InconsistentLevelException e) {
            String error = "The costMap size " + costMap.size() +  " does not match the maximum level " + this.getMaxLevel() + " of this department!";
            logger.error(error, e);
        }

        ResourceBundle skillBundle = ResourceBundle.getBundle(LEVELING_PROPERTIES);
        for (int i = 1; i <= getMaxLevel(); i++) {
            int slots = Integer.parseInt(skillBundle.getString(SKILL_SLOTS_PREFIX + i));
            int workersNeeded = Integer.parseInt(skillBundle.getString(SKILL_WORKERS_NEEDED_PREFIX + i));
            skillMap.put(i, new ProductionSkill(i, slots, workersNeeded));
        }
    }

    /**
     * Initializes the cost map for the department levels.
     *
     * @return the cost map
     */
    private Map<Integer, Double> initCostMap() {
        Map<Integer, Double> costMap = new HashMap<>();
        ResourceBundle bundle = ResourceBundle.getBundle(LEVELING_PROPERTIES);
        for (int i = 1; i <= getMaxLevel(); i++) {
            double cost = Integer.parseInt(bundle.getString(SKILL_COST_PROPERTY_PREFIX + i));
            costMap.put(i, cost);
        }
        return costMap;
    }

    /**
     * updates the production slots after leveling up and sets the machineSlotsAvailable flag to true.
     */
    private void updateProductionSlots() {
        int numberOfSlots = this.initialProductionSlots;
        List<DepartmentSkill> availableSkills = getAvailableSkills();

        for (DepartmentSkill skill : availableSkills) {
            numberOfSlots += ((ProductionSkill) skill).getNewProductionSlots();
        }
        this.productionSlots = numberOfSlots;
        this.machineSlotsAvailable = true;
    }

    /**
     * The leveling mechanism of this department.
     * It checks whether there are enough production workers hired for the level up. Otherwise it throws a LevelingRequirementNotFulFilledException
     *
     * @param level
     * @throws LevelingRequirementNotFulFilledException
     */
    public void setLevel(int level) throws LevelingRequirementNotFulFilledException {
        ProductionSkill productionSkill = (ProductionSkill) skillMap.get(level);
        if (productionSkill.getProductionWorkersNeeded() <= this.numberOfProductionWorkers) {
            super.setLevel(level);
            this.updateProductionSlots();
        } else {
            throw new LevelingRequirementNotFulFilledException("Not enough production workers employed for level up.");
        }
    }

    /**
     * Gets singleton instance.
     *
     * @return the singleton instance
     */
    public static synchronized ProductionDepartment getInstance() {
        if (ProductionDepartment.instance == null) {
            ProductionDepartment.instance = new ProductionDepartment();
        }
        return ProductionDepartment.instance;
    }

    /**
     * Sets  instance.
     *
     * @param instance the instance
     */
    public static void setInstance(ProductionDepartment instance) {
        ProductionDepartment.instance = instance;
    }

    /**
     * Create instance of production department.
     *
     * @return the production department
     */
    public static ProductionDepartment createInstance() {
        return new ProductionDepartment();
    }

    /**
     * Gets all available components.
     *
     * @param gameDate the game date
     * @return the all available components
     */
    public List<ComponentType> getAllAvailableComponents(LocalDate gameDate) {
        List<ComponentType> allAvailableComponents = new ArrayList<>();
        ComponentType[] allComponents = ComponentType.values();
        for (int i = 0; i < allComponents.length; i++) {
            if (allComponents[i].getAvailabilityDate() <= gameDate.getYear()) {
                allAvailableComponents.add(allComponents[i]);
            }
        }
        this.allAvailableComponents = allAvailableComponents;
        return this.allAvailableComponents;
    }

    /**
     * Gets available components of component category.
     *
     * @param gameDate          the game date
     * @param componentCategory the component category
     * @return the available components of component category
     */
    public List<ComponentType> getAvailableComponentsOfComponentCategory(LocalDate gameDate, ComponentCategory componentCategory) {
        List<ComponentType> availableComponentsOfComponentType = new ArrayList<>();
        this.getAllAvailableComponents(gameDate);
        for (ComponentType component : this.allAvailableComponents) {
            if (component.getComponentCategory() == componentCategory) {
                availableComponentsOfComponentType.add(component);
            }
        }
        return availableComponentsOfComponentType;
    }

    /**
     * Calculates production variable costs.
     * Accumulates the total variable costs of each produced product.
     *
     * @return the production variable costs
     */
    public double calculateProductionVariableCosts() {
        this.productionVariableCosts = 0;
        for (HashMap.Entry<Product, Integer> entry : this.numberProducedProducts.entrySet()) {
            this.productionVariableCosts += entry.getValue() * entry.getKey().calculateTotalVariableCosts();
        }
        return this.productionVariableCosts;
    }

    /**
     * Calculates production fix costs.
     * Based on report of predecessor group.
     * Facility rent was not further defined by them and is therefore left out of this calculation.
     *
     * @return the production fix costs
     */
    public double calculateProductionFixCosts() {
        this.productionFixCosts = 0;
        for (Machinery machinery : this.machines) {
            this.productionFixCosts += machinery.getPurchasePrice() + machinery.calculateMachineryDepreciation();
        }
        return this.productionFixCosts;
    }

    /**
     * Sets products total product cost for every produced product.
     */
    public void setProductsTotalProductCost() {
        for (HashMap.Entry<Product, Integer> entry : this.numberProducedProducts.entrySet()) {
            entry.getKey().setTotalProductCosts(entry.getKey().calculateTotalVariableCosts() + this.productionFixCosts / this.numberProducedProducts.size());
        }
    }

    /**
     * Gets machines.
     *
     * @return the machines
     */
    public List<Machinery> getMachines() {
        return this.machines;
    }

    /**
     * Updates monthly available machine capacity.
     * Accumulates the daily machinery capacity.
     */
    public void updateMonthlyAvailableMachineCapacity() {
        for (Machinery machinery : this.machines) {
            this.monthlyAvailableMachineCapacity += machinery.getMachineryCapacity();
        }
    }

    /**
     * Reset monthly performance metrics.
     */
    public void resetMonthlyPerformanceMetrics() {
        this.numberUnitsProducedPerMonth = 0;
        this.monthlyAvailableMachineCapacity = 0;
        this.manufactureEfficiency = 0;
        this.updateMonthlyAvailableMachineCapacity();
    }

    /**
     * Buys machinery.
     * Adds machinery to list of machines.
     *
     * @param machinery the machinery
     * @param gameDate  the game date
     * @return the purchasePrice of the machinery
     * @throws NoMachinerySlotsAvailableException the no machinery slots available exception if no slots are available
     */
    public double buyMachinery(Machinery machinery, LocalDate gameDate) throws NoMachinerySlotsAvailableException {
        if (this.productionSlots > this.machines.size()) {
            machinery.setPurchaseDate(gameDate);
            this.machines.add(machinery);
            this.monthlyAvailableMachineCapacity += machinery.getMachineryCapacity();
            if (this.productionSlots == this.machines.size()) {
                this.machineSlotsAvailable = false;
            }
            return machinery.calculatePurchasePrice();
        }
        this.machineSlotsAvailable = false;
        throw new NoMachinerySlotsAvailableException("No more Capacity available to buy new Machine.");
    }

    /**
     * Sells machinery.
     * Removes machinery from list of machines.
     *
     * @param machinery the machinery
     * @return the resellPrice
     */
    public double sellMachinery(Machinery machinery) {
        this.machines.remove(machinery);
        this.monthlyAvailableMachineCapacity -= machinery.getMachineryCapacity();
        return machinery.calculateResellPrice();
    }

    /**
     * Calculates machinery resell prices map.
     *
     * @return the map
     */
    public Map<Machinery, Double> calculateMachineryResellPrices() {
        Map<Machinery, Double> machineryResellPrice = new HashMap<>();
        for(Machinery machinery : this.machines) {
            machineryResellPrice.put(machinery, machinery.calculateResellPrice());
        }
        return machineryResellPrice;
    }

    /**
     * Maintains and repairs machinery.
     *
     * @param machinery the machinery
     * @param gameDate  the game date
     * @return the price
     */
    public double maintainAndRepairMachinery(Machinery machinery, LocalDate gameDate) {
        return machinery.maintainAndRepairMachinery(gameDate);
    }

    /**
     * Upgrades machinery.
     *
     * @param machinery the machinery
     * @param gameDate  the game date
     * @return the price
     */
    public double upgradeMachinery(Machinery machinery, LocalDate gameDate) {
        this.monthlyAvailableMachineCapacity -= machinery.getMachineryCapacity();
        this.monthlyAvailableMachineCapacity += machinery.getMachineryCapacity() * 1.2;
        return machinery.upgradeMachinery(gameDate);
    }

    /**
     * Depreciates machinery.
     *
     * @param naturalDisaster the natural disaster
     * @param gameDate        the game date
     */
    public void depreciateMachinery(boolean naturalDisaster, LocalDate gameDate) {
        for (Machinery machinery : this.machines) {
            machinery.depreciateMachinery(naturalDisaster, gameDate);
        }
    }

    /**
     * Launches product.
     * It checks whether the product category was unlocked and whether the product name is unique.
     * Adds the product to the list of launched products.
     *
     * @param product                 the product
     * @param gameDate                the game date
     * @param productCategoryUnlocked whether the product category is unlocked
     * @return the product launch costs
     * @throws ProductCategoryNotUnlockedException the product category not unlocked exception if the product category was not unlocked in the R&D department
     * @throws ProductNameAlreadyInUseException    the product name already in use exception if the product name is not unique
     */
    public double launchProduct(Product product, LocalDate gameDate, boolean productCategoryUnlocked) throws ProductCategoryNotUnlockedException, ProductNameAlreadyInUseException {
        for (Product launchedProduct : this.launchedProducts) {
            if (launchedProduct.getProductName().equals(product.getProductName())) {
                throw new ProductNameAlreadyInUseException("The product name \"" + product.getProductName() + "\" is already in use.", product.getProductName());
            }
        }
        if (productCategoryUnlocked) {
            product.setLaunchDate(gameDate);
            this.launchedProductsChange.add(product);
        } else {
            throw new ProductCategoryNotUnlockedException("The product category " +  product.getProductCategory() + " is not unlocked yet.", product.getProductCategory());
        }
        return PRODUCT_LAUNCH_COSTS;
    }

    /**
     * Sets stored components.
     *
     * @param storedComponents the stored components
     */
    public void setStoredComponents(Map<Component, Integer> storedComponents) {
        this.storedComponents = storedComponents;
    }

    /**
     * Sets total warehouse capacity.
     *
     * @param totalWarehouseCapacity the total warehouse capacity
     */
    public void setTotalWarehouseCapacity(int totalWarehouseCapacity) {
        this.totalWarehouseCapacity = totalWarehouseCapacity;
    }

    /**
     * Produce products.
     * It checks whether the product can be produced in the specified quantity (enough components, components unlocked, enough free storage, enough machine capacity),
     * otherwise it throws an exception.
     *
     * @param product               the product
     * @param quantity              the quantity
     * @param freeStorage           the free storage
     * @param allComponentsUnlocked the all components unlocked
     * @return the variable product costs.
     * @throws NotEnoughComponentsException      the not enough components exception
     * @throws NotEnoughMachineCapacityException the not enough machine capacity exception
     * @throws NotEnoughFreeStorageException     the not enough free storage exception
     * @throws ComponentLockedException           the componen locked exception
     */
    public double produceProduct(Product product, int quantity, int freeStorage, boolean allComponentsUnlocked) throws NotEnoughComponentsException, NotEnoughMachineCapacityException, NotEnoughFreeStorageException, ComponentLockedException {
        int totalMachineCapacity = 0;
        for (Machinery machinery : this.machines) {
            totalMachineCapacity += machinery.getMachineryCapacity();
        }

        if (quantity > 0) {
            if (freeStorage >= quantity) {
                if (totalMachineCapacity >= quantity) {
                    int maximumProducable = this.totalWarehouseCapacity;
                    for (Component component : product.getComponents()) {
                        boolean matched = false;
                        for (HashMap.Entry<Component, Integer> entry : this.storedComponents.entrySet()) {
                            if (component.sameComponent(entry.getKey())) {
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
                        throw new ComponentLockedException("At least on of the components is not unlocked yet. You might need to do some research first.");
                    }

                    for (Component component : product.getComponents()) {
                        for (HashMap.Entry<Component, Integer> entry : this.storedComponents.entrySet()) {
                            if (component.sameComponent(entry.getKey())) {
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

    /**
     * Remove sold components from storedComponents map.
     * It is used by the warehousing department to remove the sold components from the map in production department.
     *
     * @param component the component
     * @param quantity  the quantity
     */
    public void removeSoldComponents(Component component, int quantity) {
        for (HashMap.Entry<Component, Integer> c : this.storedComponents.entrySet()) {
            if (c.getKey().getComponentCategory() == component.getComponentCategory() && c.getKey().getSupplierCategory() == component.getSupplierCategory()) {
                this.storedComponents.put(c.getKey(), c.getValue() - quantity);
            }
        }
    }

    /**
     * Gets amount in production.
     *
     * @param product the product
     * @return the amount in production
     */
    public double getAmountInProduction(Product product) {
        return this.numberProducedProducts.get(product);
    }

    /**
     * Gets total product costs.
     *
     * @param product the product
     * @return the total product costs
     */
    public double getTotalProductCosts(Product product) {
        this.setProductsTotalProductCost();
        return product.getTotalProductCosts();
    }

    /**
     * Gets total production costs.
     *
     * @return the total production costs
     */
    public double getTotalProductionCosts() {
        this.setProductsTotalProductCost();
        double totalProductionCosts = 0;
        for(HashMap.Entry<Product, Integer> entry : this.numberProducedProducts.entrySet()) {
            totalProductionCosts += entry.getKey().getTotalProductCosts();
        }
        return totalProductionCosts;
    }

    /**
     * Sets product sales price.
     *
     * @param product    the product
     * @param salesPrice the sales price
     */
    public void setProductSalesPrice(Product product, double salesPrice) {
        /* TODO check for DecimalFormat ##,###.00 in GUI */
        if(salesPrice > 0 && salesPrice < 100000) {
            product.setSalesPrice(salesPrice);
        } else {
            // TODO throw error "salesPrice has to between 0.01 and 99,999.99"
        }
    }

    /**
     * Calculate profit margin.
     *
     * @param product the product
     * @return the profit margin
     */
    public double calculateProfitMargin(Product product) {
        return product.calculateProfitMargin();
    }

    /**
     * Calculate all products profit margin.
     */
    public void calculateAllProductsProfitMargin() {
        for(HashMap.Entry<Product, Integer> entry : this.numberProducedProducts.entrySet()) {
            entry.getKey().calculateProfitMargin();
        }
    }

    /**
     * Gets products sales price.
     *
     * @param product the product
     * @return the products sales price
     */
    public double getProductsSalesPrice(Product product) {
        return product.getSalesPrice();
    }

    /**
     * Gets products profit margin.
     *
     * @param product the product
     * @return the products profit margin
     */
    public double getProductsProfitMargin(Product product) {
        return product.calculateProfitMargin();
    }

    /**
     * Calculate production technology factor.
     * Based on report of predecessor group.
     *
     * @return the production technology factor
     */
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

    /**
     * Calculate production technology production technology.
     * Based on report of predecessor group.
     *
     * @return the production technology
     */
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

    /**
     * Calculate quality assurance factor.
     *
     * @return the double
     */
    public double calculateQualityAssuranceFactor() {
        this.qualityAssuranceFactor = 0.95 + 0.05 * this.qualityAssurance.getLevel();
        return this.qualityAssuranceFactor;
    }

    /**
     * Calculate process automation factor double.
     *
     * @return the double
     */
    public double calculateProcessAutomationFactor() {
        this.processAutomationFactor = 0.95 + 0.05 * this.processAutomation.getLevel();
        return this.processAutomationFactor;
    }

    /**
     * Calculate total engineer quality of work double.
     *
     * @return the double
     */
    public double calculateTotalEngineerQualityOfWork() {
        /* TODO placeholder for the quality of work of the engineering team*/
        this.totalEngineerQualityOfWork = 0.7 * (1 - this.totalEngineerQualityOfWorkDecreasePercentage);
        return this.totalEngineerQualityOfWork;
    }

    /**
     * Calculate total engineer productivity double.
     *
     * @return the double
     */
    public double calculateTotalEngineerProductivity() {
        this.totalEngineerProductivity = this.totalEngineerQualityOfWork * this.processAutomationFactor;
        return this.totalEngineerProductivity;
    }

    /**
     * Sets total procurement quality for all launched products.
     */
    public void setTotalProcurementQuality() {
        for (Product product : this.launchedProducts) {
            product.calculateTotalProcurementQuality();
        }
    }

    /**
     * Sets total product quality for all launched products.
     */
    public void setTotalProductQuality() {
        for (Product product : this.launchedProducts) {
            product.calculateTotalProductQuality(this.calculateProductionTechnologyFactor(), this. calculateTotalEngineerProductivity(), this.calculateQualityAssuranceFactor());
        }
    }

    /**
     * Sets total product quality of product.
     *
     * @param product the product
     */
    public void setTotalProductQualityOfProduct(Product product) {
        product.calculateTotalProductQuality(this.calculateProductionTechnologyFactor(), this.calculateTotalEngineerProductivity(), this.calculateQualityAssuranceFactor());
    }

    /**
     * Gets production technology.
     *
     * @return the production technology
     */
    public ProductionTechnology getProductionTechnology() {
        return this.productionTechnology;
    }

    /**
     * Calculate manufacture efficiency.
     * Based on report of predecessor group.
     *
     * @return the manufacture efficiency
     */
    public double calculateManufactureEfficiency() {
        /* use this order to calculate mE -> pPP -> nPPP*/
        this.manufactureEfficiency = 0;
        if (this.monthlyAvailableMachineCapacity != 0) {
            this.manufactureEfficiency = this.numberUnitsProducedPerMonth /this. monthlyAvailableMachineCapacity;
        }
        return this.manufactureEfficiency;
    }

    /**
     * Calculate production process productivity double.
     * Based on report of predecessor group.
     *
     * @return the double
     */
    public double calculateProductionProcessProductivity() {
        this.productionProcessProductivity = (this.calculateProductionTechnology().getRange() + Math.pow(Math.E, Math.log(this.calculateTotalEngineerProductivity())/10)) * this.manufactureEfficiency;
        return this.productionProcessProductivity;
    }

    /**
     * Calculates normalized production process productivity double.
     * Based on report of predecessor group.
     *
     * @return the normalized production process productivity
     */
    public double calculateNormalizedProductionProcessProductivity() {
        this.normalizedProductionProcessProductivity = (this.productionProcessProductivity - 0.2) / (3.2 - 0.2);
        return this.normalizedProductionProcessProductivity;
    }

    /**
     * Invest in system security.
     *
     * @param level    the level
     * @param gameDate the game date
     * @return the costs of the investment
     */
    public double investInSystemSecurity(int level, LocalDate gameDate) {
        this.systemSecurity = systemSecurity.invest(level, gameDate);
        return 5000 * level;
    }

    /**
     * Invest in quality assurance.
     *
     * @param level    the level
     * @param gameDate the game date
     * @return the cost of the investment
     */
    public double investInQualityAssurance(int level, LocalDate gameDate) {
        this.qualityAssurance = qualityAssurance.invest(level, gameDate);
        return 5000 * level;
    }

    /**
     * Invest in process automation.
     *
     * @param level    the level
     * @param gameDate the game date
     * @return the costs of the investment
     */
    public double investInProcessAutomation(int level, LocalDate gameDate) {
        this.processAutomation = processAutomation.invest(level, gameDate);
        return 5000 * level;
    }

    /**
     * Depreciate product investments.
     *
     * @param gameDate the game date
     */
    public void depreciateProductInvestment(LocalDate gameDate) {
        this.systemSecurity = this.systemSecurity.updateInvestment(gameDate);
        this.qualityAssurance = this.qualityAssurance.updateInvestment(gameDate);
        this.processAutomation = this.processAutomation.updateInvestment(gameDate);
    }

    /**
     * Gets number of produced products of this day.
     *
     * @return the number of produced products of this day
     */
    public Map<Product, Integer> getNumberProducedProducts() {
        return this.numberProducedProducts;
    }

    /**
     * Clears inventory of the production department (all produced products).
     */
    public void clearInventory() {
        this.numberProducedProducts.clear();
    }

    /**
     * Calculate average product base quality of products.
     *
     * @return the average product base quality
     */
    public double calculateAverageProductBaseQuality() {
        this.averageProductBaseQuality = 0;
        for (HashMap.Entry<Product, Integer> entry : this.numberProducedProducts.entrySet()) {
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

    /**
     * Decreases total engineer quality of work rel.
     *
     * @param decrease the decrease
     */
    public void decreaseTotalEngineerQualityOfWorkRel(double decrease) {
        this.totalEngineerQualityOfWorkDecreasePercentage = decrease;
        this.calculateTotalEngineerQualityOfWork();
    }

    /**
     * Increases total engineer quality of work rel.
     */
    public void increaseTotalEngineerQualityOfWorkRel() {
        this.totalEngineerQualityOfWorkDecreasePercentage = 0;
        this.calculateTotalEngineerQualityOfWork();
    }

    /**
     * Decreases process automation rel.
     * Used processAutomationFactor as processAutomation is on a Likert scale from 1 to 5
     * @param decrease the decrease
     */
    public void decreaseProcessAutomationRel(double decrease) {
        int levelDecrease = (int) Math.round(this.processAutomation.getLevel() * (1 - decrease));
        this.processAutomation.decreaseLevel(levelDecrease);
        this.decreasedProcessAutomationLevel = levelDecrease;
    }

    /**
     * Needed for {@link}ExternalEvent "ComputerVirusAttack" and its  reversing press release
     *
     * @param increase the increase
     */
    public void increaseProcessAutomationRel(double increase) {
        int levelIncrease = (int) Math.round(this.processAutomation.getLevel() * increase);
        this.processAutomation.increaseLevel(levelIncrease);
    }

    /**
     * Increases process automation rel.
     */
    public void increaseProcessAutomationRel() {
        this.processAutomation.increaseLevel(this.decreasedProcessAutomationLevel);
    }

    /**
     * Checks base quality above threshold boolean.
     *
     * @return the whether average base quality higher or equal than 80
     */
    public boolean checkBaseQualityAboveThreshold() {
        return calculateAverageProductBaseQuality() >= 80;
    }

    /**
     * Checks production technology below threshold boolean.
     *
     * @return whether production technology is below 2
     */
    public boolean checkProductionTechnologyBelowThreshold() {
        return this.calculateProductionTechnology().getRange() < 2;
    }

    /**
     * Calculate everything needed for this day.
     *
     * @param gameDate the game date
     */
    public void calculateAll(LocalDate gameDate) {
        this.getAllAvailableComponents(gameDate);
        // this.updateComponentBaseCosts(gameDate);
        this.depreciateProductInvestment(gameDate);
        this.depreciateMachinery(false, gameDate);
        this.calculateMachineryResellPrices();
        this.calculateProductionTechnologyFactor();
        this.calculateQualityAssuranceFactor();
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

    /**
     * Gets stored components.
     *
     * @return the stored components
     */
    public Map<Component, Integer> getStoredComponents() {
        return this.storedComponents;
    }

    /**
     * Gets daily machine capacity.
     *
     * @return the daily machine capacity
     */
    public int getDailyMachineCapacity() {
        int capacity = 0;
        for(Machinery machinery : this.machines) {
            capacity += machinery.getMachineryCapacity();
        }
        return  capacity;
    }

    /**
     * Gets monthly machine capacity.
     * Multiplicates the daily machine capacity by the number of days of the current month
     *
     * @param gameDate the game date
     * @return the monthly machine capacity
     */
    public int getMonthlyMachineCapacity(LocalDate gameDate) {
        int dailyCapacity = this.getDailyMachineCapacity();
        int numberOfDaysInMonth = gameDate.lengthOfMonth();
        return numberOfDaysInMonth * dailyCapacity;
    }

    /**
     * Gets launched products.
     *
     * @return the launched products
     */
    public synchronized List<Product> getLaunchedProducts() {
        return this.launchedProducts;
    }

    /**
     * Gets production technology factor.
     *
     * @return the production technology factor
     */
    public double getProductionTechnologyFactor() {
        return this.productionTechnologyFactor;
    }

    /**
     * Gets quality assurance factor.
     *
     * @return the quality assurance factor
     */
    public double getQualityAssuranceFactor() {
        return this.qualityAssuranceFactor;
    }

    /**
     * Gets quality assurance.
     *
     * @return the quality assurance
     */
    public ProductionInvestment getQualityAssurance() {
        return this.qualityAssurance;
    }

    /**
     * Gets process automation factor.
     *
     * @return the process automation factor
     */
    public double getProcessAutomationFactor() {
        return this.processAutomationFactor;
    }

    /**
     * Gets process automation.
     *
     * @return the process automation
     */
    public ProductionInvestment getProcessAutomation() {
        return this.processAutomation;
    }

    /**
     * Gets total engineer quality of work.
     *
     * @return the total engineer quality of work
     */
    public double getTotalEngineerQualityOfWork() {
        return this.totalEngineerQualityOfWork;
    }

    /**
     * Gets total engineer productivity.
     *
     * @return the total engineer productivity
     */
    public double getTotalEngineerProductivity() {
        return this.totalEngineerProductivity;
    }

    /**
     * Gets system security.
     *
     * @return the system security
     */
    public ProductionInvestment getSystemSecurity() {
        return this.systemSecurity;
    }

    /**
     * Gets production variable costs.
     *
     * @return the production variable costs
     */
    public double getProductionVariableCosts() {
        return this.productionVariableCosts;
    }

    /**
     * Gets production fix costs.
     *
     * @return the production fix costs
     */
    public double getProductionFixCosts() {
        return this.productionFixCosts;
    }

    /**
     * Gets number units produced of this month.
     *
     * @return the number units produced of this month
     */
    public int getNumberUnitsProducedPerMonth() {
        return this.numberUnitsProducedPerMonth;
    }

    /**
     * Gets monthly available machine capacity.
     *
     * @return the monthly available machine capacity
     */
    public double getMonthlyAvailableMachineCapacity() {
        return this.monthlyAvailableMachineCapacity;
    }

    /**
     * Gets manufacture efficiency.
     *
     * @return the manufacture efficiency
     */
    public double getManufactureEfficiency() {
        return this.manufactureEfficiency;
    }

    /**
     * Gets production process productivity.
     *
     * @return the production process productivity
     */
    public double getProductionProcessProductivity() {
        return this.productionProcessProductivity;
    }

    /**
     * Gets normalized production process productivity.
     *
     * @return the normalized production process productivity
     */
    public double getNormalizedProductionProcessProductivity() {
        return this.normalizedProductionProcessProductivity;
    }

    /**
     * Gets average product base quality.
     *
     * @return the average product base quality
     */
    public double getAverageProductBaseQuality() {
        return this.averageProductBaseQuality;
    }

    /**
     * Gets machinery purchase price.
     *
     * @param gameDate the game date
     * @return a machinery purchase price
     */
    public double getMachineryPurchasePrice(LocalDate gameDate) {
        Machinery machinery = new Machinery(gameDate);
        return machinery.calculatePurchasePrice();
    }

    /**
     * Gets production slots.
     *
     * @return the production slots
     */
    public int getProductionSlots() {
        return productionSlots;
    }

    /**
     * Gets machine slots available.
     *
     * @return the machine slots available
     */
    public boolean getMachineSlotsAvailable() {
        this.machineSlotsAvailable = this.productionSlots > this.machines.size();
        return this.machineSlotsAvailable;
    }

    /**
     * Sets number of production workers.
     *
     * @param numberOfProductionWorkers the number of production workers
     */
    public void setNumberOfProductionWorkers(int numberOfProductionWorkers) {
        this.numberOfProductionWorkers = numberOfProductionWorkers;
    }

    /**
     * Gets launched products change.
     *
     * @return the launched products change
     */
    public synchronized PropertyChangeSupportList<Product> getLaunchedProductsChange() {
        return launchedProductsChange;
    }

    @Override
    public void registerPropertyChangeListener(PropertyChangeListener listener) {
        this.launchedProductsChange.addPropertyChangeListener(listener);
    }
}