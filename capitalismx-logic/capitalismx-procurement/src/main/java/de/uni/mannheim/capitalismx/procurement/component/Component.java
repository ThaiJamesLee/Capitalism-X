package de.uni.mannheim.capitalismx.procurement.component;

import de.uni.mannheim.capitalismx.utils.random.RandomNumberGenerator;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Random;

import java.util.ResourceBundle;

import de.uni.mannheim.capitalismx.utils.random.RandomNumberGenerator;

public class Component extends Unit implements Serializable {

    private UnitType unitType;
    private ComponentCategory componentCategory;
    //Wird für Internationalisierung aus ComponentType geladen in korrekter sprache
    //private String componentName;
    private int componentLevel;
    private double initialComponentPrice;
    private int baseUtility;
    private int availabilityDate;
    private SupplierCategory supplierCategory;
    private double supplierCostMultiplicator;
    private int supplierQuality;
    private int supplierEcoIndex;
    private double baseCost;
    private ComponentType componentType;
    private double warehouseSalesPrice;
    private LocalDate lastPriceCalculationDate;
    private boolean baseCostInitialized;

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
        switch(supplierCategory) {
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

    public void setSupplierCategory(SupplierCategory supplierCategory, LocalDate gameDate) {
        this.supplierCategory = supplierCategory;
        this.lastPriceCalculationDate = gameDate;
        switch(supplierCategory) {
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

    public ComponentCategory getComponentCategory() {
        return this.componentCategory;
    }

    public String getComponentName(Locale locale) {
    	return this.componentType.getName(locale);
    }

    public int getComponentLevel() {
        return this.componentLevel;
    }

    public double getInitialComponentPrice() {
        return this.initialComponentPrice;
    }

    public int getBaseUtility() {
        return this.baseUtility;
    }

    public int getAvailabilityDate() {
        return this.availabilityDate;
    }

    public boolean isAvailable(LocalDate gameDate) {
        return gameDate.getYear() >= this.availabilityDate;
    }

    public SupplierCategory getSupplierCategory() {
        return this.supplierCategory;
    }

    public String getSupplierCategoryShortForm() {
        return this.supplierCategory.toString().replace(" Supplier", "");
    }

    public double getSupplierCostMultiplicator() {
        return this.supplierCostMultiplicator;
    }

    public int getSupplierQuality() {
        return this.supplierQuality;
    }

    public int getSupplierEcoIndex() {
        return this.supplierEcoIndex;
    }

    public ComponentType getComponentType() {
        return this.componentType;
    }

    public double getBaseCost() {
        return this.baseCost;
    }

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

    public UnitType getUnitType() {
        return this.unitType;
    }

    public double getWarehouseSalesPrice() {
        return this.warehouseSalesPrice;
    }

    public double getSalesPrice() {
        return this.warehouseSalesPrice;
    }
}
