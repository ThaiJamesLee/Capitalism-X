package de.uni.mannheim.capitalismx.department;

import de.uni.mannheim.capitalismx.domain.department.DepartmentImpl;
import de.uni.mannheim.capitalismx.domain.department.DepartmentSkill;
import de.uni.mannheim.capitalismx.domain.department.LevelingMechanism;
import de.uni.mannheim.capitalismx.domain.exception.InconsistentLevelException;
import de.uni.mannheim.capitalismx.domain.exception.LevelingRequirementNotFulFilledException;
import de.uni.mannheim.capitalismx.procurement.component.Component;
import de.uni.mannheim.capitalismx.procurement.department.ProcurementDepartment;
import de.uni.mannheim.capitalismx.procurement.component.Unit;
import de.uni.mannheim.capitalismx.procurement.component.UnitType;
import de.uni.mannheim.capitalismx.production.product.Product;
import de.uni.mannheim.capitalismx.production.department.ProductionDepartment;
import de.uni.mannheim.capitalismx.utils.data.PropertyChangeSupportInteger;
import de.uni.mannheim.capitalismx.utils.data.PropertyChangeSupportMap;
import de.uni.mannheim.capitalismx.warehouse.Warehouse;
import de.uni.mannheim.capitalismx.warehouse.WarehouseType;
import de.uni.mannheim.capitalismx.warehouse.exceptions.NoWarehouseSlotsAvailableException;
import de.uni.mannheim.capitalismx.warehouse.exceptions.StorageCapacityUsedException;
import de.uni.mannheim.capitalismx.warehouse.skill.WarehouseSkill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class represents the warehousing department.
 * It is used to store components and products and to sell those components or products.
 *
 * @author dzhao
 */
public class WarehousingDepartment extends DepartmentImpl {

    private static WarehousingDepartment instance;
    private List<Warehouse> warehouses;
    private Map<Unit, Integer> inventory;
    private int totalCapacity;
    private int freeStorage;
    private int storedUnits;

    /**
     * Incorporates fix costs and rental costs of warehouses.
     */
    private double monthlyCostWarehousing;

    /**
     * Daily costs for storage of units.
     */
    private double dailyStorageCost;

    /**
     * Accumulation of daily costs of units over a month.
     */
    private double monthlyStorageCost;

    /**
     * Combines the monthlyStorageCost with the monthlyCostWarehousing
     */
    private double monthlyTotalCostWarehousing;

    /**
     * Days since the free storage threshold was met
     */
    private int daysSinceFreeStorageThreshold;

    /**
     * The storage cost of a single unit.
     */
    private static final double VARIABLE_STORAGE_COST = 0.05;

    /**
     * Flag whether there are any more warehouse slots available for additional warehouses.
     */
    private boolean warehouseSlotsAvailable;

    /**
     * PropertyChangeSupportMap that is used to notify the GUI when there is a change in the inventory.
     */
    private PropertyChangeSupportMap<Unit, Integer> inventoryChange;

    /**
     * PropertyChangeSupportInteger that is used to notify the GUI when there is a change in the free storage.
     */
    private PropertyChangeSupportInteger freeStorageChange;

    private static final Logger logger = LoggerFactory.getLogger(WarehousingDepartment.class);

    /**
     * Defines how many warehouses the warehousing department can have.
     */
    private int warehouseSlots;

    /**
     * Initial value for number of warehouses possible.
     */
    private int initialWarehouseSlots;

    private static final String LEVELING_PROPERTIES = "warehouse-leveling-definition";
    private static final String MAX_LEVEL_PROPERTY = "warehouse.department.max.level";
    private static final String INITIAL_SLOTS_PROPERTY = "warehouse.department.init.slots";

    private static final String SKILL_COST_PROPERTY_PREFIX = "warehouse.skill.cost.";
    private static final String SKILL_SLOTS_PREFIX = "warehouse.skill.slots.";


