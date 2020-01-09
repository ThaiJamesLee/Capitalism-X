package de.uni.mannheim.capitalismx.warehouse;

import de.uni.mannheim.capitalismx.domain.department.DepartmentImpl;
import de.uni.mannheim.capitalismx.domain.department.DepartmentSkill;
import de.uni.mannheim.capitalismx.domain.department.LevelingMechanism;
import de.uni.mannheim.capitalismx.domain.exception.InconsistentLevelException;
import de.uni.mannheim.capitalismx.procurement.component.Component;
import de.uni.mannheim.capitalismx.procurement.component.ProcurementDepartment;
import de.uni.mannheim.capitalismx.procurement.component.Unit;
import de.uni.mannheim.capitalismx.procurement.component.UnitType;
import de.uni.mannheim.capitalismx.production.Product;
import de.uni.mannheim.capitalismx.production.ProductionDepartment;
import de.uni.mannheim.capitalismx.utils.data.PropertyChangeSupportMap;
import de.uni.mannheim.capitalismx.warehouse.skill.WarehouseSkill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.util.*;

public class WarehousingDepartment extends DepartmentImpl {

    private static WarehousingDepartment instance;
    private List<Warehouse> warehouses;
    private Map<Unit, Integer> inventory;
    private int totalCapacity;
    private int freeStorage;
    private int storedUnits;
    private double monthlyCostWarehousing;
    private double dailyStorageCost;
    private double monthlyStorageCost;
    private double monthlyTotalCostWarehousing;
    private int daysSinceFreeStorageThreshold;
    private boolean warehouseSlotsAvailable;

    private PropertyChangeSupportMap inventoryChange;

    private static final Logger logger = LoggerFactory.getLogger(WarehousingDepartment.class);

    private int baseCost;
    private int warehouseSlots;
    private int initialWarehouseSlots;

    private static final String LEVELING_PROPERTIES = "warehouse-leveling-definition";
    private static final String MAX_LEVEL_PROPERTY = "warehouse.department.max.level";
    private static final String INITIAL_SLOTS_PROPERTY = "warehouse.department.init.slots";

    private static final String SKILL_COST_PROPERTY_PREFIX = "warehouse.skill.cost.";
    private static final String SKILL_SLOTS_PREFIX = "warehouse.skill.slots.";


    private WarehousingDepartment() {
        super("Warehousing");

        this.warehouses = new ArrayList<>();
        this.inventory = new HashMap<>();
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

        this.init();
    }

    public static synchronized WarehousingDepartment getInstance() {
        if(WarehousingDepartment.instance == null) {
            WarehousingDepartment.instance = new WarehousingDepartment();
        }
        return WarehousingDepartment.instance;
    }

    private void init() {
        this.initProperties();
        this.initSkills();
    }

