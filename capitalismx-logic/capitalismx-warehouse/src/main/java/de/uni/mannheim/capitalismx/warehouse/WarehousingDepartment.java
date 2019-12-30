package de.uni.mannheim.capitalismx.warehouse;

import de.uni.mannheim.capitalismx.domain.department.DepartmentImpl;
import de.uni.mannheim.capitalismx.procurement.component.Component;
import de.uni.mannheim.capitalismx.procurement.component.ProcurementDepartment;
import de.uni.mannheim.capitalismx.procurement.component.Unit;
import de.uni.mannheim.capitalismx.procurement.component.UnitType;
import de.uni.mannheim.capitalismx.production.Product;
import de.uni.mannheim.capitalismx.production.ProductionDepartment;

import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    }

    public static synchronized WarehousingDepartment getInstance() {
        if(WarehousingDepartment.instance == null) {
            WarehousingDepartment.instance = new WarehousingDepartment();
        }
        return WarehousingDepartment.instance;
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
        Warehouse warehouse = new Warehouse(WarehouseType.BUILT);
        warehouse.setBuildDate(gameDate);
        warehouses.add(warehouse);
        this.calculateMonthlyCostWarehousing();
        return warehouse.getBuildingCost();
    }

    public double rentWarehouse() {
        Warehouse warehouse = new Warehouse(WarehouseType.RENTED);
        warehouses.add(warehouse);
        this.calculateMonthlyCostWarehousing();
        return warehouse.getMonthlyRentalCost();
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

    public double calculateMonthlyCostWarehousing() {
        double fixCostWarehouses = 0;
        double rentalCostsWarehouses = 0;
        for(Warehouse warehouse : this.warehouses) {
            fixCostWarehouses += warehouse.getMonthlyFixCostWarehouse();
            rentalCostsWarehouses += warehouse.getMonthlyRentalCost();
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
        this.calculateMonthlyCostWarehousing();
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

    }
}