    /**
     * Constructor of warehousing department.
     * Initializes all variables, including the PropertyChangeSupport variables.
     */
    private WarehousingDepartment() {
        super("Warehousing");

        this.warehouses = new ArrayList<>();
        this.inventory = new ConcurrentHashMap<>();
        this.totalCapacity = 0;
        this.freeStorage = 0;
        this.storedUnits = 0;
        this.monthlyCostWarehousing = 0;
        this.dailyStorageCost = 0;
        this.monthlyStorageCost = 0;
        this.monthlyTotalCostWarehousing = 0;
        this.daysSinceFreeStorageThreshold = 0;
        this.warehouseSlotsAvailable = true;

        this.inventoryChange = new PropertyChangeSupportMap();
        this.inventoryChange.setMap(this.inventory);
        this.inventoryChange.setAddPropertyName("inventoryChange");
        this.freeStorageChange = new PropertyChangeSupportInteger();
        this.freeStorageChange.setValue(this.freeStorage);
        this.freeStorageChange.setPropertyChangedName("freeStorageChange");

        this.init();
    }

    /**
     * Gets singleton instance.
     *
     * @return the instance
     */
    public static synchronized WarehousingDepartment getInstance() {
        if (WarehousingDepartment.instance == null) {
            WarehousingDepartment.instance = new WarehousingDepartment();
        }
        return WarehousingDepartment.instance;
    }

    /**
     * Initializes properties and skills of warehousing department.
     */
    private void init() {
        this.initProperties();
        this.initSkills();
    }

    /**
     * Initializes the properties of the department.
     */
    private void initProperties() {
        setMaxLevel(Integer.parseInt(ResourceBundle.getBundle(LEVELING_PROPERTIES).getString(MAX_LEVEL_PROPERTY)));
        this.initialWarehouseSlots = Integer.parseInt(ResourceBundle.getBundle(LEVELING_PROPERTIES).getString(INITIAL_SLOTS_PROPERTY));
        this.warehouseSlots = this.initialWarehouseSlots;
    }

    /**
     * Initializes the skills of the department
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
            skillMap.put(i, new WarehouseSkill(i, slots));
        }
    }

    /**
     * Initializes the cost map of the skills.
     *
     * @return cost map of the skills
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
     * Update the unlocked warehouse slots and set the warehouse slots available flag to true.
     */
    private void updateWarehouseSlots() {
        int numberOfSlots = this.initialWarehouseSlots;
        List<DepartmentSkill> availableSkills = getAvailableSkills();

        for (DepartmentSkill skill : availableSkills) {
            numberOfSlots += ((WarehouseSkill) skill).getNewWarehouseSlots();
        }
        this.warehouseSlots = numberOfSlots;
        this.warehouseSlotsAvailable = true;
    }

    @Override
    public void setLevel(int level) throws LevelingRequirementNotFulFilledException {
        super.setLevel(level);
        this.updateWarehouseSlots();
    }


    /**
     * Store units into the inventory.
     * It stores the newly produced products of the production department and gets the new quantity of components from the
     * production department.
     */
    public void storeUnits() {
        Map<Product, Integer> newProducts = ProductionDepartment.getInstance().getNumberProducedProducts();
        for (HashMap.Entry<Product, Integer> entry : newProducts.entrySet()) {
            if (this.inventory.get(entry.getKey()) != null) {
                int aggregatedUnits = this.inventory.get(entry.getKey()) + entry.getValue();
                this.inventoryChange.putOne(entry.getKey(), aggregatedUnits);
            } else {
                this.inventoryChange.putOne(entry.getKey(), entry.getValue());
            }
        }
        ProductionDepartment.getInstance().clearInventory();
        Map<Component, Integer> newComponents = ProcurementDepartment.getInstance().getReceivedComponents();
        for (HashMap.Entry<Component, Integer> entry : newComponents.entrySet()) {
            if (this.inventory.get(entry.getKey()) != null) {
                int aggregatedUnits = this.inventory.get(entry.getKey()) + entry.getValue();
                this.inventoryChange.putOne(entry.getKey(), aggregatedUnits);
            } else {
                this.inventoryChange.putOne(entry.getKey(), entry.getValue());
            }
        }
        ProcurementDepartment.getInstance().clearReceivedComponents();
    }

    /**
     * Calculates number of stored units.
     *
     * @return number of stored units.
     */
    public int calculateStoredUnits() {
        this.storedUnits = 0;
        for(HashMap.Entry<Unit, Integer> entry : this.inventory.entrySet()) {
            this.storedUnits += entry.getValue();
        }
        return this.storedUnits;
    }

    /**
     * Calculate total capacity of warehousing department.
     *
     * @return the total capacity
     */
    public int calculateTotalCapacity() {
        this.totalCapacity = 0;
        for (Warehouse warehouse : this.warehouses) {
            this.totalCapacity += warehouse.getCapacity();
        }
        return totalCapacity;
    }

