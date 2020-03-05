package de.uni.mannheim.capitalismx.warehouse;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;

/**
 * Represents a warehouse.
 * It has costs associated depending on the warehouse type and its resale value depreciates over time.
 * It has a capacity which is used to store products and components.
 *
 * @author dzhao
 */
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
    private int usefulLife;

    private static final String DEFAULTS_PROPERTIES_FILE= "warehouse-defaults";

    /**
     * Instantiates a new Warehouse.
     * Initializes all variables.
     *
     * @param warehouseType the warehouse type
     */
    public Warehouse(WarehouseType warehouseType) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(DEFAULTS_PROPERTIES_FILE);
        this.capacity = Integer.valueOf(resourceBundle.getString("warehouse.capacity"));
        this.warehouseType = warehouseType;
        this.variableStorageCost = Double.valueOf(resourceBundle.getString("warehouse.variable.cost"));
        this.monthlyFixCostWarehouse = Double.valueOf(resourceBundle.getString("warehouse.monthly.fix.cost"));
        this.usefulLife = Integer.valueOf(resourceBundle.getString("warehouse.useful.life"));
        this.depreciationRateWarehouse = 1 / this.usefulLife;
        if (this.warehouseType == WarehouseType.BUILT) {
            this.buildingCost = Double.valueOf(resourceBundle.getString("warehouse.building.cost.built"));
            this.monthlyRentalCost = Double.valueOf(resourceBundle.getString("warehouse.monthly.rental.cost.built"));
            this.resaleValue = Double.valueOf(resourceBundle.getString("warehouse.resaleValue.built"));
        } else {
            this.buildingCost = Double.valueOf(resourceBundle.getString("warehouse.building.cost.rented"));
            this.monthlyRentalCost = Double.valueOf(resourceBundle.getString("warehouse.monthly.rental.cost.rented"));
            this.resaleValue = Double.valueOf(resourceBundle.getString("warehouse.resaleValue.rented"));
        }
    }

    /**
     * Depreciates warehouse resale value.
     * Based on report of predecessor group
     *
     * @param gameDate the game date
     * @return resale value
     */
    public double depreciateWarehouseResaleValue(LocalDate gameDate) {
        int yearsSinceWarehouseBuilt = Period.between(buildDate, gameDate).getYears();
        if (this.warehouseType == WarehouseType.BUILT) {
            this.resaleValue = 250000 - 25000 * depreciationRateWarehouse * yearsSinceWarehouseBuilt;
            return this.resaleValue;
        }
        return 0;
    }

    /**
     * Sets capacity.
     *
     * @param capacity the capacity
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Gets building cost.
     *
     * @return the building cost
     */
    public double getBuildingCost() {
        return this.buildingCost;
    }

    /**
     * Gets monthly rental cost.
     *
     * @return the monthly rental cost
     */
    public double getMonthlyRentalCost() {
        return this.monthlyRentalCost;
    }

    /**
     * Gets capacity.
     *
     * @return the capacity
     */
    public int getCapacity() {
        return this.capacity;
    }

    /**
     * Gets variable storage cost.
     *
     * @return the variable storage cost
     */
    public double getVariableStorageCost() {
        return this.variableStorageCost;
    }

    /**
     * Gets monthly fix cost warehouse.
     *
     * @return the monthly fix cost warehouse
     */
    public double getMonthlyFixCostWarehouse() {
        return this.monthlyFixCostWarehouse;
    }

    /**
     * Gets resale value.
     *
     * @return the resale value
     */
    public double getResaleValue() {
        return this.resaleValue;
    }

    /**
     * Gets warehouse type.
     *
     * @return the warehouse type
     */
    public WarehouseType getWarehouseType() {
        return this.warehouseType;
    }

    /**
     * Gets useful life.
     *
     * @return the useful life
     */
    public int getUsefulLife() {
        return this.usefulLife;
    }

    /**
     * Calculate time used int.
     *
     * @param gameDate the game date
     * @return the int
     */
    public int calculateTimeUsed(LocalDate gameDate){
        return Period.between(this.buildDate, gameDate).getYears();
    }

    /**
     * Sets build date.
     *
     * @param buildDate the build date
     */
    public void setBuildDate(LocalDate buildDate) {
        this.buildDate = buildDate;
    }

    /**
     * Gets build date.
     *
     * @return the build date
     */
    public LocalDate getBuildDate() {
        return this.buildDate;
    }

    /**
     * Gets depreciation rate warehouse.
     *
     * @return the depreciation rate warehouse
     */
    public double getDepreciationRateWarehouse() {
        return this.depreciationRateWarehouse;
    }
}