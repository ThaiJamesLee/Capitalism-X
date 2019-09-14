package de.uni.mannheim.capitalismx.warehouse;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

public class Warehouse implements Serializable {

    private WarehouseType warehouseType;
    private double buildingCost;
    private int capacity;
    private double monthlyRentalCost;
    private double variableStorageCost;
    private double monthlyFixCostWarehouse;
    private double resaleValue;
    private LocalDate buildDate;
    private double depreciationRateWarehouse;
    //TODO usefullife jedes jahr runter?
    private int usefulLife;

    public Warehouse(WarehouseType warehouseType) {
        this.capacity = 5000;
        this.warehouseType = warehouseType;
        this.variableStorageCost = 5;
        this.monthlyFixCostWarehouse = 1000;
        this.depreciationRateWarehouse = 1/14;
        this.usefulLife = 14;
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

    public double depreciateWarehouseResaleValue(LocalDate gameDate) {
        int yearsSinceWarehouseBuilt = Period.between(buildDate, gameDate).getYears();
        if (this.warehouseType == WarehouseType.BUILT) {
            this.resaleValue = 250000 - 25000 * depreciationRateWarehouse * yearsSinceWarehouseBuilt;
            return this.resaleValue;
        }
        return 0;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
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

    public int getUsefulLife() {
        return this.usefulLife;
    }

    public int calculateTimeUsed(LocalDate gameDate){
        return Period.between(this.buildDate, gameDate).getYears();
    }

    public void setBuildDate(LocalDate buildDate) {
        this.buildDate = buildDate;
    }

    public LocalDate getBuildDate() {
        return this.buildDate;
    }

    public double getDepreciationRateWarehouse() {
        return this.depreciationRateWarehouse;
    }
}