    /**
     * Calculates free storage of warehousing department.
     *
     * @return the free storage
     */
    /* */
    public int calculateFreeStorage() {
        this.freeStorage = this.calculateTotalCapacity() - this.calculateStoredUnits();
        this.freeStorageChange.setValue(this.freeStorage);
        return this.freeStorage;
    }


    /**
     * Sell products.
     * Used to fulfill contracts.
     *
     * @param soldProduct the sold product
     * @return the revenue of the products
     */
    public double sellProduct (HashMap.Entry<Unit, Integer> soldProduct) {
        if (this.inventory.get(soldProduct.getKey()) != null && this.inventory.get(soldProduct.getKey()) >= soldProduct.getValue()) {
            int newInventoryUnits = this.inventory.get(soldProduct.getKey()) - soldProduct.getValue();
            this.inventoryChange.putOne(soldProduct.getKey(), newInventoryUnits);
        }
        return soldProduct.getKey().getSalesPrice() * soldProduct.getValue();
    }

    /**
     * Builds warehouse.
     * Adds warehouse to the list of warehouses and add its relevant properties to the warehousing department.
     * Checks whether there are still warehouse slots available before building one.
     *
     * @param gameDate the game date
     * @return the costs of building a warehouse
     * @throws NoWarehouseSlotsAvailableException the no warehouse slots available exception
     */
    public double buildWarehouse(LocalDate gameDate) throws NoWarehouseSlotsAvailableException {
        if (this.warehouseSlots > this.warehouses.size()) {
            Warehouse warehouse = new Warehouse(WarehouseType.BUILT);
            warehouse.setBuildDate(gameDate);
            warehouses.add(warehouse);
            this.calculateMonthlyCostWarehousing();
            if (this.warehouseSlots == this.warehouses.size()) {
                this.warehouseSlotsAvailable = false;
            }
            this.calculateTotalCapacity();
            this.calculateFreeStorage();
            return warehouse.getBuildingCost();
        }
        this.warehouseSlotsAvailable = false;
        throw new NoWarehouseSlotsAvailableException("No more Capacity available to build or rent a new Warehouse.");
    }

    /**
     * Rents warehouse.
     * Adds warehouse to the list of warehouses and add its relevant properties to the warehousing department.
     * Checks whether there are still warehouse slots available before building one.
     *
     * @param gameDate the game date
     * @return the cost of renting a warehouse
     * @throws NoWarehouseSlotsAvailableException the no warehouse slots available exception
     */
    public double rentWarehouse(LocalDate gameDate) throws NoWarehouseSlotsAvailableException{
        if (this.warehouseSlots > this.warehouses.size()) {
            Warehouse warehouse = new Warehouse(WarehouseType.RENTED);
            warehouses.add(warehouse);
            this.calculateMonthlyCostWarehousing();
            if (this.warehouseSlots == this.warehouses.size()) {
                this.warehouseSlotsAvailable = false;
            }
            this.calculateTotalCapacity();
            this.calculateFreeStorage();
            return warehouse.getMonthlyRentalCost();
        }
        this.warehouseSlotsAvailable = false;
        throw new NoWarehouseSlotsAvailableException("No more Capacity available to build or rent a new Warehouse.");
    }

    /**
     * Depreciate all warehouse resale values.
     *
     * @param gameDate the game date
     */
    public void depreciateAllWarehouseResaleValues(LocalDate gameDate) {
        for (Warehouse warehouse : this.warehouses) {
            if (warehouse.getWarehouseType() == WarehouseType.BUILT) {
                warehouse.depreciateWarehouseResaleValue(gameDate);
            }
        }
    }

    /**
     * Gets warehouse resale value.
     *
     * @param warehouse the warehouse
     * @return the warehouse resale value
     */
    public double getWarehouseResaleValue(Warehouse warehouse) {
        return warehouse.getResaleValue();
    }

    /**
     * Gets all warehouse resale values.
     *
     * @return the all warehouse resale values in map
     */
    public Map<Warehouse, Double> getAllWarehouseResaleValues() {
        Map<Warehouse, Double> allWarehouseResaleValues = new HashMap<>();
        for (Warehouse warehouse : this.warehouses) {
            allWarehouseResaleValues.put(warehouse, warehouse.getResaleValue());
        }
        return allWarehouseResaleValues;
    }

