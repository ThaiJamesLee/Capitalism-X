package de.uni.mannheim.capitalismx.procurement.component;

import de.uni.mannheim.capitalismx.utils.random.RandomNumberGenerator;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Random;

/**
 * This is a unit which is used for the of a production of a product, a component.
 * It has two a base cost which is randomized based on the supplier category chosen and a normal distribution with a
 * standard deviation of 0.1 and an average of 1. It determines the costs of buying this component.
 * The warehouse sales price is based on the lower bound randomized factor of the supplier cost multiplicator and the
 * supplier category. It determines for how much the component can be sold for in the warehouse.
 *
 * @author dzhao
 */
public class Component extends Unit implements Serializable {

    /**
     * The unit type of this specific component. It is always set to UnitType.COMPONENT_UNIT.
     */
    private UnitType unitType;
    private ComponentCategory componentCategory;
    private int componentLevel;

    /**
     * The initial price of component without regards to the date, the supplier and any randomizing factors.
     */
    private double initialComponentPrice;
    private int baseUtility;
    private int availabilityDate;
    private SupplierCategory supplierCategory;

    /**
     * A multiplicator used for the calculation of the base cost. It is randomized and the randomization factor is based
     * on the supplier category.
     */
    private double supplierCostMultiplicator;
    private int supplierQuality;
    private int supplierEcoIndex;

    /**
     * The base cost is the cost of buying this component.
     */
    private double baseCost;
    private ComponentType componentType;

    /**
     * Determines for how much the component can be sold for when selling through the warehouse.
     */
    private double warehouseSalesPrice;

    /**
     * Defines the date of the last baseCost calculation. It is used to make sure that baseCost is only calculated once
     * in a calendar week.
     */
    private LocalDate lastPriceCalculationDate;

    /**
     * Checks whether the baseCost was initialized.
     */
    private boolean baseCostInitialized;

    /**
     * Instantiates a new component independent from a supplier.
     *
     * @param componentType the component type
     */
    public Component(ComponentType componentType) {
        this.unitType = UnitType.COMPONENT_UNIT;
        this.componentCategory = componentType.getComponentCategory();
        //this.componentName = componentType.getComponentName();
        this.componentLevel = componentType.getComponentLevel();
        this.initialComponentPrice = componentType.getInitialComponentPrice();
        this.baseUtility = componentType.getBaseUtility();
        this.availabilityDate = componentType.getAvailabilityDate();
        this.componentType = componentType;
        this.baseCostInitialized = false;
    }

    /**
     * Instantiates a new Component. It sets the supplier and sets all relevant class variables which are dependent on
     * the supplier category.
     *
     * @param componentType    the component type
     * @param supplierCategory the supplier category
     * @param gameDate         the game date
     */
    public Component(ComponentType componentType, SupplierCategory supplierCategory, LocalDate gameDate) {
        this.unitType = UnitType.COMPONENT_UNIT;
        this.componentCategory = componentType.getComponentCategory();
        //this.componentName = componentType.getComponentName();
        this.componentLevel = componentType.getComponentLevel();
        this.initialComponentPrice = componentType.getInitialComponentPrice();
        this.baseUtility = componentType.getBaseUtility();
        this.availabilityDate = componentType.getAvailabilityDate();
        this.componentType = componentType;
        this.baseCostInitialized = false;
        this.supplierCategory = supplierCategory;
        this.lastPriceCalculationDate = gameDate;
        switch (supplierCategory) {
            case CHEAP:
                this.supplierCostMultiplicator = RandomNumberGenerator.getRandomDouble(0.7, 1.0);
                this.supplierQuality = RandomNumberGenerator.getRandomInt(80, 100);
                this.supplierEcoIndex = RandomNumberGenerator.getRandomInt(80, 100);
                this.warehouseSalesPrice = 0.7 * this.componentType.getInitialSalesPrice();
                break;
            case REGULAR:
                this.supplierCostMultiplicator = RandomNumberGenerator.getRandomDouble(0.85, 1.2);
                this.supplierQuality = RandomNumberGenerator.getRandomInt(55, 85);
                this.supplierEcoIndex = RandomNumberGenerator.getRandomInt(55, 85);
                this.warehouseSalesPrice = 0.85 * this.componentType.getInitialSalesPrice();
                break;
            case PREMIUM:
                this.supplierCostMultiplicator = RandomNumberGenerator.getRandomDouble(1.1, 1.5);
                this.supplierQuality = RandomNumberGenerator.getRandomInt(10, 60);
                this.supplierEcoIndex = RandomNumberGenerator.getRandomInt(10, 60);
                this.warehouseSalesPrice = 1.1 * this.componentType.getInitialSalesPrice();
        }
        this.calculateRandomizedBaseCost(gameDate);
    }

