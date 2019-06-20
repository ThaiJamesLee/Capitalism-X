package de.uni.mannheim.capitalismx.warehouse;

import java.time.LocalDate;
import java.time.Period;

public class Warehouse {

    private WarehouseType warehouseType;
    private double buildingCost;
    private int capacity;
    private double monthlyRentalCost;
    private double variableStorageCost;
    private double monthlyFixCostWarehouse;
    private double resaleValue;
    private LocalDate buildDate;
    private double depreciationRateWarehouse;
    /* Placeholder */
    private LocalDate gameDate = LocalDate.now();

    public Warehouse(WarehouseType warehouseType) {
        this.capacity = 5000;
        this.warehouseType = warehouseType;
        this.variableStorageCost = 5;
        this.monthlyFixCostWarehouse = 1000;
        /* Placeholder */
        this.buildDate = gameDate;
        this.depreciationRateWarehouse = 1/14;
        if(this.warehouseType == WarehouseType.BUILT) {
            this.buildingCost = 250000;
            this.monthlyRentalCost = 0;
            this.resaleValue = 250000;
        } else {
            this.buildingCost = 0;
            this.monthlyRentalCost = 10000;
            this.resaleValue = 0;
        }
    }

    public double depreciateWarehouseResaleValue() {
        int yearsSinceWarehouseBuilt = Period.between(buildDate, gameDate).getYears();
        if (this.warehouseType == WarehouseType.BUILT) {
            this.resaleValue = 250000 - 25000 * depreciationRateWarehouse * yearsSinceWarehouseBuilt;
            return this.resaleValue;
        }
        return 0;
    }


    public double getBuildingCost() {
        return this.buildingCost;
    }

    public double getMonthlyRentalCost() {
        return this.monthlyRentalCost;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public double getVariableStorageCost() {
        return this.variableStorageCost;
    }

    public double getMonthlyFixCostWarehouse() {
        return this.monthlyFixCostWarehouse;
    }

    public double getResaleValue() {
        return this.resaleValue;
    }

    public WarehouseType getWarehouseType() {
        return this.warehouseType;
    }
}
