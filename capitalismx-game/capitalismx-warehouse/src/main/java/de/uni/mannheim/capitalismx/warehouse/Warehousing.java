package de.uni.mannheim.capitalismx.warehouse;

import de.uni.mannheim.capitalismx.production.Product;
import de.uni.mannheim.capitalismx.production.Production;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Warehousing implements Serializable {

    private static Warehousing instance;
    private List<Warehouse> warehouses;
    private Map<Product, Integer> inventory;
    private int totalCapacity;
    private int freeStorage;
    private int storedUnits;
    private double monthlyCostWarehousing;
    private double dailyStorageCost;
    private double monthlyStorageCost;
    private double monthlyTotalCostWarehousing;
    private int daysSinceFreeStorageThreshold;

    private Warehousing() {
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

    public static synchronized Warehousing getInstance() {
        if(Warehousing.instance == null) {
            Warehousing.instance = new Warehousing();
        }
        return Warehousing.instance;
    }

    public void storeUnits() {
        Map<Product, Integer> newUnits = Production.getInstance().getNumberProducedProducts();
        for(HashMap.Entry<Product, Integer> entry : newUnits.entrySet()) {
            if(this.inventory.get(entry.getKey()) != null) {
                int aggregatedUnits = this.inventory.get(entry.getKey()) + entry.getValue();
                this.inventory.put(entry.getKey(), aggregatedUnits);
            } else {
                this.inventory.put(entry.getKey(), entry.getValue());
            }
        }
        Production.getInstance().clearInventory();
    }

    public int calculateStoredUnits() {
        this.storedUnits = 0;
        for(HashMap.Entry<Product, Integer> entry : this.inventory.entrySet()) {
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

    public double sellProduct (HashMap.Entry<Product, Integer> soldProduct) {
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
        if((this.freeStorage / this.totalCapacity) <01) {
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
        for(HashMap.Entry<Product, Integer> entry : this.inventory.entrySet()) {
            this.inventory.put(entry.getKey(), (int) (entry.getValue() * 0.7));
        }
    }

    public boolean checkWarehouseCapacityThreshold() {
        return (this.storedUnits / this.totalCapacity) >= 0.8;
    }

    public void decreaseCapacity(int capacity) {
        Warehouse damagedWarehouse = this.warehouses.get(0);
        damagedWarehouse.setCapacity(damagedWarehouse.getCapacity() - capacity);
    }

    public double sellProducts(Map<Product, Integer> sales) {
        double earnedMoney = 0;
        for(Map.Entry<Product, Integer> entry : this.inventory.entrySet()) {
            this.inventory.put(entry.getKey(), entry.getValue() - sales.get(entry.getKey()));
            earnedMoney += entry.getKey().getSalesPrice() * sales.get(entry.getKey());
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

    public Map<Product, Integer> getInventory() {
        return this.inventory;
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
}
