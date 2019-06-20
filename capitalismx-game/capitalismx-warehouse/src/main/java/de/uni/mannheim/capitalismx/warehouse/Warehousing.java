package de.uni.mannheim.capitalismx.warehouse;

import de.uni.mannheim.capitalismx.production.Product;
import de.uni.mannheim.capitalismx.production.Production;

import java.util.ArrayList;
import java.util.HashMap;

public class Warehousing {

    private static Warehousing instance;
    private ArrayList<Warehouse> warehouses;
    private HashMap<Product, Integer> inventory;
    private int totalCapacity;
    private int freeStorage;
    private int storedUnits;
    private double monthlyCostWarehousing;
    private double dailyStorageCost;
    private double monthlyStorageCost;
    private double monthlyTotalCostWarehousing;
    private int daysSinceFreeStorageThreshold;

    private Warehousing() {
        this.warehouses = new ArrayList<Warehouse>();
        this.inventory = new HashMap<Product, Integer>();
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
        HashMap<Product, Integer> newUnits = Production.getInstance().getInventory();
        for(HashMap.Entry<Product, Integer> entry : newUnits.entrySet()) {
            if(this.inventory.get(entry.getKey()) != null) {
                int aggregatedUnits = this.inventory.get(entry.getKey()) + entry.getValue();
                this.inventory.put(entry.getKey(), aggregatedUnits);
            } else {
                this.inventory.put(entry.getKey(), entry.getValue());
            }
        }
        Production.getInstance().clearInventory();
        this.calculateStoredUnits();
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

    public double buildWarehouse() {
        Warehouse warehouse = new Warehouse(WarehouseType.BUILT);
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

    public void depreciateAllWarehouseResaleValues() {
        for(Warehouse warehouse : this.warehouses) {
            warehouse.depreciateWarehouseResaleValue();
        }
    }

    public double getWarehouseResaleValue(Warehouse warehouse) {
        return warehouse.getResaleValue();
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

    public ArrayList<Warehouse> getWarehouses() {
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
}