    /**
     * Sets supplier category of a component. It sets all relevant class variables which are dependent on
     * the supplier category.
     *
     * @param supplierCategory the supplier category
     * @param gameDate         the game date
     */
    public void setSupplierCategory(SupplierCategory supplierCategory, LocalDate gameDate) {
        this.supplierCategory = supplierCategory;
        this.lastPriceCalculationDate = gameDate;
        switch (supplierCategory) {
            case PREMIUM:
                this.supplierCostMultiplicator = RandomNumberGenerator.getRandomDouble(1.1, 1.5);
                this.supplierQuality = RandomNumberGenerator.getRandomInt(80, 100);
                this.supplierEcoIndex = RandomNumberGenerator.getRandomInt(80, 100);
                this.warehouseSalesPrice = 0.7 * this.componentType.getInitialSalesPrice();
                break;
            case REGULAR:
                this.supplierCostMultiplicator = RandomNumberGenerator.getRandomDouble(0.85, 1.2);
                this.supplierQuality = RandomNumberGenerator.getRandomInt(55, 85);
                this.supplierEcoIndex = RandomNumberGenerator.getRandomInt(55, 85);
                this.warehouseSalesPrice = 0.85 * this.componentType.getInitialSalesPrice();
                break;
            case CHEAP:
                this.supplierCostMultiplicator = RandomNumberGenerator.getRandomDouble(0.7, 1.0);
                this.supplierQuality = RandomNumberGenerator.getRandomInt(10, 60);
                this.supplierEcoIndex = RandomNumberGenerator.getRandomInt(10, 60);
                this.warehouseSalesPrice = 1.1 * this.componentType.getInitialSalesPrice();
        }
        this.calculateRandomizedBaseCost(gameDate);
    }

    /**
     * Gets component category.
     *
     * @return the component category
     */
    public ComponentCategory getComponentCategory() {
        return this.componentCategory;
    }

    /**
     * Gets component name.
     *
     * @param locale the locale
     * @return the component name
     */
    public String getComponentName(Locale locale) {
    	return this.componentType.getName(locale);
    }

    /**
     * Gets component level.
     *
     * @return the component level
     */
    public int getComponentLevel() {
        return this.componentLevel;
    }

    /**
     * Gets initial component price.
     *
     * @return the initial component price
     */
    public double getInitialComponentPrice() {
        return this.initialComponentPrice;
    }

    /**
     * Gets base utility.
     *
     * @return the base utility
     */
    public int getBaseUtility() {
        return this.baseUtility;
    }

    /**
     * Gets availability date.
     *
     * @return the availability date
     */
    public int getAvailabilityDate() {
        return this.availabilityDate;
    }

    /**
     * Is available boolean.
     *
     * @param gameDate the game date
     * @return the boolean
     */
    public boolean isAvailable(LocalDate gameDate) {
        return gameDate.getYear() >= this.availabilityDate;
    }

    /**
     * Gets supplier category.
     *
     * @return the supplier category
     */
    public SupplierCategory getSupplierCategory() {
        return this.supplierCategory;
    }

    /**
     * Gets supplier category short form.
     *
     * @return the supplier category short form
     */
    public String getSupplierCategoryShortForm() {
        return this.supplierCategory.toString().replace(" Supplier", "");
    }

    /**
     * Gets supplier cost multiplicator.
     *
     * @return the supplier cost multiplicator
     */
    public double getSupplierCostMultiplicator() {
        return this.supplierCostMultiplicator;
    }

