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

    public double buildWarehouse(WarehouseType warehouseType) {
        Warehouse warehouse = new Warehouse(warehouseType);
        warehouses.add(warehouse);

        return warehouse.getBuildingCost();
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
}