    /**
     * Sells warehouse.
     * Removes warehouse from list of warehouses and remove associated numbers.
     * Check whether there is enough free storage to sell a warehouse. Otherwise throw an exception.
     *
     * @param warehouse the warehouse
     * @return the resale value of the warehouse
     * @throws StorageCapacityUsedException the storage capacity used exception
     */
    public double sellWarehouse(Warehouse warehouse) throws StorageCapacityUsedException {
        int fS = this.calculateFreeStorage();
        int cap = warehouse.getCapacity();
        if (fS >= cap) {
            double resaleValue = 0;
            if (warehouse.getWarehouseType() == WarehouseType.BUILT) {
                resaleValue = warehouse.getResaleValue();
            }
            warehouses.remove(warehouse);
            this.warehouseSlotsAvailable = true;
            return resaleValue;
        } else {
            if (warehouse.getWarehouseType() == WarehouseType.BUILT) {
               throw new StorageCapacityUsedException("This warehouse cannot be sold, the storage capacity is still in use.", fS, cap);
            } else {
                throw new StorageCapacityUsedException("The rent of this warehouse cannot be canceled, the storage capacity is still in use.", fS, cap);
            }
        }
    }

    /**
     * Calculates monthly cost warehousing double.
     * It adds fix costs and rental costs of warehouses
     *
     * @return monthly cost warehousing
     */
    public double calculateMonthlyCostWarehousing() {
        double fixCostWarehouses = 0;
        double rentalCostsWarehouses = 0;
        for (Warehouse warehouse : this.warehouses) {
            fixCostWarehouses += warehouse.getMonthlyFixCostWarehouse();
            rentalCostsWarehouses += warehouse.getMonthlyRentalCost();
        }
        this.monthlyCostWarehousing = fixCostWarehouses + rentalCostsWarehouses;
        return this.monthlyCostWarehousing;
    }

    /**
     * Calculates daily storage cost.
     * Multiplicates the variable storage cost with the number of stored units.
     * Adds the daily storage cost to the monthly storage cost.
     *
     * @return the daily storage cost
     */
    public double calculateDailyStorageCost() {

        this.dailyStorageCost = VARIABLE_STORAGE_COST * this.calculateStoredUnits();
        this.monthlyStorageCost += this.dailyStorageCost;
        return this.dailyStorageCost;
    }

    /**
     * Reset monthly storage cost.
     */
    public void resetMonthlyStorageCost() {
        this.monthlyStorageCost = 0;
    }

    /**
     * Calculate total monthly warehousing cost.
     *
     * @return the double
     */
    public double calculateTotalMonthlyWarehousingCost() {
        this.monthlyTotalCostWarehousing =  this.monthlyCostWarehousing + this.monthlyStorageCost;
        //this.resetMonthlyStorageCost();
        return this.monthlyTotalCostWarehousing;
    }

    /**
     * Approximates the monthly warehouse costs.
     * Adds monthlyCostWarehousing to the dailyStorageCost times the days of the month.
     *
     * @param gameDate the game date
     * @return the monthly warehouse cost
     */
    public double getMonthlyWarehouseCost(LocalDate gameDate) {
        double monthlyCostWarehousing = this.calculateMonthlyCostWarehousing();
        double dailyStorageCost = VARIABLE_STORAGE_COST * this.calculateStoredUnits();
        return monthlyCostWarehousing + dailyStorageCost * gameDate.lengthOfMonth();
    }

    /**
     * Gets free storage.
     *
     * @return the free storage
     */
    public int getFreeStorage() {
        return this.freeStorage;
    }

    /**
     * Checks free storage threshold.
     * Adds up the days since the free storage threshold was met.
     *
     * @return true if free storage divided by total capacity is smaller than 0.1, otherwise return false
     */
    public boolean checkFreeStorageThreshold() {
        if (this.calculateTotalCapacity() == 0) {
            return false;
        }
        double percentage = (double) this.calculateFreeStorage() / (double) this.calculateTotalCapacity();
        if ((percentage) < 0.1) {
            this.daysSinceFreeStorageThreshold++;
            return true;
        } else {
            this.daysSinceFreeStorageThreshold = 0;
            return false;
        }
    }

    /**
     * Gets days since free storage threshold.
     *
     * @return the days since free storage threshold
     */
    public int getDaysSinceFreeStorageThreshold() {
        return this.daysSinceFreeStorageThreshold;
    }

    /**
     * Gets warehouses.
     *
     * @return the warehouses
     */
    public List<Warehouse> getWarehouses() {
        return this.warehouses;
    }