    private void initProperties() {
        setMaxLevel(Integer.parseInt(ResourceBundle.getBundle(LEVELING_PROPERTIES).getString(MAX_LEVEL_PROPERTY)));
        this.initialWarehouseSlots = Integer.parseInt(ResourceBundle.getBundle(LEVELING_PROPERTIES).getString(INITIAL_SLOTS_PROPERTY));
        this.warehouseSlots = this.initialWarehouseSlots;
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
            skillMap.put(i, new WarehouseSkill(i, slots));
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

    private void updateWarehouseSlots() {
        int numberOfSlots = this.initialWarehouseSlots;
        List<DepartmentSkill> availableSkills = getAvailableSkills();

        for(DepartmentSkill skill : availableSkills) {
            numberOfSlots += ((WarehouseSkill) skill).getNewWarehouseSlots();
        }
        this.warehouseSlots = numberOfSlots;
        this.warehouseSlotsAvailable = true;
    }

    public void setLevel(int level) {
        super.setLevel(level);
        this.updateWarehouseSlots();
    }


    public void storeUnits() {
        Map<Product, Integer> newProducts = ProductionDepartment.getInstance().getNumberProducedProducts();
        for(HashMap.Entry<Product, Integer> entry : newProducts.entrySet()) {
            if(this.inventory.get(entry.getKey()) != null) {
                int aggregatedUnits = this.inventory.get(entry.getKey()) + entry.getValue();
                this.inventory.put(entry.getKey(), aggregatedUnits);
            } else {
                this.inventory.put(entry.getKey(), entry.getValue());
            }
        }
        ProductionDepartment.getInstance().clearInventory();
        Map<Component, Integer> newComponents = ProcurementDepartment.getInstance().getOrderedComponents();
        for(HashMap.Entry<Component, Integer> entry : newComponents.entrySet()) {
            if(this.inventory.get(entry.getKey()) != null) {
                int aggregatedUnits = this.inventory.get(entry.getKey()) + entry.getValue();
                this.inventory.put(entry.getKey(), aggregatedUnits);
            } else {
                this.inventory.put(entry.getKey(), entry.getValue());
            }
        }
        ProcurementDepartment.getInstance().clearOrderedComponents();
    }

    public int calculateStoredUnits() {
        this.storedUnits = 0;
        for(HashMap.Entry<Unit, Integer> entry : this.inventory.entrySet()) {
            this.storedUnits += entry.getValue();
        }
        return this.storedUnits;
    }

    public int calculateTotalCapacity() {
        this.totalCapacity = 0;
        for(Warehouse warehouse : this.warehouses) {
            this.totalCapacity += warehouse.getCapacity();
        }
        return totalCapacity;
    }

    /* */
    public int calculateFreeStorage() {
        return this.totalCapacity - this.storedUnits;
    }

    public double sellProduct (HashMap.Entry<Unit, Integer> soldProduct) {
        if(this.inventory.get(soldProduct.getKey()) != null && this.inventory.get(soldProduct.getKey()) >= soldProduct.getValue()) {
            int newInventoryUnits = this.inventory.get(soldProduct.getKey()) - soldProduct.getValue();
            if(newInventoryUnits == 0) {
                this.inventory.remove(soldProduct.getKey());
            } else {
                this.inventory.put(soldProduct.getKey(), newInventoryUnits);
            }
        }
        return soldProduct.getKey().getSalesPrice() * soldProduct.getValue();
    }

    public double buildWarehouse(LocalDate gameDate) {
        if(this.warehouseSlots > this.warehouses.size()) {
            Warehouse warehouse = new Warehouse(WarehouseType.BUILT);
            warehouse.setBuildDate(gameDate);
            warehouses.add(warehouse);
            this.calculateMonthlyCostWarehousing(gameDate);
            if(this.warehouseSlots == this.warehouses.size()) {
                this.warehouseSlotsAvailable = false;
            }
            return warehouse.getBuildingCost();
        }
        this.warehouseSlotsAvailable = false;
        return 0;
    }

    public double rentWarehouse(LocalDate gameDate) {
        if(this.warehouseSlots > this.warehouses.size()) {
            Warehouse warehouse = new Warehouse(WarehouseType.RENTED);
            warehouses.add(warehouse);
            this.calculateMonthlyCostWarehousing(gameDate);
            if(this.warehouseSlots == this.warehouses.size()) {
                this.warehouseSlotsAvailable = false;
            }
            return warehouse.getMonthlyRentalCost();
        }
        this.warehouseSlotsAvailable = false;
        return 0;
    }

    public void depreciateAllWarehouseResaleValues(LocalDate gameDate) {
        for(Warehouse warehouse : this.warehouses) {
            warehouse.depreciateWarehouseResaleValue(gameDate);
        }
    }

    public double getWarehouseResaleValue(Warehouse warehouse) {
        return warehouse.getResaleValue();
    }

    public Map<Warehouse, Double> getAllWarehouseResaleValues() {
        Map<Warehouse, Double> allWarehouseResaleValues = new HashMap<>();
        for(Warehouse warehouse : this.warehouses) {
            allWarehouseResaleValues.put(warehouse, warehouse.getResaleValue());
        }
        return allWarehouseResaleValues;
    }

    public double sellWarehouse(Warehouse warehouse) {
        double resaleValue = 0;
        if(warehouse.getWarehouseType() == WarehouseType.BUILT) {
            resaleValue = warehouse.getResaleValue();
        }
        warehouses.remove(warehouse);
        return resaleValue;
    }

    public double calculateMonthlyCostWarehousing(LocalDate gameDate) {
        double fixCostWarehouses = 0;
        double rentalCostsWarehouses = 0;
        LocalDate oldDate = gameDate.plusDays(-1);
        if(oldDate.getMonth() != gameDate.getMonth()) {
            for(Warehouse warehouse : this.warehouses) {
                fixCostWarehouses += warehouse.getMonthlyFixCostWarehouse();
                rentalCostsWarehouses += warehouse.getMonthlyRentalCost();
            }
        }
        this.monthlyCostWarehousing = fixCostWarehouses + rentalCostsWarehouses;
        return this.monthlyCostWarehousing;
    }

    public double calculateDailyStorageCost() {
        int variableStorageCost = 5;
        this.dailyStorageCost = 5 * calculateStoredUnits();
        this.monthlyStorageCost += this.dailyStorageCost;
        return this.dailyStorageCost;
    }

    public void resetMonthlyStorageCost() {
        this.monthlyStorageCost = 0;
    }

    public double calculateTotalMonthlyWarehousingCost() {
        this.monthlyTotalCostWarehousing =  this.monthlyCostWarehousing + this.monthlyStorageCost;
        this.resetMonthlyStorageCost();
        return this.monthlyTotalCostWarehousing;
    }

    public int getFreeStorage() {
        return this.freeStorage;
    }

    public boolean checkFreeStorageThreshold() {
        if(this.totalCapacity == 0) {
            return false;
        }
        if((this.freeStorage / this.totalCapacity) < 0.1) {
            this.daysSinceFreeStorageThreshold++;
            return true;
        } else {
            this.daysSinceFreeStorageThreshold = 0;
            return false;
        }
    }

    public int getDaysSinceFreeStorageThreshold() {
        return this.daysSinceFreeStorageThreshold;
    }

    public List<Warehouse> getWarehouses() {
        return this.warehouses;
    }

    public void decreaseStoredUnitsRel(double percentage) {
        for(HashMap.Entry<Unit, Integer> entry : this.inventory.entrySet()) {
            this.inventory.put(entry.getKey(), (int) (entry.getValue() * 0.7));
        }
    }

    public boolean checkWarehouseCapacityThreshold() {
        if(this.totalCapacity == 0) {
            return false;
        }
        return (this.storedUnits / this.totalCapacity) >= 0.8;
    }

    public void decreaseCapacity(int capacity) {
        Warehouse damagedWarehouse = this.warehouses.get(0);
        damagedWarehouse.setCapacity(damagedWarehouse.getCapacity() - capacity);
    }

    public double sellProducts(Map<Unit, Integer> sales) {
        double earnedMoney = 0;
        for(Map.Entry<Unit, Integer> entry : this.inventory.entrySet()) {
            if(entry.getKey().getUnitType() == UnitType.PRODUCT_UNIT) {
                this.inventory.put(entry.getKey(), entry.getValue() - sales.get(entry.getKey()));
                earnedMoney += entry.getKey().getSalesPrice() * sales.get(entry.getKey());
            }
        }
        return earnedMoney;
    }

    public void calculateAll() {
        this.storeUnits();
        this.calculateStoredUnits();
        this.calculateTotalCapacity();
        this.calculateFreeStorage();
        this.calculateDailyStorageCost();
        this.calculateTotalMonthlyWarehousingCost();
    }

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
		if (!this.inventory.containsKey(unit)) {
			return 0;
		}
		return this.inventory.get(unit);
	}

	public int getWarehouseSlots() {
	    return this.warehouseSlots;
    }

    public boolean getWarehouseSlotsAvailable() {
	    this.warehouseSlotsAvailable = this.warehouseSlots > this.warehouses.size();
	    return this.warehouseSlotsAvailable;
    }

    public int getTotalCapacity() {
        return this.totalCapacity;
    }

    public int getStoredUnits() {
        return this.storedUnits;
    }

    public double getMonthlyCostWarehousing() {
        return this.monthlyCostWarehousing;
    }

    public double getDailyStorageCost() {
        return this.dailyStorageCost;
    }

    public double getMonthlyStorageCost() {
        return this.monthlyStorageCost;
    }

    public double getMonthlyTotalCostWarehousing() {
        return this.monthlyTotalCostWarehousing;
    }

    public static void setInstance(WarehousingDepartment instance) {
        WarehousingDepartment.instance = instance;
    }

    @Override
    public void registerPropertyChangeListener(PropertyChangeListener listener) {
        this.inventoryChange.addPropertyChangeListener(listener);
    }
}