    /**
     * Gets supplier quality.
     *
     * @return the supplier quality
     */
    public int getSupplierQuality() {
        return this.supplierQuality;
    }

    /**
     * Gets supplier eco index.
     *
     * @return the supplier eco index
     */
    public int getSupplierEcoIndex() {
        return this.supplierEcoIndex;
    }

    /**
     * Gets component type.
     *
     * @return the component type
     */
    public ComponentType getComponentType() {
        return this.componentType;
    }

    /**
     * Gets base cost.
     *
     * @return the base cost
     */
    public double getBaseCost() {
        return this.baseCost;
    }

    /**
     * Calculate randomized base cost.
     * It can only be calculated once every calendar week. If it is called when there was already another calculation in
     * that week, it returns the saved base cost.
     * It uses the formula defined in chapter 3.4 of the final report of our predecessor group. It also adds a randomizing
     * factor based on a normal distribution with a standard deviation of 0.1 and an average of 1.
     *
     * @param gameDate the game date
     * @return the base cost as a double
     */
    public double calculateRandomizedBaseCost(LocalDate gameDate) {
        if (ChronoUnit.DAYS.between(this.lastPriceCalculationDate.with(DayOfWeek.MONDAY), gameDate.with(DayOfWeek.MONDAY)) > 0 || !baseCostInitialized) {
            this.lastPriceCalculationDate = gameDate;
            this.baseCostInitialized = true;
            int gameYear = gameDate.getYear();
            double tBPM = -0.00011199 * Math.pow((gameYear - this.availabilityDate + 1), 5)
                    + 0.01117974 * Math.pow((gameYear - this.availabilityDate + 1), 4)
                    - 0.42393538 * Math.pow((gameYear - this.availabilityDate + 1), 3)
                    + 7.32188889 * Math.pow((gameYear - this.availabilityDate + 1), 2)
                    - 49.69789098 * (gameYear - this.availabilityDate + 1)
                    + 143.3244916;
            double tBCP = this.initialComponentPrice * (tBPM / 100);
            this.baseCost = tBCP * this.supplierCostMultiplicator;
            Random random = new Random();
            double normalDistributedValue = random.nextGaussian() * 0.1 + 1;
            this.baseCost = this.baseCost * normalDistributedValue;
            return this.baseCost;
        } else {
            return this.baseCost;
        }
    }

    /**
     * Gets time based component cost.
     * It is similar to the randomized base cost calculation but it removes all randomizing
     * factors.
     * It is later used in the customer satisfaction calculation.
     *
     * @param gameDate the game date
     * @return the time based component cost
     */
    public double getTimeBasedComponentCost(LocalDate gameDate) {
        int gameYear = gameDate.getYear();
        double tBCP = 0;
        double tBPM = -0.00011199 * Math.pow((gameYear - this.availabilityDate + 1), 5)
                + 0.01117974 * Math.pow((gameYear - this.availabilityDate + 1), 4)
                - 0.42393538 * Math.pow((gameYear - this.availabilityDate + 1), 3)
                + 7.32188889 * Math.pow((gameYear - this.availabilityDate + 1), 2)
                - 49.69789098 * (gameYear - this.availabilityDate + 1)
                + 143.3244916;
        tBCP = this.initialComponentPrice * (tBPM / 100);
        return tBCP;
    }

    /**
     * Gets unit type.
     *
     * @return the unit type
     */
    public UnitType getUnitType() {
        return this.unitType;
    }

    /**
     * Gets warehouse sales price
     *
     * @return the warehouse sales price
     */
    public double getWarehouseSalesPrice() {
        return this.warehouseSalesPrice;
    }

    /**
     * Gets sales price.
     * It returns the same value as getWarehouseSalesPrice(), it is not further used but it has to be implemented for
     * a unit.
     *
     * @return the warehouse sales price
     */
    public double getSalesPrice() {
        return this.warehouseSalesPrice;
    }

    /**
     * Compares this component to an other component.
     *
     * @param component The other component used for the comparison.
     * @return true if ComponentType and SupplierCategory are equal otherwise return false
     */
    public boolean sameComponent(Component component) {
        return this.componentType == component.getComponentType() && this.supplierCategory == component.getSupplierCategory();
    }
}