    /**
     * Decrease stored units rel.
     *
     * @param percentage the percentage
     */
    public void decreaseStoredUnitsRel(double percentage) {
        for (HashMap.Entry<Unit, Integer> entry : this.inventory.entrySet()) {
            this.inventoryChange.putOne(entry.getKey(), (int) (entry.getValue() * 0.7));
        }
    }

    /**
     * Checks warehouse capacity threshold.
     *
     * @return true if stored units divided by total capacity is bigger or equals 0.8, otherwise return false.
     */
    public boolean checkWarehouseCapacityThreshold() {
        if (this.totalCapacity == 0) {
            return false;
        }
        return (this.calculateStoredUnits() / this.calculateTotalCapacity()) >= 0.8;
    }

    /**
     * Decreases capacity of a warehouse by a set amount.
     *
     * @param capacity the capacity
     */
    public void decreaseCapacity(int capacity) {
        Warehouse damagedWarehouse = this.warehouses.get(0);
        damagedWarehouse.setCapacity(damagedWarehouse.getCapacity() - capacity);
    }


    /**
     * Sell products double.
     *
     * @param sales the sales
     * @return the double
     */
    public double sellProducts(Map<Unit, Integer> sales) {
        double earnedMoney = 0;
        for (Map.Entry<Unit, Integer> entry : this.inventory.entrySet()) {
            if (entry.getKey().getUnitType() == UnitType.PRODUCT_UNIT) {
                this.inventoryChange.putOne(entry.getKey(), entry.getValue() - sales.get(entry.getKey()));
                earnedMoney += entry.getKey().getSalesPrice() * sales.get(entry.getKey());
            }
        }
        return earnedMoney;
    }

    /**
     * Sells products in the warehouse.
     * Removes sold products from stored units of production department.
     *
     * @param unit     the unit
     * @param quantity the quantity
     * @return revenue made by those sales
     */
    public double sellWarehouseProducts(Unit unit, int quantity) {
        for (HashMap.Entry<Unit, Integer> entry : this.inventory.entrySet()) {
            if (entry.getKey().getUnitType() == UnitType.PRODUCT_UNIT && unit.getUnitType() == UnitType.PRODUCT_UNIT) {
                Product productEntry = (Product) entry.getKey();
                Product productUnit = (Product) unit;
                if (productEntry == productUnit && entry.getValue() >= quantity) {
                    this.inventoryChange.putOne(entry.getKey(), entry.getValue() - quantity);
                    return quantity * productEntry.getWarehouseSalesPrice();
                }
            }
        }
        return 0;
    }

    /**
     * Sell components in warehouse.
     * Removes sold components from stored units of production department.
     *
     * @param unit     the unit
     * @param quantity the quantity
     * @return revenue made by those sales
     */
    public double sellWarehouseComponents(Unit unit, int quantity) {
        for (HashMap.Entry<Unit, Integer> entry : this.inventory.entrySet()) {
            if (entry.getKey().getUnitType() == UnitType.COMPONENT_UNIT && unit.getUnitType() == UnitType.COMPONENT_UNIT) {
                Component componentEntry = (Component) entry.getKey();
                Component componentUnit = (Component) unit;
                if (componentEntry.sameComponent(componentUnit) && entry.getValue() >= quantity) {
                    this.inventoryChange.putOne(entry.getKey(), entry.getValue() - quantity);
                    ProductionDepartment.getInstance().removeSoldComponents(componentEntry, quantity);
                    return quantity * componentEntry.getWarehouseSalesPrice();
                }
            }
        }
        return 0;
    }

    /**
     * Sets stored components in the production department.
     */
    public void setProductionDepartmentStoredComponents() {
        Map<Component, Integer> storedComponents = new HashMap<>();
        for (HashMap.Entry<Unit, Integer> entry : this.inventory.entrySet()) {
            if (entry.getKey().getUnitType() == UnitType.COMPONENT_UNIT) {
                storedComponents.put((Component) entry.getKey(), entry.getValue());
            }
        }
        ProductionDepartment.getInstance().setStoredComponents(storedComponents);
    }

    /**
     * Sets production department total warehouse capacity.
     */
    public void setProductionDepartmentTotalWarehouseCapacity() {
        ProductionDepartment.getInstance().setTotalWarehouseCapacity(this.totalCapacity);
    }

    /**
     * Clear used components of the production department from the inventory.
     */
    public void clearUsedComponents() {
        for (Map.Entry<Unit, Integer> unit : this.inventory.entrySet()) {
            Map<Component, Integer> storedComponents = ProductionDepartment.getInstance().getStoredComponents();
            if (unit.getKey().getUnitType() == UnitType.COMPONENT_UNIT) {
                Component component = (Component) unit.getKey();
                for (Map.Entry<Component, Integer> entry : storedComponents.entrySet()) {
                    if (component.sameComponent(entry.getKey())) {
                        this.inventoryChange.putOne(unit.getKey(), entry.getValue());
                    }
                }
            }
        }
    }

    /**
     * Calculates all calculations of the day.
     *
     * @param gameDate the game date
     */
    public void calculateAll(LocalDate gameDate) {
        this.clearUsedComponents();
        this.storeUnits();
        this.setProductionDepartmentStoredComponents();
        this.calculateStoredUnits();
        this.calculateTotalCapacity();
        this.calculateFreeStorage();
        this.setProductionDepartmentTotalWarehouseCapacity();
        this.depreciateAllWarehouseResaleValues(gameDate);
        this.calculateDailyStorageCost();
        this.calculateMonthlyCostWarehousing();
        this.calculateTotalMonthlyWarehousingCost();
    }

    /**
     * Gets inventory.
     *
     * @return the inventory
     */
    public Map<Unit, Integer> getInventory() {
        return this.inventory;
    }

    /**
     * Returns the amount stored in the Warehouse for the given {@link Unit}.
     *
     * @param unit The unit to check the warehouse for.
     * @return The amount stored.
     */
    public int getAmountStored(Unit unit) {
		if (unit.getUnitType() == UnitType.COMPONENT_UNIT) {
            Component component = (Component) unit;
            for (Map.Entry<Unit, Integer> entry : this.inventory.entrySet()) {
                if (entry.getKey().getUnitType() == UnitType.COMPONENT_UNIT) {
                    Component componentEntry = (Component) entry.getKey();
                    if (component.getComponentCategory() == componentEntry.getComponentCategory() && component.getSupplierCategory() == componentEntry.getSupplierCategory()) {
                        return entry.getValue();
                    }
                }
            }
        }
        if (!this.inventory.containsKey(unit)) {
            return 0;
        }
		return this.inventory.get(unit);
	}

    /**
     * Gets warehouse slots.
     *
     * @return the warehouse slots
     */
    public int getWarehouseSlots() {
	    return this.warehouseSlots;
    }

    /**
     * Gets warehouse slots available.
     *
     * @return the warehouse slots available
     */
    public boolean getWarehouseSlotsAvailable() {
	    this.warehouseSlotsAvailable = this.warehouseSlots > this.warehouses.size();
	    return this.warehouseSlotsAvailable;
    }

    /**
     * Gets total capacity.
     *
     * @return the total capacity
     */
    public int getTotalCapacity() {
        return this.totalCapacity;
    }

    /**
     * Gets stored units.
     *
     * @return the stored units
     */
    public int getStoredUnits() {
        return this.storedUnits;
    }

    /**
     * Gets monthly cost warehousing.
     *
     * @return the monthly cost warehousing
     */
    public double getMonthlyCostWarehousing() {
        return this.monthlyCostWarehousing;
    }

    /**
     * Gets daily storage cost.
     *
     * @return the daily storage cost
     */
    public double getDailyStorageCost() {
        return this.dailyStorageCost;
    }

    /**
     * Gets monthly storage cost.
     *
     * @return the monthly storage cost
     */
    public double getMonthlyStorageCost() {
        return this.monthlyStorageCost;
    }

    /**
     * Gets monthly total cost warehousing.
     *
     * @return the monthly total cost warehousing
     */
    public double getMonthlyTotalCostWarehousing() {
        return this.monthlyTotalCostWarehousing;
    }

    /**
     * Create instance warehousing department.
     *
     * @return the warehousing department
     */
    public static WarehousingDepartment createInstance() {
	    return new WarehousingDepartment();
    }

    /**
     * Sets instance.
     *
     * @param instance the instance
     */
    public static void setInstance(WarehousingDepartment instance) {
        WarehousingDepartment.instance = instance;
    }

    @Override
    public void registerPropertyChangeListener(PropertyChangeListener listener) {
        this.inventoryChange.addPropertyChangeListener(listener);
        this.freeStorageChange.addPropertyChangeListener(listener);
    }
}
